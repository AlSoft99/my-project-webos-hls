<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% 

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" />
<title>官家部落格</title>
<!-- CSS ../../../-->
<link type="text/css" rel="stylesheet" href="stylesheet/css/main.css" media="screen" />
<link type="text/css" rel="stylesheet" href="lib/ui/css/redmond/jquery-ui-custom.css" media="screen" />
<link type="text/css" rel="stylesheet" href="stylesheet/css/index.css" media="screen" />
<link type="text/css" rel="stylesheet" href="modules/css/index/index-article.css" media="screen" />
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
						<span>文章</span>
					</div>
					<div class="bl-index-content-item">
						<div class="bl-index-content-body">
							<h1>wordpress 优化wp_head()</h1>
							<p><span class="time">已有 </span><span class="time">58</span><span class="time">次阅读</span><span class="time"> | </span><span class="time">2011-10-28 22:28</span><span class="time"> | </span><span class="time">作者:</span><a href="#" class="time type">官瑞林</a><span class="time"> | </span><span class="time">文章分类:</span><span class="time">wordpress</span></p>
							<p><div class="content">优化wp_head()就是把从wp_head中移除不需要元素，同时也可以加快速度。
							步骤：
							加入到function.php
							remove_action(‘wp_head’, ‘wp_generator’);
							//wp-generator移除wordpress的版本号，本身blog的版本号没什么意义，但是如果让恶意玩家看到，可能会用官网公布的漏洞攻击blog
							remove_action(‘wp_head’, ‘wlwmanifest_link’);
							//wlwmanifest_link移除wlwmanifest相关信息
							remove_action(‘wp_head’,'rsd_link’);
							//rsd_link移除XML-RPC
							说明：wlwmanifest_link和rsd_link是为了去除这两行代码：
							这两行标记都是针对Blog的离线编辑器开放接口所使用的。其中RSD是一个广义的接口，wlwmanifest是针对微软Live Writer编辑器的。有了这两个接口，在使用离线编辑器撰写博客的时候，就可以直接在软件中选择分类，标签等等内容了。如果你不需要离线编辑，却又认为开放的两个接口不够安全，那么就可以禁用掉RSD和wlwmanifest：
							加入到header.php的wp_head();上一行（上面的也可以直接放在这个地方）
							wp_deregister_script(‘jquery’);
							在wp_head();前边加上wp_deregister_script主要作用是去除默认调用的jquery，这个只能在头部文件使用注销默认的调用js文件。去除之后，可以用google的jquery库，减少自身服务器的负担。
							方法：在head.php或footer.php文件中（本博在footer.php中）加入
							</div></p>
						</div>
						<div class="bl-index-content-discuss">
							<div class="discuss-frame">
								<h5 class="discuss-sum">评论 (<span>1</span> 个评论)</h5>
								<div class="discuss-list">
									<div class="discuss-item">
										<dl>
											<dd class="discuss-photo float"><a href="#"><img src="stylesheet/img/120_0_0.gif" /></a></dd>
											<dt><a href="#" style="font-weight: bold;">rayln</a>&nbsp;<span>2011-12-28 09:22</span><a class="float-right" href="#">删除</a><a class="float-right" href="#">编辑</a></dt>
											<dd class="discuss-content">离线编辑器开放接口所使用的。其中RSD是一个广义的接口，wlwmanifest是针对微软Live Writer编辑器的。有了这两个接口，在使用离线编辑器撰写博客的时候，就可以直接在软件中选择分类，标签等等内容了</dd>
											<dd class="clear"></dd>
										</dl>
										<dl>
											<dd class="discuss-photo float"><a href="#"><img src="stylesheet/img/120_0_0.gif" /></a></dd>
											<dt><a href="#" style="font-weight: bold;">rayln</a>&nbsp;<span>2011-12-28 09:22</span><a class="float-right" href="#">删除</a><a class="float-right" href="#">编辑</a></dt>
											<dd class="discuss-content">离线编辑器开放接口所使用的。其中RSD是一个广义的接口，wlwmanifest是针对微软Live Writer编辑器的。有了这两个接口，在使用离线编辑器撰写博客的时候，就可以直接在软件中选择分类，标签等等内容了</dd>
											<dd class="clear"></dd>
										</dl>
									</div>
								</div>
								<div>
									
								</div>
							</div>
							
						</div>
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