using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Principal;
using System.Text.RegularExpressions;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

public partial class 註冊畫面 : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {

    }

    protected void LogIn_Click(object sender, EventArgs e)
    {
        Response.Redirect("登入畫面.aspx");
    }

    protected void CreatAccount_Click(object sender, EventArgs e)
    {
        Response.Redirect("註冊畫面.aspx");
    }


    protected void btnSubmit_Click(object sender, EventArgs e)
    {
        //Response.Redirect("登入畫面.aspx");
        string U_ID = tbxUID.Text;
        string U_Name = tbxName.Text;
        string U_Password = tbxNum.Text;
        string U_Gender = null;
        if (rbtnMan.Checked)
        {
            U_Gender = "男";
        }
        if (rbtnWoman.Checked)
        {
            U_Gender = "女";
        }
        string U_BD = tbxBD.Text;
        string U_Email = tbxEmail.Text;
        string U_Phone = tbxPhone.Text;
        string U_Address = tbxAddress.Text;
        string U_identify = tbxID.Text;
        string Null = "";
        if (tbxUID.Text != "" && tbxID.Text != "" && tbxNum.Text == tbxReNum.Text && tbxNum.Text != "" && tbxBD.Text != "" && tbxReNum.Text != "" && tbxName.Text != "" && (rbtnMan.Checked || rbtnWoman.Checked) && tbxPhone.Text != "" && tbxEmail.Text != "" && tbxAddress.Text != "")
        {
            bool Uidentifycheck = Regex.IsMatch(U_identify, @"[A-Z][1-2]{1}[0-9]{8}$");
            bool Uphonecheck = Regex.IsMatch(U_Phone, @"^09[0-9]{8}$");
            bool UEmailcheck = Regex.IsMatch(U_Email, @"^[^@\s]+@[^@\s]+\.[^@\s]+$");
            bool UBDcheck = Regex.IsMatch(U_BD, @"^(19 | 20)\d\d[\-\/.]([1 - 9] | 1[012])[\-\/.]([1-9]|[12][0-9]|3[01])$");
            if (Uidentifycheck && Uphonecheck && UEmailcheck && UBDcheck)
            {
                McuMilab.BrainShaking ws = new McuMilab.BrainShaking();
                bool res = ws.UserInsert(U_Name, U_Gender, U_BD, U_Phone, U_Email, U_Address, U_ID, U_Password, Null, Null, Null, Null, Null, Null, U_identify);
                if (res == true)
                {
                    Response.Redirect("登入畫面.aspx");
                }
                if (res == false)
                {
                    lblShowError.Text = "資料輸入錯誤！";
                }
            }
            else
            {
                lblShowError.Text = "資料輸入有誤！";
            }
        }
        else
        {
            lblShowError.Text = "資料輸入不完整！";
        }
    }
}