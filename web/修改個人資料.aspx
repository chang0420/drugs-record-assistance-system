<%@ Page Language="C#" AutoEventWireup="true" CodeFile="修改個人資料.aspx.cs" Inherits="修改個人資料" %>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-F3w7mX95PdgyTmZZMECAngseQB83DfGTowi0iMjiWaeVhAn4FJkqJByhZMI3AhiU" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-/bQdsTh/da6pkI1MST/rWKFNjaCP5gBSY4sEBT38Q/9RBh9AH40zEOg7Hlq2THRZ" crossorigin="anonymous"></script>
    <title>用藥紀錄輔助系統-修改個人資料</title>
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

        .dropdown-menu {
            background-color: #ADC178;
        }

        h2 {
            text-align: center;
            color: #344E41;
            font-weight: 600;
            font-size: 35px;
        }

        * {
            box-sizing: border-box;
        }

        .wrap {
            margin: 0 auto;
            width: 500px;
            color: #588157;
            font-weight: 400;
            font-size: 20px;
        }

        .information {
            width: 500px;
            height: 40px;
            padding: 10px;
            border-radius: 10px;
            border: 1px solid #333;
        }

        label {
            padding-right: 10px;
        }

        input {
            margin-top: 10px;
        }

        #btn {
            text-align: center;
        }

            #btn input {
                width: 45%;
                margin: 2%;
                margin-top: 10px;
                border: none;
                height: auto;
                padding: 5px;
                font-size: 20px;
                border-radius: 100px;
            }

        .signUp,
        .logIn {
            cursor: pointer;
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
    </style>
</head>
<body style="background-image: url(圖/背景.jpg)">
    <form id="form1" runat="server">
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
                                                    <li><a class="dropdown-item" href="帳號連結.aspx">帳號連結</a></li>
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
            <br />
            <h2>修改個人資料</h2>
            <br />
            <div class="row">
                <div class="col-12">
                    <div class="wrap">
                        <asp:Label ID="Label2" runat="server" Text="姓名" Font-Names="微軟正黑體 Light"></asp:Label>
                        <%--<asp:RegularExpressionValidator ID="RegularExpressionValidator2" runat="server" ControlToValidate="tbxName" Display="Dynamic" Font-Bold="True" Font-Names="微軟正黑體 Light" ForeColor="Red" ValidationExpression="^[0-9]{12}$"></asp:RegularExpressionValidator>--%>
                        <asp:TextBox ID="tbxName" runat="server" CssClass="information"></asp:TextBox>
                        <br />
                        <br />
                        <asp:Label ID="Label1" runat="server" Text="身分證字號" Font-Names="微軟正黑體 Light"></asp:Label><br />
                        <asp:Label ID="txtIdendity" runat="server" Text="A230949949" Font-Names="微軟正黑體" Font-Size="Large" ForeColor="#666666"></asp:Label>
                        <br />
                        <br />
                        <asp:Label ID="Label6" runat="server" Text="性別" Font-Names="微軟正黑體 Light"></asp:Label><br />
                        <asp:Label ID="txtGender" runat="server" Text="女" Font-Names="微軟正黑體" Font-Size="Large" ForeColor="#666666"></asp:Label>
                        <br />
                        <br />
                        <asp:Label ID="Label7" runat="server" Text="生日" Font-Names="微軟正黑體 Light"></asp:Label><br />
                        <asp:Label ID="txtBirth" runat="server" Text="2001/09/21" Font-Names="微軟正黑體" Font-Size="Large" ForeColor="#666666"></asp:Label>
                        <br />
                        <br />
                        <asp:Label ID="Label3" runat="server" Text="手機" Font-Names="微軟正黑體 Light"></asp:Label>
                        <%--<asp:RegularExpressionValidator ID="RegularExpressionValidator3" runat="server" ControlToValidate="tbxPhone" Display="Dynamic" Font-Bold="True" Font-Names="微軟正黑體 Light" ForeColor="Red" ValidationExpression="^[0-9]{12}$"></asp:RegularExpressionValidator>--%>
                        <asp:TextBox ID="tbxPhone" runat="server" CssClass="information"></asp:TextBox>
                        <br />
                        <br />
                        <asp:Label ID="Label4" runat="server" Text="Email" Font-Names="微軟正黑體 Light"></asp:Label>
                        <%--<asp:RegularExpressionValidator ID="testEmail" runat="server" ControlToValidate="tbxEmail" Display="Dynamic" Font-Bold="True" Font-Names="微軟正黑體 Light" ForeColor="Red" ValidationExpression="\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*"></asp:RegularExpressionValidator>--%>
                        <asp:TextBox ID="tbxEmail" runat="server" CssClass="information"></asp:TextBox>
                        <br />
                        <br />
                        <asp:Label ID="Label5" runat="server" Text="地址" Font-Names="微軟正黑體 Light"></asp:Label>
                        <%--<asp:RegularExpressionValidator ID="RegularExpressionValidator5" runat="server" ControlToValidate="tbxAddress" Display="Dynamic" Font-Bold="True" Font-Names="微軟正黑體 Light" ForeColor="Red" ValidationExpression="^[0-9]{12}$"></asp:RegularExpressionValidator>--%>
                        <asp:TextBox ID="tbxAddress" runat="server" CssClass="information"></asp:TextBox>
                        <br />
                        <br />
                        <div id="btn">
                            <asp:Button ID="btnSubmit" runat="server" Text="修改" CssClass="signUp" Font-Names="微軟正黑體 Light" OnClick="btnSubmit_Click" />
                            <br />
                            <asp:Label ID="lblShowError" runat="server" ForeColor="Red" Font-Bold="True" Font-Names="微軟正黑體" Font-Size="XX-Large"></asp:Label>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </form>
</body>
</html>
