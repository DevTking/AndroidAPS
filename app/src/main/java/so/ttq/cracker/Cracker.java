package so.ttq.cracker;

import android.content.Intent;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import info.nightscout.androidaps.MainApp;
import info.nightscout.androidaps.R;
import info.nightscout.androidaps.interfaces.PluginType;
import info.nightscout.androidaps.plugins.constraints.objectives.ObjectivesPlugin;
import info.nightscout.androidaps.plugins.constraints.objectives.objectives.Objective;
import info.nightscout.androidaps.plugins.constraints.objectives.objectives.Objective2;
import info.nightscout.androidaps.plugins.constraints.objectives.objectives.Objective3;
import info.nightscout.androidaps.plugins.general.actions.ActionsPlugin;
import info.nightscout.androidaps.plugins.insulin.InsulinOrefBasePlugin;
import info.nightscout.androidaps.utils.HardLimits;
import info.nightscout.androidaps.utils.SP;
import info.nightscout.androidaps.utils.SntpClient;

/**
 * Created by [dev.tking] on 2019/1/24.
 * Copyright (cr) 2018 [dev.tking@outlook.com] All Rights Reserved.
 * -----------------------------------------------------
 * |                       _oo0oo_                     |
 * |                      o8888888o                    |
 * |                      88" . "88                    |
 * |                      (| -_- |)                    |
 * |                      0\  =  /0                    |
 * |                    ___/`---'\___                  |
 * |                  .' \\|     |# '.                 |
 * |                 / \\|||  :  |||# \                |
 * |                / _||||| -:- |||||- \              |
 * |               |   | \\\  -  #/ |   |              |
 * |               | \_|  ''\---/''  |_/ |             |
 * |               \  .-\__  '-'  ___/-. /             |
 * |             ___'. .'  /--.--\  `. .'___           |
 * |          ."" '<  `.___\_<|>_/___.' >' "".         |
 * |         | | :  `- \`.;`\ _ /`;.`/ - ` : | |       |
 * |         \  \ `_.   \_ __\ /__ _/   .-` /  /       |
 * |     =====`-.____`.___ \_____/___.-`___.-'=====    |
 * |                       `=---='                     |
 * |     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~   |
 * |                                                   |
 * |          Buddha bless          never BUG          |
 * -----------------------------------------------------
 */
public class Cracker {
    /**
     *
     */
    public static final boolean OBJECTIVE_COMPLETED_TRUE = true;
    /**
     * 目标时间
     */
    public static final long MINIMUM_DURATION = 0;
    /**
     * 是否破解
     */
    static final boolean IS_CRACK = true;

    public static void crack_App() {
        if (IS_CRACK) {
            InsulinOrefBasePlugin.MIN_DIA = 0;
            HardLimits.VERY_HARD_LIMIT_MAX_BG[0] = 78;

            if (SP.getInt(R.string.key_ObjectivesmanualEnacts, 0) < 20) {
                SP.putInt(R.string.key_ObjectivesmanualEnacts, 21);
            }
            if (!SP.getBoolean(R.string.key_objectiveuseprofileswitch, false)) {
                SP.putBoolean(R.string.key_objectiveuseprofileswitch, Cracker.OBJECTIVE_COMPLETED_TRUE);
            }
            if (!SP.getBoolean(R.string.key_objectiveusedisconnect, false)) {
                SP.putBoolean(R.string.key_objectiveusedisconnect, Cracker.OBJECTIVE_COMPLETED_TRUE);
            }
            if (!SP.getBoolean(R.string.key_objectiveusereconnect, false)) {
                SP.putBoolean(R.string.key_objectiveusereconnect, Cracker.OBJECTIVE_COMPLETED_TRUE);
            }
            if (!SP.getBoolean(R.string.key_objectiveusetemptarget, false)) {
                SP.putBoolean(R.string.key_objectiveusetemptarget, Cracker.OBJECTIVE_COMPLETED_TRUE);
            }
            if (!SP.getBoolean(R.string.key_objectiveuseactions, false)) {
                SP.putBoolean(R.string.key_objectiveuseactions, Cracker.OBJECTIVE_COMPLETED_TRUE);
            }
            if (!SP.getBoolean(R.string.key_objectiveuseloop, false)) {
                SP.putBoolean(R.string.key_objectiveuseloop, Cracker.OBJECTIVE_COMPLETED_TRUE);
            }
            if (!SP.getBoolean(R.string.key_objectiveusescale, false)) {
                SP.putBoolean(R.string.key_objectiveusescale, Cracker.OBJECTIVE_COMPLETED_TRUE);
            }
            //修復日期
            long targetTime = System.currentTimeMillis() + 360L * 24L * 60L * 60L * 1000L;
            SP.putLong(R.string.key_last_versionchecker_plugin_warning, targetTime);
            SP.putLong(R.string.key_last_time_this_version_detected, targetTime);
        }
    }

    /**
     * 修改环境，true开发版本，false非开发版
     */
    public static void crack_devBranch() {
        if (IS_CRACK) {
//            MainApp.devBranch = false;
        }
    }

    public static void crack_Sntp_Callback(SntpClient.Callback callback) {
        callback.success = true;
    }
}
