package dtn.fd.com.submission.mapper;

import dtn.fd.com.submission.dto.requests.SubmissionRequest;
import dtn.fd.com.submission.dto.response.SubmissionResponse;
import dtn.fd.com.submission.models.Submission;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.util.List;

public class SubmissionModelMapper {


    private static ModelMapper modelMapper;

    static {
        modelMapper=new ModelMapper();
    }
    public static List<SubmissionResponse> convertModelToSubmissionResponse(List<Submission> submissionList){
        TypeToken<List<SubmissionResponse>> typeToken=new TypeToken<>(){};
        return modelMapper.map(submissionList,typeToken.getType());
    }

    public static Submission convertSubmissionRequestToModel(SubmissionRequest submissionRequest){
        return modelMapper.map(submissionRequest,Submission.class);
    }

    public static SubmissionResponse convertModelToSubmissionResponse(Submission submission){
        return modelMapper.map(submission,SubmissionResponse.class);
    }
}
