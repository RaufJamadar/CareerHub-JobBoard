package dao;

import jdbc.DatabaseConnection;
import model.JobListing;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompanyDAOImpl implements CompanyDAO {

    @Override
    public void postJob(int companyID, String jobTitle, String jobDescription, String jobLocation, double salary, String jobType) {
        Connection conn = DatabaseConnection.getConnection();
        String sql = "INSERT INTO JobListings (CompanyID, JobTitle, JobDescription, JobLocation, Salary, JobType) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, companyID);
            pstmt.setString(2, jobTitle);
            pstmt.setString(3, jobDescription);
            pstmt.setString(4, jobLocation);
            pstmt.setDouble(5, salary);
            pstmt.setString(6, jobType);
            pstmt.executeUpdate();
            System.out.println("Job posted successfully: " + jobTitle);
        } catch (SQLException e) {
            System.out.println("Error while posting the job: " + e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }

    @Override
    public List<JobListing> getJobs(int companyID) {
        List<JobListing> jobListings = new ArrayList<>();
        Connection conn = DatabaseConnection.getConnection();
        String sql = "SELECT JobID, JobTitle, JobDescription, JobLocation, Salary, JobType, PostedDate " +
                "FROM JobListings WHERE CompanyID = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, companyID);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                JobListing jobListing = new JobListing(
                        rs.getInt("JobID"),
                        companyID,
                        rs.getString("JobTitle"),
                        rs.getString("JobDescription"),
                        rs.getString("JobLocation"),
                        rs.getDouble("Salary"),
                        rs.getString("JobType"),
                        rs.getTimestamp("PostedDate")
                );
                jobListings.add(jobListing);
            }
        } catch (SQLException e) {
            System.out.println("Error while retrieving job listings: " + e.getMessage());
        } finally {
            closeConnection(conn);
        }
        return jobListings;
    }

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
