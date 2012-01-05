<link type="text/css" rel="stylesheet" href="lib/plugin/colortip/colortip.jquery.css" media="screen" />
<script type="text/javascript" src="lib/plugin/colortip/colortip.jquery.js"></script>
<script type="text/javascript" src="lib/plugin/jquery.validate.js"></script>
<div id="dialog-register" style="display: none;" title="<span style='padding:3px 0;float:left;'>注册</span>">
	<form style="margin-top: 15px;">
		<table style="width:100%;">
			<tr>
				<td style="width:90px;">邮箱</td><td><input type="text" class="ui-input"  validate="email" isnull=true maxlength="100" minlength="0" placeholder="输入邮箱" style="width: 380px;"></td>
			</tr>
			<tr>
				<td style="width:90px;">姓名</td><td><input type="text" class="ui-input"  validate="varchar" isnull=true maxlength="20" minlength="0" placeholder="输入用户姓名" style="width: 380px;"></td>
			</tr>
			<tr>
				<td style="width:90px;">密码</td><td><input type="text" class="ui-input"  validate="password" isnull=true maxlength="20" minlength="0" placeholder="输入密码" style="width: 380px;"></td>
			</tr>
			<tr>
				<td style="width:90px;">确认密码</td><td><input type="text" class="ui-input"  validate="password" isnull=true maxlength="20" minlength="0" placeholder="输入确认密码" style="width: 380px;"></td>
			</tr>
		</table>
	</form>
</div>
<div class="index-title">
	<div class="index-title-frame">
		<div class="float"><a class="logo index-logo float" href="#"> | 为用户创造优质的在线生活体验</a>&nbsp;&nbsp;<a class="float index-logo" id="register-btn" style="padding-left: 10px;" href="#">注册</a>&nbsp;&nbsp;<a class="float index-logo" id="login-btn" style="padding-left: 10px;" href="#">登陆</a></div>
		<div class="float-right index-menu">
			<ul class="float-right">
				<li class="select"><a href="index" >首页</a></li>
				<li><a href="author">日记</a></li>
				<li><a href="#">相集</a></li>
				<li><a href="#">留言板</a></li>
				<li><a href="#">关于</a></li>
			</ul>
		</div>
		<div class="clear"></div>
	</div>
</div>
<script>
$(function(){
	function validateForm(){
		
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
				$("#dialog-register form").validateform();
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
});
</script>