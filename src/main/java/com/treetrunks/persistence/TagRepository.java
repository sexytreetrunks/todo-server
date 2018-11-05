package com.treetrunks.persistence;

import org.springframework.data.repository.CrudRepository;

import com.treetrunks.domain.Tag;

public interface TagRepository extends CrudRepository<Tag, Integer>{
	//delete from tbl_tags where todo_id=todo_id and name=tagname
	public Tag findByTodoIdAndName(int todo_id, String tagName);
}