class State {
    constructor() {
        this.map = {};
        this.allSelected = false;
    }

    init() {
        this.allSelected = this.isAllSelected();
    }

    get length() {
        return Object.keys(this.map).length;
    }

    isAllSelected() {
        const tblBody = this.$refs.tableBody;

        if (tblBody) {
            for (const row of tblBody.querySelectorAll("[data-id]")) {
                if (!this.map.hasOwnProperty(row.dataset.id)) {
                    return false;
                }
            }
        } else {
            return false;
        }

        return true;
    }

    toggle(id, title) {
        if (this.map[id]) {
            delete this.map[id];
        } else {
            this.map[id] = title ?? '';
        }

        this.allSelected = this.isAllSelected();
    }

    selectAll() {
        const tblBody = this.$refs.tableBody;
        if (tblBody) {
            const rows = tblBody.querySelectorAll("[data-id]");
            for (const row of rows) {
                this.map[row.dataset.id] = row.dataset.title;
            }
        }

        this.allSelected = true;
    }

    reset() {
        for (const key of Object.keys(this.map)) {
            delete this.map[key];
        }

        this.allSelected = false;
    }

    deselectAll() {
        const tblBody = this.$refs.tableBody;
        if (tblBody) {
            const rows = tblBody.querySelectorAll("[data-id]");
            for (const row of rows) {
                if (this.map[row.dataset.id]) {
                    delete this.map[row.dataset.id];
                }
            }
        }

        this.allSelected = false;
    }
}

document.addEventListener('alpine:init', () => {
    Alpine.data('state', () => new State());
});