package app.openschool.course.api;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import app.openschool.category.Category;
import app.openschool.category.api.CategoryGenerator;
import app.openschool.course.Course;
import app.openschool.course.api.dto.CourseDto;
import app.openschool.course.api.mapper.CourseMapper;
import app.openschool.course.difficulty.Difficulty;
import app.openschool.course.keyword.Keyword;
import app.openschool.course.language.Language;
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
      difficulty.setTitle("Initial");
      Language language = new Language();
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
  public void toCourseDtoTest() {
    CourseDto actual = CourseMapper.toCourseDto(courseList.get(0));
    assertThat(actual).hasOnlyFields("id", "title", "rating", "difficulty", "keywords");
  }
}
