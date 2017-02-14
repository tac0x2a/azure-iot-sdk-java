// Copyright (c) Microsoft. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.


package tests.unit.com.microsoft.azure.sdk.iot.device.DeviceTwin;

import com.microsoft.azure.sdk.iot.device.DeviceTwin.Device;
import com.microsoft.azure.sdk.iot.device.DeviceTwin.Pair;
import com.microsoft.azure.sdk.iot.device.DeviceTwin.Property;
import com.microsoft.azure.sdk.iot.device.DeviceTwin.PropertyCallBack;
import mockit.Deencapsulation;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;

import static org.junit.Assert.*;

public class DeviceTest
{
    @Test
    public void constructorCreatesNewMapsForProperties()
    {
        //act
        Device testDev = new Device()
        {
            @Override
            public void PropertyCall(String propertyKey, Object propertyValue, Object context)
            {

            }
        };

        //assert
        HashSet reported = Deencapsulation.getField(testDev, "reportedProp");
        HashMap desired = Deencapsulation.getField(testDev, "desiredProp");

        assertNotNull(reported);
        assertNotNull(desired);

    }

    /*
    **Tests_SRS_DEVICE_25_001: [**This method shall return a HashSet of properties that user has set by calling hasReportedProp.**]**
     */
    @Test
    public void getReportedPropertiesReturnsMapsOfProperties()
    {
        //arrange
        Device testDev = new Device()
        {
            @Override
            public void PropertyCall(String propertyKey, Object propertyValue, Object context)
            {

            }
        };

        Property test = new Property("RepProp1", "RepValue1");

        //act
        testDev.hasReportedProp(test);
        HashSet<Property> testRepProp = testDev.getReportedProp();

        //assert
        assertTrue(testRepProp.contains(test));

    }

    /*
    **Tests_SRS_DEVICE_25_002: [**The function shall add the new property to the map.**]**
     */
    @Test
    public void hasReportedPropertyAddsToReportedProperty()
    {
        //arrange
        Device testDev = new Device()
        {
            @Override
            public void PropertyCall(String propertyKey, Object propertyValue, Object context)
            {

            }
        };

        Property test = new Property("RepProp1", "RepValue1");

        //act
        testDev.hasReportedProp(test);

        //assert
        HashSet<Property> testRepProp = testDev.getReportedProp();
        assertTrue(testRepProp.contains(test));

    }

    /*
    **Tests_SRS_DEVICE_25_003: [**If the already existing property is altered and added then the this method shall replace the old one.**]**
     */
    @Test
    public void hasReportedPropertyAddsToAlteredReportedProperty()
    {
        //arrange
        Device testDev = new Device()
        {
            @Override
            public void PropertyCall(String propertyKey, Object propertyValue, Object context)
            {

            }
        };

        Property test = new Property("RepProp1", "RepValue1");
        testDev.hasReportedProp(test);
        test.setValue("RepValue2");

        //act
        testDev.hasReportedProp(test);


        //assert
        HashSet<Property> testRepProp = testDev.getReportedProp();
        assertTrue(testRepProp.contains(test));
        assertTrue(testRepProp.size() == 1);

    }

    /*
    **Tests_SRS_DEVICE_25_004: [**If the parameter reportedProp is null then this method shall throw IllegalArgumentException**]**
     */
    @Test (expected = IllegalArgumentException.class)
    public void hasReportedPropertyCannotAddNullProperty()
    {
        //arrange
        Device testDev = new Device()
        {
            @Override
            public void PropertyCall(String propertyKey, Object propertyValue, Object context)
            {

            }
        };

        //act
        testDev.hasReportedProp(null);
    }

    /*
    **Tests_SRS_DEVICE_25_005: [**The function shall return the HashMap containing the property and its callback and context pair set by the user so far.**]**
     */
    @Test
    public void getDesiredPropertiesReturnsMapsOfProperties()
    {
        //arrange
        Device testDev = new Device()
        {
            @Override
            public void PropertyCall(String propertyKey, Object propertyValue, Object context)
            {

            }
        };

        Property test = new Property("DesiredProp1", null);
        testDev.hasDesiredProperty(test, testDev, null);

        //act
        HashMap<Property, Pair<PropertyCallBack<String, Object>, Object>> testDesiredMap = testDev.getDesiredProp();

        //assert
        assertNotNull(testDesiredMap);
        assertEquals(testDesiredMap.get(test).getKey(), testDev);
    }

    /*
    **Tests_SRS_DEVICE_25_006: [**The function shall add the property and its callback and context pair to the user map of desired properties.**]**
     */
    @Test
    public void hasDesiredPropertyAddsToDesiredProperty()
    {
        //arrange
        Device testDev = new Device()
        {
            @Override
            public void PropertyCall(String propertyKey, Object propertyValue, Object context)
            {

            }
        };

        Property test = new Property("DesiredProp1", null);

        //act
        testDev.hasDesiredProperty(test, testDev, null);

        //assert
        HashMap<Property, Pair<PropertyCallBack<String, Object>, Object>> testDesiredMap = testDev.getDesiredProp();
        assertNotNull(testDesiredMap);
        assertEquals(testDesiredMap.get(test).getKey(), testDev);
    }

    /*
    **Tests_SRS_DEVICE_25_007: [**If the parameter desiredProp is null then this method shall throw IllegalArgumentException**]**
     */
    @Test (expected = IllegalArgumentException.class)
    public void hasDesiredPropertyCannotAddNullProperty()
    {
        //arrange
        Device testDev = new Device()
        {
            @Override
            public void PropertyCall(String propertyKey, Object propertyValue, Object context)
            {

            }
        };

        Property test = new Property("DesiredProp1", null);

        //act
        testDev.hasDesiredProperty(null, testDev, null);

    }

    /*
    **Tests_SRS_DEVICE_25_008: [**This method shall add the parameters to the map even if callback and object pair are null**]**
     */
    @Test
    public void hasDesiredPropertyCanAddNullPair()
    {
        //arrange
        Device testDev = new Device()
        {
            @Override
            public void PropertyCall(String propertyKey, Object propertyValue, Object context)
            {

            }
        };

        Property test = new Property("DesiredProp1", null);

        //act
        testDev.hasDesiredProperty(test, null, null);

        //assert
        HashMap<Property, Pair<PropertyCallBack<String, Object>, Object>> testDesiredMap = testDev.getDesiredProp();
        assertNotNull(testDesiredMap);
        assertEquals(testDesiredMap.get(test).getKey(), null);
    }


    @Test
    public void hasDesiredPropertyCanAddNullCB()
    {
        //arrange
        Device testDev = new Device()
        {
            @Override
            public void PropertyCall(String propertyKey, Object propertyValue, Object context)
            {

            }
        };

        Property test = new Property("DesiredProp1", null);

        //act
        testDev.hasDesiredProperty(test, null, null);

        //assert
        HashMap<Property, Pair<PropertyCallBack<String, Object>, Object>> testDesiredMap = testDev.getDesiredProp();
        assertNotNull(testDesiredMap);
        assertEquals(testDesiredMap.get(test).getKey(), null);

    }

    /*
    **Tests_SRS_DEVICE_25_009: [**The method shall remove all the reported and desired properties set by the user so far and mark existing collections as null to be garbage collected.**]**
     */
    @Test
    public void freeEmptiesAllProperties()
    {
        //arrange
        Device testDev = new Device()
        {
            @Override
            public void PropertyCall(String propertyKey, Object propertyValue, Object context)
            {

            }
        };

        Property testDes = new Property("DesiredProp1", null);
        Property testRep = new Property("DesiredProp1", null);
        testDev.hasDesiredProperty(testDes, testDev, null);
        testDev.hasReportedProp(testRep);

        //act
        testDev.free();

        //assert
        HashMap<Property, Pair<PropertyCallBack<String, Object>, Object>> testDesiredMap = testDev.getDesiredProp();
        HashSet<Property> testRepMap = testDev.getReportedProp();

        assertNull(testDesiredMap);
        assertNull(testRepMap);
    }

}
