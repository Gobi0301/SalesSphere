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
<title>List Of Companies</title>
</head>
<body>
	<div class="box-list">		 
		<div class="item">
			<div class="row ">
				<h3 class="text-center no-margin titleunderline underline"
					style="margin-top: -10px;">List Company</h3>
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
				<sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_CREATE_COMPANY')">
				<div class="row ">
					<a href="create-company"
						style="font-size: 26px; color: #7cb228; margin-left: 95%;"> <i
						class="fa fa-plus-circle" title="Create New Product"></i>
					</a>
				</div>
				</sec:authorize>
				
			   <form:form id="myForm" method="post" class="login-form clearfix" name="pageNo"
				action="search-company"  modelAttribute="searchCompany">
				<div class="row"
					style="border: 4px solid #e6e6e6; margin:15px 15px 15px 15px; background-color: #e1e1e1">
					<div class=" col-md-3">
						<div class="form-group home-left">
							<label class="hidden-xs"></label>
							<form:input type="text" class="form-control" placeholder="Company Name" path="companyName" id="companyName"
								
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
				
				
				<c:if test="${not empty totalCountOfCompany}">

                        <div class="col-sm-12" style="margin-top: 20px">
                            <p>
                                <a class="btn btn-theme btn-xs btn-default"
                                    style="color: #1b1818; font-weight: bold;"><c:out
                                        value="${totalCountOfCompany}"></c:out></a> <strong
                                    class="color-black">Company Found</strong>
                            </p>
                        </div>

                    </c:if>
                    <c:if test="${not empty totalSearchCount}">
                        <div class="col-sm-12" style="margin-top: 20px">
                            <p><a class="btn btn-theme btn-xs btn-default"
                                    style="color: #1b1818; font-weight: bold;"><c:out
                                        value="${totalSearchCount}"></c:out></a> <strong
                                    class="color-black">Company Found</strong>
                            </p>
                        </div>
                    </c:if>

				<c:if test="${!empty companyList}">
					<div class="col-sm-12">
						<div class="pi-responsive-table">
							<div class="pi-section-w pi-section-white piTooltips">
								<display:table id="data" name="${companyList.list}"
									requestURI="/view-company" export="false"
									class="pi-table pi-table-complex pi-table-hovered pi-round pi-table-shadow pi-table-all-borders">

									<display:column property="sNo" title="SNo">
									<c:out value="${data_rowNum}" />
									</display:column>
									<display:column property="companyName" title="Company Name" />
									<display:column property="industryType" title="Industry Type" />
									<display:column property="contactPerson" title="Contact Person" />
									<display:column property="companyGstNo" title="Company GST No" />
									<display:column property="companyEmail" title="Company Email" />
									<display:column property="contactNumber" title="Contact Number" />
									<display:column property="website" title="Website" />
									<%-- <display:column property="availableStocks"
										title="AvailableStocks" />
									<display:column property="productTypesbO.productTypes"
										title="ProductTypes" /> --%>
									<sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_COMPANY','ROLE_UPDATE_COMPANY')">
									<display:column url="edit-company" media="html" paramId="id"
										paramProperty="companyId" title="Edit">
										<a href="edit-company?companyId=${data.companyId}"><i
											style="text-align: center;" class="fa fa-pencil"></i></a>
									</display:column>
									</sec:authorize>
									<sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_DELETE_COMPANY')">
									<display:column url="delete-company" media="html" paramId="id"
										paramProperty="companyId" title="Delete">
										<a href="delete-company?companyId=${data.companyId}"
											onclick="return confirm('Are you sure you want to Delete?')"><i
											style="text-align: center;" class="fa fa-trash"></i></a>
									</display:column>
									</sec:authorize>
									<sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_COMPANY','ROLE_VIEW_COMPANY')">
									<display:column url="customer-tracking-status" media="html"
										paramId="id" paramProperty="companyId" title="View">
										<a href="view-company-details?companyId=${data.companyId}"> <i
											style="text-align: center; color: black;" class="fa fa-eye"></i>
										</a>
									</display:column>
									</sec:authorize>
								</display:table>
							</div>
						</div>
					</div>
				
             <nav style="text-align: center;">
					<ul class="pagination pagination-theme  no-margin center"
						style="margin-left: 575px;">
						<c:if test="${companyList.currentPage gt 1}">
							<li><a
								href="view-company?page=1&searchCompanyName=${searchCompanyName}"><span><i
										class="fa fa-angle-double-left" aria-hidden="true"></i> </span></a></li>
							<li><a
								href="view-company?page=${companyList.currentPage - 1}&searchCompanyName=${searchCompanyName}"><span><i
										class="fa fa-angle-left" aria-hidden="true"></i> </span></a></li>
						</c:if>
						<c:forEach items="${companyList.noOfPages}" var="i">
							<c:choose>
								<c:when test="${companyList.currentPage == i}">

									<li class="active"><a
										style="color: #fff; background-color: #34495e">${i}</a></li>
								</c:when>
								<c:otherwise>
									<li><a
										href="view-company?page=${i}&searchCompanyName=${searchCompanyName}">${i}</a></li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
						<c:if test="${companyList.currentPage lt companyList.totalPages}">
							<li><a
								href="view-company?page=${companyList.currentPage + 1}&searchCompanyName=${searchCompanyName}"><span><i
										class="fa fa-angle-right" aria-hidden="true"></i> </span></a></li>
							<li><a
								href="view-company?page=${companyList.lastRecordValue}&searchCompanyName=${searchCompanyName}"><span><i
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

