using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Cryptography;
using System.Text.RegularExpressions;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

public partial class 修改個人資料 : System.Web.UI.Page
{
    string UserName;
    string UID;
    protected void Page_Load(object sender, EventArgs e)
    {
        UserName = Session["UserName"].ToString();
        lblUsername.Text = UserName + "，您好！";
        UID = Session["Account"].ToString();
        if (!IsPostBack)
        {
            McuMilab.BrainShaking ws = new McuMilab.BrainShaking();
            String[] Res = ws.UserShow(UID);
            tbxName.Text = Res[0];
            tbxPhone.Text = Res[3];
            tbxEmail.Text = Res[4];
            tbxAddress.Text = Res[5];

            txtIdendity.Text = Res[6];
            txtGender.Text = Res[1];
            txtBirth.Text = Res[2];
        }

    }

    protected void LogOut_Click(object sender, EventArgs e)
    {
        Response.Redirect("Default.aspx");
    }

    protected void btnSubmit_Click(object sender, EventArgs e)
    {
        string U_ID = Session["Account"].ToString();
        string U_Name = tbxName.Text;
        string U_Email = tbxEmail.Text;
        string U_Phone = tbxPhone.Text;
        string U_Address = tbxAddress.Text;
        if (tbxName.Text!="" && tbxPhone.Text!="" && tbxEmail.Text!="" && tbxAddress.Text != "")
        {
            bool Uphonecheck = Regex.IsMatch(U_Phone, @"^09[0-9]{8}$");
            bool UEmailcheck = Regex.IsMatch(U_Email, @"^[^@\s]+@[^@\s]+\.[^@\s]+$");
            if(Uphonecheck && UEmailcheck)
            {
                McuMilab.BrainShaking ws = new McuMilab.BrainShaking();
                bool res = ws.UserUpdateUser(U_ID, U_Name, U_Phone, U_Email, U_Address);
                if (res == true)
                {
                    lblShowError.Text = "修改成功！";
                    //Session["UserName"] = U_Name;
                    //Response.Redirect("Main.aspx");
                }
            }
            else
            {
                lblShowError.Text = "資料格式不符合！";
            }
        }
        else
        {
            lblShowError.Text = "資料輸入不完整！";
        }
    }
}