package app.openschool.course;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CourseServiceImplTest {
  @Mock private CourseRepository courseRepository;

  private CourseServiceImpl courseService;

  @BeforeEach
  void setUp() {
    courseService = new CourseServiceImpl(courseRepository);
  }
}
