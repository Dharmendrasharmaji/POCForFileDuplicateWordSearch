package com.one.filesearch;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.one.filesearch.service.MyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MyService service;


    @Test
    public void getResponseStatusOk() throws Exception {

        String fileName = "myfiles.txt";

        HashMap<String, Integer> map = new HashMap<>();
        map.put("I",4);
        map.put("You",3);
        List<Map.Entry<String, Integer>> entries = new ArrayList<>(map.entrySet());

        when(service.getDuplicateWords(anyString())).thenReturn(entries);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/{fileName}", fileName))
                .andExpect(status().isOk()).andDo(print());

    }
}
