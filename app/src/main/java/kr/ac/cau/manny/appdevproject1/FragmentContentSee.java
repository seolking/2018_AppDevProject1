package kr.ac.cau.manny.appdevproject1;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentContentSee extends Fragment {
    MainActivity mainActivity = (MainActivity) getActivity();

    TextView contentSeeText;

    public FragmentContentSee() {
        super();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content_see, container, false);

        mainActivity = (MainActivity) getActivity();

        mainActivity.backButton.setVisibility(View.VISIBLE);

        mainActivity.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getFragmentManager();
                fragmentManager.beginTransaction().remove(FragmentContentSee.this).commit();
                fragmentManager.popBackStack();
            }
        });

        contentSeeText = (TextView) view.findViewById(R.id.contentSeeText);

        int input = getArguments().getInt("content_num",-1);

        switch (input){
            case 0:
                contentSeeText.setText(R.string.content1_a);
                break;
            case 1:
                contentSeeText.setText(R.string.content2_a);
                break;
            case 2:
                contentSeeText.setText(R.string.content3_a);
                break;
            case 3:
                contentSeeText.setText(R.string.content4_a);
                break;
            default:
                contentSeeText.setText(R.string.error);
                break;
        }

        return view;
    }

    @Override
    public void onDestroyView() {
        mainActivity = (MainActivity) getActivity();
        mainActivity.backButton.setVisibility(View.GONE);
        super.onDestroyView();
    }
}
