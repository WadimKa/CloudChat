package com.wadimkazak.cloudchat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

/**
 * Created by Wadim on 27.05.2018.
 */

public class MessagesListFragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList<Message> messages = new ArrayList<>();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("message");
    FirebaseAuth firebaseAuth;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_with_messages, container, false);
        recyclerView = view.findViewById(R.id.rv_list_of_messages);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        final MessagesAdapter messagesAdapter = new MessagesAdapter(messages);
        messagesAdapter.notifyDataSetChanged();
        Query query = myRef;
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                messages.add(dataSnapshot.getValue(Message.class));
                recyclerView.smoothScrollToPosition(messages.size());
                messagesAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        recyclerView.smoothScrollToPosition(messages.size());
        recyclerView.setAdapter(messagesAdapter);

        Toast.makeText(getActivity(), firebaseUser.getDisplayName(), Toast.LENGTH_SHORT).show();

        return view;
    }

    private class MessagesViewHolder extends RecyclerView.ViewHolder {
        TextView tvUserName, tvMessage;

        public MessagesViewHolder(LayoutInflater layoutInflater, ViewGroup viewGroup) {
            super(layoutInflater.inflate(R.layout.item_of_list_messages, viewGroup, false));

            tvMessage = itemView.findViewById(R.id.tv_message);
            tvUserName = itemView.findViewById(R.id.tv_user_name);
        }

        public void bind(Message message) {
            tvUserName.setText(message.getName());
            tvMessage.setText(message.getMessage());
        }
    }

    private class MessagesAdapter extends RecyclerView.Adapter<MessagesViewHolder> {
        ArrayList<Message> listOfMessages;

        public MessagesAdapter(ArrayList<Message> listOfMessages) {
            this.listOfMessages = listOfMessages;
        }

        @Override
        public MessagesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new MessagesViewHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(MessagesViewHolder holder, int position) {
            holder.bind(messages.get(position));

        }

        @Override
        public int getItemCount() {
            return listOfMessages.size();
        }
    }
}
