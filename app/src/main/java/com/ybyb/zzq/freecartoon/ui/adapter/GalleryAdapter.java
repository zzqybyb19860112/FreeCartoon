package com.ybyb.zzq.freecartoon.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import com.ybyb.zzq.freecartoon.R;
import com.ybyb.zzq.freecartoon.response.detail.DetailCartoon;
import io.realm.RealmList;

/**
 * 作者：周正权 on 2017/6/14
 * 邮箱：zhouzhengquan@shixinyun.com
 */

public class GalleryAdapter extends BaseAdapter {
    private Context mContext;
    private RealmList<DetailCartoon> list;

    public GalleryAdapter(Context context,RealmList<DetailCartoon> list) {
        this.mContext = context;
        this.list=list;
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
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder=null;
        if (convertView==null){
            convertView=View.inflate(mContext, R.layout.item_gallery,null);
            holder=new ViewHolder();
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        holder.itemIv= (ImageView) convertView.findViewById(R.id.item_iv);
        String url=list.get(position).getImage();
        Picasso.with(mContext).load(url).into(holder.itemIv);
        return convertView;
    }
    class ViewHolder{
        ImageView itemIv;
    }
}