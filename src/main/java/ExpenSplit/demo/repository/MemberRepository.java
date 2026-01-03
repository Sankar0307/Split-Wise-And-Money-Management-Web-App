package ExpenSplit.demo.repository;

import ExpenSplit.demo.entity.Member;
import ExpenSplit.demo.entity.MemberId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.plaf.metal.MetalMenuBarUI;
import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, MemberId> {

    List<Member> findByGroupId(String groupId);

    Optional <Member> findByGroupIdAndUserIndex(String groupId, Integer userIndex);

    void deleteByGroupId(String groupId);
}
