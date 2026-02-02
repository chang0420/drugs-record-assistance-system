using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

public partial class 帳號連結 : System.Web.UI.Page
{
    string UID;
    string Date = DateTime.Now.ToString("yyyy/MM/dd");
    string Time = DateTime.Now.ToString("HH:mm");
    string UserName;

    protected void Page_Load(object sender, EventArgs e)
    {
        try
        {
            UserName = Session["UserName"].ToString();
            UID = Session["Account"].ToString();
            lblUsername.Text = UserName + "，您好！";
            searchREQ();
            search();
        }
        catch
        {
            Label2.Text = "Session遺失！";
        }
        
    }

    protected void btnAddCon_Click(object sender, EventArgs e)
    {
        string AddID = txbAddUserID.Text;
        string Rel = txbRelation.Text;
        if (ID != AddID && ID != "" && AddID != "")
        {
            McuMilab.BrainShaking ws = new McuMilab.BrainShaking();
            ws.AddFamCon(UID, AddID, Rel, Date, Time);
            lblRes.Text = "OK";
        }
        else
            lblRes.Text = "輸入ID有誤！";
    }

    public void searchREQ()
    {
        ddListApprove.Items.Clear();
        ddListApprove.Text = "";
        //string AddID = txbBeCare.Text;
        McuMilab.BrainShaking ws = new McuMilab.BrainShaking();
        String[][] Res = ws.SearchFamConReq(UID);
        for (int i = 0; i < Res.Length; i++)
        {
            ddListApprove.Items.Add(Res[i][0] + " " + Res[i][1]);
        }
    }
    public void search()
    {
        ddListCheck.Items.Clear();
        //string AddID = txbBeCare.Text;
        McuMilab.BrainShaking ws = new McuMilab.BrainShaking();
        String[][] Res = ws.SearchFamCon(UID);
        for (int i = 0; i < Res.Length; i++)
        {
            ddListCheck.Items.Add(Res[i][0] + " " + Res[i][1]);
        }
    }



    protected void btnApproved_Click(object sender, EventArgs e)
    {
        //string AddID = txbBeCare.Text;
        string S1 = ddListApprove.Text;
        int i = S1.IndexOf(" ");
        string ApprovedID = S1.Substring(0, i);
        McuMilab.BrainShaking ws = new McuMilab.BrainShaking();
        ws.ApprovedFamCon(Date, Time,UID, ApprovedID);
        searchREQ();
        search();
    }


    protected void btnDelete_Click(object sender, EventArgs e)
    {
        //string AddID=txbBeCare.Text;
        string S1 = ddListApprove.Text;
        int i = S1.IndexOf(" ");
        string ApprovedID = S1.Substring(0, i);
        McuMilab.BrainShaking ws = new McuMilab.BrainShaking();
        ws.DeleteFamCon(ApprovedID);
        searchREQ();
        search();
    }

    protected void btnDelete1_Click(object sender, EventArgs e)
    {
        //string AddID = txbBeCare.Text;
        string S1 = ddListCheck.Text;
        int i = S1.IndexOf(" ");
        string ApprovedID = S1.Substring(0, i);
        McuMilab.BrainShaking ws = new McuMilab.BrainShaking();
        ws.DeleteFamCon(ApprovedID);
        searchREQ();
        search();
    }

    protected void LogOut_Click(object sender, EventArgs e)
    {
        Response.Redirect("Default.aspx");
    }
}