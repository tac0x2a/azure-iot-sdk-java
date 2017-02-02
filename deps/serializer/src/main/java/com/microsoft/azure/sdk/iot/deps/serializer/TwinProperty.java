// Copyright (c) Microsoft. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package com.microsoft.azure.sdk.iot.deps.serializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

/**
 * Twin represents representation
 */
public class TwinProperty extends HashMap<String, Object> {

    private static final Gson gson = new GsonBuilder().create();

    @SerializedName("$metadata")
    protected HashMap<String, TwinMetadata> metadata = new HashMap<>();

    @SerializedName("$version")
    protected Integer version;

    public TwinProperty(Integer version) throws IllegalArgumentException
    {
        super();
        if(version == null)
        {
            throw new IllegalArgumentException("version is mandatory.");
        }
        this.version = version;
    }

    public void addProperty(String key, Object value, Integer version) throws IllegalArgumentException
    {
        metadata.put(key, new TwinMetadata(version));
        super.put(key, value);
    }

    public Integer GetVersion()
    {
        return this.version;
    }

    public TwinMetadata GetMetadata(String key)
    {
        return this.metadata.get(key);
    }

    public String toJson()
    {
        return gson.toJson(this);
    }

    public void fromJson(String json)
    {
        TwinProperty newValues = gson.fromJson(json, TwinProperty.class);
        copy(newValues);
    }

    private void copy(TwinProperty newValues)
    {

    }

}
