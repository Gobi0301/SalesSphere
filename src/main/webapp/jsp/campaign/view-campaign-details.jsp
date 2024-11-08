<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<div class="contact-form-wrapper" style="margin-top: 50px;">
	<div class="box-list">
	<c:if test="${not empty successMessage}">
				<div class="alert alert-success" role="alert"
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
				<div class="alert alert-success">
					<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
					<strong>Success:</strong>
					<c:out value="${errorMessage}"></c:out>
				</div>
			</c:if>
			<c:if test="${not empty infoMessagemessage}">
				<div class="alert alert-info" role="alert"
					style="font-size: 12px; padding: 8px 9px 5px 10px; margin-top: 15px;">
					<button type="button" class="close" data-dismiss="alert"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<strong>Info!</strong>
					<c:out value="${infoMessagemessage}"></c:out>
				</div>
			</c:if>
		<div class="item">
			<div class="text-center underline">
				<h3>View Campaign Details</h3>
			</div>
			<div class="item" >

				<div class="desc list-capitalize">
					<div class="row clearfix" style="line-height: 2em;">

						<div class="box-list">
							<div class="item">
								<div class="row">
									<div class="row">
										<h3>Campaign Information</h3>
									</div>
									<div class="col-xs-4">
										<label style="font-weight: initial; font-weight: bold;">
											Campaign
											Name:</label>
										<c:out value="${campaignlists.campaignName}"></c:out>
									</div>
									<div class="col-xs-4">
										<label style="font-weight: initial; font-weight: bold;">Campaign
											Owner:</label>
										<c:out value="${campaignlists.campaignOwner}"></c:out>
									</div>
									<div class="col-xs-4">
										<label style="font-weight: initial; font-weight: bold;">Campaign
											Mode:</label>
										<c:out value="${campaignlists.campaignMode}"></c:out>
									</div>
									<div class="col-xs-4">
										<label style="font-weight: initial; font-weight: bold;">
											Product
											Name:</label>
										<c:out value="${campaignlists.productServiceBO.serviceName}"></c:out>
									</div>
									<div class="col-xs-4">
										<label style="font-weight: initial; font-weight: bold;">Status:</label>
										<c:out value="${campaignlists.status}"></c:out>
									</div>
								</div>
							</div>
						</div>
						<br>
						<div class="box-list">
							<div class="item">
								<div class="row">
									<div class="row">
										<h3>Additional Information</h3>
									</div>
									<div class="col-xs-4">
										<label style="font-weight: initial; font-weight: bold;">Number
											Sent:</label>
										<c:out value="${campaignlists.numberSent}"></c:out>
									</div>

									<div class="col-xs-4">
										<label style="font-weight: initial; font-weight: bold;">Started
											Time:</label>
										<c:out value="${campaignlists.startedTime}"></c:out>
									</div>

									<div class="col-xs-4">
										<label style="font-weight: initial; font-weight: bold;">End
											Time:</label>
										<c:out value="${campaignlists.endTime}"></c:out>
									</div>

									<div class="col-xs-4">
										<label style="font-weight: initial; font-weight: bold;">Expected
											Revenue:</label>
										<c:out value="${campaignlists.expectedRevenue}"></c:out>
									</div>

									<div class="col-xs-4">
										<label style="font-weight: initial; font-weight: bold;">Budgeted
											Cost:</label>
										<c:out value="${campaignlists.budgetedCost}"></c:out>
									</div>

									<div class="col-xs-4">
										<label style="font-weight: initial; font-weight: bold;">Expected
											Response:</label>
										<c:out value="${campaignlists.expectedResponse}"></c:out>
									</div>



									<div class="col-xs-4">
										<label style="font-weight: initial; font-weight: bold;">Description:</label>
										<c:out value="${campaignlists.description}"></c:out>
									</div>
								</div>
							</div>
						</div>

					</div>
				</div>
			</div>
			<br>
			<div style="text-align: right; margin-right: 31px">
				<a href="view-campaign?page=1"><span
					class="btn btn-t-primary btn-theme lebal_align mt-20">Back</span></a>
			</div>

		</div>
	</div>
</div>
<br>