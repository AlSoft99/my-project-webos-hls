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
					<td style="width:90px;">邮箱</td><td><input type="text" class="ui-input" placeholder="输入邮箱" style="width: 420px;"></td>
				</tr>
				<tr>
					<td style="width:90px;">姓名</td><td><input type="text" class="ui-input" placeholder="输入用户姓名" style="width: 420px;"></td>
				</tr>
				<tr>
					<td style="width:90px;">密码</td><td><input type="text" class="ui-input" placeholder="输入密码" style="width: 420px;"></td>
				</tr>
				<tr>
					<td style="width:90px;">确认密码</td><td><input type="text" class="ui-input" placeholder="输入确认密码" style="width: 420px;"></td>
				</tr>
			</table>
		</form>
	</div>
	<div style="margin: auto;width:969px;">
		<div class="bl-index-top">
			<div class="bl-index-login bl-index-prev"><a href="#" id="register">立即注册</a><span>or</span><input type="button" id="login" value="登陆"/></div>
			<div class="bl-index-login bl-index-now" style="display: none;">用户<span style="color: red;">xxx</span>, 欢迎您登陆本博客!</div>
		</div>
		<div class="bl-index-body" id="index-body">
			<div class="bl-index-body-left">
				<div class="bl-index-frame">
					<div class="bl-index-title">
						<span>最新文章</span>
						<a href="register" class="float-right">更多</a>
					</div>
					<div class="bl-index-content-item">
						<div class="bl-index-content-body">
							<p><a href="article?user=guanrl&article=21768681" class="title">wordpress 优化wp_head()</a></p>
							<p><span class="time">2011-10-28 22:28</span><span class="time"> | </span><span class="time">作者:</span><a href="article?user=guanrl&article=21768681" class="time type">官瑞林</a></p>
							<p><span class="content">
	优化wp_head()就是把从wp_head中移除不需要元素，同时也可以加快速度。  步骤：  加入到function.php  remove_action(‘wp_head’, ‘wp_generator’); //wp-generator移除wordpress的版本号，本身blog的版本号没什么意义，但是如果让恶意玩家看到，可能会用官网公布的漏洞攻击blog  remove_action(‘wp_head’, ‘wlwmani ...</span></p>
							<p class="time"><span>文章分类: </span><a href="article?user=guanrl&article=21768681" class="time type">wordpress</a><span> | </span><a href="article?user=guanrl&article=21768681" class="time type"><span class="time">54</span>次阅读</a><span> | </span><a href="article?user=guanrl&article=21768681" class="time type"><span class="time">54</span>次评论</a></p>
						</div>
						<div class="bl-index-content-body">
							<p><a href="article?user=guanrl&article=21768681" class="title">wordpress 优化wp_head()</a></p>
							<p><span class="time">2011-10-28 22:28</span><span class="time"> | </span><span class="time">作者:</span><a href="article?user=guanrl&article=21768681" class="time type">官瑞林</a></p>
							<p><span class="content">
	优化wp_head()就是把从wp_head中移除不需要元素，同时也可以加快速度。  步骤：  加入到function.php  remove_action(‘wp_head’, ‘wp_generator’); //wp-generator移除wordpress的版本号，本身blog的版本号没什么意义，但是如果让恶意玩家看到，可能会用官网公布的漏洞攻击blog  remove_action(‘wp_head’, ‘wlwmani ...</span></p>
							<p class="time"><span>文章分类: </span><a href="article?user=guanrl&article=21768681" class="time type">wordpress</a><span> | </span><a href="article?user=guanrl&article=21768681" class="time type"><span class="time">54</span>次阅读</a><span> | </span><a href="article?user=guanrl&article=21768681" class="time type"><span class="time">54</span>次评论</a></p>
						</div>
						<div class="bl-index-content-body">
							<p><a href="article?user=guanrl&article=21768681" class="title">wordpress 优化wp_head()</a></p>
							<p><span class="time">2011-10-28 22:28</span><span class="time"> | </span><span class="time">作者:</span><a href="article?user=guanrl&article=21768681" class="time type">官瑞林</a></p>
							<p><span class="content">
	优化wp_head()就是把从wp_head中移除不需要元素，同时也可以加快速度。  步骤：  加入到function.php  remove_action(‘wp_head’, ‘wp_generator’); //wp-generator移除wordpress的版本号，本身blog的版本号没什么意义，但是如果让恶意玩家看到，可能会用官网公布的漏洞攻击blog  remove_action(‘wp_head’, ‘wlwmani ...</span></p>
							<p class="time"><span>文章分类: </span><a href="article?user=guanrl&article=21768681" class="time type">wordpress</a><span> | </span><a href="article?user=guanrl&article=21768681" class="time type"><span class="time">54</span>次阅读</a><span> | </span><a href="article?user=guanrl&article=21768681" class="time type"><span class="time">54</span>次评论</a></p>
						</div>
					</div>
				</div>
				<div class="bl-index-frame" style="margin-top:10px;">
					<div class="bl-index-title">
						<span>最新日记</span>
						<a href="register" class="float-right">更多</a>
					</div>
					<div class="bl-index-content-item">
						<div class="bl-index-content-body">
							<p><a href="article?user=guanrl&article=21768681" class="title">wordpress 优化wp_head()</a></p>
							<p><span class="time">2011-10-28 22:28</span><span class="time"> | </span><span class="time">作者:</span><a href="article?user=guanrl&article=21768681" class="time type">官瑞林</a></p>
							<p><span class="content">
	优化wp_head()就是把从wp_head中移除不需要元素，同时也可以加快速度。  步骤：  加入到function.php  remove_action(‘wp_head’, ‘wp_generator’); //wp-generator移除wordpress的版本号，本身blog的版本号没什么意义，但是如果让恶意玩家看到，可能会用官网公布的漏洞攻击blog  remove_action(‘wp_head’, ‘wlwmani ...</span></p>
							<p class="time"><span>文章分类: </span><a href="article?user=guanrl&article=21768681" class="time type">wordpress</a><span> | </span><a href="article?user=guanrl&article=21768681" class="time type"><span class="time">54</span>次阅读</a><span> | </span><a href="article?user=guanrl&article=21768681" class="time type"><span class="time">54</span>次评论</a></p>
						</div>
						<div class="bl-index-content-body">
							<p><a href="article?user=guanrl&article=21768681" class="title">wordpress 优化wp_head()</a></p>
							<p><span class="time">2011-10-28 22:28</span><span class="time"> | </span><span class="time">作者:</span><a href="article?user=guanrl&article=21768681" class="time type">官瑞林</a></p>
							<p><span class="content">
	优化wp_head()就是把从wp_head中移除不需要元素，同时也可以加快速度。  步骤：  加入到function.php  remove_action(‘wp_head’, ‘wp_generator’); //wp-generator移除wordpress的版本号，本身blog的版本号没什么意义，但是如果让恶意玩家看到，可能会用官网公布的漏洞攻击blog  remove_action(‘wp_head’, ‘wlwmani ...</span></p>
							<p class="time"><span>文章分类: </span><a href="article?user=guanrl&article=21768681" class="time type">wordpress</a><span> | </span><a href="article?user=guanrl&article=21768681" class="time type"><span class="time">54</span>次阅读</a><span> | </span><a href="article?user=guanrl&article=21768681" class="time type"><span class="time">54</span>次评论</a></p>
						</div>
						<div class="bl-index-content-body">
							<p><a href="article?user=guanrl&article=21768681" class="title">wordpress 优化wp_head()</a></p>
							<p><span class="time">2011-10-28 22:28</span><span class="time"> | </span><span class="time">作者:</span><a href="article?user=guanrl&article=21768681" class="time type">官瑞林</a></p>
							<p><span class="content">
	优化wp_head()就是把从wp_head中移除不需要元素，同时也可以加快速度。  步骤：  加入到function.php  remove_action(‘wp_head’, ‘wp_generator’); //wp-generator移除wordpress的版本号，本身blog的版本号没什么意义，但是如果让恶意玩家看到，可能会用官网公布的漏洞攻击blog  remove_action(‘wp_head’, ‘wlwmani ...</span></p>
							<p class="time"><span>文章分类: </span><a href="article?user=guanrl&article=21768681" class="time type">wordpress</a><span> | </span><a href="article?user=guanrl&article=21768681" class="time type"><span class="time">54</span>次阅读</a><span> | </span><a href="article?user=guanrl&article=21768681" class="time type"><span class="time">54</span>次评论</a></p>
						</div>
					</div>
				</div>
				<div class="bl-index-frame" style="margin-top:10px;">
					<div class="bl-index-title">
						<span>最新图片</span>
						<a href="register" class="float-right">更多</a>
					</div>
					<div class="bl-index-list">
						<div class="bl-index-item float">
							<div class="bl-index-photo"><img src="stylesheet/img/120_0_0.gif" ></div>
							<div><span>官瑞林</span></div>
						</div>
						<div class="bl-index-item float">
							<div class="bl-index-photo"><img src="stylesheet/img/120_0_0.gif" ></div>
							<div><span>官瑞林</span></div>
						</div>
						<div class="bl-index-item float">
							<div class="bl-index-photo"><img src="stylesheet/img/120_0_0.gif" ></div>
							<div><span>官瑞林</span></div>
						</div>
						<div class="bl-index-item float">
							<div class="bl-index-photo"><img src="stylesheet/img/120_0_0.gif" ></div>
							<div><span>官瑞林</span></div>
						</div>
						<div class="bl-index-item float">
							<div class="bl-index-photo"><img src="stylesheet/img/120_0_0.gif" ></div> 
							<div><span>官瑞林</span></div>
						</div>
						<div class="bl-index-item float">
							<div class="bl-index-photo"><img src="stylesheet/img/120_0_0.gif" ></div>
							<div><span>官瑞林</span></div>
						</div>
						<div class="bl-index-item float">
							<div class="bl-index-photo"><img src="stylesheet/img/120_0_0.gif" ></div>
							<div><span>官瑞林</span></div>
						</div>
						<div class="clear"></div>
					</div>
				</div>
			</div>
			<div class="bl-index-body-right">
				<div class="bl-index-title">
					<span>关注的人</span>
					<a href="register" class="float-right">更多</a>
				</div>
				<div class="bl-index-list">
					<div class="bl-index-item">
						<div class="bl-index-photo"><img src="stylesheet/img/120_0_0.gif" ></div>
						<div><span>官瑞林</span></div>
					</div>
					<div class="bl-index-item">
						<div class="bl-index-photo"><img src="stylesheet/img/120_0_0.gif" ></div>
						<div><span>官瑞林</span></div>
					</div>
					<div class="bl-index-item">
						<div class="bl-index-photo"><img src="stylesheet/img/120_0_0.gif" ></div>
						<div><span>官瑞林</span></div>
					</div>
					<div class="bl-index-item">
						<div class="bl-index-photo"><img src="stylesheet/img/120_0_0.gif" ></div>
						<div><span>官瑞林</span></div>
					</div>
					<div class="bl-index-item">
						<div class="bl-index-photo"><img src="stylesheet/img/120_0_0.gif" ></div>
						<div><span>官瑞林</span></div>
					</div>
					<div class="bl-index-item">
						<div class="bl-index-photo"><img src="stylesheet/img/120_0_0.gif" ></div>
						<div><span>官瑞林</span></div>
					</div>
					<div class="bl-index-item">
						<div class="bl-index-photo"><img src="stylesheet/img/120_0_0.gif" ></div>
						<div><span>官瑞林</span></div>
					</div>
					<div class="bl-index-item">
						<div class="bl-index-photo"><img src="stylesheet/img/120_0_0.gif" ></div>
						<div><span>官瑞林</span></div>
					</div>
				</div>
			</div>
			<div class="clear"></div>
		</div>
	</div>
</body>
</html>