package kr.ac.cau.manny.appdevproject1;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class FragmentMain extends Fragment implements View.OnClickListener{
    View view;
    MainActivity mainActivity;

    // 버튼 뷰
    ImageButton button1;
    ImageButton button2;
    ImageButton button3;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        // 프래그먼트 뷰 할당
        view = inflater.inflate(R.layout.fragment_main, container, false);

        // 메인 액티비티 지정
        mainActivity = (MainActivity)getActivity();

        // 버튼 뷰 바인딩
        button1 = (ImageButton)view.findViewById(R.id.button1);
        button2 = (ImageButton)view.findViewById(R.id.button2);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button1: // 달력화면
                mainActivity.setFragmentArea(MainActivity.FRAG_CALENDAR);
                break;

            case R.id.button2:
                mainActivity.setFragmentArea(MainActivity.FRAG_CONTENT);
                break;
        }
    }
}
