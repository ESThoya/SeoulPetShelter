package com.animalprotect.hoyage.animalprotect;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {

    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<AnimalItem> listViewItemList = new ArrayList<AnimalItem>() ;

    // ListViewAdapter의 생성자
    public ListViewAdapter() {

    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return listViewItemList.size() ;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.animal_item, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        ImageView iconImageView = (ImageView) convertView.findViewById(R.id.animal_image) ;
        //TextView titleTextView = (TextView) convertView.findViewById(R.id.title) ;
        TextView kindCdTextView = (TextView) convertView.findViewById(R.id.kindCd) ;
        TextView colorTextView = (TextView) convertView.findViewById(R.id.color) ;
        TextView happenDtTextView = (TextView) convertView.findViewById(R.id.happenDt);
        TextView addressTextView = (TextView) convertView.findViewById(R.id.address);


        AnimalItem listViewItem = listViewItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        String imgURL = listViewItem.getImgURL();
        AQuery aq = new AQuery(convertView);
        aq.id(iconImageView).image(imgURL);
        //titleTextView.setText(listViewItem.getTitle());
        kindCdTextView.setText(listViewItem.getKindCdStr());
        colorTextView.setText(listViewItem.getColor());
        String happenDt = listViewItem.getHappenDt();
        String happenResult = happenDt.substring(0, 4) + "년 "  + happenDt.substring(4, 6) + "월 " + happenDt.substring(6, 8) + "일 발생";
        happenDtTextView.setText(happenResult);
        addressTextView.setText(listViewItem.getAddress());

        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position ;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position) ;
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(String imgUrl, String title, String kindStr, String color, String happenDt, String address) {
        AnimalItem item = new AnimalItem();

        item.setImgURL(imgUrl);
        item.setTitle(title);
        item.setKindCdStr(kindStr);
        item.setColor(color);
        item.setHappenDt(happenDt);
        item.setAddress((address));

        listViewItemList.add(item);
    }

    public void clearAll() {
        listViewItemList.clear();
    }
}
