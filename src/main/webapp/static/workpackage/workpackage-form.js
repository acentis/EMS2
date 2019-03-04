var wpForm = new Vue({
    el: '#workpackage-form',
    data: {
        wpName: '',
        projectName: '',
        projects: [],
        offeredCost: 0,
        calculatedCost: 0,
        paymentPercentage: 0,
        totalCost: 0,
        effectiveCost: 0,
        status: '',
        statuses: [],
        assignedEmployees: [],
        employees: []
    },
    mounted() {
        // use "json-server --watch response.json  --static ./" to start API
        axios.get(' http://localhost:3000/wpForm')
            .then(response => (Object.assign(this.$data, response.data)));
    },
    computed: {
        statusSelectClass: function () {
            return {
                'border-success': this.$data.status === 's1',
                'border-danger': this.$data.status === 's2',
                'border-warning': this.$data.status === 's3',
                'border-primary': this.$data.status === 's4'

            }
        }
    }
});
