package com.home.client.repository;

import com.home.client.model.Client;
import com.home.client.model.ClientId;
import com.home.client.model.DocumentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@DataJpaTest
public class ClientRepositoryTest {

    private static final String DOCUMENT_1 = "60786297000";

    @Autowired
    ClientRepository clientRepository;

    @Test
    public void whenAddClient_thenClientIsSaved() {
        var client = new Client();
        var id = ClientId.builder().document("23463049058").documentType(DocumentType.CPF).build();
        client.setId(id);
        client.setName("Peter Doe");


        Client savedClient = clientRepository.save(client);

        Client found = clientRepository.findById(savedClient.getId()).orElse(null);

        assertEquals(client.getId().getDocument(), found.getId().getDocument());
    }

    @Test
    public void whenUpdateClient_thenClientIsSaved() {
        var id = ClientId.builder().document(DOCUMENT_1).documentType(DocumentType.CPF).build();
        var client1 = new Client();
        client1.setId(id);
        client1.setName("Erick Wendel");
        client1.setEmail("erick.wendel@gmail.com");
        client1 = clientRepository.save(client1);

        Client client = clientRepository.findById(client1.getId()).orElse(null);
        var oldName = client.getName();
        client.setName("Erick Wendel 2");

        clientRepository.save(client);
        Client found = clientRepository.findById(client1.getId()).orElse(null);

        assertEquals(client.getId().getDocument(), found.getId().getDocument());
        assertEquals(client.getName(), found.getName());
        assertNotEquals(oldName, found.getName());
    }



}
