package com.interventure.task;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@ActiveProfiles("test")
@EmbeddedKafka(controlledShutdown = true)
@AutoConfigureMockMvc
class ProductServiceApplicationTests {
    
    @Autowired
	private MockMvc mockMvc;
    
//    @Test
//	void givenCustomer_whenPostCustomer_thenStatus200() throws Exception {
//		String request = "{\"name\": \"Product\",\"price\": 3}";
//		mockMvc.perform(post("/customer")
//						.contentType(MediaType.APPLICATION_JSON)
//						.content(request))
//				.andDo(print())
//				.andExpect(status().isOk())
//				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
//				.andExpect(jsonPath("$.customerId").value("1"));
//	}

//	@Test
//	void contextLoads() {
//	}

}
