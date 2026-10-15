
package backend.model;

public class Feedback {
    private String studentId;
    private int courseId;
    private int overallRating;
    private int conceptClarity;
    private int practicalExamples;
    private int teachingEffectiveness;
    private int confidence;
    private String comments;

    public Feedback(String studentId, int courseId,
                    int overallRating, int conceptClarity,
                    int practicalExamples, int teachingEffectiveness,
                    int confidence, String comments) {

        this.studentId = studentId;
        this.courseId = courseId;
        this.overallRating = overallRating;
        this.conceptClarity = conceptClarity;
        this.practicalExamples = practicalExamples;
        this.teachingEffectiveness = teachingEffectiveness;
        this.confidence = confidence;
        this.comments = comments;
    }

    public String getStudentId() {
        return studentId;
    }

    public int getCourseId() {
        return courseId;
    }

    public int getOverallRating() {
        return overallRating;
    }

    public int getConceptClarity() {
        return conceptClarity;
    }

    public int getPracticalExamples() {
        return practicalExamples;
    }

    public int getTeachingEffectiveness() {
        return teachingEffectiveness;
    }

    public int getConfidence() {
        return confidence;
    }

    public String getComments() {
        return comments;
    }
}
