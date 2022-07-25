package com.iospring.pets.petsfinder.user.Mapper;


import com.iospring.pets.petsfinder.user.dto.FindUserAlarmDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FindUserAlarmMapper implements RowMapper<FindUserAlarmDto> {

    @Override
    public FindUserAlarmDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        FindUserAlarmDto findUserAlarmDto = new FindUserAlarmDto();
        findUserAlarmDto.setPhoneNumber(rs.getString("phone_number"));
        findUserAlarmDto.setDeviceToken(rs.getString("device_token"));
        findUserAlarmDto.setMissingTime(rs.getString("missing_time"));
        findUserAlarmDto.setDistance(rs.getLong("distance"));
        return findUserAlarmDto;
    }
}
