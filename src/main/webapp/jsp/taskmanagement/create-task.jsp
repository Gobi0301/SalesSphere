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

		$('#procurementId').focus();

		$('#btnsubmit').click(function(e) {

			//Subject
			var isValid = true;
			var workItem = $('#adminUserBOId').val();
			if (workItem == 'Select') {
				isValid = false;
				$("#adminUserBOErr").show();
				$("#adminUserBOErr").html("Please Select WorkItem");
				$("#adminUserBOId").css({
					"border" : "1px solid red",
				});
			} else {
				$('#adminUserBOErr').hide();
				$('#adminUserBOId').css({
					"border" : "",
					"background" : ""
				});
			}

			//Status
			var status = $('#statusId').val();
			if (status == 'Select') {
				isValid = false;
				$("#statusIdErr").show();
				$("#statusIdErr").html("Please Select Status");
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
			var priority = $('#priorityId').val();
			if (priority == 'Select') {
				isValid = false;
				$("#priorityIdErr").show();
				$("#priorityIdErr").html("Please select Priority");
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

			//Project
			var projectName = $('#projectId').val();
			if (projectName == 'Select') {
				isValid = false;
				$("#projectErr").show();
				$("#projectErr").html("Please Select Project");
				$("#projectId").css({
					"border" : "1px solid red",
				});
			} else {
				$('#projectErr').hide();
				$('#projectId').css({
					"border" : "",
					"background" : ""
				});
			}

			//LeadName
			var leadName = $('#leadNameId').val();
			if (leadName == 'Select') {
				isValid = false;
				$("#leadNameErr").show();
				$("#leadNameErr").html("Please Select LeadName");
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
			var Description = $('#DescriptionId').val();
			if (Description == '') {
				isValid = false;
				$("#DescriptionErr").show();
				$("#DescriptionErr").html("Please enter Description");
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
			var name = $('#assignemployeeId').val();
			if (name == 'Select') {
				isValid = false;
				$("#AssignedEmployeeErr").show();
				$("#AssignedEmployeeErr").html("Please Select The Employee");
				$("#assignemployeeId").css({
					"border" : "1px solid red",
				});
			} else {
				$('#AssignedEmployeeErr').hide();
				$('#assignemployeeId').css({
					"border" : "",
					"background" : ""
				});
			}

			//Date
			var duedate = $('#date').val();
			if (duedate == '') {
				isValid = false;
				$("#dateErr").show();
				$("#dateErr").html("Please Choose the Date");
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

			if (!isValid){
				e.preventDefault();
			}});
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

<link href="resources/css/jquery-ui-1.10.4.custom.css" rel="stylesheet">


<div class="contact-form-wrapper">

	<div class="box-list">
		<div class="item">
			<div class="row ">
				<div class="text-center underline">
					<h3>Create TaskManagement</h3>
				</div>

				<form:form method="POST" id="addForm" action="create-task"
					modelAttribute="taskBO">
					<div class="col-sm-12">


						<div class="col-sm-4">
							<div class="form-group">
								<label>WorkItems<span class="font10 text-danger">*</span></label>
								<form:select type="text" path="workItemBO.workItem"
									id="adminUserBOId" class="form-control required">
									<form:option value="Select">-- Select --</form:option>
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
								<form:select type="text" path="projectBo.projectName"
									id="projectId" class="form-control required">
									<form:option value="Select">-- Select --   </form:option>
									<form:options items="${projectBOList}" itemLabel="projectName"
										itemValue="projectId" />
								</form:select>
								<form:errors path="projectBo.projectName" class="error" />
								<div id="projectErr" style="color: red;"></div>
							</div>
						</div>

						<div class="col-sm-4">
							<div class="form-group">
								<label>Lead Name<span class="font10 text-danger">*</span></label>
								<form:select type="text" path="leadsBO.firstName"
									id="leadNameId" class="form-control required">
									<form:option value="Select">-- Select --   </form:option>
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
									id="assignemployeeId" class="form-control required">
									<form:option value="Select">-- Select --</form:option>
									<form:options items="${userBOList}" itemLabel="name"
										itemValue="id" />
								</form:select>
								<form:errors path="adminUserBO.name" class="error" />
								<div id="AssignedEmployeeErr" style="color: red;"></div>
							</div>


						</div>
					</div>

					<div class="col-sm-12">
						<div class="col-sm-4">
							<div class="form-group">
								<label>Due Date <span class="font10 text-danger">*</span></label>
								<input type="text" name="Due Date" path="duedate" id="date"
									class="form-control required" placeholder="Due Date"
									maxlength="150" readonly/>
								<form:errors path="duedate" class="error" />
								<div id="dateErr" style="color: red;"></div>
							</div>
						</div>
						<div class="col-sm-8">
							<div class="form-group">
								<label> Description <span class="font10 text-danger">*</span></label>
								<form:textarea type="text" id="DescriptionId" path="Description"
									class="form-control required" placeholder="Description"
									maxlength="2000" />
								<form:errors path="Description" class="error" />
								<div id="DescriptionErr" style="color: red;"></div>
							</div>
						</div>
					</div>
					<div style="text-align: right; margin-right: 31px">

						<button type="submit" id="btnsubmit"
							class="btn btn-t-primary btn-theme lebal_align mt-20 ">Submit</button>
						<a href="view-task"><span
							class="btn btn-t-primary btn-theme lebal_align mt-20">Cancel</span></a>

					</div>
				</form:form>
			</div>
		</div>
	</div>
</div>
<br>
<br>