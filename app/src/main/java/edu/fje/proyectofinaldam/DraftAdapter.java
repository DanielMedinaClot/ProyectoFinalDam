package edu.fje.proyectofinaldam;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


    public class DraftAdapter extends RecyclerView.Adapter<DraftAdapter.ViewHolder> {
        private List<DraftList> drafts;

        private Context context;
        //final DraftAdapter.OnItemClickListener listener;


        public DraftAdapter(List<DraftList> itemList){
            this.drafts = itemList;
            //this.listener = listener;

        }
        @Override
        public int getItemCount(){
            return drafts.size();
        }



        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.draft_list,null,false);
            return new ViewHolder(view);
        }
        @Override
        public void onBindViewHolder(ViewHolder holder, int position){
            holder.bindData(drafts.get(position));
        }

        public void setItems(List<DraftList> items){

            drafts = items;
        }



        public class ViewHolder extends RecyclerView.ViewHolder{

            ImageView draftHeadshot;
            TextView draftProspect, draftNumber, draftTeam, draftPosition, draftCollege, draftCountry, draftHeight, draftWeight;

            ViewHolder(View itemView){
                super (itemView);
                draftHeadshot = itemView.findViewById(R.id.imageViewDraftHeadshot);
                draftProspect = itemView.findViewById(R.id.textViewDraftProspect);
                draftNumber = itemView.findViewById(R.id.textViewDraftNumber);
                draftTeam = itemView.findViewById(R.id.textViewDraftTeam);
                draftPosition = itemView.findViewById(R.id.textViewDraftPosition);
                draftCollege = itemView.findViewById(R.id.textViewDraftCollege);
                draftCountry = itemView.findViewById(R.id.textViewDraftCountry);
                draftHeight = itemView.findViewById(R.id.textViewDraftHeight);
                draftWeight = itemView.findViewById(R.id.textViewDraftWeight);


            }
            void bindData (DraftList item){

                draftProspect.setText(item.getProspect());
                draftNumber.setText(item.getNumber()+"");
                draftTeam.setText(item.getTeam());
                draftPosition.setText(item.getPosition());
                draftCollege.setText(item.getCollege());
                draftCountry.setText(item.getCountry());
                draftHeight.setText(item.getHeight());
                draftWeight.setText(item.getWeight());

                new DownloadImageTask(draftHeadshot).execute(item.getHeadshot());

            }
        }

}
