<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>


<link href="resources/css/jquery-ui-1.10.4.custom.css" rel="stylesheet">
<script type="text/javascript" src="resources/js/jquery-ui.min.js"></script>
<script type="text/javascript"
	src="http://js.nicedit.com/nicEdit-latest.js"></script>
<!-- <script>
	$(document).ready(function() {
		$('#datepicker').datepicker({

			changeMonth : true,
			changeYear : true,
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
			minDate: today, 
			onSelect : function(selected) {
				var dt = new Date(selected);
				dt.setDate(dt.getDate());
				$("#endDateInput").datepicker("option", "minDate", dt);
			}
		});
	});
</script>

<script>
	$(document)
			.ready(
					function() {

						$('#procurementId').focus();

						$('#btnsubmit')
								.click(
										function(e) {

											//Product Name..
											var isValid = true;
											var serviceName = $(
													'#serviceId').val();
											if (serviceName == 'Select') {
												isValid = false;
												$("#serviceNameErr").show();
												$("#serviceNameErr")
														.html(
																"Please Select Product Name");
												$("#serviceId").css({
													"border" : "1px solid red",
												});
											} else {
												$('#serviceNameErr').hide();
												$('#serviceId').css({
													"border" : "",
													"background" : ""
												});
											}

											//Supplier Name..
											var serviceName = $(
													'#supplierNameId').val();
											if (serviceName == 'Select') {
												isValid = false;
												$("#supplierNameErr").show();
												$("#supplierNameErr")
														.html(
																"Please Select Supplier Name");
												$("#supplierNameId").css({
													"border" : "1px solid red",
												});
											} else {
												$('#supplierNameErr').hide();
												$('#supplierNameId').css({
													"border" : "",
													"background" : ""
												});
											}

											//Expected Date..
											var expectedDate = $('#datepicker')
													.val();
											if (expectedDate == '') {
												isValid = false;
												$("#expectedDateErr").show();
												$("#expectedDateErr")
														.html(
																"Please enter Expected Date");
												$("#datepicker").css({
													"border" : "1px solid red",
												});
											} else {
												$('#expectedDateErr').hide();
												$('#datepicker').css({
													"border" : "",
													"background" : ""
												});
											}

											//Minimum Stock..
											var minimumStock = parseInt($(
													'#name').val());
											var maximumStock = parseInt($(
													'#nameId').val());
											if (minimumStock == 0
													|| maximumStock == 0) {

												if (minimumStock == 0) {
													isValid = false;
													$("#nameErr").show();
													$("#nameErr")
															.html(
																	"Please Enter Minimum Stock");
													$("#name").css({
														"border" : "1px solid red",

													});

												}else {
													$('#nameErr').hide();
													$('#name').css({
														"border" : "",
														"background" : ""
													});
												}

												if(maximumStock == 0){

													isValid = false;
													$("#maximumStockErr").show();
													$("#maximumStockErr")
															.html(
																	"Please Enter Maximum Stock");
													$("#nameId").css({
														"border" : "1px solid red"
													});
												}else {
													$('#maximumStockErr').hide();
													$('#nameId').css({
														"border" : "",
														"background" : ""
													});
												}

											}
											else if (!/^[0-9]{1,10}$/
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
													$("#name").css({
														"border" : "1px solid red",

													});

												}else {
													$('#nameErr').hide();
													$('#name').css({
														"border" : "",
														"background" : ""
													});
												}

												if(!/^[0-9]{1,10}$/
														.test(maximumStock)){

													isValid = false;
													$("#maximumStockErr").show();
													$("#maximumStockErr")
															.html(
																	"Please Enter Numeric Values Only");
													$("#nameId").css({
														"border" : "1px solid red"
													});
												}else {
													$('#maximumStockErr').hide();
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
												$("#name").css({
													"border" : "1px solid red"
												});
												$("#nameId").css({
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
											//Available Stock..
											var availableStock = $(
													'#availableStockId').val();
											if (availableStock == '0') {
												isValid = false;
												$("#availableStockErr").show();
												$("#availableStockErr")
														.html(
																"Please enter Available Stock");
												$("#availableStockId").css({
													"border" : "1px solid red",
												});
											} else if (!/^[0-9]{1,10}$/
													.test(availableStock)) {

												isValid = false;
												$("#availableStockErr").show();
												$("#availableStockErr")
														.html(
																"Please Enter Numeric Values Only");
												$("#availableStockId").css({
													"border" : "1px solid red",

												});

											} else {
												$('#availableStockErr').hide();
												$('#availableStockId').css({
													"border" : "",
													"background" : ""
												});
											}

											//QuantityOfProducts..
											var quantityOfProducts = $(
													'#quantityOfProductsId')
													.val();
											if (quantityOfProducts == '') {
												isValid = false;
												$("#qErr").show();
												$("#qErr")
														.html(
																"Please enter Quantity Of Products");
												$("#quantityOfProductsId")
														.css(
																{
																	"border" : "1px solid red",
																});
											} else if (!/^[0-9]{1,10}$/
													.test(quantityOfProducts)) {

												isValid = false;
												$("#qErr").show();
												$("#qErr")
														.html(
																"Please Enter Numeric Values Only");
												$("#quantityOfProductsId")
														.css(
																{
																	"border" : "1px solid red",

																});

											} else {
												$('#qErr').hide();
												$('#quantityOfProductsId').css(
														{
															"border" : "",
															"background" : ""
														});
											}

											// Unit Cost..
											var unitOfCost = $('#unitOfCostId')
													.val();
											if (unitOfCost == '0') {
												isValid = false;
												$("#unitOfCostErr").show();
												$("#unitOfCostErr")
														.html(
																"Please enter Unit Cost");
												$("#unitOfCostId").css({
													"border" : "1px solid red",
												});
											} else if (!/^[0-9]{1,10}$/
													.test(unitOfCost)) {

												isValid = false;
												$("#unitOfCostErr").show();
												$("#unitOfCostErr")
														.html(
																"Please Enter Numeric Values Only");
												$("#unitOfCostId").css({
													"border" : "1px solid red",

												});

											} else {
												$('#unitOfCostErr').hide();
												$('#unitOfCostId').css({
													"border" : "",
													"background" : ""
												});
											}

											//Total Cost..
											var availableStock = $(
													'#totalCostId').val();
											if (availableStock == '0') {
												isValid = false;
												$("#totalCostErr").show();
												$("#totalCostErr")
														.html(
																"Please enter Total Cost");
												$("#totalCostId").css({
													"border" : "1px solid red",
												});
											} else if (!/^[0-9]{1,10}$/
													.test(availableStock)) {

												isValid = false;
												$("#totalCostErr").show();
												$("#totalCostErr")
														.html(
																"Please Enter Numeric Values Only");
												$("#totalCostId").css({
													"border" : "1px solid red",

												});

											} else {
												$('#totalCostErr').hide();
												$('#totalCostId').css({
													"border" : "",
													"background" : ""
												});
											}

											//Maximum Stock..
											var maximumStock = $('#nameId')
													.val();
											if (!/^[0-9]{1,10}$/.test(maximumStock)) {
												isValid = false;
												$("#maximumStockErr").show();
												$("#maximumStockErr")
														.html(
																"Must Enter Number Only ");
												$("#nameId").css({
													"border" : "1px solid red",
												});
											} else {
												$('#maximumStockErr').hide();
												$('#nameId').css({
													"border" : "",
													"background" : ""
												});
											}

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
							<h3>Edit Procurement</h3>
						</div>

				<form:form method="POST" id="addForm" action="edit-procurement"
					modelAttribute="bo">
					  <form:hidden path="procurementId" name="id"
										value="${bo.procurementId}" />

					<div class="col-sm-12">
						<div class="col-sm-4">
							<div class="form-group">
								<label>Product Name<span class="font10 text-danger">*</span></label>
								<form:select type="text" path="productServiceBO.serviceName"
									id="serviceId" class="form-control required">

									<%-- <form:option value="${bo.productServiceBO.serviceId}">${bo.productServiceBO.serviceName}</form:option>
									<form:option value="Select">-- Select --</form:option> --%>
									<form:options items="${productlist}" itemLabel="serviceName"
										itemValue="serviceId" />
								</form:select>
								<form:errors path="productServiceBO.serviceName" class="error" />
								<div id="serviceNameErr" style="color: red;"></div>
							</div>
						</div>
						<div class="col-sm-4">
							<div class="form-group">
								<label>Supplier Name<span class="font10 text-danger">*</span></label>
								<form:select type="text" path="supplierBO.supplierName" id="supplierNameId"
									class="form-control required">
									<%-- <form:option value="${bo.supplierBO.supplierId}">${bo.supplierBO.supplierName}</form:option>
									<form:option value="${bo.supplierBO.supplierName}"></form:option>
									<form:option value="Select">-- Select --</form:option> --%>
									<form:options items="${supplierLists}" itemLabel="supplierName"
										itemValue="supplierId" />
								</form:select>
								<form:errors path="supplierBO.supplierName" class="error" />
								<div id="supplierNameErr" style="color: red;"></div>
							</div>


						</div>
						<div class="col-sm-4">
							<div class="form-group">
								<label class="element-block fw-normal font-lato">Expected
									Date<span class="font10 text-danger">*</span>
								</label>
								<form:input type="text" id="datepicker" path="expectedDate" readonly="true"
									placeholder="ExpectedDate" class="form-control element-block" />
								<!-- <label class="element-block fw-normal font-lato"
									style="font-size: 10px">EX:MM/DD/YYYY</label> -->

								<form:errors path="expectedDate" class="error" />
								<div id="expectedDateErr" style="color: red;"></div>

							</div>

						</div>

					</div>


					<div class="col-sm-12">
						<div class="col-sm-4">
							<div class="form-group">
								<label> Minimum Stock <span
									class="font10 text-danger">*</span></label>
								<form:input type="text" id="name" path="minimumStock"
									class="form-control required" placeholder="Minimum Stock"
									maxlength="150" />
								<form:errors path="minimumStock" class="error" />
								<div id="nameErr" style="color: red;"></div>
							</div>
						</div>
						<div class="col-sm-4">
							<div class="form-group">
								<label> Maximum Stock <span
									class="font10 text-danger">*</span></label>
								<form:input type="text" id="nameId" path="maximumStock"
									class="form-control required" placeholder="Maximum Stock"
									maxlength="150" />
								<form:errors path="maximumStock" class="error" />
								<div id="maximumStockErr" style="color: red;"></div>
							</div>
							
						</div>

						<div class="col-sm-4">
							<div class="form-group">
								<label> Available Stock <span
									class="font10 text-danger">*</span></label>
								<form:input type="text" id="availableStockId" path="availableStock"
									class="form-control required" placeholder="Available Stock"
									maxlength="150" />
								<form:errors path="availableStock" class="error" />
								<div id="availableStockErr" style="color: red;"></div>
							</div>



						</div>

					</div>



					<div class="col-sm-12">
						<div class="col-sm-4">
							<div class="form-group">
								<label> QuantityOfProducts <span
									class="font10 text-danger">*</span></label>
								<form:input type="text" id="quantityOfProductsId" path="quantityOfProducts"
									class="form-control required" placeholder="Quantity Of Products"
									maxlength="120" />
								<form:errors path="quantityOfProducts" class="error" />
								<div id="qErr" style="color: red;"></div>
							</div>
						</div>
						<div class="col-sm-4">
							<div class="form-group">
								<label> UnitCost <span
									class="font10 text-danger">*</span></label>
								<form:input type="text" id="unitOfCostId" path="unitOfCost"
									class="form-control required" placeholder="Unit Cost" maxlength="10" />
								<form:errors path="unitOfCost" class="error" />
								<div id="unitOfCostErr" style="color: red;"></div>
							</div>

						</div>
						<div class="col-sm-4">
							<div class="form-group">
								<label> TotalCost <span
									class="font10 text-danger">*</span></label>
								<form:input type="text" id="totalCostId" path="totalCost"
									class="form-control required" placeholder="Total Cost"
									maxlength="150" />
								<form:errors path="totalCost" class="error" />
								<div id="totalCostErr" style="color: red;"></div>
							</div>
						</div>
 

					</div>
					


					<div style="text-align: right; margin-right: 31px">
						<div class="form-group">
							<form:button type="submit" id="btnsubmit"
								class="btn btn-t-primary btn-theme lebal_align mt-20 ">Submit</form:button>
							<a href="view-procurement?page=1"><span
								class="btn btn-t-primary btn-theme lebal_align mt-20">Cancel</span></a>
						</div>
					</div>

				</form:form>




			</div>
		</div>
	</div>
</div>
<br>
<br>
