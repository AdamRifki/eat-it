package com.example.eatit.ui.Menu;

import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eatit.Interface.ItemClickListener;
import com.example.eatit.Model.Category;
import com.example.eatit.R;
import com.example.eatit.ViewHolder.MenuViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class MenuFragment extends Fragment {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference category;

    RecyclerView recycler_menu;
    RecyclerView.LayoutManager layoutManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_menu, container, false);
        final TextView textView = root.findViewById(R.id.text_home);

        //Init Firebase
        firebaseDatabase = FirebaseDatabase.getInstance();
        category = firebaseDatabase.getReference("Category");

        //Load menu
        RecyclerView recycler_menu = (RecyclerView) root.findViewById(R.id.recycler_menu);
        recycler_menu.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recycler_menu.setLayoutManager(layoutManager);

        loadMenu();

        return root;
    }
        private void loadMenu () {
            FirebaseRecyclerAdapter<Category, MenuViewHolder> adapter = new FirebaseRecyclerAdapter<Category, MenuViewHolder>(Category.class, R.layout.menu_item, MenuViewHolder.class, category) {

                @Override
                protected void populateViewHolder(MenuViewHolder menuViewHolder, Category model, int position) {
                    menuViewHolder.txtMenuName.setText(model.getName());
                    Picasso.with(getContext()).load(model.getImage())
                            .into(menuViewHolder.imageView);
                    final Category clickItem = model;
                    menuViewHolder.setItemClickListener(new ItemClickListener() {
                        @Override
                        public void onClick(View view, int position, boolean isLongClick) {
                            Toast.makeText(getActivity(), "" + clickItem.getName(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            };
            recycler_menu.setAdapter(adapter);
        }
    }
