package ExpenSplit.demo.entity;

import java.io.Serializable;
import java.util.Objects;

public class TransactionId implements Serializable {

    private String groupId;
    private Integer user1Index;
    private Integer user2Index;

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TransactionId)) return false;
        TransactionId that = (TransactionId) o;
        return Objects.equals(groupId, that.groupId)
                && Objects.equals(user1Index, that.user1Index)
                && Objects.equals(user2Index, that.user2Index);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupId, user1Index, user2Index);
    }
}
