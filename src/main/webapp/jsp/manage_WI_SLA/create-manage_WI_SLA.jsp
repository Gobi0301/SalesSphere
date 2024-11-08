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
 	
	<script type="text/javascript">
	function wislaCodeCheck() {
		var slaCode = document.getElementById("slaCodeId").value;
		 document.getElementById("btnsubmit").disabled = false;
		if (slaCode != '') {
			$.ajax({
				url : "check_workItemslaCode",
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

<script>
	$(document).ready(function() {

		$('#btnsubmit').click(function(e) {

			//SLA Code
			var isValid = true;
			var slaCode = $('#slaCodeId').val();
			if (slaCode == '') {
				isValid = false;
				$("#slaCodeErr").show();
				$("#slaCodeErr").html("Please Enter WI SLA Code");
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

			//workitem name
			var workItem = $('#workItemId').val();
			if (workItem == 'Select') {
				isValid = false;
				$("#workItemIdErr").show();
				$("#workItemIdErr").html("Please Select Workitem name");
				$("#workItemId").css({
					"border" : "1px solid red",

				});

			} else {
				$('#workItemIdErr').hide();
				$('#workItemId').css({

					"border" : "",
					"background" : ""
				});
			}

			//sla name
			var slaname = $('#slaId').val();
			if (slaname == 'Select') {
				isValid = false;
				$("#slaNameErr").show();
				$("#slaNameErr").html("Please Select SLA Name");
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

			if (!isValid){
				e.preventDefault();
			}});
	});
</script>

<link href="resources/css/jquery-ui-1.10.4.custom.css" rel="stylesheet">


<div class="contact-form-wrapper">

	<div class="box-list">
		<div class="item">
			<div class="row ">
				<div class="text-center underline">
					<h3>Create-Manage WI & SLA</h3>
				</div>

				<form:form method="POST" id="addForm" action="create-manage_WI_SLA"
					modelAttribute="manageBO">
					<div class="col-sm-12">
						<div class="col-sm-4">
							<div class="form-group">
								<label>WorkItems Name<span class="font10 text-danger">*</span></label>
								<form:select type="text" path="workItemBO.workItem"
									name="workItemId" id="workItemId" class="form-control required">
									<form:option value="Select">-- Select --</form:option>
									<form:options items="${slaLists}" itemLabel="workItem"
										itemValue="workItemId" />
								</form:select>
								<form:errors path="workItemBO.workItem" class="error" />
								<div id="workItemIdErr" style="color: red;"></div>
							</div>
						</div>
						<div class="col-sm-4">
							<div class="form-group">
								<label>SLA (No of Days)<span class="font10 text-danger">*</span></label>
								<form:select type="text" path="slaBO.slaName" id="slaId"
									class="form-control required">
									<form:option value="Select">-- Select --</form:option>
									<form:options items="${slaList}" itemLabel="slaName"
										itemValue="slaId" />
								</form:select>
								<form:errors path="slaBO.slaName" class="error" />
								<div id="slaNameErr" style="color: red;"></div>
							</div>
						</div>
						<div class="col-sm-4">
							<div class="form-group">
								<label> WI SLA Code <span class="font10 text-danger">*</span></label>
								<form:input type="text" id="slaCodeId" path="slaCode"
									class="form-control required" placeholder="WI_SLA Code"
									maxlength="120" onchange="wislaCodeCheck()"/>
								<form:errors path="slaCode" class="error" />
								<div id="slaCodeErr" style="color: red;"></div>
							</div>
						</div>
					</div>
			<div style="text-align: right; margin-right: 31px">
				<div class="form-group">
					<form:button type="submit" id="btnsubmit"
						class="btn btn-t-primary btn-theme lebal_align mt-20 ">Submit</form:button>
					<a href="view-manage_WI_SLA"><span
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
