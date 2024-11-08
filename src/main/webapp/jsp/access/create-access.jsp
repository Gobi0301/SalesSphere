<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
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

			var name = $('#accessNameInput').val();
			if (name == '') {
				isValid = false;
				$("#accessNameErr").show();
				$("#accessNameErr").html("Please Enter Access Name ");
				$("#accessNameInput").css({
					"border" : "1px solid red",

				});

			} else if (!/^[a-zA-Z0 -9._/&]*$/.test(name)) {
				isValid = false;
				$("#accessNameErr").show();
				$("#accessNameErr").html("Please enter characters only");
				$("#accessNameInput").css({
					"border" : "1px solid red",

				});

			} else {
				$('#accessNameErr').hide();
				$('#accessNameInput').css({

					"border" : "",
					"background" : ""
				});
			}
			
			// Search validation..
			

			if (isValid == false)
				e.preventDefault();

		});
	});
</script>

<script>
    $(document).ready(function () {

        $('#submit').click(function (e) {

        	 var isValid = true;
	            var accessName = $('#accessId').val(); // Assuming the input field ID is 'projectId'

	            if (accessName == '') {
	                isValid = false;
	                $("#accessErr").show();
	                $("#accessErr").html("Please enter Access Name");
	                $("#accessId").css({
	                    "border": "1px solid red",
	                });

	            } else {
	                $('#accessErr').hide();
	                $('#accessId').css({
	                    "border": "",
	                    "background": ""
	                });
	            }

            if (isValid == false)
                e.preventDefault();
        });
    });
</script>	


<script type="text/javascript">
	function accessNameCheck() {
		var accessName = document.getElementById("accessNameInput").value;
		document.getElementById("btnsubmit").disabled = false;
		if (accessName != '') {
			$.ajax({
				url : "check_accessName",
				type : "GET",
				data : 'accessName=' + accessName,
				success : function(result) {

					if (result == true) {
						$("#accessNameErr").html("Access Name Already Exists");
						document.getElementById("btnsubmit").disabled = true;
						$("#accessNameErr").show();
					} else {
						$("#accessNameErr").hide();
					}
				}
			});
		}
	};
</script>

<body>
	<div class="contact-form-wrapper">
		<div class="box-list">
			<div class="item">
				<div class="row">
					<div class="text-center underline">
						<h3
							style="text-decoration: underline; margin-top: 25px; border-bottom: 20px;">Create
							Access</h3>
					</div>
					<form:form method="POST" id="add-form" action="create-access"
						modelAttribute="accessBo">
						<div>
							<c:if test="${not empty infomessage }">
								<c:out value="${infomessage }"></c:out>
							</c:if>
						</div>
						<div class="row">
							<div class="col-sm-4">
								<div class="form-group">
									<label>Access Name<span class="font10 text-danger">*</span></label>
									<form:input id="accessNameInput" type="text" path="accessName"
										class="form-control required" placeholder="Access Name"
										maxlength="150" onchange="accessNameCheck()" />
									<form:errors path="accessName" cssClass="error" />
									<div id="accessNameErr" style="color: red;"></div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-1">
								<button type="submit" id="btnsubmit"
									class="btn btn-t-primary btn-theme lebal_align">Submit</button>
							</div>
							<div class="col-sm-1">
								<a href="view-access"><span
									class="btn btn-t-primary btn-theme lebal_align">Cancel</span></a>
							</div>
						</div>
				</form:form>
					<!-- <h3 style="text-decoration: underline; margin-top: 25px; border-bottom: 20px; text-align: center;">View Access</h3>
 --%> -->

<%-- <div class="warning">


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

			</div>
		</c:if>
	</div> --%>
	

                     
<%-- 

					<form:form id="myForm" method="post" class="login-form clearfix"
						action="search-access" commandName="viewAccess"
						modelAttribute="viewAccess">
						<div>
							<c:if test="${not empty infomessage }">
								<c:out value="${infomessage }"></c:out>

							</c:if>
						</div>
						<div class="row"
							style="border: 4px solid #e6e6e6; margin: 15px 15px 15px 15px; background-color: #e1e1e1">

							<div class=" col-md-4" style="margin-top: 8px">

								<div class="form-group home-left">
									<label class="hidden-xs"></label>
									<form:input type="ntext" class="form-control" path="accessName"
										placeholder="Access Name " id="accessId" escapeXml="false"
										style="height: 35px;font-weight: 700;"></form:input>
									<form:errors path="accessName" class="error" />
									<div id="accessErr" style="color: red;"></div>
								</div>
							</div>
							<div class=" col-md-1" style="padding-bottom: 0px;">
								<div class="form-group home-right" style="margin-top: 8px">
									<label class="hidden-xs"></label>
									<button class="btn btn-theme btn-success btn-block"
										style="padding: 6px 5px; background-color: #7cb228; border-color: #7cb228;">
										<small><i class="fa fa-search" aria-hidden="true"
											id="submit" style="font-size: 20px;"></i></small>
									</button>
								</div>
							</div>
						</div>
					</form:form> --%>
					
				<%-- 	<c:if test="${ empty infomessage}">
					   <c:if test="${not empty totalsearchcount}">
                        <div class="col-sm-12" style="margin-top: 20px">
                            <p><a class="btn btn-theme btn-xs btn-default"
                                    style="color: #1b1818; font-weight: bold;"><c:out
                                        value="${totalsearchcount}"></c:out></a> <strong
                                    class="color-black">Access Found</strong>
                            </p>
                        </div>
                    </c:if>
					 <c:if test="${not empty totalaccesscount}">
                        <div class="col-sm-12" style="margin-top: 20px">
                            <p><a class="btn btn-theme btn-xs btn-default"
                                    style="color: #1b1818; font-weight: bold;"><c:out
                                        value="${totalaccesscount}"></c:out></a> <strong
                                    class="color-black">Access Found</strong>
                            </p>
                        </div>
                    </c:if>
					
					<br>
					<c:if test="${!empty accessList.list}">
					<div class="col-sm-12" style="margin-top: -27px">
						<div class="pi-responsive-table-sm">
							<div class="pi-section-w pi-section-white piTooltips">
							
								<display:table id="data" name="${accessList.list}"
									requestURI="/view-access" pagesize="10" export="false"
									class="pi-table pi-table-complex pi-table-hovered pi-round pi-table-shadow pi-table-all-borders">
									<display:column property="sNo" title="SNo" />
									<display:column property="accessName" title="Access Name" />
									<display:column url="edit-access" media="html"
										paramId="accessId" paramProperty="accessId" title="Edit">
										<a href="edit-access?accessId=${data.accessId}"><i
											style="text-align: center;" class="fa fa-pencil"></i></a>
									</display:column>
									<display:column url="delete-access" media="html"
										paramId="accessId" paramProperty="accessId" title="Delete">
										<a href="delete-access?accessId=${data.accessId}"
											onclick="return confirm('Are you sure you want to Delete?')"><i
											style="text-align: center;" class="fa fa-trash"></i></a>
									</display:column>

								</display:table>
							</div>
						</div>
					</div>
					</c:if>
					
					 <nav style="text-align: center;">
					<ul class="pagination pagination-theme  no-margin center"
						style="margin-left: 575px;">
						<c:if test="${accessList.currentPage gt 1}">
							<li><a href="view-access?page=1&searchElement=${searchElement}"><span><i
										class="fa fa-angle-double-left" aria-hidden="true"></i> </span></a></li>
							<li><a
								href="view-access?page=${accessList.currentPage - 1}&searchElement=${searchElement}"><span><i
										class="fa fa-angle-left" aria-hidden="true"></i> </span></a></li>
						</c:if>
						<c:forEach items="${accessList.noOfPages}" var="i">
							<c:choose>
								<c:when test="${accessList.currentPage == i}">

									<li class="active"><a
										style="color: #fff; background-color: #34495e">${i}</a></li>
								</c:when>
								<c:otherwise>
									<li><a href="view-access?page=${i}&searchElement=${searchElement}">${i}</a></li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
						<c:if test="${accessList.currentPage lt accessList.totalPages}">
							<li><a
								href="view-access?page=${accessList.currentPage + 1}&searchElement=${searchElement}"><span><i
										class="fa fa-angle-right" aria-hidden="true"></i> </span></a></li>
							<li><a
								href="view-access?page=${accessList.lastRecordValue}&searchElement=${searchElement}"><span><i
										class="fa fa-angle-double-right" aria-hidden="true"></i> </span></a></li>
						</c:if>
					</ul>
				</nav>   <!-- hidden for pagination -->
</c:if> --%>
				</div>
			</div>
		</div>
	</div>
</body>
</html>