package app.openschool.discussion.api;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscussionQuestionRepository extends JpaRepository<DiscussionQuestion, Long> {

  //    Page<DiscussionQuestion> findAllByCourseId(Long id, Pageable pageable);

}
