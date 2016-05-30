/**
 * ExcelTest.java
 * Created on  7/4/2016 3:54 PM
 * modify on                user            modify content
 * 7/4/2016 3:54 PM        micx
 * <p/>
 * Dianping.com Inc.
 * Copyright (c) 2003-2014 All Rights Reserved.
 */

package com.micx.excel;


import com.google.common.collect.Lists;
import com.micx.client.ast.entity.AssertScript;
import com.micx.client.ast.enums.AssertType;
import com.micx.client.entity.CaseRequest;
import com.micx.excel.entity.ColumnProp;
import com.micx.excel.enums.ColumnType;
import com.micx.utils.JsonUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by micx  on 2016/04/07 3:54 PM.
 */
public class ExcelUtil {
    private static final String[] EXCEL_POSTFIX = {"xls", "xlsx"};

    public static void main(String[] args) {

        String fileName = "/Users/micx/Desktop/autoTest/m.xlsx";
        List<CaseRequest> requestList = null;
        try {
            requestList = parseRequest(fileName);
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println(requestList);

    }

    public static List<CaseRequest> parseRequest(String fileName) throws Exception {
        InputStream in = new FileInputStream(new File(fileName));
        List<CaseRequest> requestList = Lists.newArrayList();
        Workbook workbook;
        if(fileName.indexOf(EXCEL_POSTFIX[1]) > -1){
            workbook = new XSSFWorkbook(in);
        }else{
            workbook = new HSSFWorkbook(in);
        }
        int numberOfSheets = workbook.getNumberOfSheets();
        for (int i = 0; i < numberOfSheets; i++) {
            Sheet sheet = workbook.getSheetAt(i);
            int rows = sheet.getPhysicalNumberOfRows();
            List<ColumnProp> keyList = null;
            for(int rowNum = 0; rowNum < rows; rowNum++){
                Row row = sheet.getRow(rowNum);
                List<String> list = parseRow(row);
                if (rowNum == 0){
                    keyList = parseKey(list);
                    System.out.println(keyList);
                }else{
                    CaseRequest caseRequest = parseValue(list, keyList);
                    caseRequest.setSheet(sheet.getSheetName());
                    caseRequest.setModule(fileName);
                    if (caseRequest.getAssertType() == AssertType.SCRIPT){
                        caseRequest.setExpectResult(parseAssertScript(caseRequest.getExpectResult()));
                    }
                    requestList.add(caseRequest);
                }
            }
        }
        return requestList;
    }

    private static AssertScript parseAssertScript(Object expectResult) {
        String json = (String) expectResult;
        try {
            AssertScript script = JsonUtil.parseObject(json, AssertScript.class);
            return script;
        } catch (IOException e) {

        }
        return null;
    }

    private static CaseRequest parseValue(List<String> list, List<ColumnProp> keyList) {
        CaseRequest request = new CaseRequest();
        for (int i = 0; i < list.size(); i++) {
            ColumnProp columnProp = keyList.get(i);
            if (columnProp == null){
                continue;
            }
            ColumnType type = ColumnType.parse(columnProp.getType());
            String value = list.get(i);
            if (value.equals("$EMP")){
                continue;
            }
            type.ColumnParser.convert(columnProp,value, request);
        }
        return request;
    }

    private static List<ColumnProp> parseKey(List<String> list) {
        if (CollectionUtils.isEmpty(list)){
            return ListUtils.EMPTY_LIST;
        }
        List<ColumnProp> result = Lists.newArrayListWithCapacity(list.size());
        for (String key: list){
            result.add(parseCaseKey(key));
        }
        return result;
    }

    private static ColumnProp parseCaseKey(String key) {
        for (ColumnType columnType : ColumnType.values()){
            if (columnType.ColumnParser.equeals(columnType, key.toLowerCase())){
                return new ColumnProp(columnType.value, key);
            }
        }
        return null;
    }

    private static List<String> parseRow(Row row) {
        if (row == null){
            return ListUtils.EMPTY_LIST;
        }
        int columns = row.getLastCellNum();
        List<String> list = Lists.newArrayListWithCapacity(columns);
        for (int i = 0; i < columns; i++) {
            Cell cell = row.getCell(i);
            list.add(parseCell(cell));
        }
        return list;
    }

    private static String parseCell(Cell cell) {
        if (cell == null){
            return StringUtils.EMPTY;
        }
        int cellType = cell.getCellType();
        switch (cellType){
            case Cell.CELL_TYPE_BLANK:
                return cell.getStringCellValue();
            case Cell.CELL_TYPE_BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case Cell.CELL_TYPE_ERROR:
                return String.valueOf(cell.getErrorCellValue());
            case Cell.CELL_TYPE_FORMULA:
                return cell.getCellFormula();
            case Cell.CELL_TYPE_NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            case Cell.CELL_TYPE_STRING:
                return cell.getStringCellValue();
            default:
                return null;
        }
    }
}
