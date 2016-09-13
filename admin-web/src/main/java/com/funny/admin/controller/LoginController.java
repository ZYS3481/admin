package com.funny.admin.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.funny.admin.domain.sys.condition.MenuCondition;
import com.funny.admin.domain.sys.vo.MenuVo;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.funny.admin.domain.sys.entity.MenuEntity;
import com.funny.admin.service.sys.MenuService;
import com.funny.result.JsonResult;

@Controller
public class LoginController {

    @Resource
    private MenuService menuService;

    /**
     * 访问登录页
     * 
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/toLogin")
    public ModelAndView toLogin() throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("login");
        return mv;
    }

    /**
     * 请求登录，验证用户
     * 
     * @return
     * @throws Exception
     */
    @RequestMapping("/index")
    public ModelAndView login() {
        return new ModelAndView("index");
    }

    @RequestMapping("/main")
    public ModelAndView test() {
        return new ModelAndView("main");
    }

    /**
     * 请求登录，验证用户
     * 
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/createMenuTree")
    @ResponseBody
    public JsonResult createMenuTree() {
        JsonResult jsonResult = new JsonResult();
        try {
            List<MenuEntity> menuList = menuService.selectMenuListByCondition(new MenuCondition());
            Map<Long, MenuEntity> menuEntityMap = Maps.newHashMap();
            Map<Long, List<MenuVo>> childMap = Maps.newHashMap();

            for (MenuEntity menu : menuList) {
                menuEntityMap.put(menu.getId(), menu);
            }
            List<MenuVo> entityList = null;
            for (MenuEntity menu : menuList) {
                if (childMap.get(menu.getParentId()) == null) {
                    entityList = Lists.newArrayList();
                } else {
                    entityList = childMap.get(menu.getParentId());
                }
                MenuVo menuVo = new MenuVo();
                BeanUtils.copyProperties(menu, menuVo);
                entityList.add(menuVo);
                childMap.put(menu.getParentId(), entityList);
            }
            List<MenuVo> menuVoList = Lists.newArrayList();
            for (MenuEntity menu : menuList) {
                MenuVo menuVo = new MenuVo();
                BeanUtils.copyProperties(menu, menuVo);
                menuVo.setChildList(childMap.get(menuVo.getId()));
                menuVoList.add(menuVo);
            }
			Predicate<MenuVo> menuVoPredicate = new Predicate<MenuVo>() {
				@Override
				public boolean apply(MenuVo vo) {
					return vo.getParentId() == 0L;
				}
			};
			List<MenuVo> resultList = Lists.newArrayList(Iterables.filter(menuVoList, menuVoPredicate));
            jsonResult.setSuccess(resultList);
        } catch (Exception e) {
            e.printStackTrace();
            jsonResult.setReturncode(500);
            jsonResult.setMessage(e.getMessage());
        }
        return jsonResult;
    }

    /**
     * 用户注销
     * 
     * @return
     */
    @RequestMapping(value = "/logout")
    public ModelAndView logout() {
        // String USERNAME = Jurisdiction.getUsername(); //当前登录的用户名
        ModelAndView mv = new ModelAndView();
        // Session session = Jurisdiction.getSession(); //以下清除session缓存
        // session.removeAttribute(Const.SESSION_USER);
        // session.removeAttribute(USERNAME + Const.SESSION_ROLE_RIGHTS);
        // session.removeAttribute(USERNAME + Const.SESSION_allmenuList);
        // session.removeAttribute(USERNAME + Const.SESSION_menuList);
        // session.removeAttribute(USERNAME + Const.SESSION_QX);
        // session.removeAttribute(Const.SESSION_userpds);
        // session.removeAttribute(Const.SESSION_USERNAME);
        // session.removeAttribute(Const.SESSION_USERROL);
        // session.removeAttribute("changeMenu");
        // //shiro销毁登录
        // Subject subject = SecurityUtils.getSubject();
        // subject.logout();
        // pd.put("msg", pd.getString("msg"));
        // pd.put("SYSNAME", Tools.readTxtFile(Const.SYSNAME)); //读取系统名称
        // mv.setViewName("system/index/login");
        // mv.addObject("pd",pd);
        return mv;
    }
}
