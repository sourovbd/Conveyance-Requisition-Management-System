$(document).ready(function(){
	$('#selectAll').click(function(){
		if($("#hrEntryTableReq #selectAll").is(':checked')) {
			$("#hrEntryTableBodyReq input[type=checkbox]:enabled").each(function(){
				$(this).prop("checked",true);
			});
		}else {
			$("#hrEntryTableBodyReq input[type=checkbox]").each(function(){
				$(this).prop("checked",false);
			});
		}
	});
});

function approve() {
	var id = new Array();
	$selectedRow = $('#hrEntryTableBodyReq tr').filter(':has(:checkbox:checked)');
	$selectedRow.each(function(){
		id.push(parseInt($(this).find('td:nth-child(2)').text()));
	});
	//alert(id);
	$.ajax({
		type: "POST",
		url: "/CMS/main/hrapproveReq",
		data: {
			id: JSON.stringify(id)
		},
		cache: false,
		dataType:"json"
	}).done(function(response){
		alert("Paid successfully!");
		window.location = "/CMS/main/hrReq";
	});
}