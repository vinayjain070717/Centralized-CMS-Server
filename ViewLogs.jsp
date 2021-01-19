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
            <div class="container-fluid">
                <center>
                    <h3>Retrieve Logs</h3>
                    <!-- <form action="/CMS/retrieveLogs2"> -->
                        <div class="form-group">
                            <input type="text" class="custom-button-css" id="applicationName" placeholder="Application Name" name="applicationName" oninput="validateApplicationName()">
                            <br>
                            <label class="error_label" id="applicationNameError"></label>
                        </div>
                        <div class="form-group">
                            <input type="date" class="custom-button-css" id="date" placeholder="Date" oninput="validateDate()" name="date">
                            <br>
                            <label class="error_label" id="dateError"></label>
                        </div>
                        <div class="form-group">
                            <button type="button" class="btn btn-primary" onclick='retrieve()' id="submitInformation" >Submit</button>
                        </div>
                        <table class="table table-hover" id="LogTable">
                        <thead>
                            <tr>
                                <th scope="col">S.No.</th>
                                <th scope="col">Date and Time</th>
                                <th scope="col">log</th>
                            </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>

                    <!-- </form> -->
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
        function validateApplicationName() {
            if ($("#applicationName").val().length > 0) {
                $("#applicationNameError").text("");
                $("#applicationName").removeClass("invalid_field");
            } else {
                $("#applicationNameError").text("length cannot be 0");
                $("#applicationName").addClass("invalid_field");
            }
        }

        function validateDate() {
            if ($("#date").val().length <= 0) {
                $("#dateError").text("length cannot be 0");
                $("#date").addClass("invalid_field");
            } else {
                $("#dateError").text("");
                $("#date").removeClass("invalid_field");
            }
        }
        function retrieve()
        {
            var jsonString={"applicationName":$("#applicationName").val(),"date":$("#date").val()};
            console.log(jsonString);
            $.ajax("/CMSDrone/cms/retrieve",{
                data:jsonString,
                type:"post",
                success:function(res)
                {
                    if(res.hasResult)
                    {
                        // console.log(res.result);
                        $("#LogTable tbody").empty();
                        var i=1;
                        for(var item of res.result)
                        {
                            // console.log(i);
                            if(item.indexOf(",")==-1) continue;
                            // console.log(item);
                            var firstIndex=item.indexOf(",");
                            // console.log(item.substring(0,item.indexOf(",")));
                            var secondIndex=item.indexOf(",",firstIndex+1);
                            var abc="<tr><td>"+i+"</td>";
                            abc+="<td>"+item.substring(firstIndex+1,secondIndex)+"</td>";
                            abc+="<td>"+item.substring(secondIndex+1)+"</td></tr>";
                            $("#LogTable tbody").append(abc);
                            i++;
                        }
                    }
                },
                error:function(res)
                {
                    console.log(res);
                }

            });
        }
    </script>
    <script type="text/javascript" src="js/logout.js"></script>


    <!-- Page level plugins -->
    <!-- <script src="vendor/chart.js/Chart.min.js"></script> -->

    <!-- Page level custom scripts -->
    <!--   <script src="js/demo/chart-area-demo.js"></script>
  <script src="js/demo/chart-pie-demo.js"></script>
 -->
    </body>

    </html>