package com.aitian.salary.mapper;

import com.aitian.salary.Utils.BaseMapper;
import com.aitian.salary.mapper.provider.SalaryDynaSqlProvider;
import com.aitian.salary.model.BonusInfo;
import com.aitian.salary.model.SalaryMain;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

@Mapper
public interface BonusInfoMapper extends BaseMapper<BonusInfo> {
}