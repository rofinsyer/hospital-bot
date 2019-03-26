package com.emlook.hospital.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.emlook.hospital.R;

public class HospitalFragment extends Fragment {
    /***
     * Fragment 내부적으로 사용할 객체 생성(일반적인 자바와 다름)
     * @return
     */
    public static HospitalFragment newInstance() {
        HospitalFragment fragment = new HospitalFragment();
        return fragment;
    }

    public static HospitalFragment newInstance(String name) {
        Log.d(">> newInstance ", name);
        HospitalFragment fragment = new HospitalFragment();

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
        View layout = inflater.inflate(R.layout.hospital, container, false);
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

    private void setView(View view) {
        String name = getArguments().getString("name");
        Log.d(">> setView ", name);
        if (name.contains("메인")) {
            ((FrameLayout)view.findViewById(R.id.img_hospital)).setBackgroundResource(R.drawable.img_main_hospital);
        }else if(name.contains("조직도")){
            ((FrameLayout)view.findViewById(R.id.img_hospital)).setBackgroundResource(R.drawable.hospital11_org_chart);
        }else if(name.contains("건강검진안내")){
            ((FrameLayout)view.findViewById(R.id.img_hospital)).setBackgroundResource(R.drawable.hospital09_health_check_menu);
        }else if(name.contains("기초생리기능검사")){
            ((FrameLayout)view.findViewById(R.id.img_hospital)).setBackgroundResource(R.drawable.hospital09_health_check_basic);
        }else if(name.contains("혈액질환검사")){
            ((FrameLayout)view.findViewById(R.id.img_hospital)).setBackgroundResource(R.drawable.hospital09_2_health_check_blood);
        }else if(name.contains("흉부촬영검사")){
            ((FrameLayout)view.findViewById(R.id.img_hospital)).setBackgroundResource(R.drawable.hospital09_3_health_check_chest);
        }else if(name.contains("검진순서")){
            ((FrameLayout)view.findViewById(R.id.img_hospital)).setBackgroundResource(R.drawable.hospital10_seq);
        }else if(name.contains("협회소개")){
            ((FrameLayout)view.findViewById(R.id.img_hospital)).setBackgroundResource(R.drawable.hospital08_intro);
        }else if(name.contains("검진예약")){
            ((FrameLayout)view.findViewById(R.id.img_hospital)).setBackgroundResource(R.drawable.hospital12_reservation);
        }else if(name.contains("검진상담")){
            ((FrameLayout)view.findViewById(R.id.img_hospital)).setBackgroundResource(R.drawable.hospital13_advice);
        }else if(name.contains("CPK")){
            ((FrameLayout)view.findViewById(R.id.img_hospital)).setBackgroundResource(R.drawable.hospital14_cpk);
        }else if(name.contains("당뇨")){
            ((FrameLayout)view.findViewById(R.id.img_hospital)).setBackgroundResource(R.drawable.hospital15_glycosuria);
        }else {
            ((FrameLayout)view.findViewById(R.id.img_hospital)).setBackgroundResource(R.drawable.img_main_hospital);
        }
    }
}