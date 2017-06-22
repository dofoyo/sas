<%@ page contentType="text/html; charset=GB2312"%>
<html>
<head>
<title>Download Yjyg</title>

<style> 
.progressbar{ 
    background-color:#eee; 
    color:#222; 
    height:20px; 
    width:580px; 
    border:1px solid #bbb; 
    text-align:center; 
    position:relative; 
} 
.progressbar.bar { 
    background-color:#6CAF00; 
    height:16px; 
    width:0; 
    position:absolute; 
    left:0; 
    top:0; 
    z-index:10; 
} 
.progressbar.text { 
    height:16px; 
    position:absolute; 
    left:0; 
    top:0; 
    width:100%; 
    line-height:16px; 
    z-index:100; 
} 
</style> 

<script type="text/javascript" src="./js/inputDate.js"></script>

<script type="text/javascript">
var index;
var oldMsg;
         
//创建xmlHttpRequest对象
function createXMLHttpRequest(){
	var xmlHttp;
	if (window.ActiveXObject){
		xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
	}else if(window.XMLHttpRequest){
		xmlHttp = new XMLHttpRequest();
	}
	return xmlHttp;
}
        
//执行方法
function go(){
	showButton(false);  //隐藏按钮
	emptyInfo();
	index = 0;
	progress("[0/100]");
	
	var yearandmonth	= document.getElementById("yearandmonth").value;
	var page			= document.getElementById("page").value;
	
	var task = "create";
	task = task + "&yearandmonth="+yearandmonth;
	task = task + "&page="+page;
		
	sendToServer(task,'');
}
       
//发送
function sendToServer(task,index){
	//alert('task=' + task + ',index=' + index);
    var url = "downloadYjyg?task="+task+"&index=" + index;
	xmlHttp = createXMLHttpRequest();
    xmlHttp.open("GET",url,true);
    xmlHttp.onreadystatechange = receiveFromServer;
    xmlHttp.send(null);
}
        
//回调方法
function receiveFromServer(){
 if (xmlHttp.readyState == 4){
  if (xmlHttp.status == 200){
        var msg = xmlHttp.responseXML.getElementsByTagName("msg")[0].firstChild.data;
        alert(msg);
        progress(msg);
        appendInfo(msg);
        
        if (!isOver(msg)){
			var i = getIndex(msg);
        	if(i > index){
         		index = parseInt(i) + 1;
        	}
         	setTimeout("sendToServer('poll',"+ index +")",200);
        }else{
        	showButton(true);
        }
     }
   }
}

function getIndex(msg){
	var percent = msg.substring(msg.lastIndexOf("[")+1,msg.lastIndexOf("]"));
	//alert(percent);
	var i = percent.substring(0,percent.indexOf("/"));
	return i;
}

function showButton(flag){
	var button = document.getElementById("go");
	if(flag){
		button.disabled = false;  
	}else{
		button.disabled = true;  //隐藏按钮
	}
}


function isOver(msg){
	var flag = false;
	if(msg.indexOf("over")!=-1){
		flag = true;
	}
	return flag;
}
        
function appendInfo(info){
	if(info != "--" && oldMsg!=info){
		oldMsg = info;
	    var str = document.getElementById("info").value;
	    document.getElementById("info").value = str + "\n" + info;
	    focusInfo();
	}
}
        
function focusInfo(){   
	var e = document.getElementById("info");   
	var r =e.createTextRange();   
	r.moveStart('character',e.value.length);   
	r.collapse(true);   
	r.select();   
}
		
function emptyInfo(){
	document.getElementById("info").value = "";
}   
		
function progress(msg){
	if(msg.indexOf("]")!=-1){
		var percent = getPercent(msg);
		document.getElementById("progressbar_text").innerHTML = percent + "%";
		var obj=document.getElementById("progressbar");
		obj.style.width = percent + "%";
	}
}
function getPercent(msg){
	if(isOver(msg)){
		return 100;
	}
	var str = msg.substring(msg.lastIndexOf("[")+1,msg.lastIndexOf("]"));
	var percent =parseInt(eval(str)*100); 
	return percent>100 ? 100 : percent;
}		
        
</script>
  
 
</head>
<body bgcolor="#ffffff">
<h1>Download Yjyg
<input type="button" value="运行" id="go" onclick="go()" />
</h1>
<table>
<tbody>
	<tr>
		<td>
			<table>
				<tr>
					<td>
						Year and Month:<input type="text" name="yearandmonth" id="yearmandmonth" size="6" value="201206"/> --- just like: 201206
					</td>
					<td> _______ </td>
					<td>
						Page:<input type="text" name="page" id="page" size="6" value="1"/>  ---- it is int type.		 			
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td>
			<div class="progressbar"> 
				<div class="text" id="progressbar_text">0%</div> 
				<div class="bar" id="progressbar" style="width: 0%;"></div> 
			</div> 
		</td>	
	</tr>
	<tr>
		<td><textarea rows="20" cols="80" name="info" id="info"></textarea></td>
	</tr>
</tbody>
</table>
</body>
</html>
