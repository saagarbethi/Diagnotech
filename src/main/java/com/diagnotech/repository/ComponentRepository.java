package com.diagnotech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diagnotech.model.Components;

public interface ComponentRepository extends JpaRepository<Components, String> {
	
	
}
