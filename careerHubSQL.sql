CREATE DATABASE JobBoard;

DROP DATABASE JobBoard;

USE JobBoard;

CREATE TABLE Companies (
    CompanyID INT AUTO_INCREMENT PRIMARY KEY,
    CompanyName VARCHAR(100) NOT NULL,
    Location VARCHAR(100)
);

INSERT INTO Companies (CompanyName, Location)
VALUES 
('Tech Solutions', 'New York'),
('InnovaTech', 'San Francisco'),
('DataPulse', 'Chicago'),
('GreenField Corp', 'Austin'),
('SkyNet Labs', 'Los Angeles');

CREATE TABLE JobListings (
    JobID INT AUTO_INCREMENT PRIMARY KEY,
    CompanyID INT,
    JobTitle VARCHAR(100) NOT NULL,
    JobDescription TEXT,
    JobLocation VARCHAR(100),
    Salary DECIMAL(10, 2),
    JobType VARCHAR(20),
    PostedDate DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (CompanyID) REFERENCES Companies(CompanyID)
);

INSERT INTO JobListings (CompanyID, JobTitle, JobDescription, JobLocation, Salary, JobType, PostedDate)
VALUES 
(1, 'Software Engineer', 'Develop and maintain software applications', 'New York', 85000.00, 'Full-time', NOW()),
(2, 'Data Scientist', 'Analyze and interpret complex data sets', 'San Francisco', 95000.00, 'Full-time', NOW()),
(3, 'Front-End Developer', 'Design and develop user interfaces', 'Chicago', 70000.00, 'Part-time', NOW()),
(4, 'Systems Analyst', 'Evaluate and improve IT systems', 'Austin', 80000.00, 'Contract', NOW()),
(5, 'Network Administrator', 'Maintain and secure computer networks', 'Los Angeles', 78000.00, 'Full-time', NOW());

CREATE TABLE Applicants (
    ApplicantID INT AUTO_INCREMENT PRIMARY KEY,
    FirstName VARCHAR(50) NOT NULL,
    LastName VARCHAR(50) NOT NULL,
    Email VARCHAR(100) NOT NULL,
    Phone VARCHAR(20),
    Resume TEXT
);

INSERT INTO Applicants (FirstName, LastName, Email, Phone, Resume)
VALUES 
('John', 'Doe', 'johndoe@gmail.com', '123-456-7890', 'Resume John'),
('Jane', 'Smith', 'janesmith@hotmail.com', '987-654-3210', 'Resume Jane'),
('Mark', 'Brown', 'markbrown@yahoo.com', '555-666-7777', 'Resume Mark'),
('Lucy', 'Taylor', 'lucytaylor@outlook.com', '444-555-6666', 'Resume Lucy'),
('David', 'Jones', 'davidjones@gmail.com', '222-333-4444', 'Resume David');

CREATE TABLE JobApplications (
    ApplicationID INT AUTO_INCREMENT PRIMARY KEY,
    JobID INT,
    ApplicantID INT,
    ApplicationDate DATETIME DEFAULT CURRENT_TIMESTAMP,
    CoverLetter TEXT,
    FOREIGN KEY (JobID) REFERENCES JobListings(JobID),
    FOREIGN KEY (ApplicantID) REFERENCES Applicants(ApplicantID)
);

INSERT INTO JobApplications (JobID, ApplicantID, ApplicationDate, CoverLetter)
VALUES 
(1, 1, NOW(), 'I am very interested in the Software Engineer position and believe I have the required skills.'),
(2, 2, NOW(), 'I would love to work as a Data Scientist at your company and contribute to your team.'),
(3, 3, NOW(), 'I have strong skills in front-end development and would be a great fit for this role.'),
(4, 4, NOW(), 'I have extensive experience in IT systems analysis and would love to bring my skills to your company.'),
(5, 5, NOW(), 'I am passionate about network security and administration, and I believe I can add great value to your team.');

SELECT * FROM Companies;

SELECT * FROM JobListings;

SELECT * FROM Applicants;

SELECT * FROM JobApplications;

