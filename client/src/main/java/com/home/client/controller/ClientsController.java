package com.home.client.controller;

import com.home.client.model.Client;
import com.home.client.model.DocumentType;
import com.home.client.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientsController {
    private final ClientService clientService;
    public ClientsController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    @Operation(summary = "Retorna todos os clientes")
    public ResponseEntity<List<Client>> all() {
        List<Client> list = clientService.listAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/document/{document}/documentType/{documentType}")
    @Operation(summary = "Retorna o cliente pelo documento")
    public Client find(@PathVariable String document, @PathVariable String documentType) {
        return clientService.findByDocument(document, DocumentType.valueOf(documentType));
    }

    @PostMapping
    @Operation(summary = "Adiciona um novo cliente")
    public ResponseEntity<Client> addClient(@RequestBody Client client) {
        return ResponseEntity.ok(clientService.addClient(client));
    }
    @DeleteMapping("/document/{document}/documentType/{documentType}")
    @Operation(summary = "Opt-out de um cliente (não receberá mais notificações)")
    public ResponseEntity delete(@PathVariable String document, @PathVariable String documentType) {
        clientService.delete(document, DocumentType.valueOf(documentType));
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}