package ExpenSplit.demo.controller;

import ExpenSplit.demo.DTOs.PersonalExpenseSummaryResponse;
import ExpenSplit.demo.service.PersonalExpenseSummaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/personal-summary")
@RequiredArgsConstructor
@CrossOrigin(value = "*")
public class PersonalExpenseSummaryController {

    private final PersonalExpenseSummaryService summaryService;

    @GetMapping("/user/{userId}/group/{groupId}")
    public List<PersonalExpenseSummaryResponse> getUserGroupSummary(
            @PathVariable String userId,
            @PathVariable String groupId
    ) {
        return summaryService.getUserSummary(userId, groupId);
    }
}
