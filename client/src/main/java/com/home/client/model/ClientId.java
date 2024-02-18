package com.home.client.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Embeddable
public class ClientId  implements Serializable {

    private String document;

    private DocumentType documentType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClientId)) return false;
        ClientId clientId = (ClientId) o;
        return document.equals(clientId.document) &&
                documentType.equals(clientId.documentType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(document, documentType);
    }
}
