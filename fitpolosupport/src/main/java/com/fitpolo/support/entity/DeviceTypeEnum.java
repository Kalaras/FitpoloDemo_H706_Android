package com.fitpolo.support.entity;

/**
 * @Date 2018/4/12
 * @Author wenzheng.liu
 * @Description Versión
 * @ClassPath com.fitpolo.support.entity.DeviceTypeEnum
 */
public enum DeviceTypeEnum {
    H706("06"),
    H707("06");

    private String lastCode;

    DeviceTypeEnum(String lastCode) {
        this.lastCode = lastCode;
    }

    public String getLastCode() {
        return lastCode;
    }

    public void setLastCode(String lastCode) {
        this.lastCode = lastCode;
    }

    public static DeviceTypeEnum fromOrdinal(int ordinal) {
        for (DeviceTypeEnum deviceTypeEnum : DeviceTypeEnum.values()) {
            if (deviceTypeEnum.ordinal() == ordinal) {
                return deviceTypeEnum;
            }
        }
        return H706;
    }

    public static DeviceTypeEnum fromLastCode(String lastCode) {
        for (DeviceTypeEnum deviceTypeEnum : DeviceTypeEnum.values()) {
            if (deviceTypeEnum.getLastCode().equals(lastCode)) {
                return deviceTypeEnum;
            }
        }
        return H706;
    }
}
