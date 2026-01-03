package ExpenSplit.demo.service;

import ExpenSplit.demo.DTOs.AddMemberRequest;
import ExpenSplit.demo.DTOs.MemberResponse;
import ExpenSplit.demo.entity.Member;
import ExpenSplit.demo.entity.MemberId;
import ExpenSplit.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    // 1️⃣ Add member to group
    public MemberResponse addMember(AddMemberRequest request) {

        Member member = new Member();
        member.setUserIndex(request.userIndex());
        member.setGroupId(request.groupId());
        member.setUserId(request.userId());
        member.setUserNameInGroup(request.userNameInGroup());
        member.setIsAdmin(
                request.isAdmin() != null && request.isAdmin()
        );

        Member saved = memberRepository.save(member);
        return toResponse(saved);
    }

    // 2️⃣ Get all members of a group
    public List<MemberResponse> getMembersByGroup(String groupId) {
        return memberRepository.findByGroupId(groupId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // 3️⃣ Get single member
    public MemberResponse getMember(String groupId, Integer userIndex) {
        MemberId id = new MemberId(userIndex, groupId);
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        return toResponse(member);
    }

    // 🔁 Mapping helper
    private MemberResponse toResponse(Member m) {
        return new MemberResponse(
                m.getUserIndex(),
                m.getGroupId(),
                m.getUserId(),
                m.getUserNameInGroup(),
                m.getIsAdmin(),
                m.getStatus(),
                m.getTotalPaid(),
                m.getTotalSpent()
        );
    }
}
