package com.example.course.services;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.example.course.entities.Admin;
import com.example.course.entities.User;
import com.example.course.repositories.AdminRepository;
import com.example.course.services.exceptions.DatabaseException;
import com.example.course.services.exceptions.ResourceNotFoundException;

@Service
public class AdminService {

	@Autowired
	private AdminRepository repository;
	
	public List<Admin> findAll() {
		return repository.findAll();
	}
	
	public Admin findById(Long id) {
		Optional<Admin> obj = repository.findById(id);
		return obj.orElseThrow(()-> new ResourceNotFoundException("ResourceNotFound"));
	}
	
	public Admin findByCpf(String cpf) {
		Optional<Admin> obj = repository.findByCpf(cpf);
		return obj.orElseThrow(()-> new ResourceNotFoundException(cpf));
	}
	
	public Admin insert(Admin obj) {
		return repository.save(obj);
	}
	
	public boolean login(String cpf, String password, Long id) {
		String AdminCpf = findById(id).getCpf();
		String Adminpassword = findById(id).getpassword();
		if (AdminCpf == cpf && Adminpassword == password) {
			return true;
		}
		throw new IllegalArgumentException("Password or Cpf incorrect");
	}
	
	public Admin recoverPassword(String cpf, String name, String email) {
		if (repository.findByCpf(cpf) != null) {
			String userName = findByCpf(cpf).getName();
			String userEmail= findByCpf(cpf).getEmail();
			if(userEmail.equals(email) && userName.equals(name)) {
				return findByCpf(cpf);
			}else {
				throw new ResourceNotFoundException(cpf);
			}
		}else {
			throw new ResourceNotFoundException(cpf);
		}
}
	
	public boolean confirmarpassword(String password, String confirmacao_password) {
		if(password == confirmacao_password) {
			return true;
		}
		throw new IllegalArgumentException("Password distinct");
	}
	
	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("ResourceNotFound");
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}
	
	public Admin update(Long id, Admin obj) {
		try {
			@SuppressWarnings("deprecation")
			Admin entity = repository.getOne(id);
			updateData(entity, obj);
			return repository.save(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("ResourceNotFound");
		}	
	}

	private void updateData(Admin entity, Admin obj) {
		entity.setName(obj.getName());
		entity.setCpf(obj.getCpf());
		entity.setpassword(obj.getpassword());
		
	}
}
