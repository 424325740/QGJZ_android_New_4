package com.qigaikj.parttimejob.bean;

import java.util.List;

public class BaomingGuanliBean {


    /**
     * ret : 1
     * message : 请求成功
     * data : {"work":{"title":"测试明天兼职","start_date":"6月30日","end_date":"7月2日","way":"1","state":"2","count":"3"},"users":[{"uid":"8","uname":"Make it happen","logo":"https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKy8nrX7vOPKPlZictpqPYhIT18icicZaKv7zicIq2Tu01HALuGmf31JG7We5npMzUlpVNt9A9c4Cbbuw/132","flag":"3"},{"uid":"1","uname":"qgid_1","logo":"http://ceshi.qigaikj.com/Public/file/20171109163437.png","flag":"1"},{"uid":"37","uname":"qgid_37","logo":"http://ceshi.qigaikj.com/Public/file/20171109163437.png","flag":"1"}]}
     */

    private int ret;
    private String message;
    private DataBean data;

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * work : {"title":"测试明天兼职","start_date":"6月30日","end_date":"7月2日","way":"1","state":"2","count":"3"}
         * users : [{"uid":"8","uname":"Make it happen","logo":"https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKy8nrX7vOPKPlZictpqPYhIT18icicZaKv7zicIq2Tu01HALuGmf31JG7We5npMzUlpVNt9A9c4Cbbuw/132","flag":"3"},{"uid":"1","uname":"qgid_1","logo":"http://ceshi.qigaikj.com/Public/file/20171109163437.png","flag":"1"},{"uid":"37","uname":"qgid_37","logo":"http://ceshi.qigaikj.com/Public/file/20171109163437.png","flag":"1"}]
         */

        private WorkBean work;
        private List<UsersBean> users;

        public WorkBean getWork() {
            return work;
        }

        public void setWork(WorkBean work) {
            this.work = work;
        }

        public List<UsersBean> getUsers() {
            return users;
        }

        public void setUsers(List<UsersBean> users) {
            this.users = users;
        }

        public static class WorkBean {
            /**
             * title : 测试明天兼职
             * start_date : 6月30日
             * end_date : 7月2日
             * way : 1
             * state : 2
             * count : 3
             */

            private String title;
            private String start_date;
            private String end_date;
            private String way;
            private String state;
            private String count;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getStart_date() {
                return start_date;
            }

            public void setStart_date(String start_date) {
                this.start_date = start_date;
            }

            public String getEnd_date() {
                return end_date;
            }

            public void setEnd_date(String end_date) {
                this.end_date = end_date;
            }

            public String getWay() {
                return way;
            }

            public void setWay(String way) {
                this.way = way;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public String getCount() {
                return count;
            }

            public void setCount(String count) {
                this.count = count;
            }
        }

        public static class UsersBean {
            /**
             * uid : 8
             * uname : Make it happen
             * logo : https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKy8nrX7vOPKPlZictpqPYhIT18icicZaKv7zicIq2Tu01HALuGmf31JG7We5npMzUlpVNt9A9c4Cbbuw/132
             * flag : 3
             */

            private String uid;
            private String uname;
            private String logo;
            private String flag;

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getUname() {
                return uname;
            }

            public void setUname(String uname) {
                this.uname = uname;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public String getFlag() {
                return flag;
            }

            public void setFlag(String flag) {
                this.flag = flag;
            }
        }
    }
}
