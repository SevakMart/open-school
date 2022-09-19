package app.openschool.course.module.api.mapper;

import app.openschool.course.Course;
import app.openschool.course.EnrolledCourse;
import app.openschool.course.module.EnrolledModule;
import app.openschool.course.module.Module;
import app.openschool.course.module.api.dto.EnrolledModuleOverviewDto;
import app.openschool.course.module.item.EnrolledModuleItem;
import app.openschool.course.module.item.ModuleItem;
import app.openschool.course.module.item.api.EnrolledModuleItemMapper;
import app.openschool.course.module.item.status.ModuleItemStatus;
import app.openschool.course.module.quiz.EnrolledQuiz;
import app.openschool.course.module.quiz.api.mapper.QuizMapper;
import app.openschool.course.module.quiz.status.QuizStatus;
import app.openschool.course.module.status.ModuleStatus;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class EnrolledModuleMapper {

  public static Set<EnrolledModuleOverviewDto> toEnrolledModuleOverviewDtoList(
      Set<EnrolledModule> enrolledModuleSet) {
    Set<EnrolledModuleOverviewDto> enrolledModuleOverviewDtoSet = new HashSet<>();
    enrolledModuleSet.forEach(
        enrolledModule ->
            enrolledModuleOverviewDtoSet.add(toEnrolledModuleOverviewDto(enrolledModule)));
    return enrolledModuleOverviewDtoSet;
  }

  public static EnrolledModuleOverviewDto toEnrolledModuleOverviewDto(
      EnrolledModule enrolledModule) {
    return new EnrolledModuleOverviewDto(
        enrolledModule.getModule().getTitle(),
        enrolledModule.getModuleStatus().getType(),
        getModuleEstimatedTime(enrolledModule.getModule()),
        EnrolledModuleItemMapper.toEnrolledModuleItemOverviewDtoList(
            enrolledModule.getEnrolledModuleItems()),
        QuizMapper.enrolledQuizzesToEnrolledQuizDtoSet(enrolledModule.getEnrolledQuizzes()));
  }

  public static Set<EnrolledModule> toEnrolledModules(
      Course course, EnrolledCourse enrolledCourse) {
    Set<EnrolledModule> enrolledModules =
        course.getModules().stream()
            .map(module -> new EnrolledModule(module, ModuleStatus.inProgress(), enrolledCourse))
            .collect(Collectors.toSet());

    return enrolledModules.stream()
        .map(
            enrolledModule -> {
              enrolledModule.setEnrolledModuleItems(getEnrolledModuleItems(enrolledModule));
              enrolledModule.setEnrolledQuizzes(getEnrolledQuizzes(enrolledModule));
              return enrolledModule;
            })
        .collect(Collectors.toSet());
  }

  private static Set<EnrolledModuleItem> getEnrolledModuleItems(EnrolledModule enrolledModule) {
    return enrolledModule.getModule().getModuleItems().stream()
        .map(
            moduleItem ->
                new EnrolledModuleItem(moduleItem, enrolledModule, ModuleItemStatus.inProgress()))
        .collect(Collectors.toSet());
  }

  private static Set<EnrolledQuiz> getEnrolledQuizzes(EnrolledModule enrolledModule) {
    return enrolledModule.getModule().getModuleQuizzes().stream()
        .map(moduleQuiz -> new EnrolledQuiz(QuizStatus.isInProgress(), moduleQuiz, enrolledModule))
        .collect(Collectors.toSet());
  }

  private static long getModuleEstimatedTime(Module module) {
    return module.getModuleItems().stream().mapToLong(ModuleItem::getEstimatedTime).sum();
  }
}
