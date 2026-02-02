<%@ Page Language="C#" AutoEventWireup="true" CodeFile="生理測量.aspx.cs" Inherits="生理測量" %>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.3/Chart.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-F3w7mX95PdgyTmZZMECAngseQB83DfGTowi0iMjiWaeVhAn4FJkqJByhZMI3AhiU" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-/bQdsTh/da6pkI1MST/rWKFNjaCP5gBSY4sEBT38Q/9RBh9AH40zEOg7Hlq2THRZ" crossorigin="anonymous"></script>
    <title>用藥紀錄輔助系統-生理測量心跳</title>
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
            <br />
            <%--心跳--%>
            <h2>生理測量-心跳</h2>
            <%--//陳--%>
            <%--<div class="row justify-content-center">
              <div class="col-3">
                  <br />
                  <br />
                  <div class="card" style="width: 18rem;">
                      <ul class="list-group list-group-flush">
                        <li class="list-group-item">最新一筆：
                            <asp:Label ID="lblLast" runat="server" Text="Label"></asp:Label>
                            bpm
                        </li>
                        <li class="list-group-item">平均心率：
                            <asp:Label ID="lblHRavg" runat="server" Text="Label"></asp:Label>
                            bpm
                        </li>
                        <li class="list-group-item">異常值：
                            <asp:Label ID="lblOutlier" runat="server" Text="Label"></asp:Label>
                            筆
                        </li>
                        <li class="list-group-item">關於心率：
                            <br />心跳低於50bpm -> 心跳過慢
                            <br />心跳介於50~100bpm -> 心跳正常
                            <br />心跳高於100bpm -> 心跳過快
                            <br />
                            <br />服用高血壓藥物或心律不整藥物可能使心跳減慢，使用支氣管擴張劑或含咖啡因之藥物則可能使心跳增快。
                        </li>
                      </ul>
                    </div>
              </div>

              <div class="col-9 my-5">
                       <table>
                            <tr>
                                <td><asp:Button ID="btnSeeAll" runat="server" Text="查看所有資料" class="btn btn-outline-success" OnClick="Page_Load" /></td>
                                <td><asp:Button ID="btn7Days" runat="server" Text="查看7天資料" class="btn btn-outline-success" OnClick="btn7Days_Click" /></td>
                                <td><asp:Button ID="btn28Days" runat="server" Text="查看28天資料" class="btn btn-outline-success" OnClick="btn28Days_Click" /></td>
                            </tr>
                        </table>
                  <br />
                  <div class="card">
                    <div class="body-card">
                        <canvas id="HRChart"></canvas>
                    </div>
                </div>
                  <br />
                  <br />
                 <div class="card">
                    <div class="body-card">
                        <canvas id="HRChart2"></canvas>
                    </div>
                </div>
              </div>
              
            </div>--%>
            <br />

            <%--//調--%>
            <div class="row row-cols-md-2 justify-content-center">
                <div class="col-md-auto ">
                    <br />
                    <br />
                    <div class="card Detail" <%--style="width: 18rem;"--%>>
                        <ul class="list-group list-group-flush">
                            <li class="list-group-item">最新一筆：
                            <asp:Label ID="lblLast" runat="server" Text="Label"></asp:Label>
                                bpm
                            </li>
                            <li class="list-group-item">平均心率：
                            <asp:Label ID="lblHRavg" runat="server" Text="Label"></asp:Label>
                                bpm
                            </li>
                            <li class="list-group-item">異常值：
                            <asp:Label ID="lblOutlier" runat="server" Text="Label"></asp:Label>
                                筆
                            </li>
                            <li class="list-group-item">關於心率：
                            <br />
                                心跳低於50bpm -> 心跳過慢
                            <br />
                                心跳介於50~100bpm -> 心跳正常
                            <br />
                                心跳高於100bpm -> 心跳過快
                            <br />
                                <br />
                                服用高血壓藥物或心律不整藥物可能使心跳減慢，使用支氣管擴張劑或含咖啡因之藥物則可能使心跳增快。
                            </li>
                        </ul>
                    </div>
                </div>

                <div class="col-md-auto my-5">
                    <table>
                        <tr>
                            <td>
                                <asp:Button ID="btnSeeAll" runat="server" Text="查看所有資料" class="btn btn-outline-success" OnClick="Page_Load" /></td>
                            <td>
                                <asp:Button ID="btn7Days" runat="server" Text="查看7天資料" class="btn btn-outline-success" OnClick="btn7Days_Click" /></td>
                            <td>
                                <asp:Button ID="btn28Days" runat="server" Text="查看28天資料" class="btn btn-outline-success" OnClick="btn28Days_Click" /></td>
                        </tr>
                    </table>
                    <br />
                    <div class="card">
                        <div class="body-card Chart">
                            <canvas id="HRChart"></canvas>
                        </div>
                    </div>
                    <br />
                    <br />
                    <div class="card">
                        <div class="body-card Chart">
                            <canvas id="HRChart2"></canvas>
                        </div>
                    </div>
                </div>

            </div>
    </form>
    <%--抓心跳資料--%>
    <asp:Literal runat="server" ID="HRCahrtData" />
    <asp:Literal runat="server" ID="HRCahrtData2" />

    <script src="chart.min.js"></script>
    <script>
        var chartLabels;
        var chartData;
        var chartLow;
        var chartHigh;
        var chartYcontrolMin;
        var chartYcontrolMax;
        //const ctx = document.getElementById('HRChart');
        const backgroundcolor = [];
        //異常值
        for (i = 0; i < chartData.length; i++) {
            if (chartData[i] > 100) { backgroundcolor.push('red') }
            else { backgroundcolor.push('rgba(88,129,87,0.5)') }
        }

        //setup
        const data = {
            labels: chartLabels,//放日期
            datasets: [{
                label: '心跳(bpm)',//標題
                data: chartData,//放心率數值
                borderWidth: 1,
                lineTension: 0,
                borderColor: '#588157',
                backgroundColor: backgroundcolor,
                pointRadius: 3
            },
            {
                label: '心跳過低',//標題
                data: chartLow,//放心率數值
                borderWidth: 1,
                borderColor: 'Red',
                fill: false,
                lineTension: 0,
                pointRadius: 0
            },
            {
                label: '心跳過高',//標題
                data: chartHigh,//放心率數值
                borderWidth: 1,
                borderColor: 'Red',
                fill: false,
                lineTension: 0,
                pointRadius: 0
            },
            {//控制y軸(最小)
                label: '',//標題
                data: chartYcontrolMin,//放心率數值
                borderWidth: 0,
                borderColor: 'White',
                fill: false,
                lineTension: 0,
                pointRadius: 0
            },
            {//控制y軸(最大)
                label: '',//標題
                data: chartYcontrolMax,//放心率數值
                borderWidth: 0,
                borderColor: 'White',
                fill: false,
                lineTension: 0,
                pointRadius: 0
            }]
        };

        //horizontalDottedLine
        const horizontalDottedLine = {
            id: 'horizontalDottedLine',
            beforeDraw(chart, args, options) {
                const { ctx3, chartArea: { top, right, bottom, left, width, height },
                    scales: { x, y } } = chart;
                ctx3.save();

                ctx3.strokeStyle = 'red';
                ctx3.strokeRect(left, y.getPixelForValue(100), width, 0);
                ctx3.restore();
            }
        }

        //config
        const config = {
            type: 'line',
            data: data,
            options: {
                scales: {
                    y: {

                    },
                }
            },
            Plugin: [horizontalDottedLine]
        };

        //render/init
        const HRChart = new Chart(
            document.getElementById('HRChart'),
            config
        );


        //異常線
        //const horizontalDottedLine = {
        //    id: 'horizontalDottedLine',
        //    beforeDatasetsDraw(chart, args, options) {
        //        const { ctx, chartArea: { top, right, bottom, left, width, height },
        //            scales: { x, y } } = chart;
        //        ctx.save();

        //        ctx.strokeStyle = 'gray';
        //        ctx.strokeRect(0, 0, width, 0);
        //        ctx.restore();
        //    }
        //}


        //長條圖
        var chartData2;
        var chartBarYControl;
        const ctx2 = document.getElementById('HRChart2');

        new Chart(ctx2, {
            type: 'bar',
            data: {
                labels: ['<60', '61-65', '66-70', '71-75', '76-80', '81-85', '86-90', '91-95', '96-100', '>101'],//X
                datasets: [{
                    label: '次數',//標題
                    data: chartData2,//Y
                    borderWidth: 1,
                    borderColor: '#588157',
                    backgroundColor: 'rgba(88,129,87,0.35)'
                },
                {
                    label: '',//標題
                    data: chartBarYControl,//Y
                    borderWidth: 0,
                    borderColor: 'White',
                    fill: false,
                    type: 'line',
                    pointRadius: 0,
                    pointHitRadius: 0
                }]
            }
        });
    </script>

</body>
</html>
