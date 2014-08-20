<%@ page import="utils.LoggedUser"%>
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
    
    <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css" />
    <link rel="stylesheet" type="text/css" href="css/font-awesome.min.css" />
    <link rel="stylesheet" type="text/css" href="css/select2.css" />
    <link rel="stylesheet" type="text/css" href="css/ace.min.css" />
    
    <link rel="stylesheet" type="text/css" href="css/custom-icons.css" />
    <link rel="stylesheet" type="text/css" href="css/style.css" />
    <style type="text/css">
    
    </style>
    
    <script type="text/javascript" src="js/jquery-1.11.0.min.js"></script>

    <script type="text/javascript" src="js/page-js/cash.js"></script>
    <script type="text/javascript">
      $(document).ready(function()
      {
    	  
      });
    </script>
	
	</head>
	<body>
		<div class="bh-print-view" id = "printFacture_page">
		<div class="bh-print-body">
			<div class="row">
				<div class="col-xs-12 center bh-print-header">
					${company.name} SHOP
				</div>
			</div>
			<div class="space-4"></div>
			<div class="row">
				<div class="col-xs-3">No:</div>
				<div class="col-xs-5" id="print-talon"></div>
				<div class="col-xs-2">POS:</div>
				<div class="col-xs-2 text-right" id="print-pos">${cash.posNum}</div>
			</div>
			<div class="row">
				<div class="col-xs-3">Огноо:</div>
				<div class="col-xs-9" id="print-date"></div>
			</div>
			<div class="row">
				<div class="col-xs-3">Кассчин:</div>
				<div class="col-xs-9" id="print-cashier">${user.cashName}</div>
			</div>
<!-- 			<div class = "row">
				<div class='col-xs-4'>Барааны нэр</div>
				<div class='col-xs-2'>Тоо ширхэг</div>
				<div class='col-xs-3 text-right'>Нэгж үнэн</div>
				<div class='col-xs-3 text-right'>Нийт Дүн</div>
			</div>
 -->			<div class="space-6 bh-print-separater"></div>
			<div id="print-items">
				<c:forEach items="${itemList}" var="item" varStatus="status">
					<div class='row'>
						<div class='col-xs-4'>${item.name}</div>
					<!-- </div>
					<div class='row'>
						<div class='col-xs-4'></div> -->
						<div class='col-xs-2'>
							<fmt:formatNumber type="number" value="${item.quantity}" pattern="###############.###" />
						</div>
						<div class='col-xs-3 text-right'>
							<fmt:formatNumber type="number" value="${item.price}" pattern="###############.###" />
						</div>
						<div class='col-xs-3 text-right'>
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
				<div class="col-xs-4">Нийт дүн:</div>
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
			<div class="row hidden">
				<div class="col-xs-12 center"></div>
			</div>
		</div>
	</div>
	</body>
</html>