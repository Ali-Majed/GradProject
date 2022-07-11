package com.example.gradproject.fragment;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gradproject.R;
import com.example.gradproject.adapter.recycler.RecyclerOrderAdapter;
import com.example.gradproject.databinding.FragmentOrdersBinding;
import com.example.gradproject.interfaces.callbacks.ListCallback;
import com.example.gradproject.modle.FirestoreController;
import com.example.gradproject.modle.Orders;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class OrdersFragment extends Fragment {

    private FragmentOrdersBinding binding;
    private ArrayList<Orders> ordersArrayList=new ArrayList<>();
    private RecyclerOrderAdapter recyclerOrderAdapter;
    public static final String CHANNEL_ID="channel_id";

    private FirestoreController firestoreController;
    Orders order =new Orders();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentOrdersBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        firestoreController=new FirestoreController();
        Log.d("nameproduct", "onViewCreated: "+order.getNameProduct());
        recyclerOrderAdapter=new RecyclerOrderAdapter(requireContext(),ordersArrayList);
        binding.recyclerOrder.setAdapter(recyclerOrderAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        readOrders();
    }

    private void readOrders(){
        firestoreController.readOrder(requireContext(), new ListCallback<Orders>() {
            @Override
            public void onFinished(List<Orders> list, boolean success) {
                ordersArrayList.clear();
                ordersArrayList.addAll(list);
//                sendNotification(order.getNamePos(),order.getNameProduct());

                recyclerOrderAdapter.notifyDataSetChanged();
                if (ordersArrayList.size()==0) {
                    binding.textView2.setVisibility(View.VISIBLE);
                }else {
                    binding.textView2.setVisibility(View.GONE);

                }
            }
        });
    }

//    private void sendNotification(String name,String product) {
//        Intent intent = new Intent(requireContext(), OrdersFragment.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        PendingIntent pendingIntent = PendingIntent.getActivity(requireContext(), 0 /* Request code */, intent,
//                PendingIntent.FLAG_ONE_SHOT);
//
//
//        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        NotificationCompat.Builder notificationBuilder =
//                new NotificationCompat.Builder(requireContext() , CHANNEL_ID)
//                        .setSmallIcon(R.drawable.ic_baseline_notifications_24)
//                        .setContentTitle(name)
//                        .setContentText(product)
//                        .setAutoCancel(true)
//                        .setSound(defaultSoundUri)
//                        .setContentIntent(pendingIntent);
//
//        NotificationManager notificationManager =
//                (NotificationManager) requireContext().getSystemService(Context.NOTIFICATION_SERVICE);
//
//        // Since android Oreo notification channel is needed.
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
//                    "Channel human readable title",
//                    NotificationManager.IMPORTANCE_DEFAULT);
//            notificationManager.createNotificationChannel(channel);
//        }
//
//        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
//    }
}