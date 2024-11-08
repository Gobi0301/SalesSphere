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
<title>View Leads</title>
</head>
<script>

function page(pageNo){
	
alert(pageNo);
$('#myForm').append('<input type="hidden" name="page" value="'+ pageNo +'">');
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
				<div class="row ">
				<a href="create-opportunity" title="Create New Campaign"
					style="font-size: 26px; color: #7cb228; margin-left: 95%;"> <i
					class="fa fa-plus-circle"></i>
				</a>
			</div>
			<!-- 	SEARCHPAD START HERE -->
	
		<form:form  id="myForm" name="pageNo" method="post" class="login-form clearfix"	action="search-opportunitie" modelAttribute="searchObj">
 			<div class="row" style="border: 4px solid #e6e6e6; margin: 15px 15px 15px 15px; background-color: #e1e1e1">
 			
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
									
									<div class=" col-md-1" style="padding-bottom: 0px;">
						<div class="form-group">
							<label class="hidden-xs">Search</label>
							<button class="btn btn-theme btn-success btn-block" id="submit"
								style="padding: 6px 5px; background-color: #7cb228; border-color: #7cb228;">
								<small><i class="fa fa-search" aria-hidden="true"
									style="font-size: 20px;"></i></small>
							</button>
						</div>
					</div>
 			 			
 			 			</div>
 			
					
 			</div>
 			
 			</form:form>
 			



			<!-- 	SEARCHPAD ENDS HERE -->



			<!-- VIEW LIST START HERE -->
			<c:if test="${!empty oppoList.list}">
				<div class="col-sm-12">
					<div class="pi-responsive-table-sm">
						<div class="pi-section-w pi-section-white piTooltips">
							<display:table id="data" name="${oppoList.list}"
								requestURI="/view-opportunitie" pagesize="10" export="false"
								class="pi-table pi-table-complex pi-table-hovered pi-round pi-table-shadow pi-table-all-borders">

								<display:column property="sNo" title="SNo" />
								<display:column property="firstName" title="First Name" />
								<display:column property="lastName" title="Last Name" />
								<display:column property="productService.serviceName" title="Product" />
								
								
								
								
								
								
								

							</display:table>
						</div>
					</div>
				</div>
				<!-- VIEW LIST ENDS HERE -->


				<!-- NAVIGATION FOR VIEW LIST START HERE -->

				<nav style="text-align: center;">
				<ul class="pagination pagination-theme  no-margin center" style="margin-left: 575px;">
					<c:if test="${oppoList.currentPage gt 1}">
						<li><a href="view-opportunitie?page=1"><span><i class="fa fa-angle-double-left" aria-hidden="true"></i> </span></a></li>
						<li><a	href="view-opportunitie?page=${oppoList.currentPage - 1}"><span><i	class="fa fa-angle-left" aria-hidden="true"></i> </span></a></li>
					</c:if>	
					
					<c:forEach items="${oppoList.noOfPages}" var="i">
						<c:choose>
							<c:when test="${oppoList.currentPage == i}">
								<li class="active"><a style="color: #fff; background-color: #34495e">${i}</a></li>
							</c:when>
							<c:otherwise>
									<li><a href="view-opportunitie?page=${i}">${i}</a></li>
							</c:otherwise>						
						</c:choose>
					</c:forEach>
					
					<c:if test="${oppoList.currentPage lt oppoList.totalPages }">
							<li><a	href="view-opportunitie?page=${oppoList.currentPage + 1}"><span><i	class="fa fa-angle-right" aria-hidden="true"></i> </span></a></li>
							<li><a	href="view-opportunitie?page=${oppoList.lastRecordValue}"><span><i	class="fa fa-angle-double-right" aria-hidden="true"></i> </span></a></li>					
					</c:if>
					
				</ul>			
				</nav> 
			</c:if>
			<!-- NAVIGATION FOR VIEW LIST ENDS HERE -->








			<!-- SEARCH LIST STARTS HERE -->


			 	<c:if test="${!empty oppoLists.list}">
				<div class="col-sm-12">
							<div class="pi-responsive-table-sm">
						<div class="pi-section-w pi-section-white piTooltips">
							<display:table id="data" name="${oppoLists.list}"
								requestURI="/view-opportunitie" pagesize="10" export="false"
								class="pi-table pi-table-complex pi-table-hovered pi-round pi-table-shadow pi-table-all-borders">

														 
							<display:column property="sNo" title="SNo" />
								<display:column property="firstName" title="First Name" />
								<display:column property="lastName" title="Last Name" />
								<display:column property="productService.serviceName" title="Product" />
								
								
								
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
							<li><a onclick=page(1)><span><i
										class="fa fa-angle-double-left" aria-hidden="true"></i> </span></a></li>
							<li><a onclick=page(${oppoLists.currentPage - 1})><span><i	class="fa fa-angle-left" aria-hidden="true"></i> </span></a></li>
						</c:if>
						<c:forEach items="${oppoLists.noOfPages}" var="i">
							<c:choose>
								<c:when test="${oppoLists.currentPage == i}">
									<li class="active"><a
										style="color: #fff; background-color: #34495e">${i}</a></li>
								</c:when>
								<c:otherwise>
									<li><a onclick=page(${i})>${i}</a></li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
						<c:if
							test="${oppoLists.currentPage lt oppoLists.totalPages}">
							<li><a
								onclick=page(${oppoLists.currentPage + 1})><span><i
										class="fa fa-angle-right" aria-hidden="true"></i> </span></a></li>
							<li><a	onclick=page(${oppoLists.lastRecordValue})><span><i
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




