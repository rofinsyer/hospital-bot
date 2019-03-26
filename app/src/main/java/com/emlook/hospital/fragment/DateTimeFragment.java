package com.emlook.hospital.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.emlook.hospital.R;

import java.util.Calendar;

public class DateTimeFragment extends Fragment {

    /***
     * Fragment 내부적으로 사용할 객체 생성(일반적인 자바와 다름)
     * @return
     */
    public static DateTimeFragment newInstance() {
        DateTimeFragment fragment = new DateTimeFragment();
        return fragment;
    }

    public static DateTimeFragment newInstance(String name) {
        DateTimeFragment fragment = new DateTimeFragment();

        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        fragment.setArguments(bundle);

        return fragment;
    }

    /***
     * 자동호출 메소드
     * @param inflater : 호출 Fragment xml
     * @param container : 부모 Activity 객체
     * @param savedInstanceState : 이전 데이터값 저장 객체
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.datetime2, container, false);
        return layout;
    }

    /***
     * 자동호출 메소드(onCreateView -> onViewCreated)
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setView(view);
    }

    //    private void setView(View view) {
//        String name = getArguments().getString("name");
//        Log.d(">> setView ", name);
//
//        String output = "";
//        Calendar cal = Calendar.getInstance();
//        TextView titleText = view.findViewById(R.id.datetime);
//        if ("time".equalsIgnoreCase(name)) {
//            output = "AM_PM HH시 MM분";
//            switch (cal.get(Calendar.AM_PM)) {
//                case 0:
//                    output = output.replace("AM_PM", "오전");
//                    break;
//                default:
//                    output = output.replace("AM_PM", "오후");
//                    break;
//            }
//            output = output.replace("HH", String.valueOf(cal.get(Calendar.HOUR)));
//            output = output.replace("MM", String.valueOf(cal.get(Calendar.MINUTE)));
//        } else {
//            output = "오늘은\nYYYY년 MM월 DD일 WEEKNUM\n입니다.";
//            output = output.replace("YYYY", String.valueOf(cal.get(Calendar.YEAR)));
//            output = output.replace("MM", String.valueOf(cal.get(Calendar.MONTH) + 1));
//            output = output.replace("DD", String.valueOf(cal.get(Calendar.DATE)));
//            switch (cal.get(Calendar.DAY_OF_WEEK)) {
//                case 1:
//                    output = output.replace("WEEKNUM", "일요일");
//                    break;
//                case 2:
//                    output = output.replace("WEEKNUM", "월요일");
//                    break;
//                case 3:
//                    output = output.replace("WEEKNUM", "화요일");
//                    break;
//                case 4:
//                    output = output.replace("WEEKNUM", "수요일");
//                    break;
//                case 5:
//                    output = output.replace("WEEKNUM", "목요일");
//                    break;
//                case 6:
//                    output = output.replace("WEEKNUM", "금요일");
//                    break;
//                case 7:
//                    output = output.replace("WEEKNUM", "토요일");
//                    break;
//                default:
//                    break;
//            }
//        }
//        titleText.setText(output);
//     }
    private void setView(View view) {
        String name = getArguments().getString("name");
        Log.d(">> setView ", name);

        String output = "";
        Calendar cal = Calendar.getInstance();
        int time_10hour = cal.get(Calendar.HOUR) / 10;
        int time_1hour = cal.get(Calendar.HOUR) % 10;

        setNumberImageTime(view, R.id.img_time_10hour, time_10hour);
        setNumberImageTime(view, R.id.img_time_1hour, time_1hour);

        int time_10min = cal.get(Calendar.MINUTE) / 10;
        int time_1min = cal.get(Calendar.MINUTE) % 10;

        setNumberImageTime(view, R.id.img_time_10min, time_10min);
        setNumberImageTime(view, R.id.img_time_1min, time_1min);

        int date_1000year = cal.get(Calendar.YEAR) / 1000;
        int date_100year = (cal.get(Calendar.YEAR) / 100) % 10 ;
        int date_10year = (cal.get(Calendar.YEAR) / 10) % 10;
        int date_1year = cal.get(Calendar.YEAR) % 10;

        setNumberImageDate(view, R.id.img_date_1000year, date_1000year);
        setNumberImageDate(view, R.id.img_date_100year, date_100year);
        setNumberImageDate(view, R.id.img_date_10year, date_10year);
        setNumberImageDate(view, R.id.img_date_1year, date_1year);


        int date_10month = (cal.get(Calendar.MONTH) + 1) / 10;
        int date_1month = (cal.get(Calendar.MONTH) + 1) % 10;

        setNumberImageDate(view, R.id.img_date_10month, date_10month);
        setNumberImageDate(view, R.id.img_date_1month, date_1month);

        int date_10day = (cal.get(Calendar.DATE)) / 10;
        int date_1day = (cal.get(Calendar.DATE)) % 10;

        setNumberImageDate(view, R.id.img_date_10day, date_10day);
        setNumberImageDate(view, R.id.img_date_1day, date_1day);


        switch (cal.get(Calendar.DAY_OF_WEEK)) {
            case 1:
                ((ImageView) (view.findViewById(R.id.img_date_week))).setImageResource(R.drawable.sun);
                break;
            case 2:
                ((ImageView) (view.findViewById(R.id.img_date_week))).setImageResource(R.drawable.mon);
                break;
            case 3:
                ((ImageView) (view.findViewById(R.id.img_date_week))).setImageResource(R.drawable.tue);
                break;
            case 4:
                ((ImageView) (view.findViewById(R.id.img_date_week))).setImageResource(R.drawable.wed);
                break;
            case 5:
                ((ImageView) (view.findViewById(R.id.img_date_week))).setImageResource(R.drawable.thu);
                break;
            case 6:
                ((ImageView) (view.findViewById(R.id.img_date_week))).setImageResource(R.drawable.fri);
                break;
            case 7:
                ((ImageView) (view.findViewById(R.id.img_date_week))).setImageResource(R.drawable.sat);
                break;
            default:
                break;
        }
    }
    private void setNumberImageTime(View view, int ImgViewId, int time_num){
        switch(time_num){
            case 0:
                ((ImageView)(view.findViewById(ImgViewId))).setImageResource(R.drawable.time0);
                break;
            case 1:
                ((ImageView)(view.findViewById(ImgViewId))).setImageResource(R.drawable.time1);
                break;
            case 2:
                ((ImageView)(view.findViewById(ImgViewId))).setImageResource(R.drawable.time2);
                break;
            case 3:
                ((ImageView)(view.findViewById(ImgViewId))).setImageResource(R.drawable.time3);
                break;
            case 4:
                ((ImageView)(view.findViewById(ImgViewId))).setImageResource(R.drawable.time4);
                break;
            case 5:
                ((ImageView)(view.findViewById(ImgViewId))).setImageResource(R.drawable.time5);
                break;
            case 6:
                ((ImageView)(view.findViewById(ImgViewId))).setImageResource(R.drawable.time6);
                break;
            case 7:
                ((ImageView)(view.findViewById(ImgViewId))).setImageResource(R.drawable.time7);
                break;
            case 8:
                ((ImageView)(view.findViewById(ImgViewId))).setImageResource(R.drawable.time8);
                break;
            case 9:
                ((ImageView)(view.findViewById(ImgViewId))).setImageResource(R.drawable.time9);
                break;
        }
    }
    private void setNumberImageDate(View view, int ImgViewId, int date_num){
        switch(date_num){
            case 0:
                ((ImageView)(view.findViewById(ImgViewId))).setImageResource(R.drawable.date0);
                break;
            case 1:
                ((ImageView)(view.findViewById(ImgViewId))).setImageResource(R.drawable.date1);
                break;
            case 2:
                ((ImageView)(view.findViewById(ImgViewId))).setImageResource(R.drawable.date2);
                break;
            case 3:
                ((ImageView)(view.findViewById(ImgViewId))).setImageResource(R.drawable.date3);
                break;
            case 4:
                ((ImageView)(view.findViewById(ImgViewId))).setImageResource(R.drawable.date4);
                break;
            case 5:
                ((ImageView)(view.findViewById(ImgViewId))).setImageResource(R.drawable.date5);
                break;
            case 6:
                ((ImageView)(view.findViewById(ImgViewId))).setImageResource(R.drawable.date6);
                break;
            case 7:
                ((ImageView)(view.findViewById(ImgViewId))).setImageResource(R.drawable.date7);
                break;
            case 8:
                ((ImageView)(view.findViewById(ImgViewId))).setImageResource(R.drawable.date8);
                break;
            case 9:
                ((ImageView)(view.findViewById(ImgViewId))).setImageResource(R.drawable.date9);
                break;
        }
    }
}
