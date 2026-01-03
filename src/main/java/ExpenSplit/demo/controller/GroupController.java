package ExpenSplit.demo.controller;

import ExpenSplit.demo.DTOs.CreateGroupRequest;
import ExpenSplit.demo.DTOs.GroupResponse;
import ExpenSplit.demo.entity.Group;
import ExpenSplit.demo.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
@CrossOrigin(value = "*")
public class GroupController {

    private final GroupService groupService;

    // 1️⃣ Create group
    @PostMapping
    public Group create(@RequestBody CreateGroupRequest request) {
        return groupService.createGroup(request);
    }

    // 2️⃣ Get all groups
    @GetMapping
    public List<GroupResponse> getAll() {
        return groupService.getAllGroups();
    }

    // 3️⃣ Get single group
    @GetMapping("/{groupId}")
    public GroupResponse getOne(@PathVariable String groupId) {
        return groupService.getGroup(groupId);
    }

    @DeleteMapping("/{groupId}")
    public void deleteGroup(@PathVariable String groupId) {
        groupService.deleteGroup(groupId);
    }
}

