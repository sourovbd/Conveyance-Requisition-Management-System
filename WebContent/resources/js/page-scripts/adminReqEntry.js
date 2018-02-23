$(document).ready(function(){
	$('#selectAll').click(function(){
		if($("#adminEntryTableReq #selectAll").is(':checked')) {
			$("#adminEntryTableBodyReq input[type=checkbox]:enabled").each(function(){
				$(this).prop("checked",true);
			});
		}else {
			$("#adminEntryTableBodyReq input[type=checkbox]").each(function(){
				$(this).prop("checked",false);
			});
		}
	});
});

function approve() {
	var id = new Array();
	var userId = $('#userIdHolder').val();
	$selectedRow = $('#adminEntryTableBodyReq tr').filter(':has(:checkbox:checked)');
	$selectedRow.each(function(){
		id.push(parseInt($(this).find('td:nth-child(7)').text()));
	});
	//alert("id inside approved method: "+id);
	//alert("userId: "+userId);
	$.ajax({
		type: "POST",
		url: "/CMS/main/approveReq",
		data: {
			id: JSON.stringify(id),
			userId: userId
		},
		cache: false,
		dataType:"json"
	}).done(function(response){
		$('#adminEntryTableBodyReq tr').remove();
		var html="";
		if(response.length > 0) {
			//alert(response.length);
			for (var i = 0; i < response.length; i++) {
				
				//alert("inside for loop.");
				html = "<tr>" +
							"<td><input type='checkbox'/></td>" +
							"<td>"+response[i].reqEntryDate+"</td>" +
							"<td>"+response[i].reqSubmitDate+"</td>" +
							"<td>"+response[i].reqType+"</td>" +
							"<td>"+response[i].reqQuantity+"</td>" +
							"<td>"+response[i].reqDetails+"</td>" +
							"<td style='display: none;'>"+ response[i].reqId+"</td>" +
						"</tr>";
				$('#adminEntryTableBodyReq').append(html);
			}
			alert("Approved successfully!");
		}
		else {
			
			alert("Approved successfully!");
			window.location = "/CMS/main/adminReq";
		}
		
	});
}

function reject() {
	var id = new Array();
	var userId = $('#userIdHolder').val();
	$selectedRow = $('#adminEntryTableBodyReq tr').filter(':has(:checkbox:checked)');
	$selectedRow.each(function(){
		id.push(parseInt($(this).find('td:nth-child(7)').text()));
	});
	//alert("id inside reject method: "+id);
	//alert("userId: "+userId);
	$.ajax({
		type: "POST",
		url: "/CMS/main/rejectReq",
		data: {
			id: JSON.stringify(id),
			userId: userId
		},
		cache: false,
		dataType:"json"
	}).done(function(response){
		$('#adminEntryTableBodyReq tr').remove();
		var html="";
		if(response.length > 0) {
			//alert(response.length);
			for (var i = 0; i < response.length; i++) {
				
				//alert("inside for loop.");
				html = "<tr>" +
							"<td><input type='checkbox'/></td>" +
							"<td>"+response[i].reqEntryDate+"</td>" +
							"<td>"+response[i].reqSubmitDate+"</td>" +
							"<td>"+response[i].reqType+"</td>" +
							"<td>"+response[i].reqQuantity+"</td>" +
							"<td>"+response[i].reqDetails+"</td>" +
							"<td style='display: none;'>"+ response[i].reqId+"</td>" +
						"</tr>";
				$('#adminEntryTableBodyReq').append(html);
			}
			alert("Rejected successfully!");
		}
		else {
			
			alert("Rejected successfully!");
			window.location = "/CMS/main/adminReq";
		}
	});
}