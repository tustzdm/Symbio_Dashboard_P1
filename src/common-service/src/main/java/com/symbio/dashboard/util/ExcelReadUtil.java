package com.symbio.dashboard.util;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.business.TestCaseUtil;
import com.symbio.dashboard.constant.ErrorConst;
import com.symbio.dashboard.model.TestCase;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFCell;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class ExcelReadUtil {

    private int totalRows = 0;

    private int totalCells = 0;

    private String errorInfo;

    public ExcelReadUtil() {
    }

    /**
     * excel
     */
    private Map<Integer, String> heads = new HashMap<Integer, String>();
    ;

    /**
     * @param filePath
     * @return
     */
    public boolean validateExcel(String filePath) {
        if (filePath == null || !(isExcel2003(filePath) || isExcel2007(filePath))) {
            errorInfo = "The file name is not in the excel format";
            return false;
        }

        File file = new File(filePath);

        if (file == null || !file.exists()) {
            errorInfo = "file does not exist ";
            return false;
        }

        return true;
    }

    /**
     * @param filePath
     * @return
     */
    public Result<List<TestCase>> read(String filePath) {
        Result<List<TestCase>> Result = new Result<List<TestCase>>();
        InputStream is = null;
        try {
            if (!validateExcel(filePath)) {
                Result.setEc(ErrorConst.TESTCASE_IMPEXCEL_EXCEPTION);
                Result.setEm(ErrorConst.TESTCASE_IMPEXCEL_EXCEPTION_M);
                return Result;
            }

            boolean isExcel2003 = true;

            if (isExcel2007(filePath)) {
                isExcel2003 = false;
            }

            File file = new File(filePath);
            is = new FileInputStream(file);
            Result = read(is, isExcel2003);
            is.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            Result.setEc(ErrorConst.TESTCASE_IMPEXCEL_EXCEPTION);
            Result.setEm(ErrorConst.TESTCASE_IMPEXCEL_EXCEPTION_M);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    is = null;
                    e.printStackTrace();
                }
            }
        }
        return Result;
    }

    /**
     * @Excel @2012-08-29 16:40:15 @@param inputStream @@param isExcel2003 @@return @List
     */
    public Result<List<TestCase>> read(InputStream inputStream, boolean isExcel2003) {
        Result<List<TestCase>> Result = null;
        try {
            Result = new Result<List<TestCase>>();

            Workbook wb = null;

            if (isExcel2003) {
                wb = new HSSFWorkbook(inputStream);
            } else {
                wb = new XSSFWorkbook(inputStream);
            }
            Result = read(wb, false);

        } catch (IOException e) {
            e.printStackTrace();
            Result.setEc(ErrorConst.TESTCASE_IMPEXCEL_EXCEPTION);
            Result.setEm(ErrorConst.TESTCASE_IMPEXCEL_EXCEPTION_M);
        }

        return Result;
    }

    /**
     * @@param Workbook @@return @List<List<String>>
     */
    private List<List<String>> read(Workbook wb) {

        List<List<String>> dataLst = new ArrayList<List<String>>();

        Sheet sheet = wb.getSheetAt(0);

        this.totalRows = sheet.getPhysicalNumberOfRows();

        if (this.totalRows >= 1 && sheet.getRow(0) != null) {
            this.totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
        }

        for (int r = 0; r < this.totalRows; r++) {

            Row row = sheet.getRow(r);

            if (row == null) {
                continue;
            }

            List<String> rowLst = new ArrayList<String>();

            for (int c = 0; c < this.getTotalCells(); c++) {

                Cell cell = row.getCell(c);
                String cellValue = "";
                if (null != cell) {
                    switch (cell.getCellType()) {
                        case HSSFCell.CELL_TYPE_NUMERIC:
                            cellValue = cell.getNumericCellValue() + "";
                            break;

                        case HSSFCell.CELL_TYPE_STRING:
                            cellValue = cell.getStringCellValue();
                            break;

                        case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
                            cellValue = cell.getBooleanCellValue() + "";
                            break;

                        case HSSFCell.CELL_TYPE_FORMULA:
                            cellValue = cell.getCellFormula() + "";
                            break;

                        case HSSFCell.CELL_TYPE_BLANK:
                            cellValue = "";
                            break;

                        case HSSFCell.CELL_TYPE_ERROR:
                            cellValue = "Invaild symbol";
                            break;

                        default:
                            cellValue = "Unknown type";
                            break;
                    }
                }
                rowLst.add(cellValue);
            }

            dataLst.add(rowLst);
        }

        return dataLst;
    }

    /**
     * @param wb
     * @param f  true
     * @return head: Priority, Automation ID,... //List<List<String>> ...
     * <p>Result(List<TestCase>)
     */
    private Result<List<TestCase>> read(Workbook wb, boolean f) {

        Result<List<TestCase>> Result = new Result<List<TestCase>>();

        List<TestCase> list = null;

        Sheet sheet = wb.getSheetAt(0);

        this.totalRows = sheet.getPhysicalNumberOfRows();

        if (this.totalRows >= 1 && sheet.getRow(0) != null) {
            this.totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
            list = new ArrayList<TestCase>();
        }

        int nTotalRows = this.totalRows;
        int nTotalCells = this.getTotalCells();
        for (int r = 0; r < nTotalRows; r++) {
            TestCase testCase = null;
            Row row = sheet.getRow(r);
            if (r != 0) {
                testCase = new TestCase();
            }
            if (row == null) {
                continue;
            }

            for (int c = 0; c < nTotalCells; c++) {
                Cell cell = row.getCell(c);
                String cellValue = "";
                if (null != cell) {
                    switch (cell.getCellType()) {
                        case HSSFCell.CELL_TYPE_NUMERIC:
                            cell.setCellType(Cell.CELL_TYPE_STRING);
                            cellValue = cell.getStringCellValue();
                            break;

                        case HSSFCell.CELL_TYPE_STRING:
                            cellValue = cell.getStringCellValue();
                            break;

                        case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
                            cellValue = cell.getBooleanCellValue() + "";
                            break;

                        case HSSFCell.CELL_TYPE_FORMULA:
                            cellValue = cell.getCellFormula() + "";
                            break;

                        case HSSFCell.CELL_TYPE_BLANK:
                            cellValue = "";
                            break;

                        case HSSFCell.CELL_TYPE_ERROR:
                            cellValue = "";
                            break;

                        default:
                            cellValue = "";
                            break;
                    }
                }
                if (r == 0 && !StringUtil.isEmpty(cellValue)) {
                    heads.put(c, cellValue.trim());
                } else {
                    setAttributesVal(testCase, cellValue, c);
                }
            }

            if (testCase != null) {
                list.add(testCase);
            }
        }

        Result.setCd(list);
        return Result;
    }

    /**
     * @param testCase
     * @param cellValue
     * @param columnIndex
     * @return
     */
    public TestCase setAttributesVal(TestCase testCase, String cellValue, int columnIndex) {
        try {
            String attribute = heads.get(columnIndex);
            if (TestCaseUtil.PRIORITY.equalsIgnoreCase(attribute)
                    || TestCaseUtil.TRPRIORITY.equalsIgnoreCase(attribute)) {
                testCase.setPriority(cellValue);
            } else if (TestCaseUtil.CASEID.equalsIgnoreCase(attribute)) {
                testCase.setCaseId(cellValue);
            } else if (TestCaseUtil.SUBFEATUREAREA.equalsIgnoreCase(attribute)) {
                testCase.setSubFeatureArea(cellValue);
            } else if (TestCaseUtil.FEATUREAREA.equalsIgnoreCase(attribute)) {
                testCase.setFeatureArea(cellValue);
            } else if (TestCaseUtil.SUMMARY.equalsIgnoreCase(attribute)) {
                testCase.setSummary(cellValue);
//            } else if (TestCaseUtil.DETAILEDSTEPS.equalsIgnoreCase(attribute)) {
//                testCase.setDetailedSteps(cellValue);
//            } else if (TestCaseUtil.OWNER.equalsIgnoreCase(attribute)) {
//                testCase.setTestOwner(cellValue);
            } else if (TestCaseUtil.CLASSNAME.equalsIgnoreCase(attribute)) {
                testCase.setClassName(cellValue);
            } else if (TestCaseUtil.LOCALES.equalsIgnoreCase(attribute)) {
                testCase.setSupportLocales(cellValue);
//            } else if (TestCaseUtil.ENVIRONMENT.equalsIgnoreCase(attribute)) {
//                testCase.setEnvironment(cellValue);
//            } else if (TestCaseUtil.ACTRUALTESTERNAME.equalsIgnoreCase(attribute)) {
//                testCase.setActrualTesterName(StringUtil.splitCellValue(cellValue));
//            }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("testCase Import excel setAttributesVal exception");
        }

        return testCase;
    }

    /**
     * @@param filePath @@return
     * @boolean
     */
    public static boolean isExcel2003(String filePath) {
        return filePath.matches("^.+\\.(?i)(xls)$");
    }

    /**
     * @@param filePath @@return
     * @boolean
     */
    public static boolean isExcel2007(String filePath) {
        return filePath.matches("^.+\\.(?i)(xlsx)$");
    }

    public int getTotalRows() {
        return totalRows;
    }

    public int getTotalCells() {

        return totalCells;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public Map<Integer, String> getHeads() {
        return heads;
    }

    public void setHeads(Map<Integer, String> heads) {
        this.heads = heads;
    }
}
