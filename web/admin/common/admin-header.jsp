<header class="admin-header">
    <div class="container-fluid admin-header-container bg-secondary d-flex align-items-center">
        <div class="row flex-grow-1 gx-1">
            <div class="col-11 d-flex align-items-center">
                <button onclick="adminShowHideSidebar()" type="button" class="btn btn-outline text-white hamburger-btn" aria-label="Close">
                    <i class="bi bi-list"></i>
                </button>
                <h5 class="admin-header-title">Management Console</h5>
            </div>
            <div class="col-1 d-flex justify-content-end">
                <div class="dropdown">
                    <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownAdmin"
                            data-bs-toggle="dropdown" aria-expanded="false">
                        <i class="bi bi-person-circle"></i>
                    </button>
                    <ul class="dropdown-menu" aria-labelledby="dropdownAdmin">
                        <li><a class="dropdown-item" href="#">Your profile</a></li>
                        <li><a class="dropdown-item" href="#">Settings</a></li>
                        <form method="post" action="loginviewofAn">
                            <button type="submit" class="btn">Logout</button>
                            <input type="hidden" name="service" value="logout"/>
                        </form>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</header>