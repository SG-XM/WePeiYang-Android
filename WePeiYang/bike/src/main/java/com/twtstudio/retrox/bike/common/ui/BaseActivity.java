package com.twtstudio.retrox.bike.common.ui;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import com.twtstudio.retrox.bike.common.IViewController;
import com.twtstudio.retrox.bike.common.ui.views.LoadingDialog;
import com.twtstudio.retrox.bike.utils.ToastUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by huangyong on 16/5/18.
 */
public abstract class BaseActivity extends AppCompatActivity implements IViewController {

    protected LoadingDialog mLoadingDialog;

    protected Unbinder mUnbinder;

    protected abstract int getLayout();

    protected abstract void actionStart(Context context);

    protected abstract int getStatusbarColor();

    protected abstract void initView();

    protected abstract Toolbar getToolbar();

    protected void preInitView() {

    }

    protected void afterInitView() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        mUnbinder = ButterKnife.bind(this);

        actionStart(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(getStatusbarColor()));
        }
        Toolbar toolbar = getToolbar();
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        preInitView();
        initView();
        afterInitView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showLoadingDialog() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(this);
        }
        mLoadingDialog.show();
    }

    @Override
    public void showLoadingDialog(String message) {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(this);
        }
        mLoadingDialog.setMessage(message);
        mLoadingDialog.show();
    }

    @Override
    public void dismissLoadingDialog() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }

    @Override
    public void toastMessage(String message) {
        ToastUtils.showMessage(this,message);
    }
}
