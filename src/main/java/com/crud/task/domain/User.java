package com.crud.task.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "USERS")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
@SequenceGenerator(name = "SQ_USER_ID_GENERATOR", sequenceName = "USERS_ID_SEQ", allocationSize = 1, initialValue = 1)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_USER_ID_GENERATOR")
    @Column(name = "ID", nullable = false)
    private Long id;

    @NonNull
    @Column(name = "USERNAME", nullable = false, length = 32)
    private String username;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username);
    }
}
