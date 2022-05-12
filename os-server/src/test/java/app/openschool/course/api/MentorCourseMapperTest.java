package app.openschool.course.api;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import app.openschool.category.Category;
import app.openschool.category.api.CategoryGenerator;
import app.openschool.course.Course;
import app.openschool.course.api.dto.MentorCourseDto;
import app.openschool.course.api.mapper.MentorCourseMapper;
import app.openschool.course.difficulty.Difficulty;
import app.openschool.course.keyword.Keyword;
import app.openschool.course.language.Language;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MentorCourseMapperTest {

  List<Course> courseList = new ArrayList<>();

  @BeforeEach
  public void setUp() {
    for (long i = 1L; i < 7L; i++) {
      Course course = new Course();
      course.setId(i);
      course.setTitle("Medium");
      course.setDescription("description");
      course.setRating(5.5);
      Difficulty difficulty = new Difficulty();
      difficulty.setTitle("difficulty");
      Language language = new Language();
      language.setTitle("Java");
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
  public void toMentorCourseDto() {
    MentorCourseDto expected = MentorCourseMapper.toMentorCourseDto(courseList.get(0));
    assertThat(expected)
        .hasOnlyFields(
            "title",
            "description",
            "rating",
            "categoryTitle",
            "difficultyTitle",
            "languageTitle",
            "keywords");
  }
}
