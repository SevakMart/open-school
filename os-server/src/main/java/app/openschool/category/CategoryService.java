package app.openschool.category;

import app.openschool.category.api.dto.CategoryDto;
import app.openschool.category.api.dto.PreferredCategoryDto;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {

  Page<CategoryDto> findAllCategories(Pageable pageable);

  Map<String, List<PreferredCategoryDto>> findCategoriesByTitle(String title);
}
