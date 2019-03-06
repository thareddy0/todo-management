package net.guides.springboot.todomanagement.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class LogoutControllerTest {
	@InjectMocks
	LogoutController logoutController;
	private MockMvc mockMvc;
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(logoutController).build();
	}
	
	//Test case for logout() function.
	@Test
	public void testlogout() throws Exception {
		Authentication authentication = mock(Authentication.class);
	    SecurityContext securityContext = mock(SecurityContext.class);
	    when(securityContext.getAuthentication()).thenReturn(authentication);
	    SecurityContextHolder.setContext(securityContext);
	    when(SecurityContextHolder.getContext().getAuthentication()).thenReturn(authentication);
	    this.mockMvc.perform(get("/logout"))
			.andExpect(status().isFound())	//checking for redirection
			.andExpect(redirectedUrl("/")); //checking if redirected back to login page
	}
	@Test
	public void testlogoutNotAuthenticatedUser() throws Exception {
	    SecurityContext securityContext = mock(SecurityContext.class);
	    when(securityContext.getAuthentication()).thenReturn(null);
	    SecurityContextHolder.setContext(securityContext);
	    when(SecurityContextHolder.getContext().getAuthentication()).thenReturn(null);	        
	    this.mockMvc.perform(get("/logout"))
	    	.andExpect(status().isFound())
	        .andExpect(redirectedUrl("/")); //checking if redirected back to login page
	}

}
