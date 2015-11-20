package cn.symdata.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.symdata.common.exception.DatabaseException;
import cn.symdata.dao.SystemDictDao;
import cn.symdata.dao.impl.SystemDictDaoImpl;
import cn.symdata.entity.SystemDict;
import cn.symdata.service.SystemDictService;
import cn.symdata.vo.SystemDictVo;

import com.google.common.collect.Lists;

@Service
public class SystemDictServiceImpl implements SystemDictService {

	@Autowired
	private SystemDictDao systemDictDao;
	@Autowired
	private SystemDictDaoImpl systemDictDaoImpl;
	
	
	@Override
	public List<SystemDictVo> findByDictType(String dictType) {
		SystemDictVo systemDictVo = null;
		List<SystemDict> dictList = systemDictDao.findByDictType(dictType);
		List<SystemDictVo> listVo = Lists.newArrayList();
		for (SystemDict systemDict : dictList) {
			systemDictVo = new SystemDictVo();
			systemDictVo.setDictCode(systemDict.getDictCode());
			systemDictVo.setDictName(systemDict.getDictName());
			systemDictVo.setDictType(systemDict.getDictType());
			systemDictVo.setDictTypeName(systemDict.getDictTypeName());
			listVo.add(systemDictVo);
		}
		return listVo;
	}

	@Override
	public SystemDictVo findByDictTypeAndDictCode(String dictType, String dictCode) {
		SystemDictVo systemDictVo = null;
		SystemDict systemDict = systemDictDao.findByDictTypeAndDictCode(dictType, dictCode);
		if(systemDict!=null){
			systemDictVo = new SystemDictVo();
			systemDictVo.setDictCode(systemDict.getDictCode());
			systemDictVo.setDictName(systemDict.getDictName());
			systemDictVo.setDictType(systemDict.getDictType());
			systemDictVo.setDictTypeName(systemDict.getDictTypeName());
		}
		
		return systemDictVo;
	}

	@Override
	public Page<SystemDict> findAllList(SystemDictVo systemDictVo, Integer page) {
		Pageable pageable = new PageRequest(page != null ? page.intValue() - 1 : 0, 10);
		Page<SystemDict> list=null;
		try {
			list=systemDictDaoImpl.findAllList(systemDictVo, pageable);
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	@Transactional(readOnly = false)
	public void updateSystemDict(SystemDict systemDict,SystemDict dict) {
		if(StringUtils.isNotBlank(systemDict.getDictCode())){
			dict.setDictCode(systemDict.getDictCode());
		}
		if(StringUtils.isNotBlank(systemDict.getDictName())){
			dict.setDictName(systemDict.getDictName());
		}
		if(StringUtils.isNotBlank(systemDict.getDictType())){
			dict.setDictType(systemDict.getDictType());
		}
		if(StringUtils.isNotBlank(systemDict.getDictTypeName())){
			dict.setDictTypeName(systemDict.getDictTypeName());
		}
		if(StringUtils.isNotBlank(systemDict.getRemark())){
			dict.setRemark(systemDict.getRemark());
		}
		if(systemDict.getOrderId()!=null){
			dict.setOrderId(systemDict.getOrderId());
		}
		if(StringUtils.isNotBlank(systemDict.getStatus())){
			dict.setStatus(systemDict.getStatus());
		}
		systemDictDao.save(dict);
	}

	@Override
	@Transactional(readOnly = false)
	public void saveSystemDict(SystemDict systemDict) {
		systemDictDao.save(systemDict);
	}

	@Override
	public SystemDict findOne(String id) {
		return systemDictDao.findOne(id);
	}

	


}
