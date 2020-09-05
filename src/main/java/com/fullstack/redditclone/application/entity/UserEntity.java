package com.fullstack.redditclone.application.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "users")
@Data
public class UserEntity implements Serializable {

	private static final long serialVersionUID = 5400277633453422190L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(nullable = false)
	private String userId;
	@Column(nullable = false)
	private String firstName;
	@Column(nullable = false)
	private String lastName;
	@Column(nullable = false)
	private String password;
	@Column(nullable = false)
	private String registeredPassword;
	@Column(nullable = false)
	private String email;
	@CreationTimestamp
	private Date createdDate;
	@Column(nullable = false)
	private boolean isVerified = false;

	// One-To-Many Mappings
	
	@ToString.Exclude
	@OneToMany(mappedBy = "userDetails", fetch = FetchType.LAZY)
	private List<PostEntity> postDetails;
	
	@ToString.Exclude
	@OneToMany(mappedBy = "userDetails", cascade = CascadeType.ALL)
	private List<SubredditEntity> subredditDetails;
	
	@ToString.Exclude
	@OneToMany(mappedBy = "userDetails", cascade = CascadeType.ALL)
	private List<VoteEntity> voteDetails;
	
	@ToString.Exclude
	@OneToMany(mappedBy = "userDetails", cascade = CascadeType.ALL)
	private List<CommentEntity> commentDetails;

}
