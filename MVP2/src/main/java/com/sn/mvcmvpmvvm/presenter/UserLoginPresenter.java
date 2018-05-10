package com.sn.mvcmvpmvvm.presenter;

import android.text.TextUtils;

import com.sn.mvcmvpmvvm.UserLoginPresenterInterface;
import com.sn.mvcmvpmvvm.bean.User;
import com.sn.mvcmvpmvvm.net.UserLoginNet;

/**
 * date:2017/4/20
 * author:易宸锋(dell)
 * function:根据MVP的设计原则,把Activity里的业务逻辑拆分到Presenter层,使用网络工具也在这里,直接拿数据
 * 1.把业务逻辑代码放到这个类下
 * 2.把业务逻辑混合着更新视图的代码,也放到该类下
 * 3.混合着视图的业务逻辑代码,将其中的视图代码给抽成方法,放到我们presenter.
 * 4.就是把这些视图代码所抽成的方法,再给扔回Activity或Fragment里.
 *
 * 方法由谁调用?
 * 谁调用该方法,那么该方法肯定就在该类下?
 */

public class UserLoginPresenter {
    /**
     * 对用户输入进行非空判断,业务逻辑
     *
     * @param user
     * @return
     */
    public boolean submit(User user) {
        if (TextUtils.isEmpty(user.username) || TextUtils.isEmpty(user.password)) {
            return false;
        }
        return true;
    }

    private final UserLoginPresenterInterface mUserLoginPresenterInterface;

    /**
     * 参数是mainActivity的弊端:就是扩展性差,如果我这里信息代码重构,把Activity改为了Fragment,那么我的Presenter就不能用.
     * 为了提高Activity和Fragment的通用性 : 放置参数为接口或抽象类,实际开发中以接口居多.
     * 概念介绍:
     * 没有接口,指定具体的类:易宸锋的办公室需要打扫了,易宸锋办公室门,是人工智能门,门可以根据来者相貌,判读出这个身份,从而决定谁可以进入,谁进不去
     * 那么有一天,我对智能门说了,下午5点,我派小明过来打扫卫生,你记着放行,结果呢,到了下午5点,小明有事情,来不了,但是卫生还要打扫,所以小明就拜托了
     * 一个同学小红,去易老师的办公室打扫卫生,小红来打智能门面前,看到小红不是小明,所以坚决不放行,因此易宸锋的办公室没有被打扫.
     *
     * 定义接口,没有指定具体的类:易宸锋的办公室需要打扫了,易宸锋办公室门,是人工智能门,门可以根据来者相貌,判读出这个身份,从而决定谁可以进入,谁进不去
     * 那么有一天,我对智能门说了,下午5点,我派学生过来打扫卫生,你记着放行,结果呢,到了下午5点,小明有事情,来不了,但是卫生还要打扫,所以小明就拜托了
     * 一个同学小红,去易老师的办公室打扫卫生,小红来打智能门面前,智能判定小红是学生,所以放行,因此易宸锋的办公室就打扫了.
     */
    public UserLoginPresenter(UserLoginPresenterInterface userLoginPresenterInterface) {
        mUserLoginPresenterInterface = userLoginPresenterInterface;
    }

    public void login(final User user){
        //2.开一个子线程做耗时操作,业务逻辑
        new Thread() {
            public void run () {
                //3.创建网络工具类对象,业务逻辑
                UserLoginNet net = new UserLoginNet();
                //4.对用户输入的信息进行判断,,业务逻辑
                if (net.sendUserLoginInfo(user)) {
                    //5.Activity对象调用更新视图的方法
                    mUserLoginPresenterInterface.success();

                } else {
                    mUserLoginPresenterInterface.failed();
                }
            }
        }.start();
    }
}
