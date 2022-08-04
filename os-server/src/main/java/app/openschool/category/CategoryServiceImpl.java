package app.openschool.category;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;

import app.openschool.category.api.dto.CategoryDto;
import app.openschool.category.api.dto.CreateCategoryRequest;
import app.openschool.category.api.dto.ModifyCategoryRequest;
import app.openschool.category.api.dto.ParentAndSubCategoriesDto;
import app.openschool.category.api.dto.PreferredCategoryDto;
import app.openschool.category.api.mapper.CategoryMapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryServiceImpl implements CategoryService {

  private final CategoryRepository categoryRepository;
  private final MessageSource messageSource;

  public CategoryServiceImpl(CategoryRepository categoryRepository, MessageSource messageSource) {
    this.categoryRepository = categoryRepository;
    this.messageSource = messageSource;
  }

  @Override
  public Page<CategoryDto> findAllParentCategories(Pageable pageable) {
    return CategoryMapper.toCategoryDtoPage(
        categoryRepository.findByParentCategoryIsNull(pageable));
  }

  @Override
  @Transactional
  public Map<String, List<PreferredCategoryDto>> findCategoriesByTitle(String title) {

    Map<String, List<PreferredCategoryDto>> mappedCategories;

    if (StringUtils.isBlank(title)) {
      return mapCategoriesToSubcategories(
          categoryRepository.findCategoriesByParentCategoryId(null));
    }

    List<Category> parentCategories =
        getCategories(title, category -> category.getParentCategoryId() == null);

    List<Category> subcategories =
        getCategories(title, category -> category.getParentCategoryId() != null);

    if (parentCategories.size() > 0) {
      mappedCategories = mapCategoriesToSubcategories(parentCategories);
    } else if (subcategories.size() > 0) {
      mappedCategories = mapSubcategoriesToCategories(subcategories);
    } else {
      mappedCategories = new HashMap<>();
    }

    return mappedCategories;
  }

  private List<Category> getCategories(String title, Predicate<Category> predicate) {
    return categoryRepository.findByTitleIgnoreCaseStartingWith(title).stream()
        .filter(predicate)
        .collect(toList());
  }

  private Map<String, List<PreferredCategoryDto>> mapCategoriesToSubcategories(
      List<Category> categories) {
    return categories.stream()
        .collect(
            Collectors.toMap(
                Category::getTitle,
                (category) ->
                    categoryRepository.findCategoriesByParentCategoryId(category.getId()).stream()
                        .map((CategoryMapper::toPreferredCategoryDto))
                        .collect(toList())));
  }

  private Map<String, List<PreferredCategoryDto>> mapSubcategoriesToCategories(
      List<Category> categories) {

    Map<String, List<PreferredCategoryDto>> categoryMap = new HashMap<>();

    categories.forEach(
        (category -> {
          String parentCategoryTitle =
              categoryRepository.getById(category.getParentCategoryId()).getTitle();
          if (categoryMap.containsKey(parentCategoryTitle)) {
            categoryMap
                .get(parentCategoryTitle)
                .add(CategoryMapper.toPreferredCategoryDto(category));
          } else {
            List<PreferredCategoryDto> subcategories = new ArrayList<>();
            subcategories.add(CategoryMapper.toPreferredCategoryDto(category));
            categoryMap.put(parentCategoryTitle, subcategories);
          }
        }));

    return categoryMap;
  }

  @Override
  public ParentAndSubCategoriesDto findAll() {
    return new ParentAndSubCategoriesDto(
        categoryRepository.findByParentCategoryIsNotNull().stream()
            .collect(
                groupingBy(
                    subCategory -> CategoryMapper.toCategoryDto(subCategory.getParentCategory()),
                    mapping(CategoryMapper::toCategoryDto, toList()))));
  }

  @Override
  public Category add(CreateCategoryRequest request) {
    String title = request.getTitle();
    String logoPath = request.getLogoPath();
    Long parentCategoryId = request.getParentCategoryId();
    if (parentCategoryId == null) {
      return categoryRepository.save(new Category(title, logoPath, null));
    }
    Category parent =
        categoryRepository.findById(parentCategoryId).orElseThrow(IllegalArgumentException::new);
    return categoryRepository.save(new Category(title, logoPath, parent));
  }

  @Override
  public Category modify(Long categoryId, ModifyCategoryRequest request) {
    Category category =
        categoryRepository.findById(categoryId).orElseThrow(IllegalArgumentException::new);
    String newTitle = request.getTitle();
    String newLogoPath = request.getLogoPath();
    Long newParentCategoryId = request.getParentCategoryId();
    if (newTitle != null && newTitle.trim().length() > 0) {
      category.setTitle(newTitle);
    }
    if (newLogoPath != null && newLogoPath.trim().length() > 0) {
      category.setLogoPath(newLogoPath);
    }
    if (newParentCategoryId != null) {
      Category newParent =
          categoryRepository
              .findById(newParentCategoryId)
              .orElseThrow(IllegalArgumentException::new);
      category.setParentCategory(newParent);
    }
    return categoryRepository.save(category);
  }

  @Override
  public void delete(Long categoryId, Locale locale) {
    Category category =
        categoryRepository.findById(categoryId).orElseThrow(IllegalArgumentException::new);
    if (category.getParentCategoryId() == null) {
      throw new UnsupportedOperationException(
          messageSource.getMessage("category.delete.not.allowed", null, locale));
    }
    categoryRepository.deleteById(categoryId);
  }
}
