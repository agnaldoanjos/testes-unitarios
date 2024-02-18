package com.home.client.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.home.client.model.Client;
import com.home.client.model.ClientId;
import com.home.client.model.DocumentType;
import com.home.client.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClientsController.class)
public class ClientsControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ClientService clientService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void whenGetAllClients_thenReturns200AndClientList() throws Exception {
        var clientList = Arrays.asList(new Client(), new Client());
        given(clientService.listAll()).willReturn(clientList);

        mockMvc.perform(get("/clients")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(clientList.size())));
    }

    @Test
    public void whenFindByDocument_thenReturns200AndClient() throws Exception {
        var document = "12345678901";
        var id = ClientId.builder().document(document).documentType(DocumentType.CPF).build();
        Client client = Client.builder().id(id).build(); // Setup client with the document

        when(clientService.findByDocument(document, DocumentType.CPF)).thenReturn(client);

        mockMvc.perform(get("/clients/document/{document}/documentType/{documentType}", document, "CPF")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id.document", is(document)));
    }

    @Test
    public void whenAddClient_thenReturns200AndClient() throws Exception {
        var id = ClientId.builder().document("12345678901").documentType(DocumentType.CPF).build();
        Client client = Client.builder().id(id).build(); // Setup client with sample data

        when(clientService.addClient(client)).thenReturn(client);

        mockMvc.perform(post("/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(client)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id.document", is(client.getId().getDocument())));
    }

    @Test
    public void whenDeleteClient_thenReturns200() throws Exception {
        final String document = "12345678901";

        doNothing().when(clientService).delete(any(), any());

        mockMvc.perform(delete("/clients/document/{document}/documentType/{documentType}", document, "CPF")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
