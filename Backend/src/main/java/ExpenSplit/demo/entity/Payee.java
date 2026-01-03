package ExpenSplit.demo.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "payees_in_bills_table")
@IdClass(BillUserId.class)
@Getter @Setter
public class Payee {

    @Id
    @Column(name = "bill_id")
    private String billId;

    @Id
    @Column(name = "user_index")
    private Integer userIndex;

    @Column(nullable = false)
    private BigDecimal amount = BigDecimal.ZERO;
}
