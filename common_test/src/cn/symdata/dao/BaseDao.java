package cn.symdata.dao;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

@SuppressWarnings("hiding")
public interface BaseDao<T> extends PagingAndSortingRepository<T, String>,JpaSpecificationExecutor<T>{

}
