<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%
	// context root를 찾기 위한 프로세스... (context가 변경되더라도 바로 적용할 수 있도록)
	HttpSession session  = request.getSession();
	String contextRoot = session.getServletContext().getContextPath();
	
	// windows와 linux의 context 결과값은 서로 다르다!! 맨뒤의 "/"를 일괄적으로 붙이기 위하여 조건문으로 확실히 한다!!
	if(!"/".equals(contextRoot.substring(contextRoot.length()-1, contextRoot.length())) ){
		contextRoot += "/";
	}
%>
<script>

// 전역변수 선언하는 부분 (사이트 전체 사용하는 전역변수)
var context = "<%=contextRoot%>";
var speedListClosing = 500;		//페이지에서 목록중 상세목록 열고 닫는 속도 (1000 : 1초)
var searchTitle = "";
var dataOptionList;		// 자료 종류 array
var dataDate;				// 자료 시점
var dataForm;				// 자료 형식 
var fromChange=false;		// 변동정보에서 바로 추가되는 경우

var userEmail = "<%=session.getAttribute("userEmail")%>";


// 전역변수 선언하는 부분 끝

</script>

<script src="<%=contextRoot%>common/js/jquery-2.2.3.min.js"></script>
<script src="<%=contextRoot%>common/js/jqui/jquery-ui.js"></script>

<c:if test="${param.page == 'main'}">
<script src="<%=contextRoot%>common/js/leaflet/leaflet-src.js"></script>
<script src="<%=contextRoot%>common/js/leaflet/proj4-compressed.js"></script>
<script src="<%=contextRoot%>common/js/leaflet/proj4leaflet.js"></script>
<script src="<%=contextRoot%>common/js/leaflet/Leaflet.KoreanTmsProviders.js"></script>
<script src="<%=contextRoot%>common/js/leaflet/locationfilter.js"></script>
<script src="<%=contextRoot%>common/js/leaflet/map_control.js"></script>
</c:if>
<!-- <script src="<%=contextRoot%>common/js/virtualDB.js"></script> -->
<script src="<%=contextRoot%>common/js/realDB.js"></script>

<script src="<%=contextRoot%>common/js/common.js"></script>	<!-- 가장 마지막에 로드해야 하는 항목!!!!! (전역변수등 각종 셋팅이 있음) -->

<script>
// processing for area selection 

var sidoData = null;
var sggData = null;
var umdData = null;

var selectedSidoIndex = -1;
var selectedSggIndex = -1;
var selectedUmdIndex = -1;

var selectedAreaKeys = [];

var orderType = -1;

function disableDySearching()
{
	clearDoyeopSelectionControls();
	
	$('#searchDy').prop("disabled", true);
	$('#dyCode').prop("disabled", true);
}

function enableDySearching()
{
	$('#searchDy').prop("disabled", false);
	$('#dyCode').prop("disabled", false);
}

function disableDistrictSearching()
{
	clearDistrictSelectionControls();
	
	$('#sido').css("visibility", "hidden");
	$('#sgg').css("visibility", "hidden");
	$('#umd').css("visibility", "hidden");
}

function enableDistrictSearching()
{
	$('#sido').css("visibility", "visible");
	$('#sgg').css("visibility", "visible");
	$('#umd').css("visibility", "visible");
}

function prepareOthersOnAreaSelection()
{
	$('#addSelection').click(
		function()
		{
			orderType = 0;
			disableDySearching();
			
			if(addSelectedAreaItem())
			{
				moveCandidateToSelected();
				fitToSelectedArea();
			}

			removeCandidateAreaFromLayer();
			deleteCandidateArea();
		}
	);
	
	$('#addSelectionDy').click(
		function()
		{
			orderType = 1;
			disableDistrictSearching();
			
			if(addSelectedAreaItem())
			{
				moveCandidateToSelected();
				fitToSelectedArea();
			}

			removeCandidateAreaFromLayer();
			deleteCandidateArea();
			$("#dyCode").val("");
		}
	);
	
	$('#addSelection').prop("disabled", true);
	$('#addSelectionDy').prop("disabled", true);
	
	$('#clearAreaSelection').click(
		function()
		{
			$('#areaSelected').empty();
			clearAreaSelectionControls();

			removeAllSelectedArea();
			removeCandidateAreaFromLayer();
			deleteCandidateArea();
			
			selectedAreaKeys.length = 0;
			
			enableDySearching();
			enableDistrictSearching();
		}
	);
}

function clearAreaSelectionControls()
{
	clearDistrictSelectionControls();
	
	clearDoyeopSelectionControls();
}

function clearDistrictSelectionControls()
{
	$('#sgg').empty();
	$('#umd').empty();
	$('#sido li').removeClass("on");
	selectedSidoIndex = -1;
	selectedSggIndex = -1;
	selectedUmdIndex = -1;
	
	$('#addSelection').prop("disabled", true);
}

function clearDoyeopSelectionControls()
{
	$('#dyCode').val("");
	$('#addSelectionDy').prop("disabled", true);
}

function addSelectedAreaItem()
{
	var thisKey = null;
	var areaName = null;
	if(orderType == 0)
	{
		if(selectedSidoIndex == -1)
			return false;
		
		//thisKey = JSON.stringify([selectedSidoIndex, selectedSggIndex, selectedUmdIndex]);
		thisKey = makeBjcd(selectedSidoIndex, selectedSggIndex, selectedUmdIndex);
	}
	else if(orderType == 1)
	{
		thisKey = $("#dyCode").val();
	}
	else
	{
		//console.log("unsupported order type");
	}

	for(var key in selectedAreaKeys)
	{
		if(thisKey == selectedAreaKeys[key])
			return false;
	}

	selectedAreaKeys.push(thisKey);

	if(orderType == 0)
		areaName = makeAreaName(selectedSidoIndex, selectedSggIndex, selectedUmdIndex);
	else if(orderType == 1)
		areaName = thisKey;
	else{
		//console.log("unsupported order type");
	}

	$("#areaSelected").append('<span value="' + thisKey + '">' + areaName + '<button type="button" class="del">삭제</button></span>');
	$('#areaSelected span:last button').click(			
		function()
		{
			var keyString = $(this).parent().attr("value");
			var indexToBeRemoved;
			for(var keyIndex in selectedAreaKeys)
			{
				if(keyString == selectedAreaKeys[keyIndex])
				{
					indexToBeRemoved = keyIndex;
					break;
				}
			}

			selectedAreaKeys.splice(indexToBeRemoved, 1);
			removeSelectedArea(indexToBeRemoved);
			
			if(selectedAreaKeys.length == 0)
			{
				// 모두 지워버린 경우
				enableDySearching();
				enableDistrictSearching();
			}

			$(this).parent().remove();
		}
	);

	$('#addSelection').prop("disabled", true);
	$('#addSelectionDy').prop("disabled", true);
	
	return true;
}

function makeBjcd(sidoIndex, sggIndex, umdIndex)
{
	if(sidoIndex == -1)
		return null;
	
	if(sggIndex != -1)
	{
		if(umdIndex != -1)
			return umdData[umdIndex].bjcd;
		else
			return sggData[sggIndex].bjcd;
	}
	else
		return sidoData[sidoIndex].bjcd;
}

function makeAreaName(sidoIndex, sggIndex, umdIndex)
{
	if(sidoIndex == -1)
		return null;
	
	if(sggIndex != -1)
	{
		if(umdIndex != -1)
			return sidoData[sidoIndex].name + ' ' + sggData[sggIndex].name + ' ' + umdData[umdIndex].name;
		else
			return sidoData[sidoIndex].name + ' ' + sggData[sggIndex].name;
	}
	else
		return sidoData[sidoIndex].name;
}

function makeAllSelectedAreaNames(result)
{
	var key;
	var sidoIndex, sggIndex, umdIndex;
	for(var keyIndex in selectedAreaKeys)
	{
		key = JSON.parse(selectedAreaKeys[keyIndex]);
		sidoIndex = key[0];
		sggIndex = key[1];
		umdIndex = key[2];
		
		if(keyIndex != 0)
			result += ', ';
		
		result += makeAreaName(sidoIndex, sggIndex, umdIndex);
	}
}

function prepareDistrictLists()
{
	$.ajax({
		url: "/sdmc/nfsd/sidos.ngii",
		type: "GET",
		dataType: "json",
		async: true,
		success: sidoDataArriven,
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			alert(errorThrown);
		}
	});
}

function sidoDataArriven(data)
{
	sidoData = data;
	
	makeSidoLists(data);
}

function makeSidoLists(data)
{
	if(data.length == 0)
		return;

	var ul = $("#sido");
	for(var sidoIndex in data)
	{
		//console.log(data[sido]);
		var li = "<li value='" + sidoIndex + "'>" + data[sidoIndex].name + "</li>";
		ul.append($(li));
	}
	
	$("#sido li").click(
		function()
		{
			selectedSidoIndex = $(this).val();
			
			selectedSggIndex = -1;
			selectedUmdIndex = -1;
			$("#sgg").empty();
			$("#umd").empty();

			if(selectedSidoIndex == -1)
				return;
			
			$("#sido li").removeClass("on");
			$(this).addClass("on");

			requestGeometry();
			
			$.ajax({
				url: "/sdmc/nfsd/sggs.ngii",
				type: "GET",
				dataType: "json",
				data: "sidoCode="+sidoData[selectedSidoIndex].sidoCode,
				async: true,
				success: sggDataArriven,
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					alert(errorThrown);
				}
			});
		}
	);
}

function sggDataArriven(data)
{
	sggData = data;
	
	trimSggNames(sggData);
	
	makeSggLists(sggData);	
}

function trimSggNames(sggData)
{
	var tempSggData = [];
	for(var sgg in sggData)
	{
		if(sggData[sgg].sggCode.substring(2) == '0')
		{
			tempSggData.push(sggData[sgg]);
			for(var otherSgg in sggData)
			{
				if(sgg == otherSgg)
					continue;
				
				if(sggData[otherSgg].sggCode.substring(0, 2) == sggData[sgg].sggCode.substring(0, 2))
				{
					sggData[otherSgg].name = sggData[sgg].name + ' ' + sggData[otherSgg].name;
					tempSggData.push(sggData[otherSgg]);
				}
			} 
		}
	}
	
	sggData.length = 0;
	for(var sgg in tempSggData)
		sggData.push(tempSggData[sgg]);
}

function makeSggLists(data)
{
	$("#sgg").empty();
	$("#sgg").append('<li value="-1">전체</li>');
	
	if(data == null || data.length == 0)
		return;

	for(var sgg in data)
	{
		var li = "<li value='" + sgg + "'>" + data[sgg].name + "</li>";
		$("#sgg").append($(li));
	}
	
	$("#sgg li").click(
		function()
		{
			selectedSggIndex = $(this).val();

			selectedUmdIndex = -1;
			$("#umd").empty();

			/*
			if(false && selectedSggIndex == -1)
				return;
			*/

			$("#sgg li").removeClass("on");
			$(this).addClass("on");
			
			requestGeometry();

			$.ajax({
				url: "/sdmc/nfsd/umds.ngii",
				type: "GET",
				dataType: "json",
				data: "sidoCode="+sggData[selectedSggIndex].parentSidoCode+"&sggCode="+sggData[selectedSggIndex].sggCode,
				async: true,
				success: umdDataArriven,
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					alert(errorThrown);
				}
			});
		}
	);
}

function umdDataArriven(data)
{
	umdData = data;
	
	makeUmdLists(data);
}

function makeUmdLists(data)
{
	$("#umd").empty();
	$("#umd").append('<li value="-1">전체</li>');
	
	if(data == null || data.length == 0)
		return;

	for(var umd in data)
	{
		var li = "<li value='" + umd + "'>" + data[umd].name + "</option>";
		$("#umd").append($(li));
	}
	
	$("#umd li").click(
		function()
		{
			selectedUmdIndex = $(this).val();
			
			$("#umd li").removeClass("on");
			$(this).addClass("on");
			
			requestGeometry();
		}
	);
}

function requestGeometry()
{
	if(selectedSidoIndex == -1)
		return;

	var targetUrl = null;
	var targetBjcd = null;
	if(selectedSggIndex != -1) // sgg item is selected
	{
		if(selectedUmdIndex != -1) // sido, sgg, umd defined.
		{
			// url & param for umd geometry
			targetUrl = "/sdmc/nfsd/umdGeometry.ngii"
			targetBjcd = umdData[selectedUmdIndex].bjcd;
		}
		else	// sido, sgg defined
		{
			// url & param for sgg geometry
			targetUrl = "/sdmc/nfsd/sggGeometry.ngii"
			targetBjcd = sggData[selectedSggIndex].bjcd;
		}
	}
	else	// sido defined
	{
		// url & param for sido geometry
		targetUrl = "/sdmc/nfsd/sidoGeometry.ngii"
		targetBjcd = sidoData[selectedSidoIndex].bjcd;
	}
	$.ajax({
		url: targetUrl,
		type: "GET",
		dataType: "json",
		data: "bjcd="+targetBjcd,
		async: true,
		success: geometryArriven,
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			alert(errorThrown);
		}
	});
}

function geometryArriven(data, orderType)
{
	if(orderType == 1)
	{
		$('#addSelectionDy').prop("disabled", false);
		clearDistrictSelectionControls();
	}
	else if(orderType == "success")
	{
		$('#addSelection').prop("disabled", false);
		clearDoyeopSelectionControls();
	}
	else
	{
		//console.log("invalid usage of this function : geometryArriven");
		return;
	}

	registerCandidateArea(data);
	addCandidateAreaToLayer();
	
	if(isAnySelectedArea())
		fitToBothSelectedAndCandidateArea();
	else
		fitToCandidateArea();
}

function prepareAreaSelectionControls()
{
	prepareDistrictLists();
	prepareOthersOnAreaSelection();
	prepareMaps();
}
</script>

<script>
// processing for type selection 

var selectedLayers = [];
var selectedLayerCds = [];

function prepareTypeSelectionControls()
{
	$("#clearTypeSelection").click(clearTypeSelectionControls);
	
	setupOneLayerOnOff("trans");
	$("#transGroup label:first input").click(
		function()
		{
			if($(this).is(":checked"))
				selectAllLayers("trans");
			else
				unselectAllLayers("trans");
		}
	);
	
	setupOneLayerOnOff("water");
	$("#waterGroup label:first input").click(
		function()
		{
			if($(this).is(":checked"))
				selectAllLayers("water");
			else
				unselectAllLayers("water");
		}
	);
	
	setupOneLayerOnOff("build");
	$("#buildGroup label:first input").click(
		function()
		{
			if($(this).is(":checked"))
				selectAllLayers("build");
			else
				unselectAllLayers("build");
		}
	);
	
	setupOneLayerOnOff("eleva");
	$("#elevaGroup label:first input").click(
		function()
		{
			if($(this).is(":checked"))
				selectAllLayers("eleva");
			else
				unselectAllLayers("eleva");
		}
	);
	
	setupOneLayerOnOff("bound");
	$("#boundGroup label:first input").click(
		function()
		{
			if($(this).is(":checked"))
				selectAllLayers("bound");
			else
				unselectAllLayers("bound");
		}
	);
	
	setupOneLayerOnOff("etc");
	$("#etcGroup label:first input").click(
		function()
		{
			if($(this).is(":checked"))
				selectAllLayers("etc");
			else
				unselectAllLayers("etc");
		}
	);
}

function setupOneLayerOnOff(className)
{
	var liCount = $("#" + className + " li").length;
	for(var i = 1; i < liCount; i++)
	{
		$("#" + className + " li:eq(" + i + ") label:first input").click(
			function()
			{
				if($(this).is(":checked"))
				{
					if(!$("#" + className + " li:first label:first input").prop("checked"))
						$("#" + className + " li:first label:first input").prop("checked", true);
					
					var ulIndex = -1;
					ulIndex = preSetupSelectedGroup(className);
					
					var layerName = $(this).parent().text();
					var layerCode = $(this).parent().parent().attr("id");;
					$("#typeSelected ul:eq(" + ulIndex + ")").append("<li>" + layerName + "</li>");
					addLayerToSelectionContainer(layerName, layerCode);
				}
				else
				{
					var ulIndex = -1;
					ulIndex = findSelectedGroup(className);
					
					var targetLayerName = $(this).parent().text();
					var layerCount = $("#typeSelected ul:eq(" + ulIndex + ") li").length;
					for(var i = 0; i < layerCount; i++)
					{
						if($("#typeSelected ul:eq(" + ulIndex + ") li:eq(" + i + ")").text() == targetLayerName)
						{
							$("#typeSelected ul:eq(" + ulIndex + ") li:eq(" + i + ")").remove();
							
							if(layerCount == 1)
							{
								$("#typeSelected ul:eq(" + ulIndex + ")").remove();
								$("#" + className + " li:first label:first input").prop("checked", false);
							}
							
							break;
						}
					}
					
					removeLayerFromSelectionContainer(targetLayerName);
				}
			}
		);
	}
}

function clearTypeSelectionControls()
{
	unselectAllLayers("trans");
	unselectAllLayers("water");
	unselectAllLayers("build");
	unselectAllLayers("eleva");
	unselectAllLayers("bound");
	unselectAllLayers("etc");
}

function selectAllLayers(className)
{
	var ulIndex = -1;
	ulIndex = preSetupSelectedGroup(className);
	
	//console.log("added item index : " + ulIndex);

	$("#typeSelected ul:eq(" + ulIndex + ")").empty();
	var layerName, layerCode;
	var listLength = $("#" + className +" li").length;
	for(var i = 1; i < listLength; i++)
	{
		$("#" + className + " li:eq(" + i + ") label input").prop("checked", true);
		layerName = $("#" + className + " li:eq(" + i + ") label").text();
		layerCode = $("#" + className + " li:eq(" + i + ")").attr("id");

		$("#typeSelected ul:eq(" + ulIndex + ")").append("<li>" + layerName + "</li>");
		
		addLayerToSelectionContainer(layerName, layerCode);
	}
	
	$("#" + className + " li:first label:first input").prop("checked", true);
}

function unselectAllLayers(className)
{
	var ulIndex = -1;
	ulIndex = findSelectedGroup(className);
	
	//console.log("index to be removed : " + ulIndex);
	if(ulIndex != -1)
		$("#typeSelected ul:eq(" + ulIndex + ")").remove();
	
	var listLength = $("#" + className +" li").length;
	for(var i = 1; i < listLength; i++)
	{
		$("#" + className + " li:eq(" + i + ") label input").prop("checked", false);
		removeLayerFromSelectionContainer($("#" + className + " li:eq(" + i + ") label").text());
	}

	$("#" + className + " li:first label:first input").prop("checked", false);
}

function preSetupSelectedGroup(className)
{
	var ulCount = $("#typeSelected ul").length;
	var ulIndex = -1;
	for(var i = 0; i < ulCount; i++)
	{
		if($("#typeSelected ul:eq(" + i + ")").prop("class") == className)
		{
			ulIndex = i;
			break;
		}	
	}

	if(ulIndex == -1)
	{
		$("#typeSelected").append("<ul></ul>");
		$("#typeSelected ul:last").addClass(className);
		return ulCount;
	}
	
	return ulIndex;
}

function findSelectedGroup(className)
{
	var ulCount = $("#typeSelected ul").length;
	var ulIndex = -1;
	for(var i = 0; i < ulCount; i++)
	{
		if($("#typeSelected ul:eq(" + i + ")").prop("class") == className)
		{
			ulIndex = i;
			break;
		}	
	}
	
	return ulIndex;
}

function addLayerToSelectionContainer(layerName, layerCode)
{
	var targetIndex = -1;
	for(var layerIndex in selectedLayers)
	{
		if(layerName == selectedLayers[layerIndex])
		{
			targetIndex = layerIndex;
			break;
		}
	}
	
	if(targetIndex == -1){
		selectedLayers.push(layerName);
		selectedLayerCds.push(layerCode);
		//console.log(layerName + layerCode);
		
	}
}

function removeLayerFromSelectionContainer(layerName)
{
	var targetIndex = -1;
	for(var layerIndex in selectedLayers)
	{
		if(layerName == selectedLayers[layerIndex])
		{
			targetIndex = layerIndex;
			break;
		}
	}
	
	if(targetIndex != -1){
		selectedLayers.splice(targetIndex, 1);
		selectedLayerCds.splice(targetIndex, 1);
	}
}

var selectedFormat = "Shape + GeoTiff";
var selectedFormatCd = "shptif";
var selectedSrs = "5179";

function changeFmt(obj, val){
	//console.log($(obj).find("option[value='" + val + "']").text());
	selectedFormat = val;
	//selectedFormatCd = $(obj).find("option[value='" + val + "']").attr("id");
	selectedFormatCd = $(obj).find("option[value='" + val + "']").val();
	$("#dataFormat").text($(obj).find("option[value='" + val + "']").text());
}

function changeSrs(obj, val){
	//console.log($(obj).find("option[value='" + val + "']").text());
	selectedSrs = val;
	$("#dataSrs").text($(obj).find("option[value='" + val + "']").text());
}

</script>

<script>
var strLatestVersion = "최신버전";
var selectedDate = "";
var bLatestVersionSelected = true;

function prepareDateSelectionControls()
{
	$( "#datePicker" ).datepicker({
        changeMonth: true, 
        changeYear : true,
        dateFormat : 'yy.mm.dd',
        dayNames: ['월요일', '화요일', '수요일', '목요일', '금요일', '토요일', '일요일'],
        dayNamesMin: ['월', '화', '수', '목', '금', '토', '일'], 
        monthNamesShort: ['1','2','3','4','5','6','7','8','9','10','11','12'],
        monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
        maxDate: 0
    });
	
	$( "#datePicker" ).change(
		function()
		{
			selectedDate = $(this).val();
			addDateSelection();
		}
	);

	$("#checkLatestVersion").prop("checked", true);
	$("#checkLatestVersion").click(
		function()
		{
			if($(this).is(":checked"))
				disableDateSelection(true);
			else
				disableDateSelection(false);
			
			takeCurrentTime();
			addDateSelection();
		}
	);
	
	
	takeCurrentTime();
}

function addDateSelection()
{
	if(bLatestVersionSelected)
		$("#dateSelected").text(strLatestVersion);
	else
		$("#dateSelected").text(selectedDate);
}

function takeCurrentTime()
{
	var dateObj = new Date();
	var today = dateObj.getFullYear() + "." + (dateObj.getMonth()>8?dateObj.getMonth()+1:"0" + (dateObj.getMonth()+1)) + "." + (dateObj.getDate()>9?dateObj.getDate():"0"+dateObj.getDate());
	if(!bLatestVersionSelected)
		$("#datePicker").val(today);
	selectedDate = today;
}

function disableDateSelection(bDisable)
{
	bLatestVersionSelected = bDisable;
	if(bDisable)
		$("#datePicker").val("");
	$("#datePicker").prop("disabled", bDisable);
	$("#addDate").prop("disabled", bDisable);
}
</script>


<script>

var rate = 0;
var maxVal = 100;
var timeout = 1000;
var objTimeout = null;
var effectConstant = 0;

//loop("funcSqquenceProgress",1 );

function funcSinProgress(x){
	var retVal = 0;
	retVal = maxVal * Math.sin(x/900)+1; 
	//console.log(retVal);
	return retVal;
}

function funcSqquenceProgress(x){
	var retVal = 0;
	retVal = x; 
	//console.log(retVal);
	return retVal;
}

function loop(func, idx){
	var funcProgress = eval(func);
	
	var status = 0;
	
	// 화면이 멈춰있지 않다는 효과 생성
	effectConstant = (effectConstant * 1 + 1) % 5;
	//console.log(effectConstant);
	var tmpHtml = "데이터 파일 생성중입니다.";
	for(var i = 0 ; i < effectConstant ; i++){
		tmpHtml += ".";
	}
	$("#downProcessing p").text(tmpHtml);
	// 화면이 멈춰있지 않다는 효과 생성 끝
	
	$.ajax({
		url : "/sdmc/getOrderList.ngii",
		data : "orderId=" + $("#orderList > li.on input[name='dataId']").val(),
		type : "GET",
		dataType : "json",
		async : false,
		success : function(data){result = data;},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			//alert(errorThrown);
		}
	}).done(function(data){
		if(data.length > 0){
			status = data[0].status * 1;
		} else {
			// data에 이상이 있다면 status를 강제로 -2로 설정한다.
			status = -2;
		}
	});
	
	
	if(status >= maxVal){
		//$("#btnDown" + idx ).prop("disabled", false);
		$("#downProcessing").hide();
		$("#downReady").show();
		
		var obj = $("#orderList>li.on a");
		var orderId = $(obj).find("input[name='dataId']").val();
		$("#downReady button").unbind("click");
		$("#downReady button").click(
			function()
			{
				//location.href="/sdmc/download.ngii?orderId="+orderId;
				window.location.assign("/sdmc/download.ngii?orderId="+orderId);
			}
		);

		
	} else if(status == -2){
		// 오류가 발생할 경우 처리
		//timeout = 
		stopLoop();
		$("#downProcessing").hide();
		$("#downException").show();
		
		
	} else {
		//timeout = 
		objTimeout = setTimeout(function(){
			loop(func, idx);
		}, timeout);
	}
	
	
	$( "#progressbar" + idx ).progressbar({
		min : 0,
		max : 100,
		value: status
	});
		
	//console.log("asdf");
}

function stopLoop(){
	if(objTimeout){
		clearTimeout(objTimeout);
		objTimeout = null;
	}
	
}

</script>



<script>

/**
 * 나의 자료목록 > 주문목록 읽어오기
 */
function loadFromDatabase(){
	//var orderData = JSON.parse(localStorage.getItem(orderDataKey));
	var orderData = selectAllData();
	//console.log(orderData);
	return orderData;
}

</script>



<script>

function getFinalSelectedArea(areaCodeArray)
{
	var key = null;
	if(orderType * 1 == 0 || orderType * 1 == 1)
	{
		for(var keyIndex in selectedAreaKeys)
		{
			key = selectedAreaKeys[keyIndex];

			areaCodeArray.push(key);
		}
	}
	else
	{
		//console.log("unsupported order type");
		return null;
	}
}

function getFinalSelectedLayers()
{
	return selectedLayers;
}

function getFinalSelectedLayerCds()
{
	return selectedLayerCds;
}

function getFinalSelectedFormat()
{
	return selectedFormat;
}

function getFinalSelectedFormatCd()
{
	return selectedFormatCd;
}

function getFinalSelectedSrs()
{
	return selectedSrs;
}

function saveFinalSelectionToDatabase()
{
	var areaCodeArray = [];
	getFinalSelectedArea(areaCodeArray);
	if(areaCodeArray.length == 0)
		return false;
	
	var finalSelectedLayers = getFinalSelectedLayers();
	if(finalSelectedLayers.length == 0)
		return false;
	
	var orderedLayers = JSON.stringify(getFinalSelectedLayers());
	var orderedLayerCds = JSON.stringify(getFinalSelectedLayerCds());
	
	var format = getFinalSelectedFormat();
	var formatCd = getFinalSelectedFormatCd();
	
	var srs = getFinalSelectedSrs();

	
	//var currentOrderDataList = selectAllData(orderDataKey);
	var currentOrderDataList = selectAllData();
	var objectId = -1;
	if(currentOrderDataList == null || currentOrderDataList.length == null)
		objectId = 0;
	else
		objectId = currentOrderDataList.length;
	
	// TODO MUST change before publishing
	var userId = userEmail;
	
	var dateObj = new Date();
	var orderDate = dateObj.getFullYear() + "." + (dateObj.getMonth()>8?dateObj.getMonth()+1:"0" + (dateObj.getMonth()+1)) + "." + (dateObj.getDate()>9?dateObj.getDate():"0"+dateObj.getDate());
	var orderDate = selectedDate;
	orderDate += " " + (dateObj.getHours()>9?dateObj.getHours():"0" + (dateObj.getHours()));
	orderDate += ":" + (dateObj.getMinutes()>9?dateObj.getMinutes():"0" + (dateObj.getMinutes()));
	orderDate += ":" + (dateObj.getSeconds()>9?dateObj.getSeconds():"0" + (dateObj.getSeconds()));

	var orderVersion = selectedDate + " 00:00:00";

	var updateStatus = -1;
	if(bLatestVersionSelected)
		updateStatus = 1;
	else
		updateStatus = 0;
	
	var orderData = 
	{
		//"orderId":		objectId,				// PKey	
		"userId":		userId,					// owner of this order(SNS account)
		"orderDate":	orderDate,				// date at which order was made
		"orderVersion":	orderVersion,			// date at which selected data was born
		"areaCodeArray":JSON.stringify(areaCodeArray),// ordered area (bjcd array or 도엽 code array)
		"orderedLayers":orderedLayers,			// ordered layers (layerName array)
		"orderedLayerCds":orderedLayerCds,		// ordered layers (layerCode array)
		"orderType":	orderType,				// one of 0(행정구역), 1(도엽)
		//"format":		format,					// temporary string. originally one of 0(shp+geotiff), 1(geopackage), ...
		"format":		formatCd,				// temporary string. originally one of 0(shp+geotiff), 1(geopackage), ...
		"srs":			srs,					// temporary string. originally one of EPSG code
		"status":		0,						// number on 0~100, -1 means expired
		"updateStatus":	updateStatus			// one of 0(ignored), 1(fresh), 2(updated), 3(toBeUpdated)
	};

	//console.log(orderData);
	insertData(orderData, orderDataKey);
	
	return true;
}

function onLoad()
{
	<c:if test="${param.page == 'main'}">

	// TODO Temp Code - start
	initalizeVirtualDB();
	refreshUpdateStatusOfUserTemp(userEmail);
	// Temp Code - end
	prepareAreaSelectionControls();
	
	
	//prepareTypeSelectionControls();	// 아래 getLayerList 함수에서 목록을 준비한 후 호출하도록 변경
	getLayerList();
	
	prepareDateSelectionControls();
	
	openNotice();
	</c:if>
	<c:if test="${param.page == 'mypg'}">
	onReadyMypg();
	</c:if>
	
	if(checkNewStatus()){
		$("#navMypg").removeClass("new").addClass("new");
	} else {
		$("#navMypg").removeClass("new");
	}
}

function onReadyMypg(){
	
	// 이 함수를 이용하여 저장된 데이터를 로드해야 한다.
	//saveFinalSelectionToDatabase();
	var orderData = loadFromDatabase();
	
	deleteOrderList();
	if( orderData != null){
		for(var idx in orderData){
			// 초기화 시에는 new 항목에 대한 체크는 하지 않는다.
			if(userEmail == orderData[idx].userId){
				requestOrder(orderData[idx], true);
			}
		}
	}
	
}


$(document).ready(onLoad);
</script>

