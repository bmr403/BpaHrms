package com.bpa.hrms.testutil;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.bpa.hrms.controller.UserController;
import com.bpa.hrms.service.UserService;

import static org.hamcrest.Matchers.*;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext-test.xml"})
public class LoginServiceTest {
	
	protected final Log logger = LogFactory.getLog(LoginServiceTest.class);
	
	 	@InjectMocks
	    UserController userController;
	 	
	    @Mock
	    UserService userService;
	    
	    
	    @Autowired
	    private WebApplicationContext webApplicationContext;
	    
	    private MockMvc mockMvc;
	    
	    String username = "satya@bpatech.com";
	    String password = "12345678";

	    @Before
	    public void setUp() throws Exception {
	    	logger.info("### In Set up before init");
	        MockitoAnnotations.initMocks(this);
	      this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	        
	        logger.info("### In Set up after standalone set up ");
	    }

	    @Test
	    public void testLogin() throws Exception {
	    	logger.info("### In testLogin before when ");
	    	
	    mockMvc.perform(post("/user/loginVerification")
            .param("userEmail", username)
            .param("password", password))
            .andExpect(status().isOk())             // 200 Status
            .andExpect(jsonPath("$.data", not("")))
            .andExpect(jsonPath("$.data.userEmail", is("satya@bpatech.com")))
            .andExpect(jsonPath("$.data.password",is("12345678")))
	    	.andExpect(jsonPath("$.success",is(true)))
            ; 
	    	logger.info("### In test Login after perform method ");
	    	
	    }
	    
	    @Test
	    public void testUserId() throws Exception {
	    	logger.info("### In test get User before perform method ");
	    	mockMvc.perform(post("/user/userId")
	    	.param("userId", "2"))
            .andExpect(status().isOk())  // 200 Status
            ;
	    	logger.info("### In test get User after perform method ");
	    }

}
