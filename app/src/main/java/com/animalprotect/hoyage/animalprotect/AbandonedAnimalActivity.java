package com.animalprotect.hoyage.animalprotect;

import android.os.StrictMode;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.net.URL;

public class AbandonedAnimalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abandoned_animal);

        StrictMode.enableDefaults();

        ListView listview ;
        ListViewAdapter adapter;

        // Adapter 생성
        adapter = new ListViewAdapter() ;

        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) findViewById(R.id.animal_list);
        listview.setAdapter(adapter);

        boolean initem = false, inAge = false, inCareAddr = false, inCareNm = false, inCareTel = false;
        boolean inChargeNm = false, inColorCd = false, inDesertionNo = false, inFilename = false, inHappenDt = false;
        boolean inHappenPlace = false, inKindCd = false;

        String age = null, careAddr = null, careNm = null, careTel = null, chargeNm = null, colorCd = null, desertionNo = null, filename = null;
        String happenDt = null, happenPlace = null, kindCd = null;

        try {
            URL url = new URL("http://openapi.animal.go.kr/openapi/service/rest/abandonmentPublicSrvc/abandonmentPublic?"
                    + "&bgnde=20180801&endde=20180902&upkind=417000&state=notice&pageNo=1&startPage=1&numOfRows=10&pageSize=10&ServiceKey="
                    + "QBBSYEb2mo%2FtLgCL9UJsvRrsZ%2B9xZtixJuCL19qYwRUk10N188jyKBxQrRzR2VMWeNtLZJgfR7YbGqCzv6uEyw%3D%3D"
            ); //검색 URL부분

            Log.d("TEST", url.toString());

            XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserCreator.newPullParser();

            parser.setInput(url.openStream(), null);

            int parserEvent = parser.getEventType();
            Log.d("TEST", "파싱 시작");


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
                        Log.d("TEST", "내용 파싱 접근");
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
                        Log.d("TEST", "마지막 파싱");
                        if (parser.getName().equals("item")) {
                            Log.d(this.getClass().getName(), "아이템 추가");
                            adapter.addItem(filename, desertionNo, kindCd, colorCd, happenDt, careAddr) ;
                            /*text.setText(text.getText() + "나이 : " + age + "\n 보호소 주소: " + careAddr + "\n 보호소 이름 : " + careNm
                                    + "\n 보호소 연락처 : " + careTel + "\n 담당자명 : " + chargeNm + "\n 색상 : " + colorCd
                                    + "\n 유기번호 : " + desertionNo + "\n 이미지 파일 : " + filename + "\n 발생년월일 : " + happenDt
                                    + "\n 발생장소 : " + happenPlace + "\n 견종 : " + kindCd + "\n");*/
                            initem = false;
                        }
                        break;
                }
                parserEvent = parser.next();
            }
        } catch (Exception e) {
            //text.setText("에러가..났습니다...");
        }
    }
}
