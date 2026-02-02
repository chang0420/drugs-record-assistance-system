using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

public partial class 生理測量血壓 : System.Web.UI.Page
{
    string UID;
    string BP = "A22";
    string SBPviews = "";
    string DBPviews = "";
    string BPlabels = "";
    int i = 0;
    string BPchartData = "";
    string yControlMax = "";
    string yControlMin = "";
    string SBPHighLine = "";
    string SBPLowLine = "";
    string DBPHighLine = "";
    protected void Page_Load(object sender, EventArgs e)
    {
        string UserName = Session["UserName"].ToString();
        lblUsername.Text = UserName + "，您好！";
        UID = Session["Account"].ToString();
        McuMilab.BrainShaking ws = new McuMilab.BrainShaking();
        string U_ID = Session["Account"].ToString();
        object[][] BPres = ws.QueryPhyData(UID,BP);
        int ttlSBP = 0;
        int avgSBP = 0;
        int ttlDBP = 0;
        int avgDBP = 0;
        int OutlierSBP = 0;
        int OutlierDBP = 0;
        for (i = 0; i < BPres.Length; i++)
        {
            if (BPres[i][4].ToString() == "收縮壓")
            {
                yControlMax += 160 + ",";
                yControlMin += 20 + ",";
                SBPHighLine += 130 + ",";
                SBPLowLine += 90 + ",";
                DBPHighLine += 80 + ",";
                //
                BPlabels += "'" + BPres[i][0] + "',";
                SBPviews += BPres[i][5] + ",";
                string a = Convert.ToString(BPres[i][5]);
                ttlSBP += Convert.ToInt32(a);
                if (Convert.ToInt32(BPres[i][5]) > 130)
                {
                    OutlierSBP++;
                }
                if (Convert.ToInt32(BPres[i][5]) < 90)
                {
                    OutlierSBP++;
                }
            }
            if (BPres[i][4].ToString() == "舒張壓")
            {
                DBPviews += BPres[i][5] + ",";
                string b = Convert.ToString(BPres[i][5]);
                ttlDBP += Convert.ToInt32(b);
                if (Convert.ToInt32(BPres[i][5]) > 80)
                {
                    OutlierDBP++;
                }
                //if (Convert.ToInt32(BPres[i][5]) < 60)
                //{
                //    OutlierDBP++;
                //}
            }
        }
        BPlabels = BPlabels.Substring(0, BPlabels.Length - 1);
        SBPviews = SBPviews.Substring(0, SBPviews.Length - 1);
        DBPviews = DBPviews.Substring(0, DBPviews.Length - 1);
        yControlMax = yControlMax.Substring(0, yControlMax.Length - 1);
        yControlMin = yControlMin.Substring(0, yControlMin.Length - 1);
        SBPHighLine = SBPHighLine.Substring(0, SBPHighLine.Length - 1);
        SBPLowLine = SBPLowLine.Substring(0, SBPLowLine.Length - 1);
        DBPHighLine = DBPHighLine.Substring(0, DBPHighLine.Length - 1);

        BPchartData += "<script>";
        BPchartData += "BPchartLabels = [" + BPlabels + "]; SBPchartData = [" + SBPviews + "]; DBPchartData = [" + DBPviews + "]; chartYcontrolMax = [" + yControlMax + "]; chartYcontrolMin = [" + yControlMin + "]; chartSBPHighLine = [" + SBPHighLine + "]; chartSBPLowLine = [" + SBPLowLine + "]; chartDBPHighLine = [" + DBPHighLine + "]";
        BPchartData += "</script>";
        BPChartData.Text = BPchartData;
        //最新一筆
        string lastSBP = BPres[i - 2][5].ToString();
        lblLastSBP.Text = lastSBP;
        string lastDBP = BPres[i - 1][5].ToString();
        lblLastDBP.Text = lastDBP;
        //平均值
        avgSBP = ttlSBP / (i / 2);
        lblSBPavg.Text = avgSBP.ToString();
        avgDBP = ttlDBP / (i / 2);
        lblDBPavg.Text = avgDBP.ToString();
        //異常值
        lblOutlierSBP.Text = OutlierSBP.ToString();
        lblOutlierDBP.Text = OutlierDBP.ToString();
    }

    protected void LogOut_Click(object sender, EventArgs e)
    {
        Response.Redirect("Default.aspx");
    }

    protected void btn7Days_Click(object sender, EventArgs e)
    {
        string SBPviews = "";
        string DBPviews = "";
        string BPlabels = "";
        int ttlSBP = 0;
        int avgSBP = 0;
        int ttlDBP = 0;
        int avgDBP = 0;
        int OutlierSBP = 0;
        int OutlierDBP = 0;
        McuMilab.BrainShaking ws = new McuMilab.BrainShaking();
        object[][] BPres = ws.QueryPhyData(UID,BP);
        for (int x = 14; x > 0; x--)
        {
            if (BPres[i-x][4].ToString() == "收縮壓")
            {
                BPlabels += "'" + BPres[i-x][0] + "',";
                SBPviews += BPres[i-x][5] + ",";
                string a = Convert.ToString(BPres[i-x][5]);
                ttlSBP += Convert.ToInt32(a);
                if (Convert.ToInt32(BPres[i-x][5]) > 130)
                {
                    OutlierSBP++;
                }
                if (Convert.ToInt32(BPres[i-x][5]) < 90)
                {
                    OutlierSBP++;
                }
            }
            if (BPres[i-x][4].ToString() == "舒張壓")
            {
                DBPviews += BPres[i-x][5] + ",";
                string b = Convert.ToString(BPres[i-x][5]);
                ttlDBP += Convert.ToInt32(b);
                if (Convert.ToInt32(BPres[i-x][5]) > 80)
                {
                    OutlierDBP++;
                }
                //if (Convert.ToInt32(BPres[i-x][5]) < 60)
                //{
                //    OutlierDBP++;
                //}
            }
        }
        BPlabels = BPlabels.Substring(0, BPlabels.Length - 1);
        SBPviews = SBPviews.Substring(0, SBPviews.Length - 1);
        DBPviews = DBPviews.Substring(0, DBPviews.Length - 1);
        BPchartData += "<script>";
        BPchartData += "BPchartLabels = [" + BPlabels + "]; SBPchartData = [" + SBPviews + "]; DBPchartData = [" + DBPviews + "];";
        BPchartData += "</script>";
        BPChartData.Text = BPchartData;
        //平均值
        avgSBP = ttlSBP / 7;
        lblSBPavg.Text = avgSBP.ToString();
        avgDBP = ttlDBP / 7;
        lblDBPavg.Text = avgDBP.ToString();
        //異常值
        lblOutlierSBP.Text = OutlierSBP.ToString();
        lblOutlierDBP.Text = OutlierDBP.ToString();
    }

    protected void btn28Days_Click(object sender, EventArgs e)
    {
        string SBPviews = "";
        string DBPviews = "";
        string BPlabels = "";
        int ttlSBP = 0;
        int avgSBP = 0;
        int ttlDBP = 0;
        int avgDBP = 0;
        int OutlierSBP = 0;
        int OutlierDBP = 0;
        McuMilab.BrainShaking ws = new McuMilab.BrainShaking();
        object[][] BPres = ws.QueryPhyData(UID,BP);
        for (int x = 56; x > 0; x--)
        {
            if (BPres[i - x][4].ToString() == "收縮壓")
            {
                BPlabels += "'" + BPres[i - x][0] + "',";
                SBPviews += BPres[i - x][5] + ",";
                string a = Convert.ToString(BPres[i - x][5]);
                ttlSBP += Convert.ToInt32(a);
                if (Convert.ToInt32(BPres[i - x][5]) > 130)
                {
                    OutlierSBP++;
                }
                if (Convert.ToInt32(BPres[i - x][5]) < 90)
                {
                    OutlierSBP++;
                }

            }
            if (BPres[i - x][4].ToString() == "舒張壓")
            {
                DBPviews += BPres[i - x][5] + ",";
                string b = Convert.ToString(BPres[i - x][5]);
                ttlDBP += Convert.ToInt32(b);
                if (Convert.ToInt32(BPres[i - x][5]) > 80)
                {
                    OutlierDBP++;
                }
                //if (Convert.ToInt32(BPres[i - x][5]) < 60)
                //{
                //    OutlierDBP++;
                //}
            }
        }
        BPlabels = BPlabels.Substring(0, BPlabels.Length - 1);
        SBPviews = SBPviews.Substring(0, SBPviews.Length - 1);
        DBPviews = DBPviews.Substring(0, DBPviews.Length - 1);
        BPchartData += "<script>";
        BPchartData += "BPchartLabels = [" + BPlabels + "]; SBPchartData = [" + SBPviews + "]; DBPchartData = [" + DBPviews + "];";
        BPchartData += "</script>";
        BPChartData.Text = BPchartData;
        //平均值
        avgSBP = ttlSBP / 28;
        lblSBPavg.Text = avgSBP.ToString();
        avgDBP = ttlDBP / 28;
        lblDBPavg.Text = avgDBP.ToString();
        //異常值
        lblOutlierSBP.Text = OutlierSBP.ToString();
        lblOutlierDBP.Text = OutlierDBP.ToString();
    }
}