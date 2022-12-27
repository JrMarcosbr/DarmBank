package com.example.course.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.course.entities.Card;

public interface CardRepository  extends JpaRepository<Card, Long>{
		
}
