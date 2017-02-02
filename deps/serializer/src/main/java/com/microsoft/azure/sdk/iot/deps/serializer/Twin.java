// Copyright (c) Microsoft. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package com.microsoft.azure.sdk.iot.deps.serializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;

/**
 * Twin Representation
 */
public class Twin
{
    private static final Gson gson = new GsonBuilder().create();

    public TwinTags Tags;
    public TwinProperties Properties;

    public Twin(int desiredVersion, int reportedVersion)
    {
        Tags = new TwinTags();
        Properties = new TwinProperties(desiredVersion, reportedVersion);
    }

    public String toJson()
    {
        return gson.toJson(this);
    }

    public void fromJson(String json)
    {
        Twin newValues = gson.fromJson(json, Twin.class);
        copy(newValues);
    }

    private void copy(Twin newValues)
    {

    }
}
