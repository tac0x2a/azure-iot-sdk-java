// Copyright (c) Microsoft. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package tests.unit.com.microsoft.azure.sdk.iot.deps.serializer;


import com.google.gson.JsonElement;
import com.microsoft.azure.sdk.iot.deps.serializer.*;
import mockit.Deencapsulation;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;


/**
 * Unit tests for Twin serializer
 */
public class TwinTest {

    private static final String DATEFORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSS'Z'";
    private static final String TIMEZONE = "UTC";

    protected static class OnDesiredCallback implements TwinPropertiesChangeCallback
    {
        private HashMap<String, String> diff = null;
        public void execute(HashMap<String , String> propertyMap)
        {
            diff = propertyMap;
        }
    }

    protected static class OnReportedCallback implements TwinPropertiesChangeCallback
    {
        private HashMap<String, String> diff = null;
        public void execute(HashMap<String , String> propertyMap)
        {
            diff = propertyMap;
        }
    }

    private void assetWithError(String dt1Str, String dt2Str)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATEFORMAT);
        dateFormat.setTimeZone(TimeZone.getTimeZone(TIMEZONE));
        Date dt1 = null;
        Date dt2 = null;

        try {
            dt1 = dateFormat.parse(dt1Str);
            dt2 = dateFormat.parse(dt2Str);
        }
        catch (ParseException e) {
            assert(true);
        }

        long error = Math.abs(dt1.getTime()-dt2.getTime());

        assertThat(error, lessThanOrEqualTo(100L));
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

    /* Codes_SRS_TWIN_21_020: [The enableMetadata shall enable report metadata in Json for the Desired and for the Reported Properties.] */
    @Test
    public void toJson_emptyClass_withMetadata_succeed()
    {
        // Arrange
        Twin twin = new Twin();

        // Act
        twin.enableMetadata();

        // Assert
        String json = twin.toJson();
        assertThat(json, is("{\"properties\":{\"desired\":{\"$metadata\":{}},\"reported\":{\"$metadata\":{}}}}"));
    }

    /* Codes_SRS_TWIN_21_021: [The updateDesiredProperty shall add all provided properties to the Desired property.] */
    /* Codes_SRS_TWIN_21_050: [The getDesiredPropertyMap shall return a map with all desired property key value pairs.] */
    @Test
    public void updateDesiredProperty_succeed()
    {
        // Arrange
        Twin twin = new Twin();
        HashMap<String, Object> newValues = new HashMap<>();
        newValues.put("key1", "value1");
        newValues.put("key2", 1234);
        newValues.put("key3", "value3");
        newValues.put("key7", false);
        newValues.put("key8", 1234.456);

        // Act
        String json = twin.updateDesiredProperty(newValues);

        // Assert
        assertThat(json, is("{\"key1\":\"value1\",\"key2\":1234,\"key3\":\"value3\",\"key7\":false,\"key8\":1234.456}"));
        HashMap<String, String> result = twin.getDesiredPropertyMap();
        assertThat(result.size(), is(5));
        assertThat(result.get("key1"), is("value1"));
        double keydb = Double.parseDouble(result.get("key2"));
        assertThat(keydb, is(1234.0));
        keydb = Double.parseDouble(result.get("key8"));
        assertThat(keydb, is(1234.456));
        assertThat(result.get("key3"), is("value3"));
        assertThat(result.get("key7"), is("false"));
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
        newValues.put("key2", 1234);
        newValues.put("key3", "value3");

        try { Thread.sleep(1000); } catch (InterruptedException e) {}

        // Act
        String json = twin.updateDesiredProperty(newValues);

        // Assert
        TwinProperty resultJson = new TwinProperty();
        resultJson.update(json, null);
        assertThat(resultJson.size(), is(3));
        assertThat(resultJson.get("key1").toString(), is("value1"));
        assertThat((Double)resultJson.get("key2"), is(1234.0));
        assertThat(resultJson.get("key3").toString(), is("value3"));

        TwinProperties properties = Deencapsulation.getField(twin, "properties");
        TwinProperty originJson = properties.Desired;
        assertThat(resultJson.GetMetadata("key1").GetLastUpdateVersion(), is(originJson.GetMetadata("key1").GetLastUpdateVersion()));
        assertThat(resultJson.GetMetadata("key2").GetLastUpdateVersion(), is(originJson.GetMetadata("key2").GetLastUpdateVersion()));
        assertThat(resultJson.GetMetadata("key3").GetLastUpdateVersion(), is(originJson.GetMetadata("key3").GetLastUpdateVersion()));
        assetWithError(resultJson.GetMetadata("key1").GetLastUpdate(), originJson.GetMetadata("key1").GetLastUpdate());
        assetWithError(resultJson.GetMetadata("key2").GetLastUpdate(), originJson.GetMetadata("key2").GetLastUpdate());
        assetWithError(resultJson.GetMetadata("key3").GetLastUpdate(), originJson.GetMetadata("key3").GetLastUpdate());

        HashMap<String, String> result = twin.getDesiredPropertyMap();
        assertThat(result.size(), is(3));
        assertThat(result.get("key1"), is("value1"));
        double keydb = Double.parseDouble(result.get("key2"));
        assertThat(keydb, is(1234.0));
        assertThat(result.get("key3"), is("value3"));
    }

    /* Codes_SRS_TWIN_21_022: [The updateDesiredProperty shall return a string with json representing the desired properties with changes.] */
    @Test
    public void updateDesiredProperty_OnlyMetadataChanges_succeed()
    {
        // Arrange
        Twin twin = new Twin();
        twin.enableMetadata();
        HashMap<String, Object> newValues = new HashMap<>();
        newValues.put("key1", "value1");
        newValues.put("key2", 1234);
        newValues.put("key3", "value3");
        twin.updateDesiredProperty(newValues);

        try { Thread.sleep(1000); } catch (InterruptedException e) {}

        // Act
        String json = twin.updateDesiredProperty(newValues);

        // Assert
        TwinProperty resultJson = new TwinProperty();
        resultJson.update(json, null);
        assertThat(resultJson.size(), is(3));
        assertThat(resultJson.get("key1").toString(), is("value1"));
        assertThat((Double)resultJson.get("key2"), is(1234.0));
        assertThat(resultJson.get("key3").toString(), is("value3"));

        TwinProperties properties = Deencapsulation.getField(twin, "properties");
        TwinProperty originJson = properties.Desired;
        assertThat(resultJson.GetMetadata("key1").GetLastUpdateVersion(), is(originJson.GetMetadata("key1").GetLastUpdateVersion()));
        assertThat(resultJson.GetMetadata("key2").GetLastUpdateVersion(), is(originJson.GetMetadata("key2").GetLastUpdateVersion()));
        assertThat(resultJson.GetMetadata("key3").GetLastUpdateVersion(), is(originJson.GetMetadata("key3").GetLastUpdateVersion()));
        assetWithError(resultJson.GetMetadata("key1").GetLastUpdate(), originJson.GetMetadata("key1").GetLastUpdate());
        assetWithError(resultJson.GetMetadata("key2").GetLastUpdate(), originJson.GetMetadata("key2").GetLastUpdate());
        assetWithError(resultJson.GetMetadata("key3").GetLastUpdate(), originJson.GetMetadata("key3").GetLastUpdate());

        HashMap<String, String> result = twin.getDesiredPropertyMap();
        assertThat(result.size(), is(3));
        assertThat(result.get("key1"), is("value1"));
        double keydb = Double.parseDouble(result.get("key2"));
        assertThat(keydb, is(1234.0));
        assertThat(result.get("key3"), is("value3"));
    }

    /* Codes_SRS_TWIN_21_022: [The updateDesiredProperty shall return a string with json representing the desired properties with changes.] */
    @Test
    public void updateDesiredProperty_newKey_succeed()
    {
        // Arrange
        Twin twin = new Twin();
        HashMap<String, Object> newValues = new HashMap<>();
        newValues.put("key1", "value1");
        newValues.put("key2", 1234);
        newValues.put("key3", "value3");
        twin.updateDesiredProperty(newValues);

        newValues.clear();
        newValues.put("key4", "value4");

        // Act
        String json = twin.updateDesiredProperty(newValues);

        // Assert
        assertThat(json, is("{\"key4\":\"value4\"}"));
        HashMap<String, String> result = twin.getDesiredPropertyMap();
        assertThat(result.size(), is(4));
        assertThat(result.get("key1"), is("value1"));
        double keydb = Double.parseDouble(result.get("key2"));
        assertThat(keydb, is(1234.0));
        assertThat(result.get("key3"), is("value3"));
        assertThat(result.get("key4"), is("value4"));
    }

    /* Codes_SRS_TWIN_21_022: [The updateDesiredProperty shall return a string with json representing the desired properties with changes.] */
    @Test
    public void updateDesiredProperty_newValue_succeed()
    {
        // Arrange
        Twin twin = new Twin();
        HashMap<String, Object> newValues = new HashMap<>();
        newValues.put("key1", "value1");
        newValues.put("key2", 1234);
        newValues.put("key3", "value3");
        twin.updateDesiredProperty(newValues);

        newValues.clear();
        newValues.put("key1", "value4");

        // Act
        String json = twin.updateDesiredProperty(newValues);

        // Assert
        assertThat(json, is("{\"key1\":\"value4\"}"));
        HashMap<String, String> result = twin.getDesiredPropertyMap();
        assertThat(result.size(), is(3));
        assertThat(result.get("key1"), is("value4"));
        double keydb = Double.parseDouble(result.get("key2"));
        assertThat(keydb, is(1234.0));
        assertThat(result.get("key3"), is("value3"));
    }

    /* Codes_SRS_TWIN_21_022: [The updateDesiredProperty shall return a string with json representing the desired properties with changes.] */
    @Test
    public void updateDesiredProperty_newAndOld_succeed()
    {
        // Arrange
        Twin twin = new Twin();
        HashMap<String, Object> newValues = new HashMap<>();
        newValues.put("key1", "value1");
        newValues.put("key2", 1234);
        newValues.put("key3", "value3");
        twin.updateDesiredProperty(newValues);

        newValues.clear();
        newValues.put("key1", "value4");
        newValues.put("key2", 1234);
        newValues.put("key5", "value5");

        // Act
        String json = twin.updateDesiredProperty(newValues);

        // Assert
        assertThat(json, is("{\"key1\":\"value4\",\"key5\":\"value5\"}"));
        HashMap<String, String> result = twin.getDesiredPropertyMap();
        assertThat(result.size(), is(4));
        assertThat(result.get("key1"), is("value4"));
        double keydb = Double.parseDouble(result.get("key2"));
        assertThat(keydb, is(1234.0));
        assertThat(result.get("key3"), is("value3"));
        assertThat(result.get("key5"), is("value5"));
    }

    /* Codes_SRS_TWIN_21_022: [The updateDesiredProperty shall return a string with json representing the desired properties with changes.] */
    @Test
    public void updateDesiredProperty_mixDesiredAndReported_succeed()
    {
        // Arrange
        Twin twin = new Twin();
        HashMap<String, Object> newValues = new HashMap<>();
        newValues.put("key1", "value1");
        newValues.put("key2", 1234);
        newValues.put("key3", "value3");
        twin.updateReportedProperty(newValues);
        newValues.clear();
        newValues.put("key1", "value1");
        newValues.put("key6", "value6");
        newValues.put("key7", true);
        twin.updateDesiredProperty(newValues);
        newValues.clear();
        newValues.put("key1", "value4");
        newValues.put("key6", "value6");
        newValues.put("key5", "value5");

        // Act
        String json = twin.updateDesiredProperty(newValues);

        // Assert
        assertThat(json, is("{\"key1\":\"value4\",\"key5\":\"value5\"}"));
        HashMap<String, String> result = twin.getDesiredPropertyMap();
        assertThat(result.size(), is(4));
        assertThat(result.get("key1"), is("value4"));
        assertThat(result.get("key5"), is("value5"));
        assertThat(result.get("key6"), is("value6"));
        assertThat(result.get("key7"), is("true"));
    }

    /* Codes_SRS_TWIN_21_023: [If the provided `property` map is null, the updateDesiredProperty shall return null.] */
    /* Codes_SRS_TWIN_21_024: [If no Desired property changed its value, the updateDesiredProperty shall return null.] */
    @Test
    public void updateDesiredProperty_emptyMap_failed()
    {
        // Arrange
        Twin twin = new Twin();
        HashMap<String, Object> newValues = new HashMap<>();

        // Act
        String json = twin.updateDesiredProperty(newValues);

        // Assert
        assertNull(json);
        HashMap<String, String> result = twin.getDesiredPropertyMap();
        assertNull(result);
    }

    /* Codes_SRS_TWIN_21_024: [If no Desired property changed its value, the updateDesiredProperty shall return null.] */
    @Test
    public void updateDesiredProperty_noChanges_succeed()
    {
        // Arrange
        Twin twin = new Twin();
        HashMap<String, Object> newValues = new HashMap<>();
        newValues.put("key1", "value1");
        newValues.put("key2", 1234);
        newValues.put("key3", "value3");
        twin.updateDesiredProperty(newValues);

        // Act
        String json = twin.updateDesiredProperty(newValues);

        // Assert
        assertNull(json);
        HashMap<String, String> result = twin.getDesiredPropertyMap();
        assertThat(result.size(), is(3));
        assertThat(result.get("key1"), is("value1"));
        double keydb = Double.parseDouble(result.get("key2"));
        assertThat(keydb, is(1234.0));
        assertThat(result.get("key3"), is("value3"));
    }

    /* Codes_SRS_TWIN_21_025: [The updateReportedProperty shall add all provided properties to the Reported property.] */
    /* Codes_SRS_TWIN_21_051: [The getReportedPropertyMap shall return a map with all reported property key value pairs.] */
    @Test
    public void updateReportedProperty_succeed()
    {
        // Arrange
        Twin twin = new Twin();
        HashMap<String, Object> newValues = new HashMap<>();
        newValues.put("key1", "value1");
        newValues.put("key2", 1234);
        newValues.put("key3", "value3");

        // Act
        String json = twin.updateReportedProperty(newValues);

        // Assert
        assertThat(json, is("{\"key1\":\"value1\",\"key2\":1234,\"key3\":\"value3\"}"));
        HashMap<String, String> result = twin.getReportedPropertyMap();
        assertThat(result.size(), is(3));
        assertThat(result.get("key1"), is("value1"));
        double keydb = Double.parseDouble(result.get("key2"));
        assertThat(keydb, is(1234.0));
        assertThat(result.get("key3"), is("value3"));
    }

    /* Codes_SRS_TWIN_21_026: [The updateReportedProperty shall return a string with json representing the Reported properties with changes.] */
    @Test
    public void updateReportedProperty_newAndOld_succeed()
    {
        // Arrange
        Twin twin = new Twin();
        HashMap<String, Object> newValues = new HashMap<>();
        newValues.put("key1", 898989);
        newValues.put("key2", 1234);
        newValues.put("key3", "value3");
        twin.updateReportedProperty(newValues);

        newValues.clear();
        newValues.put("key1", 7654);
        newValues.put("key2", 1234);
        newValues.put("key5", "value5");

        // Act
        String json = twin.updateReportedProperty(newValues);

        // Assert
        assertThat(json, is("{\"key1\":7654,\"key5\":\"value5\"}"));
        HashMap<String, String> result = twin.getReportedPropertyMap();
        assertThat(result.size(), is(4));
        double keydb = Double.parseDouble(result.get("key1"));
        assertThat(keydb, is(7654.0));
        keydb = Double.parseDouble(result.get("key2"));
        assertThat(keydb, is(1234.0));
        assertThat(result.get("key3"), is("value3"));
        assertThat(result.get("key5"), is("value5"));
    }

    /* Codes_SRS_TWIN_21_026: [The updateReportedProperty shall return a string with json representing the Reported properties with changes.] */
    @Test
    public void updateReportedProperty_mixDesiredAndReported_succeed()
    {
        // Arrange
        Twin twin = new Twin();
        HashMap<String, Object> newValues = new HashMap<>();
        newValues.put("key1", "value1");
        newValues.put("key2", 1234);
        newValues.put("key3", "value3");
        twin.updateReportedProperty(newValues);
        newValues.clear();
        newValues.put("key1", "value4");
        newValues.put("key6", "value6");
        newValues.put("key7", "value7");
        twin.updateDesiredProperty(newValues);
        newValues.clear();
        newValues.put("key1", "value4");
        newValues.put("key2", 1234);
        newValues.put("key5", "value5");

        // Act
        String json = twin.updateReportedProperty(newValues);

        // Assert
        assertThat(json, is("{\"key1\":\"value4\",\"key5\":\"value5\"}"));
        HashMap<String, String> result = twin.getReportedPropertyMap();
        assertThat(result.size(), is(4));
        assertThat(result.get("key1"), is("value4"));
        double keydb = Double.parseDouble(result.get("key2"));
        assertThat(keydb, is(1234.0));
        assertThat(result.get("key3"), is("value3"));
        assertThat(result.get("key5"), is("value5"));
    }

    /* Codes_SRS_TWIN_21_027: [If the provided `property` map is null, the updateReportedProperty shall return null.] */
    /* Codes_SRS_TWIN_21_028: [If no Reported property changed its value, the updateReportedProperty shall return null.] */
    @Test
    public void updateReportedProperty_emptyMap_failed()
    {
        // Arrange
        Twin twin = new Twin();
        HashMap<String, Object> newValues = new HashMap<>();

        // Act
        String json = twin.updateReportedProperty(newValues);

        // Assert
        assertNull(json);
    }


    /* Codes_SRS_TWIN_21_034: [The updateReportedProperty shall update the Reported property using the information provided in the json.] */
    /* Codes_SRS_TWIN_21_035: [The updateReportedProperty shall generate a map with all pairs key value that had its content changed.] */
    /* Codes_SRS_TWIN_21_036: [The updateReportedProperty shall send the map with all changed pairs to the upper layer calling onReportedCallback (TwinPropertiesChangeCallback).] */
    @Test
    public void updateReportedProperty_json_emptyClass_succeed()
    {
        // Arrange
        OnReportedCallback onReportedCallback = new OnReportedCallback();
        Twin twin = new Twin();
        twin.setReportedCallback(onReportedCallback);

        String json = "{\"key1\":\"value1\",\"key2\":1234,\"key3\":\"value3\"}";

        // Act
        twin.updateReportedProperty(json);

        // Assert
        assertThat(onReportedCallback.diff.size(), is(3));
        assertThat(onReportedCallback.diff.get("key1"), is("value1"));
        double keydb = Double.parseDouble(onReportedCallback.diff.get("key2"));
        assertThat(keydb, is(1234.0));
        assertThat(onReportedCallback.diff.get("key3"), is("value3"));

        HashMap<String, String> result = twin.getReportedPropertyMap();
        assertThat(result.size(), is(3));
        assertThat(result.get("key1"), is("value1"));
        keydb = Double.parseDouble(result.get("key2"));
        assertThat(keydb, is(1234.0));
        assertThat(result.get("key3"), is("value3"));
    }

    /* Codes_SRS_TWIN_21_034: [The updateReportedProperty shall update the Reported property using the information provided in the json.] */
    /* Codes_SRS_TWIN_21_035: [The updateReportedProperty shall generate a map with all pairs key value that had its content changed.] */
    /* Codes_SRS_TWIN_21_036: [The updateReportedProperty shall send the map with all changed pairs to the upper layer calling onReportedCallback (TwinPropertiesChangeCallback).] */
    @Test
    public void updateReportedProperty_json_mixDesiredAndReported_succeed()
    {
        // Arrange
        Twin twin = new Twin();
        HashMap<String, Object> newValues = new HashMap<>();
        newValues.put("key1", "value1");
        newValues.put("key2", 1234);
        newValues.put("key3", "value3");
        twin.updateReportedProperty(newValues);
        newValues.clear();
        newValues.put("key1", "value4");
        newValues.put("key6", "value6");
        newValues.put("key7", "value7");
        twin.updateDesiredProperty(newValues);

        OnReportedCallback onReportedCallback = new OnReportedCallback();
        twin.setReportedCallback(onReportedCallback);

        String json = "{\"key1\":\"value4\",\"key2\":4321,\"key5\":\"value5\"}";

        // Act
        twin.updateReportedProperty(json);

        // Assert
        assertThat(onReportedCallback.diff.size(), is(3));
        assertThat(onReportedCallback.diff.get("key1"), is("value4"));
        double keydb = Double.parseDouble(onReportedCallback.diff.get("key2"));
        assertThat(keydb, is(4321.0));
        assertThat(onReportedCallback.diff.get("key5"), is("value5"));
        HashMap<String, String> result = twin.getReportedPropertyMap();
        assertThat(result.size(), is(4));
        assertThat(result.get("key1"), is("value4"));
        keydb = Double.parseDouble(result.get("key2"));
        assertThat(keydb, is(4321.0));
        assertThat(result.get("key3"), is("value3"));
        assertThat(result.get("key5"), is("value5"));
    }

    /* Codes_SRS_TWIN_21_037: [If the OnReportedCallback is set as null, the updateReportedProperty shall discard the map with the changed pairs.] */
    @Test
    public void updateReportedProperty_json_noCallback_emptyClass_succeed()
    {
        // Arrange
        Twin twin = new Twin();

        String json = "{\"key1\":\"value1\",\"key2\":1234,\"key3\":\"value3\"}";

        // Act
        twin.updateReportedProperty(json);

        // Assert
        HashMap<String, String> result = twin.getReportedPropertyMap();
        assertThat(result.size(), is(3));
        assertThat(result.get("key1"), is("value1"));
        double keydb = Double.parseDouble(result.get("key2"));
        assertThat(keydb, is(1234.0));
        assertThat(result.get("key3"), is("value3"));
    }

    /* Codes_SRS_TWIN_21_038: [If there is no change in the Reported property, the updateReportedProperty shall not call the OnReportedCallback.] */
    @Test
    public void updateReportedProperty_json_noChanges_succeed()
    {
        // Arrange
        OnReportedCallback onReportedCallback = new OnReportedCallback();
        Twin twin = new Twin();
        twin.setReportedCallback(onReportedCallback);
        HashMap<String, Object> newValues = new HashMap<>();
        newValues.put("key1", "value1");
        newValues.put("key2", 1234.0);
        newValues.put("key3", "value3");
        twin.updateReportedProperty(newValues);

        String json = "{\"key1\":\"value1\",\"key2\":1234.0,\"key3\":\"value3\"}";

        // Act
        twin.updateReportedProperty(json);

        // Assert
        assertNull(onReportedCallback.diff);

        HashMap<String, String> result = twin.getReportedPropertyMap();
        assertThat(result.size(), is(3));
        assertThat(result.get("key1"), is("value1"));
        double keydb = Double.parseDouble(result.get("key2"));
        assertThat(keydb, is(1234.0));
        assertThat(result.get("key3"), is("value3"));
    }

    /* Codes_SRS_TWIN_21_005: [The constructor shall call the standard constructor.] */
    /* Codes_SRS_TWIN_21_007: [The constructor shall set OnReportedCallback as null.] */
    /* Codes_SRS_TWIN_21_008: [The constructor shall set Tags as null.] */
    /* Codes_SRS_TWIN_21_006: [The constructor shall set OnDesiredCallback with the provided Callback function.] */
    /* Codes_SRS_TWIN_21_029: [The updateDesiredProperty shall update the Desired property using the information provided in the json.] */
    /* Codes_SRS_TWIN_21_030: [The updateDesiredProperty shall generate a map with all pairs key value that had its content changed.] */
    /* Codes_SRS_TWIN_21_031: [The updateDesiredProperty shall send the map with all changed pairs to the upper layer calling onDesiredCallback (TwinPropertiesChangeCallback).] */
    @Test
    public void updateDesiredProperty_json_emptyClass_succeed()
    {
        // Arrange
        OnDesiredCallback onDesiredCallback = new OnDesiredCallback();
        Twin twin = new Twin(onDesiredCallback);
        twin.setDesiredCallback(onDesiredCallback);

        String json = "{\"key1\":\"value1\",\"key2\":1234,\"key3\":\"value3\"}";

        // Act
        twin.updateDesiredProperty(json);

        // Assert
        assertThat(onDesiredCallback.diff.size(), is(3));
        assertThat(onDesiredCallback.diff.get("key1"), is("value1"));
        double keydb = Double.parseDouble(onDesiredCallback.diff.get("key2"));
        assertThat(keydb, is(1234.0));
        assertThat(onDesiredCallback.diff.get("key3"), is("value3"));

        HashMap<String, String> result = twin.getDesiredPropertyMap();
        assertThat(result.size(), is(3));
        assertThat(result.get("key1"), is("value1"));
        keydb = Double.parseDouble(result.get("key2"));
        assertThat(keydb, is(1234.0));
        assertThat(result.get("key3"), is("value3"));
    }

    /* Codes_SRS_TWIN_21_029: [The updateDesiredProperty shall update the Desired property using the information provided in the json.] */
    /* Codes_SRS_TWIN_21_030: [The updateDesiredProperty shall generate a map with all pairs key value that had its content changed.] */
    /* Codes_SRS_TWIN_21_031: [The updateDesiredProperty shall send the map with all changed pairs to the upper layer calling onDesiredCallback (TwinPropertiesChangeCallback).] */
    @Test
    public void updateDesiredProperty_json_mixDesiredAndDesired_succeed()
    {
        // Arrange
        Twin twin = new Twin();
        HashMap<String, Object> newValues = new HashMap<>();
        newValues.put("key1", "value1");
        newValues.put("key2", 1234);
        newValues.put("key3", "value3");
        twin.updateReportedProperty(newValues);
        newValues.clear();
        newValues.put("key1", "value4");
        newValues.put("key2", 1234);
        newValues.put("key6", "value6");
        newValues.put("key7", true);
        twin.updateDesiredProperty(newValues);

        OnDesiredCallback onDesiredCallback = new OnDesiredCallback();
        twin.setDesiredCallback(onDesiredCallback);

        String json = "{\"key1\":\"value4\",\"key2\":4321,\"key5\":\"value5\"}";

        // Act
        twin.updateDesiredProperty(json);

        // Assert
        assertThat(onDesiredCallback.diff.size(), is(2));
        double keydb = Double.parseDouble(onDesiredCallback.diff.get("key2"));
        assertThat(keydb, is(4321.0));
        assertThat(onDesiredCallback.diff.get("key5"), is("value5"));
        HashMap<String, String> result = twin.getDesiredPropertyMap();
        assertThat(result.size(), is(5));
        assertThat(result.get("key1"), is("value4"));
        keydb = Double.parseDouble(result.get("key2"));
        assertThat(keydb, is(4321.0));
        assertThat(result.get("key5"), is("value5"));
        assertThat(result.get("key6"), is("value6"));
        assertThat(result.get("key7"), is("true"));
    }

    /* Codes_SRS_TWIN_21_032: [If the OnDesiredCallback is set as null, the updateDesiredProperty shall discard the map with the changed pairs.] */
    @Test
    public void updateDesiredProperty_json_noCallback_emptyClass_succeed()
    {
        // Arrange
        Twin twin = new Twin();

        String json = "{\"key1\":\"value1\",\"key2\":1234,\"key3\":\"value3\"}";

        // Act
        twin.updateDesiredProperty(json);

        // Assert
        HashMap<String, String> result = twin.getDesiredPropertyMap();
        assertThat(result.size(), is(3));
        assertThat(result.get("key1"), is("value1"));
        double keydb = Double.parseDouble(result.get("key2"));
        assertThat(keydb, is(1234.0));
        assertThat(result.get("key3"), is("value3"));
    }

    /* Codes_SRS_TWIN_21_033: [If there is no change in the Desired property, the updateDesiredProperty shall not call the OnDesiredCallback.] */
    @Test
    public void updateDesiredProperty_json_noChanges_succeed()
    {
        // Arrange
        OnDesiredCallback onDesiredCallback = new OnDesiredCallback();
        Twin twin = new Twin();
        twin.setDesiredCallback(onDesiredCallback);
        HashMap<String, Object> newValues = new HashMap<>();
        newValues.put("key1", "value1");
        newValues.put("key2", 1234.0);
        newValues.put("key3", "value3");
        twin.updateDesiredProperty(newValues);

        String json = "{\"key1\":\"value1\",\"key2\":1234.0,\"key3\":\"value3\"}";

        // Act
        twin.updateDesiredProperty(json);

        // Assert
        assertNull(onDesiredCallback.diff);

        HashMap<String, String> result = twin.getDesiredPropertyMap();
        assertThat(result.size(), is(3));
        assertThat(result.get("key1"), is("value1"));
        double keydb = Double.parseDouble(result.get("key2"));
        assertThat(keydb, is(1234.0));
        assertThat(result.get("key3"), is("value3"));
    }

    /* Codes_SRS_TWIN_21_009: [The constructor shall call the standard constructor.] */
    /* Codes_SRS_TWIN_21_012: [The constructor shall set Tags as null.] */
    /* Codes_SRS_TWIN_21_010: [The constructor shall set OnDesiredCallback with the provided Callback function.] */
    /* Codes_SRS_TWIN_21_011: [The constructor shall set OnReportedCallback with the provided Callback function.] */
    /* Codes_SRS_TWIN_21_039: [The updateTwin shall fill the fields the properties in the Twin class with the keys and values provided in the json string.] */
    /* Codes_SRS_TWIN_21_041: [The updateTwin shall create a list with all properties that was updated (new key or value) by the new json.] */
    /* Codes_SRS_TWIN_21_044: [If OnDesiredCallback was provided, the updateTwin shall create a new map with a copy of all pars key values updated by the json in the Desired property, and OnDesiredCallback passing this map as parameter.] */
    /* Codes_SRS_TWIN_21_045: [If OnReportedCallback was provided, the updateTwin shall create a new map with a copy of all pars key values updated by the json in the Reported property, and OnReportedCallback passing this map as parameter.] */
    @Test
    public void updateTwin_json_emptyClass_noMetadata_succeed()
    {
        // Arrange
        OnDesiredCallback onDesiredCallback = new OnDesiredCallback();
        OnReportedCallback onReportedCallback = new OnReportedCallback();
        Twin twin = new Twin(onDesiredCallback, onReportedCallback);

        String json = "{\"properties\":{\"desired\":{\"key1\":\"value1\",\"key2\":1234,\"key3\":\"value3\"},\"reported\":{\"key1\":\"value1\",\"key2\":1234.124,\"key5\":\"value5\",\"key7\":true}}}";

        // Act
        twin.updateTwin(json);

        // Assert
        assertThat(onDesiredCallback.diff.size(), is(3));
        assertThat(onDesiredCallback.diff.get("key1"), is("value1"));
        double keydb = Double.parseDouble(onDesiredCallback.diff.get("key2"));
        assertThat(keydb, is(1234.0));
        assertThat(onDesiredCallback.diff.get("key3"), is("value3"));

        assertThat(onReportedCallback.diff.size(), is(4));
        assertThat(onReportedCallback.diff.get("key1"), is("value1"));
        keydb = Double.parseDouble(onReportedCallback.diff.get("key2"));
        assertThat(keydb, is(1234.124));
        assertThat(onReportedCallback.diff.get("key5"), is("value5"));
        assertThat(onReportedCallback.diff.get("key7"), is("true"));

        HashMap<String, String> result = twin.getDesiredPropertyMap();
        assertThat(result.size(), is(3));
        assertThat(result.get("key1"), is("value1"));
        keydb = Double.parseDouble(result.get("key2"));
        assertThat(keydb, is(1234.0));
        assertThat(result.get("key3"), is("value3"));

        result = twin.getReportedPropertyMap();
        assertThat(result.size(), is(4));
        assertThat(result.get("key1"), is("value1"));
        keydb = Double.parseDouble(result.get("key2"));
        assertThat(keydb, is(1234.124));
        assertThat(result.get("key5"), is("value5"));
        assertThat(result.get("key7"), is("true"));
    }

    /* Codes_SRS_TWIN_21_046: [If OnDesiredCallback was not provided, the updateTwin shall not do anything with the list of updated desired properties.] */
    /* Codes_SRS_TWIN_21_047: [If OnReportedCallback was not provided, the updateTwin shall not do anything with the list of updated reported properties.] */
    @Test
    public void updateTwin_json_emptyClass_noCallback_succeed()
    {
        // Arrange
        Twin twin = new Twin();

        String json = "{\"properties\":{\"desired\":{\"key1\":\"value1\",\"key2\":1234.0,\"key3\":\"value3\"},\"reported\":{\"key1\":\"value1\",\"key2\":1234.124,\"key5\":\"value5\",\"key7\":true}}}";

        // Act
        twin.updateTwin(json);

        // Assert
        String resultJson = twin.toJson();
        assertThat(resultJson, is(json));
    }

    /* Codes_SRS_TWIN_21_039: [The updateTwin shall fill the fields the properties in the Twin class with the keys and values provided in the json string.] */
    /* Codes_SRS_TWIN_21_041: [The updateTwin shall create a list with all properties that was updated (new key or value) by the new json.] */
    /* Codes_SRS_TWIN_21_048: [The getDesiredPropertyVersion shall return the desired property version.] */
    /* Codes_SRS_TWIN_21_049: [The getReportedPropertyVersion shall return the reported property version.] */
    @Test
    public void updateTwin_json_emptyClass_withFullMetadata_succeed()
    {
        // Arrange
        OnDesiredCallback onDesiredCallback = new OnDesiredCallback();
        OnReportedCallback onReportedCallback = new OnReportedCallback();
        Twin twin = new Twin(onDesiredCallback, onReportedCallback);
        twin.enableMetadata();

        String json =
            "{" +
                "\"properties\":{"+
                    "\"desired\":{" +
                        "\"key1\":\"value1\"," +
                        "\"key2\":1234.0," +
                        "\"key3\":\"value3\"," +
                        "\"$version\":3," +
                        "\"$metadata\":{" +
                            "\"key1\":{" +
                                "\"$lastUpdated\":\"2017-02-09T08:13:12.3456Z\"," +
                                "\"$lastUpdatedVersion\":3" +
                            "}," +
                            "\"key2\":{" +
                                "\"$lastUpdated\":\"2017-02-09T08:13:12.3457Z\"," +
                                "\"$lastUpdatedVersion\":5" +
                            "}," +
                            "\"key3\":{" +
                                "\"$lastUpdated\":\"2017-02-09T08:13:12.3458Z\"," +
                                "\"$lastUpdatedVersion\":3" +
                            "}" +
                        "}" +
                    "}," +
                    "\"reported\":{" +
                        "\"key1\":\"value1\"," +
                        "\"key2\":1234.124," +
                        "\"key5\":\"value5\"," +
                        "\"key7\":true," +
                        "\"$version\":5," +
                        "\"$metadata\":{" +
                            "\"key1\":{" +
                                "\"$lastUpdated\":\"2017-02-09T08:13:12.3456Z\"," +
                                "\"$lastUpdatedVersion\":3" +
                            "}," +
                            "\"key2\":{" +
                                "\"$lastUpdated\":\"2017-02-09T08:13:12.3457Z\"," +
                                "\"$lastUpdatedVersion\":5" +
                            "}," +
                            "\"key5\":{" +
                                "\"$lastUpdated\":\"2017-02-09T08:13:12.3457Z\"," +
                                "\"$lastUpdatedVersion\":5" +
                            "}," +
                            "\"key7\":{" +
                                "\"$lastUpdated\":\"2017-02-09T08:13:12.3458Z\"," +
                                "\"$lastUpdatedVersion\":3" +
                            "}" +
                        "}" +
                    "}" +
                "}" +
            "}";

        // Act
        twin.updateTwin(json);

        // Assert
        assertThat(onDesiredCallback.diff.size(), is(3));
        assertThat(onDesiredCallback.diff.get("key1"), is("value1"));
        double keydb = Double.parseDouble(onDesiredCallback.diff.get("key2"));
        assertThat(keydb, is(1234.0));
        assertThat(onDesiredCallback.diff.get("key3"), is("value3"));

        assertThat(onReportedCallback.diff.size(), is(4));
        assertThat(onReportedCallback.diff.get("key1"), is("value1"));
        keydb = Double.parseDouble(onReportedCallback.diff.get("key2"));
        assertThat(keydb, is(1234.124));
        assertThat(onReportedCallback.diff.get("key5"), is("value5"));
        assertThat(onReportedCallback.diff.get("key7"), is("true"));

        String resultJson = twin.toJson();
        assertThat(resultJson, is(json));
        assertThat(twin.getReportedPropertyVersion(), is(5));
        assertThat(twin.getDesiredPropertyVersion(), is(3));
    }

    /* Codes_SRS_TWIN_21_039: [The updateTwin shall fill the fields the properties in the Twin class with the keys and values provided in the json string.] */
    /* Codes_SRS_TWIN_21_040: [The updateTwin shall not change fields that is not reported in the json string.] */
    @Test
    public void updateTwin_json_emptyClass_withMetadataNoUpdateVersion_succeed()
    {
        // Arrange
        OnDesiredCallback onDesiredCallback = new OnDesiredCallback();
        OnReportedCallback onReportedCallback = new OnReportedCallback();
        Twin twin = new Twin(onDesiredCallback, onReportedCallback);
        twin.enableMetadata();

        String json =
                "{" +
                "\"properties\":{"+
                "\"desired\":{" +
                "\"key1\":\"value1\"," +
                "\"key2\":1234.0," +
                "\"key3\":\"value3\"," +
                "\"$version\":0," +
                "\"$metadata\":{" +
                "\"key1\":{" +
                "\"$lastUpdated\":\"2017-02-09T08:13:12.3456Z\"" +
                "}," +
                "\"key2\":{" +
                "\"$lastUpdated\":\"2017-02-09T08:13:12.3457Z\"" +
                "}," +
                "\"key3\":{" +
                "\"$lastUpdated\":\"2017-02-09T08:13:12.3458Z\"" +
                "}" +
                "}" +
                "}," +
                "\"reported\":{" +
                "\"key1\":\"value1\"," +
                "\"key2\":1234.124," +
                "\"key5\":\"value5\"," +
                "\"key7\":true," +
                "\"$version\":0," +
                "\"$metadata\":{" +
                "\"key1\":{" +
                "\"$lastUpdated\":\"2017-02-09T08:13:12.3456Z\"" +
                "}," +
                "\"key2\":{" +
                "\"$lastUpdated\":\"2017-02-09T08:13:12.3457Z\"" +
                "}," +
                "\"key5\":{" +
                "\"$lastUpdated\":\"2017-02-09T08:13:12.3457Z\"" +
                "}," +
                "\"key7\":{" +
                "\"$lastUpdated\":\"2017-02-09T08:13:12.3458Z\"" +
                "}" +
                "}" +
                "}" +
                "}" +
                "}";

        // Act
        twin.updateTwin(json);

        // Assert
        assertThat(onDesiredCallback.diff.size(), is(3));
        assertThat(onDesiredCallback.diff.get("key1"), is("value1"));
        double keydb = Double.parseDouble(onDesiredCallback.diff.get("key2"));
        assertThat(keydb, is(1234.0));
        assertThat(onDesiredCallback.diff.get("key3"), is("value3"));

        assertThat(onReportedCallback.diff.size(), is(4));
        assertThat(onReportedCallback.diff.get("key1"), is("value1"));
        keydb = Double.parseDouble(onReportedCallback.diff.get("key2"));
        assertThat(keydb, is(1234.124));
        assertThat(onReportedCallback.diff.get("key5"), is("value5"));
        assertThat(onReportedCallback.diff.get("key7"), is("true"));

        String resultJson = twin.toJson();
        assertThat(resultJson, is(json));
    }

    /* Codes_SRS_TWIN_21_039: [The updateTwin shall fill the fields the properties in the Twin class with the keys and values provided in the json string.] */
    /* Codes_SRS_TWIN_21_040: [The updateTwin shall not change fields that is not reported in the json string.] */
    @Test
    public void updateTwin_json_emptyClass_withFullMetadataNoVersion_succeed()
    {
        // Arrange
        OnDesiredCallback onDesiredCallback = new OnDesiredCallback();
        OnReportedCallback onReportedCallback = new OnReportedCallback();
        Twin twin = new Twin(onDesiredCallback, onReportedCallback);
        twin.enableMetadata();

        String json =
                "{" +
                "\"properties\":{"+
                "\"desired\":{" +
                "\"key1\":\"value1\"," +
                "\"key2\":1234.0," +
                "\"key3\":\"value3\"," +
                "\"$metadata\":{" +
                "\"key1\":{" +
                "\"$lastUpdated\":\"2017-02-09T08:13:12.3456Z\"," +
                "\"$lastUpdatedVersion\":3" +
                "}," +
                "\"key2\":{" +
                "\"$lastUpdated\":\"2017-02-09T08:13:12.3457Z\"," +
                "\"$lastUpdatedVersion\":5" +
                "}," +
                "\"key3\":{" +
                "\"$lastUpdated\":\"2017-02-09T08:13:12.3458Z\"," +
                "\"$lastUpdatedVersion\":3" +
                "}" +
                "}" +
                "}," +
                "\"reported\":{" +
                "\"key1\":\"value1\"," +
                "\"key2\":1234.124," +
                "\"key5\":\"value5\"," +
                "\"key7\":true," +
                "\"$metadata\":{" +
                "\"key1\":{" +
                "\"$lastUpdated\":\"2017-02-09T08:13:12.3456Z\"," +
                "\"$lastUpdatedVersion\":3" +
                "}," +
                "\"key2\":{" +
                "\"$lastUpdated\":\"2017-02-09T08:13:12.3457Z\"," +
                "\"$lastUpdatedVersion\":5" +
                "}," +
                "\"key5\":{" +
                "\"$lastUpdated\":\"2017-02-09T08:13:12.3457Z\"," +
                "\"$lastUpdatedVersion\":5" +
                "}," +
                "\"key7\":{" +
                "\"$lastUpdated\":\"2017-02-09T08:13:12.3458Z\"," +
                "\"$lastUpdatedVersion\":3" +
                "}" +
                "}" +
                "}" +
                "}" +
                "}";

        // Act
        twin.updateTwin(json);

        // Assert
        assertThat(onDesiredCallback.diff.size(), is(3));
        assertThat(onDesiredCallback.diff.get("key1"), is("value1"));
        double keydb = Double.parseDouble(onDesiredCallback.diff.get("key2"));
        assertThat(keydb, is(1234.0));
        assertThat(onDesiredCallback.diff.get("key3"), is("value3"));

        assertThat(onReportedCallback.diff.size(), is(4));
        assertThat(onReportedCallback.diff.get("key1"), is("value1"));
        keydb = Double.parseDouble(onReportedCallback.diff.get("key2"));
        assertThat(keydb, is(1234.124));
        assertThat(onReportedCallback.diff.get("key5"), is("value5"));
        assertThat(onReportedCallback.diff.get("key7"), is("true"));

        String resultJson = twin.toJson();
        assertThat(resultJson, is(json));
    }

    /* Codes_SRS_TWIN_21_040: [The updateTwin shall not change fields that is not reported in the json string.] */
    /* Codes_SRS_TWIN_21_041: [The updateTwin shall create a list with all properties that was updated (new key or value) by the new json.] */
    /* Codes_SRS_TWIN_21_044: [If OnDesiredCallback was provided, the updateTwin shall create a new map with a copy of all pars key values updated by the json in the Desired property, and OnDesiredCallback passing this map as parameter.] */
    /* Codes_SRS_TWIN_21_045: [If OnReportedCallback was provided, the updateTwin shall create a new map with a copy of all pars key values updated by the json in the Reported property, and OnReportedCallback passing this map as parameter.] */
    @Test
    public void updateTwin_json_changeOneField_succeed()
    {
        // Arrange
        OnDesiredCallback onDesiredCallback = new OnDesiredCallback();
        OnReportedCallback onReportedCallback = new OnReportedCallback();
        Twin twin = new Twin(onDesiredCallback, onReportedCallback);
        HashMap<String, Object> newValues = new HashMap<>();
        newValues.put("key1", "value1");
        newValues.put("key2", 1234);
        newValues.put("key3", "value3");
        twin.updateReportedProperty(newValues);
        newValues.clear();
        newValues.put("key1", "value4");
        newValues.put("key2", 1234);
        newValues.put("key6", "value6");
        newValues.put("key7", true);
        twin.updateDesiredProperty(newValues);

        String json = "{\"properties\":{" +
                "\"desired\":{\"key2\":9875}," +
                "\"reported\":{\"key1\":\"value4\"}}}";

        // Act
        twin.updateTwin(json);

        // Assert
        assertThat(onDesiredCallback.diff.size(), is(1));
        double keydb = Double.parseDouble(onDesiredCallback.diff.get("key2"));
        assertThat(keydb, is(9875.0));

        assertThat(onReportedCallback.diff.size(), is(1));
        assertThat(onReportedCallback.diff.get("key1"), is("value4"));

        HashMap<String, String> result = twin.getDesiredPropertyMap();
        assertThat(result.size(), is(4));
        assertThat(result.get("key1"), is("value4"));
        keydb = Double.parseDouble(result.get("key2"));
        assertThat(keydb, is(9875.0));
        assertThat(result.get("key6"), is("value6"));
        assertThat(result.get("key7"), is("true"));

        result = twin.getReportedPropertyMap();
        assertThat(result.size(), is(3));
        assertThat(result.get("key1"), is("value4"));
        keydb = Double.parseDouble(result.get("key2"));
        assertThat(keydb, is(1234.0));
        assertThat(result.get("key3"), is("value3"));
    }

    /* Codes_SRS_TWIN_21_041: [The updateTwin shall create a list with all properties that was updated (new key or value) by the new json.] */
    /* Codes_SRS_TWIN_21_042: [If a valid key has a null value, the updateTwin shall delete this property.] */
    /* Codes_SRS_TWIN_21_044: [If OnDesiredCallback was provided, the updateTwin shall create a new map with a copy of all pars key values updated by the json in the Desired property, and OnDesiredCallback passing this map as parameter.] */
    /* Codes_SRS_TWIN_21_045: [If OnReportedCallback was provided, the updateTwin shall create a new map with a copy of all pars key values updated by the json in the Reported property, and OnReportedCallback passing this map as parameter.] */
    @Test
    public void updateTwin_json_deleteField_noMetadata_succeed()
    {
        // Arrange
        OnDesiredCallback onDesiredCallback = new OnDesiredCallback();
        OnReportedCallback onReportedCallback = new OnReportedCallback();
        Twin twin = new Twin(onDesiredCallback, onReportedCallback);
        HashMap<String, Object> newValues = new HashMap<>();
        newValues.put("key1", "value1");
        newValues.put("key2", 1234);
        newValues.put("key3", "value3");
        twin.updateDesiredProperty(newValues);
        newValues.clear();
        newValues.put("key1", "value4");
        newValues.put("key2", 1234);
        newValues.put("key6", "value6");
        newValues.put("key7", true);
        twin.updateReportedProperty(newValues);

        String json = "{\"properties\":{" +
                "\"desired\":{\"key3\":null,\"key1\":\"value4\"}," +
                "\"reported\":{\"key1\":null,\"key5\":null,\"key7\":null}}}";

        // Act
        twin.updateTwin(json);

        // Assert
        assertThat(onDesiredCallback.diff.size(), is(2));
        assertNull(onDesiredCallback.diff.get("key3"));
        assertThat(onDesiredCallback.diff.get("key1"), is("value4"));

        assertThat(onReportedCallback.diff.size(), is(2));
        assertNull(onReportedCallback.diff.get("key1"));
        assertNull(onReportedCallback.diff.get("key7"));

        HashMap<String, String> result = twin.getDesiredPropertyMap();
        assertThat(result.size(), is(2));
        assertThat(result.get("key1"), is("value4"));
        double keydb = Double.parseDouble(result.get("key2"));
        assertThat(keydb, is(1234.0));

        result = twin.getReportedPropertyMap();
        assertThat(result.size(), is(2));
        keydb = Double.parseDouble(result.get("key2"));
        assertThat(keydb, is(1234.0));
        assertThat(result.get("key6"), is("value6"));
    }

    /* Codes_SRS_TWIN_21_043: [If the provided json is not valid, the updateTwin shall throws IllegalArgumentException.] */
    @Test (expected = IllegalArgumentException.class)
    public void updateTwin_json_missing_comma_failed()
    {
        // Arrange
        Twin twin = new Twin();

        String json = "{\"properties\":{" +
                "\"desired\":{\"key3\":null,\"key1\":\"value4\"}" +
                "\"reported\":{\"key1\":null,\"key5\":null,\"key7\":null}}}";

        // Act
        twin.updateTwin(json);

        // Assert
    }

    /* Codes_SRS_TWIN_21_043: [If the provided json is not valid, the updateTwin shall throws IllegalArgumentException.] */
    @Test (expected = IllegalArgumentException.class)
    public void updateTwin_json_badProperties_failed()
    {
        // Arrange
        Twin twin = new Twin();

        String json = "{\"property\":{" +
                "\"desired\":{\"key3\":null,\"key1\":\"value4\"}," +
                "\"reported\":{\"key1\":null,\"key5\":null,\"key7\":null}}}";

        // Act
        twin.updateTwin(json);

        // Assert
    }

    /* Codes_SRS_TWIN_21_043: [If the provided json is not valid, the updateTwin shall throws IllegalArgumentException.] */
    @Test (expected = IllegalArgumentException.class)
    public void updateTwin_json_unknownProperty_failed()
    {
        // Arrange
        Twin twin = new Twin();

        String json = "{\"property\":{" +
                "\"barProperty\":{\"key3\":null,\"key1\":\"value4\"}," +
                "\"reported\":{\"key1\":null,\"key5\":null,\"key7\":null}}}";

        // Act
        twin.updateTwin(json);

        // Assert
    }

    /* Codes_SRS_TWIN_21_043: [If the provided json is not valid, the updateTwin shall throws IllegalArgumentException.] */
    @Test (expected = IllegalArgumentException.class)
    public void updateTwin_json_InvalidKey_failed()
    {
        // Arrange
        Twin twin = new Twin();

        String json = "{\"properties\":{" +
                "\"desired\":{\"\":null,\"key1\":\"value4\"}," +
                "\"reported\":{\"key1\":null,\"key5\":null,\"key7\":null}}}";

        // Act
        twin.updateTwin(json);

        // Assert
    }

    /* Codes_SRS_TWIN_21_043: [If the provided json is not valid, the updateTwin shall throws IllegalArgumentException.] */
    @Test (expected = IllegalArgumentException.class)
    public void updateTwin_json_noKey_failed()
    {
        // Arrange
        Twin twin = new Twin();

        String json = "{\"properties\":{" +
                "\"desired\":{:null,\"key1\":\"value4\"}," +
                "\"reported\":{\"key1\":null,\"key5\":null,\"key7\":null}}}";

        // Act
        twin.updateTwin(json);

        // Assert
    }

    /* Codes_SRS_TWIN_21_043: [If the provided json is not valid, the updateTwin shall throws IllegalArgumentException.] */
    @Test (expected = IllegalArgumentException.class)
    public void updateTwin_json_InvalidValue_failed()
    {
        // Arrange
        Twin twin = new Twin();

        String json = "{\"properties\":{" +
                "\"desired\":{\"Key3\":,\"key1\":\"value4\"}," +
                "\"reported\":{\"key1\":null,\"key5\":null,\"key7\":null}}}";

        // Act
        twin.updateTwin(json);

        // Assert
    }

}
