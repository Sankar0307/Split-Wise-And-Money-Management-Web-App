package ExpenSplit.demo.helper;


import java.math.BigDecimal;

public class MemberBalance {

    private final Integer userIndex;
    private BigDecimal amount;

    public MemberBalance(Integer userIndex, BigDecimal amount) {
        this.userIndex = userIndex;
        this.amount = amount;
    }

    public Integer userIndex() {
        return userIndex;
    }

    public BigDecimal amount() {
        return amount;
    }

    public void reduce(BigDecimal value) {
        this.amount = this.amount.subtract(value);
    }


}
