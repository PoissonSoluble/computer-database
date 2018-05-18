package com.excilys.cdb.web.util;

import java.beans.PropertyEditorSupport;

import com.excilys.cdb.dto.ComputerOrdering;

public class ComputerOrderingCaseConverter extends PropertyEditorSupport
{
    @Override 
    public void setAsText(final String text) throws IllegalArgumentException
    {
        setValue(ComputerOrdering.valueOf(text.trim().toUpperCase()));
    }
}