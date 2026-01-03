package ExpenSplit.demo.repository;

import ExpenSplit.demo.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<Bill,String> {
    List<Bill> findByGroupId(String groupId);
    List<Bill> findByGroupIdOrderByCreatedAtDesc(String groupId);
}
