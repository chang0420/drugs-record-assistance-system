using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Data.SqlClient;
using System.Data;
using System.Windows.Forms;
using System.Collections;
using static System.Windows.Forms.VisualStyles.VisualStyleElement.TaskbarClock;

namespace testConnectUser
{
    internal class lib1
    {
        public static string conStr = @"Data Source=120.125.78.213\WIN-62RCDNTMNMO,1433;Initial Catalog=HIM04;Persist Security Info=True;User ID=楊子儀;Password=G7g8g964";

        public static void AddCon(string ID, string AddID, string Rel, string Date, string Time)
        {
            SqlConnection con = new SqlConnection(conStr);
            con.Open();
            string cmdTxt = string.Format("Insert into ConnectUser Values ('{0}' ,'{1}' ,'{2}' ,'N' ,'N' ,'{3}' ,'{4}' , NULL , NULL)", ID, AddID, Rel, Date, Time);
            SqlCommand cmd = new SqlCommand(cmdTxt, con);
            cmd.ExecuteNonQuery();
            con.Close();
        }
        public static string[][] SearchReq(string AddID)
        {
            SqlConnection con = new SqlConnection(conStr);
            con.Open();
            string cmdTxt = string.Format("Select U_ID, ConnRole From ConnectUser Where Applicant_ID = '{0}' And Approved = 'N'", AddID);
            SqlDataAdapter reader = new SqlDataAdapter(cmdTxt, con);
            DataTable dt = new DataTable();
            reader.Fill(dt);
            string[][] res=new string[dt.Rows.Count][];
            if (dt.Rows.Count != 0)
            {
                for (int i = 0; i < dt.Rows.Count; i++)
                {
                    res[i] = new string[2];
                    res[i][0] = dt.Rows[i][0].ToString();
                    res[i][1] = dt.Rows[i][1].ToString();
                }
            }
            con.Close();
            return res;
        }

        public static void Approved(string Date, string Time, string AddID ,string ApprovedID)
        {
            SqlConnection con = new SqlConnection(conStr);
            con.Open();
            string cmdTxt = string.Format("Update ConnectUser Set Approved = 'Y' , Approved_Date = '{0}' , Approved_Time = '{1}' Where Applicant_ID = '{2}' and U_ID = '{3}'", Date, Time, AddID, ApprovedID);
            SqlCommand cmd = new SqlCommand(cmdTxt, con);
            cmd.ExecuteNonQuery();
            con.Close();
        }

        public static string[][] Search(string AddID)
        {
            SqlConnection con = new SqlConnection(conStr);
            con.Open();
            string cmdTxt = string.Format("Select U_ID, ConnRole From ConnectUser Where Applicant_ID = '{0}' And Approved = 'Y'", AddID);
            SqlDataAdapter reader = new SqlDataAdapter(cmdTxt, con);
            DataTable dt = new DataTable();
            reader.Fill(dt);
            string[][] res = new string[dt.Rows.Count][];
            if (dt.Rows.Count != 0)
            {
                for (int i = 0; i < dt.Rows.Count; i++)
                {
                    res[i] = new string[2];
                    res[i][0] = dt.Rows[i][0].ToString();
                    res[i][1] = dt.Rows[i][1].ToString();
                }
            }
            con.Close();
            return res;
        }

        public static void Delete(string AddID, string ApprovedID)
        {
            SqlConnection con = new SqlConnection(conStr);
            con.Open();
            string cmdTxt = string.Format("Delete From ConnectUser Where Applicant_ID = '{0}' And U_ID= '{1}'", AddID, ApprovedID);
            SqlCommand cmd = new SqlCommand(cmdTxt, con);
            cmd.ExecuteNonQuery();
            con.Close();
        }
    }
}
