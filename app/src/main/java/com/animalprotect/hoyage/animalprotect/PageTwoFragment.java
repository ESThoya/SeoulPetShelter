package com.animalprotect.hoyage.animalprotect;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class PageTwoFragment extends Fragment {

    private ListView listView;
    private SimpleAdapter adapter;
    private Button btnSearch_hospital_pharmacy;
    private Button.OnClickListener onClickListener;
    private View.OnClickListener listener;
    private ProgressDialog progressDialog;
    private List<HashMap<String, String>> hosiptalList;

    public PageTwoFragment() {
        // Required empty public constructor
    }

    public static PageTwoFragment newInstance() {
        Bundle args = new Bundle();

        PageTwoFragment fragment = new PageTwoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        onClickListener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btnSearch_hospital_pharmacy:

                        progressDialog = new ProgressDialog(v.getContext());
                        progressDialog.setMessage("조회 중입니다..");
                        progressDialog.show();

                        String keyword = "";
                        getJSON(keyword);

                        break;

                }
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 버튼 초기화
        View view = inflater.inflate(R.layout.fragment_page_two, container,  false);
        StrictMode.enableDefaults();

        btnSearch_hospital_pharmacy = (Button)view.findViewById(R.id.btnSearch_hospital_pharmacy);
        btnSearch_hospital_pharmacy.setOnClickListener(onClickListener);

        // Inflate the layout for this fragment
        //FrameLayout layout = (FrameLayout)inflater.inflate(R.layout.fragment_page_two, container, false);
        listView = (ListView)view.findViewById(R.id.secondListview);

        // 리스트뷰 항목 생성
        hosiptalList = new ArrayList<HashMap<String, String>>();

        String[] from = new String[]{"NM", "ADDR", "TEL", "STATE"};
        int[] to = new int[]{R.id.hospital_name, R.id.hospital_address,
                R.id.hospital_tel, R.id.hospital_state};
        adapter = new SimpleAdapter(getContext(), hosiptalList, R.layout.hosiptal_items, from, to);
        listView.setAdapter(adapter);

        return view;
    }

    public void getJSON(final String keyword) {
        if(keyword == null) return;

        Handler mHandler = new Handler(Looper.myLooper());
        mHandler.postDelayed(new Runnable() {
            String result = "";
            @Override
            public void run() {
                BufferedReader br = null;

                try {
                    URL url = new URL("http://openapi.seoul.go.kr:8088/676648455a67776135367351797858/json/vtrHospitalInfo/1/20/");

                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("GET");

                    br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8"));
                    String line;
                    while((line = br.readLine()) != null) {
                        result = result + line + "\n";
                    }

                    br.close();
                    httpURLConnection.disconnect();

                }catch(Exception e) {
                    result = e.toString();
                }


                if (jsonParser(result)){
                    progressDialog.dismiss();
                    adapter.notifyDataSetChanged();

                    Toast.makeText(getContext(), "병원조회 완료", Toast.LENGTH_SHORT).show();
                }
            }
        }, 0);

        /*Thread thread = new Thread(new Runnable() {
            String result = "";

            @Override
            public void run() {
                BufferedReader br = null;

                try {
                    URL url = new URL("http://openapi.seoul.go.kr:8088/676648455a67776135367351797858/json/vtrHospitalInfo/1/5/");

                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("GET");

                    br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8"));
                    String line;
                    while((line = br.readLine()) != null) {
                        result = result + line + "\n";
                    }

                    br.close();
                    httpURLConnection.disconnect();

                }catch(Exception e) {
                    result = e.toString();
                }


                if (jsonParser(result)){
                    Toast.makeText(getContext(), "병원조회 완료", Toast.LENGTH_SHORT).show();
                }

                *//*Handler mHandler = new Handler(Looper.myLooper());
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (jsonParser(result)){
                            Toast.makeText(getContext(), "병원조회 완료", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, 3000);*//*


            }
        });

        thread.start();*/
    }

    public boolean jsonParser(String jsonString){
        if (jsonString == null ) return false;

        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONObject photos = jsonObject.getJSONObject("vtrHospitalInfo");
            JSONArray photo = photos.getJSONArray("row");

            hosiptalList.clear();

            for (int i = 0; i < photo.length(); i++) {
                JSONObject photoInfo = photo.getJSONObject(i);

                String name = photoInfo.getString("NM");
                String addr = photoInfo.getString("ADDR");
                String tel = "02-" + photoInfo.getString("TEL"); // 서울지역번호(02) 붙이기
                String state = photoInfo.getString("STATE");

                HashMap<String, String> photoinfoMap = new HashMap<String, String>();
                photoinfoMap.put("NM", name);
                photoinfoMap.put("ADDR", addr);
                photoinfoMap.put("TEL", tel);
                photoinfoMap.put("STATE", state);

                hosiptalList.add(photoinfoMap);

            }

            Log.d("TEST", hosiptalList.toString());

            return true;
        } catch (JSONException e) {

            Log.d("TEST", e.toString() );
        }

        return false;
    }

}
