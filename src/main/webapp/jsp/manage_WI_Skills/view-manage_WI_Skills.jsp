
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>


<!-- <link href="resources/theme/css/custom.css" rel="stylesheet">
<link href="resources/css/jquery-ui-1.10.4.custom.css" rel="stylesheet">
<script type="text/javascript" src="resources/js/jquery-ui.min.js"></script>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script> -->
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>SLA List</title>
</head>
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
					style="margin-top: -10px;">List Manage WI & SKILLS</h3>
			
				<%--  <sec:authorize access="hasAnyRole('ROLE_COMPANY','ROLE_CREATE_WI&SKILLS')">  --%>
				<div class="row ">
					<a href="create-manage_WI_Skills"
						style="font-size: 26px; color: #7cb228; margin-left: 97%;"> <i
						class="fa fa-plus-circle" title="Manage_WI_Skills"></i>
					</a>
				</div>
				<%--  </sec:authorize>  --%>
				
                           <form:form id="slaSearchForm" method="post"
					class="login-form clearfix" name="searchSla" action="search-manage_WI_Skills"
					commandName="searchManage">
					<div class="row"
						style="border: 4px solid #e6e6e6; margin: 15px 15px 15px 15px; background-color: #e1e1e1">
						 <div class=" col-md-4">
							<div class="form-group home-left">
								<label class="hidden-xs">&nbsp;</label>
								<form:select type="text" path="workitemBO.workItem" id="workItemId"
								class="form-control "
								style="height: 35px;font-weight: 700;
								   text-transform: capitalize; margin-top: -2px;">
								   
								 <form:option value="Select">-- Select WorkItem --   </form:option> 
							 	 <%-- <form:option value="${searchManage.workitemBO.workItemId}">${searchManage.workitemBO.workItem}</form:option>   								
	 --%>						 	 <form:options items="${slaLists}" itemLabel="workitemBO.workItem"
									itemValue="workitemBO.workItemId" />
							</form:select>
								<form:errors path="workitemBO.workItem" class="error" />
								<div id="workItemErr" style="color: red;"></div>
							</div>
						</div>  
						
						<%-- <div class="form-group ">
							<label class="hidden-xs"></label>
							<form:select type="text" path="workitemBO.workItem" id="workItemId"
								class="form-control "
								style="height: 35px;font-weight: 700;
								   text-transform: capitalize; margin-top: 6px;">
								   
								<form:option value="Select">-- Select WorkItem --   </form:option>
							 	<form:option value="${searchManage.workitemBO.workItemId}">${searchManage.workitemBO.workItem}</form:option>  
								<form:options items="${manageList}" itemLabel="workItem"
									itemValue="workItemId" />
							</form:select>
								<div id="workItemErr" style="color: red;"></div>
						</div> --%>
						
						<div class=" col-md-1" style="padding-bottom: 0px;">
							<div class="form-group home-right">
								<label class="hidden-xs">&nbsp;</label>
								<div class="text-center">
									<button class="btn btn-theme btn-success btn-block" id="search"
										style="padding: 6px 5px; margin-top: 2px; background-color: #7cb228; border-color: #7cb228;">
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
                                    class="color-black">WI And Skills Found</strong>
                            </p>
                        </div>

                    </c:if>
                    
                    <c:if test="${not empty totalSearchCount}">

                        <div class="col-sm-12" style="margin-top: 20px">
                            <p>
                                <a class="btn btn-theme btn-xs btn-default"
                                    style="color: #1b1818; font-weight: bold;"><c:out
                                        value="${totalSearchCount}"></c:out></a> <strong
                                    class="color-black">Wi And Skills Found</strong>
                            </p>
                        </div>

                    </c:if>
                    <c:if test="${!empty manageList.list}"> 
				<div class="col-sm-12">
					<div class="pi-responsive-table-sm">
						<div class="pi-section-w pi-section-white piTooltips">
							<display:table id="data" name="${manageList.list}"
								requestURI="/view-manage_WI_Skills"  export="false"
								class="pi-table pi-table-complex pi-table-hovered pi-round pi-table-shadow pi-table-all-borders">

								<display:column property="sNO" title="S.No" />
								<display:column property="workitemBO.workItem" title="WorkItem" />
						 		<display:column property="skillASString" title="Required Skills" />  
								<sec:authorize access="hasAnyRole('ROLE_COMPANY','ROLE_UPDATE_WI&SKILLS')">						
								<display:column url="edit-manage_WI_Skills" media="html" paramId="id"
									paramProperty="manageId" title="Edit">
									<a href="edit-manage_WI_Skills?manageId=${data.manageId}"><i
										style="text-align: center;" class="fa fa-pencil"></i></a>
								</display:column>
								</sec:authorize>
								<sec:authorize access="hasAnyRole('ROLE_COMPANY','ROLE_DELETE_WI&SKILLS')">	
								<display:column url="delete-manage_WI_Skills" media="html" paramId="id"
									paramProperty="manageId" title="Delete">
									<a href="delete-manage_WI_Skills?manageId=${data.manageId}"
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
							<li><a href="view-manage_WI_Skills?page=1&searchElement=${searchElement}"><span><i
										class="fa fa-angle-double-left" aria-hidden="true"></i> </span></a></li>
							<li><a href="view-manage_WI_Skills?page=${manageList.currentPage - 1}&searchElement=${searchElement}"><span><i
										class="fa fa-angle-left" aria-hidden="true"></i> </span></a></li>
						</c:if>
						<c:forEach items="${manageList.noOfPages}" var="i">
							<c:choose>
								<c:when test="${manageList.currentPage == i}">

									<li class="active"><a
										style="color: #fff; background-color: #34495e">${i}</a></li>
								</c:when>
								<c:otherwise>
									<li><a href="view-manage_WI_Skills?page=${i}&searchElement=${searchElement}">${i}</a></li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
						<c:if test="${manageList.currentPage lt manageList.totalPages}">
							<li><a href="view-manage_WI_Skills?page=${manageList.currentPage + 1}&searchElement=${searchElement}"><span><i
										class="fa fa-angle-right" aria-hidden="true"></i> </span></a></li>
							<li><a href="view-manage_WI_Skills?page=${manageList.lastRecordValue}&searchElement=${searchElement}"><span><i
										class="fa fa-angle-double-right" aria-hidden="true"></i> </span></a></li>
						</c:if>
					</ul>
				</nav> 
			</div>
		</div>
	</div>
</body>
</html>