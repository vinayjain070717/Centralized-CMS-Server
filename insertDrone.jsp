<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
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
            <!-- End of Topbar -->
            <!-- Begin Page Content -->
            <!-- Model Name	Model Number	Brand Name	Key	Flight Time	Load Capacity	Max Altitude	Speed -->
            <div class="container-fluid">
                <center>
                    <h3>Insert Drone</h3>

                    <div class="form-group">
                        <input type="text" class="custom-button-css" id="modelName" placeholder="Model Name">
                        <label class="error_label" id="modelNameError"></label>
                        <br>

                    </div>
                    <div class="form-group">
                        <input type="text" class="custom-button-css" id="modelNumber" placeholder="Model Number">
                        <label class="error_label" id="modelNumberError"></label>
                        <br>

                    </div>
                    <div class="form-group">
                        <input type="text" class="custom-button-css" id="brandName" placeholder="Brand Name">
                        <label class="error_label" id="brandNameError"></label>
                        <br>

                    </div>
                    <div class="form-group">
                        <input type="text" class="custom-button-css" id="droneKey" placeholder="Drone Key">
                        <label class="error_label" id="droneKeyError"></label>
                        <br>

                    </div>
                    <div class="form-group">
                        <input type="text" class="custom-button-css" id="flightTime" placeholder="Flight Time">
                        <label class="error_label" id="flightTimeError"></label>
                        <br>

                    </div>
                    <div class="form-group">
                        <input type="text" class="custom-button-css" id="loadCapacity" placeholder="Load Capacity">
                        <label class="error_label" id="loadCapacityError"></label>
                        <br>

                    </div>
                    <div class="form-group">
                        <input type="text" class="custom-button-css" id="speed" placeholder="Speed">
                        <label class="error_label" id="speedError"></label>
                        <br>

                    </div>
                    <div class="form-group">
                        <input type="text" class="custom-button-css" id="altitude" placeholder="Altitude">
                        <label class="error_label" id="altitudeError"></label>
                        <br>
                    </div>
                    <div class="form-group">
                        <label id="successLabel" class="success_label"></label>
                        <label id="errorLabel" class="error_label"></label>
                        <br>
                        <input type="submit" class="btn btn-primary" id="Insert" value="Insert" onclick="Insert()">

                    </div>
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

    <script>
        function Insert() {
            var modelName = $("#modelName").val();
            var modelNumber = $("#modelNumber").val();
            var brandName = $("#brandName").val();
            var droneKey = $("#droneKey").val();
            var flightTime = $("#flightTime").val();
            var loadCapacity = $("#loadCapacity").val();
            var speed = $("#speed").val();
            var altitude = $("#altitude").val();
            var errorFlag = false;
            console.log($("#modelName").val());
            if ($("#modelName").val().length > 0) {
                $("#modelNameError").text("");
            } else {
                $("#modelNameError").text("Invalid");
                errorFlag = true;
            }
            if ($("#modelNumber").val().length > 0) {
                $("#modelNumberError").text("");
            } else {
                $("#modelNumberError").text("Invalid");
                errorFlag = true;
            }
            if ($("#brandName").val().length > 0) {
                $("#brandNameError").text("");
            } else {
                $("#brandNameError").text("Invalid");
                errorFlag = true;
            }
            if ($("#droneKey").val().length > 0) {
                $("#droneKeyError").text("");
            } else {
                $("#droneKeyError").text("Invalid");
                errorFlag = true;
            }
            if ($("#flightTime").val().length > 0) {
                $("#flightTimeError").text("");
            } else {
                $("#flightTimeError").text("Invalid");
                errorFlag = true;
            }
            if ($("#loadCapacity").val().length > 0) {
                $("#loadCapacityError").text("");
            } else {
                $("#loadCapacityError").text("Invalid");
                errorFlag = true;
            }
            if ($("#speed").val().length > 0) {
                $("#speedError").text("");
            } else {
                $("#speedError").text("Invalid");
                errorFlag = true;
            }
            if ($("#altitude").val().length > 0) {
                $("#altitudeError").text("");
            } else {
                $("#altitudeError").text("Invalid");
                errorFlag = true;
            }
            if (!errorFlag) {
                var jsonString = {
                    "modelName": $("#modelName").val(),
                    "modelNumber": $("#modelNumber").val(),
                    "brand": $("#brandName").val(),
                    "droneKey": $("#droneKey").val(),
                    "flightTime": $("#flightTime").val(),
                    "loadCapacity": $("#loadCapacity").val(),
                    "speed": $("#speed").val(),
                    "altitude": $("#altitude").val()
                };
                $.ajax("/CMSDrone/cms/addDrone", {
                    method: "post",
                    data: JSON.stringify(jsonString),
                    contentType: "application/json",
                    success: function(res) {
                        if (res.success) {
                            $("#successLabel").text("Drone Inserted Successfully");
                            console.log(res);
                        } else if (res.isException) {
                            $("#errorLabel").text(res.exception);
                        }
                    },
                    error: function(res) {
                        $("#errorLabel").text(res.error);
                        console.log(res);
                    }

                });
            }
        }
    </script>
<script type="text/javascript" src="js/logout.js"></script>

    <!-- Bootstrap core JavaScript-->
    <script src="vendor/jquery/jquery.min.js"></script>
    <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

    <!-- Core plugin JavaScript-->
    <script src="vendor/jquery-easing/jquery.easing.min.js"></script>

    <!-- Custom scripts for all pages-->
    <script src="js/sb-admin-2.min.js"></script>
    <script type="text/javascript">
    </script>
    <link rel="stylesheet" href="css/myCssClasses.css">
    <!-- Page level plugins -->
    <!-- <script src="vendor/chart.js/Chart.min.js"></script> -->

    <!-- Page level custom scripts -->
    <!--   <script src="js/demo/chart-area-demo.js"></script>
  <script src="js/demo/chart-pie-demo.js"></script>
 -->
    </body>

    </html>