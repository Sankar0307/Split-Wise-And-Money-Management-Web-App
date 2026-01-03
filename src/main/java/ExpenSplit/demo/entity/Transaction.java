package ExpenSplit.demo.entity;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions_table")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@IdClass(TransactionId.class)
public class Transaction {

    @Id
    @Column(name = "group_id")
    private String groupId;

    @Id
    @Column(name = "user1_index")
    private Integer user1Index; // owes

    @Id
    @Column(name = "user2_index")
    private Integer user2Index; // receives

    @Column(nullable = false)
    private BigDecimal balance;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "settlement_id")
    private SettlementSession settlementSession;

}
