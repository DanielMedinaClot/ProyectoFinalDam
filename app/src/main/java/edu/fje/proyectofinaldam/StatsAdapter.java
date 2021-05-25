package edu.fje.proyectofinaldam;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


//Adaptador para generar el RecyclerView de las Stats
public class StatsAdapter extends RecyclerView.Adapter<StatsAdapter.ViewHolder> {
    private List<StatsList> stats;

    private Context context;
    //final DraftAdapter.OnItemClickListener listener;


    public StatsAdapter(List<StatsList> itemList){
        this.stats = itemList;
        //this.listener = listener;

    }
    @Override
    public int getItemCount(){
        return stats.size();
    }



    @Override
    public StatsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stats_list,null,false);
        return new StatsAdapter.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(StatsAdapter.ViewHolder holder, int position){
        holder.bindData(stats.get(position));
    }

    public void setItems(List<StatsList> items){

        stats = items;
    }



    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView seasonYear, gamesPlayed, mpg, ppg, rpg, apg, spg, bpg, topg, fgp, tpp, ftp;

        ViewHolder(View itemView){
            super (itemView);
            seasonYear = itemView.findViewById(R.id.textViewStatsSeasonYear);
            gamesPlayed = itemView.findViewById(R.id.textViewStatsGamesPlayed);
            mpg = itemView.findViewById(R.id.textViewStatsMPG);
            ppg = itemView.findViewById(R.id.textViewStatsPPG);
            rpg = itemView.findViewById(R.id.textViewStatsRPG);
            apg = itemView.findViewById(R.id.textViewStatsAPG);
            spg = itemView.findViewById(R.id.textViewStatsSPG);
            bpg = itemView.findViewById(R.id.textViewStatsBPG);
            topg = itemView.findViewById(R.id.textViewStatsTOPG);
            fgp = itemView.findViewById(R.id.textViewStatsFGP);
            tpp = itemView.findViewById(R.id.textViewStatsTPP);
            ftp = itemView.findViewById(R.id.textViewStatsFTP);



        }
        void bindData (StatsList item){
            seasonYear.setText(item.getSeasonYear());
            gamesPlayed.setText(item.getGamesPlayed());
            mpg.setText(item.getMpg());
            ppg.setText(item.getPpg());
            rpg.setText(item.getRpg());
            apg.setText(item.getApg());
            spg.setText(item.getSpg());
            bpg.setText(item.getBpg());
            topg.setText(item.getTopg());
            fgp.setText(item.getFgp());
            tpp.setText(item.getTpp());
            ftp.setText(item.getFtp());



        }


    }

}

