package cn.service.modules.moments.controller;

import cn.service.common.bean.Result;
import cn.service.common.interceptor.LoginInterceptor;
import cn.service.common.util.Global;
import cn.service.common.util.ReturnUtil;
import cn.service.modules.moments.bean.MomentsBean;
import cn.service.modules.moments.model.Moments;
import cn.service.modules.moments.service.IMomentsService;
import cn.service.modules.user.model.User;

import com.file.service.IFtpImageService;
import com.github.pagehelper.PageInfo;
import com.wordnik.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.Date;

/**
 * Created by zw on 18/2/5.
 */
@Controller
@RequestMapping("api/moments")
public class MomentsController {

    @Autowired
    private IMomentsService momentsService;

    @Autowired
    private IFtpImageService imageUpDownService;


    
    @RequestMapping(value ="/getMoments", produces ="application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(notes = "getMoments", value = "获取随笔心情列表", httpMethod = "GET", responseClass = "java.java.lang.String")
    public String getMessages(HttpServletRequest request, HttpServletResponse response,
                           @RequestParam(value = "userId",required = false) Integer userId,
                           @RequestParam(value = "sort",required = false) String sort,
                           @RequestParam(value = "pageNum",required = false) Integer pageNum,
                           @RequestParam(value = "pageSize" ,required = false) Integer pageSize){
        pageNum = Global.getDefaultVaule(pageNum, 1);
        pageSize = Global.getDefaultVaule(pageSize, Integer.parseInt(Global.getDefaultPageSize()));
        MomentsBean queryBean = new MomentsBean();
        queryBean.setUserId(userId);
        PageInfo<MomentsBean> page = null;
        try {
            page = momentsService.getMoments(queryBean, pageNum, pageSize, sort);
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnUtil.returnJson("999","发生异常!");
        }
        return ReturnUtil.returnJson("1000","获取成功",page);

    }
    
    
    
    @RequestMapping(value ="/saveMoments", produces ="application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(notes = "saveMoments", value = "保存随笔心情", httpMethod = "POST", responseClass = "java.java.lang.String")
    public String getMyCategory(HttpServletRequest request, HttpServletResponse response,
                                @RequestParam String content,
                                @RequestParam(value = "imageUrl",required = false) String imageUrl){
        User user = (User) request.getAttribute(LoginInterceptor.CURRENT_USER);
        try {
            if (user != null) {
                if (user.getId() != 1) {
                    return ReturnUtil.returnJson("1112", "用户暂无权限!");
                } else {
                    Moments moments = new Moments();
                    moments.setUserId(user.getId());
                    moments.setContent(content);
                    moments.setImageUrl(imageUrl);
                    moments.setCreateDate(new Date());
                    momentsService.saveMoments(moments);
                    return ReturnUtil.returnJson("1000", "获取成功");
                }
            } else {
                return ReturnUtil.returnJson("1111", "用户未登录!");
            }
        } catch (Exception e) {
                e.printStackTrace();
                return ReturnUtil.returnJson("999","发生异常!");
        }
    }


    @RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
    @ResponseBody
    public HttpEntity<Result> uploadImage(
            MultipartFile file) {
        System.out.println("方法开始执行了！");
        System.out.println(file.isEmpty());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_HTML);
        try {
            if (!file.isEmpty()) {
                String suffix = null;
                int index = file.getOriginalFilename().lastIndexOf(".");
                if (index != -1
                        && index != file.getOriginalFilename().length() - 1) {
                    suffix = file.getOriginalFilename().substring(index + 1);
                }
                if (suffix == null || "png|jpg|jpeg|bmp".indexOf(suffix) == -1) {
                    Result result = new Result();
                    result.setMessage("请上传图片文件");
                    result.setResultCode("1001"); // 1001为 文件格式不正确
                    HttpEntity<Result> httpEntity = new HttpEntity<Result>(
                            result, headers);
                    return httpEntity;
                }
                Result result = new Result();
                InputStream is = file.getInputStream();
                BufferedImage src = ImageIO.read(is);
                System.out.println("宽："+src.getWidth()+"高："+src.getHeight());
                if (src.getWidth() == 0 || src.getHeight() == 0) {
                    result.setMessage("图片大小不符合规范");
                    result.setResultCode("1002"); // 1002为图片大小不符合规范
                    HttpEntity<Result> httpEntity = new HttpEntity<Result>(
                            result, headers);
                    return httpEntity;
                } else {
                    String remoteFileName = imageUpDownService.uploadImage(false,file.getOriginalFilename(), suffix, file.getInputStream());
                    result.setMessage(remoteFileName);
                    result.setResultCode("1000"); // 1000上传成功
                    HttpEntity<Result> httpEntity = new HttpEntity<Result>(
                            result, headers);
                    return httpEntity;
                }
            }
        } catch (Exception e) {
            Result result = new Result();
            result.setMessage("上传失败"+e.getMessage());
            result.setResultCode("1000"); // 1000上传成功
            HttpEntity<Result> httpEntity = new HttpEntity<Result>(
                    result, headers);
            return httpEntity;
        }
        return null;

    }


}
