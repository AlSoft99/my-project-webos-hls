<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ASG DESIGN TEAM PORTFOLIO</title>
<link href="styles/default/css/main.css" rel="stylesheet" type="text/css" media="screen" />
<script type="text/javascript" src="lib/sea.min.js"></script>
<script type="text/javascript" src="lib/three.js"></script>
<script type="text/javascript" src="lib/sea.config.js"></script>
</head>
<body>
	<div class="bg-elem">
		<img class="bg-image" src="styles/default/images/bg.png" />
		<img class="bg-title" src="styles/default/images/team_title.png" />
		<div id="navigate"></div>
	</div>
	<div id="box-background" class="bg"></div>
	<audio id="audio1" src="http://interscope.edgeboss.net/download/interscope/elliegoulding/site/lights-video/music.mp3" ></audio>
</body>
</html>
<script>
	seajs.use(['box','jquery','jquery.fn','jquery.fn.flip'], function(box, $){
		/* 
		var audio = document.getElementById("audio1");
		audio.addEventListener( "canplaythrough", function(c){
			console.log("=============canplaythrough============");
			console.log(c);
			
		}, true );
		audio.load(); */
		$.queryData({
			sql:"SQL3",
			where:"2"
		}, function(c){
			console.log(c);
			debugger;
		});
		var bg = $("#box-background");
		box.createBackground(bg);
		var deliverItem = box.createItem(bg,{
			color: "#2DA4DB",
			title: "Delivered Projects",
			hover: "04",
			left:5,
			top:5,
			onclick: function(_this, item){
				//item1.animateMove(1,-1);
				openDelivered();
				vi.goIndex(1);
			}
		});
		/**
		params: {
			child: child,
			childhover: hover,
			body: body,
			item: item,
			image: image,
			title: title,
			imgWidth: imgWidth,
			imgHeight: imgHeight
		}
		**/
		function createChild(params){
			var child1 = $("<div class='full'><img src='"+params.child+"' class='full'></div>");
			var child1_hover = $("<div class='full'><img src='"+params.childhover+"' class='full'></div>");
			child1.on("click",click1);
			child1_hover.on("click",click1);
			function click1(){
				var bodyContent = $("<div class='index-dialog' style='background-color:black;' ><img src='"+params.body+"' style='' ></div>");
				var row = params.row || 6;
				var cell = params.cell || 12;
				var content = "";
				if(params.subTitle){
					content = "<div class='index-dialog-title'><table><tr><td style='padding:0 40px 30px;background:url(styles/default/images/dot_line.png) no-repeat top right;white-space:nowrap; vertical-align:top;'>"+params.title+"</td><td style='padding:0 30px;font-size:12px;vertical-align:top;'>"+params.subTitle+"</td></tr></table></div>";
				}else{
					content = "<div class='index-dialog-title'><table><tr><td style='padding:0 40px 30px;white-space:nowrap; vertical-align:top;'>"+params.title+"</td></tr></table></div>";
				}
				var dialog = {
					left: "center",
					top: "center",
					row: row,
					cell: cell,
					image: params.image,
					btn: {
						close: {
							event: function(){
								vi.goIndex(0);
								box.refreshDialogImage(params.item, {
									left: window.flip.left
								});
								return true;
							}
						}
					},
					bodyStyle: {
						"background": "url("+params.body+") no-repeat center center black",
						"border": "1px solid black"
					},
					titleStyle: {
						"background-color": "black",
						"border": "1px solid black"
					},
					bodyContent: bodyContent,
					titleContent: $(content),
					childlist: params.item.childlist,
					_this: $(this)
				};
				box.openDialog(bg, dialog, params.item);
				bodyContent.flip({
					width: dialog.cell * params.item.realwidth,
					height: (dialog.row-1) * params.item.realheight,
					imgWidth: params.imgWidth,
					imgHeight: params.imgHeight,
					initLeft: 165
				});
			}
			return box.createChild(child1,child1_hover,params.item);
		}
		/**
		params: {
			child: child,
			childhover: hover,
			body: body,
			bodyImage: bodyImage,
			item: item,
			image: image,
			title: title
		}
		**/
		function createVideo(params){
			var child5 = $("<div class='full'><img src='"+params.child+"' class='full'></div>");
			var child5_hover = $("<div class='full'><img src='"+params.childhover+"' class='full'></div>");
			child5.on("click",click5);
			child5_hover.on("click",click5);
			function click5(){
				var bodyContent = $("<div class='index-dialog' style='background-color:black;' ><video height='450' autoplay controls=\"controls\" style='margin-top:75px;'><source src=\""+params.body+"\" type=\"video/mp4\" />Your browser does not support the video tag.</video></div>");
				
				var dialog = {
					left: "center",
					top: "center",
					row: 6,
					cell: 9,
					btn: {
						close: {
							event: function(){
								vi.goIndex(0);
								return true;
							}
						}
					},
					image:params.image,
					bodyStyle: {
						"background": "url("+params.bodyImage+") no-repeat center center black",
						"border": "1px solid black"
					},
					titleStyle: {
						"background-color": "black",
						"border": "1px solid black"
					},
					bodyContent: bodyContent,
					titleContent: $("<div class='index-dialog-title'><table><tr><td style='padding:0 40px 30px;background:url(styles/default/images/dot_line.png) no-repeat top right;white-space:nowrap; vertical-align:top;'>"+params.title+"</td><td style='padding:0 30px;font-size:12px;vertical-align:top;'>"+params.subTitle+"</td></tr></table></div>"),
					childlist: params.item.childlist,
					_this: $(this)
				};
				box.openDialog(bg, dialog, params.item);
				
			}
			return box.createChild(child5,child5_hover,params.item);
		}
		
		function createDom(params){
			var child5 = $("<div class='full'><img src='"+params.child+"' class='full'></div>");
			var child5_hover = $("<div class='full'><img src='"+params.childhover+"' class='full'></div>");
			child5.on("click",click5);
			child5_hover.on("click",click5);
			function click5(){
				var bodyContent = $("<div class='index-dialog' style='background-color:black;' >"+params.body+"</div>");
				
				var dialog = {
					left: "center",
					top: "center",
					row: 6,
					cell: 9,
					btn: {
						close: {
							event: function(){
								vi.goIndex(0);
								return true;
							}
						}
					},
					image:params.image,
					bodyStyle: {
						"background": "url("+params.bodyImage+") no-repeat center center black",
						"border": "1px solid black"
					},
					titleStyle: {
						"background-color": "black",
						"border": "1px solid black"
					},
					bodyContent: bodyContent,
					titleContent: $("<div class='index-dialog-title'><table><tr><td style='padding:0 40px 30px;background:url(styles/default/images/dot_line.png) no-repeat top right;white-space:nowrap; vertical-align:top;'>"+params.title+"</td><td style='padding:0 30px;font-size:12px;vertical-align:top;'>"+params.subTitle+"</td></tr></table></div>"),
					childlist: params.item.childlist,
					_this: $(this),
					renderer: function(){
						var container;
						var camera, scene, renderer;
						var mesh;
						(function(){
							var w=700,h=600;
							container = document.getElementById( 'container' );
							scene = new THREE.Scene();
							camera = new THREE.PerspectiveCamera( 60, w / h, 1, 10000 );
							camera.position.z = 200;
							//renderer = new THREE.CanvasRenderer();
							renderer = new THREE.WebGLRenderer({antialias: true});
							renderer.setSize( w, h );
							container.appendChild( renderer.domElement );
						})();
						//earch
						(function initPlane(){
							//mesh = new THREE.Mesh( new THREE.SphereGeometry( 20, 20, 20 ), new THREE.MeshLambertMaterial({color: 0xff0000}));
							var texture1  = new THREE.ImageUtils.loadTexture('styles/default/images/earth/5142ca20-ad9a-48d2-a1bb-840a2248fef2.jpg');
							mesh = new THREE.Mesh(
								new THREE.SphereGeometry(50,50,50),
								new THREE.MeshLambertMaterial({map: texture1})
							);
							scene.add(mesh);
						})();
						var moon;
						//moon
						(function initMoon(){
							//mesh = new THREE.Mesh( new THREE.SphereGeometry( 20, 20, 20 ), new THREE.MeshLambertMaterial({color: 0xff0000}));
							var texture1  = new THREE.ImageUtils.loadTexture('styles/default/images/earth/3073083b-fd15-47d6-bca7-7a589db1bb61.jpg');
							moon = new THREE.Mesh(
								new THREE.SphereGeometry(10,20,20),
								new THREE.MeshLambertMaterial({map: texture1})
							);
							moon.position.set(-100,0,0);
							scene.add(moon);
						})();
						//box
						/* (function initCube(){
							var plane = new THREE.Mesh(new THREE.CubeGeometry(30, 30, 30), new THREE.MeshLambertMaterial({
					            color: 0xff0000
					        }));
							plane.position.x = 100;
							plane.position.z = 100;
							scene.add(plane);
						})(); */
						//shadow
						(function(){
							var plane = new THREE.Mesh( new THREE.PlaneGeometry( 300, 300, 3, 3 ), new THREE.MeshBasicMaterial( { map: THREE.ImageUtils.loadTexture( 'styles/default/images/earth/efd1edc0-500e-4fec-92b0-88a963d8bb97.png' ), overdraw: true } ) );
							plane.position.set(0,-100,-100);
							plane.rotation.x = 30;
							scene.add( plane );
						})();
						//lang
						(function initLight(){
							var light = new THREE.DirectionalLight(0xFFFFFF, 1.0, 0);
							light.position.set( -500, 0, 500 );
							scene.add(light);
						})();
						function animate() {
							requestAnimationFrame( animate );
							render();
						}
						var t=0;
						function render() {
							t++;
							renderer.clear();
							mesh.rotation.y -= 0.001;
							moon.position.set( 70*Math.sin(t/200) , 0 ,70*Math.cos(t/200));
							renderer.render( scene, camera );
						}
						animate();
					}
				};
				box.openDialog(bg, dialog, params.item);
				
			}
			return box.createChild(child5,child5_hover,params.item);
		}
		
		function openDelivered(){
			var item = deliverItem;
			
			/* var child1 = createChild({
				child: 'styles/default/images/child/deliver_01_black.png',
				childhover: 'styles/default/images/child/deliver_01.png',
				body: 'styles/default/images/dialog/deliver_largeview_01.jpg',
				item: item,
				image: {
					height: 500,
					left: 120,
					top: "center"
				},
				title: "About Me",
				subTitle: "123456",
				imgWidth: 3407,
				imgHeight: 500
			}); */
			var child1 = createVideo({
				child: 'styles/default/images/child/deliver_01_black.png',
				childhover: 'styles/default/images/child/deliver_01.png',
				body: "styles/default/images/video/newbee.mp4",
				bodyImage: "styles/default/images/video/newbee.png",
				item: item,
				image: {
					width: 800,
					height: 450,
					left: "center",
					top: "center"
				},
				title: "Newbee",
				subTitle: "Launched on HP desktop and laptop at Q1 of 2012. Take over the old version of OOBE and completely redesign a new version."
			});
			
			var child2 = createChild({
				child: 'styles/default/images/child/deliver_02_black.png',
				childhover: 'styles/default/images/child/deliver_02.png',
				body: 'styles/default/images/dialog/deliver_largeview_02.jpg',
				item: item,
				image: {
					height: 500,
					left: 165,
					top: "center"
				},
				title: "HP reader on Web-OS",
				subTitle: "Launched on Web-OS app store. A Chinese version of webOS Kindle eReader with China specific look and feel. Provide a convenient ebook importing function and a special reading mode for users to read Chinese in traditional visual look.",
				imgWidth: 3636,
				imgHeight: 500
			});
			
			var child3 = createChild({
				child: 'styles/default/images/child/deliver_03_black.png',
				childhover: 'styles/default/images/child/deliver_03.png',
				body: 'styles/default/images/dialog/deliver_largeview_03.jpg',
				item: item,
				image: {
					height: 500,
					left: 165,
					top: "center"
				},
				title: "HP Linkup",
				subTitle: "Launched on HP desktop and laptop at 2010. An application which allows user easily connect his HP laptop to desktop. User could operate laptop on desktop , and provides a hassle-free way to transfer photos between laptop and desktop by drag and drop.",
				imgWidth: 3407,
				imgHeight: 500
			});
			
			
			var child4 = createChild({
				child: 'styles/default/images/child/deliver_04_black.png',
				childhover: 'styles/default/images/child/deliver_04.png',
				body: 'styles/default/images/dialog/deliver_largeview_04.jpg',
				item: item,
				image: {
					height: 500,
					left: 165,
					top: "center"
				},
				title: "Facebook Tile",
				subTitle: "Launched on TouchSmart at 2010. Deliver to HP TouchSmart customers a touch-centric Facebook experience within the HP TouchSmart Software Suite.",
				imgWidth: 3763,
				imgHeight: 500
			});
			
			var child5 = createChild({
				child: 'styles/default/images/child/deliver_05_black.png',
				childhover: 'styles/default/images/child/deliver_05.png',
				body: 'styles/default/images/dialog/deliver_largeview_05.jpg',
				item: item,
				image: {
					height: 500,
					left: 165,
					top: "center"
				},
				title: "HP Marketplace",
				subTitle: "A new application download store that provides a destination for users to access free and paid software. User could purchase, download, and installation of paid software and customize their PC experience by specific HP offers.",
				imgWidth: 5035,
				imgHeight: 500
			});
			
			/* var child5 = createVideo({
				child: 'styles/default/images/child/deliver_05_black.png',
				childhover: 'styles/default/images/child/deliver_05.png',
				body: "styles/default/images/video/kumo.mp4",
				bodyImage: "styles/default/images/video/kumo.png",
				item: item,
				image: {
					width: 654,
					height: 450,
					left: "center",
					top: "center"
				},
				title: "Video"
			}); */
			
			var child6 = createChild({
				child: 'styles/default/images/child/deliver_06_black.png',
				childhover: 'styles/default/images/child/deliver_06.png',
				body: 'styles/default/images/dialog/deliver_largeview_06.jpg',
				item: item,
				image: {
					height: 500,
					left: 165,
					top: "center"
				},
				title: "Photo Fun",
				subTitle: "An easy and fun photo application in metro style that enable users in China to create personalized photos and share on China partner services. provides an easy-to-use, lightweight photo editing tool.",
				imgWidth: 5679,
				imgHeight: 500
			});
			
			var child7 = createChild({
				child: 'styles/default/images/child/deliver_07_black.png',
				childhover: 'styles/default/images/child/deliver_07.png',
				body: 'styles/default/images/dialog/deliver_largeview_07.jpg',
				item: item,
				image: {
					height: 500,
					left: 165,
					top: "center"
				},
				title: "Photo capture",
				subTitle: "A webcam app that allows users to take photo snapshots with unique fun effects and share to Snapfish and China social networking sites. An easy-to-use webcam tool that even young children know how to use.",
				imgWidth: 4721,
				imgHeight: 500
			});
			
			var child8 = createChild({
				child: 'styles/default/images/child/deliver_08_black.png',
				childhover: 'styles/default/images/child/deliver_08.png',
				body: 'styles/default/images/dialog/deliver_largeview_08.jpg',
				item: item,
				image: {
					height: 500,
					left: 165,
					top: "center"
				},
				title: "HP connected reader",
				subTitle: "An unique e-reading application in metro style that enable users in China to purchase, download, import books that they interested. Provide a reading design which could best support a comfort and high efficiency reading mode. ",
				imgWidth: 4280,
				imgHeight: 500
			});
			
			var child = [child1, child2, child3, child4, child5, child6, child7, child8];
			box.openChild(bg,child,item,{
				close: function(){
					vi.goIndex(0);
					return true;
				}
			});
		}
		var conceptsItem = box.createItem(bg,{
			color: "#7AB42C",
			title: "Concepts<br>&<br>Inspirations",
			hover: "05",
			left:9,
			top:3,
			onclick: function(_this, item){
				openConcepts();
				vi.goIndex(2);
			}
		});
		function openConcepts(){
			var item = conceptsItem;
			/* var child1 = $("<div class='full'><img src='styles/default/images/child/concept_01_black.png' class='full'></div>");
			child1.on("click",function(){
				box.openDialog(bg, {
					left: 0,
					top: 0,
					row: 5,
					cell: 3,
					style: {
						"background-image": "url(styles/default/images/b6eeaa6791756cb38123c205622feff8.jpg)"
					},
					childlist: item.childlist
				}, item);
			});
			var child1_hover = $("<div class='full'><img src='styles/default/images/child/concept_01.png' class='full'></div>");
			child1 = box.createChild(child1,child1_hover,item);
			var child2 = $("<div class='full'><img src='styles/default/images/child/concept_02_black.png' class='full'></div>");
			var child2_hover = $("<div class='full'><img src='styles/default/images/child/concept_02.png' class='full'></div>");
			child2 = box.createChild(child2,child2_hover,item);
			var child3 = $("<div class='full'><img src='styles/default/images/child/concept_03_black.png' class='full'></div>");
			var child3_hover = $("<div class='full'><img src='styles/default/images/child/concept_03.png' class='full'></div>");
			child3 = box.createChild(child3,child3_hover,item);
			var child4 = $("<div class='full'><img src='styles/default/images/child/concept_04_black.png' class='full'></div>");
			var child4_hover = $("<div class='full'><img src='styles/default/images/child/concept_04.png' class='full'></div>");
			child4 = box.createChild(child4,child4_hover,item);
			var child5 = $("<div class='full'><img src='styles/default/images/child/concept_05_black.png' class='full'></div>");
			var child5_hover = $("<div class='full'><img src='styles/default/images/child/concept_05.png' class='full'></div>");
			child5 = box.createChild(child5,child5_hover,item);
			var child6 = $("<div class='full'><img src='styles/default/images/child/concept_06_black.png' class='full'></div>");
			var child6_hover = $("<div class='full'><img src='styles/default/images/child/concept_06.png' class='full'></div>");
			child6 = box.createChild(child6,child6_hover,item);
			var child = [child1, child2, child3, child4, child5, child6];
			box.openChild(bg,child,item,{
				close: function(){
					vi.goIndex(0);
					return true;
				}
			}); */
			
			var child1 = createChild({
				child: 'styles/default/images/child/concept_01_black.png',
				childhover: 'styles/default/images/child/concept_01.png',
				body: 'styles/default/images/dialog/concept_largeview_01.jpg',
				item: item,
				image: {
					height: 500,
					left: 165,
					top: "center"
				},
				title: "Share Space",
				subTitle: "Concept of having a sharing space online with friends. User could share a song with a friend and listen it together in real time.",
				imgWidth: 1399,
				imgHeight: 500
			});
			
			var child2 = createChild({
				child: 'styles/default/images/child/concept_02_black.png',
				childhover: 'styles/default/images/child/concept_02.png',
				body: 'styles/default/images/dialog/concept_largeview_02.jpg',
				item: item,
				image: {
					height: 500,
					left: 165,
					top: "center"
				},
				title: "Share Space",
				subTitle: "Concept two of having a sharing space online with friends. User could share a song or a document or anything else with a friend and having fun together in real time.",
				imgWidth: 1399,
				imgHeight: 500
			});
			var child3 = createChild({
				child: 'styles/default/images/child/concept_03_black.png',
				childhover: 'styles/default/images/child/concept_03.png',
				body: 'styles/default/images/dialog/concept_largeview_03.jpg',
				item: item,
				image: {
					height: 500,
					left: 165,
					top: "center"
				},
				title: "Socktop - live view",
				subTitle: "Concept of creating a unique view for user's favorite friends on their social network.",
				imgWidth: 2537,
				imgHeight: 500
			});
			var child4 = createChild({
				child: 'styles/default/images/child/concept_04_black.png',
				childhover: 'styles/default/images/child/concept_04.png',
				body: 'styles/default/images/dialog/concept_largeview_04.jpg',
				item: item,
				image: {
					height: 500,
					left: 165,
					top: "center"
				},
				title: "Socktop - live view",
				subTitle: "Concept of creating a unique view for user's favorite friends on their social network.",
				imgWidth: 2538,
				imgHeight: 500
			});
			var child5 = createChild({
				child: 'styles/default/images/child/concept_05_black.png',
				childhover: 'styles/default/images/child/concept_05.png',
				body: 'styles/default/images/dialog/concept_largeview_05.jpg',
				item: item,
				image: {
					height: 500,
					left: 165,
					top: "center"
				},
				title: "Facebook on Touchsmart",
				subTitle: "An touch based design. Provide an out-of-box, unique user experience for user to play with their Facebook newsfeed.",
				imgWidth: 5677,
				imgHeight: 500
			});
			var child6 = createChild({
				child: 'styles/default/images/child/concept_06_black.png',
				childhover: 'styles/default/images/child/concept_06.png',
				body: 'styles/default/images/dialog/concept_largeview_06.jpg',
				item: item,
				image: {
					height: 505,
					left: 165,
					top: "center"
				},
				title: "Moonrise",
				subTitle: "An touch based design for Android pad. Explore the possibility of utilizing timeline to present photos from different source. Attempt to create a new design pattern of grouping editing controls. Maximize the possibility of gesture operating when editing photo.",
				imgWidth: 3560,
				imgHeight: 505
			});
			var child7 = createVideo({
				child: 'styles/default/images/child/concept_07_black.png',
				childhover: 'styles/default/images/child/concept_07.png',
				body: "styles/default/images/video/clock.mp4",
				bodyImage: "styles/default/images/video/clock.png",
				item: item,
				image: {
					width: 783,
					height: 460,
					left: "center",
					top: "center"
				},
				title: "Clocknotes",
				subTitle: "Play the concept of merging user’s schedule with the clock."
			});
			
			var child8 = createDom({
				child: 'styles/default/images/child/concept_08_black.png',
				childhover: 'styles/default/images/child/concept_08.png',
				body: "<div id='container' width='700' height='500'></div>",
				bodyImage: "styles/default/images/earth/earth.png",
				item: item,
				image: {
					width: 699,
					height: 600,
					left: "center",
					top: "center"
				},
				title: "3D Globe",
				subTitle: "Use WebGL to build this 3D Globe. It is real 3D technology!"
			});
			
			var child = [child1, child2, child3, child4, child5, child6, child7, child8];
			box.openChild(bg,child,item,{
				close: function(){
					vi.goIndex(0);
					return true;
				}
			});
		}
		var testingItem = box.createItem(bg,{
			color: "#964547",
			title: "Quick<br>Usability<br>Testing",
			hover: "06",
			left:10,
			top:4,
			onclick: function(_this, item){
				vi.goIndex(3);
				openTesting();
			}
		});
		function openTesting(){
			var item = testingItem;
			var child1 = createChild({
				child: 'styles/default/images/child/testing_01_black.png',
				childhover: 'styles/default/images/child/testing_01.png',
				body: 'styles/default/images/dialog/testing_largeview_01.jpg',
				item: item,
				cell: 6,
				image: {
					height: 500,
					width: 375,
					left: "center",
					top: "center"
				},
				title: "Photo Capture Usability Testing Post",
				//subTitle: "Launched on Web-OS app store. A Chinese version of webOS Kindle eReader with China specific look and feel. Provide a convenient ebook importing function and a special reading mode for users to read Chinese in traditional visual look.",
				imgWidth: 375,
				imgHeight: 500
			});
			
			var child2 = createChild({
				child: 'styles/default/images/child/testing_02_black.png',
				childhover: 'styles/default/images/child/testing_02.png',
				body: 'styles/default/images/dialog/testing_largeview_02.jpg',
				item: item,
				cell: 8,
				image: {
					height: 500,
					width: 625,
					left: "center",
					top: "center"
				},
				title: "HP reader on Web-OS Usability Testing Post",
				//subTitle: "Launched on Web-OS app store. A Chinese version of webOS Kindle eReader with China specific look and feel. Provide a convenient ebook importing function and a special reading mode for users to read Chinese in traditional visual look.",
				imgWidth: 625,
				imgHeight: 500
			});
			
			var child3 = createChild({
				child: 'styles/default/images/child/testing_03_black.png',
				childhover: 'styles/default/images/child/testing_03.png',
				body: 'styles/default/images/dialog/testing_largeview_03.jpg',
				item: item,
				cell: 6,
				image: {
					height: 500,
					width: 375,
					left: "center",
					top: "center"
				},
				title: "Photo Fun Usability Testing Post",
				//subTitle: "Launched on HP desktop and laptop at 2010. An application which allows user easily connect his HP laptop to desktop. User could operate laptop on desktop , and provides a hassle-free way to transfer photos between laptop and desktop by drag and drop.",
				imgWidth: 375,
				imgHeight: 500
			});
			
			
			var child4 = createChild({
				child: 'styles/default/images/child/testing_04_black.png',
				childhover: 'styles/default/images/child/testing_04.png',
				body: 'styles/default/images/dialog/testing_largeview_04.jpg',
				item: item,
				cell: 6,
				image: {
					height: 500,
					width: 353,
					left: "center",
					top: "center"
				},
				title: "HP Marketplace Usability Testing Post",
				//subTitle: "Launched on TouchSmart at 2010. Deliver to HP TouchSmart customers a touch-centric Facebook experience within the HP TouchSmart Software Suite.",
				imgWidth: 353,
				imgHeight: 500
			});
			
			var child5 = createChild({
				child: 'styles/default/images/child/testing_05_black.png',
				childhover: 'styles/default/images/child/testing_05.png',
				body: 'styles/default/images/dialog/testing_largeview_05.jpg',
				item: item,
				image: {
					height: 450,
					left: 165,
					top: "center"
				},
				title: "HP reader on Web-OS Usability Testing Round One",
				//subTitle: "A new application download store that provides a destination for users to access free and paid software. User could purchase, download, and installation of paid software and customize their PC experience by specific HP offers.",
				imgWidth: 2100,
				imgHeight: 450
			});
			
			var child6 = createChild({
				child: 'styles/default/images/child/testing_06_black.png',
				childhover: 'styles/default/images/child/testing_06.png',
				body: 'styles/default/images/dialog/testing_largeview_06.jpg',
				item: item,
				image: {
					height: 450,
					left: 165,
					top: "center"
				},
				title: "HP reader on Web-OS Usability Testing Round Two",
				//subTitle: "An easy and fun photo application in metro style that enable users in China to create personalized photos and share on China partner services. provides an easy-to-use, lightweight photo editing tool.",
				imgWidth: 1964,
				imgHeight: 450
			});
			
			var child7 = createChild({
				child: 'styles/default/images/child/testing_07_black.png',
				childhover: 'styles/default/images/child/testing_07.png',
				body: 'styles/default/images/dialog/testing_largeview_07.jpg',
				item: item,
				image: {
					height: 450,
					left: 165,
					top: "center"
				},
				title: "Photo Fun Usability Testing",
				//subTitle: "A webcam app that allows users to take photo snapshots with unique fun effects and share to Snapfish and China social networking sites. An easy-to-use webcam tool that even young children know how to use.",
				imgWidth: 3400,
				imgHeight: 450
			});
			
			var child = [child1, child2, child3, child4, child5, child6, child7];
			box.openChild(bg,child,item,{
				close: function(){
					vi.goIndex(0);
					return true;
				}
			});
		}
		var aboutItem = box.createItem(bg,{
			color: "#332727",
			title: "About<br>Us",
			hover: "01",
			left:11,
			top:1,
			onclick: function(_this, item){
				vi.goIndex(4);
				openAboutUs();
			}
		});
		function openAboutUs(){
			var dialog = {
				left: "center",
				top: "center",
				row: 6,
				cell: 10,
				image:{
					width: 1197,
					height: 593,
					left: "center",
					top: 0
				},
				btn: {
					close: {
						event: function(){
							vi.goIndex(0);
							return true;
						}
					}
				},
				bodyStyle: {
					"background": "url(styles/default/images/aboutus_detail.jpg) no-repeat center center black",
					"border": "1px solid black"
				},
				titleStyle: {
					"background-color": "black",
					"border": "1px solid black"
				},
				bodyContent: $("<div style='text-align:center; vertical-align:middle;'><img src='styles/default/images/aboutus_detail.jpg' style='' ></div>"),
				titleContent: $("<div style='color: white;font-size: 24px; padding-left: 30px;  line-height: 121px;'>About Us</div>")
			};
			box.openDialog(bg, dialog, aboutItem);
		}
		function initElement(){
			$(".child").remove();
			$(".close").remove();
			$(".item-cover").remove();
			$(".dialog").remove();
			$(".dialog-content").remove();
			$(".item > .body").show();
		}
		function hasSelect(_this){
			if(_this.hasClass("select")){
				return false;
			}else{
				_this.parent().find(".select").removeClass("select");
				_this.addClass("select");
				return true;
			}
		}
		var vi = box.createNavigate({
			id: "navigate",
			list: [{
				text: "Home",
				onclick: function(){
					if(!hasSelect($(this))){
						return false;
					}
					var openItem = box.getOpenItem();
					if(openItem !=null && openItem.childlist){
						//box.closeItem(openItem.childlist, $(".close"), openItem);
						box.closeItem(openItem.childlist, {
							self: $(".close"),
							item: openItem,
							cover: "item-cover"
						});
					}
					if(openItem !=null && openItem.dialog){
						if(openItem.dialog.titleDiv){
							openItem.dialog.titleDiv.remove();
						}
						if(openItem.dialog.contentDiv){
							openItem.dialog.contentDiv.remove();
						}
						box.closeItem(openItem.dialogitem,{
							self: openItem.dom.find(".close"),
							item: openItem,
							cover: "cover-dialog"
						});
					}
				}
			},{
				text: "Delivered Project",
				onclick: function(){
					if(!hasSelect($(this))){
						return false;
					}
					initElement();
					box.hideAllItems(box.getOpenItem());
					openDelivered();
				}
			},{
				text: "Concepts & Inspireations",
				onclick: function(){
					if(!hasSelect($(this))){
						return false;
					}
					initElement();
					box.hideAllItems(box.getOpenItem());
					openConcepts();
				}
			},{
				text: "Quick Usability Testing",
				onclick: function(){
					if(!hasSelect($(this))){
						return false;
					}
					initElement();
					openTesting();
				}
			},{
				text: "About Us",
				onclick: function(){
					if(!hasSelect($(this))){
						return false;
					}
					initElement();
					openAboutUs();
				}
			}],
			defaults: 0
		});
	});
</script>