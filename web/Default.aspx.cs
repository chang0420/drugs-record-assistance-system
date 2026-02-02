using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.IO;

public partial class _Default : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        File.AppendAllText("C:\\Users\\AA206\\Desktop\\web.txt", DateTime.Now.ToString());
    }



    protected void LogIn_Click(object sender, EventArgs e)
    {
        Response.Redirect("登入畫面.aspx");
    }

    protected void CreatAccount_Click(object sender, EventArgs e)
    {
        Response.Redirect("註冊畫面.aspx");
    }
}