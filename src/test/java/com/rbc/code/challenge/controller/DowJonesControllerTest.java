package com.rbc.code.challenge.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.File;
import java.io.FileInputStream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class DowJonesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testAddDowJones() throws Exception {
        String requestJson = "{\"quarter\":\"1Q2011\",\"stock\":\"AA\",\"date\":\"1/7/2017\",\"open\":\"12.4\",\"high\":\"12.4\",\"low\":\"12.17\",\"close\":\"12.28\",\"volume\":\"13761\",\"percent_change_price\":\"1.380\", \"percent_change_volume_over_last_wk\":\"-4.428\", \"previous_weeks_volume\":\"15128\", \"next_weeks_open\":\"12.35\", \"next_weeks_close\":\"11.98\", \"percent_change_next_weeks_price\":\"-3.000\", \"days_to_next_dividend\":\"19\", \"percent_return_next_dividend\":\"0.785\"}";

        mockMvc.perform(post("/api/dowjones/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated());
    }

    @Test
    public void testAddRecordWithInvalidDate() throws Exception {
        String requestJson = "{\"quarter\":\"1Q2011\",\"stock\":\"AA\",\"date\":\"1/71/2017\",\"open\":\"12.4\",\"high\":\"12.4\",\"low\":\"12.17\",\"close\":\"12.28\",\"volume\":\"13761\",\"percent_change_price\":\"1.380\", \"percent_change_volume_over_last_wk\":\"-4.428\", \"previous_weeks_volume\":\"15128\", \"next_weeks_open\":\"12.35\", \"next_weeks_close\":\"11.98\", \"percent_change_next_weeks_price\":\"-3.000\", \"days_to_next_dividend\":\"19\", \"percent_return_next_dividend\":\"0.785\"}";

        mockMvc.perform(post("/api/dowjones/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testQueryDowJones() throws Exception {
        String requestJson = "{\"quarter\":\"1Q2011\",\"stock\":\"AA\",\"date\":\"1/7/2017\",\"open\":\"12.4\",\"high\":\"12.4\",\"low\":\"12.17\",\"close\":\"12.28\",\"volume\":\"13761\",\"percent_change_price\":\"1.380\", \"percent_change_volume_over_last_wk\":\"-4.428\", \"previous_weeks_volume\":\"15128\", \"next_weeks_open\":\"12.35\", \"next_weeks_close\":\"11.98\", \"percent_change_next_weeks_price\":\"-3.000\", \"days_to_next_dividend\":\"19\", \"percent_return_next_dividend\":\"0.785\"}";

        mockMvc.perform(post("/api/dowjones/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson));
        mockMvc.perform(get("/api/dowjones/query?stock=AA"))
                .andExpect(status().isOk());
    }

    @Test
    public void testUploadDowJones() throws Exception {
        String requestJson = "[{\"quarter\":\"1Q2011\",\"stock\":\"AA\",\"date\":\"1/17/2012\",\"open\":\"12.4\",\"high\":\"12.4\",\"low\":\"12.17\",\"close\":\"12.28\",\"volume\":\"13761\",\"percent_change_price\":\"1.380\", \"percent_change_volume_over_last_wk\":\"-4.428\", \"previous_weeks_volume\":\"15128\", \"next_weeks_open\":\"12.35\", \"next_weeks_close\":\"11.98\", \"percent_change_next_weeks_price\":\"-3.000\", \"days_to_next_dividend\":\"19\", \"percent_return_next_dividend\":\"0.785\"}]";

        mockMvc.perform(post("/api/dowjones/upload")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated());
    }

    @Test
    public void testUploadFile() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("dow_jones_test.csv").getFile());

        MockMultipartFile multipartFile = new MockMultipartFile("file", "dow_jones_test.csv", "text/csv", new FileInputStream(file));

        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/dowjones/upload-file")
                        .file(multipartFile))
                .andExpect(status().isOk());
    }

    @Test
    public void testQueryNotFound() throws Exception {
        mockMvc.perform(get("/api/dowjones/query?stock=ZZZZZZZZZZZZZ"))
                .andExpect(status().isNotFound());
    }
}