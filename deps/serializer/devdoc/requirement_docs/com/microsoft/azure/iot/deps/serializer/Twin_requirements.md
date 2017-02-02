# Twin Requirements

## Overview

Twin is a representation of the device twin database as described on session 7.3.1 of Azure IoT Hub - Device Twin.

## References

## Exposed API

```java
public class Twin extends IoTHubDeviceProperties
{
    public Twin()
    public Twin(TwinPropertiesChangeCallback onDesiredCallback)
    public Twin(TwinPropertiesChangeCallback onDesiredCallback, TwinPropertiesChangeCallback onReportedCallback)
    public void setDesiredCallback(TwinPropertiesChangeCallback callback)
    public void setReportedCallback(TwinPropertiesChangeCallback callback)

    public void updateTwin(String json)
    public void updateDesiredProperty(String json)
    public void updateReportedProperty(String json)

    public String updateDesiredProperty(HashMap<String, String> property)
    public String updateReportedProperty(HashMap<String, String> property)

    public Integer GetDesiredPropertyVersion()
    public Integer GetReportedPropertyVersion()
}
```


### Twin

```java
    public Twin()
```

**SRS_TWIN_21_001: [**The constructor shall create an instance of the properties.**]**  
**SRS_TWIN_21_002: [**The constructor shall set OnDesiredCallback as null.**]**  
**SRS_TWIN_21_003: [**The constructor shall set OnReportedCallback as null.**]**  
**SRS_TWIN_21_004: [**The constructor shall set Tags as null.**]**  


### Twin

```java
    public Twin(TwinPropertiesChangeCallback onDesiredCallback)
```

**SRS_TWIN_21_005: [**The constructor shall call the satandard constructor.**]**  
**SRS_TWIN_21_006: [**The constructor shall set OnDesiredCallback with the provided Callback function.**]**  
**SRS_TWIN_21_007: [**The constructor shall set OnReportedCallback as null.**]**  
**SRS_TWIN_21_008: [**The constructor shall set Tags as null.**]**  


### Twin

```java
    public Twin(TwinPropertiesChangeCallback onDesiredCallback, TwinPropertiesChangeCallback onReportedCallback)
```

**SRS_TWIN_21_009: [**The constructor shall call the satandard constructor.**]**  
**SRS_TWIN_21_010: [**The constructor shall set OnDesiredCallback with the provided Callback function.**]**  
**SRS_TWIN_21_011: [**The constructor shall set OnReportedCallback with the provided Callback function.**]**  
**SRS_TWIN_21_012: [**The constructor shall set Tags as null.**]**  


### setDesiredCallback

```java
    public void setDesiredCallback(TwinPropertiesChangeCallback callback)
```

**SRS_TWIN_21_012: [**The constructor shall set OnDesiredCallback with the provided Callback function.**]**  


### setReportedCallback

```java
    public void setReportedCallback(TwinPropertiesChangeCallback callback)
```

**SRS_TWIN_21_013: [**The constructor shall set OnReportedCallback with the provided Callback function.**]**  


### updateTwin

```java
    public void updateTwin(String json)
```

**SRS_TWIN_21_014: [**The updateTwin shall fill the fields the properties in the Twin class with the keys and values provided in the json string.**]**  
**SRS_TWIN_21_015: [**The updateTwin shall not change fields that is not reported in the json string.**]**  
**SRS_TWIN_21_016: [**The updateTwin shall create a list with all properties that was updated (new key or value) by the new json.**]**  
**SRS_TWIN_21_017: [**If a valid key has a null value, the updateTwin shall delete this property.**]**  
**SRS_TWIN_21_018: [**If the provided json is not valid, the updateTwin shall throws IllegalArgumentException.**]**  
**SRS_TWIN_21_019: [**If OnDesiredCallback was provided, the updateTwin shall create a new map with a copy of all pars key values updated by the json in the Desired property, and OnDesiredCallback passing this map as parameter.**]**  
**SRS_TWIN_21_020: [**If OnReportedCallback was provided, the updateTwin shall create a new map with a copy of all pars key values updated by the json in the Reported property, and OnReportedCallback passing this map as parameter.**]**  
**SRS_TWIN_21_021: [**If OnDesiredCallback was not provided, the updateTwin shall not do anything with the list of updated desired properties.**]**  
**SRS_TWIN_21_022: [**If OnReportedCallback was not provided, the updateTwin shall not do anything with the list of updated reported properties.**]**  






    public void updateDesiredProperty(String json)
    public void updateReportedProperty(String json)

    public String updateDesiredProperty(HashMap<String, String> property)
    public String updateReportedProperty(HashMap<String, String> property)

    public Integer GetDesiredPropertyVersion()
    public Integer GetReportedPropertyVersion()




