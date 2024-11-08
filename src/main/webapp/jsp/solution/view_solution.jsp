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
<title>List Of Solution</title>
</head>

<script type="text/javascript">

function pageNavigation(pageNo){
	$('#myForm').append('<input type="hidden" name="page" value="'+pageNo+'">');
	document.solution.submit();
}
</script>

<script>
	$(document).ready(function() {
		$('#search').click(function(e) {
			var isValid = true;

			var solutionTitle = $('#solutionTitle').val();
			if (solutionTitle == '') {
				isValid = false;
				$("#solutionTitleErr").show();
				$("#solutionTitleErr").html("Please enter First name ");
				$("#solutionTitle").css({
					"border" : "1px solid red",

				});

			}   else {
				$('#solutionTitleErr').hide();
				$('#solutionTitle').css({

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
					style="margin-top: -10px;">List Solution</h3>
				<sec:authorize
					access="hasAnyRole('ROLE_COMPANY','ROLE_CREATE_SOLUTION')">
					<div class="row ">
						<a href="create_solution"
							style="font-size: 26px; color: #7cb228; margin-left: 98%;"> <i
							class="fa fa-plus-circle" title="Create New Solution"></i>
						</a>
					</div>
				</sec:authorize>
				<form:form id="myForm" method="post" class="login-form clearfix"
					name="solution" action="search_solution" commandName="solution"
					modelAttribute="solution">
					<div class="row"
						style="border: 4px solid #e6e6e6; padding: 15px 15px 0px 15px; background-color: #e1e1e1">
						<div class=" col-md-4">
							<div class="form-group">
								<form:input type="ntext" class="form-control"
									path="solutionTitle" placeholder="Select Solution Title "
									escapeXml="false"></form:input>
								<form:errors path="solutionTitle" cssClass="error" />
								<div id="solutionTitleErr" style="color: red;"></div>
							</div>
						</div>

						<%-- <div class=" col-md-4">
							<div class="form-group">
								<form:input ty path="" />
							</div>
						</div> --%>

						<div class=" col-md-1" style="padding-bottom: 0px;">
							<div class="form-group">
								<button class="btn btn-theme btn-success btn-block" id="ssearch"
									style="background-color: #7cb228; border-color: #7cb228;">
									<small><i class="fa fa-search" aria-hidden="true"
										style="font-size: 20px;"></i></small>
								</button>
							</div>
						</div>
					</div>
				</form:form>

				<c:if test="${not empty totalCount}">
					<div class="col-sm-12" style="margin-top: 20px">
						<p>
							<a class="btn btn-theme btn-xs btn-default"
								style="color: #1b1818; font-weight: bold;"><c:out
									value="${totalCount}"></c:out></a> <strong class="color-black">Solution
								Found</strong>
						</p>
					</div>
				</c:if>

				<c:if test="${not empty totalSearchCount}">
					<div class="col-sm-12" style="margin-top: 20px">
						<p>
							<a class="btn btn-theme btn-xs btn-default"
								style="color: #1b1818; font-weight: bold;"><c:out
									value="${totalSearchCount}"></c:out></a> <strong
								class="color-black">Solution Found</strong>
						</p>
					</div>
				</c:if>
				<c:if test="${!empty solutionBOList}">
					<div class="col-sm-12">
						<div class="pi-responsive-table-sm">
							<div class="pi-section-w pi-section-white piTooltips">
								<display:table id="data" name="${solutionBOList.list}"
									requestURI="/view_solution" export="false"
									class="pi-table pi-table-complex pi-table-hovered pi-round pi-table-shadow pi-table-all-borders">

									<display:column title="S.No" headerClass="sortable"
										media="html">
										<c:out value="${data_rowNum}" />
									</display:column>
									<display:column property="solutionTitle" title="Solution Title" />
									<display:column property="inventoryBo.serviceName"
										title="Product Name" />
									<display:column property="adminUserBo.name"
										title="Solution Owner" />
									<sec:authorize
										access="hasAnyRole('ROLE_COMPANY','ROLE_UPDATE_SOLUTION')">
										<display:column url="edit_solution" media="html" paramId="id"
											paramProperty="solutionId" title="Edit">
											<a href="edit_solution?solutionId=${data.solutionId}"><i
												style="text-align: center;" class="fa fa-pencil"></i></a>
										</display:column>
									</sec:authorize>
									<sec:authorize
										access="hasAnyRole('ROLE_COMPANY','ROLE_DELETE_SOLUTION')">
										<display:column url="delete_solution" media="html"
											paramId="id" paramProperty="solutionId" title="Delete">
											<a href="delete_solution?solutionId=${data.solutionId}"
												onclick="return confirm('Are you sure you want to Delete?')"><i
												style="text-align: center;" class="fa fa-trash"></i></a>
										</display:column>
									</sec:authorize>
									<sec:authorize
										access="hasAnyRole('ROLE_COMPANY','ROLE_VIEW_SOLUTION')">
										<display:column url="solution_tracking_status" media="html"
											paramId="id" paramProperty="solutionId" title="View">
											<a
												href="solution_tracking_status?solutionId=${data.solutionId}">
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
						<c:if test="${solutionBOList.currentPage gt 1}">
							<!-- <li><a href="view-campaign?page=1"><span><i
										class="fa fa-angle-double-left" aria-hidden="true"></i> </span></a></li> -->

							<%-- <li><a
								href="view-campaign?page=${campaignlist.currentPage - 1}"><span><i
										class="fa fa-angle-left" aria-hidden="true"></i> </span></a></li> --%>

							<li><a onclick=pageNavigation(1)><span><i
										class="fa fa-angle-double-left" aria-hidden="true"></i> </span></a></li>

							<li><a onclick=pageNavigation(${solutionBOList.currentPage
								- 1})><span><i class="fa fa-angle-left"
										aria-hidden="true"></i> </span></a></li>


						</c:if>
						<c:forEach items="${solutionBOList.noOfPages}" var="i">
							<c:choose>
								<c:when test="${solutionBOList.currentPage == i}">
									<li class="active"><a
										style="color: #fff; background-color: #34495e">${i}</a></li>
								</c:when>
								<c:otherwise>
									<%-- <li><a href="view-campaign?page=${i}">${i}</a></li> --%>

									<li><a onclick=pageNavigation(${i})>${i}</a></li>

								</c:otherwise>
							</c:choose>
						</c:forEach>
						<c:if
							test="${solutionBOList.currentPage lt solutionBOList.totalPages}">
							<li><a onclick=pageNavigation(${solutionBOList.currentPage
								+ 1})><span><i class="fa fa-angle-right"
										aria-hidden="true"></i> </span></a></li>
							<li><a
								onclick=pageNavigation(${solutionBOList.lastRecordValue})><span><i
										class="fa fa-angle-double-right" aria-hidden="true"></i> </span></a></li>
						</c:if>
					</ul>
				</nav>



			</div>
		</div>
	</div>
</body>
</html>