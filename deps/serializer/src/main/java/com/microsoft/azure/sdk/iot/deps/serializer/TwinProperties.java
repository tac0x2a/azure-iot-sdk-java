// Copyright (c) Microsoft. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package com.microsoft.azure.sdk.iot.deps.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

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
}