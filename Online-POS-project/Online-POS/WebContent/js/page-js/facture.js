$(document).ready(function()
{
	var today = new Date();
	$("#year").html(checkTime(today.getFullYear()%100));
	$("#month").html(checkTime(today.getMonth()+1));
	$("#day").html(checkTime(today.getDate()));
	initEventHandlers();
	initLogoutDialog();
	document.getElementById("talon").innerHTML = localStorage.getItem("talon");
	document.getElementById("customerCheck").innerHTML = localStorage.getItem("customerCheck");
	document.getElementById("customers").innerHTML = localStorage.getItem("customers");
	$("#bankCheck").html(localStorage.getItem("bankCheck"));
	document.getElementById("banks").innerHTML = localStorage.getItem("banks");
	$("#saleName").focus();
	
});

function checkTime(i)
{
	if (i < 10)
    {
    	i = "0" + i;
    }
    return i;
}
function initLogoutDialog()
{
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
}

function initEventHandlers()
{
	
	$(document).keydown(function(event){
//		Ctrl+4
		if(event.keyCode == 52 && event.ctrlKey){
			event.preventDefault();
			$("#logoutDialog").dialog("open");
			$("#btnCncl").focus();
			
		}
//		F4
		if(event.keyCode == 115){
			event.preventDefault();
			window.location = "cash.jsp";
		}
		if ($("a:focus").attr("id") == "back")
		{
			event.preventDefault();
			$("#saleName").focus();
		}
	});
	
	$("#buyNumber").keyup(function(event)
	{
		if (this.value != this.value.replace(/[^0-9\.]/g, '')) 
		{
	       this.value = this.value.replace(/[^0-9\.]/g, '');
	       $("#buyNumber").css("background-color","pink");
	       alert('Зөвхөн тоо оруулна уу');
	    }else
	    {
	    	$("#buyNumber").css("background-color","white");
	    }
	});
	
	$("#saleNumber").keyup(function(event)
	{
		if (this.value != this.value.replace(/[^0-9\.]/g, ''))
		{
	       this.value = this.value.replace(/[^0-9\.]/g, '');
	       $("#saleNumber").css("background-color","pink");
	       alert('Зөвхөн тоо оруулна уу');
	    }
		else
		{
			$("#saleNumber").css("background-color","white");
		}
		
	});
	
	$("#saleNumber").keydown(function(event)
	{
		if(event.which == 13){
			$("#select").trigger("click");
		}
		$("#saleNumber").css("background-color","yellow");
	});
	$("#saleName").keydown(function(event)
			{
				if(event.which == 13){
					$("#select").trigger("click");
				}
			});
	$("#buyNumber").keydown(function(event)
	{
		if(event.which == 13){
			$("#select").trigger("click");
		}
		$("#buyNumber").css("background-color","yellow");
	});
	
	$("#select").click(function(event){
		event.preventDefault();
		if($("#saleName").val().trim() !="")
		{
			if($("#buyNumber").val().trim() !="")
			{
				if($("#buyNumber").val().trim().length ==7)
				{
					if($("#saleNumber").val().trim() !="")
					{
						if($("#saleNumber").val().trim().length ==7)
						{
							$("#saleNameShow").html($("#saleName").val());
							$("#buyNumberShow").html($("#buyNumber").val());
							$("#saleNumberShow").html($("#saleNumber").val());
							 purchaseFacture();
						}else
						{
							alert("Худалдан авагчийн pегистрийн дугаар 7 урттай тоо байна");
						}
					}else
					{
						alert("Худалдан авагчийн pегистрийн дугаар оруулна уу");
					}
				}else
				{
					 alert("Борлуулагчийн pегистрийн дугаар 7 урттай тоо байна");
				}
			}else
			{
				alert("Борлуулагчийн pегистрийн дугаар оруулна уу");
			}
		}else
		{
			alert("Худалдан авагчийн нэр оруулна уу");
		}
	});
	
	function purchaseFacture()
	{
		var type = "Cash";
		var otherId = "0";
		var check = localStorage.getItem("customerCheck");
		if (check == "true")
		{
			type = "Invoice";
			otherId = localStorage.getItem("customers");
		}
		else
		{
			check = localStorage.getItem("bankCheck");
			if (check =="true")
			{
				type = "Card";
				otherId = localStorage.getItem("banks");
			}
			else
			{
				type = "Cash";
				otherId = "0";
			}
		}
		var isPurchased = false;
		if (localStorage.getItem("talon") != "")
		{
			$.ajax(
			{
				url: "purchase-itemsFacture",
				data:
				{
					"orderNum": localStorage.getItem("talon"),
					"type": type,
					"otherId": otherId,
					"nameShow":$("#saleName").val().trim(),
					"numberShow":$("#saleNumber").val().trim()
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
			  			$.ajax(
				  		{
				  			url: "clear-items",
				  		  	success: function(result)
				  		  	{
					  		  	window.print();
								window.location = "index.jsp";
				  		  	}
				  		});
			  			isPurchased = true;
			  			talon = jsonData.talon;
			  		}
			  	}
			});
			
			if (isPurchased == true)
			{
				
			}
		}
		else
		{
			alert("Талоны дугаараа оруулна уу!");
		}
	}

}