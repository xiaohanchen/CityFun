package com.example.cityfun;

import java.util.List;

import javax.annotation.Resource;

import com.example.cityfun.dal.dao.NotesDAO;
import com.example.cityfun.dal.dao.StudentDAO;
import com.example.cityfun.dal.dataobject.NotesDO;
import com.example.cityfun.dal.dataobject.StudentDO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EasyNotesApplicationTests {

	@Resource
	StudentDAO studentDAO;


	@Resource
	NotesDAO notesDAO;

	@Test
	public void contextLoads() {
	//	StudentDO studentDO = new StudentDO();
	//	studentDO.setSId("sad");
	//	studentDO.setSBirth("asda");
	//	studentDO.setSName("asda");
	//	studentDO.setSSex("asda");
	//	studentDAO.insertNotNull(studentDO);

		List<NotesDO> notesDOS = notesDAO.queryAll();
		Assert.assertNotNull(notesDOS);

	}
}
