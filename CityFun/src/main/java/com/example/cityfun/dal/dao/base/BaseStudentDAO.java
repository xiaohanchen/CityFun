package com.example.cityfun.dal.dao.base;

import com.example.cityfun.dal.dataobject.StudentDO;
import org.apache.ibatis.annotations.Mapper;

/**
* BaseStudentDAO 自动生成基类
* @author dalgen
*/
@Mapper
public interface BaseStudentDAO {

    /**
     * 插入数据,只会插入非空字段
     * @param createDO 要插入的DO类
     * @return 影响行数
     **/
    int insertNotNull(StudentDO createDO);


    /**
    * 更新数据,只会更新非空字段
    * @param updateDO 要更新的DO类
    * @return 影响行数
    **/
    int updateNotNull(StudentDO updateDO);

}

