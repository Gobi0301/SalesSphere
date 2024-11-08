

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
<script type="text/javascript">
	function pageNavigation(pageNo) {
		$('#supplierSearchForm').append(
				'<input type="hidden" name="page" value="'+pageNo+'">');
		document.searchSupplier.submit();
	}
</script>

 <script>
	$(document).ready(function() {
		$('#submit').click(function(e) {

			//Search Name Validation
			var isValid = true;
			var supplierName = $('#supplierNameId').val();
			if (supplierName == '') {
				isValid = false;
				$("#supplierNameIdErr").show();
				$("#supplierNameIdErr").html("Please enter supplierName");
				$("#supplierNameId").css({
					"border" : "1px solid red",
				});
			} else {
				$('#supplierNameIdErr').hide();
				$('#supplierNameId').css({
					"border" : "",
					"background" : ""
				});
			}

			if (isValid == false)
				e.preventDefault();
		});
	});
</script> 
<body>

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
					style="margin-top: -10px;">List Supplier</h3>
				<sec:authorize
					access="hasAnyRole('ROLE_COMPANY','ROLE_CREATE_SUPPLIERS')">
					<div class="row ">
						<a href="create-supplier"
							style="font-size: 26px; color: #7cb228; margin-left: 97%;"> <i
							class="fa fa-plus-circle" title="Create New Supplier"></i>
						</a>
					</div>
				</sec:authorize>
				<form:form id="supplierSearchForm" method="post"
					class="login-form clearfix" name="searchSupplier"
					action="search-supplier" commandName="searchSupplier">
					<div class="row"
						style="border: 4px solid #e6e6e6; margin: 15px 15px 15px 15px; background-color: #e1e1e1">
						<div class=" col-md-4">
							<div class="form-group home-left">
								<label class="hidden-xs">&nbsp;</label>
								<form:input type="ntext" class="form-control"
									path="supplierName" placeholder="Supplier Name "
									 escapeXml="false"
									style="height: 35px;font-weight: 700;"></form:input>

								<form:errors path="supplierName" class="error" />
								<!-- <div id="supplierNameIdErr" style="color: red;"></div> -->

							</div>
						</div>
						<div class=" col-md-1" style="padding-bottom: 0px;">
							<div class="form-group home-right">
								<label class="hidden-xs">&nbsp;</label>
								<button class="btn btn-theme btn-success btn-block" id="submit"
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
                            <p><a class="btn btn-theme btn-xs btn-default"
                                    style="color: #1b1818; font-weight: bold;"><c:out
                                        value="${totalCount}"></c:out></a> <strong
                                    class="color-black">Supplier Found</strong>
                            </p>
                        </div>
                    </c:if>
                    	<c:if test="${not empty totalSearchCount}">
                        <div class="col-sm-12" style="margin-top: 20px">
                            <p><a class="btn btn-theme btn-xs btn-default"
                                    style="color: #1b1818; font-weight: bold;"><c:out
                                        value="${totalSearchCount}"></c:out></a> <strong
                                    class="color-black">Supplier Found</strong>
                            </p>
                        </div>
                    </c:if>
                    <c:if test="${!empty supplierLists}">
				<div class="row">
					<div class="pi-responsive-table-sm">
						<div class="pi-section-w pi-section-white piTooltips">
							<display:table id="data" name="${supplierLists.list}"
								requestURI="/view_supplier"  export="false"
								class="pi-table pi-table-complex pi-table-hovered pi-round pi-table-shadow pi-table-all-borders">



								<display:column property="sNO" title="SNO" 
									headerClass="sortable" media="html">

								</display:column> 
								<%-- <display:column property="productServiceBO.serviceName"
									title="ProductName" /> --%>
								<display:column property="supplierName" title="Supplier Name" />
								<display:column property="emailId" title="SupplierEmail" />
								<display:column property="webSite" title="WebSite" />
								<sec:authorize
									access="hasAnyRole('ROLE_COMPANY','ROLE_VIEW_SUPPLIERS')">
								<display:column url="supplier_tracking_status" media="html"
									paramId="supplierId" paramProperty="id" title="View">
									<a
										href="supplier_tracking_status?supplierId=${data.supplierId}">
										<i style="text-align: center; color: black;"
										class="fa fa-eye"></i>
									</a>
								</display:column>
								</sec:authorize>
								<sec:authorize
									access="hasAnyRole('ROLE_COMPANY','ROLE_UPDATE_SUPPLIERS')">
									<display:column url="edit_supplier" media="html" paramId="id"
										paramProperty="supplierId" title="Edit">
										<a href="edit_supplier?supplierId=${data.supplierId}"><i
											style="text-align: center;" class="fa fa-pencil"></i></a>
									</display:column>
								</sec:authorize>
								<sec:authorize
									access="hasAnyRole('ROLE_COMPANY','ROLE_DELETE_SUPPLIERS')">
									<display:column url="delete_supplier" media="html" paramId="id"
										paramProperty="supplierId" title="Delete">
										<a href="delete_supplier?supplierId=${data.supplierId}"
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
						<c:if test="${supplierLists.currentPage gt 1}">
							<li><a href="view_supplier?page=1&searchElement=${searchElement}"><span><i
										class="fa fa-angle-double-left" aria-hidden="true"></i> </span></a></li>
							<li><a
								href="view_supplier?page=${supplierLists.currentPage - 1}&searchElement=${searchElement}"><span><i
										class="fa fa-angle-left" aria-hidden="true"></i> </span></a></li>
						</c:if>
						<c:forEach items="${supplierLists.noOfPages}" var="i">
							<c:choose>
								<c:when test="${supplierLists.currentPage == i}">

									<li class="active"><a
										style="color: #fff; background-color: #34495e">${i}</a></li>
								</c:when>
								<c:otherwise>
									<li><a href="view_supplier?page=${i}&searchElement=${searchElement}">${i}</a></li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
						<c:if
							test="${supplierLists.currentPage lt supplierLists.totalPages}">
							<li><a
								href="view_supplier?page=${supplierLists.currentPage + 1}&searchElement=${searchElement}"><span><i
										class="fa fa-angle-right" aria-hidden="true"></i> </span></a></li>
							<li><a
								href="view_supplier?page=${supplierLists.lastRecordValue}&searchElement=${searchElement}"><span><i
										class="fa fa-angle-double-right" aria-hidden="true"></i> </span></a></li>
						</c:if>
					</ul>
				</nav>
				
			</div>
		</div>
	</div>
</body>
</html>
