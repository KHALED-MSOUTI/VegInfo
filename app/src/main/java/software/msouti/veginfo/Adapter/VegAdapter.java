package software.msouti.veginfo.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import software.msouti.veginfo.R;
import software.msouti.veginfo.Utils.Tools;
import software.msouti.veginfo.Utils.VegListType;

public class VegAdapter extends RecyclerView.Adapter<VegAdapter.VegViewHolder> {

    final private ListItemClickListener mOnClickListener;
    ArrayList<VegListType> list;
    Context context;
    public VegAdapter(ListItemClickListener mOnClickListener, ArrayList<VegListType> list, Context context) {
        this.mOnClickListener = mOnClickListener;
        this.list=list;
        this.context=context;
    }

    @NonNull
    @Override
    public VegAdapter.VegViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new VegViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VegViewHolder holder, int position) {
        if (list!= null){
            Tools.loadPosterImage( list.get(position).getImagePath(), holder.imageView);
            holder.titleTextView.setText(list.get(position).getTitle());
            ViewCompat.setTransitionName(holder.imageView, context.getString(R.string.transition_name));
        }else{
            Tools.loadDefaultImage(holder.imageView);
            Tools.setBlankTitle(holder.titleTextView);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    public class VegViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView titleTextView;
        VegViewHolder(View v) {
            super(v);
            imageView = v.findViewById(R.id.item_image);
            titleTextView = v.findViewById(R.id.item_title);
            v.setOnClickListener(this);

            }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
            }
        }
}

