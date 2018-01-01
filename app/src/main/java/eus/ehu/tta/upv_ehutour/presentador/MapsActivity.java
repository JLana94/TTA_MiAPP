package eus.ehu.tta.upv_ehutour.presentador;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import eus.ehu.tta.upv_ehutour.R;
import eus.ehu.tta.upv_ehutour.modelo.DataParser;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationManager locationManager;
    private String provider;
    private LatLng miUbicacion;
    private LatLng destino;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        checkPermission();


        Log.d("Mi ubicacion","Hola");



    }

    private void checkPermission()
    {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            //mMap.setMyLocationEnabled(true);

            requestLocation();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    99);

        }

    }

    @SuppressLint("MissingPermission")
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 99) {
            if (permissions.length == 1 &&
                    permissions[0] == Manifest.permission.ACCESS_FINE_LOCATION &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);
                requestLocation();
            } else {
                Toast.makeText(this,getResources().getString(R.string.permisoDenegado),Toast.LENGTH_SHORT).show();// Permission was denied. Display an error message.
            }
        }
    }

    @SuppressLint("MissingPermission")
    private void requestLocation(){
        Log.d("Mi Ubicación", "Aqui si");
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, true);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 0, mListener);
        Log.d("Mi Ubicación", "2");
        //@SuppressLint("MissingPermission") Location location = locationManager.getLastKnownLocation(provider);
        Log.d("Mi Ubicación", "Antes de conseguir ubicación");
        //miUbicacion=new LatLng(location.getLatitude(),location.getLongitude());
        Log.d("Mi Ubicación", "Después de conseguir ubicación");
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(43.3295184, -2.960398);
        LatLng sydney2 = new LatLng(43.3320669, -2.9720285);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.addMarker(new MarkerOptions().position(sydney2).title("Marker in Sydney2"));

        //String url = "http://maps.googleapis.com/maps/api/directions/json?origin=" +miUbicacion.latitude+ "," + miUbicacion.longitude  + "&destination=" +sydney2.latitude+ "," + sydney2.longitude  + "&sensor=false&units=metric";
        //FetchUrl FetchUrl = new FetchUrl();

        // Start downloading json data from Google Directions API
        //FetchUrl.execute(url);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

    }


    private LocationListener mListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            /*Log.d("Mi Ubicación", "Antes de conseguir ubicación");
            miUbicacion=new LatLng(location.getLatitude(),location.getLongitude());
            Log.d("Mi Ubicación", "Después de conseguir ubicación");
            // Add a marker in Sydney and move the camera
            LatLng sydney = new LatLng(43.3295184, -2.960398);
            LatLng sydney2 = new LatLng(43.3320669, -2.9720285);
            mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
            mMap.addMarker(new MarkerOptions().position(sydney2).title("Marker in Sydney2"));

            String url = "http://maps.googleapis.com/maps/api/directions/json?origin=" +miUbicacion.latitude+ "," + miUbicacion.longitude  + "&destination=" +sydney2.latitude+ "," + sydney2.longitude  + "&sensor=false&units=metric";
            FetchUrl FetchUrl = new FetchUrl();

            // Start downloading json data from Google Directions API
            FetchUrl.execute(url);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/



        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }


    };







    private class FetchUrl extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try {
                // Fetching the data from web service
                data = downloadUrl(url[0]);
                Log.d("Background Task data", data.toString());
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);

        }
    }

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();
            Log.d("downloadUrl", data.toString());
            br.close();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                Log.d("ParserTask",jsonData[0].toString());
                DataParser parser = new DataParser();
                Log.d("ParserTask", parser.toString());

                // Starts parsing data
                routes = parser.parse(jObject);
                Log.d("ParserTask","Executing routes");
                Log.d("ParserTask",routes.toString());

            } catch (Exception e) {
                Log.d("ParserTask",e.toString());
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points;
            PolylineOptions lineOptions = null;

            // Traversing through all the routes
            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList<>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(10);
                lineOptions.color(Color.RED);

                Log.d("onPostExecute","onPostExecute lineoptions decoded");

            }

            // Drawing polyline in the Google Map for the i-th route
            if(lineOptions != null) {
                mMap.addPolyline(lineOptions);
            }
            else {
                Log.d("onPostExecute","without Polylines drawn");
            }
        }



    }





}
