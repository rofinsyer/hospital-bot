package com.emlook.hospital.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.emlook.hospital.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

public class WeatherFragment extends Fragment {

    // OpenWeatherMap API 사용
    public static final String API_KEY = "45bb1e8abdeb1908bb26f19f228d979f";
    public static final String API_KEY_FULL = "&appid=" + API_KEY;
    public static final String API_ADDRESS = "http://api.openweathermap.org/data/2.5/weather";
    public static String API_CITY = "?q=Seoul";
    public static String API_FULL_ADDRESS = API_ADDRESS + API_CITY + API_KEY_FULL;
    //http://api.openweathermap.org/data/2.5/weather?q=Seoul&appid=45bb1e8abdeb1908bb26f19f228d979f

    /***
     * Fragment 내부적으로 사용할 객체 생성(일반적인 자바와 다름)
     * @return
     */
    public static WeatherFragment newInstance() {
        WeatherFragment fragment = new WeatherFragment();
        return fragment;
    }

    public static WeatherFragment newInstance(String name) {
        Log.d(">> newInstance ", name);
        WeatherFragment fragment = new WeatherFragment();

        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        fragment.setArguments(bundle);

        return fragment;
    }

    /***
     * 자동호출 메소드
     * @param inflater :
     * @param container : 부모 Activity 객체
     * @param savedInstanceState : 이전 데이터값 저장 객체
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.weather2, container, false);
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

    private void setView(final View view) {
        String name = getArguments().getString("name");
        Log.d(">> setView ", name);

        new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
//        // Create a new HttpClient and Post Header
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(API_FULL_ADDRESS);
                try {
                    // Add your data
                    List<NameValuePair> params = new ArrayList<NameValuePair>(4);
//                    params.add(new BasicNameValuePair("q", "Seoul"));
//                    params.add(new BasicNameValuePair("appId", API_KEY));
//                    httppost.setEntity(new UrlEncodedFormEntity(params));
                    // Execute HTTP Post Request
                    HttpResponse response = httpclient.execute(httppost);


                    BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                    String line;
                    String json_result_str = "";
                    while (null != (line = br.readLine())) {
                        Log.d("jejang response", line);
                        json_result_str += line;
                    }
                    JSONObject jsonObject = new JSONObject(json_result_str);

                    JSONArray jsonArray = jsonObject.getJSONArray("weather");

                    String main_weather = "";
                    if (jsonArray.length() > 0) {
                        main_weather = jsonArray.getJSONObject(0).getString("main");
                    }

                    String temp = jsonObject.getJSONObject("main").getString("temp");
                    float temperature = 0;
                    int temp_i = 0;
                    if (temp != null) {
                        temperature = Float.parseFloat(temp) - 273.15f;
                        temp_i = Math.round(temperature);
                    }
                    String temp_min = jsonObject.getJSONObject("main").getString("temp_min");
                    float temperature_min = 0;
                    int temp_min_i = 0;
                    if (temp_min != null) {
                        temperature_min = Float.parseFloat(temp_min) - 273.15f;
                        temp_min_i = Math.round(temperature_min);
                    }
                    String temp_max = jsonObject.getJSONObject("main").getString("temp_max");
                    float temperature_max = 0;
                    int temp_max_i = 0;
                    if (temp_max != null) {
                        temperature_max = Float.parseFloat(temp_max) - 273.15f;
                        temp_max_i = Math.round(temperature_max);
                    }

                    String humidity = jsonObject.getJSONObject("main").getString("humidity");


                    String clouds = jsonObject.getJSONObject("clouds").getString("all");


                    Weather obj_weather = new Weather(main_weather, temp_i + "", temp_min_i + "", temp_max_i + "", humidity, clouds);
                    runUIHandler(obj_weather, view);
                    Log.d("response", response.toString());

                    Log.d("response json", json_result_str);
                    Log.d("response weather", main_weather);
                    Log.d("response temp", temperature + "");
                    Log.d("response temp_min", temperature_min + "");
                    Log.d("response temp_max", temperature_max + "");
                    Log.d("response humidity", humidity + "");
                    Log.d("response clouds", clouds + "");


                } catch (ClientProtocolException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();

//        TextView titleText = view.findViewById(R.id.title);
//        titleText.setText(name + " 안녕하세요.");
    }

    private class Weather {
        String main_weather;
        String temperature;
        String temperature_min;
        String temperature_max;
        String humidity;
        String cloud;

        public Weather(String main_weather, String temperature, String temperature_min, String temperature_max, String humidity, String cloud) {
            this.main_weather = main_weather;
            this.temperature = temperature;
            this.temperature_min = temperature_min;
            this.temperature_max = temperature_max;
            this.humidity = humidity;
            this.cloud = cloud;
        }
    }

    private void runUIHandler(final Weather obj_weather, final View view) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {

                if (obj_weather.main_weather.contains("cloud") ||obj_weather.main_weather.contains("Cloud")) {
                    int cloud_percent = Integer.parseInt(obj_weather.cloud);
                    if (cloud_percent > 50) {
                        ((ImageView) view.findViewById(R.id.weather_img)).setImageResource(R.drawable.hospital_weather_icon_03_cloudy02);
                    } else {
                        ((ImageView) view.findViewById(R.id.weather_img)).setImageResource(R.drawable.hospital_weather_icon_05_cloudy01);
                    }
                } else if (obj_weather.main_weather.contains("snow") || obj_weather.main_weather.contains("Snow")) {
                    ((ImageView) view.findViewById(R.id.weather_img)).setImageResource(R.drawable.hospital_weather_icon_08_snow01);

                } else if (obj_weather.main_weather.contains("rain") || obj_weather.main_weather.contains("Rain")) {
                    ((ImageView) view.findViewById(R.id.weather_img)).setImageResource(R.drawable.hospital_weather_icon_02_rain);
                } else {
                    int cloud_percent = Integer.parseInt(obj_weather.cloud);
                    if (cloud_percent > 50) {
                        ((ImageView) view.findViewById(R.id.weather_img)).setImageResource(R.drawable.hospital_weather_icon_03_cloudy02);
                    } else if(cloud_percent >= 20 && cloud_percent <= 50){
                        ((ImageView) view.findViewById(R.id.weather_img)).setImageResource(R.drawable.hospital_weather_icon_05_cloudy01);
                    }else if(cloud_percent < 20){
                        ((ImageView) view.findViewById(R.id.weather_img)).setImageResource(R.drawable.hospital_weather_icon_01_fine);
                    }
                }

                int cur_tmp = Integer.parseInt(obj_weather.temperature);
                int cur_tmp_10 = cur_tmp / 10;
                int cur_tmp_1 = cur_tmp % 10;

                setBigNumberImage(view, R.id.cur_tmp_10, cur_tmp_10);
                setBigNumberImage(view, R.id.cur_tmp_1, cur_tmp_1);

                int high_tmp = Integer.parseInt(obj_weather.temperature_max);
                int high_tmp_10 = high_tmp / 10;
                int high_tmp_1 = high_tmp % 10;

                setSmallNumberImage(view, R.id.high_tmp_10, high_tmp_10);
                setSmallNumberImage(view, R.id.high_tmp_1, high_tmp_1);

                int low_tmp = Integer.parseInt(obj_weather.temperature_min);
                int low_tmp_10 = low_tmp / 10;
                int low_tmp_1 = low_tmp % 10;

                setSmallNumberImage(view, R.id.low_tmp_10, low_tmp_10);
                setSmallNumberImage(view, R.id.low_tmp_1, low_tmp_1);

                int humidity = Integer.parseInt(obj_weather.humidity);
                int humidity_10 = humidity / 10;
                int humidity_1 = humidity % 10;

                setSmallNumberImage(view, R.id.humidity_10, humidity_10);
                setSmallNumberImage(view, R.id.humidity_1, humidity_1);


//                ((TextView) view.findViewById(R.id.txt_temp)).setText(obj_weather.temperature+ " °");
//                ((TextView) view.findViewById(R.id.txt_temp)).setTypeface(CustomFontsLoader.getTypeface(getActivity(), 1));
//                ((TextView) view.findViewById(R.id.txt_temp_min)).setText(obj_weather.temperature_min+ " °");
//                ((TextView) view.findViewById(R.id.txt_temp_min)).setTypeface(CustomFontsLoader.getTypeface(getActivity(), 1));
//                ((TextView) view.findViewById(R.id.txt_temp_max)).setText(obj_weather.temperature_max+ " °");
//                ((TextView) view.findViewById(R.id.txt_temp_max)).setTypeface(CustomFontsLoader.getTypeface(getActivity(), 1));
//                ((TextView) view.findViewById(R.id.txt_humidity)).setText(obj_weather.humidity+ " %");
//                ((TextView) view.findViewById(R.id.txt_humidity)).setTypeface(CustomFontsLoader.getTypeface(getActivity(), 1));
            }
        });

    }


    private void setSmallNumberImage(View view, int ImgViewId, int num){
        switch(num){
            case 0:
                ((ImageView)(view.findViewById(ImgViewId))).setImageResource(R.drawable.hospital_weather_icon_27_s_num_0);
                break;
            case 1:
                ((ImageView)(view.findViewById(ImgViewId))).setImageResource(R.drawable.hospital_weather_icon_29_s_num_1);
                break;
            case 2:
                ((ImageView)(view.findViewById(ImgViewId))).setImageResource(R.drawable.hospital_weather_icon_31_s_num_2);
                break;
            case 3:
                ((ImageView)(view.findViewById(ImgViewId))).setImageResource(R.drawable.hospital_weather_icon_33_s_num_3);
                break;
            case 4:
                ((ImageView)(view.findViewById(ImgViewId))).setImageResource(R.drawable.hospital_weather_icon_35_s_num_4);
                break;
            case 5:
                ((ImageView)(view.findViewById(ImgViewId))).setImageResource(R.drawable.hospital_weather_icon_37_s_num_5);
                break;
            case 6:
                ((ImageView)(view.findViewById(ImgViewId))).setImageResource(R.drawable.hospital_weather_icon_39_s_num_6);
                break;
            case 7:
                ((ImageView)(view.findViewById(ImgViewId))).setImageResource(R.drawable.hospital_weather_icon_41_s_num_7);
                break;
            case 8:
                ((ImageView)(view.findViewById(ImgViewId))).setImageResource(R.drawable.hospital_weather_icon_43_s_num_8);
                break;
            case 9:
                ((ImageView)(view.findViewById(ImgViewId))).setImageResource(R.drawable.hospital_weather_icon_45_s_num_9);
                break;
        }
    }

    private void setBigNumberImage(View view, int ImgViewId, int num){
        switch(num){
            case 0:
                ((ImageView)(view.findViewById(ImgViewId))).setImageResource(R.drawable.hospital_weather_icon_57_b_num_0);
                break;
            case 1:
                ((ImageView)(view.findViewById(ImgViewId))).setImageResource(R.drawable.hospital_weather_icon_59_b_num_1);
                break;
            case 2:
                ((ImageView)(view.findViewById(ImgViewId))).setImageResource(R.drawable.hospital_weather_icon_61_b_num_2);
                break;
            case 3:
                ((ImageView)(view.findViewById(ImgViewId))).setImageResource(R.drawable.hospital_weather_icon_63_b_num_3);
                break;
            case 4:
                ((ImageView)(view.findViewById(ImgViewId))).setImageResource(R.drawable.hospital_weather_icon_65_b_num_4);
                break;
            case 5:
                ((ImageView)(view.findViewById(ImgViewId))).setImageResource(R.drawable.hospital_weather_icon_67_b_num_5);
                break;
            case 6:
                ((ImageView)(view.findViewById(ImgViewId))).setImageResource(R.drawable.hospital_weather_icon_69_b_num_6);
                break;
            case 7:
                ((ImageView)(view.findViewById(ImgViewId))).setImageResource(R.drawable.hospital_weather_icon_71_b_num_7);
                break;
            case 8:
                ((ImageView)(view.findViewById(ImgViewId))).setImageResource(R.drawable.hospital_weather_icon_73_b_num_8);
                break;
            case 9:
                ((ImageView)(view.findViewById(ImgViewId))).setImageResource(R.drawable.hospital_weather_icon_76_b_num_9);
                break;
        }
    }
}

