package com.example.course.services;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.example.course.entities.Card;
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
	
	public User insert(User obj) {
		try {
			if(ValidateCPF(obj.getCpf())) {
				return repository.save(obj);
			}else {
				CPFException es = null;
				throw new CPFException(es.getMessage());
			}
			
		} catch (CPFException e) {
			throw new CPFException("Invalid CPF!");
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
        	return false;

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
            	return false;
            }
        } catch (InputMismatchException erro) {
        	return false;
    }
}
	
	public boolean login(String cpf, String senha, Long id) {
		String userCpf = findById(id).getCpf();
		String userSenha = findById(id).getPassword();
		if (userCpf == cpf && userSenha == senha) {
			return true;
		}
		throw new IllegalArgumentException("Incorrect password or CPF");
	}
	
	public boolean recuperarSenha(String cpf, String nome, String email, Long id) {
		String userCpf = findById(id).getCpf();
		String userName= findById(id).getName();
		String userEmail= findById(id).getEmail();
		if (userCpf == cpf && userName == nome && userEmail == email) {
			return true;
		}
		throw new IllegalArgumentException("Incorrect name, email or CPF");
	}
	
	public boolean confirmarSenha(String senha, String confirmacao_senha) {
		if(senha == confirmacao_senha) {
			return true;
		}
		throw new IllegalArgumentException("Distinct Password");
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
