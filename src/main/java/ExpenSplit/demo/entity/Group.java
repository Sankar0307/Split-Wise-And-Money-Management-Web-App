package ExpenSplit.demo.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name="groups_table")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor

public class Group {

    @Id
    @Column(length = 50)
    private String id; // nanoid in frontend

    @Column(nullable = false)
    private String name;

    @Column(name = "total_expense", nullable = false)
    private BigDecimal totalExpense =BigDecimal.ZERO;

    @Column(name = "currency_code")
    private String currencyCode;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
