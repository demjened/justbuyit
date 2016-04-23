package com.justbuyit.controller;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.justbuyit.config.MvcConfig;
import com.justbuyit.config.TestConfig;

/**
 * MVC tests for the /status path.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextHierarchy({
    @ContextConfiguration(classes = TestConfig.class),
    @ContextConfiguration(classes = MvcConfig.class)
})
public class StatusControllerTest {

    @Autowired
    private WebApplicationContext context;
    
    @Test
    public void testStatus() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        RequestBuilder builder = MockMvcRequestBuilders.get("/status");
        MvcResult result = mockMvc.perform(builder)
                                  .andExpect(MockMvcResultMatchers.status().isOk())
                                  .andReturn();
        
        // parse response HTML
        String html = result.getResponse().getContentAsString();
        
        // TODO: implement a better way of parsing the result, eg. with Jsoup
        Assert.assertTrue(html.contains("COMPANY1"));
        Assert.assertTrue(html.contains("USER1"));
        Assert.assertTrue(html.contains("USER2"));
    }
    
}
