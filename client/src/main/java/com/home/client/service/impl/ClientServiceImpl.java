package com.home.client.service.impl;

import com.home.client.model.Client;
import com.home.client.model.ClientId;
import com.home.client.model.DocumentType;
import com.home.client.repository.ClientRepository;
import com.home.client.service.ClientService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public void delete(final String document, DocumentType documentType) {
        Client savedClient = clientRepository.findById(ClientId.builder()
                .document(document)
                .documentType(documentType)
                .build()
            ).orElse(null);

        clientRepository.delete(savedClient);
    }

    @Override
    public Client addClient(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public Client findByDocument(String document, DocumentType documentType) {
        Client savedClient = clientRepository.findById(
                ClientId.builder()
                        .document(document)
                        .documentType(documentType)
                        .build()
                ).orElse(null);
        return savedClient;
    }

    @Override
    public List<Client> listAll() {
        return clientRepository.findAll();
    }
}
