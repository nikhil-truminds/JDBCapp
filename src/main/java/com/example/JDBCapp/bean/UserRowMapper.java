package com.example.JDBCapp.bean;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper {

    @Override
    public Object mapRow(ResultSet resultSet, int i) throws SQLException {

        User user = new User();

        user.setID(resultSet.getInt("Id"));
        user.setName(resultSet.getString("Name"));
        user.setSurname(resultSet.getString("Surname"));
        user.setCity(resultSet.getString("City"));
        user.setCountry(resultSet.getString("Country"));
        user.setPhoneNo(resultSet.getString("PhoneNo"));
        user.setEmail(resultSet.getString("Email"));

        return user;
    }
}
