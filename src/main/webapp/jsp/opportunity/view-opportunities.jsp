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
<title>View Opportunitiy</title>
</head>
<script>
	function page(pageNo) {
		alert(pageNo);
		$('#myForm').append(
				'<input type="hidden" name="page" value="'+ pageNo +'">');
		document.pageNo.submit();

	}
</script>

<script>
	$(document).ready(function() {
		$('#submit').click(function(e) {

			//Search Name Validation
			var isValid = true;
			var firstName = $('#firstName').val();
			if (firstName == '') {
				isValid = false;
				$("#firstNameErr").show();
				$("#firstNameErr").html("Please enter FirstName");
				$("#firstName").css({
					"border" : "1px solid red",
				});
			} else {
				$('#firstNameErr').hide();
				$('#firstName').css({
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
				style="margin-top: -10px;">List Opportunities</h3>
			<sec:authorize
				access="hasAnyRole('ROLE_COMPANY','ROLE_CREATE_OPPORTUNITY')">
				<div class="row ">
					<a href="create-opportunity" title="Create New Campaign"
						style="font-size: 26px; color: #7cb228; margin-left: 97%;"> <i
						class="fa fa-plus-circle"></i>
					</a>
				</div>
			</sec:authorize>
			<!-- 	SEARCHPAD START HERE -->

			<form:form id="myForm" name="pageNo" method="post"
				class="login-form clearfix" action="search-opportunities"
				modelAttribute="searchObj">
				<div class="row"
					style="border: 4px solid #e6e6e6; margin: 15px 15px 15px 15px; background-color: #e1e1e1">
					<input type="hidden" name="status" value="${status}">
					
					<div class="col-sm-12">
						<div class="col-sm-3">
							<div class="form-group">
								<label>First Name</label>
								<form:input type="text" name="firstName" path="firstName"
									id="firstName" class="form-control required"
									placeholder="First Name" maxlength="150" />
								<form:errors path="firstName" class="error" />
								<div id="firstNameErr" style="color: red;"></div>

							</div>

						</div>

						<%-- <div class="col-sm-3">
										<div class="form-group">
											<label>Last Name</label>
											<form:input type="text" name="lastName" path="lastName"
												id="lastName" class="form-control required"
												placeholder="First Name" maxlength="150" />
											<form:errors path="lastName" class="error" />
											<div id="lastNameErr" style="color: red;"></div>

										</div>

									</div> --%>

						<div class="col-sm-3">
							<div class="form-group">
								<label>Product Name</label>
								<form:select type="text" name="serviceId"
									path="productService.serviceName" placeholder="Select Product"
									class="form-control required">
									<c:if test="${not empty searchObj}">

										<%-- <form:option value="${searchObj.productService.serviceId}">${searchObj.productService.serviceName}</form:option> --%>
										<form:option value=""> Select </form:option>
										<form:options items="${prodList}" itemLabel="serviceName"
											itemValue="serviceId"></form:options>
									</c:if>

								</form:select>
								<form:errors path="productService.serviceId" class="error" />
								<div id="prductListErr" style="color: red;"></div>
							</div>
						</div>

						<div class="col-sm-3">
							<div class="form-group">
								<label>Sales Stage</label>
								<form:select type="text" name="salesStage" path="salesStage"
									class="form-control required">
									<form:option value="">Select Stage</form:option>
									<form:option value="Initiate">Initiate</form:option>
									<form:option value="Follow">Follow</form:option>
									<form:option value="Completed">Completed</form:option>
								</form:select>
								<form:errors path="salesStage" class="error" />
								<div id="salesStageErr" style="color: red;"></div>
							</div>
						</div>

						<div class=" col-md-1" style="padding-bottom: -1px;">
							<div class="form-group">
								<label class="hidden-xs"></label>
								<button class="btn btn-theme btn-success btn-block" id="ssubmit"
									style="padding: 6px 5px; background-color: #7cb228; border-color: #7cb228; margin-top: 25px;">
									<small><i class="fa fa-search" aria-hidden="true"
										style="font-size: 20px;"></i></small>
								</button>
							</div>
						</div>

					</div>


				</div>

			</form:form>




			<!-- 	SEARCHPAD ENDS HERE -->

			<c:if test="${not empty viewCount}">
				<div class="col-sm-12" style="margin-top: 20px">
					<p>
						<a class="btn btn-theme btn-xs btn-default"
							style="color: #1b1818; font-weight: bold;"><c:out
								value="${viewCount}"></c:out></a> <strong class="color-black">Opportunity
							Found</strong>
					</p>
				</div>

			</c:if>

			<!-- VIEW LIST START HERE -->
			<c:if test="${!empty oppoList.list}">
				<div class="col-sm-12">
					<div class="pi-responsive-table-sm">
						<div class="pi-section-w pi-section-white piTooltips">
							<display:table id="data" name="${oppoList.list}"
								requestURI="/view-opportunities" export="false"
								class="pi-table pi-table-complex pi-table-hovered pi-round pi-table-shadow pi-table-all-borders">

								<display:column property="sNo" title="SNo" />
								<display:column property="firstName" title="First Name" />
								<display:column property="lastName" title="Last Name" />
								<display:column property="productService.serviceName"
									title="Product" />
								<%--  <c:if test="${!empty data.endTime}"> 
								 <display:column property="endTime" title="Date of Closing" /> 
								</c:if>
								 <c:if test="${!empty data.salesStage}"> 
								<display:column property="salesStage" title="Sales Stage" />
                           </c:if> --%>
								<sec:authorize
									access="hasAnyRole('ROLE_COMPANY','ROLE_VIEW_OPPORTUNITY')">
									<display:column url="opportunity-tracking-status" media="html"
										paramId="opportunityId" paramProperty="opportunityId"
										title="View">
										<a
											href="opportunity-tracking-status?opportunityId=${data.opportunityId}"><i
											style="text-align: center;" class="fa fa-eye"></i></a>
									</display:column>
								</sec:authorize>
								<c:if test="${empty status}">
									<sec:authorize
										access="hasAnyRole('ROLE_COMPANY','ROLE_UPDATE_OPPORTUNITY')">
										<display:column url="edit-opportunity" media="html"
											paramId="opportunityId" paramProperty="opportunityId"
											title="Edit">
											<a
												href="edit-opportunity?opportunityId=${data.opportunityId}"><i
												style="text-align: center;" class="fa fa-pencil"></i></a>
										</display:column>
									</sec:authorize>
									<sec:authorize
										access="hasAnyRole('ROLE_COMPANY','ROLE_DELETE_OPPORTUNITY')">
										<display:column url="delete-account" media="html"
											paramId="opportunityId" paramProperty="opportunityId"
											title="Delete">
											<a
												href="delete-opportunity?opportunityId=${data.opportunityId}"
												onclick="return confirm('Are you sure you want to Delete?')"><i
												style="text-align: center;" class="fa fa-trash"></i></a>
										</display:column>
									</sec:authorize>
								</c:if>
							</display:table>
						</div>
					</div>
				</div>
				<!-- VIEW LIST ENDS HERE -->


				<!-- NAVIGATION FOR VIEW LIST START HERE -->

				<nav style="text-align: center;">
					<ul class="pagination pagination-theme  no-margin center"
						style="margin-left: 575px;">
						<c:if test="${oppoList.currentPage gt 1}">
							<li><a
								href="view-opportunities?page=1&searchElement=${searchElement}"><span><i
										class="fa fa-angle-double-left" aria-hidden="true"></i> </span></a></li>
							<li><a
								href="view-opportunities?page=${oppoList.currentPage - 1}&searchElement=${searchElement}"><span><i
										class="fa fa-angle-left" aria-hidden="true"></i> </span></a></li>
						</c:if>

						<c:forEach items="${oppoList.noOfPages}" var="i">
							<c:choose>
								<c:when test="${oppoList.currentPage == i}">
									<li class="active"><a
										style="color: #fff; background-color: #34495e">${i}</a></li>
								</c:when>
								<c:otherwise>
									<li><a
										href="view-opportunities?page=${i}&searchElement=${searchElement}">${i}</a></li>
								</c:otherwise>
							</c:choose>
						</c:forEach>

						<c:if test="${oppoList.currentPage lt oppoList.totalPages }">
							<li><a
								href="view-opportunities?page=${oppoList.currentPage + 1}&searchElement=${searchElement}"><span><i
										class="fa fa-angle-right" aria-hidden="true"></i> </span></a></li>
							<li><a
								href="view-opportunities?page=${oppoList.lastRecordValue}&searchElement=${searchElement}"><span><i
										class="fa fa-angle-double-right" aria-hidden="true"></i> </span></a></li>
						</c:if>

					</ul>
				</nav>
			</c:if>
			<!-- NAVIGATION FOR VIEW LIST ENDS HERE -->
			<c:if test="${not empty searchCount}">
				<div class="col-sm-12" style="margin-top: 20px">
					<p>
						<a class="btn btn-theme btn-xs btn-default"
							style="color: #1b1818; font-weight: bold;"><c:out
								value="${searchCount}"></c:out></a> <strong class="color-black">Opportunity
							Found</strong>
					</p>
				</div>
			</c:if>
			<!-- SEARCH LIST STARTS HERE -->


			<c:if test="${!empty oppoLists.list}">
				<div class="col-sm-12">
					<div class="pi-responsive-table-sm">
						<div class="pi-section-w pi-section-white piTooltips">
							<display:table id="data" name="${oppoLists.list}"
								requestURI="/view-opportunities" export="false"
								class="pi-table pi-table-complex pi-table-hovered pi-round pi-table-shadow pi-table-all-borders">


								<display:column property="sNo" title="SNo" />
								<display:column property="firstName" title="First Name" />
								<display:column property="lastName" title="Last Name" />
								<display:column property="productService.serviceName"
									title="Product" />
								<%-- <display:column property="endTime" title="Date of Closing" /> --%>
								<display:column property="salesStage" title="Sales Stage" />

								<%-- <display:column url="view-opportunity-details" media="html"
									paramId="opportunityId" paramProperty="opportunityId"
									title="View">
									<a
										href="opportunity-tracking-status?opportunityId=${data.opportunityId}"><i
										style="text-align: center;" class="fa fa-eye"></i></a>
								</display:column>
								<c:if test="${empty status}">
									<display:column url="edit-account" media="html"
										paramId="opportunityId" paramProperty="opportunityId"
										title="Edit">
										<a href="edit-opportunity?opportunityId=${data.opportunityId}"><i
											style="text-align: center;" class="fa fa-pencil"></i></a>
									</display:column>

									<display:column url="delete-account" media="html"
										paramId="opportunityId" paramProperty="opportunityId"
										title="Delete">
										<a
											href="delete-opportunity?opportunityId=${data.opportunityId}"
											onclick="return confirm('Are you sure you want to Delete?')"><i
											style="text-align: center;" class="fa fa-trash"></i></a>
									</display:column>
								</c:if> --%>
								</display:table>
						</div>
					</div>
				</div>

				<!-- SEARCH LIST END HERE -->


				<!-- NAVIGATION FOR SEARCH LIST START HERE -->

				<nav style="text-align: center;">
					<ul class="pagination pagination-theme  no-margin center"
						style="margin-left: 575px;">
						<c:if test="${oppoLists.currentPage gt 1}">
							<li><a
								href="view-opportunities?page=1&searchElement=${searchElement}"><span><i
										class="fa fa-angle-double-left" aria-hidden="true"></i> </span></a></li>
							<li><a
								href="view-opportunities?page=${oppoLists.currentPage - 1}&searchElement=${searchElement}"><span><i
										class="fa fa-angle-left" aria-hidden="true"></i> </span></a></li>
						</c:if>
						<c:forEach items="${oppoLists.noOfPages}" var="i">
							<c:choose>
								<c:when test="${oppoLists.currentPage == i}">
									<li class="active"><a
										style="color: #fff; background-color: #34495e">${i}</a></li>
								</c:when>
								<c:otherwise>
									<li><a
										href="view-opportunities?page=${i}&searchElement=${searchElement}">${i}</a></li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
						<c:if test="${oppoLists.currentPage lt oppoLists.totalPages}">
							<li><a
								href="view-opportunities?page=${oppoLists.currentPage + 1}&searchElement=${searchElement}"><span><i
										class="fa fa-angle-right" aria-hidden="true"></i> </span></a></li>
							<li><a
								href="view-opportunities?page=${oppoLists.lastRecordValue}&searchElement=${searchElement}"><span><i
										class="fa fa-angle-double-right" aria-hidden="true"></i> </span></a></li>
						</c:if>
					</ul>
				</nav>

			</c:if>


			<!-- NAVIGATION FOR SEARCH LIST ENDS HERE -->




		</div>
	</div>

</div>
</html>




