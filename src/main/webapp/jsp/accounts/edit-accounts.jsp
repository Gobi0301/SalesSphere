<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>


<link href="resources/css/jquery-ui-1.10.4.custom.css" rel="stylesheet">
<script type="text/javascript" src="resources/js/jquery-ui.min.js"></script>
<script>
	bkLib.onDomLoaded(function() {
		new nicEditor({
			buttonList : [ 'fontSize', 'bold', 'italic', 'underline', 'ol',
					'ul', 'strikeThrough', 'html' ]
		}).panelInstance('inputAddress');
	});
</script>
<style>
.blockHead {
	position: relative;
}

.blockHead h4 {
	color: #333333;
}

.error {
	border: 1px solid red;
}

.title {
	background-color: #2a3f54;
	padding: 10px;
	color: #fff;
}
</style>
</style>
<script type="text/javascript">
	function emailcheck() {
		var emails = document.getElementById("email").value;
		document.getElementById("btnSubmit").disabled = false;
		if (emails != '') {
			$.ajax({
				url : "check_emails",
				type : "GET",
				data : 'emails=' + emails,
				success : function(result) {

					if (result == true) {
						$("#emailErr").html("Email Already Exists");
						document.getElementById("btnSubmit").disabled = true;
						$("#emailErr").show();
						$("#email").css({
							"border" : "1px solid red",
						});

					} else {
						$("#emailErr").hide();
						$("#email").css({
							"border" : "",
							"background" : ""
						});
					}
				}
			});
		}
	};
</script>
<script type="text/javascript">
	function contactnumbercheck() {
		var contact = document.getElementById("contactNo").value;
		document.getElementById("btnSubmit").disabled = false;
		if (contact != '') {
			$
					.ajax({
						url : "check_accountcontact",
						type : "GET",
						data : 'contact=' + contact,
						success : function(result) {

							if (result == true) {
								$("#contactNoErr").html(
										"ContactNumber Already Exists");
								document.getElementById("btnSubmit").disabled = true;
								$("#contactNoErr").show();
								$("#contactNo").css({
									"border" : "1px solid red",
								});

							} else {
								$("#contactNoErr").hide();
								$("#contactNo").css({
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

						$('#btnSubmit')
								.click(
										function(e) {
											var isValid = true;
											var name = $('#accountOwner').val();
											if (name == '') {
												isValid = false;
												$("#accountOwnerErr").show();
												$("#accountOwnerErr")
														.html(
																"Please enter account owner");
												$("#accountOwner").css({
													"border" : "1px solid red",

												});

											} else if (!/^[a-zA-Z\s]*$/g
													.test(name)) {
												isValid = false;
												$("#accountOwnerErr").show();
												$("#accountOwnerErr")
														.html(
																"Please enter Character Only");
												$("#accountOwner").css({
													"border" : "1px solid red",

												});

											} else {
												$('#accountOwnerErr').hide();
												$('#accountOwner').css({

													"border" : "",
													"background" : ""
												});
											}

											var comapany = $('#accountName')
													.val();
											if (comapany == '') {
												isValid = false;
												$("#accountNameErr").show();
												$("#accountNameErr")
														.html(
																"Please enter account name");
												$("#accountName").css({
													"border" : "1px solid red",

												});

											} else if (!/^[a-zA-Z\s]*$/g
													.test(comapany)) {
												isValid = false;
												$("#accountNameErr").show();
												$("#accountNameErr")
														.html(
																"Please enter Character Only");
												$("#accountName").css({
													"border" : "1px solid red",

												});

											} else {
												$('#accountNameErr').hide();
												$('#accountName').css({

													"border" : "",
													"background" : ""
												});
											}
											/* 			var isValid = true;
											 var parentAccount = $('#parentAccount').val();
											 if (parentAccount == '') {
											 isValid = false;
											 $("#parentAccountErr").show();
											 $("#parentAccountErr").html("Please enter parentAccount");
											 $("#parentAccount").css({
											 "border" : "1px solid red",

											 });

											 } else if (!/^[a-zA-Z\s]*$/g.test(comapany)) {
											 isValid = false;
											 $("#parentAccountErr").show();
											 $("#parentAccountErr").html("Please enter Character Only");
											 $("#parentAccount").css({
											 "border" : "1px solid red",

											 });

											 } else {
											 $('#parentAccountErr').hide();
											 $('#parentAccount').css({

											 "border" : "",
											 "background" : ""
											 });
											 }
											 */

											var contactNo = $('#contactNo')
													.val();
											var isValid = true; // Assuming you have defined isValid somewhere in your code

											if (contactNo == '0') {
												isValid = false;
												$("#contactNoErr")
														.show()
														.html(
																"Please enter a number only");
												$("#contactNo").css({
													"border" : "1px solid red"
												});
											} else if (!/^[0-9]{1,11}$/
													.test(contactNo)) {
												isValid = false;
												$("#contactNoErr")
														.show()
														.html(
																"Please enter numbers only");
												$("#contactNo").css({
													"border" : "1px solid red"
												});
											} else if (contactNo.length !== 11) {
												isValid = false;
												$("#contactNoErr")
														.show()
														.html(
																"Must enter 11-digit number");
												$("#contactNo").css({
													"border" : "1px solid red"
												});
											} else {
												// If the input is valid, hide the error message and reset the border
												$('#contactNoErr').hide();
												$('#contactNo').css({
													"border" : "",
													"background" : ""
												});
											}

											/* 	var isValid = true;
												var accountSource = $('#accountSource').val();
												if (accountSource == '0') {
													isValid = false;
													$("#accountSourceErr").show();
													$("#accountSourceErr").html("Please enter accountSource");
													$("#accountSource").css({
														"border" : "1px solid red",

													});

												}else if (!/^[a-zA-Z\s]*$/g.test(accountSource)) {
													isValid = false;
													$("#accountNameErr").show();
													$("#accountNameErr").html("Please enter Character Only");
													$("#accountName").css({
														"border" : "1px solid red",

													});

												} else {
													$('#accountSourceErr').hide();
													$('#accountSource').css({

														"border" : "",
														"background" : ""
													});
												} */

											var annualRevenue = $(
													'#annualRevenue').val();
											if (annualRevenue == '0.0'||annualRevenue == '') {
												isValid = false;
												$("#annualRevenueErr").show();
												$("#annualRevenueErr")
														.html(
																"Please enter annual revenue");
												$("#annualRevenue").css({
													"border" : "1px solid red",
												});
											} else if (!/^[0-9.]*$/
													.test(annualRevenue)) {
												isValid = false;
												$("#annualRevenueErr").show();
												$("#annualRevenueErr")
														.html(
																"Please enter only numeric characters");
												$("#annualRevenue").css({
													"border" : "1px solid red",
												});
											} else {
												$('#annualRevenueErr').hide();
												$('#annualRevenue').css({
													"border" : "",
													"background" : ""
												});
											}
											var description = $('#description')
													.val();
											if (description == '') {
												isValid = false;
												$("#descriptionErr").show();
												$("#descriptionErr")
														.html(
																"Please enter description");
												$("#description").css({
													"border" : "1px solid red",

												});

											} else {
												$('#descriptionErr').hide();
												$('#description').css({

													"border" : "",
													"background" : ""
												});
											}

											

											var type = $('#type').val();
											if (type == '') {
												isValid = false;
												$("#typeErr").show();
												$("#typeErr").html(
														"Please selet type");
												$("#type").css({
													"border" : "1px solid red",

												});

											} else {
												$('#typeErr').hide();
												$('#type').css({

													"border" : "",
													"background" : ""
												});
											}

											var salutation = $('#salutationId')
													.val();
											if (salutation == '0') {
												isValid = false;
												$("#salutationErr").show();
												$("#salutationErr")
														.html(
																"Please selet salutation");
												$("#salutationId").css({
													"border" : "1px solid red",

												});

											} else {
												$('#salutationErr').hide();
												$('#salutationId').css({

													"border" : "",
													"background" : ""
												});
											}

											var assignedTo = $('#assignedTo')
													.val();
											if (assignedTo == '') {
												isValid = false;
												$("#assignedToErr").show();
												$("#assignedToErr")
														.html(
																"Please selet Assigned To");
												$("#assignedTo").css({
													"border" : "1px solid red",

												});

											} else {
												$('#assignedToErr').hide();
												$('#assignedTo').css({

													"border" : "",
													"background" : ""
												});
											}

											var industry = $('#industry').val();
											if (industry == '') {
												isValid = false;
												$("#industryErr").show();
												$("#industryErr")
														.html(
																"Please enter industry");
												$("#industry").css({
													"border" : "1px solid red",

												});

											} else {
												$('#industryErr').hide();
												$('#industry').css({

													"border" : "",
													"background" : ""
												});
											}

											var street = $('#street').val();
											if (street == '') {
												isValid = false;
												$("#streetErr").show();
												$("#streetErr").html(
														"Please enter street");
												$("#street").css({
													"border" : "1px solid red",

												});

											}  else {
												$('#streetErr').hide();
												$('#street').css({

													"border" : "",
													"background" : ""
												});
											}

											var city = $('#city').val();
											if (city == '') {
												isValid = false;
												$("#cityErr").show();
												$("#cityErr").html(
														"Please enter city");
												$("#city").css({
													"border" : "1px solid red",

												});

											} else if (!/^[a-zA-Z\s]*$/g
													.test(city)) {
												isValid = false;
												$("#cityErr").show();
												$("#cityErr")
														.html(
																"Please enter Character Only");
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

											var state = $('#state').val();
											if (state == '') {
												isValid = false;
												$("#stateErr").show();
												$("#stateErr").html(
														"Please enter state");
												$("#state").css({
													"border" : "1px solid red",

												});

											} else if (!/^[a-zA-Z\s]*$/g
													.test(state)) {
												isValid = false;
												$("#stateErr").show();
												$("#stateErr")
														.html(
																"Please enter Character Only");
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

											var country = $('#country').val();
											if (country == '') {
												isValid = false;
												$("#countryErr").show();
												$("#countryErr").html(
														"Please enter country");
												$("#country").css({
													"border" : "1px solid red",

												});

											} else if (!/^[a-zA-Z\s]*$/g
													.test(country)) {
												isValid = false;
												$("#countryErr").show();
												$("#countryErr")
														.html(
																"Please enter Character Only");
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

											var noOfEmployess = $(
													'#noOfEmployess').val();
											if (noOfEmployess.trim() === '0') {
												isValid = false;
												$("#noOfEmployessErr").show();
												$("#noOfEmployessErr")
														.html(
																"Please enter the number of employees");
												$("#noOfEmployess").css({
													"border" : "1px solid red"
												});
											} else if (!/^[0-9]{1,11}$/
													.test(noOfEmployess)) {
												isValid = false;
												$("#noOfEmployessErr").show();
												$("#noOfEmployessErr")
														.html(
																"Please enter a  number only");
												$("#noOfEmployess").css({
													"border" : "1px solid red"
												});
											} else {
												$('#noOfEmployessErr').hide();
												$('#noOfEmployess').css({
													"border" : "",
													"background" : ""
												});
											}

											var countryCode = $('#countryCode')
													.val();
											if (countryCode === '0') {
												isValid = false;
												$("#countryCodeErr").show();
												$("#countryCodeErr")
														.html(
																"Please enter country code");
												$("#countryCode").css({
													"border" : "1px solid red",
												});
											} else if (!/^\+?[0-9]{1,11}$/
													.test(countryCode)) {
												isValid = false;
												$("#countryCodeErr").show();
												$("#countryCodeErr")
														.html(
																"Please enter numbers only");
												$("#countryCode").css({
													"border" : "1px solid red",
												});
											} else {
												$('#countryCodeErr').hide();
												$('#countryCode').css({
													"border" : "",
													"background" : ""
												});
											}
											var postalCode = $('#postalCode')
													.val();
											if (postalCode.trim() === '') {
												isValid = false;
												$("#postalCodeErr").show();
												$("#postalCodeErr")
														.html(
																"Please enter postalCode");
												$("#postalCode").css({
													"border" : "1px solid red",

												});

											} else if (!/^[0-9]{1,11}$/
													.test(postalCode)) {
												isValid = false;
												$("#postalCodeErr").show();
												$("#postalCodeErr")
														.html(
																"Please enter numbers only");
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

											var emailValidations = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;
											var email = $("#email").val();
											if (email == '') {
												isValid = false;
												$("#emailErr").show();
												$("#emailErr")
														.html(
																"Please enter an email");
												$("#email").css({
													"border" : "1px solid red",
												});
											} else if (!emailValidations
													.test(email)) {
												$("#emailErr").show();
												$("#emailErr")
														.html(
																"Please enter a valid email");
												$("#email").css({
													"border" : "1px solid red",
												});
												isValid = false;
											} else {
												$("#emailErr").hide();
												$("#email").css({
													"border" : "",
													"background" : ""
												});
											}
                                 //Parent Account....
								/*			var parentAccount = $('#parentAccount')
											.val();
									if (parentAccount == '') {
										isValid = false;
										$("#parentAccountErr").show();
										$("#parentAccountErr")
												.html(
														"Please enter Parent Account");
										$("#parentAccount").css({
											"border" : "1px solid red",

										});

									} else {
										$('#parentAccountErr').hide();
										$('#parentAccount').css({

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

	<br> <br> <br>
		<div class="box-list">
			<div class="item">
				<div class="row ">


					<div class="text-center underline">
						<h3>Edit Account</h3>

					</div>
					<br>

						<form:form id="userForm" method="POST" modelAttribute="accountBO"
							action="edit-accounts">
							<form:hidden path="accountId" />
							<form:hidden path="contactId"/>
					  <div class="box-list">
							<div class="item">
				            <div class="row ">
				             <div class="row ">
							<h3 class="title">Account Information</h3>
							 </div>
							  <div class="col-sm-b   12"> 
							  <div class="col-sm-4">
									<div class="form-group">
										<label path="accountOwner">Account Owner <span
											class="font10 text-danger">*</span></label>
										<form:input type="text" id="accountOwner" path="accountOwner"
											class="form-control required" placeholder="accountOwner"
											maxlength="150" />
										<form:errors path="accountOwner" class="error" />
										<div id="accountOwnerErr" style="color: red;"></div>
									</div>
								</div>
								<div class="col-sm-4">
							<div class="form-group">
										<label> Salutation<span
											class="font10 text-danger">*</span></label>
										<form:select type="text" path="salutation" id="salutationId"
											class="form-control required">
											<c:if test="${not empty accountBO}">
												<form:option value="${accountBO.salutation}">${accountBO.salutation}</form:option>
											</c:if>
											<form:option value="0">Select Salutation </form:option>
											<form:option value="Mr">Mr</form:option>
											<form:option value="Mrs">Mrs</form:option>
											<form:option value="Miss">Miss</form:option>
											<form:option value="Ms">Ms</form:option>
											<form:option value="Other">Other</form:option>
										</form:select>
                                        <form:errors path="salutation" class="error" />
										<div id="salutationErr" style="color: red;"></div>									
									</div>
								</div>	
							<div class="col-sm-4">
									<div class="form-group">
										<label path="accountName">Account Name <span
											class="font10 text-danger">*</span></label>
										<form:input type="text" id="accountName" path="accountName"
											class="form-control required" placeholder="Name"
											maxlength="150" />
										<form:errors path="accountName" class="error" />
										<div id="accountNameErr" style="color: red;"></div>
									</div>
								</div>
								
							</div>  
						<div class="col-sm-b   12"> 
						<div class="col-sm-4">
									<div class="form-group">
										<label path="parentAccount">Parent Account<span
											class="font10 text-danger"></span></label>
										<form:input type="text" id="parentAccount" path="parentAccount"
											class="form-control required" placeholder="parentAccount"
											maxlength="150" />
										<form:errors path="parentAccount" class="error" />
										<div id="parentAccountErr" style="color: red;"></div>
									</div>
								</div>
							<%-- <div class="col-sm-4">
									<div class="form-group">
										<label path="accountSource">Account Source <span
											class="font10 text-danger">*</span></label>
											<form:select type="text" id="accountSource" path="accountSource"
													class="form-control required">
													<c:if test="${not empty accountBO }">
													<form:option value="${ accountBO.opportunitys.opportunityId}">
													${accountBO.opportunitys.firstName }</form:option>
													</c:if>
										<form:option value="0">-- Select --</form:option>
													<form:options items="${opportunityList}" />
														</form:select>
										<form:errors path="accountSource" class="error" />
										<div id="accountSourceErr" style="color: red;"></div>
									</div>
								</div> --%>
									 <div class="col-sm-4">
									<div class="form-group">
										<label >Assigned To <span
											class="font10 text-danger">*</span></label>
												<form:select type="text" path="assignedTo.name" id="assignedTo"
													class="form-control required">
													<c:if test="${not empty accountBO}">
												<form:option value="${accountBO.assignedTo.id}">${accountBO.assignedTo.name}</form:option>
											</c:if>
									 	<form:option value="">-- Select --</form:option> 
													<form:options items="${userBOList}" itemLabel="name"
														itemValue="id" />
														</form:select>
														<form:errors path="assignedTo" class="error" />
											<div id="assignedToErr" style="color: red;"></div>
									</div>
								</div>
								</div>
								<div class="col-sm-b   12">
									<div class="col-sm-4">
									<div class="form-group">
										<label path="annualRevenue">Annual Revenue <span
											class="font10 text-danger">*</span></label>
										<form:input type="text" id="annualRevenue" path="annualRevenue"
											class="form-control required" placeholder="AnnualRevenue"
											maxlength="150" />
										<form:errors path="annualRevenue" class="error" />
										<div id="annualRevenueErr" style="color: red;"></div>
									</div>
								</div> 
								<div class="col-sm-4">
									<div class="form-group">
										<label path="annualRevenue">Email <span
											class="font10 text-danger">*</span></label>
										<form:input type="text" id="email" path="email"
											class="form-control required" placeholder="Email"
											maxlength="150" onchange="emailcheck()"/>
										<form:errors path="email" class="error" />
										<div id="emailErr" style="color: red;"></div>
									</div>
								</div>
									 <div class="col-sm-4">
									<div class="form-group">
										<label path="contactNo">Contact No <span
											class="font10 text-danger">*</span></label>
										<form:input type="text" id="contactNo" path="contactNo"
											class="form-control required" placeholder="ContactNo"
											maxlength="11" />
										<form:errors path="contactNo" class="error" />
										<div id="contactNoErr" style="color: red;"></div>
									</div>
								</div>
								</div>
								</div>
								</div></div>
								<br>
									<div class="box-list">
							<div class="item">
				            <div class="row ">
				             <div class="row ">
							 <h3 class="title">Additional Information </h3>
							 </div>
							 <div class="col-sm-b   12">
							 	<div class="col-sm-4">
								<div class="form-group">
									<label path="type">Type<span
										class="font10 text-danger">*</span></label>
									<form:select type="text"  path="type"
										class="form-control ">
										<form:option value="">--Select Type--</form:option>
										<form:option value="SMS">SMS</form:option>
										<form:option value="Whatsapp">Whatsapp</form:option>
										<form:option value="Email">Email</form:option>
																		</form:select>
									<form:errors path="type" class="error" />
									<div id="typeErr" style="color: red;"></div>
								</div>
							</div>
							<div class="col-sm-4">
									<div class="form-group">
										<label path="industry">Industry<span
											class="font10 text-danger">*</span></label>
										<form:input type="text" id="industry" path="industry"
											class="form-control required" placeholder="industry"
											maxlength="150" />
										<form:errors path="industry" class="error" />
										<div id="industryErr" style="color: red;"></div>
									</div>
								</div>
								<div class="col-sm-4">
									<div class="form-group">
										<label path="noOfEmployess">No Of Employess<span
											class="font10 text-danger">*</span></label>
										<form:input type="text" id="noOfEmployess" path="noOfEmployess"
											class="form-control required" placeholder="noOfEmployess"
											maxlength="150" />
										<form:errors path="noOfEmployess" class="error" />
										<div id="noOfEmployessErr" style="color: red;"></div>
									</div>
								</div>
							</div>
							 	<div class="col-sm-b   12">
								<div class="col-sm-4">
									<div class="form-group">
										<label path="street">Street<span
											class="font10 text-danger">*</span></label>
										<form:input type="text" id="street" path="street"
											class="form-control required" placeholder="Street"
											maxlength="150" />
										<form:errors path="street" class="error" />
										<div id="streetErr" style="color: red;"></div>
									</div>
								</div>
								<div class="col-sm-4">
									<div class="form-group">
										<label path="city"> City<span
											class="font10 text-danger">*</span></label>
										<form:input type="text" id="city" path="city"
											class="form-control required" placeholder="City"
											maxlength="150" />
										<form:errors path="city" class="error" />
										<div id="cityErr" style="color: red;"></div>
									</div>
								</div>
								<div class="col-sm-4">
									<div class="form-group">
										<label path="state">State<span
											class="font10 text-danger">*</span></label>
										<form:input type="text" id="state" path="state"
											class="form-control required" placeholder="State"
											maxlength="150" />
										<form:errors path="state" class="error" />
										<div id="stateErr" style="color: red;"></div>
									</div>
								</div>
							</div> 
							
						 	<div class="col-sm-b   12">
								<div class="col-sm-4">
									<div class="form-group">
										<label path="country">Country<span
											class="font10 text-danger">*</span></label>
										<form:input type="text" id="country" path="country"
											class="form-control required" placeholder="Country"
											maxlength="150" />
										<form:errors path="country" class="error" />
										<div id="countryErr" style="color: red;"></div>
									</div>
								</div>
								<div class="col-sm-4">
									<div class="form-group">
										<label path="postalCode">Postal Code<span
											class="font10 text-danger">*</span></label>
										<form:input type="text" id="postalCode" path="postalCode"
											class="form-control required" placeholder="PostalCode"
											maxlength="150" />
										<form:errors path="postalCode" class="error" />
										<div id="postalCodeErr" style="color: red;"></div>
									</div>
								</div>
								<div class="col-sm-4">
									<div class="form-group">
										<label path=countryCode>Country Code<span
											class="font10 text-danger">*</span></label>
										<form:input type="text" id="countryCode" path="countryCode"
											class="form-control required" placeholder="CountryCode"
											maxlength="150" />
										<form:errors path="countryCode" class="error" />
										<div id="countryCodeErr" style="color: red;"></div>
									</div>
								</div>
							</div>  
							</div></div></div>
							<br>
							<div class="box-list">
							<div class="item">
				            <div class="row ">
				             <div class="row ">
	<h3 class="title">Description Information </h3>
							 </div>
							 <div class="col-sm-12">
							 <div class="col-sm-4">
									<div class="form-group">
										<label> Description <span
											class="font10 text-danger">*</span></label>
										<%-- <form:input type="text" path="description" id="description"
											class="form-control" placeholder="Description"
											maxlength="10" /> --%>
										<form:textarea path="description" id="description" 
											class="form-control" placeholder="Description" rows="3"  
											maxlength="100" />
										<form:errors path="description" class="error" />
										<div id="descriptionErr" style="color: red;"></div>

									</div>
								</div>				
							</div>
							 </div></div>
							</div>
								<br>

							<div style="text-align: right; margin-right: 31px">

								<div class="form-group">
									<button type="submit" id="btnSubmit"
										class="btn btn-t-primary btn-theme lebal_align mt-20 ">Update</button>
									<a href="view-accounts?page=1"><span
										class="btn btn-t-primary btn-theme lebal_align mt-20">Cancel</span></a>
								</div>
							</div>
						</form:form>
			</div>
		</div>
	</div></div>
</div>

<br>