$(document).ready(function(){
	$('#selectAll').click(function(){
		if($("#expenceEntryTable #selectAll").is(':checked')) {
			$("#expenceEntryTableBody input[type=checkbox]:enabled").each(function(){
				$(this).prop("checked",true);
			});
		}else {
			$("#expenceEntryTableBody input[type=checkbox]").each(function(){
				$(this).prop("checked",false);
			});
		}
	});
});

function approve() {
	var id = new Array();
	$selectedRow = $('#expenceEntryTableBody tr').filter(':has(:checkbox:checked)');
	$selectedRow.each(function(){
		id.push(parseInt($(this).find('td:nth-child(8)').text()));
	});
	//alert("Array of id from approve() inside expenseEntry.js: "+id);
	$.ajax({
		type: "POST",
		url: "/CMS/main/submit",
		data: {
			id: JSON.stringify(id)
		},
		cache: false,
		dataType:"json"
	}).done(function(response){
		alert("Submitted successfully!");
		window.location = "/CMS/main/expense";
	});
}