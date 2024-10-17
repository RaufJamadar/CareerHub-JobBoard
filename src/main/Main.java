package main;

import dao.ApplicantDAOImpl;
import dao.CompanyDAOImpl;
import dao.JobListingDAOImpl;
import model.Applicant;
import model.JobListing;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CompanyDAOImpl companyDAO = new CompanyDAOImpl();
        ApplicantDAOImpl applicantDAO = new ApplicantDAOImpl();
        JobListingDAOImpl jobListingDAO = new JobListingDAOImpl();

        while (true) {
            // Main menu
            System.out.println("Welcome to CareerHub!");
            System.out.println("Please select an option:");
            System.out.println("1. Company");
            System.out.println("2. Applicant");
            System.out.println("3. Quit");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    showCompanyMenu(scanner, companyDAO); // Call company-specific menu
                    break;
                case 2:
                    showApplicantMenu(scanner, applicantDAO, jobListingDAO); // Call applicant-specific menu
                    break;
                case 3:
                    System.out.println("Thank you for using CareerHub. Goodbye!");
                    System.exit(0); // Exit the program
                    break;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }

    // Show menu for Company options
    private static void showCompanyMenu(Scanner scanner, CompanyDAOImpl companyDAO) {
        while (true) {
            System.out.println("Company Menu:");
            System.out.println("1. Post Job");
            System.out.println("2. View Job Listings");
            System.out.println("3. Go Back");

            int companyChoice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (companyChoice) {
                case 1:
                    // Collect details for posting a job
                    System.out.println("Enter Company ID:");
                    int companyID = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    System.out.println("Enter Job Title:");
                    String jobTitle = scanner.nextLine();
                    System.out.println("Enter Job Description:");
                    String jobDescription = scanner.nextLine();
                    System.out.println("Enter Job Location:");
                    String jobLocation = scanner.nextLine();
                    System.out.println("Enter Salary:");
                    double salary = scanner.nextDouble();
                    scanner.nextLine();  // Consume newline
                    System.out.println("Enter Job Type (Full-time/Part-time):");
                    String jobType = scanner.nextLine();

                    companyDAO.postJob(companyID, jobTitle, jobDescription, jobLocation, salary, jobType);
                    System.out.println("Job Posted Successfully!");
                    break;
                case 2:
                    // Show job listings posted by the company
                    System.out.println("Enter Company ID:");
                    int companyIdForJobs = scanner.nextInt();
                    List<JobListing> jobs = companyDAO.getJobs(companyIdForJobs);
                    if (jobs.isEmpty()) {
                        System.out.println("No job listings available for this company.");
                    } else {
                        for (JobListing job : jobs) {
                            System.out.println("Job ID: " + job.getJobID() + " | Title: " + job.getJobTitle() +
                                    " | Location: " + job.getJobLocation() + " | Salary: " + job.getSalary());
                        }
                    }
                    break;
                case 3:
                    return; // Go back to the main menu
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }

    // Show menu for Applicant options
    private static void showApplicantMenu(Scanner scanner, ApplicantDAOImpl applicantDAO, JobListingDAOImpl jobListingDAO) {
        while (true) {
            System.out.println("Applicant Menu:");
            System.out.println("1. Create Profile");
            System.out.println("2. Apply for Job");
            System.out.println("4. Go Back");

            int applicantChoice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (applicantChoice) {
                case 1:
                    // Collect details for creating applicant profile
                    System.out.println("Enter First Name:");
                    String firstName = scanner.nextLine();
                    System.out.println("Enter Last Name:");
                    String lastName = scanner.nextLine();
                    System.out.println("Enter Email:");
                    String email = scanner.nextLine();
                    System.out.println("Enter Phone Number:");
                    String phone = scanner.nextLine();
                    System.out.println("Enter Resume (file name or path):");
                    String resume = scanner.nextLine();

                    Applicant applicant = new Applicant(0, firstName, lastName, email, phone, resume);
                    applicantDAO.createProfile(applicant);
                    System.out.println("Profile Created Successfully for: " + firstName + " " + lastName);
                    break;
                case 2:
                    // Apply for a job
                    System.out.println("Enter Applicant ID:");
                    int applicantID = scanner.nextInt();
                    System.out.println("Enter Job ID to apply for:");
                    int jobID = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    System.out.println("Enter Cover Letter:");
                    String coverLetter = scanner.nextLine();

                    applicantDAO.applyForJob(jobID, applicantID, coverLetter);
                    System.out.println("Application Submitted for Job ID: " + jobID);
                    break;
                case 4:
                    return; // Go back to the main menu
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }
}
