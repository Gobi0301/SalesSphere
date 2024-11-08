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


 <script type="text/javascript">

 function pageNavigation(pageNo){
	    $('#myForm').append('<input type="hidden" name="page" value="'+pageNo+'">');
	    $('#myForm').append('<input type="hidden" name="searchRoleName" value="'+$('#roleId').val()+'">');
	    document.roleSearch.submit();
	}
</script> 

   <!--  <script>
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
</script>  -->


<!-- <script>
		$(document).ready(function() {

			$('#submit').click(function(e) {

				var isValid = true;
				var roleName = $('#roleId').val(); // Assuming the input field ID is 'roleId'

				if (roleName == '') {
					isValid = false;
					$("#roleNameErr").show();
					$("#roleNameErr").html("Please enter roleName");
					$("#roleId").css({
						"border" : "1px solid red",
					});

				} else {
					$('#roleNameErr').hide();
					$('#roleId').css({
						"border" : "",
						"background" : ""
					});
				}

				if (isValid == false)
					e.preventDefault();
			});
		});
	</script> -->

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
				style="margin-top: -10px;">View Role</h3>
			<%-- <sec:authorize access="hasAnyRole('ROLE_COMPANY','ROLE_CREATE_GST')"> --%>
				<div class="row ">
					<a href="role-user-type"
						style="font-size: 26px; color: #7cb228; margin-left: 95%;"> <i
						class="fa fa-plus-circle" title="Create New Role"></i>
					</a>
				</div>
			<%-- </sec:authorize> --%>

			

			<form:form  id="myForm" method="post"  class="login-form clearfix" 
			 action="search-role" name="roleSearch" modelAttribute="searchObj">
				<div class="row"
					style="border: 4px solid #e6e6e6; margin: 15px 15px 15px 15px; background-color: #e1e1e1">
					<div class=" col-md-3">
						<div class="form-group home-left">
							<label class="hidden-xs"></label>
							<form:input type="ntext" id="roleId" class="form-control" path="roleName"
								placeholder="Role Name " escapeXml="false"
								style="height: 35px;font-weight: 700; margin-top: 24px;"></form:input>
						<div id="roleError" style="color: red; display: none;"></div>
						</div>
					</div>

					<div class=" col-md-1" style="padding-bottom: 0px;">
						<div class="form-group home-right">
							<label class="hidden-xs"></label>
							<button class="btn btn-theme btn-success btn-block" id="btnsubmit"
								style="padding: 6px 5px; margin-top: 24px; background-color: #7cb228; border-color: #7cb228;">
								<small><i class="fa fa-search" aria-hidden="true" 
									style="font-size: 20px;"></i></small>
							</button>
						</div>
					</div>
				</div>
			</form:form>
			
			<c:if test="${not empty totalCountOfRole}">
				<div class="col-sm-12" style="margin-top: 20px">
					<p>
						<a class="btn btn-theme btn-xs btn-default"
							style="color: #1b1818; font-weight: bold;"><c:out
								value="${totalCountOfRole}"></c:out></a> <strong
							class="color-black">Role Found</strong>
					</p>
				</div>
			</c:if>
			
			<c:if test="${not empty totalSearchCount}">
				<div class="col-sm-12" style="margin-top: 20px">
					<p>
						<a class="btn btn-theme btn-xs btn-default"
							style="color: #1b1818; font-weight: bold;"><c:out
								value="${totalSearchCount}"></c:out></a> <strong
							class="color-black">Role Found</strong>
					</p>
				</div>
			</c:if>
			
			<c:if test="${!empty roleBOList}">
				<div class="row">
					<div class="pi-responsive-table-sm">
						<div class="pi-section-w pi-section-white piTooltips">
							<display:table id="data" name="${roleBOList.list}"
								requestURI="/view-gst" export="false"
								class="pi-table pi-table-complex pi-table-hovered pi-round pi-table-shadow pi-table-all-borders">

								<display:column property="sNo" title="SNo" />
								<display:column property="roleName" title="RoleName" />
								
								<%-- <sec:authorize access="hasAnyRole('ROLE_COMPANY','ROLE_UPDATE_')"> --%>
								<display:column url="edit-role" media="html" paramId="id"
									paramProperty="roleId" title="Edit">
									<a href="edit-role-type?roleId=${data.roleId}"><i
										style="text-align: center;" class="fa fa-pencil"></i></a>
								</display:column>
								<%-- </sec:authorize> --%>
								
								<%-- <sec:authorize access="hasAnyRole('ROLE_COMPANY','ROLE_DELETE_GST')"> --%>
								<display:column url="delete-role-type" media="html" paramId="id"
									paramProperty="roleId" title="Delete">
									<a href="delete-role-type?roleId=${data.roleId}"
										onclick="return confirm('Are you sure you want to Delete?')"><i
										style="text-align: center;" class="fa fa-trash"></i></a>
								</display:column>
								<%-- </sec:authorize> --%>
								
							</display:table>
						</div>
					</div>
				</div>
				<%-- <nav style="text-align: center;">
					<ul class="pagination pagination-theme  no-margin center"
						style="margin-left: 575px;">
						<c:if test="${roleBOList.currentPage gt 1}">

							<li><a onclick=pageNavigation(1)><span><i
										class="fa fa-angle-double-left" aria-hidden="true"></i> </span></a></li>

							<li><a onclick=pageNavigation(${roleBOList.currentPage - 1})><span><i
										class="fa fa-angle-left" aria-hidden="true"></i> </span></a></li>
						</c:if>
						<c:forEach items="${roleBOList.noOfPages}" var="i">
							<c:choose>
								<c:when test="${roleBOList.currentPage == i}">
									<li class="active"><a
										style="color: #fff; background-color: #34495e">${i}</a></li>
								</c:when>
								<c:otherwise>

									<li><a onclick=pageNavigation(${i})>${i}</a></li>

								</c:otherwise>
							</c:choose>
						</c:forEach>
						<c:if test="${roleBOList.currentPage lt roleBOList.totalPages}">
							<li><a onclick=pageNavigation(${roleBOList.currentPage + 1})><span><i
										class="fa fa-angle-right" aria-hidden="true"></i> </span></a></li>
							<li><a onclick=pageNavigation(${roleBOList.lastRecordValue})><span><i
										class="fa fa-angle-double-right" aria-hidden="true"></i> </span></a></li>
						</c:if>
					</ul>
				</nav> --%>
				<nav style="text-align: center;">
					<ul class="pagination pagination-theme  no-margin center"
						style="margin-left: 575px;">
						<c:if test="${roleBOList.currentPage gt 1}">
							<li><a
								href="view-role?page=1&searchRoleName=${searchRoleName}"><span><i
										class="fa fa-angle-double-left" aria-hidden="true"></i> </span></a></li>
							<li><a
								href="view-role?page=${roleBOList.currentPage - 1}&searchRoleName=${searchRoleName}"><span><i
										class="fa fa-angle-left" aria-hidden="true"></i> </span></a></li>
						</c:if>
						<c:forEach items="${roleBOList.noOfPages}" var="i">
							<c:choose>
								<c:when test="${roleBOList.currentPage == i}">

									<li class="active"><a
										style="color: #fff; background-color: #34495e">${i}</a></li>
								</c:when>
								<c:otherwise>
									<li><a
										href="view-role?page=${i}&searchRoleName=${searchRoleName}">${i}</a></li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
						<c:if test="${roleBOList.currentPage lt roleBOList.totalPages}">
							<li><a
								href="view-role?page=${roleBOList.currentPage + 1}&searchRoleName=${searchRoleName}"><span><i
										class="fa fa-angle-right" aria-hidden="true"></i> </span></a></li>
							<li><a
								href="view-role?page=${roleBOList.lastRecordValue}&searchRoleName=${searchRoleName}"><span><i
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