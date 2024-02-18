package com.home.client.service.impl;

import com.home.client.model.Client;
import com.home.client.model.ClientId;
import com.home.client.model.DocumentType;
import com.home.client.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ClientServiceImplTest {

    @Mock
    private ClientRepository clientRepository;

    private ClientServiceImpl clientService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.clientService = new ClientServiceImpl(clientRepository);
    }

    @Test
    public void whenAddClient_thenClientIsSaved() {
        var id = ClientId.builder().document("12345678901").documentType(DocumentType.CPF).build();
        Client client = new Client();
        client.setId(id);

        when(clientRepository.save(any(Client.class))).thenReturn(client);

        Client created = clientService.addClient(client);

        verify(clientRepository, times(1)).save(client);
        assertEquals(client.getId().getDocument(), created.getId().getDocument());
    }

    @Test
    public void whenDeleteClient_thenClientIsDeleted() {
        var id = ClientId.builder().document("12345678901").documentType(DocumentType.CPF).build();
        Client client = new Client();
        client.setId(id);
        when(clientRepository.findById(id)).thenReturn(Optional.of(client));

        clientService.delete(id.getDocument(), DocumentType.CPF);

        verify(clientRepository, times(1)).delete(client);
    }
    @Test
    public void whenFindByDocument_thenClientIsReturned() {
        var id = ClientId.builder().document("12345678901").documentType(DocumentType.CPF).build();
        Client client = new Client();
        client.setId(id);
        when(clientRepository.findById(id)).thenReturn(Optional.of(client));

        Client found = clientService.findByDocument(id.getDocument(), DocumentType.CPF);

        verify(clientRepository, times(1)).findById(id);
        assertEquals(id.getDocument(), found.getId().getDocument());
    }

    @Test
    public void whenListAll_thenAllClientsAreReturned() {
        List<Client> clients = Arrays.asList(new Client(), new Client());
        when(clientRepository.findAll()).thenReturn(clients);

        List<Client> foundClients = clientService.listAll();

        verify(clientRepository, times(1)).findAll();
        assertEquals(2, foundClients.size());
    }
}
