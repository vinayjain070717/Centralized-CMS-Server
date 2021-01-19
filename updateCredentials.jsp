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
                                    <td><i class='fas fa-user-edit'></i>&nbsp;</td>
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
                    <label id="successLabel" class="success_label">${requestScope.update}</label>
                    <label id="errorLabel" class="error_label"></label>
                    <h3>Update Credentials</h3>
                    <br>
                    <div class="form-group">
                        <input type="text" class="custom-button-css" id="firstName" placeholder="First Name">
                        <label class="error_label" id="firstNameError"></label>
                        <br>

                    </div>
                    <div class="form-group">
                        <input type="text" class="custom-button-css" id="lastName" placeholder="Last Name">
                        <label class="error_label" id="lastNameError"></label>
                        <br>

                    </div>
                    <div class="form-group">
                        <input type="text" class="custom-button-css" id="username" value="${admin}" placeholder="UserName">

                        <label class="error_label" id="usernameError"></label>
                        <br>

                    </div>
                    <div class="form-group">
                        <input type="password" class="custom-button-css" id="password" placeholder="Password">
                        <label class="error_label" id="passwordError"></label>
                        <br>

                    </div>
                    <div class="form-group">
                        <input type="text" class="custom-button-css" id="confirmPassword" placeholder="Confirm Password">
                        <label class="error_label" id="confirmPasswordError"></label>
                        <br>

                    </div>

                    <div class="form-group">
                        <input type="submit" class="btn btn-primary" id="Update" value="Update" onclick="update()">
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


    <!-- Bootstrap core JavaScript-->
    <script src="vendor/jquery/jquery.min.js"></script>
    <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

    <!-- Core plugin JavaScript-->
    <script src="vendor/jquery-easing/jquery.easing.min.js"></script>

    <!-- Custom scripts for all pages-->
    <script src="js/sb-admin-2.min.js"></script>
    <script type="text/javascript">
        // function Validate()
        // {
        //   var firstName=$("#firstName").val();
        //   var lastName=$("#lastName").val();
        //   var username=$("#username").val();
        //   var password=$("#password").val();
        //   var confirmPassword=$("#confirmPassword").val();
        //   if($("#firstName").val().length>0)
        //   {

        //   }
        // }
        window.onload = function() {
            console.log("1");
            var jsonString = {
                "username": $("#username").val()
            }
            console.log("2");
            var result;
            $.ajax("/CMSDrone/cms/getAdministratorByUsername", {
                type: "post",
                data: jsonString,
                success: function(res) {
                    //console.log("Pappu bhai");
                    result = res.result;
                    console.log(result);
                    console.log(res);
                    console.log(result.username);
                    $("#firstName").val(result.firstName);
                    $("#lastName").val(result.lastName);

                    $("#password").val(result.pwd);

                }
            });
        }

        function update() {
            var firstName = $("#firstName").val();
            var lastName = $("#lastName").val();
            var username = $("#username").val();
            //var username=$("#usernameLabel").text();
            console.log(username);
            var password = $("#password").val();
            var confirmPassword = $("#confirmPassword").val();
            var regex = RegExp("^[a-zA-Z]+$", "gm");
            var regex1 = RegExp("^[a-zA-Z]+$", "gm");
            var regex2 = RegExp("^[\\w@.-_]{1,}$", "gm");
            console.log(regex2);
            console.log(typeof regex2.test(username));
            var flag = regex2.test(username);



            var regex3 = RegExp("^(?=.*[a-z])(?=.*[A-Z])(?=.*[@_#$])[\\w@_#$]{8,15}$", "gm");
            var errorFlag = false;
            if ($("#firstName").val().length > 0 && regex.test(firstName)) {
                $("#firstNameError").text("");
            } else {
                $("#firstNameError").text("Invalid");
                errorFlag = true;
            }

            if ($("#lastName").val().length > 0 && regex1.test(lastName))

            {
                $("#lastNameError").text("");
            } else {
                $("#lastNameError").text("Invalid");
                errorFlag = true;
            }

            if ($("#username").val().length > 0 && regex2.test(username)) {
                console.log("bad");
                $("#usernameError").text("");

            } else {
                console.log("good");
                $("#usernameError").text("Invalid");

                errorFlag = true;
            }
            if ($("#password").val().length > 0 && regex3.test(password)) {
                $("#passwordError").text("");
            } else {
                $("#passwordError").text("Invalid");
                errorFlag = true;

            }
            if ($("#confirmPassword").val().length <= 0) {
                $("#confirmPasswordError").text("invalid");
                errorFlag = true;
            } else if (password.localeCompare(confirmPassword)) {
                $("#confirmPasswordError").text("password not match");
                errorFlag = true;
            } else {
                $("#confirmPasswordError").text("");
            }
            if (!errorFlag) {
                var jsonString = {
                    "firstName": firstName,
                    "lastName": lastName,
                    "username": username,
                    "pwd": password,
                    "pwdKey": "123"
                };
                console.log(JSON.stringify(jsonString));
                // console.log(JSON)
                $.ajax("/CMSDrone/cms/updateAdministrator", {
                    type: "POST",
                    contentType: "application/json",
                    data: JSON.stringify(jsonString),
                    success: function(res) {
                        // console.log("success invoked");
                        console.log(res);
                        if (res.success) {
                            if (res.hasResult) {
                                // console.log(res.result.username);
                                // $("#successLabel").text("Administrator Updated");
                                alert("Administrator updated");
                                window.location.href="updateCredentials.jsp";
                            }
                        }
                        if (res.isException) {
                            // console.log(res.exception);
                            $("#errorLabel").text(res.exception);
                        }
                        if (res.hasResult) {
                            // console.log(res.result);
                            $("#successLabel").text("Administrator updated");
                        }
                    },
                    error: function(res) {
                        $("#errorLabel").text(res.error);
                    }
                });


            }
        }
    </script>
    <link rel="stylesheet" href="css/myCssClasses.css">
<script type="text/javascript" src="js/logout.js"></script>

    <!-- Page level plugins -->
    <!-- <script src="vendor/chart.js/Chart.min.js"></script> -->

    <!-- Page level custom scripts -->
    <!--   <script src="js/demo/chart-area-demo.js"></script>
  <script src="js/demo/chart-pie-demo.js"></script>
 -->
    </body>

    </html>