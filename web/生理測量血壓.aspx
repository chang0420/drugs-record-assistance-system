<%@ Page Language="C#" AutoEventWireup="true" CodeFile="生理測量血壓.aspx.cs" Inherits="生理測量血壓" %>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.3/Chart.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-F3w7mX95PdgyTmZZMECAngseQB83DfGTowi0iMjiWaeVhAn4FJkqJByhZMI3AhiU" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-/bQdsTh/da6pkI1MST/rWKFNjaCP5gBSY4sEBT38Q/9RBh9AH40zEOg7Hlq2THRZ" crossorigin="anonymous"></script>
    <title>用藥紀錄輔助系統-生理測量血壓</title>
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

        .Chart {
            min-width: 280px;
        }
        .Detail {
            width: 18rem;
        }

        /*xs*/
        @media(min-width:576px) {
            .Chart {
                min-width: 476px;
            }
            .Detail {
            width: 25rem;
        }
        }

        /*sm*/
        @media(min-width:768px) {
            .Chart {
                min-width: 668px;
            }
            .Detail {
            width: 35rem;
        }
        }

        /*md*/
        @media(min-width:992px) {
            .Chart {
                min-width: 500px;
            }
            .Detail {
            width: 45rem;
        }
        }

        @media (min-width: 1200px) {
            .Chart {
                min-width: 750px;
            }
            .Detail {
            width: 18rem;
        }
        }

        @media (min-width: 1400px) {
            .Chart {
                min-width: 750px;
            }
            .Detail {
            width: 18rem;
        }
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
            <h2>生理測量-血壓</h2>
            <%--血壓--%>
            <%--陳--%>
            <%--<div class="row justify-content-center">
               <div class="col-3">
                  <br />
                  <br />
                  <div class="card" style="width: 18rem;">
                      <ul class="list-group list-group-flush">
                        <li class="list-group-item">最新一筆收縮壓：
                            <asp:Label ID="lblLastSBP" runat="server" Text="Label"></asp:Label>
                            mmHg
                        </li>
                        <li class="list-group-item">最新一筆舒張壓：
                            <asp:Label ID="lblLastDBP" runat="server" Text="Label"></asp:Label>
                            mmHg
                        </li>
                        <li class="list-group-item">平均收縮壓：
                            <asp:Label ID="lblSBPavg" runat="server" Text="Label"></asp:Label>
                            mmHg
                        </li>
                        <li class="list-group-item">平均舒張壓：
                            <asp:Label ID="lblDBPavg" runat="server" Text="Label"></asp:Label>
                            mmHg
                        </li>
                        <li class="list-group-item">收縮壓異常值：
                            <asp:Label ID="lblOutlierSBP" runat="server" Text="0"></asp:Label>
                            筆
                        </li>
                        <li class="list-group-item">舒張壓異常值：
                            <asp:Label ID="lblOutlierDBP" runat="server" Text="0"></asp:Label>
                            筆
                        </li>
                        <li class="list-group-item">關於血壓：
                            <br />高血壓 -> 大於130/80mmHg
                            <br />正常血壓 -> 小於120/80mmHg
                            <br />低血壓 -> 收縮壓小於90mmHg
                            <br />
                            <br />
                        </li>
                      </ul>
                    </div>
              </div>
              <div class="col-9 my-5">
                        <table>
                            <tr>
                                <td><asp:Button ID="btnSeeAll" runat="server" Text="查看所有資料" class="btn btn-outline-success" OnClick="Page_Load" /></td>
                                <td><asp:Button ID="btn7Days" runat="server" Text="查看7天資料" class="btn btn-outline-success" OnClick="btn7Days_Click"  /></td>
                                <td><asp:Button ID="btn28Days" runat="server" Text="查看28天資料" class="btn btn-outline-success" OnClick="btn28Days_Click" /></td>
                            </tr>
                        </table>
                  <br />
                <div class="card">
                    <div class="body-card">
                        <canvas id="BPChart"></canvas>
                    </div>
                </div>
            </div>
            </div>--%>

            <%--調--%>
            <div class="row row-cols-md-2 justify-content-center">
               <div class="col-md-auto">
                  <br />
                  <br />
                  <div class="card Detail" <%--style="width: 18rem;"--%>>
                      <ul class="list-group list-group-flush">
                        <li class="list-group-item">最新一筆收縮壓：
                            <asp:Label ID="lblLastSBP" runat="server" Text="Label"></asp:Label>
                            mmHg
                        </li>
                        <li class="list-group-item">最新一筆舒張壓：
                            <asp:Label ID="lblLastDBP" runat="server" Text="Label"></asp:Label>
                            mmHg
                        </li>
                        <li class="list-group-item">平均收縮壓：
                            <asp:Label ID="lblSBPavg" runat="server" Text="Label"></asp:Label>
                            mmHg
                        </li>
                        <li class="list-group-item">平均舒張壓：
                            <asp:Label ID="lblDBPavg" runat="server" Text="Label"></asp:Label>
                            mmHg
                        </li>
                        <li class="list-group-item">收縮壓異常值：
                            <asp:Label ID="lblOutlierSBP" runat="server" Text="0"></asp:Label>
                            筆
                        </li>
                        <li class="list-group-item">舒張壓異常值：
                            <asp:Label ID="lblOutlierDBP" runat="server" Text="0"></asp:Label>
                            筆
                        </li>
                        <li class="list-group-item">關於血壓：
                            <br />高血壓 -> 大於130/80mmHg
                            <br />正常血壓 -> 小於120/80mmHg
                            <br />低血壓 -> 收縮壓小於90mmHg
                            <br />
                            <br />
                        </li>
                      </ul>
                    </div>
              </div>
              <div class="col-md-auto my-5">
                        <table>
                            <tr>
                                <td><asp:Button ID="btnSeeAll" runat="server" Text="查看所有資料" class="btn btn-outline-success" OnClick="Page_Load" /></td>
                                <td><asp:Button ID="btn7Days" runat="server" Text="查看7天資料" class="btn btn-outline-success" OnClick="btn7Days_Click"  /></td>
                                <td><asp:Button ID="btn28Days" runat="server" Text="查看28天資料" class="btn btn-outline-success" OnClick="btn28Days_Click" /></td>
                            </tr>
                        </table>
                  <br />
                <div class="card">
                    <div class="body-card Chart">
                        <canvas id="BPChart"></canvas>
                    </div>
                </div>
            </div>
            </div>
        </div>
    </form>
    <asp:Literal runat="server" ID="BPChartData"/>
    <script src="chart.min.js"></script>
    <script>
        //血壓
        var BPchartLabels;//抓後端Data
        var SBPchartData;
        var DBPchartData;
        var chartYcontrolMax;
        var chartYcontrolMin;
        var chartSBPHighLine;
        var chartSBPLowLine;
        var chartDBPHighLine;
        const ctx = document.getElementById('BPChart');
        const SBPbackgroundcolor = [];
        const DBPbackgroundcolor = [];
        //異常值
        for (i = 0; i < SBPchartData.length; i++) {
            if (SBPchartData[i] > 130 || SBPchartData[i] < 90) { SBPbackgroundcolor.push('red') }
            else { SBPbackgroundcolor.push('rgba(88,129,87,0.35)') }
        }

        for (j = 0; j < DBPchartData.length; j++) {
            if (DBPchartData[i] > 90 || DBPchartData[i] < 60) { DBPbackgroundcolor.push('red') }
            else { DBPbackgroundcolor.push('rgba(88,129,87,0.7)') }
        }

        new Chart(ctx, {
            type: 'line',
            data: {
                labels: BPchartLabels,//放日期
                datasets: [{
                    label: '收縮壓',//標題
                    data: SBPchartData,//放收縮壓數值
                    borderWidth: 1,
                    lineTension: 0,
                    borderColor: '#588157',
                    backgroundColor: SBPbackgroundcolor,
                    pointRadius: 4
                },
                {
                    label: '舒張壓',
                    data: DBPchartData,//放舒張壓數值
                    borderWidth: 1,
                    lineTension: 0,
                    borderColor: '#588157',
                    backgroundColor: DBPbackgroundcolor,
                    pointRadius: 4
                },
                {
                    label: '',
                    data: chartYcontrolMax,//控制y軸(最大)
                    borderWidth: 0,
                    lineTension: 0,
                    borderColor: 'White',
                    pointRadius: 0,
                    fill: false
                },
                {
                    label: '',
                    data: chartYcontrolMin,//控制y軸(最小)
                    borderWidth: 0,
                    lineTension: 0,
                    borderColor: 'White',
                    pointRadius: 0,
                    fill: false
                    },
                    {
                        label: '收縮壓高血壓線',
                        data: chartSBPHighLine,//收縮壓高血壓線
                        borderWidth: 1,
                        lineTension: 0,
                        borderColor: 'Red',
                        pointRadius: 0,
                        fill: false
                    },
                    {
                        label: '收縮壓低血壓線',
                        data: chartSBPLowLine,//收縮壓低血壓線
                        borderWidth: 1,
                        lineTension: 0,
                        borderColor: 'Red',
                        pointRadius: 0,
                        fill: false
                    },
                    {
                        label: '舒張壓高血壓線',
                        data: chartDBPHighLine,//舒張壓高血壓線
                        borderWidth: 1,
                        lineTension: 0,
                        borderColor: 'Blue',
                        pointRadius: 0,
                        fill: false
                    }]
            },
            options: {
                scales: {
                    y: {
                        beginAtZero: true
                    }
                }
            }
        });
    </script>
</body>
</html>
