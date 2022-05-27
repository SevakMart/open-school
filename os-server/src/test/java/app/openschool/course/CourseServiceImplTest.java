package app.openschool.course;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CourseServiceImplTest {

  @Mock private CourseRepository courseRepository;

  private CourseService courseService;

  @BeforeEach
  void setUp() {
    courseService = new CourseServiceImpl(courseRepository);
  }

  @Test
  public void findCourseByNonexistentId() {
    long wrongId = 999L;
    when(courseRepository.findById(wrongId)).thenReturn(Optional.empty());
    assertEquals(Optional.empty(), courseService.findCourseById(wrongId));
  }
}
