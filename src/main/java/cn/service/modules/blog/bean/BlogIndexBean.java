package cn.service.modules.blog.bean;

import cn.service.modules.dailyWords.bean.DailyWordsBean;
import cn.service.modules.messageBoard.bean.MessageBoardBean;

import java.util.List;

public class BlogIndexBean {

	private List<BlogListBean> hotBlogs;	//近期热门博客
	private List<BlogListBean> newBlogs;	//近期最新博客
	private List<BlogListBean> recommendBlogs;//推荐博客
	private List<MessageBoardBean> messageBoards; //留言列表
	private DailyWordsBean dailyWords;  //每日一语


	public List<BlogListBean> getHotBlogs() {
		return hotBlogs;
	}
	public void setHotBlogs(List<BlogListBean> hotBlogs) {
		this.hotBlogs = hotBlogs;
	}
	public List<BlogListBean> getNewBlogs() {
		return newBlogs;
	}
	public void setNewBlogs(List<BlogListBean> newBlogs) {
		this.newBlogs = newBlogs;
	}
	public List<BlogListBean> getRecommendBlogs() {
		return recommendBlogs;
	}
	public void setRecommendBlogs(List<BlogListBean> recommendBlogs) {
		this.recommendBlogs = recommendBlogs;
	}

	public List<MessageBoardBean> getMessageBoards() {
		return messageBoards;
	}
	public void setMessageBoards(List<MessageBoardBean> messageBoards) {
		this.messageBoards = messageBoards;
	}
	public DailyWordsBean getDailyWords() {
		return dailyWords;
	}
	public void setDailyWords(DailyWordsBean dailyWords) {
		this.dailyWords = dailyWords;
	}

	
}
