// Copyright (c) Microsoft. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package com.microsoft.azure.sdk.iot.deps.serializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Twin metadata representation
 */
public class TwinMetadata {

    private static final Gson gson = new GsonBuilder().create();
    private static final String DATEFORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSS'Z'";
    private static final String TIMEZONE = "UTC";

    @SerializedName("$lastUpdated")
    protected String lastUpdated;

    @SerializedName("$lastUpdatedVersion")
    protected Integer lastUpdatedVersion;

    public TwinMetadata()
    {
        update();
        lastUpdatedVersion = 0;
    }

<<<<<<< HEAD
    public TwinMetadata(int version)
    {
        update();
        lastUpdatedVersion = version;
    }

=======
>>>>>>> addf8a754e926e0d488a2aea9316163f84e12f04
    public void update(int version)
    {
        update();
        lastUpdatedVersion = version;
    }

    public void update()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATEFORMAT);
        dateFormat.setTimeZone(TimeZone.getTimeZone(TIMEZONE));
        lastUpdated = dateFormat.format(new Date());
    }

    public Integer GetLastUpdateVersion() { return lastUpdatedVersion; }
    public String GetLastUpdate() { return lastUpdated; }
}
