const loginForm = document.getElementById("loginForm");

const emailInput = document.getElementById("email");

const passwordInput =
    document.getElementById("password");

const message =
    document.getElementById("loginMessage");

const togglePassword =
    document.getElementById("togglePassword");


// Show / hide password

togglePassword.addEventListener("click", function () {

    if (passwordInput.type === "password") {

        passwordInput.type = "text";

        togglePassword.textContent = "🙈";

    } else {

        passwordInput.type = "password";

        togglePassword.textContent = "👁";

    }

});


// Login

loginForm.addEventListener("submit", function (event) {

    event.preventDefault();


    const email = emailInput.value.trim();

    const password = passwordInput.value.trim();


    // Common login credentials

    const correctEmail = "student@gmail.com";

    const correctPassword = "student123";


    if (email === "" || password === "") {

        message.textContent =
            "Please enter email and password.";

        message.style.color = "red";

        return;
    }


    if (
        email === correctEmail &&
        password === correctPassword
    ) {

        message.textContent =
            "Login successful! 🎉";

        message.style.color = "green";


        // Go to course selection

        setTimeout(function () {

            window.location.href =
                "course.html";

        }, 1000);

    } else {

        message.textContent =
            "Invalid email or password.";

        message.style.color = "red";

    }

});