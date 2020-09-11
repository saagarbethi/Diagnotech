package com.diagnotech.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Entity
@Table(name = "TESTCOMPONENTS")
public class Components {
	
	
	
	@Id
	@Column(length = 8)
	private String compid;
	
	@Column(length = 500)
	private String componentnm;
	
	@Column(length = 4000)
	private String description;
	
	@Column(length = 8)
	private String userid;
	
	@Column(length = 2)
	private String comphead;
	
	@Column(length = 2)
	private String orgf12;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date createdate = new Date();
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "hh:mm:ss a")
	private Date createtime = new Date();

}
