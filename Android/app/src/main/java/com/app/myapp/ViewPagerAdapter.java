package com.app.myapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ViewPagerAdapter extends PagerAdapter {

    ArrayList<String> medTitle,medDesc;
    ArrayList<Bitmap> Picture;
    Context context;
    ImageView sliderImage;

    public ViewPagerAdapter(Context context, ArrayList<Bitmap> image, ArrayList<String>  Title, ArrayList<String>  Desc){
        //postData();
        this.context = context;
        this.Picture=image;
        this.medDesc=Desc;
        this.medTitle=Title;

    }

    @Override
    public int getCount() {
        return medTitle.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (LinearLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slider_screen,container,false);  //連接頁面
        sliderImage = view.findViewById(R.id.sliderImage);  //取得頁面元件
        TextView sliderTitle = view.findViewById(R.id.sliderTitle);   //取得頁面元件
        TextView sliderDesc = view.findViewById(R.id.sliderDesc);    //取得頁面元件
        sliderImage.setImageBitmap(Picture.get(position));
        sliderTitle.setText(this.medTitle.get(position));
        sliderDesc.setText(this.medDesc.get(position));
        container.addView(view);   //將元件放入ViewPager
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout)object);
    }
}
