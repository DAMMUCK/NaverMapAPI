package com.kunsan.ac.kr.mapsosua;

import androidx.annotation.NonNull;
import androidx.annotation.UiThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.graphics.PointF;
import android.icu.text.IDNA;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.textclassifier.TextClassifierEvent;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    //버튼 눌렀는지 check하는 변수
    boolean check1 = false;
    boolean check2 = false;
    boolean check3 = false;

    NaverMap myMap;
    //지오코드 객체 선언
  // Geocoder geocoder = new Geocoder(this);

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


        //choice버튼 클릭시 안보이던 버튼들이 보이고 다시 한번 클릭하면 버튼들이 안보인다.
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
                if (check1==false){
                    myMap.setMapType(NaverMap.MapType.Basic);
                    Toast.makeText(getApplicationContext(),"기본지도띄우기",Toast.LENGTH_SHORT).show();
                    check1=true;
                }else{
                    myMap.setMapType(NaverMap.MapType.Navi);
                    Toast.makeText(getApplicationContext(),"네비지도띄우기",Toast.LENGTH_SHORT).show();
                    check1=false;
                }
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check2==false){
                    myMap.setMapType(NaverMap.MapType.Satellite);
                    Toast.makeText(getApplicationContext(),"위성지도띄우기",Toast.LENGTH_SHORT).show();
                    check2=true;
                }else{
                    myMap.setMapType(NaverMap.MapType.Navi);
                    Toast.makeText(getApplicationContext(),"네비지도띄우기",Toast.LENGTH_SHORT).show();
                    check2=false;
                }

            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check3==false){
                    myMap.setMapType(NaverMap.MapType.Terrain);
                    Toast.makeText(getApplicationContext(),"terrain 지도띄우기",Toast.LENGTH_SHORT).show();
                    check3=true;
                }else{
                    myMap.setMapType(NaverMap.MapType.Navi);
                    Toast.makeText(getApplicationContext(),"네비지도띄우기",Toast.LENGTH_SHORT).show();
                    check3=false;
                }
            }
        });



    }



    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        this.myMap = naverMap;
        myMap.setMapType(NaverMap.MapType.Navi);
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

        myMap.setOnMapClickListener(new NaverMap.OnMapClickListener() {//지도 클릭시
            @Override
            public void onMapClick(@NonNull PointF pointF, @NonNull LatLng latLng) {
                //지도를 클릭시 클릭한 부분의 위도와 경도를 Toast로 출력
                String text = "latitude = "+latLng.latitude+" longitude = "+latLng.longitude;
                Toast.makeText(getApplicationContext(),text,Toast.LENGTH_SHORT).show();


/*
                List<Address> list = null;
                try{
                    list = geocoder.getFromLocation(
                            latLng.latitude,
                            latLng.longitude,
                            10);
                }catch (IOException e){
                    e.printStackTrace();
                    Log.e("test","입출력 오류 - 서버에서 주소변환시 에러 발생");
                }
                if(list != null){
                    if(list.size()==0){
                        Toast.makeText(getApplicationContext(),"해당되는 주소정보는 없습니다",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(),list.get(0).getPostalCode(),Toast.LENGTH_SHORT).show();
                    }
                }*/
            }
        });

    }


}