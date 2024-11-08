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
		$('#dateValId').datepicker({

			changeMonth : true,
			changeYear : true,
		});
	});
</script> -->
<script>
    $(function() {
        var today = new Date(); 

        $("#dateValId").datepicker({
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

<script>
	$(document).ready(function() {
		$('#submit').click(function(e) {

			//Appointment date..
			var isValid = true;
			var date = $('#dateValId').val();
			if (date == '') {
				isValid = false;
				$("#dateValIdErr").show();
				$("#dateValIdErr").html("Please enter Appointment date");
				$("#dateValId").css({
					"border" : "1px solid red",
				});
			} else {
				$('#dateValIdErr').hide();
				$('#dateValId').css({
					"border" : "",
					"background" : ""
				});
			}
			//Remarks..
			var report = $('#reportId').val();
			if (report == '') {
				isValid = false;
				$("#reportIdErr").show();
				$("#reportIdErr").html("Please enter Remarks");
				$("#reportId").css({
					"border" : "1px solid red",
				});
			} else {
				$('#reportIdErr').hide();
				$('#reportId').css({
					"border" : "",
					"background" : ""
				});
			}

			//Description..
			var descriptions = $('#descValId').val();
			if (descriptions == '') {
				isValid = false;
				$("#descriptionsErr").show();
				$("#descriptionsErr").html("Please enter Description");
				$("#descValId").css({
					"border" : "1px solid red",
				});
			} else {
				$('#descriptionsErr').hide();
				$('#descValId').css({
					"border" : "",
					"background" : ""
				});
			}

			//Upload Files..
			var upload = $('#uploadFiles').val();
			if (upload === '') {
				$("#fileError").show();
				$("#fileError").html("Please select a file");
				$("#uploadFiles").css({
					"border" : "1px solid red",
				});
				e.preventDefault(); // Prevent the form submission
			} else {
				$('#fileError').hide();
				$('#uploadFiles').css({
					"border" : "",
					"background" : ""
				});
			}
			if (isValid == false)
				e.preventDefault();
		});
	});
</script>

<div class="contact-form-wrapper" style="margin-top: 50px;">
	<div class="box-list">
		<div class="item">
			<div class="text-center underline">
				<h3>Task Details</h3>
			</div>
			<div class="box-list">
				<div class="item">
					<div class="desc list-capitalize">
						<div class="row clearfix" style="line-height: 2em;">

							<div class="col-xs-3">
								<label style="font-weight: initial; font-weight: bold;">
									Subject</label>:
								<c:out value="${tasklist.workItemBO.workItem}"></c:out>
							</div>
							<div class="col-xs-3">
								<label style="font-weight: initial; font-weight: bold;">
									Status</label>:
								<c:out value="${ tasklist.status}"></c:out>
							</div>
							<div class="col-xs-3">
								<label style="font-weight: initial; font-weight: bold;">
									Priority</label>:
								<c:out value="${ tasklist.priority}"></c:out>
							</div>
							<div class="col-xs-3">
								<label style="font-weight: initial; font-weight: bold;">
									Related To</label>:
								<c:out value="${ tasklist.projectBo.projectName}"></c:out>
							</div>

							<div class="col-xs-3">
								<label style="font-weight: initial; font-weight: bold;">
									Lead Name</label>:
								<c:out value="${ tasklist.leadsBO.firstName}"></c:out>
							</div>
							<div class="col-xs-3">
								<label style="font-weight: initial; font-weight: bold;">
									Due Date </label>:
								<c:out value="${ tasklist.duedate}"></c:out>
							</div>
							<div class="col-xs-3">
								<label style="font-weight: initial; font-weight: bold;">
									Description </label>:
								<c:out value="${ tasklist.description}"></c:out>
							</div>
							<div class="col-xs-3">
								<label style="font-weight: initial; font-weight: bold;">
									Assigned Employee</label>:
								<c:out value="${ tasklist.adminUserBO.name}"></c:out>
							</div>

						</div>
					</div>
				</div>
			</div>
			<c:if test="${!empty taskBOList}">
				<div class="text-center underline">
					<h3>Task Tracking History</h3>
				</div>
				<div class="pi-responsive-table-sm">
					<div class="pi-section-w pi-section-white piTooltips">
						<display:table id="data" name="${taskBOList}"
							requestURI="/task-tracker-status" pagesize="10" export="false"
							class="pi-table pi-table-complex pi-table-hovered pi-round pi-table-shadow pi-table-all-borders">
							<display:column property="report" title="Remark" />
							<display:column property="descriptions" title="Descriptions" />
							<display:column title="Download File">
								<a href="taskDownload?taskTrackingId=${data.taskTrackingId}">
									<i style="text-align: center;" class="fa fa-download"></i>
								</a>
							</display:column>
							<display:column property="date" title="Appointment Date" />
						</display:table>
					</div>
				</div>
			</c:if>
			<div class="text-center underline">
				<h3>Update Task Tracking Status</h3>
			</div>
			<div class="item">
				<form:form method="POST" id="addForm" action="task-tracker-status"
					modelAttribute="tasklist" enctype="multipart/form-data">

					<div class="row">
						<div class="col-sm-4">
							<div class="form-group">

								<!-- <label>Appointment Date <span
											class="font10 text-danger"> *</span></label> <input type="text"
											class="form-control" id="dateValId"> -->
								<label>Appointment Date <span class="font10 text-danger">
										*</span></label>
								<form:input type="text" path="date" class="form-control"
									readonly="true"	placeholder="Appointment Date" id="dateValId" />
								<form:errors path="date" class="error" />
								<div id="dateValIdErr" style="color: red;"></div>
							</div>
						</div>

						<div class="pi-col-sm-4">
							<div class="form-group">
								<b><label>File Attachment<span class="font10 text-danger"> *</span></label></b>
								<div class="pi-input-with-icon">
									<input type="file" name="uploadTask" id="uploadFiles"
										style="margin-top: 8px;" onchange="fileCheck(this)" />
									<div id="fileError" class="error"
                                            style="color: red; font-weight: normal; font-size: px;"></div>


								</div>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-4">

							<label>Remark <span class="font10 text-danger"> *</span></label>

							<form:textarea path="report" name="report" placeholder="remarks"
								maxlength="2000" class="form-control rtext" id="reportId" />
							<form:errors path="report" class="error" />
							<div id="reportIdErr" style="color: red;"></div>

						</div>


						<div class="col-md-4">

							<label>Descriptions <span class="font10 text-danger">
									*</span></label>

							<form:textarea path="descriptions" name="descriptions"
								placeholder="descriptions" class="form-control rtext"
								maxlength="2000" id="descValId" />
							<form:errors path="descriptions" class="error" />
							<div id="descriptionsErr" style="color: red;"></div>

						</div>
						<form:hidden path="taskId" />
					</div>

					<div class="row">
						<div class="col-md-12">
							<div style="text-align: right; margin-right: 31px;">
								<button type="submit" id="submit"
									class="btn btn-t-primary btn-theme lebal_align mt-20">Submit</button>
								<a href="view-task"><span
									class="btn btn-t-primary btn-theme lebal_align mt-20">Cancel</span></a>
							</div>
						</div>
					</div>
				</form:form>
			</div>


		</div>
	</div>
</div>

