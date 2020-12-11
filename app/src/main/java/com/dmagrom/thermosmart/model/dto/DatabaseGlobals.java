package com.dmagrom.thermosmart.model.dto;

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
        None (0),
        Off (1),
        Moon (2),
        Sun (3),
        Manual (4),
        ManualMoon (5),
        ManualSun (6),
        Holidays (7);

        private final int intValue;

        ThermosmartProgram (int value) {
            intValue = value;
        }

        public int getIntValue ()
        {
            return intValue;
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
