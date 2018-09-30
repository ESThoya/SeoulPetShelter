package com.animalprotect.hoyage.animalprotect;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class PageOneFragment extends Fragment {
    private ListView listview;
    private ArrayList<String> mList;
    private ArrayAdapter<String> adapter;
    private Button btnSearch;
    private int mYear, mMonth, mDay;
    private Button.OnClickListener onClickListener;
    private ListViewAdapter listViewAdapter;

    public PageOneFragment() {

    }

    public static PageOneFragment newInstance() {
        Bundle args = new Bundle();

        PageOneFragment fragment = new PageOneFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        // Adapter 생성
        listViewAdapter = new ListViewAdapter();

        //현재 날짜와 시간을 가져오기위한 Calendar 인스턴스 선언
        Calendar cal = new GregorianCalendar();
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);

        onClickListener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btnSearch:
                        final SearchDialog sDialog = new SearchDialog();
                        sDialog.setTargetFragment(PageOneFragment.this, 0);
                        sDialog.show(getFragmentManager(), "Search Dialog");
                        sDialog.setDialogResult(new SearchDialog.OnMyDialogResult() {
                            // SearchDialog에서 보내진 값을 받는 부분
                            @Override
                            public void finish(String stDate, String edDate) {
                                listViewAdapter.clearAll();
                                reloadList(stDate, edDate);
                                sDialog.dismiss();
                            }
                        });
                        break;

                    case R.id.endDate:
                        new DatePickerDialog(getContext(), mDateSetListener, mYear, mMonth, mDay).show();
                        break;

                    case R.id.startDate:
                        new DatePickerDialog(getContext(), mDateSetListener, mYear, mMonth, mDay).show();

                        break;
                }
            }
        };

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_page_one, null);
        //FrameLayout view = (FrameLayout)inflater.inflate(R.layout.fragment_page_one, container, false);

        StrictMode.enableDefaults();

        // 버튼 초기화
        btnSearch = (Button)view.findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(onClickListener);

        /*ListViewAdapter adapter;

        // Adapter 생성
        adapter = new ListViewAdapter();*/


        BufferedReader br = null; // XML파일을 읽어들이는 단위

        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) view.findViewById(R.id.fragment_animal_list);
        listview.setAdapter(listViewAdapter);

        boolean initem = false, inAge = false, inCareAddr = false, inCareNm = false, inCareTel = false;
        boolean inChargeNm = false, inColorCd = false, inDesertionNo = false, inFilename = false, inHappenDt = false;
        boolean inHappenPlace = false, inKindCd = false;

        String age = null, careAddr = null, careNm = null, careTel = null, chargeNm = null, colorCd = null, desertionNo = null, filename = null;
        String happenDt = null, happenPlace = null, kindCd = null;
        String start_date = "20180901";
        String end_date = "20190929";

        try {
            URL url = new URL(
                    "http://openapi.animal.go.kr/openapi/service/rest/abandonmentPublicSrvc/abandonmentPublic?"
                    + "bgnde=" + start_date + "&endde=" + end_date
                    + "&pageNo=1&numOfRows=50&ServiceKey="
                    + "QBBSYEb2mo%2FtLgCL9UJsvRrsZ%2B9xZtixJuCL19qYwRUk10N188jyKBxQrRzR2VMWeNtLZJgfR7YbGqCzv6uEyw%3D%3D"
            ); //검색 URL부분

            InputStream is = url.openStream();

            Log.d("TEST", url.toString());

            XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserCreator.newPullParser();
            parser.setInput(url.openStream(), null);

            int parserEvent = parser.getEventType();
            Log.d(this.getClass().getName(), "파싱 시작");

            while (parserEvent != XmlPullParser.END_DOCUMENT) {
                switch (parserEvent) {
                    case XmlPullParser.START_TAG://parser가 시작 태그를 만나면 실행
                        if (parser.getName().equals("age")) { //title 만나면 내용을 받을수 있게 하자
                            inAge = true;
                        }
                        if (parser.getName().equals("careAddr")) { //address 만나면 내용을 받을수 있게 하자
                            inCareAddr = true;
                        }
                        if (parser.getName().equals("careNm")) { //mapx 만나면 내용을 받을수 있게 하자
                            inCareNm = true;
                        }
                        if (parser.getName().equals("careTel")) { //mapy 만나면 내용을 받을수 있게 하자
                            inCareTel = true;
                        }
                        if (parser.getName().equals("chargeNm")) { //mapy 만나면 내용을 받을수 있게 하자
                            inChargeNm = true;
                        }
                        if (parser.getName().equals("colorCd")) { //mapy 만나면 내용을 받을수 있게 하자
                            inColorCd = true;
                        }
                        if (parser.getName().equals("desertionNo")) { //mapy 만나면 내용을 받을수 있게 하자
                            inDesertionNo = true;
                        }
                        if (parser.getName().equals("filename")) { //mapy 만나면 내용을 받을수 있게 하자
                            inFilename = true;
                        }
                        if (parser.getName().equals("happenDt")) { //mapy 만나면 내용을 받을수 있게 하자
                            inHappenDt = true;
                        }
                        if (parser.getName().equals("happenPlace")) { //mapy 만나면 내용을 받을수 있게 하자
                            inHappenPlace = true;
                        }
                        if (parser.getName().equals("kindCd")) { //mapy 만나면 내용을 받을수 있게 하자
                            inKindCd = true;
                        }
                        if (parser.getName().equals("message")) { //message 태그를 만나면 에러 출력
                            //text.setText(text.getText() + "에러");
                            //여기에 에러코드에 따라 다른 메세지를 출력하도록 할 수 있다.
                        }
                        break;

                    case XmlPullParser.TEXT://parser가 내용에 접근했을때
                        if (inAge) { //isTitle이 true일 때 태그의 내용을 저장.
                            age = parser.getText();
                            inAge = false;
                        }
                        if (inCareAddr) { //isAddress이 true일 때 태그의 내용을 저장.
                            careAddr = parser.getText();
                            inCareAddr = false;
                        }
                        if (inCareNm) { //isMapx이 true일 때 태그의 내용을 저장.
                            careNm = parser.getText();
                            inCareNm = false;
                        }
                        if (inCareTel) { //isMapy이 true일 때 태그의 내용을 저장.
                            careTel = parser.getText();
                            inCareTel = false;
                        }
                        if (inChargeNm) { //isMapy이 true일 때 태그의 내용을 저장.
                            chargeNm = parser.getText();
                            inChargeNm = false;
                        }
                        if (inColorCd) { //isMapy이 true일 때 태그의 내용을 저장.
                            colorCd = parser.getText();
                            inColorCd = false;
                        }
                        if (inDesertionNo) { //isMapy이 true일 때 태그의 내용을 저장.
                            desertionNo = parser.getText();
                            inDesertionNo = false;
                        }
                        if (inFilename) { //isMapy이 true일 때 태그의 내용을 저장.
                            filename = parser.getText();
                            inFilename = false;
                        }
                        if (inHappenDt) { //isMapy이 true일 때 태그의 내용을 저장.
                            happenDt = parser.getText();
                            inHappenDt = false;
                        }
                        if (inHappenPlace) { //isMapy이 true일 때 태그의 내용을 저장.
                            happenPlace = parser.getText();
                            inHappenPlace = false;
                        }
                        if (inKindCd) { //isMapy이 true일 때 태그의 내용을 저장.
                            kindCd = parser.getText();
                            inKindCd = false;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals("item")) {
                            listViewAdapter.addItem(filename, desertionNo, kindCd, colorCd, happenDt, careAddr);
                            initem = false;
                        }
                        break;
                }
                parserEvent = parser.next();
            }
        } catch (Exception e) {
            Log.d("TEST", "에러발생");
            //text.setText("에러가..났습니다...");
        }
        return view;

    }

    public void reloadList(String stDate, String edDate) {
        boolean initem = false, inAge = false, inCareAddr = false, inCareNm = false, inCareTel = false;
        boolean inChargeNm = false, inColorCd = false, inDesertionNo = false, inFilename = false, inHappenDt = false;
        boolean inHappenPlace = false, inKindCd = false;

        String age = null, careAddr = null, careNm = null, careTel = null, chargeNm = null, colorCd = null, desertionNo = null, filename = null;
        String happenDt = null, happenPlace = null, kindCd = null;
        String start_date = "20180901";
        String end_date = "20190929";

        if(stDate != null) {
            start_date = stDate;
        }

        if(edDate != null) {
            end_date = edDate;
        }

        try {
            URL url = new URL(
                    "http://openapi.animal.go.kr/openapi/service/rest/abandonmentPublicSrvc/abandonmentPublic?"
                            + "bgnde=" + start_date + "&endde=" + end_date
                            + "&pageNo=1&numOfRows=50&ServiceKey="
                            + "QBBSYEb2mo%2FtLgCL9UJsvRrsZ%2B9xZtixJuCL19qYwRUk10N188jyKBxQrRzR2VMWeNtLZJgfR7YbGqCzv6uEyw%3D%3D"
            ); //검색 URL부분

            InputStream is = url.openStream();

            Log.d("TEST", url.toString());

            XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserCreator.newPullParser();
            parser.setInput(url.openStream(), null);

            int parserEvent = parser.getEventType();
            Log.d(this.getClass().getName(), "파싱 시작");

            while (parserEvent != XmlPullParser.END_DOCUMENT) {
                switch (parserEvent) {
                    case XmlPullParser.START_TAG://parser가 시작 태그를 만나면 실행
                        if (parser.getName().equals("age")) { //title 만나면 내용을 받을수 있게 하자
                            inAge = true;
                        }
                        if (parser.getName().equals("careAddr")) { //address 만나면 내용을 받을수 있게 하자
                            inCareAddr = true;
                        }
                        if (parser.getName().equals("careNm")) { //mapx 만나면 내용을 받을수 있게 하자
                            inCareNm = true;
                        }
                        if (parser.getName().equals("careTel")) { //mapy 만나면 내용을 받을수 있게 하자
                            inCareTel = true;
                        }
                        if (parser.getName().equals("chargeNm")) { //mapy 만나면 내용을 받을수 있게 하자
                            inChargeNm = true;
                        }
                        if (parser.getName().equals("colorCd")) { //mapy 만나면 내용을 받을수 있게 하자
                            inColorCd = true;
                        }
                        if (parser.getName().equals("desertionNo")) { //mapy 만나면 내용을 받을수 있게 하자
                            inDesertionNo = true;
                        }
                        if (parser.getName().equals("filename")) { //mapy 만나면 내용을 받을수 있게 하자
                            inFilename = true;
                        }
                        if (parser.getName().equals("happenDt")) { //mapy 만나면 내용을 받을수 있게 하자
                            inHappenDt = true;
                        }
                        if (parser.getName().equals("happenPlace")) { //mapy 만나면 내용을 받을수 있게 하자
                            inHappenPlace = true;
                        }
                        if (parser.getName().equals("kindCd")) { //mapy 만나면 내용을 받을수 있게 하자
                            inKindCd = true;
                        }
                        if (parser.getName().equals("message")) { //message 태그를 만나면 에러 출력
                            //text.setText(text.getText() + "에러");
                            //여기에 에러코드에 따라 다른 메세지를 출력하도록 할 수 있다.
                        }
                        break;

                    case XmlPullParser.TEXT://parser가 내용에 접근했을때
                        if (inAge) { //isTitle이 true일 때 태그의 내용을 저장.
                            age = parser.getText();
                            inAge = false;
                        }
                        if (inCareAddr) { //isAddress이 true일 때 태그의 내용을 저장.
                            careAddr = parser.getText();
                            inCareAddr = false;
                        }
                        if (inCareNm) { //isMapx이 true일 때 태그의 내용을 저장.
                            careNm = parser.getText();
                            inCareNm = false;
                        }
                        if (inCareTel) { //isMapy이 true일 때 태그의 내용을 저장.
                            careTel = parser.getText();
                            inCareTel = false;
                        }
                        if (inChargeNm) { //isMapy이 true일 때 태그의 내용을 저장.
                            chargeNm = parser.getText();
                            inChargeNm = false;
                        }
                        if (inColorCd) { //isMapy이 true일 때 태그의 내용을 저장.
                            colorCd = parser.getText();
                            inColorCd = false;
                        }
                        if (inDesertionNo) { //isMapy이 true일 때 태그의 내용을 저장.
                            desertionNo = parser.getText();
                            inDesertionNo = false;
                        }
                        if (inFilename) { //isMapy이 true일 때 태그의 내용을 저장.
                            filename = parser.getText();
                            inFilename = false;
                        }
                        if (inHappenDt) { //isMapy이 true일 때 태그의 내용을 저장.
                            happenDt = parser.getText();
                            inHappenDt = false;
                        }
                        if (inHappenPlace) { //isMapy이 true일 때 태그의 내용을 저장.
                            happenPlace = parser.getText();
                            inHappenPlace = false;
                        }
                        if (inKindCd) { //isMapy이 true일 때 태그의 내용을 저장.
                            kindCd = parser.getText();
                            inKindCd = false;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals("item")) {
                            listViewAdapter.addItem(filename, desertionNo, kindCd, colorCd, happenDt, careAddr);
                            initem = false;
                        }
                        break;
                }
                parserEvent = parser.next();
            }
        } catch (Exception e) {
            Log.d("TEST", "에러발생");
            //text.setText("에러가..났습니다...");
        }
        listview.setAdapter(listViewAdapter);

    }

    DatePickerDialog.OnDateSetListener mDateSetListener =
        new DatePickerDialog.OnDateSetListener() {
            @Override

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                //사용자가 입력한 값을 가져온뒤

                mYear = year;
                mMonth = monthOfYear;
                mDay = dayOfMonth;
            }
        };


}
