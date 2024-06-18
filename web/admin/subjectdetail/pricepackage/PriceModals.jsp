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

<div class="modal fade" id="createModal" tabindex="-1" aria-labelledby="crudModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <form method="POST">
                <input type="hidden" name="action" :value="mode">
                <template x-if="form.id">
                    <input type="hidden" name="id" :value="form.id">
                </template>
                <div class="modal-header">
                    <h5 class="modal-title" id="crudModalLabel" x-text="modalTitle"></h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="mb-3">
                        <label for="packageName" class="form-label">Package Name</label>
                        <input name="name" type="text" class="form-control" id="packageName" x-model="form.name" placeholder="Enter package name" required>
                    </div>
                    <div class="mb-3">
                        <label for="duration" class="form-label">Duration (months)</label>
                        <input name="duration" type="number" class="form-control" id="duration" min="1" x-model="form.duration" placeholder="Enter duration in months" required>
                    </div>
                    <div class="mb-3">
                        <label for="listPrice" class="form-label">List Price</label>
                        <input name="price" type="number" @input="calculateSalePrice" class="form-control" id="listPrice" min="1000" x-model="form.listPrice" placeholder="Enter list price" required>
                    </div>
                    <div class="mb-3">
                        <label for="salePercentage" class="form-label">Sale Percent</label>
                        <input name="percentage" type="number" class="form-control" id="salePercentage" x-model="form.salePercentage" placeholder="Sale %" min="0" max="100" @input="calculateSalePrice" required>
                    </div>
                    <div class="mb-3">
                        <label for="salePrice" class="form-label">Calculated Sale Price</label>
                        <input type="number" class="form-control" id="salePrice" x-model="form.salePrice" disabled>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                    <button type="submit" class="btn btn-primary">Save</button>
                </div>
            </form>
        </div>
    </div>
</div>