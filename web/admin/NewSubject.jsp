<%-- 
    Document   : NewSubject
    Created on : Jun 18, 2024, 2:08:40 PM
    Author     : QuanNM
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Mew Subject</title>
        <%@include file="/common/ImportBootstrap.jsp" %>
        <link rel="stylesheet" href="admin/common/admin-common.css">
        <script src="admin/common/admin-common.js"></script>
        <link rel="stylesheet" href="public/css/NewSubject.css">
    </head>
    <body>
        <div class="admin-layout">
            <%@include file="/admin/common/admin-header.jsp" %>
            <%@include file="/admin/common/admin-sidebar.jsp" %>
            <main class="admin-main">
                <form class="container" id='new-subject-form' action="admin/newsubject?service=add" onreset="formReset()">
                    <div class="row bg-light border border-1 p-3">
                        <h1>Add a new subject</h1>
                        <div class="row">
                            <div class="col-8">
                                <div class="form-group pb-3">
                                    <label for="subject-title">Title</label><br>
                                    <small id='title-warning' style="color: red; display: none;">The maximum amount of characters for subject title is 50!</small>
                                    <input type="text" class="form-control" id="subject-title" name="subjectTitle" placeholder="" oninput="validateTitle(this.value)">
                                </div>

                                <div class="form-group">
                                    <label for="subject-category">Category</label>
                                    <select class="form-control" id="subject-category" name="subjectCategory">
                                        <c:forEach var="category" items="${subjectCategoryList}">
                                            <option value="${category.getCateId()}">${category.getCateName()}</option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <div class="row">
                                    <div class="form-check col-6 pt-5 ps-5">
                                        <input class="form-check-input" type="checkbox" value="true" id="featured-flag" name="featured">
                                        <label class="form-check-label" for="featured-flag">
                                            Featured
                                        </label>
                                    </div>
                                    <div class="form-group col-6 row p-0 pt-5">
                                        <div class="col-md-2">
                                            <label for="subject-status">Status</label>
                                        </div>
                                        <div class="col-md-1 col-sm-0">
                                        </div>
                                        <div class="col-md-9 p-0">
                                            <select class="form-control" id="subject-status" name="subjectStatus">
                                                <option value="0">Unpublished</option>
                                                <option value="1">Published</option>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="col-4">
                                <div id="img-div">
                                    <img id="subject-thumbnail" src="public/images/image-break.png" onerror="imgError()" alt="Subject Thumbnail"/>
                                </div>
                            </div>

                        </div>

                        <div class="row">
                            <div class="col-8"></div>
                            <div class="col-4">
                                <!--label for="image-upload" class="form-label">Upload image</label>
                                <input class="form-control" type="file" id="image-upload"-->
                                <p  id="imgsetter-text" class="mb-0 mt-2">Link to thumbnail image:</p>
                                <div class="input-group mb-3">
                                    <input type="text" id="thumbnail-url" name="thumbnailUrl" class="form-control" placeholder="" aria-label="Subject thumbnail" aria-describedby="thumbnail-preview-btn" oninput="changeSetter()">
                                    <!--button class="btn btn-outline-secondary" type="button" id="thumbnail-preview-btn" onclick="setPreviewImg()">Preview</button-->
                                </div>
                            </div>
                        </div>

                        <div class="form-group pb-3">
                            <label for="subject-tagline">Tagline</label><br>
                            <small id='tagline-warning'>A short sentence (<50 characters) that describes this subject.</small>
                            <input type="text" class="form-control" id="subject-tagline" name="subjectTagline" placeholder="" oninput="validateTagline(this.value)">
                        </div>

                        <div class="form-group">
                            <label for="subject-brief">Brief Info</label><br>
                            <small id='brief-warning'">Write a short paragraph (<300 characters) that describes this subject.</small>
                            <textarea class="form-control" id="subject-brief" rows="3" name="subjectBrief" oninput="validateBrief(this.value)"></textarea>
                        </div>

                        <div class="form-group mt-5">
                            <label for="subject-description">Description</label>
                            <button type="button" class="btn btn-sm btn-outline-info float-end mb-1" data-bs-toggle="modal" data-bs-target="#useTemplate"><small>Use template</small></button>
                            <textarea class="form-control" id="subject-description" rows="3" name="subjectDescription"></textarea>
                        </div>
                    </div>

                    <div class="row mt-3">
                        <div class="col-6">
                            <input id="submitButton" class="btn btn-primary disabled" type="submit" value="Please choose an expert">
                            <input id="clearButton" class="btn btn-secondary" type="reset" value="Reset">
                        </div>
                        <div id='expert-search-div' class="col-6 border border-1  py-3">
                            <h3>Owner</h3>

                            <div id="subject-search" class="input-group">
                                <input id='query' type="email" class="form-control" placeholder="Enter an expert's email or name" oninput="filterExpert()" onfocus="focusSearch()" onblur="unFocusSearch()">
                                <input id='hiddenEmail' type="hidden" name="expertEmail">
                                <span class="input-group-text" >
                                    <i class="bi bi-search"></i>
                                </span>
                            </div>

                            <div style="position: relative">
                                <div id='search-result' class="bg-light border border-1 rounded-bottom-2">

                                </div>
                                Please choose an expert to create the subject
                                <div id='chosen-expert' class="bg-light border border-1 rounded-bottom-2">

                                </div>
                            </div>
                        </div>
                    </div>

                    <input type="hidden" name="service" value="add">
                </form>
            </main>
        </div>
        <script src="public/js/NewSubject.js" expertList='${expertList}'></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.0/jquery.min.js"></script>
        <c:if test="${not empty notification}">
        <script>
                                    //$(document).ready(function () {
                                    //    $("#notify").modal('show');
                                    //});
                alert('${notification}');                   
        </script>
        </c:if>

    </body>
</html>

<div class="modal fade" id="useTemplate" tabindex="-1" role="dialog" >
    <div class="modal-dialog modal-lg modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h4>Use a template for subject description</h4>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close">
                </button>
            </div>
            <div class="modal-body">
                <h4>Choose a template:</h4>
                <select class="form-control" id="template-options">
                    <option value="1" selected>4 sections with a navbar</option>
                </select>
                <strong style="color: red;"><small>Choosing a template will wipe what you've already written in the subject description text area</small></strong>
                <button type="button" class="btn btn-primary float-end mt-5" onclick="setTemplate(document.getElementById('template-options').value)" data-bs-dismiss="modal" aria-label="Close">Confirm</button>
            </div>
        </div>
    </div>
</div>

<!--div class="modal fade" id="notify" tabindex="-1" role="dialog" >
    <div class="modal-dialog modal-lg modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close">
                </button>
            </div>
            <div class="modal-body">
                ${notification}
                <c:set var="notification" scope="session" value="" />
            </div>
        </div>
    </div>
</div-->