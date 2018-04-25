package com.excilys.cdb.ui.actionhandlers;

import java.util.Arrays;
import java.util.List;

import org.springframework.data.domain.Page;

import com.excilys.cdb.service.IService;

public enum PageChoice {
    FIRST_PAGE("[f]irst page", false, "f", "first page") {
        @Override
        public Page<?> handle(Page<?> page, IService<?> service) {
            return service.getPage(0, page.getSize(), "");
        }
    },
    LAST_PAGE("[l]ast page", false, "l", "last page") {
        @Override
        public Page<?> handle(Page<?> page, IService<?> service) {
            return service.getPage(page.getTotalPages() - 1, page.getSize(), "");
        }
    },
    PREVIOUS_PAGE("[p]revious page", false, "p", "previous page") {
        @Override
        public Page<?> handle(Page<?> page, IService<?> service) {
            int pageNumber = page.getNumber() == 0 ? 1 : page.getNumber();
            return service.getPage(pageNumber - 1, page.getSize(), "");
        }
    },
    NEXT_PAGE("[n]ext page", false, "n", "next page") {
        @Override
        public Page<?> handle(Page<?> page, IService<?> service) {
            int pageNumber = page.getNumber() == page.getTotalPages() - 1 ? page.getTotalPages() - 2 : page.getNumber();
            return service.getPage(pageNumber + 1, page.getSize(), "");
        }
    },
    QUIT("[q]uit", false, "q", "quit") {
        @Override
        public Page<?> handle(Page<?> page, IService<?> Service) {
            return null;
        }
    },
    CURRENT_PAGE("current", true) {
        @Override
        public Page<?> handle(Page<?> page, IService<?> service) {
            return page;
        }
    };

    private String title;
    private boolean hidden;
    private List<String> validChoices;

    PageChoice(String pTitle, boolean pHidden, String... pValidChoices) {
        title = pTitle;
        hidden = pHidden;
        validChoices = Arrays.asList(pValidChoices);
    }

    public boolean accept(String choice) {
        if (validChoices.contains(choice.toLowerCase())) {
            return true;
        }
        return false;
    }

    public boolean isHidden() {
        return hidden;
    }

    public String getTitle() {
        return title;
    }

    public abstract Page<?> handle(Page<?> page, IService<?> service);
}
