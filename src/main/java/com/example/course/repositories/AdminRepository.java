package com.example.course.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.course.entities.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long>{
}
