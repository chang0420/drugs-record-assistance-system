<%@ Page Language="C#" AutoEventWireup="true" CodeFile="Main.aspx.cs" Inherits="Main" %>

&nbsp;<html xmlns="http://www.w3.org/1999/xhtml"><head runat="server"><meta http-equiv="Content-Type" content="text/html; charset=utf-8"/><link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-F3w7mX95PdgyTmZZMECAngseQB83DfGTowi0iMjiWaeVhAn4FJkqJByhZMI3AhiU" crossorigin="anonymous"><script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-/bQdsTh/da6pkI1MST/rWKFNjaCP5gBSY4sEBT38Q/9RBh9AH40zEOg7Hlq2THRZ" crossorigin="anonymous"></script><title>用藥紀錄輔助系統-首頁</title>
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
        .dropdown-menu{
            background-color:#ADC178;
        }


        .list{
            width:200px;
            margin-left: 2%;
            margin-top: 10%;
        }
        .pic {
          width: 150px;
          height: 150px;
        }
        .pic img {
          object-fit: contain;
          width: 500px;
          height: 350px;
        }
        .aa {
          position: relative;
        }
        .bb {
          height:500px;
        }
        h2 {
            text-align: center;
            color:#344E41;
            font-weight:600;
            font-size:35px;
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
                              <a class="nav-link" href="個人藥單.aspx">個人藥單</a>
                            </li>
                            <li class="nav-item">
                              <a class="nav-link" href="常用藥局.aspx">用藥反應紀錄</a>
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
                <h2>個人藥單</h2>  
                </div>               
                <div class="row justify-content-center">
                    <div class="col-4">
                        <br />
                        <table>
                            <tr>
                                <td><asp:TextBox ID="tbxSearchMed" runat="server" class="form-control me-2" placeholder="藥品搜尋"></asp:TextBox></td>
                                <td><asp:Button ID="btnSearchMed" runat="server" Text="搜尋藥品" class="btn btn-outline-success"  OnClick="btnSearchMed_Click"/></td>
                            </tr>
                        </table>
                    </div>
                    <div class="col-4">
                        <br />
                        <table>
                            <tr>
                                <td><asp:TextBox ID="tbxSearchPhar" runat="server" class="form-control me-2" placeholder="藥局搜尋"></asp:TextBox></td>
                                <td><asp:Button ID="btnSearchPhar" runat="server" Text="搜尋藥局" class="btn btn-outline-success" OnClick="btnSearchPhar_Click"/></td>
                            </tr>
                        </table>
                    <br />
                    </div>
                    <asp:Button ID="btnLINO" runat="server" Text="LINO" OnClick="btnLINO_Click" Width="120px" />
                </div>
                <!--所有藥單-->
<%--                                <div class="col-4">
                    
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
            <div class="col-6">
        <asp:Table ID="Table1" runat="server" CssClass="table">
        </asp:Table>
        <asp:SqlDataSource ID="PersonalPrescription" runat="server" ConnectionString="<%$ ConnectionStrings:HIM04ConnectionString %>" SelectCommand="SELECT DISTINCT ThisDate, Hospital, Department FROM PersonalPrescription WHERE (U_ID = '123')"></asp:SqlDataSource>
                 </ContentTemplate></asp:UpdatePanel></div>
            </div>
            </div>

            </div>
            </div>
            <br />--%>
    </form>
</body>
</html>
