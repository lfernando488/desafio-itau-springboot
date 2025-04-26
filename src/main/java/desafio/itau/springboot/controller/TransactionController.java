package desafio.itau.springboot.controller;


import desafio.itau.springboot.dto.TransactionDTO;
import desafio.itau.springboot.model.Transaction;
import desafio.itau.springboot.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

@RestController
@RequestMapping("/transacoes")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService){
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<Void> createTransaction(@Valid @RequestBody TransactionDTO transactionDTO){
        if(transactionDTO.getDataHora().isAfter(OffsetDateTime.now()) || transactionDTO.getValor() <= 0){
            return ResponseEntity.unprocessableEntity().build();
        }
        transactionService.addTransaction(new Transaction(transactionDTO.getValor(), transactionDTO.getDataHora()));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> clearTransactions(){
        transactionService.clearTransactions();
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //Endpoint teste (nao obrigatorio)
    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions(){
        Queue<Transaction> list = transactionService.getAllTransactions();
        List<Transaction> tr = new ArrayList<>(list);
        return ResponseEntity.ok(tr);
    }

}
