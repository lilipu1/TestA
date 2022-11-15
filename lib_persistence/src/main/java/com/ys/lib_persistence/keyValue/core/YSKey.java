package com.ys.lib_persistence.keyValue.core;

public interface YSKey {
    /**
     * 机器ID 2.0或者3.0
     */
    String V2_MACHINE_ID = "id";
    /**
     * 按商品分类显示
     */
    String ITEM_CATEGORY_ENABLED = "ShowType";
    /**
     * 开启语音提示,true开启,false关闭,默认开启
     */
    String VOICE_PROMPT_ENABLED = "voice";
    /**
     * 全屏显示
     */
    String FULL_SCREEN_ENABLED = "FullScreen";
    /**
     * 掉货检测,true打开,false关闭,默认打开
     */
    String DELIVERY_CHECK_ENABLED = "DropSensor";
    /**
     * 重启时间,默认-1,无设置
     */
    String AUTO_REBOOT_TIME = "RebootTime";
    /**
     * LED灯开启时间,默认-1,无设置
     */
    String LIGHT_ON_TIME = "LightOnStartTime";
    /**
     * LED灯关闭时间,默认-1,无设置
     */
    String LIGHT_OFF_TIME = "LightOnEndTime";
    /**
     * 连续出货失败多少次锁机,默认5次
     */
    String FAULT_LOCKED_ATTEMPTS = "ShipFailCountLock";
    /**
     * 提货码出货
     */
    String DELIVERY_CODE_ENABLED = "AppVerify";

    /**
     * 连续出货失败次数
     */
    String SHIP_FAIL_COUNT = "ShipContinFailCount";
    /**
     * 主板类型
     */
    String YS_BOARD_TYPE = "YsBoardType";
    /**
     * 购物UI类型
     */
    String SHOP_UI_TYPE = "ShopUIType";
    /**
     * LED灯带开关是否打开,1开启,0关闭,其它不存在
     */
    String LED_SWITCH_STATUS = "strDengdai_switch";
    /**
     * 玻璃加热(除雾)开关是否打开,1开启,0关闭,其它不存在
     */
    String DEMIST_SWITCH_STATUS = "$ControlParameter_Demist";
    /**
     * 左边温控模式,0：关闭  1：制冷   2：加热
     */
    String HEAT_COOL_MODE_LEFT = "HeatCoolModeLeft";
    /**
     * 左边设置的温度
     */
    String TEMP_SET_LEFT = "TempSetLeft";
    /**
     * 右边温控模式,0：关闭  1：制冷   2：加热
     */
    String HEAT_COOL_MODE_RIGHT = "HeatCoolModeRight";
    /**
     * 右边设置的温度
     */
    String TEMP_SET_RIGHT = "TempSetRight";
    /**
     * 温控模式,0：关闭  1：制冷   2：加热,默认0
     */
    String HEAT_COOL_MODE = "HeatCoolMode";
    /**
     * 温控启动时间
     */
    String TEMP_CONTROL_START_TIME = "TempControlStartTime";
    /**
     * 温控关闭时间
     */
    String TEMP_CONTROL_END_TIME = "TempControlEndTime";
    /**
     * 温控设置的温度
     */
    String TEMP_CONTROL_TEMP = "TempControlTemp";
    /**
     * 当前使用的IP
     */
    String CURRENT_IP = "UseCustomerIP";
    /**
     * 当前温度
     */
    String CURRENT_TEMP = "CURRENT_TEMP";
    /**
     * 是否是银联商务,需要在选货的时候生成动态码
     * */
    String IS_CHINAUMS = "UnionQRCode";
    /**
     * 温度校准差值,用来校准温度
     * */
    String TEMPERATURE_ADJUST_VALUE = "TEMPERATURE_ADJUST_VALUE";
    /**
     * 是否开启广告功能,默认开启
     * */
    String IS_AD_ENABLE = "IS_AD_ENABLE";

    /**
     * 广告服务器IP
     * */
    String AD_SERVER_IP = "AD_SERVER_IP";

    /**
     * 是否开启皮肤插件,true开启,false关闭,默认关闭
     * */
    String IS_SKIN_APP_OPEN = "skinAppIsOpen";
    /**
     * 是否开启看门狗,true开启,false关闭,默认关闭
     * */
    String IS_WATCH_DOG_OPEN = "WatchDogOpen";
    /**
     * 超温锁机模式,默认0关闭
     * */
    String TEMP_LOCK_TYPE = "CheckTempLock";

    /**
     * 开始运营的时间,默认null
     * */
    String SERVICE_TIME_START = "SERVICE_TIME_START";
    /**
     * 结束运营的时间,默认Null
     * */
    String SERVICE_TIME_END = "SERVICE_TIME_END";
    /**
     * 是否是节能模式,默认false非节能
     * */
    String IS_ENERGY_SAVED = "IS_ENERGY_SAVED";
    /**
     * 结束运营制冷是否打开,默认true打开
     * */
    String IS_CONDITIONER_ON_WHEN_CLOSED = "IS_CONDITIONER_ON_WHEN_CLOSED";
    /**
     * 扫码进入后台跳转的URL
     * */
    String BACKGROUND_URL = "BACKGROUND_URL";
    /*********************************蛇形机配置开始*************************************************/
    /**
     * 蛇形机左边最大货柜号
     */
    String ROOM_LEFT_MAX_SLOT_NO = "RoomLeftMaxSlotNo";
    /*********************************蛇形机配置结束*************************************************/

    /*********************************迁移标记开始**************************************************/
    /**
     * 数据是否从tray迁移到了MMKV
     */
    String IS_MIGRATE = "IS_MIGRATE";
    /**
     * 数据是否从TCN_CONFIG文件迁移到了MMKV
     */
    String IS_TCN_CONFIG_MIGRATE = "IS_TCN_CONFIG_MIGRATE";
    /**
     * 数据是否从TCN_CONFIG_FILE_CMX_LED文件迁移到了MMKV
     */
    String IS_CMX_CONFIG_MIGRATE = "IS_CMX_CONFIG_MIGRATE";
    /*********************************迁移标记结束**************************************************/

    /*********************************寻址机配置开始*************************************************/
    /**
    * X轴步数比例,默认0
    * */
    String CMX_xDistance = "CMX_xDistance";
    /**
     * Y轴步数比例,默认0
     * */
    String CMX_yDistance = "CMX_yDistance";

    /*********************************寻址机配置结束*************************************************/


    /*********************************V4键值对开始**************************************************/
    /**
     * 4.0平台机器ID
     * */
    String V4_MACHINE_ID = "V4_MACHINE_ID";
    /**
     * 通信程序秘钥
     * */
    String V4_KEY = "V4_KEY";
    /**
    * 通信程序的MQTT实例
    * */
    String V4_MQTT_INSTANCE = "V4_MQTT_INSTANCE";  //MQTT实例
    /**
     * 通信程序升级命令中的Extra
     * */
    String V4_UPGRADE_EXTRA = "UPGRADE_EXTRA";  //升级命令中的Extra
    /**
     * 通信程序升级之前的版本号
     * */
    String V4_CURRENT_VERSION = "CURRENT_VERSION";
    /**
     * 校时成功时服务器与客户端之间的时间差
     * */
    String V4_TIME_OFFSET = "TIME_OFFSET";
    /**
     * 4.0平台上一次校时成功的客户端时间
     * */
    String V4_LAST_CLIENT_TIME = "LAST_CLIENT_TIME";
    /**
     * 4.0平台注册码
     * */
    String V4_REGISTER_SECRET = "V4_REGISTER_SECRET";
    /**
     *4.0平台心跳时间戳,每分钟更新
     */
    String V4_HEARTBEAT_TIME = "V4_HEARTBEAT_TIME";
    /**
     * 通信程序是不是第一次启动界面,第一次启动要关闭一次,防止某些工控升级以后一直停在通信程序页面,默认true
     */
    String V4_IS_FIRST_START_ACTIVITY = "V4_IS_FIRST_START_ACTIVITY";
    /**
     * 通信程序是否是第一次启动,默认true
     */
    String V4_IS_FIRST_START = "V4_IS_FIRST_START";
    /**
     * 4.0平台服务器类型,0正式环境,1测试环境,2开发环境
     */
    String V4_SERVER_TYPE = "V4_SERVER_TYPE";
    /**
     * 是否需要设置默认商品,true需要,false不需要,默认true
     */
    String V4_IS_NEED_SET_DEFAULT_COMMODITY = "V4_IS_NEED_SET_DEFAULT_COMMODITY";

    /*********************************V4键值对结束**************************************************/


    /********** 程序信息 start *******************/
    /**
     * 程序保活方式的key的后缀  保活类型 0:不用保活 1:保活服务但是不包括前台运行界面 2:保活包括前台运行界面 3:不用保活并且安装之后杀死应用
     */
    String SUFFIX_KEEP_ALIVE_TYPE = "_KpAliveType";
    /**
     * 应用的Service类全路径名称
     */
    String SUFFIX_PACKAGE_SERVICE = "_Service";
    /**
     * 工控最后重启时间
     */
    String KEY_LAST_REBOOT_TIME = "LastRebootTime";
    /**
     * 最后一次上报配置的时间
     */
    String KEY_LAST_REQ_MACHINE_CONFIG_TIME = "LastReqMachineConfigTime";
    /**
     * 解锁或者锁住  不解锁就会删除没有授权的应用
     */
    String KEY_LOCK_FLAG_NUMBER = "LockFlagNumber";
    /**
     * 应用程序编号
     */
    String SUFFIX_PACKAGE_PROGRAM_ID = "_ProgramId";
    /**
     * 应用的程序名称
     */
    String SUFFIX_PACKAGE_PROGRAM_NAME = "_Name";
    /**
     * 应用的程序编号
     */
    String SUFFIX_PACKAGE_PROGRAM_FLAG = "_Flag";
    /**
     * 应用的程序备注说明
     */
    String SUFFIX_PACKAGE_PROGRAM_REMARK = "_Remark";

    /********** 程序信息 end *******************/

    /********** 机器信息 start  *******************/
    /**
     * 机器纬度
     * */
    String LATITUDE = "LATITUDE";
    /**
     * 机器经度
     * */
    String LONGITUDE = "LONGITUDE";
    /**
     * 机器地址
     * */
    String ADDRESS = "机器地址";

    /********** 机器信息 end  *******************/

}
