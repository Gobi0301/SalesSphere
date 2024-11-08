<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>

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
	$(document).ready(function() {
		$('#btnsubmit').click(function(e) {
			var isValid = true;
			//ProjectName
			var serviceName = $('#projectNameId').val();
			if (!serviceName.trim()) {
			    isValid = false;
			    $("#projectNameErr").html("Please enter Project Name");
			    $("#projectNameErr").show();
			    $("#projectNameId").css({
			    	"border" : "1px solid red",
			    		    });
			} else {
			    $('#projectNameErr').hide();
			    $('#projectNameId').css({
			        "border": "",
			        "background": ""
			    });
			}


			//Supplier Name..
			
			var projectType = $('#projectType').val();
			if (projectType=='') {
				isValid = false;
				$("#projectTypeErr").show();
				$("#projectTypeErr").html("Please Select Type");
				$("#projectType").css({
					"border" : "1px solid red",
				});
			} else {
				$('#projectTypeErr').hide();
				$('#projectType').css({
					"border" : "",
					"background" : ""
				});
			}

			//Expected Date..
			
			var projectStatus = $('#projectStatus').val();
			if (projectStatus=='') {
				isValid = false;
				$("#projectStatusErr").show();
				$("#projectStatusErr").html("Please select Status");
				$("#projectStatus").css({
					"border" : "1px solid red",
				});
			} else {
				$('#projectStatusErr').hide();
				$('#projectStatus').css({
					"border" : "",
					"background" : ""
				});
			}

			//Minimum Stock..
		
			var approval = $('#approval').val();
			if (approval=='') {
				isValid = false;
				$("#approvalErr").show();
				$("#approvalErr").html("Please select Approval");
				$("#approval").css({
					"border" : "1px solid red",
				});
			} else {
				$('#approvalErr').hide();
				$('#approval').css({
					"border" : "",
					"background" : ""
				});
			}

			//Maximum Stock..
			
			var datepicker = $('#datepicker').val();
			if (datepicker=='') {
				isValid = false;
				$("#startDateErr").show();
				$("#startDateErr").html("Please select startDate");
				$("#datepicker").css({
					"border" : "1px solid red",
				});
			} else {
				$('#startDateErr').hide();
				$('#datepicker').css({
					"border" : "",
					"background" : ""
				});
			}

			//Available Stock..
			
			var datepicker = $('#datepicker').val();
			if (datepicker=='') {
				isValid = false;
				$("#endDateErr").show();
				$("#endDateErr").html("Please select endDate");
				$("#endDateInput").css({
					"border" : "1px solid red",
				});
			} else {
				$('#endDateErr').hide();
				$('#endDateInput').css({
					"border" : "",
					"background" : ""
				});
			} 

			//QuantityOfProducts..
			
			var projectAreaInSqfts = $('#projectAreaInSqfts').val();
			if (projectAreaInSqfts =='') {
				isValid = false;
				$("#projectAreaInSqftsErr").show();
				$("#projectAreaInSqftsErr").html("Please enter Area in Sqfts");
				$("#projectAreaInSqfts").css({
					"border" : "1px solid red",
				});
			} else {
				$('#projectAreaInSqftsErr').hide();
				$('#projectAreaInSqfts').css({
					"border" : "",
					"background" : ""
				});
			}
			//QuantityOfProducts..
			
			var projectAreaInSqfts = $('#unit').val();
			if (projectAreaInSqfts =='') {
				isValid = false;
				$("#unitErr").show();
				$("#unitErr").html("Please select unit");
				$("#unit").css({
					"border" : "1px solid red",
				});
			} else {
				$('#unitErr').hide();
				$('#unit').css({
					"border" : "",
					"background" : ""
				});
			}

			// Unit Cost..
		
			var projectLocation = $('#projectLocation').val();
			if (projectLocation=='') {
				isValid = false;
				$("#projectLocationErr").show();
				$("#projectLocationErr").html("Please enter Location");
				$("#projectLocation").css({
					"border" : "1px solid red",
				});
			} else {
				$('#projectLocationErr').hide();
				$('#projectLocation').css({
					"border" : "",
					"background" : ""
				});
			}

			if (isValid == false)
				e.preventDefault();
		});
	});
</script>
<!-- <script>
		$(function() {
			$("#datepicker").datepicker({
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
	</script> -->
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
	
</head>
<body>

	<div class="row scrollspy-sidenav pb-20 body-mt-15">

		<div class="contact-form-wrapper">
			<div class="box-list">
				<div class="item">
					<div class="row ">
						<div class="text-center underline">
							<h2>Edit Project</h2>
						</div>
						<form:form method="POST" id="addForm" action="edit-project"
							modelAttribute="projectBO">
							<form:hidden path="projectId" 
						value="${projectBO.projectId}" />
							<input type="hidden" name="origin" value="${origin}">
							<input type="hidden" name="leadId" value="${leadId}">
							<!-- <h3 class="title">Project Details</h3> -->
							<fieldset>
 
							<div class="box-list">
							 <legend>Project Details</legend>
								<div class="item">
									<div class="row">
										<div class="col-sm-12">

											<div class="col-sm-3">
												<div class="form-group">
													<label>Project Name<span class="font10 text-danger">*</span></label>
													<form:input type="text" name="projectName"
														path="projectName" id="projectNameId"
														class="form-control required" placeholder="Project Name"
														maxlength="150" />
													<form:errors path="projectName" class="error" />
													<div id="projectNameErr" style="color: red;"></div>

												</div>

											</div>
											<div class="col-sm-3">
												<div class="form-group">
													<label>Type<span class="font10 text-danger">*</span></label>
													<form:select type="text" name="projectType"
														id="projectType" path="projectType"
														class="form-control required">
														<form:option value="">------------------Select------------------</form:option>
														<form:option value="Plots">Plots</form:option>
														<form:option value="Villas">Villas</form:option>
														<form:option value="Apartments">Apartments</form:option>
														<form:option value="Farm Land">Farm Land</form:option>
														<form:option value="Textile Industry">Textile Industry</form:option>
													</form:select>
													<form:errors path="projectType" class="error" />
													<div id="projectTypeErr" style="color: red;"></div>
												</div>
											</div>

											<div class="col-sm-3">
												<div class="form-group">
													<label>Status<span class="font10 text-danger">*</span></label>
													<form:select type="text" name="projectStatus"
														id="projectStatus" path="projectStatus"
														class="form-control required">
														<form:option value="">------------------Select------------------</form:option>
														<form:option value="OnGoing Project">OnGoing Project</form:option>
														<form:option value="Completed Project">Completed Project</form:option>
														<form:option value="Upcoming Project">Upcoming Project</form:option>
													</form:select>
													<form:errors path="projectStatus" class="error" />
													<div id="projectStatusErr" style="color: red;"></div>
												</div>
											</div>

											<div class="col-sm-3">
												<div class="form-group">
													<label>Approval<span class="font10 text-danger">*</span></label>
													<form:select type="text" name="approval" id="approval"
														path="approval" class="form-control required">
														<form:option value="">------------------Select------------------</form:option>
														<form:option value="DTCP Approved">DTCP Approved</form:option>
														<form:option value="CMDA Approved">CMDA Approved</form:option>
													</form:select>
													<form:errors path="approval" class="error" />
													<div id="approvalErr" style="color: red;"></div>
												</div>
											</div>
										</div>

<div class="col-sm-12">
											<div class="col-sm-2">
												<div class="form-group">
													<label>Area<span class="font10 text-danger">*</span></label>
													<form:input type="text" name="projectAreaInSqfts"
														path="projectAreaInSqfts" id="projectAreaInSqfts"
														class="form-control required" placeholder="Area"
														maxlength="150" />
													<form:errors path="projectAreaInSqfts" class="error" />
													<div id="projectAreaInSqftsErr" style="color: red;"></div>
												</div>
											</div>
											<div class="col-sm-1">
												<div class="form-group">
													<label>Unit<span class="font10 text-danger">*</span></label>
													<form:select type="text" name="unit" id="unit"
														path="unit" class="form-control required">
														<form:option value="">-Select-</form:option>
														<form:option value="Sq.Fts">Sq.Fts</form:option>
														<form:option value="Acres">Acres</form:option>
													</form:select>
													<form:errors path="unit" class="error" />
													<div id="unitErr" style="color: red;"></div>
												</div>
											</div>
											
											<div class="col-sm-3">
												<div class="form-group">

													<label> Location <span class="font10 text-danger">*</span></label>
												 <form:input type="text" name="projectLocation"
														path="projectLocation" id="projectLocation"
														class="form-control required" placeholder="Location"
														maxlength="150" /> 
													<form:errors path="projectLocation" class="error" />
													<div id="projectLocationErr" style="color: red;"></div>
												</div>

											</div>
											</div>
										</div>
									</div>
								</div>
							</div>
							<br>
						 </fieldset>
						 <fieldset>
  <legend> Timeline </legend>
										<div class="col-sm-12">
											<div class="col-sm-3">
												<div class="form-group">
													<label>Start Date <span class="font10 text-danger">*</span></label>
													<form:input type="text" name="startDate" path="startDate" readonly="true" 
														id="datepicker" class="form-control required"
														maxlength="150" />
													<form:errors path="startDate" class="error" />
													<div id="startDateErr" style="color: red;"></div>
												</div>
											</div>
											<div class="col-sm-3">
												<div class="form-group">
													<label>Completed Date <span
														class="font10 text-danger">*</span></label>
													<form:input type="text" name="endDate" path="endDate"
														 readonly="true" id="endDateInput" class="form-control required" maxlength="150" />
													<form:errors path="endDate" class="error" />
													<div id="endDateErr" style="color: red;"></div>
												</div>
											</div>
											</div>
											 </fieldset>
							<br>
							<%-- 				<h3 class="title">Luxuries Details</h3>
					<div class="box-list">
						<div class="item">
							<div class="row">
								<div class="col-sm-12">


									<div class="col-sm-6">
										<div class="form-group">

											<label> Amenities <span class="font10 text-danger">*</span></label>
											<form:textarea path="amenities" id="amenities"
												class="form-control required" placeholder="Amenities"
												maxlength="100" cols="10" rows="05" />
											<form:errors path="amenities" class="error" />
											<div id="amenitiesErr" style="color: red;"></div>
										</div>
									</div>


								</div>


							</div>
						</div>
					</div>
					<br>
					<h3 class="title">Adjacent Localities</h3>
					<div class="box-list">
						<div class="item">
							<div class="row">
								<div class="col-sm-12">

									<div class="col-sm-6">
										<div class="form-group">

											<label> Nearby Localities <span
												class="font10 text-danger">*</span></label>
											<form:textarea path="nearByLocalities" id="nearByLocalities"
												class="form-control required"
												placeholder="NearBy Localities" maxlength="100" cols="10"
												rows="05" />
											<form:errors path="nearByLocalities" class="error" />
											<div id="nearByLocalitiesErr" style="color: red;"></div>
										</div>
									</div>
								</div> --%>

							<div style="float: right; margin: -14px 5px;">
								<button type="submit" id="btnsubmit"
									class="btn btn-t-primary btn-theme lebal_align mt-20"
									style="text-align: right;">Submit</button>
								<a href="admin-home"><span
									class="btn btn-t-primary btn-theme lebal_align mt-20">Cancel</span></a>
							</div>
							</form:form>
					</div>

				</div>
			</div>
			
		</div>
	</div>
	</div>

	</div>
	</div>
	</br>
</body>

</html>
