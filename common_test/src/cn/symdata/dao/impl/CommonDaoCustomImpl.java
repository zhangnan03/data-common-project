package cn.symdata.dao.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import cn.symdata.dao.CommonDaoCustom;
import cn.symdata.dao.DaoFactory;

@Repository
public class CommonDaoCustomImpl<T> extends DaoFactory<T> implements
		CommonDaoCustom<T> {

	/**
	 * 根据sql语句查询
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Object[]> queryBySql(String sql, List params) {

		return (List<Object[]>) queryObjectsListBySql(sql, params);
	} 

	/**
	 * 根据hsql查询博客分页数据
	 * 
	 * @param hsql
	 * @param params
	 * @param pageable
	 * @return
	 */
	public Page<T> getListByHsql(String hsql, List params, Pageable pageable) {
		return hqlQueryForPage(hsql, params, pageable);
	}

	public List<T> getList(String hsql, List params) {
		return hqlQuery(hsql, params);
	}

	public void upadteBySql(String sql, List params) {
		updateBySql(sql, params);
	}

	public Page queryBySql(String sql, List params, Pageable pageable) {
		return queryObjectsPageBySql(sql, params, pageable);
	}

	@Override
	public String singlQueryBySql(String sql, List params) {
		return getSingleQuery(sql, params);
	}

	@Override
	public Object[] objQueryBySql(String sql, List params) {
		return getObjQuery(sql, params);
	}
}
