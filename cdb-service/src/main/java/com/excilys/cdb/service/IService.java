package com.excilys.cdb.service;

import org.springframework.data.domain.Page;

public interface IService<T> {
    Page<T> getPage(int page, int pageSize, String search);
}
