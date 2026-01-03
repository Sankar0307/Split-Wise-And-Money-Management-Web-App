package ExpenSplit.demo.controller;

import ExpenSplit.demo.DTOs.AddMemberRequest;
import ExpenSplit.demo.DTOs.MemberResponse;
import ExpenSplit.demo.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
@CrossOrigin(value = "*")
public class MemberController {

    private final MemberService memberService;

    // 1️⃣ Add member to group
    @PostMapping
    public MemberResponse addMember(@RequestBody AddMemberRequest request) {
        return memberService.addMember(request);
    }

    // 2️⃣ List members of a group
    @GetMapping("/group/{groupId}")
    public List<MemberResponse> getMembers(@PathVariable String groupId) {
        return memberService.getMembersByGroup(groupId);
    }

    // 3️⃣ Get single member
    @GetMapping("/{groupId}/{userIndex}")
    public MemberResponse getMember(
            @PathVariable String groupId,
            @PathVariable Integer userIndex
    ) {
        return memberService.getMember(groupId, userIndex);
    }
}
