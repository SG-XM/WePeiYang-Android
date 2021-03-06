package com.twt.service.ui.lostfound.lost;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.twt.service.R;
import com.twt.service.bean.Lost;
import com.twt.service.bean.LostItem;
import com.twt.service.ui.common.LostType;
import com.twt.service.ui.lostfound.lost.details.LostDetailsActivity;
import com.twt.service.ui.lostfound.post.ObjectType;
import com.twt.service.ui.lostfound.post.PostLostFoundActivity;
import com.twt.service.ui.main.adapter.MainLostAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by RexSun on 15/8/18.
 */
public class LostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<LostItem> dataSet = new ArrayList<>();

    public static final int TYPE_ALL_LOST = 1;
    public static final int TYPE_MY_LOST = 2;
    private int adapterType;

    public LostAdapter(Context context, int adapterType) {
        this.context = context;
        this.adapterType = adapterType;
    }


    static class ItemHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.iv_lost_item_category)
        ImageView ivCategory;
        @InjectView(R.id.tv_lost_item_date)
        TextView tvDate;
        @InjectView(R.id.tv_lost_item_position)
        TextView tvPosition;
        @InjectView(R.id.tv_lost_item_name)
        TextView tvName;
        @InjectView(R.id.tv_lost_item_number)
        TextView tvNumber;
        @InjectView(R.id.tv_lost_item_title)
        TextView tvTitle;

        public ItemHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return new ItemHolder(inflater.inflate(R.layout.item_lost_rv, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        int type = getItemViewType(position);
        ItemHolder itemHolder = (ItemHolder) holder;
        LostItem item = dataSet.get(position);
        switch (item.lost_type) {
            case LostType.OTHERS:
                itemHolder.ivCategory.setImageResource(R.mipmap.icon_others);
                break;
            case LostType.BANK_CARD:
                itemHolder.ivCategory.setImageResource(R.mipmap.icon_bank_card);
                break;
            case LostType.ID_CARD:
                itemHolder.ivCategory.setImageResource(R.mipmap.icon_id_card);
                break;
            case LostType.KEY:
                itemHolder.ivCategory.setImageResource(R.mipmap.icon_key);
                break;
            case LostType.BACKPACK:
                itemHolder.ivCategory.setImageResource(R.mipmap.icon_backpack);
                break;
            case LostType.COMPUTER_PAG:
                itemHolder.ivCategory.setImageResource(R.mipmap.icon_computer_pag);
                break;
            case LostType.WATCH:
                itemHolder.ivCategory.setImageResource(R.mipmap.icon_watch);
                break;
            case LostType.UDISK:
                itemHolder.ivCategory.setImageResource(R.mipmap.icon_udisk);
                break;
            case LostType.CUP:
                itemHolder.ivCategory.setImageResource(R.mipmap.icon_cup);
                break;
            case LostType.BOOK:
                itemHolder.ivCategory.setImageResource(R.mipmap.icon_books);
                break;
            case LostType.MOBILE_PHONE:
                itemHolder.ivCategory.setImageResource(R.mipmap.icon_mobile_phone);
                break;
            case LostType.WALLET:
                itemHolder.ivCategory.setImageResource(R.mipmap.icon_wallet);
                break;
        }
        itemHolder.tvDate.setText(item.time);
        itemHolder.tvPosition.setText(item.place);
        itemHolder.tvName.setText(item.name);
        itemHolder.tvNumber.setText(item.phone);
        itemHolder.tvTitle.setText(item.title);
        itemHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (adapterType) {
                    case TYPE_ALL_LOST:
                        LostDetailsActivity.actionStart(context, dataSet.get(position).id);
                        break;
                    case TYPE_MY_LOST:
                        PostLostFoundActivity.actionStart(context, dataSet.get(position).id, ObjectType.LOST);
                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public void refreshItems(List<LostItem> items) {
        dataSet.clear();
        for (LostItem item : items) {
            dataSet.add(item);
        }
        notifyDataSetChanged();
    }

    public void loadMoretems(List<LostItem> items) {
        for (LostItem item : items) {
            dataSet.add(item);
        }
        notifyDataSetChanged();
    }
}
