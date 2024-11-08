
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ page trimDirectiveWhitespaces="true"%>
<link href="resources/css/jquery-ui-1.10.4.custom.css" rel="stylesheet">
<script type="text/javascript" src="resources/js/jquery-ui.min.js"></script>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<!-- 
<script>
	$(function() {
		$("#startDateInput").datepicker({
			changeMonth : true,
			changeYear : true,
			numberOfMonths : 1,
			onSelect : function(selected) {
				var dt = new Date(selected);
				dt.setDate(dt.getDate());
				$("#endDateInput").datepicker("option", "minDate", dt);
			}
		});
		$("#endDateInput").datepicker({
			changeMonth : true,
			changeYear : true,
			numberOfMonths : 1,
			onSelect : function(selected) {
				var dt = new Date(selected);
				var dt2 = new Date(selected);
				dt.setDate(dt.getDate() - 1);
				dt2.setDate(dt.getDate() + 1);
			}
		});

	});
</script> -->
<script type="text/javascript">
var isEmailIdCheck = false;
var isMobileNumberCheck = false;
var isContactNumberCheck = false;

	function emailCheck() {
		var email = document.getElementById("emailIdInput").value;
		if (email != '') {
			$.ajax({
				url : "check_email",
				type : "GET",
				data : 'email=' + email,
				success : function(result) {

					if (result == true) {
						$("#input_error").html("Email Already Exists");
						$("#input_error").show();
						$("#emailIdInput").css({
							"border" : "1px solid red",
						});
						isEmailIdCheck = false;	 
					} else {
						$("#input_error").hide();
						$("#emailIdInput").css({
							"border" : "",
							"background" : ""
						});
						isEmailIdCheck = true;
					}
					enableSubmitButton();
				}
			});
		}
	};

	function contactCheck() {
		var contact = document.getElementById("contactId").value;
		if (contact != '') {
			$.ajax({
				url : "check_contact",
				type : "GET",
				data : 'contact=' + contact,
				success : function(result) {

					if (result == true) {
						$("#contacterror").html("Contact Number Already Exists");
						$("#contacterror").show();
						$("#contactId").css({
							"border" : "1px solid red",
						});
						 isContactNumberCheck = false;
						 
					} else {
						$("#contacterror").hide();
						$("#contactId").css({
							"border" : "",
							"background" : ""
						});
					 isContactNumberCheck = true;
					}
					enableSubmitButton();
				}
			});
		}
	};

	function mobileCheck() {
		var mobile = document.getElementById("mobileId").value;
		if (mobile != '') {
			$.ajax({
				url : "check_mobile",
				type : "GET",
				data : 'mobile=' + mobile,
				success : function(result) {

					if (result == true) {
						$("#mobileerror").html("Mobile Number Already Exists");
						$("#mobileerror").show();
						$("#mobileId").css({
							"border" : "1px solid red",
						});
						 isMobileNumberCheck = false; 
					} else {
						$("#mobileerror").hide();
						$("#mobileId").css({
							"border" : "",
							"background" : ""
						});
						 isMobileNumberCheck = true;
					}
					enableSubmitButton();
				}
			});
		}
	};
	function enableSubmitButton() {
        if (isEmailIdCheck  && isMobileNumberCheck && isContactNumberCheck ) {
            document.getElementById("btnSubmit").disabled = false;
        } else {
            document.getElementById("btnSubmit").disabled = true;
        }
    }
</script>
<script>
	$(document)
			.ready(
					function() {
						$('#btnSubmit')
								.click(
										function(e) {

											var isValid = true;
											var firstname = $("#firstNameInput")
											.val();
									if (firstname == '') {
										isValid = false;
										$("#firstNameErr").show();
										$("#firstNameErr")
												.html(
														"Please enter the first name")
										$("#firstNameInput").css({
											"border" : "1px solid red",
										});
										isValid = false;
									} else if (!/^[a-zA-Z\s]*$/g
											.test(firstname)) {
										$("#firstNameErr").show();
										$("#firstNameErr").html
												(
														"Please Enter Character Only");
										isValid = false;
									} else {
										$("#firstNameErr").hide();
										$("#firstNameInput").css({
											"border" : "",
											"background" : ""
										});
									}

									var lastname = $("#lastNameInput")
									.val();
							if (lastname == '') {

								$("#lastNameErr").show();
								$("#lastNameErr")
										.html(
												"Please enter the last name")

								$("#lastNameInput").css({
									"border" : "1px solid red",
								});
								isValid = false;
							} else if (!/^[a-zA-Z\s]*$/g
									.test(lastname)) {
								$("#lastNameErr").show();
								$("#lastNameErr").html
										(
												"Please Enter Character Only");
								isValid = false;
							} else {
								$("#lastNameErr").hide();
								$("#lastNameInput").css({
									"border" : "",
									"background" : ""
								});
							}

							// Phone Number Validations						
							var contactNo = $("#contactId")
									.val();
							if (contactNo == '') {

								isValid = false;
								$("#contacterror").show();
								$("#contacterror").html(
										"Please Enter Contact number ");
								$("#contactId").css({
									"border" : "1px solid red",
								});
							}else if (!/^[0-9]{1,11}$/.test(contactNo)) {
								isValid = false;
								$("#contacterror").show();
								$("#contacterror")
										.html(
												"Please Enter Numbers Only");
							}else if (contactNo.length != 11) {
								isValid = false;
								$("#contacterror").show();
								$("#contacterror")
										.html(
												"Must enter 11 Digits Numbers");
							} else {
								$("#contacterror").hide();
								$("#contactId").css({
									"border" : "",
									"background" : ""
								});
							}

							var mobileNo = $("#mobileId").val();
							if (mobileNo == '') {

								isValid = false;
								$("#mobileerror").show();
								$("#mobileerror")
										.html(
												"Please Enter Primary Mobile Number");
								$("#mobileId").css({
									"border" : "1px solid red",
								});
							}else if (!/^[0-9]{1,10}$/.test(mobileNo)) {
								isValid = false;
								$("#mobileerror").show();
								$("#mobileerror")
										.html(
												"Must Enter Numbers only");
							}else if (mobileNo.length != 10) {
								isValid = false;
								$("#mobileerror").show();
								$("#mobileerror")
										.html(
												"Must enter 10 Digits Numbers");
							} else {
								$("#mobileerror").hide();
								$("#mobileId").css({
									"border" : "",
									"background" : ""
								});

							}

							var address = $("#address").val();
							if (address == '') {

								isValid = false;
								$("#addressErr").show();
								$("#addressErr").html(
										"Please Enter address");
								$("#address").css({
									"border" : "1px solid red",
								});
							} else {
								$("#addressErr").hide();
								$("#address").css({
									"border" : "",
									"background" : ""
								});
							}

							//assign employee
							 
							var userName = $('#assignId').val();
							if (userName == 'Select') {
								isValid = false;
								$("#assignIdErr").show();
								$("#assignIdErr")
										.html(
												"Please Select Assign Name");
								$("#assignId").css({
									"border" : "1px solid red",

								});

							} else {
								$('#assignIdErr').hide();
								$('#assignId').css({

									"border" : "",
									"background" : ""
								});
							}

							//Product name
							 
							var name = $('#productId').val();
							if (name == 'Select') {
								isValid = false;
								$("#productIdErr").show();
								$("#productIdErr")
										.html(
												"Please enter Product name");
								$("#productId").css({
									"border" : "1px solid red",

								});

							} else {
								$('#productIdErr').hide();
								$('#productId').css({

									"border" : "",
									"background" : ""
								});
							}

							 
							var name = $('#datepicker').val();
							if (name == '') {
								isValid = false;
								$("#warrantyDateErr").show();
								$("#warrantyDateErr")
										.html(
												"Please enter warranty Date");
								$("#datepicker").css({
									"border" : "1px solid red",

								});

							} else {
								$('#warrantyDateErr').hide();
								$('#datepicker').css({

									"border" : "",
									"background" : ""
								});
							}

							// Email Validations 			
							var emailValidations = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;
							var emailAddress = $(
									"#emailIdInput").val();
							if (emailAddress == '') {

								isValid = false;
								$("#input_error").show();
								$("#input_error")
										.html(
												"Please enter the emailid")

								$("#emailIdInput").css({
									"border" : "1px solid red",
								});
							} else if (!emailValidations
									.test(emailAddress)) {
								$("#input_error").show();
								$("#input_error")
										.html(
												"Please Enter Valid Email ");
								isValid = false;
							} else {
								$("#input_error").hide();
								$("#emailIdInput").css({
									"border" : "",
									"background" : ""
								});
							}

							

						   var siteRegex = /(http(s)?:\\)?([\w-]+\.)+[\w-]+[.com|.in|.org]+(\[\?%&=]*)?/

								 var website = $(
										"#companyWebsiteInput")
										.val() ;

								    if (website != '' &&!siteRegex
										.test(website)) {

									$("#websiteErr").show();
									$("#websiteErr")
											.html(
													"Please Enter Valid WebSite ");
									$("#companyWebsiteInput").css({
										"border" : "1px solid red",
									});
									isValid = false;
								} else {

									$("#websiteErr").hide();
									$("#companyWebsiteInput").css({
										"border" : "",
										"background" : ""
									});
								} 

								  //Company Name..

									var companyName = $(
											"#companyNameId").val();
									if (companyName == '') {

										$("#companyNameErr").show();
										$("#companyNameErr")
												.html(
														"Please enter Company Name")

										$("#companyNameId").css({
											"border" : "1px solid red",
										});
										isValid = false;
									} else {
										$("#companyNameErr").hide();
										$("#companyNameId").css({
											"border" : "",
											"background" : ""
										});
									} 

									//Industry Type..

									var industryType = $(
											"#IndustryTypeId").val();
									if (industryType == '') {

										$("#IndustryTypeErr").show();
										$("#IndustryTypeErr")
												.html(
														"Please enter Industry Name")

										$("#IndustryTypeId").css({
											"border" : "1px solid red",
										});

										isValid = false;
									} else if (!/^[a-zA-Z\s]*$/g
											.test(industryType)) {
										$("#IndustryTypeErr").show();
										$("#IndustryTypeErr")
												.html(
														"Please Enter Character Only");
										isValid = false;
									} else {
										$("#IndustryTypeErr").hide();
										$("#IndustryTypeId").css({
											"border" : "",
											"background" : ""
										});
									}

											if (isValid == false)
												e.preventDefault();

										});
					});
</script>
<script>
	$(function() {
		 var today = new Date(); 
		$("#datepicker").datepicker({
			changeMonth : true,
			changeYear : true,
			numberOfMonths : 1,
			minDate: today,
			onSelect : function(selected) {
				var dt = new Date(selected);
				dt.setDate(dt.getDate());
				$("#endDateInput").datepicker("option", "minDate", dt);
			}
		});
	});
</script>
<div class="contact-form-wrapper">

	<div class="box-list">
		<div class="item">
			<div class="row ">
				<div class="text-center underline">
					<h3>Create Customer</h3>

				</div>
				<br>
				<!-- form post a job -->
				<form:form id="myForm" method="post" class="login-form clearfix"
					commandName="createProfile" modelAttribute="createProfile"
					action="create-customers">
					<!-- Start Warning Message  -->
					<center>
						<div class="warning" style="width: 100%; text-align: left;">
							<c:if test="${not empty infoMessage}">
								<div class="alert alert-info">
									<button type="button" class="close" data-dismiss="alert"
										aria-label="Close">
										<span aria-hidden="true">&times;</span>
									</button>
									<strong>Info!</strong>
									<c:out value="${infoMessage}"></c:out>
								</div>
							</c:if>
						</div>
					</center>
					<!-- End Warning Message  -->
					<div id="updateExperience1">
						<div class="form-group">
							<div class="row clearfix">




								<div class="col-sm-3">
									<div class="form-group">
										<label>Assign Employee<span class="font10 text-danger">*</span></label>
										<form:select type="text" path="userName" id="assignId"
											class="form-control required">
											<form:option value="Select">-- Select --   </form:option>
											<form:options items="${userBOList}" itemLabel="name"
												itemValue="id" />
										</form:select>
										<form:errors path="userName" cssClass="error" />
										<div id="assignIdErr" style="color: red;"></div>
									</div>
								</div>


								<div class="col-xs-3" style="display: block" id="addCompany">
									<label>First Name</label><font style="color: red;"> * </font>
									<form:input type="text" class="form-control " path="firstName"
										id="firstNameInput" placeholder="First Name" maxlength="100" />
									<form:errors path="firstName" cssClass="error" />
									<div id="firstNameErr" style="color: red;"></div>
								</div>

								<div class="col-xs-3">
									<label>Last Name</label><font style="color: red;"> * </font>
									<form:input type="text" class="form-control " path="lastName"
										id="lastNameInput" placeholder="Last Name" maxlength="100" />
									<form:errors path="lastName" cssClass="error" />
									<div id="lastNameErr" style="color: red;"></div>
								</div>
								<div class="col-xs-3">
									<label>Email </label><font style="color: red;"> * </font>
									<form:input type="email" class="form-control "
										path="emailAddress" id="emailIdInput"
										placeholder="Email Address" maxlength="100"
										onchange="emailCheck()" />
									<form:errors path="emailAddress" cssClass="error" />
									<div id="input_error" style="color: red"></div>
								</div>
							</div>
						</div>

						<div class="form-group">
							<div class="row clearfix">
								<div class="col-xs-3">
									<label>Primary Mobile No</label><font style="color: red;">
										* </font>
									<form:input type="text" class="form-control " path="mobileNo"
										id="mobileId" placeholder="MobileNo" maxlength="10" onchange="mobileCheck()" />
									<form:errors path="mobileNo" cssClass="error" />
									<div id="mobileerror" style="color: red"></div>
								</div>

								<div class="col-xs-3" style="display: block" id="addCompany">
									<label>Contact No</label><font style="color: red;"> * </font>
									<form:input type="text" id="contactId" class="form-control "
										path="contactNo" placeholder="ContactNo" maxlength="11" onchange="contactCheck()" />
									<form:errors path="contactNo" cssClass="error" />
									<div id="contacterror" style="color: red"></div>
								</div>

								<div class="col-xs-3" style="display: block" id="addCompany">
									<label>Company</label><font style="color: red;"> * </font>
									<form:input type="text" id="companyNameId"
										class="form-control " path="companyName"
										placeholder="Company Name" maxlength="100" />
									<form:errors path="companyName" cssClass="error" />
									<div id="companyNameErr" style="color: red"></div>
								</div>

								<div class="col-xs-3 ">
									<label>Industry</label><font style="color: red;"> * </font>
									<form:input type="text" class="form-control "
										path="industryType" id="IndustryTypeId"
										placeholder="IndustryType" maxlength="100" />
									<form:errors path="industryType" cssClass="error" />
									<div id="IndustryTypeErr" style="color: red"></div>
								</div>
							</div>
						</div>

						<div class="form-group">
							<div class="row clearfix">
								<div class="col-xs-3">
									<label>WebSite </label><font style="color: red;"> </font>
									<form:input type="text" class="form-control " path="website"
										id="companyWebsiteInput" placeholder="WebSite" maxlength="100" />
									<form:errors path="website" cssClass="error" />
									<div id="websiteErr" style="color: red"></div>
								</div>

								<div class="col-xs-3">
									<label>Address</label><font style="color: red;"> * </font>
									<form:input type="text" class="form-control " path="address"
										id="address" placeholder="Address" maxlength="100" />
									<form:errors path="address" cssClass="error" />
									<div id="addressErr" style="color: red"></div>
								</div>

								<div class="col-xs-3">
									<label>Product Name</label><font style="color: red;"> *
									</font>
									<form:select path="productServieBO.serviceName" id="productId"
										class="form-control required">

										<form:option value="Select"></form:option>
										<form:options items="${productList }" itemLabel="serviceName"
											itemValue="serviceId"></form:options>
									</form:select>
									<form:errors path="productServieBO.serviceName" cssClass="error" />
									<div id="productIdErr" style="color: red"></div>
								</div>

								<div class="col-sm-3">
									<div class="form-group">
										<label>Warranty Date <span class="font10 text-danger">*</span></label>
										<form:input type="text" name="warrantyDate"  readonly="true"
											path="warrantyDate" placeholder="warrantyDate"
											id="datepicker" class="form-control required" maxlength="150" />
										<form:errors path="warrantyDate" class="error" />
										<div id="warrantyDateErr" style="color: red;"></div>
									</div>
								</div>
							</div>

						</div>
					</div>
					<div style="text-align: right; margin-right: 31px">
						<button type="submit" id="btnSubmit"
							class="btn btn-t-primary btn-theme lebal_align mt-20">Submit</button>
						<a href="view-customers"><span
							class="btn btn-t-primary btn-theme lebal_align mt-20">Cancel</span></a>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</div>
