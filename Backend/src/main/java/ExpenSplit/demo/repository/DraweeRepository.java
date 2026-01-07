package ExpenSplit.demo.repository;

import ExpenSplit.demo.entity.BillUserId;
import ExpenSplit.demo.entity.Drawee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface DraweeRepository extends JpaRepository<Drawee, BillUserId> {

    List<Drawee> findByBillId(String billId);

    void deleteByBillId(String id);

    @Query("""
        SELECT d
        FROM Drawee d
        JOIN Bill b ON d.billId = b.id
        WHERE b.groupId = :groupId
    """)
    List<Drawee> findByGroupId(@Param("groupId") String groupId);
}
