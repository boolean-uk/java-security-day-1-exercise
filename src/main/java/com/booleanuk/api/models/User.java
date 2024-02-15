package com.booleanuk.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "user")
public class User extends Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String username;
    @Column
    private String email;

    @OneToMany(mappedBy = "user")
    @JsonIgnoreProperties("user")
    private List<Lend> lends;

    @Override
    public boolean haveNullFields() {
        return username == null || email == null;
    }

    @Override
    public void copyOverData(Model model) {
        try {
            User _user = (User) model;

            username = _user.username;
            email = _user.email;

            if (!_user.lends.isEmpty())
                lends = _user.lends;
        }
        catch (Exception e) {}
    }
}
