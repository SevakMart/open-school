package app.openschool.coursemanagement;

import app.openschool.coursemanagement.api.dto.CategoryDto;
import app.openschool.coursemanagement.api.mapper.CategoryMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
}
