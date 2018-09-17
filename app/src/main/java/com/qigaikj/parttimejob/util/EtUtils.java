package com.qigaikj.parttimejob.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Created by Administrator on 2017/12/6/006.
 * 输入框输入限制
 */

public class EtUtils {
    /**
     * edittext只能输入数值的时候做最大最小的限制
     */
    public static void setRegion(final EditText et, final double min, final double max) {
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (start >= 0) {//从一输入就开始判断，
                    if (min != -1 && max != -1) {

                        try {
                            if (s.toString().contains(".")) {
                                if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                                    s = s.toString().subSequence(0,
                                            s.toString().indexOf(".") + 3);
                                    et.setText(s);
//                                    et.setSelection(s.length());
                                }
                            }
                            double num = Double.parseDouble(s.toString());
                            LogUtils.i("输入---------------------------------》"+num);
                            //判断当前edittext中的数字(可能一开始Edittext中有数字)是否大于max
//                            if (num > max) {
//                                s = String.valueOf(max);//如果大于max，则内容为max
////                                setText(s);
//                                et.setText(s);
////                                Prompt.showTips(context, "金额不能超过" + max + "元");
//
//                            } else

                                if (num < min&&num!=0) {
                                s = String.valueOf(min);//如果小于min,则内容为min
                                et.setText(s);
                            }
                            et.requestFocus();
                            et.setSelection(et.length());
                        } catch (NumberFormatException e) {
//                            LFLog.e("ontextchanged", "==" + e.toString());
                        }
                        //edittext中的数字在max和min之间，则不做处理，正常显示即可。
                        return;
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (et.getText().toString().trim() != null && !et.getText().toString().trim().equals("")) {
                    if (et.getText().toString().trim().substring(0, 1).equals(".")) {
                        et.setText("0" + et.getText().toString().trim());
                        et.setSelection(1);
                    }
                }
            }
        });
    }
}
