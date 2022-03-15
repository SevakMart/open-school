package app.openschool.coursemanagement.sevice;


import app.openschool.coursemanagement.api.dto.CategoryDto;

import java.util.List;

public interface CourseService {

    List<CategoryDto> findAllCategories();

}
