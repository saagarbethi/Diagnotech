package com.diagnotech.controller;


import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.diagnotech.exception.ComponentException;
import com.diagnotech.model.Components;
import com.diagnotech.service.ComponentService;

@Controller
@ResponseBody
public class ComponentController {
	
	@Autowired
	ComponentService compService;
	
	@RequestMapping("/componentwelcome")
	public String welcome() {
		return "Welcome to Componet Master";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/component/{compid}")
	public Components getAllComponents(@PathVariable(value="compid") String compid ) throws ComponentException{
		return compService.getComponentById(compid).orElseThrow(()-> new ComponentException("no records found"));
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/component/")
	public List<Components>  getAllComponents(){
		return compService.getAllComponents().stream().sorted().collect(Collectors.toList());
				
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/component/page/{page}/count/{count}")
	public ResponseEntity<Map<String, Object>>  getAllComponentsByPage(@PathVariable(value="page") int page,@PathVariable(value="count") int count){
		Pageable paging = PageRequest.of(page-1, count,Sort.by("componentnm"));
		Page<Components> pageComp = compService.getAllComponents(paging);
		Map<String, Object> response = new HashMap<>();
	      response.put("components", pageComp);
	      response.put("currentPage", pageComp.getNumber()+1);
	      response.put("totalItems", pageComp.getTotalElements());
	      response.put("totalPages", pageComp.getTotalPages());
	      
	      return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/component")
	public Components save(@RequestBody Components comp) {
		return compService.save(comp);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value="/component/{compid}")
	public Map<String,Boolean> delete(@PathVariable(value="compid") String compid) throws ComponentException{
		compService.getComponentById(compid).orElseThrow(()-> new ComponentException("no records found"));
		compService.delete(compid); 
		Map<String,Boolean> map = new HashMap<>();
		map.put("deleted", true);
		return map;
	}
	
	@RequestMapping(method = RequestMethod.PUT, value="/component/{compid}")
	public Components update(@PathVariable(value="compid") String compid,@RequestBody Components components) throws ComponentException {
		Components updatecomponents = 	compService.getComponentById(compid).orElseThrow(()-> new ComponentException("no records found"));
		updatecomponents.setComphead(components.getComphead());
		updatecomponents.setComponentnm(components.getComponentnm());
		updatecomponents.setDescription(components.getDescription());
		updatecomponents.setOrgf12(components.getOrgf12());
		updatecomponents.setUserid(components.getUserid());
		return compService.save(updatecomponents);
	}
	
	@RequestMapping("/")
	public String login() {
		return "login";
	}
	

	
}
