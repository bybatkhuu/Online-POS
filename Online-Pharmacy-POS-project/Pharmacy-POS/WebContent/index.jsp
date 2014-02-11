<%@ page import="java.util.List"%>
<%@ page import="models.User"%>
<%@ page import="models.Item"%>
<%@ page import="utils.LoggedUser"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

    <title>Нүүр хуудас - [${company.name} ХХК]</title>
    
    <link rel="icon" type="image/x-icon" href="images/favicon.ico" />
    <link rel="apple-touch-icon" href="images/Logo.png">
    <link rel="apple-touch-startup-image" href="images/Logo.png">
    
    <link rel="stylesheet" href="css/bootstrap.min.css" />
    <link rel="stylesheet" href="css/font-awesome.min.css" />
    <link rel="stylesheet" href="css/jquery-ui-1.10.3.full.min.css" />
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
    <script type="text/javascript" src="js/jquery.slimscroll.min.js"></script>
    <script type="text/javascript" src="js/jquery.jkey.min.js"></script>
    <script type="text/javascript" src="js/ace-elements.min.js"></script>
    <script type="text/javascript" src="js/ace.min.js"></script>

    <script type="text/javascript" src="js/support.js"></script>
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
                                  <input type="text" name="talon" value="" class="col-xs-12 col-sm-12 input-sm bolder dark bh-bg-color-yellow bh-input-skin-1 bh-font-size-16" id="talon" pattern="[0-9]{1,3}" disabled />
                                  <span class="input-group-addon input-sm">
                                    <i class="bhicon bhicon-receipt"></i>
                                  </span>
                                </div>
                              </div>
                            </div>
                            <div class="form-group">
                              <label for="cash" class="control-label col-xs-12 col-sm-5 col-md-3 no-padding-right">Кассчин:</label>
                              <div class="col-xs-12 col-sm-7 col-md-9">
                                <div class="input-group">
                                  <input type="text" name="cash" value="${user.userName}" class="col-xs-12 col-sm-12 input-sm bolder dark bh-input-skin-1 bh-font-size-14" id="cash" disabled />
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
                                  <input type="text" name="pos" value="${user.pos}" class="col-xs-12 col-sm-12 input-sm bolder dark bh-input-skin-1 bh-font-size-14" id="pos" pattern="[0-9]{1,3}" disabled />
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
                                    <input type="text" name="barcode" class="form-control bolder dark bh-input-skin-1" id="barcode" pattern="[A-Za-z0-9]{2,32}" tabindex="1" autocomplete="off" autofocus />
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
	                                    <input type="text" name="serial" class="form-control dark bh-input-skin-1" id="serial" tabindex="3" autocomplete="off" />
	                                    <span class="input-group-addon">
	                                      <i class="bhicon bhicon-product bh-icon-size-15-5"></i>
	                                    </span>
	                                  </div>
	                                  <span class="col-xs-12 col-sm-12">
	                                      <span class="pull-right">
	                                          Сери өөрчлөх - F4
	                                      </span>
	                                  </span>
	                                </div>
	                              </div>
                            </div>

                            <div class="space-4"></div>
                            
                            <div class="row">
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
                            
                            <!-- <div class="row">
                              <div class="form-group">
                                <label for="wholesalePrice" class="col-xs-12 col-sm-5">Бөөн.үнэ:</label>
                                <div class="col-xs-12 col-sm-7">
                                  <input type="text" name="wholesalePrice" value="0" class="form-control input-sm dark text-right bh-input-skin-1" id="wholesalePrice" disabled />
                                </div>
                              </div>
                            </div>

                            <div class="row">
                              <div class="form-group">
                                <label for="wholesaleQuantity" class="col-xs-12 col-sm-5">Бөөн.тоо:</label>
                                <div class="col-xs-12 col-sm-7">
                                  <input type="text" name="wholesaleQuantity" value="0" class="form-control input-sm dark text-right bh-input-skin-1" id="wholesaleQuantity" disabled />
                                </div>
                              </div>
                            </div> -->
                            
                            <div class="space-8"></div>

                            <div class="row">
                              <div class="col-xs-12 col-sm-12 col-md-4 pull-right">
                              	<button class="col-xs-12 btn btn-primary btn-sm pull-right" id="addItem">
                              	  <i class="icon-plus"></i> Нэмэх
                              	</button>
                              </div>
                              
	                              <div class="col-xs-12 col-sm-12 col-md-8 pull-left">
	                                <div class="checkbox no-padding hidden">
	                                  <label>
	                                    <input type="checkbox" class="ace ace-switch ace-switch-6" tabindex="-1" id="insuranceCheck" <%
	                                    		if (session.getAttribute("hasInsurance") != null)
	                                    		{
	                                    			out.print("checked");
	                                    		}
	                                    %>/>
	                                    <span class="lbl">
	                                      <b>&nbsp; ЭМДС-ийн хөнгөлөлт</b>
	                                    </span>
	                                  </label>
	                                </div>
	                              </div>
                            </div>
                            
							<div class="space-4"></div>
														
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
                                  <th>Барааны нэр</th>
                                  <th>Тоо</th>
                                  <th>Нэгж</th>
                                  <th>Нэгж үнэ</th>
                                  <th>Нийт дүн</th>
                                  <th class="hidden">Хөн %</th>
                                </tr>
                              </thead>
                              <tbody id="tableBody">
                              	<%
                              		if (session.getAttribute("itemList") != null)
                              		{
                              			@SuppressWarnings("unchecked")
                              			List<Item> itemList = (List<Item>) session.getAttribute("itemList");
                              			if (itemList != null && !itemList.isEmpty())
                            	    	{
                              				int i = 1;
                              				for (Item item : itemList)
                              				{
                              					if (itemList.size() == i)
                              					{
                              						out.println("<tr class='success' id='" + item.getId() + "'>");
                              					}
                              					else
                              					{
                              						out.println("<tr id='" + item.getId() + "'>");
                              					}
	                              				out.println("<td>" + item.getName() + "</td>");
	                              				Double result = item.getQuantity() - (int)(item.getQuantity());
	                              				if (result != 0)
	                              				{
	                              					out.println("<td class='text-right'>" + item.getQuantity() + "</td>");
	                              				}
	                              				else
	                              				{
	                              					out.println("<td class='text-right'>" + (int)item.getQuantity() + "</td>");
	                              				}
	                      						out.println("<td>" + item.getUnit() + "</td>");
	                      						out.println("<td class='text-right'>" + format.format(item.getPrice()) + "</td>");
	                      						out.println("<td class='text-right'>" + format.format(item.getTotal()) + "</td>");
	                      						out.println("<td class='text-right hidden'>0</td>");
	                      						out.println("</tr>");
                              					i++;
                              				}
                            	    	}
                              		}
                              	%>
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
                                      <button class="col-xs-12 btn btn-sm btn-warning" tabindex="-1" id="updateButton">
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
                                      <input type="text" name="calTotal" value="<%
                                    		if (session.getAttribute("itemList") != null)
                  		              		{
                  		              			@SuppressWarnings("unchecked")
                  		              			List<Item> itemList = (List<Item>) session.getAttribute("itemList");
                  		              			if (itemList != null && !itemList.isEmpty())
                  		            	    	{
                  		              				double all = 0;
                  		              				for (Item item : itemList)
                  		              				{
                  		              					all = all + item.getTotal();
                  		              				}
                  		              				out.print(format.format(all));
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
                                      %>" class="form-control input-sm bolder dark text-right bh-input-skin-1 bh-input-bg-color-1" id="calTotal" disabled />
                                  </div>
                                </div>
	                                <div class="form-group hidden">
	                                  <label for="calSaleEMDS" class="col-xs-12 col-sm-6 control-label no-padding-right">
	                                    <b>Хөнгөлөлт (ЭМДС-аас):</b>
	                                  </label>
	                                  <div class="col-xs-12 col-sm-6">
	                                      <input type="text" name="calSaleEMDS" value="<%
	                                      		if (session.getAttribute("hasInsurance") != null)
	                                      		{
		                                      		if (session.getAttribute("itemList") != null)
		                  		              		{
		                  		              			@SuppressWarnings("unchecked")
		                  		              			List<Item> itemList = (List<Item>) session.getAttribute("itemList");
		                  		              			if (itemList != null && !itemList.isEmpty())
		                  		            	    	{
		                  		              				double all = 0;
		                  		              				for (Item item : itemList)
		                  		              				{
		                  		              					all = all + item.getCalTotal();
		                  		              				}
		                  		              				out.print(format.format(all));
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
	                                      		}
	                                      		else
	                                      		{
	                                      			out.print("0");
	                                      		}
	                                      %>" class="form-control input-sm bolder dark text-right bh-input-skin-1 bh-input-bg-color-1" id="calSaleEMDS" disabled />
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
                                      <input type="text" name="paid" value="0" class="form-control input-sm bolder dark text-right bh-font-size-16 bh-input-skin-2" id="paid" tabindex="4" />
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
              <div class="col-xs-4 col-sm-2 red center hidden">
                ЭМДС - Хөнгөлөлт <b class="dark">F2</b>
              </div>
              <div class="col-xs-4 col-sm-2 green center">
                Баркод <b class="dark">F3</b>
              </div>
              <div class="col-xs-4 col-sm-2 blue center">
                Тоо засах <b class="dark">F5</b>
              </div>
              <div class="col-xs-4 col-sm-2 blue center">
                Хэвлэх <b class="dark">F6</b>
              </div>
              <div class="col-xs-4 col-sm-2 blue center">
                Харуилт <b class="dark">F7</b>
              </div>
              <div class="col-xs-4 col-sm-2 blue center hidden">
                Тайлан <b class="dark">F8</b>
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
            <small class="hidden">1.Тохиргоо=(Ctrl+N) 2.Талоны загвар=(Ctrl+M) 3.Барааны хайлт=(Ctrl+F)</small>
          </div>
          <div class="col-xs-12 col-sm-4 center">
            &copy;2013  Infosystems LLC
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
	
	<div id="searchItemDialog" title="Бараа хайх">
		<br>
		<div class="col-sm-12">
			<div class="row">
				<form action="#" class="form-inline" method="POST">
						<div class="form-group">
							<label for="searchByName" class="control-label">Нэр:</label>
							<input type="text" class="form-control" id="searchByName" />
						</div>
					<div class="form-group">
						<label for="searchByBarcode" class="control-label">Баркод:</label>
						<input type="text" class="form-control" id="searchByBarcode" />
					</div>
					<button type="submit" class="btn btn-success" id="searchItem">Хайх</button>
				</form>
			</div>
			<br>
			<div class="row">
				<div class="col-sm-12">
					<table class="table table-bordered">
						<thead>
							<tr>
								<th>Нэр</th>
								<th>Баркод</th>
								<th>Сери</th>
								<th>Үнэ</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>Эм</td>
								<td>12321</td>
								<td>1</td>
								<td>1500</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
	
	</div>
	
	<div class="bh-print-view">
		<div class="row">
			<div class="col-sm-6 col-xs-12">
				<div class="row">
					<div class="col-xs-3">№:</div>
					<div class="col-xs-5" id="print-talon">1</div>
					<div class="col-xs-2">POS:</div>
					<div class="col-xs-2 center" id="print-pos">${user.pos}</div>
				</div>
				<div class="row">
					<div class="col-xs-3">Огноо:</div>
					<div class="col-xs-9" id="print-date">01/09/2014 02:03:48 PM</div>
				</div>
				<div class="row">
					<div class="col-xs-3">Кассчин:</div>
					<div class="col-xs-9" id="print-cash">${user.userName}</div>
				</div>
				<div class="row">
					<div>
						<div><hr></div>
					</div>
				</div>
				<div id="print-items">
					<%
						if (session.getAttribute("itemList") != null)
	              		{
	              			@SuppressWarnings("unchecked")
	              			List<Item> itemList = (List<Item>) session.getAttribute("itemList");
	              			if (itemList != null && !itemList.isEmpty())
	            	    	{
	              				for (Item item : itemList)
	              				{
	              					out.println("<div class='row'>");
	              						out.println("<div class='col-xs-4'>" + item.getName() + "</div>");
	              						Double result = item.getQuantity() - (int)(item.getQuantity());
                          				if (result != 0)
                          				{
                          					out.println("<div class='col-xs-1'>" + item.getQuantity() + "</div>");
                          				}
                          				else
                          				{
                          					out.println("<div class='col-xs-1'>" + (int)item.getQuantity() + "</div>");
                          				}
                          				out.println("<div class='col-xs-3 align-left'>" + format.format(item.getPrice()) + "</div>");
                          				out.println("<div class='col-xs-2 align-right'>" + format.format(item.getTotal()) + "</div>");
	              					out.println("</div>");
	              				}
	            	    	}
	              		}
					%>
				</div>
				<div class="row">
					<div>
						<div><hr></div>
					</div>
				</div>
				<div class="row">
					<div class="col-xs-3">Н/тоо:</div>
					<div class="col-xs-1" id="print-s">
						<%
							if (session.getAttribute("itemList") != null)
		              		{
		              			@SuppressWarnings("unchecked")
		              			List<Item> itemList = (List<Item>) session.getAttribute("itemList");
		              			if (itemList != null && !itemList.isEmpty())
		            	    	{
		              				out.print(itemList.size());
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
						%>
					</div>
					<div class="col-xs-4">Нийт:</div>
					<div class="col-xs-3 align-right" id="print-total">
						<%
							if (session.getAttribute("itemList") != null)
		              		{
		              			@SuppressWarnings("unchecked")
		              			List<Item> itemList = (List<Item>) session.getAttribute("itemList");
		              			if (itemList != null && !itemList.isEmpty())
		            	    	{
		              				double all = 0;
		              				for (Item item : itemList)
		              				{
		              					all = all + item.getTotal();
		              				}
		              				out.print(format.format(all));
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
						%>
					</div>
				</div>
				<div class="row">
					<div class="col-xs-4"></div>
					<div class="col-xs-4">Төлсөн:</div>
					<div class="col-xs-3 align-right" id="print-paid">0</div>
				</div>
				<div class="row">
					<div class="col-xs-4"></div>
					<div class="col-xs-4">Хариулт:</div>
					<div class="col-xs-3 align-right" id="print-return">0</div>
				</div>
				<br>
				<div class="row">
					<div class="col-xs-12 center">Thank you for shopping</div>
				</div>
				<div class="row">
					<div class="col-xs-12 center">Powered by Infosystems POS system</div>
				</div>
			</div>
		</div>
	</div>
  </body>
</html>