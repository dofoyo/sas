// JavaScript Document
//-----------------------------------------
function hideElement(eltname) { getXBrowserRef(eltname).visibility = 'hidden'; }

function getIEPos(elt,which) {
 iPos = 0
 while (elt!=null) {
  iPos += elt["offset" + which];
  elt = elt.offsetParent;
 }
 if(isNaN(iPos)){iPos=0;}
 return iPos
}

// �õ�IE�и�Ԫ��������λ��������ʹ���Ԫ����һ�������
function getIEPosX(elt) { return getIEPos(elt,"Left"); }
function getIEPosY(elt) { return getIEPos(elt,"Top"); }

function setPosition(elt,positionername) {
 positioner = null;
 if (isIE) {
  positioner = $(positionername);
  var PosX = getIEPos(positioner,"Left");
  var PosY = getIEPos(positioner,"Top");
  var DocX = document.body.clientWidth;
  var DocY = document.body.clientHeight;
  elt.left = ((DocX - PosX) < 200) ? (PosX - 120) : PosX + 30;
  elt.top  = ((DocY - PosY) < 160) ? (PosY - 160) : PosY + 20;

 } else {
  positioner = document.images[positionername];
  elt.left = positioner.x;
  elt.top = positioner.y;
 }
}

function getXBrowserRef(eltname) {
 return (isIE ? $(eltname).style : document.layers[eltname]);
}

function toggleVisible(eltname,positionerName) {
 elt = getXBrowserRef(eltname);
 if (elt.visibility == 'visible' || elt.visibility == 'show') {
   elt.visibility = 'hidden';
 } else {
   setPosition(elt,positionerName);
   elt.visibility = 'visible';
 }
}

function inputDate(targetName){
    newCalendar(calendardivID,$(targetName));
    toggleVisible(calendardivID,targetName);
}

//-----------

function getDays(month, year){
	//����ѡ�������Ƿ������ꣿ
	if (1 == month)
		return ((0 == year % 4) && (0 != (year % 100))) || (0 == year % 400) ? 29 : 28;
	else
		return daysInMonth[month];
}

function getToday(){
	// �õ����������
	this.now = new Date();
	this.year = this.now.getFullYear();
	this.month = this.now.getMonth();
	this.day = this.now.getDate();

}

function newCalendar(eltName,attachedElement){
	if (attachedElement){
	       if (displayDivName && displayDivName != eltName) hideElement(displayDivName);
	       displayElement = attachedElement;
	    }
	    displayDivName = eltName;
            today = new getToday();
            var parseYear = parseInt(displayYear + '');
            var newCal = new Date(parseYear,displayMonth,1);
            var day = -1;
            var startDayOfWeek = newCal.getDay();
            if ((today.year == newCal.getFullYear()) &&
                  (today.month == newCal.getMonth()))
	    {
               day = today.day;
            }
            var intDaysInMonth =
               getDays(newCal.getMonth(), newCal.getFullYear());
            var daysGrid = makeDaysGrid(startDayOfWeek,day,intDaysInMonth,newCal,eltName)
	    if (isIE) {
	       var elt = $(eltName);
	       elt.innerHTML = daysGrid;
	    } else {
	       var elt = document.layers[eltName].document;
	       elt.open();
	       elt.write(daysGrid);
	       elt.close();
	}
}

function incMonth(delta,eltName) {
	displayMonth += delta;
	if (displayMonth >= 12){
		displayMonth = 0;
		incYear(1,eltName);}
	else if (displayMonth <= -1){
		displayMonth = 11;
		incYear(-1,eltName);}
	else
		newCalendar(eltName);
}

function incYear(delta,eltName){
	displayYear = parseInt(displayYear + '') + delta;
	newCalendar(eltName);
}

function makeDaysGrid(startDay,day,intDaysInMonth,newCal,eltName){
	var daysGrid;
	var month = newCal.getMonth();
	var year = newCal.getFullYear();
	var isThisYear = (year == new Date().getFullYear());
	var isThisMonth = (day > -1)
	daysGrid = '<table border=1 cellspacing=0 cellpadding=2><tr><td bgcolor=#ffffff nowrap>';
	daysGrid += '<font face="courier new, courier" size=2>';
	daysGrid += '<a href="javascript:hideElement(\'' + eltName + '\')">x</a>';
	daysGrid += '&nbsp;&nbsp;';
	daysGrid += '<a href="javascript:incMonth(-1,\'' + eltName + '\')">&laquo; </a>';

	daysGrid += '<b>';
	if (isThisMonth)
		daysGrid += '<font color=blue>' + months[month] + '</font>';
	else
		daysGrid += months[month];
	daysGrid += '</b>';

	daysGrid += '<a href="javascript:incMonth(1,\'' + eltName + '\')"> &raquo;</a>';
	daysGrid += '&nbsp;&nbsp;&nbsp;';
	daysGrid += '<a href="javascript:incYear(-1,\'' + eltName + '\')">&laquo; </a>';

	daysGrid += '<b>';
	if (isThisYear)
		daysGrid += '<font color=blue>' + year + '</font>';
	else
		daysGrid += ''+year;
	daysGrid += '</b>';

	daysGrid += '<a href="javascript:incYear(1,\'' + eltName + '\')"> &raquo;</a><hr>';
	daysGrid += '<table border=0 width=100%>';
	daysGrid += '<tr>';
	daysGrid += '<td align=center><font color=red>��</font></td>';
	daysGrid += '<td align=center>һ</td>';
	daysGrid += '<td align=center>��</td>';
	daysGrid += '<td align=center>��</td>';
	daysGrid += '<td align=center>��</td>';
	daysGrid += '<td align=center>��</td>';
	daysGrid += '<td align=center><font color=red>��</font></td>';
	daysGrid += '</tr>';
	var dayOfMonthOfFirstSunday = (7 - startDay + 1);
	for (var intWeek = 0; intWeek < 6; intWeek++){
		var dayOfMonth;
		for (var intDay = 0; intDay < 7; intDay++){
			dayOfMonth = (intWeek * 7) + intDay + dayOfMonthOfFirstSunday - 7;
			if (dayOfMonth <= 0)
				daysGrid += "<td>&nbsp;</td>";
			else if (dayOfMonth <= intDaysInMonth) {
				var color = "black";
				if (intDay == 0 || intDay == 6)
					color = "red";
				if (day > 0 && day == dayOfMonth)
					color = "blue";
				daysGrid += '<td align=center><a href="javascript:setDay(';
				daysGrid += dayOfMonth + ',\'' + eltName + '\')" '
				daysGrid += 'style="color:' + color + '">';
				var dayString = dayOfMonth + "</a></td> ";
				if (dayString.length == 6)
					dayString = '0' + dayString;
				daysGrid += dayString;
			}
		}
		if (dayOfMonth < intDaysInMonth)
			daysGrid += "</tr>";
	}
	return daysGrid + "</table></td></tr></table>";
}

function setDay(day,eltName) {
	displayElement.value = displayYear + "-" +(displayMonth + 1) + "-" + day;
	hideElement(eltName);
}

//----------
calendardivID = "calendardiv"
document.write("<DIV id=" + calendardivID + " style='POSITION: absolute; z-index:3'></DIV>");

// һ���򵥵Ĳ����Ƿ�IE������ı��ʽ
isIE = (document.all ? true : false);

// ��ʼ�·ݼ����·���������
var months = new Array("һ����", "������", "������", "�ġ���", "�塡��", "������", "�ߡ���", "�ˡ���", "�š���", "ʮ����", "ʮһ��", "ʮ����");
var daysInMonth = new Array(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
var displayMonth = new Date().getMonth();
var displayYear = new Date().getFullYear();
var displayDivName;
var displayElement;

// ��ʾ��������·ݵ�����
today = new getToday();