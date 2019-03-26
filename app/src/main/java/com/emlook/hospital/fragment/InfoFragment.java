package com.emlook.hospital.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.emlook.hospital.CustomFontsLoader;
import com.emlook.hospital.R;
import com.emlook.hospital.Utils.TypewriterPost;

public class InfoFragment extends Fragment {

    /***
     * Fragment 내부적으로 사용할 객체 생성(일반적인 자바와 다름)
     * @return
     */
    public static InfoFragment newInstance() {
        InfoFragment fragment = new InfoFragment();
        return fragment;
    }

    public static InfoFragment newInstance(String name) {
        Log.d(">> newInstance ", name);
        InfoFragment fragment = new InfoFragment();

        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        fragment.setArguments(bundle);

        return fragment;
    }

    public static InfoFragment newInstance(String name, String ment) {
        Log.d(">> newInstance ", name);
        InfoFragment fragment = new InfoFragment();

        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        bundle.putString("ment", ment);
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
        View layout = inflater.inflate(R.layout.info, container, false);
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
        String ment = getArguments().getString("ment");
        Log.d(">> setView ", name);
        if(ment != null){
            Log.d(">> setView ", ment);
        }
        setInVisibleMoveIcon(view);
        if(name.contains("화장실")) {
            ((TextView)(view.findViewById(R.id.txt_info))).setTypeface(CustomFontsLoader.getTypeface(getActivity(), 1));

            int sdk = android.os.Build.VERSION.SDK_INT;
            if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                ((FrameLayout)(view.findViewById(R.id.layout_info))).setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.bg_toilet02));
            } else {
                ((FrameLayout)(view.findViewById(R.id.layout_info))).setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_toilet02));
            }
            ((TextView)(view.findViewById(R.id.txt_info))).setTypeface(CustomFontsLoader.getTypeface(getActivity(), 1));
            ((TypewriterPost)(view.findViewById(R.id.txt_info))).setCharacterDelay(75);
            ((TypewriterPost)(view.findViewById(R.id.txt_info))).animateText(ment);
            return;
        }else if (name.contains("무인민원발급기")) {
            setBuildingMap(view);
            ((ImageView) (view.findViewById(R.id.img_move_01))).setVisibility(View.VISIBLE);
        }else if (name.contains("주차권")) {
            setBuildingMap(view);
            ((ImageView) (view.findViewById(R.id.img_move_02))).setVisibility(View.VISIBLE);
        }else if (name.contains("혼인신고")) {
            setBuildingMap(view);
            ((ImageView) (view.findViewById(R.id.img_move_03))).setVisibility(View.VISIBLE);
        } else if (name.contains("전입신고")) {
            setBuildingMap(view);
            ((ImageView) (view.findViewById(R.id.img_move_04))).setVisibility(View.VISIBLE);
        } else if (name.contains("서류")) {
            setBuildingMap(view);
            ((ImageView) (view.findViewById(R.id.img_move_05))).setVisibility(View.VISIBLE);
        }else{
            ((TextView) (view.findViewById(R.id.txt_info_question))).setText(ment);
        }
        ((TextView)(view.findViewById(R.id.txt_info))).setTypeface(CustomFontsLoader.getTypeface(getActivity(), 1));
        ((TypewriterPost)(view.findViewById(R.id.txt_info))).setCharacterDelay(75);
        ((TypewriterPost)(view.findViewById(R.id.txt_info))).animateText(name);

    }
    private void setInVisibleMoveIcon(View view) {
        ((ImageView) (view.findViewById(R.id.img_move_01))).setVisibility(View.INVISIBLE);
        ((ImageView) (view.findViewById(R.id.img_move_02))).setVisibility(View.INVISIBLE);
        ((ImageView) (view.findViewById(R.id.img_move_03))).setVisibility(View.INVISIBLE);
        ((ImageView) (view.findViewById(R.id.img_move_04))).setVisibility(View.INVISIBLE);
        ((ImageView) (view.findViewById(R.id.img_move_05))).setVisibility(View.INVISIBLE);
        ((TextView) (view.findViewById(R.id.txt_info_question))).setText("");
    }
    private void setBuildingMap(View view){
        int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            ((FrameLayout)(view.findViewById(R.id.layout_info))).setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.bg_building_map));
        } else {
            ((FrameLayout)(view.findViewById(R.id.layout_info))).setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_building_map));
        }
    }
}
