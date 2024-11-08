<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<div class="box-list">
	<c:if test="${not empty errorMessage}">
						<div class="alert alert-info">
							<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
							<strong>Info:</strong>
							<c:out value="${errorMessage}"></c:out>
						</div>
					</c:if>
				</div>
				<c:if test="${not empty successMessage}">
					<div class="alert alert-success">
						<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
						<strong>Success:</strong>
						<c:out value="${successMessage}"></c:out>
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
	<div class="box-list">			
		<div class="item">
			<div class="row ">
				<h3 class="text-center no-margin titleunderline underline"
					style="margin-top: -10px;">List Case</h3>
				<sec:authorize
					access="hasAnyRole('ROLE_COMPANY','ROLE_CREATE_CASE')">
					<div class="row ">
						<a href="create-casemanagement"
							style="font-size: 26px; color: #7cb228; margin-left: 97%;"> <i
							class="fa fa-plus-circle" title="Create New case"></i>
						</a>
					</div>
				</sec:authorize>

				<!--Start form search -->
				<form:form id="myForm" name="pageNo" method="post"
					class="login-form clearfix" action="search-case"
					modelAttribute="searchCase">
					<div class="row"
						style="border: 4px solid #e6e6e6; margin: 15px 15px 15px 15px; background-color: #e1e1e1">

						<div class="col-sm-12">
							<div class="col-sm-4">
								<div class="form-group">
									<label class="hidden-xs"></label>
									<form:input type="text" id="caseOrigin" path="caseOrigin"
										class="form-control required" placeholder="CaseOrigin Name"
										maxlength="150" style="margin-top: 10px;" />
									<form:errors path="caseOrigin" class="error" />
									<div id="caseOriginErr" style="color: red;"></div>
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
									<button class="btn btn-theme btn-success btn-block"
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
									value="${totalCount}"></c:out></a> <strong class="color-black">Total
								case Found</strong>
						</p>
					</div>

				</c:if>

				<c:if test="${not empty totalsearchCount}">
					<div class="col-sm-12" style="margin-top: 20px">
						<p>
							<a class="btn btn-theme btn-xs btn-default"
								style="color: #1b1818; font-weight: bold;"><c:out
									value="${totalsearchCount}"></c:out></a> <strong
								class="color-black"> case Found</strong>
						</p>
					</div>
				</c:if>
<c:if test="${!empty caseLists}"> 
				<div class="pi-responsive-table-sm">
					<div class="pi-section-w pi-section-white piTooltips">
						<display:table id="data" name="${caseLists.list}"
							requestURI="/view-casemanagement" export="false"
							class="pi-table pi-table-complex pi-table-hovered pi-round pi-table-shadow pi-table-all-borders">

							<display:column property="sNo" title="SNo" />
							<display:column property="caseOrigin" title="case Origin" />
							<display:column property="status" title="Status" />
							<display:column property="priority" title="Priority" />
							<display:column property="commend" title="Commend" />

							<sec:authorize
								access="hasAnyRole('ROLE_COMPANY','ROLE_UPDATE_CASE')">
								<display:column url="edit-casemanagement" media="html"
									paramId="caseId" paramProperty="caseId" title="Edit">
									<a href="edit-casemanagement?caseId=${data.caseId}"><i
										style="text-align: center;" class="fa fa-pencil"></i></a>
								</display:column>
							</sec:authorize>
							<sec:authorize
								access="hasAnyRole('ROLE_COMPANY','ROLE_DELETE_CASE')">
								<display:column url="delete-casemanagement" media="html"
									paramId="caseId" paramProperty="caseId" title="Delete">
									<a href="delete-casemanagement?caseId=${data.caseId}"
										onclick="return confirm('Are you sure you want to Delete?')"><i
										style="text-align: center;" class="fa fa-trash"></i></a>
								</display:column>
							</sec:authorize>
						</display:table>
					</div>
				</div>
</c:if>
				<nav style="text-align: center;">
					<ul class="pagination pagination-theme  no-margin center"
						style="margin-left: 575px;">
						<c:if test="${caseLists.currentPage gt 1}">
							<li><a
								href="view-casemanagement?page=1&searchElement=${searchElement}"><span><i
										class="fa fa-angle-double-left" aria-hidden="true"></i> </span></a></li>
							<li><a
								href="view-casemanagement?page=${caseLists.currentPage - 1}&searchElement=${searchElement}"><span><i
										class="fa fa-angle-left" aria-hidden="true"></i> </span></a></li>
						</c:if>
						<c:forEach items="${caseLists.noOfPages}" var="i">
							<c:choose>
								<c:when test="${caseLists.currentPage == i}">
									<li class="active"><a
										style="color: #fff; background-color: #34495e">${i}</a></li>
								</c:when>
								<c:otherwise>
									<li><a
										href="view-casemanagement?page=${i}&searchElement=${searchElement}">${i}</a></li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
						<c:if test="${caseLists.currentPage lt caseLists.totalPages}">
							<li><a
								href="view-casemanagement?page=${caseLists.currentPage + 1}&searchElement=${searchElement}"><span><i
										class="fa fa-angle-right" aria-hidden="true"></i> </span></a></li>
							<li><a
								href="view-casemanagement?page=${caseLists.lastRecordValue}&searchElement=${searchElement}"><span><i
										class="fa fa-angle-double-right" aria-hidden="true"></i> </span></a></li>
						</c:if>
					</ul>
				</nav>

			
		</div>
	</div>

</body>
</div>
</html>
