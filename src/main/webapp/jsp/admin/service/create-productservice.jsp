<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>


<link href="resources/theme/css/custom.css" rel="stylesheet">
<link href="resources/css/jquery-ui-1.10.4.custom.css" rel="stylesheet">
<script type="text/javascript" src="resources/js/jquery-ui.min.js"></script>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script>
	$(document)
			.ready(
					function() {

						$('#name').focus();

						$('#btnSubmit')
								.click(
										function(e) {
											var isValid = true;
											var serviceName = $('#serviceNameId').val();
											if (serviceName == '') {
												isValid = false;
												$("#serviceNameIdErr").show();
												$("#serviceNameIdErr").html("Please Enter Product Name");
												$("#serviceNameId").css({
													"border" : "1px solid red",

												});

											} else {
												$('#serviceNameIdErr').hide();
												$('#serviceNameId').css({

													"border" : "",
													"background" : ""
												});
											}
											//product Specification

											var serviceSpecification = $(
													'#serviceInput').val();
											if (serviceSpecification == '') {
												isValid = false;
												$("#serviceErr").show();
												$("#serviceErr")
														.html(
																"Please enter product Specification value");
												$("#serviceInput").css({
													"border" : "1px solid red",

												});

											} else {
												$('#serviceErr').hide();
												$('#serviceInput').css({

													"border" : "",
													"background" : ""
												});
											}

											//Minimum Stock..
											var minimumStock = $('#name').val()
													.trim(); // Trim any leading or trailing whitespace
											var maximumStock = $('#nameId')
													.val().trim();

											if (minimumStock === ""
													|| maximumStock === "") {
												isValid = false;
												if (minimumStock === "") {
													$("#nameErr")
															.show()
															.html(
																	"Please Enter Minimum Stock");
													$("#name")
															.css(
																	{
																		"border" : "1px solid red"
																	});
												} else {
													$("#nameErr").hide();
													$("#name").css({
														"border" : "",
														"background" : ""
													});
												}

												if (maximumStock === "") {
													$("#maximumStockErr")
															.show()
															.html(
																	"Please Enter Maximum Stock");
													$("#nameId")
															.css(
																	{
																		"border" : "1px solid red"
																	});
												} else {
													$("#maximumStockErr")
															.hide();
													$("#nameId").css({
														"border" : "",
														"background" : ""
													});
												}
											} else if (!/^\d+$/
													.test(minimumStock)
													|| !/^\d+$/
															.test(maximumStock)) {
												// Check if the values contain only digits
												isValid = false;
												if (!/^\d+$/.test(minimumStock)) {
													$("#nameErr")
															.show()
															.html(
																	"Please Enter Numeric Values Only");
													$("#name")
															.css(
																	{
																		"border" : "1px solid red"
																	});
												} else {
													$("#nameErr").hide();
													$("#name").css({
														"border" : "",
														"background" : ""
													});
												}

												if (!/^\d+$/.test(maximumStock)) {
													$("#maximumStockErr")
															.show()
															.html(
																	"Please Enter Numeric Values Only");
													$("#nameId")
															.css(
																	{
																		"border" : "1px solid red"
																	});
												} else {
													$("#maximumStockErr")
															.hide();
													$("#nameId").css({
														"border" : "",
														"background" : ""
													});
												}
											} else {
												minimumStock = parseInt(minimumStock);
												maximumStock = parseInt(maximumStock);

												if (minimumStock == 0
														|| maximumStock == 0) {

													if (minimumStock == 0) {
														isValid = false;
														$("#nameErr").show();
														$("#nameErr")
																.html(
																		"Please Enter Minimum Stock");
														$("#name")
																.css(
																		{
																			"border" : "1px solid red",

																		});

													} else {
														$('#nameErr').hide();
														$('#name').css({
															"border" : "",
															"background" : ""
														});
													}

													if (maximumStock == 0) {

														isValid = false;
														$("#maximumStockErr")
																.show();
														$("#maximumStockErr")
																.html(
																		"Please Enter Maximum Stock");
														$("#nameId")
																.css(
																		{
																			"border" : "1px solid red"
																		});
													} else {
														$('#maximumStockErr')
																.hide();
														$('#nameId').css({
															"border" : "",
															"background" : ""
														});
													}

												} else if (!/^[0-9]{1,10}$/
														.test(minimumStock)
														|| !/^[0-9]{1,10}$/
																.test(maximumStock)) {

													if (!/^[0-9]{1,10}$/
															.test(minimumStock)) {
														isValid = false;
														$("#nameErr").show();
														$("#nameErr")
																.html(
																		"Please Enter Numeric Values Only");
														$("#name")
																.css(
																		{
																			"border" : "1px solid red",

																		});

													} else {
														$('#nameErr').hide();
														$('#name').css({
															"border" : "",
															"background" : ""
														});
													}

													if (!/^[0-9]{1,10}$/
															.test(maximumStock)) {

														isValid = false;
														$("#maximumStockErr")
																.show();
														$("#maximumStockErr")
																.html(
																		"Please Enter Numeric Values Only");
														$("#nameId")
																.css(
																		{
																			"border" : "1px solid red"
																		});
													} else {
														$('#maximumStockErr')
																.hide();
														$('#nameId').css({
															"border" : "",
															"background" : ""
														});
													}

												} else if (minimumStock >= maximumStock) {
													isValid = false;
													$("#nameErr").show();
													$("#nameErr")
															.html(
																	"Minimum stock should be less than Maximum stock.");
													// document.getElementById("btnsubmit").disabled = true;
													$("#name")
															.css(
																	{
																		"border" : "1px solid red"
																	});
													$("#nameId")
															.css(
																	{
																		"border" : "1px solid red"
																	});
												} else {
													$('#nameErr').hide();
													$('#name').css({
														"border" : "",
														"background" : ""
													});
													$('#maximumStockErr').hide();
													$('#nameId').css({
														"border" : "",
														"background" : ""
													});
												}
											}

											//Available Stock...

											var availableStocks = $(
													'#availableStocksId').val();
											if (availableStocks == '') {
												isValid = false;
												$("#availableStocksErr").show();
												$("#availableStocksErr")
														.html(
																"Please Enter Available Stock");
												$("#availableStocksId").css({
													"border" : "1px solid red",

												});
                                               
											}else if (!/^[0-9]{1,10}$/.test(availableStocks)) {
												isValid = false;
												$("#availableStocksErr").show();
												$("#availableStocksErr").html("Please Enter Number Only");
												$("#availableStocksId").css({
													"border" : "1px solid red",

												});

											}
											   else {
												$('#availableStocksErr').hide();
												$('#availableStocksId').css({

													"border" : "",
													"background" : ""
												});
											}
											//start date

											var datepicker = $("#startDateInput")
													.val();
											if (datepicker == '') {
												isValid = false;
												$("#startDateErr").show();
												$("#startDateErr").html(
														"Please Select StartDate")
												$("#startDateInput").css({
													"border" : "1px solid red",
												});
												isValid = false;
											} else {
												$("#startDateErr").hide();
												$("#datepicker").css({
													"border" : "",
													"background" : ""
												});
											}
											//end date

											var datepicker = $("#endDateInput")
													.val();
											if (datepicker == '') {
												isValid = false;
												$("#endDateErr").show();
												$("#endDateErr")
														.html(
																"Please Select EndDate")
												$("#endDateInput").css({
													"border" : "1px solid red",
												});
												isValid = false;
											} else {
												$("#endDateErr").hide();
												$("#endDateInput").css({
													"border" : "",
													"background" : ""
												});
											}
											//ptoduct type

											var productTypes = $(
													'#productTypesId').val();
											if (productTypes == 'Select') {
												isValid = false;
												$("#productTypesErr").show();
												$("#productTypesErr")
														.html(
																"Please Enter Product Type");
												$("#productTypesId").css({
													"border" : "1px solid red",

												});

											} else {
												$('#productTypesErr').hide();
												$('#productTypesId').css({

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
    $(function() {
        var startDate = $("#datepicker").datepicker({
            changeMonth: true,
            changeYear: true,
            numberOfMonths: 1,
            onSelect: function(selected) {
                var dt = new Date(selected);
                dt.setDate(dt.getDate());
                $("#endDateInput").datepicker("option", "minDate", dt); // Set minDate for endDateInput
                $("#endDateInput").prop("disabled", false); // Enable endDateInput
            }
        });

        $("#endDateInput").datepicker({
            changeMonth: true,
            changeYear: true,
            numberOfMonths: 1,
            beforeShowDay: function(date) {
                // Disable dates before selected startDate
                if (startDate.datepicker("getDate") === null) {
                    return [false, ""];
                }
                return [date.getTime() >= startDate.datepicker("getDate").getTime(), ""];
            }
        });

        // Initially disable endDateInput
        $("#endDateInput").prop("disabled", true);
    });
</script> -->
<!-- <script type="text/javascript">
        $(function() {
        	 var currentYear = new Date().getFullYear();
        	 var currentDate = new Date();
            
            $("#startDate").datepicker({
                dateFormat: "dd-mm-yy",
                changeMonth: true,
                changeYear: true,
                yearRange: currentYear + ":c+10",
                minDate: currentDate,
                onSelect: function(dateText) {
                    validateDates();
                }
            });
            $("#endDate").datepicker({
                dateFormat: "dd-mm-yy",
                changeMonth: true,
                changeYear: true,
                yearRange: currentYear + ":c+10",
                minDate: currentDate,
                onSelect: function(dateText) {
                    validateDates();
                }
            });
        });

        function validateDates() {
            var startDate = $("#startDate").datepicker("getDate");
            var endDate = $("#endDate").datepicker("getDate");
            var errorMessage = $("#errorMessage");

            if (startDate && endDate && startDate > endDate) {
                errorMessage.text("End date should be greater than Start date.");
            } else {
                errorMessage.text("");
            }
        }
    </script> -->
    
   <!--  <script type="text/javascript">
    $(document).ready(function() {
        $('#name').focus();

        // Initialize datepickers
        $("#startDate").datepicker({
            dateFormat: "dd-mm-yy",
            changeMonth: true,
            changeYear: true,
            minDate: new Date(),
            onSelect: function(dateText) {
                validateDates(); // Call validation when a date is selected
            }
        });

        $("#endDate").datepicker({
            dateFormat: "dd-mm-yy",
            changeMonth: true,
            changeYear: true,
            minDate: new Date(),
            onSelect: function(dateText) {
                validateDates(); // Call validation when a date is selected
            }
        });

        // Function to validate the dates
        function validateDates() {
            var startDate = $("#startDate").datepicker("getDate");
            var endDate = $("#endDate").datepicker("getDate");
            var errorMessage = $("#errorMessage");

            if (startDate && endDate && startDate > endDate) {
                errorMessage.text("End date should be greater than Start date.");
                $("#endDate").css({"border": "1px solid red"});
                $("#startDate").css({"border": "1px solid red"});
            } else {
                errorMessage.text("");
                $("#endDate").css({"border": "", "background": ""});
                $("#startDate").css({"border": "", "background": ""});
            }
        }

        // Handle form submission
        $('#btnSubmit').click(function(e) {
            var isValid = true;
            // Your existing validation code here...

            // Validate end date
            var startDate = $("#startDate").datepicker("getDate");
            var endDate = $("#endDate").datepicker("getDate");
            if (startDate && endDate && startDate > endDate) {
                isValid = false;
                $("#errorMessage").text("End date should be greater than Start date.");
                $("#endDate").css({"border": "1px solid red"});
                $("#startDate").css({"border": "1px solid red"});
            } else {
                $("#errorMessage").text("");
                $("#endDate").css({"border": "", "background": ""});
                $("#startDate").css({"border": "", "background": ""});
            }

            if (!isValid) {
                e.preventDefault(); // Prevent form submission if validation fails
            }
        });
    });
</script> -->
    
 <script>
    $(function() {
        var startDate = $("#datepicker").datepicker({
            changeMonth: true,
            changeYear: true,
            numberOfMonths: 1,
            minDate: 0,
            onSelect: function(selected) {
                var dt = new Date(selected);
                dt.setDate(dt.getDate());
                $("#endDateInput").datepicker("option", "minDate", dt); // Set minDate for endDateInput
                $("#endDateInput").prop("disabled", false); // Enable endDateInput
            }
        });

        $("#endDateInput").datepicker({
            changeMonth: true,
            changeYear: true,
            numberOfMonths: 1,
            beforeShowDay: function(date) {
                // Disable dates before selected startDate
                if (startDate.datepicker("getDate") === null) {
                    return [false, ""];
                }
                return [date.getTime() >= startDate.datepicker("getDate").getTime(), ""];
            }
        });      
        
        // Initially disable endDateInput
        $("#endDateInput").prop("disabled", true);
    });
</script> 
 <!-- <script type="text/javascript">
        $(function() {
            $("#startDateInput").datepicker({
                dateFormat: "yy-mm-dd",
                onSelect: function(dateText) {
                    validateDates();
                }
            });
            $("#endDateInput").datepicker({
                dateFormat: "yy-mm-dd",
                onSelect: function(dateText) {
                    validateDates();
                }
            });
        });

        function validateDates() {
            var startDate = $("#startDateInput").datepicker("getDate");
            var endDate = $("#endDateInput").datepicker("getDate");
            var errorMessage = $("#errorMessage");

            if (startDate && endDate && startDate > endDate) {
                errorMessage.text("Start date cannot be later than end date.");
            } else {
                errorMessage.text("");
            }
        }
    </script>
 -->
 
 <script type="text/javascript">
	function roleCheck() {
		var serviceName = document.getElementById("serviceNameId").value;
		document.getElementById("btnSubmit").disabled = false;
		if (serviceName != '') {
			$.ajax({
				url : "check_productName",
				type : "GET",
				data : 'serviceName=' + serviceName,
				success : function(result) {

					if (result == true) {
						$("#serviceNameIdErr").html("Product Name Already Exists");
						document.getElementById("btnSubmit").disabled = true;
						$("#serviceNameIdErr").show();
					} else {
						$("#serviceNameIdErr").hide();
					}
				}
			});
		}
	};
</script>

<div class="row scrollspy-sidenav pb-20 body-mt-15">
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
</div>

<div class="contact-form-wrapper">

	<div class="box-list">
		<div class="item">
			<div class="row ">

				<div class="text-center underline">
					<h3>Create Product</h3>
				</div>
				<br>

				<form:form method="POST" id="addForm" action="create-productservice"
					modelAttribute="productServiceBO">
					<div class="col-sm-12">
						<div class="col-sm-4">
							<div class="form-group">
								<label>Product Name<span class="font10 text-danger">*</span></label>
								<form:input id="serviceNameId" type="text" path="serviceName"
									class="form-control required" placeholder="Product Name"
									maxlength="150" onchange="roleCheck()"/>
								<form:errors path="serviceName" cssClass="error" />
								<div id="serviceNameIdErr" style="color: red;"></div>
							</div>
						</div>
						<div class="col-sm-4">
							<div class="form-group">
								<label>Product Specification<span
									class="font10 text-danger">*</span></label>
								<form:input id="serviceInput" type="text"
									path="serviceSpecification" class="form-control required"
									placeholder="Service Specification" maxlength="150" />
								<form:errors path="serviceSpecification" cssClass="error" />
								<div id="serviceErr" style="color: red;"></div>
							</div>
						</div>
						<div class="col-sm-4">
							<div class="form-group">
								<label>MinimumStocks<span class="font10 text-danger">*</span></label>
								<form:input type="text" path="minimumStocks"
									id="name" class="form-control required"
									placeholder="minimumstocks" maxlength="10" />
								<form:errors path="minimumStocks" cssClass="error" />
								<div id="nameErr" style="color: red;"></div>
							</div>
						</div>
					</div>

					<div class="col-sm-12">
						<div class="col-sm-4">
							<div class="form-group">
								<label>MaximumStocks<span class="font10 text-danger">*</span></label>
								<form:input type="text" path="maximumStocks"
									id="nameId" class="form-control required"
									placeholder="maximumstocks" maxlength="10" />
								<form:errors path="maximumStocks" cssClass="error" />
								<div id="maximumStockErr" style="color: red;"></div>
							</div>
						</div>

						<div class="col-sm-4">
							<div class="form-group">
								<label>AvailableStocks<span class="font10 text-danger">*</span></label>
								<form:input type="text" path="availableStocks"
									id="availableStocksId" class="form-control required"
									placeholder="availablestocks" maxlength="150" />
								<form:errors path="availableStocks" cssClass="error" />
								<div id="availableStocksErr" style="color: red;"></div>
							</div>
						</div>
						<div class="col-xs-3">
							<label>Product Types</label><font style="color: red;"> * </font>
							<form:select path="productTypesbO.productTypes"
								id="productTypesId" class="form-control required">
								<form:option value="Select">--select--</form:option>
								<form:options items="${producttypesList}"
									itemLabel="productTypes" itemValue="productTypesId"></form:options>
							</form:select>
							<form:errors path="productTypesbO.productTypes" cssClass="error" />
							<div id="productTypesErr" style="color: red;"></div>
						</div>
					</div>

					<div class="col-sm-12">

						<div class="col-sm-4">
							<div class="form-group">
								<label class="element-block fw-normal font-lato">Start
									Date<span class="font10 text-danger">*</span></font>
								</label>
								<input type="" id="datepicker" path="startDate" name="startDate" readonly="true"
									placeholder="Start Date" class="form-control required" 
									/>
								<label class="element-block fw-normal font-lato"
									style="font-size: 10px">EX:DD/MM/YYYY</label>
								<form:errors path="startDate" cssClass="error" />
								<div id="startDateErr" style="color: red;"></div>
							</div>
						</div>
						<div class="col-sm-4">
							<div class="form-group">
								<label>End Date<span class="font10 text-danger">*</span>
								</label>
								<input type="" id="endDateInput" path="endDate"  name="endDate" readonly="true"
									placeholder="End Date" class="form-control required" 
									 />
								<label class="element-block fw-normal font-lato"
									style="font-size: 10px">EX:DD/MM/YYYY</label>
								<form:errors path="endDate" cssClass="error" />
								<div id="endDateErr" style="color: red;"></div>
							</div>
							<span id="errorMessage" style="color: red;"></span>
						</div>

					</div>


					<div style="text-align: right; margin-right: 31px">
						<button type="submit" id="btnSubmit"
							class="btn btn-t-primary btn-theme lebal_align mt-20">Submit</button>
						<a href="view-productservice"><span
							class="btn btn-t-primary btn-theme lebal_align mt-20">Cancel</span></a>
					</div>
				</form:form>
			</div>
		</div>
	</div>

</div>