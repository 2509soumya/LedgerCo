DROP TABLE IF EXISTS issued_loans;
DROP TABLE IF EXISTS lumpsum_payments;

CREATE TABLE issued_loans (
loan_id INT AUTO_INCREMENT  PRIMARY KEY,
borrower_name VARCHAR(50) NOT NULL,
bank_name VARCHAR(50) NOT NULL,
principal INT(8) NOT NULL,
interest_rate INT(8) NOT NULL,
years INT(8) NOT NULL
);

CREATE TABLE lumpsum_payments (
payment_id INT AUTO_INCREMENT  PRIMARY KEY,
loan_id INT(8) NOT NULL,
lumpsum_amount INT(8) NOT NULL,
emi_num INT(8) NOT NULL,
CONSTRAINT FK_loans_payments FOREIGN KEY (loan_id) REFERENCES issued_loans(loan_id)
);