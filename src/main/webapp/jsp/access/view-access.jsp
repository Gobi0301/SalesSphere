<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
	
<link href="resources/theme/css/custom.css" rel="stylesheet">
<link href="resources/css/jquery-ui-1.10.4.custom.css" rel="stylesheet">
<script type="text/javascript" src="resources/js/jquery-ui.min.js"></script>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
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

			$('#submit').click(function(e) {

				var isValid = true;
				var accessName = $('#accessId').val(); // Assuming the input field ID is 'accessId'

				if (accessName == '') {
					isValid = false;
					$("#accessNameErr").show();
					$("#accessNameErr").html("Please enter accessName");
					$("#accessId").css({
						"border" : "1px solid red",
					});

				} else {
					$('#accessNameErr').hide();
					$('#accessId').css({
						"border" : "",
						"background" : ""
					});
				}

				if (isValid == false)
					e.preventDefault();
			});
		});
	</script> 

<div class="warning">
<c:if test="${not empty successMessage}">
				<div class="alert alert-success"> 	
					<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
					<strong>Success:</strong>
					<c:out value="${successMessage}"></c:out>
				</div>
			</c:if>
			<c:if test="${not empty errorMessage}">
				<div class="alert alert-info">
					<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
					<strong>Info:</strong>
					<c:out value="${errorMessage}"></c:out>
				</div>
			</c:if>
		</div>

<div class="box-list">
    <div class="item">
        <div class="row ">
            <h3 class="text-center no-margin titleunderline underline"
                style="margin-top: -10px;">View Access</h3>
            <br>
            
            <div class="row ">
					<a href="create-access"
						style="font-size: 26px; color: #7cb228; margin-left: 95%;"> <i
						class="fa fa-plus-circle" title="Create New Access"></i>
					</a>
				</div>

            <form:form id="myForm" method="post" class="login-form clearfix"
                action="search-access" modelAttribute="viewAccess">
                <%-- <div>
                    <c:if test="${not empty infomessage }">
                        <c:out value="${infomessage }"></c:out>
                    </c:if>
                </div> --%>
                <div class="row"
                    style="border: 4px solid #e6e6e6; margin: 15px 15px 15px 15px; background-color: #e1e1e1">
                    <div class=" col-md-3">
                        <div class="form-group home-left">
                            <label class="hidden-xs"></label>
                            <form:input type="ntext" class="form-control" path="accessName"
                                placeholder="Access Name " escapeXml="false"
                                style="height: 35px;font-weight: 700;"></form:input>
                             <div id="accessError" style="color: red; display: none;"></div>
                        </div>
                    </div>

                    <div class=" col-md-1" style="padding-bottom: 0px;">
                        <div class="form-group home-right">
                            <label class="hidden-xs"></label>
                            <button class="btn btn-theme btn-success btn-block"
                                style="padding: 6px 5px; background-color: #7cb228; border-color: #7cb228;">
                                <small><i class="fa fa-search" aria-hidden="true"
                                    style="font-size: 20px;"></i></small>
                            </button>
                        </div>
                    </div>
                </div>
            </form:form>
            
            <c:if test="${not empty totalCountOfAccess}">
				<div class="col-sm-12" style="margin-top: 20px">
					<p>
						<a class="btn btn-theme btn-xs btn-default"
							style="color: #1b1818; font-weight: bold;"><c:out
								value="${totalCountOfAccess}"></c:out></a> <strong
							class="color-black">Access Found</strong>
					</p>
				</div>
			</c:if>
			
			<c:if test="${not empty totalSearchCount}">
				<div class="col-sm-12" style="margin-top: 20px">
					<p>
						<a class="btn btn-theme btn-xs btn-default"
							style="color: #1b1818; font-weight: bold;"><c:out
								value="${totalSearchCount}"></c:out></a> <strong
							class="color-black">Access Found</strong>
					</p>
				</div>
			</c:if>
            
		<c:if test="${!empty accessBOList}">
           <!--  <div class="col-sm-12" style="margin-top: -27px"> -->
           
           <div class="row">
                <div class="pi-responsive-table-sm">
                    <div class="pi-section-w pi-section-white piTooltips">
                        <display:table id="data" name="${accessBOList.list}"
                            requestURI="/view-access" export="false"
                            class="pi-table pi-table-complex pi-table-hovered pi-round pi-table-shadow pi-table-all-borders">
							
							<display:column property="sNo" title="SNo" />
							<%-- <display:column property="accessName" title="Access Name"/> --%>
							
                             <display:column property="accessName" title="Access Name">
                                <c:choose>
                                    <c:when test="${empty data.accessName}">
                                        NA
                                    </c:when>
                                    <c:otherwise>
                                        <c:out value="${data.accessName}"/>
                                    </c:otherwise>
                                </c:choose>
                            </display:column> 

                            <display:column url="edit-access" media="html" paramId="accessId"
                                paramProperty="accessId" title="Edit">
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
            
              <nav style="text-align: center;">
					<ul class="pagination pagination-theme  no-margin center"
						style="margin-left: 575px;">
						<c:if test="${accessBOList.currentPage gt 1}">
							<li><a href="view-access?page=1&searchAccessName=${searchAccessName}"><span><i
										class="fa fa-angle-double-left" aria-hidden="true"></i> </span></a></li>
							<li><a href="view-access?page=${accessBOList.currentPage - 1}&searchAccessName=${searchAccessName}"><span><i
										class="fa fa-angle-left" aria-hidden="true"></i> </span></a></li>
						</c:if>
						<c:forEach items="${accessBOList.noOfPages}" var="i">
							<c:choose>
								<c:when test="${accessBOList.currentPage == i}">

									<li class="active"><a
										style="color: #fff; background-color: #34495e">${i}</a></li>
								</c:when>
								<c:otherwise>
									<li><a href="view-access?page=${i}&searchAccessName=${searchAccessName}">${i}</a></li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
						<c:if test="${accessBOList.currentPage lt accessBOList.totalPages}">
							<li><a href="view-access?page=${accessBOList.currentPage + 1}&searchAccessName=${searchAccessName}"><span><i
										class="fa fa-angle-right" aria-hidden="true"></i> </span></a></li>
							<li><a href="view-access?page=${accessBOList.lastRecordValue}&searchAccessName=${searchAccessName}"><span><i
										class="fa fa-angle-double-right" aria-hidden="true"></i> </span></a></li>
						</c:if>
					</ul>
				</nav>   
				
            
            </c:if>
        </div>
    </div>
    <br />
</div>
</html>
