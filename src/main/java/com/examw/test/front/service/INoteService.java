package com.examw.test.front.service;

import java.io.IOException;

import com.examw.model.DataGrid;
import com.examw.model.Json;
import com.examw.test.front.model.record.NoteInfo;

/**
 * 笔记服务接口
 * @author fengwei.
 * @since 2014年9月23日 下午2:50:55.
 */
public interface INoteService {
	/**
	 * 查询笔记
	 * @param structureItemId
	 * @param userId
	 * @return
	 * @throws IOException 
	 */
	DataGrid<NoteInfo> findNotes(NoteInfo note) throws Exception;
	/**
	 * 添加笔记
	 * @param info
	 * @return
	 * @throws IOException 
	 */
	Json addNote(NoteInfo info) throws Exception;
}
