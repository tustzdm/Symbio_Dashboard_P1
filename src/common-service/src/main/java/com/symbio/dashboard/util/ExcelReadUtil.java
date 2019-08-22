package com.symbio.dashboard.util;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.business.TestCaseUtil;
import com.symbio.dashboard.constant.ErrorConst;
import com.symbio.dashboard.constant.ProjectConst;
import com.symbio.dashboard.dto.TestRunExcelDTO;
import com.symbio.dashboard.enums.Locales;
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
            } else if (TestCaseUtil.DETAILEDSTEPS.equalsIgnoreCase(attribute)) {
                testCase.setDetailSteps(cellValue);
            } else if (TestCaseUtil.OWNER.equalsIgnoreCase(attribute)) {
                testCase.setCaseOwner(cellValue);
            } else if (TestCaseUtil.CLASSNAME.equalsIgnoreCase(attribute)) {
                testCase.setClassName(cellValue);
            } else if (TestCaseUtil.LOCALES.equalsIgnoreCase(attribute)) {
                testCase.setSupportLocales(cellValue);
            } else if (TestCaseUtil.ID.equalsIgnoreCase(attribute)) {
                testCase.setId(new Integer(cellValue));
            } else if (TestCaseUtil.CASETYPE.equalsIgnoreCase(attribute)) {
                testCase.setCaseType(new Integer(cellValue));
//            } else if (TestCaseUtil.PRODUCTID.equalsIgnoreCase(attribute)) {
//                testCase.setProductId(0);
//            } else if (TestCaseUtil.VALIDATION.equalsIgnoreCase(attribute)) {
//                testCase.setValidation(new Integer(cellValue));
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

    public Result<List<TestRunExcelDTO>> read(String fileName, List<Map<String, String>> listNameField) {
        Result<List<TestRunExcelDTO>> retResult = new Result<>();
        InputStream is = null;
        try {
            if (!validateExcel(fileName)) {
                retResult.setEc(ErrorConst.TESTCASE_IMPEXCEL_EXCEPTION);
                retResult.setEm(ErrorConst.TESTCASE_IMPEXCEL_EXCEPTION_M);
                return retResult;
            }

            boolean isExcel2003 = true;

            if (isExcel2007(fileName)) {
                isExcel2003 = false;
            }

            File file = new File(fileName);
            is = new FileInputStream(file);
            retResult = read(is, isExcel2003, listNameField);
            is.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            retResult.setEc(ErrorConst.TESTCASE_IMPEXCEL_EXCEPTION);
            retResult.setEm(ErrorConst.TESTCASE_IMPEXCEL_EXCEPTION_M);
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
        return retResult;
    }

    private Result<List<TestRunExcelDTO>> read(InputStream inputStream, boolean isExcel2003, List<Map<String, String>> listNameField) {
        Result<List<TestRunExcelDTO>> retResult = new Result<>();
        try {

            Workbook wb = null;

            if (isExcel2003) {
                wb = new HSSFWorkbook(inputStream);
            } else {
                wb = new XSSFWorkbook(inputStream);
            }
            retResult = readEx(wb, listNameField);

        } catch (IOException e) {
            e.printStackTrace();
            retResult.setEc(ErrorConst.TESTCASE_IMPEXCEL_EXCEPTION);
            retResult.setEm(ErrorConst.TESTCASE_IMPEXCEL_EXCEPTION_M);
        }

        return retResult;
    }

    private Result<List<TestCase>> read(Workbook wb, List<Map<String, String>> listNameField) {

        Result<List<TestCase>> Result = new Result<>();
        List<TestCase> list = null;
        List<Integer> importIndex = new ArrayList<>();
        List<String> colFields = new ArrayList<>();
        String colName = null;
        String colField = null;
        String cellValue = null;
        Cell cell;

        Sheet sheet = wb.getSheetAt(0);

        // 1. Get 1st row and column size
        Row headerRow = sheet.getRow(0);
        this.totalRows = sheet.getPhysicalNumberOfRows();
        if (this.totalRows >= 1 && headerRow != null) {
            this.totalCells = headerRow.getPhysicalNumberOfCells();
            list = new ArrayList<>();
        }

        // 2. Get index of match value with list map value from excel
        for (int j = 0; j < totalCells; j++) {
            cell = headerRow.getCell(j);
            cellValue = cell.getStringCellValue();
            if (cellValue.contains("/")) {
                cellValue = cellValue.split("/")[0];
            }

            for (int i = 0; i < listNameField.size(); i++) {
                colName = listNameField.get(i).get("name");
                colField = listNameField.get(i).get("field");
                if (colName.equalsIgnoreCase(cellValue)) {
                    importIndex.add(j);
                    colFields.add(colField);
                }
            }
        }


        // 3. Get cell value from each row with the index
        int nTotalRows = this.totalRows;

        for (int r = 1; r < nTotalRows; r++) {
            TestCase testCase = null;
            Row row = sheet.getRow(r);
            testCase = new TestCase();
            if (row == null) {
                continue;
            }

            for (int c = 0; c < importIndex.size(); c++) {
                cell = row.getCell(importIndex.get(c));
                cellValue = getCellValue(cell);
                if (r == 0 && !StringUtil.isEmpty(cellValue)) {
                    //heads.put(importIndex.get(c), cellValue.trim());
                    String field = CommonUtil.getCamelField(colFields.get(importIndex.get(c)));
                    heads.put(c, field);
                } else {
                    //String field = CommonUtil.getCamelField(colFields.get(importIndex.get(c)));
                    //PropertyUtil.setProperty(testCase, field, cellValue);
                    PropertyUtil.setProperty(testCase, heads.get(c), cellValue);
                }
            }

            if (testCase != null) {
                list.add(testCase);
            }
        }

        Result.setCd(list);
        return Result;
    }


    private String getCellValue(Cell cell) {
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
        return cellValue;
    }

    private Result<List<TestRunExcelDTO>> readEx(Workbook wb, List<Map<String, String>> listNameField) {
        Result<List<TestRunExcelDTO>> retResult = new Result<>();

        List<TestRunExcelDTO> list = null;

        Sheet sheet = wb.getSheetAt(0);

        this.totalRows = sheet.getPhysicalNumberOfRows();

        if (this.totalRows >= 1 && sheet.getRow(0) != null) {
            this.totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
            list = new ArrayList<>();
        }

        int nTotalRows = this.totalRows;
        int nTotalCells = this.getTotalCells();

        String strTestRunLocale = null;

        for (int r = 0; r < nTotalRows; r++) {
            TestCase testCase = null;
            Row row = sheet.getRow(r);
            if (r != 0) {
                testCase = new TestCase();
                strTestRunLocale = null;
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
                    String strField = getSettingField(listNameField, cellValue);
                    if (!CommonUtil.isEmpty(strField)) {
                        heads.put(c, strField);
                    }

                    if ("locale".equalsIgnoreCase(cellValue)) {
                        heads.put(c, "locale");
                    }
                } else {
                    String strField = heads.get(c);
                    if (ProjectConst.TESTCASE_IMP_FIELD_CASE_ID.equals(strField) && CommonUtil.isEmpty(cellValue)) {
                        testCase = null;
                        break;
                    }

                    if (!"locale".equals(strField) && !CommonUtil.isEmpty(heads.get(c))) {
                        try {
                            PropertyUtil.setProperty(testCase, heads.get(c), cellValue);
                        } catch (Exception e) {
                            e.printStackTrace();
                            return new Result<>("300303", heads.get(c));
                        }
                    } else if ("locale".equals(strField)) {
                        strTestRunLocale = cellValue;
                    }
                }
            }
            if (testCase != null) {
                TestRunExcelDTO dtoTestRun = new TestRunExcelDTO();
                dtoTestRun.setTestCase(testCase);
                if (CommonUtil.isEmpty(strTestRunLocale)) {
                    dtoTestRun.setLocale(Locales.EN_US.toString());
                } else {
                    dtoTestRun.setLocale(strTestRunLocale);
                }
                list.add(dtoTestRun);
            }
        }

        retResult.setCd(list);
        return retResult;
    }

    private String getSettingField(List<Map<String, String>> listMap, String strHead) {
        String strDBField = null;

        for (Map<String, String> map : listMap) {
            if (strHead.toLowerCase().equalsIgnoreCase(map.get("name"))
                    || strHead.toLowerCase().contains(map.get("name").toLowerCase())) {
                strDBField = CommonUtil.getCamelField(map.get("field"));
                break;
            }
        }

        return strDBField;
    }
}
