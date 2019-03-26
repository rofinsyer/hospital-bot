package com.emlook.hospital.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.emlook.hospital.R;

public class WhereInfoFragment extends Fragment {
    /***
     * Fragment 내부적으로 사용할 객체 생성(일반적인 자바와 다름)
     * @return
     */
    public static WhereInfoFragment newInstance() {
        WhereInfoFragment fragment = new WhereInfoFragment();
        return fragment;
    }

    public static WhereInfoFragment newInstance(String name) {
        Log.d(">> newInstance ", name);
        WhereInfoFragment fragment = new WhereInfoFragment();

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
        View layout = inflater.inflate(R.layout.where_info2, container, false);
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
        if (name.contains("무인민원발급기")) {
            ((FrameLayout)view.findViewById(R.id.layout_where)).setBackgroundResource(R.drawable.area06);
        } else if (name.contains("화장실")) {
            ((FrameLayout)view.findViewById(R.id.layout_where)).setBackgroundResource(R.drawable.area05);
        } else if (name.contains("행정지원과")) {
            ((FrameLayout)view.findViewById(R.id.layout_where)).setBackgroundResource(R.drawable.area01);
        } else if (name.contains("시민봉사과")) {
            ((FrameLayout)view.findViewById(R.id.layout_where)).setBackgroundResource(R.drawable.area02);
        } else if (name.contains("사회복지과")) {
            ((FrameLayout)view.findViewById(R.id.layout_where)).setBackgroundResource(R.drawable.area07);
        } else if (name.contains("가정복지과")) {
            ((FrameLayout)view.findViewById(R.id.layout_where)).setBackgroundResource(R.drawable.area08);
        } else if (name.contains("야외정원")) {
            ((FrameLayout)view.findViewById(R.id.layout_where)).setBackgroundResource(R.drawable.area03);
        } else {
            ((FrameLayout)view.findViewById(R.id.layout_where)).setBackgroundResource(R.drawable.area00);
        }
    }
}
