package com.android.jerry.dotapedia;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ItemViewHolder> {

    // ViewHolder implements interface View.OnClickListener.
    // Add itself to the itemView so it can react to onClick event.
    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ImageView listImageView;
        private final TextView listTextView;

        // get structure of one item in the recycler list as "itemView"
        private ItemViewHolder(View itemView) {
            super(itemView);
            listImageView = itemView.findViewById(R.id.listImageView);
            listTextView = itemView.findViewById(R.id.listTextView);
            // Add itself as listener
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Log.i("listener", "----Item View Holder Clicked.----" + getAdapterPosition());

            // activate item detail activity
            Item clickedItem = mItems.get(getAdapterPosition());
            Intent intent = new Intent(mContext, ItemDetailActivity.class);
            intent.putExtra("item_to_show", clickedItem);
            mContext.startActivity(intent);
        }

    }

    private final LayoutInflater mInflater;
    private List<Item> mItems;// Cached copy of items
    private Context mContext;

    // Constructor
    ItemListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    /**
     * Called when RecyclerView needs a new {@link ItemViewHolder} of the given type to represent
     * an item.
     * <p>
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     * <p>
     * The new ViewHolder will be used to display items of the adapter using
     * {@link #onBindViewHolder(ItemViewHolder, int, List)}. Since it will be re-used to display
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary {@link View#findViewById(int)} calls.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see #getItemViewType(int)
     * @see #onBindViewHolder(ItemViewHolder, int)
     */
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflate the whole structure of one item in the recycler list
        final View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new ItemViewHolder(itemView);
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
     * position.
     * <p>
     * Note that unlike {@link ListView}, RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the <code>position</code> parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use {@link ViewHolder#getAdapterPosition()} which will
     * have the updated adapter position.
     * <p>
     * Override {@link #onBindViewHolder(ViewHolder, int, List)} instead if Adapter can
     * handle efficient partial bind.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        if (mItems != null) {
            Item current = mItems.get(position);
            // set icon and item's name to UI
            holder.listImageView.setImageResource(mContext.getResources().getIdentifier(current.getIconPath(), "drawable", mContext.getPackageName()));
            holder.listTextView.setText(current.getName());
            setFadeAnimation(holder.listImageView);
            setFadeAnimation(holder.listTextView);
        } else {
            // Covers the case of data not being ready yet.
            holder.listTextView.setText("No Data.");
        }
    }

    /**
     * Called when a view created by this adapter has been detached from its window.
     * <p>
     * <p>Becoming detached from the window is not necessarily a permanent condition;
     * the consumer of an Adapter's views may choose to cache views offscreen while they
     * are not visible, attaching and detaching them as appropriate.</p>
     *
     * @param holder Holder of the view being detached
     */
    @Override
    public void onViewDetachedFromWindow(@NonNull ItemViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.listImageView.clearAnimation();
        holder.listTextView.clearAnimation();
    }

    void setItems(List<Item> items) {
        mItems = items;
        notifyDataSetChanged();
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        if (mItems != null) {
            return mItems.size();
        } else {
            return 0;
        }
    }

    private void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(400);
        view.startAnimation(anim);
    }
}
