<div class="modal fade" id="deleteModal" tabindex="-1">
    <form class="modal-dialog" method="POST">
        <input type="hidden" name="action" value="delete">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Delete these items?</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body d-flex flex-column gap-2">
                <template x-for="[key, value] in Object.entries(map)">
                    <div class="card">
                        <input type="hidden" name="ids[]" x-bind:value="key" />
                        <div class="card-body d-flex justify-content-between">
                            <h5 class="card-title">
                                ID <span x-text="key"></span>
                            </h5>
                            <p class="card-text" x-text="value"></p>
                        </div>
                    </div>
                </template>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                <button @click="sessionStorage.clear()" type="submit" class="btn btn-danger">Delete</button>
            </div>
        </div>
    </form>
</div> 

<div class="modal fade" id="markDraftModal" tabindex="-1">
    <form class="modal-dialog" method="POST">
        <input type="hidden" name="action" value="markDraft">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Draft these items?</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body d-flex flex-column gap-2">
                <template x-for="[key, value] in Object.entries(map)">
                    <div class="card">
                        <input type="hidden" name="ids[]" x-bind:value="key" />
                        <div class="card-body d-flex justify-content-between">
                            <h5 class="card-title">
                                ID <span x-text="key"></span>
                            </h5>
                            <p class="card-text" x-text="value"></p>
                        </div>
                    </div>
                </template>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                <button @click="sessionStorage.clear()" type="submit" class="btn btn-warning">Draft</button>
            </div>
        </div>
    </form>
</div> 

<div class="modal fade" id="publishModal" tabindex="-1">
    <form class="modal-dialog" method="POST">
        <input type="hidden" name="action" value="publish">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Publish these items?</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body d-flex flex-column gap-2">
                <template x-for="[key, value] in Object.entries(map)">
                    <div class="card">
                        <input type="hidden" name="ids[]" x-bind:value="key" />
                        <div class="card-body d-flex justify-content-between">
                            <h5 class="card-title">
                                ID <span x-text="key"></span>
                            </h5>
                            <p class="card-text" x-text="value"></p>
                        </div>
                    </div>
                </template>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                <button @click="sessionStorage.clear()" type="submit" class="btn btn-warning">Publish</button>
            </div>
        </div>
    </form>
</div> 
