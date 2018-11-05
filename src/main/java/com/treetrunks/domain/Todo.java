package com.treetrunks.domain;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Table(name="tbl_todos")
@EqualsAndHashCode(of="id")
@ToString(exclude="tags")
public class Todo {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String name;
	private boolean completed;
	
//	@Min(value=0)
//	private int priority;
//	private LocalDateTime deadline;

	@OneToMany(mappedBy="todo",cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private List<Tag> tags;
	
	@CreationTimestamp
	private LocalDateTime regdate;
	
}
