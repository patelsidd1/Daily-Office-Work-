<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!doctype html>
<html lang="en">
<head>
    <%@ include file="./base.jsp" %>
    <title>OTP Verification</title>
    <style>
        body {
            background-image: url('https://img.freepik.com/free-photo/blur-field-texture_1160-907.jpg?t=st=1739258428~exp=1739262028~hmac=d21a3fc98454b1382dade84d4d6347f363fab0b462f516f381f1d62478075075&w=740');
            background-size: cover;
            background-position: center;
            background-repeat: no-repeat;
            background-attachment: fixed;
        }
        .container {
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            height: 60vh;
        }
        #timer {
            font-size: 1.2rem;
            font-weight: bold;
            color: red;
            margin-bottom: 10px;
        }
    </style>

</head>
<body>
    <h1 class="text-center mb-3" style="font-size: 2.5rem; font-weight: bold; color: #333; text-transform: capitalize; text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.1); padding: 10px;">
        OTP <span style="color: #007bff; font-weight: bold;">Verification</span>
    </h1>

    <div class="container">
        <div id="timer"></div>
        <form id="otpForm" action="${pageContext.request.contextPath}/otp/verifyotp" method="post"
              style="width: 350px; padding: 20px; border-radius: 10px; background: #f8f9fa; box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);">
            <div class="form-group">
                <label for="otp" style="font-weight: bold; font-size: 1.1rem;">Enter The OTP</label>
                <input type="text" class="form-control" id="otp" name="otp" placeholder="Enter OTP" required
                       style="border-radius: 5px; padding: 10px; font-size: 1rem;">
            </div>
            <button type="submit" id="verifyBtn" class="btn btn-primary btn-block "
                    style="padding: 10px; font-size: 1.2rem; font-weight: bold; border-radius: 5px;">Verify OTP</button>
        </form>
    </div>

    <c:if test="${not empty error}">
        <div style="color: #721c24; background-color: #f8d7da; border: 1px solid #f5c6cb; padding: 10px; border-radius: 5px; font-size: 1rem; text-align: center; font-weight: 500; margin-top: 10px;">
            ${error}
        </div>
    </c:if>
       <script>
              var timeLeft = 30;
                     var elem = document.getElementById('timer');

                     var timerId = setInterval(countdown, 1000);

                     function countdown() {
                         if (timeLeft == 0) {
                             clearTimeout(timerId);
                             elem.innerHTML = "<strong>OTP Expired </strong>"; // Message in same div
                            verifyBtn.disabled = true;
                         } else {
                             elem.innerHTML = 'Time Left : ' + timeLeft + ' sec';
                             timeLeft--;
                         }
                     }

       </script>

</body>
</html>