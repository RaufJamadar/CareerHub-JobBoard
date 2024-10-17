package dao;

import model.Applicant;
import jdbc.DatabaseConnection;
import java.sql.*;

public class ApplicantDAOImpl implements ApplicantDAO {

    @Override
    public void createProfile(Applicant applicant) {
        Connection conn = DatabaseConnection.getConnection();
        String sql = "INSERT INTO Applicants (FirstName, LastName, Email, Phone, Resume) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, applicant.getFirstName());
            pstmt.setString(2, applicant.getLastName());
            pstmt.setString(3, applicant.getEmail());
            pstmt.setString(4, applicant.getPhone());
            pstmt.setString(5, applicant.getResume());
            pstmt.executeUpdate();
            System.out.println("Profile created successfully for applicant: " + applicant.getFirstName() + " " + applicant.getLastName());
        } catch (SQLException e) {
            System.out.println("Error while saving profile to the database: " + e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }

    @Override
    public void applyForJob(int jobID, int applicantID, String coverLetter) {
        Connection conn = DatabaseConnection.getConnection();
        String sql = "INSERT INTO JobApplications (JobID, ApplicantID, ApplicationDate, CoverLetter) VALUES (?, ?, NOW(), ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, jobID);
            pstmt.setInt(2, applicantID);
            pstmt.setString(3, coverLetter);
            pstmt.executeUpdate();
            System.out.println("Application submitted successfully for job ID: " + jobID);
        } catch (SQLException e) {
            System.out.println("Error while applying for the job: " + e.getMessage());
        } finally {
            closeConnection(conn);
        }
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
