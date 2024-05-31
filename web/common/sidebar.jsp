<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="offcanvas offcanvas-start w-25" tabindex="-1" id="offcanvas" data-bs-keyboard="false" data-bs-backdrop="false">
    <div class="offcanvas-header">
        <h6 class="offcanvas-title d-none d-sm-block" id="offcanvas">Menu</h6>
        <button type="button" class="btn-close text-reset" data-bs-dismiss="offcanvas" aria-label="Close"></button>
    </div>
    <div class="offcanvas-body px-0">
        <ul class="nav nav-pills flex-column mb-sm-auto mb-0 align-items-start" id="menu">
            <div class="accordion w-100" id="accordionExample">
                <div class="accordion-item">
                    <h2 class="accordion-header" id="headingOne">
                        <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
                            Top posts
                        </button>
                    </h2>
                    <div id="collapseOne" class="accordion-collapse collapse show" aria-labelledby="headingOne" data-bs-parent="#accordionExample">
                        <div class="accordion-body">
                            <div>Top post #1</div>
                            <div>Top post #2</div>
                            <div>Top post #3</div>
                            <div>Top post #4</div> 
                        </div>
                    </div>
                </div>
                <div class="accordion-item">
                    <h2 class="accordion-header" id="headingTwo">
                        <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
                            Contacts
                        </button>
                    </h2>
                    <div id="collapseTwo" class="accordion-collapse collapse" aria-labelledby="headingTwo" data-bs-parent="#accordionExample">
                        <div class="accordion-body">
                            <div>Contact #1</div>
                            <div>Contact #2</div>
                            <div>Contact #3</div>
                            <div>Contact #4</div>   
                        </div>
                    </div>
                </div>
                <%if(!(session.getAttribute("userEmail") == null)) {%>
                <div>
                    <form method="post" action="loginviewofAn">
                        <button type="submit" class="btn">Logout</button>
                        <input type="hidden" name="service" value="logout"/>
                    </form>
                </div>
                <%}%>

            </div>
        </ul>
    </div>
</div>
<div class="h-100" style="position: absolute; background-color: #dedde6; height: 100%; z-index: 0;" data-bs-toggle="offcanvas" data-bs-target="#offcanvas" role="button">
    <div style="height: 50vh"></div>   
    <button class="btn" style="position: sticky; display: block; top: 0; transform: rotate(90deg);" >
        <i class="bi bi-border-width" data-bs-toggle="offcanvas" data-bs-target="#offcanvas"></i>
    </button>
</div>