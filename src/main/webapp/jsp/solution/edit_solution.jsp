<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<link href="resources/css/jquery-ui-1.10.4.custom.css" rel="stylesheet">
<div class="warning">
<script>
		$(document).ready(function() {

			$('#caseReason').focus();

			$('#search').click(function(e) {
				var isValid = true;
				var name = $('#solutionTitleId').val();
				if (name == '') {
					isValid = false;
					$("#solutionTitleErr").show();
					$("#solutionTitleErr").html("Please enter Solution Title");
					$("#solutionTitleId").css({"border" : "1px solid red",});
				} else {
					$('#solutionTitleErr').hide();
					$('#solutionTitleId').css({

						"border" : "",
						"background" : ""
					});
				}
				//Solution owner
				
				var name = $('#nameId').val();
				if (name =='') {
					isValid = false;
					$("#nameIdErr").show();
					$("#nameIdErr").html("Please select Solution Owner");
					$("#nameId").css({
						"border" : "1px solid red",

					});

				} else {
					$('#nameIdErr').hide();
					$('#nameId').css({

						"border" : "",
						"background" : ""
					});
				}
				//Product name
				
				var name = $('#serviceId').val();
				if (name =='') {
					isValid = false;
					$("#serviceIdErr").show();
					$("#serviceIdErr").html("Please select Product name");
					$("#serviceId").css({
						"border" : "1px solid red",

					});

				} else {
					$('#serviceIdErr').hide();
					$('#serviceId').css({

						"border" : "",
						"background" : ""
					});
				}
				//Category
				
				var name = $('#categoryid').val();
				if (name =='') {
					isValid = false;
					$("#categoryidErr").show();
					$("#categoryidErr").html("Please enter Category");
					$("#categoryid").css({"border" : "1px solid red",
						});
				} else {
					$('#categoryidErr').hide();
					$('#categoryid').css({

						"border" : "",
						"background" : ""
					});
				}
				//Solution Status
				
				var name = $('#solutionIdInput').val();
				if (name =='') {
					isValid = false;
					$("#solutionIdErr").show();
					$("#solutionIdErr").html("Please select Solution Status");
					$("#solutionIdInput").css({
						"border" : "1px solid red",

					});

				} else {
					$('#solutionIdErr').hide();
					$('#solutionIdInput').css({

						"border" : "",
						"background" : ""
					});
				}
				//question
				
				var name = $('#questionId').val();
				if (name =='') {
					isValid = false;
					$("#questionidErr").show();
					$("#questionidErr").html("Please enter question");
					$("#questionId").css({
						"border" : "1px solid red",

					});

				} else {
					$('#questionidErr').hide();
					$('#questionId').css({

						"border" : "",
						"background" : ""
					});
				}
				//Answer
				
				var name = $('#answerId').val();
				if (name =='') {
					isValid = false;
					$("#answeridErr").show();
					$("#answeridErr").html("Please enter answer");
					$("#answerId").css({
						"border" : "1px solid red",

					});

				} else {
					$('#answeridErr').hide();
					$('#answerId').css({

						"border" : "",
						"background" : ""
					});
				}
				//description
				
				var name = $('#descriptionId').val();
				if (name =='') {
					isValid = false;
					$("#descriptionidErr").show();
					$("#descriptionidErr").html("Please enter description");
					$("#descriptionId").css({
						"border" : "1px solid red",

					});

				} else {
					$('#descriptionidErr').hide();
					$('#descriptionId').css({

						"border" : "",
						"background" : ""
					});
				}
				//comments
				
				var name = $('#commentsid').val();
				if (name =='') {
					isValid = false;
					$("#commentsidErr").show();
					$("#commentsidErr").html("Please enter comments");
					$("#commentsid").css({"border" : "1px solid red",});

				} else {
					$('#commentsidErr').hide();
					$('#commentsid').css({

						"border" : "",
						"background" : ""
					});
				}
				if (isValid == false)
					e.preventDefault();
			});
		});

		</script>

	<c:if test="${not empty successMessage}">
		<div class="alert alert-success" role="alert"
			style="font-size: 12px; padding: 8px 9px 5px 10px; margin-top: 15px;">
			<button type="button" class="close" data-dismiss="alert"
				aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
			<strong>success!</strong>
			<c:out value="${successMessage}"></c:out>
		</div>
	</c:if>
	<c:if test="${not empty errorMessage}">
		<div class="alert alert-info" role="alert"
			style="font-size: 12px; padding: 8px 9px 5px 10px; margin-top: 15px;">
			<button type="button" class="close" data-dismiss="alert"
				aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
			<strong>Info!</strong>
			<c:out value="${errorMessage}"></c:out>
		</div>
	</c:if>
</div>

	<div class="contact-form-wrapper">
		<div class="box-list">
			<div class="item">
				<div class="row ">
					<div class="text-center underline">
						<h3>Edit Solution</h3>
					</div>
					<form:form method="POST" id="addForm" action="edit_solution"
						modelAttribute="solutionbo">
						<form:hidden path="solutionId" />
						
							
								<div class="row"
									style="border: 4px solid #e6e6e6; margin: 15px 0px 15px 0px; background-color: #2a3f54">
									<h3 class="title" style="color:#fff">Solution Information</h3>
								</div>
								<div class="box-list">
							<div class="item">
								<div class="row">
								<div class="col-sm-12">
									<div class="col-sm-4">
										<div class="form-group">
											<label path="solutionTitle"> Solution Title <span
												class="font10 text-danger">*</span></label>
											<form:input type="text" id="solutionTitleId" path="solutionTitle"
												class="form-control required" placeholder="Solution Title "
												maxlength="150" />
											<form:errors path="solutionTitle" class="error" />
											<div id="solutionTitleErr" style="color: red;"></div>
										</div>
									</div>
									<div class="col-sm-4">
										<div class="form-group">
											<label>Solution Owner<span class="font10 text-danger">*</span></label>
											<form:select type="text" path="adminUserBo.name" id="nameId"
												class="form-control required">
												<%-- <c:if test="${not empty solutionbo.solutionOwner}"> --%>
												<form:option value="${solutionbo.adminUserBo.userId}">${solutionbo.adminUserBo.name}</form:option>
									
												<form:option value="">-- Select --   </form:option>
												<form:options items="${userBOList}" itemLabel="name"
													itemValue="id" />
											</form:select>
											 <form:errors path="adminUserBo.Name" class="error" />
												    <div id="nameIdErr" style="color: red;"></div>
										</div>
									</div>

									<div class="col-sm-4">
										<div class="form-group">
											<label>Product Name<span class="font10 text-danger">*</span></label>
											<form:select type="text" path="inventoryBo.serviceName" id="serviceId"
												class="form-control required">
												<%-- <c:if test="${not empty solutionbo.productName}"> --%>
												<form:option value="${solutionbo.inventoryBo.serviceId}">${solutionbo.inventoryBo.serviceName}</form:option>
											
												<form:option value="">-- Select --   </form:option>
												<form:options items="${productList}" itemLabel="serviceName"
													itemValue="serviceId" />
											</form:select>
											<form:errors path="inventoryBo.serviceName" class="error" />
												    <div id="serviceIdErr" style="color: red;"></div>
											
										</div>
									</div> 
								</div>
								<div class="col-sm-12">
									<div class="col-sm-4">
										<div class="form-group">
											<label path="category">Category <span
												class="font10 text-danger">*</span></label>
											<form:input type="text" id="categoryid" path="category"
												class="form-control required" placeholder="Category"
												maxlength="150" />
											<form:errors path="category" class="error" />
											<div id="categoryidErr" style="color: red;"></div>
										</div>
									</div>
									<div class="col-sm-4">
										<div class="form-group">
											<label>Solution Status<span
												class="font10 text-danger">*</span></label>
											<form:select type="text" path="status" id="solutionIdInput"
												class="form-control required">
												<form:option value="">--Select Status--</form:option>
												<form:option value="All Solution">All Solution</form:option>
												<form:option value="My Solution">My Solution</form:option>
												<form:option value="Published Solution">Published Solution</form:option>
											</form:select>
											<form:errors path="status" class="error" />
											<div id="solutionIdErr" style="color: red;"></div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<br>
						<br>
						<div class="row"
									style="border: 4px solid #e6e6e6; margin: 15px 0px 15px 0px; background-color: #2a3f54">
									<h3 class="title" style="color:#fff">Description Information</h3>
									<!-- style="background:gray;" -->
								</div>
                        <div class="box-list">
						<div class="item">
						<div class="row">
								<div class="col-sm-12">
							<div class="col-sm-6">
								<div class="form-group">
									<label>Question<span class="font10 text-danger">*</span></label>
									<label class="hidden-xs"></label>
									<form:textarea path="question" id="questionId"
										class="form-control required" placeholder="question"
										cols="130" rows="06" maxlength="500" />
									<form:errors path="question" class="error" />
									<div id="questionidErr" style="color: red;"></div>
								</div>
							</div>

							<div class="col-sm-6">
								<div class="form-group">
									<label>Answer<span class="font10 text-danger">*</span></label>
									<label class="hidden-xs"></label>
									<form:textarea path="answer" id="answerId"
										class="form-control required" placeholder="answer" cols="130"
										rows="06" maxlength="400" />
									<form:errors path="answer" class="error" />
												<div id="answeridErr" style="color: red;"></div>
								</div>
							</div>
						</div>

						<div class="col-sm-12">
							<div class="col-sm-6">
								<div class="form-group">
									<label>Description<span class="font10 text-danger">*</span></label>
									<label class="hidden-xs"></label>
									<form:textarea path="description" id="descriptionId"
										class="form-control required" placeholder="Description"
										cols="130" rows="06" maxlength="400" />
									<form:errors path="description" class="error" />
									<div id="descriptionidErr" style="color: red;"></div>
								</div>
							</div>
							<div class="col-sm-6">
								<div class="form-group">
									<label>Comments<span class="font10 text-danger">*</span></label>
									<label class="hidden-xs"></label>
									<form:textarea path="comments" id="commentsid"
										class="form-control required" placeholder="Comments"
										cols="130" rows="06" maxlength="400" />
									<form:errors path="comments" class="error" />
												<div id="commentsidErr" style="color: red;"></div>
								</div>
							</div>
						</div>
						
								</div>
								</div>
								<br>
								<br>
								<div style="text-align: right; margin-right: 31px">
									<button type="submit" id="search"
										class="btn btn-t-primary btn-theme lebal_align mt-20">Update</button>
									<a href="view_solution"><span
										class="btn btn-t-primary btn-theme lebal_align mt-20">Cancel</span></a>
								</div>
								
					</form:form>
				</div>
			</div>
		</div>
	</div>









