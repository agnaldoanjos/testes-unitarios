package com.home.client.model;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.sql.Timestamp;

@Entity
@Table(name = "CLIENT")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE client SET deleted = 'S', deleted_date = CURRENT_TIMESTAMP WHERE id = ?")
@SQLRestriction("deleted <> 'S'")
public class Client {

    @EmbeddedId
    private ClientId id;

    @Column(name="name",columnDefinition = "VARCHAR(64)")
    private String name;

    @Column(name="email", columnDefinition = "VARCHAR(255)")
    private String email;

    @Column(name="deleted", columnDefinition = "CHAR(1)")
    private String deleted;

    @Column(name="deleted_date",columnDefinition = "TIMESTAMP")
    private Timestamp deleteDate;

}
