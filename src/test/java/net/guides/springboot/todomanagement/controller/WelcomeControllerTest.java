package net.guides.springboot.todomanagement.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class WelcomeControllerTest {
	@InjectMocks
	WelcomeController welcomeController;
	private MockMvc mockMvc;

	@Before
	public void init() {
		// welcomeController = new WelcomeController();
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(welcomeController).build();
	}
	
	// Test for showWelcomeapage() by mocking the authentication of user using Mockito.
	@Test
	public void testshowWelcomePage() throws Exception {
		UserDetails user = mock(User.class);
		Authentication authentication = mock(Authentication.class);
		SecurityContext securityContext = mock(SecurityContext.class);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(user);
		this.mockMvc.perform(get("/"))
		.andExpect(status().isOk())
		.andExpect(view().name("welcome")); //asserting the returned page
	}

	@Test
	public void testshowWelcomePageNotInstanceOfUserDetails() throws Exception {
		Authentication authentication = mock(Authentication.class);
		SecurityContext securityContext = mock(SecurityContext.class);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(new String());
		this.mockMvc.perform(get("/"))
		.andExpect(status().isOk())
		.andExpect(view().name("welcome"));	//asserting the returned page
	}

}
