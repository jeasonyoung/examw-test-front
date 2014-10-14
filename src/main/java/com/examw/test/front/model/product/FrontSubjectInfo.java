package com.examw.test.front.model.product;
/**
 * 考试科目前台模型
 * @author fengwei.
 * @since 2014年10月14日 下午4:45:37.
 */
public class FrontSubjectInfo extends SubjectInfo{
	private static final long serialVersionUID = 1L;
	private Long totalFavors;	//收藏个数
	/**
	 * 获取 
	 * @return totalFavors
	 * 
	 */
	public Long getTotalFavors() {
		return totalFavors;
	}
	/**
	 * 设置 
	 * @param totalFavors
	 * 
	 */
	public void setTotalFavors(Long totalFavors) {
		this.totalFavors = totalFavors;
	}
	
}
