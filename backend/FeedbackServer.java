
package backend;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import backend.dao.StudentDAO;
import backend.model.Student;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import backend.dao.FeedbackDAO;
import backend.model.Feedback;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class FeedbackServer {

    public static void main(String[] args) throws IOException {

        HttpServer server = HttpServer.create(
            new InetSocketAddress(8080), 0
        );

        server.createContext("/api/health", exchange -> {
            addCorsHeaders(exchange);

            if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(204, -1);
                exchange.close();
                return;
            }

            if (!"GET".equalsIgnoreCase(exchange.getRequestMethod())) {
                sendResponse(exchange, 405, "Method not allowed");
                return;
            }

            sendResponse(exchange, 200, "Student Voice backend is running!");
        });
        
server.createContext("/api/students", exchange -> {
    addCorsHeaders(exchange);

    if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
        exchange.sendResponseHeaders(204, -1);
        exchange.close();
        return;
    }

    if (!"POST".equalsIgnoreCase(exchange.getRequestMethod())) {
        sendResponse(exchange, 405, "POST method required");
        return;
    }

    try {
        String body = new String(
            exchange.getRequestBody().readAllBytes(),
            StandardCharsets.UTF_8
        );

        Map<String, String> data = new HashMap<>();

        for (String pair : body.split("&")) {
            String[] parts = pair.split("=", 2);

            String key = URLDecoder.decode(
                parts[0], StandardCharsets.UTF_8
            );

            String value = parts.length > 1
                ? URLDecoder.decode(parts[1], StandardCharsets.UTF_8)
                : "";

            data.put(key, value);
        }

        String studentId = data.getOrDefault("studentId", "").trim();
        String name = data.getOrDefault("name", "").trim();
        String email = data.getOrDefault("email", "").trim();
        String branch = data.getOrDefault("branch", "").trim();
        String year = data.getOrDefault("year", "").trim();
        String semesterText =
            data.getOrDefault("semester", "").trim();

        if (studentId.isEmpty() || name.isEmpty()
                || email.isEmpty() || branch.isEmpty()
                || year.isEmpty() || semesterText.isEmpty()) {
            sendResponse(exchange, 400, "All student fields are required");
            return;
        }

        int semester = Integer.parseInt(semesterText);

        if (semester < 1 || semester > 12) {
            sendResponse(exchange, 400, "Invalid semester");
            return;
        }

        Student student = new Student(
            studentId, name, email, branch, year, semester
        );

        StudentDAO dao = new StudentDAO();

        if (dao.saveStudent(student)) {
            sendResponse(exchange, 201, "Student saved successfully!");
        } else {
            sendResponse(exchange, 500, "Could not save student");
        }

    } catch (NumberFormatException e) {
        sendResponse(exchange, 400, "Semester must be a number");
    } catch (Exception e) {
        e.printStackTrace();
        sendResponse(exchange, 500, "Server error");
    }
});
        
server.createContext("/api/feedback", exchange -> {
    addCorsHeaders(exchange);

    if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
        exchange.sendResponseHeaders(204, -1);
        exchange.close();
        return;
    }

    if (!"POST".equalsIgnoreCase(exchange.getRequestMethod())) {
        sendResponse(exchange, 405, "POST method required");
        return;
    }

    try {
        String body = new String(
            exchange.getRequestBody().readAllBytes(),
            java.nio.charset.StandardCharsets.UTF_8
        );

        Map<String, String> data = new HashMap<>();

        for (String pair : body.split("&")) {
            String[] parts = pair.split("=", 2);

            String key = URLDecoder.decode(
                parts[0], StandardCharsets.UTF_8
            );

            String value = parts.length > 1
                ? URLDecoder.decode(parts[1], StandardCharsets.UTF_8)
                : "";

            data.put(key, value);
        }

        String studentId =
            data.getOrDefault("studentId", "").trim();

        String courseName =
            data.getOrDefault("course", "").trim();

        String comments =
            data.getOrDefault("comments", "").trim();

        if (studentId.isEmpty() || courseName.isEmpty()) {
            sendResponse(exchange, 400,
                "Student ID and course are required");
            return;
        }

        if (comments.length() > 300) {
            sendResponse(exchange, 400,
                "Comments must not exceed 300 characters");
            return;
        }

        int overallRating = Integer.parseInt(
            data.getOrDefault("overallRating", "0"));
        int conceptClarity = Integer.parseInt(
            data.getOrDefault("conceptClarity", "0"));
        int practicalExamples = Integer.parseInt(
            data.getOrDefault("practicalExamples", "0"));
        int teachingEffectiveness = Integer.parseInt(
            data.getOrDefault("teachingEffectiveness", "0"));
        int confidence = Integer.parseInt(
            data.getOrDefault("confidence", "0"));

        int[] ratings = {
            overallRating, conceptClarity, practicalExamples,
            teachingEffectiveness, confidence
        };

        for (int rating : ratings) {
            if (rating < 1 || rating > 5) {
                sendResponse(exchange, 400,
                    "All ratings must be between 1 and 5");
                return;
            }
        }

        // Find the numeric course ID using the selected course name
        int courseId;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(
                 "SELECT course_id FROM courses "
                 + "WHERE LOWER(course_name) = LOWER(?)")) {

            ps.setString(1, courseName);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    sendResponse(exchange, 400,
                        "Selected course was not found");
                    return;
                }

                courseId = rs.getInt("course_id");
            }
        }

        Feedback feedback = new Feedback(
            studentId,
            courseId,
            overallRating,
            conceptClarity,
            practicalExamples,
            teachingEffectiveness,
            confidence,
            comments
        );

        FeedbackDAO dao = new FeedbackDAO();

        if (dao.saveFeedback(feedback)) {
            sendResponse(exchange, 201,
                "Feedback saved successfully!");
        } else {
            sendResponse(exchange, 500,
                "Could not save feedback");
        }

    } catch (NumberFormatException e) {
        sendResponse(exchange, 400,
            "Ratings must be valid numbers");
    } catch (Exception e) {
        e.printStackTrace();
        sendResponse(exchange, 500, "Server error");
    }
});

        server.start();

        System.out.println("Student Voice server started!");
        System.out.println("Open http://localhost:8080/api/health");
    }

    private static void addCorsHeaders(HttpExchange exchange) {
        exchange.getResponseHeaders().set(
            "Access-Control-Allow-Origin", "*"
        );
        exchange.getResponseHeaders().set(
            "Access-Control-Allow-Methods", "GET, POST, OPTIONS"
        );
        exchange.getResponseHeaders().set(
            "Access-Control-Allow-Headers", "Content-Type"
        );
    }

    private static void sendResponse(
            HttpExchange exchange, int status, String message)
            throws IOException {

        byte[] response = message.getBytes(
            java.nio.charset.StandardCharsets.UTF_8
        );

        exchange.getResponseHeaders().set(
            "Content-Type", "text/plain; charset=UTF-8"
        );

        exchange.sendResponseHeaders(status, response.length);

        try (OutputStream output = exchange.getResponseBody()) {
            output.write(response);
        }
    }
}
