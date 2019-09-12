package com.career.genius.utils.jdbcframework;

/*import com.resico.dto.sys.UserQueryResultDto;
import com.resico.exception.ResicoException;
import com.resico.service.sys.UserQueryService;*/

import com.usm.utils.ObjectHelper;

import java.util.List;

public class QuerySqlUtils {

    /**
     * 新的构造 Sql语句
     *
     * @param fieldNames
     * @param resouceUrl
     * @param userQueryService
     * @param userId
     * @return String
     * @throws Exception
     */
    /*public static String genUserAuthSql(String[] fieldNames, String resouceUrl,
                                        UserQueryService userQueryService, String userId){
        if (ObjectHelper.isEmpty(fieldNames) || ObjectHelper.isEmpty(resouceUrl)
                || ObjectHelper.isEmpty(userId)) {
            throw new ResicoException("genUserAuthSql:有参数为空");
        }
        StringBuilder sb = new StringBuilder();
        // 获取当前登录用户
        UserQueryResultDto uqr = null;
        try {
            uqr = userQueryService.findUserQueryResult(userId, resouceUrl);
        } catch (exception e) {
            e.printStackTrace();
            uqr= new UserQueryResultDto();
            uqr.setIds(userId);
        }
        if (uqr == null) {
            return "";
        }else {
            if(ObjectHelper.isEmpty(uqr.getIds())){
                uqr.setIds(userId);
            }
            String[] ids = uqr.getIds().split(",");
            int idLen = ids.length;
            int fieldLen = fieldNames.length;
            sb.append(" and (");
            for (int j = 0; j < fieldLen; j++) {
                if (j > 0) {
                    sb.append(" or ");
                }
                sb.append(fieldNames[j]);
                sb.append(" in (");
                for (int i = 0; i < idLen; i++) {
                    if (i > 0) {
                        sb.append(",");
                    }
                    sb.append("'");
                    sb.append(ids[i]);
                    sb.append("'");
                }
                sb.append(") ");
            }
            sb.append(") ");
        }
        return sb.toString();
    }*/

    /**
     * 拼接Like的Sql
     *
     * @param sql
     * @param filedName
     * @param filed
     */
    public static void stitchingSqlForLike(StringBuffer sql, Object filedName, String filed) {
        if (ObjectHelper.isNotEmpty(filedName)) {
            sql.append(" AND ");
            sql.append(filed);
            sql.append(" like '%");
            sql.append(filedName);
            sql.append("%'");
        }
    }

    /**
     * 拼接=的Sql
     *
     * @param sql
     * @param filedName
     * @param filed
     */
    public static void stitchingSqlForEquals(StringBuffer sql, Object filedName, String filed) {
        if (ObjectHelper.isNotEmpty(filedName)) {
            sql.append(" AND ");
            sql.append(filed);
            sql.append(" = ");
            if (filedName instanceof String) {
                sql.append(" '");
                sql.append(filedName);
                sql.append("'");
            }
            else {
                sql.append(filedName);
            }
        }
    }

    /**
     * 拼接多重模糊查询的Sql
     *
     * @param sql
     * @param filedName
     * @param fileds
     */
    public static void stitchingSqlForMultiple(StringBuffer sql, Object filedName, String... fileds) {
        if (ObjectHelper.isNotEmpty(filedName)) {
            sql.append(" AND (");
            for (int i = 0, len = fileds.length; i < len; i++) {
                if (i > 0) sql.append(" or ");

                sql.append(fileds[i]);
                sql.append(" like '%");
                sql.append(filedName);
                sql.append("%'");
            }
            sql.append(" )");
        }
    }

    /**
     * 转为sql中in的拼接ids使用
     *
     * @param orderIds
     * @param separate
     * @return
     */
    public static String listStrSqlByIn(List<String> orderIds, String separate) {
        StringBuffer sb = new StringBuffer();
        for (String s : orderIds) {
            if (ObjectHelper.isEmpty(sb)) {
                sb.append("(");
                sb.append("'").append(s).append("'");
            }
            else {
                sb.append(separate).append("'").append(s).append("'");
            }
        }
        if (ObjectHelper.isNotEmpty(sb)) {
            sb.append(")");
        }
        return sb.toString();
    }

    /**
     * 转为sql中in的拼接ids使用
     *
     * @param ids
     * @param separate
     * @return
     */
    public static String listIntSqlByIn(int[] ids, String separate) {
        StringBuffer sb = new StringBuffer();
        for (int s : ids) {
            if (ObjectHelper.isEmpty(sb)) {
                sb.append("(");
                sb.append(s);
            }
            else {
                sb.append(separate).append(s);
            }
        }
        if (ObjectHelper.isNotEmpty(sb)) {
            sb.append(")");
        }
        return sb.toString();
    }
    /**
     * 转为sql中in的拼接ids使用 默认逗号
     *
     * @param orderIds
     * @return
     */
    public static String listStrSqlByInAndComma(List<String> orderIds) {
        return listStrSqlByIn(orderIds,",");
    }

    /**
     * @Author Marker
     * @Date
     * @Discription 拼接SQL in语句<字符串>
     **/
    public static StringBuffer sqlStringAppend( StringBuffer sql, String param) {
        String[] array = param.split(",");
        for (int i = 0; i < array.length; i++) {
            if (i == array.length - 1) {
                sql.append("'" + array[i] + "'");
            } else {
                sql.append("'" + array[i] + "'" + ",");
            }
        }
        sql.append(")");

        return sql;
    }

    /**
     * @Author Marker
     * @Date
     * @Discription 拼接SQL in语句<数字>
     **/
    public static StringBuffer sqlIntegerAppend(StringBuffer sql,String param) {
        String[] array = param.split(",");
        for (int i = 0; i < array.length; i++) {
            if (i == array.length - 1 ) {
                sql.append(Integer.valueOf(array[i]));
            } else {
                sql.append(array[i] + ",");
            }
        }
        sql.append(")");
        return sql;
    }

    /**
     * @Author Marker
     * @Date
     * @Discription 拼接SQL in语句<数字>
     **/
    public static StringBuffer sqlIntegerAppend(StringBuffer sql,Integer[] param) {
        for (int i = 0; i < param.length; i++) {
            if (i == param.length - 1 ) {
                sql.append(Integer.valueOf(param[i]));
            } else {
                sql.append(param[i] + ",");
            }
        }
        sql.append(")");
        return sql;
    }

    /**
     * sql排序拼接
     * @param sortBy
     * @param sortKeyWord
     * @param sql
     * @return
     */
    public static StringBuffer sqlSortAppend(int sortBy,String sortKeyWord,StringBuffer sql) {

        sql.append(" ORDER BY " + sortKeyWord);
        if (sortBy == 0) {  //升序
            sql.append(" ASC ");
        }else {             //降序
            sql.append(" DESC ");
        }
        return sql;
    }

    /**
     * 功能描述: 获取分页查询起始页
     * @auther: luoqw
     * @date: 2018/6/8 10:11
     */
    public static int startPage(int page,int rows){
        return (page-1) * rows;
    }

    /**
     * 功能描述: 获取分页查询结束
     * @auther: luoqw
     * @date: 2018/6/8 10:11
     */
    public static int endPage(int page,int rows){
        return page * rows;
    }
    
}
