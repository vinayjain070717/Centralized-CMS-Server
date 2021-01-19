<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
    <c:if test="${empty sessionScope.admin}">
        <c:redirect url="index.html" />
    </c:if>
    <c:import url='header.html' />
<!--     <c:if test="${empty sessionScope.hello}">
        <h1><c:out value="empty"/></h1>
    </c:if>
 -->    <!-- Sidebar -->
    <!-- End of Sidebar -->
    <!-- Content Wrapper -->
    <div id="content-wrapper" class="d-flex flex-column">

        <!-- Main Content -->
        <div id="content">
            <!-- Topbar -->
            <!--         <h1><c:out value="${sessionScope.admin}"/></h1>
        <h1>${admin}</h1>
        <h1>${sessionScope.admin}</h1>-->
            <nav class="navbar navbar-expand navbar-light bg-white topbar">
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



            <!-- End of Topbar -->
            <!-- Begin Page Content -->
            <div class="container-fluid">
                <center>
                    <h3>USER INFORMATION</h3>
                    <table class="table table-hover" id="memberTable">
                        <thead>
                            <tr>
                                <th scope="col">S.No.</th>
                                <th scope="col">Username</th>
                                <th scope="col">Status</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="activeUserRecords" items="${applicationScope.webSocketUsers}" varStatus="i">
                                <tr class="table-light">
                                    <td>${i.count}</td>
                                    <td>${activeUserRecords.username}</td>
                                    <td>
                                    <c:if test="${!empty activeUserRecords.session}">
                                          <span class="successDot"></span>
                                    </c:if>
                                    <c:if test="${empty activeUserRecords.session}">
                                          <span class="errorDot"></span>
                                    </c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                    <br>
                    <br>
                    <h3> DRONE INFORMATION</h3>
                    <table class="table table-hover" id="droneTable">
                        <thead>
                            <tr>
                                <th scope="col">S.NO.</th>
                                <th scope="col">Drone Id</th>
                                <th scope="col">Model Name</th>
                                <th scope="col">Status</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="activeDroneRecords" items="${applicationScope.webSocketDrones}" varStatus="i">
                                <tr class="table-light">
                                    <td>${i.count}</td>
                                    <td>${activeDroneRecords.id}</td>
                                    <td>${activeDroneRecords.name}</td>
                                    <td>
                                    <c:if test="${!empty activeDroneRecords.session}">
                                          <span class="successDot"></span>
                                    </c:if>
                                    <c:if test="${empty activeDroneRecords.session}">
                                          <span class="errorDot"></span>
                                    </c:if>
                                    </td>
                                </tr>
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
                    <span>Copyright &copy; CMS 2020</span>
                </div>
            </div>
        </footer>
        <!-- End of Footer -->

    </div>
    <!-- End of Content Wrapper -->

    </div>
    <!-- End of Page Wrapper -->

    <!-- Scroll to Top Button-->
    <a class="scroll-to-top rounded" href="#page-top">
        <i class="fas fa-angle-up"></i>
    </a>
    <!--edit drone starts  -->
    <div class="modal fade" id="editModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <br>
                <h5 class="modal-title" id="exampleModalLabel">
                    <center>Edit Drone</center>
                </h5>
                <br>
                <center>

                    <div class="row mx-md-n5">

                        <div class="col" style="padding-left:80px">
                            <input type="text" class=" w-100 form-control form-control-user" id="editDroneModelName" placeholder="Model Name">
                            <label class="error_label" id="editDroneModelNameError"></label>
                        </div>
                        <div class="col" style="padding-right:90px">
                            <input type="text" class="w-100 form-control form-control-user" id="editDroneModelNumber" placeholder="Model Number">
                            <label class="error_label" id="editDroneModelNumberError"></label>
                        </div>
                    </div>


                    <div class="row mx-md-n5">
                        <div class="col" style="padding-left:80px">
                            <input type="text" class="w-100 form-control form-control-user" id="editDroneBrandName" placeholder="Brand Name">
                            <label class="error_label" id="editDroneBrandNameError"></label>
                        </div>
                        <div class="col" style="padding-right:90px">
                            <input type="text" class="w-100 form-control form-control-user" id="editDroneKey" placeholder="Drone Key" readonly>
                            <label class="error_label" id="editDroneKeyError"></label>
                        </div>
                    </div>

                    <div class="row mx-md-n5">
                        <div class="col" style="padding-left:80px">
                            <input type="text" class="w-100 form-control form-control-user" id="editDroneFlightTime" placeholder="Flight Time (in min)">
                            <label class="error_label" id="editDroneFlightTimeError"></label>
                        </div>
                        <div class="col" style="padding-right:90px">
                            <input type="text" class="w-100 form-control form-control-user" id="editDroneLoadCapacity" placeholder="Load Capacity (in kgs)">
                            <label class="error_label" id="editDroneLoadCapacityError"></label>
                        </div>
                    </div>
                    <div class="row mx-md-n5">
                        <div class="col" style="padding-left:80px">
                            <input type="text" class="w-100 form-control form-control-user" id="editDroneSpeed" placeholder="Speed (in mps)">
                            <label class="error_label" id="editDroneSpeedError"></label>
                        </div>
                        <div class="col" style="padding-right:90px">
                            <input type="text" class="w-100 form-control form-control-user" id="editDroneAltitude" placeholder="Altitude (in m)">
                            <label class="error_label" id="editDroneAltitudeError"></label>
                        </div>
                    </div>
                </center>
                <label id="commonErrorLabel"></label>
                <center>
                    <div class="row mx-md-n5">
                        <div class="col" style="padding-left:80px">
                            <input type='submit' class="w-50 btn btn-success" value="Update" onclick="Update()">
                        </div>
                        <div class="col" style="padding-right:90px">
                            <input type='submit' class="w-50 btn btn-secondary" data-dismiss="modal" value="Cancel">
                        </div>
                    </div>
                </center>
                <br>
            </div>
        </div>
    </div>
    <!--edit drone ends-->


    <!--Delete drone starts-->
    <div id="deleteModal" class="modal fade">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">Delete Drone</h4>
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                </div>
                <div class="modal-body">
                    <p>Are you sure you want to delete drone with Key:</p>
                    <p id="droneDeleteKey"></p>

                    <p class="text-warning"><small>This action cannot be undone.</small></p>
                </div>
                <div class="modal-footer">
                    <input type="button" class="btn btn-default" data-dismiss="modal" value="Cancel">
                    <input type="button" class="btn btn-danger" value="Delete" onclick="deleteDrone()">
                </div>
            </div>
        </div>
    </div>
    <!--Delete drone ends-->


<script type="text/javascript" src="js/logout.js"></script>
    <!-- Bootstrap core JavaScript-->
    <script src="vendor/jquery/jquery.min.js"></script>
    <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

    <!-- Core plugin JavaScript-->
    <script src="vendor/jquery-easing/jquery.easing.min.js"></script>

    <!-- Custom scripts for all pages-->
    <script src="js/sb-admin-2.min.js"></script>
    <style>
    .errorDot {
        height: 20px;
        width: 20px;
        background-color: #e00505ed;
        border-radius: 50%;
        display: inline-block;    
    }
    .successDot {
      height: 20px;
      width: 20px;
      background-color: green;
      border-radius: 50%;
      display: inline-block;
    }
    </style>

    </body>

    </html>