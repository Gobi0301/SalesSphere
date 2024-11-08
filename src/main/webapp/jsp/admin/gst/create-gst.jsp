<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>





<script>
	$(document).ready(function() {

		$('#sgst').focus();

		$('#btnsubmit').click(function(e) {

			//SGST..
			var isValid = true;
			var sgst = $('#sgst').val();
			if (sgst == '') {
				isValid = false;
				$("#sgstErr").show();
				$("#sgstErr").html("Please Enter SGST");
				$("#sgst").css({
					"border" : "1px solid red",
				});
			} else if (/[^0-9.%]/g.test(sgst)) {
				isValid = false;
				$("#sgstErr").show();
				$("#sgstErr").html("Please enter number Only"); //!/^(?:100(?:\.0{1,2})?|[1-9]?\d(?:\.\d{1,2})?)$/
				$("#sgst").css({
					"border" : "1px solid red",

				});

			} else {
				$('#sgstErr').hide();
				$('#sgst').css({
					"border" : "",
					"background" : ""
				});
			}

			//CGST..
			//var isValid = true;
			var cgst = $('#cgst').val();
			if (cgst == '') {
				isValid = false;
				$("#cgstErr").show();
				$("#cgstErr").html("Please Enter CGST");
				$("#cgst").css({
					"border" : "1px solid red",
				});
			} else if (/[^0-9.%]/g.test(cgst)) {
				isValid = false;
				$("#cgstErr").show();
				$("#cgstErr").html("Please enter number Only");
				$("#cgst").css({
					"border" : "1px solid red",

				});

			} else {
				$('#cgstErr').hide();
				$('#cgst').css({
					"border" : "",
					"background" : ""
				});
			}

			//date
			var start = $('#datepicker').val();
			if (start == '') {
				isValid = false;
				$("#starttErr").show();
				$("#starttErr").html("Please Enter Date");
				$("#datepicker").css({
					"border" : "1px solid red",

				});

			} else {
				$('#starttErr').hide();
				$('#datepicker').css({

					"border" : "",
					"background" : ""
				});
			}
            //product
			var product = $('#product_Id').val();
			if (product == 'Select') {
				isValid = false;
				$("#productIdErr").show();
				$("#productIdErr").html("Please Select Product");
				$("#product_Id").css({
					"border" : "1px solid red",

				});

			} else {
				$('#productIdErr').hide();
				$('#product_Id').css({

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
	function roleCheck() {
		var product = document.getElementById("product_Id").value;
		document.getElementById("btnsubmit").disabled = false;
		if (product != '') {
			$.ajax({
				url : "check_product",
				type : "GET",
				data : 'product=' + product,
				success : function(result) {

					if (result == true) {
						$("#productIdErr").html("ProductType Already Exists");
						document.getElementById("btnsubmit").disabled = true;
						$("#productIdErr").show();
					} else {
						$("#productIdErr").hide();
					}
				}
			});
		}
	};
</script>
<!--  <script>
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
	});
</script>  -->
<script>
    $(function() {
        var today = new Date(); 

        $("#datepicker").datepicker({
            changeMonth: true,
            changeYear: true,
            numberOfMonths: 1,
            minDate: today, 
            onSelect: function(selected) {
                var dt = new Date(selected);
                dt.setDate(dt.getDate()); // Set the selected date
                $("#endDateInput").datepicker("option", "minDate", dt);
            }
        });
    });
</script>

<!-- <script type="text/javascript">
	function sgstCheck() {
		var sgst = document.getElementById("sgst").value;
		document.getElementById("btnsubmit").disabled = false;
		if (sgst != '') {
			$.ajax({
				url : "check_sgst",
				type : "GET",
				data : 'sgst=' + sgst,
				success : function(result) {

					if (result == true) {
						$("#sgstErr").html("SGST value Already Exists");
						document.getElementById("btnsubmit").disabled = true;
						$("#sgstErr").show();
					} else {
						$("#sgstErr").hide();
					}
				}
			});
		}
	};
</script>

<script type="text/javascript">
	function cgstCheck() {
		var cgst = document.getElementById("cgst").value;
		document.getElementById("btnsubmit").disabled = false;
		if (cgst != '') {
			$.ajax({
				url : "check_cgst",
				type : "GET",
				data : 'cgst=' + cgst,
				success : function(result) {

					if (result == true) {
						$("#cgstErr").html("CGST value Already Exists");
						document.getElementById("btnsubmit").disabled = true;
						$("#cgstErr").show();
					} else {
						$("#cgstErr").hide();
					}
				}
			});
		}
	};
</script> -->



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
					<h3>Create GST</h3>
				</div>
				<br>
				<form:form method="POST" id="addForm" action="create-gst"
					modelAttribute="gstBO">
					<div class="row">
						<div class="col-sm-6">
							<div class="form-group">
								<label>Product<span class="font10 text-danger">*</span></label>
								<form:select type="text" path="product.serviceName"
									id="product_Id" class="form-control required" onchange="roleCheck()">
									<form:option value="Select">-- Select --</form:option>
									<form:options items="${productBOList}" itemLabel="serviceName"
										itemValue="serviceId" />
								</form:select>
								<form:errors path="product.serviceName" class="error" />
								<div id="productIdErr" style="color: red;"></div>
							</div>
						</div>
						<div class="col-sm-6">
							<div class="form-group">
								<label>Sgst Value<span class="font10 text-danger">*</span></label>
								<form:input id="sgst" type="text" path="sgst"
									class="form-control required" placeholder="Sgst Value"
									maxlength="150" onchange="sgstCheck()" />
								<form:errors path="sgst" cssClass="error" />
								<div id="sgstErr" style="color: red;"></div>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-6">
							<div class="form-group">
								<label>Cgst Value<span class="font10 text-danger">*</span></label>
								<form:input id="cgst" type="text" path="cgst"
									class="form-control required" placeholder="Cgst Value"
									maxlength="150" onchange="cgstCheck()" />
								<form:errors path="cgst" cssClass="error" />
								<div id="cgstErr" style="color: red;"></div>
							</div>
						</div>

						<div class="col-sm-6">
							<div class="form-group">
								<label class="element-block fw-normal font-lato"> Date<span
									class="font10 text-danger">*</span></font>
								</label>
								<form:input type="text" id="datepicker" path="startDate" readonly="true"
									placeholder="Date" class="form-control element-block" />
								<label class="element-block fw-normal font-lato"
									style="font-size: 10px">EX:MM/DD/YYYY</label>

								<form:errors path="startDate" cssClass="error" />
								<div id="starttErr" style="color: red;"></div>
							</div>
						</div>

					</div>

					<br />
					<div class="row">
						<div class="col-sm-1" style="float: right;">
							<a href="view-gst"><span
								class="btn btn-t-primary btn-theme lebal_align">Cancel</span></a>
						</div>
						<div class="col-sm-1" style="float: right;">
							<button type="submit" id="btnsubmit"
								class="btn btn-t-primary btn-theme lebal_align">Submit</button>
						</div>
					</div>
				</form:form>

			</div>
		</div>
	</div>
</div>
