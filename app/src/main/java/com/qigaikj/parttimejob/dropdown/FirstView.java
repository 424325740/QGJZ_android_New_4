package com.qigaikj.parttimejob.dropdown;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.qigaikj.parttimejob.R;

public class FirstView {

    private Context context;
    private MyItemClickListener listener;

    public FirstView(Context context) {
        this.context = context;
    }

    public void setListener(MyItemClickListener listener) {
        this.listener = listener;
    }

    public View firstView() {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_first, null);
        TextView tvOne = (TextView) view.findViewById(R.id.tvOne);
        TextView tvTwo = (TextView) view.findViewById(R.id.tvTwo);
        TextView tvThree = (TextView) view.findViewById(R.id.tvThree);
        TextView tvFour = (TextView) view.findViewById(R.id.tvFour);
        tvOne.setOnClickListener(new mClick("最新发布"));
        tvTwo.setOnClickListener(new mClick("价格最高"));
        tvThree.setOnClickListener(new mClick("价格最低"));
        tvFour.setOnClickListener(new mClick("距离最近"));
        return view;
    }

    private class mClick implements View.OnClickListener {

        String string;
        private mClick(String string) {
            this.string = string;
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(v, 1, string);
        }
    }

}
