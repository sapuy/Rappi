package xyz.leosap.rappiprueba.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import xyz.leosap.rappiprueba.R;
import xyz.leosap.rappiprueba.activities.DetailActivity;
import xyz.leosap.rappiprueba.common.Constants;
import xyz.leosap.rappiprueba.common.Functions;
import xyz.leosap.rappiprueba.models.Tema;


/**
 * Created by LeoSap on 30/11/2016.
 */

public class adapterCardView extends RecyclerView.Adapter<adapterCardView.MyViewHolder> {


    private final Context context;
    private final ArrayList<Tema> temas;
    public adapterCardView(Context context, ArrayList<Tema> temas) {
        this.context = context;
        this.temas = temas;
    }

    @Override
    public adapterCardView.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_card_view, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final adapterCardView.MyViewHolder holder, int position) {
        final Tema tema = temas.get(position);
        holder.tv1.setText(Functions.cleanContent(tema.getTitle()));
        holder.tv1.setSelected(true);
        holder.tv1.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        holder.tv1.setSingleLine(true);
        holder.tv2.setText(Functions.cleanContent(tema.getPublicDescription()));
        holder.tv3.setText(Functions.epochToDate(tema.getCreated()));

        String img = tema.getIconImg();
        if (Constants.debug) Log.d("LS img icon", "--> " + tema.getIconImg());
        if (img.equalsIgnoreCase("")) {
            img = tema.getHeaderImg();
            if (Constants.debug) Log.d("LS img header", "--> " + tema.getHeaderImg());
        }

        final String finalImg = img;
        Picasso.with(context)
                .load(img)
                .networkPolicy(NetworkPolicy.OFFLINE)
                //.config(Bitmap.Config.RGB_565)
                .fit()
                .placeholder(R.drawable.ic_icon_ph)
                .centerInside()
                .into(holder.iv1, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        Picasso.with(context)
                                .load(finalImg)
                                // .networkPolicy(NetworkPolicy.OFFLINE)
                                //.config(Bitmap.Config.RGB_565)
                                .fit()
                                .placeholder(R.drawable.ic_icon_ph)
                                .centerInside()
                                .into(holder.iv1);
                    }
                });

        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("id", tema.getId());
                String transitionName = context.getString(R.string.transition_image);
                ActivityOptionsCompat options =
                        ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context,
                                holder.iv1,   // The view which starts the transition
                                transitionName    // The transitionName of the view we’re transitioning to
                        );
                ActivityCompat.startActivity(context, intent, options.toBundle());

                //context.startActivity(intent);

                if (Constants.debug)
                    Log.d("LS click", tema.getTitle());

            }
        });

    }

    @Override
    public int getItemCount() {
        return temas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public final TextView tv1;
        public final TextView tv2;
        public final TextView tv3;
        public final ImageView iv1;
        public final CardView cv;

        public MyViewHolder(View view) {
            super(view);

            tv1 = (TextView) view.findViewById(R.id.tv_title);
            tv2 = (TextView) view.findViewById(R.id.tv_desc);
            tv3 = (TextView) view.findViewById(R.id.tv_suscriptors);
            iv1 = (ImageView) view.findViewById(R.id.iv_icon);
            cv = (CardView) view.findViewById(R.id.card_view);
        }
    }
}
