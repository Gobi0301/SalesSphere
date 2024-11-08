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
<link href="resources/css/jquery-ui-1.10.4.custom.css" rel="stylesheet">

<script>
	$(document).ready(function() {

		$('#btnsubmit').click(function(e) {

			//SLA Code
			var slaCode = $('#skillsCodeId').val();
			if (slaCode == '') {
				isValid = false;
				$("#skillsCodeErr").show();
				$("#skillsCodeErr").html("Please Enter Skills Code");
				$("#skillsCodeId").css({
					"border" : "1px solid red",
				});
			} else {
				$('#skillsCodeErr').hide();
				$('#skillsCodeId').css({
					"border" : "",
					"background" : ""
				});
			}

			//Skills descriptions
			var descriptions = $('#descriptionsId').val();
			if (descriptions == '') {
				isValid = false;
				$("#descriptionsErr").show();
				$("#descriptionsErr").html("Please Enter Descriptions Code");
				$("#descriptionsId").css({
					"border" : "1px solid red",
				});
			} else {
				$('#descriptionsErr').hide();
				$('#descriptionsId').css({
					"border" : "",
					"background" : ""
				});
			}

			if (isValid == false)
				e.preventDefault();
		});
	});
</script>

<!-- <script type="text/javascript">
	function skillcodecheck() {
		var skillcode = document.getElementById("skillsCodeId").value;
		document.getElementById("btnsubmit").disabled = false;
		if (skillcode != '') {
			$.ajax({
				url : "check_skillcode",
				type : "GET",
				data : 'skillcode=' + skillcode,
				success : function(result) {

					if (result == true) {
						$("#skillsCodeErr").html("SkillCode Already Exists");
						document.getElementById("btnsubmit").disabled = true;
						$("#skillsCodeErr").show();
						$("#skillsCodeId").css({
							"border" : "1px solid red",
						});

					} else {
						$("#skillsCodeErr").hide();
						$("#skillsCodeId").css({
							"border" : "",
							"background" : ""
						});
					}
				}
			});
		}
	};
</script>

<script type="text/javascript">
	function skillnamecheck() {
		var skillname = document.getElementById("descriptionsId").value;
		document.getElementById("btnsubmit").disabled = false;
		if (skillname != '') {
			$.ajax({
				url : "check_skillname",
				type : "GET",
				data : 'skillname=' + skillname,
				success : function(result) {

					if (result == true) {
						$("#descriptionsErr").html("SkillName Already Exists");
						document.getElementById("btnsubmit").disabled = true;
						$("#descriptionsErr").show();
						$("#descriptionsId").css({
							"border" : "1px solid red",
						});

					} else {
						$("#descriptionsErr").hide();
						$("#descriptionsId").css({
							"border" : "",
							"background" : ""
						});
					}
				}
			});
		}
	};
</script> -->

<script type="text/javascript">
    var isSkillCodeValid = true;
    var isSkillNameValid = true;

    function skillcodecheck() {
        var skillcode = document.getElementById("skillsCodeId").value;
        if (skillcode != '') {
            $.ajax({
                url: "check_skillcode",
                type: "GET",
                data: 'skillcode=' + skillcode,
                success: function (result) {
                    if (result == true) {
                        $("#skillsCodeErr").html("SkillCode Already Exists");
                        $("#skillsCodeErr").show();
                        $("#skillsCodeId").css({
                            "border": "1px solid red",
                        });
                        isSkillCodeValid = false;
                    } else {
                        $("#skillsCodeErr").hide();
                        $("#skillsCodeId").css({
                            "border": "",
                            "background": ""
                        });
                        isSkillCodeValid = true;
                    }
                    enableSubmitButton();
                }
            });
        }
    };

    function skillnamecheck() {
        var skillname = document.getElementById("descriptionsId").value;
        if (skillname != '') {
            $.ajax({
                url: "check_skillname",
                type: "GET",
                data: 'skillname=' + skillname,
                success: function (result) {
                    if (result == true) {
                        $("#descriptionsErr").html("SkillName Already Exists");
                        $("#descriptionsErr").show();
                        $("#descriptionsId").css({
                            "border": "1px solid red",
                        });
                        isSkillNameValid = false;
                    } else {
                        $("#descriptionsErr").hide();
                        $("#descriptionsId").css({
                            "border": "",
                            "background": ""
                        });
                        isSkillNameValid = true;
                    }
                    enableSubmitButton();
                }
            });
        }
    };

    function enableSubmitButton() {
        if (isSkillCodeValid && isSkillNameValid) {
            document.getElementById("btnsubmit").disabled = false;
        } else {
            document.getElementById("btnsubmit").disabled = true;
        }
    }
</script>





<div class="contact-form-wrapper">

	<div class="box-list">
		<div class="item">
			<div class="row ">
				<div class="text-center underline">
					<h3>Edit Skills</h3>
				</div>

				<form:form method="POST" id="addForm" action="edit-skills"
					modelAttribute="skillsBO">
					<div class="col-sm-12">
						<form:hidden path="skillsId" name="id"
							value="${skillsBO.skillsId}" />
						<div class="col-sm-4">
							<div class="form-group">
								<label> Skills Code<span class="font10 text-danger">*</span></label>
								<form:input type="text" id="skillsCodeId" path="skillsCode"
									class="form-control required" placeholder="Skills Code"
									maxlength="120" onchange="skillcodecheck()" />
								<form:errors path="skillsCode" class="error" />
								<div id="skillsCodeErr" style="color: red;"></div>
							</div>
						</div>
						<div class="col-sm-4">
							<div class="form-group">
								<label> Skill Name <span class="font10 text-danger">*</span></label>
								<form:input type="text" id="descriptionsId"
									path="descriptions" class="form-control required"
									placeholder="Skill Name" maxlength="120"
									onchange="skillnamecheck()" />
								<form:errors path="descriptions" class="error" />
								<div id="descriptionsErr" style="color: red;"></div>
							</div>
						</div>
					</div>
			
			<div style="text-align: right; margin-right: 31px">
				<div class="form-group">
					<form:button type="submit" id="btnsubmit"
						class="btn btn-t-primary btn-theme lebal_align mt-20 ">Submit</form:button>
					<a href="view-skills?page=1"><span
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
