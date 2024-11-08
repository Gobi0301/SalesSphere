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
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<script>
function pageNavigation(pageNo){
	$('#producttypesSearchForm').append('<input type="hidden" name="page" value="'+pageNo+'">');
	document.searchProducttypes.submit();
}
</script> 

<script>
	$(document).ready(function() {
		$('#search').click(function(e) {
			var isValid = true;
			var productTypesId = $('#productTypesId').val();
			if (productTypesId === '') {
				isValid = false;
				$("#productTypesErr").show();
				$("#productTypesErr").html("Please Enter ProductTypes Name");
				$("#productTypesId").css({
					"border" : "1px solid red",
				});
			} else {
				$('#productTypesErr').hide();
				$('#productTypesId').css({
					"border" : "",
					"background" : ""
				});
			}

			if (isValid == false)
				e.preventDefault();
		});
	});
</script>
<body>

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
					style="margin-top: -10px;">List ProductTypes</h3>
				<sec:authorize
					access="hasAnyRole('ROLE_COMPANY','ROLE_CREATE_PRODUCTTYPE')">
					<div class="row ">
						<a href="create_producttypes"
							style="font-size: 30px; color: #7cb228; margin-left: 97%;"> <i
							class="fa fa-plus-circle" title="Create New Producttypes"></i>
						</a>
					</div>
				</sec:authorize>
				<form:form id="producttypesSearchForm" method="post"
					class="login-form clearfix" name="searchProducttypes"
					action="search-producttypes" modelAttribute="searchProducttypes"
					commandName="searchProducttypes">
					<div class="row"
						style="border: 4px solid #e6e6e6; padding: 15px 15px 0px 15px; background-color: #e1e1e1">
						<div class="col-sm-12">
							<div class="col-sm-4">
								<div class="form-group ">

									<form:input type="ntext" path="productTypes"
										class="form-control" placeholder="ProductTypes Name" 
										escapeXml="false" maxlength="150" style="margin-top: 10px;" />
									<form:errors path="productTypes" class="error" />
									<div id="productTypesErr" style="color: red;"></div>
								</div>
							</div>
							<div class=" col-md-1" style="padding-bottom: 0px;">
								<div class="form-group home-right">
									<button class="btn btn-theme btn-success btn-block" id="search"
										style="padding: 6px 5px; margin-top: 13px; background-color: #7cb228; border-color: #7cb228;">
										<small><i class="fa fa-search" aria-hidden="true"
											style="font-size: 20px;"></i></small>
									</button>
								</div>
							</div>
						</div>
					</div>
				</form:form>
				
					<c:if test="${not empty totalCount}">
					<div class="col-sm-12" style="margin-top: 20px">
						<p>
							<a class="btn btn-theme btn-xs btn-default"
								style="color: #1b1818; font-weight: bold;"><c:out
									value="${totalCount}"></c:out></a> <strong
								class="color-black">ProductType Found</strong>
						</p>
					</div>
					</c:if>
					<c:if test="${not empty totalSearchCount}">
					<div class="col-sm-12" style="margin-top: 20px">
						<p>
							<a class="btn btn-theme btn-xs btn-default"
								style="color: #1b1818; font-weight: bold;"><c:out
									value="${totalSearchCount}"></c:out></a> <strong
								class="color-black">ProductType Found</strong>
						</p>
					</div>
					</c:if>
				
				<c:if test="${!empty listtypes}">
				<div class="col-sm-12">
					<div class="pi-responsive-table-sm">
						<div class="pi-section-w pi-section-white piTooltips">
							<display:table id="data" name="${listtypes.list}"
								requestURI="/view_producttypes" export="false"
								class="pi-table pi-table-complex pi-table-hovered pi-round pi-table-shadow pi-table-all-borders">

								<display:column property="sNO" title="SNo" />
								<c:out value="${data_rowNum}" />
								<display:column property="productTypes" title="ProductTypes" />
								<sec:authorize
									access="hasAnyRole('ROLE_COMPANY','ROLE_UPDATE_PRODUCTTYPE')">
									<display:column url="edit-user" media="html" paramId="id"
										paramProperty="productTypesId" title="Edit">
										<a href="update_producttypes?id=${data.productTypesId}"><i
											style="text-align: center;" class="fa fa-pencil"></i></a>
									</display:column>
								</sec:authorize>
								<sec:authorize
									access="hasAnyRole('ROLE_COMPANY','ROLE_DELETE_PRODUCTTYPE')">
									<display:column url="delete-user" media="html" paramId="id"
										paramProperty="productTypesId" title="Delete">
										<a
											href="delete_producttypes?productTypesId=${data.productTypesId}"
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
							<c:if test="${listtypes.currentPage gt 1}">
								
								 <li><a onclick=pageNavigation(1)><span><i
											class="fa fa-angle-double-left" aria-hidden="true"></i> </span></a></li>

								<li><a onclick=pageNavigation(${listtypes.currentPage - 1})><span><i
											class="fa fa-angle-left" aria-hidden="true"></i> </span></a></li>

							</c:if>
							<c:forEach items="${listtypes.noOfPages}" var="i">
								<c:choose>
									<c:when test="${listtypes.currentPage == i}">

										<li class="active"><a
											style="color: #fff; background-color: #34495e">${i}</a></li>
									</c:when>
									<c:otherwise>
										<li>
										<li><a onclick=pageNavigation(${i})>${i}</a></li>
 									</c:otherwise>
								</c:choose>
							</c:forEach>
							<c:if test="${listtypes.currentPage lt listtypes.totalPages}">
								<li><a onclick=pageNavigation(${listtypes.currentPage + 1})><span><i
											class="fa fa-angle-right" aria-hidden="true"></i> </span></a></li>
								<li><a onclick=pageNavigation(${listtypes.lastRecordValue})><span><i
											class="fa fa-angle-double-right" aria-hidden="true"></i> </span></a></li>
						 	</c:if>
						</ul>
					</nav>
				</c:if>
			</div>
		</div>
	</div>
</body>
</html>