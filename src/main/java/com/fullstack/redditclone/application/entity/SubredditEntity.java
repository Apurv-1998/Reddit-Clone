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

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Table(name = "sub_reddits")
@Data
public class SubredditEntity implements Serializable {

	private static final long serialVersionUID = 6922561494764041334L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable = false)
	private String subredditId;
	@Column(nullable = false)
	private String subredditName;
	@Column(nullable = false)
	@Lob
	private String description;
	@CreationTimestamp
	private Date createdDate;
	
	//One-To-Many Relations
	
	@OneToMany(mappedBy = "subredditDetails",cascade = CascadeType.ALL)
	private List<PostEntity> postDetails;
	
	
	//Many-To-One Mappings
	
	@ManyToOne
	@JoinColumn(name = "users_id")
	@JsonIgnore
	private UserEntity userDetails;
}
