<div class="modal fade" id="deactivateModal" tabindex="-1">
    <form class="modal-dialog" method="POST">
        <input type="hidden" value="deactivate" name="action">
        <input type="hidden" :value="selected.id" name="id">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Deactivate package?</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body d-flex flex-column gap-2">
                <p>Package <b x-text="selected.name"></b> will be deactivated</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                <button class="btn btn-danger">Deactivate</button>
            </div>
        </div>
    </form>
</div> 

<div class="modal fade" id="activateModal" tabindex="-1">
    <form class="modal-dialog" method="POST">
        <input type="hidden" value="activate" name="action">
        <input type="hidden" :value="selected.id" name="id">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Activate package?</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body d-flex flex-column gap-2">
                <p>Package <b x-text="selected.name"></b> will be activated</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                <button type="submit" class="btn btn-primary">Activate</button>
            </div>
        </div>
    </form>
</div> 