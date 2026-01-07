package ExpenSplit.demo.repository;

import ExpenSplit.demo.entity.BillUserId;
import ExpenSplit.demo.entity.Payee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface PayeeRepository extends JpaRepository<Payee, BillUserId> {

    List<Payee> findByBillId(String billId);

    void deleteByBillId(String id);

    @Query("""
        SELECT p
        FROM Payee p
        JOIN Bill b ON p.billId = b.id
        WHERE b.groupId = :groupId
    """)
    List<Payee> findByGroupId(@Param("groupId") String groupId);
}
