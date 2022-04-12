package app.openschool.coursemanagement.api.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import app.openschool.coursemanagement.api.CategoryGenerator;
import app.openschool.coursemanagement.api.dto.UserCourseDto;
import app.openschool.coursemanagement.entity.Category;
import app.openschool.coursemanagement.entity.Course;
import app.openschool.coursemanagement.entity.CourseStatus;
import app.openschool.coursemanagement.entity.Difficulty;
import app.openschool.coursemanagement.entity.Language;
import app.openschool.coursemanagement.entity.Module;
import app.openschool.coursemanagement.entity.ModuleItem;
import app.openschool.coursemanagement.entity.ModuleItemStatus;
import app.openschool.coursemanagement.entity.ModuleStatus;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

public class UserCourseMapperTest {

  @Test
  public void toUserCourseDto() {

    Difficulty difficulty = new Difficulty();
    difficulty.setId(1L);
    difficulty.setTitle("Medium");
    Language language = new Language();
    language.setId(1L);
    language.setTitle("English");
    Category category = CategoryGenerator.generateCategory();

    Course course = new Course();
    course.setId(1L);
    course.setTitle("Stream");
    course.setDescription("AAA");
    course.setRating(5.5);
    course.setDueDate(LocalDate.of(2022, 10, 7));
    course.setCategory(category);
    course.setDifficulty(difficulty);
    course.setLanguage(language);

    CourseStatus courseStatusInProgress = new CourseStatus();
    courseStatusInProgress.setId(1L);
    courseStatusInProgress.setType("IN_PROGRESS");
    course.setCourseStatus(courseStatusInProgress);

    ModuleStatus moduleStatusInProgress = new ModuleStatus();
    moduleStatusInProgress.setId(1L);
    moduleStatusInProgress.setType("IN_PROGRESS");

    ModuleStatus moduleStatusCompleted = new ModuleStatus();
    moduleStatusCompleted.setId(1L);
    moduleStatusCompleted.setType("COMPLETED");

    Set<Module> moduleSet = new HashSet<>();
    for (long i = 1L; i < 4L; i++) {
      Module module = new Module();
      module.setId(i);
      module.setModuleStatus(i < 3 ? moduleStatusInProgress : moduleStatusCompleted);
      module.setCourse(course);
      moduleSet.add(module);
    }
    course.setModules(moduleSet);

    ModuleItemStatus moduleItemStatusInProgress = new ModuleItemStatus();
    moduleItemStatusInProgress.setId(1L);
    moduleItemStatusInProgress.setType("IN_PROGRESS");

    ModuleItemStatus moduleItemStatusCompleted = new ModuleItemStatus();
    moduleItemStatusCompleted.setId(1L);
    moduleItemStatusCompleted.setType("COMPLETED");

    Module module1 =
        moduleSet.stream().filter(module -> module.getId().equals(1L)).findFirst().get();
    Set<ModuleItem> moduleItemsModule1 = new HashSet<>();
    for (long i = 1L; i < 3L; i++) {
      ModuleItem moduleItem = new ModuleItem();
      moduleItem.setId(i);
      moduleItem.setModuleItemType("video");
      moduleItem.setEstimatedTime(35L);
      moduleItem.setGrade(100);
      moduleItem.setModuleItemStatus(moduleItemStatusInProgress);
      moduleItem.setModule(module1);
      moduleItemsModule1.add(moduleItem);
    }
    module1.setModuleItems(moduleItemsModule1);

    Module module2 =
        moduleSet.stream().filter(module -> module.getId().equals(2L)).findFirst().get();
    Set<ModuleItem> moduleItemSetModule2 = new HashSet<>();
    for (long i = 1L; i < 3L; i++) {
      ModuleItem moduleItem = new ModuleItem();
      moduleItem.setId(i + 2);
      moduleItem.setModuleItemType("reading");
      moduleItem.setEstimatedTime(25L);
      moduleItem.setModuleItemStatus(
          i < 2 ? moduleItemStatusInProgress : moduleItemStatusCompleted);
      moduleItem.setModule(module2);
      moduleItemSetModule2.add(moduleItem);
    }
    module2.setModuleItems(moduleItemSetModule2);

    Module module3 =
        moduleSet.stream().filter(module -> module.getId().equals(3L)).findFirst().get();
    ModuleItem moduleItem = new ModuleItem();
    moduleItem.setId(5L);
    moduleItem.setModuleItemType("reading");
    moduleItem.setEstimatedTime(30L);
    moduleItem.setModuleItemStatus(moduleItemStatusCompleted);
    moduleItem.setModule(module3);
    Set<ModuleItem> moduleItemSetModule3 = new HashSet<>();
    moduleItemSetModule3.add(moduleItem);
    module3.setModuleItems(moduleItemSetModule3);

    UserCourseDto actual = UserCourseMapper.toUserCourseDto(course);
    assertEquals(95, actual.getRemainingTime());
    assertEquals(37, actual.getPercentage());
    assertEquals(LocalDate.of(2022, 10, 7), actual.getDueDate());
  }
}
