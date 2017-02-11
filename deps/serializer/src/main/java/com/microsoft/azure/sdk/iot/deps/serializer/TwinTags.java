// Copyright (c) Microsoft. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package com.microsoft.azure.sdk.iot.deps.serializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.internal.LinkedTreeMap;

import java.util.HashMap;
import java.util.Map;

/**
 * INNER TWIN CLASS
 *
 * Twin tags representation
 */
public class TwinTags extends HashMap<String, HashMap<String, String>> {

    protected TwinTags()
    {
        super();
    }

    protected TwinTags(String tag, HashMap<String, Object> tagProperties) throws IllegalArgumentException
    {
        super();
        addTag(tag, tagProperties);
    }

    protected void addTag(String tag, HashMap<String, Object> tagProperties) throws IllegalArgumentException
    {
        for (Map.Entry<String,Object> e : tagProperties.entrySet()) {
            addTag(tag, e.getKey(), e.getValue());
        }
    }

    protected void addTag(String tag, String key, Object value) throws IllegalArgumentException
    {
        if(tag == null) {
            throw new IllegalArgumentException ("tag shall not be null");
        }
        if(tag.isEmpty()) {
            throw new IllegalArgumentException("tag is empty");
        }
        if(tag.length()>128) {
            throw new IllegalArgumentException("tag is too big for json");
        }
        if(tag.contains(".") || tag.contains(" ") || tag.contains("$") ) {
            throw new IllegalArgumentException("tag contains illegal character");
        }

        if(key == null) {
            throw new IllegalArgumentException ("key shall not be null");
        }
        if(key.isEmpty()) {
            throw new IllegalArgumentException("key is empty");
        }
        if(key.length()>128) {
            throw new IllegalArgumentException("key is too big for json");
        }
        if(key.contains(".") || key.contains(" ") || key.contains("$") ) {
            throw new IllegalArgumentException("key contains illegal character");
        }

        if(value == null) {
            throw new IllegalArgumentException ("value shall not be null");
        }
        if(value.toString().isEmpty()) {
            throw new IllegalArgumentException("value is empty");
        }

        HashMap<String, String> tagHash = super.get(tag);

        if(tagHash == null)
        {
            tagHash = new HashMap<>();
        }

        tagHash.put(key, value.toString());
        super.put(tag, tagHash);
    }

    protected String GetTagProperty(String tag, String key) throws IllegalArgumentException
    {
        HashMap<String, String> property = super.get(tag);
        return property.get(key);
    }

    protected String toJson()
    {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(this);
    }

    protected JsonElement toJsonElement()
    {
        Gson gson = new GsonBuilder().create();
        /* Codes_SRS_TWIN_21_017: [The toJsonElement shall return a JsonElement with information in the Twin using json format.] */
        return gson.toJsonTree(this);
    }

    protected void fromJson(String json)
    {
        Gson gson = new GsonBuilder().create();
        TwinTags newValues = gson.fromJson(json, TwinTags.class);
        copy(newValues);
    }

    protected void fromJson(Map<String, Object> json)
    {

    }

    private void copy(TwinTags newValues)
    {

    }
}
