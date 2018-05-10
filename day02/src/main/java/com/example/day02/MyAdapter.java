package com.example.day02;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.List;

/**
 * Created by Administrator on 2018/5/10 0010.
 */

public class MyAdapter extends BaseAdapter {
    private List<Lei.ResultBean.DataBean> list;
    private Context context;
    private DisplayImageOptions options;
    public MyAdapter(List<Lei.ResultBean.DataBean> list, Context context) {
        this.list = list;
        this.context = context;
        this.options=new DisplayImageOptions.Builder()
                .cacheInMemory(true)//使用内存缓存
                .cacheOnDisk(true)//使用磁盘缓存
                .showImageOnLoading(R.mipmap.ic_launcher)//设置正在下载的图片
                .showImageForEmptyUri(R.mipmap.ic_launcher)//url为空或请求的资源不存在时
                .showImageOnFail(R.mipmap.ic_launcher)//下载失败时显示的图片
                .bitmapConfig(Bitmap.Config.RGB_565)//设置图片色彩模式  1px=2个字节  ARGB_8888 1px=4个字节   ARGB_4444 1px=2个字节  ALPHA_8 1px=1个字节
                .imageScaleType(ImageScaleType.EXACTLY)//设置图片的缩放模式
                .displayer(new RoundedBitmapDisplayer(100))//设置图片的圆角 注意：控件
                .build();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHander hander;
        if(view==null){
            hander=new ViewHander();
            view=View.inflate(context,R.layout.listview,null);
            hander.name = view.findViewById(R.id.name);
            hander.image=view.findViewById(R.id.imageview);
            view.setTag(hander);
        }else{
            hander= (ViewHander) view.getTag();
        }
        hander.name.setText(list.get(i).getTitle());
        ImageLoader.getInstance().displayImage(list.get(i).getThumbnail_pic_s(),hander.image,options);
        return view;
    }
    class ViewHander{
        TextView name;
        ImageView image;
    }
}
