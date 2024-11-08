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
<script>
	$(document).ready(function() {
		$('#search').click(function(e) {
			var isValid = true;

			var name = $('#firstId').val();
			if (name == '') {
				isValid = false;
				$("#firstErr").show();
				$("#firstErr").html("Please enter name ");
				$("#firstId").css({
					"border" : "1px solid red",

				});

			} else {
				$('#firstErr').hide();
				$('#firstId').css({

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
				style="margin-top: -10px;">List LeadReport</h3>
			<sec:authorize access="hasAnyRole('ROLE_COMPANY')">
				<div class="row ">
					<a href="create-leads"
						style="font-size: 26px; color: #7cb228; margin-left: 97%;"> <i
						class="fa fa-plus-circle" title="Create New Lead"></i>
					</a>
				</div>
			</sec:authorize>

			<form:form id="myForm" method="GET" class="login-form clearfix"
				action="search-leadss" modelAttribute="searchLeads">
				
			
				<div class="row"
					style="border: 4px solid #e6e6e6; margin: 15px 15px 15px 15px; background-color: #e1e1e1">
					<div class=" col-md-3">
						<div class="form-group home-left">
							<label class="hidden-xs"></label>

							<form:input type="ntext" class="form-control" path="firstName"
								id="firstId" placeholder="Name " escapeXml="false"
								style="height: 35px;font-weight: 700;"></form:input>
							<form:errors path="firstName" class="error" />
							<div id="firstErr" style="color: red;"></div>
						</div>
					</div>
					<input type="hidden" value="true" name="search" />
					
					<div class="col-md-3">
						<div class="form-group ">
							<label class="hidden-xs"></label>
							<form:input type="ntext" class="form-control" path="emailAddress"
								placeholder="Email Address"
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
					<div class="col-md-2 fs-mobile-search">
						<div class="form-group ">
							<label class="hidden-xs"></label>
							<form:select type="text" path="campaignBO.campaignName"
								id="compId" class="form-control "
								style="height: 35px;font-weight: 700;
								   text-transform: capitalize;">
								<form:option value="">-- Select Campaign --   </form:option>
								<form:options items="${listcampaign}" itemLabel="campaignName"
									itemValue="campaignId" />
							</form:select>
							<form:errors path="campaignBO.campaignName" class="error" />
							<div id="campErr" style="color: red;"></div>
						</div>
					</div>

					<div class=" col-md-1" style="padding-bottom: 0px;">
						<div class="form-group home-right">
							<label class="hidden-xs"></label>
							<button class="btn btn-theme btn-success btn-block" id="ssearch"
								style="padding: 6px 5px; background-color: #7cb228; border-color: #7cb228;">
								<small><i class="fa fa-search" aria-hidden="true"
									style="font-size: 20px;"></i></small>
							</button>
						</div>
					</div>
				</div>
			</form:form>
			<c:if test="${not empty totalsearchleads}">
				<div class="col-sm-12" style="margin-top: 20px">
					<p>
						<a class="btn btn-theme btn-xs btn-default"
							style="color: #1b1818; font-weight: bold;"><c:out
								value="${totalsearchleads}"></c:out></a> <strong
							class="color-black">Leads Found</strong>
					</p>
				</div>
			</c:if>
			<c:if test="${not empty totalleadlist}">
				<div class="col-sm-12" style="margin-top: 20px">
					<p>
						<a class="btn btn-theme btn-xs btn-default"
							style="color: #1b1818; font-weight: bold;"><c:out
								value="${totalleadlist}"></c:out></a> <strong class="color-black">Lead
							Found</strong>
					</p>
				</div>
			</c:if>
			<c:if test="${!empty listLeads.list}">
				<div class="col-sm-12">
					<div class="pi-responsive-table-sm">
						<div class="pi-section-w pi-section-white piTooltips">
							<display:table id="data" name="${listLeads.list}"
								requestURI="/view-leads" export="false"
								class="pi-table pi-table-complex pi-table-hovered pi-round pi-table-shadow pi-table-all-borders">

								<display:column property="sNo" title="SNo" />
								<display:column property="firstName" title="Name" />
								<display:column property="emailAddress" title="Email Address" />
								<%-- <display:column property="address" title="Address" /> --%>
								<sec:authorize access="hasRole('ROLE_ADMIN')">
									<display:column property="adminLoginBO.name" title="AssignedTo" />
								</sec:authorize>
								<display:column property="mobileNo" title="MobileNo" />
								<display:column property="campaignBO.campaignName"
									title="CampaignName" />

								<%-- <sec:authorize
									access="hasAnyRole('ROLE_ADMIN','ROLE_COMPANY','ROLE_LEADS_REPORTS')">
								 <display:column url="edit-user" media="html" paramId="id"
									paramProperty="leadsId" title="Edit">
									<a href="edit-leads?leadsId=${data.leadsId}"><i
										style="text-align: center;" class="fa fa-pencil"></i></a>
								</display:column>
								</sec:authorize>
								<sec:authorize
									access="hasAnyRole('ROLE_ADMIN','ROLE_COMPANY','ROLE_LEADS_REPORTS')">
								<display:column url="delete-user" media="html" paramId="id"
									paramProperty="leadsId" title="Delete">
									<a href="delete-leads?leadsId=${data.leadsId}"
										onclick="return confirm('Are you sure you want to Delete?')"><i
										style="text-align: center;" class="fa fa-trash"></i></a>
								</display:column>
								</sec:authorize> --%>

								<sec:authorize
									access="hasAnyRole('ROLE_COMPANY','ROLE_LEADS_REPORTS')">
									<display:column url="customer-tracking-status" media="html"
										paramId="id" paramProperty="id" title="View">
										<a href="leads-tracking-status?leadsId=${data.leadsId}"> <i
											style="text-align: center; color: black;" class="fa fa-eye"></i>
										</a>
									</display:column>
								</sec:authorize>

								<%-- <display:column url="/convert-customer" media="html"
									paramId="id" paramProperty="leadsId" title="L & C">
									<i style="text-align: center;" class="fa fa-exchange"></i>
								</display:column>  --%>
							</display:table>
						</div>
					</div>
				</div>
			</c:if>
			<nav style="text-align: center;">
				<ul class="pagination pagination-theme  no-margin center"
					style="margin-left: 575px;">
					<c:if test="${listLeads.currentPage gt 1}">
						<li><a href="view-leads-reports?page=1"><span><i
									class="fa fa-angle-double-left" aria-hidden="true"></i> </span></a></li>
						<li><a
							href="view-leads-reports?page=${listLeads.currentPage - 1}"><span><i
									class="fa fa-angle-left" aria-hidden="true"></i> </span></a></li>
					</c:if>
					<c:forEach items="${listLeads.noOfPages}" var="i">
						<c:choose>
							<c:when test="${listLeads.currentPage == i}">

								<li class="active"><a
									style="color: #fff; background-color: #34495e">${i}</a></li>
							</c:when>
							<c:otherwise>
								<li><a href="view-leads-reports?page=${i}">${i}</a></li>
							</c:otherwise>
						</c:choose>
					</c:forEach>
					<c:if test="${listLeads.currentPage lt listLeads.totalPages}">
						<li><a
							href="view-leads-reports?page=${listLeads.currentPage + 1}"><span><i
									class="fa fa-angle-right" aria-hidden="true"></i> </span></a></li>
						<li><a
							href="view-leads-reports?page=${listLeads.lastRecordValue}"><span><i
									class="fa fa-angle-double-right" aria-hidden="true"></i> </span></a></li>
					</c:if>
				</ul>
			</nav>
		</div>
	</div>
	<br />
</div>
</html>