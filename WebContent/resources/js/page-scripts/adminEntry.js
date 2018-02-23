$(document).ready(function(){
	$('#selectAll').click(function(){
		if($("#adminEntryTable #selectAll").is(':checked')) {
			$("#adminEntryTableBody input[type=checkbox]:enabled").each(function(){
				$(this).prop("checked",true);
			});
		}else {
			$("#adminEntryTableBody input[type=checkbox]").each(function(){
				$(this).prop("checked",false);
			});
		}
	});
});

function approve() {
	var id = new Array();
	var userId = $('#userIdHolder').val();
	$selectedRow = $('#adminEntryTableBody tr').filter(':has(:checkbox:checked)');
	$selectedRow.each(function(){
		id.push(parseInt($(this).find('td:nth-child(7)').text()));
	});
	//alert("id inside approved method: "+id);
	//alert("userId: "+userId);
	$.ajax({
		type: "POST",
		url: "/CMS/main/approve",
		data: {
			id: JSON.stringify(id),
			userId: userId
		},
		cache: false,
		dataType:"json"
	}).done(function(response){
		$('#adminEntryTableBody tr').remove();
		var html="";
		if(response.length > 0) {
			//alert(response.length);
			for (var i = 0; i < response.length; i++) {
				
				//alert("inside for loop.");
				html = "<tr>" +
							"<td><input type='checkbox'/></td>" +
							"<td>"+response[i].expenseConveyanceDate+"</td>" +
							"<td>"+response[i].expenseSubmitDate+"</td>" +
							"<td>"+response[i].expenseType+"</td>" +
							"<td>"+response[i].expenseAmount+"</td>" +
							"<td>"+response[i].expenseDetails+"</td>" +
							"<td style='display: none;'>"+ response[i].expenseId+"</td>" +
						"</tr>";
				$('#adminEntryTableBody').append(html);
			}
			alert("Approved successfully!");
		}
		else {
			
			alert("Approved successfully!");
			window.location = "/CMS/main/admin";
		}
		
	});
}

function reject() {
	var id = new Array();
	var userId = $('#userIdHolder').val();
	$selectedRow = $('#adminEntryTableBody tr').filter(':has(:checkbox:checked)');
	$selectedRow.each(function(){
		id.push(parseInt($(this).find('td:nth-child(7)').text()));
	});
	//alert("id inside reject method: "+id);
	//alert("userId: "+userId);
	$.ajax({
		type: "POST",
		url: "/CMS/main/reject",
		data: {
			id: JSON.stringify(id),
			userId: userId
		},
		cache: false,
		dataType:"json"
	}).done(function(response){
		$('#adminEntryTableBody tr').remove();
		var html="";
		if(response.length > 0) {
			//alert(response.length);
			for (var i = 0; i < response.length; i++) {
				
				//alert("inside for loop.");
				html = "<tr>" +
							"<td><input type='checkbox'/></td>" +
							"<td>"+response[i].expenseConveyanceDate+"</td>" +
							"<td>"+response[i].expenseSubmitDate+"</td>" +
							"<td>"+response[i].expenseType+"</td>" +
							"<td>"+response[i].expenseAmount+"</td>" +
							"<td>"+response[i].expenseDetails+"</td>" +
							"<td style='display: none;'>"+ response[i].expenseId+"</td>" +
						"</tr>";
				$('#adminEntryTableBody').append(html);
			}
			alert("Rejected successfully!");
		}
		else {
			
			alert("Rejected successfully!");
			window.location = "/CMS/main/admin";
		}
	});
}