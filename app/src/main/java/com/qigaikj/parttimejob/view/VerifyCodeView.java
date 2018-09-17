package com.qigaikj.parttimejob.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qigaikj.parttimejob.R;

/**
 *
 * 自定义输入验证码框
 *
 * */

public class VerifyCodeView extends RelativeLayout {

    private EditText editText;
    private TextView[] textViews;
    private TextView[] viewLine;
    private static int MAX = 6;
    private String inputContent;
    public VerifyCodeView(Context context) {
        this(context, null);
    }

    public VerifyCodeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerifyCodeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View.inflate(context, R.layout.view_verify_code, this);
        textViews = new TextView[MAX];
        viewLine = new TextView[MAX];
        textViews[0] = (TextView) findViewById(R.id.tv_0);
        viewLine[0] = (TextView) findViewById(R.id.viewLine_0);
        textViews[1] = (TextView) findViewById(R.id.tv_1);
        viewLine[1] = (TextView) findViewById(R.id.viewLine_1);
        textViews[2] = (TextView) findViewById(R.id.tv_2);
        viewLine[2] = (TextView) findViewById(R.id.viewLine_2);
        textViews[3] = (TextView) findViewById(R.id.tv_3);
        viewLine[3] = (TextView) findViewById(R.id.viewLine_3);
        textViews[4] = (TextView) findViewById(R.id.tv_4);
        viewLine[4] = (TextView) findViewById(R.id.viewLine_4);
        textViews[5] = (TextView) findViewById(R.id.tv_5);
        viewLine[5] = (TextView) findViewById(R.id.viewLine_5);
        editText = (EditText) findViewById(R.id.edit_text_view);
        editText.setCursorVisible(false);//隐藏光标
        setEditTextListener();
    }

    private void setEditTextListener() {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                inputContent = editText.getText().toString();
                if (inputCompleteListener != null) {
                    if (inputContent.length() >= MAX) {
                        inputCompleteListener.inputComplete();
                    } else {
                        inputCompleteListener.invalidContent();
                    }
                }
                for (int i = 0; i < MAX; i++) {
                    if (i < inputContent.length()) {
                        textViews[i].setText(String.valueOf(inputContent.charAt(i)));
                        viewLine[i].setVisibility(View.GONE);
                    } else {
                        textViews[i].setText("");
                        viewLine[i].setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }
    private InputCompleteListener inputCompleteListener;
    public void setInputCompleteListener(InputCompleteListener inputCompleteListener) {
        this.inputCompleteListener = inputCompleteListener;
    }
    public interface InputCompleteListener {
        void inputComplete();
        void invalidContent();
    }
    public String getEditContent() {
        return inputContent;
    }
}
