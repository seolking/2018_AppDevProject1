package kr.ac.cau.manny.appdevproject1;

import com.applandeo.materialcalendarview.EventDay;

import java.util.Calendar;

public class MyEventDay extends EventDay{
    private String mNote;


    public MyEventDay(Calendar day, int imageResource) {
        super(day, imageResource);
    }

    public MyEventDay(Calendar day, int imageResource, String note) {
        super(day, imageResource);
        mNote = note;
    }

    public String getNote(){
        return mNote;
    }

}
