<%@ Page Language="C#" AutoEventWireup="true" CodeFile="登入畫面.aspx.cs" Inherits="登入畫面" %>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-F3w7mX95PdgyTmZZMECAngseQB83DfGTowi0iMjiWaeVhAn4FJkqJByhZMI3AhiU" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-/bQdsTh/da6pkI1MST/rWKFNjaCP5gBSY4sEBT38Q/9RBh9AH40zEOg7Hlq2THRZ" crossorigin="anonymous"></script>
    <title>用藥紀錄輔助系統-登入畫面</title>
    <style>
        .navbar{
            background-color:#ADC178;
        }
        .navbar-brand{
            font-size:35px;
            color:#344E41;
            font-weight:900;
        }
        .nav-link{
            font-size:20px;
            color:#344E41;
            font-weight:700;
        }
        * {
            box-sizing: border-box;
        }

        .wrap {
            margin: 0 auto;
            width: 500px;
            color:#588157;
            font-weight:400;
            font-size:20px;
        }

        h2 {
            text-align: center;
            color:#344E41;
            font-weight:600;
            font-size:35px;
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

        input:focus {
            outline: none;
            /* 取消focus效果 */
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
            background-color:#ADC178;
            color: #000000;
            font-weight:600;
        }

        .signUp:hover {
            background-color:#ADC178;
            color: #000000;
            font-weight:600;
        }

        .logIn {
            background-color: #ffce49;
            color: #816516;
        }

        .logIn:hover {
            background-color: #ffc31f;
            color: #816516;
        }
        .dropdown-menu{
            background-color:#ADC178;
        }
    </style>
</head>
<body style="background-image:url(圖/背景.jpg)">
    <form id="form1" runat="server" defaultbutton="btnLogIn">
        <br />
        <br />
        <div class="container">
            <div class="row">
                <div class="col-12">
                    <!--NavBar-->
                    <div class="row">
                        <div class="col-12">
                            <nav class="navbar navbar-expand-lg navbar-light">
                              <div class="container-fluid">
                                <a class="navbar-brand" href="Default.aspx">用藥紀錄輔助系統</a>
                                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                                  <span class="navbar-toggler-icon"></span>
                                </button>
                                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                                  <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                                    <li class="nav-item">
                                      <a class="nav-link disabled" aria-current="page" href="#">個人藥單</a>
                                    </li>
                                    <li class="nav-item">
                                      <a class="nav-link disabled" href="#">用藥反應紀錄</a>
                                    </li>
                                    <li class="nav-item dropdown">
                                      <a class="nav-link disabled dropdown-toggle" href="#" id="navbarDropdown1" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                        生理測量
                                      </a>
                                      <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                                        <li><a class="dropdown-item" href="#">心跳</a></li>
                                        <li><a class="dropdown-item" href="#">血壓</a></li>
                                      </ul>
                                    </li>
                                    <li class="nav-item dropdown">
                                      <a class="nav-link disabled dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                        其他設定
                                      </a>
                                      <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                                        <li><a class="dropdown-item" href="#">修改健康資料</a></li>
                                        <li><a class="dropdown-item" href="#">修改個人資料</a></li>
                                      </ul>
                                    </li>
                                  </ul>
                                  <form class="d-flex">
                                    <asp:button text="登入" runat="server" BackColor="#588157" Font-Bold="True" Font-Size="Large" Font-Underline="False" ForeColor="White" ID="LogIn1" OnClick="LogIn_Click" />
                                    &nbsp;&nbsp;
                                    <asp:button text="註冊" runat="server" BackColor="#588157" Font-Bold="True" Font-Size="Large" ForeColor="White" ID="CreatAccount1" OnClick="CreatAccount_Click" />
                                  </form>
                                </div>
                              </div>
                            </nav>
                        </div>
                    </div>

                            <br />
                            <br />
                            <h2>用戶登入</h2>
                        <div class="wrap">
                            <asp:Label ID="lblID" runat="server" Text="請輸入帳號" Font-Names="微軟正黑體 Light"></asp:Label>
                            <asp:TextBox ID="tbxID" runat="server" CssClass="information"></asp:TextBox>
                            <br /><br />
                            <asp:Label ID="lblNum" runat="server" Text="請輸入密碼" Font-Names="微軟正黑體 Light" ></asp:Label>
                            <!--<asp:RegularExpressionValidator ID="testNum" runat="server" ControlToValidate="tbxPW" Display="Dynamic" Font-Bold="True" Font-Names="微軟正黑體 Light" ForeColor="Red" ValidationExpression="^[0-9]{12}$"></asp:RegularExpressionValidator>-->
                            <asp:TextBox ID="tbxPW" runat="server" CssClass="information" TextMode="Password"></asp:TextBox>
                            <br /><br />
                            驗證碼(大小寫不限)&nbsp;&nbsp;<asp:Image ID="Image1" runat="server" Width="150px" Height="35px" ImageUrl="Image.aspx"></asp:Image>
                            <asp:Button ID="btnchange" runat="server" Text="更新" Width="50px" Height="25px" Font-Names="微軟正黑體 Light" OnClick="btnchange_Click" Font-Bold="True" Font-Size="10pt" />
                            <br />
                            <asp:TextBox AutoComplete="Off" ID="txtCheckCode" runat="server" CssClass="information"></asp:TextBox>
                            <br />
                            <br /><br />
                            <div id="btn">
                                <asp:Button ID="btnLogIn" runat="server" Text="登入" CssClass="signUp"  Font-Names="微軟正黑體 Light" OnClick="btnLogIn_Click" />
                                <br />
                                <asp:Label ID="lblShow" runat="server" Font-Names="微軟正黑體 Light" style="color:red"></asp:Label>
                            </div>
                            
                            </div>

                            </div>
                        </div>
            <br /><br /><br />
                    </div>

</form>
</body>
</html>
