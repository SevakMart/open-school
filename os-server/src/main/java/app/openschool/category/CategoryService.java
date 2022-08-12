package app.openschool.category;

import app.openschool.category.api.dto.CategoryDto;
import app.openschool.category.api.dto.ParentAndSubCategoriesDto;
import app.openschool.category.api.dto.PreferredCategoryDto;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface CategoryService {

  Page<CategoryDto> findAllParentCategories(Pageable pageable);

  Map<String, List<PreferredCategoryDto>> findCategoriesByTitle(String title);

  ParentAndSubCategoriesDto findAll();

  Category findById(Long categoryId);

  Category add(String createCategoryRequest, MultipartFile file);

  Category update(Long categoryId, String modifyCategoryRequest, MultipartFile file);

  void delete(Long categoryId, Locale locale);
}
