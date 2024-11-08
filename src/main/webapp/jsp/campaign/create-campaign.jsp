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
	
	<style>
.blockHead {
	position: relative;
}

.blockHead h4 {
	color: #333333;
}

.error {
	border: 1px solid red;
}

.title {
	background-color: #2a3f54;
	padding: 10px;
	color: #fff;
}
</style>

<script>
	bkLib.onDomLoaded(function() {
		new nicEditor({
			buttonList : [ 'fontSize', 'bold', 'italic', 'underline', 'ol',
					'ul', 'strikeThrough', 'html' ]
		}).panelInstance('inputAddress');
	});
</script>

<div class="row scrollspy-sidenav pb-20 body-mt-15">
	<script>
		$(document).ready(function() {
			$('#btnsubmit').click(function(e) {
				var isValid = true;
				var campaignName = $('#campaignNameId').val();
				if (campaignName == '') {
					isValid = false;
					$("#campaignNameErr").show();
					$("#campaignNameErr").html("Please enter Campaign Name");
					$("#campaignNameId").css({
						"border" : "1px solid red",

					});

				}/* else if (!/^[a-zA-Z\s]*$/.test(campaignName)) {
				    isValid = false;
				    $("#campaignNameErr").show();
				    $("#campaignNameErr").html("Please enter only alphabetic characters");
				    $("#campaignNameId").css({
				        "border": "1px solid red",
				    });
				} */ else {
					$('#campaignNameErr').hide();
					$('#campaignNameId').css({

						"border" : "",
						"background" : ""
					});
				}
				//campaign owner
				var campaignOwner = $('#campaignOwnerId').val();
				if (campaignOwner == 'Select') {
					isValid = false;
					$("#campaignOwnerErr").show();
					$("#campaignOwnerErr").html("Please Select Campaign Owner");
					$("#campaignOwnerId").css({
						"border" : "1px solid red",

					});

				} else {
					$('#campaignOwnerErr').hide();
					$('#campaignOwnerId').css({

						"border" : "",
						"background" : ""
					});
				}
				//campaign mode
				var campaignMode = $('#campaignModeId').val();
				if (campaignMode == 'Select') {
					isValid = false;
					$("#campaignModeErr").show();
					$("#campaignModeErr").html("Please Select Campaign Mode");
					$("#campaignModeId").css({
						"border" : "1px solid red",

					});

				} else {
					$('#campaignModeErr').hide();
					$('#campaignModeId').css({

						"border" : "",
						"background" : ""
					});
				}
				//product name
				var serviceName = $('#serviceNameId').val();
				if (serviceName == 'Select') {
					isValid = false;
					$("#serviceNameIdErr").show();
					$("#serviceNameIdErr").html("Please Select Product Name");
					$("#serviceNameId").css({
						"border" : "1px solid red",

					});

				} else {
					$('#serviceNameIdErr').hide();
					$('#serviceNameId').css({

						"border" : "",
						"background" : ""
					});
				}
				//campaign status
				var status = $('#statusId').val();
				if (status == 'Select') {
					isValid = false;
					$("#statusErr").show();
					$("#statusErr").html("Please Select Campaign Status");
					$("#statusId").css({
						"border" : "1px solid red",

					});

				} else {
					$('#statusErr').hide();
					$('#statusId').css({

						"border" : "",
						"background" : ""
					});
				}
				//number employees
				var numberSent = $('#numberSentId').val();
				if (numberSent == '') {
					isValid = false;
					$("#numberSentErr").show();
					$("#numberSentErr").html("Please Enter Number of Employee");
					$("#numberSentId").css({
						"border" : "1px solid red",

					});

				}
				else if (!/^[0-9]{1,10}$/.test(numberSent)) {
					isValid = false;
					$("#numberSentErr").show();
					$("#numberSentErr").html("Please Enter Numeric Values Only");
					$("#numberSentId").css({
						"border" : "1px solid red",
	
					});
	
				} else {
					$('#numberSentErr').hide();
					$('#numberSentId').css({
	
						"border" : "",
						"background" : ""
					});
				}
				//Expected revenue
				var expectedRevenue = $('#expectedRevenueId').val();
				if (expectedRevenue == '') {
					isValid = false;
					$("#expectedRevenueErr")
							.show();
					$("#expectedRevenueErr")
							.html(
									"Please Enter Expected Revenue");
					$("#expectedRevenueId")
							.css(
									{
										"border" : "1px solid red",
									});

				}else if (!/^[0-9]{1,10}$/
						.test(expectedRevenue)) {

					isValid = false;
					$("#expectedRevenueErr").show();
					$("#expectedRevenueErr").html("Please Enter Numeric Values Only");
					$("#expectedRevenueId").css({
						"border" : "1px solid red",
	
					});
	
				} else {
					$('#expectedRevenueErr').hide();
					$('#expectedRevenueId').css({
	
						"border" : "",
						"background" : ""
					});
				}
				//budget cost
				var budgetedCost = $('#budgetedCostId').val();
				if (budgetedCost == '') {
					isValid = false;
					$("#budgetedCostErr")
							.show();
					$("#budgetedCostErr")
							.html(
									"Please Enter Budget Cost");
					$("#budgetedCostId")
							.css(
									{
										"border" : "1px solid red",
									});

				} else if (!/^[0-9]{1,10}$/
						.test(budgetedCost)) {

					isValid = false;
					$("#budgetedCostErr").show();
					$("#budgetedCostErr").html("Please Enter Numeric Values Only");
					$("#budgetedCostId").css({
						"border" : "1px solid red",
	
					});
	
				} else {
					$('#budgetedCostErr').hide();
					$('#budgetedCostId').css({
	
						"border" : "",
						"background" : ""
					});
				}	
				//Expected response
				var expectedResponse = $('#expectedResponseId').val();
				if (expectedResponse == '') {
					isValid = false;
					$("#expectedResponseErr").show();
					$("#expectedResponseErr").html("Please enter Expected Response");
					$("#expectedResponseId").css({
						"border" : "1px solid red",

					});

				} else {
					$('#expectedResponseErr').hide();
					$('#expectedResponseId').css({
	
						"border" : "",
						"background" : ""
					});
				}	
				//description
				var description = $('#descriptionId').val();
				if (description == '') {
					isValid = false;
					$("#descriptionErr").show();
					$("#descriptionErr").html("Please enter Description");
					$("#descriptionId").css({
						"border" : "1px solid red",

					});

				} else {
					$('#descriptionErr').hide();
					$('#descriptionId').css({

						"border" : "",
						"background" : ""
					});
				}
				//startdate
					var startedTime = $('#startDateInput').val();
					if (startedTime == '') {
						isValid = false;
						$("#startedTimeErr").show();
						$("#startedTimeErr").html("Please Select Date");
						$("#startDateInput").css({
							"border" : "1px solid red",
	
						});
	
					} else {
						$('#startedTimeErr').hide();
						$('#startDateInput').css({
	
							"border" : "",
							"background" : ""
						});
				}
					//end date
					var endTime = $('#endDateInput').val();
					if (endTime == '') {
						isValid = false;
						$("#endTimeErr").show();
						$("#endTimeErr").html("Please Select Date");
						$("#endDateInput").css({
							"border" : "1px solid red",
	
						});
	
					} else {
						$('#endTimeErr').hide();
						$('#endDateInput').css({
	
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
    $(function() {
        var startDate = $("#datepicker").datepicker({
            changeMonth: true,
            changeYear: true,
            numberOfMonths: 1,
            minDate: 0,
            onSelect: function(selected) {
                var dt = new Date(selected);
                dt.setDate(dt.getDate());
                $("#endDateInput").datepicker("option", "minDate", dt); // Set minDate for endDateInput
                $("#endDateInput").prop("disabled", false); // Enable endDateInput
            }
        });

        $("#endDateInput").datepicker({
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
        $("#endDateInput").prop("disabled", true);
    });
</script> 
	
	 <!-- <script type="text/javascript">
        $(function() {
        	 var currentYear = new Date().getFullYear();
        	 var currentDate = new Date();
            
            $("#startDate").datepicker({
                dateFormat: "dd-mm-yy",
                changeMonth: true,
                changeYear: true,
                yearRange: currentYear + ":c+10",
                minDate: currentDate,
                onSelect: function(dateText) {
                    validateDates();
                }
            });
            $("#endDate").datepicker({
                dateFormat: "dd-mm-yy",
                changeMonth: true,
                changeYear: true,
                yearRange: currentYear + ":c+10",
                minDate: currentDate,
                onSelect: function(dateText) {
                    validateDates();
                }
            });
        });

        function validateDates() {
            var startDate = $("#startDate").datepicker("getDate");
            var endDate = $("#endDate").datepicker("getDate");
            var errorMessage = $("#errorMessage");

            if (startDate && endDate && startDate > endDate) {
                errorMessage.text("End date should be greater than Start date.");
            } else {
                errorMessage.text("");
            }
        }
    </script> -->

	<!-- <script>
		$(function() {
			$("#startDateInput").datepicker({
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
	</script>  -->
	
	<!-- <script>
    $(function() {
        var currentDate = new Date(); // Get current date

        $("#startDateInput").datepicker({
            changeMonth: true,
            changeYear: true,
            numberOfMonths: 1,
            maxDate: '0', // Set maximum date to today
            onSelect: function(selected) {
                var dt = new Date(selected);
                dt.setDate(dt.getDate());
                $("#endDateInput").datepicker("option", "minDate", dt);
            }
        });

        $("#endDateInput").datepicker({
            changeMonth: true,
            changeYear: true,
            numberOfMonths: 1,
            minDate: currentDate, // Set minimum date to current date
            onSelect: function(selected) {
                var dt = new Date(selected);

                
                var dt2 = new Date(selected);
                dt.setDate(dt.getDate() - 1);
                dt2.setDate(dt.getDate() + 1);
            }
        });

    });
</script> -->
	


	<div class="warning">

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
	</div>

	<div class="contact-form-wrapper">

		<div class="box-list">
			<div class="item">
				<div class="row ">
					<div class="text-center underline">
						<h3>Create Campaign</h3>
					</div>
					<br>

					<form:form method="POST" id="addForm" action="create-campaign"
						modelAttribute="campaignBO">
						<div class="box-list">
						<div class="item">
						<div class="row ">
						
						 <!-- <div class="row" style="border: 4px solid #e6e6e6; margin: 15px 15px 15px 15px; background-color: #2a3f54"> -->
							<!--  <h3>Campaign Information</h3>  -->
							 <h3 class="title">Campaign Information</h3> 
							<!--  </div> --> 
						<div class="row">
							<div class="col-sm-4">
								<div class="form-group">
									<label>Campaign Name <span class="font10 text-danger">*</span></label>
									<form:input type="text" path="campaignName" id="campaignNameId"
										class="form-control required" placeholder="Campaign Name" />
									<form:errors path="campaignName" class="error" />
									<div id="campaignNameErr" style="color: red;"></div>
								</div>
							</div>
						
							<div class="col-sm-4">
								<div class="form-group">
									<label>Campaign Owner<span class="font10 text-danger">*</span></label>
									<form:select type="text" path="campaignOwner" id="campaignOwnerId"
										class="form-control required">
										<form:option value="Select">-- Select --</form:option>
										<form:options items="${userBOList}" itemLabel="name"
											itemValue="id" />
									</form:select>   
									<form:errors path="campaignOwner" class="error" />
									<div id="campaignOwnerErr" style="color: red;"></div>
								</div>
							</div>
							
							<div class="col-sm-4">
								<div class="form-group">
									<label>Campaign Mode <span class="font10 text-danger">*</span></label>
									<form:select type="text" path="campaignMode" id="campaignModeId"
										class="form-control required">
										<form:option value="Select">--Select--</form:option>
										<form:option value="Sms">SMS</form:option>
										<form:option value="WhatsApp">WHATSAPP</form:option>
										<form:option value="Email">EMAIL</form:option>
										<form:option value="Call">CALL</form:option>
									</form:select>
										<form:errors path="campaignMode" class="error" />
										<div id="campaignModeErr" style="color: red;"></div>
								</div>
							</div>
						</div> 
							
						<div class="row">
							<div class="col-sm-4">
								<div class="form-group">
									<label>Product Name<span class="font10 text-danger">*</span></label>
									<form:select type="text" path="productServiceBO.serviceName" 
										   id="serviceNameId" class="form-control required">
										<form:option value="Select">-- Select --</form:option>
										<form:options items="${productList}" itemLabel="serviceName"
											itemValue="serviceId" />
									</form:select>
										<form:errors path="productServiceBO.serviceName" class="error" />
										<div id="serviceNameIdErr" style="color: red;"></div>
								</div>
							</div>
						
							<div class="col-sm-4">
								<div class="form-group">
									<label>Campaign Status<span class="font10 text-danger">*</span></label>
									<!-- <label class="hidden-xs"></label> -->
										<form:select type="text" path="status"
										  id="statusId" class="form-control required">
										<form:option value="Select">--Select Status--</form:option>
										<form:option value="Initialize">Initialize</form:option>
										<form:option value="Process">Process</form:option>
										<form:option value="Complete">Complete</form:option>
										<form:option value="Not complete">Not complete</form:option>
									</form:select>
									<form:errors path="status" class="error" />
										<div id="statusErr" style="color: red;"></div>
								</div>
							</div>
						</div>	
							</div>
						</div>
					</div>
							<br>
					<div class="box-list">
						<div class="item">
						<div class="row ">
						<h3 class="title">Additional Information</h3> 
						<!-- <div class="row" style="border: 4px solid #e6e6e6; margin: 15px 15px 15px 15px; background-color: #e1e1e1">
							 <h3>Additional Information</h3>		style="background:gray;"
							 </div>	 -->
						<div class="row">
							<div class="col-sm-4">
								<div class="form-group">
									<label>Number of Employees<span class="font10 text-danger">*</span></label>
									<label class="hidden-xs"></label>
									<form:input type="ntext" class="form-control" path="numberSent"
										id="numberSentId" placeholder="Number of Employees" maxlength="10" 
										></form:input>
										<form:errors path="numberSent" class="error" />
										<div id="numberSentErr" style="color: red;"></div>
										</div>
								</div>
							
							<div class="col-sm-4">     <!-- readonly="true"  -->
								<div class="form-group">
									<label>Start Date<span class="font10 text-danger">*</span></label>
									<label class="hidden-xs"></label>
									<form:input  type="text"  
										path="startedTime" id="datepicker" class="form-control required"
										placeholder="Start Date" ></form:input>
										<form:errors path="startedTime" class="error" />
										<div id="startedTimeErr" style="color: red;"></div>
								</div>
							</div>

							<div class="col-sm-4">       <!-- readonly="true"  -->
								<div class="form-group">
									<label>End Date<span class="font10 text-danger">*</span></label>
									<label class="hidden-xs"></label>
									<form:input type="text" class="form-control required"  
									path="endTime" id="endDateInput"  
										placeholder="End Date" ></form:input>
										<form:errors path="endTime" class="error" />
										<div id="endTimeErr" style="color: red;"></div>
								</div>
								<span id="errorMessage" style="color: red;"></span>
							</div>
							
						</div>
		
						<div class="row">
							<div class="col-sm-4">
								<div class="form-group">
									<label>Expected Revenue<span class="font10 text-danger">*</span></label>
									<label class="hidden-xs"></label>
									<form:input type="ntext" class="form-control" path="expectedRevenue" maxlength="10" 
										id="expectedRevenueId" placeholder="Expected Revenue"></form:input>
										<form:errors path="expectedRevenue" class="error" />
										<div id="expectedRevenueErr" style="color: red;"></div>
										</div>
								</div>
										
							<div class="col-sm-4">
								<div class="form-group">
									<label>Budgeted Cost<span class="font10 text-danger">*</span></label>
									<label class="hidden-xs"></label>
									<form:input type="ntext" class="form-control" path="budgetedCost"
										id="budgetedCostId" placeholder="Budgeted Cost" maxlength="30" 
										></form:input>
										<form:errors path="budgetedCost" class="error" />
										<div id="budgetedCostErr" style="color: red;"></div>
								</div>	
							</div>
							
							<div class="col-sm-4">
								<div class="form-group">
									<label>Expected Response<span class="font10 text-danger">*</span></label>
									<label class="hidden-xs"></label>
									<form:input type="ntext" class="form-control" path="expectedResponse"
										id="expectedResponseId" placeholder="Expected Response" maxlength="30" 
										></form:input>
										<form:errors path="expectedResponse" class="error" />
										<div id="expectedResponseErr" style="color: red;"></div>
								</div>
							</div>
						</div>
						
						<div class="col-sm-12">
								<div class="form-group">
									<label>Description<span class="font10 text-danger">*</span></label>
									<label class="hidden-xs"></label>
										<form:textarea path="description" id="descriptionId" 
											class="form-control required" placeholder="Description" cols="130" rows="06" 
											maxlength="1000" />
										<form:errors path="description" class="error" />
										<div id="descriptionErr" style="color: red;"></div>
										</div>
								</div>	
							</div>
						</div>
					</div>
					<br>
						
							
				
				<br>
				<div style="text-align: right; margin-right: 31px">
					<button type="submit" id="btnsubmit"
						class="btn btn-t-primary btn-theme lebal_align mt-20">Submit</button>
					<a href="view-campaign"><span
						class="btn btn-t-primary btn-theme lebal_align mt-20">Cancel</span></a>
				</div>
				</form:form>
			</div>
		</div>
	</div>
</div>
</div>
<br>

