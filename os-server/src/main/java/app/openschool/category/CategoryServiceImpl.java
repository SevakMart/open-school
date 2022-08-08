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
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
  public Map<String, List<PreferredCategoryDto>> findCategoriesByTitle(String title) {

    Map<String, List<PreferredCategoryDto>> result;

    if (StringUtils.isBlank(title)) {
      result = findBySubCategories(categoryRepository.findByParentCategoryIsNotNull());
      result.putAll(findByParentCategories(categoryRepository.findByParentCategoryIsNull()));
      return result;
    }
    result =
        findBySubCategories(
            categoryRepository.findByTitleContainingIgnoreCaseAndParentCategoryIsNotNull(
                title.trim()));
    result.putAll(
        findByParentCategories(
            categoryRepository.findByTitleContainingIgnoreCaseAndParentCategoryIsNull(
                title.trim())));
    return result;
  }

  private static Map<String, List<PreferredCategoryDto>> findBySubCategories(
      List<Category> subCategories) {
    return subCategories.stream()
        .collect(
            groupingBy(
                subCategory -> subCategory.getParentCategory().getTitle(),
                mapping(CategoryMapper::toPreferredCategoryDto, toList())));
  }

  private static Map<String, List<PreferredCategoryDto>> findByParentCategories(
      List<Category> parentCategories) {
    return parentCategories.stream()
        .collect(
            Collectors.toMap(
                Category::getTitle,
                category ->
                    CategoryMapper.toPreferredCategoryDtoList(category.getSubCategories())));
  }

  @Override
  public Category findById(Long categoryId) {
    return categoryRepository.findById(categoryId).orElseThrow(IllegalArgumentException::new);
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
      return categoryRepository.save(new Category(title, null));
    }
    Category parent =
        categoryRepository.findById(parentCategoryId).orElseThrow(IllegalArgumentException::new);
    return categoryRepository.save(new Category(title, parent));
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
