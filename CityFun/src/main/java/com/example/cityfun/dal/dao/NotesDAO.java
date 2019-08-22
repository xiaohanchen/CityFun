package com.example.cityfun.dal.dao;

import java.util.List;

import com.example.cityfun.dal.dao.base.BaseNotesDAO;
import com.example.cityfun.dal.dataobject.NotesDO;
import org.apache.ibatis.annotations.Mapper;

/**
* @author dalgen
*/
@Mapper
public interface NotesDAO extends BaseNotesDAO {

    List<NotesDO> queryAll();

}

