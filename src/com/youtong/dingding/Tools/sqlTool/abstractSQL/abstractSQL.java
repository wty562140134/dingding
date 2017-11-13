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
	 * @param placeholder
	 *            占位符
	 * @param tableName
	 *            表名
	 * @param filesName
	 *            字段名
	 * @param wheres
	 *            where条件
	 * @param whereValues
	 *            where条件的value
	 * @param getFullSQL
	 *            是否获取完整SQL
	 * 
	 * @return
	 */
	public abstract String getSQL(String placeholder, List<String> tableName,
			List<String> filesName, List<String> wheres,
			List<String> whereValues, List<String> setValues, Boolean getFullSQL);

	/**
	 * SQL的where条件格式相同,设计在父类中给子类调用
	 * 
	 * @param placeholder
	 * @param wheres
	 * @param getFullSQL
	 * @throws Exception
	 */
	protected void where(String placeholder, List<String> wheres,
			List<String> whereValues, List<String> setValues, Boolean getFullSQL) {
		String where = "where";
		this.WHERE = verdict(placeholder, where, wheres, whereValues,
				setValues, getFullSQL);
	}

	/**
	 * 根据不同的sql给sql条件规整为需要的格式并返回
	 * 
	 * @param placeholder
	 *            占位符
	 * @param sqlType
	 *            sql的类型
	 * @param verdictList
	 *            sql条件字段或表名
	 * @return
	 * @throws Exception
	 */
	protected String verdict(String placeholder, String sqlType,
			List<String> verdictList, List<String> whereValues,
			List<String> setValues, Boolean getFullSQL) {
		switch (sqlType) {
		case "select":
			if (verdictList == null || verdictList.size() == 0) {
				sqlType += " *";
			} else {
				sqlType += addPlaceholder(placeholder, sqlType, verdictList);
				if (placeholder.equals("%s") && getFullSQL) {
					sqlType = String.format(sqlType, verdictList.toArray());
				}
			}
			break;

		case "from":
			if (verdictList == null || verdictList.size() == 0) {
				throw new RuntimeException("The" + " " + sqlType + " "
						+ "condition cannot be empty");
			}
			if (verdictList.size() == 1) {
				sqlType += " " + placeholder;
			} else {
				sqlType += addPlaceholder(placeholder, sqlType, verdictList);
				sqlType = dispose(sqlType, verdictList);
			}
			if (placeholder.equals("%s") && getFullSQL) {
				sqlType = String.format(sqlType, verdictList.toArray());
			}
			break;

		case "set":
			if (verdictList == null || verdictList.size() == 0) {
				throw new RuntimeException("The" + " " + sqlType + " "
						+ "condition cannot be empty");
			}
			if (verdictList.size() == 1) {
				sqlType += " " + placeholder;
			} else {
				sqlType += addPlaceholder(placeholder, sqlType, verdictList);
				sqlType = dispose(sqlType, verdictList);
			}
			if (placeholder.equals("%s") && getFullSQL) {
				if (setValues == null) {
					throw new RuntimeException("SQL set value on setting");
				}
				sqlType = String.format(sqlType, setValues.toArray());
			}
			break;

		case "where":
			if (verdictList == null || verdictList.size() == 0) {
				sqlType = "";
			}
			sqlType += addPlaceholder(placeholder, sqlType, verdictList);
			sqlType = dispose(sqlType, verdictList);
			if (placeholder.equals("%s") && getFullSQL) {
				if (whereValues != null) {
					sqlType = String.format(sqlType, whereValues.toArray());
				}
			}
			break;

		case "insert":
		case "values":
			sqlType = addPlaceholder(placeholder, sqlType, verdictList) + ")";
			if (placeholder.equals("%s") && getFullSQL) {
				sqlType = String.format(sqlType, verdictList.toArray());
			}
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
		if (wheres == null) {
			return "";
		}
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
		if (verdictList == null || verdictList.size() == 0) {
			para = "";
			return para;
		}
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
						|| sqlType.equals("values") || sqlType.equals("from")
						|| sqlType.equals("select")) {
					para += ",";
				}
			}
		}
		return para;
	}
}