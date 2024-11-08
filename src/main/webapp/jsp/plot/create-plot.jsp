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

<style>
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
							$('#submit')
									.click( 
											function(e) {
												var isValid = true;
											//Status
											var status = $('#status').val();
											if (status == 'Select') {
												isValid = false;
												$("#statusErr").show();
												$("#statusErr").html("Please Select Status Type");
												$("#status").css({
													"border" : "1px solid red",

												});

											} else {
												$('#statusErr').hide();
												$('#status').css({

													"border" : "",
													"background" : ""
												});
											}
											
											//PlotSquareFeet
											
											var plotSquareFeet = $('#plotSquareFeet').val();
											if (plotSquareFeet == '') {
												isValid = false;
												$("#plotSquareFeetErr").show();
												$("#plotSquareFeetErr").html("Please Enter PlotSquareFeet");
												$("#plotSquareFeet").css({
													"border" : "1px solid red",
												});
											}else if (!/^[0 -9._/&]*$/.test(plotSquareFeet)) {
											    // Check if slaCode contains only alphanumeric characters
											    isValid = false;
											    $("#plotSquareFeetErr").show();
											    $("#plotSquareFeetErr").html("Please Only Numbers Allowed");
											    $("#plotSquareFeet").css({
											        "border": "1px solid red",
											    });
											} else {
												$('#plotSquareFeetErr').hide();
												$('#plotSquareFeet').css({
													"border" : "",
													"background" : ""
												});
											}

											//Length
										
											var length = $('#length').val();
											if (length == '') {
												isValid = false;
												$("#lengthErr").show();
												$("#lengthErr").html("Please Enter Length");
												$("#length").css({
													"border" : "1px solid red",
												});
											}else if (!/^[0 -9._/&]*$/.test(length)) {
											    // Check if slaCode contains only alphanumeric characters
											    isValid = false;
											    $("#lengthErr").show();
											    $("#lengthErr").html("Please Only Numbers Allowed");
											    $("#length").css({
											        "border": "1px solid red",
											    });
											}  else {
												$('#lengthErr').hide();
												$('#length').css({
													"border" : "",
													"background" : ""
												});
											}

											//Width
											
											var width = $('#width').val();
											if (width == '') {
												isValid = false;
												$("#widthErr").show();
												$("#widthErr").html("Please Enter Width");
												$("#width").css({
													"border" : "1px solid red",
												});
											}else if (!/^[0 -9._/&]*$/.test(width)) {
											    // Check if slaCode contains only alphanumeric characters
											    isValid = false;
											    $("#widthErr").show();
											    $("#widthErr").html("Please Only Numbers Allowed");
											    $("#width").css({
											        "border": "1px solid red",
											    });
											}  else {
												$('#widthErr').hide();
												$('#width').css({
													"border" : "",
													"background" : ""
												});
											}
											
											
											var plotNumbers = $('#plotNumbers').val();
											if (plotNumbers == '') {
												isValid = false;
												$("#plotNumbersErr").show();
												$("#plotNumbersErr").html("Please Enter PlotNUmber");
												$("#plotNumbers").css({
													"border" : "1px solid red",

												});

											}else if (!/^[0 -9._/&]*$/.test(plotNumbers)) {
											    // Check if slaCode contains only alphanumeric characters
											    isValid = false;
											    $("#plotNumbersErr").show();
											    $("#plotNumbersErr").html("Please Only Numbers Allowed");
											    $("#plotNumbers").css({
											        "border": "1px solid red",
											    });
											} else {
												$('#plotNumbersErr').hide();
												$('#plotNumbers').css({

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
		function plotNumberCheck() {
			var plotNumbers = document.getElementById("plotNumbers").value;
			document.getElementById("submit").disabled = false;
			if (plotNumbers != '') {
				$
						.ajax({
							url : "check_plotNumbers",
							type : "GET",
							data : 'plotNumbers=' + plotNumbers,
							success : function(result) {

								if (result == true) {
									$("#plotNumbersErr").html(
											"PlotNumbers Already Exists");
									document.getElementById("submit").disabled = true;
									$("#plotNumbersErr").show();
								} else {
									$("#plotNumbersErr").hide();
								}
							}
						});
			}
		};
	</script> 

<body>
	<div class="contact-form-wrapper">

		<div class="box-list">
			<div class="item">
				<div class="row ">

					<div class="text-center underline">
						<h3>Create Plot</h3>
					</div>
					<br>
					<form:form action="create-plot" method="post" modelAttribute="Plot"
						commandName="Plot">
						<h3 class="title">Plot Details</h3>
						<div class="box-list">
							<div class="item">
								<div class="row">

									<div class="col-sm-12">
										<div class="col-sm-4">
											<div class="form-group">

												<label class="leftAlign">Plot Numbers <span
													class="font10 text-danger">*</span></label>
												<form:input type="text" path="plotNumbers" id="plotNumbers"
													class="form-control required" placeholder="plotNumbers"
													maxlength="5" onchange="plotNumberCheck()"
													text-align="left" />
												<form:errors path="plotNumbers" class="error" />
												<div id="plotNumbersErr" style="color: red;"></div>
											</div>
										</div>

										<div class="col-sm-4">
											<div class="form-group">
												<label>PlotSquareFeet <span
													class="font10 text-danger">*</span></label>
												<form:input type="text" path="plotSquareFeet"
													id="plotSquareFeet" class="form-control required"
													placeholder="plotSquareFeet" />
												<form:errors path="plotSquareFeet" class="error" />
												<div id="plotSquareFeetErr" style="color: red;"></div>
											</div>
										</div>

										<div class="col-sm-4">
											<div class="form-group">
												<label>Length <span class="font10 text-danger">*</span></label>
												<form:input type="text" path="length" id="length"
													class="form-control required" placeholder="length" />
												<form:errors path="length" class="error" />
												<div id="lengthErr" style="color: red;"></div>
											</div>

										</div>
									</div>

									<div class="col-sm-12">
										<div class="col-sm-4">
											<div class="form-group">
												<label>Width <span class="font10 text-danger">*</span></label>
												<form:input type="text" path="width" id="width"
													class="form-control required" placeholder="width" />
												<form:errors path="width" class="error" />
												<div id="widthErr" style="color: red;"></div>
											</div>
										</div>

										<div class="col-sm-4">
											<div class="form-group">
												<label>Status<span class="font10 text-danger">*</span></label>
												<!-- <label class="hidden-xs">&nbsp;</label> -->
												<form:select type="text" path="status" id="status"
													class="form-control required">
													<form:option value="Select">--Select Status--</form:option>
													<form:option value="sold">Sold</form:option>
													<form:option value="available">Available</form:option>
												</form:select>
												<form:errors path="status" class="error" />
												<div id="statusErr" style="color: red"></div>

											</div>
										</div>

									</div>
								</div>
							</div>
						</div>
						<br>



						<br>
						<div style="text-align: right; margin-right: 31px">
							<button type="submit" id="submit"
								class="btn btn-t-primary btn-theme lebal_align mt-20">Submit</button>
							<a href="view-plot"><span
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