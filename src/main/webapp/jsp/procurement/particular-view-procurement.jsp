<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<link href="resources/theme/css/custom.css" rel="stylesheet">
<link href="resources/css/jquery-ui-1.10.4.custom.css" rel="stylesheet">
<script type="text/javascript" src="resources/js/jquery-ui.min.js"></script>
<script type="text/javascript"
	src="http://js.nicedit.com/nicEdit-latest.js"></script>


<div class="contact-form-wrapper" style="margin-top: 50px;">
	<div class="box-list">
		<div class="item">
			<div class="text-center underline">
				<h3>Procurement Details</h3>
			</div>
			<div class="item" style="background-color: #e1e1e1">

				<div class="desc list-capitalize">
					<div class="row clearfix" style="line-height: 2em;">
 
                         <div class="col-xs-4">
							<label style="font-weight: initial; font-weight: bold;">
								MinimumStock&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;&nbsp;&nbsp;&nbsp;</label>
							<c:out value="${particularlist.minimumStock}"></c:out>
						</div>
						<div class="col-xs-4">
							<label style="font-weight: initial; font-weight: bold;">
								MaximumStock&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;&nbsp;&nbsp;&nbsp;</label>
							<c:out value="${particularlist.maximumStock}"></c:out>
						</div>
						<div class="col-xs-4">
										<label style="font-weight: initial; font-weight: bold;">
											Product
											Name&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;&nbsp;&nbsp;&nbsp;</label>
										<c:out value="${particularlist.productServiceBO.serviceName}"></c:out>
									</div>
									<div class="col-xs-4">
										<label style="font-weight: initial; font-weight: bold;">
											Supplier
											Name&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;&nbsp;&nbsp;&nbsp;</label>
										<c:out value="${particularlist.supplierBO.supplierName}"></c:out>
									</div>
						<div class="col-xs-4">
							<label style="font-weight: initial; font-weight: bold;">
								AvailableStock &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;&nbsp;&nbsp;&nbsp;</label>
							<c:out value="${particularlist.availableStock}"></c:out>
						</div>
						<div class="col-xs-4">
							<label style="font-weight: initial; font-weight: bold;">
								Unit Cost&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;&nbsp;&nbsp;&nbsp;</label>
							<c:out value="${particularlist.unitOfCost}"></c:out>
						</div>
						<div class="col-xs-4">
							<label style="font-weight: initial; font-weight: bold;">
								Total Cost&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;&nbsp;&nbsp;&nbsp;</label>
							<c:out value="${particularlist.totalCost}"></c:out>
						</div>
						<div class="col-xs-4">
							<label style="font-weight: initial; font-weight: bold;">
								Expected Date&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;&nbsp;&nbsp;&nbsp;</label>
							<c:out value="${particularlist.expectedDate}"></c:out>
						</div>
						 
					</div>
				</div>
				
			</div>
			 
				 
		<!-- update -->

       <div class="text-center underline">
							<h3>Activity Procurement</h3>
						</div>
						 <h2>Hi Iam Santhosh</h2>
						 
						 
					
			<div class="item">
			<form:form method="POST" id="addForm" action="activity-procurement"
				modelAttribute="particularlist">
				<div class="row">
				<div class="col-sm-12">
				   <div class="col-sm-4">
				   
				   <div class="form-group">
					 <label class="element-block fw-normal font-lato">Created Date<span
									class="font10 text-danger">*</span></font>
								</label>
								<form:input type="date" id="createdDate" path="createdDate"
									placeholder="Date" class="form-control element-block" />
								<label class="element-block fw-normal font-lato"
									style="font-size: 10px">EX:MM/DD/YYYY</label>
					 
					 
					 </div>
					  <div class="col-sm-4">
				   <div class="form-group">
					 <label class="element-block fw-normal font-lato">Modify Date<span
									class="font10 text-danger">*</span> 
								</label>
								<form:input type="date" id="modifyDate" path="modifyDate"
									placeholder="Date" class="form-control element-block" />
								<label class="element-block fw-normal font-lato"
									style="font-size: 10px">EX:MM/DD/YYYY</label>
					 
					 
					 </div>
				
				   </div>
				   <div class="col-sm-4">
				   <div class="form-group">
					 <label class="element-block fw-normal font-lato">EntityType<span
									class="font10 text-danger">*</span> 
								</label>
								<form:input type="text" id="entityType" path="entityType"
									placeholder="text" class="form-control element-block" />
								 
					 
					 
					 </div>
				
				   </div>
				    
				   </div>
				   </div>
				    
                   </div>
                   
     
				<div class="row">
					<div class="col-md-12">
						<div style="text-align: right; margin-right: 31px;">
							<button type="submit" id="btnsubmit"
								class="btn btn-t-primary btn-theme lebal_align mt-20">Submit</button>
							<a href="particular-view-procurement"><span
								class="btn btn-t-primary btn-theme lebal_align mt-20">Cancel</span></a>
						</div>
					</div>
				</div>
					</form:form>
		</div>
		</div>
	</div>
</div>
 
 