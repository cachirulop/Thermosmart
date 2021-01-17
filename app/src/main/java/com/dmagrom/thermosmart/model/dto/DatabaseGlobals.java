package com.dmagrom.thermosmart.model.dto;

import com.dmagrom.thermosmart.R;

public class DatabaseGlobals
{
    // Enumerators
    public enum ThermostatStatus
    {
        ON,
        OFF
    }

    public enum ReleStatus
    {
        RELE_ON,
        RELE_OFF
    }

    public enum ThermosmartProgram
    {
        None (0, R.drawable.ic_turn_off),
        Off (1, R.drawable.ic_turn_off),
        Moon (2, R.drawable.ic_moon),
        Sun (3, R.drawable.ic_sun),
        Manual (4, R.drawable.ic_hand),
        ManualMoon (5, R.drawable.ic_manual_moon),
        ManualSun (6, R.drawable.ic_manual_sun),
        Holidays (7, R.drawable.ic_plane);

        private final int intValue;
        private final int imageId;

        ThermosmartProgram (int value, int imageId) {
            intValue = value;
            this.imageId = imageId;
        }

        public int getIntValue ()
        {
            return intValue;
        }

        public int getImageId () {
            return imageId;
        }
    }

    // Database keys
    public static final String KEY_ROOT = "/";

    public static final String KEY_DAILY = KEY_ROOT + "daily";
    public static final String KEY_SCHEDULE = KEY_ROOT + "schedule";
    public static final String KEY_THERMOSTAT = KEY_ROOT + "thermostat";

    public static final String KEY_PROGRAMS   = KEY_SCHEDULE + "/programs";

    public static final String KEY_STATUS   = KEY_THERMOSTAT + "/status";

    public static final String KEY_CURRENT_TEMPERATURE = "currentTemperature";
    public static final String KEY_CURRENT_HUMIDITY = "currentHumidity";
    public static final String KEY_NIGHT_TEMPERATURE = "nightTemperature";
    public static final String KEY_SUN_TEMPERATURE = "sunTemperature";
    public static final String KEY_TARGET_TEMPERATURE = "targetTemperature";

    public static final String KEY_RELAY_STATUS = "relayStatus";
    public static final String KEY_TARGET_TYPE = "targetType";

    public static final String KEY_CURRENT_DAILY_PROGRAM = "program";

}
