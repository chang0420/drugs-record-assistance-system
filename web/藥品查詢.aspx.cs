using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

public partial class 藥品查詢 : System.Web.UI.Page
{
    string CHMedName;
    protected void Page_Load(object sender, EventArgs e)
    {
        string UserName = Session["UserName"].ToString();
        lblUsername.Text = UserName + "，您好！";
        CHMedName = Session["MedName"].ToString();
        SearchMed();
    }

    public void SearchMed()
    {
        McuMilab.BrainShaking ws = new McuMilab.BrainShaking();
        String[][] Res = ws.Data(CHMedName);
        try
        {
            lblMCode.Text += Res[0][0];
            lblMGenericName.Text += Res[0][1];
            lblMENName.Text += Res[0][2];
            lblMCHName.Text += Res[0][3];
            lblUse.Text += Res[0][4];
            lblMIndication.Text += Res[0][5];
            lblMSideEffect.Text += Res[0][6];
            lblInteraction.Text += Res[0][7];
            lblTaboo.Text += Res[0][8];
            lblNotice.Text += Res[0][9];
        }
        catch
        {
            Response.Redirect("個人藥單.aspx");
        }
    }

    protected void LogOut_Click(object sender, EventArgs e)
    {
        Response.Redirect("Default.aspx");
    }
}