package eus.ehu.tta.upv_ehutour.presentador;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import eus.ehu.tta.upv_ehutour.modelo.ClienteRest;
import eus.ehu.tta.upv_ehutour.modelo.Foto;
import eus.ehu.tta.upv_ehutour.modelo.User;
import eus.ehu.tta.upv_ehutour.vista.LoginActivity;

/**
 * Created by josu on 16/01/18.
 */

public class Data {

    private final String URL_SERVER="http://u017633.ehu.eus:28080/ServidorRemoto";
    private final String UPLOAD_FILE="rest/TourEHU/uploadFile";
    private final String REQUEST_FOTOS="rest/TourEHU/requestFotos";
    private final String ADD_FOTO="rest/TourEHU/addFoto";
    private final String ADD_USUARIO="rest/TourEHU/addUsuario";
    private final String CHECK_LOGIN="rest/TourEHU/checkLogin";

    ClienteRest rest;

    public Data()
    {
        rest=new ClienteRest(URL_SERVER);
    }




    public Boolean registro(User usuario) {

        JSONObject usuarioJSON=new JSONObject();
        String responseMessage="";

        try {
            usuarioJSON.put("idUsuario",1);
            usuarioJSON.put("usuario",usuario.getLogin());
            usuarioJSON.put("contrasena",usuario.getPassword());
            usuarioJSON.put("nombre",usuario.getNombre());
            usuarioJSON.put("apellido",usuario.getApellido());
            usuarioJSON.put("centroPrevio",usuario.getCentroPrevio());
            usuarioJSON.put("telefono",usuario.getTelefono());

            responseMessage=rest.postJSON(usuarioJSON,ADD_USUARIO);
            Log.d("Control",responseMessage);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(responseMessage.equals("OK"))
            return true;
        else
            return false;


    }

    public List<String> pedirFotos()
    {
        List<String> fotos=new ArrayList<>();
        try {
            JSONObject fotosJSON=new JSONObject(rest.getJson(REQUEST_FOTOS));
            JSONArray array=fotosJSON.getJSONArray("fotos");
            for(int i=0;i<array.length();i++)
            {
                JSONObject foto=(JSONObject) array.get(i);
                fotos.add(foto.getString("nombre"));
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return fotos;
    }

    public boolean checkLogin(String login,String pass)
    {
        String response="";
        try {
            response=rest.getString(CHECK_LOGIN,login,pass);


        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(response.equals("OK"))
            return true;
        else
            return false;
    }

    public boolean enviarFoto(Foto foto, InputStream is,String filename)
    {
        JSONObject fotoJSON=new JSONObject();
        String responseMessageJSON="";
        String responseMessageFile="";
        try {
            fotoJSON.put("nombre",foto.getNombre());
            fotoJSON.put("timestamp",foto.getTimestamp());
            fotoJSON.put("usuario",foto.getUsuario());
            responseMessageJSON=rest.postJSON(fotoJSON,ADD_FOTO);
            Log.d("Control","Sube el JSON");
            responseMessageFile=rest.postFile(UPLOAD_FILE,is,filename);
            Log.d("Control","Sube la foto");

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d("Response message file",responseMessageFile);
        Log.d("Response message JSON",responseMessageJSON);
        return false;


    }
}
