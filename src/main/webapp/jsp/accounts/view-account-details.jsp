<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

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

<div class="contact-form-wrapper" style="margin-top: 50px;">
<br><br>
	<div class="box-list">
		<div class="item">
			<div class="text-center underline">
				<h3>Account Details</h3>
			</div>
			<div class="row">
			<h3 class="title">Account Information</h3>
			</div>
			<div class="item" >

				<div class="desc list-capitalize">
					<div class="row clearfix" style="line-height: 2em;">

						<div class="col-xs-4">
							<label style="font-weight: initial; font-weight: bold;">
								Account Name
								:</label>
							<c:out value="${accountdetails.accountName}"></c:out>
						</div>

						<div class="col-xs-4">
							<label style="font-weight: initial; font-weight: bold;">Account
								Owner:</label>
							<c:out value="${accountdetails.accountOwner}"></c:out>
						</div>

						<div class="col-xs-4">
							<label style="font-weight: initial; font-weight: bold;">Parent
								Account
								:</label>
							<c:out value="${accountdetails.parentAccount}"></c:out>
						</div>

						<div class="col-xs-4">
							<label style="font-weight: initial; font-weight: bold;">
								Assigned
								To:</label>
							<c:out value="${accountdetails.user.name}">
							</c:out>
						</div>

						<div class="col-xs-4">
							<label style="font-weight: initial; font-weight: bold;">
								Contact
								No:</label>
							<c:out value="${accountdetails.phone}"></c:out>
						</div>
					</div>
				</div>
			</div>
			<div class="row ">
									<h3 class="title">Additional Information</h3>
								</div>

			<div class="item" >
				<div class="desc list-capitalize">
					<div class="row clearfix" style="line-height: 2em;">
						<div class="col-xs-4">
							<label style="font-weight: initial; font-weight: bold;">
								Type:</label>
							<c:out value="${accountdetails.type}"></c:out>
						</div>

						<div class="col-xs-4">
							<label style="font-weight: initial; font-weight: bold;">Industry:</label>
							<c:out value="${accountdetails.industry}"></c:out>
						</div>

						<div class="col-xs-4">
							<label style="font-weight: initial; font-weight: bold;">No
								Of
								Employess:</label>
							<c:out value="${accountdetails.numberOfEmployees}"></c:out>
						</div>


						<div class="col-xs-4">
							<label style="font-weight: initial; font-weight: bold;">Street
								:</label>
							<c:out value="${accountdetails.billingStreet}"></c:out>
						</div>

						<div class="col-xs-4">
							<label style="font-weight: initial; font-weight: bold;">City
								:</label>
							<c:out value="${accountdetails.billingCity}"></c:out>
						</div>

						<div class="col-xs-4">
							<label style="font-weight: initial; font-weight: bold;">State
								:</label>
							<c:out value="${accountdetails.billingState}"></c:out>
						</div>

						<div class="col-xs-4">
							<label style="font-weight: initial; font-weight: bold;">Country
								:</label>
							<c:out value="${accountdetails.billingCountry}"></c:out>
						</div>

						<div class="col-xs-4">
							<label style="font-weight: initial; font-weight: bold;">Postal
								Code
								:</label>
							<c:out value="${accountdetails.billingPostalCode}"></c:out>
						</div>

						<div class="col-xs-4">
							<label style="font-weight: initial; font-weight: bold;">Country
								Code
								:</label>
							<c:out value="${accountdetails.billingCountryCode}"></c:out>
						</div>

					</div>
				</div>
			</div>

			<div class="row ">
									<h3 class="title">Description Information</h3>
								</div>
			<div class="item" >

				<div class="desc list-capitalize">
					<div class="row clearfix" style="line-height: 2em;">
						<div class="col-xs-4">
							<label style="font-weight: initial; font-weight: bold;">
								Description
								:</label>
							<c:out value="${accountdetails.description}"></c:out>

						</div>
					</div>
				</div>
			</div>
			<br>
			<div style="text-align: right; margin-right: 31px">
				<a href="view-accounts?page=1"><span
					class="btn btn-t-primary btn-theme lebal_align mt-20">Back</span></a>
			</div>
		</div>
	</div>
	<br><br>
</div>
