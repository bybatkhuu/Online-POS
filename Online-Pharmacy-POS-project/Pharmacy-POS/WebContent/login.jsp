<%@ page import="utils.LoggedUser"%>
<%@ page import="models.User"%>
<%@ page import="models.Company"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
	String message = "";
	String msgClass = "";
	int status = LoggedUser.checkLogin(session);
	if (status == 1)
	{
		response.sendRedirect("index.jsp");
	}
	
	if (request.getParameter("message") != null)
	{
		int msgStatus = Integer.parseInt(request.getParameter("message"));
		switch (msgStatus)
		{
			case 200:
				message = "Хэрэглэгчийн нэр эсвэл нууц үг буруу байна!";
				msgClass = "has-error";
				break;
			case 201:
				message = "Энэ хэрэглэгч өөр газар нэвтэрсэн байна!";
				msgClass = "has-error";
				break;
			case 202:
				message = "Энэ салбарын POS-н хязгаар хэтэрсэн байна!";
				msgClass = "has-error";
				break;
			default:
				break;
		}
	}
%>
<html lang="mn">
  <%
  	Company com = new Company(session);
  %>
  <head>
    <meta charset="utf-8" />
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta name="description" content="Infosystems LLC - Online POS: Main page">
    <meta name="keywords" content="Infosystems POS, Online POS, POS, Infosystems LLC, Infosystems">
    <meta name="author" content="Infosystems LLC">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=0"/>
    <meta name="apple-mobile-web-app-capable" content="yes" />
	<meta name="apple-mobile-web-app-status-bar-style" content="black" />

    <title>Нэвтрэх хуудас - [${company.name} ХХК]</title>
    
    <link rel="icon" type="image/x-icon" href="images/favicon.ico" />
    <link rel="apple-touch-icon" href="images/Logo.png">
    <link rel="apple-touch-startup-image" href="images/Logo.png">
    
    <link rel="stylesheet" href="css/bootstrap.min.css" />
    <link rel="stylesheet" href="css/font-awesome.min.css" />
    <link rel="stylesheet" href="css/jquery-ui-1.10.3.custom.min.css" />
    <link rel="stylesheet" href="css/ace-fonts.css" />
    <link rel="stylesheet" href="css/ace.min.css" />
    <link rel="stylesheet" href="css/ace-skins.min.css" />
    
    <link rel="stylesheet" href="css/style.css" />
    <style type="text/css">
	
    </style>
    
    <script type="text/javascript" src="js/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="js/bootstrap.min.js"></script>
    <script type="text/javascript" src="js/jquery-ui-1.10.3.custom.min.js"></script>
    <script type="text/javascript" src="js/ace-elements.min.js"></script>
    <script type="text/javascript" src="js/ace.min.js"></script>

    <script type="text/javascript">
      $(document).ready(function()
      {
      });
    </script>
  </head>
  <body class="login-layout bh-login-body-bg-color">
    <div class="main-container">
      <div class="main-content">
        <div class="row">
          <div class="col-sm-10 col-sm-offset-1 bh-login-margin-top">
            <div class="login-container">
              <div class="center">
                <h1>
                  <i class="icon-medkit green"></i>
                  <span class="red">Инфосистемс</span>
                  <span class="black">POS</span>
                </h1>
                <h4 class="blue">&copy; [${company.name} ХХК]</h4>
              </div>
              <div class="space-6"></div>
              <div class="position-relative">
                <div id="login-box" class="login-box visible widget-box no-border bh-login-border-bg-color">
                  <div class="widget-body">
                    <div class="widget-main">
                      <h4 class="header blue lighter bigger">Та өөрийн мэдээллээ оруулна уу</h4>
                      <div class="space-6"></div>
                      <form action="login" method="POST">
                        <fieldset>
                          <div class="form-group <%= msgClass %>">
	                        <div class="red"><%= message %></div>
	                        <label class="block clearfix">
	                          <span class="block input-icon input-icon-right">
	                            <input type="text" name="userName" class="form-control" placeholder="Нэвтрэх нэр" pattern="[A-Za-zA-Яа-яҮүӨөЁё0-9]{2,64}" autofocus required />
	                            <i class="icon-user"></i>
	                          </span>
	                        </label>
                          </div>
                          <div class="form-group <%= msgClass %>">
	                        <label class="block clearfix">
	                          <span class="block input-icon input-icon-right">
	                            <input type="password" name="password" class="form-control" placeholder="Нууц үг" pattern="[A-Za-zA-Яа-яҮүӨөЁё0-9]{4,64}" required />
	                            <i class="icon-lock"></i>
	                          </span>
	                        </label>
                          </div>
                          <div class="space"></div>
                          <div class="clearfix">
                            <label class="inline hidden">
                              <input type="checkbox" class="ace" />
                              <span class="lbl"> Намайг сана!</span>
                            </label>
                            <button type="submit" class="width-35 pull-right btn btn-sm btn-primary">
                              <i class="icon-key"></i> Нэвтрэх
                            </button>
                          </div>
                        </fieldset>
                      </form>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </body>
</html>