<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>View Campaign</title>
<c:if test="${not empty infomessage}">
	<div class="alert alert-info" role="alert"
		style="font-size: 12px; padding: 8px 9px 5px 10px; margin-top: 15px;">
		<button type="button" class="close" data-dismiss="alert"
			aria-label="Close">
			<span aria-hidden="true">&times;</span>
		</button>

		<c:out value="${infomessage}"></c:out>
	</div>
</c:if>
</head>

<!-- <script>
		$(document).ready(function() {

			$('#submit').click(function(e) {

				var isValid = true;
				var campaignName = $('#campaignNameId').val(); // Assuming the input field ID is 'projectId'

				if (campaignName == '') {
					isValid = false;	
					$("#campaignNameErr").show();
					$("#campaignNameErr").html("Please enter CampaignName");
					$("#campaignNameId").css({
						"border" : "1px solid red",
					});

				} else {
					$('#campaignNameErr').hide();
					$('#campaignNameId').css({
						"border" : "",
						"background" : ""
					});
				}

				if (isValid == false)
					e.preventDefault();
			});
		});
	</script> -->
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
				style="margin-top: -10px;">List Campaign</h3>
			<br>
			<br>
			<sec:authorize
				access="hasAnyRole('ROLE_COMPANY','ROLE_CREATE_CAMPAIGN')">
				<div class="row ">
					<a href="create-campaign" title="Create New Campaign"
						style="font-size: 26px; color: #7cb228; margin-left: 95%;"> <i
						class="fa fa-plus-circle"></i>
					</a>
				</div>
			</sec:authorize>
			

			<!--Start form search -->
			<form:form id="myForm" method="post" class="login-form clearfix"
				action="search-campaign" modelAttribute="searchCampaign"
				commandName="searchCampaign">
				<div class="row"
					style="border: 4px solid #e6e6e6; margin: 15px 15px 15px 15px; background-color: #e1e1e1">
					<div class="col-md-3 fs-mobile-search">
						<div class="form-group">
							<label class="hidden-xs"></label>
							<form:input type="ntext" class="form-control" path="campaignName"
								id="campaignNameId" placeholder="Campaign Name "
								escapeXml="false"
								style="height: 35px;font-weight: 700; margin-top: 6px;"></form:input>
							<form:errors path="campaignName" class="error" />
							<div id="campaignNameErr" style="color: red;"></div>
						</div>
					</div>

					<div class=" col-md-3 fs-mobile-search">
						<div class="form-group ">
							<label class="hidden-xs"></label>
							<form:select type="text" path="productServiceBO.serviceName" id="serviceId"
								class="form-control "
								style="height: 35px;font-weight: 700;
								   text-transform: capitalize; margin-top: 6px;">
								<form:option value="">-- Select Products --   </form:option>
								<form:options items="${productList}" itemLabel="productServiceBO.serviceName"
									itemValue="productServiceBO.serviceId" />
							</form:select>
						</div>
					</div>

					<div class=" col-md-3 fs-mobile-search">
						<div class="form-group ">
							<label class="hidden-xs"></label>
							<form:select class="form-control" path="campaignMode"
								id="campaignMode"
								style="height: 35px;font-weight: 700;text-transform: capitalize; margin-top: 6px;">
								<form:option value="">-- Campaign Mode --   </form:option>
								<form:option value="Sms">SMS   </form:option>
								<form:option value="WhatsApp">WHATSAPP </form:option>
								<form:option value="Email">EMAIL  </form:option>
								<form:option value="Call">CALL  </form:option>
							</form:select>

						</div>
					</div>


					<input type="hidden" value="true" name="search" />

					<div class=" col-md-1" style="padding-bottom: 0px;">
						<div class="form-group home-right">
							<label class="hidden-xs"></label>
							<button class="btn btn-theme btn-success btn-block" id="submit"
								style="padding: 6px 5px; background-color: #7cb228; border-color: #7cb228; margin-top: 8px;">
								<small><i class="fa fa-search" aria-hidden="true"
									style="font-size: 20px;"></i></small>
							</button>
						</div>
					</div>
				</div>
			</form:form>

			<c:if test="${not empty totalcampaignRecordcount}">
				<div class="col-sm-12" style="margin-top: 20px">
					<p>
						<a class="btn btn-theme btn-xs btn-default"
							style="color: #1b1818; font-weight: bold;"><c:out
								value="${totalcampaignRecordcount}"></c:out></a> <strong
							class="color-black">Campaign Found</strong>
					</p>
				</div>

			</c:if>

			<c:if test="${!empty campaignlists}">
				<div class="col-sm-12">
					<!-- <hr style="border: 1px solid #e1e1e1;"> -->
					 <div class="pi-responsive-table-sm">
						<div class="pi-section-w pi-section-white piTooltips"> 
							<display:table id="data" name="${campaignlists.list}"
								requestURI="/view-campaign"  export="false"
								class="pi-table pi-table-complex pi-table-hovered pi-round pi-table-shadow pi-table-all-borders">

								<display:column property="sNo" title="SNo" />
								<display:column property="productServiceBO.serviceName"
									title="Product Name" />
								<display:column property="campaignName" title="Campaign Name" />
								<display:column property="campaignMode" title="Campaign Mode" />
								<sec:authorize
									access="hasAnyRole('ROLE_COMPANY','ROLE_CAMPAIGN_MANAGER','ROLE_CAMPAIGN_MEMBER','ROLE_SALES_MANAGER','ROLE_SALES_EXECUTE','ROLE_UPDATE_CAMPAGAIN')">
									<display:column url="edit-user" media="html" paramId="id"
										paramProperty="id" title="Edit">
										<a href="edit-campaign?campaignid=${data.campaignId}"><i
											style="text-align: center;" class="fa fa-pencil"></i></a>
									</display:column>
								</sec:authorize>
								<sec:authorize
									access="hasAnyRole('ROLE_COMPANY','ROLE_CAMPAIGN_MANAGER','ROLE_SALES_MANAGER','ROLE_DELETE_CAMPAGAIN')">
									<display:column url="delete-user" media="html" paramId="id"
										paramProperty="id" title="Delete">
										<a href="delete-campaign?campaignid=${data.campaignId}"
											onclick="return confirm('Are you sure you want to Delete?')"><i
											style="text-align: center;" class="fa fa-trash"></i></a>
									</display:column>
								</sec:authorize>

								<sec:authorize
									access="hasAnyRole('ROLE_COMPANY','ROLE_CAMPAIGN_MANAGER','ROLE_SALES_MANAGER','ROLE_VIEW_CAMPAGAIN')">
									<display:column url="view-user" media="html" paramId="id"
										paramProperty="id" title="View">
										<a
											href="campaign-tracking-status?campaignid=${data.campaignId}">
											<i style="text-align: center; color: black;"
											class="fa fa-eye"></i>
										</a>
									</display:column>
								</sec:authorize>

							</display:table>
						</div>
					</div>
				</div>
			</c:if>


			<c:if test="${!empty campaignlistsProducts}">
				<div class="col-sm-12">
					<!-- <hr style="border: 1px solid #e1e1e1;"> -->
					<div class="pi-responsive-table-sm">
						<div class="pi-section-w pi-section-white piTooltips">
							<display:table id="data" name="${campaignlistsProducts}"
								requestURI="/view-campaign" pagesize="10" export="false"
								class="pi-table pi-table-complex pi-table-hovered pi-round pi-table-shadow pi-table-all-borders">

								<display:column property="sNo" title="SNo" />
								<display:column property="productServiceBO.serviceName"
									title="Product Name" />
								<display:column property="campaignName" title="Campaign Name" />
								<display:column property="campaignMode" title="Campaign Mode" />

								<display:column url="view-user" media="html" paramId="id"
									paramProperty="id" title="View">
									<a
										href="campaign-tracking-status?campaignid=${data.campaignId}">
										<i style="text-align: center; color: black;"
										class="fa fa-search"></i>
									</a>
								</display:column>

							</display:table>

							<br>
							<div class="navigation" style="padding-left: 1200px;">

								<!-- <a onclick="history.go(-1)"> -->
								<a href="product-tracking-status?serviceId=${serviceId}"><span
									class="btn btn-t-primary btn-theme lebal_align mt-20">Back</span></a>
							</div>
						</div>
					</div>
				</div>
			</c:if>
			<nav style="text-align: center;">
				<ul class="pagination pagination-theme  no-margin center"
					style="margin-left: 575px;">
					<c:if test="${campaignlists.currentPage gt 1}">
						<li><a href="view-campaign?page=1&&search=${searchCampaign}"><span><i
									class="fa fa-angle-double-left" aria-hidden="true"></i> </span></a></li>
						<li><a
							href="view-campaign?page=${campaignlists.currentPage - 1}&&search=${searchCampaign}"><span><i
									class="fa fa-angle-left" aria-hidden="true"></i> </span></a></li>
					</c:if>
					<c:forEach items="${campaignlists.noOfPages}" var="i">
						<c:choose>
							<c:when test="${campaignlists.currentPage == i}">

								<li class="active"><a
									style="color: #fff; background-color: #34495e">${i}</a></li>
							</c:when>
							<c:otherwise>
								<li><a href="view-campaign?page=${i}">${i}</a></li>
							</c:otherwise>
						</c:choose>
					</c:forEach>
					<c:if
						test="${campaignlists.currentPage lt campaignlists.totalPages}">
						<li><a
							href="view-campaign?page=${campaignlists.currentPage + 1}"><span><i
									class="fa fa-angle-right" aria-hidden="true"></i> </span></a></li>
						<li><a
							href="view-campaign?page=${campaignlists.lastRecordValue}"><span><i
									class="fa fa-angle-double-right" aria-hidden="true"></i> </span></a></li>
					</c:if>
				</ul>
			</nav>
		</div>
	</div>
	<br />
</div>
</html>