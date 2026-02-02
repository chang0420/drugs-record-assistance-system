using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Data;
using System.Data.SqlClient;
using System.IO;
using System.Reflection;

public partial class 個人藥單 : System.Web.UI.Page
{
    TableRow myRow, myRow2, myRow3, myRow4;
    TableCell myCell, myCell1, myCell2, myCell3, myCell4, myCell5, myCell6, myCell7, myCell8;
    public string ThisDate, Department, Hospital, UserName, Account;
    string conStr = @"Data Source=120.125.78.213\WIN-62RCDNTMNMO,1433;Initial Catalog=HIM04;User ID=胡彩娟;Password=lab0908HIM";
    //demo1109.WebService1 wsMed = new demo1109.WebService1();

    protected void Page_Load(object sender, EventArgs e)
    {
        string Account = Session["Account"].ToString();
        string UserName = Session["UserName"].ToString();
        lblUsername.Text = UserName + "，您好！";
        //
        McuMilab.BrainShaking ws = new McuMilab.BrainShaking();       
        String[][] Res = ws.SelectPersonalMed(Account);

        GridView();
    }
    private void GridView()
    {
        string Account = Session["Account"].ToString();
        SqlConnection con = new SqlConnection(conStr);
        con.Open();
        string cmdTxt = string.Format("SELECT DISTINCT ThisDate as '看診日期', Hospital as '醫院/診所', Department as '科別' FROM PersonalPrescription WHERE (U_ID = '{0}')", Account);
        DataTable dataTable = new DataTable();
        SqlDataAdapter da = new SqlDataAdapter(cmdTxt, con);
        da.Fill(dataTable);
        MainMed.DataSource = dataTable; //告訴GridView資料來源為誰
        MainMed.DataBind();//綁定
        con.Close(); //連線關閉 

        
    }
    private void QueryDate2()
    {
        string Account = Session["Account"].ToString();
        SqlConnection con = new SqlConnection(conStr);
        con.Open();
        //string cmdTxt = @"SELECT U_ID, M_GenericName, M_E_BrandName, M_C_BrandName, Dose, MedPeriod, MedTiming, MedFrequency FROM PersonalPrescription LEFT OUTER JOIN Medicine ON Medicine.M_Code=PersonalPrescription.M_Code WHERE U_ID='123' AND ThisDate='{0}' AND Hospital='{1}' AND Department='{2}';",A,B,C;
        string cmdTxt = string.Format("SELECT M_GenericName, M_E_BrandName, M_C_BrandName, Dose, MedPeriod, MedTiming, MedFrequency FROM PersonalPrescription LEFT OUTER JOIN Medicine ON Medicine.M_Code=PersonalPrescription.M_Code WHERE U_ID='{0}' AND ThisDate='{1}' AND Hospital='{2}' AND Department='{3}'", Account, ThisDate, Hospital, Department);
        SqlCommand cmd = new SqlCommand(cmdTxt, con);
        IDataReader reader = cmd.ExecuteReader();
        DataTable dt = new DataTable();
        dt.Load(reader);
        reader.Close();
        con.Close();

        int i;
        for (i = 0; i < dt.Rows.Count; i++)
        {
            myRow = new TableRow();
            

            myCell = new TableCell();
            myCell1 = new TableCell();
            myCell.Text = string.Format(("學名：{0}"), dt.Rows[i][0].ToString());
           // myCell1.Text = string.Format("<img src='圖/2001_Med20230224_004255.jpg' width='100 % '/>");
            myRow.Cells.Add(myCell);
            // myRow.Cells.Add(myCell1);

            myRow2 = new TableRow();
            myCell2 = new TableCell();
            myCell7 = new TableCell();
            myCell2.Text = string.Format(("商品名：{0}"), dt.Rows[i][1].ToString());
            myCell7.Text = string.Format(("{0}"), dt.Rows[i][2].ToString());
            myRow2.Cells.Add(myCell2);
            myRow2.Cells.Add(myCell7);

            myRow3 = new TableRow();
            myCell3 = new TableCell();
            myCell4 = new TableCell();
            myCell5 = new TableCell();
            myCell6 = new TableCell();
            myCell3.Text = string.Format(("{0}"), dt.Rows[i][6].ToString());
            myCell4.Text = string.Format(("{0}"), dt.Rows[i][4].ToString());
            myCell5.Text = string.Format(("{0}"), dt.Rows[i][5].ToString());
            myCell6.Text = string.Format(("{0}"), dt.Rows[i][3].ToString());
            myRow3.Cells.Add(myCell3);
            myRow3.Cells.Add(myCell4);
            myRow3.Cells.Add(myCell5);
            myRow3.Cells.Add(myCell6);

            myRow4 = new TableRow();
            // myCell8 = new TableCell();
            //myCell8.Text = string.Format("-----------------------------------------------------------------------------");
            // myRow4.Cells.Add(myCell8);
            //myRow4.Cells.Add(cell);

            MedDetail.Rows.Add(myRow);
            MedDetail.Rows.Add(myRow2);
            MedDetail.Rows.Add(myRow3);
            //Table1.Rows.Add(myRow4);

            //myRow.Attributes.Add("class", "altrow1");
            //myRow2.Attributes.Add("class", "altrow1");
            //myRow3.Attributes.Add("class", "altrow");
            if (i == dt.Rows.Count-1)
            {
                myRow.Attributes.Add("class", "altrow1");
                myRow2.Attributes.Add("class", "altrow1");
                myRow3.Attributes.Add("class", "altrow1");
            }
            else if (i < dt.Rows.Count-1)
            {
                myRow.Attributes.Add("class", "altrow1");
                myRow2.Attributes.Add("class", "altrow1");
                myRow3.Attributes.Add("class", "altrow");
            }
        }
        MedDetail.CellPadding = 4;
        MedDetail.CellSpacing = 1;
    }

    private static string urlConvertor(string strUrl)
    {
        string tmpRootDir = HttpContext.Current.Server.MapPath(System.Web.HttpContext.Current.Request.ApplicationPath.ToString());//獲取程序根目錄  
        string urlPath = strUrl.Replace(tmpRootDir, ""); //轉換成相對路徑  
        urlPath = urlPath.Replace(@"/", @"/");
        return urlPath;
    }
    private void QueryDate()
    {
        string Account = Session["Account"].ToString();
        //string [][]med=wsMed.SelectPersonalMed("2001");
        McuMilab.BrainShaking ws = new McuMilab.BrainShaking();
        string[][] med = ws.SelectPersonalMed("Account");

        List<string> image = new List<string>();

        for (int j = 0; j < med.Length; j++)
        {
            image.Add(med[j][12]);
        }
        SqlConnection con = new SqlConnection(conStr);
        con.Open();
        //string cmdTxt = @"SELECT U_ID, M_GenericName, M_E_BrandName, M_C_BrandName, Dose, MedPeriod, MedTiming, MedFrequency FROM PersonalPrescription LEFT OUTER JOIN Medicine ON Medicine.M_Code=PersonalPrescription.M_Code WHERE U_ID='123' AND ThisDate='{0}' AND Hospital='{1}' AND Department='{2}';",A,B,C;
        string cmdTxt = string.Format("SELECT M_GenericName, M_E_BrandName, M_C_BrandName, Dose, MedPeriod, MedTiming, MedFrequency,MedPicture FROM PersonalPrescription LEFT OUTER JOIN Medicine ON Medicine.M_Code=PersonalPrescription.M_Code WHERE U_ID='{0}' AND ThisDate='{1}' AND Hospital='{2}' AND Department='{3}'", Account, ThisDate, Hospital, Department);
        SqlCommand cmd = new SqlCommand(cmdTxt, con);
        IDataReader reader = cmd.ExecuteReader();
        DataTable dt = new DataTable();
        dt.Load(reader);
        reader.Close();
        con.Close();

        int i;
        for (i = 0; i < dt.Rows.Count; i++)
        {
            myRow = new TableRow();

            myCell = new TableCell();
            myCell1 = new TableCell();
            myCell.Text = string.Format(("學名：{0}"), dt.Rows[i][0].ToString());

            //var outPutDirectory = Path.GetDirectoryName(Assembly.GetExecutingAssembly().CodeBase);
            //var iconPath = Path.Combine(outPutDirectory, image[i]);
            string sFile = @"http://localhost:8082/ImageMed/" + dt.Rows[i][7].ToString();
            //string datafile = urlConvertor(sFile);
            myCell1.Text = string.Format(@"<img src='{0}' width='160px' height='160px' />",sFile);
            myRow.Cells.Add(myCell1);
            myRow.Cells.Add(myCell);
            myCell1.RowSpan = 3;

            myRow2 = new TableRow();
            myCell2 = new TableCell();
            myCell7 = new TableCell();
            myCell2.Text = string.Format(("商品名：{0}" + "&nbsp" + "&nbsp" + "&nbsp" + "&nbsp" + "&nbsp" + "&nbsp" + "&nbsp" + "{1}"), dt.Rows[i][1].ToString(), dt.Rows[i][2].ToString());
           // myCell7.Text = string.Format(("{0}"), dt.Rows[i][2].ToString());
            myRow2.Cells.Add(myCell2);
          //  myRow2.Cells.Add(myCell7);

            myRow3 = new TableRow();
            myCell3 = new TableCell();
            myCell4 = new TableCell();
            myCell5 = new TableCell();
            myCell6 = new TableCell();
            myCell3.Text = string.Format(("{0}"+"   "+"{1}" + "   "+"   " + "{2}" +"   "+"{3}"), dt.Rows[i][6].ToString(),  dt.Rows[i][4].ToString(),
                dt.Rows[i][5].ToString(), dt.Rows[i][3].ToString());
            //myCell4.Text = string.Format(("{0}"), dt.Rows[i][4].ToString());
           // myCell5.Text = string.Format(("{0}"), dt.Rows[i][5].ToString());
           // myCell6.Text = string.Format(("{0}"), dt.Rows[i][3].ToString());
            myRow3.Cells.Add(myCell3);
            // myRow3.Cells.Add(myCell4);
            //myRow3.Cells.Add(myCell5);
            //myRow3.Cells.Add(myCell6);

            MedDetail.Rows.Add(myRow);
            MedDetail.Rows.Add(myRow2);
            MedDetail.Rows.Add(myRow3);

            if (i == dt.Rows.Count - 1)
            {
                myRow.Attributes.Add("class", "altrow1");
                myRow2.Attributes.Add("class", "altrow1");
                myRow3.Attributes.Add("class", "altrow1");
            }
            else if (i < dt.Rows.Count - 1)
            {
                myRow.Attributes.Add("class", "altrow1");
                myRow2.Attributes.Add("class", "altrow1");
                myRow3.Attributes.Add("class", "altrow");
            }
        }
        MedDetail.CellPadding = 4;
        MedDetail.CellSpacing = 1;
    }

    protected void MainMed_SelectedIndexChanged(object sender, EventArgs e)
    {
        //string Account = Session["Account"].ToString();
        //SqlConnection con = new SqlConnection(conStr);
        //con.Open();9
        //string cmdTxt = string.Format("SELECT DISTINCT ThisDate as '看診日期', Hospital as '醫院/診所', Department as '科別' FROM PersonalPrescription WHERE (U_ID = '{0}')", Account);
        //DataTable dataTable = new DataTable();
        //SqlDataAdapter da = new SqlDataAdapter(cmdTxt, con);

        //int userId = uid;
        GridView();
        int i = MainMed.SelectedIndex;
        GridViewRow gvr = MainMed.Rows[i];
        ThisDate = gvr.Cells[1].Text;
        Hospital = gvr.Cells[2].Text;
        Department = gvr.Cells[3].Text;

        //ThisDate = MainMed.SelectedRow.Cells.ToString();
        //Hospital = MainMed.SelectedRow.Cells.ToString();
        //Department = MainMed.SelectedRow.Cells.ToString();

        //Department= MainMed.SelectedValue.ToString();
        //Department = MainMed.SelectedDataKey.Values[2].ToString();

        //QueryDate2();
        QueryDate();
    }

    protected void LogOut_Click(object sender, EventArgs e)
    {
        Response.Redirect("Default.aspx");
    }

    protected void btnSearchMed_Click(object sender, EventArgs e)
    {
        string CHMedName;
        if (tbxSearchMed.Text != "")
        {
            CHMedName = tbxSearchMed.Text;
            Session["MedName"] = CHMedName;
            Response.Redirect("藥品查詢.aspx");
        }
        else
            return;
    }

    protected void btnSearchPhar_Click(object sender, EventArgs e)
    {
        string PharName;
        if (tbxSearchPhar.Text != "")
        {
            PharName = tbxSearchPhar.Text;
            Session["PName"] = PharName;
            Response.Redirect("藥局查詢.aspx");
        }
        else
        {
            return;
        }
    }
}