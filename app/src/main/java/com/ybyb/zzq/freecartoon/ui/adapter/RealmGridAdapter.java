package com.ybyb.zzq.freecartoon.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.ybyb.zzq.freecartoon.R;
import com.ybyb.zzq.freecartoon.response.survey.Chapter;
import com.ybyb.zzq.freecartoon.ui.detail.CartoonActivity;
import io.realm.RealmList;

/**
 * 作者：周正权 on 2017/6/13
 * 邮箱：zhouzhengquan@shixinyun.com
 */

public class RealmGridAdapter extends BaseAdapter {
    private Context context;
    private RealmList<Chapter> list;

    public RealmGridAdapter(Context context, RealmList<Chapter> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if (convertView==null){
            convertView=View.inflate(context, R.layout.item_chapter_gv,null);
            holder=new ViewHolder();
            holder.chapterTv= (TextView) convertView.findViewById(R.id.chapter_tv);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        final Chapter chapter=list.get(position);
        holder.chapterTv.setText(chapter.getName());
        return convertView;
    }
    class ViewHolder{
        TextView chapterTv;
    }
}
