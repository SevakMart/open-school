package app.openschool.user;

import app.openschool.category.api.dto.SavePreferredCategoriesRequestDto;
import app.openschool.category.api.dto.SavePreferredCategoriesResponseDto;
import app.openschool.course.api.dto.CourseDto;
import app.openschool.course.api.dto.UserCourseDto;
import app.openschool.user.api.dto.MentorDto;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

  Page<MentorDto> findAllMentors(Pageable pageable);

  List<CourseDto> getSuggestedCourses(Long userId);

  SavePreferredCategoriesResponseDto savePreferredCategories(
      SavePreferredCategoriesRequestDto savePreferredCategoriesDto);

  List<UserCourseDto> findUserCourses(Long userId, Long courseStatusId);
}
