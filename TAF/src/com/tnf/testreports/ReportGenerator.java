package com.tnf.testreports;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ReportGenerator {
	
	//private String folderPath = "C:\\Users\\laddunurih\\workspace\\Taylor&FrancisFramework\\src\\com\\qtpselenium\\hybrid\\testreports\\";
	
	private String folderPath =System.getProperty("user.dir")+"\\src\\com\\tnf\\testreports\\";
	//private String folderPath = "C:\\Reports\\";
	private String reportGeneratePath = "C:\\Reports";
	
	//private String reportGeneratePath = "C:\\Users\\laddunurih\\workspace\\Taylor&FrancisFramework\\src\\com\\qtpselenium\\hybrid\\testreports\\";
	
	
	public void createFolder(String folderName) {
		File file = new File(reportGeneratePath + "\\" + folderName);
		if(!file.isDirectory()) {
			boolean folderCreated = file.mkdir();
			System.out.println("FolderCreated : " + folderCreated);
		}

	}
	
	public StringBuffer getData(String filePath) {
		StringBuffer buffer = new StringBuffer();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(folderPath + filePath));
			String str;
			while ((str = br.readLine()) != null)
				buffer.append(str);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
					br = null;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return buffer;
	}
	
	public void writeFile(String path, String data) {
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			fw = new FileWriter(path);
			bw = new BufferedWriter(fw);
			bw.write(data);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				if (bw != null) {
					bw.close();
					bw = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (fw != null) {
					fw.close();
					fw = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	

	public void generateTestSteps(List<List<String>> dataList, String tcid, String rowNum) {
		
		List<String> columnNames = new ArrayList<String>();
		columnNames.add("TCID");
		columnNames.add("Description");
		columnNames.add("ExpectedResult");
		columnNames.add("Result");
		
		StringBuffer fileData = new StringBuffer();
		
		fileData.append(getData("header.txt"));
		fileData.append(prepareColumnNameData(columnNames));
		for(List<String> rowData : dataList) {
			fileData.append(addTestStepRow(rowData));
		}

		fileData.append(getData("bottom.txt"));

		writeFile(reportGeneratePath + "\\" + tcid + "\\" + tcid + rowNum +".html", fileData.toString());
	}
	
	public void generateTCIDPage(String tcid, List<String> columnNames, List<List<String>> testDataList) {
		
		StringBuffer fileData = new StringBuffer();
		fileData.append(getData("header.txt"));
		columnNames.add(0, "TCTD");
		columnNames.add("Result");
		fileData.append(prepareColumnNameData(columnNames));
		for(List<String> rowData : testDataList) {
			fileData.append(addTCIDRow(tcid, rowData));
		}

		fileData.append(getData("bottom.txt"));

		writeFile(reportGeneratePath + "\\" + tcid + "\\index.html", fileData.toString());
	}

	public void generateIndexPage(List<List<String>> indexDataList) {
		/* Prepare Column Names for Index.html */
		List<String> columnNames = new ArrayList<String>();
		columnNames.add("TCID");
		columnNames.add("Description");
		columnNames.add("Result");
		
		StringBuffer fileData = new StringBuffer();
		fileData.append(getData("header.txt"));
		
		fileData.append(prepareColumnNameData(columnNames));
		for(List<String> rowData : indexDataList) {
			fileData.append(addIndexRow(rowData));
		}
		
		fileData.append(getData("bottom.txt"));
		writeFile(reportGeneratePath + "\\" + "index.html", fileData.toString());
	}
	
	
	public String prepareColumnNameData(List<String> rowData) {
		StringBuffer columnNameBuffer = new StringBuffer();
		columnNameBuffer.append("<tr>");
		for (String columnData : rowData) {
			columnNameBuffer
					.append("<th BGCOLOR=arial width=17%><font color=white face=Verdana size=2>"
							+ columnData + "</font></th>");
		}
		columnNameBuffer.append("</tr>");
		return columnNameBuffer.toString();
	}
	
	public String prepareColumnNameData(Set<String> rowData) {
		StringBuffer columnNameBuffer = new StringBuffer();
		columnNameBuffer.append("<tr>");
		for (String columnData : rowData) {
			columnNameBuffer
					.append("<th BGCOLOR=arial width=17%><font color=white face=Verdana size=2>"
							+ columnData + "</font></th>");
		}
		columnNameBuffer.append("</tr>");
		return columnNameBuffer.toString();
	}
	
	public String addIndexRow(List<String> rowData) {
		StringBuffer indexRowData = new StringBuffer();
		indexRowData.append("<tr bgcolor=#d6cfb1>");
		indexRowData.append("<td Align=Left><a href='" + ".\\" + rowData.get(0) + "\\index.html'>" + rowData.get(0) + "</a></td>");
		indexRowData.append("<td Align=Left>" + rowData.get(1) + "</td>");
		indexRowData.append("<td Align=Left>" + rowData.get(2) + "</td>");
		indexRowData.append("</tr>");
		return indexRowData.toString();
	}
	
	public String addTCIDRow(String tcid, List<String> rowData) {
		StringBuffer indexRowData = new StringBuffer();
		indexRowData.append("<tr bgcolor=#d6cfb1>");
		indexRowData.append("<td Align=Left><a href='" + ".\\" +  tcid + rowData.get(0) + ".html'>" + tcid + "</a></td>");
		for(int i=0; i<rowData.size(); i++) {
			indexRowData.append("<td Align=Left>" + rowData.get(i) + "</td>");
		}
		indexRowData.append("</tr>");
		return indexRowData.toString();
	}
	
	public String addTestStepRow(List<String> rowData) {
		StringBuffer indexRowData = new StringBuffer();
		indexRowData.append("<tr bgcolor=#d6cfb1>");
		for(int i=0; i<rowData.size(); i++) {
			indexRowData.append("<td Align=Left>" + rowData.get(i) + "</td>");
		}
		indexRowData.append("</tr>");
		return indexRowData.toString();
	}
}
