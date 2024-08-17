package ecnic.service.money.transaction.web.controllers;

import ecnic.service.common.models.PagedResult;
import ecnic.service.money.transaction.TransactionService;
import ecnic.service.money.transaction.domain.models.CreateTransaction;
import ecnic.service.money.transaction.domain.models.Transaction;
import ecnic.service.money.transaction.domain.models.UpdateTransaction;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "api/transactions")
public class TransactionController {

  private final TransactionService transactionService;


  @GetMapping
  PagedResult<Transaction> getTransactions(
      @PageableDefault(value = 5, page = 0) Pageable pageable) {
    return transactionService.getAllTransaction(pageable);
  }

  @GetMapping("{id}")
  Transaction getTransactionById(@PathVariable Long id) {
    return transactionService.getTransaction(id);
  }

  @PostMapping
  Transaction createTransaction(@RequestBody @Valid CreateTransaction createTransaction) {
    return transactionService.createTransaction(createTransaction);
  }

  @PutMapping
  Transaction updateTransaction(@RequestBody @Valid UpdateTransaction updateTransaction) {
    return transactionService.updateTransaction(updateTransaction);
  }

  @DeleteMapping
  void deleteTransaction(@PathVariable Long id) {
    transactionService.deleteTransaction(id);
  }

}
