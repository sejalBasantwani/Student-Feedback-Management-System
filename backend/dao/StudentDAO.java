    
package backend.dao;

import backend.DBConnection;
import backend.model.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StudentDAO {

    public boolean saveStudent(Student student) {

        String sql = "INSERT INTO student "
                + "(student_id, name, email, branch, year, semester) "
                + "VALUES (?, ?, ?, ?, ?, ?) "
                + "ON DUPLICATE KEY UPDATE "
                + "name = VALUES(name), "
                + "email = VALUES(email), "
                + "branch = VALUES(branch), "
                + "year = VALUES(year), "
                + "semester = VALUES(semester)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, student.getStudentId());
            ps.setString(2, student.getName());
            ps.setString(3, student.getEmail());
            ps.setString(4, student.getBranch());
            ps.setString(5, student.getYear());
            ps.setInt(6, student.getSemester());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error saving student: " + e.getMessage());
            return false;
        }
    }
}


