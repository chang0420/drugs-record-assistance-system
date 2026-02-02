using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

public partial class 生理測量 : System.Web.UI.Page
{
    string UID;
    //回傳值
    string views = "";
    string labels = "";
    string HR = "A21";
    string chartData = "";
    string LowVal = "";//60
    string HighVal = "";//100
    string yControlMin = "";//30
    string yControlMax = "";//130
    string barYcontrolMax = "40,40,40,40,40,40,40,40,40,40";//長條圖Y軸
    int i;
    protected void Page_Load(object sender, EventArgs e)
    {
        string UserName = Session["UserName"].ToString();
        lblUsername.Text = UserName + "，您好！";
        UID = Session["Account"].ToString();
        //平均值
        int ttl = 0;
        int avg = 0;
        //
        int lower60 = 0;
        int at6165 = 0;
        int at6670 = 0;
        int at7175 = 0;
        int at7680 = 0;
        int at8185 = 0;
        int at8690 = 0;
        int at9195 = 0;
        int at96100 = 0;
        int higher101 = 0;
        string views2 = "";
        //
        McuMilab.BrainShaking ws = new McuMilab.BrainShaking();
        object[][] HRres = ws.QueryPhyData(UID,HR);
        //折線圖
        for (i = 0; i < HRres.Length; i++)
        {
            labels += "'" + HRres[i][0] + "',";
            views += HRres[i][5] + ",";
            //
            LowVal += 50 + ",";
            HighVal += 100 + ",";
            yControlMin += 20 + ",";
            yControlMax += 140 + ",";
            string a = Convert.ToString(HRres[i][5]);
            ttl += Convert.ToInt32(a);
        }
        labels = labels.Substring(0, labels.Length - 1);
        views = views.Substring(0, views.Length - 1);
        LowVal = LowVal.Substring(0, LowVal.Length - 1);
        HighVal = HighVal.Substring(0, HighVal.Length - 1);
        yControlMin = yControlMin.Substring(0, yControlMin.Length - 1);
        yControlMax = yControlMax.Substring(0, yControlMax.Length - 1);
        //最新一筆
        string last = HRres[i - 1][5].ToString();
        lblLast.Text = last;
        //平均值
        avg = ttl / i;
        lblHRavg.Text = avg.ToString();

        chartData += "<script>";
        chartData += "chartLabels = [" + labels + "]; chartData = [" + views + "]; chartLow = [" + LowVal + "]; chartHigh = [" + HighVal + "]; chartYcontrolMin = [" + yControlMin + "]; chartYcontrolMax = [" + yControlMax + "]";
        chartData += "</script>";
        HRCahrtData.Text = chartData;


        //長條圖

        for (int j = 0; j < HRres.Length; j++)
        {
            string a = Convert.ToString(HRres[j][5]);
            int b = Convert.ToInt32(a);
            if (b <= 60)
            {
                lower60++;
            }
            if (b >= 61 && b <= 65)
            {
                at6165++;
            }
            if (b >= 66 && b <= 70)
            {
                at6670++;
            }
            if (b >= 71 && b <= 75)
            {
                at7175++;
            }
            if (b >= 76 && b <= 80)
            {
                at7680++;
            }
            if (b >= 81 && b <= 85)
            {
                at8185++;
            }
            if (b >= 86 && b <= 90)
            {
                at8690++;
            }
            if (b >= 91 && b <= 95)
            {
                at9195++;
            }
            if (b >= 96 && b <= 100)
            {
                at96100++;
            }
            if (b >= 101)
            {
                higher101++;
            }
        }
        views2 += "<script>chartData2 = ['" + lower60 + "','" + at6165 + "','" + at6670 + "','" + at7175 +
            "','" + at7680 + "','" + at8185 + "','" + at8690 + "','" + at9195 + "','" + at96100 + "','" + higher101 + "']; chartBarYControl = [" + barYcontrolMax + "]</script>";
        HRCahrtData2.Text = views2;
        //異常值
        lblOutlier.Text = higher101.ToString();

    }

    protected void LogOut_Click(object sender, EventArgs e)
    {
        Response.Redirect("Default.aspx");
    }

    protected void btn7Days_Click(object sender, EventArgs e)
    {
        string views = "";
        string labels = "";
        //
        int ttl7 = 0;
        int avg7 = 0;
        //
        int lower60 = 0;
        int at6165 = 0;
        int at6670 = 0;
        int at7175 = 0;
        int at7680 = 0;
        int at8185 = 0;
        int at8690 = 0;
        int at9195 = 0;
        int at96100 = 0;
        int higher101 = 0;
        string views2 = "";
        //
        McuMilab.BrainShaking ws = new McuMilab.BrainShaking();
        object[][] HRres = ws.QueryPhyData(UID,HR);
        for(int y = 7; y > 0; y--)
        {
            labels += "'" + HRres[i-y][0] + "',";
            views += HRres[i-y][5] + ",";
        }
        labels = labels.Substring(0, labels.Length - 1);
        views = views.Substring(0, views.Length - 1);
        chartData += "<script>";
        chartData += "chartLabels = [" + labels + "]; chartData = [" + views + "]";
        chartData += "</script>";
        HRCahrtData.Text = chartData;
        //統計圖
        for(int x=1; x<8; x++)
        {
            string a = Convert.ToString(HRres[i-x][5]);
            int b = Convert.ToInt32(a);
            if (b <= 60)
            {
                lower60++;
            }
            if (b >= 61 && b <= 65)
            {
                at6165++;
            }
            if (b >= 66 && b <= 70)
            {
                at6670++;
            }
            if (b >= 71 && b <= 75)
            {
                at7175++;
            }
            if (b >= 76 && b <= 80)
            {
                at7680++;
            }
            if (b >= 81 && b <= 85)
            {
                at8185++;
            }
            if (b >= 86 && b <= 90)
            {
                at8690++;
            }
            if (b >= 91 && b <= 95)
            {
                at9195++;
            }
            if (b >= 96 && b <= 100)
            {
                at96100++;
            }
            if (b >= 101)
            {
                higher101++;
            }
            views2 = "<script>chartData2 = ['" + lower60 + "','" + at6165 + "','" + at6670 + "','" + at7175 +
                "','" + at7680 + "','" + at8185 + "','" + at8690 + "','" + at9195 + "','" + at96100 + "','" + higher101 + "']; chartBarYControl = [" + barYcontrolMax + "]</script>";
            HRCahrtData2.Text = views2;
            ttl7 += Convert.ToInt32(a);
        }
        //平均值
        avg7 = ttl7 / 7;
        lblHRavg.Text = avg7.ToString();
        //異常值
        lblOutlier.Text = higher101.ToString();
    }

    protected void btn28Days_Click(object sender, EventArgs e)
    {
        string views = "";
        string labels = "";
        //
        int ttl7 = 0;
        int avg7 = 0;
        //
        int lower60 = 0;
        int at6165 = 0;
        int at6670 = 0;
        int at7175 = 0;
        int at7680 = 0;
        int at8185 = 0;
        int at8690 = 0;
        int at9195 = 0;
        int at96100 = 0;
        int higher101 = 0;
        string views2 = "";
        //
        McuMilab.BrainShaking ws = new McuMilab.BrainShaking();
        object[][] HRres = ws.QueryPhyData(UID,HR);
        for (int y = 28; y > 0; y--)
        {
            labels += "'" + HRres[i - y][0] + "',";
            views += HRres[i - y][5] + ",";
        }
        labels = labels.Substring(0, labels.Length - 1);
        views = views.Substring(0, views.Length - 1);
        chartData += "<script>";
        chartData += "chartLabels = [" + labels + "]; chartData = [" + views + "]";
        chartData += "</script>";
        HRCahrtData.Text = chartData;
        //統計圖
        for (int x = 1; x < 29; x++)
        {
            string a = Convert.ToString(HRres[i - x][5]);
            int b = Convert.ToInt32(a);
            if (b <= 60)
            {
                lower60++;
            }
            if (b >= 61 && b <= 65)
            {
                at6165++;
            }
            if (b >= 66 && b <= 70)
            {
                at6670++;
            }
            if (b >= 71 && b <= 75)
            {
                at7175++;
            }
            if (b >= 76 && b <= 80)
            {
                at7680++;
            }
            if (b >= 81 && b <= 85)
            {
                at8185++;
            }
            if (b >= 86 && b <= 90)
            {
                at8690++;
            }
            if (b >= 91 && b <= 95)
            {
                at9195++;
            }
            if (b >= 96 && b <= 100)
            {
                at96100++;
            }
            if (b >= 101)
            {
                higher101++;
            }
            views2 = "<script>chartData2 = ['" + lower60 + "','" + at6165 + "','" + at6670 + "','" + at7175 +
                "','" + at7680 + "','" + at8185 + "','" + at8690 + "','" + at9195 + "','" + at96100 + "','" + higher101 + "']; chartBarYControl = [" + barYcontrolMax + "]</script>";
            HRCahrtData2.Text = views2;
            ttl7 += Convert.ToInt32(a);
        }
        //平均值
        avg7 = ttl7 / 28;
        lblHRavg.Text = avg7.ToString();
        //異常值
        lblOutlier.Text = higher101.ToString();
    }
}