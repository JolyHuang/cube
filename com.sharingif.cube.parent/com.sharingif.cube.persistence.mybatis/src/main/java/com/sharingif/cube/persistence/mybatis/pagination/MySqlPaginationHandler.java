package com.sharingif.cube.persistence.mybatis.pagination;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sharingif.cube.core.exception.validation.ValidationCubeException;
import com.sharingif.cube.persistence.database.pagination.IPaginationHandler;
import com.sharingif.cube.persistence.database.pagination.RowBounds;

/**
 *
 * @Description:  [MySql分页处理器]
 * @Author:       [Joly_Huang]
 * @CreateDate:   [2014年5月6日 下午11:57:59]
 * @UpdateUser:   [Joly_Huang]
 * @UpdateDate:   [2014年5月6日 下午11:57:59]
 * @UpdateRemark: [说明本次修改内容]
 * @Version:      [v1.0]
 *
 */

public class MySqlPaginationHandler implements IPaginationHandler {

	@Override
	public RowBounds handleRowBounds(int currentPage, int pageSize) {
		RowBounds rowBounds = new RowBounds();
		rowBounds.setOffset(((currentPage-1)*pageSize));
		rowBounds.setLimit(pageSize);
		
		return rowBounds;
	}
	
	@Override
	public String createCountSql(String sql) {
		return MySqlCountHepler.getCountSql(sql);
	}

	@Override
	public String createPaginationSql(String sql, RowBounds rowBounds) {
		StringBuilder paginationSql = new StringBuilder();
		paginationSql.append("SELECT A.* FROM ( ");
		paginationSql.append(sql);
		paginationSql.append(") AS A LIMIT ");
		paginationSql.append(rowBounds.getOffset());
		paginationSql.append(",");
		paginationSql.append(rowBounds.getLimit());
		
		return paginationSql.toString();
	}
	
	protected static class MySqlCountHepler {  
		  
	    /** 
	     * 获取查询总数对应的SQL 
	     * @param querySelect 
	     * @return 
	     */  
	    public static String getCountSql(String querySelect) {  
	  
	        querySelect = compress(querySelect);  
	        int orderIndex = getLastOrderInsertPoint(querySelect);  
	  
	        int formIndex = getAfterFormInsertPoint(querySelect);  
	        String select = querySelect.substring(0, formIndex);  
	  
	        //如果SELECT 中包含 DISTINCT 只能在外层包含COUNT  
	        if (select.toLowerCase().indexOf("select distinct") != -1 || querySelect.toLowerCase().indexOf("group by") != -1) {  
	            return new StringBuffer(querySelect.length()).append(  
	                    "select count(0) from (").append(  
	                    querySelect.substring(0, orderIndex)).append(" ) t")  
	                    .toString();  
	        } else {  
	            return new StringBuffer(querySelect.length()).append(  
	                    "select count(0) ").append(  
	                    querySelect.substring(formIndex, orderIndex)).toString();  
	        }  
	    }  
	  
	    /** 
	     * 得到最后一个Order By的插入点位置 
	     * 
	     * @return 返回最后一个Order By插入点的位置 
	     */  
	    private static int getLastOrderInsertPoint(String querySelect) {  
	        int orderIndex = querySelect.toLowerCase().lastIndexOf("order by");  
	        if (orderIndex == -1) {  
	            orderIndex = querySelect.length();  
	        }else{  
	           if(!isBracketCanPartnership(querySelect.substring(orderIndex, querySelect.length()))){  
	               throw new ValidationCubeException("There is no order by sql statement");  
	           }  
	        }  
	        return orderIndex;  
	    }  
	  
	  
	    /** 
	     * 得到分页的SQL 
	     * 
	     * @param offset 偏移量 
	     * @param limit  位置 
	     * @return 分页SQL 
	     */  
	    public static String getPagingSql(String querySelect, int offset, int limit) {  
	        querySelect = compress(querySelect);  
	        String sql = querySelect.replaceAll("[^\\s,]+\\.", "") + " limit " + offset + " ," + limit;  
	        return sql;  
	  
	    }  
	  
	    /** 
	     * 将SQL语句压缩成一条语句，并且每个单词的间隔都是1个空格 
	     * @param sql SQL语句 
	     * @return 如果sql是NULL返回空，否则返回转化后的SQL 
	     */  
	    private static String compress(String sql) {  
	        return sql.replaceAll("[\r\n]", " ").replaceAll("\\s{2,}", " ");  
	    }  
	  
	    /** 
	     * 得到SQL第一个正确的FROM的的插入点 
	     */  
	    private static int getAfterFormInsertPoint(String querySelect) {  
	        String regex = "\\s+FROM\\s+";  
	        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);  
	        Matcher matcher = pattern.matcher(querySelect);  
	        while (matcher.find()) {  
	            int fromStartIndex = matcher.start(0);  
	            String text = querySelect.substring(0, fromStartIndex);  
	            if (isBracketCanPartnership(text)) {  
	                return fromStartIndex;  
	            }  
	        }  
	        return 0;  
	    }  
	  
	    /** 
	     * 判断括号"()"是否匹配,并不会判断排列顺序是否正确 
	     * @param text 要判断的文本 
	     * @return 如果匹配返回TRUE, 否则返回FALSE 
	     */  
	    private static boolean isBracketCanPartnership(String text) {  
	        if (text == null || (getIndexOfCount(text, '(') != getIndexOfCount(text, ')'))) {  
	            return false;  
	        }  
	        return true;  
	    }  
	  
	    /** 
	     * 得到一个字符在另一个字符串中出现的次数 
	     * @param text 文本 
	     * @param ch   字符 
	     */  
	    private static int getIndexOfCount(String text, char ch) {  
	        int count = 0;  
	        for (int i = 0; i < text.length(); i++) {  
	            count = (text.charAt(i) == ch) ? count + 1 : count;  
	        }  
	        return count;  
	    }  
	}  



}
