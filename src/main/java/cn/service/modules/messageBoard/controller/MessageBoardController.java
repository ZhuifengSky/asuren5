package cn.service.modules.messageBoard.controller;

import cn.service.common.interceptor.LoginInterceptor;
import cn.service.common.util.Global;
import cn.service.common.util.ReturnUtil;
import cn.service.modules.messageBoard.bean.MessageBoardBean;
import cn.service.modules.messageBoard.model.MessageBoard;
import cn.service.modules.messageBoard.service.IMessageBoardService;
import cn.service.modules.user.model.User;

import com.github.pagehelper.PageInfo;
import com.wordnik.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Date;

/**
 * Created by zw on 18/2/5.
 */
@Controller
@RequestMapping("api/message")
public class MessageBoardController {

    @Autowired
    private IMessageBoardService messageBoardService;


    
    @RequestMapping(value ="/getMessages", produces ="application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(notes = "getMessages", value = "获取留言列表", httpMethod = "GET", responseClass = "java.java.lang.String")
    public String getMessages(HttpServletRequest request, HttpServletResponse response,
                           @RequestParam(value = "userId",required = false) Integer userId,
                           @RequestParam(value = "sort",required = false) String sort,
                           @RequestParam(value = "pageNum",required = false) Integer pageNum,
                           @RequestParam(value = "pageSize" ,required = false) Integer pageSize){
        pageNum = Global.getDefaultVaule(pageNum, 1);
        pageSize = Global.getDefaultVaule(pageSize, Integer.parseInt(Global.getDefaultPageSize()));
        MessageBoardBean queryBean = new MessageBoardBean();
        queryBean.setUserId(userId);
        PageInfo<MessageBoardBean> page = messageBoardService.getMessageBoard(queryBean,pageNum,pageSize,sort);
        return ReturnUtil.returnJson("1000","获取成功",page);

    }
    
    
    
    @RequestMapping(value ="/saveMessage", produces ="application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(notes = "saveMessage", value = "留言保存", httpMethod = "POST", responseClass = "java.java.lang.String")
    public String saveMessage(HttpServletRequest request, HttpServletResponse response,
                                @RequestParam String message,
                                @RequestParam String messageText){
        User user = (User) request.getAttribute(LoginInterceptor.CURRENT_USER);
        if (user!=null) {
            MessageBoard messageBoard = new MessageBoard();
            messageBoard.setUserId(user.getId());
            messageBoard.setMessage(message);
            messageBoard.setMessageText(messageText);
            messageBoard.setCreateDate(new Date());
            messageBoardService.saveMessageBoard(messageBoard);
            return ReturnUtil.returnJson("1000","保存成功");
        }else{
            return ReturnUtil.returnJson("1111", "用户未登录!");
        }
    }
}
