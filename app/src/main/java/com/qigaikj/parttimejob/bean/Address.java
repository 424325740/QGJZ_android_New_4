package com.qigaikj.parttimejob.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/11/10/010.
 * 保存地址
 */

public class Address implements Parcelable {
    //地址名称
    public String addressname;
    /*联系人ID*/
    public String address_id;
//    /*联系人首字母*/
    public String sortLetters;
//    /*联系人手机号*/
//    public String contacts_cell;
//    /*公司姓名*/
//    public String cust_name;
//    /*是否为选择联系人:1为已选择，2为未选择*/
//    public String select;
//
//    public String customer_id;//客户id
//    public String contacts_job;//联系人职务
//    public String contacts_department;//联系人所在部门
//    public String contacts_desc;//联系人备注
//    public String contacts_tel;//联系人座机
//    public String contacts_email;//联系人邮箱
//    public String contacts_address;//联系人详细地址
//    public String contacts_blog;//联系人微博
//    public String contacts_card;//联系人名片
//    public String contacts_sex;//联系人性别
//    public String contacts_birthday;//联系人生日
//    public String jx_creator;//联系人创建人
//    public String jx_create_time;//联系人创建时间
//    public String jx_update_time;//联系人最后修改时间
//    public String department_name;//联系人创建人所属部门
//    public String employee_name;//联系人所属人名称


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.addressname);
        dest.writeString(this.address_id);
        dest.writeString(this.sortLetters);
//        dest.writeString(this.contacts_cell);
//        dest.writeString(this.select);
//        dest.writeString(this.cust_name);
//        dest.writeString(this.contacts_id);
//        dest.writeString(this.customer_id);
//        dest.writeString(this.contacts_job);
//        dest.writeString(this.contacts_department);
//        dest.writeString(this.contacts_desc);
//        dest.writeString(this.contacts_tel);
//        dest.writeString(this.contacts_email);
//        dest.writeString(this.contacts_address);
//        dest.writeString(this.contacts_blog);
//        dest.writeString(this.contacts_card);
//        dest.writeString(this.contacts_sex);
//        dest.writeString(this.contacts_birthday);
//        dest.writeString(this.jx_creator);
//        dest.writeString(this.jx_create_time);
//        dest.writeString(this.jx_update_time);
//        dest.writeString(this.department_name);
//        dest.writeString(this.employee_name);
    }

    public Address() {
    }

    protected Address(Parcel in) {
        this.addressname = in.readString();
        this.address_id = in.readString();
        this.sortLetters = in.readString();
//        this.contacts_cell = in.readString();
//        this.select = in.readString();
//        this.cust_name = in.readString();
//        this.contacts_id = in.readString();
//        this.customer_id = in.readString();
//        this.contacts_job = in.readString();
//        this.contacts_department = in.readString();
//        this.contacts_desc = in.readString();
//        this.contacts_email = in.readString();
//        this.contacts_address = in.readString();
//        this.contacts_blog = in.readString();
//        this.contacts_card = in.readString();
//        this.contacts_sex = in.readString();
//        this.contacts_birthday = in.readString();
//        this.jx_creator = in.readString();
//        this.jx_create_time = in.readString();
//        this.jx_update_time = in.readString();
//        this.department_name = in.readString();
//        this.employee_name = in.readString();


    }

    public static final Parcelable.Creator<Address> CREATOR = new Parcelable.Creator<Address>() {
        @Override
        public Address createFromParcel(Parcel source) {
            return new Address(source);
        }

        @Override
        public Address[] newArray(int size) {
            return new Address[size];
        }
    };
}