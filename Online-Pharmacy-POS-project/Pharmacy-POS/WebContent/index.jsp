<%@ page import="java.util.List"%>
<%@ page import="models.User"%>
<%@ page import="models.Item"%>
<%@ page import="utils.LoggedUser"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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
    <meta name="description" content="Infosystems LLC - Online PO Main page">
    <meta name="keywords" content="Infosystems POS, Online POS, POS, Infosystems LLC, Infosystems">
    <meta name="author" content="Infosystems LLC">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=0"/>
    <meta name="apple-mobile-web-app-capable" content="yes" />
	<meta name="apple-mobile-web-app-status-bar-style" content="black" />

    <title>Нүүр хуудас - [${company.name} ХХК]</title>
    
   <link rel="icon" type="image/x-icon" href="images/favicon.ico" />
    <link rel="apple-touch-icon" href="images/Logo.png">
    <link rel="apple-touch-startup-image" href="images/Logo.png">
    
    <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css" />
    <link rel="stylesheet" type="text/css" href="css/font-awesome.min.css" />
    <link rel="stylesheet" type="text/css" href="css/jquery-ui-1.10.3.full.min.css" />
    <link rel="stylesheet" type="text/css" href="css/select2.css" />
    <link rel="stylesheet" type="text/css" href="css/ace-fonts.css" />
    <link rel="stylesheet" type="text/css" href="css/ace.min.css" />
    <link rel="stylesheet" type="text/css" href="css/ace-skins.min.css" />
    
    <link rel="stylesheet" type="text/css" href="css/custom-icons.css" />
    <link rel="stylesheet" type="text/css" href="css/style.css" />
    <style type="text/css">
    
    </style>
   
	<script type="text/javascript" src="js/jquery-2.1.1.min.js"></script>
	 <script type="text/javascript" src="js/bootstrapValidator.min.js"></script>
    <script type="text/javascript" src="js/bootstrap.min.js"></script>
    <script type="text/javascript" src="js/jquery-ui-1.10.3.full.min.js"></script>
    <script type="text/javascript" src="js/jquery.slimscroll.min.js"></script>
    <script type="text/javascript" src="js/select2.min.js"></script>
    <script type="text/javascript" src="js/ace-elements.min.js"></script>
    <script type="text/javascript" src="js/ace.min.js"></script>
    <script type="text/javascript" src="js/ace-extra.min.js"></script>
     <script type="text/javascript" src="js/page-js/cash.js"></script>
     <script type="text/javascript" src="js/jquery.jkey.min.js"></script>

    
    <script type="text/javascript">
      $(document).ready(function()
      {
    	  
      });
    </script>
  </head>
  <body>
  	<div class="bh-non-print">
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
                  <a href="report.jsp">
                    <i class="icon-bar-chart"></i>
                    Тайлан
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
    
    <%-- <jsp:useBean id="sessionBean" class="models.Company" scope="session" /> --%>
    <div class="main-container container" id="main-container">
      <div class="main-container-inner">
        <div class="main-content bh-main-content">
          <div class="page-content bh-no-bottom-padding">
            <div class="row">
              <div class="col-xs-12 col-sm-4">
                <div class="row">
                  <div class="col-xs-12 col-sm-12">
                    <div class="widget-box bh-widget-box">
                      <div class="widget-body">
                        <div class="widget-main">
                          <form method="POST" class="form-horizontal">
                            <div class="form-group">
                              <label for="talon" class="control-label col-xs-12 col-sm-5 col-md-3 no-padding-right">Талон №:</label>
                              <div class="col-xs-12 col-sm-7 col-md-9">
                                <div class="input-group">
                                  <input type="text" name="talon" value="" class="col-xs-12 col-sm-12 input-sm bolder dark bh-bg-color-yellow bh-input-skin-1 bh-font-size-16" id="talon" tabindex="3"/>
                                  <span class="input-group-addon input-sm">
                                    <i class="bhicon bhicon-receipt"></i>
                                  </span>
                                </div>
                              </div>
                            </div>
                            <div class="form-group">
                              <label for="cashier" class="control-label col-xs-12 col-sm-5 col-md-3 no-padding-right">Кассчин:</label>
                              <div class="col-xs-12 col-sm-7 col-md-9">
                                <div class="input-group">
                                  <input type="text" name="cashier" value="${user.cashName}" class="col-xs-12 col-sm-12 input-sm bolder dark bh-input-skin-1 bh-font-size-14" id="cashier" disabled />
                                  <span class="input-group-addon input-sm">
                                      <i class="bhicon bhicon-cash bh-icon-size-15"></i>
                                  </span>
                                </div>
                              </div>
                            </div>
                            <div class="form-group">
                              <label for="pos" class="control-label col-xs-12 col-sm-5 col-md-3 no-padding-right">POS №:</label>
                              <div class="col-xs-12 col-sm-7 col-md-9">
                                <div class="input-group">
                                  <input type="text" name="pos" value="${cash.posNum}" class="col-xs-12 col-sm-12 input-sm bolder dark bh-input-skin-1 bh-font-size-14" id="pos" pattern="[0-9]{1,3}" disabled />
                                  <span class="input-group-addon input-sm">
                                      <i class="bhicon bhicon-cd-software bh-icon-size-11"></i>
                                  </span>
                                </div>
                              </div>
                            </div>
                          </form>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="row">
                  <div class="col-xs-12 col-sm-12">
                    <div class="widget-box bh-widget-box bh-no-top-border">
                      <div class="widget-body">
                        <div class="widget-main">
                          <form method="POST">
                          	<input type="hidden" value="${cash.assetType}" id="assetType" />
                          	<c:choose>
                          		<c:when test="${cash.assetType == 'Select'}">
                          			<div class="row">
		                          		<div class="col-xs-12 col-sm-12">
		                          			<label for="assetAccounts">
		                          				<b>Барааны тасаг:</b>
		                          			</label>
		                          		</div>
		                          	</div>
                          			<div class="row">
		                          		<div class="col-xs-12 col-sm-12">
		                          			<select id="assetAccounts" class="width-100">
		                          			</select>
		                          		</div>
		                          	</div>
                          		</c:when>
                          		<c:otherwise>
                          			<div class="row">
		                          		<div class="col-xs-6 col-sm-6">
		                          			<label for="assetAccounts">
		                          				<b>Барааны тасаг:</b>
		                          			</label>
		                          		</div>
		                          		<div class="col-xs-6 col-sm-6">
		                          			<label class="width-100">
		                          			${cash.assetAcc}
		                          			</label>
		                          		</div>
		                          	</div>
                          			<select id="assetAccounts" class="hidden">
                          				<option value="${cash.assetAcc}" selected>${cash.assetAcc}</option>
                          			</select>
                          		</c:otherwise>
                          	</c:choose>
                            <div class="row">
                              <div class="col-xs-12 col-sm-4 no-padding">
                                <div class="form-group">
                                  <label for="quantity" class="col-xs-12">
                                    <b>Тоо:</b>
                                  </label>
                                  <div class="input-group col-xs-12">
                                    <input type="text" name="quantity" value="<%
                                    		DecimalFormat format = new DecimalFormat("###############.##");
                                    		if (session.getAttribute("itemList") != null)
                                    		{
                                    			@SuppressWarnings("unchecked")
                                      			List<Item> itemList = (List<Item>) session.getAttribute("itemList");
                                      			if (itemList != null && !itemList.isEmpty()) 
                                    	    	{
                                      				Item item = itemList.get(itemList.size() - 1);
                                      				Double result = item.getQuantity() - (int)(item.getQuantity());
    	                              				if (result != 0)
    	                              				{
    	                              					out.println(item.getQuantity());
    	                              				}
    	                              				else
    	                              				{
    	                              					out.println((int)item.getQuantity());
    	                              				}
                                    	    	}
                                      			else
                                      			{
                                      				out.print("1");
                                      			}
                                    		}
                                    		else
                                    		{
                                    			out.print("1");
                                    		}
                                    %>" class="form-control text-right bolder dark bh-input-skin-1" id="quantity" pattern="[0-9]{1,9}" tabindex="2" autocomplete="off" />
                                    <span class="input-group-addon bh-padding-6" style="font-size: 11px;" id="unit">
                                    	<%
                                    		if (session.getAttribute("itemList") != null)
                                    		{
                                    			@SuppressWarnings("unchecked")
                                      			List<Item> itemList = (List<Item>) session.getAttribute("itemList");
                                      			if (itemList != null && !itemList.isEmpty())
                                    	    	{
                                      				out.print(itemList.get(itemList.size() - 1).getUnit());
                                    	    	}
                                      			else
                                        		{
                                        			out.print("ш");
                                        		}
                                    		}
                                    		else
                                    		{
                                    			out.print("ш");
                                    		}
                                    	%>
                                    </span>
                                  </div>
                                </div>
                              </div>
                              <div class="col-xs-12 col-sm-8 no-padding">
                                <div class="form-group">
                                  <label for="barcode" class="col-xs-12">
                                    <b>Бар код:</b>
                                  </label>
                                  <div class="input-group col-xs-12">
                                    <input type="text" name="barcode" class="form-control bolder dark bh-input-skin-1" id="barcode" pattern="[A-Za-z0-9]{2,32}" title="F3" tabindex="1" autocomplete="off" autofocus />
                                    <span class="input-group-addon">
                                      <i class="icon-barcode"></i>
                                    </span>
                                  </div>
                                </div>
                              </div>
                            </div>
                            <div class="space-4"></div>
                            <div class="row">
	                              <div class="form-group">
	                                <label for="serial" class="col-xs-12 col-sm-4">
	                                  <b>Серийн №:</b>
	                                </label>
	                                <div class="col-xs-12 col-sm-8 no-padding">
	                                  <div class="input-group col-xs-12">
	                                    <input type="text" name="serial" class="form-control dark bh-input-skin-1" id="serial" tabindex="3" title="F4" autocomplete="off" />
	                                    <span class="input-group-addon">
	                                      <i class="bhicon bhicon-product bh-icon-size-15-5"></i>
	                                    </span>
	                                  </div>
	                                </div>
	                              </div>
                            </div>

                            <div class="space-4"></div>
                            <div id = "editPriceDiv" class = "hidden">
	                            <div class="row ">
	                              <div class="form-group">
	                                <label for="itemName" class="col-xs-12 col-sm-5">
	                                	Барааны нэр:
	                                </label>
	                                <div class="col-xs-12 col-sm-7">
	                                  <input type="text" name="itemName" value="<%
	                                    		if (session.getAttribute("itemList") != null)
	                                    		{
	                                    			@SuppressWarnings("unchecked")
	                                      			List<Item> itemList = (List<Item>) session.getAttribute("itemList");
	                                      			if (itemList != null && !itemList.isEmpty())
	                                    	    	{
	                                      				out.print(itemList.get(itemList.size() - 1).getName());
	                                    	    	}
	                                    		}
	                                    	%>" class="form-control input-sm dark text-right bh-input-skin-1" id="itemName" disabled />
	                                </div>
	                              </div>
	                            </div>
	                            <div class="row">
	                              <div class="form-group">
	                                <label for="unitPrice" class="col-xs-12 col-sm-5">
	                                	Үнэ:
	                                </label>
	                                <div class="col-xs-12 col-sm-7">
	                                  <input type="text" name="unitPrice" value="<%
	                                    		if (session.getAttribute("itemList") != null)
	                                    		{
	                                    			@SuppressWarnings("unchecked")
	                                      			List<Item> itemList = (List<Item>) session.getAttribute("itemList");
	                                      			if (itemList != null && !itemList.isEmpty())
	                                    	    	{
	                                      				Item item = itemList.get(itemList.size() - 1);
	    	                              				out.println(format.format(item.getPrice()));
	                                    	    	}
	                                      			else
	                                        		{
	                                        			out.print("0");
	                                        		}
	                                    		}
	                                    		else
	                                    		{
	                                    			out.print("0");
	                                    		}
	                                    	%>" class="form-control input-sm dark text-right bh-input-skin-1" id="unitPrice" disabled />
	                                </div>
	                              </div>
	                            </div>
                            </div>
                            <div class="row">
                             	<div class="col-sm-6">
                             		<div class="checkbox no-padding">
			                        	<label>
			                            	<input type="checkbox" class="ace ace-switch ace-switch-6" title = "Ctrl+1" tabindex="-1" id="customerCheck" />
			                                <span class="lbl">
			                                	<b>&nbsp;Нэхэмжлэх</b>
			                                </span>
			                            </label>
			                    	</div>
			                    </div>
       							<div class="col-sm-6">
                             		<select class="width-100" style="margin-top: 3px !important" id="customers" disabled>
                             		</select>
                             	</div>
                             </div>
                             <div class="row">
                             	<div class="col-sm-6">
                             		<div class="checkbox no-padding">
			                        	<label>
			                            	<input type="checkbox" class="ace ace-switch ace-switch-6" title = "Ctrl+2" tabindex="-1" id="bankCheck" />
			                                <span class="lbl">
			                                	<b>&nbsp; Банк </b>
			                                </span>
			                            </label>
			                    	</div>
			                    </div>
       							<div class="col-sm-6">
                             		<select class="width-100" style="margin-top: 3px !important" id="banks" disabled>
                             		</select>
                             	</div>
                             </div>
                              <div class="row" >
                             	<div class="col-xs-12 col-sm-12 col-md-12 pull-left">
	                                <div class="checkbox no-padding">
	                                  <label>
	                                    <input type="checkbox" class="ace ace-switch ace-switch-6" title="F11" tabindex="-1" id="insuranceCheck"/>
	                                    <span class="lbl">
	                                      <b>&nbsp; ЭМДС-ийн хөнгөлөлт</b>
	                                    </span>
	                                  </label>
	                                </div>
	                         	</div>
	                         </div>
							<div class="row" id = "cardDiv">
								<div class="col-sm-12">
									<div class="widget-box bh-widget-box">
                            			<div class="widget-body">
                              				<div class="widget-main" style="background-color: #EEE;">
                              					<div class="row">
                              						<div class="col-sm-4">
                              							<label for="cardNumber">Карт №: </label>
                              						</div>
                              						<div class="col-sm-8">
                              							<input type="text" name="cardNumber" title = "Ctrl+3" value="${card.cardNumber}" class="form-control input-sm bolder dark bh-input-skin-1" id="cardNumber" tabindex="4"/>
                              						</div>
                              					</div>
                              					<div class="row">
                              						<div class="col-sm-4">
                              							<label for="cardOwner">Эзэмшигч: </label>
                              						</div>
                              						<div class="col-sm-8">
                              							<div id="cardOwner" class="bolder">${card.customer.name}</div>
                              						</div>
                              					</div>
                              					<div class="hr-2 hr-double dotted"></div>
                              					<div class="row">
                              						<div class="col-sm-4">
                              							<label for="discountPercent">Хөнгөлөлт: </label>
                              						</div>
                              						<div class="col-sm-3">
                              							<span class="input-icon input-icon-right">
                              								<input type="text" name="discountPercent"
                              									value="<fmt:formatNumber type="number" value="${discountPercent}" pattern="###############.###" />"
                              									class="form-control input-sm bolder dark bh-input-skin-1" pattern="[0-9]{1,9}" id="discountPercent" disabled />
                              							</span>
                              						</div>
                              						<div class="col-sm-5">
                              							<input type="text" name="discountType" value="${card.type}" class="form-control input-sm bolder dark bh-input-skin-1" id="discountType" disabled />
                              						</div>
                              					</div>
                              				</div>
                              			</div>
                              		</div>
								</div>
							</div>
                          </form>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>

              <div class="col-xs-12 col-sm-8">
                <div class="row">
                  <div class="col-xs-12 col-sm-12">
                    <div class="widget-box bh-widget-box">
                      <div class="widget-body">
                      	<div class="table-responsive">
                          <div id="mainTableSlim">
                            <table class="table table-bordered table-hover no-margin" id="mainTable">
                              <thead>
                                <tr>
                                  <th class = "col-md-5">Барааны нэр</th>
                                  <th class = "col-md-1">Тоо</th>
                                  <th class = "col-md-1">Нэгж</th>
                                  <th class = "col-md-2">Нэгж үнэ</th>
                                  <th class = "col-md-2">Нийт дүн</th>
                                  <th class = "col-md-1">Хөн %</th>
                                  <th class="hidden">Хөнгөлөлт</th>
                                  <!-- <th style="width: 50%;">Барааны нэр</th>
                                  <th style="width: 5%;">Тоо</th>
                                  <th style="width: 5%;">Нэгж</th>
                                  <th style="width: 11%;">Нэгж үнэ</th>
                                  <th style="width: 20%;">Нийт дүн</th>
                                  <th style="width: 9%;">Хөн%</th>
                                  <th class="hidden">Хөнгөлөлт</th> -->
                                </tr>
                              </thead>
                              <tbody id="tableBody">
                              	<c:forEach items="${itemList}" var="item" varStatus="status">
                              		<tr <c:if test="${status.last}">class="success"</c:if> id="${item.id}">
                              			<td>${item.name}</td>
                              			<td class="text-right">
                              				<fmt:formatNumber type="number" value="${item.quantity}" pattern="###############.###" />
                              			</td>
                              			<td>${item.unit}</td>
                              			<td class="text-right">
                              				<fmt:formatNumber type="number" value="${item.price}" pattern="###############.###" />
                              			</td>
                              			<td class="text-right">
                              				<fmt:formatNumber type="number" value="${item.total}" pattern="###############.###" />
                              			</td>
                              			<td class="text-right">
                              				<fmt:formatNumber type="number" value="${item.discountPercent}" pattern="###.##" />
                              			</td>
                              			<td class="hidden discountTotal">
                              				<fmt:formatNumber type="number" value="${item.discountTotal}" pattern="###############.###" />
                              			</td>
                              		</tr>
                              	</c:forEach>
                              </tbody>
                            </table>
                            <div id="pager">
                            
                            </div>
                          </div>

                          <div class="widget-box bh-widget-box">
                            <div class="widget-body bh-no-bottom-border bh-no-right-border bh-no-left-border">
                              <div class="widget-main">
                                <form method="POST">
                                  <div class="row">
                                    <div class="col-xs-12 col-sm-4 col-md-3">
                                      <input type="text" name="tableTotal" value="Нийт: 0" class="form-control input-sm bh-input-skin-1" id="tableTotal" disabled />
                                    </div>
                                    <div class="col-xs-6 col-sm-4 col-md-2">
                                      <button class="col-xs-12 btn btn-sm btn-warning" title="F5" tabindex="-1" id="updateButton">
		                                <i class="icon-edit"></i> Засах
		                              </button>
                                    </div>
                                    <div class="col-xs-6 col-sm-4 col-md-2">
                                      <button class="col-xs-12 btn btn-sm btn-danger" tabindex="-1" id="deleteButton">
		                                <i class="icon-minus"></i> Хасах
		                              </button>
                                    </div>
                                    <div class="col-xs-12 col-sm-offset-4 col-sm-4 col-md-offset-0 col-md-2">
                                      <button class="col-xs-12 btn btn-sm" tabindex="-1" id="clearButton">
		                                <i class="icon-trash"></i> Цэвэрлэх
		                              </button>
                                    </div>
                                    <div class="col-xs-12 col-sm-4 col-md-3">
		                              <button class="col-xs-12 btn btn-sm btn-success" tabindex="5" id="payButton">
		                                <i class="icon-money"></i> Төлбөр төлөх
		                              </button>
                                    </div>
                                  </div>
                                </form>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>

                <div class="row">
                  <div class="col-xs-12 col-sm-12">
                    <div class="widget-box bh-widget-box bh-no-top-border">
                      <div class="widget-body">
                        <div class="widget-main">
                          <form method="POST" class="form-horizontal">
                            <div class="row">
                              <div class="col-xs-12 col-sm-6">
                                <div class="form-group">
                                  <label for="calTotal" class="col-xs-12 col-sm-6 control-label no-padding-right">
                                    <b>Нийт дүн:</b>
                                  </label>
                                  <div class="col-xs-12 col-sm-6">
                                  	<c:set var="allTotal" value="0" scope="page" />
                                  	<c:forEach items="${itemList}" var="item" varStatus="status">
                                		<c:set var="allTotal" value="${allTotal + item.total}" />
                                  	</c:forEach>
                                    <input type="text" name="calTotal"
                                    	value="<fmt:formatNumber type="number" value="${allTotal}" pattern="###############.###" />"
                                    	class="form-control input-sm bolder dark text-right bh-input-skin-1 bh-input-bg-color-1" id="calTotal" disabled />
                                  </div>
                                </div>
	                            <div>
                                	<div class="form-group">
                                		<label for="discountTotal" class="col-xs-12 col-sm-6 control-label no-padding-right">
                                			<b>Хөнгөлөлт:</b>
                                		</label>
                                		<div class="col-xs-12 col-sm-6">
                                			<c:set var="allDisTotal" value="0" scope="page" />
		                                  	<c:forEach items="${itemList}" var="item" varStatus="status">
		                                  		<c:set var="allDisTotal" value="${allDisTotal + item.discountTotal}" />
		                                  	</c:forEach>
                                			<input type="text" name="discountTotal"
                                				value="<fmt:formatNumber type="number" value="${allDisTotal}" pattern="###############.###" />"
                                				class="form-control input-sm bolder dark text-right bh-input-skin-1 bh-input-bg-color-1" id="discountTotal" disabled />
                                		</div>
                                	</div>
                                </div>
                                 <div class="form-group hidden" id = "calSaleEMDSdiv">
	                                  <label for="calSaleEMDS" class="col-xs-12 col-sm-6 control-label no-padding-right">
	                                    <b>Хөнгөлөлт (ЭМДС-аас):</b>
	                                  </label>
	                                  <div class="col-xs-12 col-sm-6">
	                                	 <input type="text" name="calSaleEMDS" class="form-control input-sm bolder dark text-right bh-input-skin-1 bh-input-bg-color-1" id="calSaleEMDS" disabled />
	                                  </div>
	                                </div>
                              </div>

                              <div class="col-xs-12 col-sm-6">
                                <div class="form-group has-error">
                                  <label for="payOff" class="col-xs-12 col-sm-5 control-label no-padding-right">
                                    <b>Төлөх:</b>
                                  </label>
                                  <div class="col-xs-12 col-sm-7">
                                      <input type="text" name="payOff" value="0" class="form-control input-sm bolder dark text-right bh-font-size-18" id="payOff" disabled />
                                  </div>
                                </div>
                                <div class="form-group has-success">
                                  <label for="paid" class="col-xs-12 col-sm-5 control-label no-padding-right">
                                    <b>Төлсөн:</b>
                                  </label>
                                  <div class="col-xs-12 col-sm-7">
                                      <input type="text" name="paid" value="0" title="F7" class="form-control input-sm bolder dark text-right bh-font-size-16 bh-input-skin-2" id="paid" tabindex="5" />
                                  </div>
                                </div>
                                <div class="form-group has-warning">
                                  <label for="return" class="col-xs-12 col-sm-5 control-label no-padding-right">
                                    <b>Хариулт:</b>
                                  </label>
                                  <div class="col-xs-12 col-sm-7">
                                      <input type="text" name="return" value="0" class="form-control input-sm bolder dark text-right bh-font-size-16" id="return" disabled />
                                  </div>
                                </div>
                              </div>
                            </div>
                          </form>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>

            <div class="row">
              <div class="blue center" style="width:9%;float: left;">
                Хөнгөлөлт <b class="dark">F2</b>
              </div>
              <div class="green center" style="width:9%;float: left;">
                Баркод <b class="dark">F3</b>
              </div>
               <div class="green center" style="width:10%;float: left;">
                Сери өөрчлөх <b class="dark">F4</b>
              </div> 
              <div class=" blue center" style="width:9%;float: left;">
                Тоо засах <b class="dark">F5</b>
              </div>
              <div class="blue center" style="width:9%;float: left;">
                Хэвлэх <b class="dark">F6</b>
              </div>
              <div class=" blue center" style="width:9%;float: left;">
                Харуилт <b class="dark">F7</b>
              </div>
              <div class=" blue center" style="width:6%;float: left;">
                Тайлан <b class="dark">F8</b>
              </div>
              <div class=" red center" style="width:9%;float: left;">
                Үнэ засах <b class="dark">F9</b>
              </div>
              <div class=" blue center" style="width:15%;float: left;">
               Хөнгөлөлт устгах <b class="dark">F10</b>
              </div>
              <div class=" blue center" style="width:15%;float: left;">
               Үнэ өөрчлөх <b class="dark">F12</b>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <footer class="footer">
      <div class="container">
        <div class="row center">
          <div class="col-xs-12 col-sm-4 text-muted center">
            <small>1.Барааны хайлт=(Ctrl+F)</small> <small class="hidden">2.Тохиргоо=(Ctrl+N) 3.Талоны загвар=(Ctrl+M)</small>
          </div>
          <div class="col-xs-12 col-sm-4 center">
            &copy;2014  Infosystems LLC
          </div>
          <div id="time" class="col-xs-12 col-sm-4 center bigger-120 bolder dark">
          </div>
        </div>
      </div>
    </footer>
    
    <div id="loadingDialog" title="Мэдээлэл">
    	<br>
    	<div class="center">
    		<img src="images/ajax-loader.gif" />
    	</div><br>
  		Таны үйлдлийг сервер лүү ачааллаж байна.<br>
  		<div class="center">
  			<b>Түр хүлээнэ үү!</b>
  		</div>
	</div>
	
	<div id="errorDialog">
		<div class="alert alert-danger">
			<p id="errorMessage">
				<strong>
	    			<i class="icon-remove"></i>
	    		</strong>
	    		Серверт алдаа гарсан эсвэл сервертэй холболт тасарсан байна!
    		</p>
    	</div>
    	<div class="alert alert-info">
    		<b>Алдааг засах: </b>
    		<ul>
    			<li>
    				Тухайн веб хуудсыг дахин дуудаж сервертэй дахин холбогдох.
    			</li>
    			<li>
    				Сервер унтарсан эсвэл сүлжээний холболт салсан эсэхийг шалгах.
    			</li>
    			<li>
    				Алдааны мэдээлэл арилахгүй байвал яааралтай системийн админтай холбогдох.
    			</li>
    		</ul>
    	</div>
	</div>
	<div id="cardUsersDialog" title="Картын хэрэглэгчид">
		<div class="row">
			<div class="col-sm-12">
				<table class="table table-bordered">
					<thead>
						<tr>
							<th>Хэрэглэгч</th>
							<th>Картын дугаар</th>
							<th>Ангилал</th>
							<th>Хөнгөлөлт %</th>
							<th>Хамтран эзэмшигч</th>
						</tr>
					</thead>
					<tbody id="cardUsersBody">
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<div id="searchItemsDialog" title="Бараа хайх">
		<div class="col-sm-12">
			<div class="row">
				<form action="#" method="POST">
					<div class="row">
						<div class="col-sm-6">
							<label for="searchByName" class="control-label">
								<b>Нэр:</b>
							</label>
						</div>
						<div class="col-sm-6">
							<label for="searchByBarcode" class="control-label">
								<b>Баркод:</b>
							</label>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-6">
							<input type="text" class="form-control input-sm" title = "F1" id="searchByName" />
						</div>
						<div class="col-sm-6">
							<input type="text" class="form-control input-sm" title = "F3" id="searchByBarcode" />
						</div>
					</div>
					<div class="space-8"></div>
					<div class="row">
						<div class="col-sm-1">
							<label for="searchByPrice" class="control-label" style="padding-top: 5px;">
								<b>Үнэ:</b>
							</label>
						</div>
						<div class="col-sm-2 no-padding">
							<input type="text" class="form-control text-center green" title = "ctrl+1" id="searchByMinPrice" style="border: 0px; font-weight:bold;" />
						</div>
						<div class="col-sm-1 no-padding">
							<input type="text" class="form-control text-center green" value="-" title = "ctrl+2" style="border: 0px; font-weight:bold;" readonly disabled />
						</div>
						<div class="col-sm-2 no-padding">
							<input type="text" class="form-control text-center green" id="searchByMaxPrice" style="border: 0px; font-weight:bold;" />
						</div>
						<div class="col-sm-4">
							<button type="submit" class="btn btn-sm btn-success pull-right" title = "F9" id="selectItems">
								Сонгох
								<i class="icon-check"></i>
							</button>
						</div>
						<div class="col-sm-2">
							<button type="submit" class="btn btn-sm btn-success pull-right" id="searchItems" title = "Enter">
								Хайх
								<i class="icon-search"></i>
							</button>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-6">
							<div id="slider-range"></div>
						</div>
					</div>
				</form>
			</div>
			<br>
			<div class="row">
				<div class="col-sm-12 no-padding">
					<table class="table table-bordered">
						<thead>
							<tr>
								<th>Нэр</th>
								<th>Баркод</th>
								<th>Сери</th>
								<th>Үнэ</th>
								<c:if test="${cash.assetType == 'Select'}">
									<th>Барааны тасаг</th>
								</c:if>
							</tr>
						</thead>
						<tbody id="searchResultBody">
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
	<div id = "helpDialog">
		<div class = "row">
			<div class = "col-xs-3">Товч</div>
			<div class = "col-xs-4">Энгийн</div>
			<div class = "col-xs-5">Бараа хайх</div>
		</div>
		<div class = "row">
			<div class = "col-xs-3">F2:</div>
			<div class = "col-xs-4">Хөнгөлөлт</div>
			<div class = "col-xs-5">Нэр</div>
		</div>
		<div class = "row">
			<div class = "col-xs-3">F3:</div>
			<div class = "col-xs-4">Баркод</div>
			<div class = "col-xs-5">Баркод</div>
		</div>
		<div class = "row">
			<div class = "col-xs-3">F4:</div>
			<div class = "col-xs-4">Ceри өөрчлөх</div>
			<div class = "col-xs-5"></div>
		</div>
		<div class = "row">
			<div class = "col-xs-3">F5:</div>
			<div class = "col-xs-4">Тоо засах</div>
			<div class = "col-xs-5"></div>
		</div>
		<div class = "row">
			<div class = "col-xs-3">F6:</div>
			<div class = "col-xs-4">Хэвлэх </div>
			<div class = "col-xs-5"></div>
		</div>
		<div class = "row">
			<div class = "col-xs-3">F7:</div>
			<div class = "col-xs-4">Харуилт</div>
			<div class = "col-xs-5"></div>
		</div>
		<div class = "row">
			<div class = "col-xs-3">F8:</div>
			<div class = "col-xs-4">Тайлан</div>
			<div class = "col-xs-5"></div>
		</div>
		<div class = "row">
			<div class = "col-xs-3">F9:</div>
			<div class = "col-xs-4">Үнэ засах</div>
			<div class = "col-xs-5">Бараа сонгох</div>
		</div>
		<div class = "row">
			<div class = "col-xs-3">F10:</div>
			<div class = "col-xs-4">Хөнгөлөлт устгах</div>
			<div class = "col-xs-5"></div>
		</div>
		<div class = "row">
			<div class = "col-xs-3">F11:</div>
			<div class = "col-xs-4">ЭМДС-ийн хөнгөлөлт</div>
			<div class = "col-xs-5"></div>
		</div>
		<div class = "row hidden">
			<div class = "col-xs-3">Delete:</div>
			<div class = "col-xs-4">Хасах</div>
			<div class = "col-xs-5"></div>
		</div>
		<div class = "row">
			<div class = "col-xs-3">Ctrl+1:</div>
			<div class = "col-xs-4">Нэхэмжлэх</div>
			<div class = "col-xs-5">Хамгийн бага үнэ</div>
		</div>
		<div class = "row">
			<div class = "col-xs-3">Ctrl+2:</div>
			<div class = "col-xs-4">Банк</div>
			<div class = "col-xs-5">Хамгийн их үнэ</div>
		</div>
		<div class = "row">
			<div class = "col-xs-3">Ctrl+3:</div>
			<div class = "col-xs-4">Карт №</div>
			<div class = "col-xs-5"></div>
		</div>
		<div class = "row">
			<div class = "col-xs-3">Ctrl+4:</div>
			<div class = "col-xs-4">Гарах</div>
			<div class = "col-xs-5"></div>
		</div>
		
	</div>
	<div id="insDisCountPriceDialog">
		<form id="attributeForm" method="post" class="form-horizontal">
		    <div class="form-group">
		    	 <div class="row">
	                <label class="col-sm-4 control-label">Жор №</label>
	                <div class="col-sm-7">
	                	<input type="text" class="form-control" name="prescription" value=""/>
	               	</div>
	             </div>
		    </div>
		    <div class="form-group">
		    	 <div class="row">
	                <label class="col-sm-4 control-label">Овог</label>
	                <div class="col-sm-7">
	                	<input type="text" class="form-control" name="firstName" value=""/>
	                </div>
	             </div>
		    </div>
		    <div class="form-group">
		    	 <div class="row">
	                <label class="col-sm-4 control-label">Нэр</label>
	                <div class="col-sm-7">
	                	<input type="text" class="form-control" name="name" value=""/>
	               	</div>
	             </div>
		    </div>
		    <div class="form-group">
		    	 <div class="row">
	                <label class="col-sm-4 control-label">Регистр №</label>
	                <div class="col-sm-7">
	               		<input type="text" class="form-control" name="regNumber" value=""/>
	               	</div>
	             </div>
		    </div>
		    <div class="form-group">
		    	 <div class="row">
	                <label class="col-sm-4 control-label">ЭМД-ийн №</label>
	                <div class="col-sm-7">
	                	<input type="text" class="form-control" name="emdNumber" value=""/>
	                </div>
	             </div>
		    </div>
		    <div class="form-group">
		    	 <div class="row">
	                <label class="col-sm-4 control-label">Хаяг/Утас</label>
	                <div class="col-sm-7">
	                	<input type="text" class="form-control" name="addressOrNumber" value=""/>
	                </div>
	             </div>
		    </div>
		    <div class="form-group">
		    	 <div class="row">
	                <label class="col-sm-4 control-label">Эмч шифр</label>
	                <div class="col-sm-7">
	                	<input type="text" class="form-control" name="cipher"/>
	                </div>
	             </div>
		    </div>
		    <button type="submit" class="btn btn-default hidden" id="validateSubmit">Validate ok</button>
		</form>
	</div>
	</div>
	<div id = "logoutDialog">
		  		<div class="center">
		  			<b>Итгэлтэй байна уу?</b>
		  		</div>
			</div>
<div class="bh-print-view">
		<div>
			<div class="row">
				<div class="col-xs-12 center bh-print-header">
					${company.name} SHOP
				</div>
			</div>
			<div class="space-4"></div>
			<div class="row">
				<div class="col-xs-3">No:</div>
				<div class="col-xs-5" id="print-talon"></div>
				<div class="col-xs-2">PO</div>
				<div class="col-xs-2 text-right" id="print-pos">${cash.posNum}</div>
			</div>
			<div class="row">
				<div class="col-xs-3">Огноо:</div>
				<div class="col-xs-9" id="print-date" style="color: background;"></div>
			</div>
			<div class="row">
				<div class="col-xs-3">Кассчин:</div>
				<div class="col-xs-9" id="print-cashier">${user.cashName}</div>
			</div>
			<!-- <div class = "row">
				<div class='col-xs-4'>Барааны нэр</div>
				<div class='col-xs-2'>Тоо</div>
				<div class='col-xs-3 text-right'>Нэгж үнэн</div>
				<div class='col-xs-3 text-right'>Нийт Дүн</div>
			</div> -->
 			<div class="space-6 bh-print-separater"></div>
			<div id="print-items" >
				<c:forEach items="${itemList}" var="item" varStatus="status">
					<div class='row'>
						<div class='col-xs-6'>${item.name}</div>
						<div class='col-xs-1'>
							<fmt:formatNumber type="number" value="${item.quantity}" pattern="###############.###" />
						</div>
						<div class='col-xs-2 text-right'>
							<fmt:formatNumber type="number" value="${item.price}" pattern="###############.###" />
						</div>
						<div class='col-xs-2 text-right'>
							<fmt:formatNumber type="number" value="${item.total}" pattern="###############.###" />
						</div>
					</div>
					<c:if test="${!status.last}">
						<div class='space-2'></div>
					</c:if>
                </c:forEach>
			</div>
			<div class="space-6 bh-print-separater"></div>
			<div class="row">
				<div class="col-xs-3">Н/тоо:</div>
				<div class="col-xs-1" id="print-item-count">
					${fn:length(itemList)}
				</div>
				<div class="col-xs-3">Нийт дүн:</div>
				<div class="col-xs-4 text-right" id="print-cal-total">
					<fmt:formatNumber type="number" value="${allTotal}" pattern="###############.###" />
				</div>
			</div>
			<div class="row">
				<div class="col-xs-4"></div>
				<div class="col-xs-4">Хөнгөлөлт:</div>
				<div class="col-xs-4 text-right" id="print-discount">
					<fmt:formatNumber type="number" value="${allDisTotal}" pattern="###############.###" />
				</div>
			</div>
			<div class="row hidden" id = "printCalSaleEMDS">
				<div class="col-xs-4"></div>
				<div class="col-xs-4">ЭМДСХ :</div>
				<div class="col-xs-4 text-right" id="print-calSaleEMDS">
					<fmt:formatNumber type="number" value="0" pattern="###############.###" />
				</div>
			</div>
			<div class="row">
				<div class="col-xs-4"></div>
				<div class="col-xs-4">Төлөх:</div>
				<div class="col-xs-4 text-right" id="print-total">
					<fmt:formatNumber type="number" value="${allTotal - allDisTotal}" pattern="###############.###" />
				</div>
			</div>
			<div class="row">
				<div class="col-xs-4"></div>
				<div class="col-xs-4">Төлсөн:</div>
				<div class="col-xs-4 text-right" id="print-paid">0</div>
			</div>
			<div class="row">
				<div class="col-xs-4"></div>
				<div class="col-xs-4">Хариулт:</div>
				<div class="col-xs-4 text-right" id="print-return">
					<fmt:formatNumber type="number" value="-${allTotal - allDisTotal}" pattern="###############.###" />
				</div>
			</div>
			<div class="space-4"></div>
			<div class="row">
				<div class="col-xs-12 center">Thank you for shopping</div>
			</div>
			<div class="row">
				<div class="col-xs-12 center">Powered by Infosystems POS system</div>
			</div>
			<div class="row">
        <div class="col-xs-12 center">Жич: Эм онцгой бараа тул буцаахгүй!</div>
      </div>
		</div>
	</div>
  
  </body>
</html>