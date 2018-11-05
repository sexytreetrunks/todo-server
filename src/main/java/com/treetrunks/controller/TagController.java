package com.treetrunks.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.treetrunks.domain.Tag;
import com.treetrunks.domain.Todo;
import com.treetrunks.persistence.TagRepository;

@CrossOrigin(origins="*")
@RestController
@RequestMapping(value="/tags")
public class TagController {
	@Autowired
	private TagRepository tagRepo;
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public Tag insert(@RequestBody Map<String, Object> payload) { //{todo_id:id, name:tagName}
		Todo todo = new Todo();
		todo.setId((int)payload.get("todo_id"));
		
		Tag tag = new Tag();
		tag.setTodo(todo);
		tag.setName((String)payload.get("name"));
		
		return tagRepo.save(tag);
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable int id) {
		tagRepo.deleteById(id);
	}
}
