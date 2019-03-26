package com.emlook.hospital.Utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.Log;

public class Typewriter extends AppCompatTextView {

    private CharSequence mText;
    private int mIndex;
    private long mDelay = 500; //Default 500ms delay
    Context mContext;

    public Typewriter(Context context) {
        super(context);
        mContext = context;
    }

    public Typewriter(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    private Handler mHandler = new Handler(Looper.getMainLooper());
//    private Runnable characterAdder = new Runnable() {
//        @Override
//        public void run() {
//            setText(mText.subSequence(0, mIndex++));
//            if(mIndex <= mText.length()) {
//                mHandler.postDelayed(characterAdder, mDelay);
//            }else{
//                mHandler.removeCallbacksAndMessages(null);
//            }
//        }
//    };

    public void animateText(CharSequence text) {
        mText = text;
        mIndex = 0;

        setText("");
        invokeThread();
//        mHandler = new Handler();
//        mHandler.removeCallbacks(characterAdder);
//        mHandler.removeCallbacksAndMessages(null);
//        mHandler.postDelayed(characterAdder, mDelay);
    }

    public void setCharacterDelay(long millis) {
        mDelay = millis;
    }

    public void invokeThread(){
        new Thread(new Runnable() {
            @Override
            public void run() {


                Log.d("jejang", "Result : length "+ mText.length());
                for(int i = 0; i<mText.length(); i++){
                    final int Index = i;
                    try{
                        Thread.sleep(500);
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            String str =mText.subSequence(0, Index).toString();
                            Log.d("jejang", "Result : "+ str);
                            setText(mText.subSequence(0, Index));
                        }
                    });
                }

            }
        }).run();
    }
}