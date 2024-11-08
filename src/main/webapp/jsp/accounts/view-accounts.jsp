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
	function pageNavigation(pageNo) {
		$('#myAccountSearchForm').append(
				'<input type="hidden" name="page" value="'+pageNo+'">');
		document.accountSearch.submit();
	}
</script>
<script>
	$(document).ready(function() {
		$('#search').click(function(e) {
			var isValid = true;

			var accountName = $('#accountNameId').val();
			if (accountName == '') {
				isValid = false;
				$("#accountNameErr").show();
				$("#accountNameErr").html("Please enter Account Name ");
				$("#accountNameId").css({
					"border" : "1px solid red",

				});

			} else if (!/^[a-zA-Z\s]*$/g.test(userName)) {
				isValid = false;
				$("#accountNameErr").show();
				$("#accountNameErr").html("Please enter characters only");
				$("#accountNameId").css({
					"border" : "1px solid red",

				});

			} else {
				$('#accountNameErr').hide();
				$('#accountNameId').css({

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
	<c:if test="${not empty successMessages}">
		<div class="alert alert-success">
			<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
			<strong>Success:</strong>
			<c:out value="${successMessages}"></c:out>
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
				style="margin-top: -10px;">List Account</h3>

			<sec:authorize
				access="hasAnyRole('ROLE_COMPANY','ROLE_CREATE_ACCOUNT')">
				<div class="row ">
					<a href="create-account" title="Create New Account"
						style="font-size: 26px; color: #7cb228; margin-left: 95%;"> <i
						class="fa fa-plus-circle"></i>
					</a>
				</div>
			</sec:authorize>
			<form:form id="myAccountSearchForm" method="post"
				class="login-form clearfix" name="accountSearch"
				action="search-account" commandName="searchAccount">

				<%-- <div class="row"
					style="border: 4px solid #e6e6e6; margin: 15px 15px 15px 15px; background-color: #e1e1e1">
					<div class=" col-md-4">
						<div class="form-group home-left">
							<!-- <label class="hidden-xs"></label> -->
							<form:input type="ntext" class="form-control" path="accountName"
								placeholder="Account Name" escapeXml="false"
								style="height: 35px;font-weight: 700;"></form:input>
						</div>
					</div>
					<div class="col-md-4">
						<div class="form-group ">
							<form:select type="text"  path="id"
								class="form-control required">
								<form:option value="">Select User </form:option>
								<form:options items="${userBOList}" itemLabel="name"
									itemValue="id" />
							</form:select>
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
				</div> --%>

				<div class="row"
					style="border: 4px solid #e6e6e6; margin: 15px 15px 15px 15px; background-color: #e1e1e1">
					<div class=" col-md-3">
						<div class="form-group home-left">
							<label class="hidden-xs"></label>
							<form:input type="ntext" class="form-control" path="accountName"
								id="accountNameId" placeholder="Account Name" escapeXml="false"
								style="height: 35px;font-weight: 700;"></form:input>
							<div id="accountNameErr" style="color: red;"></div>
						</div>
					</div>
					<div class="col-md-3 fs-mobile-search">
						<div class="form-group ">
							<label class="hidden-xs"></label>
							<form:select type="text" path="id" class="form-control required"
								style="height: 35px;font-weight: 700;
								   text-transform: capitalize;">
								<form:option value="">Assigned User </form:option>

								<form:options items="${userBOList}" itemLabel="assignedTo.name"
									itemValue="assignedTo.id" />
							</form:select>

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
			<c:if test="${not empty totalCount}">

				<div class="col-sm-12" style="margin-top: 20px">
					<p>
						<a class="btn btn-theme btn-xs btn-default"
							style="color: #1b1818; font-weight: bold;"><c:out
								value="${totalCount}"></c:out></a> <strong class="color-black">Accounts
							Found</strong>
					</p>
				</div>

			</c:if>

			<c:if test="${not empty count}">

				<div class="col-sm-12" style="margin-top: 20px">
					<p>
						<a class="btn btn-theme btn-xs btn-default"
							style="color: #1b1818; font-weight: bold;"><c:out
								value="${count}"></c:out></a> <strong class="color-black">Accounts
							Found</strong>
					</p>
				</div>

			</c:if>

			<c:if test="${!empty accountlist}">
				<div class="col-sm-12">
					<div class="pi-responsive-table-sm">
						<div class="pi-section-w pi-section-white piTooltips">
							<display:table id="data" name="${accountlist.list}"
								requestURI="/view-accounts" export="false"
								class="pi-table pi-table-complex pi-table-hovered pi-round pi-table-shadow pi-table-all-borders">
								<display:column property="sNO" title="SNo" />
								<display:column property="accountName" title="Account Name" />
								<display:column property="phone" title="Contact Number" />
								<display:column property="assignedTo.name" title="Assigned To" />
								<%-- <display:column property="opportunityBO.firstName" title="Account Source" /> --%>
								<sec:authorize
									access="hasAnyRole('ROLE_COMPANY','ROLE_VIEW_ACCOUNT')">
									<display:column url="view-account-details" media="html"
										paramId="accountId" paramProperty="accountId" title="View">
										<a href="view-account-details?accountId=${data.accountId}"><i
											style="text-align: center;" class="fa fa-eye"></i></a>
									</display:column>
								</sec:authorize>
								<sec:authorize
									access="hasAnyRole('ROLE_COMPANY','ROLE_UPDATE_ACCOUNT')">
									<display:column url="edit-account" media="html"
										paramId="accountId" paramProperty="accountId" title="Edit">
										<a href="edit-account?accountId=${data.accountId}"><i
											style="text-align: center;" class="fa fa-pencil"></i></a>
									</display:column>
								</sec:authorize>
								<sec:authorize
									access="hasAnyRole('ROLE_COMPANY','ROLE_DELETE_ACCOUNT')">
									<display:column url="delete-account" media="html"
										paramId="accountId" paramProperty="accountId" title="Delete">
										<a href="delete-account?accountId=${data.accountId}"
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
					<c:if test="${accountlist.currentPage gt 1}">
						<!-- <li><a href="view-account?page=1"><span><i
										class="fa fa-angle-double-left" aria-hidden="true"></i> </span></a></li> -->

						<li><a
							href="view-account?page=${accountlist.currentPage - 1}"><span><i
									style="cursor: pointer" class="fa fa-angle-left"
									aria-hidden="true"></i> </span></a></li>

						<li><a onclick=pageNavigation(1)><span><i
									style="cursor: pointer" class="fa fa-angle-double-left"
									aria-hidden="true"></i> </span></a></li>

						<li><a onclick=pageNavigation(${accountlist.currentPage - 1})><span><i
									class="fa fa-angle-left" style="cursor: pointer"
									aria-hidden="true"></i> </span></a></li>


					</c:if>
					<c:forEach items="${accountlist.noOfPages}" var="i">
						<c:choose>
							<c:when test="${accountlist.currentPage == i}">
								<li class="active"><a
									style="color: #fff; background-color: #34495e">${i}</a></li>
							</c:when>
							<c:otherwise>
									<%-- <li><a href="view-account?page=${i}">${i}</a></li>  --%>

								<li><a style="cursor: pointer" onclick=pageNavigation(${i})>${i}</a></li>

							</c:otherwise>
						</c:choose>
					</c:forEach>
					<c:if test="${accountlist.currentPage lt accountlist.totalPages}">
						<li><a
							href="view-account?page=${accountlist.currentPage + 1}"><span><i
									style="cursor: pointer" class="fa fa-angle-right"
									aria-hidden="true"></i> </span></a></li>
						<li><a
							href="view-account?page=${accountlist.lastRecordValue}"><span><i
									style="cursor: pointer" class="fa fa-angle-double-right"
									aria-hidden="true"></i> </span></a></li>
					</c:if>
				</ul>
			</nav> 
		<%-- 	<nav style="text-align: center;">
				<ul class="pagination pagination-theme  no-margin center"
					style="margin-left: 575px;">
					<c:if test="${accountlist.currentPage gt 1}">
						<li><a href="view-account?page=1"><span><i
									class="fa fa-angle-double-left" aria-hidden="true"></i> </span></a></li>
						<li><a
							href="view-account?page=${accountlist.currentPage - 1}"><span><i
									class="fa fa-angle-left" aria-hidden="true"></i> </span></a></li>
					</c:if>
					<c:forEach items="${accountlist.noOfPages}" var="i">
						<c:choose>
							<c:when test="${accountlist.currentPage == i}">

								<li class="active"><a
									style="color: #fff; background-color: #34495e">${i}</a></li>
							</c:when>
							<c:otherwise>
								<li><a href="view-account?page=${i}">${i}</a></li>
							</c:otherwise>
						</c:choose>
					</c:forEach>
					<c:if test="${accountlist.currentPage lt accountlist.totalPages}">
						<li><a
							href="view-account?page=${accountlist.currentPage + 1}"><span><i
									class="fa fa-angle-right" aria-hidden="true"></i> </span></a></li>
						<li><a
							href="view-account?page=${accountlist.lastRecordValue}"><span><i
									class="fa fa-angle-double-right" aria-hidden="true"></i> </span></a></li>
					</c:if>
				</ul>
			</nav>
 --%>

		</div>
	</div>
	<br />
</div>