package app.openschool.course.module.util;

import app.openschool.course.Course;
import app.openschool.course.module.Module;
import app.openschool.user.api.UserGenerator;

public class ModuleGenerator {

  public static Module generateModule() {
    Module module = Module.getInstance();
    Course course = Course.getInstance();
    course.setMentor(UserGenerator.generateMentor());
    module.setCourse(course);
    return module;
  }
}
