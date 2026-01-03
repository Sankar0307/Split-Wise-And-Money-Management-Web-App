package ExpenSplit.demo.service;

import ExpenSplit.demo.entity.Transaction;
import ExpenSplit.demo.exception.SettlementAlreadyExistsException;
import ExpenSplit.demo.helper.MemberBalance;
import ExpenSplit.demo.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import ExpenSplit.demo.DTOs.SettlementResult;
import ExpenSplit.demo.entity.Member;
import ExpenSplit.demo.repository.MemberRepository;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SettlementService {

    private final MemberRepository memberRepository;
    private final TransactionRepository transactionRepository;

    public List<SettlementResult> calculateSettlement(String groupId) {

        // 1️⃣ Fetch all members of the group
        List<Member> members = memberRepository.findByGroupId(groupId);

        // 2️⃣ Separate creditors and debtors
        List<MemberBalance> creditors = new ArrayList<>();
        List<MemberBalance> debtors = new ArrayList<>();

        for (Member m : members) {
            BigDecimal balance = m.getTotalPaid().subtract(m.getTotalSpent());

            if (balance.compareTo(BigDecimal.ZERO) > 0) {
                creditors.add(new MemberBalance(m.getUserIndex(), balance));
            } else if (balance.compareTo(BigDecimal.ZERO) < 0) {
                debtors.add(new MemberBalance(m.getUserIndex(), balance.abs()));
            }
        }

        // 3️⃣ Match debtors to creditors
        List<SettlementResult> results = new ArrayList<>();

        int i = 0, j = 0;

        while (i < debtors.size() && j < creditors.size()) {

            MemberBalance debtor = debtors.get(i);
            MemberBalance creditor = creditors.get(j);

            BigDecimal settleAmount =
                    debtor.amount().min(creditor.amount());

            results.add(new SettlementResult(
                    debtor.userIndex(),
                    creditor.userIndex(),
                    settleAmount
            ));

            debtor.reduce(settleAmount);
            creditor.reduce(settleAmount);

            if (debtor.amount().compareTo(BigDecimal.ZERO) == 0) i++;
            if (creditor.amount().compareTo(BigDecimal.ZERO) == 0) j++;
        }

        return results;
    }


    @Transactional
    public List<SettlementResult> settleAndSave(String groupId) {

        // 1️⃣ Prevent duplicate settlement
        if (transactionRepository.existsByGroupId(groupId)) {
            throw new SettlementAlreadyExistsException(
                    "Settlement already exists for this group. Add new expense to settle again."
            );
        }

        // 2️⃣ Calculate settlement
        List<SettlementResult> results = calculateSettlement(groupId);

        // 3️⃣ Save transactions
        for (SettlementResult r : results) {

            Transaction tx = new Transaction();
            tx.setGroupId(groupId);
            tx.setUser1Index(r.fromUser());
            tx.setUser2Index(r.toUser());
            tx.setBalance(r.amount());

            transactionRepository.save(tx);
        }

        // 4️⃣ RESET balances (VERY IMPORTANT)
        resetMemberBalances(groupId);

        return results;
    }


    private void resetMemberBalances(String groupId) {

        List<Member> members = memberRepository.findByGroupId(groupId);

        for (Member m : members) {
            m.setTotalPaid(BigDecimal.ZERO);
            m.setTotalSpent(BigDecimal.ZERO);
        }

        memberRepository.saveAll(members);
    }


    public List<Transaction> getSavedTransactions(String groupId) {
        return transactionRepository.findByGroupId(groupId);
    }


}
