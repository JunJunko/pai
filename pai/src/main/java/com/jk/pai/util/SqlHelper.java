package com.jk.pai.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public abstract class SqlHelper {
	// 定义要使用的变量
	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	private CallableStatement cs = null;

	protected String url = "";
	protected String userName = "";
	protected String password = "";

	private Properties pp = null;
	private FileInputStream fis = null;

	public Connection getConn() {
		return conn;
	}

	public PreparedStatement getPs() {
		return ps;
	}

	public ResultSet getRs() {
		return rs;
	}

	public CallableStatement getCs() {
		return cs;
	}

	// 加载驱动，只需要一次
	{
		try {
			// 从配置文件dbinfo.properties中读取配置信息
			pp = new Properties();
			Class.forName("org.sqlite.JDBC");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fis != null)
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			fis = null;

		}
	}

	// 得到连接
	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, userName, password);
	}

	// 处理多个update/delete/insert
	public void executeUpdateMultiParams(String[] sql, String[][] parameters) {
		try {
			// 获得连接
			conn = getConnection();
			// 可能传多条sql语句
			conn.setAutoCommit(false);
			for (int i = 0; i < sql.length; i++) {
				if (parameters[i] != null) {
					ps = conn.prepareStatement(sql[i]);
					for (int j = 0; j < parameters[i].length; j++)
						ps.setString(j + 1, parameters[i][j]);
				}
				ps.executeUpdate();
			}
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new RuntimeException(e.getMessage());
		} finally {
			// 关闭资源
			close(rs, ps, conn);
		}
	}

	// update/delete/insert
	// sql格式:UPDATE tablename SET columnn = ? WHERE column = ?
	public void executeUpdate(String sql, String[] parameters) {
		try {
			// 1.创建一个ps
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			// 给？赋值
			if (parameters != null)
				for (int i = 0; i < parameters.length; i++) {
					ps.setString(i + 1, parameters[i]);
				}
			// 执行
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();// 开发阶段
			throw new RuntimeException(e.getMessage());
		} finally {
			// 关闭资源
			close(rs, ps, conn);
		}
	}

	// select
	public ResultSet executeQuery(String sql, String[] parameters) {
		ResultSet rs = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			if (parameters != null) {
				for (int i = 0; i < parameters.length; i++) {
					ps.setString(i + 1, parameters[i]);
				}
			}
			rs = ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {

		}
		return rs;
	}

	// 调用无返回值存储过程
	// 格式： call procedureName(parameters list)
	public void callProc(String sql, String[] parameters) {
		try {
			conn = getConnection();
			cs = conn.prepareCall(sql);
			// 给？赋值
			if (parameters != null) {
				for (int i = 0; i < parameters.length; i++)
					cs.setObject(i + 1, parameters[i]);
			}
			cs.execute();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
			// 关闭资源
			close(rs, cs, conn);
		}
	}

	// 调用带有输入参数且有返回值的存储过程
	public CallableStatement callProcInput(String sql, String[] inparameters) {
		try {
			conn = getConnection();
			cs = conn.prepareCall(sql);
			if (inparameters != null)
				for (int i = 0; i < inparameters.length; i++)
					cs.setObject(i + 1, inparameters[i]);
			cs.execute();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {

		}
		return cs;
	}

	// 调用有返回值的存储过程
	public CallableStatement callProcOutput(String sql, Integer[] outparameters) {
		try {
			conn = getConnection();
			cs = conn.prepareCall(sql);
			// 给out参数赋值
			if (outparameters != null)
				for (int i = 0; i < outparameters.length; i++)
					cs.registerOutParameter(i + 1, outparameters[i]);
			cs.execute();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {

		}
		return cs;
	}

	public void close(ResultSet rs, Statement ps, Connection conn) {
		if (rs != null)
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		rs = null;
		if (ps != null)
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		ps = null;
		if (conn != null)
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		conn = null;
	}

	public boolean insertArpStandardList(List<?> list, String sql) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);

			// 优化插入第一步 设置手动提交
			conn.setAutoCommit(false);

			int len = list.size();

			for (int i = 0; i < list.size(); i++) {
				List<Field> field = Arrays.asList(list.get(i).getClass().getDeclaredFields());
				Object t = list.get(i);
				for (int j = 0; j < field.size(); j++) {

					field.get(j).setAccessible(true);
					ps.setString(j+1, field.get(j).get(t).toString());
					// TODO Auto-generated catch block
				}
				ps.addBatch();
				// if(ps.executeUpdate() != 1)result = false;
				// 每200次提交一次
				if ((i != 0 && i % 200 == 0) || i == len - 1) {// 可以设置不同的大小；如50，100，200，500，1000等等
					ps.executeBatch();
					// 优化插入第三步 提交，批量插入数据库中。
					conn.commit();
					ps.clearBatch(); // 提交后，Batch清空。
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false; // 出错才报false
		} finally {
			close(rs, ps, conn);
		}
		return true;
	}
}