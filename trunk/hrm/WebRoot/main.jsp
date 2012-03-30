<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.hrm.entity.*" %>
<%@ page import="com.hrm.util.*" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
			
	UserInfo userInfo = (UserInfo)request.getSession().getAttribute("userInfo");
	if(userInfo==null){
		out.print("<script>alert('session已经过期,请重新登陆');window.location.href='index.jsp'</script>");
		return;
	}
	String baseMsg = "{用户账号:'"+userInfo.getUserCode()+"',用户角色:'"+userInfo.getRoleInfo().getRoleName()+"',用户名:'"+userInfo.getUserName()+"',电话号码:'"+userInfo.getUserPhone()+"',电子邮箱:'"+userInfo.getUserMail()+"',身份证号:'"+userInfo.getUserQq()+"',更新时间:'"+userInfo.getUpdtTime()+"'}";
	String roleMsg = "{用户角色:'"+userInfo.getRoleInfo().getRoleName()+"',角色描述:'"+userInfo.getRoleInfo().getRoleDesc()+"'}";
	String cleardate = ClsFactory.newInstance().getCleardate();
	String equals = ClsFactory.newInstance().getEquals();
	String result = "";
	if(equals.equals("0")){
		result = "异常";
	}else if(equals.equals("1")){
		result = "正常";
	}else{
		result = "无数据检测";
	}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<link rel='stylesheet' type='text/css' href='<%=path%>/js/calendar/examples/redmond/theme.css' /> 
		<link rel='stylesheet' type='text/css' href='<%=path%>/js/calendar/fullcalendar.css' />
		<link rel="stylesheet" type="text/css" href="<%=path%>/ext/resources/css/ext-all.css" />
		<link rel="stylesheet" type="text/css" href="<%=path%>/css/file-upload.css" />
		<link rel="stylesheet" type="text/css" href="<%=path%>/css/layout-browser.css" />
		<link rel="stylesheet" type="text/css" href="<%=path%>/css/tabs/tab-scroller-menu.css" />
		<link rel="stylesheet" type="text/css" href="<%=path%>/css/tabs/tab-scroller-menu.gif" />
		<link rel="stylesheet" type="text/css" href="<%=path%>/ext/ux/css/GroupSummary.css" />
		<link rel="stylesheet" type="text/css" href="<%=path%>/ext/ux/css/RowEditor.css" />
		<link rel="stylesheet" type="text/css" href="<%=path%>/ext/ux/css/PanelResizer.css" />
		<link rel="stylesheet" type="text/css" href="<%=path%>/ext/ux/css/MultiSelect.css"/>
		<link rel="stylesheet" type="text/css" href="<%=path%>/ext/ux/css/Portal.css" />
    	<link rel="stylesheet" type="text/css" href="<%=path%>/ext/ux/css/GroupTab.css" />
    	<link rel="stylesheet" type="text/css" href="<%=path%>/ext/ux/css/Spinner.css" />
    	<link rel="stylesheet" type="text/css" href="<%=path%>/css/animated-dataview.css" />
    	<link rel="stylesheet" type="text/css" href="<%=path%>/ext/ux/fileuploadfield/css/fileuploadfield.css"/>
    	
		
		<title>人力资源管理系统(HRM)</title>
		<script>
		var baseMsg = eval("(<%=baseMsg%>)");
		var roleMsg = eval("(<%=roleMsg%>)");
		</script>
		<script type="text/javascript" src="<%=path%>/ext/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="<%=path%>/ext/ext-all-debug.js"></script>
		<script type="text/javascript" src="<%=path%>/ext/src/locale/ext-lang-zh_CN.js"></script>
		
		<script type="text/javascript" src="<%=path%>/ext/ux/TabScrollerMenu.js"></script>
		<script type="text/javascript" src="<%=path%>/ext/ux/TabCloseMenu.js"></script>
		<script type="text/javascript" src="<%=path%>/ext/ux/MultiSelect.js"></script>
		<script type="text/javascript" src="<%=path%>/ext/ux/ItemSelector.js"></script>
		<script type="text/javascript" src="<%=path%>/ext/ux/RowExpander.js"></script>
		<script type="text/javascript" src="<%=path%>/ext/ux/DataViewTransition.js"></script>
		
		<script type="text/javascript" src="<%=path%>/ext/ux/GroupTabPanel.js"></script>
	    <script type="text/javascript" src="<%=path%>/ext/ux/GroupTab.js"></script>
	    <script type="text/javascript" src="<%=path%>/ext/ux/Portal.js"></script>
	    <script type="text/javascript" src="<%=path%>/ext/ux/PortalColumn.js"></script>
	    <script type="text/javascript" src="<%=path%>/ext/ux/Portlet.js"></script>
	    
	    <script type="text/javascript" src="<%=path%>/ext/ux/RowEditor.js"></script>
	    <script type="text/javascript" src="<%=path%>/ext/ux/GroupSummary.js"></script>
	    <script type="text/javascript" src="<%=path%>/ext/ux/ProgressBarPager.js"></script>
	    <script type="text/javascript" src="<%=path%>/ext/ux/PanelResizer.js"></script>
	    <script type="text/javascript" src="<%=path%>/ext/ux/PagingMemoryProxy.js"></script>
	    <script type="text/javascript" src="<%=path%>/ext/ux/Spinner.js"></script>
	    <script type="text/javascript" src="<%=path%>/ext/ux/SpinnerField.js"></script>
		
		<script type="text/javascript" src="<%=path%>/ext/ux/treegrid/TreeGridSorter.js"></script>
        <script type="text/javascript" src="<%=path%>/ext/ux/treegrid/TreeGridColumnResizer.js"></script>
        <script type="text/javascript" src="<%=path%>/ext/ux/treegrid/TreeGridNodeUI.js"></script>
        <script type="text/javascript" src="<%=path%>/ext/ux/treegrid/TreeGridLoader.js"></script>
        <script type="text/javascript" src="<%=path%>/ext/ux/treegrid/TreeGridColumns.js"></script>
        <script type="text/javascript" src="<%=path%>/ext/ux/treegrid/TreeGrid.js"></script>
        
        <script type="text/javascript" src="<%=path%>/ext/ux/fileuploadfield/FileUploadField.js"></script>
		
		<script type="text/javascript" src="<%=path%>/js/sample-grid.js"></script>
		<script type="text/javascript" src="<%=path%>/js/common.js"></script>
		<script type="text/javascript" src="<%=path%>/logic/main.js"></script>
		<style type="text/css">
			.middle {
			    height: 20px;
			    padding-left: 30px;
			    padding-top:2px;
			    border-bottom: 1px #c0c0c0 solid;
			    font-size: 9pt;
			}
			.head_table {
			    width: 100%;
			    height: 100%;
			    background: url( images/main-head-bg.jpg ) center center
			}
			.head_title {
			    width: 100%;
			    text-align: center;
			    vertical-align: middle;
			    padding-top: 30px;
			    font-size: 14pt;
			    background: transparent url( images/hrm_title.bmp ) center top no-repeat
			}
			.head_button {
			    vertical-align: top;
			    width: 120px;
			}
			a.m_op_lk_1 {
			    background-position: 0 0;
			    font-size: 9pt;
			    cursor:hand;
			}
			
			a.m_op_lk_2 {
			    background-position: 0 -30px;
			    font-size: 9pt;
			    cursor:hand;
			}
			
			a.m_op_lk_3 {
			    background-position: 0 -65px;
			    font-size: 9pt;
			    cursor:hand;
			}
			.menu-title{
				align:center;
			    background: #D6E3F2;
			    border-style: solid;
			    border-color:#DAE6F4 #99bbe8 #99bbe8 #DAE6F4;
			    border-width: 1px;
			    margin:-2px -2px 0;
			    color:#15428b;
			    font:bold 10px tahoma,arial,verdana,sans-serif;
			    display:block;
			    padding:3px;
			}
			.icon_package_open{
				background-image: url(<%=path%>/ext/shared/mini/icon_package_open.gif) !important;
			}
			.upload-icon {
	            background: url('<%=path%>/ext/shared/icons/fam/image_add.png') no-repeat 0 0 !important;
	        }
			.icon-params {
	            background-image: url(<%=path%>/ext/shared/mini/icon_key.gif) !important;
	        }
	        .icon-menu1 {
	            background-image: url(<%=path%>/ext/shared/mini/box.gif) !important;
	        }
	        .icon-menu2 {
	            background-image: url(<%=path%>/ext/shared/mini/calendar.gif) !important;
	        }
	        .icon-menu3 {
	            background-image: url(<%=path%>/ext/shared/mini/icon_mail.gif) !important;
	        }
	        .icon-menu4 {
	            background-image: url(<%=path%>/ext/shared/mini/action_stop.gif) !important;
	        }
	        .page_find{
	        	background-image: url(<%=path%>/ext/shared/mini/page_find.gif) !important;
	        }
	        .add {
            	background-image:url(<%=path%>/ext/shared/icons/fam/add.gif) !important;
	        }
	        .option {
	            background-image:url(<%=path%>/ext/shared/icons/fam/plugin.gif) !important;
	        }
	        .remove {
	            background-image:url(<%=path%>/ext/shared/icons/fam/delete.gif) !important;
	        }
	        .save {
	            background-image:url(<%=path%>/ext/shared/icons/save.gif) !important;
	        }
	        .icon-user-add {
	            background-image: url(<%=path%>/ext/shared/icons/fam/user_add.gif) !important;
	        }
	        .icon-user-delete {
	            background-image: url(<%=path%>/ext/shared/icons/fam/user_delete.gif) !important;
	        } 
	        .icon-params {
	            background-image: url(<%=path%>/ext/shared/icons/fam/cog_edit.png) !important;
	        }
	        .icon-grid {
	            background-image:url(<%=path%>/ext/shared/icons/fam/grid.png) !important;
	        }
	        .x-icon-tickets {
            	background-image: url('images/tickets.png');
	        }
	        .x-icon-subscriptions {
	            background-image: url('images/subscriptions.png');
	        }
	        .x-icon-users {
	            background-image: url('images/group.png');
	        }
	        .x-icon-templates {
	            background-image: url('images/templates.png');
	        }
		    .page-up {
	            background-image:url(<%=path%>/ext/shared/mini/page_up.gif) !important;
	        }
	        .note-new{
	        	background-image:url(<%=path%>/ext/shared/mini/note_new.gif) !important;
	        }
		</style>
	</head>
	
	<body>
		<input id="user_id" name="user_id" type="hidden" value="<%=userInfo.getUserId()%>"/>
		<input id="user_name" name="user_name" type="hidden" value="<%=userInfo.getUserName()%>"/>
		<input id="role_code" name="role_code" type="hidden" value="<%=userInfo.getRoleInfo().getRoleCode() %>"/>
		<input id="role_name" name="role_name" type="hidden" value="<%=userInfo.getRoleInfo().getRoleName() %>"/>
		<div id="north-div" >
			
			<table class="head_table" border=0 cellpadding=0 cellspacing=0 >
				<tr>
					<td class="head_title">&nbsp;</td>
					<td class="head_button" width="200" noWrap>
					<div id="current_logout_div"  align="right">
           	  			
							<ul class="m_op">
								<a class="m_op_lk_1" id="modpwd" style="cursor: hand"><image src="ext/shared/icons/fam/application_view_list.png" style="height:10px">修改密码</image></a>
								<a class="m_op_lk_2" id="logout" style="cursor: hand"><image src="ext/shared/icons/fam/application_go.png" style="height:10px">退出</image></a>
							</ul>
						
           	  		</div>
           	  		</td>
				</tr>
				<tr>
					<td class="middle" colspan=2  style="width:100%;">
	           	  		<div id="current_post_div"  align="center">
	           	  			<span id="curr_position" style="width: 32%"></span>
	           	  			<span id="sysdate" style="width: 220px"></span>
	           	  			<span style="text-align:center; " >系统当前登录人：</span>
	           	  			<span id="serverdate" style="width: 200px"><font color='blue'><%=userInfo.getUserName()%></font></span>
	           	  			<span style="text-align:center; ">系统数据检测：</span>
	           	  			<% 
	           	  			if(equals.equals("0")){
	           	  			%>
	           	  			<span id="serverdate" style="width: 200px"><font color='red'><%=result%></font></span>
	           	  			<% 
	           	  			}else{
	           	  			%>
	           	  			<span id="serverdate" style="width: 200px"><font color='blue'><%=result%></font></span>
	           	  			<% 
	           	  			}
	           	  			%>
	           	  		</div> 
	                </td>
	            </tr>
	            <script>setInterval("document.getElementById('sysdate').innerHTML=new Date().toLocaleString()+' 星期'+'日一二三四五六'.charAt(new Date().getDay());",1000); </script>
			</table>
			<div id="main-menu-div"></div>
		</div>
		<div id="south-div"></div>
		<div id="east-div"></div>
		<div id="west-div"></div>
		<div id="center-div">
			<div style="margin-left:100px;">
			<!-- 
                <h2>欢迎使用该系统!</h2>
                <p>该系统的版本号为1.2!</p>
                <p>主页正在改建中....!!</p>
                <p>推荐使用<font color="blue">firfox</font>和<font color="blue">chrome</font>浏览器,已达到最高的体验效果!!!</p>
                <p>firefox火狐浏览器,版本3.5.5!!  请<a href="brower/firefox/FirefoxChinaEdition-latest.exe">点击下载</a>!</p>
                <p>chrome谷歌浏览器,版本5.0.4!!  请<a href="brower/chrome/chrome_installer.exe">点击下载</a>!</p>
             -->
            </div>
            <div id="center-div-page"></div>
		</div>

	</body>
</html>
