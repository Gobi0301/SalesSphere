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
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Project list</title>
</head>
<body>

	<!-- <script>
    $(document).ready(function () {

        $('#submit').click(function (e) {

        	 var isValid = true;
	            var accessName = $('#accessId').val(); // Assuming the input field ID is 'projectId'

	            if (accessName == '') {
	                isValid = false;
	                $("#accessErr").show();
	                $("#accessErr").html("Please enter Access Name");
	                $("#accessId").css({
	                    "border": "1px solid red",
	                });

	            } else {
	                $('#accessErr').hide();
	                $('#accessId').css({
	                    "border": "",
	                    "background": ""
	                });
	            }

            if (isValid == false)
                e.preventDefault();
        });
    });
</script>	 -->
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


	<script>
		$(document).ready(function() {

			$('#submit').click(function(e) {

				var isValid = true;
				var projectName = $('#projectId').val(); // Assuming the input field ID is 'projectId'

				if (projectName == '') {
					isValid = false;
					$("#projectNameErr").show();
					$("#projectNameErr").html("Please enter projectName");
					$("#projectId").css({
						"border" : "1px solid red",
					});

				} else {
					$('#projectNameErr').hide();
					$('#projectId').css({
						"border" : "",
						"background" : ""
					});
				}

				if (isValid == false)
					e.preventDefault();
			});
		});
	</script>
	<div class="box-list">
		<div class="item">
			<div class="row ">
				<h3 class="text-center no-margin titleunderline underline"
					style="margin-top: -10px;">List Projects</h3>
				<sec:authorize
					access="hasAnyRole('ROLE_COMPANY','ROLE_CREATE_PROJECT')">
					<div class="row ">
						<a href="create-project"
							style="font-size: 26px; color: #7cb228; margin-left: 97%;"> <i
							class="fa fa-plus-circle" title="Create Projects"></i>
						</a>
					</div>
				</sec:authorize>
				<form:form id="projectSearchForm" method="post"
					class="login-form clearfix" name="searchProject"
					action="search-project" modelAttribute="searchProject">
					<div class="row"
						style="border: 4px solid #e6e6e6; margin: 15px 15px 15px 15px; background-color: #e1e1e1">
						<div class=" col-md-4">
							<div class="form-group home-left">
								<label class="hidden-xs">&nbsp;</label>
								<form:input type="text" class="form-control" path="projectName"
									placeholder="Project Name " id="projectId" escapeXml="false"
									style="height: 35px;font-weight: 700;"></form:input>
								<form:errors path="projectName" class="error" />
								<div id="projectNameErr" style="color: red;"></div>

							</div>
						</div>
						<div class=" col-md-1">
							<div class="form-group home-left">
								<label class="hidden-xs">&nbsp;</label>

								<button class="btn btn-theme btn-success btn-block" id="Submit"
									style="padding: 6px 5px; margin-top: 5px; background-color: #7cb228; border-color: #7cb228;">
									<small><i class="fa fa-search" aria-hidden="true"
										style="font-size: 20px;"></i></small>
								</button>
							</div>
						</div>

					</div>
				</form:form>

                <c:if test="${not empty totalsearchcount}">
                        <div class="col-sm-12" style="margin-top: 20px">
                            <p><a class="btn btn-theme btn-xs btn-default"
                                    style="color: #1b1818; font-weight: bold;"><c:out
                                        value="${totalsearchcount}"></c:out></a> <strong
                                    class="color-black">Projects Found</strong>
                            </p>
                        </div>
                    </c:if>
                     <c:if test="${not empty totalprojectCount}">
                        <div class="col-sm-12" style="margin-top: 20px">
                            <p><a class="btn btn-theme btn-xs btn-default"
                                    style="color: #1b1818; font-weight: bold;"><c:out
                                        value="${totalprojectCount}"></c:out></a> <strong
                                    class="color-black">Projects Found</strong>
                            </p>
                        </div>
                    </c:if>
<c:if test="${!empty projectList.list}">
				<div class="col-sm-12">
					<div class="pi-responsive-table-sm">
						<div class="pi-section-w pi-section-white piTooltips">
							<display:table id="data" name="${projectList.list}"
								requestURI="/view_project" export="false"
								class="pi-table pi-table-complex pi-table-hovered pi-round pi-table-shadow pi-table-all-borders">

								<display:column property="sNo" title="S.No" />
								<display:column property="projectName" title="Project Name" />
								<display:column property="projectType" title="Type" />
								<display:column property="projectStatus" title="Status" />
								<display:column property="projectLocation" title="Location" />
								<display:column property="projectAreaInSqfts" title="Area" />
								<display:column property="unit" title="unit" />

								<sec:authorize
									access="hasAnyRole('ROLE_COMPANY','ROLE_VIEW_PROJECT')">
									<display:column url="project-tracker-status" media="html"
										paramId="id" paramProperty="projectId" title="View">
										<a href="project-tracker-status?projectId=${data.projectId}">
											<i style="text-align: center; color: black;"
											class="fa fa-eye"></i>
										</a>
									</display:column>
								</sec:authorize>
								<sec:authorize
									access="hasAnyRole('ROLE_COMPANY','ROLE_UPDATE_PROJECT')">
									<display:column url="edit-project" media="html" paramId="id"
										paramProperty="projectId" title="Edit">
										<a href="edit-project?projectId=${data.projectId}"><i
											style="text-align: center;" class="fa fa-pencil"></i></a>
									</display:column>
								</sec:authorize>
								<sec:authorize
									access="hasAnyRole('ROLE_COMPANY','ROLE_DELETE_PROJECT')">
									<display:column url="delete-Project" media="html" paramId="id"
										paramProperty="projectId" title="Delete">
										<a href="delete-Project?projectId=${data.projectId}"
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
						<c:if test="${projectList.currentPage gt 1}">
							<li><a href="view-project?page=1&searchElement=${searchElement}"><span><i
										class="fa fa-angle-double-left" aria-hidden="true"></i> </span></a></li>
							<li><a
								href="view-project?page=${projectList.currentPage - 1}&searchElement=${searchElement}"><span><i
										class="fa fa-angle-left" aria-hidden="true"></i> </span></a></li>
						</c:if>
						<c:forEach items="${projectList.noOfPages}" var="i">
							<c:choose>
								<c:when test="${projectList.currentPage == i}">

									<li class="active"><a
										style="color: #fff; background-color: #34495e">${i}</a></li>
								</c:when>
								<c:otherwise>
									<li><a href="view-project?page=${i}&searchElement=${searchElement}">${i}</a></li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
						<c:if test="${projectList.currentPage lt projectList.totalPages}">
							<li><a
								href="view-project?page=${projectList.currentPage + 1}&searchElement=${searchElement}"><span><i
										class="fa fa-angle-right" aria-hidden="true"></i> </span></a></li>
							<li><a
								href="view-project?page=${projectList.lastRecordValue}&searchElement=${searchElement}"><span><i
										class="fa fa-angle-double-right" aria-hidden="true"></i> </span></a></li>
						</c:if>
					</ul>
				</nav>   <!-- hidden for pagination -->
			</div>
		</div>
	</div>
</body>
</html>