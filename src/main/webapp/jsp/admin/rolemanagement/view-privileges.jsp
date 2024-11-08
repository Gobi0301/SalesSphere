<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>

<body>

	<br />
	<div class="box-list">
		<div class="item">
			<div class="row ">
				<h3 class="text-center no-margin titleunderline underline"
					style="margin-top: -10px;">View Privileges</h3>
				
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
			<sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_COMPANY')">
				<div class="row ">
					<br> <a href="create-privileges" title="Create New Employee"
						style="font-size: 26px; color: #7cb228; margin-left: 95%;"> <i
						class="fa fa-plus-circle"></i>
					</a>
				</div>
			</sec:authorize> 
			
				<form:form id="myForm" method="post" class="login-form clearfix"
				action="search-privileges" commandName="searchPrivileges">
				<div class="row"
					style="border: 4px solid #e6e6e6; margin:15px 15px 15px 15px; background-color: #e1e1e1">
					<div class=" col-md-3">
						<div class="form-group home-left">
							<label class="hidden-xs"></label>
							<form:input type="text" class="form-control" placeholder="Privileges Name" path="privilegename"
								escapeXml="false"
								style="height: 35px;font-weight: 700;"></form:input>
						</div>
					</div>
					
					<div class=" col-md-1" style="padding-bottom: 0px;">
						<div class="form-group home-right">
							<label class="hidden-xs"></label>
							<button class="btn btn-theme btn-success btn-block"
								style="padding: 6px 5px; background-color: #7cb228; border-color: #7cb228;">
								<small><i class="fa fa-search" aria-hidden="true"
									style="font-size: 20px;"></i></small>
							</button>
						</div>
					</div>
	
				</div>
				</form:form>
				<c:if test="${not empty totalCountOfPrivileges}">
                        <div class="col-sm-12" style="margin-top: 20px">
                            <p><a class="btn btn-theme btn-xs btn-default"
                                    style="color: #1b1818; font-weight: bold;"><c:out
                                        value="${totalCountOfPrivileges}"></c:out></a> <strong
                                    class="color-black">Privileges Found</strong>
                            </p>
                        </div>
                    </c:if>
                    
                    <c:if test="${not empty totalSearchCount}">
                        <div class="col-sm-12" style="margin-top: 20px">
                            <p><a class="btn btn-theme btn-xs btn-default"
                                    style="color: #1b1818; font-weight: bold;"><c:out
                                        value="${totalSearchCount}"></c:out></a> <strong
                                    class="color-black">Privileges Found</strong>
                            </p>
                        </div>
                    </c:if>
			
				<c:if test="${!empty privilegeslists}">
                    <div class="row">
                        <div class="pi-responsive-table-sm">
                            <div class="pi-section-w pi-section-white piTooltips">
                                <display:table id="data" name="${privilegeslists.list}"
                                    requestURI="/view-privileges"  export="false"
                                    class="pi-table pi-table-complex pi-table-hovered pi-round pi-table-shadow pi-table-all-borders">

                                    <display:column property="sNo" title="SNo" />

                                    <display:column property="privilegename"
                                        title="Privileges Name" />

                                    <display:column url="edit-privileges" media="html" paramId="id"
                                        paramProperty="privilegeId" title="Edit">
                                        <a href="edit-privileges?id=${data.id}"><i
                                            style="text-align: center;" class="fa fa-pencil"></i></a>
                                    </display:column>
                                    <display:column url="delete-privileges" media="html"
                                        paramId="id" paramProperty="privilegeId" title="Delete">
                                        <a href="delete-privileges?id=${data.id}"
                                            onclick="return confirm('Are you sure you want to Delete?')"><i
                                            style="text-align: center;" class="fa fa-trash"></i></a>
                                    </display:column>
                                </display:table>
                            </div>
                        </div>
                    </div>
	<nav style="text-align: center;">
					<ul class="pagination pagination-theme  no-margin center"
						style="margin-left: 575px;">
						<c:if test="${privilegeslists.currentPage gt 1}">
							<li><a
								href="view-privileges?page=1&searchPrivilegesName=${searchPrivilegesName}"><span><i
										class="fa fa-angle-double-left" aria-hidden="true"></i> </span></a></li>
							<li><a
								href="view-privileges?page=${privilegeslists.currentPage - 1}&searchPrivilegesName=${searchPrivilegesName}"><span><i
										class="fa fa-angle-left" aria-hidden="true"></i> </span></a></li>
						</c:if>
						<c:forEach items="${privilegeslists.noOfPages}" var="i">
							<c:choose>
								<c:when test="${privilegeslists.currentPage == i}">

									<li class="active"><a
										style="color: #fff; background-color: #34495e">${i}</a></li>
								</c:when>
								<c:otherwise>
									<li><a
										href="view-privileges?page=${i}&searchPrivilegesName=${searchPrivilegesName}">${i}</a></li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
						<c:if test="${privilegeslists.currentPage lt privilegeslists.totalPages}">
							<li><a
								href="view-privileges?page=${privilegeslists.currentPage + 1}&searchPrivilegesName=${searchPrivilegesName}"><span><i
										class="fa fa-angle-right" aria-hidden="true"></i> </span></a></li>
							<li><a
								href="view-privileges?page=${privilegeslists.lastRecordValue}&searchPrivilegesName=${searchPrivilegesName}"><span><i
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

