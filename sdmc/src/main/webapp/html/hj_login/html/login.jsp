<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
	<title>맞춤형 공간정보제공시스템</title>
<link href="../css/style.css" type="text/css" rel="stylesheet"/>
</head>
<body>
	<section class="loginform cf">
       <div class="loginForm">
        <form method="post" name="" action="/sdmc/signin.ngii">
           <div class="box">
           <li>
           <label for="usermail">Email</label>
            <input type="text" class="iText" name = "usermail" placeholder="yourname@email.com" required>
            </li>
            <br>
            <li>
            <label for="password">Password</label>
            <input type="password" name="password" id="" class="iText" placeholder="password" required>
            </li>
            <br>
            <p>
              <span class="fright"><a href="">Find ID</a>&nbsp;|&nbsp;<a href="">Find Password</a></span>
            </p>
          </div>
          <input type = "submit" value = "Login">
          <a href="singup.html" id="" class="signup">singup</a>
        </form>
      </div>
      </section>
</body>
</html>
