package ExpenSplit.demo.controller;

import ExpenSplit.demo.DTOs.AddExpenseRequest;
import ExpenSplit.demo.DTOs.ExpenseDetailResponse;
import ExpenSplit.demo.DTOs.ExpenseSummaryResponse;
import ExpenSplit.demo.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
@CrossOrigin(value = "*")
public class ExpenseController {

    private final ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<String> addExpense(@RequestBody AddExpenseRequest request) throws BadRequestException {
        expenseService.addExpense(request);
        return ResponseEntity.ok("Expense added successfully");
    }

    @GetMapping("/group/{groupId}")
    public List<ExpenseSummaryResponse> getExpenses(@PathVariable String groupId) {
        return expenseService.getExpensesByGroup(groupId);
    }

    // 2️⃣ GET expense details
    @GetMapping("/{billId}")
    public ExpenseDetailResponse getExpense(@PathVariable String billId) {
        return expenseService.getExpense(billId);
    }



}
