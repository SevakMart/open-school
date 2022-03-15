package app.openschool.coursemanagement.controller;

import app.openschool.coursemanagement.api.dto.CategoryDto;
import app.openschool.coursemanagement.sevice.CourseServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CourseController {

    private final CourseServiceImpl courseService;

    public CourseController(CourseServiceImpl courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDto>> allCategories() {
        List<CategoryDto> allCategories = this.courseService.findAllCategories();
        if (allCategories == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(allCategories);
    }
}
