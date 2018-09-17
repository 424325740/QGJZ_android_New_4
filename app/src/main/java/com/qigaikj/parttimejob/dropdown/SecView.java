package com.qigaikj.parttimejob.dropdown;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.qigaikj.parttimejob.R;

public class SecView {

    private Context context;
    private MyItemClickListener listener;

    public SecView(Context context) {
        this.context = context;
    }

    public void setListener(MyItemClickListener listener) {
        this.listener = listener;
    }

    public View secView() {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_sec, null);
        TextView tvBtnOne = (TextView) view.findViewById(R.id.btn_one);
        TextView tvBtnTwo = (TextView) view.findViewById(R.id.btn_two);
        TextView tvBtnThree = (TextView) view.findViewById(R.id.btn_three);
        tvBtnOne.setOnClickListener(new mClick("Android"));
        tvBtnTwo.setOnClickListener(new mClick("Java"));
        tvBtnThree.setOnClickListener(new mClick("HTML"));
        return view;
    }

    private class mClick implements View.OnClickListener {

        String string;

        private mClick(String string) {
            this.string = string;
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(v, 2, string);
        }
    }

}
