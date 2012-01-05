function MishangUI(c, a) {
	var b = this;
	this.wrapper = c;
	this.authorized = a && a.authorized ? a.authorized : false;
	this.ajaxLoader = new AjaxLoader(this);
	if (this.wrapper.find("#layoutContainer").length > 0) {
		this.floatingLayout = new Layout(this.ajaxLoader, {
			easing : false
		})
	} else {
		this.floatingLayout = false
	}
	this.popUpList = [ [ "#newBoard", "/board/create/" ],
			[ "#editBoard", "/board/edit/", "value" ],
			[ ".header_pub", "/pin/new_pin/" ], [ "#newPin", "/pin/upload/" ],
			[ "#newPinInCurrBoard", "/pin/upload/", "value" ],
			[ "#pinActionBtn .repin", "/pin/new_repin/", "value" ],
			[ "#pinActionBtn .editPin", "/pin/edit/", "value" ],
			[ ".a-btn-location", "/account/loccity/" ],
			[ "#a-btn-password", "/editpassword/" ],
			[ "#feedback,#slidefeedback", "/feedback/" ],
			[ "#wel_mishang", "/wel_mishang" ],
			[ ".messageUser", "/messages/compose/", "value" ] ];
	this.existPop;
	this.init = function() {
		this.adjustWidth();
		this.initIndieBox();
		this.initSecNav();
		this.initPostForm();
		this.initPinPicker();
		this.instantiatingPopUp();
		this.initCommentBlocks();
		this.initConfirmation();
		this.initCarousel();
		this.initPlaceHolder();
		this.initInstantUpload();
		this.initSlideWidget();
		this.initCharCount();
		$(window).resize(function() {
			if (b.floatingLayout && b.floatingLayout.needResetPosition()) {
				var e = b.floatingLayout.setWidth();
				$(b.wrapper).css({
					width : e
				});
				$(".header").children("div").css({
					width : e
				});
				var f = b.floatingLayout.setAllPin();
				b.floatingLayout.resetPosition(f)
			}
		});
		$(window).focus();
		this.wrapper.animate({
			opacity : 1
		});
		var d = false;
		$(window).scroll(
				function() {
					if ($(".coverFixed") && $.browser.msie
							&& $.browser.version == "6.0") {
						var e = $(".coverFixed");
						e.css({
							position : "absolute",
							top : $(window).scrollTop()
						})
					}
					var g = $(".header");
					var f = $(window).scrollTop();
					if (f > 95 && d == false) {
						d = true;
						g.addClass("fixheader")
					} else {
						if (f <= 95 && d == true) {
							d = false;
							g.removeClass("fixheader")
						}
					}
				});
		CommentInteractions.gridComment(b.floatingLayout)
	};
	this.initIndieBox = function() {
		var d = $(".popContainer");
		if (d.size() > 0) {
			this.existPop = new MishangBox("#existPop");
			this.existPop.adjustSize()
		}
	};
	this.adjustWidth = function() {
		var d = $("html").width();
		var h = $(".fixedWidthContainer");
		if (h.length > 0) {
			var g = h.width();
			$(this.wrapper).css({
				width : g
			});
			var j = $(".pin");
			for ( var f = 0; f < j.length; f++) {
				var e = new Pin($(j[f]))
			}
		} else {
			if (this.floatingLayout) {
				var g = this.floatingLayout.setWidth();
				$(this.wrapper).css({
					width : g
				});
				var j = this.floatingLayout.setAllPin();
				this.floatingLayout.setData(j);
				this.ajaxLoader.scrollListener()
			}
		}
		$(".header").children("div").css({
			width : g
		})
	};
	this.initPostForm = function() {
		var d = $("form.mishangForm");
		for ( var e = 0; e < d.length; e++) {
			var f = new Form(d[e])
		}
	};
	this.initPinPicker = function() {
		var e = $("#pinImage");
		if (e.length > 0) {
			var d = new Image();
			d.src = e.find("img").attr("src")
		}
	};
	this.initSecNav = function() {
		$("ul li").each(function() {
			var d = $(this).find("ul.secNav").length > 0 ? true : false;
			if (d) {
				$(this).hover(function() {
					$(this).addClass("current");
					$(this).find("ul.secNav").show()
				}, function() {
					$(this).removeClass("current");
					$(this).find("ul.secNav").hide()
				})
			}
		})
	};
	this.instantiatingPopUp = function() {
		var e = this;
		for ( var d in this.popUpList) {
			if ($(this.popUpList[d][0]).size() > 0) {
				$(this.popUpList[d][0]).each(
						function() {
							if (e.popUpList[d][2]) {
								var g = e.popUpList[d][1]
										+ $(this).attr(e.popUpList[d][2]) + "/"
							} else {
								var g = e.popUpList[d][1]
							}
							var f = new MishangBox("#tempPop", $(this), g);
							f.init()
						})
			}
		}
	};
	this.initCommentBlocks = function() {
		var f = $(".commentBlock");
		for ( var d = 0; d < f.length; d++) {
			var e = new CommentBlock($(f[d]))
		}
	};
	this.initConfirmation = function() {
		var d = $(".confirm");
		d.each(function() {
			if ($(this).parents(".pin").length == 0) {
				var e = new Confirm($(this))
			}
		})
	};
	this.initCarousel = function() {
		var d = $(".carousel");
		d.each(function() {
			var e = new Carousel($(this), ".carouselBlock", {
				timeInterval : 4000
			})
		})
	};
	this.initInstantUpload = function() {
		var d = $("form.InstantUpload");
		d.each(function() {
			var e = new InstantUploadLink($(this))
		})
	};
	this.initSlideWidget = function() {
		$("#slideWidget").find("#backToTop").click(function() {
			$("html , body").animate({
				scrollTop : 0
			}, 500)
		});
		$(window).scroll(
				function() {
					if ($(window).scrollTop() > 0) {
						$("#slideWidget").find(
								"#backToTop,#slidefeedback,#mishang_help")
								.fadeIn()
					} else {
						$("#slideWidget").find(
								"#backToTop,#slidefeedback,#mishang_help")
								.fadeOut()
					}
				})
	};
	this.initPlaceHolder = function() {
		var d = $("input[placeholder],textarea[placeholder]");
		d.each(function() {
			if (!$(this).attr("manual")) {
				var e = new PlaceHolder($(this))
			}
		})
	};
	this.initCharCount = function() {
		var d = $(".charInput");
		d.each(function() {
			var e = new CharCheck($(this));
			e.init()
		})
	};
	this.init()
}
function Layout(b, a) {
	this.gap = 16;
	this.pinWidth = 193 + 30 + this.gap;
	this.cols;
	this.easing = a.easing;
	this.initPins;
	this.allPins;
	this.extraWidth;
	this.extraHeight;
	this.ajaxLoader = b;
	this.positionCage = null;
	this.getLayoutContainer = function() {
		return $("#layoutContainer")
	};
	this.extraWidth = parseInt(this.getLayoutContainer().attr("extraWidth")) || 0;
	if (typeof (this.getLayoutContainer().attr("extraHeight")) != "undefined") {
		this.extraHeight = parseInt(this.getLayoutContainer().attr(
				"extraHeight"))
	} else {
		this.extraHeight = 0
	}
	this.init()
}
Layout.prototype.init = function() {
	this.initPins = $(".pin");
	for ( var b = 0; b < this.initPins.length; b++) {
		var a = new Pin($(this.initPins[b]))
	}
};
Layout.prototype.setAllPin = function() {
	this.allPins = this.getLayoutContainer().find(".pin");
	return this.allPins
};
Layout.prototype.updateHeight = function(g, c, b) {
	var f = this.getLayoutContainer().find(".pin");
	for ( var e = 0; e < f.length; e++) {
		var h = $(f[e]).attr("column");
		var d = parseInt($(f[e]).css("top"));
		if (h == g && d > b) {
			$(f[e]).css({
				top : d + c
			})
		}
	}
	var a = this.positionCage[g];
	this.positionCage[g] = a + c;
	this._setContainerHeight()
};
Layout.prototype.needResetPosition = function() {
	newCols = this.getWidthCols().cols;
	if (newCols != this.cols) {
		return true
	} else {
		return false
	}
};
Layout.prototype.resetPosition = function(d) {
	var e = this;
	for ( var c = 0; c < d.length; c++) {
		var a = e._getHighest(c);
		var b = $(d[c]);
		e.setPosition(b, a)
	}
};
Layout.prototype.getWidthCols = function() {
	var e = this;
	var a = $("body").width();
	var c = this.extraWidth > 0 ? (this.pinWidth * 3 + this.extraWidth)
			: (this.pinWidth * 4);
	var b, d;
	if (a < c) {
		d = this.extraWidth > 0 ? 3 : 4;
		b = c
	} else {
		var d = Math.floor((a - this.extraWidth) / this.pinWidth);
		if (this.extraWidth > 0) {
			d = d > 4 ? 4 : d
		} else {
			d = d > 5 ? 5 : d
		}
		b = this.pinWidth * d + this.extraWidth
	}
	return {
		width : b,
		cols : d
	}
};
Layout.prototype.setWidth = function() {
	var b = this;
	var a = this.getWidthCols();
	width = a.width;
	this.cols = a.cols;
	this._setPositionCage();
	this.getLayoutContainer().width(width - b.extraWidth - b.gap);
	return width
};
Layout.prototype.setData = function(e, d) {
	var f = this;
	if ($.browser.msie && $.browser.version != "6.0") {
		this._setDataIE(e, d)
	} else {
		for ( var c = 0; c < e.length; c++) {
			var a = f._getHighest(c);
			var b = $(e[c]);
			f.setPosition(b, a)
		}
	}
	this.ajaxLoader.loadingFlag = false
};
Layout.prototype.setPosition = function(b, a) {
	b[0].setAttribute("column", a.highestCol);
	b[0].style.cssText = "top:" + a.coordination[1] + "px;left:"
			+ a.coordination[0] + "px";
	b.hover(function() {
		$(this).find(".pinBtns").show()
	}, function() {
		$(this).find(".pinBtns").hide()
	});
	this.positionCage[a.highestCol] = a.coordination[1] + b.outerHeight();
	if ($.browser.msie) {
		b.show();
		this._setContainerHeight()
	} else {
		b.fadeIn(500)
	}
	if (!b.next().is(".pin")) {
		this._setContainerHeight()
	}
};
Layout.prototype._setDataIE = function(c, e) {
	var d = this;
	var a = [];
	if (c.length) {
		while (c.length > 0) {
			a.push(c.splice(0, 5))
		}
		b(a, 0, e);
		function b(g, f, h) {
			$.each(g[f], function(m, k) {
				var j = d._getHighest();
				var k = $(k);
				d.setPosition(k, j)
			});
			if (g[f + 1]) {
				setTimeout(function() {
					b(g, f + 1)
				}, 500)
			} else {
				if (h) {
					h()
				}
			}
		}
	}
};
Layout.prototype.append = function(k, f) {
	this.setAllPin();
	var g = this.allPins.length - 1;
	this.getLayoutContainer().append(k.content);
	this.setAllPin();
	var h = this.allPins.filter(".pin:gt(" + (g) + ")");
	for ( var d = 0; d < h.length; d++) {
		var a = new Pin($(h[d]));
		var e = $(h[d]).find("form.mishangForm");
		for ( var c = 0; c < e.length; c++) {
			var b = new Form($(e[c]))
		}
	}
	this.setData(h, f)
};
Layout.prototype.prepend = function(b) {
	var a = this.allPins.length - 1;
	this.getLayoutContainer().prepend(b.content);
	this.setAllPin();
	var c = this.allPins.filter(".pin:lt(" + (a) + ")");
	c.each(function() {
		$(this).hide();
		var e = new Pin($(this));
		var d = $(this).find("form.mishangForm");
		d.each(function() {
			var f = new Form($(this))
		})
	});
	this.setData(this.allPins)
};
Layout.prototype._getHighest = function() {
	var b = 0;
	var d = this;
	var b = jQuery
			.inArray(Math.min.apply(Math, d.positionCage), d.positionCage);
	var a = this.positionCage[b];
	var c = [ this.pinWidth * b, a + this.gap ];
	return lowest = {
		highestCol : b,
		coordination : c
	}
};
Layout.prototype._getLowest = function() {
	var c = this;
	var b = 0;
	var b = jQuery
			.inArray(Math.max.apply(Math, c.positionCage), c.positionCage);
	var a = this.positionCage[b];
	return a
};
Layout.prototype._setPositionCage = function() {
	var b = this;
	this.positionCage = new Array(this.cols);
	for ( var a = 0; a < b.positionCage.length; a++) {
		b.positionCage[a] = 0
	}
};
Layout.prototype._setContainerHeight = function() {
	var a = this._getLowest();
	a = a > 150 ? a : 150;
	this.getLayoutContainer().height(a)
};
function Pin(a) {
	this.pinObj = $(a);
	this.repin = this.pinObj.find(".repin");
	this.comment = this.pinObj.find(".commentPin");
	this.edit = this.pinObj.find(".editPin");
	this.editBoard = this.pinObj.find(".editBoard");
	this.delBoard = this.pinObj.find(".delBoard");
	this.init()
}
Pin.prototype.init = function() {
	var b = this;
	if (!$.browser.msie) {
		this.pinObj.hover(function() {
			$(this).find(".pinOrigin").css({
				opacity : 1
			})
		}, function() {
			$(this).find(".pinOrigin").css({
				opacity : 0.7
			})
		})
	}
	this.bindEvents();
	var a = b.pinObj.find(".pinBtns button");
	a.hover(function() {
		for ( var c = 0; c < a.length; c++) {
			var d = a[c].className.split(" ");
			if (d.length > 1) {
				$(a[c]).removeClass(d[1])
			}
		}
		$(this).addClass(this.className + "Hover")
	}, function() {
		for ( var c = 0; c < a.length; c++) {
			var d = a[c].className.split(" ");
			if (d.length > 1) {
				$(a[c]).removeClass(d[1])
			}
		}
		b.repin.addClass("repinHover")
	})
};
Pin.prototype.bindEvents = function() {
	var a = this;
	if (this.repin.is(".repin")) {
		this._repin()
	}
	if (this.comment.is(".commentPin")) {
		this._comment()
	}
	if (this.edit.is(".editPin")) {
		this._edit()
	}
	if (this.editBoard.is(".editBoard")) {
		this._editBoard()
	}
	if (this.delBoard.is(".delBoard")) {
		this._delBoard()
	}
};
Pin.prototype._repin = function() {
	var a = new MishangBox("#tempPop", this.repin, "/pin/new_repin/"
			+ this.repin.val() + "/");
	a.init()
};
Pin.prototype._comment = function() {
	var a = this.comment.val();
	this.comment.click(function() {
		window.location.href = "/pin/" + a + "/#comment"
	})
};
Pin.prototype._edit = function() {
	var a = new MishangBox("#tempPop", this.edit, "/pin/edit/"
			+ this.repin.val() + "/");
	a.init()
};
Pin.prototype._editBoard = function() {
	var b = this.editBoard.attr("href");
	this.editBoard.removeAttr("href");
	var a = new MishangBox("#tempPop", this.editBoard, b);
	a.init()
};
Pin.prototype._delBoard = function() {
	new Confirm(this.delBoard.parent("form"))
};
function AjaxLoader(a) {
	this.mishangUI = a;
	this.ajaxTips = $(".tips");
	this.loaderDiv = $(".ajaxLoader");
	this.loadingFlag = false;
	this.finishFlag = false;
	this.url = location.href;
	this.moreContent = null;
	this.pollingFlag = false;
	this.fixedFlag = null;
	this.notificationContainer = $(".messages_mishang");
	var b = this;
	this.init = function() {
		if (this.mishangUI.authorized && this.notificationContainer.length > 0) {
			this.notificationContainer.parents("li.msg_lists").find(
					"span.msg_close").click(function(c) {
				c.stopPropagation();
				c.preventDefault();
				$("li.msg_lists").removeClass("HaveMsgs");
				$(".messages_mishang").css("display", "none");
				$("span.msg_close").css("display", "none");
				$(".messages_mishang").prev();
				clearInterval(b.pollingFlag)
			});
			this.notificationContainer.hide();
			this.polling()
		}
	};
	this.polling = function() {
		this._getPolling();
		this.pollingFlag = setInterval(function() {
			b._getPolling()
		}, 120000)
	};
	this.appendLayout = function() {
	};
	this.scrollListener = function() {
		scrollFlag = null;
		$(window).scroll(
				function() {
					if (scrollFlag !== null) {
						return
					}
					scrollFlag = setTimeout(function() {
						scrollFlag = null;
						var c = $(window).scrollTop() + $(window).height();
						var d = $(document).height();
						var e = (d - c) < 1500 ? true : false;
						if (e) {
							if (!b.loadingFlag && !b.finishFlag
									&& b.moreContent == null) {
								b._loadMoreContent()
							}
							if ((d - c) < 300 && b.moreContent != null) {
								b._insertMoreContent()
							}
						} else {
							$(b.loaderDiv).hide()
						}
					}, 300)
				})
	};
	this._loadMoreContent = function() {
		var e = $(".pin[exclude!='true']");
		if (this.moreContent != null) {
			var c = e.length
					+ $(this.moreContent.content).filter(".pin").length
		} else {
			var c = e.length
		}
		if (c > 0) {
			if (this.url.indexOf("?") >= 0) {
				var d = this.url + "&offset=" + c
			} else {
				var d = this.url + "?offset=" + c
			}
			if (this.loadingFlag == false) {
				$.getJSON(d, function(g) {
					if (g.content && $.trim(g.content).length > 0) {
						b.moreContent = g;
						var h = $(document).height();
						var f = $(window).scrollTop() + $(window).height();
						if ((h - f) < 400) {
							b._insertMoreContent();
							b._loadMoreContent()
						}
					} else {
						b.finishFlag = true;
						if ($(".pagination").children().length > 0) {
							$(".pagination").show();
							$(b.loaderDiv).hide()
						} else {
							$(b.loaderDiv).html("没有更多了");
							$(b.loaderDiv).show()
						}
						b.loadingFlag = false
					}
				});
				if ($(".pin[exclude!=true]").length > 0) {
					$(b.loaderDiv).show()
				}
				this.loadingFlag = true
			}
		}
	};
	this._getPolling = function() {
		$.getJSON("/notification/", function(c) {
			var f = 0;
			for (n in c) {
				f += c[n];
				var e = b.notificationContainer.find(("#" + n));
				e.find("span").html(c[n]);
				if (c[n] > 0) {
					e.css({
						display : "block"
					})
				} else {
					e.hide()
				}
			}
			if (b.notificationContainer.height() > 1) {
				b.notificationContainer.parents("li.msg_lists").find(
						"span.msg_close").css("display", "block");
				if (b.notificationContainer.height() > 110) {
					b.notificationContainer.find("a").hide();
					var d = b.notificationContainer.find("a#nAll");
					d.find("span").html(f);
					b.notificationContainer.find("a#nAll").css({
						display : "block"
					})
				}
				b.notificationContainer.show();
				if (!b.fixedFlag) {
					b.fixedFlag = new FixFloating(b.notificationContainer, {
						fixedY : 46
					})
				}
				b.notificationContainer.parents("li").css({
					overflow : "visible"
				});
				b.notificationContainer.parents("li.msg_lists").addClass(
						"HaveMsgs")
			} else {
				b.notificationContainer.parents("li").css({
					overflow : "hidden"
				})
			}
		})
	};
	this._insertMoreContent = function() {
		this.mishangUI.floatingLayout.append(this.moreContent, function() {
			b.loadingFlag = false
		});
		$(this.loaderDiv).hide();
		this.moreContent = null
	};
	this.init()
}
function MishangBox(a, c, b) {
	this.popUp = $(a);
	this.innerBox = this.popUp.find(".box");
	this.url = b;
	this.jqObj = c;
	this.includeComponent;
	this.overlay = new Overlay();
	var d = this;
	this.init = function() {
		this.jqObj.click(function() {
			d.loadPopUp()
		})
	};
	this.loadPopUp = function() {
		this.innerBox.load(d.url, function(e) {
			d.overlay.fadeIn();
			d.popUp.find("a.closePop , .cancelPop , .confirmSend").click(
					function() {
						d.destroy()
					});
			d.adjustSize();
			if ($(e).find("form.mishangForm").size() > 0) {
				d.popUp.find("form.mishangForm").each(function() {
					new Form($(this), {
						mishangbox : d
					})
				});
				d.popUp.find(".charInput").each(function() {
					var f = new CharCheck($(this));
					f.init()
				})
			}
			d._registerObjects(e)
		})
	};
	this._registerObjects = function(e) {
		if ($(e).find("[confirmation]").length > 0) {
			d.popUp.find("[confirmation]").each(function() {
				new Confirm($(this))
			})
		}
	};
	this.adjustSize = function() {
		this.popUp.css("width", "auto");
		this.innerBox.css("width", "auto");
		this.popUp.css("width", this.popUp.width());
		this.innerBox.css("width", this.popUp.width() - 40);
		var g = ($("body").width() - this.popUp.width()) / 2;
		var e = $(window).height() - this.popUp.height();
		var f = e > 0 ? $(window).scrollTop() - 10 + e / 2 : 0;
		if (this.popUp.is("#existPop")) {
			this.popUp.css({
				left : g,
				top : f
			})
		} else {
			this.popUp.animate({
				left : g,
				top : f
			})
		}
		this.popUp.fadeIn()
	};
	this.destroy = function() {
		$(d.popUp).fadeOut();
		this.overlay.fadeOut()
	}
}
function NoticePop(b, a) {
	this.prototype = new MishangBox("#notice");
	this.message = b;
	if (a && a.timeout) {
		this.timeout = a.timeout
	} else {
		this.timeout = 3000
	}
	var c = this;
	this.init = function() {
		this.prototype.popUp.html(b);
		this.prototype.adjustSize();
		this.prototype.popUp.fadeIn().delay(c.timeout).fadeOut()
	};
	this.init()
}
function Validation(a) {
	this.formObj = a;
	this.validate = function() {
		var b = this._validNecessary();
		if (b) {
			return b
		}
	};
	this._validNecessary = function() {
		var b = this.formObj.find("input[necessary]");
		if (b.serialize().length == 0) {
			return $(b).first().attr("necessary")
		} else {
			return false
		}
	}
}
function Form(formObj, attr) {
	this.form = $(formObj);
	this.validation = new Validation(this.form);
	this.postUrl = this.form.attr("action");
	this.postData = {};
	this.atarea = [];
	this.belongTO;
	if (attr) {
		this.mishangbox = attr.mishangbox
	} else {
		this.mishangbox = null
	}
	var _this = this;
	this.init = function() {
		$(this.form).find(
				"input[type='text'] , textarea , input[type='password']")
				.focus(function() {
					$(this).addClass("focusinput")
				});
		$(this.form).find("textarea").focus(function() {
			$(this).parent().siblings(".jiantu").show()
		});
		$(this.form).find("input[type='text'] , input[type='password']").blur(
				function() {
					$(this).removeClass("focusinput")
				});
		$(this.form).find("a[type='submit']").click(function() {
			_this.form.submit()
		});
		$(this.form).find("input[type='submit']").click(
				function() {
					if ($(this.form).find("textarea")
							&& $(this.form).find("textarea").text() == $(
									this.form).find("textarea").attr(
									"placeholder")) {
						_this.form.find("textarea").text("")
					}
				});
		if (this.form.find(".atable").length > 0) {
			this.form.find(".atable").each(function() {
				_this.atarea.push(new Atarea($(this)))
			})
		}
		this.initInsertButton();
		this.instantiatingComponent(this.form);
		this.initSelectLayer();
		if (this.form.attr("enctype") == "multipart/form-data") {
			var uploadFile = new UploadFile(this.form, this.requestCallback)
		} else {
			this.form.submit(function() {
				_this.post();
				return false
			})
		}
	};
	this.initInsertButton = function() {
		var atBtn = this.form.find(".atButton");
		var topicButton = this.form.find(".topicButton");
		var priceButton = this.form.find(".priceButton");
		var emotionBtn = this.form.find(".emotionButton");
		var emotionli = this.form.find(".emotion_list li");
		atBtn.each(function() {
			var target = $($(this).attr("target"));
			$(this)
					.click(
							function() {
								target.focus();
								var ti = new TextareaInsert(target, "@", {
									choose : true
								});
								$(ti.textareaObj).data(
										"cursorPosition",
										ti.getCursorPosition
												.getCursorPosition());
								ti.addText();
								var position = $(ti.textareaObj).data(
										"cursorPosition");
								ti.chooseText(position.start, position.start)
							})
		});
		topicButton.each(function() {
			var target = $($(this).attr("target"));
			$(this)
					.click(
							function() {
								var ti = new TextareaInsert(target, "#输入话题#", {
									choose : true
								});
								$(ti.textareaObj).data(
										"cursorPosition",
										ti.getCursorPosition
												.getCursorPosition());
								ti.addText();
								var position = $(ti.textareaObj).data(
										"cursorPosition");
								ti.chooseText(position.start - 5,
										position.end - 1)
							})
		});
		priceButton.each(function() {
			var target = $($(this).attr("target"));
			$(this)
					.click(
							function() {
								target.focus();
								var ti = new TextareaInsert(target, "￥输入价格", {
									choose : true
								});
								$(ti.textareaObj).data(
										"cursorPosition",
										ti.getCursorPosition
												.getCursorPosition());
								ti.addText();
								var position = $(ti.textareaObj).data(
										"cursorPosition");
								ti.chooseText(position.start - 4, position.end)
							})
		});
		emotionBtn.each(function() {
			$(this).click(function() {
				$(".emotionDiv").show()
			})
		});
		emotionli.each(function() {
			var target = $($(this).attr("target"));
			$(this)
					.click(
							function() {
								target.focus();
								var ti = new TextareaInsert($($(this).attr(
										"target")), $(this).attr("title"), {
									choose : true
								});
								$(ti.textareaObj).data(
										"cursorPosition",
										ti.getCursorPosition
												.getCursorPosition());
								ti.addText();
								var position = $(ti.textareaObj).data(
										"cursorPosition");
								ti.chooseText(position.start, position.end)
							})
		})
	};
	this.initSelectLayer = function(args) {
		var display = (args && args.show) ? args.show : false;
		var firstOne = (args && args.selected) ? args.selected : false;
		this.form.find("span.select").remove();
		var select = this.form.find("select");
		if (select.length > 0) {
			select.each(function(i) {
				var selectName = $(this).attr("name");
				$(this).before(
						"<span class='select' name='" + selectName
								+ "'></span>");
				var selectContainer = $(this).prev(".select");
				var options = $(this).find("option");
				if (firstOne) {
					options.each(function() {
						$(this).removeAttr("selected")
					})
				}
				if ($(this).find("option[selected]").length > 0) {
					var current = $(this).find("option[selected]")
				} else {
					var current = $(this).find("option:first")
				}
				selectContainer.html("<span class='selected'>" + current.html()
						+ "</span>");
				var selected = $(this).prev().find(".selected");
				selectContainer
						.append("<span class='selectUlContainer'></span>");
				var selectUlContainer = selectContainer
						.find(".selectUlContainer");
				selectUlContainer.append("<ul></ul>");
				var selectUl = selectContainer.find("ul");
				options.each(function() {
					var value = $(this).val();
					var html = $(this).html();
					selectUl.append("<li val='" + value + "'><a>" + html
							+ "</a></li>")
				});
				selectUl.find("li").each(
						function() {
							var value = $(this).attr("val");
							var html = $(this).html();
							$(this).click(
									function() {
										var html = $(this).html();
										options.removeAttr("selected");
										options.filter(
												"[value='" + value + "']")
												.attr("selected", "selected");
										selected.html(html);
										selectUlContainer.hide()
									})
						});
				selected.click(function() {
					selectUlContainer.show()
				});
				$(this).hide();
				$(document).click(function(e) {
					if (isClickIn("span.select", e) == false) {
						$(selectUlContainer).hide()
					}
				});
				if (display) {
					selectUlContainer.show()
				}
				if (_this.belongTO) {
					_this.belongTO.asyncCallbackDistributor({
						methodFlag : "formSelectDivlized",
						jqSelector : "span.select"
					})
				}
			})
		}
	};
	this.selectListener = function() {
		var select = this.form.find("select");
		var hidden = this.form.find("input[type='hidden']");
		if (select.size() > 0) {
			select.each(function(i) {
				var name = $(this).attr("name");
				var hiddenInput = hidden.filter("input[name='" + name + "']");
				$(hiddenInput).val($(this).find("option:first").val());
				$(this).change(function() {
					hiddenInput.val($(this).find("option:selected").val())
				})
			})
		}
	};
	this.setCheckboxData = function(checkbox) {
		checkbox.each(function() {
			var key = $(this).attr("name");
			var val = $(this).val().length > 0 ? $(this).val() : "on";
			if ($(this).attr("checked")) {
				if (key in _this.postData) {
					eval("_this.postData." + key + "='" + val + "'")
				} else {
					eval("_this.postData." + key + "= 'off'")
				}
			}
		})
	};
	this.setRadioData = function(radios) {
		radios.each(function() {
			if ($(this).attr("checked")) {
				eval("_this.postData." + $(this).attr("name") + "='"
						+ $(this).val() + "'")
			}
		})
	};
	this.setData = function() {
		var allInput = this.form.find("input[type!='submit']");
		allInput.filter("input[type!=checkbox]").filter("input[type!='radio']")
				.filter("input[type!=select]").each(
						function(i) {
							eval("_this.postData." + $(this).attr("name")
									+ "='" + $(this).val() + "'")
						});
		this.setRadioData(allInput.filter("input[type='radio']"));
		this.setCheckboxData(allInput.filter("input[type='checkbox']"));
		var textarea = this.form.find("textarea");
		textarea.each(function() {
			var name = $(this).attr("name");
			var val = $(this).val().replace(/\n/g, " ");
			eval("_this.postData." + name + "='" + val + "'")
		})
	};
	this.post = function() {
		var validationMessage = this.validation.validate();
		if (validationMessage) {
			new NoticePop(validationMessage)
		} else {
			$.ajax({
				url : _this.postUrl,
				dataType : "json",
				data : _this.form.serialize(),
				type : "POST",
				success : function(json) {
					_this.requestCallback(json)
				},
				complete : function(json) {
					_this.form.find("input").each(function() {
						$(this).removeAttr("disabled")
					});
					_this.form.find(".loadingIcon").hide()
				}
			});
			_this.form.find(".loadingIcon").show();
			_this.form.find("input").each(function() {
				$(this).attr("disabled", "true")
			})
		}
	};
	this.requestCallback = function(data) {
		if (data instanceof Array) {
			$.each(data, function(i, json) {
				process(json)
			})
		} else {
			process(data)
		}
		function process(json) {
			switch (json.operation) {
			case "shutBox":
				var timeout = json.timeout == "undefined" ? 0 : json.timeout;
				setTimeout(function() {
					_this.mishangbox.destroy()
				}, timeout);
				break;
			case "notice":
				var timeout = json.timeout == "undefined" ? 0 : json.timeout;
				if (json.domID != "undefined") {
					if (json.timeout != "undefined") {
						new NoticePop(json.message, {
							timeout : json.timeout
						})
					} else {
						new NoticePop(json.message)
					}
				} else {
					$(json.domID).html(json.message);
					setTimeout(function() {
						$(json.domID).html("");
						$(json.domID).fadeOut()
					}, timeout)
				}
				break;
			case "redirect":
				if (json.domID == "#notice") {
					new NoticePop(json.message)
				} else {
					$(json.domID).html(json.message);
					$(json.domID).fadeIn()
				}
				setTimeout(function() {
					window.location.href = json.redirect_url
				}, json.delay * 1000);
				break;
			case "remove":
				$(json.domID).remove();
				break;
			case "update":
				var str = "$(json.domID)" + json.updateMethod
						+ "(json.content)";
				eval(str);
				if ($(json.content).is("form.mishangForm")) {
					form = $(json.content)
				} else {
					form = $(json.content).find("form.mishangForm")
				}
				if (form.length > 0) {
					$(json.domID).find("form.mishangForm").each(function() {
						if ($(this).html() == $(form).html()) {
							_this._registerNewForm($(this))
						}
					});
					$(json.domID).filter("form.mishangForm").each(function() {
						if ($(this).html() == $(form).html()) {
							_this._registerNewForm($(this))
						}
					})
				}
				break;
			case "componentCallback":
				_this.belongTO.asyncCallbackDistributor(json);
				break;
			case "registerComponent":
				var component = eval(json.registerComponent);
				break
			}
		}
	};
	this._registerNewForm = function(formSelector) {
		var mishangForm = new Form($(formSelector))
	};
	this.instantiatingComponent = function(formObj) {
		var componentID = formObj.attr("belongTO");
		var componentObj = $("[component='" + componentID + "']").first();
		var formObjs = componentObj.data("formObjs") || {};
		eval("formObjs." + formObj.attr("cname") + "= _this");
		componentObj.data("formObjs", formObjs);
		var isInstantiated = componentObj.data("isInstantiated");
		try {
			if (!isInstantiated) {
				var component = eval("new " + componentID + "(this.mishangbox)");
				componentObj.data({
					isInstantiated : component
				})
			} else {
				var component = componentObj.data("isInstantiated")
			}
			this.belongTO = component
		} catch (err) {
			component = null
		}
	};
	this.init()
}
function Component(a) {
	this.mishangbox = a;
	this.asyncCallbackDistributor = function() {
	}
}
function UploadAvatar(a) {
	this.prototype = new Component(a);
	this.component = $("div[component='UploadAvatar']");
	this.asyncCallbackDistributor = function(b) {
		switch (b.methodFlag) {
		case "avatarUploaded":
			$("#user_avatar").attr("src", b.img_url);
			$("#preview").attr("src", b.img_url);
			crop(b.img_url, b.width, b.height);
			break
		}
	}
}
function UploadPin(a) {
	this.prototype = new Component(a);
	this.component = $("div[component='UploadPin']");
	this.previewContainer;
	this.createPinForm;
	this.corousel;
	this.tab;
	var b = this;
	this.init = function() {
		this.createPinForm = this.component.find("form#createPin");
		this.previewContainer = this.component.find("div.preview");
		var c = this.component.find("ul.tab li");
		var d = this.component.find(".tabContent");
		this.tab = new Tabs(c, d);
		var e = this.component.find("form#fetchUrl");
		e.find("input").blur(function() {
			e.submit()
		})
	};
	this.asyncCallbackDistributor = function(c) {
		switch (c.methodFlag) {
		case "imgUploaded":
			this.imgUploadedCallback(c.args);
			break;
		case "urlFetched":
			this.imgUrlFetchCallback(c.args);
			break;
		case "formSelectDivlized":
			this.formSelectDivlizedCallback(c.jqSelector);
			break;
		case "createdNewBoard":
			this._newBoardCreated(c);
			break;
		case "reopenUploadPin":
			this._reopenUploadPin(c);
			break
		}
	};
	this.imgUploadedCallback = function(c) {
		this.flushPreview();
		this.previewContainer.append("<img src=" + c.url + ">");
		this.createPinForm.find("input[name='img_path']").val(c.path);
		this.createPinForm.find("input[name='img_height']").val(c.img_height);
		this.createPinForm.find("input[name='img_width']").val(c.img_width);
		this.createPinForm.find("input[name='methodFlag']").val("imgUploaded");
		this.createPinForm.find(".upload_pinmessage").css("display", "block");
		this.component.find(".tabContent").removeClass("tabTip");
		this.component.find(".tabContent p").css("display", "none");
		this.createPinForm.slideDown(function() {
			b.prototype.mishangbox.adjustSize()
		})
	};
	this.imgUrlFetchCallback = function(c) {
		this.flushPreview();
		this.createPinForm.find("input[name='methodFlag']").val("urlFetched");
		this.createPinForm.find("input[name='from_url']").val(c.url);
		$.each(c.imgInfoList, function(e, d) {
			b.previewContainer.append("<img src=" + d.url + " title='" + d.desc
					+ "'>")
		});
		this.carousel = new Carousel(b.previewContainer, "img", {
			moveCallback : b.imgListSelected,
			controlDirection : true
		});
		this.imgListSelected(this.carousel);
		this.createPinForm.slideDown(function() {
			b.prototype.mishangbox.adjustSize()
		})
	};
	this.formSelectDivlizedCallback = function(e) {
		var c = $(e);
		c
				.find(".selectUlContainer")
				.append(
						"<form action='/board/create/' belongTO='UploadPin' cname='createBoard'><span class='createNewBoard'><input type='hidden' name='instant' /><input type='text' placeholder='创建图集' name='name'><a class='button lightButton' type='submit'>创建</a><span class='inputstatus'></span></span></form>");
		c.find(".selectUlContainer input[placeholder]").each(function() {
			var f = new PlaceHolder($(this))
		});
		var d = new Form(c.find("form"))
	};
	this.imgListSelected = function(d) {
		var c = d.displays[d.cursor];
		$("textarea").html($(c).attr("title"));
		$("input[name='img_path']").val($(c).attr("src"))
	};
	this._newBoardCreated = function(d) {
		$(d.domID).prepend(d.content);
		var c = this.component.data("formObjs").createPin;
		c.initSelectLayer({
			show : false,
			selected : ":first"
		})
	};
	this._reopenUploadPin = function(c) {
		var d = new MishangBox("#tempPop", $("#newPin"), "/pin/upload/");
		d.init();
		d.loadPopUp()
	};
	this.flushPreview = function() {
		this.previewContainer.empty()
	};
	this.init()
}
function CommentBlock(a) {
	this.commentBlock = a;
	this.replyBtn = this.commentBlock.find(".reply");
	this.replyArea = this.commentBlock.find(".commentReply");
	this.form = this.commentBlock.find("form:first");
	var b = this;
	this.init = function() {
		this.replyArea.find(".charInput").each(function() {
			var c = new CharCheck($(this));
			c.init()
		});
		this.replyBtn.click(function() {
			var d = b.form.find("textarea");
			var c = "回复 " + b.replyBtn.attr("alt") + ":";
			b.replyArea.toggle();
			d.focus().text(c)
		});
		this.form.submit(function() {
			b.form.find("textarea").val("");
			b.replyArea.hide();
			return false
		})
	};
	this.init()
}
function Atarea(b) {
	this.atObj = b;
	this.friendsList;
	this.simTextArea = new SimTextArea(this.atObj);
	this.getCursorPosition = new GetCursorPostion(this.atObj.get(0));
	this.listLayer;
	this.textBuffer;
	this.interval;
	var a = new Date();
	this.uid = Math.floor(Math.random() * a.getTime());
	var c = this;
	this.init = function() {
		this.friendsList = this._getFriendsList();
		this.atObj.focus(function() {
			if (!c.interval) {
				c.interval = setInterval(function() {
					c._setIntervalListner()
				}, 300)
			}
		});
		this.atObj.blur(function() {
			clearInterval(c.interval);
			c.interval = null
		});
		this.atObj
				.keydown(function(f) {
					if (typeof (c.listLayer) != "undefined"
							&& c.listLayer.find("li.current").length > 0) {
						if (f.keyCode == 38 || f.keyCode == 40
								|| f.keyCode == 13 || f.keyCode == 27) {
							switch (f.keyCode) {
							case 13:
								var d = c.getCursorPosition.getCursorPosition();
								var e = c._isInAtRange(c.atObj.val().slice(0,
										d.start));
								c._insertFriend(e);
								break;
							case 38:
								c._switchCurrent("prev");
								break;
							case 40:
								c._switchCurrent("next");
								break;
							case 27:
								c._flushList();
								break
							}
							return false
						}
					}
				})
	};
	this._tryAppendLayer = function() {
		var f = this.getCursorPosition.getCursorPosition();
		var j = this.atObj.val();
		var g = j.charAt(f.start - 1);
		var h = j.slice(0, f.end + 1);
		var e = this._isInAtRange(h);
		if (e) {
			var d = this.simTextArea.addText(f.start - e.length);
			this._appendLayer(d, e);
			this.atObj.focus()
		} else {
			if (typeof (this.listLayer) != "undefined"
					&& this._isInAtRange(h) == false) {
				this._flushList()
			}
		}
	};
	this._appendLayer = function(d, f) {
		if (typeof (this.listLayer) != "undefined") {
			this._flushList()
		}
		$("body").append("<ul id='" + this.uid + "' class='G_friends'></ul>");
		this.listLayer = $("ul#" + this.uid);
		var e = this._searchFriends(f, this.friendsList);
		for (i in e) {
			if (i == 0) {
				this.listLayer.append("<li class='current'>" + e[i] + "</li>")
			} else {
				this.listLayer.append("<li>" + e[i] + "</li>")
			}
		}
		this.listLayer.find("li").each(function() {
			$(this).mouseover(function() {
				c.listLayer.find("li").each(function() {
					$(this).removeClass("current")
				});
				$(this).addClass("current")
			});
			$(this).click(function() {
				c._insertFriend(f)
			})
		});
		this.listLayer.css({
			left : d.left + c.atObj.offset().left + "px",
			top : d.top + c.atObj.offset().top + "px"
		})
	};
	this._flushList = function() {
		this.listLayer.empty()
	};
	this._isInAtRange = function(g) {
		var e = g.charAt(g.length - 1);
		var d = g.split(/[\n|\s|\r|\t|\f|\0]/);
		var f = d[d.length - 1];
		if (e != " " && f.lastIndexOf("@") >= 0 && f.search(/@.+/ig) >= 0) {
			return f.slice(f.lastIndexOf("@") + 1, f.length)
		} else {
			return false
		}
	};
	this._switchCurrent = function(e) {
		if (this.listLayer) {
			var d = this.listLayer.find("li.current");
			switch (e) {
			case "next":
				d.removeClass("current");
				if (d.next().length > 0) {
					d.next().addClass("current")
				} else {
					this.listLayer.find("li:first").addClass("current")
				}
				break;
			case "prev":
				d.removeClass("current");
				if (d.prev().length > 0) {
					d.prev().addClass("current")
				} else {
					this.listLayer.find("li:last").addClass("current")
				}
				break
			}
		}
	};
	this._searchFriends = function(e, f) {
		var d = [];
		for (i in f) {
			if (f[i].indexOf(e) > -1) {
				d.push(f[i] + " ")
			}
		}
		return d
	};
	this._setIntervalListner = function() {
		if (this.textBuffer == this.atObj.val()) {
			return false
		} else {
			this._tryAppendLayer();
			this.textBuffer = this.atObj.val()
		}
	};
	this._setBackCursor = function(d, g) {
		var f = this.atObj.get(0);
		if (f.createTextRange) {
			var e = f.createTextRange();
			e.moveStart("character", d.start);
			e.moveEnd("character", -1);
			e.select()
		} else {
			f.setSelectionRange(d.start, d.end);
			f.focus()
		}
	};
	this._insertFriend = function(e) {
		this.atObj.focus();
		var f = this.listLayer.find("li.current").html();
		var g = new TextareaInsert(this.atObj, f);
		var d = this.getCursorPosition.getCursorPosition();
		this.atObj.data("cursorPosition", {
			start : d.start - e.length,
			end : d.end
		});
		g.addText();
		g.chooseText(c.atObj.data("cursorPosition").start, c.atObj
				.data("cursorPosition").end);
		this._flushList()
	};
	this._getFriendsList = function() {
		$.getJSON("/api/getFriends/", function(d) {
			c.friendsList = d
		})
	};
	this.init()
}
function Carousel(a, c, b) {
	this.container = a;
	this.cursor = 0;
	this.moveCallback = b.moveCallback ? b.moveCallback : function() {
	};
	this.wrapContainer;
	this.displays;
	this.limitWidth;
	this.limitHeight;
	this.prev;
	this.next;
	this.orientation = b.orientation ? b.orientation : "horizontal";
	this.controlDirection = b.controlDirection ? b.controlDirection : false;
	this.timeInterval = b.timeInterval ? b.timeInterval : 3000;
	this.rotation = false;
	var d = this;
	this.init = function() {
		this.container.css("position", "relative");
		this.displays = this.container.find(c);
		if (this.orientation == "horizontal") {
			this.limitWidth = this.container.innerWidth()
		} else {
			this.limitHeight = this.container.innerHeight()
		}
		this._wrap();
		if (this.controlDirection) {
			this._addNav()
		} else {
			$(window).focus(function() {
				if (!d.rotation) {
					d.rotation = setInterval(function() {
						d._rotate()
					}, d.timeInterval)
				}
			});
			$(window).blur(function() {
				clearInterval(d.rotation);
				d.rotation = false
			})
		}
	};
	this._wrap = function() {
		this.displays.each(function(e) {
			if (d.orientation == "horizontal") {
				$(this).wrap("<div class='carWrapH' />")
			} else {
				$(this).wrap("<div class='carWrapV' />")
			}
		});
		this.container.wrapInner("<div class='wrapContainer' />");
		this.wrapContainer = this.container.find(".wrapContainer");
		if (this.orientation == "horizontal") {
			this.wrapContainer.width(d.displays.size() * d.limitWidth)
		} else {
			this.wrapContainer.height((d.displays.size() * d.limitHeight))
		}
	};
	this._addNav = function() {
		if (this.container.next().is(".carouselPickContainer")) {
			this.container.next().remove()
		}
		this.container
				.after("<div class='carouselPickContainer'><div class='carouselPick'>上一张</div><div class='carouselPick'>下一张</div></div>");
		this.prev = this.container.siblings(".carouselPickContainer").find(
				".carouselPick:first");
		this.next = this.container.siblings(".carouselPickContainer").find(
				".carouselPick:last");
		this.prev.css({
			left : 14,
			bottom : 8
		});
		this.next.css({
			right : 14,
			bottom : 8
		});
		this._setCursor();
		this.prev.click(function() {
			d._move("prev")
		});
		this.next.click(function() {
			d._move("next")
		})
	};
	this._rotate = function() {
		var e = new Date();
		this.wrapContainer.find("div:first").addClass("current");
		var f = this.container.find("div.current");
		var g = f.outerWidth();
		$(f).animate({
			"margin-left" : -g
		}, "500", function() {
			var h = f.detach();
			d.wrapContainer.append(h);
			f.css({
				"margin-left" : 0
			});
			f.removeClass("current")
		})
	};
	this._move = function(e) {
		if (e == "prev") {
			if (this.cursor != 0 && !this.prev.hasClass("carouselDisable")) {
				var f = parseInt(this.wrapContainer.css("left"))
						+ this.limitWidth;
				this.cursor -= 1
			}
		} else {
			if (this.cursor != this.displays.length - 1
					&& !this.next.hasClass("carouselDisable")) {
				var f = parseInt(this.wrapContainer.css("left"))
						- this.limitWidth;
				this.cursor += 1
			}
		}
		this._setCursor();
		this.wrapContainer.animate({
			left : f + "px"
		});
		this.moveCallback(this)
	};
	this._setCursor = function() {
		if (this.cursor == 0) {
			this.prev.addClass("carouselDisable")
		} else {
			this.prev.removeClass("carouselDisable")
		}
		if (this.cursor == (this.displays.length - 1)) {
			this.next.addClass("carouselDisable")
		} else {
			this.next.removeClass("carouselDisable")
		}
	};
	this.init()
}
function Tabs(a, b) {
	this.tabList = a;
	this.tabContent = b;
	var c = this;
	this.init = function() {
		this.tabList.each(function(d) {
			$(this).click(function() {
				c.eliminateCurrent();
				$(this).addClass("current");
				$(c.tabContent[d]).addClass("current")
			})
		})
	};
	this.eliminateCurrent = function() {
		this.tabList.each(function(d) {
			$(this).removeClass("current");
			$(c.tabContent[d]).removeClass("current")
		})
	};
	this.init()
}
function UploadFile(formObj, uploadedCallback) {
	this.form = formObj;
	this.iframeTpl = "<iframe name='uploadFile' class='UploadFile'></iframe>";
	this.iframe;
	this.uploadedCallback = uploadedCallback;
	this.uploadLink;
	this.hiddenUploadInput;
	var _this = this;
	this.init = function() {
		$(this.form).submit(function() {
			if ($(this).find('input[type="file"]').val() == "") {
				new NoticePop("请选择图片")
			}
			$(this).find("img.loadingIcon").show()
		});
		this.form.attr("target", "uploadFile");
		if ($("iframe.UploadFile").size() < 1) {
			$("body").append(_this.iframeTpl)
		}
		this.iframe = $("iframe.UploadFile");
		this._frameDomListener();
		this.uploadLink = this.form.find("a[targetTo]");
		if (this.uploadLink.length > 0) {
			this._initUploadLink()
		}
	};
	this._frameDomListener = function(innerID) {
		this.iframe.load(function() {
			var frameDom = $(window.frames.uploadFile.document);
			var response = $(frameDom).find("#response");
			if (response.size() > 0) {
				var responseJson = _this._formatJson($(response).html());
				_this.uploadedCallback(responseJson);
				$(_this.form).find("img.loadingIcon").hide()
			}
		})
	};
	this._initUploadLink = function() {
		this.hiddenUploadInput = this.form.find(_this.uploadLink
				.attr("targetto"));
		if ($.browser.msie == true) {
			_this.hiddenUploadInput.addClass("IEuploadInput");
			_this.hiddenUploadInput.css({
				position : "relative",
				left : 0,
				width : "auto",
				opacity : "1.0",
				filter : "alpha(opacity=100)"
			});
			_this.uploadLink.hide();
			_this.hiddenUploadInput.css("left", "");
			_this.hiddenUploadInput.get(0).onpropertychange = function() {
				if (_this.hiddenUploadInput.get(0).value) {
					_this.form.submit()
				}
			}
		} else {
			this.uploadLink.click(function() {
				_this.hiddenUploadInput.click();
				_this.hiddenUploadInput.change(function() {
					_this.form.submit()
				})
			})
		}
	};
	this._formatJson = function(str) {
		return eval("json=" + str)
	};
	this.init()
}
function isClickIn(d, c) {
	var b = $(d);
	var c = c ? c : window.event;
	var a = c.srcElement || c.target;
	if ($(a).parents(d).length > 0) {
		return true
	} else {
		return false
	}
}
function TextareaInsert(b, c, a) {
	this.textareaObj = b.get(0);
	this.text = c;
	this.getCursorPosition = new GetCursorPostion(this.textareaObj);
	var d = this;
	this.addText = function() {
		var f = $(this.textareaObj).data("cursorPosition") || {};
		var o = f.start || 0;
		var h = f.end || 0;
		var m = $(this.textareaObj).val();
		var k = m.substring(0, o);
		var e = m.substring(h);
		var j = k + this.text + e;
		var g = o + this.text.length;
		f.start = g;
		f.end = g;
		$(this.textareaObj).val(j).data("cursorPosition", f)
	};
	this.chooseText = function(j, f) {
		var h = this.textareaObj;
		if (h.createTextRange) {
			var g = h.createTextRange();
			var f = f - h.value.length;
			g.moveStart("character", j);
			g.moveEnd("character", f);
			g.select()
		} else {
			h.setSelectionRange(j, f);
			h.focus()
		}
		var e = $(this.textareaObj).data("cursorPosition")
	}
}
function GetCursorPostion(b) {
	this.textareaObj = b;
	var a = this;
	this.getCursorPosition = function() {
		var k = {};
		var m = this.textareaObj;
		if (m.style.display == "none") {
			return 0
		}
		var d = 0;
		var e = 0;
		if (typeof (m.selectionStart) == "number") {
			d = m.selectionStart;
			e = m.selectionEnd
		} else {
			if (document.selection) {
				var j = document.selection.createRange();
				if (m.nodeName === "TEXTAREA") {
					var g = document.body.createTextRange();
					g.moveToElementText(m);
					for (d = 0; g.compareEndPoints("StartToStart", j) < 0; d++) {
						g.moveStart("character", 1)
					}
					for ( var h = 0; h <= e; h++) {
						if (m.value.charAt(h) == "\n") {
							e++
						}
					}
					var g = document.body.createTextRange();
					g.moveToElementText(m);
					for (e = 0; g.compareEndPoints("StartToEnd", j) < 0; e++) {
						g.moveStart("character", 1)
					}
					for ( var h = 0; h <= e; h++) {
						if (m.value.charAt(h) == "\n") {
							e++
						}
					}
				} else {
					if (m.nodeName === "INPUT") {
						var c = j.text.length;
						j.moveStart("character", -m.value.length);
						var f = j.text.length;
						d = f - c;
						e = j.text.length
					}
				}
			}
		}
		k.start = d;
		k.end = e;
		return k
	}
}
function SimTextArea(a) {
	this.textArea = a;
	this.textAreaContent;
	this.baseOffsetLeft = this.textArea.offset().left;
	this.baseOffsetTop = this.textArea.offset().top;
	this.limitedWidth = this.textArea.width();
	this.limitedHeight = this.textArea.height();
	this.layerWrap;
	this.lineHeight = 20;
	var b = this;
	this.init = function() {
		this._setFakeLayerWrap()
	};
	this.addText = function(c) {
		this.textAreaContent = c ? this.textArea.val().slice(0, c)
				: this.textArea.val();
		this._flushContent();
		for (i = 0; i < this.textAreaContent.length; i++) {
			var e = this.textAreaContent[i] == " " ? "&nbsp;"
					: this.textAreaContent[i];
			var d = this._tryToStuffLine(e)
		}
		return d
	};
	this._setFakeLayerWrap = function() {
		var c = new Date();
		c = c.getTime();
		$("body").append("<ul id='" + c + "' class='fakeLayer'></ul>");
		this.layerWrap = $("#" + c);
		this.layerWrap.width(this.limitedWidth)
	};
	this._tryToStuffLine = function(d) {
		var h = $(this._getCurrentLine());
		h.append(d);
		if (d == "\n") {
			h = this._appendLine();
			h.html(d)
		}
		if (h.width() > this.limitedWidth) {
			h.html(h.html().slice(0, -1));
			h = this._appendLine();
			h.html(d)
		}
		var e = this._simLayerLines().length
				* parseInt(this.textArea.css("lineHeight"));
		var g = e > this.limitedHeight ? this.limitedHeight : e;
		var f = h.width();
		var c = {
			left : f,
			top : g
		};
		return c
	};
	this._getCurrentLine = function() {
		var c = this._simLayerLines().length;
		if (c == 0) {
			currentLine = this._appendLine()
		} else {
			currentLine = $(this.layerWrap.find("li:last"))
		}
		return currentLine
	};
	this._appendLine = function() {
		this.layerWrap.append("<li></li>");
		return $(this.layerWrap.find("li:last"))
	};
	this._flushContent = function() {
		this.layerWrap.find("li").each(function() {
			$(this).remove()
		})
	};
	this._simLayerLines = function() {
		return $(this.layerWrap.find("li"))
	};
	this.init()
}
function Confirm(b, a) {
	this.jqObj = b;
	this.url;
	this.mishangbox;
	this.popUrl = "/api/confirmation/?";
	if (a && a.message) {
		this.confirmationMessage = a.message
	} else {
		this.confirmationMessage
	}
	if (a && a.method == "GET") {
		this.method = "GET"
	} else {
		this.method = "POST"
	}
	var c = this;
	this.init = function() {
		this.confirmationMessage = encodeURI(this.jqObj.attr("confirmation"));
		if (this.jqObj.is("a") || this.method == "GET") {
			this.url = this.jqObj.attr("href");
			this.jqObj.click(function() {
				c.fireConfirm();
				return false
			});
			this.popUrl = this.popUrl + "message=" + this.confirmationMessage
					+ "&action=" + this.url
		} else {
			this.url = this.jqObj.attr("action");
			$(this.jqObj).submit(
					function() {
						var d = c.jqObj.serialize();
						c.popUrl = c.popUrl + "message="
								+ c.confirmationMessage + "&action=" + c.url
								+ "&" + d;
						c.fireConfirm();
						return false
					})
		}
	};
	this.fireConfirm = function() {
		this.mishangbox = new MishangBox("#confirmPop", this.jqObj, this.popUrl);
		this.mishangbox.loadPopUp()
	};
	this.init()
}
function PlaceHolder(c, b) {
	this.inputObj = c;
	if (b && b.defaultAttr) {
		this.defaultValue = this.inputObj.attr(b.defaultAttr)
	} else {
		this.defaultValue = this.inputObj.attr("placeholder")
	}
	this.copyLayer;
	var d = this;
	this.init = function() {
		if (this.inputObj.attr("type") == "password") {
			this._copyLayer();
			this.copyLayer.click(function() {
				d.copyLayer.hide();
				d.inputObj.focus()
			});
			this.inputObj.focus(function() {
				d.copyLayer.hide()
			});
			this.inputObj.blur(function() {
				if (d.inputObj.val() == "") {
					d.copyLayer.show()
				}
			})
		} else {
			if (this.inputObj.is("textarea")) {
				this.inputObj.text(d.defaultValue);
				this.inputObj.focus(function() {
					if (d.inputObj.text() == d.defaultValue) {
						d.inputObj.text("")
					}
				});
				this.inputObj.blur(function() {
					if (d.inputObj.text() == "") {
						d.inputObj.text(d.defaultValue)
					}
				})
			}
			this.inputObj.val(d.defaultValue);
			this.inputObj.focus(function() {
				if (d.inputObj.val() == d.defaultValue) {
					d.inputObj.val("")
				}
			});
			this.inputObj.blur(function() {
				if (d.inputObj.val() == "") {
					d.inputObj.val(d.defaultValue)
				}
			})
		}
	};
	this.destroy = function() {
		if (this.copyLayer) {
			this.copyLayer.remove()
		}
		this.inputObj.unbind("focus , blur")
	};
	this._copyLayer = function() {
		var e = new Date();
		var f = e.getTime();
		var g = this.inputObj.parent();
		g.append("<div id='" + f + "'></div>");
		this.copyLayer = $("#" + f);
		this.copyLayer.css({
			top : d.inputObj.position().top
					+ parseInt(d.inputObj.css("padding-top"))
					+ parseInt(d.inputObj.css("border-top-width")),
			left : d.inputObj.position().left
					+ parseInt(d.inputObj.css("padding-left"))
					+ parseInt(d.inputObj.css("border-left-width")),
			width : (d.inputObj.innerWidth() - 10) + "px",
			height : (d.inputObj.innerHeight() - 12) + "px",
			"line-height" : d.inputObj.height() + "px",
			"vertical-align" : "middel",
			position : "absolute",
			"z-index" : 100000,
			color : d.inputObj.css("color"),
			cursor : "text",
			"background-color" : d.inputObj.css("background-color"),
			"font-size" : d.inputObj.css("font-size")
		});
		this.copyLayer.html(d.defaultValue);
		this.inputObj.val("")
	};
	var a = browserCheck();
	if (a.core == "ie" && a.version < 9) {
		this.init()
	}
}
function browserCheck() {
	var b = {};
	var c = navigator.appName.toLowerCase();
	if (c.indexOf("microsoft") >= 0) {
		b.core = "ie";
		b.version = navigator.appVersion.split("(")[1].split(";")[1].split(" ")[2]
	} else {
		b.core = c;
		b.version = parseFloat(navigator.appVersion)
	}
	var a = navigator.appVersion;
	return b
}
function InstantUploadLink(a) {
	this.form = a;
	this.uploadLink = this.form.find("a[targetTo]");
	this.hiddenUploadInput;
	var b = this;
	this.init = function() {
		this.hiddenUploadInput = this.form.find(b.uploadLink.attr("targetto"));
		if ($.browser.msie == true) {
			this.hiddenUploadInput.css({
				position : "relative",
				left : 0,
				width : "auto",
				opacity : "1.0",
				filter : "alpha(opacity=100)"
			});
			this.uploadLink.hide();
			this.hiddenUploadInput.addClass("IEuploadInput");
			this.hiddenUploadInput.click(function() {
				var c = b.hiddenUploadInput.val();
				setTimeout(function() {
					if (c != b.hiddenUploadInput.val()) {
						b.form.submit()
					}
				}, 0)
			})
		} else {
			this.uploadLink.click(function() {
				b.hiddenUploadInput.click();
				b.hiddenUploadInput.change(function() {
					b.form.submit()
				})
			})
		}
	};
	this.init()
}
function CharCheck(a) {
	this.charInput = a;
	this.charLimit = this.charInput.attr("charCount");
	this.charCountSpan = this.charInput.parents("form").find(
			this.charInput.attr("targetCountNum"));
	this.interval = null;
	this.textBuffer = null;
	this.charCount = 0;
	var b = this;
	this.init = function() {
		this.charInput.focus(function() {
			if (!b.interval) {
				b.interval = setInterval(function() {
					b._setIntervalListner()
				}, 300)
			}
		});
		this.charInput.blur(function() {
			clearInterval(b.interval);
			b.interval = null
		})
	};
	this._setIntervalListner = function() {
		if (this.textBuffer == this.charInput.val()) {
			return false
		} else {
			this._calculateCharCount();
			this.textBuffer = this.charInput.val()
		}
	};
	this._calculateCharCount = function() {
		this.charCount = this.charInput.val().length;
		if (this.charCount > this.charLimit) {
			this.charInput.val(this.charInput.val().slice(0, b.charLimit));
			this.charCountSpan.html("0")
		} else {
			this.charCountSpan.html(b.charLimit - b.charCount)
		}
	}
}
function Overlay() {
	this.overlay = $("#overlay");
	this.fadeIn = function() {
		var a = $(".bigWrapper").outerHeight() > 600 ? $(".bigWrapper")
				.outerHeight() : 600;
		this.overlay.css("height", a);
		this.overlay.fadeIn();
		this.overlay.animate({
			opacity : 0.9
		})
	};
	this.fadeOut = function() {
		this.overlay.animate({
			opacity : 0
		});
		this.overlay.fadeOut()
	}
}
function SetCookie(a, c) {
	var b = 300;
	var d = new Date();
	d.setTime(d.getTime() + b * 24 * 60 * 60 * 1000);
	document.cookie = a + "=" + escape(c) + ";expires=" + d.toGMTString()
}
function getCookie(b) {
	var a = document.cookie.match(new RegExp("(^| )" + b + "=([^;]*)(;|$)"));
	if (a != null) {
		return unescape(a[2])
	}
	return null
}
function FixFloating(b, a) {
	this.obj = b;
	this.uid;
	this.fixedTop = a && a.top ? a.top : this.obj.offset().top;
	this.fixedLeft = this.obj.offset().left;
	this.fixedY = a && a.fixedY ? a.fixedY : 0;
	this.simIEFixed = a && a.simIEFixed ? a.simIEFixed : false;
	this.toggleDisplay = a && a.toggleDisplay ? a.toggleDisplay : false;
	this.toggleZindex = a && a.toggleZindex ? a.toggleZindex : false;
	var c = this;
	this.init = function() {
		var d = new Date();
		this.uid = Math.floor(Math.random() * d.getTime());
		this.obj.after("<set id='" + this.uid + "'></set>");
		if ($(window).scrollTop() > c.fixedTop) {
			this._setUp()
		}
		$(window).scroll(function() {
			if ($(window).scrollTop() > c.fixedTop) {
				c._setUp()
			} else {
				c._recover()
			}
		})
	};
	this._setUp = function() {
		this.obj.detach();
		$("body").append(this.obj);
		this.obj.addClass("coverFixed");
		this.obj.css("left", c.fixedLeft);
		this.obj.css("top", c.fixedY);
		if ($.browser.msie && $.browser.version == "6.0") {
			this.obj.css({
				position : "absolute",
				top : $(window).scrollTop()
			})
		}
		if (this.toggleDisplay) {
			this.obj.css({
				display : "block"
			})
		}
		if (this.toggleZindex) {
			this.obj.css({
				display : "block",
				"z-index" : c.toggleZindex.end
			})
		}
	};
	this._recover = function() {
		if ($("#" + c.uid).length > 0) {
			this.obj.removeClass("coverFixed");
			this.obj.detach();
			this.obj.insertBefore(("#" + c.uid));
			this.obj.css({
				left : "",
				top : ""
			})
		}
		if (this.toggleDisplay) {
			this.obj.fadeOut({
				display : "none"
			})
		}
		if (this.toggleZindex) {
			this.obj.css({
				"z-index" : c.toggleZindex.start,
				display : "none"
			})
		}
	};
	this.init()
}
var CommentInteractions = function() {
	return {
		defaultText : "我来说说...",
		clearDefaultText : function(b) {
			var d = this;
			$(b).live("focus", function() {
				$(this).val() === d.defaultText && $(this).val("")
			});
			$(b).live("blur", function() {
				$(this).val() === "" && $(this).val(d.defaultText)
			})
		},
		gridComment : function(d) {
			var b = this;
			var c = d;
			b.clearDefaultText(".write textarea");
			$(".write .GridComment").live("focus", function() {
				var f = $(this).parent().find(".Button");
				if (!f.is(":visible")) {
					var e = $(this).parents(".pin");
					var g = e.attr("column");
					var a = parseInt(e.css("top"));
					c.updateHeight(g, 31, a);
					f.css("display", "inline-block")
				}
			});
			$(".write .Button")
					.live(
							"click",
							function() {
								var m = $(this), k = m.parents("div"), g = m
										.parents(".pin"), f = $(
										".comments_count", g), e = $(
										".pin_counts", g), j = $("textarea", g)
										.val(), a = $(".comment", g), h = e.length === 0 ? true
										: false;
								if ($.trim(j).length > 140) {
									alert("不能超过140个字哦。");
									m.focus();
									return
								}
								if (j != b.defaultText && j != "") {
									if (!m.hasClass("disabled")) {
										m.addClass("disabled");
										$
												.ajax({
													url : "/pin/comment/",
													dataType : "json",
													data : {
														content : j,
														pin_id : k
																.attr("pin_id"),
														home : "1"
													},
													type : "POST",
													success : function(o) {
														if (o.status == "success") {
															o = $(o.html)
																	.hide();
															pin_counts_height = 0;
															if (a.length === 0) {
																g
																		.find(
																				".write")
																		.before(
																				"<div class='comments colormuted'></div>");
																g
																		.find(
																				".comments")
																		.html(o);
																h
																		&& g
																				.find(
																						".write")
																				.after(
																						"<p class='pin_counts'></p>");
																g
																		.find(
																				".pin_counts")
																		.append(
																				"<span class='comments_count pin_count'>1</span><span>评论</span>");
																if (h) {
																	pin_counts_height = 31
																}
															} else {
																g
																		.find(
																				"div.comments .comment:last")
																		.after(
																				o);
																l = parseInt(f
																		.html());
																f
																		.html((l + 1)
																				.toString())
															}
															$("textarea", g)
																	.val(
																			b.defaultText);
															o
																	.slideDown(
																			"fast",
																			function() {
																				var q = o
																						.outerHeight()
																						+ pin_counts_height;
																				var r = g
																						.attr("column");
																				var p = parseInt(g
																						.css("top"));
																				c
																						.updateHeight(
																								r,
																								q,
																								p)
																			})
														} else {
															alert("出错啦...")
														}
														m
																.removeClass("disabled")
													},
													complete : function(o) {
													}
												})
									}
								}
								return false
							})
		}
	}
}();