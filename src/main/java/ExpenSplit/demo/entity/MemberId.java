package ExpenSplit.demo.entity;

import lombok.*;

import java.io.Serializable;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class MemberId implements Serializable {

    private Integer userIndex;
    private String groupId;
}
