class CRUDState {
    constructor() {
        this.modalTitle = 'Create Package';
        this.mode = 'add';
        this.form = {
            name: '',
            duration: 1,
            listPrice: 1000,
            salePercentage: 0,
            salePrice: 1000
        }
    }
    
    calculateSalePrice() {
        if (this.form.listPrice && this.form.salePercentage) {
            const listPrice = Math.max(1000, this.form.listPrice);
            
            this.form.salePercentage = Math.max(0, Math.min(100, this.form.salePercentage));
            const applied = listPrice - (listPrice * this.form.salePercentage / 100);
            this.form.salePrice = Math.floor(applied);
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
            salePercentage: 0,
            salePrice: 1000
        };
        new bootstrap.Modal(document.getElementById('crudModal')).show();
    }
}

document.addEventListener('alpine:init', () => {
    Alpine.data('crudState', () => new CRUDState());
});