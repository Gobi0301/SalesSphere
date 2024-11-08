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

.hideMe {
	display: none;
}

.actions {
	display: flex;
	position: relative;
	justify-content: space-evenly;
}
</style>

<div class="contact-form-wrapper" style="margin-top: 50px;">
	<div class="box-list">
	<div class="item">
			<div class="text-center underline">
				<h3>Invoice</h3>
			</div>
			<h3 class="title">Sales Information</h3>
			<input type="hidden" path="salesOrderId" />
			<div class="item">
				<div class="row">
					<div class="col-xs-4">
						<label style="font-weight: initial; font-weight: bold;">
							Serial No:</label>
						<c:out value="${accountBO.salesOrder}"></c:out>

					</div>
					<div class="col-xs-4">
						<label style="font-weight: initial; font-weight: bold;">
							Company:</label>
						<c:out value="SOFTTWIG Technology Solutions PVT Ltd"></c:out>

					</div>
					<div class="col-xs-4">
						<label style="font-weight: initial; font-weight: bold;">
							Address:</label>
						<c:out value="Chennai"></c:out>

					</div>
					<div class="col-xs-4">
						<label style="font-weight: initial; font-weight: bold;">
							Date:</label>
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
								Name:</label>
							<c:out value="${accountBO.accountName}"></c:out>

						</div>

						<div class="col-xs-4 hideMe">
							<label style="font-weight: initial; font-weight: bold;">
								SalesorderId:</label>
							<c:out value="${salesOrderId}"></c:out>

						</div>

						<div class="col-xs-4">
							<label style="font-weight: initial; font-weight: bold;">Email:</label>
							<c:out value="${accountBO.email}"></c:out>
						</div>

						<div class="col-xs-4">
							<label style="font-weight: initial; font-weight: bold;">Contact:</label>
							<c:out value="${accountBO.contactNo}"></c:out>
						</div>


						<div class="col-xs-4">
							<label style="font-weight: initial; font-weight: bold;">City:</label>
							<c:out value="${accountBO.city}"></c:out>
						</div>

						<div class="col-xs-4">
							<label style="font-weight: initial; font-weight: bold;">State:</label>
							<c:out value="${accountBO.state}">
							</c:out>
						</div>

						<div class="col-xs-4">
							<label style="font-weight: initial; font-weight: bold;">Country:</label>
							<c:out value="${accountBO.country}"></c:out>
						</div>
					</div>

				</div>

			</div>
			</br>





			<c:if test="${!empty particularSalesList}">
				<div class="text-center underline">
					<h3>Product Details</h3>
				</div>
				<div class="pi-responsive-table-sm">
					<div class="pi-section-w pi-section-white piTooltips">
						<display:table id="data" name="${particularSalesList}"
							requestURI="/leads-tracking-status" pagesize="10" export="false"
							class="pi-table pi-table-complex pi-table-hovered pi-round pi-table-shadow pi-table-all-borders">
							<display:column property="sNo" title="S No" />
							<display:column property="priceBookBo.priceBookName" title="Pricebook Name" />
							<display:column property="quantity" title="Quantity" />
							<display:column property="priceBookBo.price" title="Unit Price" />
							<display:column property="quantityPrice" title="Total" />


						</display:table>
						<div class="row">
							<div class="col-sm-2" style="float: right;">
								<input type="text" value="${grandTotal}" readonly="true"
									class="form-control required" placeholder="Total With Gst" />
							</div>
							<div class="col-sm-2" style="float: right; margin-right: -83px;">
								<label>GrandTotal:</label>
								<%-- <c:out value="${grandTotal}"> 
								</c:out>--%>
							</div>
						</div>
						<br>
						<%-- <div class="row">
							<div class="col-sm-2" style="float: right;">
								<input type="text" value="${sgstValue}" readonly="true"
									class="form-control required" placeholder="Total With Gst" />
							</div>
							<div class="col-sm-2" style="float: right;  margin-right: -83px;">
								<label >SGST:</label>
							</div>
						</div>

						</br>
						<div class="row">
                            <div class="col-sm-2" style="float: right;">
								<input type="text" value="${cgstValue}" readonly="true"
									class="form-control required" placeholder="Total With Gst" />
							</div>
							<div class="col-sm-2" style="float: right; margin-right: -83px;">
								<label style="font-weight: initial; font-weight: bold;">CGST:</label>
							</div>

						</div> --%>
						</br>
						<div class="row">
						<div class="col-sm-2" style="float: right;">
								<input type="text" value="${overAlltotal}" readonly="true"
									class="form-control required" placeholder="Total With Gst" />
							</div>
							<div class="col-sm-2" style="float: right; margin-right: -83px;">
								<label style="font-weight: initial; font-weight: bold;">Total
									with GST:</label>
							</div>
						</div>
					</div>

				</div>

				<h6>Disclaimer:Terms And Conditions Apply</h6>


			</c:if>


			<div class="row">
				<div class="col-sm-2" style="float: right; padding-bottom: 38px;">
					<div class="actions">
					<c:if test="${paymentStatus == false }">
						<div class="payment">
							<a href=paymentmode?salesno=${salesno}><span
								class="btn btn-t-primary btn-theme lebal_align">Pay Now</span></a>
						</div>
						</c:if>
						<c:if test="${paymentStatus == true }">
						<div class="payment">
							<a ><span
								class="btn btn-t-primary btn-theme lebal_align"  >Paid</span></a>
						</div>
						</c:if>
						<div class="cancel">
							<a href="view-sales-order"><span
								class="btn btn-t-primary btn-theme lebal_align">Cancel</span></a>


						</div>

					</div>

				</div>
			</div>
		</div>
		<br>
	</div>