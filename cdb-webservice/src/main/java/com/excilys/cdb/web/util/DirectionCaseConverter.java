package com.excilys.cdb.web.util;

import java.beans.PropertyEditorSupport;

import org.springframework.data.domain.Sort.Direction;

public class DirectionCaseConverter extends PropertyEditorSupport
{
    @Override 
    public void setAsText(final String text) throws IllegalArgumentException
    {
        setValue(Direction.valueOf(text.trim().toUpperCase()));
    }
}