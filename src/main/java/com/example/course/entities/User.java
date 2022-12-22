package com.example.course.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "tb_user")
public class User implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String email;
	private String cpf;
	private String telefone;
	private int tipo_conta;
	private String senha;
	private String endereco;
	
	@JsonIgnore
	@OneToMany(mappedBy = "client")
	private List<Card> carts = new ArrayList<>();
	
	public List<Card> getCarts() {
		return carts;
	}
	
	public User() {
	}

	public User(Long id, String name, String email, String cpf, String telefone, int tipo_conta, String senha, String endereco) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.cpf = cpf;
		this.telefone = telefone;
		this.tipo_conta = tipo_conta;
		this.senha = senha;
		this.endereco = endereco;
	}

	

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public int getTipo_conta() {
		return tipo_conta;
	}

	public void setTipo_conta(int tipo_conta) {
		this.tipo_conta = tipo_conta;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public int hashCode() {
		return Objects.hash(carts, cpf, email, id, senha, tipo_conta);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(carts, other.carts) && Objects.equals(cpf, other.cpf)
				&& Objects.equals(email, other.email) && Objects.equals(id, other.id)
				&& Objects.equals(senha, other.senha) && tipo_conta == other.tipo_conta;
	}
	
	
	
	
}
