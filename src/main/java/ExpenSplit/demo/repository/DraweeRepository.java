package ExpenSplit.demo.repository;

import ExpenSplit.demo.entity.BillUserId;
import ExpenSplit.demo.entity.Drawee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DraweeRepository extends JpaRepository<Drawee, BillUserId> {

    List<Drawee> findByBillId(String billId);

    void deleteByBillId(String id);
}
