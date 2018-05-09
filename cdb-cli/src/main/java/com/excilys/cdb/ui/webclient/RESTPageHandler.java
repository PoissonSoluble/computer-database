package com.excilys.cdb.ui.webclient;

import com.excilys.cdb.ui.container.Page;

public interface RESTPageHandler<T> {

    Page<T> getListFromREST(int page, int size);
}
