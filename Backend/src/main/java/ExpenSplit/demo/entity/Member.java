package ExpenSplit.demo.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "members_table")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@IdClass(MemberId.class)
public class Member {

    @Id
    @Column(name = "user_index")
    private Integer userIndex;

    @Id
    @Column(name = "group_id")
    private String groupId;

    @Column(name = "user_id", nullable = false ,columnDefinition = "CHAR(36)")
    @JdbcTypeCode(SqlTypes.CHAR)
    private UUID userId;

    @Column(name = "username_in_group")
    private String userNameInGroup;

    @Column(name = "is_admin")
    private Boolean isAdmin = false;

    private Integer status = 0;

    @Column(name = "total_spent", nullable = false)
    private BigDecimal totalSpent = BigDecimal.ZERO;

    @Column(name = "total_paid", nullable = false)
    private BigDecimal totalPaid = BigDecimal.ZERO;


}
