package net.guides.springboot.todomanagement.controller;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
public class ErrorControllerTest {
	@InjectMocks
	ErrorController errorController;
	private MockMvc mockMvc;
	@Before
	public void init() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		MockitoAnnotations.initMocks(this);
		resolver.setPrefix("/WEB-INF/jsp/");
		resolver.setSuffix(".jsp");
		mockMvc = MockMvcBuilders.standaloneSetup(errorController).setViewResolvers(resolver).build();
	}
	@Test
	public void testhandleException() throws Exception {
		this.mockMvc.perform(get("/exceptionHandler")).andExpect(status().isOk());
	}

}
