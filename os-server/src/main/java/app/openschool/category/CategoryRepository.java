package app.openschool.category;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

  Page<Category> findByParentCategoryIsNull(Pageable pageable);

  List<Category> findByParentCategoryIsNull();

  List<Category> findByParentCategoryIsNotNull();

  List<Category> findByTitleContainingIgnoreCaseAndParentCategoryIsNull(String title);

  List<Category> findByTitleContainingIgnoreCaseAndParentCategoryIsNotNull(String title);
}
