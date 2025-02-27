<%-- 
    Document   : sliderlist
    Created on : Jun 11, 2024, 7:21:24 AM
    Author     : anlthe182228
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Question List</title>
        <%@include file="/common/ImportBootstrap.jsp" %>
        <link rel="stylesheet" href="admin/common/admin-common.css">
        <script src="admin/common/admin-common.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js" integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css" integrity="sha512-SnH5WK+bZxgPHs44uWIX+LLJAJ9/2PkPKZ5QiAj6Ta86w+fsb2TkcmfRyVX3pBnMFcV7oQPJkl9QevSCWr3W6A==" crossorigin="anonymous" referrerpolicy="no-referrer" />
    </head>
    <body>
        <c:if test="${param['index']==null }">   
            <c:set var = "index" scope = "page" value = "1"/>
        </c:if>
        <c:if test="${param['index']!=null}">
            <c:set var = "index" scope = "page" value = "${param['index']}"/>
        </c:if>
        <div class="admin-layout">
            <%@include file="/admin/common/admin-header.jsp" %>
            <%@include file="/admin/common/admin-sidebar.jsp" %>
            <main class="admin-main">
                <section class="content">
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="panel">
                                <header class="panel-heading">
                                    Slider List
                                </header>
                                <!-- <div class="box-header"> -->
                                <!-- <h3 class="box-title">Responsive Hover Table</h3> -->

                                <!-- </div> -->
                                <div class="panel-body table-responsive">
                                    <div class="sliderList">
                                        <div class="input-group" style="margin-bottom: 10px;width: 100%; ">
                                            <form class="search-slider"
                                                  style="width: 100%; "
                                                  method="get" action="sliderlist">
                                                <div class="row">
                                                    <div class="col-md-4">
                                                        <label class="form-control"
                                                               style="border:none;display: inline;">Status</label>
                                                        <select class="form-control" name="status" style="display: inline; width: 100px;">
                                                            <option value="">All</option>
                                                            <option value="1" ${param['status']==1?'selected':''}>Active</option>
                                                            <option value="0" ${param['status']==0?'selected':''}>Inactive</option>
                                                        </select>
                                                    </div>
                                                    <div class="col-md-6">

                                                        <input type="text" name="search" class="form-control" value="${param['search']}"
                                                               style="width: 250px;display: inline;" placeholder="Search" />
                                                        <select class="form-select input-sm" name="type-search"
                                                                style="display: inline; width: 90px;">
                                                            <option value="0" ${param['type-search']==0?'selected':''}>Title</option>
                                                            <option value="1" ${param['type-search']==1?'selected':''}>Backlink</option>
                                                        </select>
                                                    </div>
                                                    <div class="col-md-2">
                                                        <button class="btn btn-default">Go</button>
                                                    </div>
                                                </div>
                                            </form>
                                        </div>
                                        <table class="panel-body table-responsive" id="tablepro" cellspacing="0" style="width: 100%;">
                                            <thead>
                                                <tr style="cursor: pointer; font-size: 15px; border-bottom: 1px solid #ccc; text-align: center;">
                                                    <th>ID<i class="fa fa-caret-down" aria-hidden="true"></i></th>
                                                    <th>Title<i class="fa fa-caret-down" aria-hidden="true"></i></th>
                                                    <th>Image</th>
                                                    <th>Backlink<i class="fa fa-caret-down" aria-hidden="true"></i></th>
                                                    <th>Status<i class="fa fa-caret-down" aria-hidden="true"></i></th>
                                                    <th  width="3%">Edit</th>
                                                    <th  width="3%">Delete</th>
                                                    <th width="3%">Switch</th>
                                                </tr>
                                            </thead>
                                            <tbody>

                                                <c:forEach var="s" items="${slist}">
                                                    <tr>
                                                        <td>${s.getSilde_id()}</td>
                                                        <td data-toggle="modal" data-target="#Show${s.getSilde_id()}" style="cursor: pointer;">${s.getTitle()}</td>
                                                        <td><image  src="${s.getImage()}" width="200px"></td>
                                                        <td>${s.getBacklink()}</td>
                                                        <c:if test="${s.isActive()}">
                                                            <td><span class="label label-success" style="font-size: 15px">Active</span></td>
                                                        </c:if>
                                                        <c:if test="${!s.isActive()}">
                                                            <td><span class="label label-danger" style="font-size: 15px">Inactive</span></td>
                                                        </c:if>
                                                        <td><a class="btn btn-primary"  data-toggle="modal" data-target="#EditModalUP${s.getSilde_id()}"><i class="fa-solid fa-pen-to-square"></i></a></td>
                                                        <td><a class="btn btn-danger" data-toggle="modal" data-target="#Delete${s.getSilde_id()}"><i class="fa-solid fa-trash"></i></a></td>
                                                                <c:if test="${s.isActive()}">
                                                            <td><a class="btn btn-warning" href="sliderlist?action=switch&sid=${s.getSilde_id()}&sstatus=0">InActive</a></td>
                                                        </c:if>
                                                        <c:if test="${!s.isActive()}">
                                                            <td><a class="btn btn-success" href="sliderlist?action=switch&sid=${s.getSilde_id()}&sstatus=1">Active</a></td>
                                                        </c:if>
                                                    </tr>
                                                    <!-- Show detail modal -->

                                                <div class="modal fade" id="Delete${s.getSilde_id()}" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                                    <div class="modal-dialog" role="document">
                                                        <div class="modal-content">
                                                            <div class="modal-header">
                                                                <h5 class="modal-title" id="exampleModalLabel">Confirm delete Slide</h5>

                                                            </div>
                                                            <div class="modal-body">
                                                                <div style="text-align: center">Do you want delete Slide?</div>
                                                                <div style="text-align: center">Slide Id: ${s.getSilde_id()}</div>
                                                            </div>
                                                            <div class="modal-footer">
                                                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                                                                <button onclick="window.location = 'sliderlist?action=delete&sid=${s.getSilde_id()}'" type="button" class="btn btn-primary">Delete Slide</button>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div> 

                                                <div class="modal fade" id="Show${s.getSilde_id()}" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                                    <div class="modal-dialog" role="document">
                                                        <div class="modal-content">
                                                            <div class="modal-header">
                                                                <h5 class="modal-title" id="exampleModalLabel">Slider Detail</h5>
                                                            </div>
                                                            <div class="modal-body">
                                                                <div class="row">
                                                                    <div class="form-group col-md-6">
                                                                        <h3>ID : ${s.getSilde_id()} </h3>
                                                                    </div>
                                                                    <div class="form-group col-md-6">
                                                                        <h3>Title : ${s.getTitle()} </h3>
                                                                    </div>
                                                                    <div class="form-group col-md-12">
                                                                        <label class="control-label">Description:</label>
                                                                        <p>${s.getDescription()}</p>
                                                                    </div>
                                                                    <div class="form-group col-md-12">
                                                                        <label class="control-label">Backlink:</label>
                                                                        <p>${s.getBacklink()}</p>    
                                                                    </div>

                                                                    <div class="form-group col-md-12">
                                                                        <label class="control-label">Status:  </label> ${s.isActive()?'<span class="label label-success" style="font-size: 15px">Acive</span>':'<span class="label label-danger" style="font-size: 15px">Inacive</span>'}
                                                                    </div>
                                                                    <div class="form-group col-md-12">
                                                                        <label class="control-label">Image:</label><br>
                                                                        <image  src="${s.getImage()}" width="80%">
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="modal-footer">
                                                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <!-- edit modal -->
                                                <div class="modal fade" id="EditModalUP${s.getSilde_id()}" tabindex="-1" role="dialog" aria-hidden="true" data-backdrop="static"
                                                     data-keyboard="false">
                                                    <div class="modal-dialog modal-dialog-centered" role="document">
                                                        <div class="modal-content">
                                                            <div class="modal-body">
                                                                <form action="sliderlist?action=edit" method="post" onsubmit="return checkInput${s.getSilde_id()}();">
                                                                    <div class="row">
                                                                        <div class="form-group  col-md-12">
                                                                            <span class="thong-tin-thanh-toan">
                                                                                <h5>Edit Slide Information</h5>
                                                                            </span>
                                                                        </div>
                                                                    </div>
                                                                    <div class="row">
                                                                        <div class="form-group col-md-6">
                                                                            <label class="control-label">ID</label>
                                                                            <input class="form-control" type="text" readonly name="slideid" value="${s.getSilde_id()}">
                                                                        </div>
                                                                        <div class="form-group col-md-6">
                                                                            <label for="exampleSelect1" class="control-label">Title</label>
                                                                            <input class="form-control" type="text" id="stitle${s.getSilde_id()}" maxlength="50" name="slidetitle" required value="${s.getTitle()}">
                                                                            <span id="invalid1${s.getSilde_id()}" style="color: red;"> </span>

                                                                        </div>
                                                                        <div class="form-group col-md-12">
                                                                            <label class="control-label">Backlink</label>
                                                                            <input class="form-control" type="text" id="backlink${s.getSilde_id()}" maxlength="250" name="slidebacklink"  required value="${s.getBacklink()}">
                                                                            <span id="invalid2${s.getSilde_id()}" style="color: red;"> </span>

                                                                        </div>
                                                                        <div class="form-group col-md-12">
                                                                            <label class="control-label">Description</label>
                                                                            <input class="form-control" type="text" id="sdesc${s.getSilde_id()}" maxlength="250"  name="slidedescript" required value="${s.getDescription()}">
                                                                            <span id="invalid3${s.getSilde_id()}" style="color: red;"> </span>
                                                                        </div>
                                                                        <div class="form-group col-md-12">
                                                                            <label class="control-label">Status</label><br>
                                                                            <input  type="radio" name="slidestatus" required value="1" ${s.isActive()?'Checked':''}>Active
                                                                            <input  type="radio" name="slidestatus" required value="0" ${!s.isActive()?'Checked':''}> Inactive
                                                                        </div>
                                                                        <div class="form-group col-md-10">
                                                                            <label class="control-label" >Image</label>

                                                                            <input class="form-control" id="img${s.getSilde_id()}" onchange="changeimg${s.getSilde_id()}()" name="image" value="${s.getImage()}" type="file" >
                                                                            <input name="slideimage" id="slideimage${s.getSilde_id()}" value="${s.getImage()}" type="hidden" >
                                                                            <image src="${s.getImage()}" id="demoimg${s.getSilde_id()}" style="margin-top: 5px;" width="100%">
                                                                        </div>
                                                                    </div>
                                                                    <BR>
                                                                    <button class="btn btn-primary" type="submit">Save</button>
                                                                    <a class="btn btn-danger" data-dismiss="modal" href="#">Cancel</a>
                                                                    <BR>
                                                                </form>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <script>
                                                    function changeimg${s.getSilde_id()}() {
                                                        var file = document.getElementById("img${s.getSilde_id()}").files[0];
                                                        if (file.name.match(/.+\.(jpg|png|jpeg)/i)) {
                                                            if (file.size / (1024 * 1024) < 5) {
                                                                var fileReader = new FileReader();
                                                                fileReader.readAsDataURL(file);
                                                                fileReader.onload = function () {
                                                                    document.getElementById("slideimage${s.getSilde_id()}").value = (fileReader.result);
                                                                    document.getElementById("demoimg${s.getSilde_id()}").src = (fileReader.result);
                                                                }
                                                            } else {
                                                                uploadError();
                                                            }
                                                        } else {
                                                            uploadError();
                                                        }
                                                    }
                                                    function uploadError() {
                                                        alert('Please upload photo file < 5MB')
                                                        document.getElementById("img${s.getSilde_id()}").files[0].value = ''
                                                        document.getElementById("img${s.getSilde_id()}").type = '';
                                                        document.getElementById("img${s.getSilde_id()}").type = 'file';
                                                    }
                                                    function checkInput${s.getSilde_id()}() {
                                                        var content = document.getElementById("sdesc${s.getSilde_id()}");
                                                        console.log(1);
                                                        var a = 0;
                                                        if (content.value.length > 300) {
                                                            document.getElementById("invalid3${s.getSilde_id()}").innerHTML = "Description must less than 300 character!"
                                                            a = 1;
                                                        } else {
                                                            document.getElementById("invalid3${s.getSilde_id()}").innerHTML = ""
                                                        }
                                                        if (document.getElementById("stitle${s.getSilde_id()}").value.length > 100) {
                                                            document.getElementById("invalid1${s.getSilde_id()}").innerHTML = "Title must less than 100 character!"
                                                            a = 1;
                                                        } else {
                                                            document.getElementById("invalid1${s.getSilde_id()}").innerHTML = ""
                                                        }
                                                        if (document.getElementById("backlink${s.getSilde_id()}").value.length > 200) {
                                                            document.getElementById("invalid2${s.getSilde_id()}").innerHTML = "Backlink must less than 200 character!"
                                                            a = 1;
                                                        } else {
                                                            document.getElementById("invalid2${s.getSilde_id()}").innerHTML = ""

                                                        }
                                                        console.log(1);
                                                        if (a == 1) {
                                                            return false;
                                                        } else {
                                                            return true;
                                                        }
                                                    }

                                                </script>

                                            </c:forEach>
                                            </tbody>
                                        </table>
                                        <div class="pagination-arena " style="margin-left: 40%">
                                            <ul class="pagination">
                                                <li class="page-item"><a href="./sliderlist?status=${param['status']}&search=${param['search']}&type-search=${param['type-search']}&index=1" class="page-link"><i class="fa fa-angle-left" aria-hidden="true"></i></a></li>
                                                <li class="page-item">
                                                    <a href="./sliderlist?status=${param['status']}&search=${param['search']}&type-search=${param['type-search']}&index=${index-2}" class="page-link " style="${index-2<1?"display:none;":""}">${index-2}</a></li>
                                                <li class="page-item">
                                                    <a href="./sliderlist?status=${param['status']}&search=${param['search']}&type-search=${param['type-search']}&index=${index-1}" class="page-link " style="${index-1<1?"display:none;":""}">${index-1}</a></li>
                                                <li class="page-item active">
                                                    <a href="./sliderlist?status=${param['status']}&search=${param['search']}&type-search=${param['type-search']}&index=${index}" class="page-link">${index}</a></li>
                                                <li class="page-item">
                                                    <a href="./sliderlist?status=${param['status']}&search=${param['search']}&type-search=${param['type-search']}&index=${index+1}" class="page-link " style="${index+1>numberPage?"display:none;":""}" >${index+1}</a></li>
                                                <li class="page-item">
                                                    <a href="./sliderlist?status=${param['status']}&search=${param['search']}&type-search=${param['type-search']}&index=${index+2}" class="page-link " style="${index+2>numberPage?"display:none;":""}">${index+2}</a></li>
                                                <li><a href="./sliderlist?status=${param['status']}&search=${param['search']}&type-search=${param['type-search']}&index=${numberPage}" class="page-link"><i class="fa fa-angle-right" aria-hidden="true"></i></a></li>
                                            </ul>
                                        </div> 
                                    </div><!-- /.box-body -->
                                </div><!-- /.box -->
                            </div>
                        </div>
                </section><!-- /.content -->
                <div class="footer-main">
                    Copyright &copy Director, 2014
                </div>
            </main>
        </div>
        <script src="https://code.jquery.com/jquery-3.5.1.js"></script>
        <script src="https://cdn.datatables.net/1.12.1/js/jquery.dataTables.min.js"></script>
        <script src="https://cdn.datatables.net/1.12.1/js/dataTables.bootstrap5.min.js"></script>
        <script>
                                                    $(document).ready(function () {
                                                        $("#tablepro").DataTable({bFilter: false, bInfo: false, paging: false});
                                                    });
        </script>
        <!-- jQuery -->
        <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
        <!-- Bootstrap JS -->
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>

    </body>
</html>
