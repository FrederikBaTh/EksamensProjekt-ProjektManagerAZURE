CREATE TABLE users (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    is_admin BOOLEAN NOT NULL,
    name VARCHAR(100),
    company VARCHAR(100),
    job_title VARCHAR(100),
    description TEXT
);
INSERT INTO users (user_id,username, password, is_admin, name, company, job_title, description)
VALUES
    (1,'testUser1', 'testPassword1', false, 'Test User 1', 'Test Company 1', 'Test Job Title 1', 'Test Description 1'),
    (2,'testUser2', 'testPassword2', true, 'Test User 2', 'Test Company 2', 'Test Job Title 2', 'Test Description 2');