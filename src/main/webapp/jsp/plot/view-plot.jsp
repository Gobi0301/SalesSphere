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
<body>

	<script>
	$(document).ready(function() {

		$('#submit').click(function(e) {
			
			var isValid = true;
			var plotNumbers = $('#plotNumbers').val();
			if (plotNumbers == '') {
				isValid = false;
				$("#plotNumbersErr").show();
				$("#plotNumbersErr").html("Please enter PlotNumber");
				$("#plotNumbers").css({
					"border" : "1px solid red",

				});

			} else {
				$('#plotNumbersErr').hide();
				$('#plotNumbers').css({

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
					style="margin-top: -10px;">List Plot</h3>
				<sec:authorize access="hasAnyRole('ROLE_COMPANY')">
					<div class="row ">
						<a href="create-plot"
							style="font-size: 26px; color: #7cb228; margin-left: 97%;"> <i
							class="fa fa-plus-circle" title="Create New case"></i>
						</a>
					</div>
				</sec:authorize>

				<!--Start form search -->
				<form:form id="myForm" name="pageNo" method="post"
					class="login-form clearfix" action="search-plot"
					modelAttribute="searchPlot">
					<div class="row"
						style="border: 4px solid #e6e6e6; margin: 15px 15px 15px 15px; background-color: #e1e1e1">

						<div class="col-sm-12">
							<div class="col-sm-4">
								<div class="form-group">
									<label class="hidden-xs"></label>
									<form:input type="text" id="plotNumbers" path="plotNumbers"
										class="form-control required" placeholder="plotNumbers"
										maxlength="150" style="height: 40px;font-weight: 700;" />
									<form:errors path="plotNumbers" class="error" />
									<div id="plotNumbersErr" style="color: red;"></div>
								</div>
							</div>



							<div class=" col-md-1" style="padding-bottom: 0px;">
								<div class="form-group home-right">
									<label class="hidden-xs"></label>
									<div class="text-center">
										<button class="btn btn-theme btn-success btn-block"
											id="Submit"
											style="padding: 6px 5px; margin-top: 3px; background-color: #7cb228; border-color: #7cb228;">
											<small><i class="fa fa-search" aria-hidden="true"
												style="font-size: 25px;"></i></small>
										</button>
									</div>
								</div>
							</div>
						</div>
					</div>
				</form:form>

				<c:if test="${not empty totalCount}">
					<div class="col-sm-12" style="margin-top: 20px">
						<p>
							<a class="btn btn-theme btn-xs btn-default"
								style="color: #1b1818; font-weight: bold;"><c:out
									value="${totalCount}"></c:out></a> <strong class="color-black">Plot
								Found</strong>
						</p>
					</div>
				</c:if>

				<c:if test="${not empty count}">
					<div class="col-sm-12" style="margin-top: 20px">
						<p>
							<a class="btn btn-theme btn-xs btn-default"
								style="color: #1b1818; font-weight: bold;"><c:out
									value="${count}"></c:out></a> <strong class="color-black">Plot
								Found</strong>
						</p>
					</div>
				</c:if>
				<c:if test="${!empty plotLists}">
					<div class="pi-responsive-table-sm">
						<div class="pi-section-w pi-section-white piTooltips">
							<display:table id="data" name="${plotLists.list}"
								requestURI="/view-plot" export="false"
								class="pi-table pi-table-complex pi-table-hovered pi-round pi-table-shadow pi-table-all-borders">

								<display:column property="sNo" title="S.No" />
								<display:column property="plotNumbers" title="plotNumbers" />
								<display:column property="plotSquareFeet" title="plotSquareFeet" />
								<display:column property="length" title="length" />
								<display:column property="width" title="width" />
								<display:column property="status" title="status" />

								<sec:authorize access="hasAnyRole('ROLE_COMPANY')">
									<display:column url="edit-plot" media="html" paramId="plotId"
										paramProperty="plotId" title="Edit">
										<a href="edit-plot?plotId=${data.plotId}"><i
											style="text-align: center;" class="fa fa-pencil"></i></a>
									</display:column>
								</sec:authorize>

								<sec:authorize access="hasAnyRole('ROLE_COMPANY')">
									<display:column url="delete-plot" media="html" paramId="plotId"
										paramProperty="plotId" title="Delete">
										<a href="delete-plot?plotId=${data.plotId}"
											onclick="return confirm('Are you sure you want to Delete?')"><i
											style="text-align: center;" class="fa fa-trash"></i></a>
									</display:column>
								</sec:authorize>
							</display:table>
						</div>
					</div>
				</c:if>
				<nav style="text-align: center;">
					<ul class="pagination pagination-theme  no-margin center"
						style="margin-left: 575px;">
						<c:if test="${plotLists.currentPage gt 1}">
							<li><a
								href="view-plot?page=1&searchElement=${searchElement}"><span><i
										class="fa fa-angle-double-left" aria-hidden="true"></i> </span></a></li>
							<li><a
								href="view-plot?page=${plotLists.currentPage - 1}&searchElement=${searchElement}"><span><i
										class="fa fa-angle-left" aria-hidden="true"></i> </span></a></li>
						</c:if>
						<c:forEach items="${plotLists.noOfPages}" var="i">
							<c:choose>
								<c:when test="${plotLists.currentPage == i}">
									<li class="active"><a
										style="color: #fff; background-color: #34495e">${i}</a></li>
								</c:when>
								<c:otherwise>
									<li><a
										href="view-plot?page=${i}&searchElement=${searchElement}">${i}</a></li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
						<c:if test="${plotLists.currentPage lt plotLists.totalPages}">
							<li><a
								href="view-plot?page=${plotLists.currentPage + 1}&searchElement=${searchElement}"><span><i
										class="fa fa-angle-right" aria-hidden="true"></i> </span></a></li>
							<li><a
								href="view-plot?page=${plotLists.lastRecordValue}&searchElement=${searchElement}"><span><i
										class="fa fa-angle-double-right" aria-hidden="true"></i> </span></a></li>
						</c:if>
					</ul>
				</nav>

			</div>
		</div>
	</div>

</body>
</html>
