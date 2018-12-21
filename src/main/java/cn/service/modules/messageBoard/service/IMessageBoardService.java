package cn.service.modules.messageBoard.service;

import cn.service.modules.messageBoard.bean.MessageBoardBean;
import cn.service.modules.messageBoard.model.MessageBoard;
import com.github.pagehelper.PageInfo;

/**
 * Created by zw on 18/2/3.
 */
public interface IMessageBoardService {

    /**
     * 获取留言列表
     * @param queryBean
     * @param pageNum
     * @param pageSize
     * @param sort
     * @return
     */
    public PageInfo<MessageBoardBean> getMessageBoard(MessageBoardBean queryBean, int pageNum, int pageSize, String sort);


    /**
     * 留言的保存
     * @param messageBoard
     */
    public void saveMessageBoard(MessageBoard messageBoard);

}
