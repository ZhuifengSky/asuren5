package cn.service.modules.dailyWords.bean;

import cn.service.modules.dailyWords.model.DailyWords;

public class DailyWordsBean {

	private String showType;
	private DailyWords dailyWords;
	
	public String getShowType() {
		return showType;
	}
	public void setShowType(String showType) {
		this.showType = showType;
	}
	public DailyWords getDailyWords() {
		return dailyWords;
	}
	public void setDailyWords(DailyWords dailyWords) {
		this.dailyWords = dailyWords;
	}
	
	
}
