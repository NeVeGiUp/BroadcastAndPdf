package com.itc.suppaperless.meeting_vote.bean;

import java.util.List;

/**
 * Created by cong on 19-3-7.
 */

public class MeetingVoteBean {
    /**
     * iCmdEnum : 217
     * iVoteNum : 1
     * lstVote : [{"iVoteID":3,"strVoteTitle":"测试","strVoteName":"444","iStatus":0,"iIssueID":100000,"iScreenMode":0,"strIssueName":"在菜市场","strComment":"","strStartTime":"0000-00-00 00:00:00","strEndTime":"0000-00-00 00:00:00","iCountdown":0,"iCheckbox":0,"iRealName":1,"iIsSign":0,"iIsComment":0,"iIsPassingRate":0,"iPassingRate":0,"iPROptionID":0,"iIsTimeLimit":0,"strTimeLimit":"00:00:00","iVotingAuthentication":0,"iIsVote":0,"aiUserID":[100000,100001,100002,100003,100004,100005,100006,100007,100008,100009,100010,100011,100012,100013,100014,100015,100016,100017,100018,100019,100020,100021,100022,100023,100024,100025,100026,100027,100028,100029,100030,100031,100032,100033,100034,100035,100036,100037,100038,100039,100040,100041,100042,100043,100044,100045,100046],"lstOption":[{"iOptionID":5,"strOptionName":"赞成","iNum":0,"aiVotedUserID":[],"iScoreCount":0,"iScoreAverage":0},{"iOptionID":6,"strOptionName":"赞","iNum":0,"aiVotedUserID":[],"iScoreCount":0,"iScoreAverage":0}],"iScoreType":0,"iScoreScore":""}]
     */

    private int iCmdEnum;
    private int iVoteNum;
    private List<LstVoteBean> lstVote;

    public int getICmdEnum() {
        return iCmdEnum;
    }

    public void setICmdEnum(int iCmdEnum) {
        this.iCmdEnum = iCmdEnum;
    }

    public int getIVoteNum() {
        return iVoteNum;
    }

    public void setIVoteNum(int iVoteNum) {
        this.iVoteNum = iVoteNum;
    }

    public List<LstVoteBean> getLstVote() {
        return lstVote;
    }

    public void setLstVote(List<LstVoteBean> lstVote) {
        this.lstVote = lstVote;
    }

    public static class LstVoteBean {
        /**
         * iVoteID : 3
         * strVoteTitle : 测试
         * strVoteName : 444
         * iStatus : 0
         * iIssueID : 100000
         * iScreenMode : 0
         * strIssueName : 在菜市场
         * strComment :
         * strStartTime : 0000-00-00 00:00:00
         * strEndTime : 0000-00-00 00:00:00
         * iCountdown : 0
         * iCheckbox : 0
         * iRealName : 1
         * iIsSign : 0
         * iIsComment : 0
         * iIsPassingRate : 0
         * iPassingRate : 0
         * iPROptionID : 0
         * iIsTimeLimit : 0
         * strTimeLimit : 00:00:00
         * iVotingAuthentication : 0
         * iIsVote : 0
         * aiUserID : [100000,100001,100002,100003,100004,100005,100006,100007,100008,100009,100010,100011,100012,100013,100014,100015,100016,100017,100018,100019,100020,100021,100022,100023,100024,100025,100026,100027,100028,100029,100030,100031,100032,100033,100034,100035,100036,100037,100038,100039,100040,100041,100042,100043,100044,100045,100046]
         * lstOption : [{"iOptionID":5,"strOptionName":"赞成","iNum":0,"aiVotedUserID":[],"iScoreCount":0,"iScoreAverage":0},{"iOptionID":6,"strOptionName":"赞","iNum":0,"aiVotedUserID":[],"iScoreCount":0,"iScoreAverage":0}]
         * iScoreType : 0
         * iScoreScore :
         */

        private int iVoteID;
        private String strVoteTitle;
        private String strVoteName;
        private int iStatus;
        private int iIssueID;
        private int iScreenMode;
        private String strIssueName;
        private String strComment;
        private String strStartTime;
        private String strEndTime;
        private int iCountdown;
        private int iUpdateType;
        private int iCheckbox;
        private int iRealName;
        private int iIsSign;
        private int iIsComment;
        private int iIsPassingRate;
        private int iPassingRate;
        private int iPROptionID;
        private int iIsTimeLimit;
        private String strTimeLimit;
        private int iVotingAuthentication;
        private int iIsVote;
        private int iScoreType;
        private String iScoreScore;
        private List<Integer> aiUserID;
        private List<LstOptionBean> lstOption;

        public int getiUpdateType() {
            return iUpdateType;
        }

        public void setiUpdateType(int iUpdateType) {
            this.iUpdateType = iUpdateType;
        }


        public int getIVoteID() {
            return iVoteID;
        }

        public void setIVoteID(int iVoteID) {
            this.iVoteID = iVoteID;
        }

        public String getStrVoteTitle() {
            return strVoteTitle;
        }

        public void setStrVoteTitle(String strVoteTitle) {
            this.strVoteTitle = strVoteTitle;
        }

        public String getStrVoteName() {
            return strVoteName;
        }

        public void setStrVoteName(String strVoteName) {
            this.strVoteName = strVoteName;
        }

        public int getIStatus() {
            return iStatus;
        }

        public void setIStatus(int iStatus) {
            this.iStatus = iStatus;
        }

        public int getIIssueID() {
            return iIssueID;
        }

        public void setIIssueID(int iIssueID) {
            this.iIssueID = iIssueID;
        }

        public int getIScreenMode() {
            return iScreenMode;
        }

        public void setIScreenMode(int iScreenMode) {
            this.iScreenMode = iScreenMode;
        }

        public String getStrIssueName() {
            return strIssueName;
        }

        public void setStrIssueName(String strIssueName) {
            this.strIssueName = strIssueName;
        }

        public String getStrComment() {
            return strComment;
        }

        public void setStrComment(String strComment) {
            this.strComment = strComment;
        }

        public String getStrStartTime() {
            return strStartTime;
        }

        public void setStrStartTime(String strStartTime) {
            this.strStartTime = strStartTime;
        }

        public String getStrEndTime() {
            return strEndTime;
        }

        public void setStrEndTime(String strEndTime) {
            this.strEndTime = strEndTime;
        }

        public int getICountdown() {
            return iCountdown;
        }

        public void setICountdown(int iCountdown) {
            this.iCountdown = iCountdown;
        }

        public int getICheckbox() {
            return iCheckbox;
        }

        public void setICheckbox(int iCheckbox) {
            this.iCheckbox = iCheckbox;
        }

        public int getIRealName() {
            return iRealName;
        }

        public void setIRealName(int iRealName) {
            this.iRealName = iRealName;
        }

        public int getIIsSign() {
            return iIsSign;
        }

        public void setIIsSign(int iIsSign) {
            this.iIsSign = iIsSign;
        }

        public int getIIsComment() {
            return iIsComment;
        }

        public void setIIsComment(int iIsComment) {
            this.iIsComment = iIsComment;
        }

        public int getIIsPassingRate() {
            return iIsPassingRate;
        }

        public void setIIsPassingRate(int iIsPassingRate) {
            this.iIsPassingRate = iIsPassingRate;
        }

        public int getIPassingRate() {
            return iPassingRate;
        }

        public void setIPassingRate(int iPassingRate) {
            this.iPassingRate = iPassingRate;
        }

        public int getIPROptionID() {
            return iPROptionID;
        }

        public void setIPROptionID(int iPROptionID) {
            this.iPROptionID = iPROptionID;
        }

        public int getIIsTimeLimit() {
            return iIsTimeLimit;
        }

        public void setIIsTimeLimit(int iIsTimeLimit) {
            this.iIsTimeLimit = iIsTimeLimit;
        }

        public String getStrTimeLimit() {
            return strTimeLimit;
        }

        public void setStrTimeLimit(String strTimeLimit) {
            this.strTimeLimit = strTimeLimit;
        }

        public int getIVotingAuthentication() {
            return iVotingAuthentication;
        }

        public void setIVotingAuthentication(int iVotingAuthentication) {
            this.iVotingAuthentication = iVotingAuthentication;
        }

        public int getIIsVote() {
            return iIsVote;
        }

        public void setIIsVote(int iIsVote) {
            this.iIsVote = iIsVote;
        }

        public int getIScoreType() {
            return iScoreType;
        }

        public void setIScoreType(int iScoreType) {
            this.iScoreType = iScoreType;
        }

        public String getIScoreScore() {
            return iScoreScore;
        }

        public void setIScoreScore(String iScoreScore) {
            this.iScoreScore = iScoreScore;
        }

        public List<Integer> getAiUserID() {
            return aiUserID;
        }

        public void setAiUserID(List<Integer> aiUserID) {
            this.aiUserID = aiUserID;
        }

        public List<LstOptionBean> getLstOption() {
            return lstOption;
        }

        public void setLstOption(List<LstOptionBean> lstOption) {
            this.lstOption = lstOption;
        }

        public static class LstOptionBean {
            /**
             * iOptionID : 5
             * strOptionName : 赞成
             * iNum : 0
             * aiVotedUserID : []
             * iScoreCount : 0
             * iScoreAverage : 0
             */

            private int iOptionID;
            private String strOptionName;
            private int iNum;
            private int iScoreCount;
            private int iScoreAverage;
            private int iTotalUserNum;
            private int iNotVoteNum;
            private String strVoteName;
            private List<Integer> aiVotedUserID;
            private boolean isCheck;

            public int getiTotalUserNum() {
                return iTotalUserNum;
            }

            public void setiTotalUserNum(int iTotalUserNum) {
                this.iTotalUserNum = iTotalUserNum;
            }

            public int getiNotVoteNum() {
                return iNotVoteNum;
            }

            public void setiNotVoteNum(int iNotVoteNum) {
                this.iNotVoteNum = iNotVoteNum;
            }


            public String getStrVoteName() {
                return strVoteName;
            }

            public void setStrVoteName(String strVoteName) {
                this.strVoteName = strVoteName;
            }

            public boolean isCheck() {
                return isCheck;
            }

            public void setCheck(boolean check) {
                isCheck = check;
            }

            public int getIOptionID() {
                return iOptionID;
            }

            public void setIOptionID(int iOptionID) {
                this.iOptionID = iOptionID;
            }

            public String getStrOptionName() {
                return strOptionName;
            }

            public void setStrOptionName(String strOptionName) {
                this.strOptionName = strOptionName;
            }

            public int getINum() {
                return iNum;
            }

            public void setINum(int iNum) {
                this.iNum = iNum;
            }

            public int getIScoreCount() {
                return iScoreCount;
            }

            public void setIScoreCount(int iScoreCount) {
                this.iScoreCount = iScoreCount;
            }

            public int getIScoreAverage() {
                return iScoreAverage;
            }

            public void setIScoreAverage(int iScoreAverage) {
                this.iScoreAverage = iScoreAverage;
            }

            public List<Integer> getAiVotedUserID() {
                return aiVotedUserID;
            }

            public void setAiVotedUserID(List<Integer> aiVotedUserID) {
                this.aiVotedUserID = aiVotedUserID;
            }
        }
    }
}
