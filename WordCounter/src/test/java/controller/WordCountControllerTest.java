package controller;

import app.WordCounterApp;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = WordCounterApp.class)
@WebAppConfiguration
public class WordCountControllerTest {

    private MockMvc mvc;

    @Autowired
    private WordCountController wordCountController;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(wordCountController)
                .build();
    }

    @Test
    public void getCountIfAddWordAndGetCountForIt() throws Exception {

        mvc.perform(
                MockMvcRequestBuilders.post("/word/flower")
        );
        MockHttpServletResponse response = mvc.perform(
                MockMvcRequestBuilders.get("/word/flower")
        )
                .andReturn().getResponse();
        Assert.assertEquals(200, response.getStatus());
        Assert.assertEquals("1", response.getContentAsString());
    }

    @Test
    public void getZeroCountIfWordIsNotAdded() throws Exception {

        mvc.perform(
                MockMvcRequestBuilders.post("/word/flowerToAdd")
        );
        MockHttpServletResponse response = mvc.perform(
                MockMvcRequestBuilders.get("/word/flowerNotAdded")
        )
                .andReturn().getResponse();
        Assert.assertEquals(200, response.getStatus());
        Assert.assertEquals("0", response.getContentAsString());
    }


    @Test
    public void getTrueIfWordAdded() throws Exception {
        MockHttpServletResponse response = mvc.perform(
                MockMvcRequestBuilders.post("/word/jasmine")
                        )
                .andReturn().getResponse();
        Assert.assertEquals(200, response.getStatus());
        Assert.assertEquals("true", response.getContentAsString());
    }

    @Test
    public void getFalseIfWordNotAdded() throws Exception {
        MockHttpServletResponse response = mvc.perform(
                MockMvcRequestBuilders.post("/word/flower1")
        )
                .andReturn().getResponse();
        Assert.assertEquals(200, response.getStatus());
        Assert.assertEquals("false", response.getContentAsString());
    }
}