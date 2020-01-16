package com.example.JDBCapp.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import com.example.JDBCapp.bean.User;
import com.example.JDBCapp.bean.UserRowMapper;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

@Repository
public class UserRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<User> getUser()
    { return jdbcTemplate.query("SELECT Id, Name, Surname, City, Country, PhoneNo, Email FROM user_table", new UserRowMapper()); }

    public User findById(Integer id)
    {
        String query = "SELECT * FROM user_table WHERE Id = ?";

        try
        { return (User) this.jdbcTemplate.queryForObject(query, new Object[] { id }, new UserRowMapper()); }

        catch(EmptyResultDataAccessException ex)
        { return null; }
    }

    public Boolean saveUser(User user)
    {
        String query = "INSERT INTO user_table VALUES(?, ?, ?, ?, ?, ?, ?)";

        return jdbcTemplate.execute(query, new PreparedStatementCallback<Boolean>() {

            @Override
            public Boolean doInPreparedStatement(PreparedStatement preparedStatement) throws SQLException, DataAccessException {

                preparedStatement.setInt(1, user.getID());
                preparedStatement.setString(2, user.getName());
                preparedStatement.setString(3, user.getSurname());
                preparedStatement.setString(4, user.getCity());
                preparedStatement.setString(5, user.getCountry());
                preparedStatement.setString(6, user.getPhoneNo());
                preparedStatement.setString(7, user.getEmail());

                return preparedStatement.execute();
            }
        });
    }

    public Integer deleteUserById(Integer id)
    { return jdbcTemplate.update("DELETE FROM user_table WHERE Id = ?", id); }


    public Integer updateUser(User user)
    {
        String query = "UPDATE user_table SET Name = ?, Surname = ?, City = ?, Country = ?, PhoneNo = ?, Email = ? WHERE Id = ?";

        Object[] parameters = { user.getName(), user.getSurname(), user.getCity(), user.getCountry(), user.getPhoneNo(), user.getEmail(), user.getID() };
        int[] types = {Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.INTEGER };

        return jdbcTemplate.update(query, parameters, types);
    }

}
