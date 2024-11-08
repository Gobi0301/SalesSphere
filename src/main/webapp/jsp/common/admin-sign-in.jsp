<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!--favicon-->
<!-- <link rel="apple-touch-icon"
	href="resources/theme/images/logo.png"> -->
<link rel="shortcut icon" href="resources/theme/images/favicon.ico"
	type="image/x-icon">

<!-- bootstrap -->
<link href="resources/plugins/bootstrap-3.3.2/css/bootstrap.min.css"
	rel="stylesheet">

<!-- Icons -->
<link
	href="resources/plugins/font-awesome-4.2.0/css/font-awesome.min.css"
	rel="stylesheet">

<!-- lightbox -->
<link href="resources/plugins/magnific-popup/magnific-popup.css"
	rel="stylesheet">


<!-- Themes styles-->
<link href="resources/theme/css/theme.css" rel="stylesheet">
<!-- Your custom css -->
<link href="resources/theme/css/theme-custom.css" rel="stylesheet">
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<!-- <script>
	$(document).ready(function() {
		$('form:first *:input[type!=hidden]:first').focus();
	});
</script> -->
<!-- <script>
	$(document).ready(function() {
		$('#btnSubmit').click(function(e) {
			var isValid = true;
			$('input[type="text"]').each(function() {
				if ($.trim($(this).val()) == '') {
					isValid = false;
					$(this).css({
						"border" : "1px solid red",
					});
				} else {
					$(this).css({
						"border" : "",
						"background" : ""
					});

				}
			});

			if (isValid == false)
				e.preventDefault();
		});
	});

	$(document).ready(function() {
		$('#btnSubmit').click(function(e) {
			var isValid1 = true;
			$('input[type="password"]').each(function() {
				if ($.trim($(this).val()) == '') {
					isValid1 = false;
					$(this).css({
						"border" : "1px solid red",
					});
				} else {
					$(this).css({
						"border" : "",
						"background" : ""
					});
				}
			});
			if (isValid1 == false)
				e.preventDefault();
		});
	});

	$(document).ready(function() {
		$('#btnSubmit').click(function(e) {
			var isValid2 = true;
			$('input[type="email"]').each(function() {
				if ($.trim($(this).val()) == '') {
					isValid2 = false;
					$(this).css({
						"border" : "1px solid red",
					});
				} else {
					$(this).css({
						"border" : "",
						"background" : ""
					});
				}
			});
			if (isValid2 == false)
				e.preventDefault();
		});
	});

	$(document).ready(function() {
		$('#close').click(function(e) {
			location.reload();
		});
	});
</script> -->
<script>
    $(document).ready(function() {
        $('#myForm').submit(function(event) {
            // Reset previous error messages
            $('#emailAddressIdErr').text('');
            $('#passwordIdErr').text('');
            $('#emailAddressInput').css('border-color', '');
            $('#passwordInput').css('border-color', '');

            // Validate email address
            var emailAddress = $('#emailAddressInput').val().trim();
            if (emailAddress === '') {
                $('#emailAddressIdErr').text('Please enter an email address');
                $('#emailAddressInput').css('border-color', 'red');
                event.preventDefault();
            } else if (!validateEmail(emailAddress)) {
                $('#emailAddressIdErr').text('Please enter a valid email address');
                $('#emailAddressInput').css('border-color', 'red');
                event.preventDefault();
            }

            // Validate password
            var password = $('#passwordInput').val().trim();
            if (password === '') {
                $('#passwordIdErr').text('Please enter a password');
                $('#passwordInput').css('border-color', 'red');
                event.preventDefault();
            } else if (password.length < 4) {
                $('#passwordIdErr').text('Password must be at least 4 characters long');
                $('#passwordInput').css('border-color', 'red');
                event.preventDefault();
            }
        });

        // Email validation function
        function validateEmail(email) {
            var re = /\S+@\S+\.\S+/;
            return re.test(email);
        }
    });
</script> 

<!-- wrapper page -->
<div class="jobseekerlogin-img-align">
    <div class="search-over-lay">
        <div class="body-content clearfix" style="height: auto;">
            <div class="block-section">
                <div class="slide-content-login">
                    <div class="container logincontain">
                        <h2 class="text-center titleunderlinestatic" style="font-size: 18px; font-weight: 600;">Sign In</h2>
                        <div class="panel-md login-panel">
                            <div class="panel-body">
                            <c:if test="${param.error}">
                             <div class="alert alert-danger" role="alert" style="font-size: 13px; padding: 8px 9px 5px 10px; margin-top: 15px;" >
                             Invalid Username or Password.please try again
                              <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                                <span aria-hidden="true">&times;</span>
                                            </button>
                              </div>
                             </c:if>
                                <div class="warning">
                                
                                     <c:if test="${not empty errormessage}">
                                        <div class="alert alert-danger" role="alert" style="font-size: 12px; padding: 8px 9px 5px 10px; margin-top: 15px;">
                                            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                                <span aria-hidden="true">&times;</span>
                                            </button>
                                            <strong>Info!</strong>
                                            <c:out value="${errormessage}"></c:out>
                                        </div>
                                    </c:if> 
                                    </div>

							<form:form id="myForm" method="post" class="login-form clearfix"
								commandName="adminLogin">
								<div class="form-group">
									<label></label>
									<form:input name="emailAddress" type="email"
										path="emailAddress" class="form-control"
										id="emailAddressInput" placeholder="Email" maxlength="50"
										style="height: 35px;border-color:#fff;" />
									<form:errors path="emailAddress" cssClass="error" />
								    <div id="emailAddressIdErr" style="color: red;"></div>	
								</div>
								<div class="form-group">
									<label></label>
									<form:input type="password" class="form-control"
										path="password" id="passwordInput" placeholder="Password"
										maxlength="8" style="height: 35px;border-color:#fff;" />
									<form:errors path="password" cssClass="error" />
									<div id="passwordIdErr" style="color: red;"></div>
								</div>

								<div class="form-group">
									<label style="color: white; font-weight: normal; float: left;">
										<form:checkbox path="rememberMe" /> Remember me?
									</label>

								</div>
								<div class="form-group">
									<input type="hidden" value="${fellow}" name="fellowStatus" />
								</div>
								<div class="form-group no-margin">
									<button type="submit" id="btnSubmit"
										class="btn btn-theme btn-lg btn-t-primary btn-block"
										style="background-color: #6cc644; padding: 9px 30px;">Log
										In</button>
								</div>
								<div class="white-space-20"></div>
							</form:form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	</div>
</div>