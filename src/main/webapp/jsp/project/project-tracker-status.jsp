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

		$('#btnSubmit').click(function(e) {
			var isValid = true;
			var Amenities = $('#dateId').val();
			if (Amenities == '') {
				isValid = false;
				$("#AmenitiesErr").show();
				$("#AmenitiesErr").html("Please Enter Amenities");
				$("#dateId").css({
					"border" : "1px solid red",

				});

			} else {
				$('#AmenitiesErr').hide();
				$('#dateId').css({

					"border" : "",
					"background" : ""
				});
			}
			
			var nearByLocalities = $('#nearByLocalitiesId').val();
			if (nearByLocalities == '') {
				isValid = false;
				$("#nearByLocalitiesErr").show();
				$("#nearByLocalitiesErr").html("Please enter nearby");
				$("#nearByLocalitiesId").css({
					"border" : "1px solid red",

				});

			} 
			 else {
				$('#nearByLocalitiesErr').hide();
				$('#nearByLocalitiesId').css({

					"border" : "",
					"background" : ""
				});
			}

			if (isValid == false)
				e.preventDefault();

		});
	});
</script>
<div class="warning">


		<c:if test="${not empty successMessage}">
						<div class="alert alert-success">
							<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
							<strong>Success:</strong>
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

<div class="contact-form-wrapper" style="margin-top: 50px;">
	<div class="box-list">
		<div class="item">
<div class="text-center underline">
				<h3>Project Details</h3>
			</div>
		        <div class="box-list">
				<div class="item">
				<div class="desc list-capitalize">
					<div class="row clearfix" style="line-height: 2em;">

						<div class="col-xs-3">
							<label style="font-weight: initial; font-weight: bold;">
								Project Name</label>:
							<c:out value="${viewProject.projectName}"></c:out>
						</div>
						<div class="col-xs-3">
							<label style="font-weight: initial; font-weight: bold;">
								Type</label>:
							<c:out value="${ viewProject.projectType}"></c:out>
						</div>
						<div class="col-xs-3">
							<label style="font-weight: initial; font-weight: bold;">
								Status</label>:
							<c:out value="${ viewProject.projectStatus}"></c:out>
						</div>
						<div class="col-xs-3">
							<label style="font-weight: initial; font-weight: bold;">
								Approval</label>:
							<c:out value="${ viewProject.approval}"></c:out>
						</div>

						<div class="col-xs-3">
							<label style="font-weight: initial; font-weight: bold;">
								Location</label>:
							<c:out value="${ viewProject.projectLocation}"></c:out>
						</div>
						<div class="col-xs-3">
							<label style="font-weight: initial; font-weight: bold;">
							Area </label>:
							<c:out value="${ viewProject.projectAreaInSqfts}"></c:out>
						</div>
						<div class="col-xs-3">
							<label style="font-weight: initial; font-weight: bold;">
							Unit </label>:
							<c:out value="${ viewProject.unit}"></c:out>
						</div>
						<div class="col-xs-3">
							<label style="font-weight: initial; font-weight: bold;">
								Start Date</label>:
							<c:out value="${ viewProject.startDate}"></c:out>

						</div>
						<div class="col-xs-3">
							<label style="font-weight: initial; font-weight: bold;">
								End Date</label>:
							<c:out value="${ viewProject.endDate}"></c:out>

						</div>
					</div>
				</div>
			</div>
		</div>
		<c:if test="${!empty projectBOLists}">
			<div class="text-center underline">
				<h3>Amenities</h3>
			</div>
			<div class="pi-responsive-table-sm">
				<div class="pi-section-w pi-section-white piTooltips">
					<display:table id="data" name="${projectBOLists}"
						requestURI="/project-tracker-status" pagesize="10" export="false"
						class="pi-table pi-table-complex pi-table-hovered pi-round pi-table-shadow pi-table-all-borders">
						<display:column property="amenities" title="Amenities" />
						<display:column property="nearByLocalities" title="NearBy" />
					</display:table>
				</div>
			</div>
		</c:if>
          

			<div class="text-center underline">
				<h3>Project Details</h3>
			</div>
			<div class="item">
				<form:form method="POST" id="addForm"
					action="project-tracker-status" modelAttribute="viewProject">
					<form:hidden path="projectId" />
					<div class="row">
						<div class="col-sm-4">
							<div class="form-group">
								<label>Amenities <span class="font10 text-danger">
										*</span></label>
								<form:input type="text" path="Amenities" class="form-control"
									id="dateId" />
									<form:errors path="Amenities" class="error"/>
							<div id="AmenitiesErr" style="color: red;"></div>
							</div>
							
						</div>

						<div class="col-sm-4">
							<div class="form-group">
								<label>NearBy <span class="font10 text-danger">
										*</span></label>
								<form:input type="text" path="nearByLocalities" class="form-control"
									id="nearByLocalitiesId" />
									<form:errors path="nearByLocalities" class="error"/>
							<div id="nearByLocalitiesErr" style="color: red;"></div>
									
							</div>
							
						</div>
					</div>
					<div class="row">
						<div class="col-md-12">
							<div style="text-align: right; margin-right: 31px;">
								<button type="submit" id="btnSubmit"
									class="btn btn-t-primary btn-theme lebal_align mt-20">Submit</button>
								<a href="view-project"><span
									class="btn btn-t-primary btn-theme lebal_align mt-20">Cancel</span></a>
							</div>
						</div>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</div>

 