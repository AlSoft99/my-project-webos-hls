<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.ux.entity.UserInfo"%>
<% 
UserInfo userInfo = (UserInfo)request.getSession().getAttribute("userinfo");
String username = "";
if(userInfo!=null){
	username = userInfo.getUsername();
}
%>
<link type="text/css" rel="stylesheet" href="lib/plugin/colortip/colortip.jquery.css" media="screen" />
<script type="text/javascript" src="lib/plugin/colortip/colortip.jquery.js"></script>
<script type="text/javascript" src="lib/plugin/jquery.validate.js"></script>
<div id="dialog-register" style="display: none;" title="<span style='padding:3px 0;float:left;'>注册</span>">
	<form style="margin-top: 15px;">
		<table style="width:100%;">
			<tr>
				<td style="width:90px;">邮箱</td><td><input type="text" class="ui-input" id="userid" name="userid"  validate="email" validateAjax="checkid.do" isnull=true maxlength="100" minlength="0" placeholder="输入邮箱" style="width: 380px;"></td>
			</tr>
			<tr>
				<td style="width:90px;">姓名</td><td><input type="text" class="ui-input" id="username" name="username"  validate="default" isnull=true maxlength="20" minlength="0" placeholder="输入用户姓名" style="width: 380px;"></td>
			</tr>
			<tr>
				<td style="width:90px;">密码</td><td><input type="password" class="ui-input" id="password" name="password" confirm="con-password"  validate="password" isnull=true maxlength="20" minlength="0" placeholder="输入密码" style="width: 380px;"></td>
			</tr>
			<tr>
				<td style="width:90px;">确认密码</td><td><input type="password" class="ui-input" id="con-password" confirm="password" validate="password" isnull=true maxlength="20" minlength="0" placeholder="输入确认密码" style="width: 380px;"></td>
			</tr>
		</table>
	</form>
</div>
<div id="dialog-login" style="display: none;" title="<span style='padding:3px 0;float:left;'>登陆</span>">
	<form style="margin-top: 15px;">
		<table style="width:100%;">
			<tr>
				<td style="width:90px;">邮箱</td><td><input type="text" class="ui-input" id="userid" name="userid"  validate="email" isnull=true maxlength="100" minlength="0" placeholder="输入邮箱" style="width: 380px;"></td>
			</tr>
			<tr>
				<td style="width:90px;">密码</td><td><input type="password" class="ui-input" id="password" name="password" validate="password" isnull=true maxlength="20" minlength="0" placeholder="输入密码" style="width: 380px;"></td>
			</tr>
		</table>
	</form>
</div>
<div class="index-title">
	<div class="index-title-frame">
		<div class="float"><a class="logo float" style="margin-top: 10px;" href="#"></a>&nbsp;&nbsp;<span <% if(userInfo!=null) {%>style="display:none;"<%}%> id="registerfield"><a class="float index-logo" id="register-btn" style="padding-left: 10px;" href="#">注册</a>&nbsp;&nbsp;<a class="float index-logo" id="login-btn" style="padding-left: 10px;" href="#">登陆</a></span><span id="loginfield" <% if(userInfo==null) {%>style="display:none;"<%}%>><label id="loginname" class="float" style="padding:30px 0 10px 10px;font-size: 13px;" ><%=username %></label><label class="float" style="padding:30px 0 10px;font-size: 13px;" > ,欢迎您登陆本Bolog</label><a class="float index-logo" style="padding-left: 10px;" href="main">我的后台</a><a class="float index-logo" id="logout-btn" style="padding-left: 10px;" href="#">退出</a></span></div>
		<div class="float-right index-menu">
			<ul class="float-right">
				<li class="select"><a href="index" >首页</a></li>
				<!-- li><a href="author">日记</a></li>
				<li><a href="#">相集</a></li> -->
				<li><a href="#">留言板</a></li>
				<li><a href="#">关于</a></li>
			</ul>
		</div>
		<div class="clear"></div>
	</div>
</div>
<script>
$(function(){
	$("#dialog-register form, #dialog-login form").validateInit();
	function register(){
		var check = $("#dialog-register form").validateForm();
		if(check){
			$("body").loading("open");
			$.get("register.do?method=add&"+$("#dialog-register form").serialize(),function(val){
				if(val=="success"){
					$.toast("注册成功!");
					$("#dialog-register form input").val("");
					$("#dialog-register form").reset();
					$( "#dialog-register" ).dialog("close");
				}else{
					$.toast("注册失败, 请联系管理员!");
				}
				$("body").loading("close");
			});
			/* $.ajax({
				  url: "register.do?method=add&"+$("#dialog-register form").serialize(),
				  success: function(val){
						console.log(val);
				},
			}); */
		}else{
			
		}
	};
	function login(){
		var check = $("#dialog-login form").validateForm();
		if(check){
			$("body").loading("open");
			$.getJSON("register.do?method=login&"+$("#dialog-login form").serialize(),function(val){
				if(val.status=="success"){
					$.toast("登陆成功!");
					$( "#dialog-login" ).dialog( "close" );
					$("#loginname").text(val.username);
					$("#registerfield").hide();
					$("#loginfield").show();
					
				}else{
					$.toast("登陆失败, 请确认邮箱和密码!");
				}
				$("body").loading("close");
			});
			/* $.ajax({
				  url: "register.do?method=add&"+$("#dialog-register form").serialize(),
				  success: function(val){
						console.log(val);
				},
			}); */
		}
	}
	$( "#dialog-register" ).dialog({
		resizable: true,
		height:280,
		width:565,
		autoOpen: false,
		modal: true,
		buttons: {
			"注册": function() {
				//$( this ).dialog( "close" );
				register();
			},
			"关闭": function() {
				$( this ).dialog( "close" );
			}
		},
		close: function() {
			
		}
	});
	$( "#dialog-login" ).dialog({
		resizable: true,
		height:200,
		width:565,
		autoOpen: false,
		modal: true,
		buttons: {
			"登陆": function() {
				login();
				//$( this ).dialog( "close" );
			},
			"关闭": function() {
				$( this ).dialog( "close" );
			}
		},
		close: function() {
			
		}
	});
	$("#register-btn").click(function(){
		$( "#dialog-register" ).dialog("open");
		return false;
	});
	$("#login-btn").click(function(){
		$( "#dialog-login" ).dialog("open");
		return false;
	});
	$("#logout-btn").click(function(){
		$.get("register.do?method=logout",function(val){
			if(val=="success"){
				$.toast("退出成功!");
				$("#registerfield").show();
				$("#loginfield").hide();
			}
		});
	});
});
</script>