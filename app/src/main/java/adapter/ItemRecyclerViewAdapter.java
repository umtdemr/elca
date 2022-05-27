package adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.elca.R;

import java.util.List;

import co.dift.ui.SwipeToAction;
import entities.ItemEntity;

public class ItemRecyclerViewAdapter extends RecyclerView.Adapter<ItemRecyclerViewAdapter.ItemViewHolder> {
    private List<ItemEntity> itemList;

    class ItemViewHolder extends SwipeToAction.ViewHolder<ItemEntity> {
        private TextView lblTitle, lblMaxWatt, lblAverageUsage;

        ItemViewHolder(View view) {
            super(view);
            lblTitle = view.findViewById(R.id.lblTitle);
            lblMaxWatt = view.findViewById(R.id.lblMaxWatt);
            lblAverageUsage = view.findViewById(R.id.lblAverageUsage);
        }
    }

    public ItemRecyclerViewAdapter(List<ItemEntity> itemList) {
        this.itemList = itemList;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.element_item, parent, false);
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.element_item_actions, parent, false);
        return new ItemViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        ItemEntity item = itemList.get(position);
        holder.lblTitle.setText(item.getTitle());
        holder.lblMaxWatt.setText(String.valueOf(item.getMaxWatt()) + " watt");
        holder.lblAverageUsage.setText(String.valueOf(item.getAverageUsage()) + " hours");
        holder.data = item;
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
