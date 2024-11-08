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


<script>
	$(document).ready(function() {

		$('#btnsubmit').click(function(e) {
			var isValid = true;
			//Sla Name
			
			var slaName = $('#slaId').val();
			if (slaName == '') {
				isValid = false;
				$("#slaNameErr").show();
				$("#slaNameErr").html("Please Enter SLA");
				$("#slaId").css({
					"border" : "1px solid red",
				});
			} else if (!/^[0-9]{1,10}$/.test(slaName)) {
				isValid = false;
				$("#slaNameErr").show();
				$("#slaNameErr").html("Please enter Numbers only");
				$("#slaId").css({
					"border" : "1px solid red",

				});

			} else {
				$('#slaNameErr').hide();
				$('#slaId').css({
					"border" : "",
					"background" : ""
				});
			}

			//SLA Code
		//	var isValid = true;
			var slaCode = $('#slaCodeId').val();
			if (slaCode == '') {
				isValid = false;
				$("#slaCodeErr").show();
				$("#slaCodeErr").html("Please Enter SLA Code");
				$("#slaCodeId").css({
					"border" : "1px solid red",
				});
			} else {
				$('#slaCodeErr').hide();
				$('#slaCodeId').css({
					"border" : "",
					"background" : ""
				});
			}

			//SLA Date
		//	var isValid = true;
			var Date = $('#datepicker').val();
			if (Date == '') {
				isValid = false;
				$("#dateErr").show();
				$("#dateErr").html("Please Enter Date");
				$("#datepicker").css({
					"border" : "1px solid red",
				});
			} else {
				$('#dateErr').hide();
				$('#datepicker').css({
					"border" : "",
					"background" : ""
				});
			}
			//SLA descriptions
	//		var isValid = true;
			var descriptions = $('#descriptionsId').val();
			if (descriptions == '') {
				isValid = false;
				$("#descriptionsErr").show();
				$("#descriptionsErr").html("Please Enter Descriptions");
				$("#descriptionsId").css({
					"border" : "1px solid red",
				});
			} else {
				$('#descriptionsErr').hide();
				$('#descriptionsId').css({
					"border" : "",
					"background" : ""
				});
			}

			if (!isValid){
				e.preventDefault();
			}});
	});
</script>
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
				dt.setDate(dt.getDate());
				$("#endDateInput").datepicker("option", "minDate", dt);
			}
		});
	});
</script>
<script type="text/javascript">
	function slaCodeCheck() {
		var slaCode = document.getElementById("slaCodeId").value;
		 document.getElementById("btnsubmit").disabled = false;
		if (slaCode != '') {
			$.ajax({
				url : "check_slaCode",
				type : "GET",
				data : 'slaCode=' + slaCode,
				success : function(result) {

					if (result == true) {
						$("#slaCodeErr").html("WI_SLA Code Already Exists");
						document.getElementById("btnsubmit").disabled = true;
						$("#slaCodeErr").show();
						$("#slaCodeId").css({
							"border" : "1px solid red",
						});
					} else {
						$("#slaCodeErr").hide();
						$("#slaCodeId").css({
							"border" : "",
							"background" : ""
						});
					}
				}
			});
		}
	};
</script>


<link href="resources/css/jquery-ui-1.10.4.custom.css" rel="stylesheet">


<div class="contact-form-wrapper">

	<div class="box-list">
		<div class="item">
			<div class="row ">
				<div class="text-center underline">
					<h3>Create SLA</h3>
				</div>

				<form:form method="POST" id="addForm" action="create-sla"
					modelAttribute="slaBO">
					<div class="col-sm-12">


						<div class="col-sm-4">
							<div class="form-group">
								<label> Service Level Agreement (No of Days) <span
									class="font10 text-danger">*</span></label>
								<form:input type="text" id="slaId" path="slaName"
									class="form-control required" placeholder="SLA (No of Days)"
									maxlength="120" />
								<form:errors path="slaName" class="error" />
								<div id="slaNameErr" style="color: red;"></div>
							</div>
						</div>
						<div class="col-sm-4">
							<div class="form-group">
								<label> SLA Code<span class="font10 text-danger">*</span></label>
								<form:input type="text" id="slaCodeId" path="slaCode"
									class="form-control required" placeholder="SLA Code"
									maxlength="120" onchange="slaCodeCheck()" text-align="left" />
								<form:errors path="slaCode" class="error" />
								<div id="slaCodeErr" style="color: red;"></div>
							</div>
						</div>
						<div class="col-sm-4">
							<div class="form-group">
								<label>Date<span class="font10 text-danger">*</span></label>
								<form:input type="text" id="datepicker" path="date"
									readonly="true" class="form-control required"
									placeholder="Date" maxlength="120" />
								<form:errors path="date" class="error" />
								<div id="dateErr" style="color: red;"></div>
							</div>
						</div>
					</div>
					<div class="col-sm-12">
						<div class="col-sm-8">
							<div class="form-group">
								<label> Description <span class="font10 text-danger">*</span></label>
								<form:textarea type="text" id="descriptionsId"
									path="descriptions" class="form-control required"
									placeholder="Descriptions" maxlength="1500" cols="130"
									rows="06" />
								<form:errors path="descriptions" class="error" />
								<div id="descriptionsErr" style="color: red;"></div>
							</div>
						</div>
					</div>
					<div style="text-align: right; margin-right: 31px">
						<div class="form-group">
							<form:button type="submit" id="btnsubmit"
								class="btn btn-t-primary btn-theme lebal_align mt-20 ">Submit</form:button>
							<a href="view-sla"><span
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
