package com.fullstack.redditclone.application.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "verification_token")
@Data
public class VerificationTokenEntity implements Serializable {

	private static final long serialVersionUID = -4370359095697162401L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String tokenId;
	private Date expirationTime;
	
	//One-To-One Mapping
	
	@OneToOne
	@JoinColumn(name = "users_id")
	private UserEntity userDetails;
	

}
