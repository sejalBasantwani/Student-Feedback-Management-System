const courseCards =
    document.querySelectorAll(".course-card");

const continueButton =
    document.getElementById("continueButton");

const selectedCourse =
    document.getElementById("selectedCourse");

let selected = "";


// Select course

courseCards.forEach(function(card) {

    card.addEventListener("click", function() {

        // Remove previous selection

        courseCards.forEach(function(item) {

            item.classList.remove("selected");

        });


        // Select current card

        card.classList.add("selected");


        // Get course name

        selected =
            card.getAttribute("data-course");


        // Display selected course

        selectedCourse.textContent =
            "You selected: " + selected + " ✓";

    });

});


// Continue button

continueButton.addEventListener("click", function() {

    if (selected === "") {

        selectedCourse.textContent =
            "Please select a course first.";

        selectedCourse.style.color = "red";

        return;

    }


    // Save selected course

    localStorage.setItem(
        "selectedCourse",
        selected
    );


    // Move to details page

    window.location.href =
        "details.html";

});