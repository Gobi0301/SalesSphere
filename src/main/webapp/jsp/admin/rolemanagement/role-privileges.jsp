<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<!-- <link href="resources/css/jquery-ui-1.10.4.custom.css" rel="stylesheet">
<script type="text/javascript" src="resources/js/jquery-ui.min.js"></script> -->


<!-- <script>
	$(document).ready(function() {

		$('#btnsubmit').click(function(e) {

			var name = $('#roleid').val();
			if (name === 'Select') {
				isValid = false;
				$("#roleNameErr").show();
				$("#roleNameErr").html("Please Select Role Name");
				$("#roleid").css({
					"border" : "1px solid red",

				});

			} else {
				$('#roleNameErr').hide();
				$('#roleid').css({

					"border" : "",
					"background" : ""
				});
			}

			//privilegename
			//var isValid = true;
			var privilege = $('#privilegenameId').val();
			if (privilege == '') {
				isValid = false;
				$("#privilegenameErr").show();
				$("#privilegenameErr").html("Please Select Role Name");
				$("#privilegenameId").css({
					"border" : "1px solid red",

				});

			} else {
				$('#privilegenameErr').hide();
				$('#privilegenameId').css({

					"border" : "",
					"background" : ""
				});
			}

			if (isValid == false)
				e.preventDefault();

		});
	});
</script> -->

<!-- <script type="text/javascript">

function pageNavigation(pageNo){
	$('#myForm').append('<input type="hidden" name="page" value="'+pageNo+'">');
	document.searchRole.submit();
}
</script> -->

<script>
	$(document).ready(function() {

		$('#btnsubmit').click(function(e) {
			//Privilege Name
			var isValid = true;
			var roleName = $('#rolenameid').val();
			if (roleName == 'select') {
				isValid = false;
				$("#roleNameErr").show();
				$("#roleNameErr").html("Please Select Role Name");
				$("#rolenameid").css({
					"border" : "1px solid red",

				});

			} else {
				$('#roleNameErr').hide();
				$('#rolenameid').css({

					"border" : "",
					"background" : ""
				});
			}

			var checkedCheckboxes = $(':checkbox:checked').length;

			if (checkedCheckboxes === 0) {
				isValid = false;
				$("#privilegenameErr").show();
				$("#privilegenameErr").html("Please select at least one privilege checkbox");

			}

			if (isValid == false)
				e.preventDefault();

		});
	});
</script>

<!-- <script>
	$(document).ready(function() {

		$('#search').click(function(e) {
			//role Privilege....
			var isValid = true;
			var roleName = $('#roleNameId').val();
			if (roleName == '') {
				isValid = false;
				$("#roleErr").show();
				$("#roleErr").html("Please Enter Role Name");
				$("#roleNameId").css({
					"border" : "1px solid red",

				});

			} else {
				$('#roleErr').hide();
				$('#roleNameId').css({

					"border" : "",
					"background" : ""
				});
			}

			if (isValid == false)
				e.preventDefault();

		});
	});
</script> 
 -->

<div class="contact-form-wrapper">
	<div class="box-list">
		<div class="item">
			<div class="row">
				<div class="text-center underline">
					<h3>Assign RolePrivileges</h3>
				</div>
				<br>
				<c:if test="${not empty successMessage}">
					<div class="alert alert-success">
						<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
						<strong>Success:</strong>
						<c:out value="${successMessage}"></c:out>
					</div>
				</c:if>
				<c:if test="${not empty errorMessage}">
					<div class="alert alert-success">
						<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
						<strong>Success:</strong>
						<c:out value="${errorMessage}"></c:out>
					</div>

				</c:if>
				<div class="form-group">
					<form:form method="post" modelAttribute="roletype"
						action="role-privileges">

						<div>
							<form:hidden path="roleId" />
						</div>


						<label>Role <span class="font10 text-danger"> *</span></label>
						<form:select path="roleName" id="rolenameid"
							class="form-control required">
							<form:option value="select">--Select--</form:option>
							<form:options items="${roleboList.list}" itemLabel="roleName"
								itemValue="roleId"></form:options>


							<%-- <c:forEach items="${rolebolists}" var="rolename">
								<form:option value="${rolename.roleName}"></form:option>
							</c:forEach> --%>
						</form:select>
						<form:errors path="roleName" class="error" />
						<div id="roleNameErr" style="color: red;"></div>

						</td>





						<br />
						<br>
						<label>Privilege <span class="font10 text-danger">*</span></label>
						<form:checkboxes items="${listprivilege}"
							itemLabel="privilegename" itemValue="privilegeId"
							path="privilegename" id="privilegenameId" />
						<form:errors path="privilegename" class="error" />
						<div id="privilegenameErr" style="color: red;"></div>
						<br />
						<div style="text-align: right; margin-right: 31px">
							<button type="submit" id="btnsubmit"
								class="btn btn-t-primary btn-theme lebal_align mt-20">Submit</button>

							<a href="admin-home"><span
								class="btn btn-t-primary btn-theme lebal_align mt-20">Cancel</span></a>
						</div>


					</form:form>
 
					<h3 style="text-align: center;">View Role Privileges</h3>
					<form:form id="myForm" method="post" class="login-form clearfix"
                         action="search-role-Name" modelAttribute="searchRoleObj">
						<div class="row"
							style="border: 4px solid #e6e6e6; margin: 15px 15px 15px 15px; background-color: #e1e1e1">
							<div class=" col-md-3">
								<div class="form-group home-left">
									<label class="hidden-xs"></label>
									<form:input type="text" class="form-control" path="roleName" name="roleName"
										placeholder=" role privileges" id="roleNameId"
										escapeXml="false" style="height: 35px;font-weight: 700;" />

									<form:errors path="roleName" class="error" />
									<div id="roleErr" style="color: red;"></div>
								</div>
							</div>

							<div class=" col-md-1" style="padding-bottom: 0px;">
								<div class="form-group home-right">
									<label class="hidden-xs"></label>
									<button class="btn btn-theme btn-success btn-block" id="search"
										style="padding: 6px 5px; background-color: #7cb228; border-color: #7cb228;">
										<small><i class="fa fa-search" aria-hidden="true"
											style="font-size: 20px;"></i></small>
									</button>
								</div>
							</div>

						</div>
					</form:form>
					
					<c:if test="${not empty totalRoleCount}">

                        <div class="col-sm-12" style="margin-top: 20px">
                            <p>
                                <a class="btn btn-theme btn-xs btn-default"
                                    style="color: #1b1818; font-weight: bold;"><c:out
                                        value="${totalRoleCount}"></c:out></a> <strong
                                    class="color-black">Role Found</strong>
                            </p>
                        </div>

                    </c:if>

                   <c:if test="${not empty totalSearchCount}">

                        <div class="col-sm-12" style="margin-top: 20px">
                            <p>
                                <a class="btn btn-theme btn-xs btn-default"
                                    style="color: #1b1818; font-weight: bold;"><c:out
                                        value="${totalSearchCount}"></c:out></a> <strong
                                    class="color-black">Role Found</strong>
                            </p>
                        </div>

                    </c:if>
					
					<c:if test="${!empty roleboList.list}">
					<div class="row">
						<div class="pi-responsive-table-sm">
							<div class="pi-section-w pi-section-white piTooltips">
								<display:table id="data" name="${roleboList.list}" 
									requestURI="/role-privileges" export="false"
									class="pi-table pi-table-complex pi-table-hovered pi-round pi-table-shadow pi-table-all-borders">

									<display:column property="sNo" title="SNo" />

									<display:column property="roleName" title="Role Name" />


									<display:column url="edit-role-privileges" media="html"
										paramId="id" paramProperty="roleId" title="Edit">
										<a
											href="edit-role-privileges?roleId=${data.roleId}<%-- privilegeid=${data.privilegesbolist.privilegeId} --%>"><i
											style="text-align: center;" class="fa fa-pencil"></i></a>
									</display:column>
								</display:table>
							</div>
						</div>
					</div>
              </c:if>
				<%-- <nav style="text-align: center;">
					<ul class="pagination pagination-theme  no-margin center"
						style="margin-left: 575px;">
						<c:if test="${roleboList.currentPage gt 1}">
 
						  <li><a
								href="role-privileges?page=${roleboList.currentPage - 1}"><span><i
										class="fa fa-angle-left" aria-hidden="true"></i> </span></a></li> 

							 <li><a onclick=pageNavigation(1)><span><i
										class="fa fa-angle-double-left" aria-hidden="true"></i> </span></a></li>

							<li><a onclick=pageNavigation(${roleboList.currentPage
								- 1})><span><i class="fa fa-angle-left"
										aria-hidden="true"></i> </span></a></li>


						</c:if>
						<c:forEach items="${roleboList.noOfPages}" var="i">
							<c:choose>
								<c:when test="${roleboList.currentPage == i}">
									<li class="active"><a
										style="color: #fff; background-color: #34495e">${i}</a></li>
								</c:when>
								<c:otherwise>
									<li><a href="view-account?page=${i}">${i}</a></li> 

								 <li><a   onclick=pageNavigation(${i})>${i}</a></li> 

								</c:otherwise>
							</c:choose>
						</c:forEach>
						<c:if test="${roleboList.currentPage lt roleList.totalPages}">
						<li><a
								href="role-privileges?page=${roleboList.currentPage + 1}"><span><i
									class="fa fa-angle-right" aria-hidden="true"></i> </span></a></li>
							<li><a
								href="role-privileges?page=${roleboList.lastRecordValue}"><span><i
									class="fa fa-angle-double-right" aria-hidden="true"></i> </span></a></li>
						</c:if>
					</ul>
				</nav>
                  --%>
           
           
            <nav style="text-align: center;">
					<ul class="pagination pagination-theme  no-margin center"
						style="margin-left: 575px;">
						<c:if test="${roleboList.currentPage gt 1}">
							<li><a href="role-privileges?page=1&searchElement=${searchElement}"><span><i
										class="fa fa-angle-double-left" aria-hidden="true"></i> </span></a></li>
							<li><a
								href="role-privileges?page=${roleboList.currentPage - 1}&searchElement=${searchElement}"><span><i
										class="fa fa-angle-left" aria-hidden="true"></i> </span></a></li>
						</c:if>
						<c:forEach items="${roleboList.noOfPages}" var="i">
							<c:choose>
								<c:when test="${roleboList.currentPage == i}">

									<li class="active"><a
										style="color: #fff; background-color: #34495e">${i}</a></li>
								</c:when>
								<c:otherwise>
									<li><a href="role-privileges?page=${i}&searchElement=${searchElement}">${i}</a></li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
						<c:if test="${roleboList.currentPage lt accessList.totalPages}">
							<li><a
								href="role-privileges?page=${roleboList.currentPage + 1}&searchElement=${searchElement}"><span><i
										class="fa fa-angle-right" aria-hidden="true"></i> </span></a></li>
							<li><a
								href="role-privileges?page=${roleboList.lastRecordValue}&searchElement=${searchElement}"><span><i
										class="fa fa-angle-double-right" aria-hidden="true"></i> </span></a></li>
						</c:if>
					</ul>
				</nav>   <!-- hidden for pagination -->
           
           

				</div>
			</div>


		</div>

	</div>
</div>


