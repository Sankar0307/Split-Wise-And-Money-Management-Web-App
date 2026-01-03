package ExpenSplit.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "settlement_sessions")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class SettlementSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "group_id", nullable = false)
    private String groupId;

    @Column(name = "total_amount", nullable = false)
    private BigDecimal totalAmount;

    @Column(name = "settled_at", nullable = false)
    private LocalDateTime settledAt;
}

