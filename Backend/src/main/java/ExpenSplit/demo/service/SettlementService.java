package ExpenSplit.demo.service;

import ExpenSplit.demo.DTOs.SettlementResult;
import ExpenSplit.demo.entity.Transaction;
import ExpenSplit.demo.helper.MemberBalance;
import ExpenSplit.demo.repository.DraweeRepository;
import ExpenSplit.demo.repository.MemberRepository;
import ExpenSplit.demo.repository.PayeeRepository;
import ExpenSplit.demo.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
@RequiredArgsConstructor
public class SettlementService {

    private final MemberRepository memberRepository;
    private final PayeeRepository payeeRepository;
    private final DraweeRepository draweeRepository;
    private final TransactionRepository transactionRepository;

    /**
     * Calculates settlement from scratch and saves transactions.
     * This method is SAFE to call multiple times.
     */
    @Transactional
    public List<SettlementResult> settleAndSave(String groupId) {

        // 1️⃣ Clear old settlements (important for recalculation)
        transactionRepository.deleteByGroupId(groupId);

        // 2️⃣ Build balance map for all members
        Map<Integer, BigDecimal> balanceMap = new HashMap<>();

        memberRepository.findByGroupId(groupId)
                .forEach(m -> balanceMap.put(m.getUserIndex(), BigDecimal.ZERO));

        // 3️⃣ Add payees (money paid)
        payeeRepository.findByGroupId(groupId).forEach(p -> {
            balanceMap.put(
                    p.getUserIndex(),
                    balanceMap.get(p.getUserIndex()).add(p.getAmount())
            );
        });

        // 4️⃣ Subtract drawees (money owed)
        draweeRepository.findByGroupId(groupId).forEach(d -> {
            balanceMap.put(
                    d.getUserIndex(),
                    balanceMap.get(d.getUserIndex()).subtract(d.getAmount())
            );
        });

        // 5️⃣ Separate creditors and debtors
        List<MemberBalance> creditors = new ArrayList<>();
        List<MemberBalance> debtors = new ArrayList<>();

        for (var entry : balanceMap.entrySet()) {
            BigDecimal amount = entry.getValue();

            if (amount.compareTo(BigDecimal.ZERO) > 0) {
                creditors.add(new MemberBalance(entry.getKey(), amount));
            } else if (amount.compareTo(BigDecimal.ZERO) < 0) {
                debtors.add(new MemberBalance(entry.getKey(), amount.abs()));
            }
        }

        // 6️⃣ Sort for deterministic output (VERY IMPORTANT)
        creditors.sort((a, b) -> b.amount().compareTo(a.amount()));
        debtors.sort((a, b) -> b.amount().compareTo(a.amount()));

        // 7️⃣ Match debtors to creditors
        List<SettlementResult> results = new ArrayList<>();

        int i = 0, j = 0;
        while (i < debtors.size() && j < creditors.size()) {

            MemberBalance debtor = debtors.get(i);
            MemberBalance creditor = creditors.get(j);

            BigDecimal settleAmount = debtor.amount().min(creditor.amount());

            if (settleAmount.compareTo(BigDecimal.ZERO) > 0) {
                results.add(new SettlementResult(
                        debtor.userIndex(),
                        creditor.userIndex(),
                        settleAmount
                ));
            }

            debtor.reduce(settleAmount);
            creditor.reduce(settleAmount);

            if (debtor.amount().compareTo(BigDecimal.ZERO) == 0) i++;
            if (creditor.amount().compareTo(BigDecimal.ZERO) == 0) j++;
        }

        // 8️⃣ Save transactions
        for (SettlementResult r : results) {
            Transaction tx = new Transaction();
            tx.setGroupId(groupId);
            tx.setUser1Index(r.fromUser()); // debtor
            tx.setUser2Index(r.toUser());   // creditor
            tx.setBalance(r.amount());

            transactionRepository.save(tx);
        }

        return results;
    }

    /**
     * Fetch saved settlement transactions
     */
    public List<Transaction> getSavedTransactions(String groupId) {
        return transactionRepository.findByGroupId(groupId);
    }
}
