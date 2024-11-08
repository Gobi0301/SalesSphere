<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>


<link href="resources/css/jquery-ui-1.10.4.custom.css" rel="stylesheet">
<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.10.2/themes/smoothness/jquery-ui.css" />
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
			var isValid = true;
			var sendto = $('#sentTo').val();
			if (sendto == 'Select') {
				isValid = false;
				$("#sentToErr").show();
				$("#sentToErr").html("Please Select Products");
				$("#sentTo").css({
					"border" : "1px solid red",
				});
			} else {
				$('#sentToErr').hide();
				$('#sentTo').css({
					"border" : "",
					"background" : ""
				});
			}
			var description = $('#descriptionId').val();
			if (description == '') {
				isValid = false;
				$("#descriptionErr").show();
				$("#descriptionErr").html("Please enter description");
				$("#descriptionId").css({
					"border" : "1px solid red",
				});
			} else {
				$('#descriptionErr').hide();
				$('#descriptionId').css({
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
					<h3>Approve Procurement</h3>
				</div>
				<form:form method="POST" id="addForm" action="approve-procurement"
					modelAttribute="approveBO">
					 <%-- <form:hidden path="procurementBO.approveBO.procurementId"/>
					<form:hidden path="approveBO.procurementBO.expectedDate"/>
					<form:hidden path="approveBO.supplierBo.supplierId"/>
					<form:hidden path="approveBO.productServiceBO.serviceId"/> --%>
					<input type="hidden" name="expectedDate" value="${expectedDate}"/>
					<input type="hidden" name="supplierId" value="${supplierId}"/>
					<input type="hidden" name="serviceId" value="${serviceId}"/>
					<input type="hidden" name="procurementId" value="${procurementId}"/>
					<div class="col-sm-12">
						<div class="col-sm-4">
							<div class="form-group">
								<label>Send To <span class="font10 text-danger">*</span></label>
								<form:select type="text" path="sentTo"
									class="form-control required">
									<form:option value="Select">--Select--</form:option>
									<form:option value="Sms">SMS</form:option>
									<form:option value="WhatsApp">WHATSAPP</form:option>
									<form:option value="Email">EMAIL</form:option>
									<form:option value="Call">CALL</form:option>
								</form:select>
								<form:errors path="sentTo" class="error" />
										<div id="sentToErr" style="color: red;"></div>
								</div>
							</div>	
						</div>
								<div class="col-sm-12">
									<div class="col-sm-4">
									<div class="form-group">
										<label>Description<span class="font10 text-danger">*</span></label>
										<label class="hidden-xs">&nbsp;</label>
										<form:textarea path="description" id="descriptionId"
											class="form-control required" placeholder="Description"
											cols="130" rows="06" maxlength="400" />
										<form:errors path="description" class="error" />
										<div id="descriptionErr" style="color: red;"></div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div style="text-align: right; margin-right: 31px">
						<div class="form-group">
							<button type="submit" id="btnsubmit"
								class="btn btn-t-primary btn-theme lebal_align mt-20 ">Submit</button>
							<a href="view-procurement"><span
								class="btn btn-t-primary btn-theme lebal_align mt-20">Cancel</span></a>
								
								<%-- <a href="approve-procurement?procurementId=${data.procurementId}"><span
								class="btn btn-t-primary btn-theme lebal_align mt-20">Cancel</span></a> --%>
						</div>
					</div>


				</form:form>

			</div>
		</div>
	</div>




</body>
</html>
