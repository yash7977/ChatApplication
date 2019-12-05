package com.example.chatapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.ViewHolder> {


    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference documentReference;

    ArrayList<MessagesDomain> messagesDomainList;
    Context context;


    DocumentSnapshot document;

    SharedPreferences sharedPreferences;
    String CurrentUser;
    String tripId;

    public MessagesAdapter(ArrayList<MessagesDomain> messagesDomains, Context context, String tripId) {
        this.messagesDomainList = messagesDomains;
        this.context = context;
        this.tripId = tripId;
        this.sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context.getApplicationContext());
        this.CurrentUser = sharedPreferences.getString("CurrentUser",null);
    }

    @NonNull
    @Override
    public MessagesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.messagecardview, parent, false);
        MessagesAdapter.ViewHolder viewHolder = new MessagesAdapter.ViewHolder(view);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.MessageText.setText(messagesDomainList.get(position).getMessage());
        System.out.println("MEssageDomainListSize"+messagesDomainList.size());
        //holder.UserName.setText(messagesDomainList.get(position).getSentBy());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(messagesDomainList.get(position).getSentBy().equals(CurrentUser)){
                    messagesDomainList.remove(position);
                    notifyDataSetChanged();
                    documentReference = db.collection("Trips").document(tripId);
                    documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                document = task.getResult();
                                if (document != null && document.exists()) {
                                    document.getData().get("messagesDomains");



                                }

                            }
                        }
                    });
                }
            }
        });



    }

    @Override
    public int getItemCount() {
        return messagesDomainList.size();
    }


    public  class ViewHolder extends RecyclerView.ViewHolder {


        TextView MessageText ;
        TextView UserName ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            MessageText =itemView.findViewById(R.id.MessageText);
            UserName = itemView.findViewById(R.id.UserName);


        }




    }
}
