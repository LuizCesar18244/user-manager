package br.com.usermanager.controller;

import java.net.URI;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
	
	@Autowired
	private MockMvc mock;

	@Test
	void shouldReturnBadCredencials() throws Exception {
		URI uri = new URI("/api/auth");
		String json = "{\"name\": \"invalido\", \"password\": \"00000\"}";
		
		mock.perform(MockMvcRequestBuilders
				.post(uri)
				.content(json)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is(400));	
	}
	
	@Test
	void shouldAllowLogin() throws Exception {
		URI uri = new URI("/api/auth");
		String json = "{\"name\": \"Juca\", \"password\": \"123456\"}";
		
		mock.perform(MockMvcRequestBuilders
				.post(uri)
				.content(json)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is(200));	
	}
	
}
