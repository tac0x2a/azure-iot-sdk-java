// Copyright (c) Microsoft. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package com.microsoft.azure.sdk.iot.deps.serializer;

<<<<<<< HEAD
import com.google.gson.*;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import javax.json.Json;
import java.lang.reflect.Type;
import java.util.*;

/**
 * TwinProperty is a representation of the device twin property database.
 * It can represent `Desired` property as well `Reported` property.
 */
public class TwinProperty {

    private static final Gson gson = new GsonBuilder().create();

    protected HashMap<String, Object> property;
    protected HashMap<String, TwinMetadata> metadata;
=======
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
    protected HashMap<String, TwinMetadata> metadata = null;

    @SerializedName("$version")
>>>>>>> addf8a754e926e0d488a2aea9316163f84e12f04
    protected Integer version;

    public TwinProperty()
    {
<<<<<<< HEAD
        /* Codes_SRS_TWIN_PROPERTY_21_001: [The constructor shall call the constructor for the superClass.] */
        property = new HashMap<>();
        metadata = null;

        /* Codes_SRS_TWIN_PROPERTY_21_002: [The constructor shall store set version equals 0.] */
=======
        super();
>>>>>>> addf8a754e926e0d488a2aea9316163f84e12f04
        this.version = 0;
    }

    public TwinProperty(Boolean reportMetadata)
    {
<<<<<<< HEAD
        /* Codes_SRS_TWIN_PROPERTY_21_003: [The constructor shall call the constructor for the superClass.] */
        /* Codes_SRS_TWIN_PROPERTY_21_004: [The constructor shall store set version equals 0.] */
        this();
        if(reportMetadata){
            /* Codes_SRS_TWIN_PROPERTY_21_005: [If reportMetadata is true, constructor shall create a instance of the TwinMetadata to store the property metadata.] */
            this.metadata = new HashMap<>();
        }
        else
        {
            /* Codes_SRS_TWIN_PROPERTY_21_006: [If reportMetadata is false, constructor shall not create a instance of the TwinMetadata keeping it as null.] */
        }
=======
        this();
        if(reportMetadata){
            this.metadata = new HashMap<>();
        }
>>>>>>> addf8a754e926e0d488a2aea9316163f84e12f04
    }

    public void addProperty(String key, Object value, Integer version) throws IllegalArgumentException
    {
<<<<<<< HEAD
        if(key == null) {
            /* Codes_SRS_TWIN_PROPERTY_21_013: [If the `key` is null, the addProperty shall throw IllegalArgumentException.] */
            throw new IllegalArgumentException("Property key shall not be null");
        }
        if(key.isEmpty()) {
            /* Codes_SRS_TWIN_PROPERTY_21_014: [If the `key` is empty, the addProperty shall throw IllegalArgumentException.] */
            throw new IllegalArgumentException("Property key shall not be empty");
        }
        if(key.length()>128) {
            /* Codes_SRS_TWIN_PROPERTY_21_015: [If the `key` is more than 128 characters long, the addProperty shall throw IllegalArgumentException.] */
            throw new IllegalArgumentException("Property key is too big for json");
        }
        if(key.contains(".") || key.contains(" ") || key.contains("$") ) {
            /* Codes_SRS_TWIN_PROPERTY_21_016: [If the `key` has an illegal character, the addProperty shall throw IllegalArgumentException.] */
            throw new IllegalArgumentException("Property key contains illegal character");
        }

        if(value == null) {
            /* Codes_SRS_TWIN_PROPERTY_21_018: [If the `value` is null, the addProperty shall throw IllegalArgumentException.] */
            throw new IllegalArgumentException ("value shall not be null");
        }
        if(value.toString().isEmpty()) {
            /* Codes_SRS_TWIN_PROPERTY_21_019: [If the `value` is empty, the addProperty shall throw IllegalArgumentException.] */
            throw new IllegalArgumentException("value is empty");
        }

        /* Codes_SRS_TWIN_PROPERTY_21_007: [The addProperty shall add the provided pair key value in the superClass' hashMap.] */
        /* Codes_SRS_TWIN_PROPERTY_21_017: [If the `key` already exists, the addProperty shall replace the existed value by the new one.] */
        property.put(key, value);

        /* Codes_SRS_TWIN_PROPERTY_21_011: [If the metadata is null, the addProperty shall not create or add any metadata to it.] */
        if(metadata != null) {
            /* Codes_SRS_TWIN_PROPERTY_21_008: [The addProperty shall create an instance of the metadata related to the provided key and version.] */
            /* Codes_SRS_TWIN_PROPERTY_21_010: [The addProperty shall add the created metadata to the `metadata`.] */
            metadata.put(key, new TwinMetadata(version));
        }
=======
        if(metadata != null) {
            metadata.put(key, new TwinMetadata(version));
        }
        super.put(key, value);
>>>>>>> addf8a754e926e0d488a2aea9316163f84e12f04
    }

    public Integer GetVersion()
    {
<<<<<<< HEAD
        /* Codes_SRS_TWIN_PROPERTY_21_009: [The GetVersion shall return an Integer with the property version stored in the `version`.] */
        return this.version;
    }

    public HashMap<String, TwinMetadata> GetMetadata()
    {
        /* Codes_SRS_TWIN_PROPERTY_21_012: [The GetMetadata shall return a hashMap to all metadata.] */
        /* Codes_SRS_TWIN_PROPERTY_21_020: [If there is no metadata, the GetMetadata shall return null.] */
        return metadata;
    }

    public TwinMetadata GetMetadata(String key)
    {
        TwinMetadata twinMetadata;

        if(metadata != null){
            /* Codes_SRS_TWIN_PROPERTY_21_021: [The GetMetadata shall return the TwinMetadata for the provided key.] */
            /* Codes_SRS_TWIN_PROPERTY_21_023: [If the key do not exists, the GetMetadata shall return null.] */
            twinMetadata = metadata.get(key);
        }
        else
        {
            /* Codes_SRS_TWIN_PROPERTY_21_022: [If there is no metadata, the GetMetadata shall return null.] */
            twinMetadata = null;
        }
=======
        return this.version;
    }

    public TwinMetadata GetMetadata(String key)
    {
        TwinMetadata twinMetadata = null;

        if(metadata != null){
            twinMetadata = metadata.get(key);
        }
>>>>>>> addf8a754e926e0d488a2aea9316163f84e12f04

        return twinMetadata;
    }

    public HashMap<String, String> GetPropertyMap()
    {
<<<<<<< HEAD
        HashMap<String, String> propertyMap;

        if(property.isEmpty())
        {
            /* Codes_SRS_TWIN_PROPERTY_21_025: [If there is no key value, the GetPropertyMap shall return a null hashMap.] */
            propertyMap = null;
        }
        else
        {
            /* Codes_SRS_TWIN_PROPERTY_21_024: [The GetPropertyMap shall return a hashMap with all keys and values stored on the Twin Property.] */
            propertyMap = new HashMap<>();
            for (Map.Entry<String, Object> e : property.entrySet()) {
                propertyMap.put(e.getKey(), e.getValue().toString());
            }
        }
        return propertyMap;
    }

    public int size()
    {
        return property.size();
    }

    public Object get(String key)
    {
        return property.get(key);
=======
        HashMap<String , String> propertyMap = new HashMap<>();

        // TODO: cast this to HashMap<String , String>

        return propertyMap;
>>>>>>> addf8a754e926e0d488a2aea9316163f84e12f04
    }

    public String toJson()
    {
<<<<<<< HEAD
        /* Codes_SRS_TWIN_PROPERTY_21_026: [The toJson shall create a String with information in the TwinProperty using json format.] */
        /* Codes_SRS_TWIN_PROPERTY_21_027: [The toJson shall not include null fields.] */
        HashMap<String, Object> map = property;

        if(metadata != null) {
            map.put("$metadata", metadata);
            map.put("$version", version);
        }

        return gson.toJson(map);
=======
        return gson.toJson(this);
>>>>>>> addf8a754e926e0d488a2aea9316163f84e12f04
    }

    public void fromJson(String json)
    {
<<<<<<< HEAD
        /* Codes_SRS_TWIN_PROPERTY_21_028: [The fromJson shall fill the fields in TwinProperty with the values provided in the json string.] */
        /* Codes_SRS_TWIN_PROPERTY_21_029: [The fromJson shall not change fields that is not reported in the json string.] */
        Type stringMap = new TypeToken<Map<String, String>>(){}.getType();
        Map<String, Object> map = new HashMap<String, Object>();
        map = (Map<String, Object>) gson.fromJson(json, map.getClass());
        copy(map);
    }

    private void copy(Map<String, Object> newValues)
    {
        for (Map.Entry<String, Object> e : newValues.entrySet()) {
            if(e.getKey().equals("$version"))
            {
                version = Integer.parseInt(e.getValue().toString());
            }
            else if(e.getKey().equals("$metadata"))
            {
                metadata.put(e.getKey(), new TwinMetadata());
            }
            else
            {
                property.put(e.getKey(), e.getValue());
            }
        }
=======
        TwinProperty newValues = gson.fromJson(json, TwinProperty.class);
        copy(newValues);
    }

    private void copy(TwinProperty newValues)
    {

>>>>>>> addf8a754e926e0d488a2aea9316163f84e12f04
    }

}
