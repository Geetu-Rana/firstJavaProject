package com.JwtApp.login.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;


@Entity
@Table( name = "User", uniqueConstraints = {
		@UniqueConstraint(columnNames = "username"),
		@UniqueConstraint(columnNames = "email")
})
@Data
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer user_Id;
	@NotBlank
	@NotNull
	@Size(min = 3, max = 24, message = "size should be greater than 3 and less than 24")
	private String userName;
	
	@Email(message = "Email shold be in example@email.com")
	private String email;
	
	@NotNull
	private String password;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_role", 
		joinColumns = @JoinColumn(name ="user_ID")
		,inverseJoinColumns = @JoinColumn(name = "role_Id" )
	)
	private Set<Role> roles = new HashSet<>();

	public User() {
		// TODO Auto-generated constructor stub
	}

	public User(
			@NotBlank @NotNull @Size(min = 3, max = 24, message = "size should be greater than 3 and less than 24") String userName,
			@Email(message = "Email shold be in example@email.com") String email, @NotNull String password) {
		super();
		this.userName = userName;
		this.email = email;
		this.password = password;
	}
	
	
}
