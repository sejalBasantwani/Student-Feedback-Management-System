
package backend.model;

public class Student {

    private String studentId;
    private String name;
    private String email;
    private String branch;
    private String year;
    private int semester;

    public Student(String studentId, String name, String email,
                   String branch, String year, int semester) {
        this.studentId = studentId;
        this.name = name;
        this.email = email;
        this.branch = branch;
        this.year = year;
        this.semester = semester;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getBranch() {
        return branch;
    }

    public String getYear() {
        return year;
    }

    public int getSemester() {
        return semester;
    }
}
