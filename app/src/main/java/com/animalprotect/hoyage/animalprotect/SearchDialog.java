package com.animalprotect.hoyage.animalprotect;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class SearchDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener{

    EditText startDate, endDate;
    //RadioButton radioBtnAll, radioBtnDog, radioBtnCat, radioBtnEtc;
    Button btnDialogSearch, btnDialogCancel;
    private int day, month, year;
    private Button.OnClickListener onClickListener;
    OnMyDialogResult mDialogResult;

    Calendar calendar = Calendar.getInstance(TimeZone.getDefault());


    public SearchDialog() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        onClickListener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(v.getId()) {

                    case R.id.btnDialogSearch:
                        mDialogResult.finish(startDate.getText().toString(), endDate.getText().toString());

                        break;

                    case R.id.btnDialogCancel:
                        dismiss();
                        break;

                }
            }
        };

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        this.day = dayOfMonth;
        this.month = month;
        this.year = year;

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_dialog, container);


        // findViewId
        this.startDate = (EditText)view.findViewById(R.id.startDate);
        this.endDate = (EditText)view.findViewById(R.id.endDate);
        /*this.radioBtnAll = (RadioButton)view.findViewById(R.id.radioBtnAll);
        this.radioBtnDog = (RadioButton)view.findViewById(R.id.radioBtnDog);
        this.radioBtnCat = (RadioButton)view.findViewById(R.id.radioBtnCat);
        this.radioBtnEtc = (RadioButton)view.findViewById(R.id.radioBtnEtc);

        this.radioBtnAll.setOnClickListener(radioBtnOnClickListener);
        this.radioBtnDog.setOnClickListener(radioBtnOnClickListener);
        this.radioBtnCat.setOnClickListener(radioBtnOnClickListener);
        this.radioBtnEtc.setOnClickListener(radioBtnOnClickListener);

        this.radioBtnAll.setChecked(true); // 기본값 설정*/

        this.btnDialogSearch = (Button)view.findViewById(R.id.btnDialogSearch);
        this.btnDialogCancel = (Button)view.findViewById(R.id.btnDialogCancel);
        this.btnDialogSearch.setOnClickListener(onClickListener);
        this.btnDialogCancel.setOnClickListener(onClickListener);

        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
        startDate.setText(sdformat.format(calendar.getTime()));
        endDate.setText(sdformat.format(calendar.getTime()));

        /*Log.d("TEST", "stDate : " + stDate + " / edDate : " + edDate);
        if(stDate == null && edDate == null) { // 기본값은 금일 날짜
            SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
            startDate.setText(sdformat.format(calendar.getTime()));
            endDate.setText(sdformat.format(calendar.getTime()));

        }else { // 시작, 마지막 일자를 표시
            startDate.setText(stDate);
            endDate.setText(edDate);
        }*/


        // 시작일자
        startDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                try{
                    String strDate = startDate.getText().toString();
                    Date d = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(strDate);

                    String[] splitDate = strDate.split("-");
                    year = Integer.parseInt(splitDate[0]);
                    month = Integer.parseInt(splitDate[1])-1;
                    day = Integer.parseInt(splitDate[2]);

                    showDateDialog(startDate);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        // 종료일자
        endDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                try{
                    String strDate = endDate.getText().toString();
                    Date d = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(strDate);

                    String[] splitDate = strDate.split("-");
                    year = Integer.parseInt(splitDate[0]);
                    month = Integer.parseInt(splitDate[1])-1;
                    day = Integer.parseInt(splitDate[2]);

                    showDateDialog(endDate);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        return view;
    }

    // 라디오 버튼옵션
    /*RadioButton.OnClickListener radioBtnOnClickListener = new RadioButton.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.radioBtnAll:
                    break;

                case R.id.radioBtnDog:
                    break;

                case R.id.radioBtnCat:
                    break;

                case R.id.radioBtnEtc:
                    break;
            }
        }
    };*/

    public interface OnMyDialogResult {
        void finish(String stDate, String edDate);
    }

    public void setDialogResult(OnMyDialogResult onMyDialogResult) {
        mDialogResult = onMyDialogResult;
    }

    private void showDateDialog(final EditText editText) {
        DatePickerDialog pickerDialog = new DatePickerDialog(getActivity(), android.R.style.Theme_Holo_Light_Dialog_MinWidth, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String dateStr = year + "-" + (month+1) + "-" + dayOfMonth;;

                try{
                    Date d = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(dateStr);
                    editText.setText(sdf.format(d));

                    /*switch (editText.getId()) {
                        case R.id.startDate:
                            stDate = sdf.format(d);
                            break;

                        case R.id.endDate:
                            edDate = sdf.format(d);
                            break;
                    }*/
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, year, month, day);

        pickerDialog.getDatePicker().setCalendarViewShown(false);
        pickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        pickerDialog.show();
    }

}


