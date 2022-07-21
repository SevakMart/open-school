package app.openschool.category;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

  @Query(value = "SELECT * FROM category WHERE parent_category_id IS NULL", nativeQuery = true)
  Page<Category> findAllCategories(Pageable pageable);

  List<Category> findByParentCategoryIsNotNull();

  List<Category> findCategoriesByParentCategoryId(Long id);

  List<Category> findByTitleIgnoreCaseStartingWith(String prefix);
}
