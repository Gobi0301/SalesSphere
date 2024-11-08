<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ page trimDirectiveWhitespaces="true"%>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>

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
</script>

<script type="text/javascript">
	function emailCheck() {
		var email = document.getElementById("emailIdInput").value;
		document.getElementById("submit").disabled = false;
		if (email != '') {
			$.ajax({
				url : "check_email",
				type : "GET",
				data : 'email=' + email,
				success : function(result) {

					if (result == true) {
						$("#input_error").html("Email Already Exists");
						document.getElementById("submit").disabled = true;
						$("#input_error").show();
						$("#emailIdInput").css({
							"border" : "1px solid red",
						});

					} else {
						$("#input_error").hide();
						$("#emailIdInput").css({
							"border" : "",
							"background" : ""
						});
					}
				}
			});
		}
	};
</script>

<script>
	$(document)
			.ready(
					function() {

						$('#firstnameId').focus();

						$('#submit')
								.click(
										function(e) {

											//First Name..
											var isValid = true;
											var firstName = $('#firstnameId')
													.val();
											if (firstName == '') {
												isValid = false;
												$("#firstNameErr").show();
												$("#firstNameErr")
														.html(
																"Please Enter First Name");
												$("#firstnameId").css({
													"border" : "1px solid red",
												});
											} else if (!/^[a-zA-Z\s]*$/g
													.test(firstName)) {
												$("#firstNameErr").show();
												$("#firstNameErr")
														.html(
																"Please Enter Character Only");
												isValid = false;
											} else {
												$('#firstNameErr').hide();
												$('#firstnameId').css({
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

											//lastName..

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
												$("#lastNameErr")
														.html(
																"Please Enter Character Only");
												isValid = false;
											} else {
												$("#lastNameErr").hide();
												$("#lastNameInput").css({
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
																"Please enter the emilid")

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

											//Primary MobileNo....
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
											} else if (!/^[0-9]{1,10}$/
													.test(mobileNo)) {
												isValid = false;
												$("#mobileerror").show();
												$("#mobileerror")
														.html(
																"Must Enter Numbers only");
											} else if (mobileNo.length != 10) {
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

											// Contact Number Validations						
											var contactNo = $("#contactId")
													.val();
											if (contactNo == '') {

												isValid = false;
												$("#contacterror").show();
												$("#contacterror")
														.html(
																"Please Enter Contact number ");
												$("#contactId").css({
													"border" : "1px solid red",
												});
											} else if (!/^[0-9]{1,11}$/
													.test(contactNo)) {
												isValid = false;
												$("#contacterror").show();
												$("#contacterror")
														.html(
																"Please Enter Numbers Only");
											} else if (contactNo.length != 11) {
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

											//Company Name..

											var companyName = $(
													"#companyNameId").val();
											if (companyName == '') {

												$("#companyNameError").show();
												$("#companyNameError")
														.html(
																"Please enter Company Name")

												$("#companyNameId").css({
													"border" : "1px solid red",
												});
												isValid = false;
											} else {
												$("#companyNameError").hide();
												$("#companyNameId").css({
													"border" : "",
													"background" : ""
												});
											}

											//Industry Type..

											var industryType = $(
													"#IndustryTypeId").val();
											if (industryType == '') {

												$("#industryTypeError").show();
												$("#industryTypeError")
														.html(
																"Please enter Industry Name")

												$("#IndustryTypeId").css({
													"border" : "1px solid red",
												});

												isValid = false;
											} else if (!/^[a-zA-Z\s]*$/g
													.test(companyName)) {
												$("#industryTypeError").show();
												$("#industryTypeError")
														.html(
																"Please Enter Character Only");
												isValid = false;
											} else {
												$("#industryTypeError").hide();
												$("#IndustryTypeId").css({
													"border" : "",
													"background" : ""
												});
											}

											//Website Empty..  //curently working
											/* var website = $(
													"#companyWebsiteInput")
													.val();
											if (website == '') {

												$("#websiteErr").show();
												$("#websiteErr").html(
														"Please enter Website")

												$("#companyWebsiteInput").css({
													"border" : "1px solid red",
												});
												isValid = false;
											} else if (!/(http(s)?:\\)?([\w-]+\.)+[\w-]+[.com|.in|.org]+(\[\?%&=]*)?/
													.test(website)) {
												$("#websiteErr").show();
												$("#websiteErr")
														.html(
																"Must Enter Valid WebSite Format");
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
											} */

											/* var siteRegex = /(http(s)?:\\)?([\w-]+\.)+[\w-]+[.com|.in|.org]+(\[\?%&=]*)?/

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
											}  */

											if (isValid == false)
												e.preventDefault();
										});
					});
</script>


<div class="contact-form-wrapper">

	<div class="box-list">
		<div class="item">
			<div class="row ">
				<div class="text-center underline">
					<h3>Edit Customer</h3>
				</div>
				<br>
				<!-- form post a job -->
				<form:form id="myForm" method="post" class="login-form clearfix"
					commandName="updateProfile" modelAttribute="updateProfile"
					action="edit-customers">
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
										<form:select type="text" path="loginBO.id" id="assignId"
											class="form-control required">
											<%-- <c:if test="${not empty userName}">
												<form:option value="${id}">${userName}</form:option>
											</c:if> --%>
											<%-- <form:option value="Select">-- Select --   </form:option> --%>
											<form:options items="${userBOList}" itemLabel="name"
												itemValue="id" />
										</form:select>
										<form:errors path="userName" cssClass="error" />
										<div id="assignIdErr" style="color: red;"></div>

									</div>
								</div>
								<form:hidden path="sNo" />

								<div class="col-xs-3" style="display: block" id="addCompany">
									<label>First Name</label><font style="color: red;"> * </font>
									<form:input type="text" class="form-control  required"
										path="firstName" id="firstnameId" placeholder="First Name"
										maxlength="100" />
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
										placeholder="Email Address" onchange="emailCheck()"
										maxlength="100" readonly="true" />
									<form:errors path="emailAddress" cssClass="error" />
									<div id="input_error" style="color: red"></div>
								</div>
							</div>
						</div>

						<div class="form-group">
							<div class="row clearfix">
								<div class="col-xs-3">
									<label>Primary Mobile Number</label><font style="color: red;">
										* </font>
									<form:input type="text" class="form-control " path="mobileNo"
										maxlength="10" id="mobileId" placeholder="MobileNo"
										readonly="true" />
									<form:errors path="mobileNo" cssClass="error" />
									<div id="mobileerror" style="color: red"></div>
								</div>

								<div class="col-xs-3" style="display: block" id="addCompany">
									<label>Contact No</label><font style="color: red;"> * </font>
									<form:input type="text" id="contactId" class="form-control "
										path="contactNo" maxlength="11" placeholder="ContactNo"
										readonly="true" />
									<form:errors path="contactNo" cssClass="error" />
									<div id="contacterror" style="color: red"></div>
								</div>

								<div class="col-xs-3" style="display: block" id="addCompany">
									<label>Company</label><font style="color: red;"> * </font>
									<form:input type="text" id="companyNameId"
										class="form-control " path="companyName"
										placeholder="Company Name" maxlength="100" />
									<form:errors path="companyName" cssClass="error" />
									<div id="companyNameError" style="color: red"></div>
								</div>

								<div class="col-xs-3 ">
									<label>Industry</label><font style="color: red;"> * </font>
									<form:input type="text" class="form-control "
										path="industryType" id="IndustryTypeId"
										placeholder="IndustryType" maxlength="100" />
									<form:errors path="industryType" cssClass="error" />
									<div id="industryTypeError" style="color: red"></div>
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
										id="mobileId" placeholder="Address" maxlength="100" />
									<form:errors path="address" cssClass="error" />
									<div id="mobileerror" style="color: red"></div>
								</div>
								<div class="col-sm-3">
									<label>Product Name<span class="font10 text-danger">*</span></label>
									<form:select path="productServieBO.serviceName"
										id="serviceId" class="form-control required">
										<c:if test="${not empty serviceNames}">
											<%-- <form:option value="${serviceId}">${serviceNames}</form:option> --%>
										
										 <%-- <form:option value="Select">-- Select --   </form:option> --%>
										<form:options items="${productList}" itemLabel="serviceName"
											itemValue="serviceId" /> 
											</c:if>
									</form:select>
									<form:errors path="productServieBO.serviceName"
										cssClass="error" />
									<div id="serviceNameErr" style="color: red;"></div>
								</div>
							</div>
							<%-- <div class="col-xs-3">
								<label>Product Name</label><font style="color: red;"> * </font>
								<form:select path="serviceIdList" class="form-control required">
									<form:options items="${productList }" itemLabel="serviceName"
										itemValue="serviceId"></form:options>
								</form:select>
							</div> --%>


						</div>
					</div>
					<div style="text-align: right; margin-right: 31px">
						<button type="submit" id="submit"
							class="btn btn-t-primary btn-theme lebal_align mt-20">Submit</button>
						<a href=view-customers?page=1><span
							class="btn btn-t-primary btn-theme lebal_align mt-20">Cancel</span></a>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</div>
