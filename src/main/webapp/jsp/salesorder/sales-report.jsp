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
<script>

function page(pageNo){
	
alert(pageNo);
$('#myForm').append('<input type="hidden" name="page" value="'+ pageNo +'">');
document.pageNo.submit();

}
</script>

<script>
		$(function() {
			$("#datepicker").datepicker({
				changeMonth : true,
				changeYear : true,
				numberOfMonths : 1,
				onSelect : function(selected) {
					var dt = new Date(selected);
					dt.setDate(dt.getDate());
					$("#endDateInput").datepicker("option", "minDate", dt);
				}
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
				style="margin-top: -10px;">View Sales Order</h3>

			<sec:authorize access="hasAnyRole('ROLE_COMPANY')">
				<div class="row ">
					<a href="create-sales-order" title="Create New Campaign"
						style="font-size: 26px; color: #7cb228; margin-left: 97%;"> <i
						class="fa fa-plus-circle"></i>
					</a>
				</div>
			</sec:authorize>
			
			<!-- 	SEARCHPAD START HERE -->
			<form:form id="myForm" name="pageNo" method="post"
				class="login-form clearfix" action="sales-search"
				modelAttribute="searchObj">
				<div class="row"
					style="border: 4px solid #e6e6e6; margin: 15px 15px 15px 15px; background-color: #e1e1e1">

					<div class="col-sm-12">
						<div class="col-sm-3">
							<div class="form-group">
								<label>Sales Order No</label>
								<form:input type="text" name="salesOrderNo" path="salesOrderNo"
									id="firstName" class="form-control required"
									placeholder="Serial No" maxlength="150" />
								<form:errors path="salesOrderNo" class="error" />
								<div id="salesOrderNoErr" style="color: red;"></div>

							</div>

						</div>

						<div class="col-sm-3">
							<div class="form-group">
								<label>Account Name</label>
								<form:select type="text" name="accountId" id="accountId"
									path="accountBO.accountId" onchange="accountProfile(accountId)"
									class="form-control required">
									<form:option value="0">Select</form:option>
									<form:options items="${accountList}"></form:options>
								</form:select>
								<form:errors path="accountBO.accountId" class="error" />
								<div id="accountIdErr" style="color: red;"></div>
							</div>
						</div>

						<div class="col-sm-3">
							<div class="form-group">
								<label class="element-block fw-normal font-lato"> Date<span
									class="font10 text-danger">*</span></font>
								</label>
								<form:input type="text" id="datepicker" path="date"
									placeholder="Date" class="form-control element-block" />
								<label class="element-block fw-normal font-lato"
									style="font-size: 10px">EX:MM/DD/YYYY</label>
								<form:errors path="date" class="error" />
								<div id="datepickerErr" style="color: red;"></div>
							</div>
						</div>


						<div class="col-md-1">
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


				</div>

			</form:form>
			<c:if test="${not empty listsalesorder}">
				<div class="col-sm-12" style="margin-top: 20px">
					<p>
						<a class="btn btn-theme btn-xs btn-default"
							style="color: #1b1818; font-weight: bold;"><c:out
								value="${listsalesorder}"></c:out></a> <strong class="color-black">Lead
							Found</strong>
					</p>
				</div>
			</c:if>
			<c:if test="${not empty searchcountsalesorder}">
				<div class="col-sm-12" style="margin-top: 20px">
					<p>
						<a class="btn btn-theme btn-xs btn-default"
							style="color: #1b1818; font-weight: bold;"><c:out
								value="${searchcountsalesorder}"></c:out></a> <strong class="color-black">Lead
							Found</strong>
					</p>
				</div>
			</c:if>
			<br>
			<br>
			<br>
			<!-- 	SEARCHPAD ENDS HERE -->

			<!-- VIEW LIST START HERE -->
			<c:if test="${!empty listSales.list}">
				<div class="col-sm-12" style="margin-top: -27px">
					<div class="pi-responsive-table-sm">
						<div class="pi-section-w pi-section-white piTooltips">
							<display:table id="data" name="${listSales.list}"
								requestURI="/report-salesorder" export="false"
								class="pi-table pi-table-complex pi-table-hovered pi-round pi-table-shadow pi-table-all-borders">
								<display:column property="sNo" title="S No" />
								<display:column property="salesOrderNo" title="Sales Order No" />
								<display:column property="accountBO.accountName"
									title="Account Name" />
								<sec:authorize
									access="hasAnyRole('ROLE_COMPANY','ROLE_SALES_REPORTS')">
									<display:column url="customer-tracking-status" media="html"
										paramId="id" paramProperty="id" title="View">
										<a href="view-sales-order-list?salesno=${data.salesOrderId}">
											<i style="text-align: center; color: black;"
											class="fa fa-eye"></i>
										</a>
									</display:column>
								</sec:authorize>
								<display:column property="date" title="Date" />


							</display:table>
						</div>
					</div>
				</div>
				<!-- VIEW LIST ENDS HERE -->
				<!-- NAVIGATION FOR VIEW LIST START HERE -->

				<nav style="text-align: center;">
					<ul class="pagination pagination-theme  no-margin center"
						style="margin-left: 575px;">
						<c:if test="${listSales.currentPage gt 1}">
							<li><a href="report-salesorder?page=1"><span><i
										class="fa fa-angle-double-left" aria-hidden="true"></i> </span></a></li>
							<li><a
								href="report-salesorder?page=${listSales.currentPage - 1}"><span><i
										class="fa fa-angle-left" aria-hidden="true"></i> </span></a></li>
						</c:if>

						<c:forEach items="${listSales.noOfPages}" var="i">
							<c:choose>
								<c:when test="${listSales.currentPage == i}">
									<li class="active"><a
										style="color: #fff; background-color: #34495e">${i}</a></li>
								</c:when>
								<c:otherwise>
									<li><a href="report-salesorder?page=${i}">${i}</a></li>
								</c:otherwise>
							</c:choose>
						</c:forEach>

						<c:if test="${listSales.currentPage lt listSales.totalPages }">
							<li><a
								href="report-salesorder?page=${listSales.currentPage + 1}"><span><i
										class="fa fa-angle-right" aria-hidden="true"></i> </span></a></li>
							<li><a
								href="report-salesorder?page=${listSales.lastRecordValue}"><span><i
										class="fa fa-angle-double-right" aria-hidden="true"></i> </span></a></li>
						</c:if>

					</ul>
				</nav>
			</c:if>
			<!-- NAVIGATION FOR VIEW LIST ENDS HERE -->





			<!-- SEARCH LIST START HERE -->
			<c:if test="${!empty listSalesSearch.list}">
				<div class="col-sm-12" style="margin-top: -27px">
					<div class="pi-responsive-table-sm">
						<div class="pi-section-w pi-section-white piTooltips">
							<display:table id="data" name="${listSalesSearch.list}"
								requestURI="/report-salesorder" export="false"
								class="pi-table pi-table-complex pi-table-hovered pi-round pi-table-shadow pi-table-all-borders">
								<display:column property="sNo" title="S No" />
								<display:column property="salesOrderNo" title="Sales Order No" />
								<display:column property="accountBO.accountName"
									title="Account Name" />
								<display:column url="customer-tracking-status" media="html"
									paramId="id" paramProperty="id" title="View">
									<a href="view-sales-order-list?salesno=${data.salesOrderId}">
										<i style="text-align: center; color: black;" class="fa fa-eye"></i>
									</a>
								</display:column>
								<display:column property="date" title="Date" />

							</display:table>
						</div>
					</div>
				</div>

				<!-- SEARCH LIST ENDS HERE -->



				<!-- NAVIGATION FOR SEARCH LIST START HERE -->

				<nav style="text-align: center;">
					<ul class="pagination pagination-theme  no-margin center"
						style="margin-left: 575px;">
						<c:if test="${listSalesSearch.currentPage gt 1}">
							<li><a onclick=page(1)><span><i
										class="fa fa-angle-double-left" aria-hidden="true"></i> </span></a></li>
							<li><a onclick=page(${listSalesSearch.currentPage - 1})><span><i
										class="fa fa-angle-left" aria-hidden="true"></i> </span></a></li>
						</c:if>
						<c:forEach items="${listSalesSearch.noOfPages}" var="i">
							<c:choose>
								<c:when test="${listSalesSearch.currentPage == i}">
									<li class="active"><a
										style="color: #fff; background-color: #34495e">${i}</a></li>
								</c:when>
								<c:otherwise>
									<li><a onclick=page(${i})>${i}</a></li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
						<c:if
							test="${listSalesSearch.currentPage lt oppoLists.totalPages}">
							<li><a onclick=page(${listSalesSearch.currentPage + 1})><span><i
										class="fa fa-angle-right" aria-hidden="true"></i> </span></a></li>
							<li><a onclick=page(${listSalesSearch.lastRecordValue})><span><i
										class="fa fa-angle-double-right" aria-hidden="true"></i> </span></a></li>
						</c:if>
					</ul>
				</nav>

			</c:if>
			<!-- NAVIGATION FOR SEARCH LIST ENDS HERE -->

		</div>
	</div>
</div>

<!-- Start Create Employer -->

<div class="modal fade" id="myModal">
	<div class="modal-dialog popupwidth">
		<div class="modal-content"></div>


	</div>
</div>

</html>