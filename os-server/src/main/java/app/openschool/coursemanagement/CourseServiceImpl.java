package app.openschool.coursemanagement;

import app.openschool.coursemanagement.api.dto.CategoryDto;
import app.openschool.coursemanagement.api.dto.CategoryDtoForRegistration;
import app.openschool.coursemanagement.api.mapper.CategoryMapper;
import app.openschool.coursemanagement.entities.Category;
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
public class CourseServiceImpl implements CourseService {

  private final CategoryRepository categoryRepository;

  public CourseServiceImpl(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  @Override
  public Page<CategoryDto> findAllCategories(Pageable pageable) {
    return CategoryMapper.toCategoryDtoPage(categoryRepository.findAllCategories(pageable));
  }

  @Override
  @Transactional
  public Map<String, List<CategoryDtoForRegistration>> findCategoriesByTitle(String title) {

    Map<String, List<CategoryDtoForRegistration>> mappedCategories;

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
        .toList();
  }

  private Map<String, List<CategoryDtoForRegistration>> mapCategoriesToSubcategories(
      List<Category> categories) {
    return categories.stream()
        .collect(
            Collectors.toMap(
                Category::getTitle,
                (category) ->
                    categoryRepository.findCategoriesByParentCategoryId(category.getId()).stream()
                        .map((CategoryMapper::toCategoryDtoForRegistration))
                        .collect(Collectors.toList())));
  }

  private Map<String, List<CategoryDtoForRegistration>> mapSubcategoriesToCategories(
      List<Category> categories) {

    Map<String, List<CategoryDtoForRegistration>> categoryMap = new HashMap<>();

    categories.forEach(
        (category -> {
          String parentCategoryTitle =
              categoryRepository.findCategoryById(category.getParentCategoryId()).getTitle();

          if (categoryMap.containsKey(parentCategoryTitle)) {
            categoryMap
                .get(parentCategoryTitle)
                .add(CategoryMapper.toCategoryDtoForRegistration(category));
          } else {
            List<CategoryDtoForRegistration> subcategories = new ArrayList<>();
            subcategories.add(CategoryMapper.toCategoryDtoForRegistration(category));
            categoryMap.put(parentCategoryTitle, subcategories);
          }
        }));

    return categoryMap;
  }
}
