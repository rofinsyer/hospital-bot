package com.emlook.hospital.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.emlook.hospital.R;

public class MinwonFragment extends Fragment {
    /***
     * Fragment 내부적으로 사용할 객체 생성(일반적인 자바와 다름)
     * @return
     */

    public static MinwonFragment newInstance() {
        MinwonFragment fragment = new MinwonFragment();
        return fragment;
    }

    public static MinwonFragment newInstance(String name) {
        Log.d(">> newInstance ", name);
        MinwonFragment fragment = new MinwonFragment();

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
        View layout = inflater.inflate(R.layout.minwon2, container, false);
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
        if(name.contains("대형폐기물")){
            ((FrameLayout)view.findViewById(R.id.img_minwon)).setBackgroundResource(R.drawable.big_trash2);
        }else if(name.contains("여권")){
            ((FrameLayout)view.findViewById(R.id.img_minwon)).setBackgroundResource(R.drawable.passport);
        }
        //Text animation
    }

    @Override
    public void onPause() {
        super.onPause();

    }
}

