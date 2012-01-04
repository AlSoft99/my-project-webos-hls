$.fn.extend({
	validateform : function(){
		var val = true;
		$(this).find("input[validate],select[validate],textarea[validate]").each(function(){
			val = $(this).validate();
			if(!val){
				return val;
			}
		});
		return val;
	},
	validate:function(option){
		var varchar = /[A-Za-z0-9_]+/;
		var number = /\d/;
		var email = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
		//var password = /^(?!^\d+$|^[a-zA-Z]+$)(?:[a-zA-Z\d]{6,12})$/;
		var password = /[A-Za-z0-9_]+/;
    	/**
    	 * for example:
    	 * <input id="test" type="text" value="automaticallysentto" maxlength="225" minlength="29" errorMsg="this value is minlength {min}, maxlength {max}" regExp="/^\w{1,22}$/" validFunction="validFun" regExpMsg="this is regExp message!">
    	 * 
    	 *  //This is return function!!;
    	 * 	function validFun(a){
				a.errorMsg = "validFunction error";
				a.valid = true;
				return a;
			}
    	 */
    	var obj = $(this);
    	if(typeof(obj.attr("validate"))=="undefind"){
    		return true;
    	};
    	var id = $(this).attr("id");
		var attr = {
			isShowMsg:false,
			isChinese:true,
			maxlength:0,
			minlength:0,
			errorMsg:"",
			regExp:"",
			regExpMsg:"",
			validFunction:"",
			value:"",
			valueLength:0
		}
		//transparent red
		function drawBorder(color){
			obj.css("border","1px solid "+color);
		}
		function voluation(){
			for(var key in attr ){
				var value = obj.attr(key);
				if(typeof(value)!="undefined"){
					attr[key] = value;
				}
			}
			if(typeof(option)!="undefined"){
				for(var key in option){
					if(typeof(attr[key])!="undefined"){
						attr[key] = option[key];
					}else{
						alert("The parameter of "+key+" is error in Validate Function! Please make sure that the parameter is correct!");
					}
				}
			}
			attr.errorMsg = attr.errorMsg.replace(/{max}/g, attr.maxlength);
			attr.errorMsg = attr.errorMsg.replace(/{min}/g, attr.minlength);
		}
		function length(){
			if(attr.isChinese){
				attr.valueLength = obj.getLength(attr.value);
			}else{
				attr.valueLength = attr.value.length;
			}
		}
		function validInit(){
			voluation();
			length();
		};
		
		var judgeLength = function(){
			console.log("attr.valueLength:"+attr.valueLength);
			if(typeof(attr.minlength)!="undefined"){
				if(attr.valueLength < attr.minlength && !attr.isShowMsg){
					attr.isShowMsg = true;
				}
				attr.errorMsg += "最小长度为"+attr.minlength+" ";
			}
			if(typeof(attr.maxlength)!="undefined"){
				if(attr.valueLength > attr.maxlength && !attr.isShowMsg){
					attr.isShowMsg = true;
				}
				attr.errorMsg += "最大长度为"+attr.maxlength+" ";
			}
		};
		var judgeRegExp = function(){
			if(attr.regExp!=""){
				var regExp = eval(attr.regExp);
				attr.isShowMsg = !(regExp.test(attr.value));
				if(attr.isShowMsg){
					attr.errorMsg = attr.regExpMsg;
				}
			}
		};
		var judgeFunction = function(){
			var option = {
				valid:false,
				errorMsg:"",
				value:attr.value
			};
			if(typeof(attr.validFunction)=="undefined" || typeof(attr.validFunction)=="" || attr.validFunction==""){
				return;
			}
			option = eval(attr.validFunction+"({valid:false,errorMsg:'',value:'"+attr.value+"'})");
			if(!option.valid){
				attr.errorMsg = option.errorMsg;
				attr.isShowMsg = true;
			}else{
				attr.isShowMsg = false;
			}
		};
		validInit();
		if($(this).hasClass("default-msg")){
			attr.value = "";
		}
		if(!attr.isShowMsg){
			if(obj.attr("isnull")=="true" || obj.attr("isnull")==true){
				if($.trim(attr.value)==""){
					attr.isShowMsg = true;
					attr.errorMsg = "请不要为空 ";
				}
			}
		}
		if(!attr.isShowMsg){
			if(obj.attr("validate")=="number"){
				attr.isShowMsg = !number.test(attr.value);
				attr.errorMsg = "请输入数字 ";
			}else if(obj.attr("validate")=="email"){
				attr.isShowMsg = !email.test(attr.value);
				attr.errorMsg = "请输入正确的email地址 ";
			}else if(obj.attr("validate")=="password"){
				attr.isShowMsg = !password.test(attr.value);
				attr.errorMsg = "请输入正确的密码 ";
			}else if(obj.attr("validate")=="varchar"){
				attr.isShowMsg = !varchar.test(attr.value);
				attr.errorMsg = "请输入字母数字或者下划线 ";
			}
			judgeLength();
		}
		if(!attr.isShowMsg){
			judgeRegExp();
		}
		if(!attr.isShowMsg){
			judgeFunction();
		}
		if(attr.isShowMsg){
			obj.showErrorMessage(attr.errorMsg);
		}else{
			obj.hideErrorMessage();
		}
		return !attr.isShowMsg;
	},
	showErrorMessage:function(message){
		$(this).css("border","1px solid red");
		$(this).next(".vaildate-error").remove();
		$(this).after("<span class='icon vaildate-error ' style='background-position:-240px -200px;display:inline-block;cursor:pointer;vertical-align:middle;' title='"+message+"'></span>");
		$(".vaildate-error[title]").colorTip();
	},
	hideErrorMessage:function(){
		$(this).css("border","");
		$(this).next(".vaildate-error").remove();
		$(this).after("<span class='icon vaildate-correct' style='background-position:-240px -220px;display:inline-block;cursor:pointer;vertical-align:middle;'></span>");
	}
});