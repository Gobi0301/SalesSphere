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
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
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
	<section class="container instructor-profile-block">
		<div class="contact-form-wrapper" style="margin-top: 50px;">
			<div class="box-list">
				<div class="item">
					<div class="text-center underline">
						<h3 >Products Details</h3>
					</div>
					<div class="row">

						<h3 class="title">Products Details</h3>

						<div class="item" >

							<div class="desc list-capitalize">
								<div class="row clearfix" style="line-height: 2em;">
									<div class="col-xs-4">
										<label style="font-weight: initial; font-weight: bold;">
											serviceName</label>:
										<c:out value="${viewProducts.serviceName}"></c:out>
									</div>
									<div class="col-xs-4">
										<label style="font-weight: initial; font-weight: bold;">
											serviceSpecification</label>:
										<c:out value="${viewProducts.serviceSpecification}"></c:out>
									</div>
									<div class="col-xs-4">
										<label style="font-weight: initial; font-weight: bold;">
											startDate</label>:
										<c:out value="${viewProducts.startDate}"></c:out>
									</div>
									<div class="col-xs-4">
										<label style="font-weight: initial; font-weight: bold;">
											endDate</label>:
										<c:out value="${viewProducts.endDate}"></c:out>
									</div>
									<div class="col-xs-4">
										<label style="font-weight: initial; font-weight: bold;">
											MinimumStocks</label>:
										<c:out value="${viewProducts.minimumStocks}"></c:out>
									</div>
									<div class="col-xs-4">
										<label style="font-weight: initial; font-weight: bold;">
											MaximumStocks</label>:
										<c:out value="${viewProducts.maximumStocks}"></c:out>
									</div>
									<div class="col-xs-4">
										<label style="font-weight: initial; font-weight: bold;">
											AvailableStocks</label>:
										<c:out value="${viewProducts.availableStocks}"></c:out>
									</div>
									<div class="col-xs-4">
										<label style="font-weight: initial; font-weight: bold;">
											productTypes</label>:
										<c:out value="${viewProducts.productTypesbO.productTypes}"></c:out>
									</div>
									<br> <br>


								</div>
								
							</div>
						</div>
						<h3 class="title">Products Extra Details </h3>
						<div class="item" >
						
							<div >
								<label>Campaign Product Details</label>:
										<b><a href="view-campaign?serviceId=${viewProducts.serviceId}" target="_blank" 
										style="color: red;">Click here</a></b>
							</div>
						<br>
						
							<div class="parent">
								<label>Supplier Product Details</label>: 
								<b><a href="view_supplier?serviceId=${viewProducts.serviceId}" target="_blank" 
										style="color: red;">Click here</a></b>
							</div>
					<br>
						
							<div class="parent">
								<label>Procurement Product Details</label>: <span> </span>
							<b><a href="view-procurement?serviceId=1" target="_blank" 
										style="color: red;">Click here</a></b>
						</div>
					
						
					</div>
					</div>
					<br>
					<div class="navigation" style="padding-left: 1100px;">

						<a onclick="history.go(-1)"><span
							class="btn btn-t-primary btn-theme lebal_align mt-20">Back</span></a>
					</div>
				</div>
			</div>
		</div>
	</section>

</body>
</html>