package com.qigaikj.parttimejob.handler;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

/**
 * Created by Shsy on 2016/3/31.
 * 省市区Xml SAX解析类
 */
public class AddressXmlParserHandler extends DefaultHandler {
    /**
     * 省市区名称
     */
    private ArrayList<String> provinceItemsName = new ArrayList<>();
    private ArrayList<ArrayList<String>> cityItemsName = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> distinctItemsName = new ArrayList<>();
    /**
     * 省市区ID
     */
    private ArrayList<String> provinceItemsId = new ArrayList<>();
    private ArrayList<ArrayList<String>> cityItemsId = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> distinctItemsId = new ArrayList<>();
    /**
     * 省市区名称中的子项目
     */
    private ArrayList<String> cityItemsName_01;
    private ArrayList<String> distinctItemsName_01_01;
    private ArrayList<ArrayList<String>> distinctItemsName_01;
    /**
     * 省市区ID中的子项目
     */
    private ArrayList<String> cityItemsId_01;
    private ArrayList<String> distinctItemsId_01_01;
    private ArrayList<ArrayList<String>> distinctItemsId_01;

    public ArrayList<String> getProvinceItemsName() {
        return provinceItemsName;
    }

    public ArrayList<ArrayList<String>> getCityItemsName() {
        return cityItemsName;
    }

    public ArrayList<ArrayList<ArrayList<String>>> getDistinctItemsName() {
        return distinctItemsName;
    }

    public ArrayList<String> getProvinceItemsId() {
        return provinceItemsId;
    }

    public ArrayList<ArrayList<String>> getCityItemsId() {
        return cityItemsId;
    }

    public ArrayList<ArrayList<ArrayList<String>>> getDistinctItemsId() {
        return distinctItemsId;
    }

    /**
     * 当遇到开始标记的时候，调用这个方法
     *
     * @param uri
     * @param localName
     * @param qName
     * @param attributes
     * @throws SAXException
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equals("province")) {
            // 关于名字
            provinceItemsName.add(attributes.getValue(0));
            cityItemsName_01 = new ArrayList<>();
            distinctItemsName_01 = new ArrayList<>();
            // 关于ID
            provinceItemsId.add(attributes.getValue(1));
            cityItemsId_01 = new ArrayList<>();
            distinctItemsId_01 = new ArrayList<>();
        } else if (qName.equals("city")) {
            // 关于名字
            cityItemsName_01.add(attributes.getValue(0));
            distinctItemsName_01_01 = new ArrayList<>();
            // 关于ID
            cityItemsId_01.add(attributes.getValue(1));
            distinctItemsId_01_01 = new ArrayList<>();
        } else if (qName.equals("district")) {
            // 关于名字
            distinctItemsName_01_01.add(attributes.getValue(0));
            // 关于ID
            distinctItemsId_01_01.add(attributes.getValue(1));
        }
    }

    /**
     * 结束标记时执行的方法
     *
     * @param uri
     * @param localName
     * @param qName
     * @throws SAXException
     */
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equals("province")) {
            // 关于名字
            cityItemsName.add(cityItemsName_01);
            cityItemsName_01 = null;
            distinctItemsName.add(distinctItemsName_01);
            distinctItemsName_01 = null;
            // 关于Id
            cityItemsId.add(cityItemsId_01);
            cityItemsName_01 = null;
            distinctItemsId.add(distinctItemsId_01);
            distinctItemsId_01 = null;
        } else if (qName.equals("city")) {
            // 关于名字
            distinctItemsName_01.add(distinctItemsName_01_01);
            distinctItemsName_01_01 = null;
            // 关于Id
            distinctItemsId_01.add(distinctItemsId_01_01);
            distinctItemsId_01_01 = null;
        }
    }
}
