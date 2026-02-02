<%@ Page Language="C#" AutoEventWireup="true" CodeFile="藥品查詢.aspx.cs" Inherits="藥品查詢" %>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-F3w7mX95PdgyTmZZMECAngseQB83DfGTowi0iMjiWaeVhAn4FJkqJByhZMI3AhiU" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-/bQdsTh/da6pkI1MST/rWKFNjaCP5gBSY4sEBT38Q/9RBh9AH40zEOg7Hlq2THRZ" crossorigin="anonymous"></script>
    <title>用藥紀錄輔助系統-藥品查詢</title>
    <style>
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
                </div>
            </div>
            <br />
            <h2>藥品查詢</h2>
            <br />
            <div class="row">
                <div class="col-12">
                    <div class="card">
                      <div class="card-body">
                          <asp:Label ID="lblMCode" runat="server" Text="藥品代碼："></asp:Label><br />
                          <asp:Label ID="lblMGenericName" runat="server" Text="成分名："></asp:Label><br />
                          <asp:Label ID="lblMENName" runat="server" Text="英文商品名："></asp:Label><br />
                          <asp:Label ID="lblMCHName" runat="server" Text="中文商品名："></asp:Label><br />
                          <asp:Label ID="lblUse" runat="server" Text="用法用量："></asp:Label><br />
                          <asp:Label ID="lblMIndication" runat="server" Text="適應症："></asp:Label><br />
                          <asp:Label ID="lblMSideEffect" runat="server" Text="副作用："></asp:Label><br />
                          <asp:Label ID="lblInteraction" runat="server" Text="交互作用："></asp:Label><br />
                          <asp:Label ID="lblTaboo" runat="server" Text="禁忌："></asp:Label><br />
                          <asp:Label ID="lblNotice" runat="server" Text="醫療須知："></asp:Label>
                      </div>
                    </div>  
                </div>
            </div>
        </div>
    </form>
</body>
</html>
