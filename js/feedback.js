
const API_URL = "http://localhost:8080/api/feedback";

// Get selected course
const course = localStorage.getItem("selectedCourse");
const courseName = document.getElementById("courseName");

courseName.textContent = course || "No course selected";

// Teaching slider
const teachingSlider = document.getElementById("teaching");
const teachingValue = document.getElementById("teachingValue");

teachingSlider.addEventListener("input", function () {
    teachingValue.textContent = teachingSlider.value;
});

// Character counter
const comments = document.getElementById("comments");
const charCount = document.getElementById("charCount");

comments.addEventListener("input", function () {
    charCount.textContent = comments.value.length;
});

// Feedback form
const feedbackForm = document.getElementById("feedbackForm");
const feedbackMessage = document.getElementById("feedbackMessage");
const submitButton = feedbackForm.querySelector(
    'button[type="submit"]'
);

feedbackForm.addEventListener("submit", async function (event) {
    event.preventDefault();

    const studentId = localStorage.getItem("studentId");

    if (!studentId || !course) {
        feedbackMessage.textContent =
            "Student details or course are missing. Please start again.";
        feedbackMessage.style.color = "red";
        return;
    }

    // Get selected answers
    const overall = document.querySelector(
        'input[name="overall"]:checked'
    );

    const clarity = document.querySelector(
        'input[name="clarity"]:checked'
    );

    const practical = document.querySelector(
        'input[name="practical"]:checked'
    );

    const confidence = document.querySelector(
        'input[name="confidence"]:checked'
    );

    // Validate required questions
    if (!overall || !clarity || !practical || !confidence) {
        feedbackMessage.textContent =
            "Please answer all the required questions.";
        feedbackMessage.style.color = "red";
        return;
    }

    const data = {
        studentId: studentId,
        course: course,
        overallRating: overall.value,
        conceptClarity: clarity.value,
        practicalExamples: practical.value,
        teachingEffectiveness: teachingSlider.value,
        confidence: confidence.value,
        comments: comments.value.trim()
    };

    try {
        submitButton.disabled = true;
        submitButton.textContent = "Submitting...";

        feedbackMessage.textContent = "Saving your feedback...";
        feedbackMessage.style.color = "#555";

        const response = await fetch(API_URL, {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            body: new URLSearchParams(data).toString()
        });

        const result = await response.text();

        if (!response.ok) {
            throw new Error(result || "Could not save feedback.");
        }

        // Keep a copy for the existing thank-you page
        localStorage.setItem("feedbackData", JSON.stringify(data));

        feedbackMessage.textContent =
            "Feedback submitted successfully! 🎉";
        feedbackMessage.style.color = "green";

        setTimeout(function () {
            window.location.href = "thankyou.html";
        }, 1000);

    } catch (error) {
        console.error("Feedback submission error:", error);

        feedbackMessage.textContent =
            error.message === "Failed to fetch"
                ? "Cannot reach the server. Make sure your Java server is running."
                : error.message;

        feedbackMessage.style.color = "red";

    } finally {
        submitButton.disabled = false;
        submitButton.textContent = "Submit Feedback";
    }
});
