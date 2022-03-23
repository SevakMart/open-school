package app.openschool.coursemanagement;

import app.openschool.coursemanagement.api.dto.CategoryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CourseService {

  Page<CategoryDto> findAllCategories(Pageable pageable);
}
