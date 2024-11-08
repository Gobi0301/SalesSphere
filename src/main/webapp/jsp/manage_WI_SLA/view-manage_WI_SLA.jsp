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
<title>SLA List</title>
</head>
<body>

	<script>
		$(document).ready(function() {

			$('#submit').click(function(e) {
				//SlaCode
				var isValid = true;
				var slaCode = $('#slaCodeId').val();
				if (slaCode == '') {
					isValid = false;
					$("#slaCodeErr").show();
					$("#slaCodeErr").html("Please Enter SlaCode");
					$("#slaCodeId").css({
						"border" : "1px solid red",
					});
				} else {
					$('#slaCodeErr').hide();
					$('#slaCodeId').css({
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
					style="margin-top: -10px;">List-Manage WI & SLA</h3>
				<sec:authorize
					access="hasAnyRole('ROLE_COMPANY','ROLE_CREATE_WI&SLA')">
					<div class="row ">
						<a href="create-manage_WI_SLA"
							style="font-size: 26px; color: #7cb228; margin-left: 97%;"> <i
							class="fa fa-plus-circle" title="Manage_WI_SLA"></i>
						</a>
					</div>
				</sec:authorize>
				<form:form id="slaSearchForm" method="post"
					class="login-form clearfix" name="searchSla"
					action="search-manage_WI_SLA" commandName="searchManage">
					<div class="row"
						style="border: 4px solid #e6e6e6; margin: 15px 15px 15px 15px; background-color: #e1e1e1">
						<div class=" col-md-4">
							<div class="form-group home-left">
								<label class="hidden-xs">&nbsp;</label>
								<form:input type="text" class="form-control" path="slaCode"
									placeholder="WI_SLA Code" id="slaCodeId" escapeXml="false"
									style="height: 35px;font-weight: 700;"></form:input>

								<form:errors path="slaCode" class="error" />
								<div id="slaCodeErr" style="color: red;"></div>

							</div>
						</div>
						<div class=" col-md-1" style="padding-bottom: 0px;">
							<div class="form-group home-right">
								<label class="hidden-xs">&nbsp;</label>
								<div class="text-center">
									<button class="btn btn-theme btn-success btn-block" id="Submit"
										style="padding: 6px 5px; margin-top: 3px; background-color: #7cb228; border-color: #7cb228;">
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
									value="${totalCount}"></c:out></a> <strong class="color-black">WI&SLA
								Found</strong>
						</p>
					</div>
				</c:if>

				<c:if test="${not empty count}">
					<div class="col-sm-12" style="margin-top: 20px">
						<p>
							<a class="btn btn-theme btn-xs btn-default"
								style="color: #1b1818; font-weight: bold;"><c:out
									value="${count}"></c:out></a> <strong class="color-black">WI&SLA
								Found</strong>
						</p>
					</div>
				</c:if>
				<c:if test="${!empty manageList}">
					<div class="col-sm-12">
						<div class="pi-responsive-table-sm">
							<div class="pi-section-w pi-section-white piTooltips">
								<display:table id="data" name="${manageList.list}"
									requestURI="/view-manage_WI_SLA" export="false"
									class="pi-table pi-table-complex pi-table-hovered pi-round pi-table-shadow pi-table-all-borders">

									<display:column property="sNo" title="S.No" />
									<display:column property="slaCode" title="WI_SLA Code" />
									<display:column property="workItemBO.workItem" title="WorkItem" />
									<display:column property="slaBO.slaName" title="SLA (No of Days)" />
									<sec:authorize
										access="hasAnyRole('ROLE_COMPANY','ROLE_UPDATE_WI&SLA')">
										<display:column url="edit-manage_WI_SLA" media="html"
											paramId="id" paramProperty="manageId" title="Edit">
											<a href="edit-manage_WI_SLA?manageId=${data.manageId}"><i
												style="text-align: center;" class="fa fa-pencil"></i></a>
										</display:column>
									</sec:authorize>
									<sec:authorize
										access="hasAnyRole('ROLE_COMPANY','ROLE_DELETE_WI&SLA')">
										<display:column url="delete-manage_WI_SLA" media="html"
											paramId="id" paramProperty="manageId" title="Delete">
											<a href="delete-manage_WI_SLA?manageId=${data.manageId}"
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
						<c:if test="${manageList.currentPage gt 1}">
							<li><a
								href="view-manage_WI_SLA?page=1&searchElement=${searchElement}"><span><i
										class="fa fa-angle-double-left" aria-hidden="true"></i> </span></a></li>
							<li><a
								href="view-manage_WI_SLA?page=${manageList.currentPage - 1}&searchElement=${searchElement}"><span><i
										class="fa fa-angle-left" aria-hidden="true"></i> </span></a></li>
						</c:if>
						<c:forEach items="${manageList.noOfPages}" var="i">
							<c:choose>
								<c:when test="${manageList.currentPage == i}">

									<li class="active"><a
										style="color: #fff; background-color: #34495e">${i}</a></li>
								</c:when>
								<c:otherwise>
									<li><a
										href="view-manage_WI_SLA?page=${i}&searchElement=${searchElement}">${i}</a></li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
						<c:if test="${manageList.currentPage lt manageList.totalPages}">
							<li><a
								href="view-manage_WI_SLA?page=${manageList.currentPage + 1}&searchElement=${searchElement}"><span><i
										class="fa fa-angle-right" aria-hidden="true"></i> </span></a></li>
							<li><a
								href="view-manage_WI_SLA?page=${manageList.lastRecordValue}&searchElement=${searchElement}"><span><i
										class="fa fa-angle-double-right" aria-hidden="true"></i> </span></a></li>
						</c:if>
					</ul>
				</nav>
			</div>
		</div>
	</div>
</body>
</html>