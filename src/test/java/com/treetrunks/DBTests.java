package com.treetrunks;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;

import com.treetrunks.domain.Todo;
import com.treetrunks.domain.Tag;
import com.treetrunks.persistence.TagRepository;
import com.treetrunks.persistence.TodoRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
@Commit
public class DBTests {
	@Autowired
	TodoRepository todoRepo;
	@Autowired
	TagRepository tagRepo;
	
	@Test
	public void insertDummy() {
		String[] names = {"개밥주기","산책하기","노래부르기","귤까먹기","팝핀추기","마카롱사기"};
		boolean tmp = false;
		for(String name:names) {
			Todo todo = new Todo();
			todo.setName(name);
			todo.setCompleted(tmp);
			tmp = !tmp;
			ArrayList<Tag> list = new ArrayList<>();
			for(int i=0;i<3;i++) {
				Tag tag = new Tag();
				tag.setName(todo.getName().charAt(0) + "-tag-" + (i+1));
				tag.setTodo(todo);
				list.add(tag);
			}
			todo.setTags(list);
			todoRepo.save(todo);
		}
//		todoRepo.findAll().forEach(todo->{
//			for(int i=0;i<3;i++) {
//				Tag tag = new Tag();
//				tag.setName(todo.getName().charAt(0) + "-tag-" + (i+1));
//				tagRepo.save(tag);
//			}
//		});
	}
}
