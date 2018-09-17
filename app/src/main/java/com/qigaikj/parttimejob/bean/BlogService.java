package com.qigaikj.parttimejob.bean;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by Administrator on 2017/11/4/004.
 * 所以请求接口
 */

public interface BlogService {
    /**
     * 获取好友详情
     */
    @FormUrlEncoded
    @POST("Rong/getUsers")
    Call<WrapperRspEntity<FriendDetails>> getFriendsDetails(@Field("userId") String userId, @Field("friendId") String friendId);

    //获取验证码
    @FormUrlEncoded
    @POST("Login/sendcode")
    Call<WrapperRspEntity<Code>> getCode(@Field("phone") String phone);

    //注册账号
    @FormUrlEncoded
    @POST("Login/register")
    Call<WrapperRspEntity<Code>> getRegest(@Field("phone") String phone, @Field("password") String password, @Field("code") String code);

    //登陆
    @FormUrlEncoded
    @POST("Login/login")
    Call<WrapperRspEntity<Login>> getLogin(@Field("phone") String phone, @Field("password") String password);

    /**
     * 第三方登陆
     *
     * @param uid
     * @param name
     * @param gender
     * @param iconurl
     * @param type
     * @return
     */
    @FormUrlEncoded
    @POST("Login/thirdlogin")
    Call<WrapperRspEntity<ThirLogin>> getThirLogin(@Field("uid") String uid, @Field("name") String name, @Field("gender") String gender, @Field("iconurl") String iconurl, @Field("type") String type);

    /**
     * 微信登陆获取tocken
     *
     * @param
     * @return
     */
    @FormUrlEncoded
    @POST("sns/oauth2/access_token")
    Call<WXUser> getWxaccess_token(@Field("appid") String appid, @Field("secret") String secret, @Field("code") String code, @Field("grant_type") String grant_type);

    @FormUrlEncoded
    @POST("sns/userinfo")
    Call<WXUser> getWxaccess_UserInfo(@Field("access_token") String access_token, @Field("openid") String openid);

    /**
     * 获取微信支付所需信息
     */
    @FormUrlEncoded
    @POST("Pay/wechatPay")
    Call<WrapperRspEntity<WeiXing>> getPaywechatPay(@FieldMap Map<String, Object> objectMap);

    /**
     * 三方登陆绑定手机号
     *
     * @param uid
     * @param type
     * @param phone
     * @param verificationCode
     * @return
     */
    @FormUrlEncoded
    @POST("Login/bindphone")
    Call<WrapperRspEntity<ThirLogin>> getLoginBindPhone(@Field("uid") String uid, @Field("type") String type, @Field("phone") String phone, @Field("verificationCode") String verificationCode);

    //忘记密码
    @FormUrlEncoded
    @POST("Login/forget_password")
    Call<WrapperRspEntity<Login>> forgetPassWord(@Field("phone") String phone, @Field("NewPassword") String NewPassword, @Field("code") String code);

    //获取个人信息
    @FormUrlEncoded
    @POST("Users/users_detail")
    Call<WrapperRspEntity<UserInfo>> getUserInfo(@Field("token") String token);

    /**
     * 首页搜索
     * @param
     * @return
     */
    @FormUrlEncoded
    @POST("Homepage/search_work")
    Call<WrapperRspEntity<List<PartTime>>> getSearch(@Field("name") String name, @Field("num") String num, @Field("page") String page);

    /**
     * 获取返回的支付结果
     */
    @FormUrlEncoded
    @POST("Pay/wxpay_notify")
    Call<WrapperRspEntity<Result>> getPayNotify(@Field("token") String token, @Field("order") String order);

    /**
     * 获取钱包收支明细
     */
    @FormUrlEncoded
    @POST("User/money_detail")
    Call<WrapperRspEntity<WalletDetails>> getMoneyDetails(@Field("mid") String mid);

    /**
     * 获取资金明细
     */
    @FormUrlEncoded
    @POST("User/money_list")
    Call<WrapperRspEntity<Money>> getMoneyList(@Field("token") String token);

    /**
     * 获取提现页面数据
     */
    @FormUrlEncoded
    @POST("Pay/cash_detail")
    Call<WrapperRspEntity<Deposit>> getDeposit(@Field("token") String token);

    /**
     * 提现接口
     */
    @FormUrlEncoded
    @POST("Pay/users_cash")
    Call<WrapperRspEntity<Deposit>> getUserCash(@Field("token") String token, @Field("money") String money, @Field("type") String type, @Field("code") String code);

    /**
     * 绑定支付宝账号
     */
    @FormUrlEncoded
    @POST("Pay/bind_alipay")
    Call<WrapperRspEntity<Deposit>> getBinDing(@Field("token") String token, @Field("accounts") String accounts, @Field("realname") String realname);

    /**
     * 获取分享
     */
    @FormUrlEncoded
    @POST("News/news_share")
    Call<WrapperRspEntity<Share>> getnews_share(@Field("id") String id);

    /**
     * 获取未读消息数量
     *
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST("News/unread_count")
    Call<WrapperRspEntity<NewMessage>> getNewMessage(@Field("token") String token);

    /**
     * 获取未读消息列表
     *
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST("News/unread_reply")
    Call<WrapperRspEntity<List<NewMessage>>> getUnread_reply(@Field("token") String token);

    /**
     * 检查版本更新
     */
    @POST("Version/new_version")
    Call<WrapperRspEntity<New_Version>> getNew_version();

    /**
     * 意见反馈
     *
     * @return
     */
    @Multipart
    @POST("User/submit_suggest")
    Call<WrapperRspEntity<MyissueDetail>> getSuggest(@Part List<MultipartBody.Part> partList);

    /**------------------------- -- 新版接口 -- --------------------------------*/

    /**
     * 上传头像
     */
    @Multipart
    @POST("User/change_avatar")
    Call<WrapperRspEntity<UserImage>> uploadUserImage(@Part List<MultipartBody.Part> partList);

    /**
     * 首页轮播图
     */
    @POST("Homepage/banner_list")
    Call<WrapperRspEntity<List<HomeBannerList>>> getHomeBannerList();

    /**
     * 首页分类展示
     */
    @POST("Homepage/homepage_type")
    Call<WrapperRspEntity<List<HomeGrideViewBean>>> getHomePageType();

    /**
     * 首页福利展示
     * state（1福利 2活动）
     */
    @FormUrlEncoded
    @POST("Homepage/welfare")
    Call<WrapperRspEntity<List<HomeGridViewWelfare>>> getHomeWelfare(@Field("state") String state);

    /**
     * 优选约TA
     *
     page（页数）
     num（条数）
     *
     */
//    @POST("Yue/yueta_list")
    @FormUrlEncoded
    @POST("Homepage/yx_yueta")
    Call<WrapperRspEntity<List<HomeYueTa>>>getHomeYueTa(@Field("page") String page, @Field("num") String num);

    /**
     * 2.4优选TA约我
     * @return
     */
    @FormUrlEncoded
    @POST("Homepage/yx_demand")
    Call<WrapperRspEntity<List<HomeTaYue>>>getHomeTaYue(@Field("page") String page, @Field("num") String num);
    /**
     * 附近人
     */
    @FormUrlEncoded
    @POST("Homepage/nearby_user")
    Call<NearUserList> getNearUserList(@Field("token") String token, @Field("lng") String lng, @Field("lat") String lat, @Field("tid") String tid, @Field("title") String title);

    /**
     * 获取附近分类
     */
    @POST("Homepage/nearby_type")
    Call<WrapperRspEntity<List<NearListBean>>> getNearList();

    /**
     * 分类列表
     */
    @POST("Homepage/type_list")
    Call<WrapperRspEntity<List<ClassifyForAll>>> getClassifyForAll();
    /**
     * 分类列表轮播图
     */
    @POST("Homepage/type_banner")
    Call<WrapperRspEntity<List<ClassifyViewPagerBean>>> getClassifyForAll_Banner();
    /**
     * 分类详情
     *
     * token
     * tid（二级分类id）
     * recommend（1最新发布 2价格最低 3价格最高 4距离最近）
     * serve（服务类型 1线上服务 2线下服务）
     * gender（性别 1男 2女）
     * age（年龄 1.20以下 2.20-30 3.30-35 4.35以上）
     * lng（经度）
     * lat（纬度）
     * page（页数）
     * num（条数）
     */
    @FormUrlEncoded
    @POST("Homepage/type_detail")
    Call<WrapperRspEntity<List<ClassifyViewPagerBean>>> getClassifyForAll_Info(@Field("token") String token, @Field("tid") String tid, @Field("recommend") String recommend,
                                                                               @Field("serve") String serve, @Field("gender") String gender, @Field("age") String age,
                                                                               @Field("lng") String lng, @Field("lat") String lat, @Field("page") String page, @Field("num") String num);

    /**
     * 发布需求
     *
     token（用户验证）
     type（技能类型<一级分类名称>）
     name（技能名称<二级分类id>）
     require（技能要求）
     gender（性别要求 1男 2女 3无）
     serve（服务类型 1线上服务 2线下服务）
     lng（经度）
     lat（纬度）
     */
    @FormUrlEncoded
    @POST("Yue/demand")
    Call<WrapperRspEntity> postDemand(@Field("token") String token, @Field("type") String type, @Field("name") String name, @Field("require") String require, @Field("serve") String serve,
                                                       @Field("gender") String gender, @Field("lng") String lng, @Field("lat") String lat);

    /**
     * 发布动态
     *
     token（用户验证）
     content（动态内容）
     图片文件
     */
    @Multipart
    @POST("Users/dynamic")
    Call<WrapperRspEntity<MyissueDetail>> postDynamic(@Part List<MultipartBody  .Part> partList);

    /**
     * 发布技能
     *
     token（用户验证）
     type（技能类型<一级分类名称>）
     name（技能名称<二级分类id>）
     introduce（技能介绍）
     serve（服务类型 1线上服务 2线下服务）
     price（价钱）
     unit（单位 1天 2小时）
     sincerity_gold（诚意金）
     lng（经度）
     lat（纬度）
     图片文件
     */
    @Multipart
    @POST("Users/skill")
    Call<WrapperRspEntity<MyissueDetail>> postSkill(@Part List<MultipartBody.Part> partList);

    /**
     * 我的技能
     *
     token（用户验证）
     page（页数）
     num（条数）
     */
    @FormUrlEncoded
    @POST("Users/skill_list")
    Call<WrapperRspEntity<List<SkillData>>> getSkillList(@Field("token") String token, @Field("page") String page, @Field("num") String num);
    /**
     * 我的需求
     *
     token（用户验证）
     page（页数）
     num（条数）
     */
    @FormUrlEncoded
    @POST("Users/demand_list")
    Call<MyXuqiuBean> getXuqiu(@Field("token") String token, @Field("page") String page, @Field("num") String num);

    /**
     * 关闭技能/开启技能
     *
     token（用户验证）
     kid（技能id）
     */
    @FormUrlEncoded
    @POST("Users/close_skill")
    Call<WrapperRspEntity<SkillCloseState>> postCloseSkill(@Field("token") String token, @Field("kid") String kid);

    /**
     * 关闭需求/开启需求
     *
     token（用户验证）
     kid（技能id）
     */
    @FormUrlEncoded
    @POST("Users/close_demand")
    Call<WrapperRspEntity<SkillCloseState>> postCloseXuqiu(@Field("token") String token, @Field("kid") String kid);
    /**
     * 发布兼职
     *
     token（用户验证）
     name（兼职名称）
     address（工作地址）
     start_date（开始日期）
     end_date（结束日期）
     money（薪资）
     unit（单位 1天 2小时）
     way（结算方式 1日结 2完工结）
     introduce（兼职介绍）
     require（兼职要求）
     */
    @FormUrlEncoded
    @POST("Work/issue_work")
    Call<WrapperRspEntity> postIssue_work(@Field("token") String token, @Field("name") String name, @Field("address") String address, @Field("start_date") String start_date, @Field("end_date") String end_date,
                                                      @Field("money") String money, @Field("unit") String unit, @Field("way") String way, @Field("introduce") String introduce, @Field("require") String require);

    /**
     * 为你推荐
     *
     token（用户验证）
     */
    @FormUrlEncoded
    @POST("Users/recommend")
    Call<WrapperRspEntity<List<RecommendList>>> getRecommend(@Field("token") String token);

    /**
     * 我的关注
     *
     token（用户验证）
     state（1关注的人 2关注的兼职）
     page（页数）
     num（条数）
     */
    @FormUrlEncoded
    @POST("Users/my_collect")
    Call<WrapperRspEntity<List<FollowBean>>> getMyCollect(@Field("token") String token, @Field("state") String state, @Field("page") String page, @Field("num") String num);
    /**
     * 关注/取消关注兼职、用户
     */
    @FormUrlEncoded
    @POST("Work/like")
    Call<IsGuanzhuBean> getIsGuanzhu(@Field("token") String token,@Field("wid") String wid,@Field("uid") String uid);
    /**
     * 我的认证
     *
     token（用户验证）
     state（1认证信息 2紧急联系人信息）
     */
    @FormUrlEncoded
    @POST("Users/information")
    Call<WrapperRspEntity<AuthenticationBean>> getInformation(@Field("token") String token, @Field("state") String state);
    @FormUrlEncoded
    /**
     * 首页搜索
     */
    @POST("Homepage/search")
    Call<HomeSearchBean> getHomeSearch(@Field("token") String token, @Field("title") String title,@Field("type") String type);
    /**
     * 首页搜索
     */
    @FormUrlEncoded
    @POST("Homepage/search")
    Call<HomeSearchYuetaBean> getHomeSearchYueta(@Field("token") String token, @Field("title") String title,@Field("type") String type);
    /**
     * 首页搜索
     */
    @FormUrlEncoded
    @POST("Homepage/search")
    Call<HomeSearchTayeuBean> getHomeSearchTayue(@Field("token") String token, @Field("title") String title,@Field("type") String type);
    /**
     * 搜索记录
     */
    @FormUrlEncoded
    @POST("Users/search_history")
    Call<SearchJiluBean> getHomeSearchjilu(@Field("token") String token);
    /**
     * 全部约TA
     */
    @POST("Yue/yueta_list")
    Call<HomeAllYueTaBean> getHomeYueTaAll();

    /**
     * 全部TA约
     */
    @FormUrlEncoded
    @POST("Yue/tayue_list")
    Call<HomeTaYueAllBean> getHomeTaYueAll(@Field("token") String token, @Field("title") String title,@Field("type") String type);
    /**
     * 约TA订单
     */
    @FormUrlEncoded
    @POST("Yue/yueta")
    Call<DingDanYueTaBean> getDingdanYueta(@Field("token") String token, @Field("state") String state);
    /**
     * 约TA订单已邀请
     */
    @FormUrlEncoded
    @POST("Yue/detail")
    Call<DingdanYiYaoqingBean> getDingdanYiyyaoqing(@Field("token") String token, @Field("did") String did);

    /**
     * 确认约TA
     */
    @FormUrlEncoded
    @POST("Yue/confirm_yueta")
    Call<QuerenYuetaBean> getQuerenYueta(@Field("token") String token, @Field("did") String did,@Field("uid") String uid);
    /**
     * 编辑技能
     */
    @Multipart
    @POST("Users/edit_skill")
    Call<WrapperRspEntity<MyissueDetail>> xiugai(@Part List<MultipartBody.Part> partList);
    /**
     * 删除技能
     */
    @FormUrlEncoded
    @POST("Users/delete_skill")
    Call<WrapperRspEntity<DeleteBean>> isdelete(@Field("token") String token, @Field("kid") String kid);
    /**
     * 删除需求
     */
    @FormUrlEncoded
    @POST("Users/delete_demand")
    Call<WrapperRspEntity<DeleteBean>> isxuqiudelete(@Field("token") String token, @Field("kid") String kid);
    /**
     * 约TA评价
     */
    @FormUrlEncoded
    @POST("Yue/evaluate")
    Call<PingjiaBean> pingjia(@Field("token") String token, @Field("did") String did, @Field("level") String level, @Field("content") String content);
    /**.
     * TA约我
     */
    @FormUrlEncoded
    @POST("Yue/tayue")
    Call<TaYuewoBean> getTayuewo(@Field("token") String token, @Field("state") String state);
    /**
     * 应邀
     */
    @FormUrlEncoded
    @POST("Yue/invitation")
    Call<PingjiaBean> getYingyao(@Field("token") String token, @Field("did") String did);
    /**
     * 全部兼职
     */
    @FormUrlEncoded
    @POST("Work/work_list")
    Call<HomeJianzhiBean> getHomeJianzhi(@Field("token") String token, @Field("title") String title,@Field("type") String type);

    /**
     * 发布的兼职
     */
    @FormUrlEncoded
    @POST("Work/myissue_work")
    Call<FabuJianzhiBean> getFabujianzhi(@Field("token") String token, @Field("state") String state);
    /**
     * 录用管理
     */
    @FormUrlEncoded
    @POST("Work/offer_detail")
    Call<BaomingGuanliBean> getLuyong(@Field("token") String token, @Field("wid") String wid);
    /**
     * 报名管理
     */
    @FormUrlEncoded
    @POST("Work/apply_detail")
    Call<BaomingGuanliBean> getBaoming(@Field("token") String token, @Field("wid") String wid);
    /**
     *录用
     */
    @FormUrlEncoded
    @POST("Work/offer")
    Call<LuyongBeans> getluyongren(@Field("token") String token, @Field("wid") String wid,@Field("uid") String uid);
    /**
     * 兼职详情
     */
    @FormUrlEncoded
    @POST("Work/work_detail")
    Call<HomeJianzhiQXBean> getJianzhiQX(@Field("token") String token, @Field("wid") String wid);
    /**
     *兼职报名
     */
    @FormUrlEncoded
    @POST("Work/apply")
    Call<BaomingBean> getIsBaoming(@Field("token") String token, @Field("wid") String wid);
    /**
     * 我的兼职订单
     */
    @FormUrlEncoded
    @POST("Work/mywork")
    Call<MyJianzhiBean> getMyjianzhi(@Field("token") String token, @Field("state") String state);
    /**
     * 附近人信息
     */
    @FormUrlEncoded
    @POST("Homepage/nearby_detail")
    Call<FJmessageBean> getFujinren(@Field("token") String token, @Field("uid") String uid, @Field("lng") String lng, @Field("lat") String lat);
    /**
     * 修改密码
     */
    @FormUrlEncoded
    @POST("Users/edit_pwd")
    Call<XiuGaiBean> getXiugaipassword (@Field("token") String token, @Field("pwd") String pwd, @Field("password") String password);
}
