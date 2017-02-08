// Copyright (c) Microsoft. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package tests.unit.com.microsoft.azure.sdk.iot.deps.serializer;


import com.google.gson.JsonElement;
import com.microsoft.azure.sdk.iot.deps.serializer.Twin;
import com.microsoft.azure.sdk.iot.deps.serializer.TwinPropertiesChangeCallback;
import com.microsoft.azure.sdk.iot.deps.serializer.TwinProperty;
import org.junit.Test;

import java.util.HashMap;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;


/**
 * Unit tests for Twin serializer
 */
public class TwinTest {

    protected static class OnDesiredCallback implements TwinPropertiesChangeCallback
    {
        public void execute(HashMap<String , String> propertyMap)
        {

        }
    }

    protected static class OnReportedCallback implements TwinPropertiesChangeCallback
    {
        public void execute(HashMap<String , String> propertyMap)
        {

        }
    }

    /* Codes_SRS_TWIN_21_001: [The constructor shall create an instance of the properties.] */
    /* Codes_SRS_TWIN_21_002: [The constructor shall set OnDesiredCallback as null.] */
    /* Codes_SRS_TWIN_21_003: [The constructor shall set OnReportedCallback as null.] */
    /* Codes_SRS_TWIN_21_004: [The constructor shall set Tags as null.] */
    @Test
    public void constructor_succeed()
    {
        // Arrange
        // Act
        Twin twin = new Twin();

        // Assert
        assertNotNull(twin);
    }

    /* Codes_SRS_TWIN_21_005: [The constructor shall call the standard constructor.] */
    /* Codes_SRS_TWIN_21_007: [The constructor shall set OnReportedCallback as null.] */
    /* Codes_SRS_TWIN_21_008: [The constructor shall set Tags as null.] */
    /* Codes_SRS_TWIN_21_006: [The constructor shall set OnDesiredCallback with the provided Callback function.] */
    @Test
    public void constructor_OnDesiredCallback_succeed()
    {
        // Arrange
        OnDesiredCallback onDesiredCallback = new OnDesiredCallback();

        // Act
        Twin twin = new Twin(onDesiredCallback);

        // TODO: Include callback test

        // Assert
        assertNotNull(twin);
    }

    /* Codes_SRS_TWIN_21_009: [The constructor shall call the standard constructor.] */
    /* Codes_SRS_TWIN_21_012: [The constructor shall set Tags as null.] */
    /* Codes_SRS_TWIN_21_010: [The constructor shall set OnDesiredCallback with the provided Callback function.] */
    /* Codes_SRS_TWIN_21_011: [The constructor shall set OnReportedCallback with the provided Callback function.] */
    @Test
    public void constructor_OnDesiredAndReportedCallback_succeed()
    {
        // Arrange
        OnDesiredCallback onDesiredCallback = new OnDesiredCallback();
        OnReportedCallback onReportedCallback = new OnReportedCallback();

        // Act
        Twin twin = new Twin(onDesiredCallback, onReportedCallback);

        // TODO: Include callback test

        // Assert
        assertNotNull(twin);
    }

    /* Codes_SRS_TWIN_21_013: [The setDesiredCallback shall set OnDesiredCallback with the provided Callback function.] */
    @Test
    public void setDesiredCallback_succeed()
    {
        // Arrange
        OnDesiredCallback onDesiredCallback = new OnDesiredCallback();
        Twin twin = new Twin();

        // Act
        twin.setDesiredCallback(onDesiredCallback);

        // TODO: Include callback test

        // Assert
    }

    /* Codes_SRS_TWIN_21_014: [The setReportedCallback shall set OnReportedCallback with the provided Callback function.] */
    @Test
    public void setReportedCallback_succeed()
    {
        // Arrange
        OnReportedCallback onReportedCallback = new OnReportedCallback();
        Twin twin = new Twin();

        // Act
        twin.setReportedCallback(onReportedCallback);

        // TODO: Include callback test

        // Assert
    }

    /* Codes_SRS_TWIN_21_015: [The toJson shall create a String with information in the Twin using json format.] */
    /* Codes_SRS_TWIN_21_016: [The toJson shall not include null fields.] */
    @Test
    public void toJson_emptyClass_succeed()
    {
        // Arrange
        Twin twin = new Twin();

        // Act
        String json = twin.toJson();

        // Assert
        assertThat(json, is("{\"properties\":{\"desired\":{},\"reported\":{}}}"));
    }

    /* Codes_SRS_TWIN_21_017: [The toJsonElement shall return a JsonElement with information in the Twin using json format.] */
    /* Codes_SRS_TWIN_21_018: [The toJson shall not include null fields.] */
    @Test
    public void toJsonElement_emptyClass_succeed()
    {
        // Arrange
        Twin twin = new Twin();

        // Act
        JsonElement jsonElement = twin.toJsonElement();

        // Assert
        assertThat(jsonElement.toString(), is("{\"properties\":{\"desired\":{},\"reported\":{}}}"));
    }

    /* Codes_SRS_TWIN_21_019: [The enableTags shall create an instance of the Tags class.] */
    @Test
    public void toJson_emptyClass_withTags_succeed()
    {
        // Arrange
        Twin twin = new Twin();

        // Act
        twin.enableTags();

        // Assert
        String json = twin.toJson();
        assertThat(json, is("{\"tags\":{},\"properties\":{\"desired\":{},\"reported\":{}}}"));
    }

    /* Codes_SRS_TWIN_21_020: [The enableMetadata shall create an instance of the Metadata fro the Desired and for the Reported Properties.] */
    @Test
    public void toJson_emptyClass_withMetadata_succeed()
    {
        // Arrange
        Twin twin = new Twin();

        // Act
        twin.enableMetadata();

        // Assert
        String json = twin.toJson();
        assertThat(json, is("{\"properties\":{\"desired\":{\"$version\":0,\"$metadata\":{}},\"reported\":{\"$version\":0,\"$metadata\":{}}}}"));
    }

    /* Codes_SRS_TWIN_21_021: [The updateDesiredProperty shall add all provided properties to the Desired property.] */
    @Test
    public void updateDesiredProperty_succeed()
    {
        // Arrange
        Twin twin = new Twin();
        HashMap<String, Object> newValues = new HashMap<>();
        newValues.put("key1", "value1");
        newValues.put("key2", "value2");
        newValues.put("key3", "value3");

        // Act
        String json = twin.updateDesiredProperty(newValues);

        // Assert
        assertThat(json, is("{\"desired\":{\"key1\":\"value1\",\"key2\":\"value2\",\"key3\":\"value3\"}}"));
    }

    /* Codes_SRS_TWIN_21_021: [The updateDesiredProperty shall add all provided properties to the Desired property.] */
    @Test
    public void updateDesiredProperty_withMetadata_succeed()
    {
        // Arrange
        Twin twin = new Twin();
        twin.enableMetadata();
        HashMap<String, Object> newValues = new HashMap<>();
        newValues.put("key1", "value1");
        newValues.put("key2", "value2");
        newValues.put("key3", "value3");

        // Act
        String json = twin.updateDesiredProperty(newValues);

        // Assert
        assertNotNull(json);
        // TODO: Add test using fromJson()
    }

    /* Codes_SRS_TWIN_21_022: [The updateDesiredProperty shall return a string with json representing the new desired properties with changes.] */
    @Test
    public void updateDesiredProperty_metadataChanges_succeed()
    {
        // Arrange
        Twin twin = new Twin();
        twin.enableMetadata();
        HashMap<String, Object> newValues = new HashMap<>();
        newValues.put("key1", "value1");
        newValues.put("key2", "value2");
        newValues.put("key3", "value3");
        twin.updateDesiredProperty(newValues);

        // Act
        String json = twin.updateDesiredProperty(newValues);

        // Assert
        assertNotNull(json);
        // TODO: Add test using fromJson()
    }

    /* Codes_SRS_TWIN_21_022: [The updateDesiredProperty shall return a string with json representing the new desired properties with changes.] */
    @Test
    public void updateDesiredProperty_newKey_succeed()
    {
        // Arrange
        Twin twin = new Twin();
        HashMap<String, Object> newValues = new HashMap<>();
        newValues.put("key1", "value1");
        newValues.put("key2", "value2");
        newValues.put("key3", "value3");
        twin.updateDesiredProperty(newValues);

        newValues.clear();
        newValues.put("key4", "value4");

        // Act
        String json = twin.updateDesiredProperty(newValues);

        // Assert
        assertThat(json, is("{\"desired\":{\"key4\":\"value4\"}}"));
    }

    /* Codes_SRS_TWIN_21_022: [The updateDesiredProperty shall return a string with json representing the new desired properties with changes.] */
    @Test
    public void updateDesiredProperty_newValue_succeed()
    {
        // Arrange
        Twin twin = new Twin();
        HashMap<String, Object> newValues = new HashMap<>();
        newValues.put("key1", "value1");
        newValues.put("key2", "value2");
        newValues.put("key3", "value3");
        twin.updateDesiredProperty(newValues);

        newValues.clear();
        newValues.put("key1", "value4");

        // Act
        String json = twin.updateDesiredProperty(newValues);

        // Assert
        assertThat(json, is("{\"desired\":{\"key1\":\"value4\"}}"));
    }

    /* Codes_SRS_TWIN_21_022: [The updateDesiredProperty shall return a string with json representing the new desired properties with changes.] */
    @Test
    public void updateDesiredProperty_newAndOld_succeed()
    {
        // Arrange
        Twin twin = new Twin();
        HashMap<String, Object> newValues = new HashMap<>();
        newValues.put("key1", "value1");
        newValues.put("key2", "value2");
        newValues.put("key3", "value3");
        twin.updateDesiredProperty(newValues);

        newValues.clear();
        newValues.put("key1", "value4");
        newValues.put("key2", "value2");
        newValues.put("key5", "value5");

        // Act
        String json = twin.updateDesiredProperty(newValues);

        // Assert
        assertThat(json, is("{\"desired\":{\"key1\":\"value4\",\"key5\":\"value5\"}}"));
    }

    /* Codes_SRS_TWIN_21_022: [The updateDesiredProperty shall return a string with json representing the new desired properties with changes.] */
    @Test
    public void updateDesiredProperty_mixDesiredAndReported_succeed()
    {
        // Arrange
        Twin twin = new Twin();
        HashMap<String, Object> newValues = new HashMap<>();
        newValues.put("key1", "value1");
        newValues.put("key2", "value2");
        newValues.put("key3", "value3");
        twin.updateReportedProperty(newValues);
        newValues.clear();
        newValues.put("key1", "value1");
        newValues.put("key6", "value6");
        newValues.put("key7", "value7");
        twin.updateDesiredProperty(newValues);
        newValues.clear();
        newValues.put("key1", "value4");
        newValues.put("key6", "value6");
        newValues.put("key5", "value5");

        // Act
        String json = twin.updateDesiredProperty(newValues);

        // Assert
        assertThat(json, is("{\"desired\":{\"key1\":\"value4\",\"key5\":\"value5\"}}"));
    }

    /* Codes_SRS_TWIN_21_023: [If the provided `property` map is null, the updateDesiredProperty shall return null.] */
    /* Codes_SRS_TWIN_21_024: [If no Desired property changed its value, the updateDesiredProperty shall return null.] */
    @Test
    public void updateDesiredProperty_emptyMap_succeed()
    {
        // Arrange
        Twin twin = new Twin();
        HashMap<String, Object> newValues = new HashMap<>();

        // Act
        String json = twin.updateDesiredProperty(newValues);

        // Assert
        assertNull(json);
    }

    /* Codes_SRS_TWIN_21_024: [If no Desired property changed its value, the updateDesiredProperty shall return null.] */
    @Test
    public void updateDesiredProperty_noChanges_succeed()
    {
        // Arrange
        Twin twin = new Twin();
        HashMap<String, Object> newValues = new HashMap<>();
        newValues.put("key1", "value1");
        newValues.put("key2", "value2");
        newValues.put("key3", "value3");
        twin.updateDesiredProperty(newValues);

        // Act
        String json = twin.updateDesiredProperty(newValues);

        // Assert
        assertNull(json);
    }

    /* Codes_SRS_TWIN_21_025: [The updateReportedProperty shall add all provided properties to the Reported property.] */
    @Test
    public void updateReportedProperty_succeed()
    {
        // Arrange
        Twin twin = new Twin();
        HashMap<String, Object> newValues = new HashMap<>();
        newValues.put("key1", "value1");
        newValues.put("key2", "value2");
        newValues.put("key3", "value3");

        // Act
        String json = twin.updateReportedProperty(newValues);

        // Assert
        assertThat(json, is("{\"reported\":{\"key1\":\"value1\",\"key2\":\"value2\",\"key3\":\"value3\"}}"));
    }

    /* Codes_SRS_TWIN_21_026: [The updateReportedProperty shall return a string with json representing the new Reported properties with changes.] */
    @Test
    public void updateReportedProperty_newAndOld_succeed()
    {
        // Arrange
        Twin twin = new Twin();
        HashMap<String, Object> newValues = new HashMap<>();
        newValues.put("key1", "value1");
        newValues.put("key2", "value2");
        newValues.put("key3", "value3");
        twin.updateReportedProperty(newValues);

        newValues.clear();
        newValues.put("key1", "value4");
        newValues.put("key2", "value2");
        newValues.put("key5", "value5");

        // Act
        String json = twin.updateReportedProperty(newValues);

        // Assert
        assertThat(json, is("{\"reported\":{\"key1\":\"value4\",\"key5\":\"value5\"}}"));
    }

    /* Codes_SRS_TWIN_21_026: [The updateReportedProperty shall return a string with json representing the new Reported properties with changes.] */
    @Test
    public void updateReportedProperty_mixDesiredAndReported_succeed()
    {
        // Arrange
        Twin twin = new Twin();
        HashMap<String, Object> newValues = new HashMap<>();
        newValues.put("key1", "value1");
        newValues.put("key2", "value2");
        newValues.put("key3", "value3");
        twin.updateReportedProperty(newValues);
        newValues.clear();
        newValues.put("key1", "value4");
        newValues.put("key6", "value6");
        newValues.put("key7", "value7");
        twin.updateDesiredProperty(newValues);
        newValues.clear();
        newValues.put("key1", "value4");
        newValues.put("key2", "value2");
        newValues.put("key5", "value5");

        // Act
        String json = twin.updateReportedProperty(newValues);

        // Assert
        assertThat(json, is("{\"reported\":{\"key1\":\"value4\",\"key5\":\"value5\"}}"));
    }

    /* Codes_SRS_TWIN_21_027: [If the provided `property` map is null, the updateReportedProperty shall return null.] */
    /* Codes_SRS_TWIN_21_028: [If no Reported property changed its value, the updateReportedProperty shall return null.] */
    @Test
    public void updateReportedProperty_emptyMap_succeed()
    {
        // Arrange
        Twin twin = new Twin();
        HashMap<String, Object> newValues = new HashMap<>();

        // Act
        String json = twin.updateReportedProperty(newValues);

        // Assert
        assertNull(json);
    }

}
