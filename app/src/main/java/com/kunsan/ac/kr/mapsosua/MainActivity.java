package com.kunsan.ac.kr.mapsosua;

import androidx.annotation.NonNull;
import androidx.annotation.UiThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.icu.text.IDNA;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.geometry.Utmk;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.InfoWindow;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    NaverMap myMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button choice = (Button) findViewById(R.id.choice);
        Button btn1 = (Button)findViewById(R.id.basic);
        Button btn2 = (Button)findViewById(R.id.satellite);
        Button btn3 = (Button)findViewById(R.id.terrain);
        LinearLayout layout = (LinearLayout)findViewById(R.id.btnLayout);

        FragmentManager fm = getSupportFragmentManager();
        MapFragment mapFragment = (MapFragment) fm.findFragmentById(R.id.map);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            fm.beginTransaction().add(R.id.map, mapFragment).commit();
        }

        mapFragment.getMapAsync(this);

        choice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(layout.getVisibility()==View.INVISIBLE){
                    layout.setVisibility(View.VISIBLE);
                }else{
                    layout.setVisibility(View.INVISIBLE);
                }
                    
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myMap.setMapType(NaverMap.MapType.Basic);
                Toast.makeText(getApplicationContext(),"기본지도띄우기",Toast.LENGTH_SHORT).show();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myMap.setMapType(NaverMap.MapType.Satellite);
                Toast.makeText(getApplicationContext(),"위성지도띄우기",Toast.LENGTH_SHORT).show();
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myMap.setMapType(NaverMap.MapType.Terrain);
                Toast.makeText(getApplicationContext(),"terrain 지도띄우기",Toast.LENGTH_SHORT).show();
            }
        });



    }



    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        this.myMap = naverMap;
        myMap.setMapType(NaverMap.MapType.Basic);
        CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(35.9452863,126.6799643));
        naverMap.moveCamera(cameraUpdate);
        Toast.makeText(this.getApplicationContext(),"지도띄우기",Toast.LENGTH_SHORT).show();

        //마커띄우기
        Marker marker = new Marker();
        marker.setPosition(new LatLng(35.9452863,126.6799643));
        marker.setMap(naverMap);
        Marker marker2 = new Marker();
        marker2.setPosition(new LatLng(35.9408996,126.6864375));
        marker2.setMap(naverMap);
        Marker marker3 = new Marker();
        marker3.setPosition(new LatLng(35.9417,126.6895613));
        marker3.setMap(naverMap);

        //정보창
        InfoWindow infoWindow = new InfoWindow();
        infoWindow.setAdapter(new InfoWindow.DefaultTextAdapter(this) {
            @NonNull
            @Override
            public CharSequence getText(@NonNull InfoWindow infoWindow) {
                return "정보 창";
            }
        });

        //마커클릭시 이벤트
        Overlay.OnClickListener listener = overlay -> {
            Marker mark = (Marker)overlay;

            if (mark.getInfoWindow() == null) {
                // 현재 마커에 정보 창이 열려있지 않을 경우 엶
                infoWindow.open(mark);
            } else {
                // 이미 현재 마커에 정보 창이 열려있을 경우 닫음
                infoWindow.close();
            }

            return true;
        };

        marker.setOnClickListener(listener);
        marker2.setOnClickListener(listener);
        marker3.setOnClickListener(listener);

    }

    public void modifyMethod(){

    }

    public void clickMarker(){

    }
}