/**
* social reference js
* 소셜 관련 참조 스크립트
* 필요 reference { jquery.js }
**/
// 외부에서 VtSocial 클래스 안에 함수호출로..
var vtArgs = {};
// 마지막 처리 후 콜백할 함수 저장 공간
var vtInitFinishFn = null;
function VtSocial(params) {
   // 생성자 같은 거지만 그렇지 않기도 한 정체불명..
   vtArgs = {
      socialType : params.socialType,
      socialState : null,
      responseData : null,
      fbLoginBool : params.fbLoginBool,
      gpLoginBool : params.gpLoginBool,
      likeBool : params.likeBool,
      plusBool : params.plusBool,
      pinterestBool : params.pinterestBool,
      twitterBool : params.twitterBool
   };
   // js 로드
   VtSocial.prototype.InitLoadFn = function(callbackFn) {
      // 어떤 로그인을 원하는가?
      switch (vtArgs.socialType) {
         // 이건 초기화 기능
         case 'init':
            vtInitFinishFn = callbackFn;
            if (vtArgs.fbLoginBool) {
               if (typeof FB == 'undefined') {
                  vtFacebookLoadFn();
               } else {
                  vtFacebookInitFn();
               }
            }
            if (vtArgs.gpLoginBool) {
               if (typeof gapi == 'undefined') {
                  vtGoogleLoadFn();
               } else {
                  vtGoogleInitFn();
               }
            }
            break;
            // 페이스북
         case 'facebook':
            if (typeof FB == 'undefined') {
               vtFacebookLoadFn();
            } else {
               vtFacebookInitFn();
            }
            break;
            // 구글 플러스
         case 'google':
            if (typeof gapi == 'undefined') {
               vtGoogleLoadFn();
            } else {
               vtGoogleInitFn();
            }
            break;
         default :
            break;
      }
      // 구글 플러스 js 로드
      // 콜백 함수 정의
      function vtGoogleLoadFn() {
         vtScriptLoadFn('https://apis.google.com/js/client:plusone.js?onload=vtGoogleLoadAfterFn', null);
      }
      // 구글 플러스 js 로드 후 실행함수
      function vtGoogleLoadAfterFn() {
         // 로드 후 실행
         vtGoogleInitFn();
      }
      // 페이스북 js 로드
      function vtFacebookLoadFn() {
         vtScriptLoadFn('http://connect.facebook.net/en_US/sdk.js', vtFacebookInitFn);
      }
      // 페이스북 초기화
      function vtFacebookInitFn() {
         if (typeof window.fbAsyncInit == 'undefined') {
            // 페이스북 inint
            // 이곳에 페이스북 appid 를 넣는다.
            window.fbAsyncInit = function() {
               FB.init({
                  appId      : '760876127308605',
                  cookie     : true,
                  xfbml      : true,
                  version    : 'v2.0'
               });
               // 처음 로드시 실행하는 init 라면
               if (vtArgs.socialType == 'init') {
                  vtFacebookFinishFn();
               } else {
                  // 로그인 처리 및 가입절차로 간다.
                  // 페이스북 팝업창이 뜨는거
                  vtFacebookLoginFn_();
               }
            };
         } else {
            // 처음 로드시 실행하는 init 라면
            if (vtArgs.socialType == 'init') {
               vtFacebookFinishFn();
            } else {
               // 로그인 처리 및 가입절차로 간다.
               // 페이스북 팝업창이 뜨는거
               vtFacebookLoginFn_();
            }
         }
      }
      // 페이스북 마무리
      function vtFacebookFinishFn() {
         // 페이스북 로그인 상태 확인
         FB.getLoginStatus(function(response) {
            // 로그인 및 가입승인도 한 유저일 경우
            if (response.status == 'connected') {
               vtArgs.socialState = true;
               // 승인된 그 유저의 정보 가져오기
               FB.api('/me', function(responseData) {
                  vtArgs.responseData = responseData;
                  // 로그인 버튼 사라지기
                  $('#facebookSignButton').hide();
                  // 끝내기
                  vtInitFinishFn(vtArgs);
               });
            } else {
               // 끝내기
               vtInitFinishFn(vtArgs);
            }
         });
         // 좋아요
         if (vtArgs.likeBool) {
            var likeHtml = '';
            likeHtml += '<div class="fb-like"';
            likeHtml += ' data-href="http://www.varyto.com"';
            likeHtml += ' data-layout="button_count" data-action="like"';
            likeHtml += ' data-show-faces="true" data-share="true"></div>';
            $('.facebookLikeButton').empty().html(likeHtml);
         }
         // +1
         if (vtArgs.plusBool) {
            var plusHtml = '';
            plusHtml += '<div class="g-plusone"';
            plusHtml += ' data-annotation="bubble"></div>';
            $('.plusButton').empty().html(plusHtml);
         }
         // pinterest
         if (vtArgs.pinterestBool) {
            // getScript 로드가 되는 놈
            vtScriptLoadFn('http://assets.pinterest.com/js/pinit.js', pinterestCallbackFn);
         }
         // 트위터
         if (vtArgs.twitterBool) {
            // getScript 잘 안되는 놈
            // 잘 안되서 그냥 우선 로드...
            !function(d,s,id) {
               var js,fjs=d.getElementsByTagName(s)[0];
               if(!d.getElementById(id)) {
                  js=d.createElement(s);
                  js.id=id;
                  js.src="https://platform.twitter.com/widgets.js";
                  fjs.parentNode.insertBefore(js,fjs);
               }
            }(document,"script","twitter-wjs");
            var twitterHtml = '';
            twitterHtml += '<a href="https://twitter.com/share"';
            twitterHtml += ' class="twitter-share-button" data-lang="en"';
            twitterHtml += ' data-count="none" data-via="';
            twitterHtml += 'http://www.varyto.com';
            twitterHtml += '" data-text="';
            // 설명
            twitterHtml += 'description';
            twitterHtml += '">Tweet</a>';
            $('.twitterButton').empty().html(twitterHtml);
         }
      }
      function pinterestCallbackFn() {
         var pinHtml = '';
         pinHtml += '<a href="//kr.pinterest.com/pin/create/button/?url=';
         pinHtml += 'http://www.varyto.com';
         pinHtml += '&media=';
         // 이미지 URL
         pinHtml += 'url';
         pinHtml += '&description=';
         // 설명
         pinHtml += 'description';
         pinHtml += '" data-pin-do="buttonPin" data-pin-config="beside">';
         pinHtml += '<img src="//assets.pinterest.com/images/pidgets/pinit_fg_en_rect_gray_20.png" /></a>';
         $('.pinterestButton').empty().html(pinHtml);
      }
   };
   // 로그인함수
   VtSocial.prototype.LoginFn = function(callbackFn) {
      // 로그인 버튼 클릭 시 마지막 실행되는 함수
      vtInitFinishFn = callbackFn;
      // 로그인 종류에 따라서
      switch (vtArgs.socialType) {
         case 'facebook':
            VtSocial.prototype.InitLoadFn();
            break;
         case 'google':
            VtSocial.prototype.InitLoadFn();
            break;
         default :
            break;
      }
   };
   // 좋아요 버튼
   VtSocial.prototype.facebookLikeFn = function() {
      var likeBtn = '<div class="fb-like" data-href="http://www.naver.com"';
      likeBtn += ' data-layout="button" data-action="like"';
      likeBtn += ' data-show-faces="true" data-share="true"></div>';
      // 페이스북 좋아요 처리
      $('.facebookLikeButton').html(likeBtn);
   };
}
// 완료 콜백함수
// scope 에 받을 정보를 추가하면 된다.
// 이곳 저곳에서 접근해야하는 경우로 전역함수로 변경하였다.
function vtFacebookLoginFn_() {
   // 페이스북 로그인
   FB.login(function(response) {
      if (response.authResponse) {
         // 로그인 및 승인한 유저
         if (response.status == 'connected') {
            vtArgs.socialState = true;
            // 승인한 유저 정보 가져오기
            FB.api('/me', function(response) {
               vtArgs.responseData = response;
               // 버튼 가리기
               $('#facebookSignButton').hide();
               vtInitFinishFn(vtArgs);
            });
         } else if (response.status == 'not_authorized') {
            // 로그인은 했지만 가입은 안한 유저
            vtArgs.socialState = false;
            vtInitFinishFn(vtArgs);
         } else {
            // 로그인도 안한 유저
            vtInitFinishFn(vtArgs);
         }
      } else {
         vtInitFinishFn(vtArgs);
      }
   }, {scope: 'email'});
}
// 구글 플러스
// 정보 가져오기
// 구글 플러스 callback 함수명을 미리 날려서 콜백을 받기 때문에
// 바로 접근할 수 있도록 전역 함수로 변경한다.
function _vtGetGoogleInfoCallback_(obj) {
   // 로그인 정보 저장
   vtArgs.socialState = true;
   if (obj != null) {
      vtArgs.responseData.gp_email = obj['email'];
      vtArgs.responseData.gp_familyName = obj['family_name'];
      vtArgs.responseData.gp_gender = obj['gender'];
      vtArgs.responseData.gp_id = obj['id'];
      vtArgs.responseData.gp_link = obj['link'];
      vtArgs.responseData.gp_locale = obj['locale'];
      vtArgs.responseData.gp_name = obj['name'];
      vtArgs.responseData.gp_givenName = obj['given_name'];
      vtArgs.responseData.gp_picture = obj['picture'];
   }
   if (typeof vtInitFinishFn == 'function') {
      vtInitFinishFn(vtArgs);
   }
}
// 구글 플러스
// 완료 콜백함수
// 구글 플러스 callback 함수명을 미리 날려서 콜백을 받기 때문에
// 바로 접근할 수 있도록 전역 함수로 변경한다.
function _vtGoogleLoginFn_(authResult) {
   // 정상처리
   if (authResult) {
      // 에러가 없으면
      if (authResult['error'] == undefined){
         // 리턴 값 저장
         vtArgs.responseData = authResult;
         // 토큰 저장
         gapi.auth.setToken(authResult);
         // 버튼 숨기기
         $('#googlePlusSignButton').hide();
         // 이메일 가져오기
         gapi.client.load('oauth2', 'v2', function() {
            var request = gapi.client.oauth2.userinfo.get();
            request.execute(_vtGetGoogleInfoCallback_);
         });
      } else {
         // 에러값이 앱승인이 아니면
         if (authResult['error'] == 'immediate_failed') {
            vtArgs.socialState = false;
         }
         vtArgs.responseData = {
            error: authResult['error']
         };
         if (typeof vtInitFinishFn == 'function') {
            vtInitFinishFn(vtArgs);
         }
      }
   } else {
      vtArgs.responseData = {
         error: 'etc error'
      };
      if (typeof vtInitFinishFn == 'function') {
         vtInitFinishFn(vtArgs);
      }
   }
}
// 구글 플러스 버튼 생성 및 이런 저런거
function vtGoogleInitFn() {
   var googleParams = {
      'callback': '_vtGoogleLoginFn_',
      'clientid': '225751585490-g0f642ar1i1j7ljio4bi0qe2m0uhkak4.apps.googleusercontent.com',
      'cookiepolicy': 'single_host_origin',
      'requestvisibleactions': 'http://schemas.google.com/AddActivity',
      'scope': 'https://www.googleapis.com/auth/plus.login https://www.googleapis.com/auth/userinfo.email',
      'redirect_uri' : 'http%3A%2F%2Fosgeo.ipdisk.co.kr%2Fsdmc%2Flogin.ngii'
   }
   gapi.auth.signIn(googleParams);
}
// script 동적 로드
function vtScriptLoadFn(url, callback) {
   $.getScript(url , function(data,textStatus,xhr){
      if (xhr.readyState == 4) {
         if (typeof callback == 'function') {
            callback();
         }
      }
   });
}
