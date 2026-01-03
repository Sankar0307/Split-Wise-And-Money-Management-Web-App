package ExpenSplit.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "personal_expense_summary",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"user_id", "group_id", "category"}
        ))
@Getter @Setter
public class PersonalExpenseSummary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "group_id")
    private String groupId; // null = personal

    private String category;

    @Column(name = "total_amount", nullable = false)
    private BigDecimal totalAmount;

    @Column(name = "last_updated", nullable = false)
    private LocalDateTime lastUpdated;
}

