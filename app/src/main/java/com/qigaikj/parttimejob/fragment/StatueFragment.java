package com.qigaikj.parttimejob.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qigaikj.parttimejob.R;

/**
 * @author KBTS
 * @date 2018/5/25
 * @description
 */
public class StatueFragment extends Fragment {
    
    public static final String KEY_TITLE = "TITLE";
    public static final int ENY_IMG=0;
    
    private TextView mtxtTitle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_for_statue, container, false);
        mtxtTitle = root.findViewById(R.id.id_title);
        Bundle bundle;
        if ((bundle = getArguments()) != null) {
            if (bundle.containsKey(KEY_TITLE)) {
                mtxtTitle.setText(bundle.getString(KEY_TITLE));
            }
        }
        return root;
    }
    
    public static StatueFragment getInstance(Bundle bundle) {
        StatueFragment fragment = new StatueFragment();
        if (bundle != null) {
            fragment.setArguments(bundle);
        }
        return fragment;
    }
}