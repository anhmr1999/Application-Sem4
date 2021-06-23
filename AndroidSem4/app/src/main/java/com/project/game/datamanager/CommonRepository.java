package com.project.game.datamanager;

import android.content.ContentValues;

public interface CommonRepository<TEnity> {
    public ContentValues convertToValue(TEnity entity);
}
