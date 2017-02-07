# TwinProperty Requirements

## Overview

TwinProperty is a representation of the device twin property database. It can represent `Desired` property as well `Reported` property.


## References

session 7.3.1 of Azure IoT Hub - Device Twin.


## Exposed API

```java
public class TwinProperty extends HashMap<String, Object> {

<<<<<<< HEAD
    public TwinProperty();
    public TwinProperty(Boolean reportMetadata);
    public void addProperty(String key, Object value, Integer version) throws IllegalArgumentException;

    public Integer GetVersion();
    public HashMap<String, TwinMetadata> GetMetadata();
    public TwinMetadata GetMetadata(String key);
    public HashMap<String, String> GetPropertyMap();

    public String toJson();
    public void fromJson(String json);
=======
    public TwinProperty()
    public void addProperty(String key, Object value, Integer version) throws IllegalArgumentException

    public Integer GetVersion();
    public TwinMetadata GetMetadata(String key)
    public HashMap<String, String> GetPropertyMap()

    public String toJson()
    public void fromJson(String json)
>>>>>>> addf8a754e926e0d488a2aea9316163f84e12f04
}
```


### TwinProperty

```java
<<<<<<< HEAD
    public TwinProperty();
=======
    public TwinProperty()
>>>>>>> addf8a754e926e0d488a2aea9316163f84e12f04
```

**SRS_TWIN_PROPERTY_21_001: [**The constructor shall call the constructor for the superClass.**]**  
**SRS_TWIN_PROPERTY_21_002: [**The constructor shall store set version equals 0.**]**  

<<<<<<< HEAD
```java
    public TwinProperty(Boolean reportMetadata);
```

**SRS_TWIN_PROPERTY_21_003: [**The constructor shall call the constructor for the superClass.**]**  
**SRS_TWIN_PROPERTY_21_004: [**The constructor shall store set version equals 0.**]**  
**SRS_TWIN_PROPERTY_21_005: [**If reportMetadata is true, constructor shall create a instance of the TwinMetadata to store the property metadata.**]**  
**SRS_TWIN_PROPERTY_21_006: [**If reportMetadata is false, constructor shall not create a instance of the TwinMetadata keeping it as null.**]**  
=======
>>>>>>> addf8a754e926e0d488a2aea9316163f84e12f04

### addProperty

```java
<<<<<<< HEAD
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
=======
    public Object addProperty(String key, Object value, Integer version) throws IllegalArgumentException
```

**SRS_TWIN_PROPERTY_21_004: [**The addProperty shall create an instance of the metadata related to the provided key and version.**]**  
**SRS_TWIN_PROPERTY_21_005: [**The addProperty shall add the created metadata to the `metadata`.**]**  
**SRS_TWIN_PROPERTY_21_006: [**The addProperty shall call the put in the superClass.**]**  
**SRS_TWIN_PROPERTY_21_007: [**The addProperty shall return the same `Object` returned by the superClass.**]**  
**SRS_TWIN_PROPERTY_21_008: [**If the `key` is null, the addProperty shall throw IllegalArgumentException.**]**  
**SRS_TWIN_PROPERTY_21_009: [**If the `key` is empty, the addProperty shall throw IllegalArgumentException.**]**  
**SRS_TWIN_PROPERTY_21_010: [**If the `key` is more than 128 characters long, the addProperty shall throw IllegalArgumentException.**]**  
**SRS_TWIN_PROPERTY_21_011: [**If the `key` has an illegal character, the addProperty shall throw IllegalArgumentException.**]**  
**SRS_TWIN_PROPERTY_21_012: [**If the `key` already exists, the addProperty shall replace the existed value by the new one.**]**  
>>>>>>> addf8a754e926e0d488a2aea9316163f84e12f04


### GetVersion

```java
    public Integer GetVersion();
```

<<<<<<< HEAD
**SRS_TWIN_PROPERTY_21_009: [**The GetVersion shall return an Integer with the property version stored in the `version`.**]**  


### GetMetadata

```java
    public HashMap<String, TwinMetadata> GetMetadata();
```

**SRS_TWIN_PROPERTY_21_012: [**The GetMetadata shall return a hashMap to all metadata.**]**  
**SRS_TWIN_PROPERTY_21_020: [**If there is no metadata, the GetMetadata shall return null.**]**  
=======
**SRS_TWIN_PROPERTY_21_013: [**The GetVersion shall return an Integer with the property version stored in the `version`.**]**  
>>>>>>> addf8a754e926e0d488a2aea9316163f84e12f04


### GetMetadata

```java
    public TwinMetadata GetMetadata(String key)
```

<<<<<<< HEAD
**SRS_TWIN_PROPERTY_21_021: [**The GetMetadata shall return the TwinMetadata for the provided key.**]**  
**SRS_TWIN_PROPERTY_21_022: [**If there is no metadata, the GetMetadata shall return null.**]**  
**SRS_TWIN_PROPERTY_21_023: [**If the key do not exists, the GetMetadata shall return null.**]**  

### GetPropertyMap

```java
    public HashMap<String, String> GetPropertyMap();
```

**SRS_TWIN_PROPERTY_21_024: [**The GetPropertyMap shall return a hashMap with all keys and values stored on the Twin Property.**]**  
**SRS_TWIN_PROPERTY_21_025: [**If there is no key value, the GetPropertyMap shall return a null hashMap.**]**  
=======
**SRS_TWIN_PROPERTY_21_014: [**The GetMetadata shall return the TwinMetadata for the provided key.**]**  
**SRS_TWIN_PROPERTY_21_015: [**If the key do not exists, the GetMetadata shall return null.**]**  
>>>>>>> addf8a754e926e0d488a2aea9316163f84e12f04


### toJson

```java
    public String toJson();
```

<<<<<<< HEAD
**SRS_TWIN_PROPERTY_21_026: [**The toJson shall create a String with information in the TwinProperty using json format.**]**  
**SRS_TWIN_PROPERTY_21_027: [**The toJson shall not include null fields.**]**  
=======
**SRS_TWIN_PROPERTY_21_016: [**The toJson shall create a String with information in the TwinProperty using json format.**]**  
**SRS_TWIN_PROPERTY_21_017: [**The toJson shall not include null fields.**]**  
>>>>>>> addf8a754e926e0d488a2aea9316163f84e12f04


### fromJson

```java
    public void fromJson(String json);
```

<<<<<<< HEAD
**SRS_TWIN_PROPERTY_21_028: [**The fromJson shall fill the fields in TwinProperty with the values provided in the json string.**]**  
**SRS_TWIN_PROPERTY_21_029: [**The fromJson shall not change fields that is not reported in the json string.**]**  
=======
**SRS_TWIN_PROPERTY_21_018: [**The fromJson shall fill the fields in TwinProperty with the values provided in the json string.**]**  
**SRS_TWIN_PROPERTY_21_019: [**The fromJson shall not change fields that is not reported in the json string.**]**  
>>>>>>> addf8a754e926e0d488a2aea9316163f84e12f04
