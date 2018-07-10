package com.android.jerry.dotapedia;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class HeroListAdapter extends RecyclerView.Adapter<HeroListAdapter.HeroViewHolder> {

    // ViewHolder implements interface View.OnClickListener.
    // Add itself to the heroView so it can react to onClick event.
    class HeroViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final ImageView heroListImageView;
        private final TextView heroListTextView;

        // get structure of one hero in the recycler list as "heroView"
        private HeroViewHolder(View heroView) {
            super(heroView);
            heroListImageView = heroView.findViewById(R.id.heroListImageView);
            heroListTextView = heroView.findViewById(R.id.heroListTextView);
            // Add itself as listener
            heroView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            // activate item detail activity
            Hero clickedItem = mHeroes.get(getAdapterPosition());
            Intent intent = new Intent(mContext, HeroDetailActivity.class);
            intent.putExtra("hero_to_show", clickedItem);
            mContext.startActivity(intent);
        }
    }

    private final LayoutInflater mInflater;
    private List<Hero> mHeroes;// Cached copy of heroes
    private Context mContext;

    // Constructor
    HeroListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @NonNull
    @Override
    public HeroListAdapter.HeroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflate the whole structure of one item in the recycler list
        final View heroView = mInflater.inflate(R.layout.recyclerview_hero, parent, false);
        return new HeroListAdapter.HeroViewHolder(heroView);
    }

    @Override
    public void onBindViewHolder(@NonNull HeroListAdapter.HeroViewHolder holder, int position) {
        if (mHeroes != null) {
            Hero current = mHeroes.get(position);
            // set icon and item's name to UI
            holder.heroListImageView.setImageResource(mContext.getResources().getIdentifier(current.getIconPath(), "drawable", mContext.getPackageName()));
            holder.heroListTextView.setText(current.getName());
            setFadeAnimation(holder.heroListImageView);
            setFadeAnimation(holder.heroListTextView);
        } else {
            // Covers the case of data not being ready yet.
            holder.heroListTextView.setText("No Data.");
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
    public void onViewDetachedFromWindow(@NonNull HeroViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.heroListImageView.clearAnimation();
        holder.heroListTextView.clearAnimation();
    }

    void setHeroes(List<Hero> heros) {
        mHeroes = heros;
        notifyDataSetChanged();
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        if (mHeroes != null) {
            return mHeroes.size();
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
