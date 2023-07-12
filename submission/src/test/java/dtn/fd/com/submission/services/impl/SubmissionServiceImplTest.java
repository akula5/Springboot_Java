package dtn.fd.com.submission.services.impl;

import dtn.fd.com.submission.dto.requests.SubmissionRequest;
import dtn.fd.com.submission.enums.Status;
import dtn.fd.com.submission.models.Submission;
import dtn.fd.com.submission.services.ISubmissionService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

class SubmissionServiceImplTest {


    private ISubmissionService submissionService;

   private SubmissionRequest submissionRequest = new SubmissionRequest();

    @BeforeEach
    void setUp() {
        // Submission Request
        submissionRequest.setName("raj");
        submissionRequest.setRate(90);
        submissionRequest.setVendor("TCS");
        submissionRequest.setImplementation("Wipro");
        submissionRequest.setTech("Java");
        submissionRequest.setLead("Sagar");
        submissionRequest.setVendorEmail("raj@gmail.com");
        submissionRequest.setStatus(Status.OFFERED);

        submissionService=new SubmissionServiceImpl();
    }

    @Test
    void getAllSubmission() {
        List<Submission> submissionListExcepted = new ArrayList<>();

//        1. Test case
        List<Submission> submissionListActual=submissionService.getAllSubmission(null);
        Assertions.assertIterableEquals(submissionListExcepted,submissionListActual);

//        2. Test case
        Submission submissionExpected=submissionService.createSubmission(submissionRequest);
        Assertions.assertIterableEquals(Arrays.asList(submissionExpected),submissionService.getAllSubmission(null));



    }

    @Test
    void getSubmissionById() {
//       1. Test case
        Submission submissionExpected=submissionService.createSubmission(submissionRequest);
        Assertions.assertEquals(submissionExpected,submissionService.getSubmissionById(submissionExpected.getSerialNumber()));
    }

    @Test
    void searchSubmissionByLead() {
//       1. Test case
        Submission submissionExpected=submissionService.createSubmission(submissionRequest);
        Assertions.assertEquals(Arrays.asList(submissionExpected),submissionService.searchSubmissionByLead("sa"));
    }

    @Test
    void createSubmission() {
        Submission submissionActual=submissionService.createSubmission(submissionRequest);
        Assertions.assertEquals(submissionRequest.getName(),submissionActual.getName());
        Assertions.assertEquals(submissionRequest.getRate(),submissionActual.getRate());
        Assertions.assertEquals(submissionRequest.getVendor(),submissionActual.getVendor());
        Assertions.assertEquals(submissionRequest.getImplementation(),submissionActual.getImplementation());
        Assertions.assertEquals(submissionRequest.getTech(),submissionActual.getTech());
        Assertions.assertEquals(submissionRequest.getLead(),submissionActual.getLead());
        Assertions.assertEquals(submissionRequest.getVendorEmail(),submissionActual.getVendorEmail());
        Assertions.assertEquals(submissionRequest.getStatus(),submissionActual.getStatus());

    }

    @Test
    void updateSubmissionBySerialId() {
//       1. Test case
        Submission submissionCreated=submissionService.createSubmission(submissionRequest);

        SubmissionRequest updateSubmissionRequest=new SubmissionRequest();
        updateSubmissionRequest.setName("dnt");
        updateSubmissionRequest.setRate(100);
        updateSubmissionRequest.setVendor("DNT");
        updateSubmissionRequest.setImplementation("DNT Group");
        updateSubmissionRequest.setTech("Java");
        updateSubmissionRequest.setLead("raj");
        updateSubmissionRequest.setVendorEmail("raj.info@gmail.com");
        updateSubmissionRequest.setStatus(Status.OFFERED);

        Submission submissionActual=submissionService.updateSubmissionBySerialId(submissionCreated.getSerialNumber(),updateSubmissionRequest);
        Assertions.assertEquals(updateSubmissionRequest.getName(),submissionActual.getName());
        Assertions.assertEquals(updateSubmissionRequest.getRate(),submissionActual.getRate());
        Assertions.assertEquals(updateSubmissionRequest.getVendor(),submissionActual.getVendor());
        Assertions.assertEquals(updateSubmissionRequest.getImplementation(),submissionActual.getImplementation());
        Assertions.assertEquals(updateSubmissionRequest.getTech(),submissionActual.getTech());
        Assertions.assertEquals(updateSubmissionRequest.getLead(),submissionActual.getLead());
        Assertions.assertEquals(updateSubmissionRequest.getVendorEmail(),submissionActual.getVendorEmail());
        Assertions.assertEquals(updateSubmissionRequest.getStatus(),submissionActual.getStatus());
    }

    @Test
    void deleteSubmissionBySerialId() {
//       1. Test case
        Submission submissionCreated=submissionService.createSubmission(submissionRequest);
        String expectedMessage="Deleted Successfully";
        String actualMessage=submissionService.deleteSubmissionBySerialId(submissionCreated.getSerialNumber());
        Assertions.assertEquals(expectedMessage,actualMessage);

    }

    @AfterEach
    void tearDown() {
        submissionService.deleteAll();
    }
}