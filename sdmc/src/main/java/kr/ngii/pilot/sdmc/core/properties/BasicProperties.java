package kr.ngii.pilot.sdmc.core.properties;

public class BasicProperties {
	
	// background tile image URL
	public static final String BASIC_TILE_SERVICE = "";
	
	// database configuration
	//public static final String SPATIAL_DATABASE_URL = "jdbc:postgresql://172.22.24.249:5432/sdmc";
	public static final String DATABASE_ACCOUNT = "ngii";
	public static final String SPATIAL_DATABASE_URL = "jdbc:postgresql://210.117.198.100/sdmc";
	//public static final String SPATIAL_DATABASE_URL = "jdbc:postgresql://192.168.10.38:5432/sdmc";
	//public static final String DATABASE_ACCOUNT = "postgres";
	public static final String DATABASE_PASSWORD = "0000";
	
	// map data server configuration
	public static final String MAP_SERVER_URL = "";
	
	// data processing server 접근 설정
	//public static final String DATA_PROCESSING_API = "http://172.22.24.249/api";
	//public static final String DATA_PROCESSING_API = "http://192.168.0.4:5000/api";
	//public static final String DATA_PROCESSING_API = "http://192.168.10.38/api";
	//public static final String DATA_PROCESSING_API = "http://192.168.10.38/api";
	public static final String DATA_PROCESSING_API = "http://210.117.198.100/api";
	
	// 주문 처리 결과 관련 메시지 설정
	public static final String ORDER_HANDLING_SUCCESS = "order handling success";
	public static final String ORDER_RECEPTION_FAILURE = "order reception failure";
	public static final String ORDER_PROCESSING_FAILURE = "order processing failure";
	
	// social login 처리 결과를 넘겨 받기 위한 redirect용 url
	// 반드시 ip 정보가 아닌!! public 등록된 도메인 url을 입력해야 한다. 예:naver.com등...
	//public static final String THIS_URL = "http://osgeo.ipdisk.co.kr:48080/sdmc/loginCallback.ngii";
	public static final String THIS_URL = "http://pilot.ngii.go.kr/sdmc/loginCallback.ngii";
	
}
