package com.example.service;

import cn.hutool.core.date.DateUtil;
import com.example.common.enums.ActivityStatusEnum;
import com.example.entity.Account;
import com.example.entity.Activity;
import com.example.entity.ActivitySign;
import com.example.mapper.ActivityMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 公益活动信息表业务处理
 **/
@Service
public class ActivityService extends BaseService{

    @Resource
    private ActivityMapper activityMapper;

    @Resource
    private ActivitySignService activitySignService;

    /**
     * 新增
     */
    public void add(Activity activity) {
        activityMapper.insert(activity);
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        activityMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            activityMapper.deleteById(id);
        }
    }

    /**
     * 修改
     */
    public void updateById(Activity activity) {
        activityMapper.updateById(activity);
    }

    /**
     * 根据ID查询
     */
    public Activity selectById(Integer id) {
        Activity activity = activityMapper.selectById(id);
        Account currentUser = TokenUtils.getCurrentUser();
        ActivitySign activitySign = activitySignService.selectByActivityIdAndUserId(id, currentUser.getId());
        if (activitySign == null) {  // 没报名
            activity.setStatus(ActivityStatusEnum.NOT_SIGN.getValue());
        } else {
            activity.setStatus(ActivityStatusEnum.IS_SIGN.getValue());
        }
        Date date = new Date();
        String start = activity.getStart();
        if (DateUtil.parseDate(start).isAfter(date)) {
            activity.setStatus(ActivityStatusEnum.NOT_START.getValue());
        }
        String end = activity.getEnd();
        if (DateUtil.parseDate(end).isBefore(date)) {
            activity.setStatus(ActivityStatusEnum.IS_END.getValue());
        }
        return activity;
    }

    /**
     * 查询所有
     */
    public List<Activity> selectAll(Activity activity) {

        return activityMapper.selectAll(activity);
    }

    /**
     * 分页查询
     */
    public PageInfo<Activity> selectPage(Activity activity, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Activity> list = activityMapper.selectAll(activity);

        //改造代码部分
        Map<String, String> checkSwitchOpen = ParaConfig("CHECK_SWITCH_OPEN");
        String paracode1 = checkSwitchOpen.get("PARACODE1");
        List<Activity> list_return = new ArrayList<>();
        if(paracode1.equals("true")){
            for (Activity activity1 : list) {
                if(activity1.getTag()==0){
                    list_return.add(activity1);
                }
            }
            list = list_return;
        }
        //改造完成

        PageInfo<Activity> activityPageInfo = PageInfo.of(list);

        return activityPageInfo;
    }



    /**
     * 分页查询
     */
    public PageInfo<Activity> selectPageTEST(Activity activity, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Activity> list = activityMapper.selectAll(activity);

        //改造代码部分
        Map<String, String> checkSwitchOpen = ParaConfig("CHECK_SWITCH_OPEN");
        String paracode1 = checkSwitchOpen.get("PARACODE1");
        List<Activity> list_return = new ArrayList<>();
        if(paracode1.equals("true")){
            for (Activity activity1 : list) {
                if(activity1.getTag()==0){
                    list_return.add(activity1);
                }
            }
            list = list_return;
        }
        //改造完成

        PageInfo<Activity> activityPageInfo = PageInfo.of(list);




        return activityPageInfo;
    }
}