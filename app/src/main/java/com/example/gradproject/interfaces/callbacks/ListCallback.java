package com.example.gradproject.interfaces.callbacks;


import java.util.List;

public interface ListCallback <T>{

    void onFinished(List<T> list, boolean success);

}
