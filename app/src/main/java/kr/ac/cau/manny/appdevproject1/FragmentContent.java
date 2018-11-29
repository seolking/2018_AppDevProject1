package kr.ac.cau.manny.appdevproject1;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class FragmentContent extends Fragment {
    MainActivity mainActivity;

    // 리스트 뷰 및 어레이 리스트 선언
    ListView listView;
    ArrayList<String> arrayList;


    public FragmentContent (){
        super();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, container, false);

        // 리스트 뷰 바인딩
        listView = (ListView) view.findViewById(R.id.listView);

        // 어레이 리스트 설정
        arrayList = new ArrayList<>();

        arrayList.add(getStringFromResource(R.string.content1));
        arrayList.add(getStringFromResource(R.string.content2));
        arrayList.add(getStringFromResource(R.string.content3));
        arrayList.add(getStringFromResource(R.string.content4));

        mainActivity = (MainActivity)getActivity();

        ListAdapter listAdapter = new ListAdapter (getActivity(), R.layout.item, arrayList);
        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mainActivity.setFragmentArea(mainActivity.FRAG_CONTENT1+i);
            }
        });

        return view;
    }

    public String getStringFromResource(int source){
        return getResources().getString(source);
    }

}

class ListAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<String> arrayList;
    private int layout;


    public ListAdapter (Activity activity, int layout, ArrayList<String> data){
        this.inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        this.arrayList = data;
        this.layout = layout;

    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView=inflater.inflate(layout, parent,false);
        }
        String data = arrayList.get(i);
        System.out.println(data);

        TextView textView = (TextView) convertView.findViewById(R.id.itemTextView);
        textView.setText(data);

        return convertView;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public String getItem(int i) {
        return arrayList.get(i);
    }
}