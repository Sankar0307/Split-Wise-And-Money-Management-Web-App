package ExpenSplit.demo.repository;

import ExpenSplit.demo.entity.Transaction;
import ExpenSplit.demo.entity.TransactionId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, TransactionId> {

    List<Transaction> findByGroupId(String groupId);


    void deleteByGroupId(String groupId);

    boolean existsByGroupId(String groupId);
}
