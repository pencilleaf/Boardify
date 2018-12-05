package com.hi.boardify;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Filter;
import android.widget.Filterable;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class GalleryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    ArrayList<ImageModel> data;
    ArrayList<ImageModel> dataFull;


    public GalleryAdapter(Context context, ArrayList<ImageModel> data) {
        this.context = context;
        this.data = data;
        this.dataFull = new ArrayList<>();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.cardview_item, parent, false);
        viewHolder = new MyItemHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((MyItemHolder) holder).imagetitle.setText(data.get(position).getName());
        Glide.with(context).load(data.get(position).getUrl())
                .transition(withCrossFade())
                .thumbnail(0.5f)
                .apply(new RequestOptions().override(200,200))
                .into(((MyItemHolder) holder).thumbnail);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class MyItemHolder extends RecyclerView.ViewHolder {
        ImageView thumbnail;
        TextView imagetitle;


        public MyItemHolder(View itemView) {
            super(itemView);

            thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
            imagetitle = (TextView) itemView.findViewById(R.id.imagetitle);
        }

    }

//    @Override
//    public Filter getFilter() {
//
//        return dataFilter;
//    }
//    private Filter dataFilter = new Filter(){
//        @Override
//        protected FilterResults performFiltering(CharSequence constraint) {
//            ArrayList<ImageModel> filteredList = new ArrayList<>();
//
//            if (constraint == null || constraint.length()==0){
//                filteredList.addAll(dataFull);
//            }else{
//                String filterPattern  = constraint.toString().toLowerCase().trim();
//
//                for (ImageModel imageModel: dataFull){
//                    if(imageModel.getUrl().toLowerCase().contains(filterPattern)){
//                        filteredList.add(imageModel);
//                    }
//                }
//            }
//            FilterResults results = new FilterResults();
//            results.values = filteredList;
//            return results;
//        }
//
//        @Override
//        protected void publishResults(CharSequence constraint, FilterResults results) {
//            data.clear();
//            data.addAll((ArrayList) results.values);
//            notifyDataSetChanged();
//        }
//    };

}
