# Student Feedback Management System

A web-based application that allows students to select a course, submit feedback about their learning experience, and save their responses in a MySQL database.

## Features

- **Student Registration:** Collects student details, including name, email, branch, year, and semester.
- **Course Selection:** Allows students to select a course such as Java, Web Development, DBMS, or Python.
- **Feedback Submission:** Collects ratings for overall experience, concept clarity, practical examples, teaching effectiveness, and confidence.
- **Database Integration:** Stores student information and feedback in MySQL.
- **Confirmation Page:** Displays a thank-you message after successful feedback submission.
- **Responsive Interface:** Uses HTML, CSS, and JavaScript to create an interactive user experience.

## Technologies Used

| Technology | Purpose |
|---|---|
| HTML5 | Web page structure |
| CSS3 | Styling and responsive design |
| JavaScript | Frontend interactions and API requests |
| Core Java | Backend development |
| JDBC | Java–MySQL connectivity |
| MySQL | Database management |
| Git & GitHub | Version control and project hosting |

## Project Structure

```text
StudentFeedback/
├── index.html
├── course.html
├── details.html
├── feedback.html
├── thankyou.html
├── css/
│   └── style.css
├── js/
│   ├── course.js
│   ├── details.js
│   ├── feedback.js
│   └── thankyou.js
└── backend/
    ├── DBConnection.java
    ├── FeedbackServer.java
    ├── dao/
    ├── model/
    └── lib/
```

## Prerequisites

- Java Development Kit (JDK 21 or a compatible version)
- MySQL Server
- MySQL Connector/J
- Visual Studio Code or another Java-compatible editor
- A web browser

## Database Setup

1. Start your MySQL server.
2. Create the database:

   ```sql
   CREATE DATABASE student_feedback_db;
   ```

3. Create the required `student`, `courses`, and `feedback` tables using the project's database schema.
4. Add the required course records.
5. Configure your MySQL username and password using environment variables.

> Ensure that the database tables, column names, and foreign keys match the SQL statements in the Java DAO classes.

## Running the Project

### 1. Configure database credentials

In PowerShell, set your database credentials:

```powershell
$env:DB_USERNAME = "feedback_app"
$env:DB_PASSWORD = Read-Host "Enter your database password"
```

### 2. Compile the Java backend

Run the following command from the project root, adjusting the Connector/J path if necessary:

```powershell
javac -cp ".;backend\lib\mysql-connector-j-26.7.0\mysql-connector-j-26.7.0\mysql-connector-j-26.7.0.jar" -d . backend\DBConnection.java backend\model\Student.java backend\model\Feedback.java backend\dao\StudentDAO.java backend\dao\FeedbackDAO.java backend\FeedbackServer.java
```

### 3. Start the backend server

```powershell
java -cp ".;backend\lib\mysql-connector-j-26.7.0\mysql-connector-j-26.7.0\mysql-connector-j-26.7.0.jar" backend.FeedbackServer
```

The backend API runs on port `8080`.

### 4. Launch the frontend

Open `index.html` in your browser using a local development server, such as the Live Server extension in VS Code.

Keep the Java backend running while submitting student details and feedback.

## API Endpoints

| Method | Endpoint | Purpose |
|---|---|---|
| GET | `/api/health` | Check backend health |
| POST | `/api/students` | Save student details |
| POST | `/api/feedback` | Save feedback |

## Future Enhancements

- Administrator dashboard for viewing feedback.
- Feedback analytics and course-wise reports.
- Authentication and role-based access control.
- Exportable feedback reports.

## Author

Developed as an academic project to demonstrate frontend development, Java backend programming, database connectivity, and full-stack application integration.