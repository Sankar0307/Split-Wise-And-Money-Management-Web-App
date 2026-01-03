package ExpenSplit.demo.service;

import ExpenSplit.demo.DTOs.PersonalExpenseSummaryResponse;
import ExpenSplit.demo.repository.PersonalExpenseSummaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonalExpenseSummaryService {

    private final PersonalExpenseSummaryRepository repository;

    public List<PersonalExpenseSummaryResponse> getUserSummary(
            String userId,
            String groupId
    ) {
        return repository.findByUserIdAndGroupId(userId, groupId)
                .stream()
                .map(s -> new PersonalExpenseSummaryResponse(
                        s.getCategory(),
                        s.getTotalAmount()
                ))
                .toList();
    }
}
