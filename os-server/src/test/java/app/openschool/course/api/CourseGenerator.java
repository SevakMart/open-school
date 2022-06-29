package app.openschool.course.api;

import app.openschool.course.Course;
import app.openschool.course.EnrolledCourse;
import app.openschool.course.difficulty.Difficulty;
import app.openschool.course.language.Language;
import app.openschool.course.module.Module;
import app.openschool.course.module.item.ModuleItem;
import app.openschool.course.module.item.type.ModuleItemType;
import app.openschool.user.User;
import java.util.HashSet;
import java.util.Set;

public class CourseGenerator {

  public static Course generateCourseWithEnrolledCourses() {
    Course course = new Course();
    Set<Module> modules = new HashSet<>();
    Set<ModuleItem> moduleItems = new HashSet<>();
    Module module = new Module(1L, course, moduleItems);
    ModuleItemType moduleItemType = new ModuleItemType();
    moduleItemType.setType("TheType");
    ModuleItem moduleItem = new ModuleItem(1L, "String", moduleItemType, "kkk", 100L, module);
    moduleItems.add(moduleItem);
    modules.add(module);
    Set<EnrolledCourse> enrolledCourses = new HashSet<>();
    EnrolledCourse enrolledCourse = new EnrolledCourse(1L);
    enrolledCourses.add(enrolledCourse);

    course.setTitle("TheCourse");
    course.setGoal("TheGoal");
    course.setDescription("TheDescription");
    course.setModules(modules);
    course.setMentor(new User(1L));
    course.setRating(5.0);
    course.setEnrolledCourses(enrolledCourses);
    course.setDifficulty(new Difficulty("Basic"));
    course.setLanguage(new Language("English"));

    return course;
  }

  public static Course generateCourse() {
    Course course = new Course();
    Set<Module> modules = new HashSet<>();
    Set<ModuleItem> moduleItems = new HashSet<>();
    Module module = new Module(1L, course, moduleItems);
    ModuleItemType moduleItemType = new ModuleItemType();
    moduleItemType.setType("TheType");
    ModuleItem moduleItem = new ModuleItem(1L, "String", moduleItemType, "kkk", 100L, module);
    moduleItems.add(moduleItem);
    modules.add(module);

    course.setTitle("TheCourse");
    course.setGoal("TheGoal");
    course.setDescription("TheDescription");
    course.setModules(modules);
    course.setMentor(new User(1L));
    course.setRating(5.0);
    course.setDifficulty(new Difficulty("Basic"));
    course.setLanguage(new Language("English"));

    return course;
  }
}
