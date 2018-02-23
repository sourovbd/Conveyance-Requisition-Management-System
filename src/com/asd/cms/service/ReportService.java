package com.asd.cms.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import net.sf.jasperreports.engine.JRBand;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRCsvExporterParameter;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;

import com.asd.cms.util.Utilities;

import org.hibernate.SessionFactory;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service("reportService")
public class ReportService {
	
	private JdbcTemplate jdbcTemplate;
	@Resource(name="jdbcDataSource") 
	public void setDataSource(DataSource dataSource) { 
		
		this.jdbcTemplate = new JdbcTemplate(dataSource); 
	}
						// For Hibernate Connection
	/*@Autowired
	private SessionFactory sessionFactory;*/
	
	public void generateReport(String path, String outputFormat, Map<String, Object> param, HttpServletResponse res){
		try{
			if(new File(path).exists()){
				
				JasperPrint jp = getReport(path, param);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				//String outputFormat = req.getParameter("outputFormat");
				if(outputFormat.equals("pdf")){
					
					JasperExportManager.exportReportToPdfStream(jp, baos);
					
				}else if(outputFormat.equals("csv")){
					
					JRCsvExporter exporter = new JRCsvExporter();
					exporter.setParameter(JRCsvExporterParameter.JASPER_PRINT, jp);
					exporter.setParameter(JRCsvExporterParameter.OUTPUT_STREAM, baos);
					exporter.setParameter(JRCsvExporterParameter.FIELD_DELIMITER, ",");
					exporter.setParameter(JRCsvExporterParameter.RECORD_DELIMITER,System.getProperty("line.separator"));
					
					exporter.exportReport();
					
				}else if(outputFormat.equals("xls")){
					
					JRXlsExporter exporterXLS = new JRXlsExporter(); 
					exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jp); 
					exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, baos); 
					exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE); 
					exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE); 
					exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE); 
					exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE); 
					exporterXLS.exportReport(); 
					
				}else if(outputFormat.equals("doc")){
					
					JRDocxExporter exporterDOC = new JRDocxExporter();
					exporterDOC.setParameter(JRXlsExporterParameter.JASPER_PRINT, jp); 
					exporterDOC.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, baos);
					exporterDOC.exportReport();
				}else{
					
					JRHtmlExporter exporterHTML = new JRHtmlExporter();
					exporterHTML.setParameter(JRHtmlExporterParameter.JASPER_PRINT, jp);
					exporterHTML.setParameter(JRHtmlExporterParameter.OUTPUT_STREAM, baos);
					exporterHTML.setParameter(JRHtmlExporterParameter.IMAGES_URI, "images?image=");
					exporterHTML.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, false);
					exporterHTML.setParameter(JRHtmlExporterParameter.IGNORE_PAGE_MARGINS, true);
					exporterHTML.exportReport();
				}
				
				String fileName = "CMS_REPORTS_" + Utilities.getCurrentLoginID() + "_" + Utilities.getCurrentTimestamp().toString() + "." + outputFormat.toLowerCase();
				//System.out.println(outputFormat);
				//System.out.println(fileName);
				res.setHeader("Content-Disposition", "inline; filename="+ fileName);
				String contentType = "";
				if (outputFormat.equals("html"))
					contentType = "text/html";
				else if (outputFormat.equals("xls"))
					contentType = "application/vnd.ms-excel";
				else if (outputFormat.equals("doc"))
					contentType = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
				else
					contentType = "application/" + outputFormat;
				
				//System.out.println(contentType);
				
				res.setContentType(contentType);
				res.setContentLength(baos.size());
				writeReportToResponseStream(res, baos);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public JasperPrint getReport(String path, Map<String, Object> parameters) throws SQLException{
		JasperPrint print = new JasperPrint();		
		JasperReport report;
		try {
									// For Hibernate Connection
			/*SessionFactoryImplementor sessionFactoryImplementation = (SessionFactoryImplementor) sessionFactory;
		    ConnectionProvider connectionProvider = sessionFactoryImplementation.getConnectionProvider();*/
		    
			report = JasperCompileManager.compileReport(path);
			
			print = JasperFillManager.fillReport(report, parameters, jdbcTemplate.getDataSource().getConnection());
									// For Hibernate Connection
			//print = JasperFillManager.fillReport(report, parameters, connectionProvider.getConnection());
			
		} catch (JRException e) {
			
			e.printStackTrace();
		}
		return print;
	}
	
	private void writeReportToResponseStream(HttpServletResponse response,
			ByteArrayOutputStream baos) {
		//logger.debug("Writing report to the stream");
		try {
			// Retrieve the output stream
			ServletOutputStream outputStream = response.getOutputStream();
			// Write to the output stream
			baos.writeTo(outputStream);
			// Flush the stream
			outputStream.flush();
			//outputStream.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
