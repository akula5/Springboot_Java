create schema Monday;
use Monday;
create table Employee (
    id INTEGER PRIMARY KEY,
    first_name varchar(30),
    last_name varchar(30),
    job_start_date date,
    salary float(10),
    department varchar(40)
);
insert into Employee values('Niharika', 'Akula', '2023-01-05', '55000', 'CS');
insert into Employee values('Vinay', 'Kokkonda', '2023-04-13', '75000', 'ECE');
insert into Employee values('Tejaswi', 'Shetty', '2021-07-22', '20000', 'EEE');
insert into Employee values('Deekshitha', 'Kutty', '2022-11-08', '10000', 'CS');
insert into Employee values('Koushika', 'Reddy', '2020-07-22', '27000', 'ECE');

select max(salary) from Employee;

select * from Employee
where job_start_date >= date_sub(curdate(), interval 6 month);

select department, count(*) as no_of_employees from Employee
group by department;

create table lead_details
 (
 lead_id VARCHAR(20) primary key,
 first_name VARCHAR(30),
 last_name varchar(30),
 email_address VARCHAR(50),
 phone_number VARCHAR(15)
 );

  create table consultant_details
 (
 consultant_id VARCHAR(50) primary key,
 lead_id VARCHAR(20),
 first_name VARCHAR(30),
 last_name VARCHAR(30),
 email_address VARCHAR(50),
 phone_number VARCHAR(15),
 Foreign key (lead_id) REFERENCES lead_details(lead_id)
 );

create table submission
(
 submission_id VARCHAR(50) primary key,
 consultant_id VARCHAR(50),
 submission_date DATETIME,
 vendor_company VARCHAR(60),
 vendor_name VARCHAR(80),
 vendor_email_address VARCHAR(50),
 vendor_phone_number VARCHAR(15),
 implementation_partner varchar(60),
 client_name VARCHAR(40),
 pay_rate DOUBLE,
 submission_status VARCHAR(60),
 submission_type VARCHAR(10),
 city VARCHAR(50),
 state VARCHAR(2),
 zip INTEGER,
 FOREIGN KEY (consultant_id) REFERENCES consultant_details(consultant_id)
 );

 INSERT INTO lead_details (lead_id, first_name, last_name, email_address, phone_number) VALUES
 ('LD001', 'Vinay', 'Kumar', 'Vinay@example.com', '1234567890'),
 ('LD002', 'Sagar', 'Kokkonda', 'Sagar@example.com', '9876543210'),
 ('LD003', 'Kranthi', 'Shetty', 'Kranthi@example.com', '5678901234'),
 ('LD004', 'Manohar', 'Kutty', 'Manohar@example.com', '8901234567'),
 ('LD005', 'Anand', 'Brown', 'Anand@example.com', '4567890123');



 -- Insert data into the "consultant_details" table
 INSERT INTO consultant_details (consultant_id, lead_id, first_name, last_name, email_address, phone_number) VALUES
 ('CD001', 'LD001', 'Niharika', 'Akula', 'akula.consultant@example.com', '1234567890'),
 ('CD002', 'LD002', 'Vinay', 'Kokkonda', 'kokkonda.consultant@example.com', '9876543210'),
 ('CD003', 'LD003', 'Tejaswi', 'Shetty', 'shetty.consultant@example.com', '5678901234'),
 ('CD004', 'LD004', 'Deekshitha', 'Kutty', 'kutty.consultant@example.com', '8901234567'),
 ('CD005', 'LD005', 'Tulasi', 'Sherla', 'sherla.consultant@example.com', '4567890123');



 -- Insert data into the "submission" table
 INSERT INTO submission (submission_id, consultant_id, submission_date, vendor_company, vendor_name, vendor_email_address, vendor_phone_number, implementation_partner, client_name, pay_rate, submission_status, submission_type, city, state, zip) VALUES
 ('S001', 'CD001', '2023-01-15', 'Vendor Company 1', 'Vendor Name 1', 'vendor1@example.com', '1234567890', 'Implementation Partner 1', 'Client Name 1', 5000.00, 'Submitted', 'Type A', 'City 1', 'ST', 12345),
 ('S002', 'CD002', '2022-11-02', 'Vendor Company 2', 'Vendor Name 2', 'vendor2@example.com', '9876543210', 'Implementation Partner 2', 'Client Name 2', 4000.00, 'Submitted', 'Type B', 'City 2', 'ST', 23456),
 ('S003', 'CD003', '2023-02-28', 'Vendor Company 3', 'Vendor Name 3', 'vendor3@example.com', '5678901234', 'Implementation Partner 3', 'Client Name 3', 6000.00, 'Submitted', 'Type C', 'City 3', 'ST', 34567),
 ('S004', 'CD004', '2022-09-20', 'Vendor Company 4', 'Vendor Name 4', 'vendor4@example.com', '8901234567', 'Implementation Partner 4', 'Client Name 4', 5500.00, 'Submitted', 'Type A', 'City 4', 'ST', 45678),
 ('S005', 'CD005', '2023-03-10', 'Vendor Company 5', 'Vendor Name 5', 'vendor5@example.com', '4567890123', 'Implementation Partner 5', 'Client Name 5', 5200.00, 'Submitted', 'Type B', 'City 5', 'ST', 56789);


UPDATE consultant_details SET email_address = 'java@example.com' WHERE consultant_id = 'CD001';

-- 3. Write a SQL to find total number of submissions for each constulant.
SELECT consultant_details.consultant_id,consultant_details.first_name AS consultant_first_name, consultant_details.last_name AS consultant_last_name,count(*) AS total_submission
FROM consultant_details JOIN submission ON (consultant_details.consultant_id = submission.consultant_id) GROUP BY consultant_details.consultant_id;

-- 4. Write a SQL to find total number of submissions for each constulant by each submission day
SELECT consultant_details.consultant_id,consultant_details.first_name AS consultant_first_name, consultant_details.last_name AS consultant_last_name,
count(*) AS total_submission,submission.submission_date
FROM consultant_details JOIN submission ON (consultant_details.consultant_id = submission.consultant_id) GROUP BY consultant_details.consultant_id,submission.submission_date;


-- 5. write a SQL to delete all submissions where "rate" is null
DELETE FROM submission WHERE pay_rate IS NULL;

-- 6. Given a lead name and submission date, Write a SQL query to find the submissions.
SELECT submission.* FROM submission JOIN consultant_details ON (submission.consultant_id=consultant_details.consultant_id)
JOIN lead_details ON (lead_details.lead_id=consultant_details.lead_id)
WHERE ( lead_details.first_name ="Manohar" AND lead_details.last_name="Kutty");

-- 7. Write a SQL query to find the number of submissions by each lead.
SELECT ld.lead_id,ld.first_name,ld.last_name, COUNT(s.submission_id) AS num_submissions FROM lead_details AS ld
LEFT JOIN consultant_details AS cd ON (ld.lead_id = cd.lead_id)
LEFT JOIN submission AS s ON (cd.consultant_id = s.consultant_id)
GROUP BY ld.lead_id;
