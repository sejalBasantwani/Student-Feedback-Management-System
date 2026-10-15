
const API_URL = "http://localhost:8080/api/students";

// Display selected course
const course = localStorage.getItem("selectedCourse");
const courseName = document.getElementById("courseName");

courseName.textContent = course || "No course selected";

// Get form elements
const detailsForm = document.getElementById("detailsForm");
const message = document.getElementById("detailsMessage");
const continueButton = detailsForm.querySelector(
    'button[type="submit"]'
);

// Handle form submission
detailsForm.addEventListener("submit", async function(event) {
    event.preventDefault();

    if (!course) {
        message.textContent = "Please select a course first.";
        message.style.color = "red";
        return;
    }

    const fullName = document.getElementById("fullName").value.trim();
    const studentId = document.getElementById("studentId").value.trim();
    const email = document.getElementById("email").value.trim();
    const branch = document.getElementById("branch").value;
    const year = document.getElementById("year").value;
    const semester = document.getElementById("semester").value;

    // Validate form fields
    if (!fullName || !studentId || !email ||
        !branch || !year || !semester) {
        message.textContent = "Please fill in all the details.";
        message.style.color = "red";
        return;
    }

    if (!document.getElementById("email").checkValidity()) {
        message.textContent = "Please enter a valid email address.";
        message.style.color = "red";
        return;
    }

    // Prepare data for the Java backend
    const studentData = new URLSearchParams({
        studentId: studentId,
        name: fullName,
        email: email,
        branch: branch,
        year: year,
        semester: semester
    });

    try {
        continueButton.disabled = true;
        continueButton.textContent = "Saving...";
        message.textContent = "Saving your details...";
        message.style.color = "#555";

        const response = await fetch(API_URL, {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            body: studentData.toString()
        });

        const result = await response.text();

        if (!response.ok) {
            throw new Error(result || "Could not save student details.");
        }

        // Save details locally for use on later pages
        localStorage.setItem("studentName", fullName);
        localStorage.setItem("studentId", studentId);
        localStorage.setItem("studentEmail", email);
        localStorage.setItem("branch", branch);
        localStorage.setItem("year", year);
        localStorage.setItem("semester", semester);

        message.textContent = "Details saved successfully! ✓";
        message.style.color = "green";

        setTimeout(function() {
            window.location.href = "feedback.html";
        }, 800);

    } catch (error) {
        console.error("Student registration error:", error);

        message.textContent =
            error.message.includes("Failed to fetch")
                ? "Cannot reach the server. Make sure your Java server is running."
                : error.message;

        message.style.color = "red";

    } finally {
        continueButton.disabled = false;
        continueButton.textContent = "Continue →";
    }
});

// Back button
document.getElementById("backButton").addEventListener("click", function() {
    window.location.href = "course.html";
});
