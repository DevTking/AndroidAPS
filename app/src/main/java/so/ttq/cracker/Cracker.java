package so.ttq.cracker;

import java.util.Calendar;

import info.nightscout.androidaps.MainApp;
import info.nightscout.androidaps.plugins.constraints.objectives.ObjectivesPlugin;
import info.nightscout.androidaps.plugins.constraints.objectives.objectives.Objective;
import info.nightscout.androidaps.plugins.constraints.objectives.objectives.Objective2;
import info.nightscout.androidaps.plugins.insulin.InsulinOrefBasePlugin;
import info.nightscout.androidaps.utils.HardLimits;

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
     * 是否破解
     */
    static final boolean IS_CRACK = true;

    static int MANUAL_ENACTS_NEEDED = 0;

    public static void crack_App() {
        if (IS_CRACK) {
            InsulinOrefBasePlugin.MIN_DIA = 0;
            MANUAL_ENACTS_NEEDED = new Objective2().MANUAL_ENACTS_NEEDED;
            crack_BgTarget();
        }
    }

    /**
     * 破解ObjectivesPlugin static
     *
     * @param objectivesPlugin
     */
    public static void crack_ObjectivesPlugin(ObjectivesPlugin objectivesPlugin) {
        if (IS_CRACK) {
            objectivesPlugin.manualEnacts = 20;
        }
    }

    /**
     * @param objective 破解目标
     */
    public static void crack_Objective(Objective objective) {
        if (IS_CRACK) {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(System.currentTimeMillis() - (1000l * 60l * 60l * 24l * 365l));
            objective.setStartedOn(cal.getTime());
        }
    }

    /**
     * 修改环境，true开发版本，false非开发版
     */
    public static void crack_devBranch() {
        if (IS_CRACK) {
            MainApp.devBranch = false;
        }
    }

    /**
     * 修改最大目标
     */
    public static void crack_BgTarget() {
        HardLimits.VERY_HARD_LIMIT_MAX_BG[0] = 78;
    }

    public static String toAndroidAPSNotes() {
        return "。";
    }
}
