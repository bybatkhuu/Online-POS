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
	$("#loadingDialog").dialog(
	{
		autoOpen: false,
		modal: true
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
    });
}

function initSearchDialog()
{
	$("#searchItemsDialog").dialog(
	{
		autoOpen: false,
		modal: true,
		width: 550,
		height: 400
	});
}

function initOrderNum()
{
	$.ajax(
    {
    	url: "get-order-num",
  	  	success: function(result)
  	  	{
  	  		$("#talon").val(result);
  	  		$("#print-talon").val(result);
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

function addItem(event)
{
	var barcode = $("#barcode").val().trim();
	if (barcode != "")
  	{
		var quantity = $("#quantity").val().trim();
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
				  		var barcodeHasItem = 0;
					  	$.ajax(
					  	{
					  		url: "barcode-has-item",
						  	data: { "barcode" : barcode },
						  	success: function(result)
						  	{
						  		barcodeHasItem = result;
						  	}
					  	});
				  		if (barcodeHasItem == 1)
				  		{
				  			var serial = $("#serial").val();
				  			var serialID = -1;
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
											alert(serial + " гэсэн серитэй бараа танай салбарт алга!");
											$("#serial").val("");
											serial = "";
										}
										else
										{
											serialID = 0;
										}
									}
								});
				  				if (serialID == 0)
				  				{
				  					$.ajax(
							  		{
							  			url: "get-serialID",
							  			data: { "serial" : serial },
										success: function(result)
										{
											serialID = result;
										}
							  		});
				  				}
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
						      		var jsonData = eval("(" + result + ")");
						    	  	if (jsonData.hasItem == "false")
						    	  	{
						    	  		var response = "<tr class='success' id='" + jsonData.ID + "'>" +
						    		 				 		"<td>" + jsonData.name + "</td>" +
						    		 				 		"<td class='text-right'>" + jsonData.quantity + "</td>" +
						    		 				 		"<td>" + jsonData.unit + "</td>" +
						    		 				 		"<td class='text-right'>" + jsonData.price + "</td>" +
						    		 				 		"<td class='text-right'>" + jsonData.total + "</td>" +
						    		 				 		"<td class='text-right hidden'>0</td>" +
						    		  				 	"</tr>";
						    	  		$("#tableBody > tr[class='success']").removeClass();
						    		  	$("#tableBody").append(response);
						    		  	$("#tableBody > tr[id='" + jsonData.ID + "']").bind("click", function()
						    		  	{
						    		  		clickedRow($(this));
						    		  	});
						    		  	$("#tableBody > tr[id='" + jsonData.ID + "']").bind("dblclick", function()
						    		  	{
						    		  		bindForm($(this));
								    	});
						    	  	}
						    	  	else
						    	  	{
						    	  		if (serial != "")
						    	  		{
							    	  		if (jsonData.serialID != serialID)
							    	  		{
							    	  			alert(jsonData.name + " гэсэн бараа өөр серитэй орсон байна!\nСерийг өөрчилж оруулна уу.");
							    	  		}
						    	  		}
						    	  		
						    	  		var response = 	"<td>" + jsonData.name + "</td>" +
		    		 				 				   	"<td class='text-right'>" + jsonData.quantity + "</td>" +
		    		 				 				   	"<td>" + jsonData.unit + "</td>" +
		    		 				 				   	"<td class='text-right'>" + jsonData.price + "</td>" +
		    		 				 				   	"<td class='text-right'>" + jsonData.total + "</td>" +
						    	  						"<td class='text-right hidden'>0</td>";
						    		  	$("tr[id='" + jsonData.ID + "']").html(response);
						    	  	}
						    	  	$("#unitPrice").val(jsonData.price);
						    	  	$("#itemName").val(jsonData.name);
						    	  	$("#unit").text(jsonData.unit);
						    	  	checkAll();
						    	  	$("#quantity").val("1");
						    	  	$("#barcode").val("");
						    	  	$("#serial").val("");
						      	}
						  	});
				  		}
				  		else
				  		{
				  			alert(barcode + " гэсэн баркодтой бараа олдохгүй байна!");
				  		}
    			  	}
				  	else
				  	{
				  		alert(barcode + " гэсэн баркод байхгүй байна!");
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
  	event.preventDefault();
}

function bindForm(element)
{
	var oldQuant = $(element).children().eq(thQuant).text();
	var price = parseFloat($(element).children().eq(thPrice).text());
	var id = $(element).attr("id");
	$(element).children().first().next().html
	(
		"<input type='text' value='" + oldQuant + "' size='1' class='input-sm' id='newQuant' />"
	);
	
	$("#newQuant").focus();
	$("#newQuant").keypress(function(event)
	{
		if (event.which == 13)
		{
			var newQuant = parseFloat($("#newQuant").val());
	  		$("#newQuant").remove();
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
	  				$(element).children().eq(thQuant).text(newQuant);
	  				var total = price * newQuant;
	  				$(element).children().eq(thTotal).text(total);
	  		  		checkAll();
	  			}
	  		});
		}
	});
  	$("#newQuant").focusout(function()
  	{
  		var newQuant = parseFloat($("#newQuant").val());
  		$("#newQuant").remove();
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
	  				$(element).children().eq(thQuant).text(newQuant);
	  				var total = price * newQuant;
	  				$(element).children().eq(thTotal).text(total);
	  				checkAll();
  			}
  		});
  	});
}
function clickedRow(element)
{
	$("#tableBody > tr[class='success']").removeClass();
	$(element).addClass("success");
	checkAll();
}

function purchase()
{
	window.print();
	$.ajax(
	{
		url: "purchase-items",
	  	success: function(result)
	  	{
	  		$("#talon").val(result);
	  		$("#print-talon").val(result);
	  		$("#tableBody").html("");
	  		checkAll();
	  	}
	});
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
		
		if (event.which == 113)
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
		  	$("#paid").focus();
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

	$("#barcode").keyup(function(event)
	{
		var barcode = $("#barcode").val();
		var quantity = $("#quantity").val();
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
		var barcode = $("#barcode").val();
		var quantity = $("#quantity").val();
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
			addItem(event);
		}
	});
	$("#quantity").keypress(function(event)
  	{
  		if(event.which == 13)
	  	{
  			event.preventDefault();
  			if ($("#barcode").val() != "")
  			{
  				addItem(event);
  			}
  			else
  			{
  				if ($(this).val() != "")
  				{
  					if (!isNaN($(this).val()))
  				  	{
  				  		if ($(this).val() > 0)
  					  	{
			  				$.ajax(
			  				{
			  					url: "update-item",
			  					data:
			  					{
			  						"id": $("#tableBody > tr[class='success']").attr("id"),
			  						"newQuant" : $(this).val()
			  					},
			  					success: function(result)
			  					{
			  						var res = eval(result);
			  						if (res == true)
			  						{
				  						var i = $("#tableBody > tr[class='success']").index();
				  						$("#tableBody > tr").eq(i).children().eq(thQuant).text($("#quantity").val());
				  			  			var total = (($("#tableBody > tr").eq(i).children().eq(thPrice).text() * 10) * ($("#quantity").val() * 10)) / 100;
				  			  			$("#tableBody > tr").eq(i).children().eq(thTotal).text(total);
				  			  			checkAll();
			  						}
			  					}
			  				});
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
  		addItem(event);
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
  		var id = $("#tableBody > tr[class='success']").attr("id");
  		$.ajax(
  		{
  			url: "remove-item",
  			data: { "id": id },
  			success: function(result)
  			{
  				$("#tableBody > tr[class='success']").remove();
  				checkAll();
  			}
  		});
  		if ($("#tableBody > tr").size() > 0)
  		{
  			$("#tableBody > tr:eq(" + ($("#tableBody > tr").size() - 1) + ")").addClass("success");
  		}
  		event.preventDefault();
  	});
  	
  	$("#clearButton").click(function(event)
  	{
  		$.ajax(
  		{
  			url: "clear-items",
  		  	success: function()
  		  	{
  		  		$("#tableBody").html("");
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
  			$.ajax(
  			{
  				url: "searchItems",
  				data:
  				{
  					"itemName": $("#searchByName").val().trim(),
  					"barcode": $("#searchByBarcode").val().trim()
  				},
  				success: function(result)
  				{
  					$("#searchResultBody").html(result);
  				}
  			});
  		}
  		else
  		{
  			alert("Хайх утгаа оруулна уу!");
  		}
  	});
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