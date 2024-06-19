class State {
    constructor() {
        this.map = {};
        this.filteredMaps = {
            publishable: {},
            deletable: {},
            publishableLength: 0,
            deletableLength: 0
        };
        this.allSelected = false;
    }

    init() {
        this.allSelected = this.isAllSelected();
        this.$watch('map', newMap => {
            const publishable = Object.keys(newMap)
                    .filter(k => newMap[k].valid)
                    .reduce((obj, k) => {
                        obj[k] = newMap[k];
                        return obj;
                    }, {});
                    
                    
            const deletable = Object.keys(newMap)
                    .filter(k => newMap[k].attempts === 0)
                    .reduce((obj, k) => {
                        obj[k] = newMap[k];
                        return obj;
                    }, {});
                    
                    
            this.filteredMaps.publishable = publishable;
            this.filteredMaps.deletable = deletable;
            
            this.filteredMaps.publishableLength = Object.keys(publishable).length;
            this.filteredMaps.deletableLength = Object.keys(deletable).length;
        });
    }

    get length() {
        return Object.keys(this.map).length;
    }

    isAllSelected() {
        const tblBody = this.$refs.tableBody;

        if (tblBody) {
            for (const checkbox of tblBody.querySelectorAll("input[data-id][type='checkbox']:not([disabled])")) {
                if (!this.map.hasOwnProperty(checkbox.dataset.id)) {
                    return false;
                }
            }
        } else {
            return false;
        }

        return true;
    }

    toggle(id, title, valid, attempts) {
        if (this.map[id]) {
            delete this.map[id];
        } else {
            this.map[id] = {title, valid, attempts};
        }

        this.allSelected = this.isAllSelected();
    }

    selectAll() {
        const tblBody = this.$refs.tableBody;
        if (tblBody) {
            const checkboxes = tblBody.querySelectorAll("input[data-id][type='checkbox']:not([disabled])");
            for (const checkbox of checkboxes) {
                const {title, valid, id, attempts} = checkbox.dataset;
                this.map[id] = {title, valid: valid === "true", attempts: parseInt(attempts)};
            }
        }

        this.allSelected = this.isAllSelected();
    }

    reset() {
        for (const key of Object.keys(this.map)) {
            delete this.map[key];
        }

        this.allSelected = this.isAllSelected();
    }

    deselectAll() {
        const tblBody = this.$refs.tableBody;
        if (tblBody) {
            const checkboxes = tblBody.querySelectorAll("input[data-id][type='checkbox']:not([disabled])");
            for (const checkbox of checkboxes) {
                if (this.map[checkbox.dataset.id]) {
                    delete this.map[checkbox.dataset.id];
                }
            }
        }

        this.allSelected = this.isAllSelected();
    }
}

document.addEventListener('alpine:init', () => {
    Alpine.data('state', () => new State());
});