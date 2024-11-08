<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>



<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>

<script type="text/javascript">

function pageNavigation(pageNo){
	
	$('#myForm').append('<input type="hidden" name="page" value="'+pageNo+'">');
	document.privilege.submit();
}
</script>

<body>
	<div class="box-list">
	<c:if test="${not empty successMessage}">
				<div class="alert alert-success" role="alert"
					style="font-size: 12px; padding: 8px 9px 5px 10px; margin-top: 15px;">
					<button type="button" class="close" data-dismiss="alert"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<strong>success!</strong>
					<c:out value="${successMessage}"></c:out>
				</div>
			</c:if>
			
			<c:if test="${not empty errorMessage}">
				<div class="alert alert-success">
					<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
					<strong>Success:</strong>
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
		<div class="item">
			<div class="row ">
				<h3 class="text-center no-margin titleunderline underline"
					style="margin-top: -10px;">List Privilege Access</h3>

				<div class="row ">
					<a href="create-privilege-access"
						style="font-size: 26px; color: #7cb228; margin-left: 95%;"> <i
						class="fa fa-plus-circle" title="Create New privilege-access"></i>
					</a>
				</div>
				<form:form id="myForm" method="post" class="login-form clearfix" name="privilege"
				action="search-privilege-name" modelAttribute="privilegeSerachObj">
				
				<div class="row"
					style="border: 4px solid #e6e6e6; margin: 15px 15px 15px 15px; background-color: #e1e1e1">
					<div class=" col-md-3">
						<div class="form-group home-left">
							<label class="hidden-xs"></label>
							<form:input type="text" class="form-control" path="privilegename" id="privilegenameid"
								placeholder="Privilege Name" escapeXml="false" 
								style="height: 35px;font-weight: 700;"></form:input>
								<form:errors path="privilegename" cssClass="error" />
											<div id="privilegenameErr" style="color: red;"></div>
						</div>
					</div>

					<div class=" col-md-1" style="padding-bottom: 0px;">
						<div class="form-group home-right">
							<label class="hidden-xs"></label>
							<button class="btn btn-theme btn-success btn-block" id="search"
								style="padding: 6px 5px; background-color: #7cb228; border-color: #7cb228;">
								<small><i class="fa fa-search" aria-hidden="true"
									style="font-size: 20px;"></i></small>
							</button>
						</div>
					</div>

				</div>
			</form:form>
			

			</div>

			<c:if test="${not empty privilegeBOlist}">

				<div class="col-sm-12" style="margin-top: 20px">
					<p>
						<a class="btn btn-theme btn-xs btn-default"
							style="color: #1b1818; font-weight: bold;"><c:out
								value="${privilegeBOlist}"></c:out></a> <strong class="color-black">privilege
							Found</strong>
					</p>
				</div>

			</c:if>

			<c:if test="${not empty totalCountOfprivilege}">

				<div class="col-sm-12" style="margin-top: 20px">
					<p>
						<a class="btn btn-theme btn-xs btn-default"
							style="color: #1b1818; font-weight: bold;"><c:out
								value="${totalCountOfprivilege}"></c:out></a> <strong class="color-black">UserRoleSearch
							Found</strong>
					</p>
				</div>

			</c:if>

			<c:if test="${!empty listPrivilegesBO}">
				<div class="row">
					<div class="pi-responsive-table-sm">
						<div class="pi-section-w pi-section-white piTooltips">
							<display:table id="data" name="${listPrivilegesBO.list}" 
								requestURI="/view-privilege-access" export="false"
								class="pi-table pi-table-complex pi-table-hovered pi-round pi-table-shadow pi-table-all-borders">

								<display:column property="sNo" title="SNo" />
								<display:column property="privilegename" title="Privilege Name" />

								<display:column url="edit-privilege-access" media="html"
									paramId="privilegeId" paramProperty="privilegeId" title="Edit">
									<a href="edit-privilege-access?privilegeId=${data.privilegeId}">
										<i style="text-align: center;" class="fa fa-pencil"></i>
									</a>
								</display:column>

							</display:table>
						</div>
					</div>
				</div>
		<%-- 	 	<nav style="text-align: center;">
					<ul class="pagination pagination-theme  no-margin center"
						style="margin-left: 575px;">
						<c:if test="${listPrivilegesBO.currentPage gt 1}">

							<li><a onclick=pageNavigation(1)><span><i
										class="fa fa-angle-double-left" aria-hidden="true"></i> </span></a></li>

							<li><a onclick=pageNavigation(${listPrivilegesBO.currentPage - 1})><span><i
										class="fa fa-angle-left" aria-hidden="true"></i> </span></a></li>
						</c:if>
						<c:forEach items="${listPrivilegesBO.noOfPages}" var="i">
							<c:choose>
								<c:when test="${listPrivilegesBO.currentPage == i}">
									<li class="active"><a
										style="color: #fff; background-color: #34495e">${i}</a></li>
								</c:when>
								<c:otherwise>

									<li><a onclick=pageNavigation(${i})>${i}</a></li>

								</c:otherwise>
							</c:choose>
						</c:forEach>
						<c:if test="${listPrivilegesBO.currentPage lt listPrivilegesBO.totalPages}">
							<li><a onclick=pageNavigation(${listPrivilegesBO.currentPage + 1})><span><i
										class="fa fa-angle-right" aria-hidden="true"></i> </span></a></li>
							<li><a onclick=pageNavigation(${listPrivilegesBO.lastRecordValue})><span><i
										class="fa fa-angle-double-right" aria-hidden="true"></i> </span></a></li>
						</c:if>
					</ul>
				</nav>  --%>
				<nav style="text-align: center;">
					<ul class="pagination pagination-theme  no-margin center"
						style="margin-left: 575px;">
						<c:if test="${listPrivilegesBO.currentPage gt 1}">
							<li><a href="view-privilege-access?page=1"><span><i
										class="fa fa-angle-double-left" aria-hidden="true"></i> </span></a></li>
							<li><a href="view-privilege-access?page=${listPrivilegesBO.currentPage - 1}"><span><i
										class="fa fa-angle-left" aria-hidden="true"></i> </span></a></li>
						</c:if>
						<c:forEach items="${listPrivilegesBO.noOfPages}" var="i">
							<c:choose>
								<c:when test="${listPrivilegesBO.currentPage == i}">

									<li class="active"><a
										style="color: #fff; background-color: #34495e">${i}</a></li>
								</c:when>
								<c:otherwise>
									<li><a href="view-privilege-access?page=${i}">${i}</a></li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
						<c:if test="${listPrivilegesBO.currentPage lt listPrivilegesBO.totalPages}">
							<li><a href="view-privilege-access?page=${listPrivilegesBO.currentPage + 1}"><span><i
										class="fa fa-angle-right" aria-hidden="true"></i> </span></a></li>
							<li><a href="view-privilege-access?page=${listPrivilegesBO.lastRecordValue}"><span><i
										class="fa fa-angle-double-right" aria-hidden="true"></i> </span></a></li>
						</c:if>
					</ul>
				</nav>
			</c:if>
			

		</div>
	</div>
</body>
</html>