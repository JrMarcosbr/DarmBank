package com.example.course.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.course.entities.Card;

public interface CardRepository  extends JpaRepository<Card, Long>{

}
