<%@ Page Language="C#" AutoEventWireup="true" CodeFile="健康資料.aspx.cs" Inherits="健康資料" %>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-F3w7mX95PdgyTmZZMECAngseQB83DfGTowi0iMjiWaeVhAn4FJkqJByhZMI3AhiU" crossorigin="anonymous" />
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-/bQdsTh/da6pkI1MST/rWKFNjaCP5gBSY4sEBT38Q/9RBh9AH40zEOg7Hlq2THRZ" crossorigin="anonymous"></script>

    <title>用藥副作用紀錄系統-修改健康資料</title>
    <!-- 張 toastr v2.1.4 -->
    <%--<!-- jQuery v1.9.1 --> <script src="https://code.jquery.com/jquery-1.9.1.min.js"></script> <!-- toastr v2.1.4 --> 
    <link href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/2.1.4/toastr.min.css" rel="stylesheet" /> 
    <script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/2.1.4/toastr.min.js"></script>--%>

    <%-- 胡 bootstrap Toast--%>
       <%--<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet" />
    <link href="https://getbootstrap.com/docs/5.3/assets/css/docs.css" rel="stylesheet" />
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>--%>
    <style>
        .navbar {
            background-color: #ADC178
        }

        .navbar-brand {
            font-size: 35px;
            color: #344E41;
            font-weight: 900;
        }

        .nav-link {
            font-size: 20px;
            color: #344E41;
            font-weight: 700;
        }

        h2 {
            text-align: center;
            color: #344E41;
            font-weight: 600;
            font-size: 35px;
        }

        .user {
            font-size: 16px;
            color: #344E41;
            font-weight: 700;
        }

        .dropdown-menu {
            background-color: #ADC178;
        }

        #btn input {
            width: 30%;
            margin: 2%;
            margin-top: 10px;
            border: none;
            height: auto;
            padding: 5px;
            font-size: 20px;
            border-radius: 100px;
        }

        #btn {
            text-align: center;
        }

        .signUp {
            background-color: #ADC178;
            color: #000000;
            font-weight: 600;
        }

            .signUp:hover {
                background-color: #ADC178;
                color: #000000;
                font-weight: 600;
            }

        h5 {
            color: #344E41;
            font-weight: 600;
            font-size: 25px;
        }

        h6 {
            color: #344E41;
            font-weight: 600;
            font-size: 20px;
        }

        .card {
            background-color: #F6F4D2;
            min-height: 420px;
            min-width: 430px;
        }

        h2 {
            text-align: center;
            color: #344E41;
            font-weight: 600;
            font-size: 35px;
        }

        .txtbxOther {
            Height: 62px;
        }
        .txtbxMed{
            width:auto;
        }
        .txtbxPreg{
            width:auto;
        }
        /*xs*/
        @media(min-width:576px) {
            .card {
                min-width: 450px;
                min-height: 430px;
            }

            .txtbxOther {
                min-height: 62px;
            }
        }

        /*sm*/
        @media(min-width:768px) {
            .card {
                min-width: 230px;
                min-height: 550px;
            }

            .txtbxOther {
                min-height: 70px;
            }
        }

        /*md*/
        @media(min-width:992px) {
            .card {
                min-width: 230px;
                min-height: 550px;
            }

            .txtbxOther {
                min-height: 70px;
            }
        }

        @media (min-width: 1200px) {
            .card {
                min-width: 400px;
                min-height: 500px;
            }

            .txtbxOther {
                min-height: 62px;
            }
        }

        @media (min-width: 1400px) {
            .card {
                min-width: 400px;
                min-height: 500px;
            }

            .txtbxOther {
                min-height: 62px;
            }
        }
        </style>

    <script>
        $(function () {
            toastr.options = {
                "closeButton": false,
                "debug": false,
                "newestOnTop": false,
                "progressBar": false,
                "positionClass": "toast-bottom-full-width",
                "preventDuplicates": false,
                "onclick": null,
                "showDuration": "300",
                "hideDuration": "1000",
                "timeOut": "2000",
                "extendedTimeOut": "1000",
                "showEasing": "swing",
                "hideEasing": "linear",
                "showMethod": "fadeIn",
                "hideMethod": "fadeOut"
            }
        });
    </script>
</head>
<body style="background-image: url(圖/背景.jpg)">
    <%--張方法--%>
    <script>
        $(document).ready(function () {
            var a = document.getElementById("btnCheck");
        });
        function showToast() {

            if ($('#cbxNo').is(":checked") = false) {
                toastr.warning("<h1>請填寫姓名!</h1>", "注意");
            }
        };
        //checkbox 
        function cancelCheck(obj, domName) {
            if (obj.checked) {
                alert('ok');
                var doms = document.getElementsByName(domName);
                for (var i = 0; i < doms.length; i++) {
                    if (obj.value == '無') {
                        document.getElementsByName(domName)[i].checked = false;
                    }
                    else {
                        if (domName == 'disease')
                            document.getElementsByName(domName)[0].checked = false;
                    }
                }
                obj.checked = true;
            }
        }
    </script>
    <%--胡--%>
    <script src="../bootstrap5/bootstrap.bundle.min.js"></script>
    <script>
        document.querySelector("#liveToastBtn").onclick = function () {
            new bootstrap.Toast(document.querySelector('#liveToast')).show();
        }
    </script>
    <%--<div id="dialog">
  <p>
    <span class="ui-icon ui-icon-circle-check" style="float:left; margin:0 7px 50px 0;"></span>
    修改完成！
  </p>
</div>--%>
    <form id="form1" runat="server">
        <br />
        <br />
        <div class="position-fixed top-50 start-50 translate-middle" style="z-index: 5">
            <div id="liveToast" class="toast hide" data-bs-animation="false" role="alert" aria-live="assertive" aria-atomic="true" data-bs-delay="3000">
                <div class="toast-header">
                    <strong class="me-auto">通知</strong>
                </div>
                <div class="toast-body">
                    您有欄位未完成填寫！
                </div>
            </div>
        </div>
        <!-- Flexbox container for aligning the toasts -->
        <asp:ScriptManager ID="ScriptManager1" runat="server">
        </asp:ScriptManager>
        <asp:UpdatePanel ID="UpdatePanel1" runat="server">
            <ContentTemplate>
                <%--<div
      aria-live="polite"
      aria-atomic="true"
      class="d-flex justify-content-center align-items-center w-100">--%>
                <!-- Then put toasts within -->

                <%--    </div>--%>

                <div class="container">
                    <div class="row justify-content-center">
                        <!--NavBar-->
                        <div class="col-12">
                            <nav class="navbar navbar-expand-lg navbar-light">
                      <div class="container-fluid">
                        <a class="navbar-brand">用藥紀錄輔助系統</a>
                        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                          <span class="navbar-toggler-icon"></span>
                        </button>
                        <div class="collapse navbar-collapse" id="navbarSupportedContent">
                          <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                            <li class="nav-item">
                              <a class="nav-link" href="個人藥單.aspx">個人藥單</a>
                            </li>
                            <li class="nav-item">
                              <a class="nav-link" href="用藥月報.aspx">用藥反應紀錄</a>
                            </li>
                            <li class="nav-item dropdown">
                              <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown1" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                生理測量
                              </a>
                              <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                                <li><a class="dropdown-item" href="生理測量.aspx">心跳</a></li>
                                <li><a class="dropdown-item" href="生理測量血壓.aspx">血壓</a></li>
                              </ul>
                            </li>
                            <li class="nav-item dropdown">
                              <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                其他設定
                              </a>
                              <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                                <li><a class="dropdown-item" href="健康資料.aspx">修改健康資料</a></li>
                                <li><a class="dropdown-item" href="修改個人資料.aspx">修改個人資料</a></li>
                                <li><a class="dropdown-item" href="帳號連結.aspx">帳號連結</a></li>
                              </ul>
                            </li>
                          </ul>
                          <form class="d-flex">
                            <asp:Label ID="lblUsername" runat="server" Text="User" Class="user"></asp:Label>
                            &nbsp;&nbsp;
                            <asp:Button ID="LogOut" runat="server" Text="登出" BackColor="#588157" Font-Bold="True" Font-Size="Large" Font-Underline="False" ForeColor="White" OnClick="LogOut_Click"/>
                          </form>
                        </div>
                      </div>
                    </nav>
                            <br />
                            <h2>修改健康資料</h2><br />
                        </div>
                    </div>
                </div>
                <div class="container">
                    <div class="row-cols-md-2 row-cols-sm-1 row gy-5 justify-content-center">
                        <div class="col-md-5 col-sm-auto col-auto">
                            <%--<div class="card" style="height:470px;">--%>
                            <div class="card">
                                <div class="card-body">
                                    <h5>疾病史：</h5>
                                    <%--<button id="FF" visible="false" ></button>--%>
                                    <h6>
                                        <asp:CheckBox ID="cbxNo" name="disease" runat="server" Text="無" AutoPostBack="True" OnCheckedChanged="cbxNo_CheckedChanged2" />
                                        <br />
                                        <asp:CheckBox ID="cbx惡性腫瘤" runat="server" Text="惡性腫瘤" />
                                        <br />
                                        <asp:CheckBox ID="cbx心臟疾病" runat="server" Text="心臟疾病" />
                                        <br />
                                        <asp:CheckBox ID="cbx高血壓疾病" runat="server" Text="高血壓疾病" />
                                        <br />
                                        <asp:CheckBox ID="cbx慢性肝病及肝硬化" runat="server" Text="慢性肝病及肝硬化" />
                                        <br />
                                        <asp:CheckBox ID="cbx免疫性疾病" runat="server" Text="免疫性疾病" />
                                        <br />
                                        <asp:CheckBox ID="cbx癲癇" runat="server" Text="癲癇" />
                                        <br />
                                        <asp:CheckBox ID="cbx精神病" runat="server" Text="精神病" />
                                        <br />
                                        <asp:CheckBox ID="cbx糖尿病" runat="server" Text="糖尿病" />
                                        <br />
                                        <asp:CheckBox ID="cbx前列腺肥大" runat="server" Text="前列腺肥大" />
                                        <br />
                                        <asp:CheckBox ID="cbx腦血管疾病" runat="server" Text="腦血管疾病" />
                                        <br />
                                        <asp:CheckBox ID="cbx肺部疾病" runat="server" Text="肺部疾病" />
                                        <br />
                                        <asp:CheckBox ID="cbx腎臟病" runat="server" Text="腎臟病" />
                                        <br />
                                        <asp:CheckBox ID="cbxOther" runat="server" Text="其他" AutoPostBack="True" OnCheckedChanged="cbxOther_CheckedChanged" />
                                        &nbsp;&nbsp; 
                          <asp:TextBox ID="txtbxOther" runat="server" CssClass="txtbxOther" TextMode="MultiLine" Enabled="False"></asp:TextBox>
                                        <%--<asp:Button ID="btnCheck" runat="server" Text="確認" CssClass="signUp"  Font-Names="微軟正黑體 Light" OnClick="btnCheck_Click" OnClientClick="showToast()"/><%--OnClientClick="showToast()"--%>
                                        <%--<button type="button" class="btn btn-primary" id="liveToastBtn" onclick="">显示吐司消息</button>--%>
                                        <br />
                                    </h6>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-5 col-sm-auto col-auto">
                            <div class="card">
                                <div class="card-body">
                                    <h5>藥物過敏：</h5>
                                    <h6>
                                        <asp:RadioButton ID="rbtn藥物過敏N" runat="server" Text="否" GroupName="藥物過敏" />
                                        &nbsp;&nbsp;&nbsp;
                          <asp:RadioButton ID="rbtn藥物過敏Y" runat="server" Text="是：" GroupName="藥物過敏" OnCheckedChanged="rbtn藥物過敏Y_CheckedChanged" />&nbsp;
                                        <asp:TextBox ID="txtbx藥物過敏" runat="server" CssClass="txtbxMed" Enabled="False"></asp:TextBox>
                                    </h6>
                                    <br />
                                    <h5>懷孕：</h5>
                                    <h6>
                                        <asp:RadioButton ID="rbtn懷孕N" runat="server" Text="否" GroupName="懷孕" />
                                        &nbsp;&nbsp;&nbsp;
                          <asp:RadioButton ID="rbtn懷孕Y" runat="server" Text="是" GroupName="懷孕" />&nbsp;
                                        <asp:Label ID="Label1" runat="server" Text="週數："></asp:Label>
                                        <asp:TextBox ID="txtbx懷孕" runat="server" CssClass="txtbxPreg" Enabled="False"></asp:TextBox>
                                    </h6>
                                    <br />
                                    <h5>吸菸史：</h5>
                                    <h6>
                                        <asp:RadioButton ID="rbtn吸菸N" runat="server" Text="否" GroupName="吸菸" />
                                        &nbsp;&nbsp;&nbsp;
                          <asp:RadioButton ID="rbtn吸菸Y" runat="server" Text="是" GroupName="吸菸" />
                                    </h6>
                                    <br />
                                    <h5>檳榔：</h5>
                                    <h6>
                                        <asp:RadioButton ID="rbtn檳榔N" runat="server" Text="否" GroupName="檳榔" />
                                        &nbsp;&nbsp;&nbsp;
                          <asp:RadioButton ID="rbtn檳榔Y" runat="server" Text="是" GroupName="檳榔" />
                                    </h6>
                                    <br />
                                    <h5>喝酒：</h5>
                                    <h6>
                                        <asp:DropDownList ID="DropDL喝酒" runat="server" Font-Names="微軟正黑體">
                                            <asp:ListItem>無飲酒</asp:ListItem>
                                            <asp:ListItem>一周三次以內（偶爾）</asp:ListItem>
                                            <asp:ListItem>一周三次以上（經常）</asp:ListItem>
                                        </asp:DropDownList>
                                    </h6>
                                </div>
                            </div>
                        </div>
                        <div id="btn">
                            <asp:Label ID="lblShowError" runat="server" ForeColor="Red" Font-Bold="True" Font-Names="微軟正黑體" Font-Size="XX-Large" ></asp:Label>
                            <br />
                            <asp:Button ID="btnCheck" runat="server" Text="確認" CssClass="signUp" Font-Names="微軟正黑體 Light" OnClick="btnCheck_Click" OnClientClick="showToast()" /><%--OnClientClick="showToast()"--%>

                        </div>
                    </div>
                </div>
            </ContentTemplate>
        </asp:UpdatePanel>
    </form>


</body>
</html>
