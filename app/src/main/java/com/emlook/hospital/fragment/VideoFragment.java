package com.emlook.hospital.fragment;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.VideoView;

import com.emlook.hospital.R;
import com.emlook.hospital.activity.MainActivity;

public class VideoFragment extends Fragment {
    /***
     * Fragment 내부적으로 사용할 객체 생성(일반적인 자바와 다름)
     * @return
     */
    public static VideoFragment newInstance() {
        VideoFragment fragment = new VideoFragment();
        return fragment;
    }

    public static VideoFragment newInstance(String name) {
        Log.d(">> newInstance ", name);
        VideoFragment fragment = new VideoFragment();

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
        View layout = inflater.inflate(R.layout.video, container, false);
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
        String path = "android.resource://" + getActivity().getPackageName() + "/" + R.raw.video01;
        ((VideoView)view.findViewById(R.id.video_health)).setVideoURI(Uri.parse(path));
        ((VideoView)view.findViewById(R.id.video_health)).start();
        ((VideoView)view.findViewById(R.id.video_health)).setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                ((MainActivity)getActivity()).setFinishedVideo();
            }
        });
    }
}