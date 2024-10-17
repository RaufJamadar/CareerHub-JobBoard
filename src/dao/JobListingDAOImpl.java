package dao;

import jdbc.DatabaseConnection;
import model.Applicant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JobListingDAOImpl implements JobListingDAO {

    @Override
    public void apply(int jobID, int applicantID, String coverLetter) {
        Connection conn = DatabaseConnection.getConnection();
        String sql = "INSERT INTO JobApplications (JobID, ApplicantID, ApplicationDate, CoverLetter) VALUES (?, ?, NOW(), ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, jobID);
            pstmt.setInt(2, applicantID);
            pstmt.setString(3, coverLetter);
            pstmt.executeUpdate();
            System.out.println("Application submitted successfully for applicant ID: " + applicantID);
        } catch (SQLException e) {
            System.out.println("Error while applying for the job: " + e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }

    @Override
    public List<Applicant> getApplicants(int jobID) {
        List<Applicant> applicants = new ArrayList<>();
        Connection conn = DatabaseConnection.getConnection();
        String sql = "SELECT a.ApplicantID, a.FirstName, a.LastName, a.Email, a.Phone, a.Resume " +
                "FROM Applicants a " +
                "JOIN JobApplications ja ON a.ApplicantID = ja.ApplicantID " +
                "WHERE ja.JobID = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, jobID);
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
            System.out.println("Error while retrieving applicants: " + e.getMessage());
        } finally {
            closeConnection(conn);
        }
        return applicants;
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
