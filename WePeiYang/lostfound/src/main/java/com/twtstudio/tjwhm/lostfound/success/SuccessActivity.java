package com.twtstudio.tjwhm.lostfound.success;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.twtstudio.tjwhm.lostfound.R;
import com.twtstudio.tjwhm.lostfound.base.BaseActivity;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import java.util.Objects;

import butterknife.BindView;

/**
 * Created by tjwhm on 2017/7/2.
 **/

public class SuccessActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.success_cardView)
    CardView success_cardView;
    @BindView(R.id.share_wechatfriends)
    ImageView share_wechatfriends;
    @BindView(R.id.share_wechatzone)
    ImageView share_wechatzone;
    @BindView(R.id.share_qqfriends)
    ImageView share_qqfriends;
    @BindView(R.id.share_qzone)
    ImageView share_qzone;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_success;
    }

    @Override
    protected Toolbar getToolbarView() {
        toolbar.setTitle("分享");
        return toolbar;
    }

    @Override
    protected boolean isShowBackArrow() {
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setOnClickListenerForViews();
        Bundle bundle = getIntent().getExtras();
        String shareOrSuccess = bundle.getString("index");
        if (Objects.equals(shareOrSuccess, "share")) {
            success_cardView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {

        if (view == share_wechatfriends) {
            new ShareAction(SuccessActivity.this).setPlatform(SHARE_MEDIA.WEIXIN)
                    .withText("hello")
                    .setCallback(umShareListener)
                    .share();

        } else if (view == share_wechatzone) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_WIFI_STATE},
                    1);

        } else if (view == share_qqfriends) {

            UMImage image = new UMImage(SuccessActivity.this, R.drawable.lost_search);
            new ShareAction(SuccessActivity.this).setPlatform(SHARE_MEDIA.QQ)
                    .withText("hello")
                    .withMedia(image)
                    .setCallback(umShareListener)
                    .share();
        } else if (view == share_qzone) {
            new ShareAction(SuccessActivity.this).setPlatform(SHARE_MEDIA.QZONE)
                    .withText("hello")
                    .setCallback(umShareListener)
                    .share();
        }
    }

    private void setOnClickListenerForViews() {
        share_wechatfriends.setOnClickListener(this);
        share_wechatzone.setOnClickListener(this);
        share_qqfriends.setOnClickListener(this);
        share_qzone.setOnClickListener(this);
    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //分享开始的回调
        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            //    Log.d("plat","platform"+platform);

            Toast.makeText(SuccessActivity.this, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(SuccessActivity.this, platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
            if (t != null) {
                //     Log.d("throw","throw:"+t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(SuccessActivity.this, platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //授权成功
            Toast.makeText(this, "aa", Toast.LENGTH_SHORT).show();

            new ShareAction(SuccessActivity.this).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                    .withText("hello")
                    .setCallback(umShareListener)
                    .share();
        } else {
            Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show();
            //授权失败
        }
    }
}
