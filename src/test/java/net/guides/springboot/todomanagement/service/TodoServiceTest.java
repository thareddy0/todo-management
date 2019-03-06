package net.guides.springboot.todomanagement.service;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;

import net.guides.springboot.todomanagement.model.Todo;
import net.guides.springboot.todomanagement.repository.TodoRepository;

public class TodoServiceTest {
	@InjectMocks
	TodoService todoService = new TodoService();
	@Mock
	TodoRepository todoRepository;
	
	@SuppressWarnings("unused")
	private MockMvc mockMvc;
	@Before
	public void init() {		
		
		MockitoAnnotations.initMocks(this);
		}
	@Test
	public void testgetTodosByUser() {
		//TodoRepository todoRepository = mock(TodoRepository.class);
		List<Todo> listOfTodo = new ArrayList<Todo>();
		 listOfTodo.add(getTodo(1));
		Mockito.when(todoRepository.findByUserName(any(String.class))).thenReturn(listOfTodo);		
		//TodoService todoService = new TodoService (todoRepository);
				
		List<Todo> result = todoService.getTodosByUser("admin");
		assertEquals(result.get(0).getId(),1);
		assertEquals(result.get(0).getDescription(),"Task 1");
	}
	@Test
	public void testgetTodoById() {
		Optional<Todo> listOfTodo = Optional.of(getTodo(1));
				Mockito.when(todoRepository.findById(any(Long.class))).thenReturn(listOfTodo);		
				Optional<Todo> result = todoService.getTodoById(1);
				assertEquals(result.isPresent(),true);
				assertTrue(result.equals(listOfTodo));				
	}
	@Test
	public void testupdateTodo() {
		Mockito.when(todoRepository.save(any(Todo.class))).thenReturn(getTodo(1));
		todoService.updateTodo(getTodo(1));		
		
	}
	@Test
	public void testaddTodo() {
		Date date = new Date(1-2-2019);		
		Mockito.when(todoRepository.save(any(Todo.class))).thenReturn(getTodo(1));
		todoService.addTodo("testTask", "This is a test task for todo",date , true);
		
	}
	@Test
	public void testdeleteTodo() {
		Optional<Todo> listOfTodo = Optional.of(getTodo(1));
		Mockito.when(todoRepository.findById(any(Long.class))).thenReturn(listOfTodo);		
		todoService.deleteTodo(1);
		
	}
	@Test
	public void testdeleteTodoNotPresent() {
		Optional<Todo> listOfTodo = Optional.empty();
		Mockito.when(todoRepository.findById(any(Long.class))).thenReturn(listOfTodo);		
		todoService.deleteTodo(1);
		
	}
	@Test
	public void testsaveTodo() {
		Mockito.when(todoRepository.save(any(Todo.class))).thenReturn(getTodo(1));
		todoService.saveTodo(getTodo(1));
	}
	private Todo getTodo(long id) {
		// TODO Auto-generated method stub
		Todo todo = new Todo();
		todo.setId(id);
		todo.setDescription("Task "+id);
		todo.setTargetDate(new Date());
		todo.setUserName("admin");
		
		return todo;
	}
}
