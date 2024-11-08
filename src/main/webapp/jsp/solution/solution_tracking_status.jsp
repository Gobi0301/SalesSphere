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
				<h3>Solution Details</h3>
			</div>
			<div class="row"
							style="border: 4px solid #e6e6e6; margin: 15px 0px 15px 0px;background-color: #2a3f54">
							<h3 style="color:#fff">Solution Information</h3>

						</div>
			<div class="item" style="background-color: #e1e1e1">

				<div class="desc list-capitalize">
					<div class="row clearfix" style="line-height: 2em;">

						<div class="col-xs-6">
							<label style="font-weight: initial; font-weight: bold;">
								Solution Title</label>:
							<c:out value="${ viewsolution.solutionTitle}"></c:out>
						</div>

						<div class="col-xs-6">
							<label style="font-weight: initial; font-weight: bold;">
								Solution Owner</label>:
							<c:out value="${ viewsolution.adminUserBo.name}"></c:out>
						</div>

						<div class="col-xs-6">
							<label style="font-weight: initial; font-weight: bold;">
								Product Name</label>:
							<c:out value="${ viewsolution.inventoryBo.serviceName}"></c:out>
						</div>

						<div class="col-xs-6">
							<label style="font-weight: initial; font-weight: bold;">
								Solution Status</label>:
							<c:out value="${ viewsolution.status}"></c:out>
						</div>
					</div>
				</div>
			</div>
			<div class="row"
							style="border: 4px solid #e6e6e6; margin: 15px 0px 15px 0px; background-color: #2a3f54">
							<h3 style="color:#fff">Description Information</h3>
						</div>
			<div class="item" style="background-color: #e1e1e1">
				<div class="desc list-capitalize">
					<div class="row clearfix" style="line-height: 2em;">
						<div class="col-xs-6">
							<label style="font-weight: initial; font-weight: bold;">
								Question</label>:
							<c:out value="${ viewsolution.question}"></c:out>
						</div>
						<div class="col-xs-6">
							<label style="font-weight: initial; font-weight: bold;">
								Answer</label>:
							<c:out value="${ viewsolution.answer}"></c:out>
						</div>
						<div class="col-xs-6">
							<label style="font-weight: initial; font-weight: bold;">
								Description</label>:
							<c:out value="${ viewsolution.description}"></c:out>
						</div>
						<div class="col-xs-6">
							<label style="font-weight: initial; font-weight: bold;">
								comments</label>:
							<c:out value="${ viewsolution.comments}"></c:out>
						</div>
					</div>
				</div>
			</div>
		</div>
		<br> <br>
						<div style="text-align: right; margin-right: 31px">
							<a href="view_solution"><span
								class="btn btn-t-primary btn-theme lebal_align mt-20">Back</span></a>
						</div>
		
	</div>
</div>






