

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
<script type="text/javascript">
	function pagesearch(pageNo) {
		alert(pageNo);
		$('#myForm').append(
				'<input type="hidden" name="page" value="'+pageNo+'">');
		document.searchName.submit();
	}
</script>
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
				style="margin-top: -10px;">List Contact</h3>
			<br> <a href="create-contact" title="Create New Employee"
				style="font-size: 26px; color: #7cb228; margin-left: 95%;"> <i
				class="fa fa-plus-circle"></i>
			</a> 
			
			<form:form id="myForm" method="post" class="login-form clearfix"
				action="search-contacts" name="searchName"
				commandName="searchContacts">
				<div class="row"
					style="border: 4px solid #e6e6e6; margin: 15px 15px 15px 15px; background-color: #e1e1e1">

					<div class="col-md-3">
						<div class="form-group ">
							<label class="hidden-xs"></label>
							<form:input type="ntext" class="form-control" path="email"
								placeholder="Email Address"
								style="height:35px;font-weight: 700;"></form:input>

						</div>
					</div>


					<div class="col-md-3">
						<div class="form-group ">
							<label class="hidden-xs"></label>
							<form:input type="ntext" class="form-control" path="firstname"
								placeholder="firstname" style="height:35px;font-weight: 700;"></form:input>

						</div>
					</div>
					<%--  <div class="col-md-3">
						<div class="form-group ">
							<label class="hidden-xs"></label>
							<form:select type="text" path="opportunityBO.firstName"
								class="form-control required">
								<form:option value="">-- Select --   </form:option>
								<form:options items="${opportunitylist}" itemLabel="firstName"
									itemValue="opportunityId" />

							</form:select>
						</div>
					</div>  --%>


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
			<c:if test="${!empty contactlist.list}">
			
				 <div class="col-sm-12">
					<div class="pi-responsive-table-sm">
						<div class="pi-section-w pi-section-white piTooltips">
							<display:table id="data" name="${contactlist.list}"
								requestURI="/view-campaign" pagesize="10" export="false"
								class="pi-table pi-table-complex pi-table-hovered pi-round pi-table-shadow pi-table-all-borders">
								<%-- <display:column property="sNo" title="Sno" /> --%>
								<display:column property="firstname" title="First Name" />
								<display:column property="opportunityBO.firstName"
									title="Opportunity Name" />
								<display:column property="email" title="emailId" />
								<display:column property="phone" title="phone" />
								 <display:column property="accountBO.accountName" title="Account Name" /> 

								<display:column url="view-contact-details" media="html"
									paramId="contactId" paramProperty="contactId" title="View">
									<a href="view-contact-details?id=${data.contactId}"><i
										style="text-align: center;" class="fa fa-eye"></i></a>
								</display:column>
								<display:column url="edit-contact" media="html"
									paramId="contactId" paramProperty="contactId" title="Edit">
									<a href="edit-contact?id=${data.contactId}"><i
										style="text-align: center;" class="fa fa-pencil"></i></a>
								</display:column>
								<display:column url="delete-contact" media="html"
									paramId="contactId" paramProperty="contactId" title="Delete">
									<a href="delete-contact?id=${data.contactId}"
										onclick="return confirm('Are you sure you want to Delete?')"><i
										style="text-align: center;" class="fa fa-trash"></i></a>

								</display:column>
							</display:table>
						</div>
					</div>
				</div>
				</c:if>
				<nav style="text-align: center;">
					<ul class="pagination pagination-theme  no-margin center"
						style="margin-left: 575px;">
						
						
						<c:if test="${contactlist.currentPage gt 1}">
						<li><a href="view-contact?page=1"><span><i
									class="fa fa-angle-double-left" aria-hidden="true"></i> </span></a></li>
						<li><a
							href="view-contact?page=${contactlist.currentPage - 1}"><span><i
									class="fa fa-angle-left" aria-hidden="true"></i> </span></a></li>
					</c:if>
						<c:forEach items="${contactlist.noOfPages}" var="i">
							<c:choose>
								<c:when test="${contactlist.currentPage == i}">
									<li class="active"><a
										style="color: #fff; background-color: #34495e">${i}</a></li>
								</c:when>
								<c:otherwise>
									<li><a href="view-contact?page=${i}">${i}</a></li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
						<c:if test="${contactlist.currentPage lt contactlist.totalPages}">
							<li><a
								href="view-contact?page=${contactlist.currentPage + 1}"><span><i
										class="fa fa-angle-right" aria-hidden="true"></i> </span></a></li>
							<li><a
								href="view-contact?page=${contactlist.lastRecordValue}"><span><i
										class="fa fa-angle-double-right" aria-hidden="true"></i> </span></a></li>
						</c:if>
					</ul>
				</nav>
			
			



		</div>
	</div>
	<br />
</div>