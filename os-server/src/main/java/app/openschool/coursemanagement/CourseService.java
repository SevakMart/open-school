package app.openschool.coursemanagement;

import app.openschool.coursemanagement.api.dto.CategoryDto;
import app.openschool.coursemanagement.api.dto.CategoryDtoForRegistration;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CourseService {

  Page<CategoryDto> findAllCategories(Pageable pageable);

  Map<String, List<CategoryDtoForRegistration>> findCategoriesByTitle(String title);
}
