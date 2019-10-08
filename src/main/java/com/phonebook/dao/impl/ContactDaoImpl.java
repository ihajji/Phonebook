package com.phonebook.dao.impl;

import com.phonebook.dao.contract.Dao;
import com.phonebook.model.bean.Contact;
import com.phonebook.dao.AbstractDao;
import com.phonebook.model.exception.NotFoundException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Contact dao implementation.
 */
@Component("ContactDao")
public class ContactDaoImpl extends AbstractDao implements Dao<Contact> {
    private static final String CONTACT_TABLE = "Contact";
    private static final String ID_COL = "contactId";
    private static final String FIRSTNAME_COL = "firstname";
    private static final String LASTNAME_COL = "lastname";
    private static final String PHONENUMBER_COL = "phoneNumber";


    @Override
    public int save(Contact contact) throws NotFoundException{
        Number key;

        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(getJdbcTemplate());
        jdbcInsert.withTableName(CONTACT_TABLE).usingGeneratedKeyColumns(ID_COL);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(FIRSTNAME_COL, contact.getFirstname());
        parameters.put(LASTNAME_COL, contact.getLastname());
        parameters.put(PHONENUMBER_COL, contact.getPhoneNumber());

        try
        {
            key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        }
        catch (DataAccessException e)
        {
            throw new NotFoundException(e.getMessage());
        }

        return key.intValue();
    }

    @Override
    public Contact get(int id) throws NotFoundException{
        String sqlRequest = "SELECT * FROM " + CONTACT_TABLE + " WHERE " + ID_COL + " = ?";

        try
        {
            return getJdbcTemplate().queryForObject(sqlRequest, new Object[]{id}, contactRowMapper());
        }
        catch (DataAccessException e)
        {
            throw new NotFoundException(e.getMessage());
        }
    }

    @Override
    public Collection<Contact> getAll() throws NotFoundException{
        String sqlRequest = "SELECT * FROM " + CONTACT_TABLE;

        try
        {
            return getJdbcTemplate().query(sqlRequest, contactRowMapper());
        }
        catch (DataAccessException e)
        {
            throw new NotFoundException(e.getMessage());
        }
    }


    /**
     * Update a contact in databse
     * @param contact Contact to update with updated information
     * @return Return 1 for success or 0
     */
    @Override
    public int update(Contact contact) throws NotFoundException{
        String sqlRequest = "" +
                "UPDATE "+ CONTACT_TABLE +
                " SET "+FIRSTNAME_COL+" = :"+FIRSTNAME_COL+", " +
                "" + LASTNAME_COL + " = :" + LASTNAME_COL + ", " +
                "" +PHONENUMBER_COL + " = :" + PHONENUMBER_COL + " " +
                "WHERE " + ID_COL + " = :"+ID_COL;

        SqlParameterSource vParams = new BeanPropertySqlParameterSource(contact);

        try
        {
            return getNamedParameterJdbcTemplate().update(sqlRequest, vParams);
        }
        catch (DataAccessException e)
        {
            throw new NotFoundException(e.getMessage());
        }
    }

    /**
     * Delete a contact from database
     * @param id Identifier of the contact to delete
     * @return Return 1 for success or 0
     */
    @Override
    public int delete(Integer id) throws NotFoundException{
        String sqlRequest = "DELETE FROM " + CONTACT_TABLE + " WHERE " + ID_COL + " = ?";

        try
        {
            return getJdbcTemplate().update(sqlRequest, id);
        }
        catch (DataAccessException e)
        {
            throw new NotFoundException(e.getMessage());
        }
    }

    private RowMapper<Contact> contactRowMapper(){
         return (pRS, pRowNum) -> {
            Contact contact = new Contact();
            contact.setContactId(pRS.getInt(ID_COL));
            contact.setFirstname(pRS.getString(FIRSTNAME_COL));
            contact.setLastname(pRS.getString(LASTNAME_COL));
            contact.setPhoneNumber(pRS.getString(PHONENUMBER_COL));
            return contact;
        };
    }
}
