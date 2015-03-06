package com.wenwen.clinic.chatuidemo.utils;

public class Constants {
    // 默认http传输时的编码为utf-8
    public static final String DEFAULT_URL_ENCODING = "UTF-8";
    
    public static final class ActionCode {
        public static final int ACT_HOSPITAL_SELECT = 1000;    // 医院选择
        public static final int ACT_DEPARTMENT_SELECT = 1001;    //科室选择
        public static final int  ACT_FIND= 1002;//查找
        
    }
    public static final String[] hostpital = { "浙江大学医学院附属第二医院", "浙1医院",
            "浙江大学医学院附属邵逸夫医院", "浙江省中医院", "温州医院院附属第一医院", "浙江大学医学院附属妇产科医院",
            "宁波市第一医院", "杭州市中医院", "浙江省人民医院", "台州医院", "宁波市中医院  " };

    public static final String[] department = { "内科", "外科", "妇科", "儿科", "神经科", "眼科",
            "耳鼻喉科", "口腔科", "泌尿科", "骨科", "创伤科", "内分泌科", "精神科", "麻醉科", "病理科",
            "皮肤科", "胸外科", "心脏外科", "脑外科" };
}
