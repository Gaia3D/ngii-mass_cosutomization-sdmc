
/**
 * goSubMenu 
 * id와 path를 이용하여 해당 페이지로 이동하는 함수.
 * 페이지별 id는 추후 menu 테이블에서 관리한다.
 * @param idx : 해당 페이지의 id
 * @param path : 해당 페이지의 경로 (추후 db에서 정보를 가져오는 것으로 대체된다.)
 * @deprecated
 */
function goSubMenu(idx, path){
	
	try{
		//removeAlldataSearchResults();
		//removeAllMapDifferences();
		//removeAllOrderArea();
	} catch(e){
		//console.log(e);
	}
	
	changeTopMenu(idx);
	//alert(path);
	$(".contents").load('/sdmc/viewPage.ngii?nextPageName=' + path);
}


/**
 * changeTopMenu
 * 상단의 메뉴 하이라이트를 변경하는 함수.
 * id를 이용하여 현재 메뉴에 따른 항목을 하이라이트시켜준다.
 * @param idx : 메뉴 id
 * @deprecated
 */
function changeTopMenu(idx){
	$(".nav li").each(function(){
		$(this).removeClass("on");
	});
	
	$(".nav li:nth-child(" + Math.floor(idx/100) + ")").addClass("on");		// idx의 백자리 숫자를 구하여 상단의 하이라이트 메뉴를 결정한다.

	if(idx < 120 || idx >= 300)	// 페이지별로 검색창이 필요한 페이지와 필요없는 페이지가 있다. 이를 필터링하는 조건임.
		$(".SearchWrap").show();
	else
		$(".SearchWrap").hide();
	
	$(".nav li:nth-child(" + idx + ")").addClass("on");
}


function goPage(url){
	location.href = context + "viewPage.ngii?nextPageName=" + url;
}

/**
 * logout
 * 로그아웃 프로세스
 * 세션을 종료하고 초기 페이지로 이동한다.
 */
function logout(){
	if(confirm("로그아웃하시겠습니까?")){
		location.href="/sdmc/logout.ngii";
	}
}

function requestData(){
	if(confirm("상기 자료를 요청하시겠습니까?")){
		
		//todo 데이터 db에 넣기
		if(!saveFinalSelectionToDatabase()){
			alert("영역과 자료 종류를 선택한 후 요청할 수 있습니다.")
			return false;
		}
		
	}
}


/**
 * openDetailArea
 * openAllDetail 함수의 확장 함수.
 * 목록의 상세 내역을 모두 보이도록 하고 감춰두었던 목록 영역까지 보이도록 성정한다.
 * @deprecated
 */
function openDetailArea(){
	$("#dataList").slideDown(speedListClosing);
	openAllDetail();
}


/**
 * openAllDetail
 * openListDetail 함수의 확장 함수
 * 목록의 갯수만큼 loop를 돌면서 각 상세내역을 보이게 설정해준다.
 * @deprecated
 */
function openAllDetail(){
	$("#dataList li").each(function(){
		openListDetail(this);
	});
}


/**
 * closeDetailArea
 * closeAllDetail 함수의 확장 함수
 * 목록의 상세내역을 모두 안보이도록 한 뒤 목록 자체도 숨겨버린다.
 * @deprecated
 */
function closeDetailArea(){
	closeAllDetail();
	$("#dataList").slideUp(speedListClosing);
}


/**
 * closeAllDetail
 * closeListDetail 함수의 확장 함수
 * 목록의 갯수만큼 loop를 돌며 모든 상세내역을 숨김처리한다.
 */
function closeAllDetail(){
	$("#dataList li").each(function(){
		closeListDetail(this);
	});
}

/**
 * openListDetail
 * 목록중 해당 항목의 상세내역을 보여준다.
 * @param obj : 목록중에서 상세내역을 보여줄 항목 (주로 li)
 * @deprecated
 */
function openListDetail(obj){
	$(obj).find("ul").slideDown(speedListClosing);
	$(obj).find("div.datalist").slideDown(speedListClosing);
	$(obj).find("button.open").hide();
	$(obj).find("button.shut").show();
}


/**
 * closeListDetail
 * 목록중 해당 항목의 상세내역을 감춰준다.
 * @param obj : 목록중에서 상세내역을 숨길 항목 (주로 li)
 * @deprecated
 */
function closeListDetail(obj){
	$(obj).find("ul").slideUp(speedListClosing);
	$(obj).find("div.datalist").slideUp(speedListClosing);
	$(obj).find("button.open").show();
	$(obj).find("button.shut").hide();
}

function searchKeyword(){
	goSubMenu(110, "order/searchResult");
}

/**
 * 자료목록 창에서 상세보기 요소 뿌려주는 함수
 * @param dataObj
 * @param newFlag : true로 넣어주면 new 항목에 대한 검사를 하지 않는다.(즉 하이라이트후 상세화면만 보여준다.)
 * @returns {Boolean}
 */
function openDetail(dataObj, newFlag){
	
	
	if(!newFlag && $(dataObj).hasClass("new")){
		if(confirm("데이터를 요청하신 이후 새로운 데이터가 등록되었습니다.\n모든 신규 데이터를 재요청하시겠습니까?")){
			$(dataObj).parent().parent().find("li>a.new").each(function(){
				requestNewOrder(this);
			});
			
			updateAllNewStatus();
			
			return false;
		}
	}
	
	$(dataObj).parent().parent().find("li").each(function(){
		$(this).removeClass("on");
	})
	$(dataObj).parent().addClass("on");
	
	//console.log($(dataObj).find("input[name='id']").val());
	//console.log($(dataObj).find("span.name").text());
	
	var dataId = $(dataObj).find("input[name='dataId']").val();
	var dataTitle = $(dataObj).find("span.name").text();
	var dataDate = $(dataObj).find("span.date").text();
	var dataFmt = $(dataObj).find("input[name='dataFormatName']").val();
	var dataSrs = $(dataObj).find("input[name='dataSrsName']").val();
	var dataVersion = $(dataObj).find("input[name='dataVersion']").val();
	var dataLayers = $(dataObj).find("input[name='dataLayerName']").val();
	var dataStatus = $(dataObj).find("input[name='dataStatus']").val();
	var dataUpdateStatus = $(dataObj).find("input[name='dataUpdateStatus']").val();
	var dataLayerArray = [];
	
	if(dataLayers != null && dataLayers != undefined){
		dataLayerArray = dataLayers.split(",");
	}
	
	var html =  "            <table>\n"
				+"            	<tr>\n"
				+"                	<th>위치</th>\n"
				+"                    <td class=\"title\">" + dataTitle + "</td>\n"
				+"                </tr>\n"
				+"                <tr>\n"
				+"                	<th>주문일</th>\n"
				+"                    <td>" + dataDate + "</td>\n"
				+"                </tr>\n"
				+"                <tr>\n"
				+"                	<th>종류</th>\n"
				+"                    <td>\n"
				+"                    	<p class=\"trans\">\n";
				
				for(var idx in dataLayerArray){
					html += "                          <span>" + dataLayerArray[idx] + "</span>\n";
				}
				
	html 	+=	"                        </p>\n"
				+"                    </td>\n"
				+"                </tr>\n"
				+"                <tr>\n"
				+"                	<th>Format</th>\n"
				+"                    <td>" + dataFmt + "</td>\n"
				+"                </tr>\n"
				+"                <tr>\n"
				+"                	<th>SRS</th>\n"
				+"                    <td>" + dataSrs + "</td>\n"
				+"                </tr>\n"
				+"                <tr>\n"
				+"                	<th>시점</th>\n"
				+"                    <td>" + dataVersion + "</td>\n"
				+"                </tr>\n"
				+"            </table>";
	
	
	$(".tablewrap").html(html);

	
	stopLoop();

	$(".message").hide();

	//progressbar 초기화
	// rate 값을 db의 값으로 설정해준다.
	
	var partTimes = dataDate.split(" ");
	if(partTimes.length > 1)
	var partDate = partTimes[0].split(".")
	var reqDate;
	var today = new Date();
	//console.log(partDate);
	//console.log(partTime);
	if(partTimes.length > 1){
		var partTime = partTimes[1].split(":");
		reqDate = new Date(partDate[0], partDate[1]-1, partDate[2], partTime[0], partTime[1], partTime[2]);
	} else {
		reqDate = new Date(partDate[0], partDate[1]-1, partDate[2]);
		
	}
	
	//rate = dataStatus;
	rate = Math.floor((today - reqDate)/1000);
	if(rate > 100) rate = 100;
	
	//dataDate
	
	if(dataId == '0000'){
		//alert("요청 주문이 존재하지 않습니다.");
	}else if(dataUpdateStatus == 2){
		$("#downOrdered").show();
	} else if(dataStatus == -1){
		$("#downExpired").show();
	} else if(dataStatus == -2){
		$("#downException").show();
	} else {
		//$("#downExpired").show();
		if(dataStatus < maxVal){
			$("#downProcessing").show();
			effectConstant = 0;
			loop("funcSqquenceProgress",1 );
		} else {
			effectConstant = 0;
			loop("funcSqquenceProgress",1 );
			$("#downReady").show();
		}
	}

}


var newOrderId = 100;

function requestNewOrder(dataObj){
	var cloneObj = $(dataObj).parent().clone();
	var dateObj = new Date();
	var today = dateObj.getFullYear() + "." + (dateObj.getMonth()>8?dateObj.getMonth()+1:"0" + (dateObj.getMonth()+1)) + "." + (dateObj.getDate()>9?dateObj.getDate():"0"+dateObj.getDate());
	today += " " + (dateObj.getHours()>9?dateObj.getHours():"0" + (dateObj.getHours()));
	today += ":" + (dateObj.getMinutes()>9?dateObj.getMinutes():"0" + (dateObj.getMinutes()));
	today += ":" + (dateObj.getSeconds()>9?dateObj.getSeconds():"0" + (dateObj.getSeconds()));

	$(cloneObj).find("span.date").text(today);
	$(cloneObj).find("a").removeClass("new");
	$(dataObj).removeClass("new");
	//$(cloneObj).find("a").removeClass("new");
	
	//console.log(cloneObj);
	
	var orderId = $(cloneObj).find("input[name='dataId']").val();
	var userId = userEmail;
	var orderDate = today;
	var orderVersion = today + "-00-00-00";
	var orderType = $(cloneObj).find("input[name='dataOrderType']").val();
	var areaCodeArray = $(cloneObj).find("input[name='dataAreaCodeArray']").val();
	var orderedLayers = $(cloneObj).find("input[name='dataLayerName']").val();
	var orderedLayerCds = $(cloneObj).find("input[name='dataLayer']").val();
	var geoName = $(cloneObj).find("span.name").text();
	var orderType = $(cloneObj).find("input[name='dataOrderType']").val();
	var format = $(cloneObj).find("input[name='dataFormat']").val();
	var srs = $(cloneObj).find("input[name='dataSrs']").val();
	var status = 0;
	$(cloneObj).find("input[name='dataStatus']").val(status);
	var updateStatus = 1;
	$(cloneObj).find("input[name='dataUpdateStatus']").val(updateStatus);
	
	var orderData = {
/*			"orderId": orderId,
			"userId":userId,
			"orderDate":orderDate,
			"orderVersion":orderVersion,
			"bjcdArray":bjcdArray,
			"orderedLayers":orderedLayers,
			"geoName":geoName,
			"orderType":0,
			"format":format,
			"srs":srs,
			"status":status,
			"updateStatus":updateStatus
*/
			"orderId":		orderId,				// PKey	
			"userId":		userId,					// owner of this order(SNS account)
			"orderDate":	orderDate,				// date at which order was made
			"orderVersion":	orderVersion,			// date at which selected data was born
			"areaCodeArray":JSON.stringify(areaCodeArray),// ordered area (bjcd array or 도엽 code array)
			"orderedLayers":orderedLayers,			// ordered layers (layerName array)
			"orderedLayerCds":orderedLayerCds,		// ordered layers (layerCode array)
			"geoName":		geoName,				// one of 행정구역명, 도엽명
			"orderType":	orderType,				// one of 0(행정구역), 1(도엽)
			//"format":		format,					// temporary string. originally one of 0(shp+geotiff), 1(geopackage), ...
			"format":		format,				// temporary string. originally one of 0(shp+geotiff), 1(geopackage), ...
			"srs":			srs,					// temporary string. originally one of EPSG code
			"status":		0,						// number on 0~100, -1 means expired
			"updateStatus":	updateStatus			// one of 0(ignored), 1(fresh), 2(updated), 3(toBeUpdated)

	};
	
	insertData(orderData, "orderData");
	
	$("#orderList").prepend(cloneObj);
	
	
	openDetail($(cloneObj).find("a"));
}


function requestOrder(orderData, newFlag){
	
	var dateObj = new Date();
	var today = dateObj.getFullYear() + "." + (dateObj.getMonth()>8?dateObj.getMonth()+1:"0" + (dateObj.getMonth()+1)) + "." + (dateObj.getDate()>9?dateObj.getDate():"0"+dateObj.getDate());

	var html = "            <li>\n"
				+"            	<a href=\"#\" onclick=\"openDetail(this);return false;\" " + (orderData.updateStatus==3?"class=\"new\"":"") + ">\n"
				+"          		<input type=\"hidden\" name=\"dataId\" value=\"" + orderData.orderId + "\" />\n"
				+"          		<input type=\"hidden\" name=\"dataSrs\" value=\"" + orderData.srs + "\" />\n"
				+"          		<input type=\"hidden\" name=\"dataSrsName\" value=\"" + orderData.srsName + "\" />\n"
				+"          		<input type=\"hidden\" name=\"dataAreaCodeArray\" value=\"" + orderData.orderedAreaCode + "\" />\n"
				+"          		<input type=\"hidden\" name=\"dataOrderType\" value=\"" + orderData.orderType + "\" />\n"
				+"          		<input type=\"hidden\" name=\"dataFormat\" value=\"" + orderData.format + "\" />\n"
				+"          		<input type=\"hidden\" name=\"dataFormatName\" value=\"" + orderData.formatName + "\" />\n"
				+"          		<input type=\"hidden\" name=\"dataVersion\" value=\"" + orderData.orderVersion + "\" />\n"
				+"          		<input type=\"hidden\" name=\"dataLayer\" value=\"" + orderData.orderedLayer + "\" />\n"
				+"          		<input type=\"hidden\" name=\"dataLayerName\" value=\"" + orderData.orderedLayerName + "\" />\n"
				+"          		<input type=\"hidden\" name=\"dataStatus\" value=\"" + orderData.status + "\" />\n"
				+"          		<input type=\"hidden\" name=\"dataUpdateStatus\" value=\"" + orderData.updateStatus + "\" />\n"
				+"          		<span class=\"date\">" + orderData.orderDate + "</span>\n"
				+"					<span class=\"name\">" + orderData.geoName + "</span>\n"
				+"            	</a>\n"
				+"          </li>"
	
	var newObj = $(html);
	$("#orderList").find("li#noData").remove();
	$("#orderList").prepend(newObj);
	openDetail($(newObj).find("a"), newFlag);
}

function deleteOrderList(){
	$("#orderList").empty();
	var html = "            <li id=\"noData\">\n"
				+"            	<a href=\"#\" onclick=\"openDetail(this);return false;\">\n"
				+"          		<input type=\"hidden\" name=\"dataId\" value=\"0000\" />\n"
				+"          		<input type=\"hidden\" name=\"dataSrs\" value=\"\" />\n"
				+"          		<input type=\"hidden\" name=\"dataSrsName\" value=\"\" />\n"
				+"          		<input type=\"hidden\" name=\"dataFormat\" value=\"\" />\n"
				+"          		<input type=\"hidden\" name=\"dataFormatName\" value=\"\" />\n"
				+"          		<input type=\"hidden\" name=\"dataSrs\" value=\"epsg:2097\" />\n"
				+"          		<input type=\"hidden\" name=\"dataVersion\" value=\"\" />\n"
				+"          		<input type=\"hidden\" name=\"dataStatus\" value=\"\" />\n"
				+"          		<span class=\"date\"> </span>\n"
				+"					<span class=\"name\">요청 자료가 존재하지 않습니다.</span>\n"
				+"            	</a>\n"
				+"          </li>"

	var newObj = $(html);
	$("#orderList").prepend(newObj);
	openDetail($(newObj).find("a"));

}


function getLayerList() {

	$.ajax({
		url : "/sdmc/getLayerList.ngii",
		type : "GET",
		dataType : "json",
		async : true,
		success : displayLayerList,
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert(errorThrown);
		}
	});
	
	$.ajax({
		url : "/sdmc/getFormatList.ngii",
		type : "GET",
		dataType : "json",
		async : true,
		success : displayFormatList,
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert(errorThrown);
		}
	});
	
	$.ajax({
		url : "/sdmc/getSrsList.ngii",
		type : "GET",
		dataType : "json",
		async : true,
		success : displaySrsList,
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert(errorThrown);
		}
	});
}

function goDoyeupSearch(){
	var dyCode = $("#dyCode").val();
	
	$.ajax({
		url : "/sdmc/getDoyeupList.ngii?dyCd=" + dyCode,
		type : "GET",
		dataType : "json",
		async : true,
		success : displayDoyeupList,
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert(errorThrown);
		}
	});
}
		

function displayLayerList(result) {
	//console.log(result);

	var canvas = $(".typelist");
	var ulItem = null;
	var liItem = null;

	for (idx in result) {
		//console.log(result[idx]);
		var itemData = result[idx];
		if (!itemData.layerUpperId) {
			if (ulItem) {
				canvas.append(ulItem);
				//console.log(ulItem);
			}
			liItem = $("<li id='"
					+ itemData.layerNameEng
					+ "Group' class=\"group\"><label><input type=\"checkbox\"> "
					+ itemData.layerName + "</label></li>");
			ulItem = $("<ul id=\"" + itemData.layerNameEng + "\"></ul>");
			ulItem.append(liItem);

		} else {
			liItem = $("<li id='" + itemData.layerId
					+ "'><label><input type=\"checkbox\"> "
					+ itemData.layerName + "</label></li>");
			ulItem.append(liItem);
		}

	}

	canvas.append(ulItem);
	prepareTypeSelectionControls();

}


function displayFormatList(result) {
	//console.log(result);

	var canvas = $("#selectFormat");
	$(canvas).empty();
	var inputItem = null;

	for (idx in result) {
		//console.log(result[idx]);
		var itemData = result[idx];
		inputItem = $("<option value=\"" + itemData.formatCd + "\">" + itemData.formatName+ "</option>");
		canvas.append(inputItem);
	}
}

function displaySrsList(result) {
	//console.log(result);

	var canvas = $("#selectSrs");
	$(canvas).empty();
	var inputItem = null;

	for (idx in result) {
		//console.log(result[idx]);
		var itemData = result[idx];
		inputItem = $("<option value=\"" + itemData.srsCd + "\" " + (itemData.srsCd == "5179" ? "selected":"") + ">" + itemData.srsName+ "</option>");
		canvas.append(inputItem);
	}
}

function displayDoyeupList(result){
	//console.log(result);
	
	var cnt = result.length;
	if(cnt == 1){
		var itemData = result[0];
		
		geometryArriven(JSON.parse(result[0].geom), 1);
		
//		if(confirm("해당 도엽 데이터가 검색이 되었습니다. 영역으로 추가하시겠습니까?")){
//			geometryArriven(JSON.parse(result[0].geom));
//		}
	} else {
		alert("도엽 데이터가 존재하지 않습니다.\n 도엽 번호는 정확히 일치하는 경우에만 조회할 수 있습니다.");
	}

}


function openNotice(){
	$.ajax({
		url : "/sdmc/getNoticeItem.ngii?userId=" + userEmail,
		type : "GET",
		dataType : "json",
		async : true,
		success : showNotice,
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			//alert(errorThrown);
		}
	});
}

function showNotice(data){
	var layer = $(".layer");
	
	if(data != null && data.length > 0){
		$(layer).find(".info").text(data[0].noticeTitle);
		$(layer).find(".notice").text(data[0].noticeTitle);
		$(layer).find(".text").text(data[0].noticeContent);
	}
	
	
	//console.log(data);
	
	$(".layer").show();
}

function closeNotice(){
	$(".layer").hide();
}