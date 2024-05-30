const dateRangeToggler = document.querySelector('#dateRange');
const startDatePicker = document.querySelector('#startDate');
const endDatePicker = document.querySelector('#endDate');

class DateController {
    constructor(toggler, start, end) {
        this.toggler = toggler;
        this.start = start;
        this.end = end;
        
        const today = new Date();
        
        if (!start.value) {
            this.start.defaultValue = this.dateToString(today);
        }
        
        if (!end.value) {
            this.end.defaultValue = this.dateToString(today);
        }
        
        this.start.addEventListener('change', () => this.constraintInputs());
        this.end.addEventListener('change', () => this.constraintInputs());
        this.toggler.addEventListener('change', () => this.showHideInputs());
        
        this.constraintInputs();
        this.showHideInputs();
    }
    
    dateToString(date) {
        const year = date.getFullYear();
        const month = (date.getMonth() + 1).toString().padStart(2, '0');
        const day = date.getDate().toString().padStart(2, '0');
        return `${year}-${month}-${day}`;
    }
    
    showHideInputs() {
        if (this.toggler.value === 'any') {
            this.start.disabled = true;
            this.end.disabled = true;
            this.start.hidden = true;
            this.end.hidden = true;
        } else {
            this.start.disabled = false;
            this.end.disabled = false;
            this.start.hidden = false;
            this.end.hidden = false;
        }
    }
    
    constraintInputs() {
        let startDate = new Date(this.start.value);
        let endDate = new Date(this.end.value);
        
        if (startDate.getTime() > endDate.getTime()) {
            [startDate, endDate] = [endDate, startDate];
        }
        
        this.start.max = this.dateToString(endDate);
        this.end.min = this.dateToString(startDate);
        
        this.start.value = this.end.min;
        this.end.value = this.start.max;
    }
}

const controller = new DateController(dateRangeToggler, startDatePicker, endDatePicker);