package com.treetrunks.controller;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.treetrunks.domain.Tag;
import com.treetrunks.domain.Todo;
import com.treetrunks.persistence.TagRepository;
import com.treetrunks.persistence.TodoRepository;

@CrossOrigin(origins="*") //http://heowc.tistory.com/73 http://ithub.tistory.com/63 -- has been blocked by CORS policy: No 'Access-Control-Allow-Origin' header is present on the requested resource. spring boo
@RestController
@RequestMapping(value="/todos")
public class TodoController {
	@Autowired
	private TodoRepository todoRepo;
	
	@Autowired
	private TagRepository tagRepo;
	
	//태그 여러개로 검색되도록하려면 query dsl을 적용해야할지도...?
	@GetMapping
	public Iterable<Todo> readAllOrSearch(@RequestParam(value="tagName", required=false) String tagName) {
//		if(tagName !=null)
//			return todoRepo.findByTagName(tagName);
//		else
			return todoRepo.findAll();
	}
	
	@GetMapping(value="/{id}")
	public Todo read(@PathVariable int id) {
		return todoRepo.findById(id).orElse(null);
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public Todo create(@RequestBody Map<String, Object> payload) {
		Todo todo = new Todo();
		todo.setName((String)payload.get("name"));
		todo.setCompleted((boolean)payload.get("completed"));
		ArrayList<String> tagnames = (ArrayList<String>)payload.get("tags");
		ArrayList<Tag> tags = new ArrayList<>();
		for(String tagname:tagnames) {
			Tag tag = new Tag();
			tag.setName(tagname);
			tag.setTodo(todo);
			tags.add(tag);
		}
		todo.setTags(tags);
		return todoRepo.save(todo);
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PutMapping(value="/{id}")
	public void update(@PathVariable int id, @RequestBody Map<String,Object> payload) {
		Todo todo = todoRepo.findById(id).get();
		Object name = payload.get("name");
		Object completed = payload.get("completed");
		
		todo.setName(name.equals(null)? todo.getName():(String)name);
		todo.setCompleted(completed.equals(null)? todo.isCompleted():(boolean)completed);
		
		todoRepo.save(todo);
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping(value="/{id}")
	public void delete(@PathVariable int id) {
		todoRepo.deleteById(id);
	}
	
}
