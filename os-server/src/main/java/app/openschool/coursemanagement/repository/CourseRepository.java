package app.openschool.coursemanagement.repository;

import app.openschool.coursemanagement.entity.CategoryEntity;
import app.openschool.coursemanagement.entity.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<CourseEntity, Long> {

    @Query(value = "SELECT title, logo_path from category WHERE parent_category_id IS NULL", nativeQuery = true)
    List<CategoryEntity> findAllCategories();

}
