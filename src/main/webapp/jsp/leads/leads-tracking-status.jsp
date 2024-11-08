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
	bkLib.onDomLoaded(function() {
		new nicEditor({
			buttonList : [ 'fontSize', 'bold', 'italic', 'underline', 'ol',
					'ul', 'strikeThrough', 'html' ]
		}).panelInstance('inputAddress');
	});
</script>

<script>
 
        // Below code sets format to the
        // datetimepicker having id as
        // datetime
        $('#datetime').datetimepicker({
            format: 'hh:mm:ss a'
        });
    </script>

<script>
	$(document).ready(function() {
		$('#dateValId').datepicker({

			changeMonth : true,
			changeYear : true,
		});
	});
</script>
<script>
	$(document).ready(function() {
		$('#nextdateValId').datepicker({

			changeMonth : true,
			changeYear : true,
		});
	});
</script>
<script>
		$(function() {
			$("#dateValId").datepicker({
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
<script>
	$(document).ready(function() {
		$('#btnsubmit').click(function(e) {
			var isValid = true;
			$('input[type="text"].required').each(function() {
				if ($.trim($(this).val()) == '') {
					isValid = false;
					$(this).css({
						"border" : "1px solid red",
					});
				} else {
					$(this).css({
						"border" : "",
						"background" : ""
					});
				}

				if (isValid == false)
					e.preventDefault();

			});

			});
	});

	$(document).ready(function() {
		$('#btnsubmit').click(function(e) {
			var isValid1 = true;
			$('input[id="password"].required').each(function() {
				if ($.trim($(this).val()) == '') {
					isValid1 = false;
					$(this).css({
						"border" : "1px solid red",
					});
				} else {
					$(this).css({
						"border" : "",
						"background" : ""
					});
				}
			});
			if (isValid1 == false)
				e.preventDefault();

		});
	});
</script>

<script>
		$(document).ready(function() {
			$('#btnsubmit').click(function(e) {

				//action date
				
				//var isValid = true;
				var date = $('#dateValId').val();
				if (date =='') {
					isValid = false;
					$("#dateValIdErr").show();
					$("#dateValIdErr").html("Please Select Action Date");
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


				//next appointment
				//var isValid = true;
				var nextdate = $('#nextdateValId').val();
				if (nextdate =='') {
					isValid = false;
					$("#nextdateValIdErr").show();
					$("#nextdateValIdErr").html("Please Select Next Appointment Date");
					$("#nextdateValId").css({
						"border" : "1px solid red",

					});

				} else {
					$('#nextdateValIdErr').hide();
					$('#nextdateValId').css({

						"border" : "",
						"background" : ""
					});
				}

				var fromslot = $('#statusId').val();
				if (fromslot == 'Select') {
					isValid = false;
					$("#timeSlotErr").show();
					$("#timeSlotErr").html("Please Select Fromslot");
					$("#statusId").css({
						"border" : "1px solid red",
					});
				} else {
					$('#timeSlotErr').hide();
					$('#statusId').css({
						"border" : "",
						"background" : ""
					});
				}

				var toslot = $('#endTime').val();
				if (toslot == 'Select') {
					isValid = false;
					$("#endTimeSlotErr").show();
					$("#endTimeSlotErr").html("Please Select toslot");
					$("#endTime").css({
						"border" : "1px solid red",
					});
				} else {
					$('#endTimeSlotErr').hide();
					$('#endTime').css({
						"border" : "",
						"background" : ""
					});
				}

				
				var description = $('#descriptionId').val();
				if( description ==''){
					isValid = false;
					$("#descValIdErr").show();
					$("#descValIdErr").html("Please Enter Description");
					$("#descriptionId").css({
						"border" : "1px solid red",
						});
					}else{
						$('#descValIdErr').hide();
						$('#descriptionId').css({
							"border" : "",
							"background" : ""
							});
						} 
					

				if (isValid == false)
					e.preventDefault();
			});
		});
		
	</script>

<script>
    document.addEventListener("DOMContentLoaded", function() {
        var fromSlotSelect = document.getElementById("statusId");
        var toSlotSelect = document.getElementById("endTime");

        fromSlotSelect.addEventListener("change", function() {
            var selectedFromSlot = fromSlotSelect.value;
            var selectedFromSlotTime = parseTime(selectedFromSlot);

            // Clear previous options
            toSlotSelect.innerHTML = "";

            // Limit end time to 6:00 PM
            var limitTime = { hours: 18, minutes: 0 }; // 18 represents 6:00 PM in 24-hour format

            // Generate options for "To Slot" starting from 30 minutes after "From Slot"
            while (!timeIsAfter(selectedFromSlotTime, limitTime)) {
                var nextSlotTime = add30Minutes(selectedFromSlotTime);
                var nextSlotTimeString = formatTime(nextSlotTime);

                var option = document.createElement("option");
                option.text = nextSlotTimeString;
                option.value = nextSlotTimeString;
                toSlotSelect.add(option);

                // Increment by 30 minutes for the next iteration
                selectedFromSlotTime = nextSlotTime;
            }
        });

        function parseTime(timeString) {
            var parts = timeString.split(/:| /); // Split by colon or space to handle AM/PM
            var hours = parseInt(parts[0]);
            var minutes = parseInt(parts[1]);
            // Convert PM hours to 24-hour format
            if (parts[2] === 'PM' && hours !== 12) {
                hours += 12;
            }
            return {
                hours: hours,
                minutes: minutes
            };
        }

        function add30Minutes(time) {
            var result = {
                hours: time.hours,
                minutes: time.minutes + 30
            };

            if (result.minutes >= 60) {
                result.hours++;
                result.minutes -= 60;
            }

            return result;
        }

        function formatTime(time) {
            var hours = time.hours % 12 || 12; // Convert to 12-hour format
            var period = time.hours < 12 ? "AM" : "PM"; // Determine AM or PM
            var minutes = time.minutes < 10 ? "0" + time.minutes : time.minutes;
            return hours + ":" + minutes + " " + period;
        }

        function timeIsAfter(time1, time2) {
            if (time1.hours > time2.hours) {
                return true;
            } else if (time1.hours === time2.hours && time1.minutes > time2.minutes) {
                return true;
            }
            return false;
        }
    });
</script>

<!-- <div class="warning"> -->

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
<!-- </div> -->

<div class="contact-form-wrapper" style="margin-top: 50px;">
	<div class="box-list">
		<div class="item">
			<div class="text-center underline">
				<h3>Leads Details</h3>
			</div>
			<div class="item">

				<div class="desc list-capitalize">
					<div class="row clearfix" style="line-height: 2em;">

						<div class="col-xs-4">
							<label style="font-weight: initial; font-weight: bold;">
								Salutation:</label>
							<c:out value="${viewleads.salutation}"></c:out>
						</div>

						<div class="col-xs-4">
							<label style="font-weight: initial; font-weight: bold;">
								Name:</label>
							<c:out value="${viewleads.firstName}"></c:out>
						</div>

						<div class="col-xs-4">
							<label style="font-weight: initial; font-weight: bold;">
								Last Name:</label>
							<c:out value="${viewleads.lastName}"></c:out>
						</div>

						<div class="col-xs-4">
							<label style="font-weight: initial; font-weight: bold;">Email:</label>
							<c:out value="${viewleads.emailAddress}"></c:out>
						</div>

						<div class="col-xs-4">
							<label style="font-weight: initial; font-weight: bold;">Contact:</label>
							<c:out value="${viewleads.contactNo}"></c:out>
						</div>


						<div class="col-xs-4">
							<label style="font-weight: initial; font-weight: bold;">Mobile:</label>
							<c:out value="${viewleads.mobileNo}"></c:out>
						</div>

						<div class="col-xs-4">
							<label style="font-weight: initial; font-weight: bold;">Company:</label>
							<c:out value="${viewleads.companyName}"></c:out>
						</div>

						<div class="col-xs-4">
							<label style="font-weight: initial; font-weight: bold;">Industry:</label>
							<c:out value="${viewleads.industryType}"></c:out>
						</div>

						<div class="col-xs-4">
							<label style="font-weight: initial; font-weight: bold;">Status:</label>
							<c:out value="${viewleads.status}"></c:out>
						</div>

						<div class="col-xs-4">
							<label style="font-weight: initial; font-weight: bold;">AnnualRevenue:</label>
							<c:out value="${viewleads.annualRevenue}"></c:out>
						</div>

						<div class="col-xs-4">
							<label style="font-weight: initial; font-weight: bold;">Designation:</label>
							<c:out value="${viewleads.designation}"></c:out>
						</div>

						<div class="col-xs-4">
							<label style="font-weight: initial; font-weight: bold;">Description:</label>
							<c:out value="${viewleads.description}"></c:out>
						</div>

						<div class="col-xs-4">
							<label style="font-weight: initial; font-weight: bold;">Assign
								Employees:</label>
							<c:out value="${viewleads.adminLoginBO.name}"></c:out>
						</div>

						<div class="col-xs-4">
							<label style="font-weight: initial; font-weight: bold;">Lead
								Source:</label>
							<c:out value="${viewleads.campaignBO.campaignName}"></c:out>
						</div>

						<div class="col-xs-4">
							<label style="font-weight: initial; font-weight: bold;">Rating:</label>
							<c:out value="${viewleads.rating}"></c:out>
						</div>

						<div class="col-xs-4">
							<label style="font-weight: initial; font-weight: bold;">Fax:</label>
							<c:out value="${viewleads.fax}"></c:out>
						</div>

						<div class="col-xs-4">
							<label style="font-weight: initial; font-weight: bold;">Street:</label>
							<c:out value="${viewleads.street}"></c:out>
						</div>

						<div class="col-xs-4">
							<label style="font-weight: initial; font-weight: bold;">City:</label>
							<c:out value="${viewleads.city}"></c:out>
						</div>

						<div class="col-xs-4">
							<label style="font-weight: initial; font-weight: bold;">District:</label>
							<c:out value="${viewleads.district}"></c:out>
						</div>

						<div class="col-xs-4">
							<label style="font-weight: initial; font-weight: bold;">State:</label>
							<c:out value="${viewleads.state}"></c:out>
						</div>

						<div class="col-xs-4">
							<label style="font-weight: initial; font-weight: bold;">Country:</label>
							<c:out value="${viewleads.country}"></c:out>
						</div>

						<div class="col-xs-4">
							<label style="font-weight: initial; font-weight: bold;">PostalCode:</label>
							<c:out value="${viewleads.postalCode}"></c:out>
						</div>

						<div class="col-xs-4">
							<label style="font-weight: initial; font-weight: bold;">Website:</label>
							<c:out value="${viewleads.website}"></c:out>
						</div>

					</div>
					<%-- <div class="row clearfix" style="line-height: 2em;">
						<div class="col-xs-12">
						<label style="font-weight: initial; font-weight: bold;">Address:</label>
						<c:out value="${viewleads.address}"></c:out>
					</div>
					</div> --%>
				</div>

			</div>

			<c:if test="${!empty taskManagementList}">
				<div class="text-center underline">
					<h3>TaskManagement Lead</h3>
				</div>
				<div class="pi-responsive-table-sm">
					<div class="pi-section-w pi-section-white piTooltips">
						<display:table id="data" name="${taskManagementList}"
							requestURI="/leads-tracking-status" pagesize="10" export="false"
							class="pi-table pi-table-complex pi-table-hovered pi-round pi-table-shadow pi-table-all-borders">
							<display:column property="sNo" title="S.No" />
							<display:column property="workItemBO.workItem" title="Workitems" />
							<%-- <display:column property="subject" title="Subject" /> --%>
							<display:column property="status" title="Status" />
							<display:column property="priority" title="Priority" />
							<display:column property="adminUserBO.name"
								title="AssignedEmployee" />
							<%-- <display:column property="relatedTo" title="RelatedTo" /> --%>
							<display:column property="leadName" title="EntityType" />

							<%-- <display:column url="task-tracker-status" media="html"
								paramId="id" paramProperty="taskId" title="Click Here">
								<a href="task-tracker-status?taskId=${data.taskId}"> <i
									style="text-align: center; color: blue;"
									class="fa fa-external-link"></i>
								</a>
							</display:column> --%>
							<!-- Edit Column -->
							<%-- <display:column title="Edit">
                    <a href="#" data-toggle="modal" data-target="#editModal" data-taskid="${data.taskId}">
                        <i style="text-align: center; color: orange;" class="fa fa-edit"></i>
                    </a>
                </display:column> --%>

							<display:column url="edit-task" media="html" paramId="id"
								paramProperty="taskId" title="Edit">
								<a href="edit-taskleads?taskId=${data.taskId}"> <i
									style="text-align: center; color: orange;" class="fa fa-edit"></i>
								</a>
							</display:column>

							<%-- <display:column url="task-tracker-status" media="html"
								paramId="id" paramProperty="taskId" title="View">
								<a href="task-tracker-status?taskId=${data.taskId}"> <i
									style="text-align: center; color: black;" class="fa fa-eye"></i>
								</a>
							</display:column> --%>

							<display:column url="delete-taskleads" media="html" paramId="id"
								paramProperty="taskId" title="Delete">
								<a
									href="delete-taskleads?taskId=${data.taskId}&entityId=${data.entityId}"
									onclick="return confirm('Are you sure you want to Delete?')">
									<i style="text-align: center;" class="fa fa-trash"></i>
								</a>
							</display:column>


						</display:table>
					</div>
				</div>
			</c:if>




			<%-- <c:if test="${!empty taskManagementList}">
				<div class="text-center underline">
					<h3>TaskManagement Lead</h3>
				</div>
				<div class="pi-responsive-table-sm">
					<div class="pi-section-w pi-section-white piTooltips">
						<display:table id="data" name="${taskManagementList}"
							requestURI="/leads-tracking-status" pagesize="10" export="false"
							class="pi-table pi-table-complex pi-table-hovered pi-round pi-table-shadow pi-table-all-borders">
							<display:column property="subject" title="Subject" />
							<display:column property="status" title="Status" />
							<display:column property="priority" title="Priority" />
							<display:column property="adminUserBO.name"
								title="AssignedEmployee" />
							<display:column property="relatedTo" title="RelatedTo" />
							<display:column property="leadName" title="LeadName" />
							<display:column url="task-tracker-status" media="html"
								paramId="id" paramProperty="taskId" title="Click Here">
								<a href="task-tracker-status?taskId=${data.taskId}"> <i
									style="text-align: center; color: blue;"
									class="fa fa-external-link"></i>
								</a>
							</display:column>
						</display:table>
					</div>
				</div>
			</c:if>
 --%>


			<c:if test="${!empty leadsBOList}">
				<div class="text-center underline">
					<h3>Leads Tracking History</h3>
				</div>
				<div class="pi-responsive-table-sm">
					<div class="pi-section-w pi-section-white piTooltips">
						<display:table id="data" name="${leadsBOList}"
							requestURI="/leads-tracking-status" pagesize="10" export="false"
							class="pi-table pi-table-complex pi-table-hovered pi-round pi-table-shadow pi-table-all-borders">
							<display:column property="date" title="Action Date" />
							<display:column property="description" title="Description" />
							<display:column property="nextAppointmentDate"
								title="Next Appointment Date" />
						</display:table>
					</div>
				</div>
			</c:if>

			<%-- 			<div class="text-center underline">
				<h3>Update Leads Tracking Status</h3>
			</div>
			<div class="item">
				<form:form method="POST" id="addForm" action="leads-tracking-status"
					modelAttribute="viewleads">
					<div class="row">
						<!-- <div class="col-md-12"> -->
						<div class="col-sm-4">
							<div class="form-group">

								<!-- <label>Appointment Date <span
											class="font10 text-danger"> *</span></label> <input type="text"
											class="form-control" id="dateValId"> -->
								<label>Action Date <span class="font10 text-danger">
										*</span></label>
								<form:input type="text" path="date" class="form-control"
									id="dateValId" placeholder="Action Date" />
								<form:errors path="date" class="error" />
								<div id="dateValIdErr" style="color: red;"></div>
							</div>
						</div>


						<div class="col-sm-4">
							<div class="form-group">

								<label>Next Appointment Date <span
									class="font10 text-danger"> *</span></label>
								<form:input type="text" path="nextAppointmentDate"
									class="form-control" id="nextdateValId"
									placeholder="Next Appoitment Date" />
								<form:errors path="nextAppointmentDate" class="error" />
								<div id="nextdateValIdErr" style="color: red;"></div>
							</div>
						</div>
						<div class="col-sm-4">
							<div class="form-group">
								<label>From Slot <span class="font10 text-danger">
										*</span></label>
								<form:input type="time" path="timeSlot" class="form-control"/>
									<form:errors path="timeSlot" class="error" />
									<div id="timeSlotErr" style="color: red;"></div>

								<form:select type="text" path="timeSlot" id="statusId"
									class="form-control required">
									<form:option value="Select">--Select--</form:option>
									<form:option value="6:00 AM">6:00 AM</form:option>
									<form:option value="6:30 AM">6:30 AM</form:option>
									<form:option value="7:00 AM">7:00 AM</form:option>
									<form:option value="7:30 AM">7:30 AM</form:option>
									<form:option value="8:00 AM">8:00 AM</form:option>
									<form:option value="8:30 AM">8:30 AM</form:option>
									<form:option value="9:00 AM">9:00 AM</form:option>
									<form:option value="9:30 AM">9:30 AM</form:option>
									<form:option value="10:00 AM">10:00 AM</form:option>
									<form:option value="10:30 AM">10:30 AM</form:option>
									<form:option value="11:00 AM">11:00 AM</form:option>
									<form:option value="11:30 AM">11:30 AM</form:option>
									<form:option value="12:00 PM">12:00 PM</form:option>
									<form:option value="12:30 PM">12:30 PM</form:option>
									<form:option value="1:00 PM">1:00 PM</form:option>
									<form:option value="1:30 PM">1:30 PM</form:option>
									<form:option value="2:00 PM">2:00 PM</form:option>
									<form:option value="2:30 PM">2:30 PM</form:option>
									<form:option value="3:00 PM">3:00 PM</form:option>
									<form:option value="3:30 PM">3:30 PM</form:option>
									<form:option value="4:00 PM">4:00 PM</form:option>
									<form:option value="4:30 PM">4:30 PM</form:option>
									<form:option value="5:00 PM">5:00 PM</form:option>
									<form:option value="5:30 PM">5:30 PM</form:option>
									<form:option value="6:00 PM">6:00 PM</form:option>
								</form:select>
								<form:errors path="timeSlot" class="error" />
								<div id="timeSlotErr" style="color: red;"></div>

							</div>
						</div>

						<!-- </div> -->
					</div>
					<form:hidden path="leadsId" />
					<div class="row">
						<div class="col-sm-4">
							<div class="form-group">
								<label>To Slot <span class="font10 text-danger">
										*</span></label>
								<form:input type="time" path="endTimeSlot" class="form-control"/>
									<form:errors path="endTimeSlot" class="error" />
									<div id="endTimeSlotErr" style="color: red;"></div>

								<form:select type="text" path="endTimeSlot" id="endTime"
									class="form-control required">
									<form:option value="Select">--Select--</form:option>
									<form:option value="6:00 AM">6:00 AM</form:option>
									<form:option value="6:30 AM">6:30 AM</form:option>
									<form:option value="7:00 AM">7:00 AM</form:option>
									<form:option value="7:30 AM">7:30 AM</form:option>
									<form:option value="8:00 AM">8:00 AM</form:option>
									<form:option value="8:30 AM">8:30 AM</form:option>
									<form:option value="9:00 AM">9:00 AM</form:option>
									<form:option value="9:30 AM">9:30 AM</form:option>
									<form:option value="10:00 AM">10:00 AM</form:option>
									<form:option value="10:30 AM">10:30 AM</form:option>
									<form:option value="11:00 AM">11:00 AM</form:option>
									<form:option value="11:30 AM">11:30 AM</form:option>
									<form:option value="12:00 PM">12:00 PM</form:option>
									<form:option value="12:30 PM">12:30 PM</form:option>
									<form:option value="1:00 PM">1:00 PM</form:option>
									<form:option value="1:30 PM">1:30 PM</form:option>
									<form:option value="2:00 PM">2:00 PM</form:option>
									<form:option value="2:30 PM">2:30 PM</form:option>
									<form:option value="3:00 PM">3:00 PM</form:option>
									<form:option value="3:30 PM">3:30 PM</form:option>
									<form:option value="4:00 PM">4:00 PM</form:option>
									<form:option value="4:30 PM">4:30 PM</form:option>
									<form:option value="5:00 PM">5:00 PM</form:option>
									<form:option value="5:30 PM">5:30 PM</form:option>
									<form:option value="6:00 PM">6:00 PM</form:option>
								</form:select>
								<form:errors path="endTimeSlot" class="error" />
								<div id="endTimeSlotErr" style="color: red;"></div>
							</div>
						</div>
						<div class="col-md-4">
							<label>Descriptions <span class="font10 text-danger">
									*</span></label>

							<form:textarea path="description" name="description"
								placeholder="Description" class="form-control rtext"
								id="descValId" />
							<label>Description<span class="font10 text-danger">*</span></label>
							<label class="hidden-xs"></label>
							<form:textarea path="description" id="descriptionId"
								class="form-control required" placeholder="Description"
								maxlength="2000" />
							<form:errors path="description" class="error" />
							<div id="descValIdErr" style="color: red;"></div>

						</div>
					</div>
					</br>
					<div class="row">
						<div class="col-md-12">
							<div style="text-align: right; margin-right: 31px;">
								<button type="submit" id="btnsubmit"
									class="btn btn-t-primary btn-theme lebal_align mt-20">Submit</button>
								<a href="view-leads"><span
									class="btn btn-t-primary btn-theme lebal_align mt-20">Cancel</span></a>
							</div>
						</div>
					</div>
				</form:form>
			</div> --%>
		</div>
	</div>
</div>


