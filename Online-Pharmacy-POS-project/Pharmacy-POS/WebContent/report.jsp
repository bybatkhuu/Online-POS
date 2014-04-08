<%@ page import="utils.LoggedUser"%>
<%@ page import="models.Company"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	int status = LoggedUser.checkLogin(session);
	if (status != 1)
	{
		if (status == 0)
		{
			response.sendRedirect("login.jsp");
		}
		else
		{
			response.sendRedirect("login.jsp?message=" + status);
		}
	}
%>
<!DOCTYPE html>
<html lang="mn">
	<head>
		<meta charset="utf-8" />
	    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
	    <meta name="description" content="Infosystems LLC - Online POS: Main page">
	    <meta name="keywords" content="Infosystems POS, Online POS, POS, Infosystems LLC, Infosystems">
	    <meta name="author" content="Infosystems LLC">
	    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=0"/>
	    <meta name="apple-mobile-web-app-capable" content="yes" />
		<meta name="apple-mobile-web-app-status-bar-style" content="black" />
	
	    <title>Тайлан - [${company.name} ХХК]</title>
	    
	    <link rel="icon" type="image/x-icon" href="images/favicon.ico" />
	    <link rel="apple-touch-icon" href="images/Logo.png">
	    <link rel="apple-touch-startup-image" href="images/Logo.png">
	    
	    <link rel="stylesheet" href="css/bootstrap.min.css" />
	    <link rel="stylesheet" href="css/font-awesome.min.css" />
	    <link rel="stylesheet" href="css/jquery-ui-1.10.3.full.min.css" />
	    <link rel="stylesheet" href="css/daterangepicker.css" />
	    <link rel="stylesheet" href="css/ace-fonts.css" />
	    <link rel="stylesheet" href="css/ace.min.css" />
	    <link rel="stylesheet" href="css/ace-skins.min.css" />
	    
	    <link rel="stylesheet" href="css/custom-icons.css" />
	    <link rel="stylesheet" href="css/style.css" />
	    <style type="text/css">
	    
	    </style>
	    
	    <script type="text/javascript" src="js/jquery-1.10.2.min.js"></script>
	    <script type="text/javascript" src="js/bootstrap.min.js"></script>
	    <script type="text/javascript" src="js/jquery-ui-1.10.3.full.min.js"></script>
	    <script type="text/javascript" src="js/date-time/moment.min.js"></script>
		<script type="text/javascript" src="js/date-time/daterangepicker.min.js"></script>
	    <script type="text/javascript" src="js/ace-elements.min.js"></script>
	    <script type="text/javascript" src="js/ace.min.js"></script>
	    
	    <script type="text/javascript">
	      $(document).ready(function()
	      {
	    	  $('#dateRangePicker').daterangepicker
	    		(
	    			{
	    				startDate: moment().startOf('month'),
	    				endDate: moment(),
	    				format: 'YYYY-MM-DD',
	    				showDropdowns: true,
	    				ranges:
	    				{
	    					'Өнөөдөр': [moment(), moment()],
	    					/* 'Өчигдөр': [moment().subtract('days', 1), moment().subtract('days', 1)], */
	    					'Сүүлийн 7 хоног': [moment().subtract('days', 6), moment()],
	    					/* 'Сүүлийн 30 хоног': [moment().subtract('days', 29), moment()], */
	    					'Энэ сар': [moment().startOf('month'), moment().endOf('month')],
	    					'Сүүлийн сар': [moment().subtract('month', 1).startOf('month'), moment().subtract('month', 1).endOf('month')]
	    				},
	    				locale:
	    				{
	    					applyLabel: 'Сонгох',
	    					cancelLabel: 'Цуцлах',
	    					fromLabel: 'Эхлэх',
	    					toLabel: 'Дуусах',
	    					customRangeLabel: 'Гараар сонгох',
	    					daysOfWeek: ['Ня', 'Да', 'Мя', 'Лх', 'Пү', 'Ба','Бя'],
	    					monthNames: ['1 сар', '2 сар', '3 сар', '4 сар', '5 сар', '6 сар', '7 сар', '8 сар', '9 сар', '10 сар', '11 сар', '12 сар']
	    				}
	    			},
	    			function(start, end, label)
	    			{
	    				$('#firstDate').val(start.format('YYYY-MM-DD'));
	    				$('#secondDate').val(end.format('YYYY-MM-DD'));
	    			}
	    		);
	    		$('#firstDate').on('apply.daterangepicker', function(ev, picker)
	    		{
	    			$('#firstDate').val(picker.startDate.format('YYYY-MM-DD'));
	    			$('#secondDate').val(picker.endDate.format('YYYY-MM-DD'));
	    		});
	    		$('#firstDate').on('cancel.daterangepicker', function(ev, picker)
	    		{
	    			$('#firstDate').val('');
	    			$('#secondDate').val('');
	    		});
	      });
	    </script>
	</head>
	<body>
		<header class="navbar navbar-default" id="navbar">
	      <div class="navbar-container container" id="navbar-container">
	        <div class="navbar-header pull-left">
	          <a href="index.jsp" class="navbar-brand">
	            <small>
	              <i class="icon-medkit"></i>
	              Инфосистемс POS
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
	    
	    <div class="main-container container" id="main-container">
      		<div class="main-container-inner">
        		<div class="main-content bh-main-content">
        			<div class="page-content">
        				<div class="page-header">
        					<h1>Тайлан</h1>
        				</div>
        				<div class="row">
        					<div class="col-sm-12">
        						<form class="form-horizontal">
        							<div class="row">
        								<div class="col-sm-12">
        									<label class="control-label" for="dateRangePicker">
        										Огноо:
        									</label>
        								</div>
        							</div>
        							<div class="row">
        								<div class="col-sm-12">
        									<input type="text" name="date" class="form-control" id="dateRangePicker" />
        								</div>
        							</div>
        						</form>
        					</div>
        				</div>
        				<div class="space-8"></div>
			            <div class="row">
			              	<div class="col-sm-12">
			              		<div class="table-responsive">
			              			<table class="table table-striped table-bordered table-hover">
			              				<thead>
			              					<tr>
			              						<th>Date</th>
			              						<th></th>
			              					</tr>
			              				</thead>
			              				<tbody>
			              					<tr>
			              						<td></td>
			              						<td></td>
			              					</tr>
			              				</tbody>
			              			</table>
			              		</div>
			          		</div>
			          	</div>
			          	
			        </div>
        		</div>
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
	</body>
</html>