package com.example.demo.Model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

	@Id
	@Column(name="username")
	private String username;
	private String password;
	private boolean enabled;
	
	@ManyToMany
	@JoinTable(
	  name = "users_roles", 
	  joinColumns = @JoinColumn(name = "username"), 
	  inverseJoinColumns = @JoinColumn(name = "role"))
	private Set<Role> roles=new HashSet<>();
 
	

}
