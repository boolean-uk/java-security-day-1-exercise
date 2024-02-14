package com.booleanuk.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column
	private String first_name;
	@Column
	private String last_name;
	@Column
	private String phone;
	@Column
	private String email;
	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private List<Loan> loans;

	public User(int id) {
		this.id = id;
	}

	public User(String first_name, String last_name, String phone, String email) {
		this.first_name = first_name;
		this.last_name = last_name;
		this.phone = phone;
		this.email = email;
	}
}
