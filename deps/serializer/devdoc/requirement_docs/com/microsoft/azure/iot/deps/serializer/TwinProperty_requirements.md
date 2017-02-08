# TwinProperty Requirements

## Overview

TwinProperty is a representation of the device twin property database. It can represent `Desired` property as well `Reported` property.


## References

session 7.3.1 of Azure IoT Hub - Device Twin.


## Exposed API

```java
public class TwinProperty extends HashMap<String, Object> {

    public TwinProperty();
    public TwinProperty(Boolean reportMetadata);
    public void addProperty(String key, Object value, Integer version) throws IllegalArgumentException;

    public int size();
    public Object get(String key);

    public Integer GetVersion();
    public HashMap<String, TwinMetadata> GetMetadata();
    public TwinMetadata GetMetadata(String key);
    public HashMap<String, String> GetPropertyMap();

    public String toJson();
    public void fromJson(String json);
}
```


### TwinProperty

```java
    public TwinProperty();
```

**SRS_TWIN_PROPERTY_21_001: [**The constructor shall call the constructor for the superClass.**]**  
**SRS_TWIN_PROPERTY_21_002: [**The constructor shall store set version equals 0.**]**  

```java
    public TwinProperty(Boolean reportMetadata);
```

**SRS_TWIN_PROPERTY_21_003: [**The constructor shall call the constructor for the superClass.**]**  
**SRS_TWIN_PROPERTY_21_004: [**The constructor shall store set version equals 0.**]**  
**SRS_TWIN_PROPERTY_21_005: [**If reportMetadata is true, constructor shall create a instance of the TwinMetadata to store the property metadata.**]**  
**SRS_TWIN_PROPERTY_21_006: [**If reportMetadata is false, constructor shall not create a instance of the TwinMetadata keeping it as null.**]**  

### addProperty

```java
    public void addProperty(String key, Object value, Integer version) throws IllegalArgumentException
```

**SRS_TWIN_PROPERTY_21_007: [**The addProperty shall add the provided pair key value in the superClass' hashMap.**]**  
**SRS_TWIN_PROPERTY_21_008: [**The addProperty shall create an instance of the metadata related to the provided key and version.**]**  
**SRS_TWIN_PROPERTY_21_010: [**The addProperty shall add the created metadata to the `metadata`.**]**  
**SRS_TWIN_PROPERTY_21_011: [**If the metadata is null, the addProperty shall not create or add any metadata to it.**]**  
**SRS_TWIN_PROPERTY_21_013: [**If the `key` is null, the addProperty shall throw IllegalArgumentException.**]**  
**SRS_TWIN_PROPERTY_21_014: [**If the `key` is empty, the addProperty shall throw IllegalArgumentException.**]**  
**SRS_TWIN_PROPERTY_21_015: [**If the `key` is more than 128 characters long, the addProperty shall throw IllegalArgumentException.**]**  
**SRS_TWIN_PROPERTY_21_016: [**If the `key` has an illegal character, the addProperty shall throw IllegalArgumentException.**]**  
**SRS_TWIN_PROPERTY_21_017: [**If the `key` already exists, the addProperty shall replace the existed value by the new one.**]**  
**SRS_TWIN_PROPERTY_21_018: [**If the `value` is null, the addProperty shall throw IllegalArgumentException.**]**  
**SRS_TWIN_PROPERTY_21_019: [**If the `value` is empty, the addProperty shall throw IllegalArgumentException.**]**  


### GetVersion

```java
    public Integer GetVersion();
```

**SRS_TWIN_PROPERTY_21_009: [**The GetVersion shall return an Integer with the property version stored in the `version`.**]**  


### GetMetadata

```java
    public HashMap<String, TwinMetadata> GetMetadata();
```

**SRS_TWIN_PROPERTY_21_012: [**The GetMetadata shall return a hashMap to all metadata.**]**  
**SRS_TWIN_PROPERTY_21_020: [**If there is no metadata, the GetMetadata shall return null.**]**  


### GetMetadata

```java
    public TwinMetadata GetMetadata(String key)
```

**SRS_TWIN_PROPERTY_21_021: [**The GetMetadata shall return the TwinMetadata for the provided key.**]**  
**SRS_TWIN_PROPERTY_21_022: [**If there is no metadata, the GetMetadata shall return null.**]**  
**SRS_TWIN_PROPERTY_21_023: [**If the key do not exists, the GetMetadata shall return null.**]**  

### GetPropertyMap

```java
    public HashMap<String, String> GetPropertyMap();
```

**SRS_TWIN_PROPERTY_21_024: [**The GetPropertyMap shall return a hashMap with all keys and values stored on the Twin Property.**]**  
**SRS_TWIN_PROPERTY_21_025: [**If there is no key value, the GetPropertyMap shall return a null hashMap.**]**  


### toJson

```java
    public String toJson();
```

**SRS_TWIN_PROPERTY_21_026: [**The toJson shall create a String with information in the TwinProperty using json format.**]**  
**SRS_TWIN_PROPERTY_21_027: [**The toJson shall not include null fields.**]**  


### fromJson

```java
    public void fromJson(String json);
```

**SRS_TWIN_PROPERTY_21_028: [**The fromJson shall fill the fields in TwinProperty with the values provided in the json string.**]**  
**SRS_TWIN_PROPERTY_21_029: [**The fromJson shall not change fields that is not reported in the json string.**]**  
**SRS_TWIN_PROPERTY_21_030: [**If the provided json contains $Version, the fromJson shall update the version.**]**  
**SRS_TWIN_PROPERTY_21_031: [**If the provided json contains $Metadata, the fromJson shall update the metadata for each provided key.**]**  
**SRS_TWIN_PROPERTY_21_032: [**If there is no metadata, and the provided json contains $Metadata, the fromJson shall create a metadata instance.**]**  


### size

```java
    public int size();
```

**SRS_TWIN_PROPERTY_21_033: [**The size shall return the number of keys in the property map.**]**  


### get

```java
    public Object get(String key);
```

**SRS_TWIN_PROPERTY_21_034: [**The get shall return the property value related to the provided key.**]**  
**SRS_TWIN_PROPERTY_21_035: [**If the key does not exists, the get shall return null.**]**  
