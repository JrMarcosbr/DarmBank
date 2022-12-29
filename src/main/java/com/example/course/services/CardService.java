package com.example.course.services;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.example.course.entities.Card;
import com.example.course.entities.enums.CardStatus;
import com.example.course.repositories.CardRepository;
import com.example.course.services.exceptions.DatabaseException;
import com.example.course.services.exceptions.ResourceNotFoundException;
//import com.example.course.services.exceptions.DatabaseException;
//import com.example.course.services.exceptions.ResourceNotFoundException;

@Service
public class CardService {

	@Autowired
	private CardRepository repository;
	
	public List<Card> findAll() {
		return repository.findAll();
	}
		
	public Card findById(Long id) {
		Optional<Card> obj = repository.findById(id);
		return obj.orElseThrow();
	}
	public Card insert(Card obj) {
		return repository.save(obj);
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
	
	public Card update(Long id, Card obj) {
		try {
			Card entity = repository.getOne(id);
			updateData(entity, obj);
			return repository.save(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("ResourceNotFound");
		}	
	}

	private void updateData(Card entity, Card obj) {
		entity.setCartStatus(obj.getCartStatus());
		entity.setJustify(obj.getJustify());
	}
}
