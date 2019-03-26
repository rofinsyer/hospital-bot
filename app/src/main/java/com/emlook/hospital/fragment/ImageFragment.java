package com.emlook.hospital.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.emlook.hospital.CustomFontsLoader;
import com.emlook.hospital.R;
import com.emlook.hospital.Utils.TypewriterPost;

public class ImageFragment extends Fragment {
    /***
     * Fragment 내부적으로 사용할 객체 생성(일반적인 자바와 다름)
     * @return
     */
    public static ImageFragment newInstance() {
        ImageFragment fragment = new ImageFragment();
        return fragment;
    }

    public static ImageFragment newInstance(String name) {
        Log.d(">> newInstance ", name);
        ImageFragment fragment = new ImageFragment();

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
        View layout = inflater.inflate(R.layout.img_content, container, false);
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

        if(name.contains("정원")){
            ((FrameLayout)(view.findViewById(R.id.layout_image))).setBackgroundResource(R.drawable.garden);
            ((TextView)(view.findViewById(R.id.txt_image))).setTypeface(CustomFontsLoader.getTypeface(getActivity(), 1));
            ((TypewriterPost)(view.findViewById(R.id.txt_image))).setCharacterDelay(100);
            ((TypewriterPost)(view.findViewById(R.id.txt_image))).animateText("12층 야외 정원에 올라가시면 됩니다.\n맑은 공기를 맡고 예쁜 하늘을 구경해 보세요.");
        }else if(name.contains("주차권")){
            ((FrameLayout)(view.findViewById(R.id.layout_image))).setBackgroundResource(R.drawable.parking_ticket);
            ((TextView)(view.findViewById(R.id.txt_image))).setTypeface(CustomFontsLoader.getTypeface(getActivity(), 1));
            ((TypewriterPost)(view.findViewById(R.id.txt_image))).setCharacterDelay(100);
            ((TypewriterPost)(view.findViewById(R.id.txt_image))).animateText("주차권의 바코드를 인식시켜주세요.\n12나1234 차량이 맞습니까?");
        }
        //Text animation
    }
}
