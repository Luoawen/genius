package com.career.genius.utils.jdbcframework;


/*import com.resico.crm.utils.common.PageQuery;
import com.resico.crm.utils.common.QuerySqlUtils;*/
import com.career.genius.utils.common.PageQuery;
import com.usm.enums.CodeEnum;
import com.usm.vo.PageDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Auther: luoqw
 * @Date: 2018/6/11 16:55
 * @Description:
 */
@Component
@Slf4j
public class SupportJdbcTemplate {

    @Resource
    JdbcTemplate jdbcTemplate;

    public SupportJdbcTemplate() {
    }

    public <T> T queryForDto(String sql, final Class<T> beanType) {
        return this.jdbcTemplate().query(sql, rs -> rs.next() ? (new BeanProcessor()).toBean(rs, beanType) : null);
    }

    public JdbcOperations jdbcTemplate() {
        return this.jdbcTemplate;
    }

    public <T> T queryForDto(String sql, final Class<T> beanType, Object... args) {
        return this.jdbcTemplate().query(sql, args, rs -> rs.next() ? (new BeanProcessor()).toBean(rs, beanType) : null);
    }

    public <T> T queryForDto(String sql, final Class<T> beanType, Object[] args, int[] argTypes) {
        return this.jdbcTemplate().query(sql, args, argTypes, rs -> rs.next() ? (new BeanProcessor()).toBean(rs, beanType) : null);
    }

    public <T> List<T> queryForList(String sql, final Class<T> beanType) {
        return this.jdbcTemplate().query(sql, (rs, rowNum) -> (new BeanProcessor()).toBean(rs, beanType));
    }

    public <T> List<T> queryForList(String sql, final Class<T> beanType, Object... args) {
        return this.jdbcTemplate().query(sql, args, (rs, rowNum) -> (new BeanProcessor()).toBean(rs, beanType));
    }

    public <T> List<T> queryForList(String sql, final Class<T> beanType, Object[] args, int[] argTypes) {
        return this.jdbcTemplate().query(sql, args, argTypes, (rs, rowNum) -> (new BeanProcessor()).toBean(rs, beanType));
    }

    public <T> PageDto<T> queryForPage(StringBuffer sql, final Class<T> beanType, List param, PageQuery queryParam) {
        PageDto<T> result = new PageDto<>();
        Integer total = countAll(sql.toString(), param);
        result.setTotal(total);
        sql.append(" LIMIT ?,? ");
        // limit 起始数
        param.add(QuerySqlUtils.startPage(queryParam.getPage(), queryParam.getLimit()));
        // 一次查询条数
        param.add(queryParam.getLimit());
        List<T> query = this.jdbcTemplate().query(sql.toString(), param.toArray(), (rs, rowNum) -> (new BeanProcessor()).toBean(rs, beanType));
        result.setData(query);
        result.setPageSize(queryParam.getLimit());
        result.setCurrentpage(queryParam.getPage());
        int temp = (total + queryParam.getLimit() - 1) / queryParam.getLimit();
        result.setTotalPage(temp <= 0 ? 1 : temp);
        result.setCode(CodeEnum.Success.getCode());
        result.setMsg("成功");
        return result;
    }

    /**
     * @Author Marker
     * @Date 查询分页总数
     * @Discription
     **/
    private Integer countAll(String sql,List param) {

        int index = getFirstMatchingIndex(sql, "FROM");

        String replacedString = sql.substring(sql.indexOf("SELECT") + 6, index);
        String newSql = sql.replace(replacedString, " COUNT(1) ");
        Integer total = this.jdbcTemplate.queryForObject(newSql.substring(0,newSql.indexOf("ORDER BY")), Integer.class,param.toArray());
        return total;
    }

    public static int getFirstMatchingIndex(String input, String query) {
        char[] inputChars = input.toCharArray();
        char[] queryChars = query.toCharArray();
        int inputLength = input.length();
        int queryLength = query.length();

        int inputIndex = 0;
        int queryIndex = 0;
        while (inputIndex < inputLength && queryIndex < queryLength) {
            if (inputChars[inputIndex] == queryChars[queryIndex]) {
                queryIndex++;
                inputIndex++;
            }
            else {
                inputIndex = inputIndex - queryIndex + 1;
                queryIndex = 0;
            }
        }
        int index = queryIndex == queryLength ? (queryLength > 1 ? inputIndex - queryLength : inputIndex - 1) : -1;
        return index;
    }

}
