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
<title>Task list</title>
</head>
<body>

	<script>
		$(document).ready(function() {

			$('#search').click(function(e) {
				//LeadName
				var isValid = true;
				var leadName = $('#leadNameId').val();
				if (leadName == '') {
					isValid = false;
					$("#leadNameErr").show();
					$("#leadNameErr").html("Please enter LeadName");
					$("#leadNameId").css({
						"border" : "1px solid red",
					});
				} else {
					$('#leadNameErr').hide();
					$('#leadNameId').css({
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
					style="margin-top: -10px;">List TaskManagement</h3>
				<sec:authorize
					access="hasAnyRole('ROLE_COMPANY','ROLE_CREATE_TASK')">
					<div class="row ">
						<a href="create-task"
							style="font-size: 26px; color: #7cb228; margin-left: 97%;"> <i
							class="fa fa-plus-circle" title="Create Task"></i>
						</a>
					</div>
				</sec:authorize>

				<form:form id="taskSearchForm" method="post"
					class="login-form clearfix" name="searchTask" action="search-task"
					commandName="searchTask">
					<div class="row"
						style="border: 4px solid #e6e6e6; margin: 15px 15px 15px 15px; background-color: #e1e1e1">
						<div class=" col-md-4">
							<div class="form-group home-left">
								<label class="hidden-xs">&nbsp;</label>
								<form:input type="text" class="form-control" path="leadName"
									placeholder="Lead Name " id="leadNameId" escapeXml="false"
									style="height: 35px;font-weight: 700;"></form:input>

								<form:errors path="leadName" class="error" />
								<div id="leadNameErr" style="color: red;"></div>

							</div>
						</div>
						<div class=" col-md-1" style="padding-bottom: 0px;">
							<div class="form-group home-right">
								<label class="hidden-xs">&nbsp;</label>
								<div class="text-center">
									<button class="btn btn-theme btn-success btn-block" id="Search"
										style="padding: 6px 5px; margin-top: 3px; background-color: #7cb228; border-color: #7cb228;">
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
                            <p><a class="btn btn-theme btn-xs btn-default"
                                    style="color: #1b1818; font-weight: bold;"><c:out
                                        value="${totalCount}"></c:out></a> <strong
                                    class="color-black">Task Found</strong>
                            </p>
                        </div>
                    </c:if>
                    
                    <c:if test="${not empty totalSearchCount}">
                        <div class="col-sm-12" style="margin-top: 20px">
                            <p><a class="btn btn-theme btn-xs btn-default"
                                    style="color: #1b1818; font-weight: bold;"><c:out
                                        value="${totalSearchCount}"></c:out></a> <strong
                                    class="color-black">Task Found</strong>
                            </p>
                        </div>
                    </c:if>
<c:if test="${!empty taskList.list}"> 
				<div class="col-sm-12">
					<div class="pi-responsive-table-sm">
						<div class="pi-section-w pi-section-white piTooltips">
							<display:table id="data" name="${taskList.list}"
								requestURI="/view-task"  export="false"
								class="pi-table pi-table-complex pi-table-hovered pi-round pi-table-shadow pi-table-all-borders">

								<display:column property="sNo" title="S.No" />
								<display:column property="workItemBO.workItem"
									title="WorkItem Name" />
								<display:column property="status" title="Status" />
								<display:column property="priority" title="Priority" />
								<display:column property="adminUserBO.name"
									title="Assigned Employee" />
								<%-- <display:column property="projectBo.projectName"
									title="relatedTo" /> --%>
								<display:column property="leadsBO.firstName" title="Lead Name" />
								<display:column property="duedate" title="Due Date" />
								<sec:authorize
									access="hasAnyRole('ROLE_COMPANY','ROLE_VIEW_TASK')">
									<display:column url="task-tracker-status" media="html"
										paramId="id" paramProperty="taskId" title="View">
										<a href="task-tracker-status?taskId=${data.taskId}"> <i
											style="text-align: center; color: black;" class="fa fa-eye"></i>
										</a>
									</display:column>
								</sec:authorize>
								<sec:authorize
									access="hasAnyRole('ROLE_COMPANY','ROLE_UPDATE_TASK')">
									<display:column url="edit-task" media="html" paramId="id"
										paramProperty="taskId" title="Edit">
										<a href="edit-task?taskId=${data.taskId}"><i
											style="text-align: center;" class="fa fa-pencil"></i></a>
									</display:column>
								</sec:authorize>
								<sec:authorize
									access="hasAnyRole('ROLE_COMPANY','ROLE_DELETE_TASK')">
									<display:column url="delete-task" media="html" paramId="id"
										paramProperty="taskId" title="Delete">
										<a href="delete-task?taskId=${data.taskId}"
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
						<c:if test="${taskList.currentPage gt 1}">
							<li><a href="view-task?page=1&searchLeadName=${searchLeadName}"><span><i
										class="fa fa-angle-double-left" aria-hidden="true"></i> </span></a></li>
							<li><a href="view-task?page=${taskList.currentPage - 1}&searchLeadName=${searchLeadName}"><span><i
										class="fa fa-angle-left" aria-hidden="true"></i> </span></a></li>
						</c:if>
						<c:forEach items="${taskList.noOfPages}" var="i">
							<c:choose>
								<c:when test="${taskList.currentPage == i}">

									<li class="active"><a
										style="color: #fff; background-color: #34495e">${i}</a></li>
								</c:when>
								<c:otherwise>
									<li><a href="view-task?page=${i}&searchLeadName=${searchLeadName}">${i}</a></li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
						<c:if test="${taskList.currentPage lt taskList.totalPages}">
							<li><a href="view-task?page=${taskList.currentPage + 1}&searchLeadName=${searchLeadName}"><span><i
										class="fa fa-angle-right" aria-hidden="true"></i> </span></a></li>
							<li><a href="view-task?page=${taskList.lastRecordValue}&searchLeadName=${searchLeadName}"><span><i
										class="fa fa-angle-double-right" aria-hidden="true"></i> </span></a></li>
						</c:if>
					</ul>
				</nav>
			</div>
		</div>
	</div>
</body>
</html>