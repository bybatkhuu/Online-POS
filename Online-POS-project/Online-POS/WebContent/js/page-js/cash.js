var thName = 0;
var thQuant = 1;
var thUnit = 2;
var thPrice = 3;
var thTotal = 4;
var thDisCountPercent = 5;
var thDiscount = 6;
var leftKey = 1;

$(document).ready(function()
{
	startTime();
	ajaxSetup();
	initSearchDialog();
	initHelpDialog();
	initOrderNum();
	initBarcode();
	initSearchSlider();
	initTableSlim();
	initTypes();
	checkAll();
	
	initEventHandlers();
});
function startTime()
{
	var today = new Date();
	var weekday = new Array(7);
	weekday[0] = "Ня";
	weekday[1] = "Да";
	weekday[2] = "Мя";
	weekday[3] = "Лх";
	weekday[4] = "Пү";
	weekday[5] = "Ба";
	weekday[6] = "Бя";
	  
	var year = today.getFullYear();
	var month = today.getMonth() + 1;
	var date = today.getDate();
    var hour = today.getHours();
    var minute = today.getMinutes();
    var second = today.getSeconds();
    var day = weekday[today.getDay()];
    
    // add a zero in front of numbers < 10
    month = checkTime(month);
    date = checkTime(date);
    hour = checkTime(hour);
    minute = checkTime(minute);
    second = checkTime(second);
    var dateTimeStr = year + "." + month + "." + date + " - " + hour + ":" + minute + ":" + second + " "/* + " <b class='blue'>"*/ + day /*+ "</b>"*/;
    $("#time").html(dateTimeStr);
    $("#print-date").html(dateTimeStr);
    setTimeout(function(){ startTime(); }, 500);
}
function checkTime(i)
{
	if (i < 10)
    {
    	i = "0" + i;
    }
    return i;
}

function ajaxSetup()
{
	$("#cardUsersDialog").dialog(
	{
		autoOpen: false,
		modal: true,
		width: 700
	});
	$("#logoutDialog").dialog(
	{
		title: "Гарах",
		title_html: true,
		autoOpen: false,
		modal: true,
		buttons:[ 
				{
					html: "<i class='icon-off bigger-110'></i>&nbsp; Гарах",
					"class" : "btn btn-danger btn-xs ",
					"id" : "btnok",
						click: function() {
							window.location = "logout";
						}
					},
					{
					html: "<i class='icon-remove bigger-110'></i>&nbsp; Болих",
					"class" : "btn btn-success btn-xs",
					"id" : "btnCncl",
						click: function() {
							$(this).dialog("close");
						}
					}
		         ]
	});
	
	$.widget("ui.dialog", $.extend({}, $.ui.dialog.prototype,
	{
		_title: function(title)
		{
			var $title = this.options.title || '&nbsp;';
			if(("title_html" in this.options) && this.options.title_html == true)
				title.html($title);
			else
				title.text($title);
		}
	}));
	
	$("#loadingDialog").dialog(
	{
		autoOpen: false,
		modal: true
	});
	
	$("#errorDialog").dialog(
	{
		autoOpen: false,
		modal: true,
		title: "<div class='widget-header header-color-red'><h5><i class='icon-warning-sign'></i>&nbsp;Анхааруулга!</h5></div>",
		title_html: true,
		width: 550,
		buttons:
		[{
			html: "<i class='icon-refresh'></i>&nbsp; Дахин унших",
			"class" : "btn btn-success btn-xs",
			click: function()
			{
				window.location.reload(true);
			}
		},
		{
			html: "<i class='icon-remove'></i>&nbsp; Цуцлах",
			"class" : "btn btn-xs",
			click: function()
			{
				$(this).dialog("close");
			}
		}]
	});
	
	$.ajaxSetup(
	{
		async: false,
	});
	
	$(document).ajaxStart(function()
    {
		$("#loadingDialog").dialog("open");
	}).ajaxStop(function()
	{
		$("#loadingDialog").dialog("close");
    }).ajaxError(function(event, xhr, options)
    {
    	if (xhr.status == 500)
    	{
    		errorDialog("Серверт алдаа гарсан байна!\nАлдааны хүсэлт: " + options.url);
    	}
    	else if (xhr.status == 404)
    	{
    		errorDialog("Сервертэй холбогдохгүй буюу серверээс холболт тасарсан байна!\nАлдааны хүсэлт: " + options.url);
    	}
    	else
    	{
    		errorDialog("Веб програмд ямар нэгэн алдаа гарлаа!\nАлдааны хүсэлт: " + options.url);
    	}
    });
}

function errorDialog(message)
{
	$("#errorMessage").html("<strong><i class='icon-remove'></i></strong>&nbsp; " + message);
	$("#errorDialog").dialog("open");
}

function initTypes()
{
	if ($("#assetType").val() == "Select")
	{
		$.ajax(
		{
			url: "get-asset-accounts",
			success: function(result)
			{
				$("#assetAccounts").html(result);
			}
		});
		$("#assetAccounts").select2();
	}
	
	$.ajax(
	{
		url: "get-customers",
		success: function(result)
		{
			$("#customers").html(result);
		}
	});
	$("#customers").select2();
	$("#customerCheck").click(function()
	{
		var check = $("#customerCheck").prop("checked");
		if (check.toString() == "true")
		{
			$("#customers").select2("enable", true);
			$("#bankCheck").prop("disabled", true);
		}
		else
		{
			$("#customers").select2("enable", false);
			$("#bankCheck").prop("disabled", false);
		}
	});
	
	$.ajax(
	{
		url: "get-banks",
		success: function(result)
		{
			$("#banks").html(result);
		}
	});
	$("#banks").select2();
	$("#bankCheck").click(function()
	{
		var check = $("#bankCheck").prop("checked");
		if (check.toString() == "true")
		{
			$("#banks").select2("enable", true);
			$("#customerCheck").prop("disabled", true);
		}
		else
		{
			$("#banks").select2("enable", false);
			$("#customerCheck").prop("disabled", false);
			
		}
	});
}

function initSearchDialog()
{
	$("#searchItemsDialog").dialog(
	{
		title: "<div class='widget-header'><h5>Бараа хайх</h5></div>",
		title_html: true,
		autoOpen: false,
		modal: true,
		width: 700,
		height: 550
	});
}
function initHelpDialog()
{
	$("#helpDialog").dialog(
	{
		title: "<div class='widget-header'><h5>Тусламж</h5></div>",
		title_html: true,
		autoOpen: false,
		modal: true,
		width: 400,
		height: 250,
		buttons:[ 
						{
						html: "<i class='icon-check bigger-110'></i>&nbsp; Ok",
						"class" : "btn btn-success btn-xs",
						"id" : "btnBack",
							click: function() {
								$(this).dialog("close");
							}
						}
			         ]
	});
}
function initOrderNum()
{
	$.ajax(
    {
    	url: "get-order-num",
  	  	success: function(result)
  	  	{
  	  		if (result.trim() != "0")
  	  		{
  	  			$("#talon").val(result);
  	  			$("#print-talon").val(result);
  	  		}
  	  		else
  	  		{
  	  			alert("Анхааруулга:\nСерверээс талоны мэдээлэл авч чадахгүй байна!\nВеб хуудсаа дахин дуудна уу!\nЭсвэл системийн админтай холбогдоно уу!");
  	  		}
  	  	}
    });
}

function initBarcode()
{
	$.ajax(
	{
		url: "get-barcodes",
		success: function(result)
		{
			var barcodes = result.split(",");
			$("#barcode").autocomplete(
			{
				source: function(request, response)
				{
					var matches = $.map(barcodes, function(tag)
					{
						if (tag.toUpperCase().indexOf(request.term.toUpperCase()) === 0)
						{
							return tag;
						}
					});
					response(matches);
				}
			});
		}
	});
}

function initTableSlim()
{
	$('#mainTableSlim').slimScroll(
    {
      	height: '374px',
      	railVisible: true,
      	size: '12px'
    });
}

function initSearchSlider()
{
	var $slider = $("#slider-range").slider(
	{
		range: true,
		min: 0,
		max: 100000,
		step: 10,
		slide: function(event, ui)
		{
			$("#searchByMinPrice").val(ui.values[0]);
			$("#searchByMaxPrice").val(ui.values[1]);
		}
	});	
	$.ajax(
	{
		url: "get-min-max-price",
		success: function(result)
		{
			var jsonData = eval("(" + result + ")");
			
			if (jsonData.hasPrice == "true")
			{
				$slider.slider("option", "min", parseFloat(jsonData.minPrice));
				$slider.slider("option", "max", parseFloat(jsonData.maxPrice));
				$slider.slider("option", "values", [parseFloat(jsonData.minPrice), parseFloat(jsonData.maxPrice)]);
			}
		}
	});
	$("#searchByMinPrice").val($("#slider-range").slider("values", 0));
	$("#searchByMaxPrice").val($("#slider-range").slider("values", 1));
	
	$("#searchByMinPrice").keyup(function(event)
	{
		$("#slider-range").slider("option", "values", [$("#searchByMinPrice").val(), $("#searchByMaxPrice").val()]);
	});
	
	$("#searchByMaxPrice").keyup(function(event)
	{
		$("#slider-range").slider("option", "values", [$("#searchByMinPrice").val(), $("#searchByMaxPrice").val()]);
	});
}

function addItem(barcode, quantity, assetAcc)
{
	if (barcode != "")
  	{
	  	if (quantity != "")
	  	{
	  		if (!isNaN(quantity))
		  	{
		  		if (quantity > 0)
			  	{
			  		var isBarcode = 0;
				  	$.ajax(
				  	{
				  		url: "check-barcode",
					  	data:
					  	{
					  		"barcode": barcode,
					  		"assetAcc": assetAcc
					  	},
					  	success: function(result)
					  	{
					  		isBarcode = result;
					  	}
				  	});
				  	if (isBarcode == 1)
				  	{
			  			$.ajax(
					  	{
					  		url: "add-item",
						  	data:
						  	{
						  		"barcode": barcode,
							  	"quantity": quantity,
							  	"assetAcc": assetAcc
					      	},
					      	success: function(result)
					      	{
					      		$("#tableBody").html(result);					      		
							  	$("#mainTableSlim").scrollTop($("#tableBody > tr[class='success']").index()*12);
					      		initTableEvents();
					    	  	checkAll();
					      	}
					  	});
    			  	}
				  	else
				  	{
				  		alert("'" + barcode + "' гэсэн баркодтай бараа энэ тасагт олдохгүй байна!");
    			  	}
			  	}
			  	else
			  	{
			  		alert("Тоо хэмжээгээ 0-ээс их өгнө үү!");
			  	}
	      	}
		  	else
		  	{
		  		alert("Тоо хэмжээн дунд үсэг, тэмдэгт байна!");
		  	}
	  	}
	  	else
	  	{
	  		alert("Тоо хэмжээгээ оруулна уу!");
	  	}
  	}
  	else
  	{
	  	alert("Баркод хоосон байна!");
  	}
}

function bindForm(element)
{
	var oldQuant = parseFloat($(element).children().eq(thQuant).text().trim());
	var oldDisCountPercent = parseFloat($(element).children().eq(thDisCountPercent).text().trim());
	var price = parseFloat($(element).children().eq(thPrice).text().trim());
	var id = $(element).attr("id");
	if(leftKey == 1){
		$(element).children().first().next().html
		(
			"<input type='text' value='' size='1' class='input-sm' id='newQuant' />"
		);
		
		$("#newQuant").focus();
		
		$("#newQuant").keydown(function(event)
		{
			
			if (event.which == 13)
			{
				updateFromTable(element, id, price, oldQuant);
			}
			 if(event.which == 37 || event.which == 39)
			{
				 event.preventDefault();
				if($("#newQuant").is(":focus")){
					leftKey = 2;
					$("#newQuant").remove();
					$(element).children().eq(thQuant).text(oldQuant);
					 bindForm(element);
				}else
				if($("#newQuant1").is(":focus"))
				{
					$("#newQuant1").remove();
					leftKey = 1;
					$(element).children().eq(thDisCountPercent).text(oldDisCountPercent);
					 bindForm(element);
				}
				
			}
			
		});
				
	  	$("#newQuant").focusout(function()
	  	{
	  		updateFromTable(element, id, price, oldQuant);
	  	});
	}else{
		if($("#cardNumber").val() == ""){
			$(element).children().eq(thDisCountPercent).html
			(
				"<input type='text' value='' size='1' class='input-sm' id='newQuant1' />"
			);
			
			$("#newQuant1").focus();
			
			$("#newQuant1").keydown(function(event)
					{
						
						if (event.which == 13)
						{
							updateFromTableDisCount(element, id, oldDisCountPercent);
						}
						 if(event.which == 37 || event.which == 39)
						{
							 event.preventDefault();
							if($("#newQuant").is(":focus")){
								leftKey = 2;
								$("#newQuant").remove();
								$(element).children().eq(thQuant).text(oldQuant);
								 bindForm(element);
							}else
							if($("#newQuant1").is(":focus"))
							{
								$("#newQuant1").remove();
								leftKey = 1;
								$(element).children().eq(thDisCountPercent).text(oldDisCountPercent);
								 bindForm(element);
							}
							
						}
						
					});			
		  	$("#newQuant1").focusout(function()
		  	{
		  		updateFromTableDisCount(element, id, oldDisCountPercent);
		  	});
		}else{
			leftKey = 1;
			alert("Давхар хөнгалөлт авч болохгүй");
		}
	}
	
}
function updateFromTable(element, id, price, oldQuant)
{
	var newQuant = parseFloat($("#newQuant").val());
	$("#newQuant").remove();
	if (oldQuant != newQuant && !isNaN(newQuant) && 0 <= newQuant)
	{
		update(id, newQuant);
	}
	else
	{
		$(element).children().eq(thQuant).text(oldQuant);
	}
}
function updateFromTableDisCount(element, id, oldDiscount)
{
	var newDiscount = parseFloat($("#newQuant1").val());
	$("#newQuant1").remove();
	if (oldDiscount != newDiscount && !isNaN(newDiscount) && 0 <= newDiscount && newDiscount <=100)
	{
		updateDisCount(id, newDiscount);
	}
	else
	{
		$(element).children().eq(thDisCountPercent).text(oldDiscount);
	}
}

function clickedRow(element)
{
	$("#tableBody > tr[class='success']").removeClass();
	$(element).addClass("success");
	$("#itemName").val($(element).children().eq(thName).text().trim());
  	$("#unitPrice").val($(element).children().eq(thPrice).text().trim());
  	$("#unit").text($(element).children().eq(thUnit).text().trim());
  	$("#quantity").val("1");
	checkAll();
}
function update(id, newQuant)
{
	$.ajax(
	{
		url: "update-item",
		data:
		{
			"id": id,
			"newQuant": newQuant
		},
		success: function(result)
		{
			$("#tableBody").html(result);
			initTableEvents();
			checkAll();
		}
	});
}
function updateDisCount(id, newDisCount)
{
	$.ajax(
	{
		url: "updateDisCount-item",
		data:
		{
			"id": id,
			"newDisCount": newDisCount
		},
		success: function(result)
		{
			$("#tableBody").html(result);
			initTableEvents();
			checkAll();
		}
	});
}
function purchase()
{
	var type = "Cash";
	var otherId = "0";
	var check = $("#customerCheck").prop("checked");
	if (check.toString() == "true")
	{
		type = "Invoice";
		otherId = $("#customers").val().trim();
		$("#customerCheck").trigger('click');
	}
	else
	{
		check = $("#bankCheck").prop("checked");
		if (check.toString() == "true")
		{
			type = "Card";
			otherId = $("#banks").val().trim();
			$("#bankCheck").trigger('click');
		}
		else
		{
			type = "Cash";
			otherId = "0";
		}
	}
	var talon = 0;
	var isPurchased = false;
	
	if ($("#talon").val().trim() != "")
	{
		$.ajax(
		{
			url: "purchase-items",
			data:
			{
				"orderNum": $("#talon").val().trim(),
				"type": type,
				"otherId": otherId
			},
		  	success: function(result)
		  	{
		  		var jsonData = eval("(" + result + ")");
		  		if (jsonData.isPurchased != "true")
		  		{
		  			errorDialog("Гүйлгээ амжилтгүй боллоо. Та веб програмаа дахин дуудаж гүйлгээгээ дахин хийнэ үү!\nАлдааны мэдээлэл: Талон - " + $("#talon").val().trim() + ", Кассчин - " + $("#cashier").val().trim());
		  			//location.reload(true);
		  		}
		  		else
		  		{
		  			isPurchased = true;
		  			talon = jsonData.talon;
		  		}
		  	}
		});
		
		if (isPurchased == true)
		{
			window.print();
			$("#talon").val(talon);
	  		$("#print-talon").val(talon);
	  		$("#tableBody").html("");
	  		$("#quantity").val("1");
	  		$("#unit").text("ш");
	  		$("#unitPrice").val("");
	  		$("#itemName").val("");
	  		$("#cardNumber").val("");
	  		$("#cardOwner").text("");
	  		$("#discountPercent").val("");
	  		$("#discountType").val("");
	  		checkAll();
	  		$("#barcode").focus();
		}
	}
	else
	{
		alert("Талоны дугаараа оруулна уу!");
	}
}

function isNumber(n)
{
	return !isNaN(parseFloat(n)) && isFinite(n);
}

function editPrice()
{
	if ($("#unitPrice").val() != "")
	{
		if (isNumber($("#unitPrice").val()))
		{
			var priceCheck = $("#tableBody > tr[class='success']").children().eq(thPrice).text();
				if(priceCheck > $("#unitPrice").val()){
				$.ajax(
				{
					url: "edit-price",
					data:
					{
						"id" : $("#tableBody > tr[class='success']").attr("id"),
						"price" : $("#unitPrice").val()
					},
					success: function(result)
					{
						$("#tableBody").html(result);
						initTableEvents();
						checkAll();
					}
				});
			}else{
				alert($("#tableBody > tr[class='success']").children().eq(thPrice).text().trim()+"-с бага тоо оруулна уу");
				$("#unitPrice").val($("#tableBody > tr[class='success']").children().eq(thPrice).text().trim());
			}
		}
		else
		{
			alert("Үнэ засах хэсэгт зөвхөн тоо оруулна уу!");
			$("#unitPrice").val($("#tableBody > tr[class='success']").children().eq(thPrice).text().trim());
		}
	}
	else
	{
		alert("Засах үнээ оруулна уу!");
		$("#unitPrice").val($("#tableBody > tr[class='success']").children().eq(thPrice).text());
	}
	$("#unitPrice").prop("disabled", true);
}
function loadPrintView()
{
	$("#print-talon").html($("#talon").val());
	$("#print-pos").html($("#pos").val());
	$("#print-date").html($("#time").html());
	$("#print-cashier").html($("#cashier").val());
	var str = "";
	for (var i = 0; i < $("#tableBody > tr").size(); i++)
	{
		str = str + "<div class='row' >";
			str = str + "<div class='col-xs-6' style = 'padding-left:5px;margin-left:5px'>" + $("#tableBody > tr:eq(" + i + ")").children().eq(thName).html() + "</div>";
			str = str + "<div class='col-xs-1' style = 'padding-left:0px;margin-left:0px' >" + $("#tableBody > tr:eq(" + i + ")").children().eq(thQuant).html() + "</div>";
			str = str + "<div class='col-xs-2' style = 'padding-left:0px;margin-left:0px'>" + $("#tableBody > tr:eq(" + i + ")").children().eq(thPrice).html() + "</div>";
			str = str + "<div class='col-xs-2' style = 'padding-left:0px;margin-left:0px'>" + $("#tableBody > tr:eq(" + i + ")").children().eq(thTotal).html() + "</div>";
		str = str + "</div>";
		if (i < ($("#tableBody > tr").size() - 1))
		{
			str = str + "<div class='space-2 row'></div>";
		}
	}
	$("#print-items").html(str);
	$("#print-item-count").html($("#tableBody > tr").size());
	
	$("#print-cal-total").html($("#calTotal").val());
	$("#print-discount").html($("#discountTotal").val());
	$("#print-total").html($("#payOff").val());
	$("#print-paid").html($("#paid").val());
	$("#print-return").html($("#return").val());
	
}

function initTableEvents()
{
	$("#tableBody > tr").bind("click", function()
	{
		clickedRow($(this));
	});
	$("#tableBody > tr").bind("dblclick", function()
	{
		bindForm($(this));
	});
	$("#unitPrice").val($("#tableBody > tr[class='success']").children().eq(thPrice).text().trim());
	$("#itemName").val($("#tableBody > tr[class='success']").children().eq(thName).text().trim());
	$("#unit").text($("#tableBody > tr[class='success']").children().eq(thUnit).text().trim());
	$("#quantity").val("1");
    $("#barcode").val("");
}

function initEventHandlers()
{
	$(document).keydown(function(event)
	{
		//ctrl+f
		if (event.which== 70 && event.ctrlKey)
		{
			event.preventDefault();
			$("#searchItemsDialog").dialog("open");
			
		}
		//ctrl+1
		if(event.keyCode == 49 && event.ctrlKey){
			event.preventDefault();
			//silverPen start
			if(!$("#searchItemsDialog").parents(".ui-dialog").is(":visible")){
				$("#customerCheck").trigger('click');
				if($("#customerCheck").prop("checked")){
					$("#customers").focus();
				};
				//silverPen end
			}else{
				$("#searchByMinPrice").focus();
				$("#searchByMinPrice").val("");
			}
		}else
		//ctrl+2
		if(event.keyCode == 50 && event.ctrlKey){
			event.preventDefault();
			if(!$("#searchItemsDialog").parents(".ui-dialog").is(":visible")){
				$("#bankCheck").trigger('click');
				if($("#bankCheck").prop("checked")){
					$("#banks").focus();
				};
			}else{
				$("#searchByMaxPrice").focus();
				$("#searchByMaxPrice").val("");
			}
		}else
//		ctrl+3
		if(event.keyCode == 51 && event.ctrlKey){
			event.preventDefault();
			if(!$("#searchItemsDialog").parents(".ui-dialog").is(":visible")){
				$("#cardNumber").focus();
			}
		}else
		//alt+4
		if(event.keyCode == 115 && event.altKey){
			if(!$("#searchItemsDialog").parents(".ui-dialog").is(":visible")){
				event.preventDefault();
				$("#logoutDialog").dialog("open");
				$("#btnCncl").focus();
			}
			
		}else
		//F1
		if(event.which == 112){
			event.preventDefault();
			if(!$("#searchItemsDialog").parents(".ui-dialog").is(":visible")){
				$("#helpDialog").dialog("open");
			}
		}else
//		F10
		 if(event.which == 121)
		 {
			 if(!$("#searchItemsDialog").parents(".ui-dialog").is(":visible"))
			 {
//				 if($("#tableBody > tr").size() !=0){
//					 loadPrintView();
				 if($("#discountPercent").val().trim() == ""){
					 if($("#tableBody > tr").size() !=0){
						 /*if($("#return").val() >= 0 && $("#payOff").val() != 0){*/
							 localStorage.removeItem("talon");
							 localStorage.setItem("talon",$("#talon").val().trim());
							 localStorage.setItem("customerCheck",$("#customerCheck").prop("checked").toString());
							 localStorage.setItem("customers",$("#customers").val().trim());
							 localStorage.setItem("bankCheck",$("#bankCheck").prop("checked").toString());
							 localStorage.setItem("banks",$("#banks").val().trim());
							 $.ajax({
									url: "item-group",
									success: function(result)
									{
									}
								});
							window.location = "facture.jsp";
						/* }else{
							 alert("Төлбөр Хийгээгүй байна");
						 }*/
					 }else{
		  				 alert("Хоосон падан өгөхгүй");
		  			 }
				 }
				 else{
					 alert("Хөнгөлөлт оруулах боломжгүй");
				 }
			}else{
				
			}
		 }
		 else
		if (event.which == 9)
	  	{
			if ($("input:focus").attr("id") == "paid")
			{
				event.preventDefault();
				$("#barcode").focus();
			}
	  	}
		//F3
		else if (event.which == 114)
	  	{
			if(!$("#searchItemsDialog").parents(".ui-dialog").is(":visible")){
				event.preventDefault();
			  	$("#barcode").focus();
			}else{
				event.preventDefault();
			  	$("#searchByBarcode").focus();
			}
			
	  	}
		//F2
		 if (event.which == 113)
		  	{
					event.preventDefault();
				if(!$("#searchItemsDialog").parents(".ui-dialog").is(":visible")){
					if($("#cardNumber").val()==""){
						$("#discountPercent").prop("disabled", false);
					  	$("#discountPercent").focus();
					}else{
						alert("Давхар хөнгалөлт авч болохгүй");
					}
				
				 }else{
					 $("#searchByName").focus();
				 }
		  	}
		 //f4
		else if (event.which == 115 &&! event.altKey)
	  	{
			event.preventDefault();
			 if(!$("#searchItemsDialog").parents(".ui-dialog").is(":visible")){
				$("#discountPercent").prop("disabled", true);
				$.ajax(
				{
					url: "remove-discount-card",
					success: function(result)
					{
						$("#tableBody").html(result);
						initTableEvents();
						$("#cardNumber").val("");
						$("#cardOwner").text("");
						$("#discountPercent").val("");
						$("#discountType").val("");
						checkAll();
					}
				});
			 }
	  	}
		 //f5
	  	else if (event.which == 116)
	  	{
	  		event.preventDefault();
	  		 if(!$("#searchItemsDialog").parents(".ui-dialog").is(":visible")){
		  		if(!$("#newQuant").is(":focus")&& !$("#newQuant1").is(":focus")){
			  	$("#updateButton").trigger('click');
			  	checkAll();
		  		}
	  		 }
	  	}
		 //f6
	  	else if (event.which == 117)
	  	{
	  		event.preventDefault();
	  		 if(!$("#searchItemsDialog").parents(".ui-dialog").is(":visible")){
			  	window.print();
	  		 }
	  	}
		 //f7
	  	else if (event.which == 118)
	  	{
	  		event.preventDefault();
	  		 if(!$("#searchItemsDialog").parents(".ui-dialog").is(":visible")){
	  			 if($("#payOff").val()>0){
	  				$("#paid").val("");
				  	$("#paid").focus();
	  			 }else{
	  				 alert("Бараа байхгүй байна");
	  			 }
			  		
	  		 }
	  	}
		 //f8
	  	else if (event.which == 119)
	  	{
	  		event.preventDefault();
	  		 if(!$("#searchItemsDialog").parents(".ui-dialog").is(":visible")){
	  			 window.location.assign("report.jsp");
	  		 }
	  	}
		 //f9
	  	else if (event.which == 120)
	  	{
	  		event.preventDefault();
	  		 if(!$("#searchItemsDialog").parents(".ui-dialog").is(":visible")){
	  			//costca start 
		  		if ($("#tableBody > tr[class='success']").size() == 1)
		  		{
		  			$("#unitPrice").prop("disabled", false);
			  		$("#unitPrice").focus();
		  		}
		  		else
		  		{
		  			$("#unitPrice").prop("disabled", true);
		  		}
		  		
	  			 //costca end
	  		}else{
	  			$("#selectItems").focus();
	  			$("#selectItems").trigger("click");
	  		}
	  	}
		 //Up arrow
	  	else
	  		if (event.which == 38)
	  	{
	  		if (!$("#barcode").is(":focus"))
	  		{
	  			event.preventDefault();
	  			if(!$("#searchItemsDialog").parents(".ui-dialog").is(":visible")){
			  		var i = $("#tableBody > tr[class='success']").index();
				  	var s = $("#tableBody > tr").size();
				  	if (s > 0)
				  	{
				  		if (i <= s - 1)
					  	{
					  		if (i > 0)
						  	{
						  		i--;
						  	}
						  	else
						  	{
						  		i = $("#tableBody > tr").size() - 1;
						  	}
						  	$("#tableBody > tr[class='success']").removeClass();
						  	$("#tableBody > tr").eq(i).addClass("success");
						  	$("#itemName").val($("#tableBody > tr").eq(i).children().eq(thName).text().trim());
						  	$("#unitPrice").val($("#tableBody > tr").eq(i).children().eq(thPrice).text().trim());
						  	$("#unit").text($("#tableBody > tr").eq(i).children().eq(thUnit).text().trim());
						  	$("#quantity").val($("#tableBody > tr").eq(i).children().eq(thQuant).text().trim());
					  	}
				  		$("#mainTableSlim").scrollTop(i*12);
					}
				  	checkAll();
	  			}else{
	  				var i = $("#searchResultBody > tr[class='success']").index();
				  	var s = $("#searchResultBody > tr").size();
				  	if (s > 0)
				  	{
				  		if (i <= s - 1)
					  	{
					  		if (i > 0)
						  	{
						  		i--;
						  	}
						  	else
						  	{
						  		i = $("#searchResultBody > tr").size() - 1;
						  	}
						  	$("#searchResultBody > tr[class='success']").removeClass();
						  	$("#searchResultBody > tr").eq(i).addClass("success");
					  	}
				  		$("#searchItemsDialog").scrollTop(i*12);
					}
	  			}
	  		}
	  	}
//		 Down arrow
	  	else if (event.which == 40)
	  	{
	  		if (!$("#barcode").is(":focus"))
	  		{
	  			event.preventDefault();
	  			if(!$("#searchItemsDialog").parents(".ui-dialog").is(":visible")){
				  	var i = $("#tableBody > tr[class='success']").index();
				  	var s = $("#tableBody > tr").size();
				  	if (s > 0)
				  	{
					  	if (i <= s - 1)
					  	{
						  	if (i != s - 1)
						  	{
							  	i++;
						  	}
						  	else
						  	{
							  	i = 0;
						  	}
						  	$("#tableBody > tr[class='success']").removeClass();
						  	$("#tableBody > tr").eq(i).addClass("success");
						  	$("#tableBody > tr[class='success']").focus();
						  	$("#itemName").val($("#tableBody > tr").eq(i).children().eq(thName).text().trim());
						  	$("#unitPrice").val($("#tableBody > tr").eq(i).children().eq(thPrice).text().trim());
						  	$("#unit").text($("#tableBody > tr").eq(i).children().eq(thUnit).text().trim());
						  	$("#quantity").val($("#tableBody > tr").eq(i).children().eq(thQuant).text().trim());
					  	}
					  	$("#mainTableSlim").scrollTop(i*12);
				  	}
				  	checkAll();
	  			}
		  		else{
		  			if(!$("#selectItems").is(":focus")){
		  				event.preventDefault();
		  			}
		  			var i = $("#searchResultBody > tr[class='success']").index();
				  	var s = $("#searchResultBody > tr").size();
				  	if (s > 0)
				  	{
					  	if (i <= s - 1)
					  	{
						  	if (i != s - 1)
						  	{
							  	i++;
						  	}
						  	else
						  	{
							  	i = 0;
						  	}
						  	$("#searchResultBody > tr[class='success']").removeClass();
						  	$("#searchResultBody > tr").eq(i).addClass("success");
					  	}
					  	
				  	}
				  	$("#searchItemsDialog").scrollTop(i*12);
	  			}
	  		}
	  	}
		 //delete
		//costca start
		 /*
		 else 
			 if (event.which == 46)
	  	{
	  		if (!$("#barcode").is(":focus") && !$("#discountPercent").is(":focus")
	  				&& !$("#searchByName").is(":focus")& !$("#searchByBarcode").is(":focus")
	  				&& !$("#paid").is(":focus")&& !$("#quantity").is(":focus")
	  				&& !$("#cardNumber").is(":focus")&& !$("#talon").is(":focus")
	  				&& !$("#newQuant").is(":focus")&& !$("#newQuant1").is(":focus")
	  		){
	  			$("#deleteButton").trigger("click");
	  			checkAll();
	  		}
	  	}
	  	*/
		 //costca end
  	});

	$("#discountPercent").focusout(function()
  	{
		var bool = setDiscountPercentFocusOut();
		if(!bool){
			$("#discountPercent").focus();
		}
  	});
	$("#quantity").focusout(function()
  	{
		this.value = this.value.replace(/[^0-9\.]/g, '');	
		if(this.value == ''){
			this.value = 1;
		}
  	});
	$("#discountPercent").keypress(function(event)
			{
				if(event.which == 13)
				{
				event.preventDefault();
				$("#barcode").focus();
				}
			});
	$("#cardNumber").keypress(function(event)
	{
		if(event.which == 13)
		{
			var hasCardNumber = 0;
			event.preventDefault();
			$.ajax(
			{
				url: "check-card-number",
				data:
				{
					"cardNumber": $("#cardNumber").val()
				},
				success: function(result)
				{
					hasCardNumber = result;
				}
			});
			
			if (hasCardNumber == 1)
			{
				$.ajax(
				{
					url: "get-card-users",
					data:
					{
						"cardNumber": $("#cardNumber").val()
					},
					success: function(result)
					{
						$("#cardUsersBody").html(result);
						$("#cardUsersBody tr").click(function(event)
						{
							$("#cardOwner").text($(this).children().eq(0).text().trim());
							$("#discountPercent").val($(this).children().eq(3).text().trim());
							$("#discountType").val($(this).children().eq(2).text().trim());
							$("#cardUsersDialog").dialog("close");
							var tmpStr = $(this).attr("id").split("-");
							var cardId = tmpStr[1];
							tmpStr = $(this).children().eq(0).attr("id").split("-");
							var customerId = tmpStr[1];
							$.ajax(
							{
								url: "set-discount-card",
								data:
								{
									"cardId": cardId,
									"customerId": customerId,
									"customerName": $(this).children().eq(0).text().trim(),
									"cardNumber": $(this).children().eq(1).text().trim(),
									"type": $(this).children().eq(2).text().trim(),
									"discountPercent": $(this).children().eq(3).text().trim(),
									"partOwner": $(this).children().eq(4).text().trim()
								},
								success: function(result)
								{
									$("#tableBody").html(result);
									initTableEvents();
									checkAll();
								}
							});
						});
						$("#cardUsersDialog").dialog("open");
					}
				});
			}
			else
			{
				alert("Таны оруулсан картын дугаар бүртгэлгүй байна!");
			}
		}
	});
	
	$("#barcode").keypress(function(event)
	{
		if(event.which == 13)
		{
			event.preventDefault();
			addItem($("#barcode").val().trim(), $("#quantity").val().trim(), $("#assetAccounts").val().trim());
		}
	});
	$("#quantity").keypress(function(event)
  	{
  		if(event.which == 13)
	  	{
  			event.preventDefault();
  			if ($("#barcode").val() != "")
  			{
  				addItem($("#barcode").val().trim(), $("#quantity").val().trim(), $("#assetAccounts").val().trim());
  			}
  			else
  			{
  				if ($(this).val() != "")
  				{
  					if (!isNaN($(this).val()))
  				  	{
  				  		if ($(this).val() > 0)
  					  	{
			  				update($("#tableBody > tr[class='success']").attr("id"), $(this).val());
  					  	}
  				  		else
  				  		{
  				  			alert("Тоо хэмжээг 0-с их өгнө үү!");
  				  		}
  					}
  					else
  					{
  						alert("Тоо хэмжээн дунд үсэг, тэмдэгт байна!");
  					}
  				}
  				else
  				{
  					alert("Тоо, хэмжээ хоосон байна.");
  				}
  			}
	  	}
  	});
  	$("#addItem").click(function(event)
  	{
  		event.prventDeafult();
  		addItem($("#barcode").val().trim(), $("#quantity").val().trim(), $("#assetAccounts").val().trim());
  	});
  	
  	$("#unitPrice").keypress(function(event)
  	{
  		if (event.which == 13)
  		{
  			event.preventDefault();
  			editPrice();
  		}
  	});
  	
  	$("#tableBody > tr").click(function()
  	{
  		clickedRow($(this));
  	});
  	
  	$("#tableBody > tr").dblclick(function()
  	{
  		bindForm($(this));
  	});
  	
  	/*$("body").click(function(event)
  	{
  		if (event.target.nodeName != "TD")
  		{
  			if (event.target.getAttribute('id') != "updateButton" && event.target.getAttribute('id') != "deleteButton")
  			{
  				$("#tableBody > tr[class='success']").removeClass();
  				checkAll();
  			}
  		}
  	});*/
  	
  	$("#updateButton").click(function(event)
  	{
  		event.preventDefault();
  		if(!$("#newQuant").is(":focus") &&!$("#newQuant1").is(":focus")){
  			bindForm($("#tableBody > tr[class='success']"));
  		}
  	});
  	
  	$("#deleteButton").click(function(event)
  	{
  		event.preventDefault();
  		var id = $("#tableBody > tr[class='success']").attr("id");
  		$.ajax(
  		{
  			url: "remove-item",
  			data: { "id": id },
  			success: function(result)
  			{
  				$("#tableBody > tr[class='success']").remove();
  			}
  		});
  		
  		if ($("#tableBody > tr").size() > 0)
  		{
  			$("#tableBody > tr:eq(" + ($("#tableBody > tr").size() - 1) + ")").addClass("success");
  			$("#itemName").val($("#tableBody > tr[class='success']").children().eq(thName).text().trim());
	  		$("#quantity").val($("#tableBody > tr[class='success']").children().eq(thQuant).text().trim());
	  		$("#unitPrice").val($("#tableBody > tr[class='success']").children().eq(thPrice).text().trim());
  		}
	  	else
	  	{
	  		$("#quantity").val("1");
	  		$("#itemName").val("");
	  		$("#unitPrice").val("");
	  		$("#unit").text("ш");
	  	}
  		checkAll();
  	});
  	
  	$("#clearButton").click(function(event)
  	{
  		$.ajax(
  		{
  			url: "clear-items",
  		  	success: function(result)
  		  	{
  		  		$("#tableBody").html("");
  		  		$("#quantity").val("1");
  		  		$("#itemName").val("");
  		  		$("#unitPrice").val("");
  		  		$("#unit").text("ш");
  		  		checkAll();
  		  	}
  		});
  		event.preventDefault();
  	});
  	
  	$("#payButton").click(function(event)
  	{
  		event.preventDefault();
  		purchase();
  	});
  	
  	$("#paid").keyup(function(event)
  	{
  		if ($("#payOff").val() > 0)
  		{
	  		if (event.which == 13 && $("#return").val() >= 0)
	  		{
	  			event.preventDefault();
	  			loadPrintView();
	  			purchase();
	  		}
  		}
  		checkAll();
  	});
  	$("#paid").focusout(function()
  	{
  		if(this.value == ""){
  			this.value = 0;
  		}
  	});
  	$("#selectItems").click(function(event){
  		event.preventDefault();
  		if($("#selectItems").is(':focus')){
	  		if($("#searchResultBody > tr").size() > 0){
	  			var i = $("#searchResultBody > tr[class='success']").index();
	  			addItem($("#searchResultBody > tr").eq(i).children().eq(1).text().trim(), 1, $("#searchResultBody > tr").eq(i).children().eq(3).text().trim());
				$("#searchItemsDialog").dialog("close");
	  		}else
	  		{
	  			alert("Бараа сонгоно уу!");
	  		}
  		}
  	});
  	$("#searchItems").click(function(event)
  	{
  		event.preventDefault();
	  		if ($("#searchByName").val().trim() != "" || $("#searchByBarcode").val().trim() != "")
	  		{
	  			if (!isNumber($("#searchByMinPrice").val()))
	  			{
	  				$("#searchByMinPrice").val($("#slider-range").slider("option", "min"));
	  				$("#slider-range").slider("option", "values", [$("#searchByMinPrice").val(), $("#searchByMaxPrice").val()]);
	  			}
	  			
	  			if (!isNumber($("#searchByMaxPrice").val()))
	  			{
	  				$("#searchByMaxPrice").val($("#slider-range").slider("option", "max"));
	  				$("#slider-range").slider("option", "values", [$("#searchByMinPrice").val(), $("#searchByMaxPrice").val()]);
	  			}
	  			$.ajax(
	  			{
	  				url: "searchItems",
	  				data:
	  				{
	  					"itemName": $("#searchByName").val().trim(),
	  					"barcode": $("#searchByBarcode").val().trim(),
	  					"minPrice": $("#searchByMinPrice").val().trim(),
	  					"maxPrice": $("#searchByMaxPrice").val().trim(),
	  					"assetAcc":$("#assetAccounts").val().trim()
	  				},
	  				success: function(result)
	  				{
	  					$("#searchResultBody").html(result);
		  				$("#searchResultBody > tr").click(function(event)
	  					{
	  						event.preventDefault();
	  						$("#searchResultBody > tr[class='success']").removeClass();
	  						$(this).addClass("success");
	  					});
	  					
		  				$("#searchResultBody > tr").dblclick(function(event)
		  				{
		  					event.preventDefault();
		  					addItem($(this).children().eq(1).text().trim(), 1, $(this).children().eq(3).text().trim());
		  					$("#searchResultBody > tr[class='success']").removeClass();
		  					$("#searchItemsDialog").dialog("close");
		  				});
	  				}
	  			});
	  		}
	  		else
	  		{
	  			alert("Хайх утгаа оруулна уу!");
	  		}
	  		
  	});
  	$("#searchByBarcode").keydown(function(event)
  	{
  		if(event.which == 13){
  			$("#searchItems").trigger('click');
  		}
  	});
  	$("#searchByName").keydown(function(event)
  	{
  		if(event.which == 13){
	  		$("#searchItems").trigger('click');
	  	}
  	});
	$("#searchByMinPrice").keydown(function(event)
  	{
  		if(event.which == 13){
	  		$("#searchItems").trigger('click');
	  	}
  	});
	$("#searchByMaxPrice").keydown(function(event)
  	{
  		if(event.which == 13){
	  		$("#searchItems").trigger('click');
	  	}
  	});
	$("#assetAccounts").change(function(){
		alert("");
		$.ajax(
				{
					url: "get-barcodes",
					data:
					{
						"assetAccounts": $("#assetAccounts").val()
					},
					success: function(result)
					{
						var barcodes = result.split(",");
						$("#barcode").autocomplete(
						{
							source: function(request, response)
							{
								var matches = $.map(barcodes, function(tag)
								{
									if (tag.toUpperCase().indexOf(request.term.toUpperCase()) === 0)
									{
										return tag;
									}
								});
								response(matches);
							}
						});
					}
				});
	});
	
}
function setDiscountPercentFocusOut(){
	if($("#discountPercent").val() != ""){
		if (!isNaN($("#discountPercent").val()))
	  	{
	  		if ($("#discountPercent").val() > 0)
		  	{
	  			if($("#discountPercent").val() <=100){
		  			$.ajax(
							{
								url: "set-discount-All",
								data:
								{
									"discountPercent": $("#discountPercent").val()
								},
								success: function(result)
								{
									$("#tableBody").html(result);
									initTableEvents();
									checkAll();
									
								}
							});
		  			return true;
	  			}
	  			else{
	  				alert("Хөнгөлөлт 100-с бага өгнө үү!");
	  			}
		  	}
	  		else
	  		{
	  			alert("Хөнгөлөлт 0-с их өгнө үү!");
	  		}
		}
		else
		{
			alert("Хөнгөлөлт дунд үсэг, тэмдэгт байна!");
		}	
		return false;
		}
		else{
			$("#discountPercent").prop("disabled", true);
			$.ajax(
			{
				url: "remove-discount-card",
				success: function(result)
				{
					$("#tableBody").html(result);
					initTableEvents();
					$("#cardNumber").val("");
					$("#cardOwner").text("");
					$("#discountPercent").val("");
					$("#discountType").val("");
					checkAll();
				}
			});
		}
	return true;
	
}
function checkAll()
{
	var barcode = $("#barcode").val();
	var quantity = $("#quantity").val();
	if (barcode.length > 0 && quantity.length > 0)
	{
		$("#addItem").prop("disabled", false);
	}
	else
	{
		$("#addItem").prop("disabled", true);
	}
	
	$("#clearButton").prop("disabled", true);
	$("#deleteButton").prop("disabled", true);
	$("#updateButton").prop("disabled", true);
	$("#payButton").prop("disabled", true);
	
	if ($("#tableBody > tr").size() <= 0)
	{
		$("#calTotal").val("0");
		$("#discountTotal").val("0");
	  	$("#payOff").val("0");
	  	$("#paid").prop("disabled", true);
	  	$("#paid").val("0");
	  	$("#return").val("0");
	  	$("#itemCount").val("Нийт: 0");
	}
	else
	{
		$("#itemCount").val("Нийт: " + $("#tableBody > tr").size());
		$("#clearButton").prop("disabled", false);
		var total = 0;
	  	var rowSize = $("#tableBody > tr").size();
	  	for (var i = 0; rowSize > i; i++)
	  	{
	  		total = total + parseFloat($("#tableBody > tr:eq(" + i + ")").children().eq(thTotal).text().trim());
	  	}
	  	$("#calTotal").val(total);
	  	
	  	var discountTotal = 0;
	  	for (var i = 0; rowSize > i; i++)
	  	{
	  		discountTotal = discountTotal + parseFloat($("#tableBody > tr:eq(" + i + ")").children().eq(thDiscount).text().trim());
	  	}
	  	$("#discountTotal").val(discountTotal);
	  	var payOff = total - discountTotal;
	  	if (payOff > 0)
	  	{
	  		$("#paid").prop("disabled", false);
	  	}
	  	$("#payOff").val(payOff);
	  	var ret = $("#paid").val() - payOff;
  		$("#return").val(ret);
  		if (ret > 0)
  		{
  			$("#payButton").prop("disabled", false);
  		}
  		else if (ret == 0 && $("#paid").val() >= 0)
  		{
  			$("#payButton").prop("disabled", false);
  		}
  		else
  		{
  			$("#payButton").prop("disabled", true);
  		}
	  	
	  	var crs = $("#tableBody > tr[class='success']").size();
	  	if (crs == 1)
	  	{
	  		$("#deleteButton").prop("disabled", false);
			$("#updateButton").prop("disabled", false);
	  	}
	}
}