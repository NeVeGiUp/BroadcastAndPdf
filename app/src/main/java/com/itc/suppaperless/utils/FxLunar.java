package com.itc.suppaperless.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * 自定义农历类，实现农历和公历的转换
 */
class FxLunar {

    private int _lunarYear;   //农历的年份
    private int _lunarMonth;
    private int _lunarDay;
    private boolean _isLeapMonth;  //_lunarMonth表示的农历月是否是闰月
    private int _leapMonthOfYear = 0;   //闰的是哪个月

    private int _solarYear;   //阳历的年份
    private int _solarMonth;
    private int _solarDay;

    private final static String chineseNumber[] = {"一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "冬", "腊"};
    private final static String[] Animals = new String[]{"鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊", "猴", "鸡", "狗", "猪"};
    private final static String[] Gan = new String[]{"甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸"};
    private final static String[] Zhi = new String[]{"子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥"};

    private final static long[] lunarInfo = new long[]{0x04bd8, 0x04ae0, 0x0a570,
            0x054d5, 0x0d260, 0x0d950, 0x16554, 0x056a0, 0x09ad0, 0x055d2,
            0x04ae0, 0x0a5b6, 0x0a4d0, 0x0d250, 0x1d255, 0x0b540, 0x0d6a0,
            0x0ada2, 0x095b0, 0x14977, 0x04970, 0x0a4b0, 0x0b4b5, 0x06a50,
            0x06d40, 0x1ab54, 0x02b60, 0x09570, 0x052f2, 0x04970, 0x06566,
            0x0d4a0, 0x0ea50, 0x06e95, 0x05ad0, 0x02b60, 0x186e3, 0x092e0,
            0x1c8d7, 0x0c950, 0x0d4a0, 0x1d8a6, 0x0b550, 0x056a0, 0x1a5b4,
            0x025d0, 0x092d0, 0x0d2b2, 0x0a950, 0x0b557, 0x06ca0, 0x0b550,
            0x15355, 0x04da0, 0x0a5d0, 0x14573, 0x052d0, 0x0a9a8, 0x0e950,
            0x06aa0, 0x0aea6, 0x0ab50, 0x04b60, 0x0aae4, 0x0a570, 0x05260,
            0x0f263, 0x0d950, 0x05b57, 0x056a0, 0x096d0, 0x04dd5, 0x04ad0,
            0x0a4d0, 0x0d4d4, 0x0d250, 0x0d558, 0x0b540, 0x0b5a0, 0x195a6,
            0x095b0, 0x049b0, 0x0a974, 0x0a4b0, 0x0b27a, 0x06a50, 0x06d40,
            0x0af46, 0x0ab60, 0x09570, 0x04af5, 0x04970, 0x064b0, 0x074a3,
            0x0ea50, 0x06b58, 0x055c0, 0x0ab60, 0x096d5, 0x092e0, 0x0c960,
            0x0d954, 0x0d4a0, 0x0da50, 0x07552, 0x056a0, 0x0abb7, 0x025d0,
            0x092d0, 0x0cab5, 0x0a950, 0x0b4a0, 0x0baa4, 0x0ad50, 0x055d9,
            0x04ba0, 0x0a5b0, 0x15176, 0x052b0, 0x0a930, 0x07954, 0x06aa0,
            0x0ad50, 0x05b52, 0x04b60, 0x0a6e6, 0x0a4e0, 0x0d260, 0x0ea65,
            0x0d530, 0x05aa0, 0x076a3, 0x096d0, 0x04bd7, 0x04ad0, 0x0a4d0,
            0x1d0b6, 0x0d250, 0x0d520, 0x0dd45, 0x0b5a0, 0x056d0, 0x055b2,
            0x049b0, 0x0a577, 0x0a4b0, 0x0aa50, 0x1b255, 0x06d20, 0x0ada0};

    //农历部分假日
    private final static String[] lunarHoliday = new String[]{
            "0101 春节",
            "0115 元宵",
            "0505 端午",
            "0707 七夕",
            "0715 中元",
            "0815 中秋",
            "0909 重阳",
            "1208 腊八",
            "1224 小年",
            "0100 除夕"
    };

    //公历部分节假日
    private final static String[] solarHoliday = new String[]{
            "0101 元旦",
            "0214 情人",
            "0308 妇女",
            "0312 植树",
            "0315 消费者权益日",
            "0401 愚人",
            "0501 劳动",
            "0504 青年",
            "0512 护士",
            "0601 儿童",
            "0701 建党",
            "0801 建军",
            "0808 父亲",
            "0909 毛泽东逝世纪念",
            "0910 教师",
            "0928 孔子诞辰",
            "1001 国庆",
            "1006 老人",
            "1024 联合国日",
            "1112 孙中山诞辰纪念",
            "1220 澳门回归纪念",
            "1225 圣诞",
            "1226 毛泽东诞辰纪念"
    };

    FxLunar() {
        setSolarDate(new Date());
    }

    /**
     * 传回农历 y年的总天数
     */
    private static int getLunarDayCountOfYear(int y) {
        int i, sum = 348;
        for (i = 0x8000; i > 0x8; i >>= 1) {
            if ((lunarInfo[y - 1900] & i) != 0)
                sum += 1;
        }
        return (sum + getLunarDayCountOfLeapMonth(y));
    }

    /**
     * 传回农历 y年m月的总天数
     */
    private static int getLunarDayCountOfMonth(int y, int m) {
        if ((lunarInfo[y - 1900] & (0x10000 >> m)) == 0)
            return 29;
        else
            return 30;
    }

    /**
     * 传回农历 y年闰月的天数
     */
    private static int getLunarDayCountOfLeapMonth(int y) {
        if (getLunarLeapMonth(y) != 0) {
            if ((lunarInfo[y - 1900] & 0x10000) != 0)
                return 30;
            else
                return 29;
        } else
            return 0;
    }

    /**
     * 传回农历 y年闰哪个月 1-12 , 没闰传回 0
     */
    private static int getLunarLeapMonth(int y) {
        return (int) (lunarInfo[y - 1900] & 0xf);
    }

    /**
     * 传回农历 y年的生肖
     */
    private static String getAnimal(int year) {
        return Animals[(year - 4) % 12];
    }

    // ====== 传入 月日的offset 传回干支, 0=甲子
    private static String cyclicalm(int num) {
        return (Gan[num % 10] + Zhi[num % 12]);
    }

    /**
     * 传回干支, 0=甲子
     */
    private static String getGanZhi(int year) {
        int num = year - 1900 + 36;
        return (cyclicalm(num));
    }

    /**
     * 传回农历日的表达式，如"初十"
     */
    private static String getChinaDayString(int day) {
        String chineseTen[] = {"初", "十", "廿", "卅"};
        int n = day % 10 == 0 ? 9 : day % 10 - 1;
        if (day > 30)
            return "";
        if (day == 10)
            return "初十";
        else
            return chineseTen[day / 10] + chineseNumber[n];
    }

    /**
     * 设置当前阳历日期
     */
    void setSolarDate(Date solarDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(solarDate);
        setSolarDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * 设置当前阳历日期
     */
    void setSolarDate(int year, int month, int day) {

        _solarYear = year;
        _solarMonth = month;
        _solarDay = day;

        Calendar baseDate = Calendar.getInstance();
        baseDate.clear();
        baseDate.set(1900, 0, 31);   //起始日期

        Calendar nowaday = Calendar.getInstance();
        nowaday.clear();
        nowaday.set(year, month - 1, day);  //当前日期

        // 求出和1900年1月31日相差的天数
        int offset = (int) ((nowaday.getTime().getTime() - baseDate.getTime().getTime()) / 86400000L);

        // 用offset减去每农历年的天数
        // 计算当天是农历第几天
        // i最终结果是农历的年份
        // offset是当年的第几天
        int iYear, daysOfYear = 0;
        for (iYear = 1900; iYear < 10000 && offset > 0; iYear++) {
            daysOfYear = getLunarDayCountOfYear(iYear);
            offset -= daysOfYear;
        }
        if (offset < 0) {
            offset += daysOfYear;
            iYear--;
        }
        // 农历年份
        _lunarYear = iYear;

        _leapMonthOfYear = getLunarLeapMonth(iYear); // 闰哪个月,1-12
        _isLeapMonth = false;

        // 用当年的天数offset,逐个减去每月（农历）的天数，求出当天是本月的第几天
        int iMonth, daysOfMonth = 0;
        for (iMonth = 1; iMonth < 13 && offset > 0; iMonth++) {
            // 闰月
            if (_leapMonthOfYear > 0 && iMonth == (_leapMonthOfYear + 1) && !_isLeapMonth) {
                --iMonth;
                _isLeapMonth = true;
                daysOfMonth = getLunarDayCountOfLeapMonth(_lunarYear);
            } else
                daysOfMonth = getLunarDayCountOfMonth(_lunarYear, iMonth);

            offset -= daysOfMonth;
            // 解除闰月
            if (_isLeapMonth && iMonth == (_leapMonthOfYear + 1))
                _isLeapMonth = false;
        }
        // offset为0时，并且刚才计算的月份是闰月，要校正
        if (offset == 0 && _leapMonthOfYear > 0 && iMonth == _leapMonthOfYear + 1) {
            if (_isLeapMonth) {
                _isLeapMonth = false;
            } else {
                _isLeapMonth = true;
                --iMonth;
            }
        }
        // offset小于0时，也要校正
        if (offset < 0) {
            offset += daysOfMonth;
            --iMonth;
        }
        _lunarMonth = iMonth;
        _lunarDay = offset + 1;
    }

    /**
     * 设置当前农历日期
     */
    private void setLunarDate(int year, int month, int day) {
        SetLunarDate(year, month, day, false);
    }

    /**
     * 设置当前农历日期, isLeapMonth表示设置的月份是否是闰月
     */
    private void SetLunarDate(int year, int month, int day, boolean isLeapMonth) {
        _lunarYear = year;
        _lunarMonth = month;
        _lunarDay = day;

        Calendar dt0 = Calendar.getInstance();
        dt0.clear();
        dt0.set(1900, 0, 31);  //阳历1900-1-31对应与农历1900-1-1

        int offset = 0;   //表示农历日期和当前日期相差的天数
        for (int i = 1900; i < _lunarYear; i++) {
            offset += getLunarDayCountOfYear(i);
        }
        _isLeapMonth = false;
        _leapMonthOfYear = getLunarLeapMonth(_lunarYear); // 闰哪个月,1-12
        int iMonth, daysOfMonth;

        if (_leapMonthOfYear == 0 || _lunarMonth < _leapMonthOfYear || (_lunarMonth == _leapMonthOfYear && !isLeapMonth)) {  //如果没有闰月,或是该月比闰月小,或是不是计算的闰月
            for (iMonth = 1; iMonth < _lunarMonth; iMonth++) {
                daysOfMonth = getLunarDayCountOfMonth(_lunarYear, iMonth);
                offset += daysOfMonth;
            }
        } else if (_lunarMonth == _leapMonthOfYear) {  //如果要求计算闰月，则计算时要加上非闰月
            for (iMonth = 1; iMonth <= _lunarMonth; iMonth++) {
                daysOfMonth = getLunarDayCountOfMonth(_lunarYear, iMonth);
                offset += daysOfMonth;
            }
        } else {  //计算的月份比闰月大，则计算时加上闰月
            for (iMonth = 1; iMonth < _lunarMonth; iMonth++) {
                daysOfMonth = getLunarDayCountOfMonth(_lunarYear, iMonth);
                offset += daysOfMonth;
            }
            offset += getLunarDayCountOfLeapMonth(_lunarYear);
        }

        offset += _lunarDay - 1;
        dt0.add(Calendar.DAY_OF_MONTH, offset);

        _isLeapMonth = isLeapMonth && _leapMonthOfYear == month;

        _solarYear = dt0.get(Calendar.YEAR);
        _solarMonth = dt0.get(Calendar.MONTH) + 1;
        _solarDay = dt0.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取当前表示的阳历日期
     */
    private Date GetSolarDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(_solarYear, _solarMonth - 1, _solarDay);
        return calendar.getTime();
    }

    /**
     * 获取当前的节日，如"春节"
     */
    private String getFestival() {
        //返回公历节假日名称
        for (String aSolarHoliday : solarHoliday) {
            String sd = aSolarHoliday.split(" ")[0];  //节假日的日期
            String sdv = aSolarHoliday.split(" ")[1]; //节假日的名称
            String smonth_v = _solarMonth + "";
            String sday_v = _solarDay + "";
            String smd;
            if (_solarMonth < 10) {
                smonth_v = "0" + _solarMonth;
            }
            if (_solarDay < 10) {
                sday_v = "0" + _solarDay;
            }
            smd = smonth_v + sday_v;
            if (sd.trim().equals(smd.trim())) {
                return sdv;
            }
        }

        //返回农历节假日名称
        for (String aLunarHoliday : lunarHoliday) {
            String ld = aLunarHoliday.split(" ")[0];   //节假日的日期
            String ldv = aLunarHoliday.split(" ")[1];  //节假日的名称
            String lmonth_v = _lunarMonth + "";
            String lday_v = _lunarDay + "";
            String lmd;
            if (_lunarMonth < 10) {
                lmonth_v = "0" + _lunarMonth;
            }
            if (_lunarDay < 10) {
                lday_v = "0" + _lunarDay;
            }
            lmd = lmonth_v + lday_v;
            if (ld.trim().equals(lmd.trim())) {
                return ldv;
            }
        }
        return "";
    }

    /**
     * 判断农历的日期是否正确，不正确则返回改正后的结果，如"2014-5-31"->"2014-6-1"
     */
    private static int[] checkLunarDate(int year, int month, int day) {
        if (month < 1) {
            return checkLunarDate(year - 1, month + 12, day);
        } else if (month > 12) {
            return checkLunarDate(year + 1, month - 12, day);
        }

        if (day < 1) {
            if (month == 1) {
                return checkLunarDate(year - 1, 12, day + getLunarDayCountOfMonth(year - 1, 12));  //1月的话则年份-1
            }
            return checkLunarDate(year, month - 1, day + getLunarDayCountOfMonth(year, month - 1));  //月份-1
        } else if (day > getLunarDayCountOfMonth(year, month)) {
            if (month == 12) {
                return checkLunarDate(year + 1, 1, day - getLunarDayCountOfMonth(year, month));   //12月的话则年份+1
            }
            return checkLunarDate(year, month + 1, day - getLunarDayCountOfMonth(year, month));   //月份+1
        }
        return new int[]{year, month, day};
    }

    /**
     * 得到两个农历的时间差，fr-fl
     */
    public static int isEqual(FxLunar fl, FxLunar fr, int field) {
        if (field == FxHelp.YEAR) {
            return fr._lunarYear - fl._lunarYear;
        } else if (field == FxHelp.MONTH) {
            return (fr._lunarYear - fl._lunarYear) * 12 + fr._lunarMonth - fl._lunarMonth;
        } else {
            return FxHelp.isEqual(fl.GetSolarDate(), fr.GetSolarDate());
        }
    }

    public String toString() {
        return _lunarYear + "年" + (_isLeapMonth ? "闰" : "") + chineseNumber[_lunarMonth - 1] + "月" + getChinaDayString(_lunarDay);
    }

    /**
     * 格式化日期
     *
     * @param format 格式：
     *               <br>				yyyy 	阳历年
     *               <br>				MM		阳历月
     *               <br>				dd 		阳历日
     *               <br>				WK		星期
     *               <br>				WI		该月的第几个星期
     *               <br>				LLLL	农历年(数字)
     *               <br>				LM		农历月(数字)
     *               <br>				LD		农历日(数字)
     *               <br>				CCCC	农历年(汉字)
     *               <br>				CM		农历月(汉字)
     *               <br>				CD		农历日(汉字)
     *               <br>				SX		生肖
     *               <br>				GZ		干支
     *               <br>				FS		节日
     *               <p>
     *               example:this.toString("yyyy年MM月dd日 农历GZ年CM月CD 星期WK");
     */
    String toString(String format) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(_solarYear, _solarMonth - 1, _solarDay);
        return format.replace("yyyy", "" + _solarYear)
                .replace("MM", "" + FxHelp.format(_solarMonth, 2, true))
                .replace("dd", "" + FxHelp.format(_solarDay, 2, true))
                .replace("WK", "" + FxHelp.WEEKDAY_STRINGS[calendar.get(Calendar.DAY_OF_WEEK) - 1].replace("星期", ""))
                .replace("WI", "" + calendar.get(Calendar.WEEK_OF_MONTH))
                .replace("LLLL", "" + _lunarYear)
                .replace("LM", "" + (_isLeapMonth ? "闰" : "") + FxHelp.format(_lunarMonth, 2, true))
                .replace("LD", "" + FxHelp.format(_lunarDay, 2, true))
                .replace("CCCC", "" + FxHelp.formatChineseString(_lunarYear, false))
                .replace("CM", "" + (_isLeapMonth ? "闰" : "") + chineseNumber[_lunarMonth - 1])
                .replace("CD", "" + getChinaDayString(_lunarDay))
                .replace("SX", "" + getAnimal(_lunarYear))
                .replace("GZ", "" + getGanZhi(_lunarYear))
                .replace("FS", "" + getFestival());
    }
}
