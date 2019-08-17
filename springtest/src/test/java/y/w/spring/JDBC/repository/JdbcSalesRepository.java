package y.w.spring.JDBC.repository;

import y.w.spring.model.Sales;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JdbcSalesRepository implements SalesRepository
{
    private String SELECT_SALES = "SELECT DEPT_NO, PART_NO, COUNTRY, AMOUNT, ORDER_NO FROM SALES";

    private JdbcOperations jdbcOperations; // Interface implemented by JdbcTemplate

    @Autowired
    public JdbcSalesRepository(JdbcOperations jdbcOperations)
    {
        this.jdbcOperations = jdbcOperations;
    }

    @Override public Sales findById(Long id)
    {
        return jdbcOperations.queryForObject(SELECT_SALES
                + " where ORDER_NO = ?", new SalesRowMapper(), id);
    }

    /**
     * Using lambda.
     *
     * @param id
     * @return
     */
    @Override public Sales findOne(Long id)
    {
        return jdbcOperations.queryForObject(SELECT_SALES + " where ORDER_NO = ?",
                (rs, rowNum) -> new Sales(
                    rs.getLong("ORDER_NO"),
                    rs.getString("DEPT_NO"),
                    rs.getString("COUNTRY"),
                    rs.getString("PART_NO"),
                    rs.getDouble("AMOUNT")
                ),
                id);
    }

    @Override public void delete(Sales sales)
    {

    }

    @Override public Sales save(Sales sales)
    {
        Long id = sales.getId();
        if ( id == null)
        {
            id = insertAndReturnId(sales);
            return new Sales(id, sales.getPartNo(), sales.getDeptNo(), sales.getCountry(), sales.getAmount());
        }
        else
        {
            jdbcOperations.update("UPDATE SALES SET DEPT_NO = ?, PART_NO = ?, COUNTRY = ?, AMOUNT = ? WHERE ORDER_NO = ?",
                    sales.getDeptNo(), sales.getPartNo(), sales.getCountry(), sales.getAmount(), id);
        }
        return sales;
    }

    @Override
    public Long count()
    {
        return jdbcOperations.queryForObject("select cound(*) from Sales", Long.class);
    }

    @Override public List<Sales> findAll()
    {
        return jdbcOperations.query(SELECT_SALES, new SalesRowMapper());
    }

    /**
     * Inserts a Sales using SimpleJdbcInsert. 
     * Involves no direct SQL and is able to return the ID of the newly created sales.
     * @param sales a sales to insert into the databse
     * @return the ID of the newly inserted sales
     */
    private long insertAndReturnId(Sales sales) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert((JdbcTemplate)jdbcOperations).withTableName("SALES");
        jdbcInsert.setGeneratedKeyName("ORDER_NO");
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("DEPT_NO", sales.getDeptNo());
        args.put("COUNTRY", sales.getCountry());
        args.put("PART_NO", sales.getPartNo());
        args.put("AMOUNT", sales.getAmount());
        long orderNo = jdbcInsert.executeAndReturnKey(args).longValue();
        return orderNo;
    }
    
    private static final class SalesRowMapper implements RowMapper<Sales>
    {
        @Override
        public Sales mapRow(ResultSet rs, int rowNum) throws SQLException
        {
            long orderNo = rs.getLong("ORDER_NO");
            String deptNo = rs.getString("DEPT_NO");
            String country = rs.getString("COUNTRY");
            String partNo = rs.getString("PART_NO");
            Double amount = rs.getDouble("AMOUNT");

            return new Sales(orderNo, partNo, deptNo, country, amount);
        }
    }
}
