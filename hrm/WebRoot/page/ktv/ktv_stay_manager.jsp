<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'user_add.jsp' starting page</title>
    <script type="text/javascript" src="<%=path%>/logic/ktv_stay_manager.js"></script>
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
    </style>
    
  </head>
  
  <body>
    <div id="ktv_stay_manager_table"></div>
  </body>
</html>
