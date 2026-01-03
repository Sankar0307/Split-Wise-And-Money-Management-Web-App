package ExpenSplit.demo.entity;


import lombok.*;

import java.io.Serializable;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode
public class BillUserId implements Serializable {

    private String billId;
    private Integer userIndex;
}
