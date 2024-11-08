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

			//Workitem
			var isValid =true;
			var workitem = $('#workitemId').val();
			if (workitem == 'Select') {
				isValid = false;
				$("#workItemErr").show();
				$("#workItemErr").html("Please Enter Workitem");
				$("#workitemId").css({
					"border" : "1px solid red",
				});
			} else {
				$('#workItemErr').hide();
				$('#workitemId').css({
					"border" : "",
					"background" : ""
				});
			}

			//skills

			var checkedCheckboxes = $(':checkbox:checked').length;

			if (checkedCheckboxes === 0) {
				isValid = false;
				$("#skllsErr").show();
				$("#skllsErr").html("Please select at least one skills checkbox");

			}else {
				$('#skllsErr').hide();
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
					<h3>Create - Relation b/w WI & Skills</h3>
				</div>

				<form:form method="POST" id="addForm"
					action="create-manage_WI_Skills" modelAttribute="manageBO">
					<div class="col-sm-12">
						<div class="col-sm-12">
							<div class="form-group">
								<label>WorkItem<span class="font10 text-danger">*</span></label>
								<form:select type="text" path="workitemBO.workItem"
									name="workItemId" id="workitemId" class="form-control required">
									<form:option value="Select">-- Select --</form:option>
									<form:options items="${slaLists}" itemLabel="workItem"
										itemValue="workItemId" />
								</form:select>
								<form:errors path="workitemBO.workItem" class="error" />
								<div id="workItemErr" style="color: red;"></div>
							</div>
						</div>
					</div>
					<div class="col-sm-12">

						<div class="form-group">
							<label>Skills<span class="font10 text-danger">*</span></label>
							<%-- <form:select type="text" path="skillsBO.descriptions" multiple="multiple"
									id="descriptions" class="form-control required">
									<form:option value="Select">-- Select --</form:option>
									<form:options items="${slaList}" itemLabel="descriptions"
										itemValue="descriptions" />
								</form:select> --%>
							<form:checkboxes items="${slaList}" path="skillsBO.descriptions"
								itemLabel="descriptions" itemValue="skillsId" />


							<form:errors path="skillsBO.descriptions" class="error" />
							<div id="skllsErr" style="color: red;"></div>
						</div>


					</div>
					<div style="text-align: right; margin-right: 31px">
						<div class="form-group">
							<form:button type="submit" id="btnsubmit"
								class="btn btn-t-primary btn-theme lebal_align mt-20 ">Submit</form:button>
							<a href="view-manage_WI_Skills"><span
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
