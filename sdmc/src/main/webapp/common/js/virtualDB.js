/**
 * 
 */

// use localStorage like as database for order system

// data structure
//
//	orderData = 
//	{
//		"orderId":		uniqueNumber,			// PKey	
//		"userId":		"userId",				// owner of this order(SNS account)
//		"orderDate":	"yyyy.mm.dd hh:mm:ss",	// date at which order was made
//		"orderVersion":	"yyyy.mm.dd hh:mm:ss",	// date at which data was made
//		"bjcdArray":	["", "", ...],			// ordered area (bjcd array or 도엽 code array)
//		"orderedLayers":["", "", ...],			// ordered layers (layerCode array)
//		"geoName":		"",						// one of 행정구역명, 도엽명
//		"orderType":	number,					// one of 0(행정구역), 1(도엽)
//		"format":		number,					// temporary string. originally one of 0(shp+geotiff), 1(shp+jpg), 2(geopackage+geotiff), 3(geopackage+jpg)
//		"srs":			number,					// temporary string. originally one of EPSG code
//		"status":		number,					// number on 0~100, -1 means expired
//		"updateStatus":	number					// one of 0(ignored), 1(fresh), 2(updated), 3(toBeUpdated)
//	};
//	
//	localStorage.setItem("orderData", [orderData1, orderData2, ...]);
//
//	layerVersionByDistrict =
//	{
//		"id":			uniqueNumber,			// layerCode & version package unique id
//		"layerCode":	"layerCode",			// temporary string of layer name. originally table name of NFSD
//		"date":			"yyyy-mm-dd-hh-mm-ss"	// date at which that version is applied
//		"bjcd":			""						// 영역별 버전관리를 위한 umd bjcd
//	}
//	
//	localStorage.setItem("layerVersionsByDistrict", [layerVersionByDistrict1, layerVersionByDistrict2, ...]);
//
//	layerVersionByMapCode =
//	{
//		"id":			uniqueNumber,			// layerCode & version package unique id
//		"layerCode":	"layerCode",			// temporary string of layer name. originally table name of NFSD
//		"date":			"yyyy-mm-dd-hh-mm-ss"	// date at which that version is applied
//		"mapCode":		""						// 영역별 버전관리를 위한 도엽 코드
//	}
//	
//	localStorage.setItem("layerVersionsByMapCode", [layerVersionByMapCode1, layerVersionByMapCode2, ...]);

orderDataKey = "orderData";
layerVersionsByDistrictKey = "layerVersionsByDistrict";
layerVersionsByMapCode = "layerVersionsByMapCode";

/**
 * jsonData와 db table key을 받아 해당 DB에 insert하는 함수
 * @param jsonData : record to be inserted
 * @param keyType : key matched table name 
 */
function insertData(jsonData, keyType)
{
	//console.log(jsonData);
	var strUpdatedData;
	var strPreviousData = localStorage.getItem(keyType);

	if(strPreviousData)
	{
		var previousData = JSON.parse(strPreviousData);
		previousData.push(jsonData);
		strUpdatedData = JSON.stringify(previousData);
	}
	else
	{
		strUpdatedData = JSON.stringify([jsonData]); 
	}

	localStorage.setItem(keyType, strUpdatedData);
}

function selectAllData(keyType)
{
	return JSON.parse(localStorage.getItem(keyType));
}


/**
 * user, container를 입력받아 List<orderData> 를 리턴해준다.
 * @param user : user.email
 * @param container : 
 */
function selectOrderDataOfUser(user, container)
{
	var orderData = selectAllData(orderDataKey);
	for(var index in orderData)
	{
		if(orderData[index].userId == user)
			container.push(orderData[index]);
	}
}

function refreshUpdateStatusOfUser(user)
{
	var orderData = selectAllData(orderDataKey);
	for(var orderIndex in orderData)
	{
		if(orderData[orderIndex].userId != user || orderData[orderIndex].updateStatus != 1)
			continue;

		if(orderData[orderIndex].orderType == 0)
		{
			var versionData = selectAllData(layerVersionsByDistrictKey);
			var layerCode;
			for(var layerIndex in orderData[orderIndex].orderedLayers)
			{
				layerCode = orderData[orderIndex].orderedLayers[layerIndex];
				var bjcd;
				for(var bjcdIndex in orderData[orderIndex].geometry)
				{
					bjcd = orderData[orderIndex].geometry[bjcdIndex];
					for(var versionIndex in versionData)
					{
						// 시도, 시군구, 읍면동 따져서 비교해야 하는데 일단 단순 비교로 넘어간다.
						if(versionData[versionIndex].layerCode != layerCode || versionData[versionIndex].bjcd != bjcd)
							continue;
						
						if(orderData[orderIndex].orderDate < versionData[versionIndex].date)
							orderData[orderIndex].updateStatus = 3;
					}
				}
			}
		}
		else if(orderData[orderIndex].orderType == 1)
		{
			// implement later
		}
		else
			alert('Error! Invalid Value in orderData.orderType');
	}
}

function refreshUpdateStatusOfUserTemp(user)
{
	var orderData = selectAllData(orderDataKey);
	for(var orderIndex in orderData)
	{
		if(orderData[orderIndex].userId != user || orderData[orderIndex].updateStatus != 1)
			continue;

		if(orderData[orderIndex].orderType == 0)
		{
			var versionData = selectAllData(layerVersionsByDistrictKey);
			var layerCode;
			for(var layerIndex in orderData[orderIndex].orderedLayers)
			{
				layerCode = orderData[orderIndex].orderedLayers[layerIndex];
				for(var versionIndex in versionData)
				{
					if(versionData[versionIndex].layerCode != layerCode)
						continue;
					
					if(orderData[orderIndex].orderDate < versionData[versionIndex].date)
						orderData[orderIndex].updateStatus = 3;
				}
			}
		}
		else if(orderData[orderIndex].orderType == 1)
		{
			// implement later
		}
		else
			alert('Error! Invalid Value in orderData.orderType');
	}
	
	var strFreshedOrderData = JSON.stringify(orderData);
	localStorage.setItem(orderDataKey, strFreshedOrderData);
}

function initalizeVirtualDB()
{
	initializeLayerVersions();
	initializeOrderData();
}

function initializeOrderData()
{
	var strOrderData = localStorage.getItem(orderDataKey);
	if(strOrderData != null && strOrderData.length != 0)
		return;
	
	//var user = ["hjkim@gaia3d.com","hsson@gaia3d.com","bjjang@gaia3d.com","shshin@gaia3d.com"];
	var user = ["hjkim@gaia3d.com","hsson@gaia3d.com"];
	var baseLayers = [" 도로경계면"," 도로링크"," 읍/면/동(행정경계)"];

	var baseObject =
	{
		"orderId":		0,													// PKey	
		"userId":		"",													// owner of this order(SNS account)
		"orderDate":	"",													// date at which order was made
		"orderVersion":	"",													// date at which data was made
		"bjcdArray":	["1111010100","1111010200","1111010300"],			// ordered area (bjcd array or 도엽 code array)
		"orderedLayers":[],													// ordered layers (layerCode array)
		"geoName":		"서울시 종로구 청운동, 서울시 종로구 신교동, 서울시 종로구 궁정동",		// one of 행정구역명, 도엽명
		"orderType":	0,													// one of 0(행정구역), 1(도엽)
		"format":		"Shape + GeoTiff",									// temporary string. originally one of 0(shp+geotiff), 1(shp+jpg), 2(geopackage+geotiff), 3(geopackage+jpg)
		"srs":			"4326",												// temporary string. originally one of EPSG code
		"status":		-1,													// number on 0~100, -1 means expired
		"updateStatus":	1													// one of 0(ignored), 1(fresh), 2(updated), 3(toBeUpdated)
	};
	
	var layerVersions = selectAllData(layerVersionsByDistrictKey);
	
	var resultObjects = [];
	var targetDate = "";
	var targetLayer = "";
	var newObject = null;
	for(var userIndex in user)
	{
		baseObject.userId = user[userIndex];
		for(var i = 0; i <= baseLayers.length; i++)
		{
			newObject = JSON.parse(JSON.stringify(baseObject));
			newObject.userId = user[userIndex];
			
			newObject.orderId = resultObjects.length;
			
			if(i == 0)
				targetLayer = baseLayers[0];
			else
				targetLayer = baseLayers[i-1];
			
			newObject.orderedLayers.push(targetLayer);

			var latestIndex = -1;
			for(var versionIndex in layerVersions)
			{
				if(layerVersions[versionIndex].layerCode != targetLayer)
					continue;
				
				if(latestIndex == -1)
				{
					latestIndex = versionIndex;
					continue;
				}
				
				if(layerVersions[versionIndex].date > layerVersions[latestIndex].date)
					latestIndex = versionIndex;
			}
			
			if(i == 0)
			{
				newObject.updateStatus = 2;
				newObject.orderDate = layerVersions[latestIndex].date.substring(0, layerVersions[latestIndex].date.length - 9) + " 00:00:00";
				newObject.orderVersion = newObject.orderDate;
			}
			else
			{
				newObject.updateStatus = 1;
				if(i == 1)
					newObject.orderDate = layerVersions[latestIndex].date.substring(0, layerVersions[latestIndex].date.length - 9) + " 15:00:00";
				else
					newObject.orderDate = layerVersions[latestIndex].date.substring(0, layerVersions[latestIndex].date.length - 9) + " 06:00:00";
				
				newObject.orderVersion = newObject.orderDate;
			}

			resultObjects.push(newObject);
		}
	}
	
	var strOrderData = JSON.stringify(resultObjects);
	localStorage.setItem(orderDataKey, strOrderData);
}

function initializeLayerVersions()
{
	var strLayerVersions = localStorage.getItem(layerVersionsByDistrictKey);
	if(strLayerVersions != null && strLayerVersions.length != 0)
		return;
	
	var layerList =
	[
		" 도로경계면"," 도로링크"," 도로노드"," 철도경계면"," 철도링크"," 철도노드"," 입체도로"," 교량"," 터널"," 철도승강장"," 인도",
	    " 하천경계면"," 하천중심선"," 실폭하천선",
	    " 건물",
	    " 등고선"," 표고점",
	    " 특별시/광역시/도"," 시/군/구"," 읍/면/동(행정경계)"," 읍/면/동(법정경계)",
	    " POI"," 정사영상"," DEM"," 측량기준점"," 지적"
	];
	
	var resultObjects = [];
	var loopCount = 0;
	var randomYear = 0;
	var randomMonth = 0;
	var randomDay = 0;
	var strDate = "";
	for(var layerIndex in layerList)
	{
		loopCount = getRandomInt(4, 6);
		for(var i = 0; i < loopCount; i++)
		{
			randomYear = getRandomInt(2011, 2016);
			if(randomYear > 2016)
				randomYear = 2016;
			
			if(randomYear == 2016)
			{
				randomMonth = getRandomInt(1, 5);
				if(randomMonth > 5)
					randomMonth = 5;
			}
			else
			{
				randomMonth = getRandomInt(1, 12);
				if(randomMonth > 12)
					randomMonth = 12;
			}
			
			if(randomYear == 2016 && randomMonth == 5)
				randomDay = getRandomInt(1, 15);
			else
				randomDay = getRandomInt(1, 28);
			
			strDate = randomYear + "." + (randomMonth > 9 ? randomMonth : "0" + randomMonth) + "." + (randomDay > 9 ? randomDay : "0" + randomDay) + " 09:00:00";
				
			var object = 
			{
				"id":			resultObjects.length,	// layerCode & version package unique id
				"layerCode":	layerList[layerIndex],	// temporary string of layer name. originally table name of NFSD
				"date":			strDate,				// date at which that version is applied
				"bjcd":			""						// 영역별 버전관리를 위한 umd bjcd
			};
			
			resultObjects.push(object);
		}
	}
	
	strLayerVersions = JSON.stringify(resultObjects);
	localStorage.setItem(layerVersionsByDistrictKey, strLayerVersions);
}

function updateAllNewStatus(){
	
	var orderData = selectAllData(orderDataKey);
	for(var orderIndex in orderData){
		if(orderData[orderIndex].updateStatus != 3 ){
			continue;
		} else if(orderData[orderIndex].userId == userEmail){
			orderData[orderIndex].updateStatus = 2;
		}
	}

	localStorage.setItem(orderDataKey, JSON.stringify(orderData));
	$("#navMypg").removeClass("new");
}


function checkNewStatus(){
	
	var retFlag = false;
	var orderData = selectAllData(orderDataKey);
	
	for(var orderIndex in orderData){
		if(orderData[orderIndex].updateStatus == 3 ){
			retFlag = true;
			break;
		} 
	}

	return retFlag;
}


function changeUpdateStatus(orderId, status){
	var orderData = selectAllData(orderDataKey);
	for(var orderIndex in orderData){
		if(orderData[orderIndex].orderId == orderId){
			orderData[orderIndex].status = status;
		}
	}
	
	//insertData(orderData, orderDataKey);
	localStorage.setItem(orderDataKey, JSON.stringify(orderData));
}


function getRandomInt(min, max)
{
	return Math.floor(Math.random() * (max - min + 1)) + min;
}