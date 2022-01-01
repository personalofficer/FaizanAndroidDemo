package com.example.faizanandroiddemo.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.faizanandroiddemo.R;
import com.example.faizanandroiddemo.activity.MainActivity;
import com.example.faizanandroiddemo.adapter.UserListAdapter;
import com.example.faizanandroiddemo.model.User;
import com.example.faizanandroiddemo.model.UserDataBase;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class fragmentHome extends Fragment implements View.OnClickListener{

    // ui elements
    private RecyclerView recyclerUserList;
    private ImageView img_add_item;

    // List
    List<User> userList = new ArrayList<>();

    // Adapter
   private UserListAdapter userListAdapter;

    // room database
    private UserDataBase userDataBase;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        userDataBase = Room.databaseBuilder(getActivity(),
                UserDataBase.class, "UserTable").allowMainThreadQueries().build();
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                addRoomData();
            }
        });
        init(view);
        return view;
    }

    private void addRoomData() {

        if (userDataBase.taskDao().getAll().size() > 0) {
            userDataBase.taskDao().deleteAll();
        }
        if (userList.size() > 0) {
            userList.clear();
        }

        User userModel = new User();
        for (int i = 0; i < 10; i++) {
            userModel.setUid(i + 1);
            userModel.setFirstName("Alex Former");
            userModel.setDescription("Employment agency in Sacramento, .........");
            userModel.setCelcius("45F");
            userDataBase.taskDao().insert(userModel);
        }

        userList.addAll(userDataBase.taskDao().getAll());

        Log.i("MainActivity", "addRoomDataSIZE: " + userDataBase.taskDao().getAll().size());

    }


    private void init(View view) {
        recyclerUserList = view.findViewById(R.id.recyclerUserList);
        img_add_item = view.findViewById(R.id.img_add_item);
        seupAdapter();

        img_add_item.setOnClickListener(this);
//        getBundle();
    }

//    private void getBundle() {
//        Bundle bundle = getArguments();
//        if (null != bundle) {
//            if (bundle.containsKey("USERLIST")) {
//                Log.i("HomeFragment", "getBundleSIZE_BEFORE: " + userList.size());
//
//                userList.addAll((Collection<? extends User>) bundle.getSerializable("USERLIST"));
//                Log.i("HomeFragment", "getBundleSIZE: " + userList.size());
//                userListAdapter.notifyDataSetChanged();
//            }
//        }
//
//        ((MainActivity) getActivity()).setFragmentRefreshListener
//                (new MainActivity.FragmentRefreshListener() {
//                    @Override
//                    public void onRefresh(List<User> userList_data) {
//                        Log.i("CHECK", "onRefreshLIST: " + userList.size());
//                        if (userList.size() > 0) {
//                            userList.clear();
//                            userListAdapter.notifyDataSetChanged();
//                        }
//                        userList.addAll(userList_data);
//                        Log.i("HomeFragment", "getBundleSIZE: " + userList.size());
//                        userListAdapter.notifyDataSetChanged();
//                        // Refresh Your Fragment
//                    }
//                });
//
//    }

    private void seupAdapter() {
        recyclerUserList.setLayoutManager(new LinearLayoutManager(getActivity()));

        userListAdapter = new UserListAdapter(getActivity(), userList);
        recyclerUserList.setAdapter(userListAdapter);

        userListAdapter.notifyDataSetChanged();

    }

    private void openBottomSheet() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity());
        bottomSheetDialog.setContentView(R.layout.cust_bottom_sheet_dialog);

        EditText edt_name, edt_celcius, edt_address;
        LinearLayout ll_add;
        edt_name = bottomSheetDialog.findViewById(R.id.edt_name);
        edt_celcius = bottomSheetDialog.findViewById(R.id.edt_celcius);
        edt_address = bottomSheetDialog.findViewById(R.id.edt_address);
        ll_add = bottomSheetDialog.findViewById(R.id.ll_add);

        ll_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (edt_name.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Please Enter UserName", Toast.LENGTH_SHORT).show();
                } else if (edt_celcius.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Please Enter Celsius", Toast.LENGTH_SHORT).show();
                } else if (edt_address.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Please Enter Address", Toast.LENGTH_SHORT).show();
                } else {
                    User userModel = new User();
                    userModel.setUid(userList.size() + 1);
                    userModel.setFirstName(edt_name.getText().toString().trim());
                    userModel.setDescription(edt_address.getText().toString().trim());
                    userModel.setCelcius(edt_celcius.getText().toString().trim());
                    userDataBase.taskDao().insert(userModel);

                    // sent data to fragment
                    if (userList.size() > 0) {
                        userList.clear();
                    }
                    userList.addAll(userDataBase.taskDao().getAll());
                    userListAdapter.notifyDataSetChanged();

                    Log.i("MainActivity", "LIST_SIZE: " + userList.size());
                    bottomSheetDialog.dismiss();
                    Toast.makeText(getActivity(), "Added", Toast.LENGTH_SHORT).show();
                }

            }
        });


        bottomSheetDialog.show();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_add_item:
                openBottomSheet();
        }
    }
}