<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'menu_manager.jsp' starting page</title>
    
	<script type="text/javascript" src="<%=path%>/logic/menu_manager.js"></script>
    <style type="text/css">
        .icon-grid {
            background-image:url(<%=path%>/ext/shared/icons/fam/grid.png) !important;
        }
        #button-grid .x-panel-body {
            border:1px solid #99bbe8;
            border-top:0 none;
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
        .x-tree-node-leaf .tree-add{
		  	background-image: url(<%=path%>/ext/resources/images/default/tree/folder.gif) !important;
		}
		.x-tree-node-leaf .tree-del{
		  	background-image: url(<%=path%>/ext/resources/images/default/tree/delete.gif) !important;
		}
        
    </style>

  </head>
  
  <body>
  	<table>
  		<tr>
  			<td valign="top"><div id="menu_manager_role"></div></td>
  			<td valign="top"><div id="menu_manager_menu"></div></td>
  			<td valign="top"><div id="menu_manager_role_menu"></div></td>
  		</tr>
  	</table>
    
    
  </body>
</html>
