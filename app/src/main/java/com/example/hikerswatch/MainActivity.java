package com.example.hikerswatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    LocationManager locationManager;
    LocationListener locationListener;
    Location l;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,10000,0,locationListener);
                l=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        locationManager =(LocationManager)this.getSystemService(LOCATION_SERVICE);
        locationListener=new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.i("Inside","onLocationUpdate()");

                l=location;
                String address="";
                Geocoder geo=new Geocoder(getApplicationContext(), Locale.getDefault());
                List<Address> lis= null;
                try {
                    lis = geo.getFromLocation(l.getLatitude(),l.getLongitude(),1);
                } catch (Exception e) {
                    Log.i("Exception",e.toString());
                    e.printStackTrace();
                }
                if(lis.get(0).getThoroughfare()!=null){
                    address=address+lis.get(0).getThoroughfare()+",";
                }
                if(lis.get(0).getLocality()!=null){
                    address=address+lis.get(0).getLocality()+"\n";
                }
                if(lis.get(0).getPostalCode()!=null){
                    address=address+lis.get(0).getPostalCode()+" ";
                }
                if(lis.get(0).getAdminArea()!=null){
                    address=address+lis.get(0).getAdminArea()+" ";
                }
                Log.i("Address",address);

                TextView textView2=(TextView)findViewById(R.id.textView2);
                TextView textView3=(TextView)findViewById(R.id.textView3);
                TextView textView4=(TextView)findViewById(R.id.textView4);
                TextView textView5=(TextView)findViewById(R.id.textView5);
                TextView textView7=(TextView)findViewById(R.id.textView7);
                textView2.setText("Latitude:"+Double.toString(l.getLatitude()));
                textView3.setText("Longitude:"+Double.toString(l.getLongitude()));
                textView4.setText("Accuracy:"+Double.toString(l.getAccuracy()));
                textView5.setText("Altitude:"+Double.toString(l.getAltitude()));
                textView7.setText(address);
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

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        }else{
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,10000,0,locationListener);
            l=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }

        TextView textView2=(TextView)findViewById(R.id.textView2);
        TextView textView3=(TextView)findViewById(R.id.textView3);
        TextView textView4=(TextView)findViewById(R.id.textView4);
        TextView textView5=(TextView)findViewById(R.id.textView5);
        TextView textView7=(TextView)findViewById(R.id.textView7);

        try{
            if(l!=null){
                String address="";
                Geocoder geo=new Geocoder(this, Locale.getDefault());
                List<Address> lis=geo.getFromLocation(l.getLatitude(),l.getLongitude(),1);
                if(lis.get(0).getThoroughfare()!=null){
                    address=address+lis.get(0).getThoroughfare()+",";
                }
                if(lis.get(0).getLocality()!=null){
                    address=address+lis.get(0).getLocality()+"\n";
                }
                if(lis.get(0).getPostalCode()!=null){
                    address=address+lis.get(0).getPostalCode()+" ";
                }
                if(lis.get(0).getAdminArea()!=null){
                    address=address+lis.get(0).getAdminArea()+" ";
                }
                Log.i("Address",address);

                textView2.setText("Latitude:"+Double.toString(l.getLatitude()));
                textView3.setText("Longitude:"+Double.toString(l.getLongitude()));
                textView4.setText("Accuracy:"+Double.toString(l.getAccuracy()));
                textView5.setText("Altitude:"+Double.toString(l.getAltitude()));
                textView7.setText(address);
            }

        }catch(Exception e){
            Log.i("Exception",e.toString());
            e.printStackTrace();
        }

    }


}