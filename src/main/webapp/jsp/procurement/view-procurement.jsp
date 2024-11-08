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
<body>
	<script type="text/javascript">
		function pageNavigation(pageNo) {
			$('#myForm').append(
					'<input type="hidden" name="page" value="'+pageNo+'">');
			document.searchProcurement.submit();
		}
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
	<!-- <script>
		$(document).ready(function() {

			$('#btnsubmit').click(function(e) {
				//SlaCode
				var isValid = true;
				//var workItem = $('#serviceId').val();
				var workItem = $("#serviceIds").val();
				console.log(workItem);
				//var workItem = document.getElementById("serviceId").value;
				/* var workItems = $("#assignedTo").val();
				console.log(workItems);
				var workItem = document.getElementById("serviceId").value;
				console.log(workItem); */
				if (workItem == 'Select') {
					isValid = false;
					$("#serviceNameErr").show();
					$("#serviceNameErr").html("Please Select Products");
					
					});
				} else {
					$('#serviceNameErr').hide();
					$('#serviceIds').css({
						"border" : "",
						"background" : ""
					});
				}

				if (isValid == false)
					e.preventDefault();
			});
		});
	</script> -->

	<div class="box-list">
		<div class="item">
			<div class="row ">
				<h3 class="text-center no-margin titleunderline underline">List
					Procurement</h3>
				<sec:authorize
					access="hasAnyRole('ROLE_COMPANY','ROLE_CREATE_PROCUREMENT')">
					<div class="row ">
						<a href="create-procurement"
							style="font-size: 26px; color: #7cb228; margin-left: 97%;"> <i
							class="fa fa-plus-circle" title="Create New Procurement"></i>
						</a>
					</div>
				</sec:authorize>
				<form:form id="myForm" method="post" class="login-form clearfix"
					action="search-procurement" commandName="searchProcurement">
					<div class="row"
						style="border: 4px solid #e6e6e6; margin: 15px 15px 15px 15px; background-color: #e1e1e1">
						<%-- <div class=" col-md-3">
							<div class="form-group ">
								<label class="hidden-xs"></label>
								<form:select type="text" path="productServiceBO.serviceName"
									id="serviceid" class="form-control "
									style="height: 35px;font-weight: 700;
								   text-transform: capitalize; margin-top: 23px;">

									<form:option value="Select">-- Select Products --</form:option>
									<c:if
										test="${not empty searchProcurement.productServiceBO.serviceName}">
										<form:option
											value="${searchProcurement.productServiceBO.serviceId}">${searchProcurement.productServiceBO.serviceName}</form:option>
									</c:if>
									<form:options items="${productlist}" itemLabel="serviceName"
										itemValue="serviceId" />
								</form:select>
								<form:errors path="productServiceBO.serviceName" class="error" />
								<div id="serviceNameErr" style="color: red;"></div>
							</div>
						</div> --%>

						<div class="col-sm-3">
							<div class="form-group">
								<label path="productServiceBO.serviceName"> </label>
								<form:select type="text" id="serviceIds"
									path="productServiceBO.serviceName"
									class="form-control required"
									style="height: 35px;font-weight: 700;
								   text-transform: capitalize; margin-top: 23px;">
									<form:option value="">-- Select --</form:option>
									<c:if
										 test="${not empty searchProcurement.productServiceBO.serviceName}">
										<form:option
											value="${searchProcurement.productServiceBO.serviceId}">${searchProcurement.productServiceBO.serviceName}</form:option>
									</c:if>
									<form:options items="${productlist}" itemLabel="serviceName"
										itemValue="serviceId" />
								</form:select>
							<form:errors path="productServiceBO.serviceName" class="error" />
								 <div id="serviceNameErr" style="color: red;"></div> 
							</div> 
						</div>
						<div class=" col-md-3">
							<div class="form-group ">
								<label class="hidden-xs"></label> <select
									name="procurementStatus" id="procurementStatusId"
									class="form-control "
									style="height: 35px; font-weight: 700; text-transform: capitalize; margin-top: 23px;">
																			
									<option value="Select">-- Select Procurement Status --</option>
									<option value="Approved">Approved</option>
									<option value="Rejected">Rejected</option>
								</select>
							</div>
						</div>

						<div class=" col-md-1" style="padding-bottom: 0px;">
							<div class="form-group home-right">
								<label class="hidden-xs">&nbsp;</label>
								<button class="btn btn-theme btn-success btn-block"
									id="btnsubmit"
									style="padding: 6px 5px; background-color: #7cb228; border-color: #7cb228;">
									<small><i class="fa fa-search" aria-hidden="true"
										style="font-size: 20px;"></i></small>
								</button>
							</div>
						</div>
					</div>
				</form:form>
				<c:if test="${not empty rejectCount}">
					<div class="col-sm-12" style="margin-top: 20px">
						<p>
							<a class="btn btn-theme btn-xs btn-default"
								style="color: #1b1818; font-weight: bold;"><c:out
									value="${rejectCount}"></c:out></a> <strong class="color-black">Supplier
								Found</strong>
						</p>
					</div>
				</c:if>
				<c:if test="${!empty rejectedprocurementList}">
					<div class="col-sm-12">
						<div class="pi-responsive-table-sm">
							<div class="pi-section-w pi-section-white piTooltips">
								<display:table id="data" name="${rejectedprocurementList.list}"
									requestURI="/view-procurement" export="false"
									class="pi-table pi-table-complex pi-table-hovered pi-round pi-table-shadow pi-table-all-borders">
									<display:column title="S.No" headerClass="sortable"
										media="html">
										<c:out value="${data_rowNum}"></c:out>
									</display:column>
									<display:column property="productServiceBO.serviceName"
										title="ProductName" />
									<display:column property="supplierBO.supplierName"
										title="SupplierName" />
									<display:column url="procurement-tracking-status" media="html"
										paramId="procurementBO.procurementId"
										paramProperty="procurementBO.procurementId" title="View">
										<a
											href="procurement-tracking-status?procurementId=${data.procurementBO.procurementId}"><i
											style="text-align: center;" class="fa fa-eye"></i></a>
									</display:column>

								</display:table>


							</div>
						</div>
					</div>
				</c:if>
				<c:if test="${not empty approvedCount}">
					<div class="col-sm-12" style="margin-top: 20px">
						<p>
							<a class="btn btn-theme btn-xs btn-default"
								style="color: #1b1818; font-weight: bold;"><c:out
									value="${approvedCount}"></c:out></a> <strong class="color-black">Supplier
								Found</strong>
						</p>
					</div>
				</c:if>
				<c:if test="${!empty approvedprocurementList}">
					<div class="col-sm-12">
						<div class="pi-responsive-table-sm">
							<div class="pi-section-w pi-section-white piTooltips">
								<display:table id="data" name="${approvedprocurementList.list}"
									requestURI="/view-procurement" export="false"
									class="pi-table pi-table-complex pi-table-hovered pi-round pi-table-shadow pi-table-all-borders">
									<display:column title="S.No" headerClass="sortable"
										media="html">
										<c:out value="${data_rowNum}"></c:out>
									</display:column>
									<display:column property="productServiceBO.serviceName"
										title="ProductName" />
									<display:column property="supplierBO.supplierName"
										title="SupplierName" />
									<display:column property="expectedDate" title="ExpectedDate" />

									<display:column url="procurement-tracking-status" media="html"
										paramId="procurementBO.procurementId"
										paramProperty="procurementBO.procurementId" title="View">
										<a
											href="procurement-tracking-status?procurementId=${data.procurementBO.procurementId}"><i
											style="text-align: center;" class="fa fa-eye"></i></a>
									</display:column>

								</display:table>


							</div>
						</div>
					</div>
				</c:if>
				<c:if test="${not empty procurementtotalCount}">
					<div class="col-sm-12" style="margin-top: 20px">
						<p>
							<a class="btn btn-theme btn-xs btn-default"
								style="color: #1b1818; font-weight: bold;"><c:out
									value="${procurementtotalCount}"></c:out></a> <strong
								class="color-black">procurement Search Found</strong>
						</p>
					</div>
				</c:if>
				<c:if test="${not empty totalCount}">
					<div class="col-sm-12" style="margin-top: 20px">
						<p>
							<a class="btn btn-theme btn-xs btn-default"
								style="color: #1b1818; font-weight: bold;"><c:out
									value="${totalCount}"></c:out></a> <strong class="color-black">Procurement
								Found</strong>
						</p>
					</div>
				</c:if>
				<c:if test="${!empty procurementList}">
					<div class="col-sm-12">
						<div class="pi-responsive-table-sm">
							<div class="pi-section-w pi-section-white piTooltips">
								<display:table id="data" name="${procurementList.list}"
									requestURI="/view-procurement" export="false"
									class="pi-table pi-table-complex pi-table-hovered pi-round pi-table-shadow pi-table-all-borders">
									<display:column title="S.No" headerClass="sortable"
										media="html">
										<c:out value="${data_rowNum}"></c:out>
									</display:column>
									<display:column property="productServiceBO.serviceName"
										title="ProductName" />
									<display:column property="supplierBO.supplierName"
										title="SupplierName" />
									<display:column property="availableStock"
										title="AvailableStock" />
									<display:column property="expectedDate" title="ExpectedDate" />
									<sec:authorize
										access="hasAnyRole('ROLE_COMPANY','ROLE_UPDATE_PRODUCT')">
										<display:column url="edit-procurement" media="html"
											paramId="id" paramProperty="procurementId" title="Edit">
											<a
												href="edit-procurement?procurementId=${data.procurementId}"><i
												style="text-align: center;" class="fa fa-pencil"></i></a>
										</display:column>
									</sec:authorize>
									<sec:authorize
										access="hasAnyRole('ROLE_COMPANY','ROLE_DELETE_PROCUREMENT')">
										<display:column url="delete-Procurement" media="html"
											paramId="id" paramProperty="procurementId" title="Delete">
											<a
												href="delete-Procurement?procurementId=${data.procurementId}"
												onclick="return confirm('Are you sure you want to Delete?')"><i
												style="text-align: center;" class="fa fa-trash"></i></a>
										</display:column>
									</sec:authorize>
									<sec:authorize
										access="hasAnyRole('ROLE_COMPANY','ROLE_DELETE_PROCUREMENT')">
										<c:if test="${ data.approveBO.approveId>0}">
											<display:column url="approve-procurement" media="html"
												paramId="procurementId" paramProperty="procurementId"
												title="Approve">
												<a
													href="approve-procurement?procurementId=${data.procurementId}"><i
													style="text-align: center;" class="fa fa-check-square"></i></a>
											</display:column>
										</c:if>
									</sec:authorize>

									<sec:authorize
										access="hasAnyRole('ROLE_COMPANY','ROLE_DELETE_PROCUREMENT')">
										<c:if test="${empty  data.approveBO}">
											<display:column url="approve-procurement" media="html"
												paramId="procurementId" paramProperty="procurementId"
												title="Approve">
												<a
													href="approve-procurement?procurementId=${data.procurementId}"><i
													style="text-align: center;" class="fa fa-circle-thin"></i></a>
											</display:column>
										</c:if>
									</sec:authorize>

									<sec:authorize
										access="hasAnyRole('ROLE_COMPANY','ROLE_DELETE_PROCUREMENT')">
										<display:column url="reject-procurement" media="html"
											paramId="procurementId" paramProperty="procurementId"
											title="Reject">
											<a
												href="reject-procurement?procurementId=${data.procurementId}"><i
												style="text-align: center;" class="fa fa-circle-thin"></i></a>
										</display:column>
									</sec:authorize>

									<sec:authorize
										access="hasAnyRole('ROLE_COMPANY','ROLE_DELETE_PROCUREMENT')">
										<display:column url="procurement-tracking-status" media="html"
											paramId="procurementId" paramProperty="procurementId"
											title="View">
											<a
												href="procurement-tracking-status?procurementId=${data.procurementId}"><i
												style="text-align: center;" class="fa fa-eye"></i></a>
										</display:column>
									</sec:authorize>
									<%-- <display:column url="activity-procurement" media="html"
									paramId="procurementId" paramProperty="procurementId"
									title="View">
									<a href="activity-procurement?procurementId=${data.procurementId}"><i
										style="text-align: center;" class="fa fa-eye"></i></a>
								</display:column>
								    --%>

								</display:table>


							</div>
						</div>
					</div>
				</c:if>
				<nav style="text-align: center;">
					<ul class="pagination pagination-theme  no-margin center"
						style="margin-left: 575px;">
						<c:if test="${procurementList.currentPage gt 1}">
							<li><a
								href="view-procurement?page=1&searchElement=${searchElement}"><span><i
										class="fa fa-angle-double-left" aria-hidden="true"></i> </span></a></li>
							<li><a
								href="view-procurement?page=${procurementList.currentPage - 1}&searchElement=${searchElement}"><span><i
										class="fa fa-angle-left" aria-hidden="true"></i> </span></a></li>
						</c:if>
						<c:forEach items="${procurementList.noOfPages}" var="i">
							<c:choose>
								<c:when test="${procurementList.currentPage == i}">

									<li class="active"><a
										style="color: #fff; background-color: #34495e">${i}</a></li>
								</c:when>
								<c:otherwise>
									<li><a
										href="view-procurement?page=${i}&searchElement=${searchElement}">${i}</a></li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
						<c:if
							test="${procurementList.currentPage lt procurementList.totalPages}">
							<li><a
								href="view-procurement?page=${procurementList.currentPage + 1}&searchElement=${searchElement}"><span><i
										class="fa fa-angle-right" aria-hidden="true"></i> </span></a></li>
							<li><a
								href="view-procurement?page=${procurementList.lastRecordValue}&searchElement=${searchElement}"><span><i
										class="fa fa-angle-double-right" aria-hidden="true"></i> </span></a></li>
						</c:if>
					</ul>
				</nav>
				<%--   </c:if>   --%>


			</div>
		</div>
	</div>
</body>
</html>