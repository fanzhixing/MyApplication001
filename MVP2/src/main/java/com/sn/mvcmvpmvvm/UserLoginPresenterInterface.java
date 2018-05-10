package com.sn.mvcmvpmvvm;

/**
 * date:2017/4/20
 * author:易宸锋(dell)
 * function:定义接口,无论是Activity还是Fragment都可以使用Presenter的业务逻辑
 * 提示:接口里的代码注释必须写(分配任务:高级程序员,负责写Presenter层,写业务逻辑,定义接口,中级程序员负责写VIew层,更新数据用接口)
 */

public interface UserLoginPresenterInterface {

    /**
     * 登录成功
     */
    void success();

    /**
     * 登录失败
     */
    void failed();
}
