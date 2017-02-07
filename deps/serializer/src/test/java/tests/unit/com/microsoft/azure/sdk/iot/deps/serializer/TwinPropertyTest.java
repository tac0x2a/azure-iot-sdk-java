// Copyright (c) Microsoft. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package tests.unit.com.microsoft.azure.sdk.iot.deps.serializer;

import com.microsoft.azure.sdk.iot.deps.serializer.TwinMetadata;
import com.microsoft.azure.sdk.iot.deps.serializer.TwinProperty;
import org.junit.Test;

import java.util.HashMap;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Unit tests for Twin Property serializer
 */
public class TwinPropertyTest {

    private static final String bigString_150chars =
            "01234567890123456789012345678901234567890123456789" +
                    "01234567890123456789012345678901234567890123456789" +
                    "01234567890123456789012345678901234567890123456789";
    private static final String specialCharString = "value special chars !@#$%^&*()_";
    private static final String illegalString_Dot = "illegal.key";
    private static final String illegalString_Space = "illegal key";
    private static final String illegalString_Dollar = "illegal$key";

    /* Tests_SRS_TWIN_PROPERTY_21_001: [The constructor shall call the constructor for the superClass.] */
    /* Tests_SRS_TWIN_PROPERTY_21_002: [The constructor shall store set version equals 0.] */
    /* Tests_SRS_TWIN_PROPERTY_21_009: [The GetVersion shall return an Integer with the property version stored in the `version`.] */
    @Test
    public void constructor_succeed()
    {
        // Arrange
        // Act
        TwinProperty twinProperty = new TwinProperty();

        // Assert
        assertNotNull(twinProperty);
        assertThat(twinProperty.GetVersion(), is(0));
    }

    /* Tests_SRS_TWIN_PROPERTY_21_003: [The constructor shall call the constructor for the superClass.] */
    /* Tests_SRS_TWIN_PROPERTY_21_004: [The constructor shall store set version equals 0.] */
    /* Tests_SRS_TWIN_PROPERTY_21_006: [If reportMetadata is false, constructor shall not create a instance of the TwinMetadata keeping it as null.] */
    /* Tests_SRS_TWIN_PROPERTY_21_020: [If there is no metadata, the GetMetadata shall return null.] */
    @Test
    public void constructor_metadata_false_succeed()
    {
        // Arrange
        // Act
        TwinProperty twinProperty = new TwinProperty(false);

        // Assert
        assertNotNull(twinProperty);
        assertThat(twinProperty.GetVersion(), is(0));
        assertNull(twinProperty.GetMetadata());
    }

    /* Tests_SRS_TWIN_PROPERTY_21_005: [If reportMetadata is true, constructor shall create a instance of the TwinMetadata to store the property metadata.] */
    /* Tests_SRS_TWIN_PROPERTY_21_012: [The GetMetadata shall return a hashMap to all metadata.] */
    @Test
    public void constructor_metadata_true_succeed()
    {
        // Arrange
        // Act
        TwinProperty twinProperty = new TwinProperty(true);

        // Assert
        assertNotNull(twinProperty);
        assertThat(twinProperty.GetVersion(), is(0));
        assertNotNull(twinProperty.GetMetadata());
    }

    /* Tests_SRS_TWIN_PROPERTY_21_007: [The addProperty shall add the provided pair key value in the superClass' hashMap.] */
    /* Tests_SRS_TWIN_PROPERTY_21_011: [If the metadata is null, the addProperty shall not create or add any metadata to it.] */
    @Test
    public void addProperty_noMetadata_succeed()
    {
        // Arrange
        TwinProperty twinProperty = new TwinProperty(false);

        // Act
        twinProperty.addProperty("key1", "value1", 3);

        // Assert
        assertEquals(twinProperty.size(), 1);
        assertThat(twinProperty.get("key1").toString(), is("value1"));
        assertNull(twinProperty.GetMetadata());
    }

    /* Tests_SRS_TWIN_PROPERTY_21_008: [The addProperty shall create an instance of the metadata related to the provided key and version.] */
    /* Tests_SRS_TWIN_PROPERTY_21_010: [The addProperty shall add the created metadata to the `metadata`.] */
    @Test
    public void addProperty_metadata_succeed()
    {
        // Arrange
        TwinProperty twinProperty = new TwinProperty(true);

        // Act
        twinProperty.addProperty("key1", "value1", 3);

        // Assert
        assertEquals(twinProperty.size(), 1);
        assertThat(twinProperty.get("key1").toString(), is("value1"));
        assertThat(twinProperty.GetMetadata("key1").GetLastUpdateVersion(), is(3));
    }

    /* Tests_SRS_TWIN_PROPERTY_21_013: [If the `key` is null, the addProperty shall throw IllegalArgumentException.] */
    @Test (expected = IllegalArgumentException.class)
    public void addProperty_null_key_failed()
    {
        // Arrange
        TwinProperty twinProperty = new TwinProperty();

        // Act
        twinProperty.addProperty(null, "value1", 3);

        // Assert
    }

    /* Tests_SRS_TWIN_PROPERTY_21_014: [If the `key` is empty, the addProperty shall throw IllegalArgumentException.] */
    @Test (expected = IllegalArgumentException.class)
    public void addProperty_empty_key_failed()
    {
        // Arrange
        TwinProperty twinProperty = new TwinProperty();

        // Act
        twinProperty.addProperty("", "value1", 3);

        // Assert
    }

    /* Tests_SRS_TWIN_PROPERTY_21_015: [If the `key` is more than 128 characters long, the addProperty shall throw IllegalArgumentException.] */
    @Test (expected = IllegalArgumentException.class)
    public void addProperty_big_key_failed()
    {
        // Arrange
        TwinProperty twinProperty = new TwinProperty();

        // Act
        twinProperty.addProperty(bigString_150chars, "value1", 3);

        // Assert
    }

    /* Tests_SRS_TWIN_PROPERTY_21_016: [If the `key` has an illegal character, the addProperty shall throw IllegalArgumentException.] */
    @Test (expected = IllegalArgumentException.class)
    public void addProperty_illegalDot_key_failed()
    {
        // Arrange
        TwinProperty twinProperty = new TwinProperty();

        // Act
        twinProperty.addProperty(illegalString_Dot, "value1", 3);

        // Assert
    }

    /* Tests_SRS_TWIN_PROPERTY_21_016: [If the `key` has an illegal character, the addProperty shall throw IllegalArgumentException.] */
    @Test (expected = IllegalArgumentException.class)
    public void addProperty_illegalSpace_key_failed()
    {
        // Arrange
        TwinProperty twinProperty = new TwinProperty();

        // Act
        twinProperty.addProperty(illegalString_Space, "value1", 3);

        // Assert
    }

    /* Tests_SRS_TWIN_PROPERTY_21_016: [If the `key` has an illegal character, the addProperty shall throw IllegalArgumentException.] */
    @Test (expected = IllegalArgumentException.class)
    public void addProperty_illegalDollar_key_failed()
    {
        // Arrange
        TwinProperty twinProperty = new TwinProperty();

        // Act
        twinProperty.addProperty(illegalString_Dollar, "value1", 3);

        // Assert
    }

    /* Tests_SRS_TWIN_PROPERTY_21_017: [If the `key` already exists, the addProperty shall replace the existed value by the new one.] */
    @Test
    public void addProperty_duplicate_key_succeed()
    {
        // Arrange
        TwinProperty twinProperty = new TwinProperty();

        // Act
        twinProperty.addProperty("key1", "value1", 3);
        twinProperty.addProperty("key1", "value2", 3);

        // Assert
        assertThat(twinProperty.get("key1").toString(), is("value2"));
    }

    /* Tests_SRS_TWIN_PROPERTY_21_018: [If the `value` is null, the addProperty shall throw IllegalArgumentException.] */
    @Test (expected = IllegalArgumentException.class)
    public void addProperty_null_value_failed()
    {
        // Arrange
        TwinProperty twinProperty = new TwinProperty();

        // Act
        twinProperty.addProperty("key1", null, 3);

        // Assert
    }

    /* Tests_SRS_TWIN_PROPERTY_21_019: [If the `value` is empty, the addProperty shall throw IllegalArgumentException.] */
    @Test (expected = IllegalArgumentException.class)
    public void addProperty_empty_value_failed()
    {
        // Arrange
        TwinProperty twinProperty = new TwinProperty();

        // Act
        twinProperty.addProperty("key1", "", 3);

        // Assert
    }

    /* Tests_SRS_TWIN_PROPERTY_21_007: [The addProperty shall add the provided pair key value in the superClass' hashMap.] */
    @Test
    public void addProperty_big_value_succeed()
    {
        // Arrange
        TwinProperty twinProperty = new TwinProperty();

        // Act
        twinProperty.addProperty("key1", bigString_150chars, 3);

        // Assert
        assertThat(twinProperty.get("key1").toString(), is(bigString_150chars));
    }

    /* Tests_SRS_TWIN_PROPERTY_21_007: [The addProperty shall add the provided pair key value in the superClass' hashMap.] */
    @Test
    public void addProperty_specialChar_value_succeed()
    {
        // Arrange
        TwinProperty twinProperty = new TwinProperty();

        // Act
        twinProperty.addProperty("key1", specialCharString, 3);

        // Assert
        assertThat(twinProperty.get("key1").toString(), is(specialCharString));
    }

    /* Tests_SRS_TWIN_PROPERTY_21_021: [The GetMetadata shall return the TwinMetadata for the provided key.] */
    @Test
    public void getMetadata_key_succeed()
    {
        // Arrange
        TwinProperty twinProperty = new TwinProperty(true);

        // Act
        twinProperty.addProperty("key1", "value1", 3);

        // Assert
        assertThat(twinProperty.GetMetadata("key1").GetLastUpdateVersion(), is(3));
    }

    /* Tests_SRS_TWIN_PROPERTY_21_022: [If there is no metadata, the GetMetadata shall return null.] */
    @Test
    public void getMetadata_noMetadata_succeed()
    {
        // Arrange
        TwinProperty twinProperty = new TwinProperty(false);

        // Act
        twinProperty.addProperty("key1", "value1", 3);

        // Assert
        assertNull(twinProperty.GetMetadata("key1"));
    }

    /* Tests_SRS_TWIN_PROPERTY_21_023: [If the key do not exists, the GetMetadata shall return null.] */
    @Test
    public void getMetadata_noKey_succeed()
    {
        // Arrange
        TwinProperty twinProperty = new TwinProperty(true);

        // Act
        twinProperty.addProperty("key1", "value1", 3);

        // Assert
        assertNull(twinProperty.GetMetadata("key2"));
    }

    /* Codes_SRS_TWIN_PROPERTY_21_024: [The GetPropertyMap shall return a hashMap with all keys and values stored on the Twin Property.] */
    @Test
    public void getPropertyMap_succeed()
    {
        // Arrange
        TwinProperty twinProperty = new TwinProperty();
        twinProperty.addProperty("key1", "value1", 3);
        twinProperty.addProperty("key2", "value2", 3);
        twinProperty.addProperty("key3", "value3", 3);

        // Act
        HashMap<String, String> propertyMap = twinProperty.GetPropertyMap();

        // Assert
        assertThat(propertyMap.size(), is(3));
        assertThat(propertyMap.get("key1"), is("value1"));
        assertThat(propertyMap.get("key2"), is("value2"));
        assertThat(propertyMap.get("key3"), is("value3"));
    }

    /* Codes_SRS_TWIN_PROPERTY_21_025: [If there is no key value, the GetPropertyMap shall return a null hashMap.] */
    @Test
    public void getPropertyMap_empty_failed()
    {
        // Arrange
        TwinProperty twinProperty = new TwinProperty();

        // Act
        HashMap<String, String> propertyMap = twinProperty.GetPropertyMap();

        // Assert
        assertNull(propertyMap);
    }

    /* Codes_SRS_TWIN_PROPERTY_21_026: [The toJson shall create a String with information in the TwinProperty using json format.] */
    /* Codes_SRS_TWIN_PROPERTY_21_027: [The toJson shall not include null fields.] */
    @Test
    public void toJson_noMetadata_succeed()
    {
        // Arrange
        TwinProperty twinProperty = new TwinProperty();
        twinProperty.addProperty("key1", "value1", 3);
        twinProperty.addProperty("key2", "value2", 3);
        twinProperty.addProperty("key3", "value3", 3);

        // Act
        String json = twinProperty.toJson();

        // Assert
        assertThat(json, is("{\"key1\":\"value1\",\"key2\":\"value2\",\"key3\":\"value3\"}"));
    }

    /* Codes_SRS_TWIN_PROPERTY_21_026: [The toJson shall create a String with information in the TwinProperty using json format.] */
    @Test
    public void toJson_withMetadata_succeed()
    {
        // Arrange
        TwinProperty twinProperty = new TwinProperty(true);
        twinProperty.addProperty("key1", "value1", 3);
        twinProperty.addProperty("key2", "value2", 3);
        twinProperty.addProperty("key3", "value3", 3);

        // Act
        String json = twinProperty.toJson();

        TwinProperty result = new TwinProperty();
        result.fromJson(json);

        // Assert
        assertThat(json, is("{\"key1\":\"value1\",\"key2\":\"value2\",\"key3\":\"value3\",\"$version\":0,\"$metadata\":{\"key1\":{\"$lastUpdated\":\"2017-02-07T04:04:35.0965Z\",\"$lastUpdatedVersion\":3},\"key2\":{\"$lastUpdated\":\"2017-02-07T04:04:35.0965Z\",\"$lastUpdatedVersion\":3},\"key3\":{\"$lastUpdated\":\"2017-02-07T04:04:35.0965Z\",\"$lastUpdatedVersion\":3}}}"));
    }

    /* Codes_SRS_TWIN_PROPERTY_21_028: [The fromJson shall fill the fields in TwinProperty with the values provided in the json string.] */
    /* Codes_SRS_TWIN_PROPERTY_21_029: [The fromJson shall not change fields that is not reported in the json string.] */

}
