package com.emlook.hospital.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.emlook.hospital.R;
import com.emlook.hospital.activity.MainActivity;

public class CustomizedServiceFragment extends Fragment {

    int count = 0;
    /***
     * Fragment 내부적으로 사용할 객체 생성(일반적인 자바와 다름)
     * @return
     */
    public static CustomizedServiceFragment newInstance() {
        CustomizedServiceFragment fragment = new CustomizedServiceFragment();
        return fragment;
    }

    public static CustomizedServiceFragment newInstance(String name) {
        Log.d(">> newInstance ", name);
        CustomizedServiceFragment fragment = new CustomizedServiceFragment();

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
        View layout = inflater.inflate(R.layout.customized_service, container, false);
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
//        ((TextView)(view.findViewById(R.id.txt_info))).setTypeface(CustomFontsLoader.getTypeface(getActivity(), 1));
//        ((TextView)(view.findViewById(R.id.txt_info))).setText(name);

        WebView webView = (WebView)view.findViewById(R.id.web_service);
        webView.loadUrl("https://www.gov.kr/portal/indSvcFind/step1#noback");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new FindCustomizedServiceWebViewClient());
    }

    class FindCustomizedServiceWebViewClient extends WebViewClient{
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            Log.d("jejang", url);


            if(url.contains("https://www.gov.kr/portal/indSvcFind/step/svcFindRslt#noback")){
                if(count == 1){//페이지를 두번 띄워서 들어감
                    ((MainActivity)getActivity()).startCustomizedServiceMent();
                    count = 0;
                }
                count++;
            }
        }
    }
}
