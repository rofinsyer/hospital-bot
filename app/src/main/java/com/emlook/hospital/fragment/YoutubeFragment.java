package com.emlook.hospital.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.emlook.hospital.R;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubePlayerView;

public class YoutubeFragment extends YouTubePlayerSupportFragment implements YouTubePlayer.OnInitializedListener {
    /***
     * Fragment 내부적으로 사용할 객체 생성(일반적인 자바와 다름)
     * @return
     */
    static YoutubeFragment obj;
    String API_KEY = "AIzaSyCf3_DXiyfaHBS0gjK7GMYA-J9HLFf1crU";
    public static YoutubeFragment newInstance() {
        obj = new YoutubeFragment();
        return obj;
    }

    public YoutubeFragment(){
        this.initialize(API_KEY, this);
    }
    public static YoutubeFragment newInstance(String name) {
        Log.d(">> newInstance ", name);
        YoutubeFragment fragment = new YoutubeFragment();

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
        View layout = inflater.inflate(R.layout.youtube, container, false);
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
       // String name = getArguments().getString("name");
       // Log.d(">> setView ", name);
//        final YouTubePlayerView youTubePlayerView = view.findViewById(R.id.view_youtube);
//        obj.initialize(API_KEY, listener);
        YouTubePlayerSupportFragment youTubePlayerFragment = (YouTubePlayerSupportFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.view_youtube);
        youTubePlayerFragment.initialize(API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo("QadSVWXF_ks");
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        });
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        youTubePlayer.loadVideo("QadSVWXF_ks");
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }
}