package com.diagnotech.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.diagnotech.model.Components;

public interface ComponentService {
	
	public Optional<Components> getComponentById(String compid) ;
	
	public List<Components> getAllComponents();
	
	public Page<Components> getAllComponents(Pageable paging);
	
	public Components save(Components comp);
	
	public void delete(String compid);
}
