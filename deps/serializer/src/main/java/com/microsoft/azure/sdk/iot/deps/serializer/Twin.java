// Copyright (c) Microsoft. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package com.microsoft.azure.sdk.iot.deps.serializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.HashMap;

/**
 * Twin Representation
 */
public class Twin
{

    private static final Gson gson = new GsonBuilder().create();
    private static TwinPropertiesChangeCallback onDesiredCallback = null;
    private static TwinPropertiesChangeCallback onReportedCallback = null;

    private TwinTags tags = null;
    private TwinProperties properties = new TwinProperties();

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
        properties.Desired.enableMetadata();
        properties.Reported.enableMetadata();
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
        JsonElement updatedElements = properties.Desired.update(property);

        if(updatedElements == null)
        {
            /* Codes_SRS_TWIN_21_023: [If the provided `property` map is null, the updateDesiredProperty shall return null.] */
            /* Codes_SRS_TWIN_21_024: [If no Desired property changed its value, the updateDesiredProperty shall return null.] */
            json = null;
        }
        else
        {
            /* Codes_SRS_TWIN_21_022: [The updateDesiredProperty shall return a string with json representing the new desired properties with changes.] */
            JsonObject twinJson = new JsonObject();
            twinJson.add("desired", updatedElements);

            json = twinJson.toString();
        }

        return json;
    }

    public String updateReportedProperty(HashMap<String, Object> property)
    {
        String json;
        /* Codes_SRS_TWIN_21_025: [The updateReportedProperty shall add all provided properties to the Reported property.] */
        JsonElement updatedElements = properties.Reported.update(property);

        if(updatedElements == null)
        {
            /* Codes_SRS_TWIN_21_027: [If the provided `property` map is null, the updateReportedProperty shall return null.] */
            /* Codes_SRS_TWIN_21_028: [If no Reported property changed its value, the updateReportedProperty shall return null.] */
            json = null;
        }
        else
        {
            /* Codes_SRS_TWIN_21_026: [The updateReportedProperty shall return a string with json representing the new Reported properties with changes.] */
            JsonObject twinJson = new JsonObject();
            twinJson.add("reported", updatedElements);

            json = twinJson.toString();
        }

        return json;
    }


    public void updateTwin(String json)
    {
        Twin newValues = gson.fromJson(json, Twin.class);
        copyAndReport(newValues);
    }

    public void updateDesiredProperty(String json)
    {
        Twin newValues = new Twin();
        newValues.properties.Desired.fromJson(json);
        copyAndReport(newValues);
    }

    public void updateReportedProperty(String json)
    {
        Twin newValues = new Twin();
        newValues.properties.Reported.fromJson(json);
        copyAndReport(newValues);
    }

    public Integer getDesiredPropertyVersion()
    {
        return properties.Desired.GetVersion();
    }

    public Integer getReportedPropertyVersion()
    {
        return properties.Reported.GetVersion();
    }

    private void copyAndReport(Twin newValues)
    {
        // TODO: Copy the new properties values to this instance.
        // TODO: fire callback if any changes in the properties.
    }

    protected static void reporteDesiredChanged(TwinProperty property)
    {
        if(onDesiredCallback != null){
            onDesiredCallback.execute(property.GetPropertyMap());
        }
    }

    protected static void reporteReportedChanged(TwinProperty property)
    {
        if(onReportedCallback != null){
            onReportedCallback.execute(property.GetPropertyMap());
        }
    }
}
