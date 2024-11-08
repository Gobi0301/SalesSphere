<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>



<style>
.title {
	background-color: #2a3f54;
	padding: 10px;
	color: #fff;
}
</style>

<script type="text/javascript">
	function phoneNumberCheck() {
		var phoneNumber = document.getElementById("contactId").value;
		document.getElementById("submit").disabled = false;
		if (phoneNumber != '') {
			$.ajax({
				url : "check_phonenumber",
				type : "GET",
				data : 'phoneNumber=' + phoneNumber,
				success : function(result) {

					if (result == true) {
						$("#contactIdErr")
								.html("Contact Number Already Exists");
						document.getElementById("submit").disabled = true;
						$("#contactIdErr").show();
					} else {
						$("#contactIdErr").hide();
					}
				}
			});
		}
	};
</script>
<script type="text/javascript">
	function mobilenumbercheck() {
		var mobilenumber = document.getElementById("mobileNoId").value;
		document.getElementById("submit").disabled = false;

		if (mobilenumber != '') {
			$.ajax({
				url : "check_mobilenumber",
				type : "GET",
				data : 'mobilenumber=' + mobilenumber,
				success : function(result) {

					if (result == true) {
						$("#errormobileNumber").html(
								"Mobile Number Already Exists");
						document.getElementById("submit").disabled = true;
						$("#errormobileNumber").show();
					} else {
						$("#errormobileNumber").hide();
					}
				}
			});
		}
	};
</script>
<script type="text/javascript">
	function companyemailcheck() {
		var companyemail = document.getElementById("CompanyEmailinput").value;
		document.getElementById("submit").disabled = false;

		if (companyemail != '') {
			$.ajax({
				url : "check_companyemail",
				type : "GET",
				data : 'companyemail=' + companyemail,
				success : function(result) {

					if (result == true) {
						$("#companyEmailErr").html(
								"Company Email Already Exists");
						document.getElementById("submit").disabled = true;
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
	function companywebsitecheck() {
		var companywebsite = document.getElementById("CompanyWebsiteinput").value;
		document.getElementById("submit").disabled = false;

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
								document.getElementById("submit").disabled = true;
								$("#websiteErr").show();
							} else {
								$("#websiteErr").hide();
							}
						}
					});
		}
	};
</script>
<script type="text/javascript">
	function companygstcheck() {
		var companygst = document.getElementById("CompanyGstNoinput").value;
		document.getElementById("submit").disabled = false;

		if (companygst != '') {
			$.ajax({
				url : "check_companygst",
				type : "GET",
				data : 'companygst=' + companygst,
				success : function(result) {

					if (result == true) {
						$("#companyGstNo").html("Company Gst Already Exists");
						document.getElementById("submit").disabled = true;
						$("#companyGstNo").show();
					} else {
						$("#companyGstNo").hide();
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
						$('#submit')
								.click(
										function(e) {

											//companyname
											var isValid = true;
											var companyName = $("#CompanyName")
													.val();

											if (companyName == '') {
												isValid = false;
												$("#companyNameErr").show();
												$("#companyNameErr")
														.html(
																"Please Enter The Company Name")
												$("#CompanyName").css({
													"border" : "1px solid red",
												});
												//isValid = false;
											} else if (!/^[a-zA-Z\s]*$/g
													.test(companyName)) {
												$("#companyNameErr").show();
												$("#companyNameErr")
														.html(
																"Please Enter Character Only");
												isValid = false;
											} else {
												$("#companyNameErr").hide();
												$("#CompanyName").css({
													"border" : "",
													"background" : ""
												});
											}
											//companyOwner
											var companyOwner = $(
													"#CompanyOwnerinput").val();
											if (companyOwner == '') {
												isValid = false;
												$("#companyOwnerErr").show();
												$("#companyOwnerErr")
														.html(
																"Please Enter The company Owner")
												$("#CompanyOwnerinput").css({
													"border" : "1px solid red",
												});
												isValid = false;
											} else if (!/^[a-zA-Z\s]*$/g
													.test(companyOwner)) {
												$("#companyOwnerErr").show();
												$("#companyOwnerErr")
														.html(
																"Please Enter Character Only");
												isValid = false;
											} else {
												$("#companyOwnerErr").hide();
												$("#CompanyOwnerinput").css({
													"border" : "",
													"background" : ""
												});
											}
											//mobile
											var mobile = $("#mobileNoId").val();
											if (!/^[0-9]{1,10}$/.test(mobile)) {
												isValid = false;
												$("#errormobileNumber").show();
												$("#errormobileNumber")
														.html(
																"Please Enter Numbers Only");
												$("#mobileNoId").css({
													"border" : "1px solid red",
												});
											} else if (mobile.length < 10) {
												isValid = false;
												$("#errormobileNumber").show();
												$("#errormobileNumber")
														.html(
																"Must enter 10 Digits Numbers");
												$("#mobileNoId").css({
													"border" : "1px solid red",
												});
											} else {
												$("#errormobileNumber").hide();
												$("#mobileNoId").css({
													"border" : "",
													"background" : ""

												});
											}
											//email
											var emailValidations = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;
											var companyEmail = $(
													"#CompanyEmailinput").val();
											if (companyEmail == '') {

												isValid = false;
												$("#companyEmailErr").show();
												$("#companyEmailErr").html(
														"Please Enter Email")
												$("#CompanyEmailinput").css({
													"border" : "1px solid red",
												});
											} else if (!emailValidations
													.test(companyEmail)) {
												$("#companyEmailErr").show();
												$("#companyEmailErr")
														.html(
																"Please Enter Valid Email ");
												$("#CompanyEmailinput").css({
													"border" : "1px solid red",
												});
												isValid = false;
											} else {
												$("#companyEmailErr").hide();
												$("#CompanyEmailinput").css({
													"border" : "",
													"background" : ""
												});
											}

										 	var password = $('#passwordId').val();
											if (password == '') {
												isValid = false;
												$("#passwordIdErr").show();
												$("#passwordIdErr").html("Please enter password");
												$("#passwordId").css({
													"border" : "1px solid red",

												});

											} else {
												$('#passwordIdErr').hide();
												$('#passwordId').css({

													"border" : "",
													"background" : ""
												});
											}

											 
											var confirmPassword = $('#confirmPasswordId').val();
											if (confirmPassword == '') {
												isValid = false;
												$("#confirmPasswordErr").show();
												$("#confirmPasswordErr").html("Please enter confirm Password");
												$("#confirmPasswordId").css({
													"border" : "1px solid red",

												});

											} else {
												$('#confirmPasswordErr').hide();
												$('#confirmPasswordId').css({

													"border" : "",
													"background" : ""
												});
											}
											var password = $("#passwordId").val();
								            var confirmPassword = $("#confirmPasswordId").val();
								            if (password != confirmPassword) {
								                alert("Passwords do not match.");
								                isValid = false;
								            }
																					
											//industrytype
											var industryType = $(
													'#CompanyIndustrytypeinput')
													.val();
											if (industryType == 'Select') {
												isValid = false;
												$("#IndustryTypeErr").show();
												$("#IndustryTypeErr")
														.html(
																"Please Select Industry Type");
												$("#CompanyIndustrytypeinput")
														.css(
																{
																	"border" : "1px solid red",

																});

											} else {
												$('#IndustryTypeErr').hide();
												$('#CompanyIndustrytypeinput')
														.css({

															"border" : "",
															"background" : ""
														});
											}
											//street
											var street = $('#street').val();
											if (street == '') {
												isValid = false;
												$("#streetErr").show();
												$("#streetErr").html(
														"Please Enter Address");
												$("#street").css({
													"border" : "1px solid red",

												});

											} else {
												$('#streetErr').hide();
												$('#street').css({

													"border" : "",
													"background" : ""
												});
											}
											//city
											var city = $('#city').val();
											if (city == '') {
												isValid = false;
												$("#cityErr").show();
												$("#cityErr").html(
														"Please Enter City");
												$("#city").css({
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
												$('#city').css({

													"border" : "",
													"background" : ""
												});
											}
											//state
											var state = $('#state').val();
											if (state == '') {
												isValid = false;
												$("#stateErr").show();
												$("#stateErr").html(
														"Please Enter State");
												$("#state").css({
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
												$('#state').css({

													"border" : "",
													"background" : ""
												});
											}
											//country
											var country = $('#country').val();
											if (country == '') {
												isValid = false;
												$("#countryErr").show();
												$("#countryErr").html(
														"Please Enter Country");
												$("#country").css({
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
												$('#country').css({

													"border" : "",
													"background" : ""
												});
											}
											//postalcode
											var postalCode = $('#postalCode')
													.val();
											if (!/^[0-9]{1,11}$/
													.test(postalCode)) {
												isValid = false;
												$("#postalCodeErr").show();
												$("#postalCodeErr")
														.html(
																"Please Enter PostalCode");
												$("#postalCode").css({
													"border" : "1px solid red",

												});

											} else if (postalCode.length < 6) {
												isValid = false;
												$("#postalCodeErr").show();
												$("#postalCodeErr")
														.html(
																"Must enter 6 Digits Numbers");
												$("#postalCode").css({
													"border" : "1px solid red",
												});
											} else {
												$('#postalCodeErr').hide();
												$('#postalCode').css({

													"border" : "",
													"background" : ""
												});
											}
											//district
											var district = $('#district').val();
											if (district == '') {
												isValid = false;
												$("#districtErr").show();
												$("#districtErr")
														.html(
																"Please Enter District");
												$("#district").css({
													"border" : "1px solid red",

												});

											} else if (!/^[a-zA-Z\s]*$/g
													.test(district)) {
												isValid = false;
												$("#districtErr").show();
												$("#districtErr")
														.html(
																"Please Enter Characters Only");
												$("#district").css({
													"border" : "1px solid red",
												});
											} else {
												$('#districtErr').hide();
												$('#district').css({

													"border" : "",
													"background" : ""
												});
											}

											//GST
											//var isValid=true;
											var gst = $('#CompanyGstNoinput')
													.val();
											if (gst == '') {
												isValid = false;
												$("#companyGstNo").show();
												$("#companyGstNo")
														.html(
																"Please Enter Gst Number");
												$("#CompanyGstNoinput").css({
													"border" : "1px solid red",

												});

											} else if (!/^[a-zA-Z0-9]*$/
													.test(gst)) {
												isValid = false;
												$("#companyGstNo").show();
												$("#companyGstNo")
														.html(
																"Please Enter Correct Format(Ex:sedr12345df34s3)");
												$("#CompanyGstNoinput").css({
													"border" : "1px solid red",
												});
											} else if (gst.length < 15) {
												isValid = false;
												$("#companyGstNo").show();
												$("#companyGstNo")
														.html(
																"Must enter 15 GST Numbers");
												$("#CompanyGstNoinput").css({
													"border" : "1px solid red",
												});
											} else {
												$('#companyGstNo').hide();
												$('#CompanyGstNoinput').css({

													"border" : "",
													"background" : ""
												});
											}

											//contact
											var contact = $("#contactId").val();
											if (contact == '') {

												isValid = false;
												$("#contactIdErr").show();
												$("#contactIdErr")
														.html(
																"Please Enter The Contact Number")
												$("#contactId").css({
													"border" : "1px solid red",
												});
											} else if (!/^[0-9]{1,11}$/
													.test(contact)) {
												isValid = false;
												$("#contactIdErr").show();
												$("#contactIdErr")
														.html(
																"Please Enter Numbers Only");
												$("#contactId").css({
													"border" : "1px solid red",
												});
											} else if (contact.length != 11) {
												isValid = false;
												$("#contactIdErr").show();
												$("#contactIdErr")
														.html(
																"Must Enter 11 Digits Numbers");
												$("#contactId").css({
													"border" : "1px solid red",
												});
											} else {
												$("#contactIdErr").hide();
												$("#contactId").css({
													"border" : "",
													"background" : ""
												});
											}

											//website
											const urlPattern = /^(https?:\/\/)?(www\.)?[a-zA-Z0-9-]+\.(com|in|org)$/;

											var website = $(
													'#CompanyWebsiteinput')
													.val();
											if (website == '') {
												isValid = false;
												$("#websiteErr").show();
												$("#websiteErr").html(
														"Please Enter Website");
												$("#CompanyWebsiteinput").css({
													"border" : "1px solid red",
												});
											} else if (!urlPattern
													.test(website)) {
												var isValid = false;
												$("#websiteErr").show();
												$("#websiteErr")
														.html(
																"Please Enter Correct Format");
												$("#CompanyWebsiteinput").css({
													"border" : "1px solid red",
												});
											} else {
												$('#websiteErr').hide();
												$('#CompanyWebsiteinput').css({
													"border" : "",
													"background" : ""
												});
											}

											if (isValid == false)
												e.preventDefault();

										});
					});
</script>

<script type="text/javascript">
    $(function() {
        $("#btnSubmit").click(function() {
            var password = $("#passwordId").val();
            var confirmPassword = $("#confirmPasswordId").val();
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
						<h3>Create Company</h3>
					</div>
					<br>
					<form:form action="create-company" method="post"
						modelAttribute="Company" commandName="Company">
						<h3 class="title">Company Details</h3>
						<div class="box-list">
							<div class="item">
								<div class="row">

									<div class="col-sm-12">
										<div class="col-sm-4">
											<div class="form-group">
												<label class="leftAlign">Company Name <span
													class="font10 text-danger">*</span></label>
												<form:input type="text" path="companyName" id="CompanyName"
													class="form-control required" placeholder="Company Name"
													text-align="left" />
												<form:errors path="companyName" class="error" />
												<div id="companyNameErr" style="color: red;"></div>
											</div>
										</div>

										<div class="col-sm-4">
											<div class="form-group">
												<label>Contact Person <span
													class="font10 text-danger">*</span></label>
												<form:input type="text" path="contactPerson"
													id="CompanyOwnerinput" class="form-control required"
													placeholder="Company Owner" />
												<form:errors path="contactPerson" class="error" />
												<div id="companyOwnerErr" style="color: red;"></div>
											</div>
										</div>

										<div class="col-sm-4">
											<div class="form-group">
												<label>Company GST No <span
													class="font10 text-danger">*</span></label>
												<form:input type="text" path="companyGstNo"
													id="CompanyGstNoinput" class="form-control required"
													placeholder="Company GST No" maxlength="15"
													onchange="companygstcheck()" />
												<form:errors path="companyGstNo" class="error" />
												<div id="companyGstNo" style="color: red;"></div>
											</div>

										</div>
									</div>

									<div class="col-sm-12">
										<div class="col-sm-4">
											<div class="form-group">
												<label>E-Mail <span class="font10 text-danger">*</span></label>
												<form:input type="text" path="companyEmail"
													id="CompanyEmailinput" class="form-control required"
													placeholder="Company Email" onchange="companyemailcheck()" />
												<form:errors path="companyEmail" class="error" />
												<div id="companyEmailErr" style="color: red;"></div>
											</div>
										</div>

										<div class="col-sm-4">
											<div class="form-group">
												<label>Contact Number <span
													class="font10 text-danger">*</span></label>
												<form:input type="ntext" path="contactNumber" id="contactId"
													class="form-control required" placeholder="Company number"
													maxlength="11" onchange="phoneNumberCheck()" />
												<form:errors path="contactNumber" class="error" />
												<div id="contactIdErr" style="color: red;"></div>
											</div>
										</div>

										<div class="col-sm-4">
											<div class="form-group">
												<label>Mobile Number <span
													class="font10 text-danger">*</span></label>
												<form:input type="ntext" path="mobileNumber" id="mobileNoId"
													class="form-control required" placeholder="Mobile Number"
													maxlength="10" onchange="mobilenumbercheck()" />
												<form:errors path="mobileNumber" class="error" />
												<div id="errormobileNumber" style="color: red;"></div>
											</div>
										</div>
									</div>



									<div class="col-sm-12">
										<div class="col-sm-4">
											<div class="form-group">
												<label>Website <span class="font10 text-danger">*</span></label>
												<form:input type="text" path="website"
													id="CompanyWebsiteinput" class="form-control required"
													placeholder="Website" onchange="companywebsitecheck()" />
												<form:errors path="website" class="error" />
												<div id="websiteErr" style="color: red;"></div>
											</div>
										</div>

		<div class="col-sm-4">
						<div class="form-group">
							<label path="password"> Password <span
								class="font10 text-danger">*</span></label>
							<form:input type="password" path="password" id="passwordId"
								class="form-control required" placeholder="password"
								maxlength="8" />
							<form:errors path="password" class="error" />
							<div id="passwordIdErr" style="color: red;"></div>
						</div>
					</div>

										<div class="col-sm-4">
						<div class="form-group">
							<label path="Confirm password"> Confirm password <span
								class="font10 text-danger">*</span></label>
							<form:input type="password" path="confirmPassword"
								id="confirmPasswordId" class="form-control required"
								placeholder="confirm password" maxlength="8" />
							<form:errors path="confirmPassword" class="error" />
							<div id="confirmPasswordErr" style="color: red;"></div>

						</div>
					</div>
									</div>

									<div class="col-sm-12">
										<div class="col-sm-4">
											<div class="form-group">
												<label>Industry Type<span class="font10 text-danger">*</span></label>
												<!-- <label class="hidden-xs">&nbsp;</label> -->
												<form:select type="text" path="industryType"
													id="CompanyIndustrytypeinput" class="form-control required">
													<form:option value="Select">--Select Status--</form:option>
													<form:option value="IT Sector">IT Sector</form:option>
													<form:option value="Non IT Sector">Non IT Sector</form:option>
													<form:option value="realEstate">Real Estate</form:option>
												</form:select>
												<form:errors path="industryType" class="error" />
												<div id="IndustryTypeErr" style="color: red"></div>

											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<br>


						<h3 class="title">Company Address Details</h3>
						<div class="box-list">
							<div class="item">
								<div class="row">

									<div class="row">
										<div class="col-sm-4">
											<div class="form-group">
												<label>Address <span class="font10 text-danger">*</span></label>
												<form:input type="text" path="street" id="street"
													class="form-control required" placeholder="Street" />
												<form:errors path="street" class="error" />
												<div id="streetErr" style="color: red;"></div>
											</div>
										</div>

										<div class="col-sm-4">
											<div class="form-group">
												<label>City<span class="font10 text-danger">*</span></label>
												<form:input type="text" path="city" id="city"
													class="form-control required" placeholder="City" />
												<form:errors path="city" class="error" />
												<div id="cityErr" style="color: red;"></div>
											</div>
										</div>

										<div class="col-sm-4">
											<div class="form-group">
												<label>District<span class="font10 text-danger">*</span></label>
												<form:input type="text" path="district" id="district"
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
												<form:input type="text" path="state" id="state"
													class="form-control required" placeholder="State" />
												<form:errors path="state" class="error" />
												<div id="stateErr" style="color: red;"></div>
											</div>
										</div>

										<div class="col-sm-4">
											<div class="form-group">
												<label>Country <span class="font10 text-danger">*</span></label>
												<form:input type="text" path="country" id="country"
													class="form-control required" placeholder="Country" />
												<form:errors path="country" class="error" />
												<div id="countryErr" style="color: red;"></div>
											</div>
										</div>

										<div class="col-sm-4">
											<div class="form-group">
												<label>Postal Code <span class="font10 text-danger">*</span></label>
												<form:input type="ntext" path="postalCode" id="postalCode"
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
							<a href="view-company"><span
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