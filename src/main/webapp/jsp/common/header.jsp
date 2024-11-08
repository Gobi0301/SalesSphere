<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<div class="wrapper">
	<header class="main-header">
		<!-- main navbar -->
		<nav
			class="navbar navbar-default navbar navbar-fixed-top main-navbar hidden-sm hidden-xs">
			<div class="container">
				<div class="collapse navbar-collapse">
					<ul class="nav navbar-nav nav-bar-width">
        <sec:authorize access="isAuthenticated()">
            
            <li class="header-tab" style="width: 95px; color: #fff; font-size: 12px; font-weight: 600; margin: 8px 0 10px 10px;">
                <a href="<c:url value='/admin-home' />">
                    <span>
                        <i class="fa fa-bar-chart" aria-hidden="true"></i>
                        <span style="color: #fff; font-size: 14px; font-weight: 600;">MySales</span>
                    </span>
                </a>
            </li>
        </sec:authorize>
        <sec:authorize access="!isAuthenticated()">
           
            <li class="header-tab" style="width: 95px; color: #fff; font-size: 12px; font-weight: 600; margin: 8px 0 10px 10px;">
                <a href="<c:url value='/admin-sign-in' />">
                    <span>
                        <i class="fa fa-bar-chart" aria-hidden="true"></i>
                        <span style="color: #fff; font-size: 14px; font-weight: 600;">MySales</span>
                    </span>
                </a>
            </li>
        </sec:authorize>
    </ul>


					<sec:authorize access="isAuthenticated()">
						<ul class="nav navbar-nav" style="float: right;">


							<sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_COMPANY')">
								<li class="dropdown" id="fdJobId"><a href="#"
									class="pd-tp-27" style="font-size: 12px; font-weight: 600;"><span
										class="dropbtn">Users<i class="fa fa-caret-down"
											aria-hidden="true" style="font-size: 15px; margin-left: 7px;"></i></span></a>
									<div class="dropdown-content dropdown-menu" role="menu"
										style="list-style-type: none;">
										<sec:authorize access="hasAnyRole('ROLE_ADMIN')">
										<a href="view-role" style="height: 15px;"><i
											class="fa fa-play" aria-hidden="true" style="font-size: 8px;"></i> Manage Role</a>
											
										 <a href="view-privileges" style="height: 15px;"><i	
										 class="fa fa-play" aria-hidden="true" style="font-size: 8px;"></i> Manage Privileges</a> </a> 
										 
										 <a href="role-privileges" style="height: 15px;"><i class="fa fa-play"
											aria-hidden="true" style="font-size: 8px;"></i> Manage Role Privileges</a> 
											
											<a href="view-access" style="height: 15px;"><i
											class="fa fa-play" aria-hidden="true" style="font-size: 8px;"></i> Manage Access</a>  
											
											<a href="view-privilege-access" style="height: 15px;"><i 
											class="fa fa-play" aria-hidden="true" style="font-size: 8px;"></i> Privileges Access</a>
											</sec:authorize>
										<sec:authorize
											access="hasAnyRole('ROLE_ADMIN','ROLE_POC','ROLE_COMPANY')">
											<sec:authorize access="hasAnyRole('ROLE_COMPANY')">
												<a href="create-employees" style="height: 15px;"><i
													class="fa fa-play" aria-hidden="true"
													style="font-size: 8px;"></i> Create Employees</a>
											</sec:authorize>
											<a href="view-employees" style="height: 15px;"><i
												class="fa fa-play" aria-hidden="true"
												style="font-size: 8px;"></i> List Employees</a>
											<a href="create-user-roles" style="height: 15px;"><i
												class="fa fa-play" aria-hidden="true"
												style="font-size: 8px;"></i> Manage User & Role</a>
										</sec:authorize>
									</div></li>

								<li class="dropdown" id="fdJobId"><a href="#"
									class="pd-tp-27" style="font-size: 12px; font-weight: 600;"><span
										class="dropbtn">Company<i class="fa fa-caret-down"
											aria-hidden="true" style="font-size: 15px; margin-left: 3px;"></i></span></a>
									<div class="dropdown-content dropdown-menu" role="menu"
										style="list-style-type: none;">
										<sec:authorize access="hasAnyRole('ROLE_ADMIN')">
											<a href="create-company" style="height: 15px;"><i
												class="fa fa-play" aria-hidden="true"
												style="font-size: 8px;"></i> Create Company</a>
										</sec:authorize>
										<a href="view-company" style="height: 15px;"><i
											class="fa fa-play" aria-hidden="true" style="font-size: 8px;"></i>
											List Company</a>
										<!--  <a href="create-branch" style="height: 15px;"><i
											class="fa fa-play" aria-hidden="true" style="font-size: 8px;"></i>
											Create Branch</a> -->
									</div></li>
							</sec:authorize>


							<sec:authorize access="hasAnyRole('ROLE_VIEW_PRODUCTTYPE','ROLE_VIEW_PROCUREMENT','ROLE_CREATE_PROCUREMENT','ROLE_MANAGE_PRODUCT','ROLE_CREATE_PRODUCT','ROLE_VIEW_PRODUCT','ROLE_CREATE_PRODUCTTYPE','ROLE_ADMIN','ROLE_COMPANY','ROLE_VIEW_SUPPLIERS','ROLE_CREATE_SUPPLIERS','ROLE_VIEW_PRICEBOOK','ROLE_CREATE_PRICEBOOK','ROLE_CREATE_GST','ROLE_VIEW_GST')">


								<li class="dropdown" id="fdJobId"><a href="#"
									class="pd-tp-27" style="font-size: 12px; font-weight: 600;"><span
										class="dropbtn">Inventory<i class="fa fa-caret-down"
											aria-hidden="true" style="font-size: 15px; margin-left: 3px;"></i></span></a>
									<div class="dropdown-content dropdown-menu" role="menu"
										style="list-style-type: none;">
										
										<sec:authorize access="hasAnyRole('ROLE_COMPANY','ROLE_CREATE_PRODUCTTYPE')">
											<a href="create_producttypes" style="height: 15px;"><i
												class="fa fa-play" aria-hidden="true"
												style="font-size: 8px;"></i> Create productTypes</a>
										</sec:authorize>

										<sec:authorize access="hasAnyRole('ROLE_COMPANY','ROLE_VIEW_PRODUCTTYPE','ROLE_ADMIN')">
											<a href="view_producttypes" style="height: 15px;"><i
												class="fa fa-play" aria-hidden="true"
												style="font-size: 8px;"></i> List productTypes</a>
										</sec:authorize>

										<sec:authorize access="hasAnyRole('ROLE_COMPANY','ROLE_MANAGE_PRODUCT')">
											<a href="create-productservice" style="height: 15px;"><i
												class="fa fa-play" aria-hidden="true"
												style="font-size: 8px;"></i> Create Product</a>
										</sec:authorize>

										<sec:authorize access="hasAnyRole('ROLE_COMPANY','ROLE_VIEW_PRODUCT','ROLE_ADMIN')">
											<a href="view-productservice" style="height: 15px;"><i
												class="fa fa-play" aria-hidden="true"
												style="font-size: 8px;"></i> List Product</a>
										</sec:authorize>

										<sec:authorize access="hasAnyRole('ROLE_COMPANY','ROLE_CREATE_GST')">
											<a href="create-gst" style="height: 15px;"><i
												class="fa fa-play" aria-hidden="true"
												style="font-size: 8px;"></i> Create GST</a>
										</sec:authorize>

										<sec:authorize access="hasAnyRole('ROLE_COMPANY','ROLE_VIEW_GST','ROLE_ADMIN')">
											<a href="view-gst" style="height: 15px;"><i
												class="fa fa-play" aria-hidden="true"
												style="font-size: 8px;"></i> List GST</a>
										</sec:authorize>

										<sec:authorize access="hasAnyRole('ROLE_COMPANY','ROLE_CREATE_SUPPLIERS')">
											<a href="create-supplier" style="height: 15px;"><i
												class="fa fa-play" aria-hidden="true"
												style="font-size: 8px;"></i> Create Suppliers</a>
										</sec:authorize>

										<sec:authorize access="hasAnyRole('ROLE_COMPANY','ROLE_VIEW_SUPPLIERS','ROLE_ADMIN')">
											<a href="view_supplier" style="height: 15px;"><i
												class="fa fa-play" aria-hidden="true"
												style="font-size: 8px;"></i> List Suppliers</a>
										</sec:authorize>

										<sec:authorize access="hasAnyRole('ROLE_COMPANY','ROLE_CREATE_PROCUREMENT')">
											<a href="create-procurement" style="height: 15px;"><i
												class="fa fa-play" aria-hidden="true"
												style="font-size: 8px;"></i> Create procurement</a>
										</sec:authorize>

										<sec:authorize access="hasAnyRole('ROLE_COMPANY','ROLE_VIEW_PROCUREMENT','ROLE_ADMIN')">
											<a href="view-procurement" style="height: 15px;"><i
												class="fa fa-play" aria-hidden="true"
												style="font-size: 8px;"></i> List procurement</a>
										</sec:authorize>

										<sec:authorize access="hasAnyRole('ROLE_COMPANY','ROLE_CREATE_PRICEBOOK')">
											<a href="create-pricebook" style="height: 15px;"><i
												class="fa fa-play" aria-hidden="true"
												style="font-size: 8px;"></i> Create PriceBook</a>
										</sec:authorize>

										<sec:authorize access="hasAnyRole('ROLE_COMPANY','ROLE_VIEW_PRICEBOOK','ROLE_ADMIN')">
											<a href="view-pricebook" style="height: 15px;"><i
												class="fa fa-play" aria-hidden="true"
												style="font-size: 8px;"></i> List PriceBook</a>
										</sec:authorize>
									</div></li>
							</sec:authorize>

							<sec:authorize
								access="hasAnyRole('ROLE_ADMIN','ROLE_COMPANY','ROLE_CREATE_PROJECT','ROLE_VIEW_PROJECT','ROLE_CREATE_PLOT','ROLE_VIEW_PLOT')">
								<li class="dropdown" id="fdJobId"><a href="#"
									class="pd-tp-27" style="font-size: 12px; font-weight: 600;">
										<span class="dropbtn">Projects<i
											class="fa fa-caret-down" aria-hidden="true"
											style="font-size: 15px; margin-left: 3px;"></i></span>
								</a>

									<div class="dropdown-content dropdown-menu" role="menu"
										style="list-style-type: none;">
										<sec:authorize
											access="hasAnyRole('ROLE_COMPANY','ROLE_CREATE_PROJECT')">
											<a href="create-project" style="height: 15px;"><i
												class="fa fa-play" aria-hidden="true"
												style="font-size: 8px;"></i> Create Project</a>
										</sec:authorize>

										<sec:authorize
											access="hasAnyRole('ROLE_ADMIN','ROLE_COMPANY','ROLE_VIEW_PROJECT')">
											<a href="view-project" style="height: 15px;"><i
												class="fa fa-play" aria-hidden="true"
												style="font-size: 8px;"></i> List Projects</a>
										</sec:authorize>

										<sec:authorize
											access="hasAnyRole('ROLE_COMPANY','ROLE_CREATE_PLOT')">
											<a href="create-plot" style="height: 15px;"><i
												class="fa fa-play" aria-hidden="true"
												style="font-size: 8px;"></i> Create Plot</a>
										</sec:authorize>

										<sec:authorize
											access="hasAnyRole('ROLE_ADMIN','ROLE_COMPANY','ROLE_VIEW_PLOT')">
											<a href="view-plot" style="height: 15px;"><i
												class="fa fa-play" aria-hidden="true"
												style="font-size: 8px;"></i> List Plot</a>
										</sec:authorize>
									</div></li>
							</sec:authorize>

							<%-- <sec:authorize access="hasAnyRole('ROLE_ADMIN')">
									<!-- <li class="dropdown" id="fdJobId"><a href="#"
										class="pd-tp-27" style="font-size: 12px; font-weight: 600;"><span
											class="dropbtn">Tasks<i class="fa fa-caret-down"
												aria-hidden="true"
												style="font-size: 15px; margin-left: 3px;"></i></span></a>

										<div class="dropdown-content dropdown-menu" role="menu"
											style="list-style-type: none;"></div></li> -->

									<li class="dropdown" id="fdJobId"><a href="#"
										class="pd-tp-27" style="font-size: 12px; font-weight: 600;"><span
											class="dropbtn">SLA<i class="fa fa-caret-down"
												aria-hidden="true"
												style="font-size: 15px; margin-left: 3px;"></i></span></a>

										<div class="dropdown-content dropdown-menu" role="menu"
											style="list-style-type: none;">
											
										</div></li>
									<li class="dropdown" id="fdJobId"><a href="#"
										class="pd-tp-27" style="font-size: 12px; font-weight: 600;"><span
											class="dropbtn">Skills<i class="fa fa-caret-down"
												aria-hidden="true"
												style="font-size: 15px; margin-left: 3px;"></i></span></a>

										<div class="dropdown-content dropdown-menu" role="menu"
											style="list-style-type: none;">
											
										</div></li>

								</sec:authorize> --%>

							<sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_CREATE_WI&SLA','ROLE_CREATE_TASK','ROLE_VIEW_TASK','ROLE_VIEW_WI&SLA','ROLE_CREATE_SKILLS','ROLE_VIEW_SKILLS','ROLE_COMPANY','ROLE_CREATE_WORKITEM','ROLE_VIEW_WORKITEM','ROLE_VIEW_SLA','ROLE_CREATE_SLA','ROLE_CREATE_WI&SLA','ROLE_VIEW_WI&SLA')">
								<li class="dropdown" id="fdJobId"><a href="#"
									class="pd-tp-27" style="font-size: 12px; font-weight: 600;">
										<span class="dropbtn">Work Item<i
											class="fa fa-caret-down" aria-hidden="true"
											style="font-size: 15px; margin-left: 3px;"></i></span>
								</a>

									<div class="dropdown-content dropdown-menu" role="menu"
										style="list-style-type: none;">
										<sec:authorize access="hasAnyRole('ROLE_COMPANY','ROLE_CREATE_WORKITEM')">
											<a href="create-workitem" style="height: 15px;"><i
												class="fa fa-play" aria-hidden="true"
												style="font-size: 8px;"></i> Create WorkItem</a>
										</sec:authorize>

										<sec:authorize
											access="hasAnyRole('ROLE_ADMIN','ROLE_COMPANY','ROLE_VIEW_WORKITEM')">
											<a href="list-workitems" style="height: 15px;"><i
												class="fa fa-play" aria-hidden="true"
												style="font-size: 8px;"></i> List WorkItems</a>
										</sec:authorize>

										<sec:authorize access="hasAnyRole('ROLE_COMPANY','ROLE_CREATE_SLA')">
											<a href="create-sla" style="height: 15px;"><i
												class="fa fa-play" aria-hidden="true"
												style="font-size: 8px;"></i> Create SLA</a>
										</sec:authorize>

										<sec:authorize
											access="hasAnyRole('ROLE_ADMIN','ROLE_COMPANY','ROLE_VIEW_SLA')">
											<a href="view-sla" style="height: 15px;"><i
												class="fa fa-play" aria-hidden="true"
												style="font-size: 8px;"></i> List SLA</a>
										</sec:authorize>

										<sec:authorize access="hasAnyRole('ROLE_COMPANY','ROLE_CREATE_WI&SLA')">
											<a href="create-manage_WI_SLA" style="height: 15px;"><i
												class="fa fa-play" aria-hidden="true"
												style="font-size: 8px;"></i> Create WI & SLA</a>
										</sec:authorize>

										<sec:authorize
											access="hasAnyRole('ROLE_ADMIN','ROLE_COMPANY','ROLE_VIEW_WI&SLA')">
											<a href="view-manage_WI_SLA" style="height: 15px;"><i
												class="fa fa-play" aria-hidden="true"
												style="font-size: 8px;"></i> List WI & SLA</a>
										</sec:authorize>

										<sec:authorize access="hasAnyRole('ROLE_COMPANY','ROLE_CREATE_SKILLS')">
											<a href="create-skills" style="height: 15px;"><i
												class="fa fa-play" aria-hidden="true"
												style="font-size: 8px;"></i> Create Skills</a>
										</sec:authorize>

										<sec:authorize
											access="hasAnyRole('ROLE_ADMIN','ROLE_COMPANY','ROLE_VIEW_SKILLS')">
											<a href="view-skills" style="height: 15px;"><i
												class="fa fa-play" aria-hidden="true"
												style="font-size: 8px;"></i> List Skills</a>
										</sec:authorize>

										<sec:authorize access="hasAnyRole('ROLE_COMPANY','ROLE_CREATE_WI&SLA')">
											<a href="create-manage_WI_Skills" style="height: 15px;"><i
												class="fa fa-play" aria-hidden="true"
												style="font-size: 8px;"></i> Create WI & Skills</a>
										</sec:authorize>

										<sec:authorize
											access="hasAnyRole('ROLE_ADMIN','ROLE_COMPANY','ROLE_CREATE_WI&SLA')">
											<a href="view-manage_WI_Skills" style="height: 15px;"><i
												class="fa fa-play" aria-hidden="true"
												style="font-size: 8px;"></i> List WI & Skills</a>
										</sec:authorize>

										<sec:authorize access="hasAnyRole('ROLE_COMPANY','ROLE_CREATE_TASK')">
											<a href="create-task" style="height: 15px;"><i
												class="fa fa-play" aria-hidden="true"
												style="font-size: 8px;"></i> Create Task</a>
										</sec:authorize>

										<sec:authorize
											access="hasAnyRole('ROLE_ADMIN','ROLE_COMPANY','ROLE_VIEW_TASK')">
											<a href="view-task" style="height: 15px;"><i
												class="fa fa-play" aria-hidden="true"
												style="font-size: 8px;"></i> List Task</a>
										</sec:authorize>
									</div></li>
							</sec:authorize>

							<sec:authorize
								access="hasAnyRole('ROLE_ADMIN','ROLE_COMPANY','ROLE_ACCOUNT_MANAGER','ROLE_ACCOUNT_TEAM','ROLE_CREATE_CASE','ROLE_VIEW_CASE','ROLE_VIEW_CUSTOMER','ROLE_CREATE_CUSTOMER')">
								<li class="dropdown" id="fdJobId"><a href="#"
									class="pd-tp-27" style="font-size: 12px; font-weight: 600;">
										<span class="dropbtn">Cases <i class="fa fa-caret-down"
											aria-hidden="true" style="font-size: 15px; margin-left: 3px;"></i></span>
								</a>
									<div class="dropdown-content dropdown-menu" role="menu"
										style="list-style-type: none;">
										<sec:authorize
											access="hasAnyRole('ROLE_COMPANY','ROLE_ACCOUNT_MANAGER','ROLE_CREATE_CASE')">
											<a href="create-casemanagement" style="height: 15px;"><i
												class="fa fa-play" aria-hidden="true"
												style="font-size: 8px;"></i> Create Case</a>
										</sec:authorize>
										<sec:authorize
											access="hasAnyRole('ROLE_ADMIN','ROLE_COMPANY','ROLE_ACCOUNT_MANAGER','ROLE_ACCOUNT_TEAM','ROLE_VIEW_CASE')">
											<a href="view-casemanagement" style="height: 15px;"><i
												class="fa fa-play" aria-hidden="true"
												style="font-size: 8px;"></i> List Case</a>
										</sec:authorize>

										<sec:authorize
											access="hasAnyRole('ROLE_COMPANY','ROLE_ACCOUNT_MANAGER','ROLE_CREATE_CUSTOMER')">
											<a href="create-customers" style="height: 15px;"><i
												class="fa fa-play" aria-hidden="true"
												style="font-size: 8px;"></i> Create Customers</a>
										</sec:authorize>

										<sec:authorize
											access="hasAnyRole('ROLE_ADMIN','ROLE_COMPANY','ROLE_ACCOUNT_MANAGER','ROLE_VIEW_CUSTOMER')">
											<a href="view-customers?number=1" style="height: 15px;"><i
												class="fa fa-play" aria-hidden="true"
												style="font-size: 8px;"></i> List Customers</a>
										</sec:authorize>

										<sec:authorize
											access="hasAnyRole('ROLE_COMPANY','ROLE_ACCOUNT_MANAGER','ROLE_ACCOUNT_TEAM','ROLE_CREATE_SOLUTION')">
											<a href="create_solution" style="height: 15px;"><i
												class="fa fa-play" aria-hidden="true"
												style="font-size: 8px;"></i> Create Solution</a>
										</sec:authorize>

										<sec:authorize
											access="hasAnyRole('ROLE_ADMIN','ROLE_COMPANY','ROLE_ACCOUNT_MANAGER','ROLE_ACCOUNT_TEAM','ROLE_VIEW_SOLUTION')">
											<a href="view_solution" style="height: 15px;"><i
												class="fa fa-play" aria-hidden="true"
												style="font-size: 8px;"></i> List Solution</a>
										</sec:authorize>
									</div></li>
							</sec:authorize>


							<sec:authorize
								access="hasAnyRole('ROLE_ADMIN','ROLE_COMPANY','ROLE_CREATE_CAMPAIGN','ROLE_VIEW_CAMPAIGN')">

								<li class="dropdown" id="fdJobId"><a href="#"
									class="pd-tp-27" style="font-size: 12px; font-weight: 600;">
										<span class="dropbtn">Campaigns <i
											class="fa fa-caret-down" aria-hidden="true"
											style="font-size: 15px; margin-left: 3px;"></i></span>
								</a>
									<div class="dropdown-content dropdown-menu" role="menu"
										style="list-style-type: none;">
										<sec:authorize
											access="hasAnyRole('ROLE_COMPANY','ROLE_ACCOUNT_MANAGER','ROLE_CREATE_CAMPAIGN')">
											<a href="create-campaign" style="height: 15px;"><i
												class="fa fa-play" aria-hidden="true"
												style="font-size: 8px;"></i> Create Campaign</a>
										</sec:authorize>
										<sec:authorize
											access="hasAnyRole('ROLE_ADMIN','ROLE_COMPANY','ROLE_ACCOUNT_MANAGER','ROLE_VIEW_CAMPAIGN')">
											<a href="view-campaign" style="height: 15px;"><i
												class="fa fa-play" aria-hidden="true"
												style="font-size: 8px;"></i> List Campaign</a>
										</sec:authorize>
									</div></li>
							</sec:authorize>

							<sec:authorize
								access="hasAnyRole('ROLE_CREATE_LEAD','ROLE_ADMIN','ROLE_COMPANY','ROLE_VIEW_LEAD','ROLE_MANAGE_LEAD')">

								<li class="dropdown" id="fdJobId"><a href="#"
									class="pd-tp-27" style="font-size: 12px; font-weight: 600;">
										<span class="dropbtn">Leads <i class="fa fa-caret-down"
											aria-hidden="true" style="font-size: 15px; margin-left: 3px;"></i></span>
								</a>
									<div class="dropdown-content dropdown-menu" role="menu"
										style="list-style-type: none;">
										<sec:authorize
											access="hasAnyRole('ROLE_COMPANY','ROLE_CREATE_LEAD')">
											<a href="create-leads" style="height: 15px;"><i
												class="fa fa-play" aria-hidden="true"
												style="font-size: 8px;"></i> Create Leads</a>
										</sec:authorize>
										<sec:authorize
											access="hasAnyRole('ROLE_ADMIN','ROLE_COMPANY','ROLE_VIEW_LEAD')">
											<a href="view-leads" style="height: 15px;"><i
												class="fa fa-play" aria-hidden="true"
												style="font-size: 8px;"></i> List Leads</a>
										</sec:authorize>
										<sec:authorize
											access="hasAnyRole('ROLE_ADMIN','ROLE_COMPANY','ROLE_MANAGE_LEAD')">
											<a href="upload-leads" style="height: 15px;"><i
												class="fa fa-play" aria-hidden="true"
												style="font-size: 8px;"></i> Upload Leads</a>
										</sec:authorize>
									</div></li>
							</sec:authorize>

							<sec:authorize
								access="hasAnyRole('ROLE_ADMIN','ROLE_COMPANY','ROLE_CREATE_ACCOUNT','ROLE_VIEW_ACCOUNT')">

								<li class="dropdown" id="fdJobId"><a href="#"
									class="pd-tp-27" style="font-size: 12px; font-weight: 600;">
										<span class="dropbtn">Accounts<i
											class="fa fa-caret-down" aria-hidden="true"
											style="font-size: 15px; margin-left: 3px;"></i></span>
								</a>
									<div class="dropdown-content dropdown-menu" role="menu"
										style="list-style-type: none;">
										<sec:authorize
											access="hasAnyRole('ROLE_COMPANY','ROLE_ACCOUNT_MANAGER','ROLE_CREATE_ACCOUNT')">
											<a href="create-account" style="height: 15px;"><i
												class="fa fa-play" aria-hidden="true"
												style="font-size: 8px;"></i> Create Account </a>
										</sec:authorize>
										<sec:authorize
											access="hasAnyRole('ROLE_ADMIN','ROLE_COMPANY','ROLE_VIEW_ACCOUNT')">
											<a href="view-accounts" style="height: 15px;"><i
												class="fa fa-play" aria-hidden="true"
												style="font-size: 8px;"></i> List Accounts </a>
										</sec:authorize>
									</div></li>
							</sec:authorize>






							<sec:authorize
								access="hasAnyRole('ROLE_ADMIN','ROLE_COMPANY','ROLE_CREATE_OPPORTUNITY','ROLE_VIEW_OPPORTUNITY')">
								<li class="dropdown" id="fdJobId"><a href="#"
									class="pd-tp-27" style="font-size: 12px; font-weight: 600;">
										<span class="dropbtn">Opportunity<i
											class="fa fa-caret-down" aria-hidden="true"
											style="font-size: 15px; margin-left: 3px;"></i></span>
								</a>
									<div class="dropdown-content dropdown-menu" role="menu"
										style="list-style-type: none;">
										<sec:authorize
											access="hasAnyRole('ROLE_COMPANY','ROLE_CREATE_OPPORTUNITY')">
											<a href="create-opportunity" style="height: 15px;"><i
												class="fa fa-play" aria-hidden="true"
												style="font-size: 8px;"></i> Create Opportunity</a>
										</sec:authorize>
										<sec:authorize
											access="hasAnyRole('ROLE_ADMIN','ROLE_COMPANY','ROLE_VIEW_OPPORTUNITY')">
											<a href="view-opportunities" style="height: 15px;"><i
												class="fa fa-play" aria-hidden="true"
												style="font-size: 8px;"></i> List Opportunities</a>
										</sec:authorize>
									</div></li>
							</sec:authorize>

							<sec:authorize
								access="hasAnyRole('ROLE_ADMIN','ROLE_COMPANY','ROLE_SALES_TEAM','ROLE_CREATE_SALESORDER','ROLE_VIEW_SALESORDER')">
								<li class="dropdown" id="fdJobId"><a href="#"
									class="pd-tp-27" style="font-size: 12px; font-weight: 600;"><span
										class="dropbtn">Sales Order <i class="fa fa-caret-down"
											aria-hidden="true" style="font-size: 15px; margin-left: 3px;"></i></span></a>

									<div class="dropdown-content dropdown-menu" role="menu"
										style="list-style-type: none;">
										<sec:authorize
											access="hasAnyRole('ROLE_COMPANY','ROLE_CREATE_SALESORDER')">
											<a href="create-sales-order" style="height: 15px;"><i
												class="fa fa-play" aria-hidden="true"
												style="font-size: 8px;"></i> Create Sales </a>
										</sec:authorize>
										<sec:authorize
											access="hasAnyRole('ROLE_ADMIN','ROLE_COMPANY','ROLE_VIEW_SALESORDER')">
											<a href="view-sales-order" style="height: 15px;"><i
												class="fa fa-play" aria-hidden="true"
												style="font-size: 8px;"></i> List Sales </a>
										</sec:authorize>
									</div></li>
							</sec:authorize>
							<sec:authorize
								access="hasAnyRole('ROLE_ADMIN','ROLE_COMPANY','ROLE_CREATE_REPORTS','ROLE_VIEW_REPORTS')">

								<li class="dropdown" id="fdJobId"><a href="#"
									class="pd-tp-27" style="font-size: 12px; font-weight: 600;"><span
										class="dropbtn">Reports<i class="fa fa-caret-down"
											aria-hidden="true" style="font-size: 15px; margin-left: 3px;"></i></span></a>

									<div class="dropdown-content dropdown-menu" role="menu"
										style="list-style-type: none;">
										<sec:authorize
											access="hasAnyRole('ROLE_ADMIN','ROLE_COMPANY','ROLE_VIEW_REPORTS')">
											<a href="view-leads-reports?number=1" style="height: 15px;"><i
												class="fa fa-play" aria-hidden="true"
												style="font-size: 8px;"></i> Leads Report </a>
										</sec:authorize>
										<sec:authorize
											access="hasAnyRole('ROLE_ADMIN','ROLE_COMPANY','ROLE_VIEW_REPORTS')">
											<a
												href="view-opportunitie?number=1&status=opportunityreports"
												style="height: 15px;"><i class="fa fa-play"
												aria-hidden="true" style="font-size: 8px;"></i> Opportunity
												Report </a>
											<a href="report-salesorder" style="height: 15px;"><i
												class="fa fa-play" aria-hidden="true"
												style="font-size: 8px;"></i> Sales Report </a>
										</sec:authorize>
									</div></li>
							</sec:authorize>
							<sec:authorize access="isAuthenticated()">
								<li class="dropdown" style="top: 10px;"><a href="logout"><span
										class="btn btn-theme  btn-pill btn-xs btn-line">Logout</span></a></li>
							</sec:authorize>
						</ul>
					</sec:authorize>
				</div>
			</div>
	</header>

	<!--start mobile header -->
	<div class="container mobi" style="background-color: #2a3f54;">
		<nav class="mobile-nav hidden-md hidden-lg navbar navbar-fixed-top">
			<ul class="nav navbar-nav nav-block-left">
				<li class="header-tab"
					style="width: 95px; color: #fff; font-size: 18px; font-weight: 600; margin: 10px 0 0 20px;"><span>
						<i class="fa fa-bar-chart" aria-hidden="true"><span
							style="color: #fff; font-size: 14px; font-weight: 600;"> <i>MySales</i></span></i>
				</span></li>
			</ul>
			<a href="#" class="btn-nav-toogle first"> <span class="bars"></span>
				Menu
			</a>

			<div class="mobile-nav-block">

				<a href="#" class="btn-nav-toogle"> <span class="barsclose"></span>
					Close
				</a>
				<ul class="nav navbar-nav" style="float: right;">
					<sec:authorize access="hasAnyRole('ROLE_ADMIN')">

						<li class="dropdown" id="fdJobId"><a href="#"
							class="pd-tp-27" style="font-size: 12px; font-weight: 600;"><span
								class="dropbtn">My Products<i class="fa fa-caret-down"
									aria-hidden="true" style="font-size: 15px; margin-left: 3px;"></i></span></a>
							<div class="dropdown-content dropdown-menu" role="menu"
								style="list-style-type: none;">
								<a href="create-gst" style="height: 15px;"><i
									class="fa fa-play" aria-hidden="true" style="font-size: 8px;"></i>
									Create GST</a> <a href="view-gst" style="height: 15px;"><i
									class="fa fa-play" aria-hidden="true" style="font-size: 8px;"></i>
									List GST</a> <a href="create-productservice" style="height: 15px;"><i
									class="fa fa-play" aria-hidden="true" style="font-size: 8px;"></i>
									Create Product</a> <a href="view-productservice"
									style="height: 15px;"><i class="fa fa-play"
									aria-hidden="true" style="font-size: 8px;"></i> List Product</a>
							</div></li>

						<li class="dropdown" id="fdJobId"><a href="#"
							class="pd-tp-27" style="font-size: 12px; font-weight: 600;"><span
								class="dropbtn">Access Management<i
									class="fa fa-caret-down" aria-hidden="true"
									style="font-size: 15px; margin-left: 3px;"></i></span></a>
							<div class="dropdown-content dropdown-menu" role="menu"
								style="list-style-type: none;">
								<a href="role-user-type" style="height: 15px;"><i
									class="fa fa-play" aria-hidden="true" style="font-size: 8px;"></i>
									Create Role</a> <a href="view-role" style="height: 15px;"><i
									class="fa fa-play" aria-hidden="true" style="font-size: 8px;"></i>
									List Roles</a> <a href="role-privileges" style="height: 15px;"><i
									class="fa fa-play" aria-hidden="true" style="font-size: 8px;"></i>
									Role Privileges</a> <a href="create-privileges"
									style="height: 15px;"><i class="fa fa-play"
									aria-hidden="true" style="font-size: 8px;"></i> Create
									Privileges</a> <a href="view-privileges" style="height: 15px;"><i
									class="fa fa-play" aria-hidden="true" style="font-size: 8px;"></i>
									List Privileges</a> <a href="create-access" style="height: 15px;"><i
									class="fa fa-play" aria-hidden="true" style="font-size: 8px;"></i>
									Create Access</a> <a href="view-access" style="height: 15px;"><i
									class="fa fa-play" aria-hidden="true" style="font-size: 8px;"></i>
									List Access</a> <a href="create-privilege-access"
									style="height: 15px;"><i class="fa fa-play"
									aria-hidden="true" style="font-size: 8px;"></i> Privileges
									Access</a> <a href="view-privilege-access" style="height: 15px;"><i
									class="fa fa-play" aria-hidden="true" style="font-size: 8px;"></i>
									List Privileges-Access</a>


							</div></li>

						<li class="dropdown" id="fdJobId"><a href="#"
							class="pd-tp-27" style="font-size: 12px; font-weight: 600;"><span
								class="dropbtn">Employees<i class="fa fa-caret-down"
									aria-hidden="true" style="font-size: 15px; margin-left: 3px;"></i></span></a>
							<div class="dropdown-content dropdown-menu" role="menu"
								style="list-style-type: none;">
								<a href="create-employees" style="height: 15px;"><i
									class="fa fa-play" aria-hidden="true" style="font-size: 8px;"></i>
									Create Employees</a> <a href="view-employees" style="height: 15px;"><i
									class="fa fa-play" aria-hidden="true" style="font-size: 8px;"></i>
									List Employees</a><a href="create-user-roles" style="height: 15px;"><i
									class="fa fa-play" aria-hidden="true" style="font-size: 8px;"></i>
									Manage User & Role</a>
							</div></li>
					</sec:authorize>
					<sec:authorize
						access="hasAnyRole('ROLE_ADMIN','ROLE_CAMPAIGN_TEAM','ROLE_SALES_TEAM','ROLE_CAMPAIGN_MANAGER','ROLE_SALES_MANAGER')">
						<li class="dropdown" id="fdJobId"><a href=" "
							class="pd-tp-27" style="font-size: 12px; font-weight: 600;"><span
								class="dropbtn">Campaigns <i class="fa fa-caret-down"
									aria-hidden="true" style="font-size: 15px; margin-left: 3px;"></i></span></a>

							<div class="dropdown-content dropdown-menu" role="menu"
								style="list-style-type: none;">
								<a href="create-campaign" style="height: 15px;"><i
									class="fa fa-play" aria-hidden="true" style="font-size: 8px;"></i>
									Create Campaign</a> <a href="view-campaign" style="height: 15px;"><i
									class="fa fa-play" aria-hidden="true" style="font-size: 8px;"></i>
									List Campaign</a>
							</div></li>

						<li class="dropdown" id="fdJobId"><a href="#"
							class="pd-tp-27" style="font-size: 12px; font-weight: 600;"><span
								class="dropbtn">Leads <i class="fa fa-caret-down"
									aria-hidden="true" style="font-size: 15px; margin-left: 3px;"></i></span></a>

							<div class="dropdown-content dropdown-menu" role="menu"
								style="list-style-type: none;">
								<a href="create-leads" style="height: 15px;"><i
									class="fa fa-play" aria-hidden="true" style="font-size: 8px;"></i>
									Create Leads</a> <a href="view-leads" style="height: 15px;"><i
									class="fa fa-play" aria-hidden="true" style="font-size: 8px;"></i>
									List Leads</a>
							</div></li>
					</sec:authorize>

					<sec:authorize
						access="hasAnyRole('ROLE_ADMIN','ROLE_SALES_TEAM','ROLE_SALES_MANAGER')">

						<li class="dropdown" id="fdJobId"><a href="#"
							class="pd-tp-27" style="font-size: 12px; font-weight: 600;"><span
								class="dropbtn">Customers <i class="fa fa-caret-down"
									aria-hidden="true" style="font-size: 15px; margin-left: 3px;"></i></span></a>

							<div class="dropdown-content dropdown-menu" role="menu"
								style="list-style-type: none;">
								<a href="create-customers" style="height: 15px;"><i
									class="fa fa-play" aria-hidden="true" style="font-size: 8px;"></i>
									Create Customers</a> <a href="view-customers" style="height: 15px;"><i
									class="fa fa-play" aria-hidden="true" style="font-size: 8px;"></i>
									List Customers</a>
							</div></li>





						<li class="dropdown" id="fdJobId"><a href="#"
							class="pd-tp-27" style="font-size: 12px; font-weight: 600;"><span
								class="dropbtn">Sales Order <i class="fa fa-caret-down"
									aria-hidden="true" style="font-size: 15px; margin-left: 3px;"></i></span></a>

							<div class="dropdown-content dropdown-menu" role="menu"
								style="list-style-type: none;">
								<a href="create-sales-order" style="height: 15px;"><i
									class="fa fa-play" aria-hidden="true" style="font-size: 8px;"></i>
									Create Sales </a> <a href="view-sales-order" style="height: 15px;"><i
									class="fa fa-play" aria-hidden="true" style="font-size: 8px;"></i>
									List Sales </a>
							</div></li>





					</sec:authorize>

					<sec:authorize
						access="hasAnyRole('ROLE_ADMIN','ROLE_SALES_TEAM','ROLE_SALES_MANAGER')">

						<li class="dropdown" id="fdJobId"><a href="#"
							class="pd-tp-27" style="font-size: 12px; font-weight: 600;"><span
								class="dropbtn">Reports<i class="fa fa-caret-down"
									aria-hidden="true" style="font-size: 15px; margin-left: 3px;"></i></span></a>

							<div class="dropdown-content dropdown-menu" role="menu"
								style="list-style-type: none;">
								<a href="view-leads-reports" style="height: 15px;"><i
									class="fa fa-play" aria-hidden="true" style="font-size: 8px;"></i>
									Leads Report </a> <a href="view-report?report=report"
									style="height: 15px;"><i class="fa fa-play"
									aria-hidden="true" style="font-size: 8px;"></i> Customers
									Report </a>
								<sec:authorize access="hasAnyRole('ROLE_ADMIN')">
									<a href="view-sales-order-list?salesid=salesid"
										style="height: 15px;"><i class="fa fa-play"
										aria-hidden="true" style="font-size: 8px;"></i> Sales Report </a>
								</sec:authorize>
							</div></li>
					</sec:authorize>



					<sec:authorize access="isAuthenticated()">
						<li class="dropdown" style="top: 10px;"><a href="logout"><span
								class="btn btn-theme  btn-pill btn-xs btn-line">Logout</span></a></li>
					</sec:authorize>
				</ul>
			</div>
		</nav>
	</div>
</div>


