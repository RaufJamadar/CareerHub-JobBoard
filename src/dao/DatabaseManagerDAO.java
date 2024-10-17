package dao;

import model.*;

import java.util.List;

public interface DatabaseManagerDAO {

    // Method to initialize the database schema and tables
    void initializeDatabase();

    // Method to insert a job listing into the database
    void insertJobListing(JobListing job);

    // Method to insert a company into the database
    void insertCompany(Company company);

    // Method to insert an applicant into the database
    void insertApplicant(Applicant applicant);

    // Method to insert a job application into the database
    void insertJobApplication(JobApplication application);

    // Method to retrieve all job listings
    List<JobListing> getJobListings();

    // Method to retrieve all companies
    List<Company> getCompanies();

    // Method to retrieve all applicants
    List<Applicant> getApplicants();

    // Method to retrieve all applications for a specific job listing
    List<JobApplication> getApplicationsForJob(int jobID);

    // Method to search for jobs within a specified salary range
    void searchJobsBySalaryRange(double minSalary, double maxSalary);
}

