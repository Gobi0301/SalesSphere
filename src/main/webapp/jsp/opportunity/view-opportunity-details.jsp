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
<title>View Opportunity Detailsssssssssssssss</title>
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
				<c:if test="${not empty successMessage}">
					<div class="alert alert-success">
						<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
						<strong>Success:</strong>
						<c:out value="${successMessage}"></c:out>
					</div>
				</c:if>
				<c:if test="${not empty errorMessage}">
					<div class="alert alert-success">
						<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
						<strong>Success:</strong>
						<c:out value="${errorMessage}"></c:out>
					</div>
				</c:if>
				
				 
					<div class="item">
						<h3 class="text-center underline">List OpportunityDetails</h3>
						<div class="row">

							<h3 class="title">Personal Information</h3>
							<div class="item">
								<div class="desc list-capitalize">
									<div class="row clearfix" style="line-height: 2em;">
										<div class="col-xs-4">
											<div class="parent">
												<label>Salutation :</label> <span>${opportunityBO.salutation}</span>
											</div>
										</div>
										<div class="col-xs-4">
											<div class="parent">
												<label>First Name :</label> <span>${opportunityBO.firstName}</span>
											</div>
										</div>
										<div class="col-xs-4">
											<div class="parent">
												<label>Last Name :</label> <span>${opportunityBO.lastName}</span>
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
												<label>Account Name :</label> <span>${opportunityBO.accountBO.accountName}</span>
											</div>
										</div>
										<%-- <div class="col-xs-4">
							<div class="parent">
								<label>Lead Source :</label> <span>${profile.leads.leadsId}</span>
							</div>
						</div> --%>

										<div class="col-xs-4">
											<div class="parent">
												<label>Assigned To :</label> <span>${opportunityBO.user.name}</span>
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
												<label>Product :</label> <span>${opportunityBO.productService.serviceName}</span>
											</div>
										</div>
										<div class="col-xs-4">
											<div class="parent">
												<label>Amount :</label> <span>${opportunityBO.amount}</span>
											</div>
										</div>
										<div class="col-xs-4">
											<div class="parent">
												<label>Sales Stage :</label> <span>${opportunityBO.salesStage}</span>
											</div>
										</div>

									</div>
									<div class="row clearfix" style="line-height: 3em;">
										<div class="col-xs-4">
											<div class="parent">
												<label>Expected Closing Date :</label> <span>${opportunityBO.endTime}</span>
											</div>
										</div>
										<div class="col-xs-4">
											<div class="parent">
												<label>Next Step :</label> <span>${opportunityBO.nextStep}</span>
											</div>
										</div>
										<div class="col-xs-4">
											<div class="parent">
												<label>Probability :</label> <span>${opportunityBO.probability}
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
												<span>${opportunityBO.description}</span>
											</div>
										</div>
									</div>
								</div>
							</div>
							<c:if test="${!empty opportunityBOLists}">
				<div class="text-center underline">
					<h3>Opportunity Tracking History</h3>
				</div>
				<div class="pi-responsive-table-sm">
					<div class="pi-section-w pi-section-white piTooltips">
						<display:table id="data" name="${opportunityBOLists}"
							requestURI="/opportunity-tracking-status" pagesize="10"
							export="false"
							class="pi-table pi-table-complex pi-table-hovered pi-round pi-table-shadow pi-table-all-borders">
							<display:column property="convertedDate" title="Converted Date" />
							<display:column property="followupDate" title="Followup Date" />
							<display:column property="description" title="Description" />
							
						</display:table>
					</div>
				</div>
			</c:if>
			
			<!-- <div class="text-center underline">
			<h3>Update Opportunity Tracking Status</h3>
		</div> -->
		<<%-- div class="item">
			<form:form method="POST" id="addForm" action="opportunity-tracking-status"
				modelAttribute="profile">
				<div class="row">
					<div class="col-sm-4">
						<div class="form-group">

							<form:hidden path="opportunityId" />
							<label>Converted Date <span class="font10 text-danger">
									*</span></label>
							<form:input path="convertedDate" class="form-control" type="text" id="dateValId" 
							placeholder="ConvertedDate" />
						</div>
					</div>
					
					
					<div class="col-sm-4">
						<div class="form-group">
						
						<label>FollowUp Date <span class="font10 text-danger">
									*</span></label>
							<form:input path="followupDate" class="form-control" id="nextdateValId" 
							placeholder ="FollowUpDate" />
						</div>
					</div>
					<div class="col-sm-4">
								<div class="form-group">
									<label>From Slot <span class="font10 text-danger">
										*</span></label>
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
				</div>
			
				<div class="row">
				<div class="col-sm-4">
								<div class="form-group">
									<label>To Slot <span class="font10 text-danger">
										*</span></label>
									<form:select type="text" path="endTimeSlot" id="statusId"
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
							</ div>
					
					<div class="col-md-8">
						<label>Descriptions <span class="font10 text-danger">
								*</span></label>

						<form:textarea path="description" name="description"
							placeholder="Description" class="form-control rtext"
							id="descValId" maxlength="2000" rows="3"/>
						<div id="errorSkill" style="color: red;"></div>

					</div>
					
				</div>
							<div class="navigation">

								<a href="view-opportunities"><span
									class="btn btn-t-primary btn-theme lebal_align mt-20">Back</span></a>
							</div>
							</form:form>
						</div> --%>
					</div>

				</div>
			</div>
			<br>
		</section>

	</main>
</body>
</html>