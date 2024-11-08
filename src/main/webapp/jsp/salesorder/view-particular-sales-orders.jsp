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
.title {
	background-color: #2a3f54;
	padding: 10px;
	color: #fff;
}
</style>

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
				<div class="alert alert-info">
					<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
					<strong>Info:</strong>
					<c:out value="${errorMessage}"></c:out>
				</div>
			</c:if>
		<div class="item">
			<div class="text-center underline">
				<h3>Invoice</h3>
			</div>
			<h3 class="title"> Sales Information</h3>
			
			
			<div class="item">
				<div class="row">
					<div class="col-xs-4">
						<label style="font-weight: initial; font-weight: bold;">
							Sales Order No</label>:
						<c:out value="${salesNo}"></c:out>

					</div>
					<div class="col-xs-4">
						<label style="font-weight: initial; font-weight: bold;">
							Company</label>:
						<c:out value="SOFTTWIG Technology Solutions PVT Ltd"></c:out>

					</div>
					<div class="col-xs-4">
						<label style="font-weight: initial; font-weight: bold;">
							Address</label>:
						<c:out value="Chennai"></c:out>

					</div>
					<div class="col-xs-4">
						<label style="font-weight: initial; font-weight: bold;">
							Date</label>:
						<c:out value="${date}"></c:out>

					</div>

				</div>
			</div>
			<h3 class="title">Account Information</h3>


			<div class="item">

				<div class="desc list-capitalize">
					<div class="row clearfix" style="line-height: 2em;">


						<div class="col-xs-4">
							<label style="font-weight: initial; font-weight: bold;">
								Name</label>:
							<c:out value="${accountBO.accountName}"></c:out>
						</div>
						<div class="col-xs-4">
							<label style="font-weight: initial; font-weight: bold;">Email</label>:
							<c:out value="${accountBO.email}"></c:out>
						</div>
						<div class="col-xs-4">
							<label style="font-weight: initial; font-weight: bold;">Contact</label>:
							<c:out value="${accountBO.contactNo}"></c:out>
						</div>
						<div class="col-xs-4">
							<label style="font-weight: initial; font-weight: bold;">City</label>:
							<c:out value="${accountBO.city}"></c:out>
						</div>
						<div class="col-xs-4">
							<label style="font-weight: initial; font-weight: bold;">State</label>:
							<c:out value="${accountBO.state}">
							</c:out>
						</div>

						<div class="col-xs-4">
							<label style="font-weight: initial; font-weight: bold;">Country</label>:
							<c:out value="${accountBO.country}"></c:out>
						</div>
					</div>

				</div>

			</div>


			<c:if test="${!empty particularSalesList}">
				<div class="text-center underline">
					<h3>Price Details</h3>
				</div>
				<div class="pi-responsive-table-sm">
					<div class="pi-section-w pi-section-white piTooltips">
						<display:table id="data" name="${particularSalesList}"
							requestURI="/leads-tracking-status" pagesize="10" export="false"
							class="pi-table pi-table-complex pi-table-hovered pi-round pi-table-shadow pi-table-all-borders">
							<display:column property="sNo" title="S No" />
							<%-- <display:column property="salesOrderNo" title="Sales Order No" /> --%>
					<display:column property="priceBookBo.priceBookName" title="Pricebook Name" />
									<display:column property="quantity" title="Quantity" />
								<display:column property="priceBookBo.price" title="Unit Price" />
							<%-- 		<display:column property="quantityPrice" title="Total" /> --%>
							

						</display:table>
						<div class="row">

							<div class="col-sm-2" style="float: right;">
								<input type="text" value="${grandTotal}" readonly="true"
								class=	"form-control required" placeholder="Grand Total" />
							</div>
							<div class="col-sm-2" style="float: right; margin-right: -83px;">
								<!-- <div class="form-group"> -->
								<label>Grand Total</label>

								<!-- </div> -->
							</div>

						</div>
						</br>
				<%-- 		<div class="row">

							<div class="col-sm-2" style="float: right;">
								<input type="text" value="${sgstValue}" readonly="true"
									class="form-control required" placeholder="SGST" />
							</div>
							<div class="col-sm-2" style="float: right; margin-right: -83px;">
								<!-- <div class="form-group"> -->
								<label>SGST</label> 

								<!-- </div> -->
							</div>

						</div>

						</br>
						<div class="row">

							<div class="col-sm-2" style="float: right;">
								<input type="text" value="${cgstValue}" readonly="true"
									class="form-control required" placeholder="CGST" />
							</div>
							<div class="col-sm-2" style="float: right; margin-right: -83px;">
								<!-- <div class="form-group"> -->
								<label>CGST</label> 

								<!-- </div> -->
							</div>

						</div> --%>
						</br>
						<div class="row">

							<div class="col-sm-2" style="float: right;">
								<input type="text" value="${overAlltotal}" readonly="true"
									class="form-control required" placeholder="Total With Gst" />
							</div>
							<div class="col-sm-2" style="float: right; margin-right: -83px;">
								<!-- <div class="form-group"> -->
								<label>Total With Gst</label>

								<!-- </div> -->
							</div>

						</div>


					</div>

				</div>

				<h6>Disclaimer:Terms And Condtions Apply</h6>

				<div style="float: right; margin: -14px 5px;">
					<c:if test="${!empty invoiceName}">
						<a href="download-invoice?salesId=${salesId}"><span
							class="btn btn-t-primary btn-theme lebal_align mt-20">Download
								Invoice</span></a>
					</c:if>
					<c:if test="${empty invoiceName}">
						<a href="generate-invoice?salesNo=${salesId}"><span
							class="btn btn-t-primary btn-theme lebal_align mt-20">Generate
								Invoice</span></a>
					</c:if>
					<a href="view-sales-order"><span
						class="btn btn-t-primary btn-theme lebal_align mt-20">Back</span></a>
				</div>
			</c:if>
		</div>
		</br>
	</div>
</div>

