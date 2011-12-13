<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.io.*"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="org.apache.commons.codec.binary.*"%>
<%@ page import="org.apache.commons.fileupload.disk.DiskFileItemFactory"%>
<%@ page import="org.apache.commons.fileupload.FileItemFactory"%>
<%@ page import="org.apache.commons.fileupload.servlet.ServletFileUpload"%>
<%@ page import="org.apache.commons.fileupload.FileItem"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>SWFUpload简单使用例子 Java版（JSP）</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

<script type="text/javascript" src="swfupload/swfupload.js"></script>
<script type="text/javascript" src="js/swfupload.queue.js"></script>
<script type="text/javascript" src="js/fileprogress.js"></script>
<script type="text/javascript" src="js/handlers.js"></script>

<link href="css/default.css" rel="stylesheet" type="text/css" />

<script type="text/javascript">
	var upload;
	window.onload = function() {
		upload = new SWFUpload(
				{
					// 处理文件上传的url  ${pageContext.request.contextPath}
					upload_url : "/swfupload/example.jsp?upload=1", // 路径写全，否则Firefox下会出现404错误。自由修改处一：处理文件上传的url路径，注意还要写全部

					// 上传文件限制设置
					file_size_limit : "10240", // 10MB
					file_types : "*.jpg;*.gif;*.png", //此处也可以修改成你想限制的类型，比如：*.doc;*.wpd;*.pdf
					file_types_description : "Image Files",
					file_upload_limit : "0",
					file_queue_limit : "10",

					// 事件处理设置（所有的自定义处理方法都在handler.js文件里）
					file_dialog_start_handler : fileDialogStart,
					file_queued_handler : fileQueued,
					file_queue_error_handler : fileQueueError,
					file_dialog_complete_handler : fileDialogComplete,
					upload_start_handler : uploadStart,
					upload_progress_handler : uploadProgress,
					upload_error_handler : uploadError,
					upload_success_handler : uploadSuccess,
					upload_complete_handler : uploadComplete,

					// 按钮设置
					button_image_url : "swfupload/xpbutton.png", // 按钮图标
					button_placeholder_id : "spanButtonPlaceholder",
					button_width : 61,
					button_height : 22,

					// swf设置
					flash_url : "swfupload/swfupload.swf",

					custom_settings : {
						progressTarget : "fsUploadProgress",
						cancelButtonId : "btnCancel"
					},

					// Debug 设置
					debug : false
				});
	}
</script>

</head>
<%
	String uploadSign = request.getParameter("upload");
	String rootPath = request.getParameter("rootPath");
	String path = request.getParameter("path");
	if (rootPath == null)
		rootPath = "";
	rootPath = rootPath.trim();
	if (rootPath.equals("")) {
		rootPath = application.getRealPath("files");//自由修改处二：指定服务器固定文件
	}

	if (path == null) {
		path = rootPath;
	} else {
		path = new String(Base64.decodeBase64(path.getBytes()));
	}
%>
<%
	//上传操作
	if (null != uploadSign && !"".equals(uploadSign)) {
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		//upload.setHeaderEncoding("UTF-8");
		try {
			List items = upload.parseRequest(request);
			System.out.println("items:"+items.size());
			if (null != items) {
				Iterator itr = items.iterator();
				while (itr.hasNext()) {
					FileItem item = (FileItem) itr.next();
					if (item.isFormField()) {
						continue;
					} else {
						//自由修改处三：可修改上传后的文件命名方式
						SimpleDateFormat sdf = new SimpleDateFormat(
								"yyyyMMddkkmmss");//以当前精确到秒的日期为上传的文件的文件名
						String type = item.getName().split("\\.")[1];//获取文件类型
						File savedFile = new File(path,
								sdf.format(new Date()) + "." + type);
						item.write(savedFile);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
%>
<body>
	<div class="flash" id="fsUploadProgress"></div>
	<div style="padding-left: 5px;">
		<span id="spanButtonPlaceholder"></span> <input id="btnCancel"
			type="button" value="取消" onclick="cancelQueue(upload);"
			disabled="disabled"
			style="margin-left: 2px; height: 22px; font-size: 8pt;" />
	</div>
</body>
</html>
