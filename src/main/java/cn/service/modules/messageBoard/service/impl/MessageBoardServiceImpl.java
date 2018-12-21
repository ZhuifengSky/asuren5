package cn.service.modules.messageBoard.service.impl;

import cn.service.modules.messageBoard.bean.MessageBoardBean;
import cn.service.modules.messageBoard.dao.MessageBoardDao;
import cn.service.modules.messageBoard.model.MessageBoard;
import cn.service.modules.messageBoard.service.IMessageBoardService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zw on 18/2/3.
 */
@Service
public class MessageBoardServiceImpl implements IMessageBoardService {

    @Autowired
    private MessageBoardDao messageBoardDao;
    /**
     * 获取留言列表
     * @param queryBean
     * @param pageNum
     * @param pageSize
     * @param sort
     * @return
     */
    public PageInfo<MessageBoardBean> getMessageBoard(MessageBoardBean queryBean, int pageNum, int pageSize, String sort){
        if (sort==null || sort.equals("")) {
            sort="b.create_date desc";
        }
        PageHelper.startPage(pageNum, pageSize);
        PageHelper.orderBy(sort);
        List<MessageBoardBean> list = messageBoardDao.findList(queryBean);
        return new PageInfo<>(list);
    }


    /**
     * 留言的保存
     * @param messageBoard
     */
    public void saveMessageBoard(MessageBoard messageBoard){
        messageBoardDao.insertSelective(messageBoard);
    }
}

