package com.intimation.myquiz.rest;

import com.intimation.myquiz.model.ModelClassMapper;

/**
 * Created by gorillalogic on 6/12/15.
 */
public interface OnPostExecuteListener {
    public void onSuccess(ModelClassMapper model);
    public void onFailure();
}
