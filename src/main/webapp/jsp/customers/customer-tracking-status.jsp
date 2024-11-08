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
    $(function() {
        var startDate = $("#dateValId").datepicker({
            changeMonth: true,
            changeYear: true,
            numberOfMonths: 1,
            minDate: 0,
            onSelect: function(selected) {
                var dt = new Date(selected);
                dt.setDate(dt.getDate());
                $("#nextdateValId").datepicker("option", "minDate", dt); // Set minDate for endDateInput
                $("#nextdateValId").prop("disabled", false); // Enable endDateInput
            }
        });

        $("#nextdateValId").datepicker({
            changeMonth: true,
            changeYear: true,
            numberOfMonths: 1,
            beforeShowDay: function(date) {
                // Disable dates before selected startDate
                if (startDate.datepicker("getDate") === null) {
                    return [false, ""];
                }
                return [date.getTime() >= startDate.datepicker("getDate").getTime(), ""];
            }
        });      
        
        // Initially disable endDateInput
        $("#nextdateValId").prop("disabled", true);
    });
</script>
<!-- <script>
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
</script> -->
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

		$('#date').focus();

		$('#btnsubmit').click(function(e) {

			//Action date..
			var isValid = true;
			var date = $('#dateValId').val();
			if (date == '') {
				isValid = false;
				$("#dateErr").show();
				$("#dateErr").html("Please Enter Action Date");
				$("#dateValId").css({
					"border" : "1px solid red",
				});
			} else {
				$('#dateErr').hide();
				$('#dateValId').css({
					"border" : "",
					"background" : ""
				});
			}

			//Next Appointment Date..
			var nextAppointmentDate = $('#nextdateValId').val();
			if (nextAppointmentDate == '') {
				isValid = false;
				$("#nextdateErr").show();
				$("#nextdateErr").html("Please Enter Next Appointment Date");
				$("#nextdateValId").css({
					"border" : "1px solid red",
				});
			} else {
				$('#nextdateErr').hide();
				$('#nextdateValId').css({
					"border" : "",
					"background" : ""
				});
			}

			//description...
			var description = $('#descValId').val();
			if (description == '') {
				isValid = false;
				$("#descriptionErr").show();
				$("#descriptionErr").html("Please Enter Description");
				$("#descValId").css({
					"border" : "1px solid red",

				});

			} else {
				$('#descriptionErr').hide();
				$('#descValId').css({

					"border" : "",
					"background" : ""
				});
			}
			//From Slot...
			var timeSlot = $('#startTime').val();
			if (timeSlot == 'Select') {
				isValid = false;
				$("#timeSlotErr").show();
				$("#timeSlotErr").html("Please Enter From Slot");
				$("#startTime").css({
					"border" : "1px solid red",

				});

			} else {
				$('#timeSlotErr').hide();
				$('#startTime').css({

					"border" : "",
					"background" : ""
				});
			}
			//To Slot...
			var endTimeSlot = $('#endTime').val();
			if (endTimeSlot == 'Select') {
				isValid = false;
				$("#endTimeSlotErr").show();
				$("#endTimeSlotErr").html("Please Enter To Slot");
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
			if (isValid == false)
				e.preventDefault();
		});
	});
</script>

<script>
    document.addEventListener("DOMContentLoaded", function() {
        var fromSlotSelect = document.getElementById("startTime");
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
				<h3>Customer Details</h3>
			</div>
			<div class="item">

				<div class="desc list-capitalize">
					<div class="row clearfix" style="line-height: 2em;">


						<div class="col-xs-3">
							<label style="font-weight: initial; font-weight: bold;">
								Name:</label>
							<c:out value="${viewemployer.firstName}"></c:out>

						</div>



						<div class="col-xs-3">
							<label style="font-weight: initial; font-weight: bold;">Email:</label>
							<c:out value="${viewemployer.emailAddress}"></c:out>
						</div>

						<div class="col-xs-3">
							<label style="font-weight: initial; font-weight: bold;">Contact:</label>
							<c:out value="${viewemployer.contactNo}"></c:out>
						</div>


						<div class="col-xs-3">
							<label style="font-weight: initial; font-weight: bold;">Mobile:</label>
							<c:out value="${viewemployer.mobileNo}"></c:out>
						</div>

						<div class="col-xs-3">
							<label style="font-weight: initial; font-weight: bold;">Company:</label>
							<c:out value="${viewemployer.companyName}">
							</c:out>
						</div>

						<div class="col-xs-3">
							<label style="font-weight: initial; font-weight: bold;">Industry:</label>
							<c:out value="${viewemployer.industryType}"></c:out>
						</div>
						<%-- <div class="col-xs-3">
							<label style="font-weight: initial; font-weight: bold;">Assingned User:</label>
							<c:out value="${viewemployer.adminUserBO.name}"></c:out>
						</div> --%>
					</div>
				</div>

			</div>
			<c:if test="${!empty clientBOList}">
				<div class="text-center underline">
					<h3>Customer Tracking History</h3>
				</div>


				<div class="pi-responsive-table-sm">
					<div class="pi-section-w pi-section-white piTooltips">
						<display:table id="data" name="${clientBOList}"
							requestURI="/customer-tracking-status" pagesize="10"
							export="false"
							class="pi-table pi-table-complex pi-table-hovered pi-round pi-table-shadow pi-table-all-borders">
							<display:column property="date" title="Appointment Date" />
							<display:column property="description" title="Description" />
							<display:column property="nextAppointmentDate"
								title="Next Appointment Date" />
						</display:table>
					</div>
				</div>
			</c:if>

			<div class="text-center underline">
				<h3>Update Customer Tracking Status</h3>
			</div>
			<div class="item">
				<form:form method="POST" id="addForm"
					action="customer-tracking-status" modelAttribute="viewemployer">
					<div class="row">
						<div class="col-sm-4">
							<div class="form-group">
								<!-- <label>Appointment Date <span
											class="font10 text-danger"> *</span></label> <input type="text"
											class="form-control" id="dateValId"> -->
								<label>Action Date <span class="font10 text-danger">
										*</span></label>
								<form:input path="date" placeholder="Action Date"   readonly="true"
									class="form-control" id="dateValId" />
								<form:errors path="date" cssClass="error" />
								<div id="dateErr" style="color: red;"></div>
							</div>
						</div>


						<div class="col-sm-4">
							<div class="form-group">

								<label>Next Appointment Date <span
									class="font10 text-danger"> *</span></label>
								<form:input path="nextAppointmentDate"   readonly="true"
									placeholder="Next Appointment Date" class="form-control"
									id="nextdateValId" />
								<form:errors path="nextAppointmentDate" cssClass="error" />
								<div id="nextdateErr" style="color: red;"></div>
							</div>
						</div>
					</div>
					<form:hidden path="id" />
					<div class="row">
						<div class="col-md-4">

							<label>Descriptions <span class="font10 text-danger">
									*</span></label>

							<form:textarea path="description" ame="description"
								placeholder="Description" class="form-control rtext"
								id="descValId" />
							<form:errors path="description" cssClass="error" />
							<div id="descriptionErr" style="color: red;"></div>

						</div>
						<div class="col-sm-4">
							<div class="form-group">
								<label>From Slot <span class="font10 text-danger">
										*</span></label>
								 <form:select type="text" path="timeSlot" id="startTime"
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
						<div class="col-sm-4">
							<div class="form-group">
								<label>To Slot <span class="font10 text-danger">
										*</span></label>
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


					</div>

					<div class="row">
						<div class="col-md-12">
							<div
								style="text-align: right; margin-right: 31px; margin-top: 9px;">
								<button type="submit" id="btnsubmit"
									class="btn btn-t-primary btn-theme lebal_align mt-20">Submit</button>
								<a href="view-customers"><span
									class="btn btn-t-primary btn-theme lebal_align mt-20">Cancel</span></a>
							</div>
						</div>
					</div>
				</form:form>
			</div>
		</div>
	</div>
	<br />
</div>


