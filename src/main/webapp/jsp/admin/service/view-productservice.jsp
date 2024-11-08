<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<link href="resources/theme/css/custom.css" rel="stylesheet">
<link href="resources/css/jquery-ui-1.10.4.custom.css" rel="stylesheet">
<script type="text/javascript" src="resources/js/jquery-ui.min.js"></script>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<html>
<head>
<meta charset="ISO-8859-1">
<title>List Of Products</title>
</head>

<script>
	$(document).ready(function() {
		$('#search').click(function(e) {
			var isValid = true;

			var name = $('#nameId').val();
			if (name == '') {
				isValid = false;
				$("#nameErr").show();
				$("#nameErr").html("Please enter First name ");
				$("#nameId").css({
					"border" : "1px solid red",

				});

			} else if (!/^[a-zA-Z\s]*$/g.test(userName)) {
				isValid = false;
				$("#nameErr").show();
				$("#nameErr").html("Please enter characters only");
				$("#nameId").css({
					"border" : "1px solid red",

				});

			} else {
				$('#nameErr').hide();
				$('#nameId').css({

					"border" : "",
					"background" : ""
				});
			}

			if (isValid == false)
				e.preventDefault();

		});
	});
</script>
<div class="warning">
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
			</div>
<div class="box-list">
	<div class="item">
		<div class="row ">
			<h3 class="text-center no-margin titleunderline underline"
				style="margin-top: -10px;">List Product</h3>
			<sec:authorize
				access="hasAnyRole('ROLE_COMPANY','ROLE_CREATE_PRODUCT')">
				<div class="row ">
					<a href="create-productservice"
						style="font-size: 26px; color: #7cb228; margin-left: 98%;"> <i
						class="fa fa-plus-circle" title="Create New Product"></i>
					</a>
				</div>
			</sec:authorize>
			<form:form id="myForm" method="post" class="login-form clearfix"
				action="search-productservice" commandName="searchProduct">
				<div class="row"
					style="border: 4px solid #e6e6e6; padding: 15px 15px 0px 15px; background-color: #e1e1e1">
					<div class=" col-md-4">
						<div class="form-group">
							<form:input type="ntext" class="form-control" path="serviceName"
								id="nameId" placeholder="Product Name " escapeXml="false"></form:input>
							<div id="nameErr" style="color: red;"></div>
						</div>
					</div>

					<div class=" col-md-3 fs-mobile-search">
						<div class="form-group ">
							<label class="hidden-xs"></label>
							<form:select type="text" path="productTypesbO.productTypes"
								class="form-control "
								style="height: 35px;font-weight: 500;
								   text-transform: capitalize;">
								<form:option value="">-- Select ProductType --   </form:option>
								<form:options items="${producttypesList}"
									itemLabel="productTypes" itemValue="productTypesId" />
							</form:select>
						</div>
					</div>

					<div class=" col-md-1" style="padding-bottom: 0px;">
						<div class="form-group">
							<button class="btn btn-theme btn-success btn-block" id="Search"
								style="background-color: #7cb228; border-color: #7cb228;">
								<small><i class="fa fa-search" aria-hidden="true"
									style="font-size: 20px;"></i></small>
							</button>
						</div>
					</div>
				</div>
			</form:form>
			<c:if test="${not empty searchproductcount}">
				<div class="col-sm-12" style="margin-top: 20px">
					<p>
						<a class="btn btn-theme btn-xs btn-default"
							style="color: #1b1818; font-weight: bold;"><c:out
								value="${searchproductcount}"></c:out></a> <strong class="color-black">Product
							Found</strong>
					</p>
				</div>
			</c:if>
			<c:if test="${not empty totalCountOfProduct}">

				<div class="col-sm-12" style="margin-top: 20px">
					<p>
						<a class="btn btn-theme btn-xs btn-default"
							style="color: #1b1818; font-weight: bold;"><c:out
								value="${totalCountOfProduct}"></c:out></a> <strong
							class="color-black">Product Found</strong>
					</p>
				</div>

			</c:if>


			<c:if test="${!empty serviceList}">
				<div class="col-sm-12">
					<div class="pi-responsive-table-sm">
						<div class="pi-section-w pi-section-white piTooltips">
							<display:table id="data" name="${serviceList.list}"
								requestURI="/view-productservice" export="false"
								class="pi-table pi-table-complex pi-table-hovered pi-round pi-table-shadow pi-table-all-borders">

								<display:column property="sNo" title="SNo" />
								<display:column property="serviceName" title="Product Name" />
								<%-- <display:column property="rupees" title="Fees" /> --%>
								<display:column property="minimumStocks" title="MinimumStocks" />
								<display:column property="maximumStocks" title="MaximumStocks" />
								<%-- 	<display:column property="availableStocks"
									title="AvailableStocks" /> --%>
								<display:column property="productTypesbO.productTypes"
									title="ProductTypes" />
								<sec:authorize
									access="hasAnyRole('ROLE_COMPANY','ROLE_UPDATE_PRODUCT')">
									<display:column url="edit-user" media="html" paramId="id"
										paramProperty="serviceId" title="Edit">
										<a href="edit-productservice?serviceId=${data.serviceId}"><i
											style="text-align: center;" class="fa fa-pencil"></i></a>
									</display:column>
								</sec:authorize>
								<sec:authorize
									access="hasAnyRole('ROLE_COMPANY','ROLE_DELETE_PRODUCT')">
									<display:column url="delete-user" media="html" paramId="id"
										paramProperty="serviceId" title="Delete">
										<a href="delete-productservice?serviceId=${data.serviceId}"
											onclick="return confirm('Are you sure you want to Delete?')"><i
											style="text-align: center;" class="fa fa-trash"></i></a>
									</display:column>
								</sec:authorize>
								<sec:authorize
									access="hasAnyRole('ROLE_COMPANY','ROLE_VIEW_PRODUCT')">
									<display:column url="customer-tracking-status" media="html"
										paramId="id" paramProperty="serviceId" title="View">
										<a href="product-tracking-status?serviceId=${data.serviceId}">
											<i style="text-align: center; color: black;"
											class="fa fa-eye"></i>
										</a>
									</display:column>
								</sec:authorize>
							</display:table>
						</div>
					</div>
				</div>
			</c:if>
			<nav style="text-align: center;">
				<ul class="pagination pagination-theme  no-margin center"
					style="margin-left: 575px;">
					<c:if test="${serviceList.currentPage gt 1}">
						<li><a href="view-productservice?page=1&searchElement=${searchElement}&searchProductType=${searchProductType}"><span><i
									class="fa fa-angle-double-left" aria-hidden="true"></i> </span></a></li>
						<li><a
							href="view-productservice?page=${serviceList.currentPage - 1}&searchElement=${searchElement}&searchProductType=${searchProductType}"><span><i
									class="fa fa-angle-left" aria-hidden="true"></i> </span></a></li>
					</c:if>
					<c:forEach items="${serviceList.noOfPages}" var="i">
						<c:choose>
							<c:when test="${serviceList.currentPage == i}">

								<li class="active"><a
									style="color: #fff; background-color: #34495e">${i}</a></li>
							</c:when>
							<c:otherwise>
								<li><a href="view-productservice?page=${i}&searchElement=${searchElement}&searchProductType=${searchProductType}">${i}</a></li>
							</c:otherwise>
						</c:choose>
					</c:forEach>
					<c:if test="${serviceList.currentPage lt serviceList.totalPages}">
						<li><a
							href="view-productservice?page=${serviceList.currentPage + 1}&searchElement=${searchElement}&searchProductType=${searchProductType}"><span><i
									class="fa fa-angle-right" aria-hidden="true"></i> </span></a></li>
						<li><a
							href="view-productservice?page=${serviceList.lastRecordValue}&searchElement=${searchElement}&searchProductType=${searchProductType}"><span><i
									class="fa fa-angle-double-right" aria-hidden="true"></i> </span></a></li>
					</c:if>
				</ul>
			</nav>
		</div>
	</div>
</div>

</html>