package dtn.fd.com.submission.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dtn.fd.com.submission.dto.requests.SubmissionRequest;
import dtn.fd.com.submission.dto.response.SubmissionResponse;
import dtn.fd.com.submission.enums.Status;
import dtn.fd.com.submission.models.Submission;
import dtn.fd.com.submission.services.ISubmissionService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

//https://github.com/kishanjavatrainer/TicketBookingRestControllerJunitTesting/blob/master/TicketBookingRestControllerJunitTesting/src/test/java/com/infotech/book/ticket/app/controller/TicketBookingControllerTest.java

@WebMvcTest(SubmissionController.class)
class SubmissionControllerTest {

    @MockBean
    ISubmissionService submissionService;

    @Autowired
    private MockMvc mockMvc;

    private SubmissionRequest submissionRequest;
    private Submission submission;

    private SubmissionResponse submissionResponse;


    @BeforeEach
    void setUp() {
        ModelMapper modelMapper=new ModelMapper();
        submissionRequest = new SubmissionRequest();
        submissionRequest.setName("niha");
        submissionRequest.setRate(90);
        submissionRequest.setVendor("TCS");
        submissionRequest.setImplementation("Wipro");
        submissionRequest.setTech("Java");
        submissionRequest.setLead("Sagar");
        submissionRequest.setVendorEmail("niha@gmail.com");
        submissionRequest.setStatus(Status.OFFERED);

        submission = new Submission();
        submission=modelMapper.map(submissionRequest,Submission.class);
        submission.setSerialNumber(UUID.randomUUID().toString());

        submissionResponse=modelMapper.map(submission,SubmissionResponse.class);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getSubmissionList() throws Exception {
        when(submissionService.getAllSubmission(null)).thenReturn(new ArrayList<>());

        String expectedInJson = this.mapToJson(new ArrayList<>());

        String URI = "/submission";


        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(URI)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        String outputInJson = response.getContentAsString();

        assertThat(outputInJson).isEqualTo(expectedInJson);
        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void getSubmissionBySerialNumber() throws Exception {

        String serialId=submission.getSerialNumber();
        when(submissionService.getSubmissionById(serialId)).thenReturn(submission);

        String expectedInJson = this.mapToJson(submissionResponse);

        String URI = "/submission/"+serialId;


        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(URI)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        String outputInJson = response.getContentAsString();

        assertThat(outputInJson).isEqualTo(expectedInJson);
        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void createSubmission() throws Exception {

        when(submissionService.createSubmission(submissionRequest)).thenReturn(submission);

        String inputInJson = this.mapToJson(submissionRequest);
        String expectedInJson = this.mapToJson(submissionResponse);

        String URI = "/submission";


        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(URI)
                .accept(MediaType.APPLICATION_JSON).content(inputInJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        String outputInJson = response.getContentAsString();

        assertThat(outputInJson).isEqualTo(expectedInJson);
        Assertions.assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    @Test
    void updateSubmissionBySerialId() throws Exception{
        String serialId=submission.getSerialNumber();
        when(submissionService.updateSubmissionBySerialId(serialId,submissionRequest)).thenReturn(submission);

        String inputInJson = this.mapToJson(submissionRequest);
        String expectedInJson = this.mapToJson(submissionResponse);

        String URI = "/submission/"+serialId;


        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .patch(URI)
                .accept(MediaType.APPLICATION_JSON).content(inputInJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        String outputInJson = response.getContentAsString();

        assertThat(outputInJson).isEqualTo(expectedInJson);
        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());

    }

    @Test
    void deleteSubmissionBySerialId() throws Exception {
        String serialNumber=UUID.randomUUID().toString();
        when(submissionService.deleteSubmissionBySerialId(serialNumber)).thenReturn("Deleted Successfully");

        String expected ="Deleted Successfully";

        String URI = "/submission/"+serialNumber;


        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete(URI)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        String outputInJson = response.getContentAsString();

        assertThat(outputInJson).isEqualTo(expected);
        Assertions.assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());

    }

    /**
     * Maps an Object into a JSON String. Uses a Jackson ObjectMapper.
     */
    private String mapToJson(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }
}