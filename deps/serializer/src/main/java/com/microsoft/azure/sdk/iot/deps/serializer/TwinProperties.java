// Copyright (c) Microsoft. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package com.microsoft.azure.sdk.iot.deps.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;

import java.util.HashMap;
import java.util.Map;

/**
 * INNER TWIN CLASS
 *
 * Twin Properties representation
 *
 * This class is part of the Twin. It is necessary to generate the properties json.
 */
public class TwinProperties
{
    private TwinProperty Desired = new TwinProperty();
    private TwinProperty Reported = new TwinProperty();

    protected void enableDesiredMetadata() { Desired.enableMetadata(); }
    protected void enableReportedMetadata() { Reported.enableMetadata(); }

    protected JsonElement updateDesired(Map<String, Object> property)
    {
        /* Codes_SRS_TWIN_21_021: [The updateDesiredProperty shall add all provided properties to the Desired property.] */
        return Desired.update(property);
    }
    protected JsonElement updateReported(Map<String, Object> property)
    {
        /* Codes_SRS_TWIN_21_025: [The updateReportedProperty shall add all provided properties to the Reported property.] */
        return Reported.update(property);
    }

    protected void updateDesired(String json, TwinPropertiesChangeCallback onDesiredCallback) { Desired.update(json, onDesiredCallback); }
    protected void updateReported(String json, TwinPropertiesChangeCallback onDesiredCallback) { Reported.update(json, onDesiredCallback); }

    protected Integer getDesiredVersion() { return Desired.GetVersion(); }
    protected Integer getReportedVersion() { return Reported.GetVersion(); }

    protected Map<String, String> getDesiredPropertyMap() { return Desired.GetPropertyMap(); }
    protected Map<String, String> getReportedPropertyMap() { return Reported.GetPropertyMap(); }

    protected String toJson()
    {
        return toJsonElement().toString();
    }

    protected JsonElement toJsonElement()
    {
        /* Codes_SRS_TWIN_21_017: [The toJsonElement shall return a JsonElement with information in the Twin using json format.] */
        JsonObject propertiesJson = new JsonObject();

        /* Codes_SRS_TWIN_21_087: [**The toJsonElement shall include the `desired` property in the json even if it has no content.] */
        JsonElement desired = Desired.toJsonElement();
        propertiesJson.add("desired", desired);

        /* Codes_SRS_TWIN_21_088: [**The toJsonElement shall include the `reported` property in the json even if it has no content.] */
        JsonElement reported = Reported.toJsonElement();
        propertiesJson.add("reported", reported);

        return (JsonElement) propertiesJson;
    }

    protected void update(Map<String, Object> jsonTree,
                       TwinPropertiesChangeCallback onDesiredCallback, TwinPropertiesChangeCallback onReportedCallback)
            throws IllegalArgumentException
    {
        for(Map.Entry<String, Object> entry : jsonTree.entrySet())
        {
            if(entry.getKey().equals("desired"))
            {
                Desired.update((LinkedTreeMap<String, Object>) entry.getValue(), onDesiredCallback);
            }
            else if(entry.getKey().equals("reported"))
            {
                Reported.update((LinkedTreeMap<String, Object>) entry.getValue(), onReportedCallback);
            }
            else
            {
                throw new IllegalArgumentException("Invalid Property");
            }
        }

    }
}
