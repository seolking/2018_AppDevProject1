package kr.ac.cau.manny.appdevproject1;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;


public class MainActivity extends Activity implements View.OnClickListener{
    public static final int FRAG_MAIN = 0;
    public static final int FRAG_CALENDAR = 1;
    public static final int FRAG_CONTENT = 2;
    //public static final int FRAG_SORTEDLIST = 3;
    public static final int FRAG_CONTENT1 = 4;
    public static final int FRAG_CONTENT2 = 5;
    public static final int FRAG_CONTENT3 = 6;
    public static final int FRAG_CONTENT4 = 7;

    public SharedPreferences pref;
    public SharedPreferences.Editor prefEditor;
    public boolean isAlarmOn;



    // 첫 실행에 IntroActivity를 실행시키기 위한 boolean
    Boolean isFirstRun = true;



    // 뷰 바인딩을 할 필요 없는 것도 일단 선언해둔다.

    // DrawerLayout 뷰
    DrawerLayout drawerLayout;
    RelativeLayout topArea;
    TextView drawText;
    Switch alarmSwitch;
    View divideLine1;
    ConstraintLayout listArea;

    RelativeLayout quickMenuArea;
    RelativeLayout quickMenu1;
    RelativeLayout quickMenu2;
    RelativeLayout quickMenu3;



    // 메인 화면 뷰
    RelativeLayout mainArea;

    // 타이틀 바 뷰
    RelativeLayout titleBarArea;
    TextView titleText;
    ImageButton drawerButton;
    ImageButton backButton;

    // 프래그먼트 구역 뷰
    FrameLayout fragmentArea;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        pref = getSharedPreferences("MainActivityPref", MODE_PRIVATE);
        prefEditor = pref.edit();
        isAlarmOn = pref.getBoolean("AlarmStatus",false);


        // DrawerLayout 뷰 바인딩
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        alarmSwitch = (Switch) findViewById(R.id.alarmSwitch);
        alarmSwitch.setChecked(isAlarmOn);

        quickMenu1 = (RelativeLayout) findViewById(R.id.quickMenu1);
        quickMenu2 = (RelativeLayout) findViewById(R.id.quickMenu2);

        quickMenu1.setOnClickListener(this);
        quickMenu2.setOnClickListener(this);



        // 메인화면 뷰 바인딩
        mainArea = (RelativeLayout) findViewById(R.id.mainArea);

        // 타이틀바 뷰 바인딩
        titleBarArea = (RelativeLayout)findViewById(R.id.titleBarArea);
        titleText = (TextView)findViewById(R.id.titleText);
        drawerButton = (ImageButton)findViewById(R.id.drawerButton);
        backButton = (ImageButton)findViewById(R.id.backButton);
        backButton.setVisibility(View.GONE);


        // 프래그먼트 구역 뷰 바인딩
        fragmentArea = (FrameLayout) findViewById(R.id.fragmentArea);


        // Drawer 꺼내기 버튼
        drawerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(Gravity.START);
            }
        });


        // 알람스위치 설정
        alarmSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean current_status) {
                Toast.makeText(MainActivity.this, "체크상태" + current_status, Toast.LENGTH_LONG).show();
                prefEditor.remove("AlarmStatus");
                prefEditor.putBoolean("AlarmStatus", current_status);
                prefEditor.apply();
            }
        });

        setFragmentArea(FRAG_MAIN);
    }

    @Override
    protected void onResume() {
        super.onResume();
        titleText.setText(R.string.app_title);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // 첫 실행이면 IntroActivity를 실행한다
        if (isFirstRun) {
            startActivity(new Intent(this, IntroActivity.class));
            isFirstRun = false;
        }
    }

    // 프래그먼트 변경 메소드
    public void setFragmentArea (int selection){
        Fragment frag;
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction trans = fragmentManager.beginTransaction();

        Bundle bundle = new Bundle();

        switch(selection){
            case FRAG_MAIN: // 메인화면
                frag = new FragmentMain();

                break;

            case FRAG_CALENDAR: // 달력화면
                frag = new FragmentCalendar();
                break;

            case FRAG_CONTENT: // Content 보여주는 화면
                frag = new FragmentContent();
                break;

            case FRAG_CONTENT1: // content 1
                bundle.putInt("content_num",FRAG_CONTENT1 - 4);
                frag = new FragmentContentSee();
                frag.setArguments(bundle);
                break;

            case FRAG_CONTENT2: // content 1
                bundle.putInt("content_num",FRAG_CONTENT1 - 3);
                frag = new FragmentContentSee();
                frag.setArguments(bundle);
                break;

            case FRAG_CONTENT3: // content 1
                bundle.putInt("content_num",FRAG_CONTENT1 - 2);
                frag = new FragmentContentSee();
                frag.setArguments(bundle);
                break;

            case FRAG_CONTENT4: // content 1
                bundle.putInt("content_num",FRAG_CONTENT1 - 1);
                frag = new FragmentContentSee();
                frag.setArguments(bundle);
                break;

            default:
                frag = new FragmentMain();
                break;
        }

        trans.replace(R.id.fragmentArea, frag);
        if (selection != FRAG_MAIN) // 메인화면일때는 stack에 넣지 않음
            trans.addToBackStack(null);

        trans.commit();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.quickMenu1:
                setFragmentArea(FRAG_CALENDAR);
                break;
            case R.id.quickMenu2:
                setFragmentArea(FRAG_CONTENT);
                break;
            default:
                setFragmentArea(FRAG_MAIN);
                break;
        }
        drawerLayout.closeDrawer(Gravity.START);
    }
}