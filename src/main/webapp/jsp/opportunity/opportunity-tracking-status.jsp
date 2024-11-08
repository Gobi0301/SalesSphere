<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>View Opportunity Details</title>
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
	document.addEventListener("DOMContentLoaded",
			function() {
				var fromSlotSelect = document.getElementById("startTime");
				var toSlotSelect = document.getElementById("endTime");

				fromSlotSelect.addEventListener("change", function() {
					var selectedFromSlot = fromSlotSelect.value;
					var selectedFromSlotTime = parseTime(selectedFromSlot);

					// Clear previous options
					toSlotSelect.innerHTML = "";

					// Limit end time to 6:00 PM
					var limitTime = {
						hours : 18,
						minutes : 0
					}; // 18 represents 6:00 PM in 24-hour format

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
						hours : hours,
						minutes : minutes
					};
				}

				function add30Minutes(time) {
					var result = {
						hours : time.hours,
						minutes : time.minutes + 30
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
					var minutes = time.minutes < 10 ? "0" + time.minutes
							: time.minutes;
					return hours + ":" + minutes + " " + period;
				}

				function timeIsAfter(time1, time2) {
					if (time1.hours > time2.hours) {
						return true;
					} else if (time1.hours === time2.hours
							&& time1.minutes > time2.minutes) {
						return true;
					}
					return false;
				}
			});
</script>

<script>
	$(document).ready(function() {

		$('#btnsubmit').click(function(e) {

			//Workitem
			var isValid = true;
			var convertedDate = $('#dateValId').val();
			if (convertedDate == '') {
				isValid = false;
				$("#convertedDateErr").show();
				$("#convertedDateErr").html("Please Enter ConvertedDate");
				$("#dateValId").css({
					"border" : "1px solid red",
				});
			} else {
				$('#convertedDateErr').hide();
				$('#dateValId').css({
					"border" : "",
					"background" : ""
				});
			}

			 //FollowUpDate
			var FollowUpDate = $('#nextdateValId').val();
			if (FollowUpDate == '') {
				isValid = false;
				$("#followupDateErr").show();
				$("#followupDateErr").html("Please Enter FollowUpDate");
				$("#nextdateValId").css({
					"border" : "1px solid red",
				});
			} else {
				$('#followupDateErr').hide();
				$('#nextdateValId').css({
					"border" : "",
					"background" : ""
				});
			} 

			 //From Slot
			var timeSlot = $('#startTime').val();
			if (timeSlot == 'Select') {
				isValid = false;
				$("#timeSlotErr").show();
				$("#timeSlotErr").html("Please Select From Slot");
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

			 //To Slot
			var endTimeSlot = $('#endTime').val();
			if (endTimeSlot == 'Select') {
				isValid = false;
				$("#endTimeSlotErr").show();
				$("#endTimeSlotErr").html("Please Select To Slot");
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

			 //To Slot
			var description = $('#endTime').val();
			if (description == 'Select') {
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
			if (isValid == false)
				e.preventDefault();
		});
	});
</script>




<style>
.blockHead {
	position: relative;
}

.blockHead h4 {
	color: #333333;
}

.parent {
	position: relative;
	display: flex;
	float: left;
	justify-content: space-evenly;
}

.title {
	background-color: #2a3f54;
	padding: 10px;
	color: #fff;
}

.navigation {
	margin: 23px -4px 16px 30px;
	padding: 8px;
	float: right;
}
</style>
</head>
<body>
	<main id="main">
		<section class="container instructor-profile-block">
			<div class="contact-form-wrapper" style="margin-top: 50px;">
				<div class="box-list">
					<div class="item">
						<h3 class="text-center underline">View Opportunity Details</h3>
						<div class="row">

							<h3 class="title">Personal Information</h3>
							<div class="item">
								<div class="desc list-capitalize">
									<div class="row clearfix" style="line-height: 2em;">
										<div class="col-xs-4">
											<div class="parent">
												<label>Salutation :</label> <span>${profile.salutation}</span>
											</div>
										</div>
										<div class="col-xs-4">
											<div class="parent">
												<label>First Name :</label> <span>${profile.firstName}</span>
											</div>
										</div>
										<div class="col-xs-4">
											<div class="parent">
												<label>Last Name :</label> <span>${profile.lastName}</span>
											</div>
										</div>

									</div>
								</div>
							</div>
						</div>
						<br>
						<div class="row">
							<h3 class="title">Account Information</h3>
							<div class="item">
								<div class="desc list-capitalize">
									<div class="row clearfix" style="line-height: 2em;">
										<div class="col-xs-4">
											<div class="parent">
												<label>Account Name :</label> <span>${profile.accountBO.accountName}</span>
											</div>
										</div>
										<%-- <div class="col-xs-4">
							<div class="parent">
								<label>Lead Source :</label> <span>${profile.leads.leadsId}</span>
							</div>
						</div> --%>

										<div class="col-xs-4">
											<div class="parent">
												<label>Assigned To :</label> <span>${profile.user.name}</span>
											</div>
										</div>



									</div>
								</div>
							</div>
						</div>

						<br>
						<div class="row">
							<h3 class="title">Sales Information</h3>
							<div class="item">
								<div class="desc list-capitalize">
									<div class="row clearfix" style="line-height: 3em;">

										<div class="col-xs-4">
											<div class="parent">
												<label>Product :</label> <span>${profile.productService.serviceName}</span>
											</div>
										</div>
										<div class="col-xs-4">
											<div class="parent">
												<label>Amount :</label> <span>${profile.amount}</span>
											</div>
										</div>
										<div class="col-xs-4">
											<div class="parent">
												<label>Sales Stage :</label> <span>${profile.salesStage}</span>
											</div>
										</div>

									</div>
									<div class="row clearfix" style="line-height: 3em;">
										<div class="col-xs-4">
											<div class="parent">
												<label>Expected Closing Date :</label> <span>${profile.endTime}</span>
											</div>
										</div>
										<div class="col-xs-4">
											<div class="parent">
												<label>Next Step :</label> <span>${profile.nextStep}</span>
											</div>
										</div>
										<div class="col-xs-4">
											<div class="parent">
												<label>Probability :</label> <span>${profile.probability}
													%</span>
											</div>
										</div>


									</div>

								</div>
							</div>
						</div>
						<br>


						<div class="row">
							<h3 class="title">Additional Information</h3>
							<div class="item">
								<div class="desc list-capitalize">
									<div class="row clearfix" style="line-height: 3em;">

										<div class="col-lg-6">
											<label>Description :</label>
											<div class="description">
												<span>${profile.description}</span>
											</div>
										</div>
									</div>
								</div>
							</div>
							<!-- <div class="navigation">
										
										<a href="view-opportunities"><span
											class="btn btn-t-primary btn-theme lebal_align mt-20">Back</span></a>
									</div> -->
						</div>

						<%-- <c:if test="${not empty activityCount}">
							<div class="col-sm-12" style="margin-top: 20px">
								<a class="btn btn-theme btn-xs btn-default"
									style="color: #1b1818; font-weight: bold;"><c:out
										value="${activityCount}"></c:out></a> <strong class="color-black">Activity
									Found</strong>
							</div>
						</c:if> --%>
						
						<c:if test="${!empty opportunityBOLists}">
							<div class="text-center underline">
								<h3>Opportunity Tracking History</h3>
							</div>
							<div class="pi-responsive-table-sm">
								<div class="pi-section-w pi-section-white piTooltips">
									<display:table id="data" name="${opportunityBOLists}"
										requestURI="/opportunity-tracking-status" export="false" pagesize="10"
										class="pi-table pi-table-complex pi-table-hovered pi-round pi-table-shadow pi-table-all-borders">
										<display:column property="convertedDate"
											title="Converted Date" />
										<display:column property="followupDate" title="Followup Date" />
										<display:column property="description" title="Description" />

									</display:table>
								</div>
							</div>
						</c:if>

						<div class="text-center underline">
							<h3>Update Opportunity Tracking Status</h3>
						</div>
						<div class="item">
							<form:form method="POST" id="addForm"
								action="opportunity-tracking-status" modelAttribute="profile">
								<div class="row">
									<div class="col-sm-4">
										<div class="form-group">

											<form:hidden path="opportunityId" />
											<label>Converted Date <span
												class="font10 text-danger"> *</span></label>
											<form:input path="convertedDate" class="form-control" readonly="true"
												type="text" id="dateValId" placeholder="ConvertedDate" />
											<form:errors path="convertedDate" cssClass="error" />
											<div id="convertedDateErr" style="color: red"></div>
										</div>
									</div>


									<div class="col-sm-4">
										<div class="form-group">

											<label>FollowUp Date <span class="font10 text-danger">
													*</span></label>
											<form:input path="followupDate" class="form-control" readonly="true"
												id="nextdateValId" placeholder="FollowUpDate" />
											<form:errors path="followupDate" cssClass="error" />
											<div id="followupDateErr" style="color: red"></div>
										</div>
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
								</div>

								<div class="row">
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
									<div class="col-md-8">
										<label>Descriptions <span class="font10 text-danger">
												*</span></label>

										<form:textarea path="description" name="description"
											placeholder="Description" class="form-control rtext"
											id="descValId" maxlength="2000" rows="3" />
										<form:errors path="description" cssClass="error" />
										<div id="descriptionErr" style="color: red"></div>

									</div>

								</div>
								</br>
								<div class="row">
									<div class="col-md-12">
										<div style="text-align: right; margin-right: 31px;">
											<button type="submit" id="btnsubmit"
												class="btn btn-t-primary btn-theme lebal_align mt-20">Submit</button>
											<a href="view-opportunities"><span
												class="btn btn-t-primary btn-theme lebal_align mt-20">Cancel</span></a>
										</div>
									</div>
								</div>
							</form:form>
						</div>
					</div>

				</div>
			</div>
			<br>
		</section>

	</main>
</body>
</html>