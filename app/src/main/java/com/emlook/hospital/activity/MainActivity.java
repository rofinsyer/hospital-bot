package com.emlook.hospital.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.TextView;

import com.emlook.hospital.R;
import com.emlook.hospital.fragment.AdvertiseFragment;
import com.emlook.hospital.fragment.DateTimeFragment;
import com.emlook.hospital.fragment.HospitalFragment;
import com.emlook.hospital.fragment.MainFragment;
import com.emlook.hospital.fragment.MusicFragment;
import com.emlook.hospital.fragment.MusicPlayer;
import com.emlook.hospital.fragment.VideoFragment;
import com.emlook.hospital.fragment.WeatherFragment;
import com.emlook.hospital.fragment.YoutubeFragment;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.sanbot.opensdk.beans.FuncConstant;
import com.sanbot.opensdk.beans.OperationResult;
import com.sanbot.opensdk.function.beans.SpeakOption;
import com.sanbot.opensdk.function.beans.handmotion.AbsoluteAngleHandMotion;
import com.sanbot.opensdk.function.beans.handmotion.NoAngleHandMotion;
import com.sanbot.opensdk.function.beans.headmotion.AbsoluteAngleHeadMotion;
import com.sanbot.opensdk.function.beans.speech.Grammar;
import com.sanbot.opensdk.function.unit.HardWareManager;
import com.sanbot.opensdk.function.unit.SpeechManager;
import com.sanbot.opensdk.function.unit.WheelMotionManager;
import com.sanbot.opensdk.function.unit.interfaces.hardware.TouchSensorListener;
import com.sanbot.opensdk.function.unit.interfaces.speech.RecognizeListener;
import com.sanbot.opensdk.function.unit.interfaces.speech.WakenListener;

import static com.emlook.hospital.fragment.WeatherFragment.API_KEY;

public class MainActivity extends BaseActivity {

    SpeechManager speechManager = (SpeechManager) getUnitManager(FuncConstant.SPEECH_MANAGER);
    HardWareManager hardWareManager = (HardWareManager) getUnitManager(FuncConstant.HARDWARE_MANAGER);
    WheelMotionManager wheelMotionManager = (WheelMotionManager) getUnitManager(FuncConstant.WHEELMOTION_MANAGER);

    TextView speak_show;

    Activity activity;

    // 음성인식
    private String voiceState = "0";
    OperationResult operationReusult;
    private int sleep_num = 0;
    private int car_parking = 0;
    private int married = 0;
    private int visit_service = 0;
    boolean isPlayingVideo = false;


    Handler handler = new Handler();

    private int adImgIndex = 0;
    private String[] adImgName = {"img_190308_01", "img_190308_02", "img_190308_03"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        register(MainActivity.class);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activity = this;
    }

    @Override
    public void onMainServiceConnected() {
        super.onMainServiceConnected();

        speechManagerStart("안녕하세요, 국민과 함께 건강한 내일을 만드는 한국건강관리협회입니다. 무엇을 도와드릴까요?");

        moveCenterDownHead();
        moveDownHand();

        hardWareManager.setOnHareWareListener(new TouchSensorListener() {
            @Override
            public void onTouch(int part) {

                Log.d("touch_head : {}", String.valueOf(part));

                switch (part) {
//                    case 5: //touch_back_head_left
//                    case 6: //touch_back_head_right
                    case 11: //touch_head_middle
//                    case 12: //touch_head_left
//                    case 13: //touch_head_right

                        speechManager.doWakeUp();
                        speechManagerStart("안녕하세요, 국민과 함께 건강한 내일을 만드는 한국건강관리협회입니다. 무엇을 도와드릴까요?");
                        FragmentManager fm = getSupportFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();

                        ft.replace(R.id.fragment, AdvertiseFragment.newInstance("메인"));
                        ft.commit();

                        moveCenterDownHead();
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                moveUpRightHand();
                                moveUpLeftHand();
                            }
                        });

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                moveDownHand();

                            }
                        }, 3000);

                        setFinishedVideo();
                        MusicPlayer.newInstance().stopMusic();
                        break;
                }
            }
        });

        speechManager.setOnSpeechListener(new WakenListener() {
            @Override
            public void onWakeUp() {
            }

            @Override
            public void onSleep() {
                try {

                    if (isPlayingVideo == true) {
                        return;
                    }
                    Thread.sleep(7500);
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();

                    ft.replace(R.id.fragment, AdvertiseFragment.newInstance("메인"));
                    ft.commit();

                    speechManagerStart("안녕하세요, 국민과 함께 건강한 내일을 만드는 한국건강관리협회입니다. 무엇을 도와드릴까요?");
                    moveCenterDownHead();
                    moveDownHand();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                speechManager.doWakeUp();
            }
        });

        speechManager.setOnSpeechListener(new RecognizeListener() {
            @Override
            public void onStopRecognize() {
            }

            @Override
            public void onStartRecognize() {
            }

            @Override
            public void onRecognizeVolume(int i) {
            }

            @Override
            public void onError(int i, int i1) {
                Log.d(">> Recognize onError", String.valueOf(i));
            }

            @Override
            public boolean onRecognizeResult(Grammar grammar) {
                String input = grammar.getText();
                Log.d(">> Recognize Result", input);

                setFragment(input);
                return true;
            }
        });
        speechManager.doWakeUp();
    }

    /***
     * speechManagerStart :
     * @param text
     */
    public void speechManagerStart(String text) {

        Log.d(">> voiceState", voiceState.toString());

        try {
            if (!"0".equals(voiceState)) {
                Thread.sleep(400);
            }

            speechManagerStop();
            SpeakOption speakOption = new SpeakOption();
            speakOption.setSpeed(50);
            operationReusult = speechManager.startSpeak(text, speakOption);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /***
     * speechManagerStop
     */
    public void speechManagerStop() {
        operationReusult = speechManager.isSpeaking();
        voiceState = operationReusult.getResult();
        speechManager.stopSpeak();
    }

    public void moveUpRightHand() {
        NoAngleHandMotion motion = new NoAngleHandMotion(NoAngleHandMotion.PART_RIGHT, 5, NoAngleHandMotion.ACTION_UP);
        handMotionManager.doNoAngleMotion(motion);
    }

    public void moveUpLeftHand() {
        NoAngleHandMotion motion = new NoAngleHandMotion(NoAngleHandMotion.PART_LEFT, 5, NoAngleHandMotion.ACTION_UP);
        handMotionManager.doNoAngleMotion(motion);
    }

    public void moveDownHand() {
        AbsoluteAngleHandMotion motion = new AbsoluteAngleHandMotion(NoAngleHandMotion.PART_RIGHT, 5, 180);
        AbsoluteAngleHandMotion motion2 = new AbsoluteAngleHandMotion(NoAngleHandMotion.PART_LEFT, 5, 180);
        handMotionManager.doAbsoluteAngleMotion(motion);
        handMotionManager.doAbsoluteAngleMotion(motion2);
    }

    public void moveCenterUpHead() {
        //headMotionManager.dohorizontalCenterLockMotion();
//        RelativeAngleHeadMotion motion = new RelativeAngleHeadMotion(RelativeAngleHeadMotion.ACTION_UP, 3);
        AbsoluteAngleHeadMotion motion2 = new AbsoluteAngleHeadMotion(AbsoluteAngleHeadMotion.ACTION_VERTICAL, 40);
        headMotionManager.doAbsoluteAngleMotion(motion2);
    }

    public void moveCenterDownHead() {
        headMotionManager.dohorizontalCenterLockMotion();
//        RelativeAngleHeadMotion motion = new RelativeAngleHeadMotion(RelativeAngleHeadMotion.ACTION_DOWN, 3);
        AbsoluteAngleHeadMotion motion2 = new AbsoluteAngleHeadMotion(AbsoluteAngleHeadMotion.ACTION_VERTICAL, 0);
        headMotionManager.doAbsoluteAngleMotion(motion2);
    }

    private void setFragment(String input) {
        String ment = "";
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (input.indexOf("시간") > -1 || input.indexOf("몇시") > -1 || input.indexOf("몇 시") > -1) {
            ft.replace(R.id.fragment, DateTimeFragment.newInstance("time"));
            moveUpRightHand();
            moveUpLeftHand();
            moveCenterUpHead();
        } else if (input.indexOf("날짜") > -1 || input.indexOf("며칠") > -1) {
            ft.replace(R.id.fragment, DateTimeFragment.newInstance("date"));
            moveUpRightHand();
            moveUpLeftHand();
            moveCenterUpHead();
        } else if (input.indexOf("날씨") > -1 || input.indexOf("미세먼지") > -1) {
            ft.replace(R.id.fragment, WeatherFragment.newInstance("weather"));
            moveUpRightHand();
            moveUpLeftHand();
            moveCenterUpHead();
        } else if (input.contains("누구")) {
            voiceState = "1";
            ment = "저는 병원안내로봇입니다. 무엇이든 물어보세요.";
            moveUpRightHand();
            moveUpLeftHand();
            moveCenterUpHead();
        } else if (input.contains("스트레칭") || input.contains("영상")) {
            voiceState = "1";
            ment = "스트레칭 동작 영상을 재생합니다. 같이 따라해보시기 바랍니다.";
            ft.replace(R.id.fragment, VideoFragment.newInstance("동영상"));
            setStartVideo();
            moveArmRepeat();
            moveCenterUpHead();

        } else if (input.contains("협회 소개") ||input.contains("협회소개")) {
            voiceState = "1";
            ment = "한국건강관리협회는, 보건복지부장관이, 국민보건의료 시책 상, 필요로 하는, 질환의 조기 발견, 예방을 위한 효율적인 건강검진과 치료, 역학적 조사연구 및 보건교육을 실시하고 있습니다.";
            ft.replace(R.id.fragment, HospitalFragment.newInstance("협회소개"));
            moveUpRightHand();
            moveUpLeftHand();
            moveCenterUpHead();
        } else if (input.contains("협회 조직") || input.contains("협회조직")) {
            voiceState = "1";
            ment = "한국건강관리협회 조직은 기획조정본부, 운영관리본부, 사업관리 본부, 검진관리본부, 건강증진본부가 있습니다.";
            ft.replace(R.id.fragment, HospitalFragment.newInstance("조직도"));
            moveUpRightHand();
            moveUpLeftHand();
            moveCenterUpHead();
        } else if (input.contains("검진 안내") || input.contains("검진안내")) {
            voiceState = "1";
            ment = "건강검진안내를 해드리겠습니다. 저희 한국건강관리협회는 기초생리기능검사, 혈액질환검사, 흉부촬영검사 등을 시행하고 있습니다. 더 자세히 알고 싶은 검사 내용을 말씀해주세요.";
            ft.replace(R.id.fragment, HospitalFragment.newInstance("건강검진안내"));
            moveUpRightHand();
            moveUpLeftHand();
            moveCenterUpHead();
        } else if (input.contains("기초 생리") || input.contains("기초생리")) {
            voiceState = "1";
            ment = "기초 생리 기능 검사는 문진, 키와 몸무게의 측정, 시력과 혈압의 측정 등 건강검진의 기본적인 검사 항목들을 검사합니다.";
            ft.replace(R.id.fragment, HospitalFragment.newInstance("기초생리기능검사"));
            moveUpRightHand();
            moveUpLeftHand();
            moveCenterUpHead();
        } else if (input.contains("혈액")) {
            voiceState = "1";
            ment = "혈액은 몸 안의 세포에 필요한 산소와 영양소를 공급합니다. 혈액은 세포의 대사작용에 의해 발생하는\n" +
                    " 이산화탄소와 노폐물을 배출하는 역할을 합니다. 혈액질환 검사는 이러한 혈액 속 세포 성분과 관련된 검사를 합니다.";
            ft.replace(R.id.fragment, HospitalFragment.newInstance("혈액질환검사"));
            moveUpRightHand();
            moveUpLeftHand();
            moveCenterUpHead();
        }

        else if (input.contains("흉부 촬영") || input.contains("흉부촬영")) {
            voiceState = "1";
            ment = "흉부촬영검사는 영상의학검사에서 가장 기본의 되는 검사로써, 양측의 폐, 기관과 기관지, 종격동, 늑골, 쇄골, 횡경막, 위의 상부 등을 관찰하는 검사입니다.";
            ft.replace(R.id.fragment, HospitalFragment.newInstance("흉부촬영검사"));
            moveUpRightHand();
            moveUpLeftHand();
            moveCenterUpHead();
        }

        else if (input.contains("검진 순서") || input.contains("검진순서")) {
            voiceState = "1";
            ment = "건강검진 순서는 다음과 같습니다. 먼저 상담과 접수를 한 후 문진표를 작성합니다. 그 다음 기초검사, 채뇨, 채변 검사, 채혈, 호흡기능 검사, 심전도, 안저, 초음파 검사, 흉부 X선, 체성분 검사, 스트레스 측정, 결과상담 순으로 진행됩니다.";
            ft.replace(R.id.fragment, HospitalFragment.newInstance("검진순서"));
            moveUpRightHand();
            moveUpLeftHand();
            moveCenterUpHead();
        } else if (input.contains("검진 예약") || input.contains("검진예약")) {
            voiceState = "1";
            ment = "한국건강관리협회 메디체크에서 실시하는 건강검진은 일반, 기업, 해외검진으로 나누어 예약하실 수 있습니다. 홈페이지를 통하여 예약하실 수 있습니다. ";
            ft.replace(R.id.fragment, HospitalFragment.newInstance("검진예약"));
            moveUpRightHand();
            moveUpLeftHand();
            moveCenterUpHead();
        } else if (input.contains("상담")) {
            voiceState = "1";
            ment = "검진과 관련한 상담이 필요한 경우 홈페이지의 간편 예약 검진 상담신청을 이용하시면, 자세한 상담을 받으실 수 있습니다. ";
            ft.replace(R.id.fragment, HospitalFragment.newInstance("검진상담"));
            moveUpRightHand();
            moveUpLeftHand();
            moveCenterUpHead();
        } else if (input.indexOf("노래") > -1 || input.indexOf("음악") > -1) {
            if (input.indexOf("정지") > -1 || input.indexOf("중지") > -1 || input.indexOf("끄기") > -1 || input.indexOf("꺼") > -1 || input.contains("종료")) {
                voiceState = "1";
                ment = "음악 재생을 중지합니다.";
                MusicPlayer.newInstance().stopMusic();
                ft.replace(R.id.fragment, AdvertiseFragment.newInstance("메인"));
                moveCenterDownHead();
                moveDownHand();
            } else if (input.indexOf("재생") > -1 || input.indexOf("틀어") > -1 || input.contains("들어")) {
                voiceState = "1";
                ment = "음악을 재생해 드릴게요.";
                ft.replace(R.id.fragment, MusicFragment.newInstance("음악"));
                MusicPlayer.newInstance().playMusic(getApplicationContext());
                moveUpRightHand();
                moveUpLeftHand();
                moveCenterUpHead();
            } else {
//                voiceState = "1";
//                ment = "음악을 재생해 드릴게요.";
//                ft.replace(R.id.fragment, MusicFragment.newInstance("음악"));
//                MusicPlayer.newInstance().playMusic(getApplicationContext());
//                moveUpRightHand();
//                moveUpLeftHand();
//                moveCenterUpHead();
            }
        } else if (input.indexOf("정지") > -1 || input.indexOf("중지") > -1 || input.indexOf("끄기") > -1 || input.contains("종료")) {
            voiceState = "1";
            ment = "음악 재생을 중지합니다.";
            MusicPlayer.newInstance().stopMusic();
            ft.replace(R.id.fragment, AdvertiseFragment.newInstance("메인"));
            moveCenterDownHead();
            moveDownHand();
        }
//        else if (input.contains("CPK") || input.contains("씨피케이")
//                || input.contains("시피케이")
//                || input.contains("CP 케이")
//                || input.contains("지피케이")
//                || input.contains("집 피케이")
//                || input.contains("피케이")
//                || input.contains("히비키")
//                || input.contains("집이 케이")
//                || input.contains("12개"))
//    {
//            voiceState = "1";
//            ment = "급성심근경색을 확인하는 검사입니다.";
//            ft.replace(R.id.fragment, HospitalFragment.newInstance("CPK"));
//            moveUpLeftHand();
//            moveCenterUpHead();
//        }
        else if(input.contains("당뇨")){
            voiceState = "1";
            ment = "당뇨검사 항목에는 글루코스, 에이치비 에이원C 검사가 있습니다.";
            ft.replace(R.id.fragment, HospitalFragment.newInstance("당뇨"));
            moveUpRightHand();
            moveUpLeftHand();
            moveCenterUpHead();
        } else {
            voiceState = "0";
            ft.replace(R.id.fragment, AdvertiseFragment.newInstance("메인"));
//            voiceState = "1";
//            ment = "제가 알아듣지 못한 질문 입니다. ";
//            moveCenterDownHead();
//            moveDownHand();
        }
        ft.commit();
        speechManagerStart(ment);
        speechManager.doWakeUp();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                moveDownHand();
//                moveCenterDownHead();
            }
        }, 3000);
    }

    public void setStartVideo() {
        isPlayingVideo = true;
    }

    boolean isUp = false;
    Runnable downHand = new Runnable() {
        @Override
        public void run() {
            if (isUp == true) {
                moveDownHand();
                isUp = false;
            }
            if (isPlayingVideo) {
                handler.postDelayed(upHand, 3000);
            }
        }
    };

    Runnable upHand = new Runnable() {
        @Override
        public void run() {
            if (isUp == false) {
                moveUpRightHand();
                moveUpLeftHand();
                isUp = true;
            }
            if (isPlayingVideo) {
                handler.postDelayed(downHand, 3000);
            }
        }
    };


    public void moveArmRepeat() {
        handler.post(upHand);
    }

    public void setFinishedVideo() {
        isPlayingVideo = false;
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.fragment, AdvertiseFragment.newInstance("메인"));
        ft.commit();

        speechManagerStart("안녕하세요, 국민과 함께 건강한 내일을 만드는 한국건강관리협회입니다. 무엇을 도와드릴까요?");
        moveCenterDownHead();
        moveDownHand();
        speechManager.doWakeUp();
    }
//    private void setFragment(String input) {
//        String ment = "";
//        FragmentManager fm = getSupportFragmentManager();
//        FragmentTransaction ft = fm.beginTransaction();
//        if (input.indexOf("시간") > -1 || input.indexOf("몇시") > -1) {
//            ft.replace(R.id.fragment, DateTimeFragment.newInstance("time"));
//        } else if (input.indexOf("날짜") > -1 || input.indexOf("며칠") > -1) {
//            ft.replace(R.id.fragment, DateTimeFragment.newInstance("date"));
//        } else if (input.indexOf("날씨") > -1 || input.indexOf("미세먼지") > -1) {
//            ft.replace(R.id.fragment, WeatherFragment.newInstance("weather"));
//        } else if (input.indexOf("메뉴") > -1) {
//            voiceState = "1";
//            ment = "민원봇은 시설물 안내와, 민원 안내, 조직도 안내를 해드립니다. ";
//        } else if (input.indexOf("시설물") > -1) {
//            voiceState = "1";
//            ment = "어느 시설물을 찾으시나요?";
//        } else if (input.indexOf("무인민원발급기") > -1 || input.indexOf("민원발급기") > -1 || input.indexOf("발급기") > -1) {
//            voiceState = "1";
//            ment = "무인민원발급기는 입구 중앙 계단 앞에 비치되어 있습니다.";
//            ft.replace(R.id.fragment, WhereInfoFragment.newInstance("무인민원발급기"));
//        } else if (input.indexOf("화장실") > -1) {
//            voiceState = "1";
//            ment = "화장실은 입구 중앙 계단 옆에 있습니다. ";
//            ft.replace(R.id.fragment, WhereInfoFragment.newInstance("화장실"));
//        } else if (input.indexOf("행정") > -1 || input.indexOf("행정지원") > -1 || input.indexOf("행정 지원") > -1 || input.indexOf("행정지원과") > -1) {
//            voiceState = "1";
//            ment = "행정지원과는 입구에서 왼편으로 20미터 가시면 있습니다.";
//            ft.replace(R.id.fragment, WhereInfoFragment.newInstance("행정지원과"));
//        } else if (input.indexOf("시민") > -1 || input.indexOf("시민봉사") > -1 || input.indexOf("시민 봉사") > -1 || input.indexOf("시민봉사과") > -1) {
//            voiceState = "1";
//            ment = "시민봉사과는 입구에서 왼편으로 15미터 가시면 있습니다.";
//            ft.replace(R.id.fragment, WhereInfoFragment.newInstance("시민봉사과"));
//        } else if (input.indexOf("사회") > -1 || input.indexOf("사회복지") > -1 || input.indexOf("사회 복지") > -1 || input.indexOf("사회복지과") > -1) {
//            voiceState = "1";
//            ment = "사회복지과는 입구에서 오른편으로  20미터 가시면 있습니다.";
//            ft.replace(R.id.fragment, WhereInfoFragment.newInstance("사회복지과"));
//        } else if (input.indexOf("가정") > -1 || input.indexOf("가정복지") > -1 || input.indexOf("가정 복지") > -1 || input.indexOf("가정복지과") > -1) {
//            voiceState = "1";
//            ment = "가정복지과는 입구에서 오른편으로 15미터 가시면 있습니다.";
//            ft.replace(R.id.fragment, WhereInfoFragment.newInstance("가정복지과"));
//        } else if (input.indexOf("정원") > -1 || input.indexOf("야외정원") > -1 || input.indexOf("야외 정원") > -1) {
//            voiceState = "1";
//            ment = "야외정원은 입구 중앙 계단 뒤편에 있습니다.";
//            ft.replace(R.id.fragment, WhereInfoFragment.newInstance("야외정원"));
//        } else if (input.indexOf("민원") > -1 || input.indexOf("민원안내") > -1 || input.indexOf("민원 안내") > -1) {
//            voiceState = "1";
//            ment = "어느 민원에 대한 설명이 필요 하신가요?";
//        } else if (input.indexOf("대형") > -1 || input.indexOf("대형폐기물") > -1 || input.indexOf("대형 폐기물") > -1 || input.indexOf("폐기물") > -1) {
//            voiceState = "1";
//            ment = "대형 폐기물은 접수를 통해 품목과 일정을 문의합니다. 해당 수수료를 납부하고 스티커를 부착해 배출하시면 됩니다.";
//            ft.replace(R.id.fragment, MinwonFragment.newInstance("대형폐기물"));
//        } else if (input.indexOf("여권") > -1) {
//            voiceState = "1";
//            ment = "여권에 부착할 사진과 여권 발급 신청서를 작성하시고 신분증과 함께 창구로 오시면 됩니다.";
//            ft.replace(R.id.fragment, MinwonFragment.newInstance("여권"));
//        } else if (input.indexOf("건축") > -1 || input.indexOf("건축 허가") > -1) {
//            voiceState = "1";
//            ment = "건축 허가는 건축 허가 신고를 하신 후 철거 또는 멸실된 이후 착공 신고를 합니다. 이후 사용승인을 하신후에 건축물대장이 나오면 유지관리 하시면 됩니다. ";
//        } else if (input.indexOf("도로") > -1 || input.indexOf("도로 점용") > -1 || input.indexOf("도로 점용 허가") > -1
//                || input.indexOf("도로점용") > -1 || input.indexOf("도로점용허가") > -1) {
//            voiceState = "1";
//            ment = "도로점용허가는 허가 신청을 하신 후 관련 부서와의 협의를 합니다. 점용허가가 나면 점용료를 납부하시고 점용공사를 착수하시면 됩니다.";
//        } else if (input.indexOf("조직") > -1 || input.indexOf("조직도") > -1) {
//            voiceState = "1";
//            ment = "저희 구청의 조직도는 구청장과 그 아래 행정지원국, 시민봉사국, 사회복지국, 가정복지국, 안전생활국, 도시관리국으로 구성되어 있습니다.";
//            ft.replace(R.id.fragment, OfficeMemberFragment.newInstance("조직도"));
//        } else if (input.indexOf("노래") > -1 || input.indexOf("음악") > -1) {
//            if (input.indexOf("중지") > -1 || input.indexOf("끄기") > -1 || input.indexOf("꺼") > -1) {
//                voiceState = "1";
//                ment = "음악 재생을 중지합니다.";
//                MusicPlayer.newInstance().stopMusic();
//            }else if (input.indexOf("재생") > -1 || input.indexOf("틀어") > -1){
//                voiceState = "1";
//                ment = "음악을 재생해 드릴게요.";
//                ft.replace(R.id.fragment, MusicFragment.newInstance("음악"));
//                MusicPlayer.newInstance().playMusic(getApplicationContext());
//            }else {
//                voiceState = "1";
//                ment = "음악을 재생해 드릴게요.";
//                ft.replace(R.id.fragment, MusicFragment.newInstance("음악"));
//                MusicPlayer.newInstance().playMusic(getApplicationContext());
//            }
//        } else if(input.indexOf("중지") > -1 || input.indexOf("끄기") > -1){
//            voiceState = "1";
//            ment = "음악 재생을 중지합니다.";
//            MusicPlayer.newInstance().stopMusic();
//        } else {
//            voiceState = "0";
//            ft.replace(R.id.fragment, MainFragment.newInstance(input));
//        }
//        ft.commit();
//        speechManagerStart(ment);
//        speechManager.doWakeUp();
//    }


    public void startCustomizedServiceMent() {
        String ment = "보여드린 정보는 마음에 드시나요? 회원 가입시 모바일로 더 많은 정보를 보내드립니다. 회원가입 하시겠어요?";
        speechManagerStart(ment);
        visit_service = 2;
        speechManager.doWakeUp();
    }

    public void completeJoinMember() {
        String ment = "회원가입이 완료되었습니다. 많은 이용 부탁드립니다.";
        speechManagerStart(ment);
        visit_service = 0;
        speechManager.doWakeUp();
    }
/***
 *
 * @param input
 */
//    private void setFragment(String input) {
//        FragmentManager fm = getSupportFragmentManager();
//        FragmentTransaction ft = fm.beginTransaction();
//
//        Log.d(">>", String.valueOf(input.indexOf("뒤")));
//
//        if (input.indexOf("시간") > -1 || input.indexOf("몇시") > -1) {
//            ft.replace(R.id.fragment, DateTimeFragment.newInstance("time"));
//        } else if (input.indexOf("날짜") > -1 || input.indexOf("며칠") > -1) {
//            ft.replace(R.id.fragment, DateTimeFragment.newInstance("date"));
//        } else if(input.indexOf("날씨") > -1  || input.indexOf("미세먼지") > -1) {
//            ft.replace(R.id.fragment, WeatherFragment.newInstance("weather"));
//        } else if (input.indexOf("화장실") > -1) {
//            voiceState = "1";
//            speechManagerStart("화장실을 찾으시는군요! 화장실은 우리은행 맞은 편에 위치하고 있습니다.");
//            ft.replace(R.id.fragment, WhereInfoFragment.newInstance("toilet"));
//        } else if (input.indexOf("여권") > -1) {
//            voiceState = "1";
//            String ment = "", name = "";
//            if (input.indexOf("언제") > -1 || input.indexOf("찾을") > -1 || input.indexOf("교부") > -1 || input.indexOf("받을") > -1) {
//                ment = "여권 교부일 안내 입니다.\n" +
//                        "여권 신청인의 신원 조회 상 문제가 없을 경우, " +
//                        "신청일로부터 4일째 되는 날 여권을 찾으실 수 있습니다. " +
//                        "월요일에 여권신청을 하신 경우, 목요일 오전부터 여권교부가 가능합니다. " +
//                        "단, 연장근로시간에 여권을 신청하신 경우, 다음 날 오전에 접수되므로 " +
//                        "이 경우에는 금요일 오전부터 여권교부가 가능합니다." +
//                        "여권교부는 토, 일요일과 공휴일을 제외한 평일에만 가능합니다.";
//                name = "when";
//            } else if (input.indexOf("수수료") > -1 || input.indexOf("얼마") > -1) {
//                ment = "여권 발급 수수료 안내 입니다." +
//                        "일반인의 경우," +
//                        "10년 이내의 복수여권, 48면은 53000원, 24면은 50000원입니다." +
//                        "8세 이상 18세 미만의 경우, " +
//                        "5년 이내의 복수여권, 48면은 45000원, 24면은 42000원이고," +
//                        "8세 미만의 5년 이내 복수여권, 48면은 33000원, 24면은 30000원입니다." +
//                        "1년 이내의 단수여권의 경우, 20000원의 수수료가 부여됩니다." +
//                        "자세한 내용은, 외교부 여권안내 홈페이지를, 참조해주시기 바랍니다." +
//                        "http://www.passport.go.kr/issue/commission.php";
//                name = "how_much";
//            } else if (input.indexOf("서류") > -1 || input.indexOf("뭐가") > -1 || input.indexOf("어떤") > -1) {
//                ment = "여권 신청 서류 안내 입니다. " +
//                        "일반인의 경우, 여권발급신청서, 여권용 사진 1매, " +
//                        "전자여권이 아닌 경우는 2매, 신분증, 병역관계 서류가 필요합니다." +
//                        "18세 미만의 미성년인 경우, 법정대리인 동의서와, 대리인의 친필 사인, " +
//                        "법정 대리인의 신분증과, 기본증명서 및, 가족증명서가 추가로 필요합니다." +
//                        "자세한 내용은, 외교부 여권발급 홈페이지를, 참조해주세요." +
//                        "http://www.passport.go.kr/issue/general.php";
//                name = "what";
//            } else if (input.indexOf("근무시간") > -1) {
//                ment = "민원여권과 근무시간 안내입니다." +
//                        "여권 접수 업무는 매주 월요일 오전 9시부터 오후 8시까지 하고 있으며," +
//                        "여권 교부 업무는 매주 월요일부터 금요일까지 오전 9시에서 오후 8시까지 가능합니다." +
//                        "오전 11시 30분부터 오후 1시 30분까지는 점심시간 교대업무로 인해 대기 시간이 길어질 수 있으니 양해바랍니다. ";
//                name = "how_long";
//            } else {
//                ment = "민원여권과를 찾으시군요. ";
//                name = "where";
//            }
//            speechManagerStart(ment);
//            if(name.equals("where")){
//                ft.replace(R.id.fragment, WhereInfoFragment.newInstance(name));
//            }else{
//                ft.replace(R.id.fragment, InfoFragment.newInstance(name));
//            }
//        } else if ((input.indexOf("민원여권과") > -1 && input.indexOf("위치") > -1)
//                || input.indexOf("주민등록") > -1 || input.indexOf("발급") > -1
//                || input.indexOf("전입") > -1 || input.indexOf("전출") > -1
//                || input.indexOf("출생신고") > -1 || input.indexOf("혼인신고") > -1
//                ) {
//            voiceState = "1";
//            speechManagerStart("민원여권과를 찾으시군요.");
//            ft.replace(R.id.fragment, WhereInfoFragment.newInstance("where"));
//        } else if (input.indexOf("뭘") > -1 || input.indexOf("메뉴") > -1) {
//            voiceState = "1";
//            speechManagerStart("저는 이런 것들을 할 수 있어요. 무엇을 도와드릴까요?");
//            ft.replace(R.id.fragment, InfoFragment.newInstance("menu"));
//        } else if (input.indexOf("뒤") > -1 || input.indexOf("처음") > -1) {
//            voiceState = "1";
//            ft.replace(R.id.fragment, MainFragment.newInstance(input));
//        } else {
//            voiceState = "0";
//            ft.replace(R.id.fragment, MainFragment.newInstance(input));
//        }
//        ft.commit();
//    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        NoAngleWheelMotion wheelMotion = new NoAngleWheelMotion(NoAngleWheelMotion.ACTION_STOP_RUN, 1, 1);
//        wheelMotionManager.doNoAngleMotion(wheelMotion);
//    }
//
//    @Override
//    public void onRestart() {
//        super.onRestart();
//        NoAngleWheelMotion wheelMotion = new NoAngleWheelMotion(NoAngleWheelMotion.ACTION_STOP_RUN, 1, 1);
//        wheelMotionManager.doNoAngleMotion(wheelMotion);
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        NoAngleWheelMotion wheelMotion = new NoAngleWheelMotion(NoAngleWheelMotion.ACTION_FORWARD_RUN, 1, 1);
//        wheelMotionManager.doNoAngleMotion(wheelMotion);
//    }

}
