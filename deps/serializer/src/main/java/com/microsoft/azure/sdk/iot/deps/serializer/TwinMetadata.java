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
 * INNER TWIN CLASS
 *
 * Twin metadata representation
 *
 */
public class TwinMetadata {

    private static final String DATEFORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSS'Z'";
    private static final String TIMEZONE = "UTC";

    @SerializedName("$lastUpdated")
    private String lastUpdated;

    @SerializedName("$lastUpdatedVersion")
    private Integer lastUpdatedVersion;

    protected TwinMetadata()
    {
        update();
        lastUpdatedVersion = null;
    }

    protected TwinMetadata(Integer version)
    {
        update();
        lastUpdatedVersion = version;
    }

    protected TwinMetadata(Integer version, String dateTime)
    {
        lastUpdated = dateTime;
        lastUpdatedVersion = version;
    }

    protected boolean update(String dateTime, Integer version)
    {
        boolean updated;

        if (!lastUpdated.equals(dateTime)) {
            updated = true;
        } else if(lastUpdatedVersion == null) {
            if(version == null) {
                updated = false;
            } else {
                updated = true;
            }
        } else if(lastUpdatedVersion.equals(version)) {
            updated = false;
        } else {
            updated = true;
        }

        lastUpdated = dateTime;
        lastUpdatedVersion = version;

        return updated;
    }

    protected void update(int version)
    {
        update();
        lastUpdatedVersion = version;
    }

    protected void update()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATEFORMAT);
        dateFormat.setTimeZone(TimeZone.getTimeZone(TIMEZONE));
        lastUpdated = dateFormat.format(new Date());
    }

    protected Integer GetLastUpdateVersion() { return lastUpdatedVersion; }
    protected String GetLastUpdate() { return lastUpdated; }
}
