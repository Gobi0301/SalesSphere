<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script>
	$(document).ready(function() {
		$('#btnsubmit').click(function(e) {

			//sgst
			//var isValid = true;
			var sgst = $('#sgstId').val();
			if (sgst == '') {
				isValid = false;
				$("#sgstErr").show();
				$("#sgstErr").html("Please Enter SGST Value");
				$("#sgstId").css({
					"border" : "1px solid red",

				});

			} else {
				$('#sgstErr').hide();
				$('#sgstId').css({

					"border" : "",
					"background" : ""
				});
			}

			//cgst
			//var isValid = true;
			var cgst = $('#cgstId').val();
			if (cgst == '') {
				isValid = false;
				$("#cgstErr").show();
				$("#cgstErr").html("Please Enter CGST Value");
				$("#cgstId").css({
					"border" : "1px solid red",

				});

			} else {
				$('#cgstErr').hide();
				$('#cgstId').css({

					"border" : "",
					"background" : ""
				});
			}

			if (isValid == false)
				e.preventDefault();

		});
	});
</script>

<!-- <script type="text/javascript">
	$(function() {
		var dtToday = new Date();

		var month = dtToday.getMonth() + 1;
		var day = dtToday.getDate();
		var year = dtToday.getFullYear();
		if (month < 10)
			month = '0' + month.toString();
		if (day < 10)
			day = '0' + day.toString();

		var minDate = year + '-' + month + '-' + day;

		$('#inputFirstName').attr('min', minDate);
	});
</script> -->
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
</script>
 -->
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
 
 <div class="contact-form-wrapper">
	<div class="box-list">
		<div class="item">
			<div class="row ">
				<div class="text-center underline">
					<h3>Edit Gst</h3>
				</div>
				<br>
				<form:form method="POST" id="addForm" action="edit-gst"
					modelAttribute="gstBO">
					<div class="row">
					<div class="col-sm-6">
							<div class="form-group">
								<label>Product<span class="font10 text-danger">*</span></label>
								<form:select type="text" path="product.serviceId"  id="serviceNameId"
										class="form-control">	
									<form:options items="${productBOList}" itemLabel="serviceName"
										itemValue="serviceId" />
								</form:select>
								<form:errors path="product.serviceName" class="error" />
								<div id="productIdErr" style="color: red;"></div>
							</div>
						</div>
						
						<div class="col-sm-6">
							<div class="form-group">
								<label> SGst Value <span class="font10 text-danger">*</span></label>
								<form:input type="text" path="sgst" id="sgstId"
									class="form-control required" placeholder="Sgst Value"
									maxlength="150" />
								<form:errors path="sgst" class="error" />
								<div id="sgstErr" style="color: red;"></div>
							</div>
						</div>
						<form:hidden path="gstId" />

						<div class="col-sm-6">
							<div class="form-group">
								<label>CGst Value <span class="font10 text-danger">*</span></label>
								<form:input id="cgstId" type="text" path="cgst"
									class="form-control required" placeholder="Cgst Value"
									maxlength="150" />
								<form:errors path="cgst" class="error" />
								<div id="cgstErr" style="color: red;"></div>
							</div>
						</div>

						<div class="col-sm-6">
							<div class="form-group">
								<label>Date <span class="font10 text-danger">*</span></label>
								<form:input id="datepicker" type="text" path="beginDate"
									 readonly = "true" class="form-control required" placeholder="Date"
									maxlength="150" />
								<form:errors path="beginDate" class="input_error" />
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-1" style="float: right;">
							<a href=view-gst?page=1><span
								class="btn btn-t-primary btn-theme lebal_align">Cancel</span></a>
						</div>
						<div class="col-sm-1" style="float: right;">
							<button type="submit" id="btnsubmit"
								class="btn btn-t-primary btn-theme lebal_align">Update</button>
						</div>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</div>
