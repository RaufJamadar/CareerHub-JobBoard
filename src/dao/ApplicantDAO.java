package dao;

import model.Applicant;

public interface ApplicantDAO {
    void createProfile(Applicant applicant);
    void applyForJob(int jobID, int applicantID, String coverLetter);
}
