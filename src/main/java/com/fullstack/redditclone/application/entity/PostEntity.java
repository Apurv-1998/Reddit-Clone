package com.fullstack.redditclone.application.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;


@Entity
@Table(name = "posts")
@Data
public class PostEntity implements Serializable {

	private static final long serialVersionUID = 5479706720035552250L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable = false)
	private String postId;
	@Column(nullable = false)
	private String postName;
	@Nullable
	private String postUrl;
	@Nullable
	@Lob
	private String description;
	private int voteCount;
	@CreationTimestamp
	private Date createdDate;
	
	
	// One-To-Many Mappings
	
	
	@OneToMany(mappedBy = "postDetails",cascade = CascadeType.ALL)
	private List<VoteEntity> voteDetails;
	
	
	@OneToMany(mappedBy = "postDetails",cascade = CascadeType.ALL)
	private List<CommentEntity> commentDetails;
	
	//Many-To-One Mappings
	
	@ManyToOne
	@JoinColumn(name = "users_id")
	@JsonIgnore
	private UserEntity userDetails;
	
	@ManyToOne
	@JoinColumn(name = "sub_reddits_id")
	@JsonIgnore
	private SubredditEntity subredditDetails;
	

}
