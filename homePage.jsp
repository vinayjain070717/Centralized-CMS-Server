<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
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
                                <th scope="col">First Name</th>
                                <th scope="col">Last Name</th>
                                <th scope="col">Username</th>
                                <th scope="col">Mobile Number</th>
                            </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                    <br>
                    <br>
                    <h3> DRONE INFORMATION</h3>
                    <table class="table table-hover" id="droneTable">
                        <thead>
                            <tr>
                                <th scope="col">S.NO.</th>
                                <th scope="col">Model Name</th>
                                <th scope="col">Model Number</th>
                                <th scope="col">Brand Name</th>
                                <th scope="col">Key</th>
                                <th scope="col">Flight Time</th>
                                <th scope="col">Load Capacity</th>
                                <th scope="col">Max Altitude</th>
                                <th scope="col">Speed</th>
                                <th scope="col">Edit/Delete</th>
                            </tr>
                        </thead>
                        <tbody>
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


    <script type="text/javascript">
        var droneIdKeyMap = {};

        window.onload = function() {
            $.get("/CMSDrone/cms/getMembers", function(data, status) {
                console.log(data);
                if (status) {
                    for (var i = 0; i < data.length; i++) {
                        // console.log(data[i].username);
                        var tableBody = $("#memberTable tbody");
                        var tableRow = "<tr class='table-light'><td>" + (i + 1) + "</td><td>" + data[i].firstName + "</td>";
                        tableRow = tableRow + "<td>" + data[i].lastName + "</td><td>" + data[i].username + "</td>";
                        tableRow = tableRow + "<td>" + data[i].mobileNumber + "</td></tr>";
                        tableBody.append(tableRow);
                    }
                } else {

                }
            });
            $.get("/CMSDrone/cms/getDrones", function(data, status) {
                console.log(data);
                if (status) {
                    for (var i = 0; i < data.length; i++) {
                        // console.log(data[i].username);
                        droneIdKeyMap[data[i].droneKey] = data[i].id;
                        var tableBody = $("#droneTable tbody");
                        var tableRow = "<tr class='table-light'><td>" + (i + 1) + "</td><td>" + data[i].modelName + "</td>";
                        tableRow = tableRow + "<td>" + data[i].modelNumber + "</td><td>" + data[i].brand + "</td>";
                        tableRow = tableRow + "<td>" + data[i].droneKey + "</td>";
                        tableRow = tableRow + "<td>" + data[i].flightTime + "</td><td>" + data[i].loadCapacity + "</td>";
                        tableRow = tableRow + "<td>" + data[i].altitude + "</td><td>" + data[i].speed + "</td>";
                        tableRow = tableRow + "<td><i style='padding: 0px 10px 0px 10px' class='fas fa-edit' data-toggle='modal' data-target='#editModal' onclick='edit()'></i><i style='padding: 0px 10px 0px 10px' class='fas fa-trash-alt' data-toggle='modal' data-target='#deleteModal' onclick='deleteRow()'></i></td></tr>";
                        tableBody.append(tableRow);
                    }
                } else {

                }
            });
        }

        function deleteRow() {
            $("#droneTable tr").click(function() {
                var column = $(this).children();
                $("#droneDeleteKey").text(column[4].innerHTML);
            });

        }

        function deleteDrone2() {
            console.log("delete drone invoked");
            var i = 0;
            var droneKey = "dk3"
            var t = document.getElementById("droneTable");
            $("#droneTable tr").each(function() {
                var val = $(t.rows[i].cells[4]).text();
                console.log(val);
                if (i != 0) $(t.rows[i].cells[0]).text(i);
                if (val.localeCompare(droneKey) == 0) {
                    t.deleteRow(i);
                    i--;
                }
                i++;
            })
            $("#deleteModal .close").click()

        }

        function deleteDrone() {
            var jsonString = {
                "droneId": droneIdKeyMap[$("#droneDeleteKey").text()]
            };
            console.log(jsonString);
            $.ajax("/CMSDrone/cms/deleteDrone", {
                type: "post",
                data: jsonString,
                success: function(res) {
                    //console.log("Pappu bhai");
                    console.log(res);
                    var i = 0;
                    var t = document.getElementById("droneTable");
                    $("#droneTable tr").each(function() {
                        var val = $(t.rows[i].cells[4]).text();
                        if (i != 0) $(t.rows[i].cells[0]).text(i);
                        if (val.localeCompare($("#droneDeleteKey").text()) == 0) {
                            t.deleteRow(i);
                            i--;
                        }
                        i++;



                    });
                    $("#deleteModal .close").click()

                },
                error: function(res) {
                    console.log(res);
                    $("#deleteModal .close").click()
                }

            });

        }




        function Update2() {
            console.log(droneIdKeyMap[$("#editDroneKey").val()]);
        }

        function Update() {
            var editDroneModelName = $("#editDroneModelName").val();
            var editDronneModelnumber = $("#editDroneModelNumber").val();
            var editDroneBrandName = $("editDroneBrandName").val();
            var editDroneKey = $("editDroneKey").val();
            var editDroneFlightTime = $("editDroneFlightTime").val();
            var editDroneLoadCapacity = $("editDroneLoadCapacity").val();
            var editDroneSpeed = $("editDroneSpeed").val();
            var editDroneAltitude = $("editDroneAltitude").val();
            var errorFlag = false;
            if ($("#editDroneModelName").val().length > 0) {
                $("#editDroneModelName").text("");
            } else {
                $("#editDroneModelNameError").text("Invalid");
                errorFlag = true;
            }
            if ($("#editDroneModelNumber").val().length > 0) {
                $("#editDroneModelNumber").text("");
            } else {
                $("#editDroneModelNumberError").text("Invalid");
                errorFlag = true;
            }
            if ($("#editDroneBrandName").val().length > 0) {
                $("#editDroneBrandName").text("");
            } else {
                $("#editDroneBrandNameError").text("Invalid");
                errorFlag = true;
            }
            if ($("#editDroneKey").val().length > 0) {
                $("#editDroneKey").text("");
            } else {
                $("#editDroneKeyError").text("Invalid");
                errorFlag = true;
            }
            if ($("#editDroneFlightTime").val().length > 0) {
                $("#editDroneFlightTime").text("");
            } else {
                $("#editDroneFlightTimeError").text("Invalid");
                errorFlag = true;
            }
            if ($("#editDroneLoadCapacity").val().length > 0) {
                $("#editDroneLoadCapacity").text("");
            } else {
                $("#editDroneLoadCapacityError").text("Invalid");
                errorFlag = true;
            }
            if ($("#editDroneSpeed").val().length > 0) {
                $("#editDroneSpeed").text("");
            } else {
                $("#editDroneSpeedError").text("Invalid");
                errorFlag = true;
            }
            if ($("#editDroneAltitude").val().length > 0) {
                $("#editDroneAltitude").text("");
            } else {
                $("#editDroneAltitudeError").text("Invalid");
                errorFlag = true;
            }

            if (!errorFlag) {
                var jsonString = {
                    "id": droneIdKeyMap[$("#editDroneKey").val()],
                    "modelName": $("#editDroneModelName").val(),
                    "modelNumber": $("#editDroneModelNumber").val(),
                    "brand": $("#editDroneBrandName").val(),
                    "droneKey": $("#editDroneKey").val(),
                    "flightTime": $("#editDroneFlightTime").val(),
                    "loadCapacity": $("#editDroneLoadCapacity").val(),
                    "speed": $("#editDroneSpeed").val(),
                    "altitude": $("#editDroneAltitude").val()
                };
                console.log(jsonString);
                $.ajax("/CMSDrone/cms/updateDrone", {
                    type: "post",
                    contentType: "application/json",
                    data: JSON.stringify(jsonString),
                    success: function(res) {
                        if (res.success) {
                            //console.log("Update invoked successfully");
                            $("#editModal").modal("hide");
                            $("#droneTable tbody").empty();
                            $.get("/CMSDrone/cms/getDrones", function(data, status) {
                                console.log(data);
                                if (status) {
                                    for (var i = 0; i < data.length; i++) {
                                        // console.log(data[i].username);
                                        droneIdKeyMap[data[i].droneKey] = data[i].id;
                                        var tableBody = $("#droneTable tbody");
                                        var tableRow = "<tr class='table-light'><td>" + (i + 1) + "</td><td>" + data[i].modelName + "</td>";
                                        tableRow = tableRow + "<td>" + data[i].modelNumber + "</td><td>" + data[i].brand + "</td>";
                                        tableRow = tableRow + "<td>" + data[i].droneKey + "</td>";
                                        tableRow = tableRow + "<td>" + data[i].flightTime + "</td><td>" + data[i].loadCapacity + "</td>";
                                        tableRow = tableRow + "<td>" + data[i].altitude + "</td><td>" + data[i].speed + "</td>";
                                        tableRow = tableRow + "<td><i style='padding: 0px 10px 0px 10px' class='fas fa-edit' data-toggle='modal' data-target='#editModal' onclick='edit()'></i><i style='padding: 0px 10px 0px 10px' class='fas fa-trash-alt' data-toggle='modal' data-target='#deleteModal' onclick='deleteRow()'></i></td></tr>";
                                        tableBody.append(tableRow);
                                    }
                                } else {

                                }
                            });
                            //console.log("Cool");
                        }
                        if (res.isException) {
                            $("#commonErrorLabel").text(res.exception);
                        }
                    },
                    error: function(res) {
                            console.log(res);
                        }
                        // console.log(data);
                        // console.log(status);

                });
            }

        }

        function edit() {
            $("#editDroneModelNameError").text("");
            $("#editDroneModelNumberError").text("");
            $("#editDroneBrandNameError").text("");
            $("#editDroneKeyError").text("");
            $("#editDroneFlightTimeError").text("");
            $("#editDroneLoadCapacityError").text("");
            $("#editDroneSpeedError").text("");
            $("#editDroneAltitudeError").text("");

            $("#droneTable tr").click(function() {
                var column = $(this).children();
                $("#editDroneModelName").val(column[1].innerHTML);
                $("#editDroneModelNumber").val(column[2].innerHTML);
                $("#editDroneBrandName").val(column[3].innerHTML);
                $("#editDroneKey").val(column[4].innerHTML);
                $("#editDroneFlightTime").val(column[5].innerHTML);
                $("#editDroneLoadCapacity").val(column[6].innerHTML);
                $("#editDroneAltitude").val(column[7].innerHTML);
                $("#editDroneSpeed").val(column[8].innerHTML);
            });

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

    <!-- Page level plugins -->
    <!-- <script src="vendor/chart.js/Chart.min.js"></script> -->

    <!-- Page level custom scripts -->
    <!--   <script src="js/demo/chart-area-demo.js"></script>
  <script src="js/demo/chart-pie-demo.js"></script>
 -->
    </body>

    </html>