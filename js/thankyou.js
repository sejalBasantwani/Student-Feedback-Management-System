// ========================================
// GET STORED DATA
// ========================================

const studentName =
    localStorage.getItem("studentName");

const course =
    localStorage.getItem("selectedCourse");

const feedbackData =
    JSON.parse(
        localStorage.getItem("feedbackData")
    );


// ========================================
// DISPLAY STUDENT NAME
// ========================================

const nameElement =
    document.getElementById("studentName");


if (studentName) {

    nameElement.textContent =
        studentName;

}


// ========================================
// DISPLAY COURSE
// ========================================

const courseElement =
    document.getElementById("courseName");


if (course) {

    courseElement.textContent =
        course;

}


// ========================================
// DISPLAY RATING
// ========================================

const ratingElement =
    document.getElementById("overallRating");


if (feedbackData) {

    const rating =
        Number(feedbackData.overallRating);

    ratingElement.textContent =
        "⭐".repeat(rating);

}


// ========================================
// DISPLAY CONFIDENCE
// ========================================

const confidenceElement =
    document.getElementById("confidence");


if (feedbackData) {

    const confidence =
        Number(feedbackData.confidence);

    const confidenceText = {

        1: "Low 😟",

        2: "Somewhat Low 😐",

        3: "Moderate 🙂",

        4: "Confident 😃",

        5: "Very Confident 🤩"

    };

    confidenceElement.textContent =
        confidenceText[confidence];

}


// ========================================
// NEW FEEDBACK
// ========================================

document
    .getElementById("newFeedback")
    .addEventListener(
        "click",
        function() {

            // Clear previous feedback

            localStorage.removeItem(
                "feedbackData"
            );

            localStorage.removeItem(
                "selectedCourse"
            );

            localStorage.removeItem(
                "studentName"
            );

            localStorage.removeItem(
                "studentId"
            );

            localStorage.removeItem(
                "branch"
            );

            localStorage.removeItem(
                "year"
            );

            localStorage.removeItem(
                "semester"
            );


            // Start again

            window.location.href =
                "course.html";

        }
    );


// ========================================
// EXIT
// ========================================

document
    .getElementById("exitButton")
    .addEventListener(
        "click",
        function() {

            window.location.href =
                "index.html";

        }
    );