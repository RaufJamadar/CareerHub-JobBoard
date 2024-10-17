package dao;

import model.JobListing;
import java.util.List;

public interface CompanyDAO {
    void postJob(int companyID, String jobTitle, String jobDescription, String jobLocation, double salary, String jobType);
    List<JobListing> getJobs(int companyID);
}
