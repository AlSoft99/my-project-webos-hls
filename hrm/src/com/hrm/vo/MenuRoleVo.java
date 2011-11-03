package com.hrm.vo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.hrm.control.Request;
import com.hrm.dao.MenuLevelBinaryDAO;
import com.hrm.dao.MenuLevelStairDAO;
import com.hrm.dao.RoleInfoDAO;
import com.hrm.dao.RoleMenuBinaryDAO;
import com.hrm.dao.RoleMenuStairDAO;
import com.hrm.entity.MenuLevelBinary;
import com.hrm.entity.MenuLevelStair;
import com.hrm.entity.RoleMenuBinary;
import com.hrm.entity.RoleMenuBinaryId;
import com.hrm.entity.RoleMenuStair;
import com.hrm.entity.RoleMenuStairId;
import com.hrm.entity.UserInfo;
import com.hrm.server.InitData;

public class MenuRoleVo implements BaseVo {

	private RoleInfoDAO roleInfoDAO;
	private MenuLevelStairDAO menuLevelStairDAO;
	private MenuLevelBinaryDAO menuLevelBinaryDAO;
	private RoleMenuStairDAO roleMenuStairDAO;
	private RoleMenuBinaryDAO roleMenuBinaryDAO;
	private static Logger logger = Logger.getLogger(MenuRoleVo.class);

	public RoleInfoDAO getRoleInfoDAO() {
		return roleInfoDAO;
	}

	public void setRoleInfoDAO(RoleInfoDAO roleInfoDAO) {
		this.roleInfoDAO = roleInfoDAO;
	}
	public Request execute(Request request) throws Exception{
		String result_menu = "";
		UserInfo userInfo = (UserInfo)request.getUserInfo();
		try{
			String action = request.getParamsMap().get("action");
			if (action.equals("role")) {//角色选择树的json装载
				result_menu = "[";
				List<List<String>> list = InitData.getJsonList("roleInfo");
				for(List<String> list1 : list ){
					result_menu += "{id:'"+list1.get(0)+"',text:'"+list1.get(1)+"',qtip:'"+list1.get(1)+"',leaf:true},";
				}
				result_menu = result_menu.substring(0, result_menu.length()-1);
				result_menu += "]";
				result_menu = result_menu.replaceAll("\"", "");
			}else if(action.equals("main_menu")){//主菜单的json装载
				String role_code = request.getParamsMap().get("role_code");
				result_menu = "[";
				List<MenuLevelStair> menuStairList = menuLevelStairDAO.findByProperty("roleCode", role_code);
				List<MenuLevelBinary> menuBinaryList = menuLevelBinaryDAO.findByProperty("roleCode", role_code);
				for(MenuLevelStair stair: menuStairList){
					result_menu += getTreeChild(stair, menuBinaryList)+",";
				}
				result_menu = result_menu.substring(0, result_menu.length()-1);
				result_menu += "]";
			}else if(action.equals("role_main_menu")){
				String role_code = request.getParamsMap().get("role_code");
				result_menu = "[";
				String hql = "select a.menuId,a.menuName,a.menuTips from MenuLevelStair a";
				List<Object[]> menuStairList = roleMenuStairDAO.findByHql(hql, new String[]{});
				final String hql1 = "select a.menu_tree_code,a.menu_tree_name,a.menu_tree_tips,a.menu_code,(select b.menu_tree_code  from role_menu_binary b where b.role_code='"+role_code+"' and b.menu_tree_code=a.menu_tree_code) flag from menu_level_binary a ";
				List<Object[]> menuBinaryList = roleMenuBinaryDAO.getHibernateTemplate().executeFind(new HibernateCallback<List<Object[]>>(){

					public List<Object[]> doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session.createSQLQuery(hql1).list();
					}
					
				});
				for(Object[] stair: menuStairList){
					String tree = getRoleTreeChild(stair, menuBinaryList);
					if(!"".equals(tree)){
						result_menu += tree+",";
					}
				}
				result_menu = result_menu.substring(0, result_menu.length()-1);
				result_menu += "]";
			}else if(action.equals("role_menu")){//角色选择菜单的json装载
				String role_code = request.getParamsMap().get("role_code");
				result_menu = "[";
				String hql = "select a.id.menuCode,b.menuName,b.menuTips from RoleMenuStair a,MenuLevelStair b where a.id.roleCode=? and b.menuCode=a.id.menuCode";
				List<Object[]> menuStairList = roleMenuStairDAO.findByHql(hql, new String[]{role_code});
				hql = "select a.id.menuTreeCode,b.menuTreeName,b.menuTreeTips,b.menuCode from RoleMenuBinary a,MenuLevelBinary b where a.id.roleCode=? and b.menuTreeCode=a.id.menuTreeCode";
				List<Object[]> menuBinaryList = roleMenuBinaryDAO.findByHql(hql, new String[]{role_code});
				for(Object[] stair: menuStairList){
					result_menu += getTreeChild(stair, menuBinaryList)+",";
				}
				result_menu = result_menu.substring(0, result_menu.length()-1);
				result_menu += "]";
			}else if(action.equals("insert")){//主菜单的插入菜单操作
				boolean isRoot = Boolean.valueOf(request.getParamsMap().get("isRoot"));
				String parentNodeId = request.getParamsMap().get("parentNodeId");
				String tree_name = request.getParamsMap().get("tree_name");
				String tree_tips = request.getParamsMap().get("tree_tips");
				String createId = createId();
				if (isRoot) {
					MenuLevelStair stair = new MenuLevelStair();
					stair.setMenuId(createId);
					stair.setMenuCode(createId);
					stair.setMenuName(tree_name);
					stair.setMenuTips(tree_tips);
					stair.setRoleCode("0");
					stair.setUserName(userInfo.getUserId());
					stair.setUserUpdt(new Date());
					menuLevelStairDAO.save(stair);
				}else{
					String tree_page = request.getParamsMap().get("tree_page");
					MenuLevelBinary bin = new MenuLevelBinary();
					bin.setMenuTreeCode(createId);
					bin.setMenuCode(parentNodeId);
					bin.setMenuPage("");
					bin.setMenuTreeName(tree_name);
					bin.setMenuTreeTips(tree_tips);
					bin.setMenuPage(tree_page);
					bin.setRoleCode("0");
					bin.setUserName(userInfo.getUserId());
					bin.setUserUpdt(new Date());
					menuLevelBinaryDAO.save(bin);
				}
				result_menu = "{'success':true,'msg':'"+createId+"'}";
			}else if(action.equals("delete")){//主菜单的删除操作
				boolean isRoot = Boolean.valueOf(request.getParamsMap().get("isRoot"));
				boolean isLeaf = Boolean.valueOf(request.getParamsMap().get("isLeaf"));
				String menu_code = request.getParamsMap().get("menu_code");
				if (isRoot) {
					menuLevelBinaryDAO.delete(new MenuLevelBinary());
					menuLevelStairDAO.delete(new MenuLevelStair());
				}else if(!isRoot && isLeaf){
					MenuLevelBinary bin = new MenuLevelBinary();
					bin.setMenuTreeCode(menu_code);
					bin.setRoleCode("0");
					menuLevelBinaryDAO.delete(bin);
					String hql = "delete from RoleMenuBinary a where a.id.menuTreeCode='"+menu_code+"'";
					roleMenuBinaryDAO.deleteHql(hql);
				}else{
					String hql = "";
					String[] childMenu_code = request.getParamsMap().get("childMenu_code").split(",");
					List<MenuLevelBinary> binList = new ArrayList<MenuLevelBinary>();
					for (int i = 0; i < childMenu_code.length; i++) {
						MenuLevelBinary bin = new MenuLevelBinary();
						bin.setMenuTreeCode(childMenu_code[i]);
						binList.add(bin);
						
					}
					menuLevelBinaryDAO.deleteList(binList);
					MenuLevelStair stair = new MenuLevelStair();
					stair.setMenuId(menu_code);
					stair.setRoleCode("0");
					menuLevelStairDAO.delete(stair);
					hql = "delete from RoleMenuStair where id.menuCode='"+menu_code+"'";
					roleMenuStairDAO.deleteHql(hql);
					hql = "delete from RoleMenuBinary a where a.id.menuCode='"+menu_code+"'";
					roleMenuBinaryDAO.deleteHql(hql);
				}
				result_menu = "{'success':true,'msg':''}";
			}else if(action.equals("update")){//主菜单的更新操作
				String menu_id = request.getParamsMap().get("menu_id");
				String menu_name = request.getParamsMap().get("menu_name");
				boolean isLeaf = Boolean.valueOf(request.getParamsMap().get("leaf"));
				if(isLeaf){
					MenuLevelBinary bin = menuLevelBinaryDAO.findById(menu_id);
					bin.setMenuTreeName(menu_name);
					bin.setRoleCode("0");
					menuLevelBinaryDAO.update(bin);
				}else{
					MenuLevelStair stair = menuLevelStairDAO.findById(menu_id);
					stair.setRoleCode("0");
					stair.setMenuName(menu_name);
					menuLevelStairDAO.update(stair);
				}
				result_menu = "{'success':true,'msg':''}";
			}else if(action.equals("updateWin")){//主菜单的更新操作1207
				String menu_id = request.getParamsMap().get("menu_id");
				String menu_name = request.getParamsMap().get("tree_name");
				String tree_tips = request.getParamsMap().get("tree_tips");
				String role_code = request.getParamsMap().get("role_code");
				boolean isLeaf = Boolean.valueOf(request.getParamsMap().get("leaf"));
				if(isLeaf){
					String tree_page = request.getParamsMap().get("tree_page");
					MenuLevelBinary bin = menuLevelBinaryDAO.findById(menu_id);
					bin.setMenuTreeName(menu_name);
					bin.setMenuTreeTips(tree_tips);
					bin.setRoleCode(role_code);
					bin.setMenuPage(tree_page);
					menuLevelBinaryDAO.update(bin);
				}else{
					MenuLevelStair stair = menuLevelStairDAO.findById(menu_id);
					stair.setRoleCode(role_code);
					stair.setMenuName(menu_name);
					stair.setMenuTips(tree_tips);
					menuLevelStairDAO.update(stair);
				}
				result_menu = "{'success':true,'msg':''}";
			}else if(action.equals("subtree")){//主菜单往角色菜单拖拽节点的插入操作
				String menu_code = request.getParamsMap().get("menu_code");
				String[] child_code = request.getParamsMap().get("child_code").split(",");
				String role_code = request.getParamsMap().get("role_code");
				String parent_code = request.getParamsMap().get("new_parent_code");
				boolean leaf = Boolean.valueOf(request.getParamsMap().get("leaf"));
				if (leaf) {
					RoleMenuBinary bin = new RoleMenuBinary();
					RoleMenuBinaryId binId = new RoleMenuBinaryId();
					binId.setMenuCode(parent_code);
					binId.setMenuTreeCode(menu_code);
					binId.setRoleCode(role_code);
					bin.setId(binId);
					bin.setUserUpdt(new Date());
					roleMenuBinaryDAO.save(bin);
				}else{
					RoleMenuStair stair = new RoleMenuStair();
					RoleMenuStairId stairId = new RoleMenuStairId();
					stairId.setMenuCode(menu_code);
					stairId.setRoleCode(role_code);
					stair.setId(stairId);
					stair.setUserUpdt(new Date());
					roleMenuStairDAO.save(stair);
					for (int i = 0; i < child_code.length; i++) {
						if (child_code[i].equals("")) {
							continue;
						}
						RoleMenuBinary bin = new RoleMenuBinary();
						RoleMenuBinaryId binId = new RoleMenuBinaryId();
						binId.setMenuCode(menu_code);
						binId.setMenuTreeCode(child_code[i]);
						binId.setRoleCode(role_code);
						bin.setId(binId);
						bin.setUserUpdt(new Date());
						roleMenuBinaryDAO.save(bin);
					}
				}
				result_menu = "{'success':true,'msg':'操作成功'}";
				
			}else if(action.equals("deleteRole")){//角色菜单的删除操作
				boolean isRoot = Boolean.valueOf(request.getParamsMap().get("isRoot"));
				boolean isLeaf = Boolean.valueOf(request.getParamsMap().get("isLeaf"));
				String menu_code = request.getParamsMap().get("menu_code");
				String role_code = request.getParamsMap().get("role_code");
				if (isRoot) {
					roleMenuStairDAO.delete(new RoleMenuStair());
					roleMenuBinaryDAO.delete(new RoleMenuBinary());
				}else if(!isRoot && isLeaf){
					String parent_node = request.getParamsMap().get("parent_node");
					RoleMenuBinary bin = new RoleMenuBinary();
					RoleMenuBinaryId binId = new RoleMenuBinaryId();
					binId.setRoleCode(role_code);
					binId.setMenuCode(parent_node);
					binId.setMenuTreeCode(menu_code);
					bin.setId(binId);
					roleMenuBinaryDAO.delete(bin);
				}else{
					String[] childMenu_code = request.getParamsMap().get("childMenu_code").split(",");
					List<RoleMenuBinary> binList = new ArrayList<RoleMenuBinary>();
					for (int i = 0; i < childMenu_code.length; i++) {
						RoleMenuBinary bin = new RoleMenuBinary();
						RoleMenuBinaryId binId = new RoleMenuBinaryId();
						binId.setRoleCode(role_code);
						binId.setMenuCode(menu_code);
						binId.setMenuTreeCode(childMenu_code[i]);
						bin.setId(binId);
						binList.add(bin);
					}
					roleMenuBinaryDAO.deleteList(binList);
					RoleMenuStair stair = new RoleMenuStair();
					RoleMenuStairId stairId = new RoleMenuStairId();
					stairId.setMenuCode(menu_code);
					stairId.setRoleCode(role_code);
					stair.setId(stairId);
					roleMenuStairDAO.delete(stair);
				}
			}else if(action.equals("selftree")){
				String menu_tree_code = request.getParamsMap().get("menu_code");
//				String role_code = request.getParameter("role_code");
				String new_menu_code = request.getParamsMap().get("new_parent_code");
//				String old_menu_code = request.getParameter("old_parent_code");
				MenuLevelBinary bin = menuLevelBinaryDAO.findById(menu_tree_code);
				bin.setMenuCode(new_menu_code);
				bin.setUserName(userInfo.getUserId());
				bin.setUserUpdt(new Date());
				menuLevelBinaryDAO.getHibernateTemplate().update(bin);
				String hql = "delete from RoleMenuBinary a where a.id.menuTreeCode='"+menu_tree_code+"'";
				roleMenuBinaryDAO.deleteHql(hql);
			}
		}catch(Exception e){
			logger.error("MenuRoleVo的execute方法错误", e);
		}
		request.setResponse(result_menu);
		return request;
	}
	public String createId(){
		return "TD"+System.currentTimeMillis()+(Math.random()+"").substring(5, 15)+"";
	}
	public String getTreeChild(MenuLevelStair stair,List<MenuLevelBinary> list){
		String result = "{id:\""+stair.getMenuId()+"\",text:\""+stair.getMenuName()+"\",qtip:\""+stair.getMenuTips()+"\",leaf:false,";
		result += "children:[";
		if(isExist(stair,list)){
			for(MenuLevelBinary binary:list){
				if (stair.getMenuCode().equals(binary.getMenuCode())) {
					result += "{id:\""+binary.getMenuTreeCode()+"\",text:\""+binary.getMenuTreeName()+"\",qtip:\""+binary.getMenuTreeTips()+"\",page:\""+binary.getMenuPage()+"\",leaf:true},";
				}
			}
			result = result.substring(0, result.length()-1);
		}
		result += "]}";
		return result;
	}
	public String getTreeChild(RoleMenuStair stair,List<RoleMenuBinary> list){
		String result = "{id:\""+stair.getId().getMenuCode()+"\",text:\""+stair.getId().getMenuCode()+"\",qtip:\""+stair.getId().getMenuCode()+"\",leaf:false,";
		result += "children:[";
		if(isExist(stair,list)){
			for(RoleMenuBinary binary:list){
				if (stair.getId().getMenuCode().equals(binary.getId().getMenuCode())) {
					result += "{id:\""+binary.getId().getMenuTreeCode()+"\",text:\""+binary.getId().getMenuTreeCode()+"\",qtip:\""+binary.getId().getMenuTreeCode()+"\",leaf:true},";
				}
			}
			result = result.substring(0, result.length()-1);
		}
		result += "]}";
		return result;
	}
	//a.id.menuCode,b.menuName,b.menuTips
	public String getTreeChild(Object[] stair,List<Object[]> list){
		String result = "{id:\""+stair[0]+"\",text:\""+stair[1]+"\",qtip:\""+stair[2]+"\",leaf:false,";
		result += "children:[";
		if(isExist(stair,list)){
			for(Object[] binary:list){
				if (stair[0].equals(binary[3])) {
					result += "{id:\""+binary[0]+"\",text:\""+binary[1]+"\",qtip:\""+binary[2]+"\",leaf:true},";
				}
			}
			result = result.substring(0, result.length()-1);
		}
		result += "]}";
		return result;
	}
	public String getRoleTreeChild(Object[] stair,List<Object[]> list){
		String result = "{id:\""+stair[0]+"\",text:\""+stair[1]+"\",qtip:\""+stair[2]+"\",leaf:false,";
		result += "children:[";
		boolean flag = false;
		if(isExist(stair,list)){
			for(Object[] binary:list){
				if (stair[0].equals(binary[3]) && binary[4]==null) {
					flag = true;
					result += "{id:\""+binary[0]+"\",text:\""+binary[1]+"\",qtip:\""+binary[2]+"\",leaf:true},";
				}
			}
			if(flag){
				result = result.substring(0, result.length()-1);
			}
		}
		result += "]}";
		if(!flag){
			result = "";
		}
		return result;
	}
	public boolean isExist(MenuLevelStair stair,List<MenuLevelBinary> list){
		for(MenuLevelBinary binary:list){
			if (stair.getMenuCode().equals(binary.getMenuCode())) {
				return true;
			}
		}
		return false;
	}
	public boolean isExist(RoleMenuStair stair,List<RoleMenuBinary> list){
		for(RoleMenuBinary binary:list){
			if (stair.getId().getMenuCode().equals(binary.getId().getMenuCode())) {
				return true;
			}
		}
		return false;
	}
	public boolean isExist(Object[] stair,List<Object[]> list){
		for(Object[] binary:list){
			if (stair[0].equals(binary[3])) {
				return true;
			}
		}
		return false;
	}
	public void fillterList(List<MenuLevelStair> list_1,List<Object[]> list_2){
		for(int i = list_1.size()-1 ; i >=0; i--){
			MenuLevelStair stair = list_1.get(i);
			for(Object[] obj : list_2){
				if(stair.getMenuCode().equals(obj[0].toString())){
					list_1.remove(i);
				}
			}
		}
	}
	public MenuLevelStairDAO getMenuLevelStairDAO() {
		return menuLevelStairDAO;
	}

	public void setMenuLevelStairDAO(MenuLevelStairDAO menuLevelStairDAO) {
		this.menuLevelStairDAO = menuLevelStairDAO;
	}

	public MenuLevelBinaryDAO getMenuLevelBinaryDAO() {
		return menuLevelBinaryDAO;
	}

	public void setMenuLevelBinaryDAO(MenuLevelBinaryDAO menuLevelBinaryDAO) {
		this.menuLevelBinaryDAO = menuLevelBinaryDAO;
	}

	public RoleMenuStairDAO getRoleMenuStairDAO() {
		return roleMenuStairDAO;
	}

	public void setRoleMenuStairDAO(RoleMenuStairDAO roleMenuStairDAO) {
		this.roleMenuStairDAO = roleMenuStairDAO;
	}

	public RoleMenuBinaryDAO getRoleMenuBinaryDAO() {
		return roleMenuBinaryDAO;
	}

	public void setRoleMenuBinaryDAO(RoleMenuBinaryDAO roleMenuBinaryDAO) {
		this.roleMenuBinaryDAO = roleMenuBinaryDAO;
	}

}
