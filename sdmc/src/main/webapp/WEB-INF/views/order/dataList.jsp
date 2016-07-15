<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page contentType="text/html; charset=UTF-8" %>

    	<p class="where">
        	위치설정
            <button id="clearAreaSelection"type="button" class="small">비움</button>
        </p>
        <div id="areaSelected" class="ResultList"></div>
        <!-- END 위치 -->
        
        <p class="what">
        	자료의 종류
            <button id="clearTypeSelection" type="button" class="small">비움</button>
        </p>
        <!-- 교통 trans, 수계 water, 건축물 build, 고도 eleva, 경계 bound, 기타 etc -->
        <div id="typeSelected" class="ResultList"></div>
        <!-- END 종류 -->
        
        <p class="when">
        	포맷 설정
        </p>
        <div id="dataFormat" class="ResultList" style="height:50px">
        	Shape + GeoTiff
        </div>
        <p class="when">
        	좌표계 설정
        </p>
        <div id="dataSrs" class="ResultList" style="height:50px">
        	EPSG:5179 (GRS80 UTM-K)
        </div>
        <p class="when">
        	생성시점
        </p>
        <div id="dateSelected" class="ResultList" style="height:50px">
        	최신버전
        </div>
        <!-- END 시점 -->
        <button type="button" class="point" onclick="requestData();">자료요청</button>
        
