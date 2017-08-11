package com.twt.service.home.user;

import android.databinding.ObservableArrayList;

import com.kelin.mvvmlight.base.ViewModel;
import com.twt.service.BR;
import com.twt.service.R;
import com.twt.service.base.BaseActivity;

import me.tatarka.bindingcollectionadapter.BaseItemViewSelector;
import me.tatarka.bindingcollectionadapter.ItemView;
import me.tatarka.bindingcollectionadapter.ItemViewSelector;
import me.tatarka.bindingcollectionadapter2.ItemBinding;
import me.tatarka.bindingcollectionadapter2.OnItemBind;

/**
 * Created by retrox on 2017/1/14.
 */

public class UserFragViewModel implements ViewModel {

    private BaseActivity mActivity;

    public ObservableArrayList list = new ObservableArrayList();

    public final OnItemBind<ViewModel> onItemBind = new OnItemBind<ViewModel>() {
        @Override
        public void onItemBind(ItemBinding itemBinding, int position, ViewModel item) {
            if (position == 0) {
                itemBinding.set(BR.viewModel, R.layout.item_user_avatar);
            } else if (1 <= position && position <= 3) {
                itemBinding.set(BR.viewModel, R.layout.item_user_commons);
            } else {
                itemBinding.set(BR.viewModel, R.layout.item_user_settings);
            }
        }

    };

    public UserFragViewModel(BaseActivity activity) {
        mActivity = activity;
        init();
    }

    private void init() {
        list.add(new AvatarItemViewModel());
        list.add(new CommonItemViewModel(mActivity, CommonItemViewModel.MESSAGE));
        list.add(new CommonItemViewModel(mActivity, CommonItemViewModel.COLLECTION));
        list.add(new CommonItemViewModel(mActivity, CommonItemViewModel.RECORD));
//        list.add(new PrefItemViewModel(mActivity,PrefItemViewModel.NIGHTMODE));
        list.add(new PrefItemViewModel(mActivity, PrefItemViewModel.SETTINGS));
        list.add(new PrefItemViewModel(mActivity, PrefItemViewModel.EXIT));
    }
}
