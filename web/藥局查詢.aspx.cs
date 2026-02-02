using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

public partial class 藥局查詢 : System.Web.UI.Page
{
    string PName;
    TableRow myRow;
    TableCell myCell;
    protected void Page_Load(object sender, EventArgs e)
    {
        string UserName = Session["UserName"].ToString();
        lblUsername.Text = UserName + "，您好！";
        PName = Session["PName"].ToString();
        McuMilab.BrainShaking ws = new McuMilab.BrainShaking();
        String[][] Res = ws.SearchPh(PName);
        try
        {
            lblPName.Text += Res[0][0];
            lblPAddress.Text += Res[0][1];
            lblPNum.Text += Res[0][2];
            DataTable dt = new DataTable();
            myRow = new TableRow();
            myCell = new TableCell();
            myCell.Text = @"<iframe
                              width='600'
                              height = '500'
                              src = 'https://www.google.com/maps/embed/v1/place?key=AIzaSyAmzX_T4Modkgf2RhGwVG6q-qilHeEFKCM &q=" + Res[0][0] + "' />";
            myRow.Cells.Add(myCell);
            PhDetail.Rows.Add(myRow);
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