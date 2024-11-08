<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Upload Leads</title>
</head>

<script>
    $(document).ready(function () {
        $('#btnSubmit').click(function (e) {
            var upload = $('#uploadFiles').val();
            if (upload === '') {
                $("#fileError").show();
                $("#fileError").html("Please select a file");
                $("#uploadFiles").css({
                    "border": "1px solid red",
                });
                e.preventDefault(); // Prevent the form submission
            } else {
                $('#fileError').hide();
                $('#uploadFiles').css({
                    "border": "",
                    "background": ""
                });
            }
        });
    });
</script>
<body>
    <div class="contact-form-wrapper">
        <br><br><br>
        <div class="box-list">
            <div class="item">
                <div class="row ">
                    <div class="text-center underline">
                        <h3>Upload Leads</h3>
                    </div>
                    <div class="pi-section-w pi-section-white piICheck piStylishSelect">
                        <div class="pi-section pi-padding-bottom-50">
                            <div style="text-align: center; margin-top: -20px; margin-bottom: 20px;">
                                <!-- <h4 class="h4 pi-weight-200 pi-uppercase  pi-margin-bottom-25"
                                </h4> -->
                            </div>
                        </div>
                        <form:form action="upload-leads" method="post" modelAttribute="leadsOBJ" commandName="leadsOBJ"
                            enctype="multipart/form-data">
                            <div class="pi-col-sm-6">
                                <div class="form-group">
                                    <b><label>Upload Leads</label></b>
                                    <div class="pi-input-with-icon">
                                        <input type="file" name="uploadleads" id="uploadFiles" style="margin-top: 8px;" />
                                        <div id="fileError" class="error"
                                            style="color: red; font-weight: bold; font-size: 13px;"></div>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group" style="text-align: right;">
                                <input type="submit" value="Submit" class="btn btn-t-primary btn-theme" id="btnSubmit" />
                                <a href="view-leads">
                                    <input type="button" value="Cancel" class="btn btn-t-primary btn-theme" />
                                </a>
                            </div>
                        </form:form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
