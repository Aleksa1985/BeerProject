package com.lexsoft.project.beer.database.model;

import lombok.*;
import org.hibernate.annotations.LazyToOne;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
public class UserDb extends BaseEntity<Long> {

    private String username;
    private String password;
    private String role;

    @Builder
    public UserDb(Long id, Date created, Date updated, String username, String password, String role) {
        super(id, created, updated);
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
