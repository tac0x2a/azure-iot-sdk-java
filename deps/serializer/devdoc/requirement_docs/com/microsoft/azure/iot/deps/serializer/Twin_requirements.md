# Twin Requirements

## Overview

Twin is a representation of the device twin database.

## References

Session 7.3.1 of Azure IoT Hub - Device Twin.

## Exposed API

```java
public class Twin extends IoTHubDeviceProperties
{
    public Twin();
    public Twin(TwinPropertiesChangeCallback onDesiredCallback);
    public Twin(TwinPropertiesChangeCallback onDesiredCallback, 
                TwinPropertiesChangeCallback onReportedCallback);
    
    public void setDesiredCallback(TwinPropertiesChangeCallback callback);
    public void setReportedCallback(TwinPropertiesChangeCallback callback);
    public void enableTags();
    public void enableMetadata();

    public void updateTwin(String json);
    public void updateDesiredProperty(String json);
    public void updateReportedProperty(String json);

    public String updateDesiredProperty(HashMap<String, String> property);
    public String updateReportedProperty(HashMap<String, String> property);

    public Integer getDesiredPropertyVersion();
    public Integer getReportedPropertyVersion();
    
    public HashMap<String, String> getDesiredPropertyMap();
    public HashMap<String, String> getReportedPropertyMap();
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

**SRS_TWIN_21_005: [**The constructor shall call the standard constructor.**]**  
**SRS_TWIN_21_006: [**The constructor shall set OnDesiredCallback with the provided Callback function.**]**  
**SRS_TWIN_21_007: [**The constructor shall set OnReportedCallback as null.**]**  
**SRS_TWIN_21_008: [**The constructor shall set Tags as null.**]**  


### Twin

```java
    public Twin(TwinPropertiesChangeCallback onDesiredCallback, 
                TwinPropertiesChangeCallback onReportedCallback)
```

**SRS_TWIN_21_009: [**The constructor shall call the standard constructor.**]**  
**SRS_TWIN_21_010: [**The constructor shall set OnDesiredCallback with the provided Callback function.**]**  
**SRS_TWIN_21_011: [**The constructor shall set OnReportedCallback with the provided Callback function.**]**  
**SRS_TWIN_21_012: [**The constructor shall set Tags as null.**]**  


### setDesiredCallback

```java
    public void setDesiredCallback(TwinPropertiesChangeCallback callback)
```

**SRS_TWIN_21_013: [**The setDesiredCallback shall set OnDesiredCallback with the provided Callback function.**]**  


### setReportedCallback

```java
    public void setReportedCallback(TwinPropertiesChangeCallback callback)
```

**SRS_TWIN_21_014: [**The setReportedCallback shall set OnReportedCallback with the provided Callback function.**]**  


### toJson

```java
    public String toJson()
```

**SRS_TWIN_21_015: [**The toJson shall create a String with information in the Twin using json format.**]**  
**SRS_TWIN_21_016: [**The toJson shall not include null fields.**]**  


### toJsonElement

```java
    public String toJsonElement()
```

**SRS_TWIN_21_017: [**The toJsonElement shall return a JsonElement with information in the Twin using json format.**]**  
**SRS_TWIN_21_018: [**The toJson shall not include null fields.**]**  


### enableTags

```java
    public void enableTags();
```
**SRS_TWIN_21_019: [**The enableTags shall create an instance of the Tags class.**]**  


### enableMetadata

```java
    public void enableMetadata();
```

**SRS_TWIN_21_020: [**The enableMetadata shall enable report metadata in Json for the Desired and for the Reported Properties.**]**  


### updateDesiredProperty

```java
    public String updateDesiredProperty(HashMap<String, Object> property)
```

**SRS_TWIN_21_021: [**The updateDesiredProperty shall add all provided properties to the Desired property.**]**  
**SRS_TWIN_21_022: [**The updateDesiredProperty shall return a string with json representing the desired properties with changes.**]**  
**SRS_TWIN_21_023: [**If the provided `property` map is null, the updateDesiredProperty shall return null.**]**  
**SRS_TWIN_21_024: [**If no Desired property changed its value, the updateDesiredProperty shall return null.**]**  


### updateReportedProperty

```java
    public String updateReportedProperty(HashMap<String, Object> property)
```

**SRS_TWIN_21_025: [**The updateReportedProperty shall add all provided properties to the Reported property.**]**  
**SRS_TWIN_21_026: [**The updateReportedProperty shall return a string with json representing the Reported properties with changes.**]**  
**SRS_TWIN_21_027: [**If the provided `property` map is null, the updateReportedProperty shall return null.**]**  
**SRS_TWIN_21_028: [**If no Reported property changed its value, the updateReportedProperty shall return null.**]**  


### updateDesiredProperty

```java
    public void updateDesiredProperty(String json)
```

**SRS_TWIN_21_029: [**The updateDesiredProperty shall update the Desired property using the information provided in the json.**]**  
**SRS_TWIN_21_030: [**The updateDesiredProperty shall generate a map with all pairs key value that had its content changed.**]**  
**SRS_TWIN_21_031: [**The updateDesiredProperty shall send the map with all changed pairs to the upper layer calling onDesiredCallback (TwinPropertiesChangeCallback).**]**  
**SRS_TWIN_21_032: [**If the OnDesiredCallback is set as null, the updateDesiredProperty shall discard the map with the changed pairs.**]**  
**SRS_TWIN_21_033: [**If there is no change in the Desired property, the updateDesiredProperty shall not call the OnDesiredCallback.**]**  


### updateReportedProperty

```java
    public void updateReportedProperty(String json)
```

**SRS_TWIN_21_034: [**The updateReportedProperty shall update the Reported property using the information provided in the json.**]**  
**SRS_TWIN_21_035: [**The updateReportedProperty shall generate a map with all pairs key value that had its content changed.**]**  
**SRS_TWIN_21_036: [**The updateReportedProperty shall send the map with all changed pairs to the upper layer calling onReportedCallback (TwinPropertiesChangeCallback).**]**  
**SRS_TWIN_21_037: [**If the OnReportedCallback is set as null, the updateReportedProperty shall discard the map with the changed pairs.**]**  
**SRS_TWIN_21_038: [**If there is no change in the Reported property, the updateReportedProperty shall not call the OnReportedCallback.**]**  


### updateTwin

```java
    public void updateTwin(String json)
```

**SRS_TWIN_21_039: [**The updateTwin shall fill the fields the properties in the Twin class with the keys and values provided in the json string.**]**  
**SRS_TWIN_21_040: [**The updateTwin shall not change fields that is not reported in the json string.**]**  
**SRS_TWIN_21_041: [**The updateTwin shall create a list with all properties that was updated (new key or value) by the new json.**]**  
**SRS_TWIN_21_042: [**If a valid key has a null value, the updateTwin shall delete this property.**]**  
**SRS_TWIN_21_043: [**If the provided json is not valid, the updateTwin shall throws IllegalArgumentException.**]**  
**SRS_TWIN_21_044: [**If OnDesiredCallback was provided, the updateTwin shall create a new map with a copy of all pars key values updated by the json in the Desired property, and OnDesiredCallback passing this map as parameter.**]**  
**SRS_TWIN_21_045: [**If OnReportedCallback was provided, the updateTwin shall create a new map with a copy of all pars key values updated by the json in the Reported property, and OnReportedCallback passing this map as parameter.**]**  
**SRS_TWIN_21_046: [**If OnDesiredCallback was not provided, the updateTwin shall not do anything with the list of updated desired properties.**]**  
**SRS_TWIN_21_047: [**If OnReportedCallback was not provided, the updateTwin shall not do anything with the list of updated reported properties.**]**  


### getDesiredPropertyVersion

```java
    public Integer getDesiredPropertyVersion()
```

**SRS_TWIN_21_048: [**The getDesiredPropertyVersion shall return the desired property version.**]**  


### getReportedPropertyVersion

```java
    public Integer getReportedPropertyVersion()
```

**SRS_TWIN_21_049: [**The getReportedPropertyVersion shall return the reported property version.**]**  


### getDesiredPropertyMap

```java
    public HashMap<String, String> getDesiredPropertyMap()
```

**SRS_TWIN_21_050: [**The getDesiredPropertyMap shall return a map with all desired property key value pairs.**]**  

### getReportedPropertyMap

```java
    public HashMap<String, String> getReportedPropertyMap()
```

**SRS_TWIN_21_051: [**The getReportedPropertyMap shall return a map with all reported property key value pairs.**]**  
