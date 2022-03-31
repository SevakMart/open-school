package app.openschool.coursemanagement;

import static org.mockito.Mockito.when;

import app.openschool.coursemanagement.api.CategoryGenerator;
import app.openschool.coursemanagement.entities.Category;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
public class CourseServiceImplTest {

  @Mock private CategoryRepository categoryRepository;

  private CourseService courseService;

  @BeforeEach
  void setUp() {
    courseService = new CourseServiceImpl(categoryRepository);
  }

  @Test
  void findAllCategories() {
    List<Category> categoryList = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      categoryList.add(CategoryGenerator.generateCategory());
    }
    Pageable pageable = PageRequest.of(0, 2);
    Page<Category> categoryPage = new PageImpl<>(categoryList, pageable, 5);
    when(categoryRepository.findAllCategories(pageable)).thenReturn(categoryPage);
    Assertions.assertEquals(3, courseService.findAllCategories(pageable).getTotalPages());
    Assertions.assertEquals(5, courseService.findAllCategories(pageable).getTotalElements());
    Mockito.verify(categoryRepository, Mockito.times(2)).findAllCategories(pageable);
  }
}
