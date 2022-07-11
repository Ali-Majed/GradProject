package com.example.gradproject.modle;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.gradproject.R;
import com.example.gradproject.fragment.OrdersFragment;
import com.example.gradproject.interfaces.callbacks.ListCallback;
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

public class FirestoreController {
    private FirebaseFirestore firebaseFirestore;

    public FirestoreController() {
        firebaseFirestore = FirebaseFirestore.getInstance();

    }


    public void readProduct(ListCallback<Product> listCallback){

firebaseFirestore.collection("product").get()
        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    List<Product> products=new ArrayList<>();
                    for (QueryDocumentSnapshot snapshot:task.getResult()){
                        Product product = snapshot.toObject(Product.class);
                        if (Objects.requireNonNull(FirebaseAuth.getInstance()
                                .getCurrentUser()).getUid().equals(product.getUser_id())) {
                            Log.d("TAGuser", "onComplete: " + product.getUser_id());
                            product.setId_product(snapshot.getId());
//
                        products.add(product);
                        }
                    }
                    listCallback.onFinished(products,true);
                }else{
                    listCallback.onFinished(null,false);
                }
            }
        });

    }
    public void readOrderPos(ListCallback<Orders> listCallback){

        firebaseFirestore.collection("order")
                .orderBy("time", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Orders> ordersList=new ArrayList<>();
                            for (QueryDocumentSnapshot snapshot : task.getResult()) {
                                Orders order = snapshot.toObject(Orders.class);
                                if (Objects.requireNonNull(FirebaseAuth.getInstance()
                                        .getCurrentUser()).getUid().equals(order.getIdPos())){
                                    Log.d("TAGuser", "onComplete: "+order.getIdCompany());
                                    order.setIdOrder(snapshot.getId());
                                    ordersList.add(order);
                                }
                                listCallback.onFinished(ordersList,true);
                            }

                        }else {
                            listCallback.onFinished(null,true);

                        }
                    }
                });

    }

    public void readOrder(Context context,ListCallback<Orders> listCallback){

        firebaseFirestore.collection("order").orderBy("time", Query.Direction.DESCENDING).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {


                            List<Orders> ordersList=new ArrayList<>();
                            for (QueryDocumentSnapshot snapshot : task.getResult()) {
                                Orders order = snapshot.toObject(Orders.class);
                                if (Objects.requireNonNull(FirebaseAuth.getInstance()
                                        .getCurrentUser()).getUid().equals(order.getIdCompany())){
                                    Log.d("TAGuser", "onComplete: "+order.getIdCompany());
                                    order.setIdOrder(snapshot.getId());
                                    ordersList.add(order);

                                }

                            }
                            listCallback.onFinished(ordersList,true);

                        }else {
                            listCallback.onFinished(null,false);

                        }
                    }
                });

    }

    public void readPos(ListCallback<UserPOS> listCallback){



        firebaseFirestore.collection("users")
                .whereEqualTo("usertypePos",1)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<UserPOS> arrayList=new ArrayList<>();
                            for (QueryDocumentSnapshot snapshot : task.getResult()) {
                                UserPOS userPOS = snapshot.toObject(UserPOS.class);
                                Log.d("TAGuser", "onComplete: "+userPOS.getIdPos());
                                userPOS.setIdPos(snapshot.getId());
                                arrayList.add(userPOS);
                            }
                            listCallback.onFinished(arrayList,true);
                        }else {
                            listCallback.onFinished(null,false);
                        }
                    }
                });

    }

    public void readCompany(ListCallback<UsersCompany> listCallback){



        firebaseFirestore.collection("users")
                .whereEqualTo("usertypePos",0)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<UsersCompany> usersCompanies=new ArrayList<>();
                            for (QueryDocumentSnapshot snapshot : task.getResult()) {
                                UsersCompany usersCompany = snapshot.toObject(UsersCompany.class);
                                Log.d("TAGuser", "onComplete: "+usersCompany.getId());
                                usersCompany.setId(snapshot.getId());
                                usersCompanies.add(usersCompany);

                            }
                            listCallback.onFinished(usersCompanies,true);
                        }else {
                            listCallback.onFinished(null,false);
                        }
                    }
                });

    }


}
