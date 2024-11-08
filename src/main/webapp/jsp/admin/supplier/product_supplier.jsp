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

			<form:form method="POST" id="addForm" action="product_supplier"
					modelAttribute="SupplierBO">
			<form:hidden path="supplierId" name="supplierId"
						value="${SupplierBO.supplierId}" />
				<div class="box-list">
						<div class="item">
							<div class="row ">
                                 <h3 class="title">Product Information</h3> 
								<div class="col-sm-12">

									<div class="col-sm-4">
										<div class="form-group">
											<label>Product Name<span class="font10 text-danger">*</span></label>
											<form:select type="text" path="productServiceBO.serviceName" id="serviceid"
												class="form-control required">
												<form:option value="${SupplierBO.productServiceBO.serviceId}">${SupplierBO.productServiceBO.serviceName}</form:option>

												<form:options items="${productList}" itemLabel="serviceName"
													itemValue="serviceId" />
											</form:select>
											<form:errors path="productServiceBO.serviceName" class="error" />
											<div id="serviceNameErr" style="color: red;"></div>
							
										</div>
									</div>
									
									<%-- <div class="col-sm-4">
										<div class="form-group">
											<label>Product Name<span class="font10 text-danger">*</span></label>
											<form:select type="text" path="productServiceBO.serviceName"
												id="serviceid" onchange="productProfile(serviceid)"
												class="form-control required">

												<form:option value="select">-- Select --</form:option>
												<form:options items="${productlist}" itemLabel="serviceName"
													itemValue="serviceId" />
											</form:select>
											<form:errors path="productServiceBO.serviceName" class="error" />
													<div id="serviceNameErr" style="color: red;"></div>
										</div>
									</div> --%>
									
									<div class="col-sm-4">
										<div class="form-group">
											<label> Technical Oriented <span
												class="font10 text-danger">*</span></label>
											<form:input type="text" path="techOriented"
												id="techOrientedId" class="form-control required"
												placeholder="techOriented" />
											<form:errors path="techOriented" class="error" />
											<div id="techOrientedErr" style="color: red;"></div>
										</div>
									</div>
									<div class="col-sm-4">
										<div class="form-group">
											<label> FinancialAmount <span
												class="font10 text-danger">*</span></label>
											<form:input type="text" path="financialAmount"
												id="financialAmountId" class="form-control required"
												placeholder="finanamount" />
											<form:errors path="financialAmount" class="error" />
											<div id="financialAmountErr" style="color: red;"></div>
										</div>
									</div>
								</div>
								<div class="col-sm-12">
									<div class="col-sm-4">
										<div class="form-group">
											<label> Rating <span
												class="font10 text-danger">*</span></label>
											<form:select type="text" path="rating" id="ratingId"
												class="form-control required" placeholder="rating">
												<form:option value="">Select Rating</form:option>
												<form:option value="1">*</form:option>
												<form:option value="2">**</form:option>
												<form:option value="3">***</form:option>
												<form:option value="4">****</form:option>
												<form:option value="5">*****</form:option>
												</form:select>
											<form:errors path="rating" class="error" />
											<div id="ratingErr" style="color: red;"></div>

										</div>
									</div>


								</div>

							</div>
							<div style="text-align: right; margin-right: 31px">
								<button type="submit" id="btnSubmit"
									class="btn btn-t-primary btn-theme lebal_align mt-20">Submit</button>
								<a href="admin-home"><span
									class="btn btn-t-primary btn-theme lebal_align mt-20">Cancel</span></a>
							</div>
						</div>
					</div>
					</form:form>

</html>