package com.home.client.service;

import com.home.client.model.Client;
import com.home.client.model.DocumentType;

import java.util.List;

public interface ClientService {
    void delete(String document, DocumentType documentType);

    Client addClient(Client client);

    Client findByDocument(String document, DocumentType documentType);

    List<Client> listAll();
}
