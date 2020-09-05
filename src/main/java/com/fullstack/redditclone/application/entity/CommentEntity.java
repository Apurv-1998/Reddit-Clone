package com.fullstack.redditclone.application.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.ToString;


@Entity
@Table(name = "comments")
@Data
public class CommentEntity implements Serializable {

	private static final long serialVersionUID = 3960262538031782022L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable = false)
	private String commentId;
	@CreationTimestamp
	private Date createdDate;
	@Column(nullable = false)
	@Lob
	private String text;
	
	
	//Many-To-One Relations
	
	@ToString.Exclude
	@ManyToOne
	@JoinColumn(name = "users_id")
	@JsonIgnore
	private UserEntity userDetails;
	
	@ToString.Exclude
	@ManyToOne
	@JoinColumn(name = "posts_id")
	@JsonIgnore
	private PostEntity postDetails;

}
