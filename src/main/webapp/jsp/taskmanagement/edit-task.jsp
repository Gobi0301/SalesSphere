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




<link href="resources/css/jquery-ui-1.10.4.custom.css" rel="stylesheet">

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
						$('#btnsubmit')
								.click(
										function(e) {
											var isValid = true;
											//Subject

											var subject = $('#subjectId').val();
											if (subject == 'Select') {
												isValid = false;
												$("#subjectErr").show();
												$("#subjectErr")
														.html(
																"Please Select Subject");
												$("#subjectId").css({
													"border" : "1px solid red",
												});
											} else {
												$('#subjectErr').hide();
												$('#subjectId').css({
													"border" : "",
													"background" : ""
												});
											}

											//Status

											var status = $('#statusId').val();
											if (status == 'Select') {
												isValid = false;
												$("#statusIdErr").show();
												$("#statusIdErr").html(
														"Please Select Status");
												$("#statusId").css({
													"border" : "1px solid red",
												});
											} else {
												$('#statusIdErr').hide();
												$('#statusId').css({
													"border" : "",
													"background" : ""
												});
											}

											//Priority

											var priority = $('#priorityId')
													.val();
											if (priority == 'Select') {
												isValid = false;
												$("#priorityIdErr").show();
												$("#priorityIdErr")
														.html(
																"Please enter Priority");
												$("#priorityId").css({
													"border" : "1px solid red",
												});
											} else {
												$('#priorityIdErr').hide();
												$('#priorityId').css({
													"border" : "",
													"background" : ""
												});
											}

											//RelatedTo

											var relatedTo = $('#relatedTo')
													.val();
											if (relatedTo == '') {
												isValid = false;
												$("#relatedToErr").show();
												$("#relatedToErr")
														.html(
																"Please enter RelatedTo");
												$("#relatedTo").css({
													"border" : "1px solid red",
												});
											} else {
												$('#relatedToErr').hide();
												$('#relatedTo').css({
													"border" : "",
													"background" : ""
												});
											}

											//LeadName

											var leadName = $('#leadNameId')
													.val();
											if (leadName == '') {
												isValid = false;
												$("#leadNameErr").show();
												$("#leadNameErr")
														.html(
																"Please enter LeadName");
												$("#leadNameId").css({
													"border" : "1px solid red",
												});
											} else {
												$('#leadNameErr').hide();
												$('#leadNameId').css({
													"border" : "",
													"background" : ""
												});
											}

											//Description

											var Description = $(
													'#DescriptionId').val();
											if (Description == '') {
												isValid = false;
												$("#DescriptionErr").show();
												$("#DescriptionErr")
														.html(
																"Please enter Description");
												$("#DescriptionId").css({
													"border" : "1px solid red",
												});
											} else {
												$('#DescriptionErr').hide();
												$('#DescriptionId').css({
													"border" : "",
													"background" : ""
												});
											}

											//AssignedEmployee..

											var AssignedEmployee = $(
													'#AssignedEmployeeId')
													.val();
											if (AssignedEmployee == '') {
												isValid = false;
												$("#AssignedEmployeeErr")
														.show();
												$("#AssignedEmployeeErr")
														.html(
																"Please enter AssignedEmployee");
												$("#AssignedEmployeeId").css({
													"border" : "1px solid red",
												});
											} else {
												$('#AssignedEmployeeErr')
														.hide();
												$('#AssignedEmployeeId').css({
													"border" : "",
													"background" : ""
												});
											}

											// closed Date

											var duedate = $('#date').val();
											if (duedate == '') {
												isValid = false;
												$("#dateErr").show();
												$("#dateErr")
														.html(
																"Please enter closed Date");
												$("#date").css({
													"border" : "1px solid red",
												});
											} else {
												$('#dateErr').hide();
												$('#date').css({
													"border" : "",
													"background" : ""
												});
											}

											if (isValid == false) {
												e.preventDefault();
											}
										});
					});
</script>

<script>
	$(function() {
		var today = new Date();
		$("#date").datepicker({
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
					<h3>Edit TaskManagement</h3>
				</div>

				<form:form method="POST" id="addForm" action="edit-task"
					modelAttribute="taskBO">
					<div class="col-sm-12">
						<form:hidden path="taskId" name="id" value="${taskBO.taskId}" />

						<div class="col-sm-4">
							<%-- <div class="form-group">
								<label>Subject<span class="font10 text-danger">*</span></label>
								<form:select type="text" path="subject" id="subjectId"
									class="form-control required">
									<form:option value="Select">--Select--</form:option>
									<form:option value="Cold Call and Appointment">Cold Call and Appointment</form:option>
									<form:option value="Project Details Explanation">Project Details Explanation</form:option>
									<form:option value="Site Visit Slot Booked">Site Visit Slot Booked</form:option>
									<form:option value="Site Visit Completed">Site Visit Completed</form:option>
									<form:option value="Quote Proposal">Quote Proposal</form:option>
									<form:option value="Proposal Negotiation">Proposal Negotiation</form:option>
									<form:option value="Payment Initiation and Closure">Payment Initiation and Closure</form:option>
								</form:select>
								<form:errors path="subject" class="error" />
								<div id="subjectErr" style="color: red;"></div>
							</div> --%>
							<div class="form-group">
								<label>WorkItems<span class="font10 text-danger">*</span></label>
								<form:select type="text" path="workItemBO.workItem"
									id="adminUserBOId" class="form-control required">
									<form:option value="${taskBO.workItemBO.workItemId}">${taskBO.workItemBO.workItem}</form:option>
									<form:options items="${slaList}" itemLabel="workItem"
										itemValue="workItemId" />
								</form:select>
								<form:errors path="workItemBO.workItem" class="error" />
								<div id="adminUserBOErr" style="color: red;"></div>
							</div>
						</div>
						<div class="col-sm-4">
							<div class="form-group">
								<label>Status<span class="font10 text-danger">*</span></label>
								<form:select type="text" path="status" id="statusId"
									class="form-control required">
									<form:option value="Select">--Select Status--</form:option>
									<form:option value="Open">Open</form:option>
									<form:option value="Assigned">Assigned</form:option>
									<form:option value="Contact Attempted">Contact Attempted</form:option>
									<form:option value="Working">Working</form:option>
									<form:option value="Proposal">Proposal</form:option>
									<form:option value="Completed">Completed</form:option>
									<form:option value="Lost">Lost</form:option>
								</form:select>
								<form:errors path="status" class="error" />
								<div id="statusIdErr" style="color: red;"></div>
							</div>
						</div>
						<div class="col-sm-4">
							<div class="form-group">
								<label>Priority<span class="font10 text-danger">*</span></label>
								<form:select type="text" path="priority" id="priorityId"
									class="form-control required">
									<form:option value="Select">--Select Priority--</form:option>
									<form:option value="High">High</form:option>
									<form:option value="Highest">Highest</form:option>
									<form:option value="Low">Low</form:option>
									<form:option value="Lowest">Lowest</form:option>
									<form:option value="Normal">Normal</form:option>
								</form:select>
								<form:errors path="priority" class="error" />
								<div id="priorityIdErr" style="color: red;"></div>
							</div>
						</div>
					</div>
					<div class="col-sm-12">
						<div class="col-sm-4">
							<div class="form-group">
								<label>Project<span class="font10 text-danger">*</span></label>
								<form:select path="projectBo.projectName" id="projectId"
									class="form-control required">
									<form:option value="${taskBO.projectBo.projectId}">${taskBO.projectBo.projectName}</form:option>
									<form:options items="${projectBOList}" itemLabel="projectName"
										itemValue="projectId" />
								</form:select>
								<form:errors path="projectBo.projectName" class="error" />
								<div id="productErr" style="color: red;"></div>
							</div>
						</div>
						<div class="col-sm-4">
							<div class="form-group">
								<label>Lead Name <span class="font10 text-danger">*</span></label>
								<%-- <form:input type="text" id="leadNameId" path="leadName"
									class="form-control required" placeholder="Name"
									maxlength="150" /> --%>

								<form:select path="leadsBO.firstName" id="leadsId"
									class="form-control required">
									<form:option value="${taskBO.leadsBO.leadsId}">${taskBO.leadsBO.firstName}</form:option>
									<form:options items="${leadList}" itemLabel="firstName"
										itemValue="leadsId" />
								</form:select>
								<form:errors path="leadsBO.firstName" class="error" />
								<div id="leadNameErr" style="color: red;"></div>
							</div>

						</div>
						<div class="col-sm-4">
							<div class="form-group">
								<label>Assign Employee<span class="font10 text-danger">*</span></label>
								<form:select type="text" path="adminUserBO.name"
									id="procurementId" class="form-control required">

									<form:option value="${taskBO.adminUserBO.adminId}">${taskBO.adminUserBO.name}</form:option>
									<form:option value="Select">-- Select --</form:option>
									<form:options items="${userBOList}" itemLabel="name"
										itemValue="id" />
								</form:select>
								<form:errors path="adminUserBO.name" class="error" />
								<div id="serviceNameErr" style="color: red;"></div>

							</div>

						</div>
					</div>
					<div class="col-sm-12">
						<div class="col-sm-4">
							<div class="form-group">
								<label> Description <span class="font10 text-danger">*</span></label>
								<form:input type="text" id="DescriptionId" path="Description"
									class="form-control required" placeholder="Description"
									maxlength="2000" />
								<form:errors path="Description" class="error" />
								<div id="DescriptionErr" style="color: red;"></div>
							</div>
						</div>
						<div class="col-sm-4">
							<div class="form-group">
								<label>Due Date <span class="font10 text-danger">*</span></label>
								<form:input type="text" name="Due Date" path="duedate" id="date"
									readonly="true"		class="form-control required" placeholder="Due Date" />
								<form:errors path="duedate" class="error" />
								<div id="dateErr" style="color: red;"></div>
							</div>
						</div>

					</div>
					<div style="text-align: right; margin-right: 31px">
						<div class="form-group">
							<form:button type="submit" id="btnsubmit"
								class="btn btn-t-primary btn-theme lebal_align mt-20 ">Submit</form:button>
							<a href="view-task?page=1"><span
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
