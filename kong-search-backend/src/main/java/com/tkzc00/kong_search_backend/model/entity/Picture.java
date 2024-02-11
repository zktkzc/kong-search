package com.tkzc00.kong_search_backend.model.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Picture implements Serializable {
    private static final long serialVersionUID = 6348905895966658373L;

    private String title;
    private String url;
}
