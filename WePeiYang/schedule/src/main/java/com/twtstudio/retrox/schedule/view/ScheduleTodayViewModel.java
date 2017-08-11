package com.twtstudio.retrox.schedule.view;

import android.databinding.ObservableArrayList;

import com.kelin.mvvmlight.base.ViewModel;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.twtstudio.retrox.schedule.BR;
import com.twtstudio.retrox.schedule.R;
import com.twtstudio.retrox.schedule.model.ClassTable;
import com.twtstudio.retrox.schedule.model.ClassTableProvider;
import com.twtstudio.retrox.schedule.model.CourseHelper;

import java.util.List;

import me.tatarka.bindingcollectionadapter.ItemViewSelector;
import me.tatarka.bindingcollectionadapter.itemviews.ItemViewClassSelector;
import me.tatarka.bindingcollectionadapter2.ItemBinding;
import me.tatarka.bindingcollectionadapter2.OnItemBind;

/**
 * Created by retrox on 2017/2/8.
 */

public class ScheduleTodayViewModel {

    private RxAppCompatActivity rxActivity;

    public final ObservableArrayList<ViewModel> items = new ObservableArrayList<>();

    public final OnItemBind<ViewModel> onItemBind = new OnItemBind<ViewModel>() {
        @Override
        public void onItemBind(ItemBinding itemBinding, int position, ViewModel item) {
            if (item instanceof TodayInfoViewModel){
                itemBinding.set(BR.viewModel, R.layout.item_schedule_course_today_info);
            }else if (item instanceof CourseDetailViewModel){
                itemBinding.set(BR.viewModel, R.layout.item_schedule_course_detail);
            }else if (item instanceof ToScheduleActViewModel){
                itemBinding.set(BR.viewModel, R.layout.item_schedule_course_jump);
            }else if (item instanceof CourseEmptyViewModel){
                itemBinding.set(BR.viewModel,R.layout.item_schedule_course_empty);
            }
        }
    };

//    public final ItemViewSelector itemView = ItemViewClassSelector.builder()
//            .put(TodayInfoViewModel.class, BR.viewModel, R.layout.item_schedule_course_today_info)
//            .put(CourseDetailViewModel.class,BR.viewModel,R.layout.item_schedule_course_detail)
//            .put(ToScheduleActViewModel.class,BR.viewModel,R.layout.item_schedule_course_jump)
//            .put(CourseEmptyViewModel.class,BR.viewModel,R.layout.item_schedule_course_empty)
//            .build();

    public final CourseHelper courseHelper = new CourseHelper();

    public ScheduleTodayViewModel(RxAppCompatActivity rxActivity) {
        this.rxActivity = rxActivity;
        initData();
    }

    private void initData(){
        ClassTableProvider.init(rxActivity)
                .registerAction(this::processData)
                .getData();
    }

    private void processData(ClassTable classTable){
        items.add(new TodayInfoViewModel(classTable));
        List<ClassTable.Data.Course> courseList = courseHelper.getTodayCourses(classTable,true);
        for (int i = courseList.size() - 1; i >= 0; i--) {
            //去除后面结尾的 "无" 空课程
            if (courseList.get(i).coursename.equals("无")){
                courseList.remove(i);
            }else {
                break;
            }
        }
        for (ClassTable.Data.Course course : courseList) {
            if (course.coursename.equals("无")){
                items.add(new CourseEmptyViewModel(course));
            }else {
                items.add(new CourseDetailViewModel(course));
            }
        }
        items.add(new ToScheduleActViewModel(rxActivity));
    }

}
