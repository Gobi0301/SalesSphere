<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<link href="resources/theme/css/custom.css" rel="stylesheet">
<link href="resources/css/jquery-ui-1.10.4.custom.css" rel="stylesheet">
<script type="text/javascript" src="resources/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="http://js.nicedit.com/nicEdit-latest.js"></script>

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
				<h3>Supplier Details</h3>
			</div>
			<h3 class="title">Supplier Information</h3>
			<div class="row ">
				<a href="supplier_product?supplierId=${viewsupplier.supplierId}"
					style="font-size: 26px; color: #7cb228; margin-left: 95%;">
					<i class="fa fa-plus-circle" title="Create New product"></i>
				</a>
			</div>
			
			<div class="item" style="background-color: #e1e1e1">
				<div class="desc list-capitalize">
					<div class="row clearfix" style="line-height: 2em;">
						<div class="col-xs-4">
							<label style="font-weight: initial; font-weight: bold;">
								Supplier Name</label>:
							<c:out value="${viewsupplier.supplierName}"></c:out>
						</div>
						
						<div class="col-xs-4">
							<label style="font-weight: initial; font-weight: bold;">
								EmailId</label>:
							<c:out value="${viewsupplier.emailId}"></c:out>
						</div>
						
						<div class="col-xs-4">
							<label style="font-weight: initial; font-weight: bold;">
								Mobile NO</label>:
							<c:out value="${viewsupplier.mobileNo}"></c:out>
						</div>
						
						<div class="col-xs-4">
							<label style="font-weight: initial; font-weight: bold;">
								Address</label>:
							<c:out value="${viewsupplier.address}"></c:out>
						</div>
						
						<div class="col-xs-4">
							<label style="font-weight: initial; font-weight: bold;">
								City</label>:
							<c:out value="${viewsupplier.city}"></c:out>
						</div>
						
						<div class="col-xs-4">
							<label style="font-weight: initial; font-weight: bold;">
								State</label>:
							<c:out value="${viewsupplier.state}"></c:out>
						</div>
						<div class="col-xs-4">
							<label style="font-weight: initial; font-weight: bold;">
								Country</label>:
							<c:out value="${viewsupplier.country}"></c:out>
						</div>
						<div class="col-xs-4">
							<label style="font-weight: initial; font-weight: bold;">
								Location</label>:
							<c:out value="${viewsupplier.location}"></c:out>
						</div>
						<div class="col-xs-4">
							<label style="font-weight: initial; font-weight: bold;">
								Web Site</label>:
							<c:out value="${viewsupplier.webSite}"></c:out>
						</div>
					</div>
				</div>
			</div>
             <br><br>
			<c:if test="${!empty viewsupplier.supplierProductBOList}">
				<h3 class="text-center no-margin titleunderline underline"
					style="margin-top: -10px;">Product Information</h3>
				<div class="col-sm-12">
					<div class="pi-responsive-table-sm">
						<div class="pi-section-w pi-section-white piTooltips">
							<display:table id="data" name="${viewsupplier.supplierProductBOList}"
								requestURI="/view_supplier" export="false"
								class="pi-table pi-table-complex pi-table-hovered pi-round pi-table-shadow pi-table-all-borders">
								
								<display:column title="SNo" media="html">
							    <c:out value="${data_rowNum}"></c:out>
								</display:column>
								<display:column property="serviceName" title="Product Name" />
								<display:column property="techOriented" title="Tech Oriented" />
								<display:column property="buyingPrice" title="Buying Price" />
							
							     </display:table>
						</div>
					</div>
				</div>
			</c:if>
			

			<div style="text-align: right; margin-right: 31px">
				<a href="view_supplier"><span class="btn btn-t-primary btn-theme lebal_align mt-21">Back</span></a>
			</div>
			<br>
		</div>
	</div>
</div>

