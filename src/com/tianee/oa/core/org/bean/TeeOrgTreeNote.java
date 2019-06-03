package com.tianee.oa.core.org.bean;
import org.hibernate.annotations.Index;
import java.util.List;

public class TeeOrgTreeNote {
	
		private String id;
		private String name;
		private List<TeeOrgTreeNote> childs;//子节点
		private TeeOrgTreeNote parent;//父节点
		private int level;//深度
		private boolean leaf;//是否叶子节点
		
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public List<TeeOrgTreeNote> getChilds() {
			return childs;
		}
		public void setChilds(List<TeeOrgTreeNote> childs) {
			this.childs = childs;
		}
		public TeeOrgTreeNote getParent() {
			return parent;
		}
		public void setParent(TeeOrgTreeNote parent) {
			this.parent = parent;
		}
		public int getLevel() {
			return level;
		}
		public void setLevel(int level) {
			this.level = level;
		}
		public boolean isLeaf() {
			return leaf;
		}
		public void setLeaf(boolean leaf) {
			this.leaf = leaf;
		}
}
