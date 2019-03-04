<%@ page contentType="text/html; charset=ISO-8859-15" pageEncoding="ISO-8859-15"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page isELIgnored="false"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-15">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <script type="text/javascript" src="<c:url value='/static/bootstrap4/jquery-3.3.1.js' />"></script>
    <link href="<c:url value='/static/bootstrap4/bootstrap.css' />" rel="stylesheet" />
    <link href="<c:url value='/static/css/app.css' />" rel="stylesheet" />
    <title>Work Package</title>
</head>
<body>
<%@include file="authheader.jsp"%>
<main role="main" class="container-fluid">
    <div id="workpackage-form" class="row workpackage-form">
        <div class="col-7">
            <div class="card card-outline-secondary">
                <div class="card-header">
                    <h6>Arbeitspaket</h6>
                </div>
                <div class="card-body">
                    <form>
                        <div class="form-group row">
                            <div class="col">
                                <label for="wpName">Name des Arbeitspakets</label>
                                <input v-model="wpName" type="text" class="form-control" id="wpName"
                                       placeholder="Name des Arbeitspakets">
                            </div>
                        </div>
                        <div class="form-group row">
                            <div class="col">
                                <label for="projectName">Projectname</label>
                                <select v-model="projectName" id="projectName" class="form-control">
                                    <option v-for="project in projects" :value="project.value">{{project.text}}</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group row">
                            <div class="col">
                                <label for="offeredCost">Angebotener Preis</label>
                                <div class="input-group col pl-0">
                                    <div class="input-group-prepend">
                                        <div class="input-group-text">€</div>
                                    </div>
                                    <input v-model="offeredCost" type="text" class="form-control" id="offeredCost">
                                </div>
                            </div>
                            <div class="col">
                                <label for="calculatedCost">Fakturierte Umsatze</label>
                                <div class="input-group col pl-0">
                                    <div class="input-group-prepend">
                                        <div class="input-group-text">€</div>
                                    </div>
                                    <input v-model="calculatedCost" type="text" class="form-control"
                                           id="calculatedCost">
                                </div>
                            </div>
                            <div class="col">
                                <label for="totalCost">Geplanter Aufwand</label>
                                <div class="input-group col pl-0">
                                    <div class="input-group-prepend">
                                        <div class="input-group-text">€</div>
                                    </div>
                                    <input v-model="totalCost" type="text" class="form-control" id="totalCost">
                                </div>
                            </div>
                            <div class="col">
                                <label for="effectiveCost">Ist-Aufwand</label>
                                <div class="input-group col pl-0">
                                    <div class="input-group-prepend">
                                        <div class="input-group-text">€</div>
                                    </div>
                                    <input v-model="effectiveCost" type="text" class="form-control" id="effectiveCost">
                                </div>
                            </div>
                        </div>
                        <div class="form-group row">
                            <div class="col-3">
                                <label for="paymentPercentage">Fakturiert</label>
                                <div class="input-group col pl-0">
                                    <div class="input-group-prepend">
                                        <div class="input-group-text">%</div>
                                    </div>
                                    <input v-model="paymentPercentage" type="text" class="form-control"
                                           id="paymentPercentage">
                                </div>
                            </div>
                            <div class="col-3">
                                <label for="status">Status</label>
                                <select v-model="status" :class="statusSelectClass" class="form-control" id="status">
                                    <option v-for="status in statuses" :value="status.value">{{status.text}}</option>
                                </select>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <div class="col-5">
            <div class="card">
                <div class="card-header">
                    <h6>Mitarbeiter statistics</h6>
                </div>
                <div class="card-body">
                    <canvas id="canvas"></canvas>
                </div>
            </div>
        </div>
    </div>
    <div class="row pt-3">
        <div class="col">
            <div class="card">
                <div class="card-header">
                    <h6>Faktura</h6>
                    <button type="button" class="btn btn-info float-right"><span class="pr-2">Neue Faktura</span><i
                            class="fas fa-plus"></i></button>
                </div>
                <div class="card-body">
                    <table id="example" class="table table-striped table-bordered">
                        <thead>
                        <tr>
                            <th>Project</th>
                            <th>Arbeitspakete</th>
                            <th>Abgerechnet</th>
                            <th>Datum</th>
                            <th>Betrag</th>
                            <th>Bermerkung</th>
                            <th>Fakturiert in %</th>
                            <th>Zustendig</th>
                            <th>Erstelit von</th>
                        </tr>
                        </thead>
                    </table>
                </div>
            </div>
        </div>
    </div>
    <div id="employee-worktime" class="row pt-3 pb-3">
        <div class="col">
            <div class="card">
                <div class="card-header">
                    <div class="row">
                        <div class="col">
                            <span>Mitarbeiter aktualisieren</span>
                        </div>
                    </div>
                </div>
                <div class="card-body  p-0">
                    <modal :employees="employees" @selected-items-event="receiveEmployee"></modal>
                    <div class="row">
                        <div class="col">
                            <table class="table table-bordered">
                                <thead>
                                <tr>
                                    <th scope="col">Jahr</th>
                                    <th scope="col">Mitarbeiter</th>
                                    <th scope="col">Plantage</th>
                                    <th scope="col"></th>
                                    <th scope="col">Januar</th>
                                    <th scope="col">Februar</th>
                                    <th scope="col">Marz</th>
                                    <th scope="col">April</th>
                                    <th scope="col">Mai</th>
                                    <th scope="col">Juni</th>
                                    <th scope="col">Juli</th>
                                    <th scope="col">August</th>
                                    <th scope="col">September</th>
                                    <th scope="col">October</th>
                                    <th scope="col">November</th>
                                    <th scope="col">Dezember</th>
                                    <th scope="col"></th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <td>2017</td>
                                    <td>Zagoret Sergiu</td>
                                    <td>
                                        <input class="form-control" type="text" size="4">
                                    </td>
                                    <td>
                                        Verfugbar
                                    </td>
                                    <td align="center">3.3</td>
                                    <td align="center">3.3</td>
                                    <td align="center">3.3</td>
                                    <td align="center">3.3</td>
                                    <td align="center">3.3</td>
                                    <td align="center">3.3</td>
                                    <td align="center">3.3</td>
                                    <td align="center">3.3</td>
                                    <td align="center">3.3</td>
                                    <td align="center">3.3</td>
                                    <td align="center">3.3</td>
                                    <td align="center">3.3</td>
                                    <td></td>

                                </tr>
                                <tr>
                                    <td></td>
                                    <td></td>
                                    <td>

                                    </td>
                                    <td>
                                        Geplant
                                    </td>
                                    <td>
                                        <input class="form-control" type="text" size="4">
                                    </td>
                                    <td>
                                        <input class="form-control" type="text" size="4">
                                    </td>
                                    <td>
                                        <input class="form-control" type="text" size="4">
                                    </td>
                                    <td>
                                        <input class="form-control" type="text" size="4">
                                    </td>
                                    <td>
                                        <input class="form-control" type="text" size="4">
                                    </td>
                                    <td>
                                        <input class="form-control" type="text" size="4">
                                    </td>
                                    <td>
                                        <input class="form-control" type="text" size="4">
                                    </td>
                                    <td>
                                        <input class="form-control" type="text" size="4">
                                    </td>
                                    <td>
                                        <input class="form-control" type="text" size="4">
                                    </td>
                                    <td>
                                        <input class="form-control" type="text" size="4">
                                    </td>
                                    <td>
                                        <input class="form-control" type="text" size="4">
                                    </td>
                                    <td>
                                        <input class="form-control" type="text" size="4">
                                    </td>
                                    <td>
                                    </td>
                                </tr>
                                <tr>
                                    <td></td>
                                    <td></td>
                                    <td>

                                    </td>
                                    <td>
                                        Zeiterfassung
                                    </td>
                                    <td>
                                        <input class="form-control" type="text" size="4">
                                    </td>
                                    <td>
                                        <input class="form-control" type="text" size="4">
                                    </td>
                                    <td>
                                        <input class="form-control" type="text" size="4">
                                    </td>
                                    <td>
                                        <input class="form-control" type="text" size="4">
                                    </td>
                                    <td>
                                        <input class="form-control" type="text" size="4">
                                    </td>
                                    <td>
                                        <input class="form-control" type="text" size="4">
                                    </td>
                                    <td>
                                        <input class="form-control" type="text" size="4">
                                    </td>
                                    <td>
                                        <input class="form-control" type="text" size="4">
                                    </td>
                                    <td>
                                        <input class="form-control" type="text" size="4">
                                    </td>
                                    <td>
                                        <input class="form-control" type="text" size="4">
                                    </td>
                                    <td>
                                        <input class="form-control" type="text" size="4">
                                    </td>
                                    <td>
                                        <input class="form-control" type="text" size="4">
                                    </td>
                                    <td>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div><div class="row">
                    <div class="col">
                        <table class="table table-bordered">
                            <thead>
                            <tr>
                                <th scope="col">Jahr</th>
                                <th scope="col">Mitarbeiter</th>
                                <th scope="col">Plantage</th>
                                <th scope="col"></th>
                                <th scope="col">Januar</th>
                                <th scope="col">Februar</th>
                                <th scope="col">Marz</th>
                                <th scope="col">April</th>
                                <th scope="col">Mai</th>
                                <th scope="col">Juni</th>
                                <th scope="col">Juli</th>
                                <th scope="col">August</th>
                                <th scope="col">September</th>
                                <th scope="col">October</th>
                                <th scope="col">November</th>
                                <th scope="col">Dezember</th>
                                <th scope="col"></th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr style="background-color: rgba(0,0,0,.05);">
                                <td>2017</td>
                                <td>Zagoret Sergiu</td>
                                <td>
                                    <input class="form-control" type="text" size="4">
                                </td>
                                <td>
                                    Verfugbar
                                </td>
                                <td align="center">3.3</td>
                                <td align="center">3.3</td>
                                <td align="center">3.3</td>
                                <td align="center">3.3</td>
                                <td align="center">3.3</td>
                                <td align="center">3.3</td>
                                <td align="center">3.3</td>
                                <td align="center">3.3</td>
                                <td align="center">3.3</td>
                                <td align="center">3.3</td>
                                <td align="center">3.3</td>
                                <td align="center">3.3</td>
                                <td></td>

                            </tr>
                            <tr>
                                <td></td>
                                <td></td>
                                <td>

                                </td>
                                <td>
                                    Geplant
                                </td>
                                <td>
                                    <input class="form-control" type="text" size="4">
                                </td>
                                <td>
                                    <input class="form-control" type="text" size="4">
                                </td>
                                <td>
                                    <input class="form-control" type="text" size="4">
                                </td>
                                <td>
                                    <input class="form-control" type="text" size="4">
                                </td>
                                <td>
                                    <input class="form-control" type="text" size="4">
                                </td>
                                <td>
                                    <input class="form-control" type="text" size="4">
                                </td>
                                <td>
                                    <input class="form-control" type="text" size="4">
                                </td>
                                <td>
                                    <input class="form-control" type="text" size="4">
                                </td>
                                <td>
                                    <input class="form-control" type="text" size="4">
                                </td>
                                <td>
                                    <input class="form-control" type="text" size="4">
                                </td>
                                <td>
                                    <input class="form-control" type="text" size="4">
                                </td>
                                <td>
                                    <input class="form-control" type="text" size="4">
                                </td>
                                <td>
                                </td>
                            </tr>
                            <tr style="background-color: rgba(0,0,0,.05);">
                                <td></td>
                                <td></td>
                                <td>

                                </td>
                                <td>
                                    Zeiterfassung
                                </td>
                                <td>
                                    <input class="form-control" type="text" size="4">
                                </td>
                                <td>
                                    <input class="form-control" type="text" size="4">
                                </td>
                                <td>
                                    <input class="form-control" type="text" size="4">
                                </td>
                                <td>
                                    <input class="form-control" type="text" size="4">
                                </td>
                                <td>
                                    <input class="form-control" type="text" size="4">
                                </td>
                                <td>
                                    <input class="form-control" type="text" size="4">
                                </td>
                                <td>
                                    <input class="form-control" type="text" size="4">
                                </td>
                                <td>
                                    <input class="form-control" type="text" size="4">
                                </td>
                                <td>
                                    <input class="form-control" type="text" size="4">
                                </td>
                                <td>
                                    <input class="form-control" type="text" size="4">
                                </td>
                                <td>
                                    <input class="form-control" type="text" size="4">
                                </td>
                                <td>
                                    <input class="form-control" type="text" size="4">
                                </td>
                                <td>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
                    <div class="jumbotron jumbotron-fluid m-0">
                        <div class="container">
                            <h2 class="display-4">Employees Time Sheet</h2>
                            <p class="lead">This is a modified jumbotron that occupies the entire horizontal space
                                of its parent.</p>

                        </div>
                    </div>
                </div>
                <div class="card-footer">
                    <div class="row justify-content-end">
                        <div class="col-3">
                            <div class="input-group">
                                <select v-model="employee" class="custom-select" id="inputGroupSelect0211">
                                    <option value="default">Please select one...</option>
                                    <option v-for="employee in employees" :value="employee.id">{{employee.name}}
                                    </option>
                                </select>
                                <div class="input-group-append">
                                    <button @click="receiveEmployee([employee])" type="button" class="btn btn-info"
                                            :disabled="employee === 'default'">Hinzufügen
                                    </button>
                                </div>
                                <div class="btn-group input-group-append">
                                    <button type="button" class="btn btn-info dropdown-toggle border-left"
                                            data-toggle="dropdown"
                                            aria-haspopup="true" aria-expanded="false">
                                    </button>
                                    <div class="dropdown-menu dropdown-menu-right">
                                        <a class="dropdown-item" href="#"
                                           data-toggle="modal"
                                           data-target="#assigned-employees">Select multiple</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>
<footer class="footer">
    <div class="container float-left">
        <span class="text-muted">Ecolibro Projektverwaltung</span>
    </div>
</footer>
<!-- /.container -->
<!-- template for the modal component -->
<script type="text/x-template" id="modal-template">
    <!-- MODAL -->
    <div class="modal fade" id="assigned-employees" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
         aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Assigned Employees</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form>
                        <select id="assignedEmployees1" v-model="assignedEmployees" class="form-control" size="8"
                                multiple>
                            <option v-for="employee in employees" :value="employee.id" :disabled="employee.disabled">
                                {{employee.name}}
                            </option>
                        </select>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    <button @click="handleClick" type="button" class="btn btn-primary" data-dismiss="modal">Save
                        changes
                    </button>
                </div>
            </div>
        </div>
    </div>
</script>



<script type="text/javascript" src="<c:url value='/static/bootstrap4/popper.js' />"></script>
<script type="text/javascript" src="<c:url value='/static/bootstrap4/bootstrap.js' />"></script>

<!-- Vuejs library and dependencies -->
<script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
<script src="https://unpkg.com/vuex@3.0.1/dist/vuex.js"></script>
<script src="https://cdn.jsdelivr.net/npm/axios@0.12.0/dist/axios.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/lodash@4.13.1/lodash.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.7.2/Chart.js"></script>
<script src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>
<script src="https://cdn.datatables.net/1.10.19/js/dataTables.bootstrap4.min.js"></script>


<script type="text/javascript" src="<c:url value='/static/workpackage/workpackage.js' />"></script>
<script type="text/javascript" src="<c:url value='/static/workpackage/workpackage-form.js' />"></script>
<script type="text/javascript" src="<c:url value='/static/workpackage/invoice.js' />"></script>
<script type="text/javascript" src="<c:url value='/static/workpackage/statistics.js' />"></script>
<script type="text/javascript" src="<c:url value='/static/workpackage/employee-worktime.js' />"></script>
</body>
</html>
