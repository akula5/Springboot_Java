package dtn.fd.com.submission.services.impl;

import dtn.fd.com.submission.dto.requests.SubmissionRequest;
import dtn.fd.com.submission.mapper.SubmissionModelMapper;
import dtn.fd.com.submission.models.Submission;
import dtn.fd.com.submission.services.ISubmissionService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
public class SubmissionServiceImpl implements ISubmissionService {

    private static final List<Submission> submissionList = new ArrayList<>();

    @Override
    public List<Submission> getAllSubmission(String lead) {
        if(lead==null)
            return submissionList;
        else
            return searchSubmissionByLead(lead);
    }

    @Override
    public Submission getSubmissionById(String serialNumber) {
        return submissionList.stream().filter(submission -> submission.getSerialNumber().equals(serialNumber.trim())).findFirst().orElseThrow();
    }

    @Override
    public List<Submission> searchSubmissionByLead(String lead) {
        Pattern pattern = Pattern.compile(lead, Pattern.CASE_INSENSITIVE);
        return submissionList.stream().filter(submission -> pattern.matcher(submission.getLead()).find()).toList();
    }

    @Override
    public Submission createSubmission(SubmissionRequest submissionRequest) {
        Submission submission = SubmissionModelMapper.convertSubmissionRequestToModel(submissionRequest);
        submission.setSerialNumber(UUID.randomUUID().toString());
        submissionList.add(submission);
        return submission;
    }

    @Override
    public Submission updateSubmissionBySerialId(String serialNumber, SubmissionRequest submissionRequest) {
        for (int i = 0; i < submissionList.size(); i++) {
            if (submissionList.get(i).getSerialNumber().equals(serialNumber)) {
                Submission submission = SubmissionModelMapper.convertSubmissionRequestToModel(submissionRequest);
                submission.setSerialNumber(serialNumber);
                submissionList.set(i, submission);
                return submission;
            }
        }
        throw new RuntimeException(serialNumber + " doesn't exist");
    }

    @Override
    public String deleteSubmissionBySerialId(String serialNumber) {
        for (int i = 0; i < submissionList.size(); i++) {
            if (submissionList.get(i).getSerialNumber().equals(serialNumber)) {
                submissionList.remove(i);
                return "Deleted Successfully";
            }
        }
        throw new RuntimeException(serialNumber + " doesn't exist");
    }

    @Override
    public void deleteAll() {
          submissionList.clear();
    }
}
