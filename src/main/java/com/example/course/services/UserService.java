package com.example.course.services;


import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.example.course.entities.User;
import com.example.course.repositories.UserRepository;
import com.example.course.services.exceptions.CPFException;
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
	
	public User findByCpf(String cpf) {
		Optional<User> obj = repository.findByCpf(cpf);
		return obj.orElseThrow(()-> new ResourceNotFoundException(cpf));
	}
	
	
	
	public User insert(User obj) {
		try {
			ValidateCPF(obj.getCpf());
			return repository.save(obj);
		} catch (CPFException e) {
			throw new CPFException(obj.getCpf());
		}
	}
	
	public boolean ValidateCPF(String CPF) {
        if (CPF.equals("00000000000") ||
            CPF.equals("11111111111") ||
            CPF.equals("22222222222") || CPF.equals("33333333333") ||
            CPF.equals("44444444444") || CPF.equals("55555555555") ||
            CPF.equals("66666666666") || CPF.equals("77777777777") ||
            CPF.equals("88888888888") || CPF.equals("99999999999") ||
            (CPF.length() != 11))
        	throw new IllegalArgumentException("Invalid CPF");

        char dig10, dig11;
        int sm, i, r, num, peso;

        try {

            sm = 0;
            peso = 10;
            for (i=0; i<9; i++) {

            num = (int)(CPF.charAt(i) - 48);
            sm = sm + (num * peso);
            peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig10 = '0';
            else dig10 = (char)(r + 48); 

            sm = 0;
            peso = 11;
            for(i=0; i<10; i++) {
	            num = (int)(CPF.charAt(i) - 48);
	            sm = sm + (num * peso);
	            peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11)) {
            	dig11 = '0';
            }
            else { 
            	dig11 = (char)(r + 48);
            }
            if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10))) {
                 return(true);
            }else {
            	throw new IllegalArgumentException("Invalid CPF");
            }
        } catch (InputMismatchException erro) {
            throw new IllegalArgumentException("Invalid CPF");
    }
}
	
	public User login(String cpf, String password) {
			if(findByCpf(cpf) != null) {
				String userPassword = findByCpf(cpf).getPassword();
				if(userPassword.equals(password)) {
					Optional<User> obj = repository.findByCpf(cpf);
					return obj.orElseThrow(()-> new ResourceNotFoundException(cpf));
				}else {
					throw new ResourceNotFoundException(userPassword);
				}
			}else {
				throw new ResourceNotFoundException("INVALID PASSWORD");			}
			}
	
	public User recoverPassword(String cpf, String name, String email) {
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
	
	public boolean confirmPassword(String password, String confirm_password) {
		if(password.equals(confirm_password)) {
			return true;
		}
		return false;
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
		entity.setContact(obj.getContact());
		entity.setPassword(obj.getPassword());
		entity.setAccountType(obj.getAccountType());
		
	}
}
