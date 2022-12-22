package com.example.course.services;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.example.course.entities.User;
import com.example.course.repositories.UserRepository;
import com.example.course.services.exceptions.DatabaseException;
import com.example.course.services.exceptions.ResourceNotFoundException;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;
	
	public List<User> findAll() {
		return repository.findAll();
	}
	
	public User findById(Long id) {
		Optional<User> obj = repository.findById(id);
		return obj.orElseThrow(()-> new ResourceNotFoundException(id));
	}
	
	public User insert(User obj) {
		return repository.save(obj);
	}
	
	public boolean login(String cpf, String senha, Long id) {
		String userCpf = findById(id).getCpf();
		String userSenha = findById(id).getSenha();
		if (userCpf == cpf && userSenha == senha) {
			return true;
		}
		throw new IllegalArgumentException("Password or Cpf incorrect");
	}
	
	public boolean recuperarSenha(String cpf, String nome, String email, Long id) {
		String userCpf = findById(id).getCpf();
		String userName= findById(id).getName();
		String userEmail= findById(id).getEmail();
		if (userCpf == cpf && userName == nome && userEmail == email) {
			return true;
		}
		throw new IllegalArgumentException("Name, Email or Cpf incorrect");
	}
	
	public boolean confirmarSenha(String senha, String confirmacao_senha) {
		if(senha == confirmacao_senha) {
			return true;
		}
		throw new IllegalArgumentException("Password distinct");
	}
	
	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}
	
	public User update(Long id, User obj) {
		try {
			@SuppressWarnings("deprecation")
			User entity = repository.getOne(id);
			updateData(entity, obj);
			return repository.save(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}	
	}

	private void updateData(User entity, User obj) {
		entity.setName(obj.getName());
		entity.setEmail(obj.getEmail());
		entity.setCpf(obj.getCpf());
		entity.setTelefone(obj.getTelefone());
		entity.setSenha(obj.getSenha());
		entity.setTipo_conta(obj.getTipo_conta());
		
	}
}
