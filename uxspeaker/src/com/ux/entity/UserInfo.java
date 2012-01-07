package com.ux.entity;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity  
@Table(name = "ux_user_info")
public class UserInfo implements Serializable {   
	private static final long serialVersionUID = 1L;
	@Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="seq")
	@SequenceGenerator(name="seq",sequenceName="id",allocationSize=1)
    @Column(name = "id", nullable = false)
    private Integer id;

}
