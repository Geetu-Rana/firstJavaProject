package com.JwtApp.login.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer role_id;
	
	@Enumerated(EnumType.STRING)
	@NotNull
	@NotBlank
	@Column(length = 20)
	private ERole roleName;

	public Role(ERole roleName) {
		super();
		this.roleName = roleName;
	}
	
	
	
}
