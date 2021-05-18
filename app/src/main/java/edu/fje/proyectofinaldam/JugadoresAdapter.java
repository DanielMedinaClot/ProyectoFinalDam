package edu.fje.proyectofinaldam;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class JugadoresAdapter extends RecyclerView.Adapter<JugadoresAdapter.ViewHolder>{
    private List<JugadoresList> players;

    private Context context;
    //final DraftAdapter.OnItemClickListener listener;


    public JugadoresAdapter(List<JugadoresList> itemList){
        this.players = itemList;
        //this.listener = listener;

    }
    @Override
    public int getItemCount(){
        return players.size();
    }



    @Override
    public JugadoresAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.jugadores_list,null,false);
        return new JugadoresAdapter.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(JugadoresAdapter.ViewHolder holder, int position){
        holder.bindData(players.get(position));
    }

    public void setItems(List<JugadoresList> items){

        players = items;
    }



    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView temporaryDisplayName, jersey , pos, nbaDebutYear, country;

        ViewHolder(View itemView){
            super (itemView);
            temporaryDisplayName = itemView.findViewById(R.id.textViewRosterName);
            jersey = itemView.findViewById(R.id.textViewRosterJersey);
            pos = itemView.findViewById(R.id.textViewRosterPos);
            nbaDebutYear = itemView.findViewById(R.id.textViewRosterNbaDebutYear);
            country = itemView.findViewById(R.id.textViewRosterCountry);
        }
        void bindData (JugadoresList item){
            temporaryDisplayName.setText(item.getTemporaryDisplayName());
            jersey.setText(item.getJersey());
            pos.setText(item.getPos());
            nbaDebutYear.setText(item.getNbaDebutYear());
            country.setText(item.getCountry());
        }


    }

}


