package com.ybyb.zzq.freecartoon.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import com.ybyb.zzq.freecartoon.R;
import com.ybyb.zzq.freecartoon.response.survey.CartoonOverView;
import com.ybyb.zzq.freecartoon.ui.look.ChapterActivity;
import java.util.List;

/**
 * 作者：周正权 on 2017/6/9
 * 邮箱：zhouzhengquan@shixinyun.com
 */

public class ListRvAdapter extends RecyclerView.Adapter {
    private List<CartoonOverView> list;
    private Context context;

    public ListRvAdapter(Context context, List<CartoonOverView> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.item_cartoon_rv, parent, false);//item显示不正常的错误原因
        return new RvViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        RvViewHolder holder= (RvViewHolder) viewHolder;
        final CartoonOverView data=list.get(position);
        holder.nameTv.setText(data.getName());
        holder.areaTv.setText(data.getArea());
        String lastUpdateTime=data.getLastUpdate()+"";
        String[] time={lastUpdateTime.substring(0,4),lastUpdateTime.substring(4,6),lastUpdateTime.substring(6)};
        holder.lastUpdateTv.setText("最后更新："+time[0]+"-"+time[1]+"-"+time[2]);
        Picasso.with(context).load(data.getCoverImg()).into(holder.coverImgIv);
        holder.desTv.setText(data.getDes());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChapterActivity.start(context, data.getName());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    private class RvViewHolder extends RecyclerView.ViewHolder{
        TextView nameTv;
        TextView areaTv;
        TextView lastUpdateTv;
        ImageView coverImgIv;
        TextView desTv;

        public RvViewHolder(View itemView) {
            super(itemView);
            nameTv= (TextView) itemView.findViewById(R.id.name_tv);
            areaTv= (TextView) itemView.findViewById(R.id.area_tv);
            lastUpdateTv= (TextView) itemView.findViewById(R.id.last_update_tv);
            coverImgIv= (ImageView) itemView.findViewById(R.id.cover_img_iv);
            desTv= (TextView) itemView.findViewById(R.id.des_tv);
        }
    }
}
