package app.openschool.category;

import app.openschool.category.api.dto.CategoryDto;
import app.openschool.category.api.dto.CreateCategoryRequest;
import app.openschool.category.api.dto.ModifyCategoryDataRequest;
import app.openschool.category.api.dto.ModifyCategoryImageRequest;
import app.openschool.category.api.dto.ParentAndSubCategoriesDto;
import app.openschool.category.api.dto.PreferredCategoryDto;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {

  Page<CategoryDto> findAllParentCategories(Pageable pageable);

  Map<String, List<PreferredCategoryDto>> findCategoriesByTitle(String title);

  ParentAndSubCategoriesDto findAll();

  Category findById(Long categoryId);

  Category add(CreateCategoryRequest createCategoryRequest);

  Category updateData(Long categoryId, ModifyCategoryDataRequest request);

  Category updateImage(Long categoryId, ModifyCategoryImageRequest request);

  void delete(Long categoryId, Locale locale);
}
