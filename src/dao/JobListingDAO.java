package dao;

import model.Applicant;

import java.util.List;

public interface JobListingDAO {
    void apply(int jobID, int applicantID, String coverLetter);
    List<Applicant> getApplicants(int jobID);
}
