<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<script type="text/javascript" src="resources/js/jquery-ui.min.js"></script>
<script type="text/javascript"
	src="http://js.nicedit.com/nicEdit-latest.js"></script>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<script>
	$(document).ready(function() {

		$('#btnsubmit').click(function(e) {
			//Privilege Name
			var isValid = true;
			var name = $('#privilegeid').val();
			if (name == 'select') {
				isValid = false;
				$("#privilegeErr").show();
				$("#privilegeErr").html("Please Select Privilege Name");
				$("#privilegeid").css({
					"border" : "1px solid red",

				});

			} else {
				$('#privilegeErr').hide();
				$('#privilegeid').css({

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

			var privilegename = $('#privilegenameid').val();
			if (privilegename == '') {
				isValid = false;
				$("#privilegenameErr").show();
				$("#privilegenameErr").html("Please Enter Privilege Name ");
				$("#privilegenameid").css({
					"border" : "1px solid red",

				});

			} else if (!/^[a-zA-Z_&\s]*$/g.test(privilegename)) {
				isValid = false;
				$("#privilegenameErr").show();
				$("#privilegenameErr").html("Please enter characters only");
				$("#privilegenameid").css({
					"border" : "1px solid red",

				});

			} else {
				$('#privilegenameErr').hide();
				$('#privilegenameid').css({

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
    $(document)
            .ready(
                    function() {
                        var $infoAlertContainer = $('#infoAlertContainer');

                        $(document)
                                .on(
                                        'click',
                                        '#btnsubmit',
                                        function(e) {
                                            // Remove the previous alert if it exists
                                            $infoAlertContainer.empty();

                                            var checkedCheckboxes = $(':checkbox:checked').length;

                                            if (checkedCheckboxes === 0) {
                                                var infoMessage = 'Please select atleast one Access checkbox.';

                                                var $infoAlert = $('<div class="alert alert-info" id="infoAlert">'
                                                        + '<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>'
                                                        + '<strong>Info!</strong> <span id="infoMessage"></span>'
                                                        + '</div>');

                                                $infoAlert.find('#infoMessage')
                                                        .text(infoMessage);
                                                $infoAlertContainer
                                                        .append($infoAlert);

                                                $('html, body').animate({
                                                    scrollTop : 0
                                                }, 'slow');

                                                e.preventDefault();
                                            }
                                        });
                    });
    </script>
<div class="contact-form-wrapper" style="margin-top: 50px;">
	<div class="box-list">
		<div class="item">
			<div class="text-center underline">
				<h3>Privilege - Access</h3>
			</div>
			<br />
			<form:form method="POST" id="addForm"
				action="create-privilege-access" modelAttribute="privilegeBO">
				<div>
					<c:if test="${not empty infomessage }">
						<c:out value="${infomessage }"></c:out>
					</c:if>
				</div>
				<div id="infoAlertContainer"></div>
				<%-- 					<div class="warning">

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
	</div> --%>

				<div class="warning">

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
				</div>

				<div class="row">
					<div class="col-md-12">
						<div class="form-group">

							<label>Privilege Name <span class="font10 text-danger">
									*</span></label>
							<form:select path="privilegename" id="privilegeid"
								class="form-control required">
								<form:option value="select">--Select--</form:option>
								<form:options items="${privilegeList }"
									itemLabel="privilegename" itemValue="privilegeId"></form:options>
							</form:select>

							<form:errors path="privilegename" cssClass="error" />
							<div id="privilegeErr" style="color: red;"></div>
						</div>
						<br> <label style="margin-left: 5px;">Access Name <span
							class="font10 text-danger">*</span></label>
						<form:checkboxes items="${accessBO}" itemLabel="accessName"
							itemValue="accessId" path="accessName" />
					</div>

				</div>

				<br />
				<div class="row">
					<div class="col-sm-1" style="float: right;">
						<a href="view-privilege-access"><span
							class="btn btn-t-primary btn-theme lebal_align">Cancel</span></a>
					</div>
					<div class="col-sm-1" style="float: right;">
						<button type="submit" id="btnsubmit"
							class="btn btn-t-primary btn-theme lebal_align">Submit</button>
					</div>
				</div>
			</form:form>

		<%-- 	<h3 style="text-align: center;">View Privilege Access</h3>
			<form:form id="myForm" method="post" class="login-form clearfix"
				action="search-privilege-name" commandName="privilegeSerachObj">
				<div>
					<c:if test="${not empty infomessage }">
						<c:out value="${infomessage }"></c:out>

					</c:if>
				</div>
				<div class="row"
					style="border: 4px solid #e6e6e6; margin: 15px 15px 15px 15px; background-color: #e1e1e1">
					<div class=" col-md-3">
						<div class="form-group home-left">
							<label class="hidden-xs"></label>
							<form:input type="text" class="form-control" path="privilegename" id="privilegenameid"
								placeholder="Privilege Name" escapeXml="false" 
								style="height: 35px;font-weight: 700;"></form:input>
								<form:errors path="privilegename" cssClass="error" />
											<div id="privilegenameErr" style="color: red;"></div>
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
			
			<c:if test="${not empty privilegeBOlist}">

				<div class="col-sm-12" style="margin-top: 20px">
					<p>
						<a class="btn btn-theme btn-xs btn-default"
							style="color: #1b1818; font-weight: bold;"><c:out
								value="${privilegeBOlist}"></c:out></a> <strong class="color-black">privilege
							Found</strong>
					</p>
				</div>

			</c:if>


			<c:if test="${not empty countOfUsers}">

				<div class="col-sm-12" style="margin-top: 20px">
					<p>
						<a class="btn btn-theme btn-xs btn-default"
							style="color: #1b1818; font-weight: bold;"><c:out
								value="${countOfUsers}"></c:out></a> <strong class="color-black">UserRoleSearch
							Found</strong>
					</p>
				</div>

			</c:if>

			<c:if test="${!empty listPrivilegesBO}">
				<div class="row">
					<div class="pi-responsive-table-sm">
						<div class="pi-section-w pi-section-white piTooltips">
							<display:table id="data" name="${listPrivilegesBO}" pagesize="10"
								requestURI="/view-privilege-access" export="false"
								class="pi-table pi-table-complex pi-table-hovered pi-round pi-table-shadow pi-table-all-borders">

								<display:column property="sNo" title="SNo" />
								<display:column property="privilegename" title="Privilege Name" />

								<display:column url="edit-privilege-access" media="html"
									paramId="privilegeId" paramProperty="privilegeId" title="Edit">
									<a href="edit-privilege-access?privilegeId=${data.privilegeId}">
										<i style="text-align: center;" class="fa fa-pencil"></i>
									</a>
								</display:column>

							</display:table>
						</div>
					</div>
				</div>
			</c:if>
			
			<nav style="text-align: center;">
						<ul class="pagination pagination-theme  no-margin center"
							style="margin-left: 575px;">
							<c:if test="${listPrivilegesBO.currentPage gt 1}">
								<li><a href="create-privilege-access?page=1"><span><i
											class="fa fa-angle-double-left" aria-hidden="true"></i> </span></a></li>
								<li><a
									href="create-privilege-access?page=${listPrivilegesBO.currentPage - 1}"><span><i
											class="fa fa-angle-left" aria-hidden="true"></i> </span></a></li>
							</c:if>
							<c:forEach items="${listPrivilegesBO.noOfPages}" var="i">
								<c:choose>
									<c:when test="${listPrivilegesBO.currentPage == i}">

										<li class="active"><a
											style="color: #fff; background-color: #34495e">${i}</a></li>
									</c:when>
									<c:otherwise>
										<li><a href="create-privilege-access?page=${i}">${i}</a></li>
									</c:otherwise>
								</c:choose>
							</c:forEach>
							<c:if test="${listPrivilegesBO.currentPage lt listPrivilegesBO.totalPages}">
								<li><a
									href="create-privilege-accesss?page=${listPrivilegesBO.currentPage + 1}"><span><i
											class="fa fa-angle-right" aria-hidden="true"></i> </span></a></li>
								<li><a
									href="create-privilege-access?page=${listPrivilegesBO.lastRecordValue}"><span><i
											class="fa fa-angle-double-right" aria-hidden="true"></i> </span></a></li>
							</c:if>
						</ul>
						</nav> --%>
		</div>
	</div>
</div>
</div>

