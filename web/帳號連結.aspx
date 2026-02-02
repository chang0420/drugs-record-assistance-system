<%@ Page Language="C#" AutoEventWireup="true" CodeFile="帳號連結.aspx.cs" Inherits="帳號連結" %>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-F3w7mX95PdgyTmZZMECAngseQB83DfGTowi0iMjiWaeVhAn4FJkqJByhZMI3AhiU" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-/bQdsTh/da6pkI1MST/rWKFNjaCP5gBSY4sEBT38Q/9RBh9AH40zEOg7Hlq2THRZ" crossorigin="anonymous"></script>
    <title>用藥紀錄輔助系統-帳號連結</title>
    <style>
        .try{
            background-color:#ADC178;
        }
        .navbar{
            background-color:#ADC178
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
        .user{
            font-size:18px;
            color:#344E41;
            font-weight:700;
        }
        h4{
            color:#053B06;
            font-weight:800;
        }
        h5{
            color:#0B5D1E;
            font-weight:500;
        }
        .card{
            background-color:#F6F4D2;
        }
        .cardfont{
            color:#0B5D1E;
            font-weight:500;
        }
        .signUp:hover {
            background-color:#ADC178;
            color: #000000;
            font-weight:600;
        }
        h2 {
            text-align: center;
            color:#344E41;
            font-weight:600;
            font-size:35px;
        }
        .dropdown-menu{
            background-color:#ADC178;
        }
    </style>
</head>
<body style="background-image:url(圖/背景.jpg)">
    <form id="form1" runat="server">
        <br />
        <br />
        <div class="container">
            <!--NavBar-->
            <div class="row">
                <div class="col-12">
                   <!--NavBar-->
                    <div class="row justify-content-center">
                        <div class="col-12">
                            <nav class="navbar navbar-expand-lg navbar-light">
                              <div class="container-fluid">
                                <a class="navbar-brand disabled"`>用藥紀錄輔助系統</a>
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
                    <h2>帳號連結</h2>
                    <br />
                    <div class="row justify-content-center">
                        <div class="col-4">
                            <div class="card" style="width: 23rem;height:16rem;">
                              <div class="card-body">
                                <h4 class="card-title">新增關係</h4>
                                <h5 class="card-subtitle mb-2 text-muted">輸入您親友或長者的帳號，並輸入關係建立連結</h5>
                                  <asp:Label ID="Label2" runat="server" Text="被照護者帳號：" Font-Size="15pt"></asp:Label>
                                    <asp:TextBox ID="txbAddUserID" runat="server" Width="119px"></asp:TextBox>
                                    <br />
                                    <br />
                                    <asp:Label ID="Label3" runat="server" Text="關係：" Font-Size="15pt"></asp:Label>
                                    <asp:TextBox ID="txbRelation" runat="server" Width="119px"></asp:TextBox>
                                    &nbsp;
                                    <asp:Button ID="btnAddCon" runat="server" OnClick="btnAddCon_Click" Font-Size="15pt" Text="新增"/>
                                    <asp:Label ID="lblRes" runat="server"></asp:Label>
                                    <br />
                              </div>
                            </div>
                        </div>
                        <div class="col-4">
                            <div class="card" style="width: 23rem;height:16rem;">
                              <div class="card-body">
                                <h4 class="card-title">核准加入</h4>
                                <h5 class="card-subtitle mb-2 text-muted">當有親友要求加入您的帳號時，可點選確認進行核准</h5>
                                  <br />
                                  <asp:DropDownList ID="ddListApprove" Font-Size="15pt" runat="server" Width="157px">
                                    </asp:DropDownList>
                                    &nbsp;
                                    <asp:Button ID="btnApproved" runat="server" Font-Size="15pt" OnClick="btnApproved_Click" Text="核准"/>
                                    &nbsp;
                                    <asp:Button ID="btnDelete" runat="server" Font-Size="15pt" Text="拒絕" OnClick="btnDelete_Click"/>
                                    <br />
                                    <br />
                              </div>
                            </div>
                        </div>
                        <div class="col-4">
                            <div class="card" style="width: 23rem;height:16rem;">
                              <div class="card-body">
                                <h4 class="card-title">現有關係</h4>
                                <h5 class="card-subtitle mb-2 text-muted">可查看現在有誰與您的帳號連結，也可以刪除關係</h5>
                                  <br />
                                  <asp:DropDownList ID="ddListCheck" Font-Size="15pt" runat="server" Width="157px">
                                    </asp:DropDownList>
                                    &nbsp;
                                    <asp:Button ID="btnDelete1" runat="server" Font-Size="15pt" Text="刪除關係" OnClick="btnDelete1_Click"/>
                                    <br />
                              </div>
                            </div>
                        </div>

                    </div>
                    </div></div></div></div></div>
    </form>
</body>
</html>
