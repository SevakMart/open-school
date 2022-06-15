package app.openschool.category;

import app.openschool.category.api.dto.CategoryDto;
import app.openschool.category.api.dto.PreferredCategoryDto;
import app.openschool.category.api.mapper.CategoryMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    if (StringUtils.isBlank(title)) {
      return mapCategoriesToSubcategories(
          categoryRepository.findByParentCategoryIsNull());
    }

    var subCategoriesGroupedByParent =
        categoryRepository.findByTitleIgnoreCaseStartingWith(title).stream()
            .filter(category -> !category.isParent())
            .collect(
                Collectors.groupingBy(
                    category -> category.getParentCategory().getTitle(), Collectors.toSet()));

    final Map<String, List<PreferredCategoryDto>> mappedCategories = new HashMap<>();
    subCategoriesGroupedByParent.forEach(
        (s, categories) ->
            mappedCategories.put(s, CategoryMapper.toPreferredCategoryDtos(categories)));
    return mappedCategories;
  }

  private Map<String, List<PreferredCategoryDto>> mapCategoriesToSubcategories(
      List<Category> categories) {

    return categories.stream()
        .collect(
            Collectors.toMap(
                Category::getTitle,
                category -> CategoryMapper.toPreferredCategoryDtos(category.getSubCategories())));
  }

}
