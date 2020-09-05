package com.fullstack.redditclone.application.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fullstack.redditclone.application.shared.VoteType;

import lombok.Data;

@Entity
@Table(name = "votes")
@Data
public class VoteEntity implements Serializable {
	
	
	private static final long serialVersionUID = 5001884556162813726L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable = false)
	private String voteId;
	@Column(nullable = false)
	private VoteType voteType;
	@Column(nullable = false)
	private VoteType lastAction;
	
	
	//Many-To-One Mapping
	
	
	@ManyToOne
	@JoinColumn(name = "users_id")
	@JsonIgnore
	private UserEntity userDetails;
	
	
	@ManyToOne
	@JoinColumn(name = "posts_id")
	@JsonIgnore
	private PostEntity postDetails;

}
