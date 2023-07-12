package dtn.fd.com.submission.services;


import dtn.fd.com.submission.dto.requests.SubmissionRequest;
import dtn.fd.com.submission.models.Submission;

import java.util.List;

public interface ISubmissionService {

     List<Submission> getAllSubmission(String lead);
     List<Submission> searchSubmissionByLead(String lead);
     Submission getSubmissionById(String id);

     Submission createSubmission(SubmissionRequest submissionRequest);
     Submission updateSubmissionBySerialId(String serialNumber, SubmissionRequest submissionRequest);
     String deleteSubmissionBySerialId(String serialNumber);

     void deleteAll();
}
