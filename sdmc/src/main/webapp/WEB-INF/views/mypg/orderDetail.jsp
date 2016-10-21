<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page contentType="text/html; charset=UTF-8" %>

    	<!-- 자료가 생성중일 경우 아래 DIV 활성화 -->
        <div class="message" id="downProcessing" style="display:none">
        	<p class="download" style="text-align:left;padding-left:320px;"></p>
        	<div id="progressbar1"></div>
        </div>
    	<!-- 자료가 생성중일 경우 아래 DIV 활성화 -->
        <div class="message" id="downReady" style="display:none">
        	<p class="download">
        		자료생성이 완료되었습니다.
            </p>
            <button type="button">다운로드</button>
            <button type="button">온맵으로 배포</button>
            <button type="button">국가인터넷지도 배포</button>
        </div>
        <!-- 만료되었을 경우 아래 DIV 활성화 -->
        <div class="message" id="downExpired" style="display:none">
        	<p class="expired">
        		본 자료는 만료되었습니다.
            </p>
            <button type="button" onclick="requestNewOrder($('#orderList li.on a'));return false;">같은 자료 재신청</button>
        </div>        
        
        <!-- 자료 갱신되었을 경우 기존 자료에 아래 DIV 활성화 -->
        <div class="message" id="downOrdered" style="display:none">
        	<p class="expired">
        		갱신 요청된 주문입니다.
            </p>
        </div>        
        
        <!-- 오류 발생할 경우 아래 DIV 활성화 -->
        <div class="message" id="downException" style="display:none">
        	<p class="expired">
        		 예외가 발생하였습니다.<br /> 관리자에게 문의바랍니다.
            </p>
        </div>        
        
        <div class="tablewrap">
            <table>
            	<tr>
                	<th>위치</th>
                    <td class="title">서울특별시 강남구 개포1동, 개포2동, 개포3동</td>
                </tr>
                <tr>
                	<th>주문일</th>
                    <td>2016. 5. 10</td>
                </tr>
                <tr>
                	<th>종류</th>
                    <td>
                    	<p class="trans">
                    		<span> 도로경계면</span>
                            <span> 도로경계면</span>
                            <span> 도로경계면</span>
                            <span> 도로경계면</span>
                            <span> 도로경계면</span>
                            <span> 도로경계면</span>
                        </p>
                    </td>
                </tr>
                <tr>
                	<th>Format</th>
                    <td>Shape + GeoTiff</td>
                </tr>
                <tr>
                	<th>SRS</th>
                    <td>358.55455</td>
                </tr>
                <tr>
                	<th>시점</th>
                    <td>최근생성자료</td>
                </tr>
            </table>
        </div>
                     