<%@ Page Language="C#" AutoEventWireup="true" CodeFile="用藥月報.aspx.cs" Inherits="常用藥局" %>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-F3w7mX95PdgyTmZZMECAngseQB83DfGTowi0iMjiWaeVhAn4FJkqJByhZMI3AhiU" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-/bQdsTh/da6pkI1MST/rWKFNjaCP5gBSY4sEBT38Q/9RBh9AH40zEOg7Hlq2THRZ" crossorigin="anonymous"></script>
    <title>用藥紀錄輔助系統-用藥月報</title>
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

        .user {
            font-size: 18px;
            color: #344E41;
            font-weight: 700;
        }

        .signUp:hover {
            background-color: #ADC178;
            color: #000000;
            font-weight: 600;
        }

        h2 {
            text-align: center;
            color: #344E41;
            font-weight: 600;
            font-size: 35px;
        }

        .dropdown-menu {
            background-color: #ADC178;
        }

        .card-body {
            font-size: 20px;
        }

        .auto-style1 {
            position: relative;
            display: flex;
            flex-direction: column;
            min-width: 0;
            word-wrap: break-word;
            background-color: #fff;
            background-clip: border-box;
            border-radius: .25rem;
            left: -1px;
            top: 0px;
        }

        .Btn {
            background-color: #91BF94;
            Color: black;
            border: none;
            border-radius: 3px;
            margin-inline-start: 1px;
        }

        .Select {
            /*width:auto;*/
            Height: 230px;
            Width: 194px;
        }

        /*xs*/
        @media(min-width:576px) {
            .Select {
                Height: 230px;
                Width: 140px;
            }
        }

        /*sm*/
        @media(min-width:768px) {
            .Select {
                Height: 230px;
                Width: 140px;
            }
        }

        /*md*/
        @media(min-width:992px) {
            .Select {
                Height: 230px;
                Width: 170px;
            }
        }

        @media (min-width: 1200px) {
            .Select {
                Height: 230px;
                Width: 194px;
            }
        }

        @media (min-width: 1400px) {
            .Select {
                Height: 230px;
                Width: 194px;
            }
        }
    </style>
</head>
<body style="background-image: url(圖/背景.jpg)">
    <form id="form1" runat="server">
        <asp:ScriptManager ID="ScriptManager1" runat="server"></asp:ScriptManager>
        <asp:UpdatePanel ID="UpdatePanel1" runat="server">
            <ContentTemplate>
                <br />
                <br />
                <div class="container">
                    <!--NavBar-->
                    <div class="row">
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
                                                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown1" role="button" data-bs-toggle="dropdown" aria-expanded="false">生理測量
                                                </a>
                                                <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                                                    <li><a class="dropdown-item" href="生理測量.aspx">心跳</a></li>
                                                    <li><a class="dropdown-item" href="生理測量血壓.aspx">血壓</a></li>
                                                </ul>
                                            </li>
                                            <li class="nav-item dropdown">
                                                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">其他設定
                                                </a>
                                                <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                                                    <li><a class="dropdown-item" href="健康資料.aspx">修改健康資料</a></li>
                                                    <li><a class="dropdown-item" href="修改個人資料.aspx">修改個人資料</a></li>
                                                    <li><a class="dropdown-item" href="https://notify-bot.line.me/oauth/authorize?response_type=code&scope=notify&response_mode=form_post&client_id=eq8PIuW9PvsGcEKqX4XdL4&redirect_uri=http://120.125.78.217:8081/LiNo.aspx&state=NO_STATE">帳號連結</a></li>
                                                </ul>
                                            </li>
                                        </ul>
                                    </div>
                                    <asp:Label ID="lblUsername" runat="server" Text="User" Class="user"></asp:Label>
                                    &nbsp;&nbsp;
                            <asp:Button ID="LogOut" runat="server" Text="登出" BackColor="#588157" Font-Bold="True" Font-Size="Large" Font-Underline="False" ForeColor="White" OnClick="LogOut_Click" />
                                </div>
                        </div>
                        </nav>
                    </div>
                </div>
                </div>
            <br />
                <h2>用藥反應紀錄</h2>
                <br />

                <div class="row-cols-md-2 row-cols-sm-1 row justify-content-center">
                    <div class="col-md-3 col-sm-4">
                        <div class="card">
                            <div class="card-body">
                                <asp:DropDownList ID="DropDownList1" runat="server" Font-Names="微軟正黑體">
                                    <asp:ListItem>2023年2月</asp:ListItem>
                                    <asp:ListItem>2023年1月</asp:ListItem>
                                    <asp:ListItem>2022年12月</asp:ListItem>
                                    <asp:ListItem>2022年11月</asp:ListItem>
                                    <asp:ListItem>2022年10月</asp:ListItem>
                                    <asp:ListItem>2022年9月</asp:ListItem>
                                </asp:DropDownList>
                                <asp:Button ID="Button2" runat="server" OnClick="Button2_Click" Text=" 查詢副作用 " CssClass="Btn" Font-Names="微軟正黑體" BackColor="#91BF94" ForeColor="Black" />
                                <br />
                                <br />
                                <asp:ListBox ID="ListBox1" runat="server" Font-Bold="True" Font-Size="Large" Font-Names="微軟正黑體" CssClass="Select"></asp:ListBox>
                                <br />
                                <asp:Button ID="Button1" CssClass="Btn" runat="server" Text=" 細項查詢 " OnClick="Button1_Click" Font-Names="微軟正黑體" />
                            </div>
                        </div>
                        <br />
                    </div>
                    <div class="col-md-6 col-sm-4">
                        <div class="auto-style1">
                            <div class="card-body">
                                <asp:Label ID="Label1" runat="server" Text="副作用紀錄：" Font-Names="微軟正黑體"></asp:Label>
                            </div>
                        </div>
                    </div>
                </div>
            </ContentTemplate>
        </asp:UpdatePanel>
    </form>

</body>
</html>
