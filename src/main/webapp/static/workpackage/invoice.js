$(document).ready(function () {
    $('#example').DataTable({
        "ajax": 'http://localhost:3000/invoice'
    });
});