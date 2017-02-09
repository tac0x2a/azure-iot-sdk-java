// Copyright (c) Microsoft. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package com.microsoft.azure.sdk.iot.deps.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;

import java.util.HashMap;
import java.util.Map;

/**
 * Twin Properties representation
 *
 * This class is part of the Twin. It is necessary to generate the properties json.
 */
public class TwinProperties
{
    /* Codes_SRS_TWIN_PROPERTIES_21_001: [The Desired shall store an instance of the TwinProperty for the Twin `Desired` properties.] */
    public TwinProperty Desired = new TwinProperty();

    /* Codes_SRS_TWIN_PROPERTIES_21_002: [The Reported shall store an instance of the TwinProperty for the Twin `Reported` properties.]*/
    public TwinProperty Reported = new TwinProperty();

    public void enableDesiredMetadata() { Desired.enableMetadata(); }
    public void enableReportedMetadata() { Desired.enableMetadata(); }

    public JsonElement updateDesired(HashMap<String, Object> property) { return Desired.update(property); }
    public JsonElement updateReported(HashMap<String, Object> property) { return Reported.update(property); }

    public HashMap<String, String> fromJsonDesired(String json) { return Desired.fromJson(json); }
    public HashMap<String, String> fromJsonReported(String json) { return Reported.fromJson(json); }

    public Integer getDesiredVersion() { return Desired.GetVersion(); }
    public Integer getReportedVersion() { return Reported.GetVersion(); }

    public HashMap<String, String> getDesiredPropertyMap() { return Desired.GetPropertyMap(); }
    public HashMap<String, String> getReportedPropertyMap() { return Reported.GetPropertyMap(); }

    public String toJson()
    {
        return toJsonElement().toString();
    }

    public JsonElement toJsonElement()
    {
        JsonElement desired = Desired.toJsonElement();
        JsonElement reported = Reported.toJsonElement();

        JsonObject propertiesJson = new JsonObject();
        propertiesJson.add("desired", desired);
        propertiesJson.add("reported", reported);

        return (JsonElement) propertiesJson;
    }

    public void fromJson(LinkedTreeMap<String, Object> jsonTree)
    {
        for(Map.Entry<String, Object> entry : jsonTree.entrySet())
        {
            if(entry.getKey().equals("desired"))
            {
                Desired.fromJson((LinkedTreeMap<String, Object>) entry.getValue());
            }
            else if(entry.getKey().equals("reported"))
            {
                Reported.fromJson((LinkedTreeMap<String, Object>) entry.getValue());
            }
        }

    }
}
