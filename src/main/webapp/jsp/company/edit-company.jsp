<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>


<link href="resources/css/jquery-ui-1.10.4.custom.css" rel="stylesheet">
<script type="text/javascript" src="resources/js/jquery-ui.min.js"></script>
<style>
.title {
	background-color: #2a3f54;
	padding: 10px;
	color: #fff;
}
</style>
<!-- <script type="text/javascript">
	function companyemailcheck() {
		var companyemail = document.getElementById("emailId").value;
		document.getElementById("btnsubmit").disabled = false;

		if (companyemail != '') {
			$.ajax({
				url : "check_companyemail",
				type : "GET",
				data : 'companyemail=' + companyemail,
				success : function(result) {

					if (result == true) {
						$("#companyEmailErr").html(
								"Company Email Already Exists");
						document.getElementById("btnsubmit").disabled = true;
						$("#companyEmailErr").show();
					} else {
						$("#companyEmailErr").hide();
					}
				}
			});
		}
	};
</script>

<script type="text/javascript">
	function phoneNumberCheck() {
		var phoneNumber = document.getElementById("contactNum").value;
		document.getElementById("btnsubmit").disabled = false;
		if (phoneNumber != '') {
			$.ajax({
				url : "check_phonenumber",
				type : "GET",
				data : 'phoneNumber=' + phoneNumber,
				success : function(result) {

					if (result == true) {
						$("#contactErr").html("Contact Number Already Exists");
						document.getElementById("btnsubmit").disabled = true;
						$("#contactErr").show();
					} else {
						$("#contactErr").hide();
					}
				}
			});
		}
	};
</script>

<script type="text/javascript">
	function mobilenumbercheck() {
		var mobilenumber = document.getElementById("mobileId").value;
		document.getElementById("btnsubmit").disabled = false;

		if (mobilenumber != '') {
			$.ajax({
				url : "check_mobilenumber",
				type : "GET",
				data : 'mobilenumber=' + mobilenumber,
				success : function(result) {

					if (result == true) {
						$("#mobileErr").html("Mobile Number Already Exists");
						document.getElementById("btnsubmit").disabled = true;
						$("#mobileErr").show();
					} else {
						$("#mobileErr").hide();
					}
				}
			});
		}
	};
</script>

<script type="text/javascript">
	function companygstcheck() {
		var companygst = document.getElementById("gstId").value;
		document.getElementById("btnsubmit").disabled = false;

		if (companygst != '') {
			$.ajax({
				url : "check_companygst",
				type : "GET",
				data : 'companygst=' + companygst,
				success : function(result) {

					if (result == true) {
						$("#companyGstNoErr")
								.html("Company Gst Already Exists");
						document.getElementById("btnsubmit").disabled = true;
						$("#companyGstNoErr").show();
					} else {
						$("#companyGstNoErr").hide();
					}
				}
			});
		}
	};
</script>

<script type="text/javascript">
	function companywebsitecheck() {
		var companywebsite = document.getElementById("websiteId").value;
		document.getElementById("btnsubmit").disabled = false;

		if (companywebsite != '') {
			$
					.ajax({
						url : "check_companywebsite",
						type : "GET",
						data : 'companywebsite=' + companywebsite,
						success : function(result) {

							if (result == true) {
								$("#websiteErr").html(
										"Company Website Already Exists");
								document.getElementById("btnsubmit").disabled = true;
								$("#websiteErr").show();
								
							} else {
								$("#websiteErr").hide();
							}
						}
					});
		}
	};
</script> -->

<script type="text/javascript">
	var isEmailValid = false;
	var isPhoneNumberValid = false;
	var isMobileNumberValid = false;
	var isCompanyGstValid = false;
	var isCompanyWebsiteValid = false;

	function companyemailcheck() {
		var companyemail = document.getElementById("emailId").value;
		if (companyemail != '') {
			$.ajax({
				url : "check_companyemail",
				type : "GET",
				data : 'companyemail=' + companyemail,
				success : function(result) {
					if (result == true) {
						$("#companyEmailErr").html(
								"Company Email Already Exists");
						$("#companyEmailErr").show();
						isEmailValid = false;
					} else {
						$("#companyEmailErr").hide();
						isEmailValid = true;
					}
					enableSubmitButton();
				}
			});
		}
	};

	function phoneNumberCheck() {
		var phoneNumber = document.getElementById("contactNum").value;
		if (phoneNumber != '') {
			$.ajax({
				url : "check_phonenumber",
				type : "GET",
				data : 'phoneNumber=' + phoneNumber,
				success : function(result) {
					if (result == true) {
						$("#contactErr").html("Contact Number Already Exists");
						$("#contactErr").show();
						isPhoneNumberValid = false;
					} else {
						$("#contactErr").hide();
						isPhoneNumberValid = true;
					}
					enableSubmitButton();
				}
			});
		}
	};

	function mobilenumbercheck() {
		var mobilenumber = document.getElementById("mobileId").value;
		if (mobilenumber != '') {
			$.ajax({
				url : "check_mobilenumber",
				type : "GET",
				data : 'mobilenumber=' + mobilenumber,
				success : function(result) {
					if (result == true) {
						$("#mobileErr").html("Mobile Number Already Exists");
						$("#mobileErr").show();
						isMobileNumberValid = false;
					} else {
						$("#mobileErr").hide();
						isMobileNumberValid = true;
					}
					enableSubmitButton();
				}
			});
		}
	};

	function companygstcheck() {
		var companygst = document.getElementById("gstId").value;
		if (companygst != '') {
			$.ajax({
				url : "check_companygst",
				type : "GET",
				data : 'companygst=' + companygst,
				success : function(result) {
					if (result == true) {
						$("#companyGstNoErr")
								.html("Company Gst Already Exists");
						$("#companyGstNoErr").show();
						isCompanyGstValid = false;
					} else {
						$("#companyGstNoErr").hide();
						isCompanyGstValid = true;
					}
					enableSubmitButton();
				}
			});
		}
	};

	function companywebsitecheck() {
		var companywebsite = document.getElementById("websiteId").value;
		if (companywebsite != '') {
			$
					.ajax({
						url : "check_companywebsite",
						type : "GET",
						data : 'companywebsite=' + companywebsite,
						success : function(result) {
							if (result == true) {
								$("#websiteErr").html(
										"Company Website Already Exists");
								$("#websiteErr").show();
								isCompanyWebsiteValid = false;
							} else {
								$("#websiteErr").hide();
								isCompanyWebsiteValid = true;
							}
							enableSubmitButton();
						}
					});
		}
	};

	function enableSubmitButton() {
		if (isEmailValid && isPhoneNumberValid && isMobileNumberValid
				&& isCompanyGstValid && isCompanyWebsiteValid) {
			document.getElementById("btnsubmit").disabled = false;
		} else {
			document.getElementById("btnsubmit").disabled = true;
		}
	}
</script>
<script>
	$(document)
			.ready(
					function() {
						$('#submit')
								.click(
										function(e) {

											//companyOwner
											var isValid = true;
											var companyName = $(
													"#companyNameId").val();

											if (companyName == '') {
												isValid = false;
												$("#companyNameErr").show();
												$("#companyNameErr")
														.html(
																"Please Enter The Company Name")
												$("#companyNameId").css({
													"border" : "1px solid red",
												});
												isValid = false;
											} else if (!/^[a-zA-Z\s]*$/g
													.test(companyName)) {
												$("#companyNameErr").show();
												$("#companyNameErr")
														.html(
																"Please Enter Character Only");
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

											//contact Person
											var contactPerson = $('#contactId')
													.val();
											if (contactPerson == '') {
												isValid = false;
												$("#contactPersonErr").show();
												$("#contactPersonErr")
														.html(
																"Please Enter contact Person");
												$("#contactId").css({
													"border" : "1px solid red",

												});

											} else if (!/^[a-zA-Z\s]*$/g
													.test(contactPerson)) {
												isValid = false;
												$("#contactPersonErr").show();
												$("#contactPersonErr")
														.html(
																"Please Enter Character Only");
												$("#contactId").css({
													"border" : "1px solid red",
												});
											} else {
												$('#contactPersonErr').hide();
												$('#contactId').css({

													"border" : "",
													"background" : ""
												});
											}

											//GST
											var gst = $('#gstId').val();
											if (gst == '') {
												isValid = false;
												$("#companyGstNoErr").show();
												$("#companyGstNoErr").html(
														"Please Enter Country");
												$("#gstId").css({
													"border" : "1px solid red",

												});

											} else if (!/^[a-zA-Z0-9]*$/
													.test(gst)) {
												isValid = false;
												$("#companyGstNoErr").show();
												$("#companyGstNoErr")
														.html(
																"Please Enter Correct Format(Ex:sedr12345df34s3)");
												$("#gstId").css({
													"border" : "1px solid red",
												});
											} else if (gst.length < 15) {
												isValid = false;
												$("#companyGstNoErr").show();
												$("#companyGstNoErr")
														.html(
																"Must enter 15 GST Numbers");
												$("#gstId").css({
													"border" : "1px solid red",
												});
											} else {
												$('#companyGstNoErr').hide();
												$('#gstId').css({

													"border" : "",
													"background" : ""
												});
											}

											//email
											var emailValidations = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;
											var companyEmail = $("#emailId")
													.val();
											if (companyEmail == '') {

												isValid = false;
												$("#companyEmailErr").show();
												$("#companyEmailErr").html(
														"Please Enter Email")
												$("#emailId").css({
													"border" : "1px solid red",
												});
											} else if (!emailValidations
													.test(companyEmail)) {

												$("#companyEmailErr").show();
												$("#companyEmailErr")
														.html(
																"Please Enter Valid Email ");
												$("#emailId").css({
													"border" : "1px solid red",
												});
												isValid = false;
											} else {
												$("#companyEmailErr").hide();
												$("#emailId").css({
													"border" : "",
													"background" : ""
												});
											}
											//website
											const urlPattern = /^(https?:\/\/)?(www\.)?[a-zA-Z0-9-]+\.(com|in|org)$/;

											var website = $('#websiteId').val();
											if (website == '') {
												isValid = false;
												$("#websiteErr").show();
												$("#websiteErr").html(
														"Please Enter Website");
												$("#websiteId").css({
													"border" : "1px solid red",
												});
											} else if (!urlPattern
													.test(website)) {
												var isValid = false;
												$("#websiteErr").show();
												$("#websiteErr")
														.html(
																"Please Enter Correct Format");
												$("#websiteId").css({
													"border" : "1px solid red",
												});
											} else {
												$('#websiteErr').hide();
												$('#websiteId').css({
													"border" : "",
													"background" : ""
												});
											}

											/* //Password 
											var pvalidation = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@#$!%*?&])[A-Za-z\d@#$!%*?&]{8,15}$/;
											var password = $("#passId").val();
											if (password == '') {
												isValid = false;
												$("#passErr").show();
												$("#passErr")
														.html(
																"Please Enter Valid details ");
												$("#passId").css({
													"border" : "1px solid red",
												});

											} else if (!pvalidation
													.test(password)) {
												isValid = false;
												$("#passErr").show();
												$("#passErr")
														.html(
																"Please Enter correct format ");
												$("#passId").css({
													"border" : "1px solid red",
												});
											} else {
												$("#passErr").hide();
												$("#passId").css({
													"border" : "",
													"background" : ""
												});
											}
											var cpvalidation = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@#$!%*?&])[A-Za-z\d@#$!%*?&]{8,15}$/;
											var confirmPassword = $(
													"#confirmId").val();
											if (confirmPassword == '') {
												isValid = false;
												$("#confirmPasswordInputErr")
														.show();
												$("#confirmPasswordInputErr")
														.html(
																"Please Enter Valid details ");
												$("#confirmId").css({
													"border" : "1px solid red",
												});
											} else if (!cpvalidation
													.test(confirmPassword)) {
												isValid = false;
												$("#confirmPasswordInputErr")
														.show();
												$("#confirmPasswordInputErr")
														.html(
																"Please Enter correct format ");
												$("#confirmId").css({
													"border" : "1px solid red",
												});
											} else {
												$("#confirmPasswordInputErr")
														.hide();
												$("#confirmId").css({
													"border" : "",
													"background" : ""
												});
											}
											var password1 = $("#passwordInput")
													.val();
											var confirmPassword1 = $(
													"#confirmId").val();
											if (password1 != confirmPassword1) {
												isValid = false;
												$("#confirmPasswordInputErr")
														.show();
												$("#confirmPasswordInputErr")
														.html(
																"Password Do Not Match. ");
												$("#confirmId").css({
													"border" : "1px solid red",
												});
											} */

											var password = $('#passId').val();
											if (password == '') {
												isValid = false;
												$("#passErr").show();
												$("#passErr")
														.html(
																"Please enter password");
												$("#passId").css({
													"border" : "1px solid red",

												});

											} else {
												$('#passErr').hide();
												$('#passId').css({

													"border" : "",
													"background" : ""
												});
											}

											var confirmPassword = $(
													'#confirmId').val();
											if (confirmPassword == '') {
												isValid = false;
												$("#confirmPasswordInputErr")
														.show();
												$("#confirmPasswordInputErr")
														.html(
																"Please enter confirm Password");
												$("#confirmId").css({
													"border" : "1px solid red",

												});

											} else {
												$('#confirmPasswordInputErr')
														.hide();
												$('#confirmId').css({

													"border" : "",
													"background" : ""
												});
											}
											var password = $("#passId").val();
											var confirmPassword = $(
													"#confirmId").val();
											if (password != confirmPassword) {
												alert("Passwords do not match.");
												isValid = false;
											}

											//industrytype
											var industryType = $('#industryId')
													.val();
											if (industryType == 'Select') {
												isValid = false;
												$("#industryErr").show();
												$("#industryErr")
														.html(
																"Please Select Industry Type");
												$("#industryId").css({
													"border" : "1px solid red",

												});

											} else {
												$('#industryErr').hide();
												$('#industryId').css({

													"border" : "",
													"background" : ""
												});
											}

											//Address
											var street = $('#streetId').val();
											if (street == '') {
												isValid = false;
												$("#streetErr").show();
												$("#streetErr").html(
														"Please Enter Address");
												$("#streetId").css({
													"border" : "1px solid red",

												});

											} else {
												$('#streetErr').hide();
												$('#streetId').css({

													"border" : "",
													"background" : ""
												});
											}

											//city
											var city = $('#cityId').val();
											if (city == '') {
												isValid = false;
												$("#cityErr").show();
												$("#cityErr").html(
														"Please Enter City");
												$("#cityId").css({
													"border" : "1px solid red",

												});

											} else if (!/^[a-zA-Z\s]*$/g
													.test(city)) {
												isValid = false;
												$("#cityErr").show();
												$("#cityErr")
														.html(
																"Please Enter Character Only");
												$("#city").css({
													"border" : "1px solid red",
												});
											} else {
												$('#cityErr').hide();
												$('#cityId').css({

													"border" : "",
													"background" : ""
												});
											}

											//district
											var district = $('#districtId')
													.val();
											if (district == '') {
												isValid = false;
												$("#districtErr").show();
												$("#districtErr")
														.html(
																"Please Enter District");
												$("#districtId").css({
													"border" : "1px solid red",

												});

											} else if (!/^[a-zA-Z\s]*$/g
													.test(district)) {
												isValid = false;
												$("#districtErr").show();
												$("#districtErr")
														.html(
																"Please Enter Characters Only");
												$("#districtId").css({
													"border" : "1px solid red",
												});
											} else {
												$('#districtErr').hide();
												$('#districtId').css({

													"border" : "",
													"background" : ""
												});
											}

											//state
											var state = $('#stateId').val();
											if (state == '') {
												isValid = false;
												$("#stateErr").show();
												$("#stateErr").html(
														"Please Enter State");
												$("#stateId").css({
													"border" : "1px solid red",

												});

											} else if (!/^[a-zA-Z\s]*$/g
													.test(state)) {
												isValid = false;
												$("#stateErr").show();
												$("#stateErr")
														.html(
																"Please Enter Character Only");
												$("#state").css({
													"border" : "1px solid red",
												});
											} else {
												$('#stateErr').hide();
												$('#stateId').css({

													"border" : "",
													"background" : ""
												});
											}

											//country
											var country = $('#countryId').val();
											if (country == '') {
												isValid = false;
												$("#countryErr").show();
												$("#countryErr").html(
														"Please Enter Country");
												$("#countryId").css({
													"border" : "1px solid red",

												});

											} else if (!/^[a-zA-Z\s]*$/g
													.test(country)) {
												isValid = false;
												$("#countryErr").show();
												$("#countryErr")
														.html(
																"Please Enter Character Only");
												$("#country").css({
													"border" : "1px solid red",
												});
											} else {
												$('#countryErr').hide();
												$('#countryId').css({

													"border" : "",
													"background" : ""
												});
											}

											//postalcode
											var postalCode = $('#postalId')
													.val();
											if (!/^[0-9]{1,11}$/
													.test(postalCode)) {
												isValid = false;
												$("#postalCodeErr").show();
												$("#postalCodeErr")
														.html(
																"Please Enter Number only");
												$("#postalId").css({
													"border" : "1px solid red",

												});

											} else if (postalCode.length < 6) {
												isValid = false;
												$("#postalCodeErr").show();
												$("#postalCodeErr")
														.html(
																"Must enter 6 Digits Numbers");
												$("#postalId").css({
													"border" : "1px solid red",
												});
											} else {
												$('#postalCodeErr').hide();
												$('#postalId').css({

													"border" : "",
													"background" : ""
												});
											}

											//mobile
											var mobileNumber = $("#mobileId")
													.val();
											if (!/^[0-9]{1,10}$/
													.test(mobileNumber)) {
												isValid = false;
												$("#mobileErr").show();
												$("#mobileErr")
														.html(
																"Please Enter Numbers Only");
												$("#mobileId").css({
													"border" : "1px solid red",
												});
											} else if (mobileNumber.length < 10) {
												isValid = false;
												$("#mobileErr").show();
												$("#mobileErr")
														.html(
																"Must enter 10 Digits Numbers");
												$("#mobileId").css({
													"border" : "1px solid red",
												});
											} else {
												$("#mobileErr").hide();
												$("#mobileId").css({
													"border" : "",
													"background" : ""

												});
											}

											//contact
											var contactNumber = $("#contactNum")
													.val();
											if (contactNumber == '') {

												isValid = false;
												$("#contactErr").show();
												$("#contactErr")
														.html(
																"Please Enter The Contact Number")
												$("#contactNum").css({
													"border" : "1px solid red",
												});
											} else if (!/^[0-9]{1,11}$/
													.test(contactNumber)) {
												isValid = false;
												$("#contactErr").show();
												$("#contactErr")
														.html(
																"Please Enter Numbers Only");
												$("#contactNum").css({
													"border" : "1px solid red",
												});
											} else if (contactNumber.length != 11) {
												isValid = false;
												$("#contactErr").show();
												$("#contactErr")
														.html(
																"Must Enter 11 Digits Numbers");
												$("#contactNum").css({
													"border" : "1px solid red",
												});
											} else {
												$("#contactErr").hide();
												$("#contactNum").css({
													"border" : "",
													"background" : ""
												});
											}

											if (isValid == false) {
												e.preventDefault();
											}

										});
					});
</script>

<script type="text/javascript">
	$(function() {
		$("#btnsubmit").click(function() {
			var password = $("#passId").val();
			var confirmPassword = $("#confirmId").val();
			if (password != confirmPassword) {
				alert("Passwords do not match.");
				return false;
			}
			return true;
		});
	});
</script>

<body>

	<div class="contact-form-wrapper">

		<div class="box-list">
			<div class="item">
				<div class="row ">

					<div class="text-center underline">
						<h3>Edit Company</h3>
					</div>
					<br>
					<form:form action="edit-company" method="post"
						modelAttribute="CompanyBO" commandName="CompanyBO">
						<form:hidden path="companyId" />
						<form:hidden path="cachedata" />
						<h3 class="title">Company Details</h3>
						<div class="box-list">
							<div class="item">
								<div class="row">

									<div class="col-sm-12">
										<div class="col-sm-4">
											<div class="form-group">

												<label class="leftAlign">Company Name <span
													class="font10 text-danger">*</span></label>
												<form:input type="text" path="companyName"
													id="companyNameId" class="form-control required"
													placeholder="Company Name" text-align="left" />
												<form:errors path="companyName" class="error" />
												<div id="companyNameErr" style="color: red;"></div>

											</div>
										</div>

										<div class="col-sm-4">
											<div class="form-group">
												<label>Contact Person <span
													class="font10 text-danger">*</span></label>
												<form:input type="text" path="contactPerson" id="contactId"
													class="form-control required" placeholder="Contact person" />
												<form:errors path="contactPerson" class="error" />
												<div id="contactPersonErr" style="color: red;"></div>
											</div>
										</div>

										<div class="col-sm-4">
											<div class="form-group">
												<label>Company GST No <span
													class="font10 text-danger">*</span></label>
												<form:input type="text" path="companyGstNo" id='gstId'
													maxlength="15" class="form-control required"
													placeholder="Company GST No" onchange="companygstcheck()" />
												<form:errors path="companyGstNo" class="error" />
												<div id="companyGstNoErr" style="color: red;"></div>
											</div>
										</div>
									</div>

									<div class="col-sm-12">
										<div class="col-sm-4">
											<div class="form-group">
												<label>E-Mail <span class="font10 text-danger">*</span></label>
												<form:input type="text" path="companyEmail" id="emailId"
													onchange="companyemailcheck()"
													class="form-control required" placeholder="Company Email" />
												<form:errors path="companyEmail" class="error" />
												<div id="companyEmailErr" style="color: red;"></div>
											</div>
										</div>

										<div class="col-sm-4">
											<div class="form-group">
												<label>Contact Number <span
													class="font10 text-danger">*</span></label>
												<form:input type="ntext" path="contactNumber"
													id="contactNum" class="form-control required"
													placeholder="Contact number" onchange="phoneNumberCheck()"
													maxlength="11" />
												<form:errors path="contactNumber" class="error" />
												<div id="contactErr" style="color: red;"></div>
											</div>
										</div>

										<div class="col-sm-4">
											<div class="form-group">
												<label>Mobile Number <span
													class="font10 text-danger">*</span></label>
												<form:input type="ntext" path="mobileNumber" id="mobileId"
													maxlength="10" class="form-control required"
													placeholder="Mobile Number" onchange="mobilenumbercheck()" />
												<form:errors path="mobileNumber" class="error" />
												<div id="mobileErr" style="color: red;"></div>
											</div>
										</div>
									</div>



									<div class="col-sm-12">
										<div class="col-sm-4">
											<div class="form-group">
												<label>Website <span class="font10 text-danger">*</span></label>
												<form:input type="text" path="website" id="websiteId"
													class="form-control required" placeholder="Website"
													onchange="companywebsitecheck()" />
												<form:errors path="website" class="error" />
												<div id="websiteErr" style="color: red;"></div>
											</div>
										</div>

										<div class="col-sm-4">
											<div class="form-group">
												<label>Password <span class="font10 text-danger">*</span></label>
												<form:input type="password" path="password" id="passId"
													class="form-control required" placeholder="Password" />
												<form:errors path="password" class="error" />
												<div id="passErr" style="color: red;"></div>
											</div>
										</div>

										<div class="col-sm-4">
											<div class="form-group">
												<label>Confirm Password <span
													class="font10 text-danger">*</span></label>
												<form:input type="password" path="confirmPassword"
													id="confirmId" class="form-control required"
													placeholder="Password" />
												<form:errors path="confirmPassword" class="error" />
												<div id="confirmPasswordInputErr" style="color: red;"></div>
											</div>
										</div>
									</div>

									<div class="col-sm-12">
										<div class="col-sm-4">
											<div class="form-group">
												<label>Industry Type<span class="font10 text-danger">*</span></label>
												<!-- <label class="hidden-xs">&nbsp;</label> -->
												<form:select type="text" path="industryType" id="industryId"
													class="form-control required">
													<form:option value="Select">--Select Status--</form:option>
													<form:option value="IT Sector">IT Sector</form:option>
													<form:option value="Non IT Sector">Non IT Sector</form:option>
													<form:option value="realEstate">Real Estate</form:option>
												</form:select>
												<form:errors path="industryType" class="error" />
												<div id="industryErr" style="color: red;"></div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>

						<br>
						<h3 class="title">Address Details</h3>
						<div class="box-list">
							<div class="item">
								<div class="row">

									<div class="row">
										<div class="col-sm-4">
											<div class="form-group">
												<label>Address <span class="font10 text-danger">*</span></label>
												<form:input type="text" path="street" id="streetId"
													class="form-control required" placeholder="Street" />
												<form:errors path="street" class="error" />
												<div id="streetErr" style="color: red;"></div>
											</div>
										</div>

										<div class="col-sm-4">
											<div class="form-group">
												<label>City<span class="font10 text-danger">*</span></label>
												<form:input type="text" path="city" id="cityId"
													class="form-control required" placeholder="City" />
												<form:errors path="city" class="error" />
												<div id="cityErr" style="color: red;"></div>
											</div>
										</div>

										<div class="col-sm-4">
											<div class="form-group">
												<label>District<span class="font10 text-danger">*</span></label>
												<form:input type="text" path="district" id="districtId"
													class="form-control required" placeholder="District" />
												<form:errors path="district" class="error" />
												<div id="districtErr" style="color: red;"></div>
											</div>
										</div>
									</div>

									<div class="row">
										<div class="col-sm-4">
											<div class="form-group">
												<label>State <span class="font10 text-danger">*</span></label>
												<form:input type="text" path="state" id="stateId"
													class="form-control required" placeholder="State" />
												<form:errors path="state" class="error" />
												<div id="stateErr" style="color: red;"></div>
											</div>
										</div>

										<div class="col-sm-4">
											<div class="form-group">
												<label>Country <span class="font10 text-danger">*</span></label>
												<form:input type="text" path="country" id="countryId"
													class="form-control required" placeholder="Country" />
												<form:errors path="country" class="error" />
												<div id="countryErr" style="color: red;"></div>
											</div>
										</div>

										<div class="col-sm-4">
											<div class="form-group">
												<label>Postal Code <span class="font10 text-danger">*</span></label>
												<form:input type="ntext" path="postalCode" id="postalId"
													maxlength="6" class="form-control required"
													placeholder="PostalCode" />
												<form:errors path="postalCode" class="error" />
												<div id="postalCodeErr" style="color: red;"></div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>

						<br>
						<div style="text-align: right; margin-right: 31px">
							<button type="submit" id="submit"
								class="btn btn-t-primary btn-theme lebal_align mt-20">Submit</button>
							<a href="create-company?page=1"><span
								class="btn btn-t-primary btn-theme lebal_align mt-20">Cancel</span></a>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</div>

	<br>
	<br>
	<br>


</body>