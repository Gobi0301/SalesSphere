<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>View Employees</title>
</head>
<!-- <script>
	$(document).ready(function() {
		$('#search').click(function(e) {
			var isValid = true;

			var name = $('#nameId').val();
			if (name == '') {
				isValid = false;
				$("#nameErr").show();
				$("#nameErr").html("Please enter First name ");
				$("#nameId").css({
					"border" : "1px solid red",

				});

			} else if (!/^[a-zA-Z\s]*$/g.test(userName)) {
				isValid = false;
				$("#nameErr").show();
				$("#nameErr").html("Please enter characters only");
				$("#nameId").css({
					"border" : "1px solid red",

				});

			} else {
				$('#nameErr').hide();
				$('#nameId').css({

					"border" : "",
					"background" : ""
				});
			}

			if (isValid == false)
				e.preventDefault();

		});
	});
</script>
 -->

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
	<div class="item">
	<div class="row ">
			<h3 class="text-center no-margin titleunderline underline"
				style="margin-top: -10px;">List Employees</h3>
			
			<sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_COMPANY')">
				<div class="row ">
					<br> <a href="create-employees" title="Create New Employee"
						style="font-size: 26px; color: #7cb228; margin-left: 95%;"> <i
						class="fa fa-plus-circle"></i>
					</a>
				</div>
			</sec:authorize>

			<form:form id="myForm" method="post" class="login-form clearfix"
				action="search-employees" commandName="searchEmployees">
				<div class="row"
					style="border: 4px solid #e6e6e6; margin: 15px 15px 15px 15px; background-color: #e1e1e1">
					<div class=" col-md-3">
						<div class="form-group home-left">
							<label class="hidden-xs"></label>
							<form:input type="ntext" class="form-control" path="name"
								id="nameId" placeholder="First Name " escapeXml="false"
								style="height: 35px;font-weight: 700;"></form:input>
							<form:errors path="name" cssClass="error" />
							<div id="nameErr" style="color: red;"></div>
						</div>
					</div>
					<div class="col-md-3">
						<div class="form-group ">
							<label class="hidden-xs"></label>
							<form:input type="ntext" class="form-control" path="emailAddress"
								placeholder="Email Address"
								style="height:35px;font-weight: 700;"></form:input>

						</div>
					</div>

					<div class=" col-md-3">
						<div class="form-group">
							<label class="hidden-xs"></label>
							<%--  <form:select type="text" path="skillsBO.descriptions"
								id="skillsId" class="form-control "
								style="height: 35px;font-weight: 700;
								 ">

								<form:option value="">-- Select skills --   </form:option>
								<form:option value="${searchEmployees.skillsBO.skillsId}">${searchEmployees.skillsBO.descriptions}</form:option>
								<form:options items="${SkillsListBO}" itemLabel="descriptions"
									itemValue="skillsId" />
							</form:select>  --%>
							
 							<form:select type="text" path="skillsBO.descriptions" id="skillsId"
								class="form-control "
								style="height: 35px;font-weight: 700;
								   text-transform: capitalize; ">
								<form:option value="">-- Select skills --   </form:option>
								<form:options items="${SkillsListBO}" itemLabel="descriptions"
									itemValue="skillsId" />
							</form:select> 
							
							<!-- <div id="workItemErr" style="color: red;"></div> -->
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
			<c:if test="${not empty totalUserRecordcount}">

				<div class="col-sm-12" style="margin-top: 20px">
					<p>
						<a class="btn btn-theme btn-xs btn-default"
							style="color: #1b1818; font-weight: bold;"><c:out
								value="${totalUserRecordcount}"></c:out></a> <strong class="color-black">Employee
							Found</strong>
					</p>
				</div>

			</c:if>
			<c:if test="${not empty totalsearchcount}">

				<div class="col-sm-12" style="margin-top: 20px">
					<p>
						<a class="btn btn-theme btn-xs btn-default"
							style="color: #1b1818; font-weight: bold;"><c:out
								value="${totalsearchcount}"></c:out></a> <strong class="color-black">Employee
							Found</strong>
					</p>
				</div>

			</c:if>
			<c:if test="${!empty userBOLists.list}">
				<div class="col-sm-12">
					<!-- <hr style="border: 1px solid #e1e1e1;"> -->
					<div class="pi-responsive-table-sm">
						<div class="pi-section-w pi-section-white piTooltips">
							<display:table id="data" name="${userBOLists.list}"
								requestURI="/view-employees" export="false"
								class="pi-table pi-table-complex pi-table-hovered pi-round pi-table-shadow pi-table-all-borders">
								<display:column title="S.No" headerClass="sortable" media="html">
									<c:out value="${data.sNo}" />
								</display:column>

								<c:if test="${data.status =='Active'}">
									<display:column url="active-deactive-user" media="html"
										paramId="id" title="Name">
										<a
											href="active-deactive-user?status=${data.status},${data.id}"
											onclick="return confirm('Are you sure you want to Deactive?')"
											style="color: green;"> <c:out value="${data.name }"></c:out></a>
									</display:column>
								</c:if>
								<c:if test="${data.status =='De-Active'}">
									<display:column url="active-deactive-user" media="html"
										paramId="id" title="Name">
										<a
											href="active-deactive-user?status=${data.status},${data.id}"
											onclick="return confirm('Are you sure you want to active?')"
											style="color: red;"> <c:out value="${data.name }"></c:out></a>
									</display:column>
								</c:if>
								<display:column property="emailAddress" title="Email" />
								<display:column property="skillASString" title="Required Skills" />
								<display:column property="mobileNo" title="Mobile" />
								<display:column url="edit-employees" media="html" paramId="id"
									paramProperty="id" title="Edit">
									<a href="edit-employees?id=${data.id}"><i
										style="text-align: center;" class="fa fa-pencil"></i></a>
								</display:column>
								<display:column url="delete-employees" media="html" paramId="id"
									paramProperty="id" title="Delete">
									<a href="delete-employees?id=${data.id}"
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
					<c:if test="${userBOLists.currentPage gt 1}">
						<li><a
							href="view-employees?page=1&searchElement=${searchElement}"><span><i
									class="fa fa-angle-double-left" aria-hidden="true"></i> </span></a></li>
						<li><a
							href="view-employees?page=${userBOLists.currentPage - 1}&searchElement=${searchElement}"><span><i
									class="fa fa-angle-left" aria-hidden="true"></i> </span></a></li>
					</c:if>
					<c:forEach items="${userBOLists.noOfPages}" var="i">
						<c:choose>
							<c:when test="${userBOLists.currentPage == i}">

								<li class="active"><a
									style="color: #fff; background-color: #34495e">${i}</a></li>
							</c:when>
							<c:otherwise>
								<li><a href="view-employees?page=${i}&searchElement=${searchElement}">${i}</a></li>
							</c:otherwise>
						</c:choose>
					</c:forEach>
					<c:if test="${userBOLists.currentPage lt userBOLists.totalPages}">
						<li><a
							href="view-employees?page=${userBOLists.currentPage + 1}&searchElement=${searchElement}"><span><i
									class="fa fa-angle-right" aria-hidden="true"></i> </span></a></li>
						<li><a
							href="view-employees?page=${userBOLists.lastRecordValue}&searchElement=${searchElement}"><span><i
									class="fa fa-angle-double-right" aria-hidden="true"></i> </span></a></li>
					</c:if>
				</ul>
			</nav>


		</div>
		<br />
	</div>
	</div>
</html>