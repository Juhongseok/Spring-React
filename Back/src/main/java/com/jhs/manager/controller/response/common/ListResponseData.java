package com.jhs.manager.controller.response.common;

import lombok.Getter;

import java.util.List;

@Getter
public class ListResponseData<T> extends ResponseData {
    private List<T> data;

    protected ListResponseData(List<T> data) {
        this.data = data;
    }

    public static <T> ListResponseData of(List<T> data) {
        return new ListResponseData(data);
    }
}
