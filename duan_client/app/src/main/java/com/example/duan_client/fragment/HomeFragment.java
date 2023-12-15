package com.example.duan_client.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

import com.example.duan_client.OrderActivity;
import com.example.duan_client.R;
import com.example.duan_client.adapter.SlideViewPager2Adapter;
import com.example.duan_client.model.Photo;
import me.relex.circleindicator.CircleIndicator3;

public class HomeFragment extends Fragment {
    MaterialButton btnTableOrder, fabCallHotline;
    ImageView imgMap;
    ViewPager2 viewPager2;
    CircleIndicator3 circleIndicator3;
    List<Photo> list;
    Handler mHandler = new Handler();
    Runnable mRunable = new Runnable() {
        @Override
        public void run() {
            if (viewPager2.getCurrentItem() == list.size() - 1)
                viewPager2.setCurrentItem(0);
            else
                viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
        }
    };
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager2 = view.findViewById(R.id.slideViewPager2);
        circleIndicator3 = view.findViewById(R.id.circleIndicator);
        btnTableOrder = view.findViewById(R.id.btnTableOrder);
        fabCallHotline = view.findViewById(R.id.fabCallHotline);
        imgMap = view.findViewById(R.id.imgMap);

        slideShow();
        requestPermission();
        btnTableOrder.setOnClickListener(v -> startActivity(new Intent(getActivity(), OrderActivity.class)));
        fabCallHotline.setOnClickListener(v -> callHotline());
//        btnTableOrder.setOnClickListener(v -> openGGMap());
    }

//    private void openGGMap() {
//        Uri gmmIntentUri = Uri.parse("geo:21.0381328,-105.744593");
//        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
//        mapIntent.setPackage("com.google.android.apps.maps");
//        if (mapIntent.resolveActivity(getActivity().getPackageManager()) != null) {
//            startActivity(mapIntent);
//        }
//    }

    public void requestPermission() {
        if (ContextCompat.checkSelfPermission(getContext(),android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),new String[] {android.Manifest.permission.CALL_PHONE},123);
        }
    }

    private void callHotline() {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "0379103490"));
            startActivity(intent);
    }

    private void slideShow() {
        list = getListPhoto();
        SlideViewPager2Adapter adapter = new SlideViewPager2Adapter(list);
        viewPager2.setAdapter(adapter);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.setClipChildren(false);
        viewPager2.setClipToPadding(false);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer((page, position) -> {
            float r = 1 - Math.abs(position);
            page.setScaleY(0.85f + r * 0.15f);
        });

        viewPager2.setPageTransformer(compositePageTransformer);

//        viewPager2.setPageTransformer(new ZoomOutPageTransformer());
        circleIndicator3.setViewPager(viewPager2);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mHandler.removeCallbacks(mRunable);
                mHandler.postDelayed(mRunable, 3000);
            }
        });
    }

    private List<Photo> getListPhoto() {
        List<Photo> photos = new ArrayList<>();
        photos.add(new Photo(R.drawable.infor_img));
        photos.add(new Photo(R.drawable.infor_img_1));
        photos.add(new Photo(R.drawable.infor_img_2));
        photos.add(new Photo(R.drawable.infor_img));
        photos.add(new Photo(R.drawable.infor_img_1));
        photos.add(new Photo(R.drawable.infor_img_2));
        photos.add(new Photo(R.drawable.infor_img));
        photos.add(new Photo(R.drawable.infor_img_1));
        photos.add(new Photo(R.drawable.infor_img_2));
        return photos;
    }

    @Override
    public void onPause() {
        super.onPause();
        mHandler.removeCallbacks(mRunable);
    }

    @Override
    public void onResume() {
        super.onResume();
        mHandler.postDelayed(mRunable, 3000);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (!(grantResults[0] == PackageManager.PERMISSION_GRANTED) && requestCode == 123) {
            requestPermission();
        }
    }
}