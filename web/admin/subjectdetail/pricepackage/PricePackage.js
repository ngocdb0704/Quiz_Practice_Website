class CRUDState {
    constructor() {
        this.modalTitle = 'Create Package';
        this.mode = 'add';
        this.form = {
            name: '',
            duration: 1,
            listPrice: 1000,
            salePercentage: 0
        }
    }
    
    editPackage(e) {
        this.modalTitle = 'Edit Package';
        this.mode = 'edit';
        this.form = { ...e.target.dataset };
        this.calculateSalePrice();
        new bootstrap.Modal(document.getElementById('crudModal')).show();
    }
    
    addPackage() {
        this.modalTitle = 'Create Package';
        this.mode = 'add';
        this.form = {
            name: '',
            duration: 1,
            listPrice: 1000,
            salePercentage: 0
        };
        new bootstrap.Modal(document.getElementById('crudModal')).show();
    }
}

document.addEventListener('alpine:init', () => {
    Alpine.data('crudState', () => new CRUDState());
});