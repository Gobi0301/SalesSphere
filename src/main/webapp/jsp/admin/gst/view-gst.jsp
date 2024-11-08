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
<script>
	$(function() {
		$("#startDateInput").datepicker({
			changeMonth : true,
			changeYear : true,
			numberOfMonths : 1,
			onSelect : function(selected) {
				var dt = new Date(selected);
				dt.setDate(dt.getDate());
			}
		});
	});
</script>

<script type="text/javascript">
	function pageNavigation(pageNo) {

		$('#myGstSearchForm').append(
				'<input type="hidden" name="page" value="'+pageNo+'">');
		document.gstSearch.submit();
	}
</script>

<script>
	$(document).ready(function() {

		$('#btnsubmit').click(function(e) {
			var isValid = true;
			//Product Name..

			var sgstValue = $('#sgstInput').val();
			if (sgstValue == '') {
				isValid = false;
				$("#sgstError").show();
				$("#sgstError").html("Please  enter sgst value");
				$("#sgstInput").css({
					"border" : "1px solid red",
				});
			} else {
				$('#sgstError').hide();
				$('#sgstInput').css({
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
				style="margin-top: -10px;">List Gst</h3>
			<sec:authorize access="hasAnyRole('ROLE_COMPANY','ROLE_CREATE_GST')">
				<div class="row ">
					<a href="create-gst"
						style="font-size: 30px; color: #7cb228; margin-left: 97%;"> <i
						class="fa fa-plus-circle" title="Create New Gst"></i>
					</a>
				</div>
			</sec:authorize>



			<form:form id="myGstSearchForm" method="post"
				class="login-form clearfix" action="search-gst"
				modelAttribute="searchGst">
				<div class="row"
					style="border: 4px solid #e6e6e6; margin: 15px; background-color: #e1e1e1;">
					<div class="col-md-3">
						<div class="form-group home-left">
							<label class="hidden-xs"></label>
							<form:input type="ntext" id="sgstInput" class="form-control"
								path="sgst" placeholder="Sgst" escapeXml="false"
								style="height: 35px; font-weight: 700; margin-top: 24px;"></form:input>
							<div id="sgstError" style="color: red; display: none;"></div>
						</div>
					</div>
					<div class="col-md-3">
						<div class="form-group home-left">
							<label class="hidden-xs"></label>
							<form:input type="ntext" class="form-control" path="cgst"
								placeholder="Cgst" escapeXml="false"
								style="height: 35px; font-weight: 700; margin-top: 24px;"></form:input>
						</div>
					</div>
					<div class="col-md-3 fs-mobile-search">
						<div class="form-group">
							<label class="hidden-xs"></label>
							<form:select type="text" path="product.serviceName"
								class="form-control"
								style="height: 35px; font-weight: 700; text-transform: capitalize; margin-top: 24px;">
								<form:option value="">-- Select Products --</form:option>
								<form:options items="${productList}" itemLabel="serviceName"
									itemValue="serviceId" />
							</form:select>
						</div>
					</div>
					<div class="col-md-3" style="padding-bottom: 0;">
						<div class="form-group home-right">
							<label class="hidden-xs"></label>
							<button class="btn btn-theme btn-success btn-block"
								id="btnsubmit"
								style="padding: 6px 5px; margin-top: 24px; background-color: #7cb228; border-color: #7cb228;">
								<small><i class="fa fa-search" aria-hidden="true"
									style="font-size: 20px;"></i></small>
							</button>
						</div>
					</div>
				</div>
			</form:form>

			<c:if test="${not empty totalCountOfGST}">
				<div class="col-sm-12" style="margin-top: 20px">
					<p>
						<a class="btn btn-theme btn-xs btn-default"
							style="color: #1b1818; font-weight: bold;"><c:out
								value="${totalCountOfGST}"></c:out></a> <strong class="color-black">Gst
							Found</strong>
					</p>
				</div>
			</c:if>

			<c:if test="${not empty totalSearchCount}">
				<div class="col-sm-12" style="margin-top: 20px">
					<p>
						<a class="btn btn-theme btn-xs btn-default"
							style="color: #1b1818; font-weight: bold;"><c:out
								value="${totalSearchCount}"></c:out></a> <strong class="color-black">Gst
							Found</strong>
					</p>
				</div>
			</c:if>

			<c:if test="${!empty gstList.list}">
				<div class="row">
					<div class="pi-responsive-table-sm">
						<div class="pi-section-w pi-section-white piTooltips">
							<display:table id="data" name="${gstList.list}"
								requestURI="/view-gst" export="false"
								class="pi-table pi-table-complex pi-table-hovered pi-round pi-table-shadow pi-table-all-borders">

								<display:column property="sNo" title="SNo" />
								<display:column property="product.serviceName"
									title="Product Name" />
								<display:column property="sgst" title="Sgst" />
								<display:column property="cgst" title="Cgst" />
								<display:column property="beginDate" title="Date" />

								<sec:authorize
									access="hasAnyRole('ROLE_COMPANY','ROLE_UPDATE_GST')">
									<display:column url="edit-gst" media="html" paramId="id"
										paramProperty="gstId" title="Edit">
										<a href="edit-gst?gstId=${data.gstId}"><i
											style="text-align: center;" class="fa fa-pencil"></i></a>
									</display:column>
								</sec:authorize>
								<sec:authorize
									access="hasAnyRole('ROLE_COMPANY','ROLE_DELETE_GST')">
									<display:column url="delete-gst" media="html" paramId="id"
										paramProperty="gstId" title="Delete">
										<a href="delete-gst?gstId=${data.gstId}"
											onclick="return confirm('Are you sure you want to Delete?')"><i
											style="text-align: center;" class="fa fa-trash"></i></a>
									</display:column>
								</sec:authorize>
							</display:table>
						</div>
					</div>
				</div>
				<nav style="text-align: center;">
					<ul class="pagination pagination-theme  no-margin center"
						style="margin-left: 575px;">


						<c:if test="${gstList.currentPage gt 1}">
							<li><a href="view-gst?page=1&searchElement=${searchElement}"><span><i
										class="fa fa-angle-double-left" aria-hidden="true"></i> </span></a></li>
							<li><a
								href="view-gst?page=${gstList.currentPage - 1}&searchElement=${searchElement}"><span><i
										class="fa fa-angle-left" aria-hidden="true"></i> </span></a></li>
						</c:if>
						<c:forEach items="${gstList.noOfPages}" var="i">
							<c:choose>
								<c:when test="${gstList.currentPage == i}">

									<li class="active"><a
										style="color: #fff; background-color: #34495e">${i}</a></li>
								</c:when>
								<c:otherwise>
									<li><a
										href="view-gst?page=${i}&searchElement=${searchElement}">${i}</a></li>
								</c:otherwise>
							</c:choose>
						</c:forEach>

						<c:if test="${gstList.currentPage lt gstList.totalPages}">
							<li><a
								href="view-gst?page=${gstList.currentPage + 1}&searchElement=${searchElement}"><span><i
										class="fa fa-angle-right" aria-hidden="true"></i> </span></a></li>
							<li><a
								href="view-gst?page=${gstList.lastRecordValue}&searchElement=${searchElement}"><span><i
										class="fa fa-angle-double-right" aria-hidden="true"></i> </span></a></li>
						</c:if>


					</ul>
				</nav>
			</c:if>
		</div>
	</div>
	<br />
</div>
</html>