package ExpenSplit.demo.service;

import ExpenSplit.demo.DTOs.*;
import ExpenSplit.demo.entity.*;
import ExpenSplit.demo.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ExpenseService {

    private final BillRepository billRepository;
    private final PayeeRepository payeeRepository;
    private final DraweeRepository draweeRepository;
    private final MemberRepository memberRepository;
    private final GroupRepository groupRepository;
    private final PersonalExpenseSummaryRepository personalExpenseSummaryRepository;

    @Transactional
    public void addExpense(AddExpenseRequest request) throws BadRequestException {

        if (request.amount() == null || request.amount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("Bill amount must be greater than zero");
        }

        if (request.payees() == null || request.payees().isEmpty()) {
            throw new BadRequestException("At least one payee is required");
        }

        if (request.drawees() == null || request.drawees().isEmpty()) {
            throw new BadRequestException("At least one drawee is required");
        }

        Group group = groupRepository.findById(request.groupId())
                .orElseThrow(() -> new BadRequestException("Group not found"));

        memberRepository.findByGroupIdAndUserIndex(
                request.groupId(), request.createdBy()
        ).orElseThrow(() ->
                new BadRequestException("Bill creator not found in group")
        );

        BigDecimal payeeTotal = request.payees().stream()
                .map(PayeeRequest::amount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal draweeTotal = request.drawees().stream()
                .map(DraweeRequest::amount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (payeeTotal.compareTo(request.amount()) != 0) {
            throw new BadRequestException("Sum of payees must equal bill amount");
        }

        if (draweeTotal.compareTo(request.amount()) != 0) {
            throw new BadRequestException("Sum of drawees must equal bill amount");
        }

        Bill bill = new Bill();
        bill.setId(request.billId());
        bill.setName(request.name());
        bill.setAmount(request.amount());
        bill.setCategory(request.category());
        bill.setGroupId(request.groupId());
        bill.setCreatedBy(request.createdBy());
        bill.setCreatedAt(LocalDateTime.now());

        billRepository.save(bill);

        // Payees
        for (PayeeRequest p : request.payees()) {

            Member member = memberRepository
                    .findByGroupIdAndUserIndex(request.groupId(), p.userIndex())
                    .orElseThrow(() ->
                            new BadRequestException("Payee not found in group:" + p.userIndex())
                    );

            Payee payee = new Payee();
            payee.setBillId(request.billId());
            payee.setUserIndex(p.userIndex());
            payee.setAmount(p.amount());

            payeeRepository.save(payee);

            member.setTotalPaid(member.getTotalPaid().add(p.amount()));
            memberRepository.save(member);
        }

        // Drawees (IMPORTANT PART)
        for (DraweeRequest d : request.drawees()) {

            Member member = memberRepository
                    .findByGroupIdAndUserIndex(request.groupId(), d.userIndex())
                    .orElseThrow(() ->
                            new BadRequestException("Drawee not found in group: " + d.userIndex())
                    );

            Drawee drawee = new Drawee();
            drawee.setBillId(request.billId());
            drawee.setUserIndex(d.userIndex());
            drawee.setAmount(d.amount());

            draweeRepository.save(drawee);

            member.setTotalSpent(member.getTotalSpent().add(d.amount()));
            memberRepository.save(member);

            // ✅ NEW: update personal expense summary
            updatePersonalSummary(
                    member.getUserId().toString(),
                    request.groupId(),
                    request.category(),
                    d.amount()
            );
        }

        group.setTotalExpense(group.getTotalExpense().add(request.amount()));
        groupRepository.save(group);
    }

    // 🔽 NEW helper method (isolated & safe)
    private void updatePersonalSummary(
            String userId,
            String groupId,
            String category,
            BigDecimal amount
    ) {
        PersonalExpenseSummary summary =
                personalExpenseSummaryRepository
                        .findByUserIdAndGroupIdAndCategory(userId, groupId, category)
                        .orElseGet(() -> {
                            PersonalExpenseSummary ps = new PersonalExpenseSummary();
                            ps.setUserId(userId);
                            ps.setGroupId(groupId);
                            ps.setCategory(category);
                            ps.setTotalAmount(BigDecimal.ZERO);
                            return ps;
                        });

        summary.setTotalAmount(summary.getTotalAmount().add(amount));
        summary.setLastUpdated(LocalDateTime.now());

        personalExpenseSummaryRepository.save(summary);
    }

    // Existing methods (unchanged)

    public List<ExpenseSummaryResponse> getExpensesByGroup(String groupId) {
        return billRepository.findByGroupIdOrderByCreatedAtDesc(groupId)
                .stream()
                .map(b -> new ExpenseSummaryResponse(
                        b.getId(),
                        b.getName(),
                        b.getAmount(),
                        b.getCategory(),
                        b.getCreatedBy(),
                        b.getCreatedAt()
                ))
                .toList();
    }

    public ExpenseDetailResponse getExpense(String billId) {
        Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> new RuntimeException("Expense not found"));

        List<PayeeInfo> payees = payeeRepository.findByBillId(billId)
                .stream()
                .map(p -> new PayeeInfo(p.getUserIndex(), p.getAmount()))
                .toList();

        List<DraweeInfo> drawees = draweeRepository.findByBillId(billId)
                .stream()
                .map(d -> new DraweeInfo(d.getUserIndex(), d.getAmount()))
                .toList();

        return new ExpenseDetailResponse(
                bill.getId(),
                bill.getName(),
                bill.getAmount(),
                bill.getCategory(),
                bill.getCreatedBy(),
                bill.getCreatedAt(),
                payees,
                drawees
        );
    }
}
