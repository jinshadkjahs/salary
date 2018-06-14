package com.aitian.salary.Utils;

import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.aitian.salary.model.BonusInfo;
import com.aitian.salary.model.SalaryMain;
import com.aitian.salary.model.SalaryType;
import com.aitian.salary.model.SalaryTypeEmp;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;



public class ReadExcel {
    //总行数
    private int totalRows = 0;  
    //总条数
    private int totalCells = 0; 
    //错误信息接收器
    private String errorMsg;
    private List<Object> objList;
    //2003- 版本的excel
    private final static String excel2003L =".xls";
    //2007+ 版本的excel
    private final static String excel2007U =".xlsx";
    int[] suucess = new int[3];
    //构造方法
    public ReadExcel(){}
    //获取总行数
    public int getTotalRows()  { return totalRows;} 
    //获取总列数
    public int getTotalCells() {  return totalCells;} 
    //获取错误信息
    public String getErrorInfo() { return errorMsg; }  
    
  /**
   * 验证EXCEL文件
   * @param filePath
   * @return
   */
  public boolean validateExcel(String filePath){
        if (filePath == null || !(WDWUtil.isExcel2003(filePath) || WDWUtil.isExcel2007(filePath))){  
            errorMsg = "文件名不是excel格式";  
            return false;  
        }  
        return true;
  }
    
  /**
   * 读EXCEL文件，获取客户信息集合
   *
   * @return
   */
  public List<Object> getExcelInfo(){
    return objList;
  }
  /**
   * 根据excel里面的内容读取客户信息
   * @param is 输入流
   * @param isExcel2003 excel是2003还是2007版本
   * @return
   * @throws IOException
   */
  public  List<Object> getExcelInfo(InputStream is,boolean isExcel2003){
       List<Object> objList=null;
       try{
           /** 根据版本选择创建Workbook的方式 */
           Workbook wb = null;
           //当excel是2003时
           if(isExcel2003){
               wb = new HSSFWorkbook(is); 
           }
           else{//当excel是2007时
               wb = new XSSFWorkbook(is); 
           }
           //读取Excel里面客户的信息
           objList=readExcelValue(wb);
       }
       catch (IOException e)  {  
           e.printStackTrace();  
       }  
       return objList;
  }
  /**
   * 读取Excel里面客户的信息
   * @param wb
   * @return
   */
  private List<Object> readExcelValue(Workbook wb){
      //得到第一个shell  
       Sheet sheet=wb.getSheetAt(0);
       
      //得到Excel的行数
       this.totalRows=sheet.getPhysicalNumberOfRows();
       
      //得到Excel的列数(前提是有行数)
       if(totalRows>=1 && sheet.getRow(0) != null){
            this.totalCells=sheet.getRow(0).getPhysicalNumberOfCells();
       }
       List<Object> objList=new ArrayList<Object>();
       try{
           if(totalCells == 12){
               objList = getPactSalary(sheet);
           }else if(totalCells == 6){
               objList = getBonusDetail(sheet);
           }else if(totalCells == 32){
               objList = getFormalSalary(sheet);
           }else{
               suucess[0] = 1;
           }
       }catch (Exception e){
            if(e.getMessage()!=null&&e.getMessage().matches("[0-9]*-[0-9]*")){
                suucess[0] = 2;
                String[] strArr = e.getMessage().split("-");
                suucess[1] = Integer.parseInt(strArr[0]);
                suucess[2] = Integer.parseInt(strArr[1]);
            }else {
                if(suucess[0] == 0)
                suucess[0] = 3;
            }
       }
       return objList;
    }

    public List<Object> getBonusDetail(Sheet sheet){
        List<Object> objList=new ArrayList<Object>();
        for(int r=1;r<totalRows;r++){
            Row row = sheet.getRow(r);
            if (row == null) continue;
            BonusInfo info = new BonusInfo();
            //循环Excel的列
            for(int c = 0; c <this.totalCells; c++){
                Cell cell = row.getCell(c);
                Object val = getCellValue(cell);
               if (null != cell){
                   if(c==0){//第一列不读
                   }else if(c==1){
                       if(val == null){
//                                throw new RuntimeException((r+1)+"-"+(c+1));
                           break;
                       }
                       if(StringUtils.isBlank(val.toString())){
                           break;
                       }
                       info.setEmpId(getCellValue(cell).toString());//职工编号
                   }else if(c==2){
                       info.setEmpName(val == null?"":val.toString());//姓名
                   }else if(c==3){
                       if(val == null){
                           throw new RuntimeException((r+1)+"-"+(c+1));
                       }
                       info.setMoney(val == null?"":val.toString());//金额
                   }else if(c==4){
                       info.setCont(val == null?"":val.toString());//内容
                   }else if(c==5){
                       info.setManageDepart(val == null?"":val.toString());//归口管理部门
                   }
               }
            }
            if(info.getEmpId() != null) objList.add(info);
        }
        return objList;
    }


    public List<Object> getPactSalary(Sheet sheet){
        List<Object> objList=new ArrayList<Object>();
        int c = 0;
        int r = 0;

        Row oneRow = sheet.getRow(0);
        Integer[] ids = new Integer[this.totalCells];
        for(c = 5; c <this.totalCells-1; c++){
            Cell cell = oneRow.getCell(c);
            if (null != cell) {
                List<SalaryType> types = ConverterSystem.PACT_SALARY_TYPE.stream().filter(type ->
                        type.getSalaryName().equals(getCellValue(cell).toString().trim())
                ).collect(Collectors.toList());
                if(types.size()>0){
                    ids[c] = types.get(0).getSalaryType();
                }else {
                    suucess[0] = 1;
                    throw new RuntimeException();
                }
            }
        }
        try {
            for(r=1;r<totalRows;r++){
                Row row = sheet.getRow(r);
                if (row == null) continue;
                SalaryMain info = new SalaryMain();
                info.setSalaryTypeEmpList(new ArrayList<>());
                //循环Excel的列
                for(c = 0; c <this.totalCells; c++){
                    Cell cell = row.getCell(c);
                    Object val = getCellValue(cell);
                    if (null != cell){
                        if(c==0){//第一列不读
                        }else if(c==1){
                            if(val == null){
//                                throw new RuntimeException((r+1)+"-"+(c+1));
                                break;
                            }
                            if(StringUtils.isBlank(val.toString())){
                                break;
                            }
                            info.setEmpId(val.toString());//职工编号
                        }else if(c==2){
    //                        info.setDepartId(getCellValue(cell).toString());//科室
                        }else if(c==3){
                            //姓名
                        }else if(c==4){
                            //身份证号
                        }else if(c>=5 && c<=10){
                            //基本工资
                            //绩效
                            //节加
                            //其他奖
                            //全勤奖
                            //补发
                            SalaryTypeEmp salaryTypeEmp = new SalaryTypeEmp();
                            salaryTypeEmp.setSalaryType(ids[c]);
                            Long amount = 0l;
                            if(val != null){
                                if(StringUtils.isNotBlank(val.toString())) {
                                    Double d = Double.parseDouble(getCellValue(cell).toString()) * ConverterSystem.MULTIPLE;
                                    amount = d.longValue();
                                }
                            }
                            salaryTypeEmp.setMoney(amount);
                            info.getSalaryTypeEmpList().add(salaryTypeEmp);
                        }else if(c==11){
                            if(val == null){
                                info.setGrossPay(0L);
                            }else {
                                if(StringUtils.isNotBlank(val.toString())){
                                    Double d = Double.parseDouble(getCellValue(cell).toString())*ConverterSystem.MULTIPLE;
                                    info.setGrossPay(d.longValue());//应领工资
                                }
                            }
                        }
                    }
                }
                if(info.getEmpId() != null) objList.add(info);
            }

        }catch (Exception e){
            throw new RuntimeException((r+1)+"-"+(c+1));
        }
        return objList;
    }

    public List<Object> getFormalSalary(Sheet sheet){
        List<Object> objList=new ArrayList<Object>();
        int c = 0;
        int r = 0;

        Row oneRow = sheet.getRow(0);
        Integer[] ids = new Integer[this.totalCells];
        for(c = 3; c <this.totalCells-4; c++){
            Cell cell = oneRow.getCell(c);
            if (null != cell) {
                List<SalaryType> types = ConverterSystem.FORMAL_SALARY_TYPE.stream().filter(type ->
                    type.getSalaryName().equals(getCellValue(cell).toString().trim())
                ).collect(Collectors.toList());
                if(types.size()>0){
                    ids[c] = types.get(0).getSalaryType();
                }else {
                    suucess[0] = 1;
                    throw new RuntimeException();
                }
            }
        }
        try{
            for(r=1;r<totalRows;r++){
                Row row = sheet.getRow(r);
                if (row == null) continue;
                SalaryMain info = new SalaryMain();
                info.setSalaryTypeEmpList(new ArrayList<>());
                //循环Excel的列
                for(c = 0; c <this.totalCells; c++){
                    Cell cell = row.getCell(c);
                    Object val = getCellValue(cell);
                    if (null != cell){
                        if(c==0){
                            if(val == null){
//                                throw new RuntimeException((r+1)+"-"+(c+1));
                                break;
                            }
                            if(StringUtils.isBlank(val.toString())){
                                break;
                            }
                            info.setEmpId(val.toString());//职工编号
                        }else if(c==1){
    //                        info.setDepartId(getCellValue(cell).toString());//科室
                        }else if(c==2){
                            //姓名
                        }else if(c==3){
                            //月工资
                        }else if(c==4){
                            //身份证号
                        }else if(c==28){
                            if(val == null){
                                info.setGrossPay(0L);
                            }else {
                                if(StringUtils.isNotBlank(val.toString())){
                                    Double d = Double.parseDouble(getCellValue(cell).toString())*ConverterSystem.MULTIPLE;
                                    info.setGrossPay(d.longValue());//应领工资
                                }
                            }
                        }else if(c==29){
                            if(val == null){
                                info.setGrossPay(0L);
                            }else {
                                if(StringUtils.isNotBlank(val.toString())) {
                                    Double d = Double.parseDouble(getCellValue(cell).toString()) * ConverterSystem.MULTIPLE;
                                    info.setNetPayroll(d.longValue());//实发工资
                                }
                            }
                        }else if(c==30){
                            //养老号
                        }else if(c==31){
                            //身份证号
                        }else if(c >= 3 && c<=27){
                                //基本工资
                                //绩效
                                //节加
                                //其他奖
                                //全勤奖
                                //补发
                            SalaryTypeEmp salaryTypeEmp = new SalaryTypeEmp();
                            salaryTypeEmp.setSalaryType(ids[c]);
                            Long amount = 0l;
                            if(val != null){
                                if(StringUtils.isNotBlank(val.toString())){
                                    Double d = Double.parseDouble(getCellValue(cell).toString()) * ConverterSystem.MULTIPLE;
                                    amount = d.longValue();
                                }
                            }
                            salaryTypeEmp.setMoney(amount);
                            info.getSalaryTypeEmpList().add(salaryTypeEmp);
                        }
                    }
                }
                if(info.getEmpId() != null) objList.add(info);
            }
        }catch (Exception e){
            throw new RuntimeException((r+1)+"-"+(c+1));
        }
        return objList;
    }

    public int[] checkExcel( InputStream inputStream, String fileName) {

//        File file1 = null;
//        try {
//            File path = new File(ResourceUtils.getURL("classpath:").getPath());
//            File file = new  File(path.getAbsolutePath(),"\\upload\\");
//            //创建一个目录 （它的路径名由当前 File 对象指定，包括任一必须的父路径。）
//            if (!file.exists()) file.mkdirs();
//            //新建一个文件
//            file1 = new File(file.getAbsoluteFile()+"\\fileupload" + new Date().getTime() + ".xlsx");
//            //将上传的文件写入新建的文件中
//
//            FileCopyUtils.copy(inputStream, new FileOutputStream(file1));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        //初始化客户信息的集合
        List<Object> objects=new ArrayList<Object>();
        //初始化输入流
        InputStream is = null;
        try{
            //验证文件名是否合格
            if(!validateExcel(fileName)){
                suucess[0] = 2;
                return suucess;
            }
            //根据文件名判断文件是2003版本还是2007版本
            boolean isExcel2003 = true;
            if(WDWUtil.isExcel2007(fileName)){
                isExcel2003 = false;
            }
            //根据新建的文件实例化输入流
            is = inputStream;
            //根据excel里面的内容读取客户信息
            objects = getExcelInfo(is, isExcel2003);
            is.close();
        }catch(Exception e){
            e.printStackTrace();
        } finally{
            if(is !=null)
            {
                try{
                    is.close();
                }catch(IOException e){
                    is = null;
                    e.printStackTrace();
                }
            }
        }
        objList = objects;
        return  suucess;
    }

    public  Object getCellValue(Cell cell){
        Object value = null;
        //格式化number String字符
        DecimalFormat df = new DecimalFormat("0");
        //日期格式化
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");
        //格式化数字
        DecimalFormat df2 = new DecimalFormat("0.00");

        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                value = cell.getRichStringCellValue().getString();
                break;
            case Cell.CELL_TYPE_NUMERIC:
                if("General".equals(cell.getCellStyle().getDataFormatString())){
                    value = df.format(cell.getNumericCellValue());
                }else if("m/d/yy".equals(cell.getCellStyle().getDataFormatString())){
                    value = sdf.format(cell.getDateCellValue());
                }else{
                    value = df2.format(cell.getNumericCellValue());
                }
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                value = cell.getBooleanCellValue();
                break;
            case Cell.CELL_TYPE_BLANK:
                value = "";
                break;
            default:
                break;
        }
        return value;
    }

    public  Workbook getWorkbook(InputStream inStr,String fileName) throws Exception{
        Workbook wb = null;
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        if(excel2003L.equals(fileType)){
            //2003-
            wb = new HSSFWorkbook(inStr);
        }else if(excel2007U.equals(fileType)){
            //2007+
            wb = new XSSFWorkbook(inStr);
        }else{
            throw new Exception("解析的文件格式有误！");
        }
        return wb;
    }
}