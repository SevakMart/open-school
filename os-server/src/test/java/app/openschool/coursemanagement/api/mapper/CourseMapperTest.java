package app.openschool.coursemanagement.api.mapper;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import app.openschool.coursemanagement.api.CategoryGenerator;
import app.openschool.coursemanagement.api.dto.CourseDto;
import app.openschool.coursemanagement.entity.Category;
import app.openschool.coursemanagement.entity.Course;
import app.openschool.coursemanagement.entity.Difficulty;
import app.openschool.coursemanagement.entity.Keyword;
import app.openschool.coursemanagement.entity.Language;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CourseMapperTest {
  List<Course> courseList = new ArrayList<>();

  @BeforeEach
  public void setUp() {
    for (long i = 1L; i < 7L; i++) {
      Course course = new Course();
      course.setId(i);
      course.setTitle("Medium");
      course.setDescription("AAA");
      course.setRating(5.5);
      Difficulty difficulty = new Difficulty();
      difficulty.setId(1L);
      difficulty.setTitle("Initial");
      Language language = new Language();
      language.setId(1L);
      language.setTitle("English");
      Category category = CategoryGenerator.generateCategory();
      course.setDifficulty(difficulty);
      course.setLanguage(language);
      course.setCategory(category);
      Keyword keyword = new Keyword();
      keyword.setId(1L);
      keyword.setTitle("Programming");
      Set<Keyword> keywordSet = new HashSet<>();
      keywordSet.add(keyword);
      course.setKeywords(keywordSet);
      courseList.add(course);
    }
  }

  @Test
  public void toCategoryDtoTest() {
    CourseDto actual = CourseMapper.toCourseDto(courseList.get(0));
    assertThat(actual).hasOnlyFields("title", "rating", "difficulty", "keywords");
  }
}
