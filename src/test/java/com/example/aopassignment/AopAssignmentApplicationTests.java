package com.example.aopassignment;

import com.example.aopassignment.model.Student;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AopAssignmentApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void testAddStudent() throws Exception {
		Student student = new Student("John Doe", "john.doe@example.com", "Computer Science");

		ResultActions result = mockMvc.perform(post("/students")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(student)));

		result.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("John Doe"))
				.andExpect(jsonPath("$.email").value("john.doe@example.com"))
				.andExpect(jsonPath("$.department").value("Computer Science"));
	}

	@Test
	void testGetAllStudents() throws Exception {
		mockMvc.perform(get("/students"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}

	@Test
	void testGetStudentById() throws Exception {
		Student student = new Student("Jane Doe", "jane.doe@example.com", "Physics");
		ResultActions createResult = mockMvc.perform(post("/students")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(student)));
		String response = createResult.andReturn().getResponse().getContentAsString();
		Integer id = objectMapper.readValue(response, Student.class).getId();

		mockMvc.perform(get("/students/{id}", id))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("Jane Doe"))
				.andExpect(jsonPath("$.email").value("jane.doe@example.com"))
				.andExpect(jsonPath("$.department").value("Physics"));
	}

	@Test
	void testUpdateStudent() throws Exception {
		Student student = new Student("Test Student", "test@example.com", "History");
		ResultActions createResult = mockMvc.perform(post("/students")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(student)));
		String response = createResult.andReturn().getResponse().getContentAsString();
		Integer id = objectMapper.readValue(response, Student.class).getId();

		Student updatedStudent = new Student("Updated Student", "updated@example.com", "Math");

		mockMvc.perform(put("/students/{id}", id)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(updatedStudent)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("Updated Student"))
				.andExpect(jsonPath("$.email").value("updated@example.com"))
				.andExpect(jsonPath("$.department").value("Math"));
	}

	@Test
	void testDeleteStudent() throws Exception {
		Student student = new Student("To Be Deleted", "delete@example.com", "Chemistry");
		ResultActions createResult = mockMvc.perform(post("/students")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(student)));
		String response = createResult.andReturn().getResponse().getContentAsString();
		Integer id = objectMapper.readValue(response, Student.class).getId();

		mockMvc.perform(delete("/students/{id}", id))
				.andExpect(status().isNoContent());

		mockMvc.perform(get("/students/{id}", id))
				.andExpect(status().isNotFound());
	}

}
