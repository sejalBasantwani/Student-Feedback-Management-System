
package backend.dao;

import backend.DBConnection;
import backend.model.Feedback;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FeedbackDAO {

    public boolean saveFeedback(Feedback feedback) {

        String sql = "INSERT INTO feedback "
                + "(student_id, course_id, overall_rating, "
                + "concept_clarity, practical_examples, "
                + "teaching_effectiveness, confidence, comments) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, feedback.getStudentId());
            ps.setInt(2, feedback.getCourseId());
            ps.setInt(3, feedback.getOverallRating());
            ps.setInt(4, feedback.getConceptClarity());
            ps.setInt(5, feedback.getPracticalExamples());
            ps.setInt(6, feedback.getTeachingEffectiveness());
            ps.setInt(7, feedback.getConfidence());
            ps.setString(8, feedback.getComments());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error saving feedback: "
                    + e.getMessage());
            return false;
        }
    }
}
