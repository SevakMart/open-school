package app.openschool.category;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

  Page<Category> findByParentCategoryIsNull(Pageable pageable);

  List<Category> findByParentCategoryIsNull();

  List<Category> findByParentCategoryIsNotNull();

  Optional<Category> findByTitle(String title);

  List<Category> findByTitleContainingIgnoreCaseAndParentCategoryIsNull(String title);

  List<Category> findByTitleContainingIgnoreCaseAndParentCategoryIsNotNull(String title);
}
