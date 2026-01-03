package ExpenSplit.demo.controller;


import ExpenSplit.demo.DTOs.SettlementResult;
import ExpenSplit.demo.entity.Transaction;
import ExpenSplit.demo.service.SettlementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/settlements")
@RequiredArgsConstructor
@CrossOrigin(value = "*")
public class SettlementController {

    private final SettlementService settlementService;

    @PostMapping("/{groupId}")
    public List<SettlementResult> settle(@PathVariable String groupId) {
        return settlementService.settleAndSave(groupId);
    }

    @GetMapping("/{groupId}")
    public List<Transaction> getSettlement(@PathVariable String groupId) {
        return settlementService.getSavedTransactions(groupId);
    }

}
