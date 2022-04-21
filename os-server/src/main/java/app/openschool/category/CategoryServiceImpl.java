package app.openschool.category;

import app.openschool.category.api.dto.CategoryDto;
import app.openschool.category.api.dto.PreferredCategoryDto;
import app.openschool.category.api.mapper.CategoryMapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryServiceImpl implements CategoryService {

  private final CategoryRepository categoryRepository;

  public CategoryServiceImpl(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  @Override
  public Page<CategoryDto> findAllCategories(Pageable pageable) {
    return CategoryMapper.toCategoryDtoPage(categoryRepository.findAllCategories(pageable));
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
        .collect(Collectors.toList());
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
                        .collect(Collectors.toList())));
  }

  private Map<String, List<PreferredCategoryDto>> mapSubcategoriesToCategories(
      List<Category> categories) {

    Map<String, List<PreferredCategoryDto>> categoryMap = new HashMap<>();

    categories.forEach(
        (category -> {
          String parentCategoryTitle =
              categoryRepository.findCategoryById(category.getParentCategoryId()).getTitle();

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
}
