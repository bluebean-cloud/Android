package com.example.success.entity;

import org.greenrobot.greendao.converter.PropertyConverter;
import java.sql.Time;

public class TimeConverter implements PropertyConverter<Time, Long> {

    @Override
    public Time convertToEntityProperty(Long databaseValue) {
        // 将数据库存储的 long 类型转换为 java.sql.Time 对象
        return new Time(databaseValue);
    }

    @Override
    public Long convertToDatabaseValue(Time entityProperty) {
        // 将 java.sql.Time 对象转换为 long 类型进行存储
        return entityProperty.getTime();
    }
}
