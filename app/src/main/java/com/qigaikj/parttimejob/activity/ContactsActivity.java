package com.qigaikj.parttimejob.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContactsActivity extends BaseActivity {

    @BindView(R.id.llContactsName)
    LinearLayout llContactsName;
    @BindView(R.id.tvContactsName)
    TextView tvContactsName;
    @BindView(R.id.llContactsPhone)
    LinearLayout llContactsPhone;
    @BindView(R.id.tvContactsPhone)
    TextView tvContactsPhone;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        ButterKnife.bind(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_contacts;
    }

    @Override
    protected void initDetail() {
        
    }

    @Override
    protected void initTitleView() {
        getTvTextTitle().setVisibility(View.VISIBLE);
        getTvTextTitle().setText("联系人");
        getIvLeftImage().setVisibility(View.VISIBLE);
        getIvLeftImage().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
