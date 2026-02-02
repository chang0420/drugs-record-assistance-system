using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

public partial class 健康資料 : System.Web.UI.Page
{
    public string UID, MedHistory = null;
    //public static List<string> MedHistory = new List<string>();
    protected void Page_Load(object sender, EventArgs e)
    {
        string UserName = Session["UserName"].ToString();
        lblUsername.Text = UserName + "，您好！";
        UID = Session["Account"].ToString();
        if (!IsPostBack)
        {
            //McuMilab.BrainShaking ws = new McuMilab.BrainShaking();
            //string[] Res = ws.HealthShow(UID);

            McuMilab.BrainShaking ws = new McuMilab.BrainShaking();
            //string[] res = ws.HealthShow(UID);

            //demo1109.WebService1 ws2 = new demo1109.WebService1();
            String[] Res = ws.HealthShow(UID);
            if (Res[0] == "無")
            {
                rbtn藥物過敏N.Checked = true;
            }
            //if (Res[0] == "有")
            //{
            //    rbtn藥物過敏Y.Checked = true;
            //}
            if (Res[0].IndexOf("有") > -1)
            {
                rbtn藥物過敏Y.Checked = true;
                int i = Res[0].IndexOf("有:");
                string Other = Res[0].Substring(i + 2);
                txtbx藥物過敏.Text = Other;
            }

            if (Res[1] == "無")
            {
                cbxNo.Checked = true;
            }
            else
            {
                if (Res[1].IndexOf("惡性腫瘤") > -1)
                    cbx惡性腫瘤.Checked = true;
                if (Res[1].IndexOf("腦血管疾病") > -1)
                    cbx腦血管疾病.Checked = true;
                if (Res[1].IndexOf("心臟疾病") > -1)
                    cbx心臟疾病.Checked = true;
                if (Res[1].IndexOf("肺部疾病") > -1)
                    cbx肺部疾病.Checked = true;
                if (Res[1].IndexOf("腎臟疾病") > -1)
                    cbx腎臟病.Checked = true;
                if (Res[1].IndexOf("高血壓疾病") > -1)
                    cbx高血壓疾病.Checked = true;
                if (Res[1].IndexOf("慢性肝病及肝硬化") > -1)
                    cbx慢性肝病及肝硬化.Checked = true;
                if (Res[1].IndexOf("免疫性疾病") > -1)
                    cbx免疫性疾病.Checked = true;
                if (Res[1].IndexOf("前列腺肥大") > -1)
                    cbx前列腺肥大.Checked = true;
                if (Res[1].IndexOf("精神病") > -1)
                    cbx精神病.Checked = true;
                if (Res[1].IndexOf("癲癇") > -1)
                    cbx癲癇.Checked = true;
                if (Res[1].IndexOf("糖尿病") > -1)
                    cbx糖尿病.Checked = true;
                if (Res[1].IndexOf("其他") > -1)
                {
                    cbxOther.Checked = true;
                    int i = Res[1].IndexOf("其他:");
                    string Other = Res[1].Substring(i + 3);
                    txtbxOther.Text = Other;
                }
            }

            if (Res[2] == "無")
            {
                rbtn吸菸N.Checked = true;
            }
            else if (Res[2] == "有")
            {
                rbtn吸菸Y.Checked = true;
            }

            if (Res[3] == "無")
            {
                DropDL喝酒.SelectedIndex = 0;
            }
            else if (Res[3] == "偶爾")
            {
                DropDL喝酒.SelectedIndex = 1;
            }
            else if (Res[3] == "經常")
            {
                DropDL喝酒.SelectedIndex = 2;
            }

            if (Res[4] == "無")
            {
                rbtn檳榔N.Checked = true;
            }
            if (Res[4] == "有")
            {
                rbtn檳榔Y.Checked = true;
            }

            if (Res[5] == "無")
            {
                rbtn懷孕N.Checked = true;
            }
            //if (Res[5] == "有")
            //{
            //    rbtn懷孕Y.Checked = true;
            //}
            if (Res[5].IndexOf("有") > -1)
            {
                rbtn懷孕Y.Checked = true;
                int i = Res[5].IndexOf("有:");
                string Other = Res[5].Substring(i + 2);
                txtbx懷孕.Text = Other;
            }
        }
    }
    //public void CheckHealthUpdate()
    //{
    //    if (cbxNo.Checked == false && cbx惡性腫瘤.Checked == false && cbx心臟疾病.Checked == false && cbx高血壓疾病.Checked == false && cbx慢性肝病及肝硬化.Checked == false && cbx免疫性疾病.Checked == false && cbx癲癇.Checked == false
    //        && cbx精神病.Checked == false && cbx糖尿病.Checked == false && cbx前列腺肥大.Checked == false && cbx腦血管疾病.Checked == false && cbx肺部疾病.Checked == false && cbx腎臟病.Checked == false && cbxOther.Checked == false)
    //    {
    //        MedHistory.Add("false");
    //    }
    //    else
    //    {
    //        if (cbxNo.Checked == true)
    //        {
    //            MedHistory.Add("無");
    //        }
    //        else
    //        {
    //            if (cbx惡性腫瘤.Checked == true)
    //            {
    //                MedHistory.Add("惡性腫瘤");
    //            }
    //            if (cbx腦血管疾病.Checked == true)
    //            {
    //                MedHistory.Add("腦血管疾病");
    //            }
    //            if (cbx心臟疾病.Checked == true)
    //            {
    //                MedHistory.Add("心臟疾病");
    //            }
    //            if (cbx肺部疾病.Checked == true)
    //            {
    //                MedHistory.Add("肺部疾病");
    //            }
    //            if (cbx腎臟病.Checked == true)
    //            {
    //                MedHistory.Add("腎臟疾病");
    //            }
    //            if (cbx高血壓疾病.Checked == true)
    //            {
    //                MedHistory.Add("高血壓疾病");
    //            }
    //            if (cbx慢性肝病及肝硬化.Checked == true)
    //            {
    //                MedHistory.Add("慢性肝病及肝硬化");
    //            }
    //            if (cbx免疫性疾病.Checked == true)
    //            {
    //                MedHistory.Add("免疫性疾病");
    //            }
    //            if (cbx前列腺肥大.Checked == true)
    //            {
    //                MedHistory.Add("前列腺肥大");
    //            }
    //            if (cbx精神病.Checked == true)
    //            {
    //                MedHistory.Add("精神病");
    //            }
    //            if (cbx癲癇.Checked == true)
    //            {
    //                MedHistory.Add("癲癇");
    //            }
    //            if (cbx糖尿病.Checked == true)
    //            {
    //                MedHistory.Add("糖尿病");
    //            }
    //            if (cbxOther.Checked == true)
    //            {
    //                if (txtbxOther.Text != string.Empty)
    //                {
    //                    MedHistory.Add("其他:" + txtbxOther);
    //                }
    //            }
    //        }
    //    }

    //}
    public void AddMedHistory()
    {
        //if (cbxNo.Checked == false && cbx惡性腫瘤.Checked == false && cbx心臟疾病.Checked == false && cbx高血壓疾病.Checked == false && cbx慢性肝病及肝硬化.Checked == false && cbx免疫性疾病.Checked == false && cbx癲癇.Checked == false
        //    && cbx精神病.Checked == false && cbx糖尿病.Checked == false && cbx前列腺肥大.Checked == false && cbx腦血管疾病.Checked == false && cbx肺部疾病.Checked == false && cbx腎臟病.Checked == false && cbxOther.Checked == false)
        //{
        //    MedHistory += "false";
        //}
        //else
        //{
        //if (cbxNo.Checked == true)
        //{
        //    MedHistory += "無";
        //}
        //else
        //{
        if (cbx惡性腫瘤.Checked == true)
        {
            MedHistory += "惡性腫瘤;";
        }
        if (cbx腦血管疾病.Checked == true)
        {
            MedHistory += "腦血管疾病;";
        }
        if (cbx心臟疾病.Checked == true)
        {
            MedHistory += "心臟疾病;";
        }
        if (cbx肺部疾病.Checked == true)
        {
            MedHistory += "肺部疾病;";
        }
        if (cbx腎臟病.Checked == true)
        {
            MedHistory += "腎臟疾病;";
        }
        if (cbx高血壓疾病.Checked == true)
        {
            MedHistory += "高血壓疾病;";
        }
        if (cbx慢性肝病及肝硬化.Checked == true)
        {
            MedHistory += "慢性肝病及肝硬化;";
        }
        if (cbx免疫性疾病.Checked == true)
        {
            MedHistory += "免疫性疾病;";
        }
        if (cbx前列腺肥大.Checked == true)
        {
            MedHistory += "前列腺肥大;";
        }
        if (cbx精神病.Checked == true)
        {
            MedHistory += "精神病;";
        }
        if (cbx癲癇.Checked == true)
        {
            MedHistory += "癲癇;";
        }
        if (cbx糖尿病.Checked == true)
        {
            MedHistory += "糖尿病;";
        }
        if (cbxOther.Checked == true)
        {
            if (txtbxOther.Text != string.Empty)
            {
                MedHistory += ("其他:" + txtbxOther.Text);
            }
        }
    }

    protected void LogOut_Click(object sender, EventArgs e)
    {
        Response.Redirect("Default.aspx");
    }
    protected void btnCheck_Click(object sender, EventArgs e)
    {
        MedHistory = null;
        if (cbxNo.Checked)
        {
            MedHistory = "無";
        }
        else
        {
            AddMedHistory();
        }
        string DrugAllergy = null;
        if (rbtn藥物過敏Y.Checked)
        {
            if (txtbx藥物過敏.Text != string.Empty)
            {
                DrugAllergy += ("有:" + txtbx藥物過敏.Text);
            }
            else
            {
                DrugAllergy = null;
            }
        }
        else if(rbtn藥物過敏N.Checked)
        {
            DrugAllergy = "無";
        }
        string Pregnant = null;
        if (rbtn懷孕Y.Checked)
        {
            if (txtbx懷孕.Text != string.Empty)
            {
                Pregnant += ("有:" + txtbx懷孕.Text);
            }
            else
            {
                Pregnant = null;
            }
        }
        if (rbtn懷孕N.Checked)
        {
            Pregnant = "無";
        }
        string Smoke = null;
        if (rbtn吸菸Y.Checked)
        {
            Smoke = "有";
        }
        if (rbtn吸菸N.Checked)
        {
            Smoke = "無";
        }
        string Betlet = null;
        if (rbtn檳榔Y.Checked)
        {
            Betlet = "有";
        }
        if (rbtn檳榔N.Checked)
        {
            Betlet = "無";
        }
        string Drunk = null;
        if (DropDL喝酒.SelectedIndex == 0)
        {
            Drunk = "無";
        }
        else if (DropDL喝酒.SelectedIndex == 1)
        {
            Drunk = "偶爾";
        }
        else if (DropDL喝酒.SelectedIndex == 2)
        {
            Drunk = "經常";
        }

        if (DrugAllergy != null & MedHistory != null & Smoke != null & Drunk != null & Betlet != null & Pregnant != null)
        {
            McuMilab.BrainShaking ws = new McuMilab.BrainShaking();
            bool res = ws.UpdateHealth(UID, DrugAllergy, MedHistory, Smoke, Drunk, Betlet, Pregnant);
            if (res==true)
            {
                lblShowError.Text = "修改成功！";
                //Response.Redirect("Main.aspx");
            }
        }
        else
        {
            //Page.ClientScript.RegisterStartupScript(Page.GetType(), "", "<script language='javascript'>showToast();</script>");
            // Response.Write(@"<script language='javascript'>showToast();</script>");
            // Response.Write(@"<script language='javascript'>toastr.warning('請填寫姓名!','注意');</script>");

            //Page.ClientScript.RegisterClientScriptBlock(this.GetType(), "Message", "<script>alert('有沒填到ㄉ');</script>");
            lblShowError.Text = "資料有誤！";           
        }

        //陳~~
        //McuMilab.BrainShaking ws = new McuMilab.BrainShaking();
        //bool res = ws.UpdateHealth(UID, DrugAllergy, MedHistory, Smoke, Drunk, Betlet, Pregnant);
        //if (res)
        //{
        //    Response.Redirect("Main.aspx");
        //}
        //else if (MedHistory == null)
        //{
        //    lblShowError.Text = "資料有誤！";
        //}
        //else
        //{
        //    lblShowError.Text = "資料有誤！";
        //}
    }


    protected void cbxOther_CheckedChanged(object sender, EventArgs e)
    {
        if (cbxOther.Checked == false)
        {
            txtbxOther.Text = "";
            txtbxOther.Enabled = false;
        }
        else
        {
            txtbxOther.Enabled = true;
        }
    }


    protected void cbxNo_CheckedChanged2(object sender, EventArgs e)
    {
        if (cbxNo.Checked == true)
        {
            cbx惡性腫瘤.Checked = false;
            cbx惡性腫瘤.Enabled = false;
            cbx心臟疾病.Checked = false;
            cbx心臟疾病.Enabled = false;
            cbx高血壓疾病.Checked = false;
            cbx高血壓疾病.Enabled = false;
            cbx慢性肝病及肝硬化.Checked = false;
            cbx慢性肝病及肝硬化.Enabled = false;
            cbx免疫性疾病.Checked = false;
            cbx免疫性疾病.Enabled = false;
            cbx癲癇.Checked = false;
            cbx癲癇.Enabled = false;
            cbx精神病.Checked = false;
            cbx精神病.Enabled = false;
            cbx糖尿病.Checked = false;
            cbx糖尿病.Enabled = false;
            cbx前列腺肥大.Checked = false;
            cbx前列腺肥大.Enabled = false;
            cbx腦血管疾病.Checked = false;
            cbx腦血管疾病.Enabled = false;
            cbx肺部疾病.Checked = false;
            cbx肺部疾病.Enabled = false;
            cbx腎臟病.Checked = false;
            cbx腎臟病.Enabled = false;
            cbxOther.Checked = false;
            cbxOther.Enabled = false;
            txtbxOther.Text = "";
            txtbxOther.Enabled = false;
        }
        else
        {
            cbx惡性腫瘤.Enabled = true;
            cbx心臟疾病.Enabled = true;
            cbx高血壓疾病.Enabled = true;
            cbx慢性肝病及肝硬化.Enabled = true;
            cbx免疫性疾病.Enabled = true;
            cbx癲癇.Enabled = true;
            cbx精神病.Enabled = true;
            cbx糖尿病.Enabled = true;
            cbx前列腺肥大.Enabled = true;
            cbx腦血管疾病.Enabled = true;
            cbx肺部疾病.Enabled = true;
            cbx腎臟病.Enabled = true;
            cbxOther.Enabled = true;
            txtbxOther.Enabled = true;
        }
    }

    protected void rbtn藥物過敏Y_CheckedChanged(object sender, EventArgs e)
    {
        //if (rbtn藥物過敏Y.Checked == true)
        //{
        //    txtbx藥物過敏.Enabled = true;
        //}
        //else
        //{
        //    txtbx藥物過敏.Enabled = false;
        //}
    }
}