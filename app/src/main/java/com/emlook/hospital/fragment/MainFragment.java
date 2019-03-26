package com.emlook.hospital.fragment;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.emlook.hospital.R;

public class MainFragment extends Fragment {
    private Handler imageHandler;
    Animation in;
    Animation out;
    ImageView imageView;
    private int animationCounter = 1;
    /***
     * Fragment 내부적으로 사용할 객체 생성(일반적인 자바와 다름)
     * @return
     */
    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    public static MainFragment newInstance(String name) {
        Log.d(">> newInstance ", name);
        MainFragment fragment = new MainFragment();

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
        final View layout = inflater.inflate(R.layout.content_main, container, false);
        WebView web = (WebView) layout.findViewById(R.id.web_main);
        web.setBackgroundColor(Color.TRANSPARENT);
        web.loadUrl("file:///android_asset/htmls/question.html");
        web.setWebViewClient(new WebViewClient() {
                                 @Override
                                 public void onPageStarted(WebView view, String url, Bitmap favicon) {
                                     super.onPageStarted(view, url, favicon);
                                     ((ImageView)layout.findViewById(R.id.img_main)).setVisibility(View.VISIBLE);
                                 }

                                 @Override
                                 public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                                     view.loadUrl("file:///android_asset/htmls/question.html");
                                     return true;
                                 }

                                 @Override
                                 public void onPageFinished(WebView view, String url) {
                                     super.onPageFinished(view, url);
                                     ((ImageView)layout.findViewById(R.id.img_main)).setVisibility(View.INVISIBLE);
                                 }
                             }

        );
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

        Log.d(">> setView ", name + " AdvertiseFragment");
        imageView = (ImageView)view.findViewById(R.id.img_advertise);

        in = AnimationUtils.loadAnimation(getActivity(), R.anim.left_to_right_in);
        out = AnimationUtils.loadAnimation(getActivity(), R.anim.left_to_right_out);

        imageHandler = new Handler(Looper.getMainLooper());
        imageHandler.post(new Runnable() {
            @Override
            public void run() {
                Log.d(">> setView ", " AdvertiseFragment imageHandler.post");
                switch(animationCounter++){
                    case 1 :
                        imageView.setImageResource(R.drawable.img_main_hospital);
                        break;
                    case 2:
                        imageView.setImageResource(R.drawable.img_190308_01);
                        break;
                    case 3 :
                        imageView.setImageResource(R.drawable.img_190308_02);
                        break;
                }
                animationCounter %=4;
                if(animationCounter == 0) animationCounter = 1;
                imageHandler.postDelayed(this, 3000);
            }
        });
    }

}
