package app.openschool.course.module.api;

import app.openschool.course.EnrolledCourse;
import app.openschool.course.api.dto.EnrolledCourseOverviewDto;
import app.openschool.course.module.EnrolledModule;
import java.util.List;

public class EnrolledModuleMapper {
    public static EnrolledModuleOverviewDto toEnrolledModuleOverviewDto(
            EnrolledModule enrolledModule) {

        return new EnrolledModuleOverviewDto(
                enrolledModule.getModule().getTitle;
                enrolledModule.
                enrolledCourse.getCourseStatus().getType(),
                getCourseGrade(enrolledCourse),
                getCourseEstimatedTime(enrolledCourse.getCourse()),
                List.of());
    }
}
