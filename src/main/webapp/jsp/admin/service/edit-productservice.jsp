<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
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

<!-- <script>
	$(function() {
		$("#datepicker").datepicker();
	});
</script>
<script>
	$(function() {
		$("#datepickers").datepicker();
	});
</script> -->
<!-- <script>
	$(document).ready(function() {
		$('#btnsubmit').click(function(e) {
			
//productname
			var isValid = true;
var product = $('#inputFirstName').val();
if (product == '') {
    isValid = false;
    $("#proErr").show();
    $("#proErr").html("Please Enter Product Name");
    $("#inputFirstName").css({
        "border" : "1px solid red",

    });

} else if (!/^[a-zA-Z\s]*$/g.test(product)) {
    $("#proErr").show();
    $("#proErr").html("Please Enter Characters Only");
    $("#inputFirstName").css({
        "border" : "1px solid red",

    });
} else {
    $('#proErr').hide();
    $('#inputFirstName').css({
        "border" : "",
        "background" : ""
    });
}
			//productspecification
			
			var serviceSpecification = $('#serviceSpecificationId').val();
			if (serviceSpecification == '') {
				isValid = false;
				$("#prospecificationErr").show();
				$("#prospecificationErr").html("Please Enter Product Specification");
				$("#serviceSpecificationId").css({
					"border" : "1px solid red",

				});

			} else {
				$('#prospecificationErr').hide();
				$('#serviceSpecificationId').css({

					"border" : "",
					"background" : ""
				});
			}


			//minimumStocks
			
			var minimumStocks = $('#minimumStocksId').val();
			if (minimumStocks == '') {
				isValid = false;
				$("#minimumStocksErr").show();
				$("#minimumStocksErr").html("Please Enter Product MinimumStocks");
				$("#minimumStocksId").css({
					"border" : "1px solid red",
				});
			} else if (!/^[0-9]{1,10}$/.test(minimumStocks)) {
				$("#minimumStocksErr").show();
				$("#minimumStocksErr").html("Please Enter number Only");
				$("#minimumStocksId").css({
					"border" : "1px solid red",
				});
				var isValid = false;
			} else {
				$('#minimumStocksErr').hide();
				$('#minimumStocksId').css({

					"border" : "",
					"background" : ""
				});
			}


			//availableStocks
			
			var maximumStocks = $('#maximumStocksId').val();
			if (maximumStocks == '') {
				isValid = false;
				$("#maximumStocksErr").show();
				$("#maximumStocksErr").html("Please Enter Product MaximumStocks");
				$("#maximumStocksId").css({
					"border" : "1px solid red",
				});
			}  else if (!/^[0-9]{1,10}$/.test(maximumStocks)) {
				$("#maximumStocksErr").show();
				$("#maximumStocksErr").html("Please Enter number Only");
				$("#maximumStocksId").css({
					"border" : "1px solid red",
				});
				var isValid = false;
			}else {
				$('#maximumStocksErr').hide();
				$('#maximumStocksId').css({

					"border" : "",
					"background" : ""
				});
			}

			if (minimumStocks >= maximumStocks) {
				isValid = false;
				$("#minimumStocksErr").show();
				$("#minimumStocksErr")
						.html(
								"Minimum stocks should be less than maximum stocks.");
				$("#minimumStocksId").css({
					"border" : "1px solid red",
				});
			}

			//producttype
			
			var protype = $('#typeId').val();
			if (protype == 'select') {
				isValid = false;
				$("#typeErr").show();
				$("#typeErr").html("Please Select ProductType");
				$("#typeId").css({
					"border" : "1px solid red",

				});

			} else {
				$('#typeErr').hide();
				$('#typeId').css({

					"border" : "",
					"background" : ""
				});
			}

			//startdate
			
			var datepick = $('#datepicker').val();
			if (datepick == '') {
				isValid = false;
				$("#datepickerErr").show();
				$("#datepickerErr").html("Please Select Start Date");
				$("#datepicker").css({
					"border" : "1px solid red",

				});

			} else {
				$('#datepickerErr').hide();
				$('#datepicker').css({

					"border" : "",
					"background" : ""
				});
			}
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
</script> -->

<script>
	$(document)
			.ready(
					function() {

						$('#name').focus();

						$('#btnsubmit')
								.click(
										function(e) {
											var isValid = true;
											var serviceName = $('#inputFirstName').val();
											if (serviceName == '') {
												isValid = false;
												$("#proErr").show();
												$("#proErr").html("Please enter  Product Name");
												$("#inputFirstName").css({
													"border" : "1px solid red",

												});

											} else {
												$('#proErr').hide();
												$('#inputFirstName').css({

													"border" : "",
													"background" : ""
												});
											}
											//product Specification

											var serviceSpecification = $(
													'#serviceSpecificationId').val();
											if (serviceSpecification == '') {
												isValid = false;
												$("#prospecificationErr").show();
												$("#prospecificationErr")
															.html(
																"Please enter product Specification value");
												$("#serviceSpecificationId").css({
													"border" : "1px solid red",

												});

											} else {
												$('#prospecificationErr').hide();
												$('#serviceSpecificationId').css({

													"border" : "",
													"background" : ""
												});
											}

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
											if (availableStocks == '0') {
												isValid = false;
												$("#availableStocksErr").show();
												$("#availableStocksErr")
														.html(
																"Please enter Available Stock value");
												$("#availableStocksId").css({
													"border" : "1px solid red",

												});
                                               
											}else if (!/^[0-9]{1,10}$/.test(availableStocks)) {
												isValid = false;
												$("#availableStocksErr").show();
												$("#availableStocksErr").html("Please enter number Only");
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

											var datepicker = $("#datepicker")
													.val();
											if (datepicker == '') {
												isValid = false;
												$("#startDateErr").show();
												$("#startDateErr").html(
														"Please select date")
												$("#datepicker").css({
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
																"Please select end date")
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
																"Please enter product type");
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

<div class="warning">
	<div class="box-list">
		<div class="item">
			<div class="row ">
				<div class="text-center underline">
					<h3>Edit Product</h3>
				</div>
				<br>
				<form:form method="POST" id="addForm" action="edit-productservice"
					modelAttribute="productBO">


					<div class="col-sm-12">
						<div class="col-sm-4">
							<div class="form-group">
								<label> Product Name <span class="font10 text-danger">*</span></label>
								<form:input id="inputFirstName" type="text" path="serviceName"
									class="form-control required" placeholder="Product Name"
									maxlength="150" />
								<form:errors path="serviceName" class="error" />
								<div id="proErr" style="color: red;"></div>
							</div>
						</div>
						<form:hidden path="serviceId" />

						<div class="col-sm-4">
							<div class="form-group">
								<label>Product Specification <span
									class="font10 text-danger">*</span></label>
								<form:input type="text" path="serviceSpecification" id="serviceSpecificationId"
									class="form-control required" placeholder="Specification" />
								<form:errors path="serviceSpecification" class="error" />
								<div id="prospecificationErr" style="color: red;"></div>
							</div>
						</div>

						<div class="col-sm-4">
							<div class="form-group">
								<label>MinimumStocks<span class="font10 text-danger">*</span></label>
								<form:input type="text" path="minimumStocks" id="name"
									class="form-control required" placeholder="minimumstocks"
									maxlength="150" />
								<form:errors path="minimumStocks" cssClass="error" />
								<div id="nameErr" style="color: red;"></div>
							</div>
						</div>
					</div>
					<div class="col-sm-12">
						<div class="col-sm-4">
							<div class="form-group">
								<label>MaximumStocks<span class="font10 text-danger">*</span></label>
								<form:input type="text" path="maximumStocks" id="nameId"
									class="form-control required" placeholder="maximumstocks"
									maxlength="150" />
								<form:errors path="maximumStocks" cssClass="error" />
								<div id="maximumStockErr" style="color: red;"></div>
							</div>
						</div>
						<div class="col-sm-4">
							<div class="form-group">
								<label>AvailableStocks<span class="font10 text-danger">*</span></label>

								<form:input type="text" path="availableStocks"  id="availableStocksId"
									class="form-control required" placeholder="availablestocks"
									maxlength="150" />
								<form:errors path="availableStocks" cssClass="error" />
								<div id="availableStocksErr"   style="color: red;"></div>
							</div>
						</div>
						<div class="col-xs-3">
							<label>Product Types</label><font style="color: red;">*</font>
							<form:select path="productTypesbO.productTypesId" id="typeId" class="form-control required">									
								<%-- <form:option value="Select">--select--</form:option> --%>
								<form:options items="${producttypesList}"
									itemLabel="productTypes" itemValue="productTypesId" ></form:options>
									<form:errors path="productTypesbO.productTypes" cssClass="error" />
								<div id="typeErr" style="color: red;"></div>
									
							</form:select>
						</div> 
					</div>

					<div class="col-sm-12">
						<div class="col-sm-4">
							<div class="form-group">
								<label class="element-block fw-normal font-lato">Start
									Date<span class="font10 text-danger">*</span></font>
								</label>
								<form:input type="text" id="datepicker" path="startDate" readonly="true"
									placeholder="Start Date" class="form-control required" />
								<label class="element-block fw-normal font-lato"
									style="font-size: 10px">EX:MM/DD/YYYY</label>
								
							</div>
						</div>
						<div class="col-sm-4">
							<div class="form-group">
								<label>End Date<span class="font10 text-danger">*</span>
								</label>
								<form:input type="text" id="endDateInput" path="endDate" readonly="true"
									placeholder="End Date" class="form-control required" />
								<label class="element-block fw-normal font-lato"
									style="font-size: 10px">EX:MM/DD/YYYY</label>
							</div>
						</div>
						
					</div>

					<div style="text-align: right; margin-right: 31px">
						<button type="submit" id="btnsubmit"
							class="btn btn-t-primary btn-theme lebal_align mt-20">Update</button>
						<a href=view-productservice?page=1><span
							class="btn btn-t-primary btn-theme lebal_align mt-20">Cancel</span></a>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</div>