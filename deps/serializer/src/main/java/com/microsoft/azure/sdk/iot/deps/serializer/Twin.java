// Copyright (c) Microsoft. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package com.microsoft.azure.sdk.iot.deps.serializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;

import java.util.HashMap;
import java.util.Map;

/**
 * Twin Representation
 */
public class Twin
{

    private static final Gson gson = new GsonBuilder().create();
    private static TwinPropertiesChangeCallback onDesiredCallback = null;
    private static TwinPropertiesChangeCallback onReportedCallback = null;

    private TwinTags tags = null;
    protected TwinProperties properties = new TwinProperties();

    public Twin()
    {
        /* Codes_SRS_TWIN_21_001: [The constructor shall create an instance of the properties.] */
        /* Codes_SRS_TWIN_21_002: [The constructor shall set OnDesiredCallback as null.] */
        /* Codes_SRS_TWIN_21_003: [The constructor shall set OnReportedCallback as null.] */
        /* Codes_SRS_TWIN_21_004: [The constructor shall set Tags as null.] */
    }

    public Twin(TwinPropertiesChangeCallback onDesiredCallback)
    {
        /* Codes_SRS_TWIN_21_005: [The constructor shall call the standard constructor.] */
        /* Codes_SRS_TWIN_21_007: [The constructor shall set OnReportedCallback as null.] */
        /* Codes_SRS_TWIN_21_008: [The constructor shall set Tags as null.] */
        this();

        /* Codes_SRS_TWIN_21_006: [The constructor shall set OnDesiredCallback with the provided Callback function.] */
        setDesiredCallback(onDesiredCallback);
    }

    public Twin(TwinPropertiesChangeCallback onDesiredCallback, TwinPropertiesChangeCallback onReportedCallback)
    {
        /* Codes_SRS_TWIN_21_009: [The constructor shall call the standard constructor.] */
        /* Codes_SRS_TWIN_21_012: [The constructor shall set Tags as null.] */
        this();

        /* Codes_SRS_TWIN_21_010: [The constructor shall set OnDesiredCallback with the provided Callback function.] */
        /* Codes_SRS_TWIN_21_011: [The constructor shall set OnReportedCallback with the provided Callback function.] */
        setDesiredCallback(onDesiredCallback);
        setReportedCallback(onReportedCallback);
    }

    public void setDesiredCallback(TwinPropertiesChangeCallback callback)
    {

        /* Codes_SRS_TWIN_21_013: [The setDesiredCallback shall set OnDesiredCallback with the provided Callback function.] */
        onDesiredCallback = callback;
    }

    public void setReportedCallback(TwinPropertiesChangeCallback callback)
    {
        /* Codes_SRS_TWIN_21_014: [The setReportedCallback shall set OnReportedCallback with the provided Callback function.] */
        onReportedCallback = callback;
    }

    public void enableTags()
    {
        /* Codes_SRS_TWIN_21_019: [The enableTags shall create an instance of the Tags class.] */
        tags = new TwinTags();
    }

    public void enableMetadata()
    {
        /* Codes_SRS_TWIN_21_020: [The enableMetadata shall create an instance of the Metadata fro the Desired and for the Reported Properties.] */
        properties.enableDesiredMetadata();
        properties.enableReportedMetadata();
    }

    public String toJson()
    {
        /* Codes_SRS_TWIN_21_015: [The toJson shall create a String with information in the Twin using json format.] */
        /* Codes_SRS_TWIN_21_016: [The toJson shall not include null fields.] */
        return toJsonElement().toString();
    }

    public JsonElement toJsonElement()
    {
        /* Codes_SRS_TWIN_21_017: [The toJsonElement shall return a JsonElement with information in the Twin using json format.] */
        JsonObject twinJson = new JsonObject();

        /* Codes_SRS_TWIN_21_018: [The toJson shall not include null fields.] */
        if(tags != null) {
            twinJson.add("tags", tags.toJsonElement());
        }

        twinJson.add("properties", properties.toJsonElement());

        return (JsonElement) twinJson;
    }

    public String updateDesiredProperty(HashMap<String, Object> property)
    {
        String json;
        /* Codes_SRS_TWIN_21_021: [The updateDesiredProperty shall add all provided properties to the Desired property.] */
        JsonElement updatedElements = properties.updateDesired(property);

        if(updatedElements == null)
        {
            /* Codes_SRS_TWIN_21_023: [If the provided `property` map is null, the updateDesiredProperty shall return null.] */
            /* Codes_SRS_TWIN_21_024: [If no Desired property changed its value, the updateDesiredProperty shall return null.] */
            json = null;
        }
        else
        {
            /* Codes_SRS_TWIN_21_022: [The updateDesiredProperty shall return a string with json representing the new desired properties with changes.] */
            json = updatedElements.toString();
        }

        return json;
    }

    public String updateReportedProperty(HashMap<String, Object> property)
    {
        String json;
        /* Codes_SRS_TWIN_21_025: [The updateReportedProperty shall add all provided properties to the Reported property.] */
        JsonElement updatedElements = properties.updateReported(property);

        if(updatedElements == null)
        {
            /* Codes_SRS_TWIN_21_027: [If the provided `property` map is null, the updateReportedProperty shall return null.] */
            /* Codes_SRS_TWIN_21_028: [If no Reported property changed its value, the updateReportedProperty shall return null.] */
            json = null;
        }
        else
        {
            /* Codes_SRS_TWIN_21_026: [The updateReportedProperty shall return a string with json representing the new Reported properties with changes.] */
            json = updatedElements.toString();
        }

        return json;
    }


    public void updateTwin(String json)
    {
        Map<String, Object> jsonTree = new HashMap<String, Object>();
        jsonTree = (Map<String, Object>) gson.fromJson(json, jsonTree.getClass());

        for(Map.Entry<String, Object> entry : jsonTree.entrySet())
        {
            if(entry.getKey().equals("properties"))
            {
                properties.update((LinkedTreeMap<String, Object>) entry.getValue(), onDesiredCallback, onReportedCallback);
            }
            else if(entry.getKey().equals("tags"))
            {
                tags.fromJson((LinkedTreeMap<String, Object>) entry.getValue());
            }
        }
    }

    public void updateDesiredProperty(String json)
    {
        /* Codes_SRS_TWIN_21_029: [The updateDesiredProperty shall update the Desired property using the information provided in the json.] */
        /* Codes_SRS_TWIN_21_030: [The updateDesiredProperty shall generate a map with all pairs key value that had its content changed.] */
        /* Codes_SRS_TWIN_21_031: [The updateDesiredProperty shall send the map with all changed pairs to the upper layer calling onDesiredCallback (TwinPropertiesChangeCallback).] */
        /* Codes_SRS_TWIN_21_032: [If the OnDesiredCallback is set as null, the updateDesiredProperty shall discard the map with the changed pairs.] */
        /* Codes_SRS_TWIN_21_033: [If there is no change in the Desired property, the updateDesiredProperty shall not call the OnDesiredCallback.] */
        properties.updateDesired(json, onDesiredCallback);
    }

    public void updateReportedProperty(String json)
    {
        /* Codes_SRS_TWIN_21_034: [The updateReportedProperty shall update the Reported property using the information provided in the json.] */
        /* Codes_SRS_TWIN_21_035: [The updateReportedProperty shall generate a map with all pairs key value that had its content changed.] */
        /* Codes_SRS_TWIN_21_036: [The updateReportedProperty shall send the map with all changed pairs to the upper layer calling onReportedCallback (TwinPropertiesChangeCallback).] */
        /* Codes_SRS_TWIN_21_037: [If the OnReportedCallback is set as null, the updateReportedProperty shall discard the map with the changed pairs.] */
        /* Codes_SRS_TWIN_21_038: [If there is no change in the Reported property, the updateReportedProperty shall not call the OnReportedCallback.] */
        properties.updateReported(json, onReportedCallback);
    }

    public Integer getDesiredPropertyVersion()
    {
        return properties.getDesiredVersion();
    }

    public Integer getReportedPropertyVersion()
    {
        return properties.getReportedVersion();
    }

    public HashMap<String, String> getDesiredPropertyMap() {
        return properties.getDesiredPropertyMap();
    }

    public HashMap<String, String> getReportedPropertyMap() {
        return properties.getReportedPropertyMap();
    }
}
