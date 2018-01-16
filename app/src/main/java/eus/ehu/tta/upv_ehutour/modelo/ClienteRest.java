package eus.ehu.tta.upv_ehutour.modelo;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

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


}
