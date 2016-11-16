package com.example.ashwin.photoviewinviewpager;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by ashwin on 10/11/16.
 */

public class ViewPagerAdapter extends PagerAdapter
{
    private ArrayList<Integer> IMAGES;
    private LayoutInflater inflater;
    private Context context;
    //private SimpleDraweeView draweeView;
    //private PhotoViewAttacher mAttacher;


    public ViewPagerAdapter(Context context, ArrayList<Integer> IMAGES) {
        this.context = context;
        this.IMAGES=IMAGES;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return IMAGES.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, final int position) {

        inflater = (LayoutInflater) view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View imageLayout = inflater.inflate(R.layout.image_layout, view, false);

        assert imageLayout != null;
        final PhotoView imageView = (PhotoView) imageLayout.findViewById(R.id.image);

        /**
         * fresco
         */
        /*SimpleDraweeView draweeView;
        DraweeController controller;
        String TAG = "SlidingImagePagerAdapter";
        // image from web
        WrapContentDraweeView wrapDraweeView = (WrapContentDraweeView) view.findViewById(R.id.imageView);
        wrapDraweeView.setCallingClass(TAG);
        wrapDraweeView.setImageURI(Uri.parse( IMAGES.get(position) ));*/


        /**
         * simple image
         */
        //imageView.setImageResource(R.drawable.image1);


        /**
         * glide
         */
        /*Glide.with(imageLayout.getContext())
           .load( "assets2.mirraw.com/images/1225697/SDJ017_small_m.jpg?75770890" )
           .placeholder(R.drawable.image1)
           .dontAnimate()
           .fitCenter()
           .error(R.drawable.default_avatar_circular)
           .into(imageView);*/


        /**
         * uil
         */
        ImageLoader imageLoader = ImageLoader.getInstance();

        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .showImageOnLoading(R.drawable.loading)
                .showImageOnFail(R.drawable.no_image_available)
                .displayer(new FadeInBitmapDisplayer(300))
                .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context.getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache())
                .discCacheSize(500 * 1024 * 1024)
                .build();

        ImageLoader.getInstance().init(config);

        imageLoader.displayImage("drawable://"+IMAGES.get(position), imageView, defaultOptions);

        view.addView(imageLayout, 0);

        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }
}
