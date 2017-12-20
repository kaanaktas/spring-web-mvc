package com.sps.model;

import com.sps.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class JdbcPersonDao implements PersonDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Person> findAll() {
        return jdbcTemplate.query("select id, first_name, last_name from t_person", (rs, rowNum) -> {
            Person person = new Person();
            person.setId(rs.getLong("id"));
            person.setFirstName(rs.getString("first_name"));
            person.setLastName(rs.getString("last_name"));
            return person;
        });
    }

    @Override
    public Person findById(Long id) {
        return jdbcTemplate.queryForObject("select first_name, last_name from t_person where id = ?",
                (rs, rowNum) -> {
                    Person person = new Person();
                    person.setId(id);
                    person.setFirstName(rs.getString("first_name"));
                    person.setLastName(rs.getString("last_name"));
                    return person;
                }, id);
    }

    @Override
    public void create(Person person) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(conn -> {
            PreparedStatement stmt = conn.prepareStatement(
                    "insert into t_person(id,first_name,last_name) values(t_person_sequence.nextval,?,?)");
            stmt.setString(1, person.getFirstName());
            stmt.setString(2, person.getLastName());
            return stmt;
        }, keyHolder);
        person.setId(keyHolder.getKey().longValue());

    }

    @Override
    public void update(Person person) {
        jdbcTemplate.update("update t_person set first_name = ?, last_name = ? where id = ?", person.getFirstName(),
                person.getLastName(), person.getId());
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update("delete from t_person where id = ?", id);
    }
}
