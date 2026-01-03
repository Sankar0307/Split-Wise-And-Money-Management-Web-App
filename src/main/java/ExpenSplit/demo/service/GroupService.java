package ExpenSplit.demo.service;

import ExpenSplit.demo.DTOs.CreateGroupRequest;
import ExpenSplit.demo.DTOs.GroupResponse;
import ExpenSplit.demo.entity.Group;
import ExpenSplit.demo.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {

    private final GroupRepository groupRepository;
    private final MemberRepository memberRepository;
    private final BillRepository billRepository;
    private final PayeeRepository payeeRepository;
    private final DraweeRepository draweeRepository;
    private final TransactionRepository transactionRepository;

    public GroupService(
            GroupRepository groupRepository,
            MemberRepository memberRepository,
            BillRepository billRepository,
            PayeeRepository payeeRepository,
            DraweeRepository draweeRepository,
            TransactionRepository transactionRepository
    ) {
        this.groupRepository = groupRepository;
        this.memberRepository = memberRepository;
        this.billRepository = billRepository;
        this.payeeRepository = payeeRepository;
        this.draweeRepository = draweeRepository;
        this.transactionRepository = transactionRepository;
    }

    public Group createGroup(CreateGroupRequest request) {

        Group group = new Group();
        group.setId(request.id());
        group.setName(request.name());
        group.setCurrencyCode(request.currencyCode());

        return groupRepository.save(group);
    }

    public List<GroupResponse> getAllGroups() {
        return groupRepository.findAll()
                .stream()
                .map(g -> new GroupResponse(
                        g.getId(),
                        g.getName(),
                        g.getCurrencyCode(),
                        g.getTotalExpense()
                ))
                .toList();
    }

    public GroupResponse getGroup(String groupId) {
        Group g = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        return new GroupResponse(
                g.getId(),
                g.getName(),
                g.getCurrencyCode(),
                g.getTotalExpense()
        );
    }

    @Transactional
    public void deleteGroup(String groupId) {

        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        // 1️⃣ Delete settlements
        transactionRepository.deleteByGroupId(groupId);

        // 2️⃣ Delete bills + payees + drawees
        var bills = billRepository.findByGroupId(groupId);

        for (var bill : bills) {
            payeeRepository.deleteByBillId(bill.getId());
            draweeRepository.deleteByBillId(bill.getId());
        }

        billRepository.deleteAll(bills);

        // 3️⃣ Delete members
        memberRepository.deleteByGroupId(groupId);

        // 4️⃣ Delete group
        groupRepository.delete(group);
    }
}
