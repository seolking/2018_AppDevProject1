package kr.ac.cau.manny.appdevproject1;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class FragmentCalendar extends Fragment implements OnDayClickListener {
    View view;

    Activity activity;
    CalendarView calendarView;
    List<EventDay> eventDays = new ArrayList<>();

    SharedPreferences eventDaysPref;
    SharedPreferences.Editor editor;



    public FragmentCalendar(){
        super();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_calendar, container, false);

        calendarView = (CalendarView)view.findViewById(R.id.calendarView);
        calendarView.showCurrentMonthPage();
        calendarView.setOnDayClickListener(this);

        activity = getActivity();
        eventDaysPref = activity.getSharedPreferences("EventDaysPref", Context.MODE_PRIVATE);



        //eventDaysPref.edit().clear().apply();

        eventDays = loadEventDays();
        calendarView.setEvents(eventDays);


        return view;
    }

    public void saveEventDay(MyEventDay newEventDay){
        editor = eventDaysPref.edit();
        editor.putString(String.valueOf(newEventDay.getCalendar().getTime().getTime()), newEventDay.getNote());
        editor.apply();
    }

    public void removeEventDay(String noUseEventDay){
        editor = eventDaysPref.edit();
        editor.remove(noUseEventDay);
        editor.apply();
    }

    @Override
    public void onDayClick(EventDay eventDay) {
        final EventDay clickedDay = eventDay;

        final String check = eventDaysPref.getString(String.valueOf(eventDay.getCalendar().getTime().getTime()), "none");
        if (check.equals("none")){ // 클릭한 날짜에 이벤트가 존재하지 않을 때
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            String Month = String.valueOf(eventDay.getCalendar().getTime().getMonth()+1);
            String Date = String.valueOf(eventDay.getCalendar().getTime().getDate());
            builder.setTitle( Month + "월 " + Date + "일에는 메모가 없습니다. 추가하시겠습니까?");


            builder.setPositiveButton("네", new DialogInterface.OnClickListener() { // 메모를 추가할때
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    AlertDialog.Builder secondBuilder = new AlertDialog.Builder(getActivity());
                    secondBuilder.setTitle("메모를 입력해주세요");
                    final EditText editText = new EditText(getActivity());
                    secondBuilder.setView(editText);

                    secondBuilder.setPositiveButton("저장", new DialogInterface.OnClickListener() { // 메모추가를 저장할 때
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String newNote = editText.getText().toString();

                            if (newNote.equals("")){ //빈 입력이 아닐 때만 저장
                                new AlertDialog.Builder(getActivity()).setTitle("입력이 없어 메모가 저장되지 않았습니다").show();


                            }
                            else{
                                MyEventDay newEventDay = new MyEventDay(clickedDay.getCalendar(), R.drawable.ic_note_32dp, newNote);
                                eventDays.add(newEventDay);
                                calendarView.setEvents(eventDays);

                                saveEventDay(newEventDay);
                            }

                        }
                    });

                    secondBuilder.setNegativeButton("취소", new DialogInterface.OnClickListener() { // 메모추가를 취소할 때
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // 동작할 것이 없으므로 빈 줄
                        }
                    });

                    secondBuilder.show();
                }
            });

            builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    // 동작할 것이 없으므로 빈 줄
                }
            });

            builder.show();
        }
        else {
            // 이벤트가 존재할때
            AlertDialog.Builder otherBuilder = new AlertDialog.Builder(getActivity());
            otherBuilder.setTitle("메모를 편집하려면 편집버튼을 누르세요");

            TextView textView = new TextView(getActivity());
            textView.setText(check);
            textView.setTextSize(20);
            textView.setTextColor(Color.GRAY);
            otherBuilder.setView(textView);


            otherBuilder.setPositiveButton("편집", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    AlertDialog.Builder editBuilder = new AlertDialog.Builder(getActivity());
                    editBuilder.setTitle("메모를 전부 지우고 수정을 누르면 메모가 삭제됩니다");

                    final EditText editText2 = new EditText(getActivity());
                    editText2.setText(check);
                    editBuilder.setView(editText2);

                    editBuilder.setPositiveButton("수정", new DialogInterface.OnClickListener() { // 메모추가를 저장할 때
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String newNote = editText2.getText().toString();

                            if (newNote.equals("")){ // 빈 입력이면 메모를 삭제
                                removeEventDay(String.valueOf(clickedDay.getCalendar().getTime().getTime()));
                                new AlertDialog.Builder(getActivity()).setTitle("입력이 없어 메모가 삭제되었습니다.").show();
                                eventDays = loadEventDays();
                                calendarView.setEvents(eventDays);

                            }
                            else{
                                MyEventDay newEventDay = new MyEventDay(clickedDay.getCalendar(), R.drawable.ic_note_32dp, newNote);
                                eventDays.add(newEventDay);
                                calendarView.setEvents(eventDays);

                                saveEventDay(newEventDay);
                            }

                        }
                    });

                    editBuilder.setNegativeButton("취소", new DialogInterface.OnClickListener() { // 메모추가를 취소할 때
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // 동작할 것이 없으므로 빈 줄
                        }
                    });

                    editBuilder.show();
                }
            });

            otherBuilder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    // 동작할 것이 없으므로 빈줄
                }
            });

            otherBuilder.show();

        }

    }

    public List<EventDay> loadEventDays(){
        List<EventDay> retEventDays = new ArrayList<>();

        HashMap<String, String> map = (HashMap <String, String>) eventDaysPref.getAll();

        for (String s : map.keySet()){
            long date = Long.valueOf(s);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date(date));

            retEventDays.add(new MyEventDay(calendar, R.drawable.ic_note_32dp, map.get(s)));
        }


        return retEventDays;
    }
}
