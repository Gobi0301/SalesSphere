<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>


<link href="resources/css/jquery-ui-1.10.4.custom.css" rel="stylesheet">
<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.2/themes/smoothness/jquery-ui.css" />
    <script src="http://code.jquery.com/jquery-1.9.1.js"></script>
    <script src="http://code.jquery.com/ui/1.10.2/jquery-ui.js"></script>
    <link rel="stylesheet" href="/resources/demos/style.css" />
   
    
    

<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<script>
	$(document).ready(function() {
		$('#btnsubmit').click(function(e) {
			//Search Name Validation
			var reject = $('#rejectId').val();
			if (reject == '') {
				isValid = false;
				$("#rejectErr").show();
				$("#rejectErr").html("Please enter description");
				$("#rejectId").css({
					"border" : "1px solid red",
				});
			} else {
				$('#rejectErr').hide();
				$('#rejectId').css({
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
<div class="box-list">
<div class="item">
<div class="row ">
<div class="text-center underline">
							<h3>Reject Procurement</h3>
						</div>
						<form:form method="POST" id="addForm" action="reject-procurement " modelAttribute="rejectBO">
						<input type="hidden" name="supplierId" value="${supplierId}"/>
					<input type="hidden" name="serviceId" value="${serviceId}"/>
					<input type="hidden" name="procurementId" value="${procurementId}"/>
						<div class="col-sm-12">
							<div class="col-sm-4">
							<div class="form-group">
							<label>What Reason<span class="font10 text-danger">*</span></label>
									<label class="hidden-xs"></label>
										<form:textarea path="reason" id="rejectId" 
											class="form-control required" placeholder="Description" cols="130" rows="06" 
											maxlength="400" />
										<form:errors path="reason" class="error" />
										<div id="rejectErr" style="color: red;"></div>
							
							
							</div>
							</div>
							</div>
						<div style="text-align: right; margin-right: 31px">
						<div class="form-group">
							<button type="submit" id="btnsubmit"
								class="btn btn-t-primary btn-theme lebal_align mt-20 ">Submit</button>
							<a href="view-procurement?page=1"><span
								class="btn btn-t-primary btn-theme lebal_align mt-20">Cancel</span></a>
						</div>
					</div>
						
						
						</form:form>
						
						</div>
						</div>
						</div>