package adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.elca.R;

import java.util.List;

import model.Item;

public class ItemRecyclerViewAdapter extends RecyclerView.Adapter<ItemRecyclerViewAdapter.ItemViewHolder> {
    private List<Item> itemList;

    class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView lblTitle, lblMaxWatt, lblAverageUsage;

        ItemViewHolder(View view) {
            super(view);
            lblTitle = view.findViewById(R.id.lblTitle);
            lblMaxWatt = view.findViewById(R.id.lblMaxWatt);
            lblAverageUsage = view.findViewById(R.id.lblAverageUsage);
        }
    }

    public ItemRecyclerViewAdapter(List<Item> itemList) {
        this.itemList = itemList;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.element_item, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        Item item = itemList.get(position);
        holder.lblTitle.setText(item.getTitle());
        holder.lblMaxWatt.setText(String.valueOf(item.getMaxWatt()) + " watt");
        holder.lblAverageUsage.setText(String.valueOf(item.getAverageUsage()) + " saat");
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
