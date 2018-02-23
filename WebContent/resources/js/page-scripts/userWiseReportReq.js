function reportPrintReq() {
	var outputFormat = "pdf";
	var dateFrom = $('#reqDateFrom').val();
	var dateTo = $('#reqDateTo').val();
	var employeeId = $('#employeeId').val();
	
	var myWindow = window.open("Print Preview.");
	var html = "<div><h3>View</h3>" +
			"<iframe id='ifrmrptSummary' width='100%' height='100%'></iframe>" +
			"</div>";
	var scriptCode = "myWindow<script type='text/javascript'>"+
					"document.getElementById('ifrmrptSummary').src='/CMS/main/userWiseReportReq?dateFrom="+dateFrom+"&dateTo="+dateTo+"&employeeId="+employeeId
					+"&outputFormat="+outputFormat+"'</script>";
	myWindow.document.write("<html><head></head><body>" + html + scriptCode+ "</body></html>");
	
};