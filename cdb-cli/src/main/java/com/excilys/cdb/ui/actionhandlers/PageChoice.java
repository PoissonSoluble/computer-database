package com.excilys.cdb.ui.actionhandlers;

import java.util.Arrays;
import java.util.List;

import com.excilys.cdb.pagination.Page;

public enum PageChoice {
    FIRST_PAGE("[f]irst page", false, "f", "first page") {
        @Override
        public boolean handle(Page<?> page) {
            page.first();
            return true;
        }
    },
    LAST_PAGE("[l]ast page", false, "l", "last page") {
        @Override
        public boolean handle(Page<?> page) {
            page.last();
            return true;
        }
    },
    PREVIOUS_PAGE("[p]revious page", false, "p", "previous page") {
        @Override
        public boolean handle(Page<?> page) {
            page.previous();
            return true;
        }
    },
    NEXT_PAGE("[n]ext page", false, "n", "next page") {
        @Override
        public boolean handle(Page<?> page) {
            page.next();
            return true;
        }
    },
    QUIT("[q]uit", false, "q", "quit") {
        @Override
        public boolean handle(Page<?> page) {
            return false;
        }
    },
    CURRENT_PAGE("current", true) {
        @Override
        public boolean handle(Page<?> page) {
            return true;
        }
    };

    private String title;
    private boolean hidden;
    private List<String> validChoices;

    PageChoice(String pTitle, boolean pHidden,  String... pValidChoices) {
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

    public abstract boolean handle(Page<?> page);
}
