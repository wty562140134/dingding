package com.youtong.dingding.Tools.sqlTool;

import java.util.Arrays;
import java.util.List;

import com.youtong.dingding.Tools.sqlTool.SQLs.SQLDelete;
import com.youtong.dingding.Tools.sqlTool.SQLs.SQLInsert;
import com.youtong.dingding.Tools.sqlTool.SQLs.SQLSelect;
import com.youtong.dingding.Tools.sqlTool.SQLs.SQLUpdate;
import com.youtong.dingding.Tools.sqlTool.abstractSQL.abstractSQL;

/**
 * 封装sql的类
 * 
 * @author 123
 * 
 */
public class SQLStringUtil {

	private String placeholder;
	private List<String> tableName;
	private List<String> filesName;
	private List<String> values;
	private List<String> where;

	public SQLStringUtil() {
		setPlaceholder("?");
	}

	/**
	 * 获取SQLstr的代理函数,传入相应的sql对象后调用对象的重写的getSQL函数
	 * 
	 * @param obj
	 *            sql对象
	 * @return
	 */
	public String getSQL(abstractSQL obj) {
		String sql = null;
		if (this.values != null) {
			sql = obj.getSQL(this.placeholder, this.tableName, this.filesName,
					this.values);
			this.placeholder = "?";
			return sql;
		}
		sql = obj.getSQL(this.placeholder, this.tableName, this.filesName,
				this.where);
		this.placeholder = "?";
		return sql;
	}

	/**
	 * 设置SQL的占位符默认是?
	 * 
	 * @param placeholder
	 * @return
	 */
	public SQLStringUtil setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
		return this;
	}

	/**
	 * 设置需要增删改查的表名
	 * 
	 * @param tableName
	 * @return
	 */
	public SQLStringUtil setSQLTableName(String... tableName) {
		this.tableName = Arrays.asList(tableName);
		return this;
	}

	/**
	 * 设置需要增删改查的字段名
	 * 
	 * @param FilesName
	 * @return
	 */
	public SQLStringUtil setSQLFileName(String... filesName) {
		this.filesName = Arrays.asList(filesName);
		return this;
	}

	/**
	 * 设置增删改查的where条件字段名
	 * 
	 * @param where
	 * @return
	 */
	public SQLStringUtil setSQLWhere(String... where) {
		this.where = Arrays.asList(where);
		return this;
	}

	/**
	 * 设置insert into语句的values
	 * 
	 * @param value
	 * @return
	 */
	public SQLStringUtil setSQLValue(String... values) {
		this.values = Arrays.asList(values);
		return this;
	}

	public static void main(String[] args) {
		SQLStringUtil sql = new SQLStringUtil();
		String sqlStr = null;

		sqlStr = sql.setSQLTableName("user", "dept").setSQLFileName()
				.setSQLWhere("ip", "id", "gg").getSQL(new SQLSelect());
		System.out.println(sqlStr);

		sqlStr = sql.setPlaceholder("%s").setSQLTableName("user")
				.setSQLWhere("id", "ip").getSQL(new SQLDelete());
		System.out.println(sqlStr);

		sqlStr = sql.setSQLTableName("user").setSQLFileName("id", "ip")
				.setSQLWhere("name", "address").getSQL(new SQLUpdate());
		System.out.println(sqlStr);

		sqlStr = sql.setPlaceholder("%s").setSQLTableName("user")
				.setSQLFileName("name", "address")
				.setSQLValue("jack", "kunming").getSQL(new SQLInsert());
		System.out.println(sqlStr);
	}
}