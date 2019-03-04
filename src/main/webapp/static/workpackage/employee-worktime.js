// register modal component
Vue.component('modal', {
    props: {
        employees: Array,
    },
    data() {
        return {
            assignedEmployees: [],
        }
    },
    template: '#modal-template',
    methods: {
        handleClick: function () {
            this.$emit('selected-items-event', this.$data.assignedEmployees);
            console.log("Assigned employees: " + JSON.stringify(this.$data.assignedEmployees));
        }
    }
});

var employeeWorktime = new Vue({
    el: '#employee-worktime',
    data: {
        showModal: false,
        employee: 'default',
        employees: []
    },
    created() {
        axios.get(' http://localhost:3000/employees')
            .then(response => {
                this.employees = response.data;
            });
    },
    methods: {
        receiveEmployee: function (employees) {
            console.log("Received employees:" + JSON.stringify(employees))
        }
    }
});
