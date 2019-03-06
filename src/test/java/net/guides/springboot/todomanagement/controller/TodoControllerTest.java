package net.guides.springboot.todomanagement.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import net.guides.springboot.todomanagement.model.Todo;
import net.guides.springboot.todomanagement.service.TodoService;

public class TodoControllerTest {
	@InjectMocks
	TodoController todoController;
	@Mock
	TodoService todoService;
	private MockMvc mockMvc;

	@Before
	public void init() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		MockitoAnnotations.initMocks(this);
		resolver.setPrefix("/WEB-INF/jsp/");
		resolver.setSuffix(".jsp");
		mockMvc = MockMvcBuilders.standaloneSetup(todoController).setViewResolvers(resolver).build();
	}
	// Testing showTodos() my mocking the authentication of user using Mockito.
	@Test
	public void testshowTodos() throws Exception {
		UserDetails user = mock(User.class);
		Authentication authentication = mock(Authentication.class);
		SecurityContext securityContext = mock(SecurityContext.class);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(user);
		List<Todo> todos = new ArrayList<Todo>();
		todos.add(getTodo(1));
		Mockito.when(todoService.getTodosByUser(any(String.class))).thenReturn(todos);
		this.mockMvc.perform(get("/list-todos"))
			.andExpect(status().isOk())
			.andExpect(view().name("list-todos"));  //assert the page returned
		
	}

	@Test
	public void testshowTodosNotInstanceOfUserDetails() throws Exception {
		Authentication authentication = mock(Authentication.class);
		SecurityContext securityContext = mock(SecurityContext.class);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(new String());
		List<Todo> todos = new ArrayList<Todo>();
		todos.add(getTodo(1));
		Mockito.when(todoService.getTodosByUser(any(String.class))).thenReturn(todos);
		this.mockMvc.perform(get("/list-todos")).andExpect(status().isOk())
			.andExpect(view().name("list-todos"));

	}

	@Test
	public void testshowAddTodoPage() throws Exception {
		this.mockMvc.perform(get("/add-todo"))
			.andExpect(status().isOk())
			.andExpect(view().name("todo"));

	}

	@Test
	public void testdeleteTodo() throws Exception {
		Mockito.doNothing().when(todoService).deleteTodo(any(long.class));
		this.mockMvc.perform(get("/delete-todo").param("id", "1"))
				.andExpect(status().isFound())		// asserting for redirection
				.andExpect(redirectedUrl("/list-todos"));	// assert the redirected page returned

	}

	@Test
	public void testshowUpdateTodoPage() throws Exception {
		Optional<Todo> todo = Optional.of(getTodo(1));
		Mockito.when(todoService.getTodoById(any(Long.class))).thenReturn(todo);
		this.mockMvc.perform(get("/update-todo").param("id", "1"))
				.andExpect(status().isOk())
				.andExpect(view().name("todo"));
	}

	private Todo getTodo(long id) {
		Todo todo = new Todo();
		todo.setId(id);
		todo.setDescription("Task " + id);

		return todo;
	}

}
