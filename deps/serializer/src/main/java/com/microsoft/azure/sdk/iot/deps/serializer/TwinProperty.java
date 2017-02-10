// Copyright (c) Microsoft. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package com.microsoft.azure.sdk.iot.deps.serializer;

import com.google.gson.*;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.*;

/**
 * TwinProperty is a representation of the device twin property database.
 * It can represent `Desired` property as well `Reported` property.
 */
public class TwinProperty {

    private static final Gson gson = new GsonBuilder().create();
    private static final String versionTag = "$version";
    private static final String metadataTag = "$metadata";
    private static final String lastUpdateTag = "$lastUpdated";
    private static final String lastUpdateVersionTag = "$lastUpdatedVersion";

    public class Property
    {
        protected Object value;
        protected TwinMetadata metadata;
        public Property(Object val, Integer propertyVersion)
        {
            this.value = val;
            this.metadata = new TwinMetadata(propertyVersion);
        }
    }

    private HashMap<String, Property> property;
    private Integer version;
    private Boolean reportMetadata;

    public TwinProperty()
    {
        /* Codes_SRS_TWIN_PROPERTY_21_001: [The constructor shall call the constructor for the superClass.] */
        property = new HashMap<>();

        reportMetadata = false;

        /* Codes_SRS_TWIN_PROPERTY_21_002: [The constructor shall set version as null.] */
        this.version = null;
    }

    public TwinProperty(Boolean reportMetadata)
    {
        /* Codes_SRS_TWIN_PROPERTY_21_003: [The constructor shall call the constructor for the superClass.] */
        /* Codes_SRS_TWIN_PROPERTY_21_004: [The constructor shall set version as null.] */
        this();
        if(reportMetadata){
            /* Codes_SRS_TWIN_PROPERTY_21_005: [If reportMetadata is true, constructor shall create a instance of the TwinMetadata to store the property metadata.] */
            reportMetadata = true;
        }
        else
        {
            /* Codes_SRS_TWIN_PROPERTY_21_006: [If reportMetadata is false, constructor shall not create a instance of the TwinMetadata keeping it as null.] */
            reportMetadata = false;
        }
    }

    public void enableMetadata()
    {
        reportMetadata = true;
    }

    public Boolean addProperty(String key, Object value, Integer propertyVersion) throws IllegalArgumentException
    {
        Boolean change = false;

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

        if((!property.containsKey(key)) || (!property.get(key).value.equals(value)) || reportMetadata) {
            /* Codes_SRS_TWIN_PROPERTY_21_007: [The addProperty shall add the provided pair key value in the superClass' hashMap.] */
            /* Codes_SRS_TWIN_PROPERTY_21_017: [If the `key` already exists, the addProperty shall replace the existed value by the new one.] */
            /* Codes_SRS_TWIN_PROPERTY_21_008: [The addProperty shall create an instance of the metadata related to the provided key and version.] */
            /* Codes_SRS_TWIN_PROPERTY_21_010: [The addProperty shall add the created metadata to the `metadata`.] */
            change = true;
            property.put(key, new Property(value, propertyVersion));
        }

        return change;
    }

    public JsonElement update(Map<String, Object> property)
    {
        JsonElement updatedJsonElement;
        TwinProperty updated = new TwinProperty();

        if(property != null)
        {

            for(Map.Entry<String, Object> entry : property.entrySet())
            {
                if(addProperty(entry.getKey(), entry.getValue(), null))
                {
                    updated.addProperty(entry.getKey(), entry.getValue(), null);
                }
            }

            if(updated.size() > 0) {
                updatedJsonElement = updated.toJsonElement();
            }
            else
            {
                updatedJsonElement = null;
            }
        }
        else
        {
            updatedJsonElement = null;
        }

        return updatedJsonElement;
    }

    public Integer GetVersion()
    {
        /* Codes_SRS_TWIN_PROPERTY_21_009: [The GetVersion shall return an Integer with the property version stored in the `version`.] */
        return this.version;
    }

    public TwinMetadata GetMetadata(String key)
    {
        TwinMetadata twinMetadata;

        if(property.containsKey(key)) {
            /* Codes_SRS_TWIN_PROPERTY_21_021: [The GetMetadata shall return the TwinMetadata for the provided key.] */
            /* Codes_SRS_TWIN_PROPERTY_21_023: [If the key do not exists, the GetMetadata shall return null.] */
            twinMetadata = property.get(key).metadata;
        }
        else {
            twinMetadata = null;
        }

        return twinMetadata;
    }

    public HashMap<String, String> GetPropertyMap()
    {
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
            for (Map.Entry<String, Property> e : property.entrySet()) {
                propertyMap.put(e.getKey(), e.getValue().value.toString());
            }
        }
        return propertyMap;
    }

    public int size()
    {
        /* Codes_SRS_TWIN_PROPERTY_21_033: [The size shall return the number of keys in the property map.] */
        return property.size();
    }

    public Object get(String key)
    {
        Object result;

        if(property.containsKey(key)) {
            /* Codes_SRS_TWIN_PROPERTY_21_034: [**The get shall return the property value related to the provided key.] */
            result = property.get(key).value;
        } else {
            /* Codes_SRS_TWIN_PROPERTY_21_035: [**If the key does not exists, the get shall return null.] */
            result = null;
        }
        return result;
    }

    public String toJson()
    {
        /* Codes_SRS_TWIN_PROPERTY_21_026: [The toJson shall create a String with information in the TwinProperty using json format.] */
        /* Codes_SRS_TWIN_PROPERTY_21_027: [The toJson shall not include null fields.] */
        return toJsonElement().toString();
    }

    public JsonElement toJsonElement()
    {
        HashMap<String, Object> map = new HashMap<>();
        HashMap<String, TwinMetadata> metadata = new HashMap<>();

        for(Map.Entry<String, Property> entry : property.entrySet()) {
            map.put(entry.getKey(), entry.getValue().value);
            metadata.put(entry.getKey(), entry.getValue().metadata);
        }

        if(reportMetadata) {
            map.put(metadataTag, metadata);
        }

        if(version != null) {
            map.put(versionTag, version);
        }

        return gson.toJsonTree(map);
    }

    public void update(LinkedTreeMap<String, Object> jsonTree,
                       TwinPropertiesChangeCallback onCallback) throws IllegalArgumentException
    {
        HashMap<String, String> diffField = new HashMap<>();
        HashMap<String, String> diffMetadata = new HashMap<>();

        try {
            updateVersion(jsonTree);
            diffField = updateFields(jsonTree);
            diffMetadata = updateMetadata(jsonTree);
        } catch (Exception e) {
            throw new IllegalArgumentException("Malformed Json:" + e);
        }

        if(reportMetadata)
        {
            for(Map.Entry<String, String> entry : diffMetadata.entrySet())
            {
                Property val = property.get(entry.getKey());
                if(val == null){
                    diffField.put(entry.getKey(), null);
                } else {
                    diffField.put(entry.getKey(), val.value.toString());
                }
            }
        }

        if((diffField.size() != 0) &&(onCallback != null))
        {
            onCallback.execute(diffField);
        }
    }

    public void update(String json, TwinPropertiesChangeCallback onCallback) throws IllegalArgumentException
    {
        /* Codes_SRS_TWIN_PROPERTY_21_028: [The update shall fill the fields in TwinProperty with the values provided in the json string.] */
        /* Codes_SRS_TWIN_PROPERTY_21_029: [The update shall not change fields that is not reported in the json string.] */
        Type stringMap = new TypeToken<Map<String, String>>(){}.getType();
        LinkedTreeMap<String, Object> newValues = new LinkedTreeMap<String, Object>();
        try {
            newValues = (LinkedTreeMap<String, Object>) gson.fromJson(json, newValues.getClass());
        } catch (Exception e) {
            throw new IllegalArgumentException("Malformed Json:" + e);
        }
        update(newValues, onCallback);
    }

    private void updateVersion(LinkedTreeMap<String, Object> jsonTree) {
        for (Map.Entry<String, Object> entry : jsonTree.entrySet()) {
            if (entry.getKey().equals(versionTag)) {
                /* Codes_SRS_TWIN_PROPERTY_21_030: [If the provided json contains $version, the update shall update the version.] */
                version = new Integer( (int) ((double) entry.getValue()));
                break;
            }
        }
    }

    private HashMap<String, String>  updateMetadata(LinkedTreeMap<String, Object> jsonTree)
    {
        HashMap<String, String> diff = new HashMap<>();
        for (Map.Entry<String, Object> entry : jsonTree.entrySet()) {
            if(entry.getKey().equals(metadataTag))
            {
                /* Codes_SRS_TWIN_PROPERTY_21_031: [If the provided json contains $metadata, the update shall update the metadata for each provided key.] */
                LinkedTreeMap<String, Object> metadataTree = (LinkedTreeMap<String, Object>)entry.getValue();
                for (LinkedTreeMap.Entry<String, Object> item : metadataTree.entrySet()) {
                    if(property.containsKey(item.getKey())) {
                        LinkedTreeMap<String, Object> itemTree = (LinkedTreeMap<String, Object>) item.getValue();
                        String lastUpdated = null;
                        Integer lastUpdatedVersion = null;
                        for (LinkedTreeMap.Entry<String, Object> metadataItem : itemTree.entrySet()) {
                            if (metadataItem.getKey().equals(lastUpdateTag)) {
                                lastUpdated = metadataItem.getValue().toString();
                            } else if (metadataItem.getKey().equals(lastUpdateVersionTag)) {
                                lastUpdatedVersion = (int) ((double) metadataItem.getValue());
                            }
                        }
                        if(property.get(item.getKey()).metadata.update(lastUpdated, lastUpdatedVersion))
                        {
                            diff.put(item.getKey(), item.getValue().toString());
                        }
                    }
                }
                break;
            }
        }
        return diff;
    }

    private HashMap<String, String> updateFields(LinkedTreeMap<String, Object> jsonTree) throws IllegalArgumentException
    {
        HashMap<String, String> diff = new HashMap<>();

        for (Map.Entry<String, Object> entry : jsonTree.entrySet()) {
            if(entry.getKey().isEmpty())
            {
                throw new IllegalArgumentException("Invalid Key on Json");
            }
            if(!entry.getKey().contains("$"))
            {
                if(property.containsKey(entry.getKey()))
                {
                    if(entry.getValue() == null)
                    {
                        property.remove(entry.getKey());
                        diff.put(entry.getKey(), null);
                    }
                    else if(!property.get(entry.getKey()).value.equals(entry.getValue()))
                    {
                        property.put(entry.getKey(), new Property(entry.getValue(), null));
                        diff.put(entry.getKey(), entry.getValue().toString());
                    }
                }
                else if(entry.getValue() != null)
                {
                    property.put(entry.getKey(), new Property(entry.getValue(), null));
                    diff.put(entry.getKey(), entry.getValue().toString());
                }
            }
        }

        return diff;
    }

}
