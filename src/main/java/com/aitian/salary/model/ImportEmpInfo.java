package com.aitian.salary.model;

public class ImportEmpInfo {
    //导入成功数量
    private Integer successNums;

    //导入失败数量
    private Integer failNums;

    //导入失败员工编号
    private String failEmpNo;

    //是否全部导入
    private boolean isAllImport;

    public boolean isAllImport() {
        return isAllImport;
    }

    public void setAllImport(boolean allImport) {
        isAllImport = allImport;
    }

    public ImportEmpInfo() {
    }

    public ImportEmpInfo(Integer successNums, Integer failNums, String failEmpNo) {
        this.successNums = successNums;
        this.failNums = failNums;
        this.failEmpNo = failEmpNo;
    }

    public int getSuccessNums() {
        return successNums;
    }

    public void setSuccessNums(Integer successNums) {
        this.successNums = successNums;
    }

    public int getFailNums() {
        return failNums;
    }

    public void setFailNums(Integer failNums) {
        this.failNums = failNums;
    }

    public String getFailEmpNo() {
        return failEmpNo;
    }

    public void setFailEmpNo(String failEmpNo) {
        this.failEmpNo = failEmpNo;
    }

    @Override
    public String toString() {
        return "ImportEmpInfo{" +
                "successNums=" + successNums +
                ", failNums=" + failNums +
                ", failEmpNo='" + failEmpNo + '\'' +
                ", isAllImport=" + isAllImport +
                '}';
    }
}
