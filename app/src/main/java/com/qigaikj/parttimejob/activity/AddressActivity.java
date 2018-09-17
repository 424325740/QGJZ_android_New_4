package com.qigaikj.parttimejob.activity;

import android.content.res.AssetManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.adapter.AddressListAdapter;
import com.qigaikj.parttimejob.base.BaseActivity;
import com.qigaikj.parttimejob.bean.Address;
import com.qigaikj.parttimejob.handler.AddressXmlParserHandler;
import com.qigaikj.parttimejob.util.SharedPreferencesHelper;
import com.qigaikj.parttimejob.view.PtClearEditText;
import com.qigaikj.parttimejob.view.PtSideBar;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import io.rong.imkit.tools.CharacterParser;

/**
 * Created by Administrator on 2017/11/10/010.
 * 选择地址页面
 */

public class AddressActivity extends BaseActivity implements View.OnClickListener {
    private PtClearEditText ptClearEditText;
    private ListView lv_ddress__list;
    private TextView tv_activity_ddress__dialog;
    private PtSideBar sb_activity_addressbook;
    private AddressXmlParserHandler handler;
    private CharacterParser characterParser;
    private TextView tv_citydq;
    private TextView tv_city_1;
    private TextView tv_city_2;
    private TextView tv_city_3;
    private TextView tv_city_4;
    private TextView tv_city_5;
    private TextView tv_city_6;
    private TextView tv_city_7;
    private TextView tv_city_8;
    private TextView tv_city_9;
    /**
     * 排序后的数据
     */
    private List<Address> newlists;
    /**
     * 适配器
     */
    private AddressListAdapter addressListAdapter;
    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private AddPinyinComparator pinyinComparator;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_address;
    }

    @Override
    protected void initDetail() {
        ptClearEditText = (PtClearEditText) findViewById(R.id.et_address_clear);
        lv_ddress__list = (ListView) findViewById(R.id.lv_ddress__list);
        tv_activity_ddress__dialog = (TextView) findViewById(R.id.tv_activity_ddress__dialog);
        sb_activity_addressbook = (PtSideBar) findViewById(R.id.sb_activity_addressbook);
        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.address_title_layout, null);
        tv_citydq = (TextView) view.findViewById(R.id.tv_city_dq);
        tv_citydq.setOnClickListener(this);
        tv_city_1 = (TextView) view.findViewById(R.id.tv_city_1);
        tv_city_1.setOnClickListener(this);
        tv_city_2 = (TextView) view.findViewById(R.id.tv_city_2);
        tv_city_3 = (TextView) view.findViewById(R.id.tv_city_3);
        tv_city_4 = (TextView) view.findViewById(R.id.tv_city_4);
        tv_city_5 = (TextView) view.findViewById(R.id.tv_city_5);
        tv_city_6 = (TextView) view.findViewById(R.id.tv_city_6);
        tv_city_7 = (TextView) view.findViewById(R.id.tv_city_7);
        tv_city_8 = (TextView) view.findViewById(R.id.tv_city_8);
        tv_city_9 = (TextView) view.findViewById(R.id.tv_city_9);
        tv_city_2.setOnClickListener(this);
        tv_city_3.setOnClickListener(this);
        tv_city_4.setOnClickListener(this);
        tv_city_5.setOnClickListener(this);
        tv_city_6.setOnClickListener(this);
        tv_city_7.setOnClickListener(this);
        tv_city_8.setOnClickListener(this);
        tv_city_9.setOnClickListener(this);
        lv_ddress__list.addHeaderView(view);
        initView();
        lv_ddress__list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    sharedPreferencesHelper.putString(SharedPreferencesHelper.CITY, String.valueOf(addressListAdapter.getAddressList().get(position - 1).addressname));
                    finish();
                }
            }
        });

    }

    @Override
    protected void initTitleView() {
        getIvLeftImage().setVisibility(View.VISIBLE);
        getIvLeftImage().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getTvTextTitle().setVisibility(View.VISIBLE);
        getTvTextTitle().setText("选择城市");

    }

    public void initView() {
        // 解析
        AssetManager asset = getAssets();
        try {
            InputStream input = asset.open("province_data.xml");
            // 创建一个解析xml的工厂对象
            SAXParserFactory spf = SAXParserFactory.newInstance();
            // 解析xml
            SAXParser parser = spf.newSAXParser();
            handler = new AddressXmlParserHandler();
            parser.parse(input, handler);
            input.close();

        } catch (Throwable e) {
            e.printStackTrace();
        }
//        for (int i = 0; i < handler.getDistinctItemsName().size(); i++) {
//            for (int j = 0; j < handler.getCityItemsName().size(); j++) {
//                Address address = new Address();
//                address.addressname = handler.getCityItemsName().get(i).get(j);
//                oldlist.add(address);
//            }
//        }

        characterParser = CharacterParser.getInstance();
        pinyinComparator = new AddPinyinComparator();

        sb_activity_addressbook.setTextView(tv_activity_ddress__dialog);
        //设置右侧触摸监听
        sb_activity_addressbook.setOnTouchingLetterChangedListener(new PtSideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                int position = addressListAdapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    lv_ddress__list.setSelection(position);
                }
            }
        });
        //获取姓名首字母
        newlists = filledData1();
        // 根据a-z进行排序源数据
        Collections.sort(newlists, pinyinComparator);
        ptClearEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
                filterData(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        /**
         * 创建适配器
         */
        addressListAdapter = new AddressListAdapter(newlists);
        lv_ddress__list.setAdapter(addressListAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        String ss = sharedPreferencesHelper.getString(SharedPreferencesHelper.LOCATION_CITY, "");
        if (ss == null && ss.equals("")) {
            tv_citydq.setVisibility(View.INVISIBLE);
        } else {
            tv_citydq.setText(ss);
        }

    }

    /**
     * 对数据进行重新排列，并获取首字母
     *
     * @return
     */
    private List<Address> filledData1() {
        List<Address> mSortList = new ArrayList<Address>();

        for (int i = 0; i < handler.getProvinceItemsName().size(); i++) {
            for (int j = 0; j < handler.getCityItemsName().get(i).size(); j++) {
                Address sortModel = new Address();
                sortModel.addressname = handler.getCityItemsName().get(i).get(j);
                //汉字转换成拼音
                String pinyin = characterParser.getSelling(handler.getCityItemsName().get(i).get(j));
                String sortString = pinyin.substring(0, 1).toUpperCase();
                // 正则表达式，判断首字母是否是英文字母
                if (sortString.matches("[A-Z]")) {
                    sortModel.sortLetters = (sortString.toUpperCase());
                } else {
                    sortModel.sortLetters = ("#");
                }
                mSortList.add(sortModel);
            }
        }
        return mSortList;
    }

    /**
     * 根据输入框中的值来过滤数据并更新ListView
     *
     * @param filterStr
     */
    private void filterData(String filterStr) {
        List<Address> filterDateList = new ArrayList<Address>();

        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = newlists;
        } else {
            filterDateList.clear();
            for (Address sortModel : newlists) {
                String name = sortModel.addressname;

                if (name.indexOf(filterStr.toString()) != -1 || characterParser.getSelling(name).startsWith(filterStr.toString())) {
                    filterDateList.add(sortModel);
                }
            }
        }

        // 根据a-z进行排序
        Collections.sort(filterDateList, pinyinComparator);
        addressListAdapter.setAddressList(filterDateList);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_city_dq:
                String city = sharedPreferencesHelper.getString(SharedPreferencesHelper.LOCATION_CITY, "");
                sharedPreferencesHelper.putString(SharedPreferencesHelper.CITY, city);
                finish();
                break;
            case R.id.tv_city_1:
                sharedPreferencesHelper.putString(SharedPreferencesHelper.CITY, "北京市");
                finish();
                break;
            case R.id.tv_city_2:
                sharedPreferencesHelper.putString(SharedPreferencesHelper.CITY, "上海市");
                finish();
                break;
            case R.id.tv_city_3:
                sharedPreferencesHelper.putString(SharedPreferencesHelper.CITY, "广州市");
                finish();
                break;
            case R.id.tv_city_4:
                sharedPreferencesHelper.putString(SharedPreferencesHelper.CITY, "深圳市");
                finish();
                break;
            case R.id.tv_city_5:
                sharedPreferencesHelper.putString(SharedPreferencesHelper.CITY, "武汉市");
                finish();
                break;
            case R.id.tv_city_6:
                sharedPreferencesHelper.putString(SharedPreferencesHelper.CITY, "天津市");
                finish();
                break;
            case R.id.tv_city_7:
                sharedPreferencesHelper.putString(SharedPreferencesHelper.CITY, "西安市");
                finish();
                break;
            case R.id.tv_city_8:
                sharedPreferencesHelper.putString(SharedPreferencesHelper.CITY, "南京市");
                finish();
                break;
            case R.id.tv_city_9:
                sharedPreferencesHelper.putString(SharedPreferencesHelper.CITY, "杭州市");
                finish();
                break;

        }
    }

    /**
     * @author xiaanming
     *         搜索功能实现，对汉字进行排序的实现类
     */
    public static class AddPinyinComparator implements Comparator<Address> {

        //这里主要是用来对ListView里面的数据根据ABCDEFG...来排序
        public int compare(Address o1, Address o2) {
            if (o1.sortLetters.equals("@")
                    || o2.sortLetters.equals("#")) {
                return -1;
            } else if (o1.sortLetters.equals("#")
                    || o2.sortLetters.equals("@")) {
                return 1;
            } else {
                return o1.sortLetters.compareTo(o2.sortLetters);
            }
        }

    }
}
