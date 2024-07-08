<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add Price Package</title>
        <%@include file="/common/ImportBootstrap.jsp" %>
        <link rel="stylesheet" href="admin/common/admin-common.css">
        <script src="admin/common/admin-common.js"></script>
        <link rel="stylesheet" href="admin/subjectdetail/pricepackage/PricePackageEdit.css">
    </head>
    <body>
        <div class="admin-layout">
            <%@include file="/admin/common/admin-header.jsp" %>
            <%@include file="/admin/common/subject-sidebar.jsp" %>
            <main class="admin-main">
                <div class="container card mt-5">
                    <form class="card-body" method="POST" x-data="{
                          price: 1000,
                          percentage: 0,
                          get salePrice() {
                            if (this.price < 1000 || this.percentage > 100 || this.percentage < 0) {
                              return -1;
                            }
                            
                            return Math.floor(((100 - this.percentage) / 100) * this.price);
                          }
                    }">
                        <h2 class="my-4">
                            <i class="bi bi-tag-fill"></i>
                            Add Price Package
                        </h2>
                        
                        <div class="form-container">
                            <i class="bi bi-book"></i>
                            <label for="name" class="col-form-label" required>Name</label>
                            <input pattern="\S(.*\S)?" type="text" class="form-control" name="name" id="name" required placeholder="Enter package name" title="Input must not contain trailing space">

                            <i class="bi bi-clock"></i>
                            <label for="accessDuration" class="col-form-label">Access Duration (in months)</label>
                            <input type="number" value="1" min="1" max="48" name="duration" class="form-control" id="accessDuration" placeholder="Enter access duration in months" required>

                            <i class="bi bi-tag"></i>
                            <label for="listPrice" class="col-form-label">List Price</label>
                            <div class="input-group">
                                <span class="input-group-text">VND</span>
                                <input type="number" class="form-control" name="price" id="listPrice" placeholder="Enter list price" value="1000" step="1" min="1000" max="2000000000" x-model="price" required>
                            </div>

                            <i class="bi bi-percent"></i>
                            <label for="salePercentage" class="col-form-label">Sale Percentage</label>
                            <input type="number" class="form-control" id="salePercentage" x-model="percentage" name="percentage" placeholder="Enter sale percentage" value="0" step="1" min="0" max="100" required>

                            <i class="bi bi-tags"></i>
                            <label for="salePrice" class="col-form-label">Sale Price</label>
                            <div class="input-group">
                                <span class="input-group-text">VND</span>
                                <input type="text" value="1000" :value="salePrice >= 0 ? salePrice : 'Invalid'" class="form-control" id="salePrice" placeholder="Sale Price" disabled>
                            </div>

                            <i class="bi bi-textarea-resize"></i>
                            <label for="description" class="col-form-label">Description</label>
                            <textarea 
                                class="form-control"
                                id="description"
                                name="description"
                                rows="4"
                                maxlength="2048"
                                placeholder="Enter description"
                            ></textarea>

                            <div>&nbsp;</div>
                            <div>&nbsp;</div>
                            <div x-data="{ 
                                 reset: false,
                                 timeout: null,
                                 ask() {
                                    this.reset = true;
                                    this.timeout && clearTimeout(this.timeout);
                                    this.timeout = setTimeout(() => this.reset = false, 5000);
                                 }
                            }">
                                <button type="submit" class="btn btn-primary">Add</button>
                                <button 
                                    type="button" class="btn btn-outline-danger" 
                                    @click="ask" x-show="!reset">Reset</button>
                                <input type="reset" class="btn btn-danger" @click="reset = false" x-show="reset" value="Really Reset?">
                            </div>
                        </div>
                    </form>
                </div>
            </main>
        </div>

        <script src="public/js/alpine/core.min.js"></script>
        <%@include file="/admin/common/notyf.jsp" %>
    </body>
</html>


