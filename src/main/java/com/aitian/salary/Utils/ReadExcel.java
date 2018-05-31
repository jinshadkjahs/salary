package com.aitian.salary.Utils;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.aitian.salary.model.Salary;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.aspectj.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
    private List<Salary> salaryList;
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
  public List<Salary> getExcelInfo(){
    return salaryList;
  }
  /**
   * 根据excel里面的内容读取客户信息
   * @param is 输入流
   * @param isExcel2003 excel是2003还是2007版本
   * @return
   * @throws IOException
   */
  public  List<Salary> getExcelInfo(InputStream is,boolean isExcel2003){
       List<Salary> customerList=null;
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
           customerList=readExcelValue(wb);
       }
       catch (IOException e)  {  
           e.printStackTrace();  
       }  
       return customerList;
  }
  /**
   * 读取Excel里面客户的信息
   * @param wb
   * @return
   */
  private List<Salary> readExcelValue(Workbook wb){
      //得到第一个shell  
       Sheet sheet=wb.getSheetAt(0);
       
      //得到Excel的行数
       this.totalRows=sheet.getPhysicalNumberOfRows();
       
      //得到Excel的列数(前提是有行数)
       if(totalRows>=1 && sheet.getRow(0) != null){
            this.totalCells=sheet.getRow(0).getPhysicalNumberOfCells();
       }
       
       List<Salary> customerList=new ArrayList<Salary>();
      Salary customer;
      //循环Excel行数,从第二行开始。标题不入库
       for(int r=1;r<totalRows;r++){
           Row row = sheet.getRow(r);
           if (row == null) continue;
           customer = new Salary();
           
           //循环Excel的列
           for(int c = 0; c <this.totalCells; c++){    
               Cell cell = row.getCell(c);
//               if (null != cell){
//                   if(c==0){//第一列不读
//                   }else if(c==1){
//                       customer.setcName(cell.getStringCellValue());//客户名称
//                   }else if(c==2){
//                       customer.setSimpleName(cell.getStringCellValue());//客户简称
//                   }else if(c==3){
//                       customer.setTrade(cell.getStringCellValue());//行业
//                   }else if(c==4){
//                       customer.setSource(cell.getStringCellValue());//客户来源
//                   }else if(c==5){
//                       customer.setAddress(cell.getStringCellValue());//地址
//                   }else if(c==6){
//                       customer.setRemark(cell.getStringCellValue());//备注信息
//                   }
//               }
           }
           //添加客户
           customerList.add(customer);
       }
       return customerList;
    }

    public int[] checkExcel( FileInputStream inputStream, String fileName) {
       int[] suucess = new int[0];
        File file1 = null;
        try {
            File path = new File(ResourceUtils.getURL("classpath:").getPath());
            File file = new  File(path.getAbsolutePath(),"\\upload\\");
            //创建一个目录 （它的路径名由当前 File 对象指定，包括任一必须的父路径。）
            if (!file.exists()) file.mkdirs();
            //新建一个文件
            file1 = new File(file.getAbsoluteFile()+"\\fileupload" + new Date().getTime() + ".xlsx");
            //将上传的文件写入新建的文件中

            FileUtil.copyStream(inputStream, new FileOutputStream(file1));
        } catch (Exception e) {
            e.printStackTrace();
        }

        //初始化客户信息的集合
        List<Salary> salarys=new ArrayList<Salary>();
        //初始化输入流
        InputStream is = null;
        try{
            //验证文件名是否合格
            if(!validateExcel(fileName)){
                return null;
            }
            //根据文件名判断文件是2003版本还是2007版本
            boolean isExcel2003 = true;
            if(WDWUtil.isExcel2007(fileName)){
                isExcel2003 = false;
            }
            //根据新建的文件实例化输入流
            is = new FileInputStream(file1);
            //根据excel里面的内容读取客户信息
            salarys = getExcelInfo(is, isExcel2003);
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
        salaryList = salarys;
        return  suucess;
    }

}