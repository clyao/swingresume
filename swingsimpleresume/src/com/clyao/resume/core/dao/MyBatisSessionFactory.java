package com.clyao.resume.core.dao;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 * @Title: mybatis实例化工具类
 * @Package com.clyao.swingsimpleresume.util
 * @Description: mybatis实例化工具类
 * @author clyao
 * @date 2017-03-17 15:00
 * @version V1.0
 */
public class MyBatisSessionFactory {

	private static SqlSessionFactory sqlSessionFactory = null;

	/**
	 * @Title: 初始化Session工厂
	 * @Description: 初始化Session工厂
	 * @param 无
	 * @return void
	 * @throws 布局异常
	 */
	private static void initialFactory() {
		try {
			InputStream inputStream = Resources.getResourceAsStream("config/mybatis-config.xml");
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @Title: 获取Session
	 * @Description: 获取Session
	 * @param 无
	 * @return SqlSession
	 * @throws 布局异常
	 */
	public static SqlSession getSession() {
		if (sqlSessionFactory == null) {
			initialFactory();
		}
		return sqlSessionFactory.openSession();
	}

	/**
	 * @Title: 关闭Session
	 * @Description: 关闭Session
	 * @param 无
	 * @return SqlSession
	 * @throws 布局异常
	 */
	public static void closeSession(SqlSession session) {
		if (null != session) {
			// 关闭Sqlsession对象
			session.close();
		}
		session = null;
	}
}
