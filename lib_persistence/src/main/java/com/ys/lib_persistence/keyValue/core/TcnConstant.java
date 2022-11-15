package com.ys.lib_persistence.keyValue.core;


/**
 * Created by Songjiancheng on 2016/4/12.
 */
public interface TcnConstant {

    //public static final String IP="114.215.136.158";
    public static final String IP = "www.tcnvmms.com";

    public static final int SCOKET = 9801;
    public static final String TCN_CONFIG = "tcn_config.txt";
    public static final String TCN_CONFIG_IRIS_BOOST = "IRIS_Boost.json";
    public static final String YS_CONFIG_SLOTINFO = "YsConfig/SlotInfo.json";
    public static final String PATH_SDCARD = "/mnt/sdcard";
    public static final String ECOMMERCE_URL = "http://www.xxx.com/WXdevelop/VerifyCode";
    public static final String APK_UPDATE_URL = "http://tfs.android.tcnvmms.com:4103/Android/TcnVending/update.xml"; //默认发布地址
    public static final String UPDATE_APKURL_Ali5 = "http://tfs.android.tcnvmms.com:4103/Android/AliFaceSmlie5/update.xml";//smile5.0   与原来标准程序不兼容
    public static final String UPDATE_APKURL_profile = "http://tfs.android.tcnvmms.com:4103/Android/AliFaceSmlie5/profile.dat";//smile5.0   与原来标准程序不兼容
//    public static final String UPDATE_APKURL_Ali = "http://tfs.android.tcnvmms.com:4103/Android/AliFaceSmlie/update.xml";
//    public static final String UPDATE_APKURL_Ali_Uat = "http://tfs.android.tcnvmms.com:4103/Android/AliFaceSmlieUat/update.xml";

    public static final String APK_UPDATE_NAME = "TcnVending.apk";
    public static final String CARD_DEFAULT_KEY = "123456";

    public final static String[] USE_TCN_OR_CUSTOMER_IP = {"默认", "使用自己的服务器", "CDZZF", "CDDLBS", "CDMQT", "CDMQTFuP", "MQTV4"};// CDDLBS:纯电子支付独立部署服务器
    public final static String[] PAY_SYSTEM_TYPE = {"不使用主板支付系统", "使用主板支付系统"};
    public final static String[] QRCODE_SHOW_TYPE = {
            "NONE", "单码显示(多码合一)", "单码显示", "两个码显示", "不显示二维码",//4
            "两个码(微信&支付宝)", "两个码(多码合一&电商)", "两个码(多码合一&银联)", "单码(银联)", "单码(电商)",//9
            "单码(ZG)", "单码(GH)", "两个码(多码合一&银联ZG)", "IRIS", "Boost",    //14
            "IRIS&Boost", "单码显示S(多码合一)", "Beep", "gopay&danapay", "VNPay",//19
            "KsherPay", "LinePay", "SunwonPay", "MoMoPay", "IngenicoPay",   //24
            "CustomPay", "建行龙支付", "nequi", "ipay88 pay", "QPay merchant",//29
            "澳门通支付", "Paytm", "CustomQRAPP", "YsDynamicFup", "YsDynamicUnion", "YsDynamicUnion&Ones", "YsAP", "OffPaytm", //YsAP微信小程序     //37
            "ZaloPay", "Ones&YsS", "YsS", "YsDynamicCCB", "YsDynamicCCB&Ones", "YsS2","YsCustom","Ones&YsCustom"
    };

    public final static String[] DATA_TYPE = {"8C", "Common", "DRIVE_BOARD", "DRIVE_BOARD_LATTICE",
            "DRIVE_BOARD_LIFTER", "TH&GZ", "ShJ&TH", "ShJ&GZ", "ShJ&TH&GZ", "COFF", "TH&CF", "GZ&CF",
            "ShJ&CF", "TH&GZ&CF", "ShJ&TH&CF", "ShJ&GZ&CF", "Snake", "Sk&TH", "Sk&GZ"   //18
            , "Sk&SHJ", "Sk&TH&GZ", "NCF", "HANBAO", "ShJZJQH", "djs", "hefan", "shhefan", "shaobing",
            "wrsd", "sx", "pfrice", "ylth", "icec3", "fdzp", "MLZ", "WRDGS", "FDZK"   //36
            , "YTC", "coco", "miq", "snakenk", "snakenkth", "snakenkgz", "mbl", "redb", "ysbd", "tcnstand",
            "kim", "SD&GZ", "Meit", "tcnstandjs", "HWater", "cfmkx", "SD&Spr"      //53
            , "SD&Spr&GZ", "SD&Lifter", "SD&Lifter&Spr", "SD&Lifter&GZ", "SDJS&GZ", "SDJS&Spr", "SDJS&Spr&GZ", "FSC"   //61
            , "TH&ShJ", "TH&CF&ShJ", "JinMa", "Kwdni","YptADH815","XinXiaM102"};

    public final static String[] KEY_SLOT_DISPLAY_TYPE = {"无按键对应", "有按键对应", "只有按键对应", "USBKeyMapSlotPaiPai", "KeyMapSlotPaiPai"};
    public final static int[] PICK_UP_TIMEOUT_MODE = {0, 1};
    public final static String[] DEBUG_TYPE = {"menu", "log"};
    public final static String[] GPIO_TYPE = {"NONE", "DoorCheck"};
    public final static String[] CASH_DRIVE_TYPE = {"NONE", "GRE", "Dirve5In"};
    public final static String[] STORE_SALES_TYPE = {"NONE", "StoreSalesData"};
    public final static String[] WXFACE_TYPE = {"0", "1"};      //"微信刷脸类型（0:普通  1:强推）"
    public final static String[] FACE_OPEN = {"0","1", "2", "3", "4", "5", "6", "7", "8", "9","10"};      //"微信刷脸类型（0:普通  1:强推）"
    public final static String[] DEBUG_LOG_TYPE = {"0", "1"};      //"微信刷脸类型（0:普通  1:强推）"
    public final static String[] IS_YS_TYPE = {"0", "1"};      //"微信刷脸类型（0:普通  1:强推）"

    public final static String[] DEVICE_CONTROL_TYPE = {"NONE", "红色主板", "红色主板蛇形机", "红色主板蛇形机带副柜", "OD",
            "弹簧驱动", "格子驱动", "升降驱动", "咖啡驱动", "蛇形机驱动",
            "TCN-CF-6C", "HANBAO", "升降中间取货", "弹夹式饮料机", "盒饭机",
            "双核盒饭机", "烧饼机", "TCN-WRD", "TCN-SX", "TCN-S800-6C-BMH"      //19
            , "YTh", "TCN-ICEC-3", "TCN-D720-FD-ZP", "TCN-D900-MLZ", "TCN-WRD-GS",
            "TCN-D720V-FD-ZK", "TCN-YTC", "TCN-COCO", "TCN-TMQ", "TCN-CMC-NKEY",  //29
            "TCN-MBL", "TCN-HB2", "TCN-REDB", "YSDRIVE", "TCN-STAND", //34
            "Y-KM", "TCN-NODRV", "TCN-MeT", "TCN-STANDJS", "TCN_WATER", "TCN_cfmkx", "TCN_FSC", "JinMa", "Kwdni",    //43
            "YptADH815","XinXiaM102"};

    public final static String[] LIFT_MODE = {"default", "BoxLunch", "RowCol", "DoorC", "BoxLunchDoub", "Djs", "refrig", "NCoffee", "hb2", "lft1", "lft2", "lft5", "lft10", "lft30"
            , "lftSwitchStatusDoor"};

    public final static String[] LATTI_MODE = {"default", "refrig"};

    public final static String[] SNAKE_KEY_MODE = {"no_price", "price", "priceRedu1000"};

    public final static String[] EDIT_SOLT_MODE = {"是", "否"};
    public final static String[] MQTT_SERVER_ADDRESS = {"0", "1", "2"};

    public final static String[] LOCK_MACHINE = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};

    public final static String[] IC_CARD_TYPE = {"4字节", "韦根", "鞍山水世界", "USB按键接收", "VID&PID", "ZLG_SP", "ZLG_EC", "ZLG_TCN_IC", "ZLG_TCN_ID", "ZLG_READ"
            , "USB_CHFK", "USB_READ", "ZLG_TCN_ICKEYAMOUNT", "EC_USB_BYACCOUNT", "EC_COM_BYACCOUNT", "EC_USB_VERIFY", "AJBM", "AJBM-WEBSERVICE"};
    public final static String[] PAY_REFUND_ADRESS_SELECT = {"新地址", "原地址"};

    public final static String[] REMOUT_ADVERT_TYPE = {"平台自带", "用于对接", "urad", "ad"};
    public final static String[] LOG_UPLOAD_TYPE = {"不上传", "上传"};

    public final static String[] PAY_ORDER_REPORT_TYPE = {"使用主板订单", "不使用主板订单"};

    public final static String[] SERIPORT_BAUD_RATE = {"2400", "4800", "9600", "19200", "38400", "57600", "115200"};
    public final static String[] ITEM_COUNT_EVERY_PAGE = {"8", "10", "14", "16", "20"};

    public final static String[] SRIPORT_GRP_MAP = {"NONE", "0", "1", "2", "0|1", "1|2", "2|3", "0|1|2", "1|2|3", "2|3|4", "0|1|2|3", "1|2|3|4", "2|3|4|5", "0~100", "0~200", "0~300", "0~400"};

    //蛇形机货道样式
    public final static String[] SNAKE_SLOT_STYLE_TYPE = {"02N-1", "02N-2", "02N-3", "02N-4", "03N-1", "03N-2", "03N-3", "03N-4", "03N-5", "03N-6","03N-7", "04N-1", "04N-2", "04N-3", "04N-4", "04N-5", "04N-6", "04N-7","04N-8"};

    public static final String FOLDER_VIDEO_IMAGE_AD_REMOTE = "/TcnFolder/VideoAndImageRemote";
    public static final String FOLDER_VIDEO_IMAGE_AD_GIVEN = "/TcnFolder/VideoAndImage";

    public static final String FOLDER_VIDEO_IMAGE_AD_HDMIVICE = "/TcnFolder/VideoAndImageAdHDMIVice";
    public static final String FOLDER_VIDEO_IMAGE_AD = "/TcnFolder/VideoAndImageAd";
    public static final String FOLDER_IMAGE_GOODS = "/TcnFolder/ImageGoods";
    public static final String FOLDER_IMAGE_SCREEN = "/TcnFolder/ImageScreen";
    public static final String FOLDER_BURDEN = "/TcnFolder/ImageBurden";
    public static final String FOLDER_TCNPAYAPP = "/TcnPayAPP";

    public static final String FOLDER_IMAGE_BACKGROUND = "/TcnFolder/ImageBackground";
    public static final String FOLDER_IMAGE_HELP = "/TcnFolder/ImageHelp";
    public static final String FOLDER_TEXT = "/TcnFolder/Text";
    public static final String FOLDER_DriveUpgrade = "/TcnFolder";
    public static final String FOLDER_SHOW = "/TcnFolder/Show";
    public static final String FOLDER_IMAGE_QRPAY = "/QrPayImage";
    public static final String FOLDER_ADVERT_IMAGE_RIGHT = "/TcnFolder/ImageRight";
    public static final String FOLDER_ADVERT_IMAGE_PAY = "/TcnFolder/ImagePay";
    public static final String FOLDER_IMAGE_PAY_ICON = "/TcnFolder/payIcon.png";
    public static final String FOLDER_ADVERT_IMAGE_PAYLOGO = "/TcnFolder/ImagePayLogo";
    public static final String FOLDER_ADVERT_IMAGE_GOUWU = "/TcnFolder/ImageGouwu";
    public static final String FOLDER_POLLFile = "/TcnFolder/pollFile";//文件下载缓存池
    public static final String FOLDER_SELF_CHECK = "/TcnFolder/SelfCheck";//文件下载缓存池

    public static final String FOLDER_PAY_ADVERT_IMAGE = "/TcnFolder/ImagePayAdvert";// 当不显示支付二维码时显示的图片

    public static final String FILENAME_ADVERT = "advert.txt";

    public static final String FILENAME_README = "ReadMe.xml";

//    public static final String TCN_CONFIG_FILENAME = "tcnconfig.txt";

    public static final String FOLDER_PAYINFO = "/PayInfo";
    public static final String FILENAME_PAYINFO = "PayInfo.txt";

    public static final String TCN_LOG_NAME = "microlog.txt";

    public static final String TIME = "HH:mm";
    public static final String TIME1 = "HHmm";

    public static final String DATE_FORMAT_YMDHMS = "yyyyMMddHHmmss";
    public static final String DATE_FORMAT_HMS = "HHmmss";
    public static final String YEAR = "yyyy/MM/dd-HH:mm:ss";
    public static final String YEAR_HM = "yyyy-MM-dd  HH:mm";
    public static final String YEAR_M_D = "yyyy/MM/dd";
    public static final String Y_M_D = "yyyy-MM-dd";
    public static final String YMD = "yyyyMMdd";

    public static final String USB_CONFIG_SLOT_FILE = "tcn_product.xlsx";
    public static final String USB_CONFIG_IMAGEGOODS_FILE = "TcnImageGoods";
    public static final String USB_CONFIG_BACK_DESKTOP = "TcnBackDesktop";
    public static final String USB_CONFIG_COPYlOG = "TcnCopyLog";

    public static final String GOODS_TYPE_ALL = "TYPE_ALL";
    public static final String GOODS_TYPE_OTHER = "TYPE_OTHER";

    public final static String[] TCN_ADTYPE = {"U盘拷贝", "后台下发", "轮询广告分成"};
    String[] TCN_DISCOUNT = {"Discount1", "Discount2", "Discount3", "Discount4", "Discount5"};
    /**
     * 主广告位增加一个字段；
     * 0：表示整个替换广告（会将原广告删除，并播放下载广告）
     * 1：表示添加活动广告物料（原来广告存在但不播放）；即只播放广告活动物料图片
     * 2：表示添加活动广告物料（原来广告存在也播放）；即客户广告和机器广告轮流播放
     */

    String scImage = "Screeempty.png";
    String adImage = "adEmpty.png";
    String[] TCN_ADMAINPlaytype = {"0", "1", "2"};

    final String ADVERT_BASE_URL = "http://ad.ourvend.com:8090/";
    final String ADVERT_BASE_URL_YG = "http://www.ygvem.com:8090/";
    final String ADVERT_BASE_URL_VIP = "http://vip.ourvend.com:8090/";

//    String ADVERT_BASE_URL = "http://121.42.32.97:8090/";
//    String ADVERT_BASE_URL = "http://47.104.62.7:8090/";
    //    final String ADVERT_BASE_URL = "http://hsw.cross.echosite.cn";

    public static final String DRIVE_PARAMS_STAND = "/YsFolder/config/drives_params.txt";
    public static final String DRIVE_PARAMS_ACTION = "/YsFolder/config/drives_params_action.txt";
    public static final String DRIVE_PARAMS_WORK_STATUS = "/YsFolder/config/drives_params_status.txt";

    public static final String DRIVE_PARAMS_STAND_TEMP = "/YsFolder/config/drives_temp_params.txt";
    public static final String DRIVE_PARAMS_ACTION_TEMP = "/YsFolder/config/drives_temp_params_action.txt";
    public static final String DRIVE_PARAMS_WORK_STATUS_TEMP = "/YsFolder/config/drives_temp_params_status.txt";

    int carmeNum = 4;
    boolean isTest = false;
    int ERRCODEINT = -99;

    String DISCOUNT_PFX = "Dsct";
    String CUSTOMREBOOTTIME = "CUSTOMREBOOTTIME";

    String TcnPayPackageName = "com.tcn.pay";    //TcnPay  app的包名
    String wxFacePackageName = "com.tencent.wxpayface";    //微信刷脸支付app的报名
}
