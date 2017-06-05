package com.appjar.dogbuster.dogbuster;

/**
 * Created by HP on 18-03-2017.
 */

        import android.app.ProgressDialog;
        import android.content.Context;
        import android.os.AsyncTask;
        import android.util.Log;
        import android.widget.TextView;

        import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStream;
        import java.io.InputStreamReader;
        import java.io.OutputStreamWriter;
        import java.net.HttpURLConnection;
        import java.net.MalformedURLException;
        import java.net.URL;
        import java.net.URLConnection;
        import java.net.URLEncoder;

/**
 * Created by Ascalonic on 2/11/2017.
 */


public class ServerInterface extends AsyncTask<String, Void, String> {


    private TextView statusField,roleField;
    String resp;
    private Context cont;
    private int myflag;
    private int byGetOrPost = 0;

    public AsyncResponse del;

    public ServerInterface(Context context,AsyncResponse mydel,int flag) {
        this.cont = context;
        this.del=mydel;
        resp=new String("");
        this.myflag=flag;
    }


    protected void onPreExecute(){
    }

    @Override
    protected String doInBackground(String... arg0) {

        String msg="";

        if(myflag==0)
        {
            try {
                String latitude = (String)arg0[0];
                String longitude = (String)arg0[1];

                String link = "http://dogbuster.in/check_warning.php";

                String data = URLEncoder.encode("lat", "UTF-8") + "=" +
                        URLEncoder.encode(latitude, "UTF-8");

                data+="&"+URLEncoder.encode("longi", "UTF-8") + "=" +
                        URLEncoder.encode(longitude, "UTF-8");

                Log.d(data,"CONN_TEST");

                URL url = new URL(link);

                URLConnection conn = url.openConnection();

                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                wr.write(data);
                wr.flush();

                BufferedReader reader = new BufferedReader(new
                        InputStreamReader(conn.getInputStream()));

                StringBuilder sb = new StringBuilder();
                String line = null;

                // Read Server Response
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                    break;
                }
                msg=sb.toString();
            } catch (Exception e) {

                msg= new String("Exception: " + e.getMessage());
            }
        }
        else if(myflag==1)
        {
            try {
                String email = (String)arg0[0];
                String password = (String)arg0[1];

                String link = "http://dogbuster.in/login.php";

                String data = URLEncoder.encode("mail_id", "UTF-8") + "=" +
                        URLEncoder.encode(email, "UTF-8");

                data+="&"+URLEncoder.encode("pwd", "UTF-8") + "=" +
                        URLEncoder.encode(password, "UTF-8");

                Log.d(data,"CONN_TEST");

                URL url = new URL(link);

                URLConnection conn = url.openConnection();

                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                wr.write(data);
                wr.flush();

                BufferedReader reader = new BufferedReader(new
                        InputStreamReader(conn.getInputStream()));

                StringBuilder sb = new StringBuilder();
                String line = null;

                // Read Server Response
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                    break;
                }
                msg=sb.toString();
            } catch (Exception e) {

                msg= new String("Exception: " + e.getMessage());
            }
        }

        else if(myflag==2)
        {
            try {

                String link = "http://dogbuster.in/getcount.php";
                URL url = new URL(link);

                URLConnection conn = url.openConnection();

                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                wr.write("");
                wr.flush();

                BufferedReader reader = new BufferedReader(new
                        InputStreamReader(conn.getInputStream()));

                StringBuilder sb = new StringBuilder();
                String line = null;

                // Read Server Response
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                    break;
                }
                msg=sb.toString();
            } catch (Exception e) {

                msg= new String("Exception: " + e.getMessage());
            }
        }

        else if(myflag==3)
        {
            try {

                String link = "http://dogbuster.in/getcoords.php";
                URL url = new URL(link);

                URLConnection conn = url.openConnection();

                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                wr.write("");
                wr.flush();

                BufferedReader reader = new BufferedReader(new
                        InputStreamReader(conn.getInputStream()));

                StringBuilder sb = new StringBuilder();
                String line = null;

                // Read Server Response
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                    break;
                }
                msg=sb.toString();
            } catch (Exception e) {

                msg= new String("Exception: " + e.getMessage());
            }
        }

        else if(myflag==4)
        {
            try {
                String latitude = (String)arg0[0];
                String longitude = (String)arg0[1];
                String range = (String)arg0[2];
                String danger_level = (String)arg0[3];

                String link = "http://dogbuster.in/addloc.php";

                String data = URLEncoder.encode("lat", "UTF-8") + "=" +
                        URLEncoder.encode(latitude, "UTF-8");

                data+="&"+URLEncoder.encode("longi", "UTF-8") + "=" +
                        URLEncoder.encode(longitude, "UTF-8");

                data+="&"+URLEncoder.encode("d_range", "UTF-8") + "=" +
                        URLEncoder.encode(range, "UTF-8");

                data+="&"+URLEncoder.encode("danger_level", "UTF-8") + "=" +
                        URLEncoder.encode(danger_level, "UTF-8");

                Log.d(data,"CONN_TEST");

                URL url = new URL(link);

                URLConnection conn = url.openConnection();

                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());


                wr.write(data);
                wr.flush();

                BufferedReader reader = new BufferedReader(new
                        InputStreamReader(conn.getInputStream()));

                StringBuilder sb = new StringBuilder();
                String line = null;

                // Read Server Response
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                    break;
                }
                msg=sb.toString();
            } catch (Exception e) {

                msg= new String("Exception:" +e.toString());
            }
        }

        else if(myflag==5)
        {
//            try {
//                String fname = (String)arg0[0];
//                String lname = (String)arg0[1];
//                String address = (String)arg0[2];
//                String phone = (String)arg0[3];
//                String mail_id = (String)arg0[4];
//                String pass = (String)arg0[5];
//                String cpass = (String)arg0[6];
//
//                String link = "http://dogbuster.in/dsignup.php";
//
//                String data = URLEncoder.encode("fname", "UTF-8") + "=" +
//                        URLEncoder.encode(fname, "UTF-8");
//
//                data+="&"+URLEncoder.encode("lname", "UTF-8") + "=" +
//                        URLEncoder.encode(lname, "UTF-8");
//
//                data+="&"+URLEncoder.encode("address", "UTF-8") + "=" +
//                        URLEncoder.encode(address, "UTF-8");
//
//                data+="&"+URLEncoder.encode("phone", "UTF-8") + "=" +
//                        URLEncoder.encode(phone, "UTF-8");
//
//                data+="&"+URLEncoder.encode("mail_id", "UTF-8") + "=" +
//                        URLEncoder.encode(mail_id, "UTF-8");
//
//                data+="&"+URLEncoder.encode("password", "UTF-8") + "=" +
//                        URLEncoder.encode(pass, "UTF-8");
//
//                data+="&"+URLEncoder.encode("confirm", "UTF-8") + "=" +
//                        URLEncoder.encode(cpass, "UTF-8");
//
//                Log.d(data,"CONN_TEST");
//
//                URL url = new URL(link);
//
//                URLConnection conn = url.openConnection();
//
//                //conn.setDoOutput(true);
//                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
//
//                wr.write(data);
//                wr.flush();
//
//                BufferedReader reader = new BufferedReader(new
//                        InputStreamReader(conn.getInputStream()));
//
//                StringBuilder sb = new StringBuilder();
//                String line = null;
//
//                // Read Server Response
//                while ((line = reader.readLine()) != null) {
//                    sb.append(line);
//                    break;
//                }
//                msg=sb.toString();
//            } catch (Exception e) {
//
//                msg= new String("Exception: " + e.getStackTrace());
//                e.printStackTrace();
//            }

            URL url;
            InputStream is = null;
            BufferedReader br;
            String line;

            try {

                String fname = (String)arg0[0];
                String lname = (String)arg0[1];
                String address = (String)arg0[2];
                String phone = (String)arg0[3];
                String mail_id = (String)arg0[4];
                String pass = (String)arg0[5];
                String cpass = (String)arg0[6];

                String data = URLEncoder.encode("fname", "UTF-8") + "=" +
                        URLEncoder.encode(fname, "UTF-8");

                data+="&"+URLEncoder.encode("lname", "UTF-8") + "=" +
                        URLEncoder.encode(lname, "UTF-8");

                data+="&"+URLEncoder.encode("address", "UTF-8") + "=" +
                        URLEncoder.encode(address, "UTF-8");

                data+="&"+URLEncoder.encode("phone", "UTF-8") + "=" +
                        URLEncoder.encode(phone, "UTF-8");

                data+="&"+URLEncoder.encode("mail_id", "UTF-8") + "=" +
                        URLEncoder.encode(mail_id, "UTF-8");

                data+="&"+URLEncoder.encode("password", "UTF-8") + "=" +
                        URLEncoder.encode(pass, "UTF-8");

                data+="&"+URLEncoder.encode("confirm", "UTF-8") + "=" +
                        URLEncoder.encode(cpass, "UTF-8");

                url = new URL("http://dogbuster.in/dsignup.php?"+data);


                is = url.openStream();  // throws an IOException
                br = new BufferedReader(new InputStreamReader(is));

                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                    msg+=line;
                }
            } catch (MalformedURLException mue) {
                mue.printStackTrace();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            } finally {
                try {
                    if (is != null) is.close();
                } catch (IOException ioe) {
                    // nothing to see here
                }
            }
        }
        else if(myflag==6)
        {
            URL url;
            InputStream is = null;
            BufferedReader br;
            String line;

            try {

                String mail_id = (String)arg0[0];

                String data = URLEncoder.encode("mail_id", "UTF-8") + "=" +
                        URLEncoder.encode(mail_id, "UTF-8");

                url = new URL("http://dogbuster.in/forgot.php?"+data);

                is = url.openStream();  // throws an IOException
                br = new BufferedReader(new InputStreamReader(is));

                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                    msg+=line;
                }
            } catch (MalformedURLException mue) {
                mue.printStackTrace();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            } finally {
                try {
                    if (is != null) is.close();
                } catch (IOException ioe) {
                    // nothing to see here
                }
            }
        }

        return msg;

    }

    @Override
    protected void onPostExecute(String result){
        del.processFinish(result);
    }
}