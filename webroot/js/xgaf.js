function winOpen(url){
	var arg='height=800,width=1280,toolbar=no,menubar=no,status=yes,resizable=yes,scrollbars=yes,top=60,left=60';
	window.open(url,'popWindow',arg);
}

/*
behavior=json is fixed.
parameter is also fixed.
*/
function doAjaxRequest(url,parameter,setFunction){
	alert('doAjaxRequest');
	new Ajax.Request(url+'?behavior=json&parameter='+parameter,{method:'get',parameters:'',onSuccess: function(req){var json=req.responseText.strip();eval(setFunction+'(json.evalJSON())');}});
}

//behavior=selectList is fixed.
function doSelect(url,setFunction){
	winOpen(url + '?behavior=selectList&setFunction='+setFunction);
}

function format(num,mask){
   var str = mask.substring(mask.indexOf(".")+1);
   var n = str.length;
   var result = Math.round(num*Math.pow(10,n))/Math.pow(10,n);  
   return  result;  
}
