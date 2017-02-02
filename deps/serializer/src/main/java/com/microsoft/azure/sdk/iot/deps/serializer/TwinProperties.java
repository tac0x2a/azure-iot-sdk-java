// Copyright (c) Microsoft. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package com.microsoft.azure.sdk.iot.deps.serializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Twin represents representation
 */
public class TwinProperties
{
    private static final Gson gson = new GsonBuilder().create();

    public TwinProperty Desired;
    public TwinProperty Reported;

    public TwinProperties(int desiredVersion, int reportedVersion)
    {
        Desired = new TwinProperty(desiredVersion);
        Reported = new TwinProperty(reportedVersion);
    }

    public String toJson()
    {
        return gson.toJson(this);
    }

    public void fromJson(String json)
    {
        TwinProperties newValues = gson.fromJson(json, TwinProperties.class);
        copy(newValues);
    }

    private void copy(TwinProperties newValues)
    {

    }
}
