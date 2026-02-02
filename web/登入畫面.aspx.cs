using System;
using System.Activities.Expressions;
using System.Collections.Generic;
using System.Drawing;
using System.Linq;
using System.Runtime.InteropServices;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

public partial class 登入畫面 : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {

    }
    protected void btnSubmit_Click(object sender, EventArgs e)
    {
        Response.Redirect("個人藥單.aspx");
    }

    protected void LogIn_Click(object sender, EventArgs e)
    {
        Response.Redirect("登入畫面.aspx");
    }
    protected void CreatAccount_Click(object sender, EventArgs e)
    {
        Response.Redirect("註冊畫面.aspx");
    }

    protected void btnLogIn_Click(object sender, EventArgs e)
    {
        string account = tbxID.Text;
        string password = tbxPW.Text;
        if (tbxID.Text != "" && tbxPW.Text != "")
        {
            McuMilab.BrainShaking ws = new McuMilab.BrainShaking();
            string res = ws.UserSelect(account, password);
            if (res != null)
            {
                if (string.Compare(Session["CheckCode"].ToString(), txtCheckCode.Text, true) == 0)
                {
                    Session["UserName"] = res;
                    Session["Account"] = account;
                    Response.Redirect("個人藥單.aspx");
                }
                else
                {
                    txtCheckCode.Text = "";
                    lblShow.Text = "驗證碼錯誤！";
                }
            }
            else
            {
                lblShow.Text = "資料輸入有誤！";
            }
        }
        else
        {
            lblShow.Text = "資料輸入有誤！";
        }
    }

    protected void btnchange_Click(object sender, EventArgs e)
    {
        Image1.ImageUrl = "Image.aspx";
    }
}