using System;
using System.Collections.Generic;
using System.Linq;
using System.Text.RegularExpressions;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

public partial class 常用藥局 : System.Web.UI.Page
{
    string year = "";
    string month = "";
    string UID;
    public static List<string> selectEffect=new List<string>();
    protected void Page_Load(object sender, EventArgs e)
    {
        string UserName = Session["UserName"].ToString();
        lblUsername.Text = UserName + "，您好！";
        //string U_ID = Session["Account"].ToString();
        UID = Session["Account"].ToString();
      

    }

    protected void LogOut_Click(object sender, EventArgs e)
    {
        Response.Redirect("Default.aspx");
    }

    protected void Button1_Click(object sender, EventArgs e)
    {
        //int p=ListBox1.SelectedIndex;
        //string x = selectEffect[p];

        Label1.Text = "";
        string total = "";
        string x = ListBox1.SelectedItem.ToString();
        x = Regex.Replace(x, "[0-9]", "", RegexOptions.IgnoreCase);
        //x = x.Substring(0, x.Length - 1);

        //string str = ListBox1.SelectedItem.ToString();
        //string[] sArray = str.Split(" ");

        string ddl = DropDownList1.Text;
        year = ddl.Substring(0, 4);
        month = ddl.Substring(5, 1);
        McuMilab.BrainShaking ws = new McuMilab.BrainShaking();
        string[] res = ws.SelectMonthlyDetail(UID, x, year, month);
        for (int i = 3; i < res.Length; i += 4)
        {
            total += res[i - 2] + "\r\n" + res[i - 1] + "\r\n" + res[i] + "<br>";
            Label1.Text = total;
        }
        if (total == "")
        {
            Label1.Text = "無備註";
        }
    }

    protected void Button2_Click(object sender, EventArgs e)
    {
        ListBox1.Items.Clear();
        string ddl = DropDownList1.Text;
        year = ddl.Substring(0, 4);
        month = ddl.Substring(5, 1);
        string a = "";
        McuMilab.BrainShaking ws = new McuMilab.BrainShaking();
        string[] res = ws.SelectMonthlyEffect(UID, year, month);
                
        for (int i = 1; i < res.Length; i += 2)
        {
            //a = res[i] + " " + res[i - 1] + "次";
            //ListBox1.Items.Add(a);

            //selectEffect.Add(res[i]);

            a = res[i] + res[i - 1];
            ListBox1.Items.Add(a);
        }
    }
}