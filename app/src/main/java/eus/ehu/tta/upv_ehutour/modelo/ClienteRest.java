package eus.ehu.tta.upv_ehutour.modelo;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by josu on 16/01/18.
 */

public class ClienteRest {

    private final String baseURL;



    public ClienteRest(String url)
    {
        baseURL=url;
    }

    private HttpURLConnection getConnection(String path) throws IOException {
        URL url=new URL(baseURL+"/"+path);
        HttpURLConnection connection=(HttpURLConnection) url.openConnection();
        connection.setUseCaches(false);
        return connection;
    }
    private HttpURLConnection getConnectionQuery(String path, String login, String pass) throws IOException {
        URL url=new URL(baseURL+"/"+path+"?login="+login+"&password="+pass);
        HttpURLConnection connection=(HttpURLConnection) url.openConnection();
        connection.setUseCaches(false);
        return connection;
    }
    public String getJson (String path) throws IOException, JSONException
    {
        HttpURLConnection conn=null;
        try {
            conn=getConnection(path);
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                return br.readLine();

            }finally {
                if (conn!=null)
                {
                    conn.disconnect();
                }
            }
        }finally {
            if (conn!=null)
            {
                conn.disconnect();
            }
        }
    }

    public String getString (String path, String login, String pass) throws IOException, JSONException
    {
        HttpURLConnection conn=null;
        try {
            conn=getConnectionQuery(path,login,pass);
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String readLine =br.readLine();
                return readLine;

            }finally {
                if (conn!=null)
                {
                    conn.disconnect();
                }
            }
        }finally {
            if (conn!=null)
            {
                conn.disconnect();
            }
        }
    }

    public String postJSON(final JSONObject json, String path) throws IOException
    {
        HttpURLConnection conn=null;
        try
        {
            conn=getConnection(path);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type","application/json; charset=UTF-8");
            PrintWriter pw=new PrintWriter(conn.getOutputStream());
            pw.print(json.toString());
            pw.close();
            Log.d("Control",conn.getResponseMessage());
            return conn.getResponseMessage();
        }finally {
            if (conn!=null)
                conn.disconnect();
        }
    }
    public String postFile(String path, InputStream is, String fileName) throws IOException{
        String boundary=Long.toString(System.currentTimeMillis());
        String newLine="\r\n";
        String prefix="--";
        HttpURLConnection connection=null;

        try
        {
            connection=getConnection(path);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type","multipart/form-data;boundary="+boundary);
            connection.setDoOutput(true);
            DataOutputStream out =new DataOutputStream(connection.getOutputStream());
            out.writeBytes(prefix+boundary+newLine);
            out.writeBytes("Content-Disposition: form-data; name=\"filetype\""+newLine);
            out.writeBytes(newLine);
            out.writeBytes("img"+newLine);
            out.writeBytes(prefix+boundary+newLine);
            out.writeBytes("Content-Disposition: form-data; name=\"file\";filename=\""+fileName+"\""+newLine);
            out.writeBytes("Content-Type: image/jpg"+newLine);//Aqui poner content type imagen para que me lo coja mi servidor
            out.writeBytes(newLine);
            byte[] data=new byte[1024*1024];
            int len;
            while ((len=is.read(data))>0) {
                out.write(data, 0, len);
                Log.d("Control", "1");
            }
            out.writeBytes(newLine);
            out.writeBytes(prefix+boundary+prefix+newLine);
            out.close();
            return connection.getResponseMessage();
        }finally {
            if (connection!=null)
                connection.disconnect();
        }
    }


}
