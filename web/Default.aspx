<%@ Page Language="C#" AutoEventWireup="true" CodeFile="Default.aspx.cs" Inherits="_Default" %>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-F3w7mX95PdgyTmZZMECAngseQB83DfGTowi0iMjiWaeVhAn4FJkqJByhZMI3AhiU" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-/bQdsTh/da6pkI1MST/rWKFNjaCP5gBSY4sEBT38Q/9RBh9AH40zEOg7Hlq2THRZ" crossorigin="anonymous"></script>
    <title>用藥紀錄輔助系統</title>
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
                    <nav class="navbar navbar-expand-lg navbar-light">
                      <div class="container-fluid">
                        <a class="navbar-brand">用藥紀錄輔助系統</a>
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
                                <li><a class="dropdown-item" href="#">帳號連結</a></li>
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
            <!--主要內容-->
              <div class="row justify-content-center">
                <div class="col-4" style="padding-top:200px">
                  <div style="font-size:36px;color:#344E41;font-weight:900" >提醒您每日的用藥</div>
                  <div style="font-size:20px;color:#344E41;font-weight:600">不讓忙碌耽誤您的健康</div>
                </div>
                <div class="col-4">
                  <img src="圖/用藥.png" alt="Alternate Text" />
                </div>
              </div>
            <br />
            <br />
            <br />
             <div class="row justify-content-center">
                <div class="col-4" style="padding-top:200px">
                  <div style="font-size:36px;color:#344E41;font-weight:900">紀錄服藥後是否有副作用</div>
                  <div style="font-size:20px;color:#344E41;font-weight:600">方便下次回診調整用藥</div>
                </div>
                <div class="col-4">
                  <img src="圖/副作用.png" alt="Alternate Text" />
                </div>
              </div>
            <br />
            <br />
            <br />
              <div class="row justify-content-center">
                <div class="col-4" style="padding-top:200px">
                  <div style="font-size:36px;color:#344E41;font-weight:900">追蹤生理數據</div>
                  <div style="font-size:20px;color:#344E41;font-weight:600">隨時關注身理狀態</div>
                </div>
                <div class="col-4">
                  <img src="圖/量測.png" alt="Alternate Text" />
                </div>
              </div>
            <br />
            <br />
            <br />
             <div class="row justify-content-center">
                <div class="col-4" style="padding-top:200px">
                  <div style="font-size:36px;color:#344E41;font-weight:900">長照服務</div>
                  <div style="font-size:20px;color:#344E41;font-weight:600">協助年長者解決各類需求</div>
                </div>
                <div class="col-4">
                  <img src="圖/長照.png" alt="Alternate Text" />
                </div>
              </div>
            <br />
            <br />
            <br />
            <div class="row justify-content-center">
                <div class="col-4" style="padding-top:200px">
                  <div style="font-size:36px;color:#344E41;font-weight:900">快速搜尋藥局</div>
                  <div style="font-size:20px;color:#344E41;font-weight:600">查看缺藥狀態更便利</div>
                </div>
                <div class="col-4">
                  <img src="圖/藥局.png" alt="Alternate Text" />
                </div>
              </div>
        </div>
    </form>
</body>

</html>
