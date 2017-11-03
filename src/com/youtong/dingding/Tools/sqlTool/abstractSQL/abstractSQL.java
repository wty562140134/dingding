package com.youtong.dingding.Tools.sqlTool.abstractSQL;

import java.text.MessageFormat;
import java.util.List;

/**
 * 通过继承这个抽象类,重写getSQL函数即可用代理类sqlStringUtil的getSQL()获取处理并返回的SQL
 * 
 * @author 123
 * 
 */
public abstract class abstractSQL {

	protected String WHERE;

	/**
	 * 获取sql字符串的抽象方法
	 * 
	 * @param selection
	 * @param selectTableName
	 * @param wheres
	 * @return
	 */
	public abstract String getSQL(String placeholder, List<String> tableName,
			List<String> filesName, List<String> wheres);

	/**
	 * SQL的where条件格式相同,设计在父类中给子类调用
	 * 
	 * @param wheres
	 */
	protected void where(String placeholder, List<String> wheres) {
		String where = "where";
		this.WHERE = verdict(placeholder, where, wheres);
	}

	/**
	 * 根据不同的sql给sql条件规整为需要的格式并返回
	 * 
	 * @param sqlType
	 * @param verdictList
	 * @return
	 */
	protected String verdict(String placeholder, String sqlType,
			List<String> verdictList) {
		switch (sqlType) {
		case "select":
			if (verdictList.size() == 0) {
				sqlType += " *";
			} else {
				sqlType += addPlaceholder(placeholder, sqlType, verdictList);
			}
			break;

		case "from":
		case "set":
			if (verdictList.size() == 0) {
				throw new RuntimeException("The" + " " + sqlType + " "
						+ "condition cannot be empty");
			}
			if (verdictList.size() == 1) {
				sqlType += " " + placeholder;
			} else {
				sqlType += addPlaceholder(placeholder, sqlType, verdictList);
				sqlType = dispose(sqlType, verdictList);
			}
			break;

		case "where":
			if (verdictList.size() == 0) {
				sqlType = "";
			}
			sqlType += addPlaceholder(placeholder, sqlType, verdictList);
			sqlType = dispose(sqlType, verdictList);
			break;

		case "insert":
		case "values":
			sqlType = addPlaceholder(placeholder, sqlType, verdictList) + ")";
			break;

		default:
			break;
		}
		return sqlType;
	}

	/**
	 * 给where和set条件变换格式为 字段=?的格式并返回
	 * 
	 * @param where
	 * @param wheres
	 * @return
	 */
	private String dispose(String where, List<String> wheres) {
		where = MessageFormat.format(where, wheres.toArray());
		return where;
	}

	/**
	 * 跟具不同的sql添加占位符和格式整理
	 * 
	 * @param sqlType
	 * @param verdictList
	 * @return
	 */
	private String addPlaceholder(String placeholder, String sqlType,
			List<String> verdictList) {
		String para = null;
		if (sqlType.equals("insert") || sqlType.equals("values")) {
			para = "(";
		} else {
			para = "";
		}
		for (int i = 0; i < verdictList.size(); i++) {
			if (sqlType.equals("where") || sqlType.equals("set")) {
				int index = i;
				para += " {" + index + "} =" + " " + placeholder;
				if (i + 1 < verdictList.size() && sqlType.equals("where")) {
					para += " and";
				}
			} else {
				para += " " + placeholder;
			}
			if (i + 1 < verdictList.size()) {
				if (sqlType.equals("set") || sqlType.equals("insert")
						|| sqlType.equals("values") || sqlType.equals("from")) {
					para += ", ";
				}
			}
		}
		return para;
	}
}