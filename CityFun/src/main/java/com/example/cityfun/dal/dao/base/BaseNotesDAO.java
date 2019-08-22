package com.example.cityfun.dal.dao.base;

import com.example.cityfun.dal.dataobject.NotesDO;

/**
* BaseNotesDAO 自动生成基类
* @author dalgen
*/
public interface BaseNotesDAO {

    /**
     * 插入数据,只会插入非空字段
     * @param createDO 要插入的DO类
     * @return 影响行数
     **/
    int insertNotNull(NotesDO createDO);


    /**
    * 更新数据,只会更新非空字段
    * @param updateDO 要更新的DO类
    * @return 影响行数
    **/
    int updateNotNull(NotesDO updateDO);

}

