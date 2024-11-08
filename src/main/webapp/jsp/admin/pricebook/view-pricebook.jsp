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
<title>Insert title here</title>
</head>
<!-- <!-- <script>
	$(document).ready(function() {

		/* $('#emailAddressId').focus(); */

		$('#submit').click(function(e) {

			//Search Name Validation
			var isValid = true;
			var priceBookName = $('#priceBookName').val();
			if (priceBookName == '') {
				isValid = false;
				$("#priceBookNameErr").show();
				$("#priceBookNameErr").html("Please Enter priceBookName");
				$("#priceBookName").css({
					"border" : "1px solid red",
				});
			} else {
				$('#priceBookNameErr').hide();
				$('#priceBookName').css({
					"border" : "",
					"background" : ""
				});
			}

			if (isValid == false)
				e.preventDefault();
		});
	});
</script> --> -->
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
</div>
<div class="box-list">
	<div class="item">
		<div class="row ">
			<h3 class="text-center no-margin titleunderline underline"
				style="margin-top: -10px;">List PriceBook</h3>
			<sec:authorize
				access="hasAnyRole('ROLE_COMPANY','ROLE_CREATE_PRICEBOOK')">
				<div class="row ">
					<a href="create-pricebook"
						style="font-size: 26px; color: #7cb228; margin-left: 97%;"> <i
						class="fa fa-plus-circle" title="Create New Pricebook"></i>
					</a>
				</div>
			</sec:authorize>



			<!--Start form search -->
			<form:form id="myForm" name="pageNo" method="post"
				class="login-form clearfix" action="search-pricebook"
				modelAttribute="searchPrice">
				<div class="row"
					style="border: 4px solid #e6e6e6; margin: 15px 15px 15px 15px; background-color: #e1e1e1">

					<div class="col-sm-12">
						<div class="col-sm-4">
							<div class="form-group">
								<label class="hidden-xs"></label>
								<form:input type="text" id="priceBookName" path="priceBookName"
									class="form-control required" placeholder="PriceBook Name"
									maxlength="150" style="margin-top: 10px;" />
								<form:errors path="priceBookName" class="error" />
								<div id="priceBookNameErr" style="color: red;"></div>
							</div>
						</div>

						<%--  <div class="col-sm-3">
										<div class="form-group">
											<label>PriceBook Owner</label>
											<form:select type="text" 
												path="id" placeholder="Select Owner"
												class="form-control required">
												
												<form:option value=""> Select </form:option>
												<form:options items="${priceBo}" itemLabel="name"
													itemValue="id"></form:options>
											
											</form:select>
												<form:errors path="id" class="error" />
											<div id="idErr" style="color: red;"></div>
										</div>
									</div>  --%>





						<div class=" col-md-1" style="padding-bottom: 0px;">
							<div class="form-group home-right">
								<label class="hidden-xs"></label>
								<button class="btn btn-theme btn-success btn-block" id="submit"
									style="padding: 6px 5px; margin-top: 13px; background-color: #7cb228; border-color: #7cb228;">
									<small><i class="fa fa-search" aria-hidden="true"
										style="font-size: 20px;"></i></small>
								</button>
							</div>
						</div>
					</div>
				</div>
			</form:form>
			<c:if test="${not empty totalpricebookcount}">

				<div class="col-sm-12" style="margin-top: 20px">
					<p>
						<a class="btn btn-theme btn-xs btn-default"
							style="color: #1b1818; font-weight: bold;"><c:out
								value="${totalpricebookcount}"></c:out></a> <strong
							class="color-black">Pricebook Found</strong>
					</p>
				</div>

			</c:if>
			<c:if test="${not empty searchpricebookcount}">

				<div class="col-sm-12" style="margin-top: 20px">
					<p>
						<a class="btn btn-theme btn-xs btn-default"
							style="color: #1b1818; font-weight: bold;"><c:out
								value="${searchpricebookcount}"></c:out></a> <strong
							class="color-black">Pricebook Found</strong>
					</p>
				</div>

			</c:if>

			<c:if test="${!empty pricebook}">
				<div class="col-sm-12">
					<div class="pi-responsive-table-sm">
						<div class="pi-section-w pi-section-white piTooltips">
							<display:table id="data" name="${pricebook.list}"
								requestURI="/view-pricebook" export="false"
								class="pi-table pi-table-complex pi-table-hovered pi-round pi-table-shadow pi-table-all-borders">

								<display:column property="sNo" title="SNo" />
								<display:column property="admin.name" title="PriceBook Owner" />
								<display:column property="priceBookName" title="PriceBook Name" />
								<sec:authorize
									access="hasAnyRole('ROLE_COMPANY','ROLE_VIEW_PRICEBOOK')">
									<display:column url="view-pricebook-details" media="html"
										paramId="priceBookId" paramProperty="priceBookId" title="View">
										<a
											href="view-pricebook-details?priceBookId=${data.priceBookId}"><i
											style="text-align: center;" class="fa fa-eye"></i></a>
									</display:column>
								</sec:authorize>
								<sec:authorize
									access="hasAnyRole('ROLE_COMPANY','ROLE_UPDATE_PRICEBOOK')">
									<display:column url="edit-pricebook" media="html"
										paramId="priceBookId" paramProperty="priceBookId" title="Edit">
										<a href="edit-pricebook?priceBookId=${data.priceBookId}"><i
											style="text-align: center;" class="fa fa-pencil"></i></a>
									</display:column>
								</sec:authorize>
								<sec:authorize
									access="hasAnyRole('ROLE_COMPANY','ROLE_DELETE_PRICEBOOK')">
									<display:column url="delete-pricebook" media="html"
										paramId="priceBookId" paramProperty="priceBookId"
										title="Delete">
										<a href="delete-pricebook?priceBookId=${data.priceBookId}"
											onclick="return confirm('Are you sure you want to Delete?')"><i
											style="text-align: center;" class="fa fa-trash"></i></a>
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
					<c:if test="${pricebook.currentPage gt 1}">
						<li><a
							href="view-pricebook?page=1&searchElement=${searchElement}"><span><i
									class="fa fa-angle-double-left" aria-hidden="true"></i> </span></a></li>
						<li><a
							href="view-pricebook?page=${pricebook.currentPage - 1}&searchElement=${searchElement}"><span><i
									class="fa fa-angle-left" aria-hidden="true"></i> </span></a></li>
					</c:if>
					<c:forEach items="${pricebook.noOfPages}" var="i">
						<c:choose>
							<c:when test="${pricebook.currentPage == i}">
								<li class="active"><a
									style="color: #fff; background-color: #34495e">${i}</a></li>
							</c:when>
							<c:otherwise>
								<li><a
									href="view-pricebook?page=${i}&searchElement=${searchElement}">${i}</a></li>
							</c:otherwise>
						</c:choose>
					</c:forEach>
					<c:if test="${pricebook.currentPage lt pricebook.totalPages}">
						<li><a
							href="view-pricebook?page=${pricebook.currentPage + 1}&searchElement=${searchElement}"><span><i
									class="fa fa-angle-right" aria-hidden="true"></i> </span></a></li>
						<li><a
							href="view-pricebook?page=${pricebook.lastRecordValue}&searchElement=${searchElement}"><span><i
									class="fa fa-angle-double-right" aria-hidden="true"></i> </span></a></li>
					</c:if>
				</ul>
			</nav>

		</div>
	</div>
</div>
</html>