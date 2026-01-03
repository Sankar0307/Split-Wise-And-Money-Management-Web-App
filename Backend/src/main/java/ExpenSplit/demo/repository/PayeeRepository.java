package ExpenSplit.demo.repository;

import ExpenSplit.demo.entity.BillUserId;
import ExpenSplit.demo.entity.Payee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PayeeRepository extends JpaRepository<Payee, BillUserId> {

    List<Payee> findByBillId(String billId);

    void deleteByBillId(String id);
}
