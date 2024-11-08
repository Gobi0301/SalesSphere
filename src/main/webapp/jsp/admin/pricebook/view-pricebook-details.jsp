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

<br>
<br>
<br>
<div class="contact-form-wrapper" style="margin-top: 50px;">
	<div class="box-list">
		<div class="item">
			<div class="text-center underline">
				<h3>PriceBook Details</h3>
			</div>
			<!-- <div class="row ">
				<h3 class="title">PriceBook Information</h3>
			</div> -->
			<h3 class="title"> List Pricebook</h3>
			<div class="item">

				<div class="desc list-capitalize">
					<div class="row clearfix" style="line-height: 2em;">
						<div class="col-xs-6">
							<label style="font-weight: initial; font-weight: bold;">
								priceBook Owner :</label>
							<c:out value="${priceBookVO.user.name}"></c:out>

						</div>

						<div class="col-xs-6">
							<label style="font-weight: initial; font-weight: bold;">priceBook
								Name :</label>
							<c:out value="${priceBookVO.priceBookName}"></c:out>
						</div>
					</div>
					<%-- <div class="row">
						<div class="col-xs-4">
							<label style="font-weight: initial; font-weight: bold;">description:</label>
							<c:out value="${priceBookVO.description}"></c:out>
						</div>
					</div> --%>
				</div>
			</div>

			<h3 class="title">Products Details</h3>
			<div class="item">

				<div class="desc list-capitalize">
					<div class="row clearfix" style="line-height: 2em;">
						<div class="col-xs-6">
							<label style="font-weight: initial; font-weight: bold;">
								product Name :</label>
							<c:out value="${priceBookVO.productservicevo.serviceName}"></c:out>

						</div>

						<div class="col-xs-6">
							<label style="font-weight: initial; font-weight: bold;">Supplier
								Name :</label>
							<c:out value="${priceBookVO.suppliervo.supplierName}"></c:out>
						</div>
						<div class="col-xs-6">
							<label style="font-weight: initial; font-weight: bold;">Final
								Amount :</label>
							<c:out value="${priceBookVO.price}"></c:out>
						</div>
					</div>

				</div>
			</div>
			<br>
			<div class="item">
				<div class="desc list-capitalize">
					<div class="row clearfix" style="line-height: 2em;">
						<div class="row">
							<div class="col-xs-4">
								<label style="font-weight: initial; font-weight: bold;">description:</label>
								<c:out value="${priceBookVO.description}"></c:out>
							</div>
						</div>
					</div>
				</div>
			</div>
			<br>
			<div style="text-align: right; margin-right: 31px">
				<a href="view-pricebook?page=1"><span
					class="btn btn-t-primary btn-theme lebal_align mt-20">Back</span></a>
			</div>

		</div>
	</div>
</div>

