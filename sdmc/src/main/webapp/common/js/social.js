// 소셜 로그인 init 처리
/*
* fbLoginBool : 페이스북 로그인 (true, false)
* gpLoginBool : 구글 플러스 로그인 (true, false)
* likeBool : 페이스북 라이크버튼 유무 (true, false)
* plusBool : 구글플러스 +1 버튼 유무 (true, false)
* pinterestBool : pinterest 버튼 유무 (true, false)
* twitterBool : twitter 버튼 유무 (true, false)
*/
var allRequest = {
    socialType : 'init',
    fbLoginBool : true,
    gpLoginBool : true
};
var sc = new VtSocial(allRequest);
$(document).ready(function() {
    sc.InitLoadFn(socialInitFn_);
});
// init 콜백함수
function socialInitFn_(params) {
    if (typeof params != 'undefined' && params.socialState) {
        console.log(params.responseData);
    }
}
// 구글 플러스 로그인 버튼 이벤트
function googleLoginFn() {
    // 구글 플러스 initFn 호출
    // html 페이지에서 class 명을 googlePlusSignButton 로 해야만 버튼이 생성된다.
    var googleRequest = {
        socialType : 'google'
    };
    // 클래스 선언 후 LoginFn 호출하면서 콜백함수를 넣는다
    var sc = new VtSocial(googleRequest);
    sc.LoginFn(googleLoginFn_);
}
// 구글 플러스 로그인 콜백함수
// 리턴 값 중 responseData 값으로 원하는 처리를 한다.
function googleLoginFn_(params) {
    if (typeof params != 'undefined' && params.socialState) {
        console.log(params.responseData);
    }
}
// 로그인 버튼 이벤트
function facebookLoginFn() {
    var facebookRequest = {
        socialType : 'facebook'
    };
    // social 관련 클래스
    // 클래스 선언 후 LoginFn 호출하면서 콜백함수를 넣는다
    var sc = new VtSocial(facebookRequest);
    sc.LoginFn(facebookLoginFn_);
}
// 로그인 후 처리하는 함수
function facebookLoginFn_(params) {
    if (typeof params != 'undefined' && params.socialState) {
        console.log(params.responseData);
    }
}
