<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>


<link href="resources/css/jquery-ui-1.10.4.custom.css" rel="stylesheet">
<script type="text/javascript" src="resources/js/jquery-ui.min.js"></script>
<!-- <script type="text/javascript"
	src="http://js.nicedit.com/nicEdit-latest.js"></script> -->

<script>
	bkLib.onDomLoaded(function() {
		new nicEditor({
			buttonList : [ 'fontSize', 'bold', 'italic', 'underline', 'ol',
					'ul', 'strikeThrough', 'html' ]
		}).panelInstance('inputAddress');
	});
</script>
<script type="text/javascript">
    $(document).ready(function () {
        $('#btnSubmit').on('click', function (event) { // Trigger validation on button click
            event.preventDefault(); // Prevent default form submission
            
            var assignedToId = $('#assignedTo').val();

            // Check if the assignedToId is valid (not equal to 0, or whatever specific value you want)
            if (assignedToId !== "0") { 
                $('#assignedToErr').text(''); // Clear error message
                
                // Proceed with AJAX request or form submission
                $.ajax({
                    url: '/validateAssignedTo', // Replace with your validation URL
                    type: 'POST',
                    data: {
                        assignedToId: assignedToId
                    },
                    success: function (response) {
                        if (response.success) {
                            $('#assignedToErr').text(''); // Ensure any existing error message is cleared
                            $('#yourFormId').submit(); // Submit the form if validation passes
                        } else {
                            $('#assignedToErr').text(response.errorMessage); // Display error message if invalid
                        }
                    },
                });
            } else {
                $('#assignedToErr').text('Please select a valid option.'); // Display an error if value is invalid
            }
        });

        // Remove the error message when a valid option is selected
        $('#assignedTo').on('change', function () {
            var assignedToId = $(this).val();
            if (assignedToId !== "0") {
                $('#assignedToErr').text(''); // Clear the error message
            }
        });
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

<script>
	$(document)
			.ready(
					function() {
						$('#btnSubmit')
								.click(
										function(e) {
											var isValid = true;

											// Validate Account Owner
											validateField(
													'#accountOwner',
													/^[a-zA-Z\s]*$/,
													"Please enter account owner",
													"Please enter characters only",
													"#accountOwnerErr");

											// Validate Account Name
											validateField(
													'#accountName',
													/^[a-zA-Z\s]*$/,
													"Please enter account name",
													"Please enter characters only",
													"#accountNameErr");

											// Validate Parent Account
											/* validateField('#parentAccount', /^[a-zA-Z\s]*$/, "Please enter parent account", "Please enter characters only", "#parentAccountErr"); */

											// Validate Contact Number
											validateField(
													'#contactNo',
													/^[0-9]{11}$/,
													"Please enter contact number",
													"Please enter an 11-digit number",
													"#contactNoErr");

											// Validate Annual Revenue
											validateField(
													'#annualRevenue',
													/^[0-9]*$/,
													"Please enter annual revenue",
													"Please enter only numeric characters",
													"#annualRevenueErr");

											// Validate Description
											validateField('#description', /.+/,
													"Please enter description",
													"", "#descriptionErr");

											// Validate Type
											validateField('#type', /.+/,
													"Please select type", "",
													"#typeErr");

											// Validate Salutation
											validateSelectField(
													'#salutationId',
													"Please select salutation",
													"#salutationErr");

											// Validate Assigned To
											validateSelectField(
													'#assignedToId',
													"Please select Assigned To",
													"#assignedToIdErr");

											// Validate Industry
											validateField('#industry', /.+/,
													"Please enter industry",
													"", "#industryErr");

											// Validate Street
											validateField('#street', /.+/,
													"Please enter street", "",
													"#streetErr");

											// Validate City
											validateField(
													'#city',
													/^[a-zA-Z\s]*$/,
													"Please enter city",
													"Please enter characters only",
													"#cityErr");

											// Validate State
											validateField(
													'#state',
													/^[a-zA-Z\s]*$/,
													"Please enter state",
													"Please enter characters only",
													"#stateErr");

											// Validate Country
											validateField(
													'#country',
													/^[a-zA-Z\s]*$/,
													"Please enter country",
													"Please enter characters only",
													"#countryErr");

											// Validate Number of Employees
											validateField(
													'#noOfEmployess',
													/^[0-9]{1,11}$/,
													"Please enter the number of employees",
													"Please enter a number only",
													"#noOfEmployessErr");

											// Validate Country Code
											validateField(
													'#countryCode',
													/^\+?[0-9]{1,11}$/,
													"Please enter country code",
													"Please enter numbers only",
													"#countryCodeErr");

											// Validate Postal Code
											validateField(
													'#postalCode',
													/^[0-9]{1,11}$/,
													"Please enter postal code",
													"Please enter numbers only",
													"#postalCodeErr");

											// Validate Email
											validateEmail(
													'#email',
													"Please enter email",
													"Please enter a valid email",
													"#emailErr");

											if (!isValid) {
												e.preventDefault();
											}

											function validateField(selector,
													regex, emptyMessage,
													invalidMessage,
													errorSelector) {
												var value = $(selector).val();
												if (value.trim() == '') {
													isValid = false;
													$(errorSelector).show()
															.html(emptyMessage);
													$(selector).css("border",
															"1px solid red");
												} else if (!regex.test(value)) {
													isValid = false;
													$(errorSelector)
															.show()
															.html(
																	invalidMessage);
													$(selector).css("border",
															"1px solid red");
												} else {
													$(errorSelector).hide();
													$(selector).css("border",
															"").css(
															"background", "");
												}
											}

											function validateSelectField(
													selector, emptyMessage,
													errorSelector) {
												var value = $(selector).val();
												if (value == '0') {
													isValid = false;
													$(errorSelector).show()
															.html(emptyMessage);
													$(selector).css("border",
															"1px solid red");
												} else {
													$(errorSelector).hide();
													$(selector).css("border",
															"").css(
															"background", "");
												}
											}

											function validateEmail(selector,
													emptyMessage,
													invalidMessage,
													errorSelector) {
												var emailValidations = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;
												var value = $(selector).val();
												if (value.trim() == '') {
													isValid = false;
													$(errorSelector).show()
															.html(emptyMessage);
													$(selector).css("border",
															"1px solid red");
												} else if (!emailValidations
														.test(value)) {
													isValid = false;
													$(errorSelector)
															.show()
															.html(
																	invalidMessage);
													$(selector).css("border",
															"1px solid red");
												} else {
													$(errorSelector).hide();
													$(selector).css("border",
															"").css(
															"background", "");
												}
											}
										});
					});
</script>




<div class="warning">

	<c:if test="${not empty successMessage}">
		<div class="alert alert-info" role="alert"
			style="font-size: 12px; padding: 8px 9px 5px 10px; margin-top: 15px;">
			<button type="button" class="close" data-dismiss="alert"
				aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
			<strong>success!</strong>
			<c:out value="${successMessage}"></c:out>
		</div>
	</c:if>
	<c:if test="${not empty errorMessage}">
		<div class="alert alert-info" role="alert"
			style="font-size: 12px; padding: 8px 9px 5px 10px; margin-top: 15px;">
			<button type="button" class="close" data-dismiss="alert"
				aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
			<strong>Info!</strong>
			<c:out value="${errorMessage}"></c:out>
		</div>
	</c:if>
	<c:if test="${not empty errorMobileMessage}">
		<div class="alert alert-info" role="alert"
			style="font-size: 12px; padding: 8px 9px 5px 10px; margin-top: 15px;">
			<button type="button" class="close" data-dismiss="alert"
				aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
			<strong>Info!</strong>
			<c:out value="${errorMobileMessage}"></c:out>
		</div>
	</c:if>
</div>

<div class="contact-form-wrapper">
	<br> <br> <br>
	<div class="box-list">
		<div class="item">
			<div class="row ">
				<div class="text-center underline">
					<h3>Create Account</h3>
				</div>
				<br>
				<form:form method="POST" id="addForm" action="create-account"
					modelAttribute="account">
					<input type="hidden" name="origin" value="${origin}">
					<input type="hidden" name="leadId" value="${leadId}">


					<div class="box-list">
						<div class="item">
							<div class="row ">
								<div class="row ">
									<!--  <h3 style="background:#483D8B;">Account Information </h3> -->
									<h3 class="title">Account Information</h3>
								</div>
								<div class="col-sm-b   12">
									<div class="col-sm-4">
										<div class="form-group">
											<label path="accountOwner">Account Owner <span
												class="font10 text-danger">*</span></label>
											<form:input type="text" id="accountOwner" path="accountOwner"
												class="form-control required" placeholder="Account Owner"
												maxlength="150" />
											<form:errors path="accountOwner" class="error" />
											<div id="accountOwnerErr" style="color: red;"></div>
										</div>
									</div>

									<div class="col-sm-4">
										<div class="form-group">
											<label> Salutation<span class="font10 text-danger">*</span></label>
											<form:select type="text" path="salutation" id="salutationId"
												class="form-control required">
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
							</div>
							<div class="row ">
								<div class="col-sm-12">

									<div class="col-sm-4">
										<div class="form-group">
											<label path="parentAccount">Parent Account<span
												class="font10 text-danger"></span></label>
											<form:input type="text" id="parentAccount"
												path="parentAccount" class="form-control required"
												placeholder="Parent Account" maxlength="150" />
											<form:errors path="parentAccount" class="error" />
											<div id="parentAccountErr" style="color: red;"></div>
										</div>
									</div>
									<!-- <div class="col-sm-4">
									<div class="form-group">
										<label path="accountSource">Account Source <span
											class="font10 text-danger">*</span></label>
											<form:select type="text" id="accountSource" path="accountSource"
													class="form-control required">
										<form:option value="0">-- Select --</form:option>
													<form:options items="${opportunityList}" />
														</form:select>
										<form:errors path="accountSource" class="error" />
										<div id="accountSourceErr" style="color: red;"></div>
									</div>
								</div>  -->
									<div class="col-sm-4">
										<div class="form-group">
											<label for="assignedTo">Assigned To <span
												class="font10 text-danger">*</span></label> <select id="assignedTo"
												class="form-control required">
												<c:if test="${account.assignedTo.adminId > 0}">
													<option value="${account.assignedTo.adminId}">${account.assignedTo.name}</option>
												</c:if>
												<option value="0">-- Select --</option>
												<c:forEach var="user" items="${userBOList}">
													<option value="${user.id}">${user.name}</option>
												</c:forEach>
											</select>
											<div id="assignedToErr" style="color: red;"></div>
										</div>
									</div>
								</div>

								<div class="col-sm-4">
									<div class="form-group">
										<label path="annualRevenue">Annual Revenue <span
											class="font10 text-danger">*</span></label>
										<form:input type="text" id="annualRevenue"
											path="annualRevenue" class="form-control required"
											placeholder="Annual Revenue" maxlength="150" />
										<form:errors path="annualRevenue" class="error" />
										<div id="annualRevenueErr" style="color: red;"></div>
									</div>
								</div>
							</div>
							<div class="row ">
								<div class="col-sm-4">
									<div class="form-group">
										<label path="annualRevenue">Email <span
											class="font10 text-danger">*</span></label>
										<form:input type="text" id="email" path="email"
											class="form-control required" placeholder="Email"
											maxlength="150" onchange="emailcheck()" />
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
											maxlength="11" onchange="contactnumbercheck()" />
										<form:errors path="contactNo" class="error" />
										<div id="contactNoErr" style="color: red;"></div>
									</div>
								</div>
							</div>
						</div>
					</div>
			</div>
			<br>
			<div class="box-list">
				<div class="item">
					<div class="row ">
						<div class="row ">
							<h3 class="title">Additional Information</h3>
						</div>
						<div class="row ">
							<div class="col-sm-b   12">
								<div class="col-sm-4">
									<div class="form-group">
										<label path="type">Type<span
											class="font10 text-danger">*</span></label>
										<form:select type="text" path="type" class="form-control ">
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
											class="form-control required" placeholder="Industry"
											maxlength="150" />
										<form:errors path="industry" class="error" />
										<div id="industryErr" style="color: red;"></div>
									</div>
								</div>
								<div class="col-sm-4">
									<div class="form-group">
										<label path="noOfEmployess">No Of Employess<span
											class="font10 text-danger">*</span></label>
										<form:input type="text" id="noOfEmployess"
											path="noOfEmployess" class="form-control required"
											placeholder="noOfEmployess" maxlength="150" />
										<form:errors path="noOfEmployess" class="error" />
										<div id="noOfEmployessErr" style="color: red;"></div>
									</div>
								</div>
							</div>
						</div>
						<div class="row ">
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
						</div>
						<div class="row ">
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
						</div>
					</div>
				</div>

			</div>
			<br>
			<div class="box-list">
				<div class="item">
					<div class="row ">
						<div class="row ">
							<h3 class="title">Description Information</h3>
						</div>
						<div class="col-sm-12">

							<div class="form-group">
								<label> Description <span class="font10 text-danger">*</span></label>
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
				</div>
			</div>
			<br>
			<div style="text-align: right; margin-right: 30px">
				<c:if test="${empty origin}">
					<button type="submit" id="btnSubmit"
						class="btn btn-t-primary btn-theme lebal_align mt-20"
						style="text-align: right;">Submit</button>
				</c:if>
				<c:if test="${not empty origin}">
					<button type="submit" id="btnSubmit" name="next"
						class="btn btn-t-primary btn-theme lebal_align mt-20"
						style="text-align: right;" value="accountConfirm">Next</button>

				</c:if>
				<a href="view-accounts"><span
					class="btn btn-t-primary btn-theme lebal_align mt-20">Cancel</span></a>

			</div>

			</form:form>


		</div>
	</div>
</div>
</div>

<br>



