<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>


<style>
.title {
	background-color: #2a3f54;
	padding: 10px;
	color: #fff;
}
</style>
<link href="resources/css/jquery-ui-1.10.4.custom.css" rel="stylesheet">
<script type="text/javascript" src="resources/js/jquery-ui.min.js">
	
</script>
<script>
	    bkLib.onDomLoaded(function() {
		new nicEditor({
			buttonList : [ 'fontSize', 'bold', 'italic', 'underline', 'ol',
					'ul', 'strikeThrough', 'html' ]
		}).panelInstance('inputAddress');
	});
</script>
<div class="row scrollspy-sidenav pb-20 body-mt-15">
	   
	<script>
		$(document).ready(function() {

			$('#caseReason').focus();

			$('#btnsubmit').click(function(e) {
				var isValid = true;
				var name = $('#solutionTitleId').val();
				if (name == '') {
					isValid = false;
					$("#solutionTitleErr").show();
					$("#solutionTitleErr").html("Please enter Solution Title");
					$("#solutionTitleId").css({
						"border" : "1px solid red",
					});
				} else {
					$('#solutionTitleErr').hide();
					$('#solutionTitleId').css({

						"border" : "",
						"background" : ""
					});
				}
				//Solution owner
				var isValid = true;
				var name = $('#nameId').val();
				if (name == 'Select') {
					isValid = false;
					$("#nameIdErr").show();
					$("#nameIdErr").html("Please Select Solution Owner");
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
				var isValid = true;
				var name = $('#serviceId').val();
				if (name == 'Select') {
					isValid = false;
					$("#serviceIdErr").show();
					$("#serviceIdErr").html("Please Select Product name");
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
				var isValid = true;
				var name = $('#categoryid').val();
				if (name == '') {
					isValid = false;
					$("#categoryidErr").show();
					$("#categoryidErr").html("Please enter Category");
					$("#categoryid").css({
						"border" : "1px solid red",
					});
				} else {
					$('#categoryidErr').hide();
					$('#categoryid').css({

						"border" : "",
						"background" : ""
					});
				}
				//Solution Status
				var isValid = true;
				var name = $('#solutionId').val();
				if (name == 'Select') {
					isValid = false;
					$("#solutionIdErr").show();
					$("#solutionIdErr").html("Please Select Solution Status");
					$("#solutionId").css({
						"border" : "1px solid red",

					});

				} else {
					$('#solutionIdErr').hide();
					$('#solutionId').css({

						"border" : "",
						"background" : ""
					});
				}
				//question
				var isValid = true;
				var name = $('#questionid').val();
				if (name == '') {
					isValid = false;
					$("#questionidErr").show();
					$("#questionidErr").html("Please enter question");
					$("#questionid").css({
						"border" : "1px solid red",

					});

				} else {
					$('#questionidErr').hide();
					$('#questionid').css({

						"border" : "",
						"background" : ""
					});
				}
				//Answer
				var isValid = true;
				var name = $('#answerid').val();
				if (name == '') {
					isValid = false;
					$("#answeridErr").show();
					$("#answeridErr").html("Please enter answer");
					$("#answerid").css({
						"border" : "1px solid red",

					});

				} else {
					$('#answeridErr').hide();
					$('#answerid').css({

						"border" : "",
						"background" : ""
					});
				}
				//description
				var isValid = true;
				var name = $('#descriptionid').val();
				if (name == '') {
					isValid = false;
					$("#descriptionidErr").show();
					$("#descriptionidErr").html("Please enter description");
					$("#descriptionid").css({
						"border" : "1px solid red",

					});

				} else {
					$('#descriptionidErr').hide();
					$('#descriptionid').css({

						"border" : "",
						"background" : ""
					});
				}
				//comments
				var isValid = true;
				var name = $('#commentsid').val();
				if (name == '') {
					isValid = false;
					$("#commentsidErr").show();
					$("#commentsidErr").html("Please enter comments");
					$("#commentsid").css({
						"border" : "1px solid red",
					});

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

	<script type="text/javascript"
		src="http://js.nicedit.com/nicEdit-latest.js">
		
	</script>
	<div class="warning">

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
						<h3>Create Solution</h3>
					</div>
					<br>
					<form:form method="POST" id="addForm" action="create_solution"
						modelAttribute="solutionBO">
						<div class="row">
							<h3 class="title">Solution Information</h3>

						</div>
						<div class="box-list">
							<div class="item">
								<div class="row">

									<div class="col-sm-12">
										<div class="col-sm-4">
											<div class="form-group">
												<label path="solutionTitle"> Solution Title <span
													class="font10 text-danger">*</span></label>
												<form:input type="text" path="solutionTitle"
													id="solutionTitleId" class="form-control required"
													placeholder="Solution Title" maxlength="150" />
												<form:errors path="solutionTitle" class="error" />
												<div id="solutionTitleErr" style="color: red;"></div>
											</div>
										</div>
										<div class="col-sm-4">
											<div class="form-group">
												<label>Solution Owner<span
													class="font10 text-danger">*</span></label>
												<form:select type="text" path="adminUserBo.Name" id="nameId"
													class="form-control required">
													<form:option value="Select">-- Select --</form:option>
													<form:options items="${userBOList}" itemLabel="Name"
														itemValue="id" />
												</form:select>
												<form:errors path="adminUserBo.Name" class="error" />
												<div id="nameIdErr" style="color: red;"></div>
											</div>
										</div>

										<div class="col-sm-4">
											<div class="form-group">
												<label>Product Name<span class="font10 text-danger">*</span></label>
												<form:select type="text" path="inventoryBo.serviceName"
													id="serviceId" class="form-control required">
													<form:option value="Select">-- Select --</form:option>
													<form:options items="${productList}"
														itemLabel="serviceName" itemValue="serviceId" />
												</form:select>
												<form:errors path="inventoryBo.serviceName" class="error" />
												<div id="serviceIdErr" style="color: red;"></div>
											</div>
										</div>

									</div>

									<div class="col-sm-12">

										<div class="col-sm-4">
											<label path="category">Category<span
												class="font10 text-danger">*</span></label>
											<form:input type="text" id="categoryid" path="category"
												class="form-control required" placeholder="Category"
												maxlength="150" />
											<form:errors path="category" class="error" />
											<div id="categoryidErr" style="color: red;"></div>
										</div>
										<div class="col-sm-4">
											<div class="form-group">
												<label>Solution Status<span
													class="font10 text-danger">*</span></label>
												<form:select type="text" path="status" id="solutionId"
													class="form-control required">
													<form:option value="Select">--Select Status--</form:option>
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
						</div>
						<br>
						<br>
						<div class="row">
							<h3 class="title">Description Information</h3>
						</div>
						<div class="box-list">
							<div class="item">
								<div class="row ">

									<div class="col-sm-12">
										<div class="col-sm-6">
											<div class="form-group">
												<label path="question"> Question <span
													class="font10 text-danger">*</span></label>
												<form:textarea path="question" id="questionid"
													class="form-control required" placeholder="Question"
													cols="150" rows="06" maxlength="2000" />
												<form:errors path="question" class="error" />
												<div id="questionidErr" style="color: red;"></div>
											</div>
										</div>


										<div class="col-sm-6">
											<div class="form-group">
												<label path="answer"> Answer <span
													class="font10 text-danger">*</span></label>
												<form:textarea path="answer" id="answerid"
													class="form-control required" placeholder="Answer"
													cols="150" rows="06" maxlength="2000" />
												<form:errors path="answer" class="error" />
												<div id="answeridErr" style="color: red;"></div>
											</div>
										</div>
									</div>
									<div class="col-sm-12">

										<div class="col-sm-6">
											<div class="form-group">
												<label path="description"> Description <span
													class="font10 text-danger">*</span></label>
												<form:textarea path="description" id="descriptionid"
													class="form-control required" placeholder="Description"
													cols="150" rows="06" maxlength="2000" />
												<form:errors path="description" class="error" />
												<div id="descriptionidErr" style="color: red;"></div>
											</div>
										</div>

										<div class="col-sm-6">
											<div class="form-group">
												<label path="comments"> Comments <span
													class="font10 text-danger">*</span></label>
												<form:textarea path="comments" id="commentsid"
													class="form-control required" placeholder="comments"
													cols="150" rows="06" maxlength="2000" />
												<form:errors path="comments" class="error" />
												<div id="commentsidErr" style="color: red;"></div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<br>
						<br>



						<div style="text-align: right; margin-right: 31px">
							<button type="submit" id="btnsubmit"
								class="btn btn-t-primary btn-theme lebal_align mt-20">Submit</button>
							<a href="view_solution"><span
								class="btn btn-t-primary btn-theme lebal_align mt-20">Cancel</span></a>
						</div>


					</form:form>


				</div>
			</div>
		</div>
	</div>
</div>
