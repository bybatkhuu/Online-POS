<%@ page import="utils.LoggedUser"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	int status = LoggedUser.checkLogin(session);
	if (status != 1)
	{
		response.sendRedirect("login.jsp");
	}
%>
<!DOCTYPE html>
<html lang="mn">
	<head>
		<meta charset="utf-8" />
	    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
	    <meta name="description" content="Infosystems LLC - Online POS: Report page">
	    <meta name="keywords" content="Infosystems POS, Online POS, POS, Infosystems LLC, Infosystems">
	    <meta name="author" content="Infosystems LLC">
	    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=0"/>
	    <meta name="apple-mobile-web-app-capable" content="yes" />
		<meta name="apple-mobile-web-app-status-bar-style" content="black" />
	
	    <title>Падаан - [${company.name} ХХК]</title>
	    
	    <link rel="icon" type="image/x-icon" href="images/favicon.ico" />
	    <link rel="apple-touch-icon" href="images/Logo.png">
	    <link rel="apple-touch-startup-image" href="images/Logo.png">
	    
	    <link rel="stylesheet" href="css/bootstrap.min.css" />
	    <link rel="stylesheet" href="css/font-awesome.min.css" />
	    <link rel="stylesheet" href="css/jquery-ui-1.10.3.full.min.css" />
	    <link rel="stylesheet" href="css/daterangepicker-bs3.css" />
	    <link rel="stylesheet" href="css/ace-fonts.css" />
	    <link rel="stylesheet" href="css/ace.min.css" />
	    <link rel="stylesheet" href="css/ace-skins.min.css" />
	    
	    <link rel="stylesheet" href="css/custom-icons.css" />
	    <link rel="stylesheet" href="css/style.css" />
	    <style type="text/css">
	    	
	    </style>
	    
	    <script type="text/javascript" src="js/ace-extra.min.js"></script>
	    <script type="text/javascript" src="js/jquery-1.11.0.min.js"></script>
	    <script type="text/javascript" src="js/bootstrap.min.js"></script>
	    <script type="text/javascript" src="js/jquery-ui-1.10.3.full.min.js"></script>
	    <script type="text/javascript" src="js/jquery.ui.datepicker-mn.js"></script>
	    <script type="text/javascript" src="js/jquery.dataTables.min.js"></script>
		<script type="text/javascript" src="js/jquery.dataTables.bootstrap.js"></script>
		<script type="text/javascript" src="js/jquery.maskedinput.min.js"></script>
	    <script type="text/javascript" src="js/date-time/moment.min.js"></script>
	    <script type="text/javascript" src="js/ace-elements.min.js"></script>
	    <script type="text/javascript" src="js/ace.min.js"></script>
	    
	    <script type="text/javascript" src="js/page-js/facture.js"></script>
	    <script type="text/javascript">
	    	$(document).ready(function()
	    	{
	    		
	      	});
	    </script>
	</head>
	<body>
	<header class="navbar navbar-default" id="navbar">
		<c:set var="allTotal" value="0" scope="page" />
		<c:forEach items="${itemList}" var="item" varStatus="status">
             <c:set var="allTotal" value="${allTotal + item.total}" />
         </c:forEach>
		<div class="navbar-container container" id="navbar-container">
			<div class="navbar-header pull-left">
				<a href="index.jsp" class="navbar-brand">
            		<small>
              			<i class="icon-medkit"></i>
              			Инфосистемс POS ${test}
            		</small>
          		</a><!-- /.brand -->
        	</div><!-- /.navbar-header -->
        	<div class="navbar-header pull-right">
          		<ul class="nav ace-nav">
            		<li class="light-blue2">
              			<a href="#" data-toggle="dropdown" class="dropdown-toggle">
                			<span class="user-info">
                  				<small>Тавтай морилно уу,</small>
                  				${user.userName}
                			</span>
                			<i class="icon-caret-down"></i>
              			</a>
              			<ul class="user-menu pull-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
                			<li class="hidden">
                  				<a href="#">
                    				<i class="icon-cog"></i>
                    				Тохиргоо
                  				</a>
                			</li>
                			<li class="hidden">
                				<a href="#">
			                    	<i class="icon-user"></i>
			                    	Хувийн хэрэг
			                  	</a>
			                </li>
			                <li>
			                  	<a href="index.jsp">
			                    	<i class="bhicon bhicon-cash"></i>
			                    	Касс
			                  	</a>
			                </li>
			                <li class="divider"></li>
			                <li>
			                  	<a href="logout">
			                    	<i class="icon-off"></i>
			                    	Гарах
			                  	</a>
			                </li>
              			</ul>
            		</li>
          		</ul><!-- /.ace-nav -->
        	</div><!-- /.navbar-header -->
      	</div><!-- /.container -->
    </header>
	<div class="bh-non-print">
        	<div class = "row center" style="font-size: 20px">
        	Нэмэгдсэн өртгийн албан татбарын падан 
        	</div>
	        <div class = "row">
	        	 <div class = "col-xs-12 col-sm-4">
		        	 <div class="form-group">
			             <label for="buyName" class="control-label col-xs-12 col-sm-6 col-md-6 no-padding-right">Борлуулагчийн нэр:</label>
			             <div class="col-xs-12 col-sm-6 col-md-6">
			               <div class="input-group">
			                 <!-- <input type="text" name="buyName" tabindex="1" value="" class="col-xs-12 col-sm-12 input-sm bolder dark bh-input-skin-1 bh-font-size-16" id="buyName" />
			                -->
			                <label class="control-label col-xs-12 col-sm-12">${company.name}</label>
			                </div>
			             </div>
			           </div>
			     </div>
			     <div class = "col-xs-12 col-sm-4 col-sm-offset-3">
		        	 <div class="form-group">
			             <label for="saleName" class="control-label col-xs-12 col-sm-6 col-md-6 no-padding-right">Худалдан авагчийн нэр:</label>
			             <div class="col-xs-12 col-sm-6 col-md-6">
			               <div class="input-group">
			                 <input type="text" name="saleName" tabindex="2" value="" class="col-xs-12 col-sm-12 input-sm bolder dark bh-input-skin-1 bh-font-size-16" id="saleName" />
			               </div>
			             </div>
			           </div>
			     </div>
			</div>
			<div class = "row">
	        	 <div class = "col-xs-12 col-sm-4">
		        	 <div class="form-group">
			             <label for="buyNumber" class="control-label col-xs-12 col-sm-6 col-md-6 no-padding-right">Регистрийн дугаар:</label>
			             <div class="col-xs-12 col-sm-6 col-md-6">
			               <div class="input-group">
			                 <input type="text" maxlength="7" tabindex="3" name="buyNumber" value="" class="col-xs-12 col-sm-12 input-sm bolder dark bh-input-skin-1 bh-font-size-16" id="buyNumber" />
			               </div>
			             </div>
			           </div>
			     </div>
			     <div class = "col-xs-12 col-sm-4 col-sm-offset-3">
		        	 <div class="form-group">
			             <label for="saleNumber" class="control-label col-xs-12 col-sm-6 col-md-6 no-padding-right">Регистрийн дугаар:</label>
			             <div class="col-xs-12 col-sm-6 col-md-6">
			               <div class="input-group">
			                 <input type="text" maxlength="7" tabindex="4" name="saleNumber" value="" class="col-xs-12 col-sm-12 input-sm bolder dark bh-input-skin-1 bh-font-size-16" id="saleNumber" />
			               </div>
			             </div>
			          </div>
			     </div>
			</div>
			<div class = "row">
	          	<div class = "col-xs-12 col-sm-offset-7 col-sm-1">
					<button type="submit" tabindex="6" class="btn btn-sm btn-success btn-block" id="select">
						Хэвлэх
						<i class="icon-ok"></i>
					</button>
				</div>
				<div class = "col-xs-12 col-sm-1">
					<a href ="cash.jsp" class="btn btn-sm btn-danger btn-block" tabindex="7" id="back">
						Буцах
						<i class="icon-reply"></i>
					</a>
				</div>
				<div id = "talon" class = "hidden"></div>
				<div id = "customerCheck" class = "hidden"></div>
				<div id = "customers" class = "hidden"></div>
				<div id = "bankCheck" class = "hidden"></div>
				<div id = "banks" class = "hidden"></div>
			</div>
			<div class = "row">
				<div class = "col-sm-11">
					 <table class="table table-bordered table-hover no-margin">
			             <thead>
			               <tr>
			                 <th class = "col-md-1">Д/д</th>
			                 <th class = "col-md-4">Барааны нэр</th>
			                 <th class = "col-md-1">Нэгж</th>
			                 <th class = "col-md-2">Тоо хэмжээ</th>
			                 <th class = "col-md-1">Нэгжийн үнэ төгрөг</th>
			                 <th class = "col-md-1">Бүгд үнэ төгрөг</th>
			               </tr>
			             </thead>
			             <tbody id="tableBody">
						<c:forEach items="${itemGroupList}" var="item" varStatus="status">
						<tr>
							<td>${status.index+1}</td>
							<td>${item.factureName}</td>
							<td>${item.unit}</td>
							<td><fmt:formatNumber type="number" value="${item.quantity}" pattern="###############.###" /></td>
							<td><fmt:formatNumber type="number" value="${item.price}" pattern="###############.###" /></td>
							<td><fmt:formatNumber type="number" value="${item.total}" pattern="###############.###" /></td>
							</tr>
		                </c:forEach>
		               <%--  <tr>
		                   <th >Бараа ажил, үйлчилгээний үнэ</th>
		                   <th ><fmt:formatNumber type="number" value="${allTotal}" pattern="###############.###" /></th>
		                 </tr>
		                  <tr>
		                   <th >Нэмэгдсэн өртгийн албан татвар</th>
		                   <th><fmt:formatNumber type="number" value="${allTotal/10}" pattern="###############.###" /></th>
		                 </tr>
		                  <tr>
		                   <th >Нийт үнэ</th>
		                   <th class = "col-md-1"><fmt:formatNumber type="number" value="${allTotal/10+allTotal}" pattern="###############.###" /></th>
		                   <th class="hidden"></th>
		                 </tr>   --%>             
		                </tbody>
		            </table>
				</div>
			</div>
			<div class = "row">
				<div class="col-sm-offset-8 col-sm-1" >
							Нийт дүн:
				</div>
				<div class="col-sm-1" >
							<fmt:formatNumber type="number" value="${allTotal/10+allTotal}" pattern="###############.###" />
				</div>
			</div>
			<footer class="footer">
	        	<div class="container">
		        	<div class="row center">
		          		<div class="col-xs-12 col-sm-offset-4 col-sm-4 center">
		            		&copy;2014  Infosystems LLC
		          		</div>
		          		<div id="time" class="col-xs-12 col-sm-4 center bigger-120 bolder dark">
		          		</div>
		        	</div>
		      	</div>
		      </footer>
        </div>
	<div class="bh-print-view" id = "printFacture_page" style="font-size: 14px">
			<div style="margin-top:2.2cm;margin-left: 2.2cm">
				<div style="margin-top:0cm;margin-left: 11.3cm;width: 20px;" id = "year"></div>
				<div style="margin-top:-0.25cm;margin-left: 12.5cm;width: 20px;" id = "month"></div>
				<div style="margin-top:-0.25cm;margin-left: 13.7cm;width: 30px;" id = "day"></div>
			</div>
			<div style="height: 1.5cm;margin-left: 2.2cm">
				<div style="margin-top:0.7cm">
					<div style="margin-top:0px;margin-left: 2cm;width: 5cm;">${company.name}</div>
					<div style="margin-top:-0.1cm;margin-left: 12.5cm;width: 5cm;" id = "saleNameShow"></div>
				</div>
				<div>
					<div style="margin-top:0.3cm;margin-left: 4cm;width: 3cm;" id = "buyNumberShow"></div>
					<div style="margin-top:-0.2cm;margin-left: 14.5cm;width: 3cm;" id = "saleNumberShow"></div>
				</div>
			</div>
			<div id="print-items" style="margin-top:0.4cm;height: 4.3cm;margin-left: 2.2cm" >
				<c:forEach items="${itemGroupList}" var="item" varStatus="status">
					<div style="margin-left: 1cm;width: 0.7cm;height: 0.5cm">${status.index+1}</div>
					<div style="margin-top:-0.5cm;margin-left: 1.7cm;width: 5cm;height: 0.5cm">${item.factureName}</div>
					<div style="margin:-0.5cm;margin-left: 6.7cm;width: 1.5cm;height: 0.5cm">
						${item.factureCode}
					</div>
					<div style="margin-top:-0.38cm;margin-left: 8.2cm;width: 1.5cm;height: 0.5cm">
						${item.unit}
					</div>
					<div style="margin-top:-0.5cm;margin-left: 9.7cm;width: 2.1cm;text-align: center">
						<fmt:formatNumber type="number" value="${item.quantity}" pattern="###############.###" />
					</div>
					<div style="margin-top:-0.25cm;margin-left: 11.8cm;width: 2.4cm;height: 0.5cm">
						<fmt:formatNumber type="number" value="${item.price}" pattern="###############.###" />
					</div>
					<div style="margin-top:-0.5cm;margin-left: 14.2cm;width: 2.5cm;height: 0.5cm">
						<fmt:formatNumber type="number" value="${item.total}" pattern="###############.###" />
					</div>
                </c:forEach>
			</div>
			<div style=";margin-left: 2.2cm">
				<div style="margin-top:0.8cm;">
					<div id="print-cal-total" style="margin-left: 14.2cm;width: 2.5cm;">
						<fmt:formatNumber type="number" value="${allTotal}" pattern="###############.###" />
					</div>
				</div>
				<div style="margin-top:0.2cm;">
					<div id="print-cal-total" style="margin-left: 14.2cm;width: 2.5cm;">
						<fmt:formatNumber type="number" value="${allTotal/10}" pattern="###############.###" />
					</div>
				</div> 
				<div style="margin-top:0.2cm;">
					<div  id="print-cal-total" style="margin-left: 14.2cm;width: 2.5cm">
						<fmt:formatNumber type="number" value="${allTotal/10+allTotal}" pattern="###############.###" />
					</div>
				</div> 
			</div>
		</div>
		<div id = "logoutDialog">
		  		<div class="center">
		  			<b>Итгэлтэй байна уу?</b>
		  		</div>
		</div>
	</body>
	
</html>