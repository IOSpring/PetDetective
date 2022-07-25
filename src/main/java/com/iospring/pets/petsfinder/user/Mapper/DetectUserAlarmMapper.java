package com.iospring.pets.petsfinder.user.Mapper;

import com.iospring.pets.petsfinder.user.dto.DetectUserAlarmDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DetectUserAlarmMapper implements RowMapper<DetectUserAlarmDto> {

    @Override
    public DetectUserAlarmDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        DetectUserAlarmDto detectUserAlarmDto = new DetectUserAlarmDto();
        detectUserAlarmDto.setPhoneNumber(rs.getString("phone_number"));
        detectUserAlarmDto.setDeviceToken(rs.getString("device_token"));
        detectUserAlarmDto.setDistance(rs.getLong("distance"));
        return null;
    }
}
