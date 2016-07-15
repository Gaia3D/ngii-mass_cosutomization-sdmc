package kr.ngii.pilot.sdmc.util;

/**
 * StringUtil 모음
 * @author sgshs
 * 2016.05.30
 *
 */
public class StringUtil {
	
	/**
	 * isEmpty(String)
	 * string을 받아 null인지 체크해주는 함수. 워낙 빈번히 쓰이기 때문에 별도 static 함수로 분리함. javascript와의 호환을 위하여 빈 텍스트나 undefined 텍스트도 null로 간주한다.
	 * @param str null체크하는 text
	 * @return boolean
	 */
	public static boolean isEmpty(String str){
		boolean flag = false;
		
		if(isNull(str)){
			flag = true;
		} else if(str.length() < 1){
			flag = true;
		} else if("".equals(str)){
			flag = true;
		} else if("undefined".equals(str)){
			flag = true;
		} else if("null".equals(str)){
			flag = true;
		}
		
		return flag;
	}
	
	/**
	 * isNull
	 * text가 null인지 판단하는 함수. null이거나  ""인 경우를 체크해준다.
	 * @param str
	 * @return boolean
	 */
	public static boolean isNull(String str){
		boolean flag = false;
		
		if(str == null){
			flag = true;
		} 
		return flag;
		
	}
}
