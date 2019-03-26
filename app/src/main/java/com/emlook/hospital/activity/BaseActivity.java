package com.emlook.hospital.activity;

import com.sanbot.opensdk.base.BindBaseActivity;
import com.sanbot.opensdk.beans.FuncConstant;
import com.sanbot.opensdk.function.unit.HandMotionManager;
import com.sanbot.opensdk.function.unit.HardWareManager;
import com.sanbot.opensdk.function.unit.HeadMotionManager;
import com.sanbot.opensdk.function.unit.ModularMotionManager;
import com.sanbot.opensdk.function.unit.SpeechManager;
import com.sanbot.opensdk.function.unit.SystemManager;
import com.sanbot.opensdk.function.unit.WheelMotionManager;

public class BaseActivity extends BindBaseActivity {

    public static SpeechManager speechManager;
    public static HardWareManager hardWareManager;
    public static HeadMotionManager headMotionManager;
    public static HandMotionManager handMotionManager;
    public static WheelMotionManager wheelMotionManager;
    public static SystemManager systemManager;
    public static ModularMotionManager modularMotionManager;


    @Override
    protected void onMainServiceConnected() {
        speechManager = (SpeechManager)getUnitManager(FuncConstant.SPEECH_MANAGER);
        hardWareManager = (HardWareManager)getUnitManager(FuncConstant.HARDWARE_MANAGER);
        headMotionManager = (HeadMotionManager)getUnitManager(FuncConstant.HEADMOTION_MANAGER);
        handMotionManager = (HandMotionManager)getUnitManager(FuncConstant.HANDMOTION_MANAGER);
        wheelMotionManager = (WheelMotionManager)getUnitManager(FuncConstant.WHEELMOTION_MANAGER);
        systemManager = (SystemManager)getUnitManager(FuncConstant.SYSTEM_MANAGER);
        modularMotionManager = (ModularMotionManager)getUnitManager(FuncConstant.MODULARMOTION_MANAGER);
    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        register(BaseActivity.class);
//        super.onCreate(savedInstanceState);
//    }
}
