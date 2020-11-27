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

    public enum ThermostatTargetType
    {
        None,
        Manual,
        Sun,
        Night,
        Comfort
    }

    // Database keys
    public static final String KEY_THERMOSTAT = "thermostat";

    public static final String KEY_STATUS = KEY_THERMOSTAT + "/status";

    public static final String KEY_CURRENT_TEMPERATURE = "currentTemperature";
    public static final String KEY_CURRENT_HUMIDITY = "currentHumidity";
    public static final String KEY_NIGHT_TEMPERATURE = "nightTemperature";
    public static final String KEY_SUN_TEMPERATURE = "sunTemperature";
    public static final String KEY_TARGET_TEMPERATURE = "targetTemperature";

    public static final String KEY_RELAY_STATUS = "/relayStatus";
    public static final String KEY_TARGET_TYPE = "targetType";
}
