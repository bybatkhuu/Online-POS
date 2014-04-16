$(document).ready(function()
{
	$('#startDate').daterangepicker
	(
		{
			startDate: moment().startOf('month'),
			endDate: moment(),
			format: 'YYYY-MM-DD',
			buttonClasses: ['btn btn-sm'],
			applyClass: 'btn-primary',
			cancelClass: 'btn-default',
			showDropdowns: true,
			ranges:
			{
				'Өнөөдөр': [moment(), moment()],
				'Өчигдөр': [moment().subtract('days', 1), moment().subtract('days', 1)],
				'Сүүлийн 7 хоног': [moment().subtract('days', 6), moment()],
				'Сүүлийн 30 хоног': [moment().subtract('days', 29), moment()],
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
			$('#startDate').val(start.format('YYYY-MM-DD'));
			$('#endDate').val(end.format('YYYY-MM-DD'));
		}
	);
	$('#startDate').on('apply.daterangepicker', function(ev, picker)
	{
		$('#startDate').val(picker.startDate.format('YYYY-MM-DD'));
		$('#endDate').val(picker.endDate.format('YYYY-MM-DD'));
	});
	$('#startDate').on('cancel.daterangepicker', function(ev, picker)
	{
		$('#startDate').val('');
		$('#endDate').val('');
	});
	
	$("#endDate").datepicker(
	{
		changeMonth: true,
		changeYear: true,
		dateFormat: "yy-mm-dd",
		option: $.datepicker.regional["mn"]
	});
	
	$('#startDate').mask('9999-99-99');
	$('#endDate').mask('9999-99-99');
	
	$('#mainTable').dataTable();
	
	$("#searchBtn").click(function(event)
    {
    	if (($("#startDate").val().trim() == "") || ($("#startDate").val().trim() == ""))
    	{
    		event.preventDefault();
    	}
    });
});