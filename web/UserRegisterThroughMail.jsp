<div class="modal fade" id="registrationModal" tabindex="-1" aria-labelledby="registrationModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="registrationModalLabel">User Registration</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div style="margin-left: 126px">
                Already have an account? 
                <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#loginModal">
                    Login
                </button>
            </div>
            <div class="modal-body">
                <form id="registrationForm" onsubmit="registerUser(event)">
                    <input type="hidden" name="action" value="register">
                    <div style="display: flex">
                        <div class="mb-3">
                            <label class="form-label">Email:</label>
                            <input type="email" class="form-control" name="email" placeholder="Enter your email" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Password:</label>
                            <input type="password" class="form-control" name="password" placeholder="Enter your password" required>
                        </div>
                    </div>
                    <div style="display: flex">
                        <div class="mb-3">
                            <label class="form-label">Full Name:</label>
                            <input type="text" class="form-control" name="fullName" placeholder="Enter your full name">
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Mobile:</label>
                            <input type="text" class="form-control" name="mobile" placeholder="Enter your mobile">
                        </div>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Gender:</label>
                        <select class="form-select" name="gender" required>
                            <option value="" disabled selected>Select gender</option>
                            <option value="1">Male</option>
                            <option value="2">Female</option>
                        </select>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Verification Code:</label>
                        <div class="d-flex">
                            <input type="text" class="form-control me-2" name="verificationCode" placeholder="Enter verification code" required>
                            <button id="sendCodeButton" type="button" class="btn btn-primary" onclick="sendVerificationCode()">Send Code</button>
                        </div>
                    </div>
                    <button type="submit" class="btn btn-primary">Register</button>
                    <div id="error-message" class="alert alert-danger mt-4" role="alert" style="display: none;">
                        Invalid verification code
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<script>
    var countdownTimer;

    function sendVerificationCode() {
        var email = $("input[name='email']").val();
        var button = document.querySelector("#sendCodeButton");

        // Clear any previous error messages
        $("#error-message").hide();

        disableInputs(true);  // Disable inputs immediately
        button.textContent = "Sending...";
        button.disabled = true;

        if (email) {
            $.ajax({
                url: "SendVerificationCode",
                type: "POST",
                data: { email: email },
                success: function(response) {
                    button.textContent = "Sent";
                    alert("Verification code sent to your email.");
                    startCountdown();
                },
                error: function(xhr) {
                    if (xhr.status === 409) { // 409 Conflict
                        $("#error-message").text("Email already existed!").show();
                    } else {
                        alert("Failed to send verification code. Please check the email address and try again.");
                    }
                    button.textContent = "Send Code";
                    button.disabled = false;
                    disableInputs(false);  // Re-enable inputs on error
                }
            });
        } else {
            alert("Please enter your email.");
            button.textContent = "Send Code";
            button.disabled = false;
            disableInputs(false);  // Re-enable inputs if email is empty
        }
    }

    function startCountdown() {
        var countdown = 60;
        var button = document.querySelector("#sendCodeButton");

        countdownTimer = setInterval(function() {
            if (countdown > 0) {
                button.textContent = "Resend: " + countdown + "s";
                countdown--;
            } else {
                clearInterval(countdownTimer);
                button.textContent = "Send Code";
                button.disabled = false; // Enable button after countdown
                disableInputs(false); // Re-enable inputs after countdown
            }
        }, 1000);
    }

    function disableInputs(readonly) {
    $("input[name='email']").prop('readonly', readonly);
}

    function registerUser(event) {
        event.preventDefault();
        $.ajax({
            url: "VerificationUserRegister",
            type: "POST",
            data: $("#registrationForm").serialize(),
            success: function (response) {
                if (response === "success") {
                    window.location.href = "blankPageForDelayAfterRegister.jsp";
                } else {
                    $("#error-message").text(response).show();
                    if (response === "Expired code") {
                        clearInterval(countdownTimer);
                        $("#sendCodeButton").prop('disabled', false).text('Send Code');
                        disableInputs(false); // Re-enable inputs if code expired
                    }
                }
            },
            error: function () {
                alert("Registration failed. Please try again.");
            }
        });
    }
</script>