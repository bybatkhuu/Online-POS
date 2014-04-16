var thName = 0;
var thQuant = 1;
var thUnit = 2;
var thPrice = 3;
var thTotal = 4;
var thSale = 5;

$(document).ready(function()
{
	startTime();
	ajaxSetup();
	initSearchDialog();
	initOrderNum();
	initBarcode();
	initSearchSlider();
	initTableSlim();
	
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
    var dateTimeStr = year + "/" + month + "/" + date + " - " + hour + ":" + minute + ":" + second + " <b class='blue'>" + day + "</b>";
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
		async: false
	});
	
	$(document).ajaxStart(function()
    {
		$("#loadingDialog").dialog("open");
	}).ajaxStop(function()
	{
		$("#loadingDialog").dialog("close");
    }).ajaxError(function()
    {
    	errorDialog("Сервертэй холбогдохгүй буюу серверээс холболт тасарсан байна!");
    });
}

function errorDialog(message)
{
	$("#errorMessage").html("<strong><i class='icon-remove'></i></strong>&nbsp; " + message);
	$("#errorDialog").dialog("open");
}

function initSearchDialog()
{
	$("#searchItemsDialog").dialog(
	{
		autoOpen: false,
		modal: true,
		width: 550,
		height: 500
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
      	height: '356px',
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

function addItem(barcode, quantity, serial)
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
					  	data: { "barcode" : barcode },
					  	success: function(result)
					  	{
					  		isBarcode = result;
					  	}
				  	});
				  	if (isBarcode == 1)
				  	{
			  			if (serial != "")
			  			{
			  				$.ajax(
			  				{
			  					url: "serial-has-item",
			  					data:
			  					{
			  						"barcode" : barcode,
			  						"serial" : serial
			  					},
								success: function(result)
								{
									if (result != 1)
									{
										alert("'" + serial + "' гэсэн сери тухайн баркодон дээр, танай салбарт олдохгүй байна!");
										$("#serial").val("");
										serial = "";
									}
								}
							});
			  			}
			  			
			  			$.ajax(
					  	{
					  		url: "add-item",
						  	data:
						  	{
						  		"barcode" : barcode,
							  	"quantity" : quantity,
							  	"serial" : serial
					      	},
					      	success: function(result)
					      	{
					      		$("#tableBody").html(result);
					      		$("#tableBody > tr").bind("click", function()
						    	{
					      			clickedRow($(this));
						    	});
					      		$("#tableBody > tr").bind("dblclick", function()
						    	{
					      			bindForm($(this));
								});
					    	  	$("#unitPrice").val($("#tableBody > tr[class='success']").children().eq(thPrice).text());
					    	  	$("#itemName").val($("#tableBody > tr[class='success']").children().eq(thName).text());
					    	  	$("#unit").text($("#tableBody > tr[class='success']").children().eq(thUnit).text());
					    	  	$("#quantity").val("1");
					    	  	$("#barcode").val("");
					    	  	$("#serial").val("");
					    	  	checkAll();
					      	}
					  	});
    			  	}
				  	else
				  	{
				  		alert("'" + barcode + "' гэсэн баркод танай салбарт олдохгүй байна!");
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
	var oldQuant = parseFloat($(element).children().eq(thQuant).text());
	var price = parseFloat($(element).children().eq(thPrice).text());
	var id = $(element).attr("id");
	$(element).children().first().next().html
	(
		"<input type='text' value='' size='1' class='input-sm' id='newQuant' />"
	);
	
	$("#newQuant").focus();
	
	$("#newQuant").keypress(function(event)
	{
		if (event.which == 13)
		{
			updateFromTable(element, id, price, oldQuant);
		}
	});
  	$("#newQuant").focusout(function()
  	{
  		updateFromTable(element, id, price, oldQuant);
  	});
}
function updateFromTable(element, id, price, oldQuant)
{
	var newQuant = parseFloat($("#newQuant").val());
	$("#newQuant").remove();
	if (oldQuant != newQuant && !isNaN(newQuant) && 0 < newQuant)
	{
		update(id, newQuant);
	}
	else
	{
		$(element).children().eq(thQuant).text(oldQuant);
	}
}
function clickedRow(element)
{
	$("#tableBody > tr[class='success']").removeClass();
	$(element).addClass("success");
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
			$("#tableBody > tr").bind("click", function()
			{
				clickedRow($(this));
			});
		    $("#tableBody > tr").bind("dblclick", function()
			{
		    	bindForm($(this));
			});
			checkAll();
		}
	});
}

function purchase()
{
	window.print();
	$.ajax(
	{
		url: "purchase-items",
		data: { "orderNum" : $("#talon").val() },
	  	success: function(result)
	  	{
	  		var jsonData = eval("(" + result + ")");
	  		if (jsonData.isPurchased != "true")
	  		{
	  			alert("Алдаа: Гүйлгээ амжилтгүй боллоо!\nТа хуудсаа дахин дуудаж гүйлгээгээ хийнэ үү!");
	  		}
	  		else
	  		{
	  			$("#talon").val(jsonData.talon);
		  		$("#print-talon").val(jsonData.talon);
		  		$("#tableBody").html("");
		  		$("#quantity").val("1");
		  		$("#unit").text("ш");
		  		$("#unitPrice").val("");
		  		$("#itemName").val("");
		  		checkAll();
	  		}
	  	}
	});
	$("#barcode").focus();
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
					$("#tableBody > tr[class='success']").children().eq(thPrice).text($("#unitPrice").val());
					var price = parseFloat($("#unitPrice").val());
					var quantity = parseFloat($("#tableBody > tr[class='success']").children().eq(thQuant).text());
					var total = price * quantity;
					$("#tableBody > tr[class='success']").children().eq(thTotal).text(total);
					checkAll();
				}
			});
		}
		else
		{
			alert("Үнэ засах хэсэгт зөвхөн тоо оруулна уу!");
			$("#unitPrice").val($("#tableBody > tr[class='success']").children().eq(thPrice).text());
		}
	}
	else
	{
		alert("Засах үнээ оруулна уу!");
		$("#unitPrice").val($("#tableBody > tr[class='success']").children().eq(thPrice).text());
	}
	$("#unitPrice").prop("disabled", true);
}

function calculate()
{
	var str = "<table>";
	for (var i = 0; i < $("#tableBody > tr").size(); i++)
	{
		str = str + "<tr>";
			str = str + "<td class='col-xs-4'>" + $("#tableBody > tr:eq(" + i + ")").children().eq(thName).html() + "</td>";
			str = str + "<td class='col-xs-1'>" + $("#tableBody > tr:eq(" + i + ")").children().eq(thQuant).html() + "</td>";
			str = str + "<td class='col-xs-3 align-left'>" + $("#tableBody > tr:eq(" + i + ")").children().eq(thPrice).html() + "</td>";
			str = str + "<td class='col-xs-2 align-right'>" + $("#tableBody > tr:eq(" + i + ")").children().eq(thTotal).html() + "</td>";
		str = str + "</tr>";
	}
	str = str + "</table>";
	$("#print-items").html(str);
	$("#print-s").html($("#tableBody > tr").size());
	
	$("#tableTotal").val("Нийт: " + $("#tableBody > tr").size());
	$("#clearButton").prop("disabled", false);
	
}

function initEventHandlers()
{
	$(document).keydown(function(event)
	{
		if (event.keyCode == 70 && event.ctrlKey)
		{
			event.preventDefault();
			$("#searchItemsDialog").dialog("open");
		}
		
		if (event.which == 9)
		{
			if ($("input:focus").attr("id") == "paid")
			{
				event.preventDefault();
				$("#barcode").focus();
			}
		}
		else if (event.which == 113)
	  	{
	  		event.preventDefault();
		  	$("#insuranceCheck").trigger('click');
		  	var check = $("#insuranceCheck").prop("checked");
	  		$.ajax(
	  		{
	  			url: "check-insurance",
	  			data: { "hasInsurance" : check },
	  			success: function(result)
	  			{
	  			}
	  		});
	  	}
		else if (event.which == 114)
	  	{
			event.preventDefault();
		  	$("#barcode").focus();
	  	}
	  	else if (event.which == 115)
	  	{
	  		event.preventDefault();
		  	$("#serial").focus();
	  	}
	  	else if (event.which == 116)
	  	{
	  		event.preventDefault();
		  	$("#updateButton").trigger('click');
		  	checkAll();
	  	}
	  	else if (event.which == 117)
	  	{
	  		event.preventDefault();
		  	window.print();
	  	}
	  	else if (event.which == 118)
	  	{
	  		event.preventDefault();
	  		$("#paid").val("");
		  	$("#paid").focus();
	  	}
	  	else if (event.which == 120)
	  	{
	  		event.preventDefault();
	  		$("#unitPrice").prop("disabled", false);
	  		$("#unitPrice").focus();
	  	}
	  	else if (event.which == 38)
	  	{
	  		if (!$("#barcode").is(":focus"))
	  		{
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
					  	$("#itemName").val($("#tableBody > tr").eq(i).children().eq(thName).text());
					  	$("#unitPrice").val($("#tableBody > tr").eq(i).children().eq(thPrice).text());
					  	$("#unit").text($("#tableBody > tr").eq(i).children().eq(thUnit).text());
					  	$("#quantity").val($("#tableBody > tr").eq(i).children().eq(thQuant).text());
				  	}
				}
			  	checkAll();
	  		}
	  	}
	  	else if (event.which == 40)
	  	{
	  		if (!$("#barcode").is(":focus"))
	  		{
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
					  	$("#itemName").val($("#tableBody > tr").eq(i).children().eq(thName).text());
					  	$("#unitPrice").val($("#tableBody > tr").eq(i).children().eq(thPrice).text());
					  	$("#unit").text($("#tableBody > tr").eq(i).children().eq(thUnit).text());
					  	$("#quantity").val($("#tableBody > tr").eq(i).children().eq(thQuant).text());
				  	}
			  	}
			  	checkAll();
	  		}
	  	}
	  	else if (event.which == 46)
	  	{
		  	$("#deleteButton").trigger("click");
		  	checkAll();
	  	}
  	});
	
	$("#unitPrice").keypress(function(event)
	{
		if (event.which == 13)
	  	{
			event.preventDefault();
			editPrice();
	  	}
	});

	$("#barcode").keyup(function(event)
	{
		var barcode = $("#barcode").val().trim();
		var quantity = $("#quantity").val().trim();
		if (barcode != "" && quantity != "")
		{
			$("#addItem").prop("disabled", false);
		}
		else
		{
			$("#addItem").prop("disabled", true);
		}
	});
	$("#quantity").keyup(function(event)
	{
		var barcode = $("#barcode").val().trim();
		var quantity = $("#quantity").val().trim();
		if (barcode != "" && quantity != "")
		{
			$("#addItem").prop("disabled", false);
		}
		else
		{
			$("#addItem").prop("disabled", true);
		}
	});	
	
	$("#barcode").keypress(function(event)
	{
		if(event.which == 13)
		{
			event.preventDefault();
			addItem($("#barcode").val().trim(), $("#quantity").val().trim(), $("#serial").val());
		}
	});
	$("#quantity").keypress(function(event)
  	{
  		if(event.which == 13)
	  	{
  			event.preventDefault();
  			if ($("#barcode").val() != "")
  			{
  				addItem($("#barcode").val().trim(), $("#quantity").val().trim(), $("#serial").val());
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
  		event.preventDefault();
  		addItem($("#barcode").val().trim(), $("#quantity").val().trim(), $("#serial").val().trim());
  	});

  	$("#serial").keypress(function(event)
  	{
  		if(event.which == 13)
	  	{
  			
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
  		bindForm($("#tableBody > tr[class='success']"));
  		event.preventDefault();
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
  			$("#itemName").val($("#tableBody > tr[class='success']").children().eq(thName).text());
	  		$("#quantity").val($("#tableBody > tr[class='success']").children().eq(thQuant).text());
	  		$("#unitPrice").val($("#tableBody > tr[class='success']").children().eq(thPrice).text());
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
	  			purchase();
	  		}
  		}
  		checkAll();
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
  					"maxPrice": $("#searchByMaxPrice").val().trim()
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
	  					addItem($(this).children().eq(1).text(), 1, $(this).children().eq(2).text());
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
}

function checkAll()
{
	$("#print-talon").html($("#talon").val());
	$("#print-pos").html($("#pos").val());
	$("#print-date").html($("#time").html());
	$("#print-cash").html($("#cash").val());
	var str = "<table>";
	for (var i = 0; i < $("#tableBody > tr").size(); i++)
	{
		str = str + "<tr>";
			str = str + "<td class='col-xs-4'>" + $("#tableBody > tr:eq(" + i + ")").children().eq(thName).html() + "</td>";
			str = str + "<td class='col-xs-1'>" + $("#tableBody > tr:eq(" + i + ")").children().eq(thQuant).html() + "</td>";
			str = str + "<td class='col-xs-3 align-left'>" + $("#tableBody > tr:eq(" + i + ")").children().eq(thPrice).html() + "</td>";
			str = str + "<td class='col-xs-2 align-right'>" + $("#tableBody > tr:eq(" + i + ")").children().eq(thTotal).html() + "</td>";
		str = str + "</tr>";
	}
	str = str + "</table>";
	$("#print-items").html(str);
	$("#print-s").html($("#tableBody > tr").size());
	
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
	  	$("#payOff").val("0");
	  	$("#paid").prop("disabled", true);
	  	$("#paid").val("0");
	  	$("#return").val("0");
	  	$("#tableTotal").val("Нийт: 0");
	}
	else
	{
		$("#tableTotal").val("Нийт: " + $("#tableBody > tr").size());
		$("#clearButton").prop("disabled", false);
		
		var total = 0;
	  	var rowSize = $("#tableBody > tr").size();
	  	for (var i = 0; rowSize > i; i++)
	  	{
	  		total = total + parseFloat($("#tableBody > tr:eq(" + i + ")").children().eq(thTotal).text());
	  	}
	  	$("#calTotal").val(total);
	  	var payOff = total;
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
  		else if (ret == 0 && $("#paid").val() > 0)
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
	$("#print-total").html($("#payOff").val());
	$("#print-paid").html($("#paid").val());
	$("#print-return").html($("#return").val());
}