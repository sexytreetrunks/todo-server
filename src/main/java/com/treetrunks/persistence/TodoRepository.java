package com.treetrunks.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.treetrunks.domain.Todo;

public interface TodoRepository extends CrudRepository<Todo, Integer>{
	//@Query(value="select * from tbl_todos where id in (select distinct todoid from tbl_tags where name like %?%)", nativeQuery=true)
	//public List<Todo> findByTagName(String tagName);
}
