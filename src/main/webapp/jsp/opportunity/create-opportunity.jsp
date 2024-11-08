<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>

<link href="resources/css/jquery-ui-1.10.4.custom.css" rel="stylesheet">
<script type="text/javascript" src="resources/js/jquery-ui.min.js"></script>
<script type="text/javascript"
	src="http://js.nicedit.com/nicEdit-latest.js"></script>
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
	$(document).ready(function() {

		$('#firstName').focus();

		$('#btnsubmit').click(function(e) {
			var isValid = true;
			var type = $('#salutation').val();
			if (type == '') {
				isValid = false;
				$("#salutationErr").show();
				$("#salutationErr").html("Please select salutation");
				$("#salutation").css({
					"border" : "1px solid red",

				});

			} else {
				$('#salutationErr').hide();
				$('#salutation').css({

					"border" : "",
					"background" : ""
				});
			}

			//First Name
			var name = $('#firstName').val();
			if (name == '') {
				isValid = false;
				$("#firstNameErr").show();
				$("#firstNameErr").html("Please enter first name");
				$("#firstName").css({
					"border" : "1px solid red",
				});
			} else if (!/^[a-zA-Z\s]*$/g.test(name)) {
				$("#firstNameErr").show();
				$("#firstNameErr").html("Please Enter Character Only");
				isValid = false;
			} else {
				$('#firstNameErr').hide();
				$('#firstName').css({
					"border" : "",
					"background" : ""
				});
			}

			var name = $('#lastName').val();
			if (name == '') {
				isValid = false;
				$("#lastNameErr").show();
				$("#lastNameErr").html("Please enter Last name");
				$("#lastName").css({
					"border" : "1px solid red",
				});
			} else if (!/^[a-zA-Z\s]*$/g.test(name)) {
				$("#lastNameErr").show();
				$("#lastNameErr").html("Please Enter Character Only");
				isValid = false;
			} else {
				$('#lastNameErr').hide();
				$('#lastName').css({
					"border" : "",
					"background" : ""
				});
			}

			var name = $('#amount').val();
			if (name == '') {
				isValid = false;
				$("#amountErr").show();
				$("#amountErr").html("Please enter the amount");
				$("#amount").css({
					"border" : "1px solid red",
				});
			} else if (!/^[0-9]{1,11}$/.test(name)) {
				isValid = false;
				$("#amountErr").show();
				$("#amountErr").html("Please Enter Numbers Only");
				$("#amount").css({
					"border" : "1px solid red",
				});
			} else {
				$('#amountErr').hide();
				$('#amount').css({
					"border" : "",
					"background" : ""
				});
			}

			//Probability
			var name = $('#probability').val();
			if (name == '') {
				isValid = false;
				$("#probabilityErr").show();
				$("#probabilityErr").html("Please enter the probability");
				$("#probability").css({
					"border" : "1px solid red",
				});
			} else if (!/^[0-9]{1,11}$/.test(name)) {
				isValid = false;
				$("#probabilityErr").show();
				$("#probabilityErr").html("Please Enter Numbers Only");
				$("#probability").css({
					"border" : "1px solid red",
				});
			} else {
				$('#probabilityErr').hide();
				$('#probability').css({
					"border" : "",
					"background" : ""
				});
			}

			//EndTime
			var name = $('#datepicker').val();
			if (name == '') {
				isValid = false;
				$("#endTimeErr").show();
				$("#endTimeErr").html("Please enter the closing date");
				$("#datepicker").css({
					"border" : "1px solid red",
				});
			} else {
				$('#endTimeErr').hide();
				$('#datepicker').css({
					"border" : "",
					"background" : ""
				});
			}

			//Next Step
			var name = $('#nextStepInput').val();
			if (name == '') {
				isValid = false;
				$("#nextStepErr").show();
				$("#nextStepErr").html("Please enter the next step");
				$("#nextStepInput").css({
					"border" : "1px solid red",
				});
			} else {
				$('#nextStepErr').hide();
				$('#nextStepInput').css({
					"border" : "",
					"background" : ""
				});
			}

			//Description
			var name = $('#description').val();
			if (name == '') {
				isValid = false;
				$("#descriptionErr").show();
				$("#descriptionErr").html("Please enter the description");
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

			//SalesStage (Static DropDown)
			var type = $('#salesStage').val();
			if (type == '') {
				isValid = false;
				$("#salesStageErr").show();
				$("#salesStageErr").html("Please select sales stage");
				$("#salesStage").css({
					"border" : "1px solid red",

				});

			} else {
				$('#salesStageErr').hide();
				$('#salesStage').css({

					"border" : "",
					"background" : ""
				});
			}
			//Account Name (Dynamic DropDown)
			var accountID = $('#accountId').val();
			if (accountID == '0') {
				isValid = false;
				$("#accountIdErr").show();
				$("#accountIdErr").html("Please select Account");
				$("#accountId").css({
					"border" : "1px solid red",

				});

			} else {
				$('#accountIdErr').hide();
				$('#accountId').css({

					"border" : "",
					"background" : ""
				});
			}

			var leadSource = $('#leadSourceInput').val();
			if (leadSource == '0') {
				isValid = false;
				$("#leadSourceErr").show();
				$("#leadSourceErr").html("Please select lead source");
				$("#leadSourceInput").css({
					"border" : "1px solid red",

				});

			} else {
				$('#leadSourceErr').hide();
				$('#leadSourceInput').css({

					"border" : "",
					"background" : ""
				});
			}

			var assignedTo = $('#assignedToInput').val();
			if (assignedTo == '0') {
				isValid = false;
				$("#assignedToErr").show();
				$("#assignedToErr").html("Please select Assigned To");
				$("#assignedToInput").css({
					"border" : "1px solid red",

				});

			} else {
				$('#assignedToErr').hide();
				$('#assignedToInput').css({

					"border" : "",
					"background" : ""
				});
			}

			var assignedTo = $('#productInput').val();
			if (assignedTo == '0') {
				isValid = false;
				$("#productErr").show();
				$("#productErr").html("Please select Product");
				$("#productInput").css({
					"border" : "1px solid red",

				});

			} else {
				$('#productErr').hide();
				$('#productInput').css({

					"border" : "",
					"background" : ""
				});
			}

			if (isValid == false)
				e.preventDefault();
		});
	});
</script>
<!-- <script>
	function accountName(accountId) {
		
		

		var accountId  = $("#accountId").val();
		alert(accountId);
		$.ajax({
			type : "GET",
			url : 'getAssignedTo',
			data : 'accountId=' + $("#accountId").val(),
			success : function(result) {
				 var $accountId = $("#assignedToInput");
		            $accountId.empty();
		            $accountId.append($('<option>', { 
		                value: result.Id,
		                text : result.name
		            }));

			},
		});
	};
</script> -->
<script>
	function accountName(accountId) {
		var accountId = $("#accountId").val();
		//alert(accountId);

		$.ajax({
			type : "GET",
			url : 'getAssignedTo',
			data : {
				accountId : accountId
			}, // Use an object for data
			success : function(result) {
				//alert(result.name);
				var $select = $("#assignedToInput");
				$select.empty(); // Clear any existing options

				// Ensure result contains the expected properties
				 $select.append($('<option>', { 
			                value: result.userId,
			                text : result.name
			            }));
				
			},
			error : function(xhr, status, error) {
				console.error("AJAX Error:", status, error);
			}
		});
	}
</script>

<!-- <script>
	$(function() {
		$("#datepicker").datepicker({
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
<!-- <script>
    document.addEventListener('DOMContentLoaded', function() {
        // Get the date input element
        var dateInput = document.getElementById('datepicker');
        
        // Get today's date in YYYY-MM-DD format
        var today = new Date().toISOString().split('T')[0];
        
        // Set the min attribute of the date input to today's date
        dateInput.setAttribute('min', today);

        // Event listener for displaying the date in DD-MM-YYYY format
        dateInput.addEventListener('change', function() {
            var selectedDate = new Date(this.value);
            var formattedDate = selectedDate.getDate().toString().padStart(2, '0') + '-' +
                                (selectedDate.getMonth() + 1).toString().padStart(2, '0') + '-' +
                                selectedDate.getFullYear();
           /*  alert("Selected date is: " + formattedDate); */ // Display or use the formatted date as needed
        });
    });
</script> -->
<script>
	$(function() {
		var today = new Date();

		$("#datepicker").datepicker({
			changeMonth : true,
			changeYear : true,
			numberOfMonths : 1,
			minDate : today,
			onSelect : function(selected) {
				var dt = new Date(selected);
				dt.setDate(dt.getDate()); // Set the selected date
				$("#endDateInput").datepicker("option", "minDate", dt);
			}
		});
	});
</script>
</head>
<body>

	<div class="row scrollspy-sidenav pb-20 body-mt-15">

		<div class="contact-form-wrapper">
			<div class="box-list">
				<div class="item">
					<div class="row ">
						<div class="text-center underline">
							<h3>Create Opportunity</h3>
						</div>
						<form:form method="POST" id="addForm" action="create-opportunity"
							modelAttribute="opportunityBO">
							<input type="hidden" name="origin" value="${origin}">
							<input type="hidden" name="leadId" value="${leadId}">
							<h3 class="title">Personal Information</h3>
							<div class="box-list">
								<div class="item">
									<div class="row">
										<div class="col-sm-12">
											<div class="col-sm-4">
												<div class="form-group">
													<label>Salutation<span class="font10 text-danger">*</span></label>
													<form:select type="text" name="salutation" id="salutation"
														path="salutation" class="form-control required">
														<form:option value="">Select User</form:option>
														<form:option value="Mr">Mr</form:option>
														<form:option value="Ms">Ms</form:option>
														<form:option value="Mrs">Mrs</form:option>
														<form:option value="Others">Others</form:option>
													</form:select>
													<form:errors path="salutation" class="error" />
													<div id="salutationErr" style="color: red;"></div>
												</div>
											</div>

											<div class="col-sm-4">
												<div class="form-group">
													<label>First Name<span class="font10 text-danger">*</span></label>
													<form:input type="text" name="firstName" path="firstName"
														id="firstName" class="form-control required"
														placeholder="First Name" maxlength="150" />
													<form:errors path="firstName" class="error" />
													<div id="firstNameErr" style="color: red;"></div>

												</div>

											</div>
											<div class="col-sm-4">
												<div class="form-group">
													<label>Last Name<span class="font10 text-danger">*</span></label>
													<form:input type="text" name="lastName" path="lastName"
														id="lastName" class="form-control required"
														placeholder="Last Name" maxlength="150" />
													<form:errors path="lastName" class="error" />
													<div id="lastNameErr" style="color: red;"></div>

												</div>

											</div>
										</div>
									</div>
								</div>
							</div>
							<br>
							<h3 class="title">Account Information</h3>
							<div class="box-list">
								<div class="item">
									<div class="row">
										<div class="col-sm-12">
											<div class="col-sm-4">
												<div class="form-group">
													<label>Account Name <span
														class="font10 text-danger">*</span></label>
													<form:select type="text" name="accountId" id="accountId"
														path="accountBO.accountId"
														onchange="accountName(accountId)"
														class="form-control required">
														<form:option value="0">Choose Type</form:option>
														<form:options items="${accountList}"></form:options>

													</form:select>


													<form:errors path="accountBO.accountId" class="error" />
													<div id="accountIdErr" style="color: red;"></div>
												</div>
											</div>

											<div class="col-sm-4">
												<div class="form-group">
													<label>Lead Source<span class="font10 text-danger">*</span></label>

													<form:select type="text" name="assignedTo"
														id="leadSourceInput" path="leads.leadsId"
														class="form-control required">
														<c:if test="${opportunityBO.leads.adminId>0}">
															<form:option value="${opportunityBO.leads.adminId}">${opportunityBO.assignedTo.name}</form:option>
														</c:if>
														<form:option value="0">Select User</form:option>
														<form:options items="${leadList}" itemLabel="firstName"
															itemValue="leadsId"></form:options>

													</form:select>
													<form:errors path="leads.leadsId" class="error" />
													<div id="leadSourceErr" style="color: red;"></div>

												</div>

											</div>
											<div class="col-sm-4">
												<div class="form-group">
													<label>Assigned To<span class="font10 text-danger">*</span></label>

													<!-- Ensure the select element is ready to be updated dynamically -->
													<form:select name="assignedTo" path="user.id"
														class="form-control required" id="assignedToInput">
														<!-- Initial default option -->
														<form:option value="0">Select User</form:option>
														<form:options items="${userBOList}" itemLabel="name"
															itemValue="id"></form:options>
													</form:select>

													<form:errors path="user.id" class="error" />
													<div id="assignedToErr" style="color: red;"></div>
												</div>
											</div>

										</div>

									</div>
								</div>
							</div>
							<br>
							<h3 class="title">Sales Information</h3>
							<div class="box-list">
								<div class="item">
									<div class="row">
										<div class="col-sm-12">


											<div class="col-sm-4">
												<div class="form-group">
													<label>Product Name <span
														class="font10 text-danger">*</span></label>

													<form:select type="text" name="serviceId"
														path="productService.serviceId"
														class="form-control required" id="productInput">
														<c:if test="${opportunityBO.productService.serviceId>0}">
															<form:option
																value="${opportunityBO.productService.serviceId}">${opportunityBO.productService.serviceName}</form:option>
														</c:if>
														<form:option value="0">Select Product</form:option>
														<form:options items="${prodList}" itemLabel="serviceName"
															itemValue="serviceId"></form:options>
													</form:select>
													<form:errors path="productService.serviceId" class="error" />
													<div id="productErr" style="color: red;"></div>
												</div>
											</div>
											<div class="col-sm-4">
												<div class="form-group">
													<label>Amount<span class="font10 text-danger">*</span></label>
													<form:input type="text" name="amount" path="amount"
														id="amount" class="form-control required"
														placeholder="Amount" maxlength="150" />
													<form:errors path="amount" class="error" />
													<div id="amountErr" style="color: red;"></div>

												</div>
											</div>
											<div class="col-sm-4">
												<div class="form-group">
													<label>Sales Stage <span class="font10 text-danger">*</span></label>
													<form:select type="text" name="salesStage" id="salesStage"
														path="salesStage" class="form-control required">
														<form:option value="">Select Stage</form:option>
														<form:option value="Initiate">Initiate</form:option>
														<form:option value="Follow">Follow</form:option>
														<form:option value="Completed">Completed</form:option>
													</form:select>
													<form:errors path="salesStage" class="error" />
													<div id="salesStageErr" style="color: red;"></div>
												</div>
											</div>

										</div>

										<div class="col-sm-12">
											<div class="col-sm-4">
												<div class="form-group">
													<label>Expected Closing Date <span
														class="font10 text-danger">*</span></label>
													<form:input type="text" name="endTime" path="endTime"
														 id="datepicker"
														class="form-control required"
														placeholder="Expected Closing Date" />
													<form:errors path="endTime" class="error" />
													<div id="endTimeErr" style="color: red;"></div>
												</div>
											</div>
											<div class="col-sm-4">
												<div class="form-group">
													<label>Next Step<span class="font10 text-danger">*</span></label>
													<form:input type="text" path="nextStep" id="nextStepInput"
														class="form-control required" placeholder="Next Step"
														maxlength="150" />
													<form:errors path="nextStep" class="error" />
													<div id="nextStepErr" style="color: red;"></div>
												</div>
											</div>
											<div class="col-sm-4">
												<div class="form-group">
													<label>Probability<span class="font10 text-danger">*</span></label>
													<form:input type="text" name="probability"
														path="probability" id="probability"
														class="form-control required" placeholder="Probability"
														maxlength="150" />
													<form:errors path="probability" class="error" />
													<div id="probabilityErr" style="color: red;"></div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
							<br>
							<h3 class="title">Additional Information</h3>
							<div class="box-list">
								<div class="item">
									<div class="row">
										<div class="col-sm-12">

											<div class="col-sm-8">
												<div class="form-group">

													<label> Description <span
														class="font10 text-danger">*</span></label>
													<form:textarea path="description" id="description"
														class="form-control required" placeholder="Description"
														maxlength="1500" cols="190" rows="06" />
													<form:errors path="description" class="error" />
													<div id="descriptionErr" style="color: red;"></div>
												</div>
											</div>
										</div>

										<div style="float: right; margin: -14px 5px;">
											<button type="submit" id="btnsubmit"
												class="btn btn-t-primary btn-theme lebal_align mt-20"
												style="text-align: right;">Submit</button>
											<a href="view-opportunities"><span
												class="btn btn-t-primary btn-theme lebal_align mt-20">Cancel</span></a>
										</div>
									</div>


								</div>
							</div>
						</form:form>
					</div>
				</div>
			</div>

		</div>
	</div>
	</br>
</body>

</html>