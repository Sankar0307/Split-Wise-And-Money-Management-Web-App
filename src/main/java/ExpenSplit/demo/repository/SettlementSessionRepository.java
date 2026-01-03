package ExpenSplit.demo.repository;


import ExpenSplit.demo.entity.SettlementSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SettlementSessionRepository
        extends JpaRepository<SettlementSession, Long> {

    Optional<SettlementSession> findTopByGroupIdOrderBySettledAtDesc(String groupId);
}

