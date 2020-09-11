package com.diagnotech.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.diagnotech.model.Components;
import com.diagnotech.repository.ComponentRepository;

@Service
public class ComponentServiceImpl implements ComponentService{

	@Autowired
	ComponentRepository compRepo;
	
	@Override
	public Optional<Components> getComponentById(String compid) {
		return compRepo.findById(compid);
	}

	@Override
	public List<Components> getAllComponents() {
		return compRepo.findAll();
	}

	@Override
	public Components save(Components comp) {
		return compRepo.save(comp);
	}

	@Override
	public Page<Components> getAllComponents(Pageable paging) {
		return compRepo.findAll(paging);
	}

	@Override
	public void delete(String compid) {
		compRepo.deleteById(compid);
		
	}

}
