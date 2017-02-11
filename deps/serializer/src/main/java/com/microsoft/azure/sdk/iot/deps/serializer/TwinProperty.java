// Copyright (c) Microsoft. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package com.microsoft.azure.sdk.iot.deps.serializer;

import com.google.gson.*;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * INNER TWIN CLASS
 *
 * TwinProperty is a representation of the device twin property database.
 * It can represent `Desired` property as well `Reported` property.
 *
 */
public class TwinProperty {

    private static final String versionTag = "$version";
    private static final String metadataTag = "$metadata";
    private static final String lastUpdateTag = "$lastUpdated";
    private static final String lastUpdateVersionTag = "$lastUpdatedVersion";

    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock readLock = lock.readLock();

    private class Property
    {
        private Object value;
        private TwinMetadata metadata;
        private Property(Object val, Integer propertyVersion)
        {
            this.value = val;
            this.metadata = new TwinMetadata(propertyVersion);
        }
    }

    private Map<String, Property> property;
    private Integer version;
    private Boolean reportMetadata;

    protected TwinProperty()
    {
        property = new HashMap<>();

        reportMetadata = false;

        this.version = null;
    }

    protected TwinProperty(Boolean reportMetadata)
    {
        this();
        if(reportMetadata){
            reportMetadata = true;
        }
        else
        {
            reportMetadata = false;
        }
    }

    protected void enableMetadata()
    {
        /* Codes_SRS_TWIN_21_020: [The enableMetadata shall enable report metadata in Json for the Desired and for the Reported Properties.] */
        reportMetadata = true;
    }

    protected Boolean addProperty(String key, Object value, Integer propertyVersion) throws IllegalArgumentException
    {
        /* Codes_SRS_TWIN_21_059: [The updateDesiredProperty shall only change properties in the map, keep the others as is.] */
        /* Codes_SRS_TWIN_21_061: [All `key` and `value` in property shall be case sensitive.] */
        /* Codes_SRS_TWIN_21_060: [The updateReportedProperty shall only change properties in the map, keep the others as is.] */
        /* Codes_SRS_TWIN_21_062: [All `key` and `value` in property shall be case sensitive.] */
        Boolean change = false;

        if(key == null) {
            /* Codes_SRS_TWIN_21_073: [If any `key` is null, the updateDesiredProperty shall throw IllegalArgumentException.] */
            /* Codes_SRS_TWIN_21_079: [If any `key` is null, the updateReportedProperty shall throw IllegalArgumentException.] */
            throw new IllegalArgumentException("Property key shall not be null");
        }
        if(key.isEmpty()) {
            /* Codes_SRS_TWIN_21_074: [If any `key` is empty, the updateDesiredProperty shall throw IllegalArgumentException.] */
            /* Codes_SRS_TWIN_21_080: [If any `key` is empty, the updateReportedProperty shall throw IllegalArgumentException.] */
            throw new IllegalArgumentException("Property key shall not be empty");
        }
        if(key.length()>128) {
            /* Codes_SRS_TWIN_21_075: [If any `key` is more than 128 characters long, the updateDesiredProperty shall throw IllegalArgumentException.] */
            /* Codes_SRS_TWIN_21_081: [If any `key` is more than 128 characters long, the updateReportedProperty shall throw IllegalArgumentException.] */
            throw new IllegalArgumentException("Property key is too big for json");
        }
        if(key.contains(".") || key.contains(" ") || key.contains("$") ) {
            /* Codes_SRS_TWIN_21_076: [If any `key` has an illegal character, the updateDesiredProperty shall throw IllegalArgumentException.] */
            /* Codes_SRS_TWIN_21_082: [If any `key` has an illegal character, the updateReportedProperty shall throw IllegalArgumentException.] */
            throw new IllegalArgumentException("Property key contains illegal character");
        }

        if((!property.containsKey(key)) || (!property.get(key).value.equals(value)) || reportMetadata) {
            change = true;
        }

        /* Codes_SRS_TWIN_21_077: [If any `key` already exists, the updateDesiredProperty shall replace the existed value by the new one.] */
        /* Codes_SRS_TWIN_21_083: [If any `key` already exists, the updateReportedProperty shall replace the existed value by the new one.] */
        property.put(key, new Property(value, propertyVersion));

        return change;
    }

    protected JsonElement update(Map<String, Object> property)
    {
        JsonElement updatedJsonElement;
        TwinProperty updated = new TwinProperty();

        if(property != null)
        {
            synchronized (lock) {
                for (Map.Entry<String, Object> entry : property.entrySet()) {
                    if (addProperty(entry.getKey(), entry.getValue(), null)) {
                        if (entry.getValue() != null) {
                            /* Codes_SRS_TWIN_21_078: [If any `value` is null, the updateDesiredProperty shall store it but do not report on Json.] */
                            /* Codes_SRS_TWIN_21_084: [If any `value` is null, the updateReportedProperty shall store it but do not report on Json.] */
                            updated.addProperty(entry.getKey(), entry.getValue(), null);
                        }
                    }
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

    protected Integer GetVersion()
    {
        /* Codes_SRS_TWIN_21_048: [The getDesiredPropertyVersion shall return the desired property version.] */
        /* Codes_SRS_TWIN_21_049: [The getReportedPropertyVersion shall return the reported property version.] */
        return this.version;
    }

    protected TwinMetadata GetMetadata(String key)
    {
        TwinMetadata twinMetadata = null;

        readLock.lock();
        try {
            if (property.containsKey(key)) {
                twinMetadata = property.get(key).metadata;
            } else {
                twinMetadata = null;
            }
        } finally {
            readLock.unlock();
        }

        return twinMetadata;
    }

    protected Map<String, String> GetPropertyMap()
    {
        /* Codes_SRS_TWIN_21_050: [The getDesiredPropertyMap shall return a map with all desired property key value pairs.] */
        /* Codes_SRS_TWIN_21_051: [The getReportedPropertyMap shall return a map with all reported property key value pairs.] */
        Map<String, String> propertyMap = null;

        readLock.lock();
        try {
            if (property.isEmpty()) {
                propertyMap = null;
            } else {
                propertyMap = new HashMap<>();
                for (Map.Entry<String, Property> e : property.entrySet()) {
                    if (e.getValue().value == null) {
                        propertyMap.put(e.getKey(), null);
                    } else {
                        propertyMap.put(e.getKey(), e.getValue().value.toString());
                    }
                }
            }
        } finally {
            readLock.unlock();
        }
        return propertyMap;
    }

    protected int size()
    {
        int size = 0;

        readLock.lock();
        try{
            size = property.size();
        } finally {
            readLock.unlock();
        }

        return size;
    }

    protected Object get(String key)
    {
        Object result = null;

        readLock.lock();
        try {
            if (property.containsKey(key)) {
                result = property.get(key).value;
            } else {
                result = null;
            }
        } finally {
            readLock.unlock();
        }
        return result;
    }

    protected String toJson()
    {
        return toJsonElement().toString();
    }

    protected JsonElement toJsonElement()
    {
        /* Codes_SRS_TWIN_21_017: [The toJsonElement shall return a JsonElement with information in the Twin using json format.] */
        Gson gson = new GsonBuilder().create();
        Map<String, Object> map = new HashMap<>();
        Map<String, TwinMetadata> metadata = new HashMap<>();

        readLock.lock();
        try {
            for (Map.Entry<String, Property> entry : property.entrySet()) {
                /* Codes_SRS_TWIN_21_018: [The toJsonElement shall not include null fields.] */
                if(entry.getValue().value != null) {
                    map.put(entry.getKey(), entry.getValue().value);
                    metadata.put(entry.getKey(), entry.getValue().metadata);
                }
            }
        } finally {
            readLock.unlock();
        }

        if(reportMetadata) {
            map.put(metadataTag, metadata);
        }

        if(version != null) {
            map.put(versionTag, version);
        }

        return gson.toJsonTree(map);
    }

    protected void update(LinkedTreeMap<String, Object> jsonTree,
                       TwinPropertiesChangeCallback onCallback) throws IllegalArgumentException
    {
        Map<String, String> diffField = new HashMap<>();
        Map<String, String> diffMetadata = new HashMap<>();

        try {
            /* Codes_SRS_TWIN_21_039: [The updateTwin shall fill the fields the properties in the Twin class with the keys and values provided in the json string.] */
            /* Codes_SRS_TWIN_21_029: [The updateDesiredProperty shall update the Desired property using the information provided in the json.] */
            /* Codes_SRS_TWIN_21_034: [The updateReportedProperty shall update the Reported property using the information provided in the json.] */
            updateVersion(jsonTree);
            /* Codes_SRS_TWIN_21_041: [The updateTwin shall create a list with all properties that was updated (new key or value) by the new json.] */
            /* Codes_SRS_TWIN_21_030: [The updateDesiredProperty shall generate a map with all pairs key value that had its content changed.] */
            /* Codes_SRS_TWIN_21_035: [The updateReportedProperty shall generate a map with all pairs key value that had its content changed.] */
            diffField = updateFields(jsonTree);
            diffMetadata = updateMetadata(jsonTree);
        } catch (Exception e) {
            /* Codes_SRS_TWIN_21_092: [If the provided json is not valid, the updateDesiredProperty shall throws IllegalArgumentException.] */
            throw new IllegalArgumentException("Malformed Json:" + e);
        }

        if(reportMetadata)
        {
            for(Map.Entry<String, String> entry : diffMetadata.entrySet())
            {
                Property val = null;
                readLock.lock();
                try {
                    val = property.get(entry.getKey());
                } finally {
                    readLock.unlock();
                }
                if (val == null) {
                    diffField.put(entry.getKey(), null);
                } else {
                    diffField.put(entry.getKey(), val.value.toString());
                }
            }
        }

        /* Codes_SRS_TWIN_21_046: [If OnDesiredCallback was not provided, the updateTwin shall not do anything with the list of updated desired properties.] */
        /* Codes_SRS_TWIN_21_047: [If OnReportedCallback was not provided, the updateTwin shall not do anything with the list of updated reported properties.] */
        /* Codes_SRS_TWIN_21_069: [If there is no change in the Desired property, the updateTwin shall not change the reported database and not call the OnReportedCallback.] */
        /* Codes_SRS_TWIN_21_070: [If there is no change in the Reported property, the updateTwin shall not change the reported database and not call the OnReportedCallback.] */
        /* Codes_SRS_TWIN_21_032: [If the OnDesiredCallback is set as null, the updateDesiredProperty shall discard the map with the changed pairs.] */
        /* Codes_SRS_TWIN_21_033: [If there is no change in the Desired property, the updateDesiredProperty shall not change the database and not call the OnDesiredCallback.] */
        /* Codes_SRS_TWIN_21_037: [If the OnReportedCallback is set as null, the updateReportedProperty shall discard the map with the changed pairs.] */
        /* Codes_SRS_TWIN_21_038: [If there is no change in the Reported property, the updateReportedProperty shall not change the database and not call the OnReportedCallback.] */
        /* Codes_SRS_TWIN_21_093: [If the provided json is not valid, the updateReportedProperty shall throws IllegalArgumentException.] */
        if((diffField.size() != 0) &&(onCallback != null))
        {
            /* Codes_SRS_TWIN_21_044: [If OnDesiredCallback was provided, the updateTwin shall create a new map with a copy of all pars key values updated by the json in the Desired property, and OnDesiredCallback passing this map as parameter.] */
            /* Codes_SRS_TWIN_21_045: [If OnReportedCallback was provided, the updateTwin shall create a new map with a copy of all pars key values updated by the json in the Reported property, and OnReportedCallback passing this map as parameter.] */
            /* Codes_SRS_TWIN_21_031: [The updateDesiredProperty shall send the map with all changed pairs to the upper layer calling onDesiredCallback (TwinPropertiesChangeCallback).] */
            /* Codes_SRS_TWIN_21_036: [The updateReportedProperty shall send the map with all changed pairs to the upper layer calling onReportedCallback (TwinPropertiesChangeCallback).] */
            onCallback.execute(diffField);
        }
    }

    protected void update(String json, TwinPropertiesChangeCallback onCallback) throws IllegalArgumentException
    {
        Type stringMap = new TypeToken<Map<String, String>>(){}.getType();
        LinkedTreeMap<String, Object> newValues = new LinkedTreeMap<String, Object>();
        try {
            Gson gson = new GsonBuilder().create();
            newValues = (LinkedTreeMap<String, Object>) gson.fromJson(json, newValues.getClass());
        } catch (Exception e) {
            throw new IllegalArgumentException("Malformed Json:" + e);
        }
        update(newValues, onCallback);
    }

    private void updateVersion(LinkedTreeMap<String, Object> jsonTree) {
        for (Map.Entry<String, Object> entry : jsonTree.entrySet()) {
            if (entry.getKey().equals(versionTag)) {
                version = new Integer( (int) ((double) entry.getValue()));
                break;
            }
        }
    }

    private Map<String, String>  updateMetadata(LinkedTreeMap<String, Object> jsonTree)
    {
        Map<String, String> diff = new HashMap<>();
        for (Map.Entry<String, Object> entry : jsonTree.entrySet()) {
            if(entry.getKey().equals(metadataTag))
            {
                LinkedTreeMap<String, Object> metadataTree = (LinkedTreeMap<String, Object>)entry.getValue();
                for (LinkedTreeMap.Entry<String, Object> item : metadataTree.entrySet()) {
                    synchronized (lock) {
                        if (property.containsKey(item.getKey())) {
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
                            if (property.get(item.getKey()).metadata.update(lastUpdated, lastUpdatedVersion)) {
                                diff.put(item.getKey(), item.getValue().toString());
                            }
                        }
                    }
                }
                break;
            }
        }
        return diff;
    }

    private Map<String, String> updateFields(LinkedTreeMap<String, Object> jsonTree) throws IllegalArgumentException
    {
        Map<String, String> diff = new HashMap<>();

        for (Map.Entry<String, Object> entry : jsonTree.entrySet()) {
            if(entry.getKey().isEmpty())
            {
                throw new IllegalArgumentException("Invalid Key on Json");
            }
            if(!entry.getKey().contains("$"))
            {
                synchronized (lock){
                    /* Codes_SRS_TWIN_21_040: [The updateTwin shall not change fields that is not reported in the json string.] */
                    if (property.containsKey(entry.getKey())) {
                        if (entry.getValue() == null) {
                            /* Codes_SRS_TWIN_21_042: [If a valid key has a null value, the updateTwin shall delete this property.] */
                            property.remove(entry.getKey());
                            diff.put(entry.getKey(), null);
                        } else if (!property.get(entry.getKey()).value.equals(entry.getValue())) {
                            property.put(entry.getKey(), new Property(entry.getValue(), null));
                            diff.put(entry.getKey(), entry.getValue().toString());
                        }
                    } else if (entry.getValue() != null) {
                        property.put(entry.getKey(), new Property(entry.getValue(), null));
                        diff.put(entry.getKey(), entry.getValue().toString());
                    }
                }
            }
        }

        return diff;
    }

}
