<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<div class="contact-form-wrapper" style="margin-top: 50px;">
	<div class="box-list">
		<div class="item">
			<div class="text-center underline">
				<h3>List Company</h3>
			</div>
			<div class="item" style="background-color: #e1e1e1">

				<div class="desc list-capitalize">
					<div class="row clearfix" style="line-height: 2em;">


						<br>
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
								<div class="row">
									<div class="row">
										<h3>Company Information</h3>
									</div>
									<div class="col-xs-4">
										<label style="font-weight: initial; font-weight: bold;">
											Company
											Name&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>:
										<c:out value="${companyList.companyName}"></c:out>
									</div>
									<div class="col-xs-4">
										<label style="font-weight: initial; font-weight: bold;">Contact
											Person&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>:
										<c:out value="${companyList.contactPerson}"></c:out> 
									</div>
									<div class="col-xs-4">
										<label style="font-weight: initial; font-weight: bold;">Company
											GstNo&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>:
										<c:out value="${companyList.companyGstNo}"></c:out>
									</div>
									<div class="col-xs-4">
										<label style="font-weight: initial; font-weight: bold;">Company
											Email&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>:
										<c:out value="${companyList.companyEmail}"></c:out>
									</div>

									<div class="col-xs-4">
										<label style="font-weight: initial; font-weight: bold;">
											Website
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>:
										<c:out value="${companyList.website}"></c:out>
									</div>

									<div class="col-xs-4">
										<label style="font-weight: initial; font-weight: bold;">
											contactNumber&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>:
										<c:out value="${companyList.contactNumber}"></c:out>
									</div>

									<div class="col-xs-4">
										<label style="font-weight: initial; font-weight: bold;">
											MobileNumber&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>:
										<c:out value="${companyList.mobileNumber}"></c:out>
									</div>


									<div class="col-xs-4">
										<label style="font-weight: initial; font-weight: bold;">
											IdustryType&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>:
										<c:out value="${companyList.industryType}"></c:out>
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
										<label style="font-weight: initial; font-weight: bold;">
											Street&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>:
										<c:out value="${companyList.street}"></c:out>
									</div>

									<div class="col-xs-4">
										<label style="font-weight: initial; font-weight: bold;">
											City
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>:
										<c:out value="${companyList.city}"></c:out>
									</div>

									<div class="col-xs-4">
										<label style="font-weight: initial; font-weight: bold;">
											District&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>:
										<c:out value="${companyList.district}"></c:out>
									</div>

									<div class="col-xs-4">
										<label style="font-weight: initial; font-weight: bold;">
											State&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>:
										<c:out value="${companyList.state}"></c:out>
									</div>

									<div class="col-xs-4">
										<label style="font-weight: initial; font-weight: bold;">
											Country&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>:
										<c:out value="${companyList.country}"></c:out>
									</div>

									<div class="col-xs-4">
										<label style="font-weight: initial; font-weight: bold;">
											PostalCode&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>:
										<c:out value="${companyList.postalCode}"></c:out>
									</div>
								</div>
							</div>
						</div>
						<br> <br>
						<div style="text-align: right; margin-right: 31px">
							<a href="view-company?page=1"><span
								class="btn btn-t-primary btn-theme lebal_align mt-20">Back</span></a>
						</div>

					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<br>