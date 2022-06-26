package app.openschool.course.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import app.openschool.category.Category;
import app.openschool.category.api.CategoryGenerator;
import app.openschool.course.Course;
import app.openschool.course.EnrolledCourse;
import app.openschool.course.api.dto.UserCourseDto;
import app.openschool.course.api.mapper.UserCourseMapper;
import app.openschool.course.difficulty.Difficulty;
import app.openschool.course.language.Language;
import app.openschool.course.module.EnrolledModule;
import app.openschool.course.module.Module;
import app.openschool.course.module.item.EnrolledModuleItem;
import app.openschool.course.module.item.ModuleItem;
import app.openschool.course.module.item.status.ModuleItemStatus;
import app.openschool.course.module.item.type.ModuleItemType;
import app.openschool.course.module.status.ModuleStatus;
import app.openschool.course.status.CourseStatus;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;

public class EnrolledUserCourseMapperTest {

  @Test
  public void toEnrolledUserCourseDto() {

    Difficulty difficulty = new Difficulty();
    difficulty.setTitle("Medium");
    Language language = new Language();
    language.setTitle("English");
    Category category = CategoryGenerator.generateCategory();

    Course course = new Course();
    course.setId(1L);
    course.setTitle("Stream");
    course.setDescription("AAA");
    course.setRating(5.5);
    course.setCategory(category);
    course.setDifficulty(difficulty);
    course.setLanguage(language);

    CourseStatus courseStatusInProgress = new CourseStatus();
    courseStatusInProgress.setId(1L);
    courseStatusInProgress.setType("IN_PROGRESS");

    EnrolledCourse enrolledCourse = new EnrolledCourse();
    enrolledCourse.setId(1L);
    enrolledCourse.setCourse(course);
    enrolledCourse.setCourseStatus(courseStatusInProgress);
    enrolledCourse.setDueDate(LocalDate.of(2022, 10, 7));

    Set<Module> moduleSet = new HashSet<>();
    for (long i = 1L; i < 4L; i++) {
      Module module = new Module();
      module.setId(i);
      module.setCourse(course);
      moduleSet.add(module);
    }
    course.setModules(moduleSet);

    ModuleStatus moduleStatusInProgress = new ModuleStatus();
    moduleStatusInProgress.setId(1L);
    moduleStatusInProgress.setType("IN_PROGRESS");

    ModuleStatus moduleStatusCompleted = new ModuleStatus();
    moduleStatusCompleted.setId(1L);
    moduleStatusCompleted.setType("COMPLETED");

    Set<EnrolledModule> enrolledModuleSet = new HashSet<>();
    for (long i = 1L; i < 4L; i++) {
      EnrolledModule enrolledModule = new EnrolledModule();
      enrolledModule.setId(i);
      List<Module> modules = new ArrayList<>(moduleSet);
      enrolledModule.setModule(modules.get((int) i - 1));
      enrolledModule.setModuleStatus(i < 3 ? moduleStatusInProgress : moduleStatusCompleted);
      enrolledModule.setEnrolledCourse(enrolledCourse);
      enrolledModuleSet.add(enrolledModule);
    }
    enrolledCourse.setEnrolledModules(enrolledModuleSet);

    Module module1 =
        moduleSet.stream().filter(module -> module.getId().equals(1L)).findFirst().get();
    Set<ModuleItem> moduleItemsModule1 = new HashSet<>();
    for (long i = 1L; i < 3L; i++) {
      ModuleItem moduleItem = new ModuleItem();
      moduleItem.setId(i);
      ModuleItemType moduleItemType = new ModuleItemType();
      moduleItemType.setType("video");
      moduleItem.setModuleItemType(moduleItemType);
      moduleItem.setEstimatedTime(35L);
      moduleItem.setModule(module1);
      moduleItemsModule1.add(moduleItem);
    }
    module1.setModuleItems(moduleItemsModule1);

    Module module2 =
        moduleSet.stream().filter(module -> module.getId().equals(2L)).findFirst().get();
    Set<ModuleItem> moduleItemsModule2 = new HashSet<>();
    for (long i = 1L; i < 3L; i++) {
      ModuleItem moduleItem = new ModuleItem();
      moduleItem.setId(i + 2);
      ModuleItemType moduleItemType = new ModuleItemType();
      moduleItemType.setType("reading");
      moduleItem.setModuleItemType(moduleItemType);
      moduleItem.setEstimatedTime(25L);
      moduleItem.setModule(module2);
      moduleItemsModule2.add(moduleItem);
    }
    module2.setModuleItems(moduleItemsModule2);

    Module module3 =
        moduleSet.stream().filter(module -> module.getId().equals(3L)).findFirst().get();
    ModuleItem moduleItem = new ModuleItem();
    moduleItem.setModule(module3);
    moduleItem.setId(5L);
    ModuleItemType moduleItemType = new ModuleItemType();
    moduleItemType.setType("reading");
    moduleItem.setModuleItemType(moduleItemType);
    moduleItem.setEstimatedTime(30L);
    Set<ModuleItem> moduleItemsModule3 = new HashSet<>();
    moduleItemsModule3.add(moduleItem);
    module3.setModuleItems(moduleItemsModule3);

    ModuleItemStatus moduleItemStatusInProgress = new ModuleItemStatus();
    moduleItemStatusInProgress.setId(1L);
    moduleItemStatusInProgress.setType("IN_PROGRESS");

    ModuleItemStatus moduleItemStatusCompleted = new ModuleItemStatus();
    moduleItemStatusCompleted.setId(1L);
    moduleItemStatusCompleted.setType("COMPLETED");

    EnrolledModule enrolledModule1 =
        enrolledModuleSet.stream().filter(module -> module.getId().equals(1L)).findFirst().get();
    Set<EnrolledModuleItem> enrolledModuleItemsEnrolledModule1 = new HashSet<>();
    for (long i = 1L; i < 3L; i++) {
      EnrolledModuleItem enrolledModuleItem = new EnrolledModuleItem();
      enrolledModuleItem.setId(i);
      enrolledModuleItem.setGrade(100);
      List<ModuleItem> moduleItems = new ArrayList<>(moduleItemsModule1);
      enrolledModuleItem.setModuleItem(moduleItems.get((int) i - 1));
      enrolledModuleItem.setModuleItemStatus(moduleItemStatusInProgress);
      enrolledModuleItem.setEnrolledModule(enrolledModule1);
      enrolledModuleItemsEnrolledModule1.add(enrolledModuleItem);
    }
    enrolledModule1.setEnrolledModuleItems(enrolledModuleItemsEnrolledModule1);

    EnrolledModule enrolledModule2 =
        enrolledModuleSet.stream().filter(module -> module.getId().equals(2L)).findFirst().get();
    Set<EnrolledModuleItem> enrolledModuleItemsEnrolledModule2 = new HashSet<>();
    for (long i = 1L; i < 3L; i++) {
      EnrolledModuleItem enrolledModuleItem = new EnrolledModuleItem();
      enrolledModuleItem.setId(i + 2);
      enrolledModuleItem.setGrade(100);
      List<ModuleItem> moduleItems = new ArrayList<>(moduleItemsModule2);
      enrolledModuleItem.setModuleItem(moduleItems.get((int) i - 1));
      enrolledModuleItem.setModuleItemStatus(
          i < 2 ? moduleItemStatusInProgress : moduleItemStatusCompleted);
      enrolledModuleItem.setEnrolledModule(enrolledModule2);
      enrolledModuleItemsEnrolledModule2.add(enrolledModuleItem);
    }
    enrolledModule2.setEnrolledModuleItems(enrolledModuleItemsEnrolledModule2);

    EnrolledModule enrolledModule3 =
        enrolledModuleSet.stream().filter(module -> module.getId().equals(3L)).findFirst().get();
    EnrolledModuleItem enrolledModuleItem = new EnrolledModuleItem();
    enrolledModuleItem.setId(5L);
    enrolledModuleItem.setGrade(100);
    enrolledModuleItem.setModuleItemStatus(moduleItemStatusCompleted);
    enrolledModuleItem.setModuleItem(moduleItem);
    enrolledModuleItem.setEnrolledModule(enrolledModule3);
    Set<EnrolledModuleItem> enrolledModuleItemsModule3 = new HashSet<>();
    enrolledModuleItemsModule3.add(enrolledModuleItem);
    enrolledModule3.setEnrolledModuleItems(enrolledModuleItemsModule3);

    UserCourseDto actual = UserCourseMapper.toUserCourseDto(enrolledCourse);
    assertEquals(95, actual.getRemainingTime());
    assertEquals(37, actual.getPercentage());
    assertEquals(LocalDate.of(2022, 10, 7), actual.getDueDate());
  }
}
