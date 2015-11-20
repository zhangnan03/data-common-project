package cn.symdata.dto;

import java.util.Set;

import cn.symdata.entity.Menu;

import com.google.common.collect.Sets;

public class MenuDto {

	/**
	 * 菜单名称
	 */
	public String name;
	/**
	 * 菜单code码
	 */
	public String code;
	/**
	 * 父id
	 */
	public String parentId;
	/**
	 * 系统标识
	 */
	public String systemMark;
	/**
	 * 路径
	 */
	public String url;
	/**
	 * 菜单标识
	 */
	private String menuFlag;
	
	public Menu parent;
	
	private Set<Menu> children = Sets.newTreeSet();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getSystemMark() {
		return systemMark;
	}
	public void setSystemMark(String systemMark) {
		this.systemMark = systemMark;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Menu getParent() {
		return parent;
	}
	public void setParent(Menu parent) {
		this.parent = parent;
	}
	public Set<Menu> getChildren() {
		return children;
	}
	public void setChildren(Set<Menu> children) {
		this.children = children;
	}
	public String getMenuFlag() {
		return menuFlag;
	}
	public void setMenuFlag(String menuFlag) {
		this.menuFlag = menuFlag;
	}

	
}
