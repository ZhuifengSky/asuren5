package cn.service.modules.moments.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.service.modules.moments.bean.MomentsBean;
import cn.service.modules.moments.dao.MomentsDao;
import cn.service.modules.moments.model.Moments;
import cn.service.modules.moments.service.IMomentsService;

import javax.annotation.Resource;

@Service
public class MomentServiceImpl implements IMomentsService {

	@Resource
	private MomentsDao momentsDaoImpl;
	
	@Override
	public PageInfo<MomentsBean> getMoments(MomentsBean queryBean, int pageNum,
			int pageSize, String sort) throws Exception {
		if (sort==null || sort.equals("")) {
            sort="m.create_date desc";
        }
        PageHelper.startPage(pageNum, pageSize);
        PageHelper.orderBy(sort);
        List<MomentsBean> list = momentsDaoImpl.findList(queryBean);
        return new PageInfo<>(list);
	}

	@Override
	public void saveMoments(Moments momment) throws Exception {
		momentsDaoImpl.insertSelective(momment);
	}

	@Override
	public int deleteMoments(Integer id) throws Exception {
		return momentsDaoImpl.deleteByPrimaryKey(id);
	}
	
	

}
