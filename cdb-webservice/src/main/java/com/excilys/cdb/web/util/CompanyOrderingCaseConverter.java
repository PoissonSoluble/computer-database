package com.excilys.cdb.web.util;

import java.beans.PropertyEditorSupport;

import com.excilys.cdb.dto.CompanyOrdering;

public class CompanyOrderingCaseConverter  extends PropertyEditorSupport
{
    @Override 
    public void setAsText(final String text) throws IllegalArgumentException
    {
        setValue(CompanyOrdering.valueOf(text.trim().toUpperCase()));
    }
}
