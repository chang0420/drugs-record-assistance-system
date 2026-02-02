<%@ Page Language="C#" AutoEventWireup="true" CodeFile="個人藥單.aspx.cs" Inherits="個人藥單" %>

<!DOCTYPE html>




<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-F3w7mX95PdgyTmZZMECAngseQB83DfGTowi0iMjiWaeVhAn4FJkqJByhZMI3AhiU" crossorigin="anonymous" />
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-/bQdsTh/da6pkI1MST/rWKFNjaCP5gBSY4sEBT38Q/9RBh9AH40zEOg7Hlq2THRZ" crossorigin="anonymous"></script>

    <title>用藥反應紀錄系統</title>
    <style>
        .navbar {
            background-color: #ADC178
        }

        .navbar-brand {
            font-size: 33px;
            color: #344E41;
            font-weight: 900;
        }

        .nav-link {
            font-size: 18px;
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

        .GridView {
            min-width: 390px;
            padding-left: 200px;
            font-size: 17px;
        }

        .medtable {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            font-size: 17px;
            background-color: lightgoldenrodyellow;
            border: 2px;
            border-style: solid;
            border-color: khaki;
            line-height: 35px;
            margin: 0px;
            /*max-width:700px;*/
            min-width: 390px;
        }

        .altrow {
            /* background-color:red;*/
            border-bottom: 3px solid;
            border-bottom-color: yellowgreen;
            /*border-bottom-color:yellowgreen;*/
        }

        .altrow1 {
            /* background-color:red;*/
            border-bottom: 2px solid;
            border-bottom-color: khaki;
        }

        .bltrow {
            /* background-color:red;*/
            border-bottom: 3px solid;
            border-bottom-color: yellowgreen;
            /*border-bottom-color:yellowgreen;*/
        }

        .bltrow1 {
            /* background-color:red;*/
            border-bottom: 2px solid;
            border-bottom-color: khaki;
        }

        .GridView:hover {
            background-color: bisque;
        }


        /*xs*/
        @media(min-width:576px) {
            .medtable {
                min-width: 420px;
            }

            .GridView {
                min-width: 420px;
            }
        }

        /*sm*/
        @media(min-width:768px) {
            .medtable {
                min-width: 500px;
            }

            .GridView {
                min-width: 500px;
            }
        }

        /*md*/
        @media(min-width:992px) {
            .medtable {
                min-width: 500px;
            }

            .GridView {
                min-width: 310px;
            }
        }

        @media (min-width: 1200px) {
            .medtable {
                min-width: 550px;
            }

            .GridView {
                min-width: 380px;
            }
        }

        @media (min-width: 1400px) {
            .medtable {
                min-width: 550px;
            }

            .GridView {
                min-width: 420px;
            }
        }
    </style>

</head>
<body style="background-image: url(圖/背景.jpg)">
    <form id="form1" runat="server">
        <br />
        <br />
        <asp:ScriptManager ID="ScriptManager1" runat="server"></asp:ScriptManager>
        <asp:UpdatePanel ID="UpdatePanel1" runat="server">
            <ContentTemplate>
                <div class="container">
                    <div class="row justify-content-center">
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
                                                        <li><a class="dropdown-item" href="https://notify-bot.line.me/oauth/authorize?response_type=code&scope=notify&response_mode=form_post&client_id=eq8PIuW9PvsGcEKqX4XdL4&redirect_uri=http://120.125.78.217:8081/LiNo.aspx&state=NO_STATE">連結LineNotify</a></li>
                                                    </ul>
                                                </li>
                                            </ul>
                                            <form class="d-flex">
                                                <asp:Label ID="lblUsername" runat="server" Text="User" Class="user"></asp:Label>
                                                &nbsp;&nbsp;
                            <asp:Button ID="LogOut" runat="server" Text="登出" BackColor="#588157" Font-Bold="True" Font-Size="Large" Font-Underline="False" ForeColor="White" OnClick="LogOut_Click" />
                                            </form>
                                        </div>
                                    </div>
                                </nav>
                            </div>
                        </div>
                    </div>
                    <br />
                </div>
                
                <%--搜尋藥品/藥局--%>
                <div class="container text-center">
                    <div class="row-cols-md-2 row-cols-sm-1 row gx-5 justify-content-center">
                        <div class="col-md-auto col-sm-auto col-auto">
                            <br />
                            <table class="t1">
                                <tr>
                                    <td>
                                        <asp:TextBox ID="tbxSearchMed" runat="server" class="form-control me-3" placeholder="藥品搜尋"></asp:TextBox></td>
                                    <td>
                                        <asp:Button ID="btnSearchMed" runat="server" Text="搜尋藥品" class="btn btn-outline-success" OnClick="btnSearchMed_Click" /></td>
                                </tr>
                            </table>
                        </div>
                        <div class="col-md-auto col-sm-auto col-auto">
                            <br />
                            <table class="t2">
                                <tr>
                                    <td>
                                        <asp:TextBox ID="tbxSearchPhar" runat="server" class="form-control me-3" placeholder="藥局搜尋"></asp:TextBox></td>
                                    <td>
                                        <asp:Button ID="btnSearchPhar" runat="server" Text="搜尋藥局" class="btn btn-outline-success" OnClick="btnSearchPhar_Click" /></td>
                            </table>
                            <br />
                            <br />
                        </div>
                    </div>
                </div>

                <div class="container">
                    <div class="row gx-5 gy-5 row-cols-md-2 row-cols-sm-1 row justify-content-center">
                        <!--所有藥單-->
                        <div class="col-auto col-md-auto col-sm-auto">
                            <asp:GridView ID="MainMed" runat="server" CellPadding="4" Font-Names="微軟正黑體" GridLines="None" OnSelectedIndexChanged="MainMed_SelectedIndexChanged" CssClass="GridView" ForeColor="#333333" BorderColor="#D0DAE6" BorderStyle="Solid" BorderWidth="2px">
                                <AlternatingRowStyle BackColor="White" ForeColor="#284775" />
                                <Columns>
                                    <asp:CommandField SelectText="●" ShowSelectButton="True">
                                        <ControlStyle Font-Size="Smaller" />
                                    </asp:CommandField>
                                </Columns>
                                <EditRowStyle BackColor="#999999" />
                                <FooterStyle BackColor="#5D7B9D" Font-Bold="True" ForeColor="White" />
                                <HeaderStyle BackColor="#5D7B9D" Font-Bold="True" ForeColor="White" />
                                <PagerStyle BackColor="#284775" ForeColor="White" HorizontalAlign="Center" />
                                <RowStyle BackColor="#F7F6F3" ForeColor="#333333" />
                                <SelectedRowStyle BackColor="#E2DED6" Font-Bold="True" ForeColor="#333333" />
                                <SortedAscendingCellStyle BackColor="#E9E7E2" />
                                <SortedAscendingHeaderStyle BackColor="#506C8C" />
                                <SortedDescendingCellStyle BackColor="#FFFDF8" />
                                <SortedDescendingHeaderStyle BackColor="#6F8DAE" />
                            </asp:GridView>
                        </div>

                        <!--細項藥品-->
                        <%--<div class="col-md-auto">--%>
                        <div class="col-auto col-md-auto col-sm-auto">
                            <asp:Table ID="MedDetail" runat="server" CssClass="medtable">
                            </asp:Table>
                        </div>
                    </div>
                    <br />
                    <br />
                    <br />
                    <br />
                </div>
            </ContentTemplate>
        </asp:UpdatePanel>
    </form>
</body>
</html>
