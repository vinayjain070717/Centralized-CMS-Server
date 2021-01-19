<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
        <c:if test="${empty sessionScope.admin}">
            <c:redirect url="index.html" />
        </c:if>
        <c:import url='header.html' />

        <!-- Sidebar -->
        <!-- End of Sidebar -->
        <!-- Content Wrapper -->
        <div id="content-wrapper" class="d-flex flex-column">

            <!-- Main Content -->
            <div id="content">
                <!-- Topbar -->
                <!--         <h1><c:out value="${sessionScope.admin}"/></h1>
        <h1>${admin}</h1>
        <h1>${sessionScope.admin}</h1>-->
                <nav class="navbar navbar-expand navbar-light bg-white topbar mb-4">
                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-lg-6"></div>
                            <div class="col-lg-6"></div>
                        </div>
                        <div class="row">
                            <div class="col-lg-8"></div>
                            <div class="col-lg-4"></div>
                        </div>
                        <div class="row">
                            <div class="col-lg-12">
                                <table>
                                    <tr>
                                        <td><i class='fas fa-user'></i>&nbsp;</td>
                                        <td><b>${admin}</b></td>
                                    </tr>
                                </table>
                            </div>
                        </div>
                    </div>
                </nav>

                <div class="container-fluid">
                    <center>
                        <h3>Active Connections</h3>
                        <table class="table table-hover" id="memberDroneTable">
                            <thead>
                                <tr>
                                    <th scope="col">S.No.</th>
                                    <th scope="col">User Name</th>
                                    <th scope="col">Drone Name</th>
                                    <th scope="col">Drone Id</th>

                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="activeDroneRecords" items="${applicationScope.webSocketDrones}" varStatus="i">
                                    <c:forEach var="activeUserRecords" items="${applicationScope.webSocketUsers}">
                                        <c:if test="${activeDroneRecords.isConnected==true}">
                                            <c:if test="${fn:containsIgnoreCase(activeUserRecords.droneId, activeDroneRecords.id)}">
                                                <tr onclick="onRowClick('${activeDroneRecords.droneStatus.distanceTravelled}','${activeDroneRecords.droneStatus.longitude}','${activeDroneRecords.droneStatus.latitude}','${activeDroneRecords.droneStatus.speed}','${activeDroneRecords.droneStatus.altitude}','${activeDroneRecords.droneStatus.timeElapsed}','${activeDroneRecords.droneStatus.remainingFlightTime}','${activeDroneRecords.droneStatus.airSpeed}')">
                                                    <td>${i.count}</td>
                                                    <td>${activeUserRecords.username}</td>
                                                    <td>${activeDroneRecords.name}</td>
                                                    <td>${activeDroneRecords.id}</td>
                                                </tr>
                                            </c:if>
                                        </c:if>
                                    </c:forEach>
                                </c:forEach>
                            </tbody>
                        </table>

                    </center>
                </div>
                <!-- /.container-fluid -->

            </div>
            <!-- End of Main Content -->

            <!-- Footer -->
            <footer class="sticky-footer bg-white">
                <div class="container my-auto">
                    <div class="copyright text-center my-auto">
                        <span>Copyright &copy; Your Website 2019</span>
                    </div>
                </div>
            </footer>

            <a class="scroll-to-top rounded" href="#page-top">
                <i class="fas fa-angle-up"></i>
            </a>


            <div class="modal fade" id="statusModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <label></label>
                        <h5 class="modal-title" id="exampleModalLabel">
                            <center><b>Drone Status</b></center>
                        </h5>
                        <label></label>
                        <center>

                            <div class="form-group row">

                                <div class="col-sm-5 mb-3 mb-sm-0" style="padding-left:30px">
                                    <b>Distance Travelled</b><input type="text" class="form-control form-control-user" id="distanceTravelled" readonly>
                                </div>
                                <div class="col-sm-5 mb-3 mb-sm-0">
                                    <b>Longitude</b><input type="text" class="form-control form-control-user" id="longitude" readonly>

                                </div>
                            </div>


                            <div class="form-group row">
                                <div class="col-sm-5 mb-3 mb-sm-0" style="padding-left:30px">
                                    <b>Latitude</b><input type="text" class="form-control form-control-user" id="latitude" readonly>

                                </div>
                                <div class="col-sm-5 mb-3 mb-sm-0">
                                    <b>Speed</b><input type="text" class="form-control form-control-user" id="speed" readonly>

                                </div>
                            </div>

                            <div class="form-group row">
                                <div class="col-sm-5 mb-3 mb-sm-0" style="padding-left:30px">
                                    <b>Altitude</b><input type="text" class="form-control form-control-user" id="altitude" readonly>

                                </div>
                                <div class="col-sm-5 mb-3 mb-sm-0">
                                    <b>Time Elapsed</b><input type="text" class="form-control form-control-user" id="timeElapsed" readonly>

                                </div>
                            </div>
                            <div class="form-group row">
                                <div class="col-sm-5 mb-3 mb-sm-0" style="padding-left:30px">
                                    <b>Remaining Flight Time</b><input type="text" class="form-control form-control-user" id="remainingFlightTime" readonly>

                                </div>
                                <div class="col-sm-5 mb-3 mb-sm-0">
                                    <b>Air Speed</b><input type="text" class="form-control form-control-user" id="airSpeed" readonly>

                                </div>
                            </div>
                        </center>
                        <label id="commonErrorLabel"></label>
                        <center>
                            <div class="col-sm-5 mb-3 mb-sm-0">


                                <!--input type='submit' class="btn btn-secondary" data-dismiss="modal" value="Cancel" onclick="edit()"-->
                                <input type='submit' class="btn btn-secondary" data-dismiss="modal" value="OK">
                            </div>
                            <label></label>
                        </center>
                    </div>
                </div>
            </div>

            <script>
                function onRowClick(distanceTravelled, longitude, latitude, speed, altitude, timeElapsed, remainingFlightTime, airSpeed) {
                    console.log("on click function invoked");
                    $('#statusModal').modal();
                    $("#distanceTravelled").val(distanceTravelled);
                    $("#longitude").val(longitude);
                    $("#latitude").val(latitude);
                    $("#speed").val(speed);
                    $("#altitude").val(altitude);
                    $("#timeElapsed").val(timeElapsed);
                    $("#remainingFlightTime").val(remainingFlightTime);
                    $("#airSpeed").val(airSpeed);
                }
            </script>

<script type="text/javascript" src="js/logout.js"></script>

            <script src="vendor/jquery/jquery.min.js"></script>
            <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

            <!-- Core plugin JavaScript-->
            <script src="vendor/jquery-easing/jquery.easing.min.js"></script>

            <!-- Custom scripts for all pages-->
            <script src="js/sb-admin-2.min.js"></script>

            <!-- Page level plugins -->
            <!-- <script src="vendor/chart.js/Chart.min.js"></script> -->

            <!-- Page level custom scripts -->
            <!--   <script src="js/demo/chart-area-demo.js"></script>
  <script src="js/demo/chart-pie-demo.js"></script>
 -->
            </body>


            </html>