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

<div class="box-list">
<c:if test="${not empty successMessage}">
					<div class="alert alert-success">
						<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
						<strong>Success:</strong>
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
				
				 
	<div class="item">
		<div class="row ">
			<h3 class="text-center no-margin titleunderline underline"
				style="margin-top: -10px;">List Customers</h3>

			<a href="create-customers"
				style="font-size: 26px; color: #7cb228; margin-left: 95%;"> <i
				class="fa fa-plus-circle" title="Create New Customer"></i>
			</a> <br>
			
			<c:if test="${empty reportSearch}">
				<!--Start form search -->

				<form:form id="myForm" method="post" class="login-form clearfix"
					action="searchcustomer-report" commandName="searchjobseeker">
					<div class="row"
						style="border: 4px solid #e6e6e6; margin: 15px 15px 15px 15px; background-color: #e1e1e1">
						<div class=" col-md-4">
							<div class="form-group home-left">
								<label class="hidden-xs"></label>
								<form:input type="ntext" class="form-control" path="companyName"
									placeholder="Company Name" escapeXml="false"
									style="height: 35px;font-weight: 700;"></form:input>
							</div>
						</div>
						<input type="hidden" value="true" name="search" />

						<div class="col-md-4">
							<div class="form-group  search-center">
								<label class="hidden-xs"></label>
								<form:input type="ntext" class="form-control"
									path="emailAddress" placeholder="Email Address"
									style="height:35px;font-weight: 700;"></form:input>

							</div>
						</div>

						<div class=" col-md-3">
							<div class="form-group ">
								<label class="hidden-xs"></label>
								<form:input type="ntext" class="form-control" path="mobileNo"
									placeholder="Mobile Number" maxlength="10"
									style="height:35px;font-weight: 700;"></form:input>
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
			</c:if>
			<br>
			<c:if test="${!empty listClients.list}">
				<div class="col-sm-12" style="margin-top: -27px">
					<div class="pi-responsive-table-sm">
						<div class="pi-section-w pi-section-white piTooltips">
							<display:table id="data" name="${listClients.list}"
								requestURI="/view-customers" pagesize="10" export="false"
								class="pi-table pi-table-complex pi-table-hovered pi-round pi-table-shadow pi-table-all-borders">

								<display:column property="sNo" title="S No" />

								<c:if test="${data.status == 'opened'}">
									<td style="color: green;"><display:column
											property="firstName" title="Name" /></td>
								</c:if>
								<c:if test="${data.status == 'Closed'}">
									<td style="color: #FA5858;"><display:column
											property="firstName" title="Name" /></td>

								</c:if>
								<display:column property="companyName" title="Company Name" />
								<display:column property="emailAddress" title="Email Address" />
								<sec:authorize access="hasRole('ROLE_ADMIN')">
									<display:column property="adminUserBO.name" title="Assinged To" />
								</sec:authorize>
								<display:column property="mobileNo" title="MobileNo" />
								
							</display:table>
						</div>
					</div>
				</div>
			</c:if>
			<nav style="text-align: center;">
				<ul class="pagination pagination-theme  no-margin center"
					style="margin-left: 575px;">
					<c:if test="${listClients.currentPage gt 1}">
						<li><a href="view-report?page=1"><span><i
									class="fa fa-angle-double-left" aria-hidden="true"></i> </span></a></li>
						<li><a
							href="view-report?page=${listClients.currentPage - 1}"><span><i
									class="fa fa-angle-left" aria-hidden="true"></i> </span></a></li>
					</c:if>
					<c:forEach items="${listClients.noOfPages}" var="i">
						<c:choose>
							<c:when test="${listClients.currentPage == i}">

								<li class="active"><a
									style="color: #fff; background-color: #34495e">${i}</a></li>
							</c:when>
							<c:otherwise>
								<li><a href="view-report?page=${i}">${i}</a></li>
							</c:otherwise>
						</c:choose>
					</c:forEach>
					<c:if test="${listClients.currentPage lt listClients.totalPages}">
						<li><a
							href="view-report?page=${listClients.currentPage + 1}"><span><i
									class="fa fa-angle-right" aria-hidden="true"></i> </span></a></li>
						<li><a
							href="view-report?page=${listClients.lastRecordValue}"><span><i
									class="fa fa-angle-double-right" aria-hidden="true"></i> </span></a></li>
					</c:if>
				</ul>
			</nav>
			
		</div>
	</div>
	
</div>
</html>