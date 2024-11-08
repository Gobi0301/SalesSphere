<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<link href="resources/css/jquery-ui-1.10.4.custom.css" rel="stylesheet">
<script type="text/javascript" src="resources/js/jquery-ui.min.js"></script>
<script type="text/javascript"
	src="http://js.nicedit.com/nicEdit-latest.js"></script>

<script type="text/javascript">
	function workitemCodeCheck() {
		var workItemCode = document.getElementById("workItemCodeId").value;
		 document.getElementById("btnsubmit").disabled = false;
		if (workItemCode != '') {
			$.ajax({
				url : "check_workitem",
				type : "GET",
				data : 'workItemCode=' + workItemCode,
				success : function(result) {

					if (result == true) {
						$("#workItemCodeErr").html("Workitem Code Already Exists");
						document.getElementById("btnsubmit").disabled = true;
						$("#workItemCodeErr").show();
						$("#workItemCodeId").css({
							"border" : "1px solid red",
						});
					} else {
						$("#workItemCodeErr").hide();
						$("#workItemCodeId").css({
							"border" : "",
							"background" : ""
						});
					}
				}
			});
		}
	};
</script>

<script>
	$(document).ready(function() {

		$('#btnsubmit').click(function(e) {

			//Workitem type
			var isValid = true;
			var workItemType = $('#WorkItemTypeId').val();
			if (workItemType == '') {
				isValid = false;
				$("#workItemTypeErr").show();
				$("#workItemTypeErr").html("Please Enter type");
				$("#WorkItemTypeId").css({
					"border" : "1px solid red",
				});
			} else {
				$('#workItemTypeErr').hide();
				$('#WorkItemTypeId').css({
					"border" : "",
					"background" : ""
				});
			}
			

			//Workitem Code
			var workItemCode = $('#workItemCodeId').val();
			if (workItemCode == '') {
				isValid = false;
				$("#workItemCodeErr").show();
				$("#workItemCodeErr").html("Please Enter WorkItemCode");
				$("#workItemCodeId").css({
					"border" : "1px solid red",
				});
			}else if (!/^[a-zA-Z0 -9._/&\%]*$/.test(workItemCode))  {
				isValid = false;
				$("#workItemCodeErr").show();
				$("#workItemCodeErr").html("Please Enter Workitem Code(Ex:Soft123)");
				$("#workItemCodeId").css({
					"border" : "1px solid red",
				});
			}  else {
				$('#workItemCodeErr').hide();
				$('#workItemCodeId').css({
					"border" : "",
					"background" : ""
				});
			}
			//Workitem name
			var workItem = $('#workItemId').val();
			if (workItem == '') {
				isValid = false;
				$("#workItemErr").show();
				$("#workItemErr").html("Please Enter WorkItem");
				$("#workItemId").css({
					"border" : "1px solid red",
				});
			}  else if (!/^[a-zA-Z ]*$/g.test(workItem)) {
				isValid = false;
				$("#workItemErr").show();
				$("#workItemErr").html("Please enter characters only");
			} else {
				$('#workItemErr').hide();
				$('#workItemId').css({
					"border" : "",
					"background" : ""
				});
			}
			if (!isValid){
				e.preventDefault();
			}});
	});
</script>


<link href="resources/css/jquery-ui-1.10.4.custom.css" rel="stylesheet">


<div class="contact-form-wrapper">

	<div class="box-list">
		<div class="item">
			<div class="row ">
				<div class="text-center underline">
					<h3>Create WorkItem</h3>
				</div>

				<form:form method="POST" id="addForm" action="create-workitem"
					modelAttribute="workItemBO">
					<div class="col-sm-12">


						<div class="col-sm-4">
							<div class="form-group">
								<label> WorkItem Type <span
									class="font10 text-danger">*</span></label>
								<form:input type="text" id="WorkItemTypeId" path="workItemType"
									class="form-control required" placeholder="WorkItem Type"
									maxlength="120" />
								<form:errors path="workItemType" class="error" />
								<div id="workItemTypeErr" style="color: red;"></div>
							</div>
						</div>
						<div class="col-sm-4">
							<div class="form-group">
								<label> WorkItem Code<span class="font10 text-danger">*</span></label>
								<form:input type="text" id="workItemCodeId" path="workItemCode"
									class="form-control required" placeholder="WorkItem Code"
									maxlength="120" onchange="workitemCodeCheck()"/>
								<form:errors path="workItemCode" class="error" />
								<div id="workItemCodeErr" style="color: red;"></div>
							</div>
						</div>
						<div class="col-sm-4">
							<div class="form-group">
								<label> WorkItem Name<span class="font10 text-danger">*</span></label>
								<form:input type="text" id="workItemId" path="workItem"
									class="form-control required" placeholder="WorkItem Name"
									maxlength="120" />
								<form:errors path="workItem" class="error" />
								<div id="workItemErr" style="color: red;"></div>
							</div>
						</div>
					</div>
			</div>
			<div style="text-align: right; margin-right: 31px">
				<div class="form-group">
					<form:button type="submit" id="btnsubmit"
						class="btn btn-t-primary btn-theme lebal_align mt-20 ">Submit</form:button>
					<a href="list-workitems"><span
						class="btn btn-t-primary btn-theme lebal_align mt-20">Cancel</span></a>
				</div>
			</div>
			</form:form>
		</div>
	</div>
</div>
</div>
<br>
<br>
