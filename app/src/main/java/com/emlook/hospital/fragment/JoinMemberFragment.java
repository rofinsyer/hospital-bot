package com.emlook.hospital.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.emlook.hospital.CustomFontsLoader;
import com.emlook.hospital.R;
import com.emlook.hospital.activity.MainActivity;

public class JoinMemberFragment  extends Fragment {
    View layout;
    /***
     * Fragment 내부적으로 사용할 객체 생성(일반적인 자바와 다름)
     * @return
     */
    public static JoinMemberFragment newInstance() {
        JoinMemberFragment fragment = new JoinMemberFragment();
        return fragment;
    }

    public static JoinMemberFragment newInstance(String name) {
        Log.d(">> newInstance ", name);
        JoinMemberFragment fragment = new JoinMemberFragment();

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
        layout = inflater.inflate(R.layout.join_member, container, false);
        ((LinearLayout)(layout.findViewById(R.id.layout_join))).setVisibility(View.VISIBLE);
        ((TextView) layout.findViewById(R.id.txt_name)).setTypeface(CustomFontsLoader.getTypeface(getActivity(), 1));
        ((TextView) layout.findViewById(R.id.txt_phone)).setTypeface(CustomFontsLoader.getTypeface(getActivity(), 1));

        ((Button)(layout.findViewById(R.id.btn_ok))).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((LinearLayout)(layout.findViewById(R.id.layout_join))).setVisibility(View.INVISIBLE);
                ((TextView)(layout.findViewById(R.id.txt_complete))).setVisibility(View.VISIBLE);
                ((TextView) layout.findViewById(R.id.txt_complete)).setTypeface(CustomFontsLoader.getTypeface(getActivity(), 1));
                ((MainActivity)getActivity()).completeJoinMember();
                hideKeyboard();
            }
        });
        return layout;
    }

    public void hideKeyboard(){
        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = getActivity().getCurrentFocus();

        if(view == null){
            view = new View(getActivity());
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
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

    }
}
