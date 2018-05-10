package com.sn.mvcmvpmvvm.net;

import android.os.SystemClock;

import com.sn.mvcmvpmvvm.bean.User;


/**
 * date:2017/4/19
 * author:易宸锋(dell)
 * function:根据MVC的设计原则,把模拟的网络请求逻辑拆分到Module层
 */

public class UserLoginNet {

    /**
     * 对用户输入信息是否正确进行判断
     * @param user
     * @return
     */
    public boolean sendUserLoginInfo(User user){
        //模拟耗时操作
        SystemClock.sleep(2000);
        //对用户输入的信息进行判断
        if("ycf".equals(user.username) && "ycf".equals(user.password)){
            //登录成功
            return true;
        }else{
            //登录失败
            return false;
        }
    }

}
