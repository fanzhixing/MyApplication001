package com.sn.mvcmvpmvvm;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sn.mvcmvpmvvm.bean.User;
import com.sn.mvcmvpmvvm.presenter.UserLoginPresenter;

import static com.sn.mvcmvpmvvm.R.id.username;

/**
 *
 * MVC:实际就是抽了个Module,也就是对项目的网络,数据库抽出来.
 * MVVM:实际就是把业务逻辑,保留在Activity或Fragment里,把视图给抽出来

 * MVP(Module +View +Presenter):用到了,接口,多态知识点.不仅仅拆分Module,还对Activity或fragment的业务逻辑层进行拆分.把业务逻辑给抽出来
 * MVP实际上就是MVC的升级版,严格的讲Android的视图层和逻辑层拆分出来,一个功能拆分出数个类,,根据模块功能拆分出抽象类,使用Bean类进行数据的传递.
 * 关键点:基于MVC,module必抽,业务逻辑,"presenter",接口
 *
 * 0.把原始代码改为MVC的设计模式.MVP是MVC的升级.
 * 1.阅读要拆分的代码,把Activity或Fragment的代码分成两部分,一部分为业务逻辑 ,一部分为视图
 * 2.将Activity中的业务逻辑代码,拆分到presenter包下UserLoginPresenter中.
 * 3.定义使用IUserLoginView接口,使Activity与UserLoginPresenter耦合性降低.
 * 特点:技术要求较高,写起来麻烦,但是项目的阅读性和可维护性高
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener, UserLoginPresenterInterface {

    //视图层
    private EditText mUsername;
    private EditText mPassword;
    private Button login;
    private ProgressDialog mProgressDialog;
    private UserLoginPresenter mUserLoginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //进行控件初始化
        initView();
        //动态的创建一个进度条,视图层
        mProgressDialog = new ProgressDialog(this);

        //创建presenter类对象,把Activity的业务逻辑的类对象拆分出去,把Activity对象传进去
        mUserLoginPresenter = new UserLoginPresenter(this);

    }

    //点击按钮,上传用户信息
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                //视图层
                final String  username= mUsername.getText().toString().trim();
                final String  password= mPassword.getText().toString().trim();
                //创建Bean类,然后把数据放到bean类里,视图层
                final User user = new User();
                user.username=username;
                user.password=password;

                //判断信息是否为null,业务逻辑层
                boolean userInfo = mUserLoginPresenter.submit(user);
                //也是业务逻辑.****
                if (userInfo){
                    //显示进度条,视图层
                    mProgressDialog.show();
                    //1.使用presenter,这个处理业务逻辑的类,对用户输入信息是否正确进行判断
                    mUserLoginPresenter.login(user);
                }else {
                    //视图层
                    Toast.makeText(MainActivity.this, "输入不能为null,么么哒", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
    public void success() {
        //5.登录成功的逻辑,谈一个吐司,更新UI       视图层
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //7.关闭进度条,弹吐司
                mProgressDialog.dismiss();
                Toast.makeText(MainActivity.this, "欢迎回来,思密达", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void failed() {
        //登录失败的逻辑,谈一个吐司,更新UI    视图层
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //关闭进度条
                mProgressDialog.dismiss();
                Toast.makeText(MainActivity.this, "您的信息有误,请重新填写", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //初始化控件     视图层
    private void initView() {
        mUsername = (EditText) findViewById(username);
        mPassword = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);

        login.setOnClickListener(this);
    }


}