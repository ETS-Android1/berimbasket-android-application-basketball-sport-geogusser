package ir.berimbasket.app.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import ir.berimbasket.app.R;
import ir.berimbasket.app.bundle.BundleXp;

/**
 * Created by mohammad hosein on 7/22/2017.
 */

public class AdapterXp extends RecyclerView.Adapter<AdapterXp.XpViewHolder> {

    private final Context context;
    private final ArrayList<BundleXp> xpList;
    private Typeface typeface;

    public AdapterXp(ArrayList<BundleXp> xpList, Context context) {
        this.xpList = xpList;
        this.context = context;
        typeface = Typeface.createFromAsset(context.getAssets(), "fonts/yekan.ttf");
    }

    @Override
    public AdapterXp.XpViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_xp, parent, false);
        AdapterXp.XpViewHolder holder = new AdapterXp.XpViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(AdapterXp.XpViewHolder holder, final int position) {
        holder.setData(position);
        holder.txtXpTitle.setTypeface(typeface);
        holder.txtXpBadge.setTypeface(typeface);
        holder.txtXpScore.setTypeface(typeface);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(xpList.get(position).getLink());

                CustomTabsIntent.Builder intentBuilder = new CustomTabsIntent.Builder();
                intentBuilder.setToolbarColor(ContextCompat.getColor(context, R.color.colorPrimary));
                intentBuilder.setSecondaryToolbarColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
                CustomTabsIntent customTabsIntent = intentBuilder.build();
                customTabsIntent.launchUrl(context, uri);
            }
        });
    }


    @Override
    public int getItemCount() {
        return xpList.size();
    }

    class XpViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txtXpTitle, txtXpBadge, txtXpScore;

        XpViewHolder(View itemView) {
            super(itemView);
            this.txtXpTitle = (TextView) itemView.findViewById(R.id.txtXpTitle);
            this.txtXpBadge = (TextView) itemView.findViewById(R.id.txtXpBadge);
            this.txtXpScore = (TextView) itemView.findViewById(R.id.txtXpScore);
        }

        public void setData(int pos) {
            this.txtXpTitle.setText(xpList.get(pos).getTitle());
            this.txtXpBadge.setText(String.valueOf(xpList.get(pos).getLevel()));
            this.txtXpScore.setText(String.valueOf(xpList.get(pos).getScore()) + "  امتیاز ");
        }

        @Override
        public void onClick(View view) {

        }
    }

}
