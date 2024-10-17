package dao;

import jdbc.DatabaseConnection;
import model.Applicant;
import model.Company;
import model.JobApplication;
import model.JobListing;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManagerDAOImpl implements DatabaseManagerDAO {
    public void initializeDatabase() {
        Connection conn = DatabaseConnection.getConnection();
        String createJobsTable = "CREATE TABLE IF NOT EXISTS JobListings (" +
                "JobID INT PRIMARY KEY AUTO_INCREMENT, " +
                "CompanyID INT, " +
                "JobTitle VARCHAR(255), " +
                "JobDescription TEXT, " +
                "JobLocation VARCHAR(255), " +
                "Salary DECIMAL(10, 2), " +
                "JobType VARCHAR(50), " +
                "PostedDate DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                "FOREIGN KEY (CompanyID) REFERENCES Companies(CompanyID)" +
                ");";

        String createCompaniesTable = "CREATE TABLE IF NOT EXISTS Companies (" +
                "CompanyID INT PRIMARY KEY AUTO_INCREMENT, " +
                "CompanyName VARCHAR(255), " +
                "Location VARCHAR(255)" +
                ");";

        String createApplicantsTable = "CREATE TABLE IF NOT EXISTS Applicants (" +
                "ApplicantID INT PRIMARY KEY AUTO_INCREMENT, " +
                "FirstName VARCHAR(255), " +
                "LastName VARCHAR(255), " +
                "Email VARCHAR(255), " +
                "Phone VARCHAR(50), " +
                "Resume TEXT" +
                ");";

        String createJobApplicationsTable = "CREATE TABLE IF NOT EXISTS JobApplications (" +
                "ApplicationID INT PRIMARY KEY AUTO_INCREMENT, " +
                "JobID INT, " +
                "ApplicantID INT, " +
                "ApplicationDate DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                "CoverLetter TEXT, " +
                "FOREIGN KEY (JobID) REFERENCES JobListings(JobID), " +
                "FOREIGN KEY (ApplicantID) REFERENCES Applicants(ApplicantID)" +
                ");";

        try (Statement stmt = conn.createStatement()) {
            // Execute all SQL statements to create tables
            stmt.execute(createJobsTable);
            stmt.execute(createCompaniesTable);
            stmt.execute(createApplicantsTable);
            stmt.execute(createJobApplicationsTable);
            System.out.println("Database initialized successfully.");
        } catch (SQLException e) {
            System.out.println("Error initializing database: " + e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }

    public void insertJobListing(JobListing job) {
        Connection conn = DatabaseConnection.getConnection();
        String sql = "INSERT INTO JobListings (CompanyID, JobTitle, JobDescription, JobLocation, Salary, JobType) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, job.getCompanyID());
            pstmt.setString(2, job.getJobTitle());
            pstmt.setString(3, job.getJobDescription());
            pstmt.setString(4, job.getJobLocation());
            pstmt.setDouble(5, job.getSalary());
            pstmt.setString(6, job.getJobType());
            pstmt.executeUpdate();
            System.out.println("Job listing inserted successfully.");
        } catch (SQLException e) {
            System.out.println("Error inserting job listing: " + e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }


    public void insertCompany(Company company) {
        Connection conn = DatabaseConnection.getConnection();
        String sql = "INSERT INTO Companies (CompanyName, Location) VALUES (?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, company.getCompanyName());
            pstmt.setString(2, company.getLocation());
            pstmt.executeUpdate();
            System.out.println("Company inserted successfully.");
        } catch (SQLException e) {
            System.out.println("Error inserting company: " + e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }

    public void insertApplicant(Applicant applicant) {
        Connection conn = DatabaseConnection.getConnection();
        String sql = "INSERT INTO Applicants (FirstName, LastName, Email, Phone, Resume) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, applicant.getFirstName());
            pstmt.setString(2, applicant.getLastName());
            pstmt.setString(3, applicant.getEmail());
            pstmt.setString(4, applicant.getPhone());
            pstmt.setString(5, applicant.getResume());
            pstmt.executeUpdate();
            System.out.println("Applicant inserted successfully.");
        } catch (SQLException e) {
            System.out.println("Error inserting applicant: " + e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }

    public void insertJobApplication(JobApplication application) {
        Connection conn = DatabaseConnection.getConnection();
        String sql = "INSERT INTO JobApplications (JobID, ApplicantID, CoverLetter) VALUES (?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, application.getJobID());
            pstmt.setInt(2, application.getApplicantID());
            pstmt.setString(3, application.getCoverLetter());
            pstmt.executeUpdate();
            System.out.println("Job application inserted successfully.");
        } catch (SQLException e) {
            System.out.println("Error inserting job application: " + e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }

    public List<JobListing> getJobListings() {
        List<JobListing> jobListings = new ArrayList<>();
        Connection conn = DatabaseConnection.getConnection();
        String sql = "SELECT JobID, CompanyID, JobTitle, JobDescription, JobLocation, Salary, JobType, PostedDate FROM JobListings";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                JobListing job = new JobListing(
                        rs.getInt("JobID"),
                        rs.getInt("CompanyID"),
                        rs.getString("JobTitle"),
                        rs.getString("JobDescription"),
                        rs.getString("JobLocation"),
                        rs.getDouble("Salary"),
                        rs.getString("JobType"),
                        rs.getTimestamp("PostedDate")
                );
                jobListings.add(job);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving job listings: " + e.getMessage());
        } finally {
            closeConnection(conn);
        }
        return jobListings;
    }

    public List<Company> getCompanies() {
        List<Company> companies = new ArrayList<>();
        Connection conn = DatabaseConnection.getConnection();
        String sql = "SELECT CompanyID, CompanyName, Location FROM Companies";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Company company = new Company(
                        rs.getInt("CompanyID"),
                        rs.getString("CompanyName"),
                        rs.getString("Location")
                );
                companies.add(company);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving companies: " + e.getMessage());
        } finally {
            closeConnection(conn);
        }
        return companies;
    }

    public List<Applicant> getApplicants() {
        List<Applicant> applicants = new ArrayList<>();
        Connection conn = DatabaseConnection.getConnection();
        String sql = "SELECT ApplicantID, FirstName, LastName, Email, Phone, Resume FROM Applicants";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Applicant applicant = new Applicant(
                        rs.getInt("ApplicantID"),
                        rs.getString("FirstName"),
                        rs.getString("LastName"),
                        rs.getString("Email"),
                        rs.getString("Phone"),
                        rs.getString("Resume")
                );
                applicants.add(applicant);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving applicants: " + e.getMessage());
        } finally {
            closeConnection(conn);
        }
        return applicants;
    }

    public List<JobApplication> getApplicationsForJob(int jobID) {
        List<JobApplication> jobApplications = new ArrayList<>();
        Connection conn = DatabaseConnection.getConnection();
        String sql = "SELECT ApplicationID, JobID, ApplicantID, ApplicationDate, CoverLetter FROM JobApplications WHERE JobID = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, jobID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                JobApplication application = new JobApplication(
                        rs.getInt("ApplicationID"),
                        rs.getInt("JobID"),
                        rs.getInt("ApplicantID"),
                        rs.getString("ApplicationDate"),
                        rs.getString("CoverLetter")
                );
                jobApplications.add(application);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving job applications: " + e.getMessage());
        } finally {
            closeConnection(conn);
        }
        return jobApplications;
    }

    // Method to search for jobs within a specified salary range
    public void searchJobsBySalaryRange(double minSalary, double maxSalary) {
        Connection conn = DatabaseConnection.getConnection();
        String sql = "SELECT j.JobTitle, c.CompanyName, j.Salary " +
                "FROM JobListings j " +
                "JOIN Companies c ON j.CompanyID = c.CompanyID " +
                "WHERE j.Salary BETWEEN ? AND ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // Set the minimum and maximum salary in the query
            pstmt.setDouble(1, minSalary);
            pstmt.setDouble(2, maxSalary);

            // Execute the query and get the results
            ResultSet rs = pstmt.executeQuery();

            // Check if there are any results
            boolean hasResults = false;
            System.out.println("Job Listings with salary between " + minSalary + " and " + maxSalary + ":");
            while (rs.next()) {
                hasResults = true;
                // Retrieve job title, company name, and salary
                String jobTitle = rs.getString("JobTitle");
                String companyName = rs.getString("CompanyName");
                double salary = rs.getDouble("Salary");

                // Print the job details
                System.out.println("Job Title: " + jobTitle + ", Company: " + companyName + ", Salary: " + salary);
            }

            if (!hasResults) {
                System.out.println("No job listings found within the specified salary range.");
            }

        } catch (SQLException e) {
            // Handle any SQL exceptions
            System.out.println("Error while searching for job listings: " + e.getMessage());
        } finally {
            // Close the database connection
            closeConnection(conn);
        }
    }

    // Utility method to close the database connection
    private void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("Error while closing connection: " + e.getMessage());
            }
        }
    }
}
