

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<script>
	$(function() {
		$("#startDateInput").datepicker({
			changeMonth : true,
			changeYear : true,
			numberOfMonths : 1,
			onSelect : function(selected) {
				var dt = new Date(selected);
				dt.setDate(dt.getDate());
			}
		});
	});
</script>

<script>
	$(document).ready(function() {
		$('#btnsubmit').click(function(e) {
			var isValid = true;

			var name = $('#name').val();
			if (name == '') {
				isValid = false;
				$("#nameErr").show();
				$("#nameErr").html("Please enter role name ");
				$("#name").css({
					"border" : "1px solid red",

				});

			} /* else if (!/"^[a-zA-Z'-]+([a-zA-Z'-]+)*$"
									) {
								isValid = false;
								$("#nameErr").show();
								$("#nameErr").html("Please enter characters only");
								$("#name").css({
									"border" : "1px solid red",

								});

							} */
			else if (!/^[a-zA-Z_\s]*$/g.test(name)) {
				$("#nameErr").show();
				$("#nameErr").html("Please Enter Character Only");
				var isValid = false;
			} else {
				$('#nameErr').hide();
				$('#name').css({

					"border" : "",
					"background" : ""
				});
			}

			if (isValid == false)
				e.preventDefault();

		});
	});
</script>
<script>
	$(document).ready(function() {
		$('#search').click(function(e) {
			var isValid = true;

			var roleName = $('#RoleNameId').val();
			if (roleName == '') {
				isValid = false;
				$("#RoleNameErr").show();
				$("#RoleNameErr").html("Please enter role name ");
				$("#RoleNameId").css({
					"border" : "1px solid red",

				});

			} else if (!/^[a-zA-Z\s]*$/g.test(userName)) {
				isValid = false;
				$("#RoleNameErr").show();
				$("#RoleNameErr").html("Please enter characters only");
				$("#RoleNameId").css({
					"border" : "1px solid red",

				});

			} else {
				$('#RoleNameErr').hide();
				$('#RoleNameId').css({

					"border" : "",
					"background" : ""
				});
			}

			if (isValid == false)
				e.preventDefault();

		});
	});
</script>

<script type="text/javascript">
	function roleCheck() {
		var roleName = document.getElementById("name").value;
		document.getElementById("btnsubmit").disabled = false;
		if (roleName != '') {
			$.ajax({
				url : "check_roleName",
				type : "GET",
				data : 'roleName=' + roleName,
				success : function(result) {

					if (result == true) {
						$("#nameErr").html("Role Name Already Exists");
						document.getElementById("btnsubmit").disabled = true;
						$("#nameErr").show();
					} else {
						$("#nameErr").hide();
					}
				}
			});
		}
	};
</script>

<div class="contact-form-wrapper">
	<div class="box-list">
		<div class="item">
			<div class="row" style="margin: 15px 15px 15px 15px;">
				<div class="text-center underline">
					<h3 style="height: 35px; font-weight: 700;">Create Role</h3>
				</div>
				<br>
				<form:form method="POST" id="addForm" action="check_roleName"
					modelAttribute="roleobj" commandName="roleobj">
					<div>
						<c:if test="${not empty createinfomessage}">

							<c:out value="${createinfomessage}"></c:out>
						</c:if>
					</div>
					<div>
						<c:if test="${not empty createerrormessage}">
							<c:out value="${createerrormessage}"></c:out>
						</c:if>
					</div>
					<div class="row">
						<div class="col-sm-4">
							<div class="form-group">
								<label>Role Name<span class="font10 text-danger">*</span></label>
								<form:input type="text" path="roleName" id="name"
									class="form-control required" placeholder="RoleName"
									maxlength="150" onchange="roleCheck()" />
								<form:errors path="roleName" cssClass="error" />
								<div id="nameErr" style="color: red;"></div>
							</div>
						</div>
					</div>
					<div class="row">

						<div class="col-sm-1">
							<button type="submit" id="btnsubmit"
								class="btn btn-t-primary btn-theme lebal_align">Submit</button>
						</div>

						<div class="col-sm-1">
							<a href="view-role"><span
								class="btn btn-t-primary btn-theme lebal_align">Cancel</span></a>
						</div>
					</div>


				</form:form>
				<br>

		<%-- 	 <c:if test="${not empty sucessmessage}">
					<div class="alert alert-success">
						<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
						<strong>Success:</strong>
						<c:out value="${sucessmessage}"></c:out>
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

				<c:if test="${not empty successMessage}">
					<div class="alert alert-info" role="alert"
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
				<c:if test="${not empty infoMessage}">
			<div class="alert alert-info" role="alert"
				style="font-size: 12px; padding: 8px 9px 5px 10px; margin-top: 15px;">
				<button type="button" class="close" data-dismiss="alert"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<strong>Info!</strong>
				<c:out value="${infoMessage}"></c:out>
			</div>
		</c:if>

				<div class="row">
					<div class="pi-responsive-table-sm">
						<div class="pi-section-w pi-section-white piTooltips">

							<h3 class="text-center no-margin titleunderline underline"
								style="margin-top: -10px;">List Role</h3>
							<form:form id="myForm" method="post" class="login-form clearfix"
								action="search-role" commandName="searchObj">
								<div class="row"
									style="border: 4px solid #e6e6e6; margin: 15px 15px 15px 15px; background-color: #e1e1e1">
									<div class=" col-md-3">
										<div class="form-group home-left">
											<label class="hidden-xs"></label>
											<form:input type="text" class="form-control" path="roleName"
												escapeXml="false" placeholder="RoleName" id="RoleNameIds"
												style="height: 35px;font-weight: 700;"></form:input>
											<form:errors path="roleName" cssClass="error" />
											<div id="RoleNameErr" style="color: red;"></div>
										</div>
									</div>

									<div class=" col-md-1" style="padding-bottom: 0px;">
										<div class="form-group home-right">
											<label class="hidden-xs"></label>
											
											<button class="btn btn-theme btn-success btn-block"
												id="search"
												style="padding: 6px 5px; background-color: #7cb228; border-color: #7cb228;">
												<small><i class="fa fa-search" aria-hidden="true"
													style="font-size: 20px;"></i></small>
											</button>
										</div>
									</div>

								</div>
							</form:form>
							<c:if test="${!empty listroleobj}">
							<display:table id="data" name="${listroleobj}"
								requestURI="/view-role" pagesize="10" export="false"
								class="pi-table pi-table-complex pi-table-hovered pi-round pi-table-shadow pi-table-all-borders">

								<display:column property="sNo" title="S.No" />

								<display:column property="roleName" title="RoleName" />
								<display:column url="edit-role-type" media="html"
									paramId="roleId" paramProperty="roleId" title="Edit">
									<a href="edit-role-type?roleId=${data.roleId}"><i
										style="text-align: center;" class="fa fa-pencil"></i></a>

								</display:column>
								<display:column url="delete-role-type" media="html"
									paramId="roleId" paramProperty="roleId" title="Delete">
									<a href="delete-role-type?roleId=${data.roleId}"
										onclick="return confirm('Are you sure you want to Delete?')"><i
										style="text-align: center;" class="fa fa-trash"></i></a>
								</display:column>
							</display:table>
                         </c:if>
						</div>
					</div>
				</div> --%>
				
			</div>
		</div>
	</div>
</div>