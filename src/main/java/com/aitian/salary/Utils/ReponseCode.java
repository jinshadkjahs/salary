package com.aitian.salary.Utils;

public class ReponseCode {

    /**
     *  请求成功
     */
    public static final String REQUEST_SUCCESS = "0000";
    /**
     * 非法请求
     */
    public static final String ILLEGAL_REQUEST = "1111";
    /**
     *  参数为空
     */
    public static final String PARAMETER_NULL_ERROR = "1001";
    /**
     * 用户或者密码错误
     */
    public static final String PWD_OR_NAME_ERROR = "1002";
    /**
     * 没有登录
     */
    public static final String NOT_LOGINED = "1003";
    /**
     * 非法参数
     */
    public static final String ILLEGAL_PARAMETER = "1004";
    /**
     * excel导入失败
     */
    public static final String EXCEL_IMPORT_ERROR = "1005";

    /**
     * 请求失败
     */
    public static final String REQUEST_ERROR = "1006";

    /**
     * 员工当月工资信息已经存在
     */
    public static final String SALARY_EXIST_ERROR = "1007";

    /**
     * 没有选择文件
     */
    public static final String HAS_NOT_FILE = "1008";

    /**
     * 文件格式不对
     */
    public static final String NOT_ALLOW_FILE = "1009";

    /**
     * 密码错误
     */
    public static final String PASSWORD_ERROR = "1010";
}
