package ExpenSplit.demo.repository;

import ExpenSplit.demo.entity.PersonalExpenseSummary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PersonalExpenseSummaryRepository
        extends JpaRepository<PersonalExpenseSummary, Long> {

    Optional<PersonalExpenseSummary>
    findByUserIdAndGroupIdAndCategory(
            String userId,
            String groupId,
            String category
    );

    List<PersonalExpenseSummary> findByUserId(String userId);

    List<PersonalExpenseSummary> findByUserIdAndGroupId(
            String userId,
            String groupId
    );
}
