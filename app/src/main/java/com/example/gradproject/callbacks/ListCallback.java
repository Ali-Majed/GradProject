package com.example.gradproject.callbacks;

import java.util.List;

public interface ListCallback <T>{
    void onFinished(List<T> list, boolean success);

}
