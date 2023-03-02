package app.openschool.course.module.api;

import app.openschool.course.Course;
import app.openschool.course.module.Module;
import app.openschool.user.User;
import app.openschool.user.api.UserGenerator;
import app.openschool.user.role.Role;

public class ModuleGenerator {
  public static Module generateModule() {
    Module module = new Module();
    Course course = new Course();
    course.setMentor(UserGenerator.generateMentor());
    module.setCourse(course);
    return module;
  }

  public static Module generateModuleWithAnotherUser() {
    Module module = ModuleGenerator.generateModule();
    module.getCourse().getMentor().setEmail("another@gmail");
    return module;
  }
}
