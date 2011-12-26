<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" />
<title>官家部落格</title>
<!-- CSS -->
<link type="text/css" rel="stylesheet" href="stylesheet/css/index.css" media="screen" />
<link type="text/css" rel="stylesheet" href="stylesheet/css/main.css" media="screen" />
<link type="text/css" rel="stylesheet" href="lib/ui/css/redmond/jquery-ui-custom.css" media="screen" />
<!-- JavaScripts-->
<script type="text/javascript" src="lib/ui/js/jquery.min.js"></script>
<script type="text/javascript" src="lib/ui/js/jquery.ui.custom.min.js"></script>
<script type="text/javascript" src="lib/common.js"></script>
<script type="text/javascript" src="modules/index.js"></script>
</head>
<body>
	<div id="dialog-register" title="<span style='padding:3px 0;float:left;'>注册</span>">
		<form>
			<table style="width:100%;">
				<tr>
					<td style="width:90px;">邮箱</td><td><input type="text" class="ui-input" defaultMsg="输入邮箱" style="width: 420px;"></td>
				</tr>
				<tr>
					<td style="width:90px;">姓名</td><td><input type="text" class="ui-input" defaultMsg="输入用户姓名" style="width: 420px;"></td>
				</tr>
				<tr>
					<td style="width:90px;">密码</td><td><input type="text" class="ui-input" defaultMsg="输入密码" style="width: 420px;"></td>
				</tr>
				<tr>
					<td style="width:90px;">确认密码</td><td><input type="text" class="ui-input" defaultMsg="输入确认密码" style="width: 420px;"></td>
				</tr>
			</table>
		</form>
	</div>
	<div class="bl-index-top">
		<div class="bl-index-login bl-index-prev"><a href="#" id="register">立即注册</a><span>or</span><input type="button" id="login" value="登陆"/></div>
		<div class="bl-index-login bl-index-now" style="display: none;">用户<span style="color: red;">xxx</span>, 欢迎您登陆本博客!</div>
	</div>
	<div class="bl-index-man">
		<div class="bl-index-title">
			<span>关注的人</span>
			<a href="register" class="float-right">更多</a>
		</div>
		<div class="bl-index-list">
			<div class="bl-index-item">
				<div><img src="stylesheet/img/120_0_0.gif" width="100" height="100"></div>
				<div><span>官瑞林</span></div>
			</div>
			<div class="bl-index-item">
				<div><img src="stylesheet/img/120_0_0.gif" width="100" height="100"></div>
				<div><span>官瑞林</span></div>
			</div>
			<div class="bl-index-item">
				<div><img src="stylesheet/img/120_0_0.gif" width="100" height="100"></div>
				<div><span>官瑞林</span></div>
			</div>
			<div class="bl-index-item">
				<div><img src="stylesheet/img/120_0_0.gif" width="100" height="100"></div>
				<div><span>官瑞林</span></div>
			</div>
			<div class="bl-index-item">
				<div><img src="stylesheet/img/120_0_0.gif" width="100" height="100"></div>
				<div><span>官瑞林</span></div>
			</div>
			<div class="bl-index-item">
				<div><img src="stylesheet/img/120_0_0.gif" width="100" height="100"></div>
				<div><span>官瑞林</span></div>
			</div>
			<div class="bl-index-item">
				<div><img src="stylesheet/img/120_0_0.gif" width="100" height="100"></div>
				<div><span>官瑞林</span></div>
			</div>
			<div class="bl-index-item">
				<div><img src="stylesheet/img/120_0_0.gif" width="100" height="100"></div>
				<div><span>官瑞林</span></div>
			</div>
			<div class="clear"></div>
		</div>
	</div>
</body>
</html>