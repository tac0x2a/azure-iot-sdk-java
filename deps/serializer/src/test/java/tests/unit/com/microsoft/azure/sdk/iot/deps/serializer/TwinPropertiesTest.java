// Copyright (c) Microsoft. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package tests.unit.com.microsoft.azure.sdk.iot.deps.serializer;

import com.microsoft.azure.sdk.iot.deps.serializer.TwinProperties;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Unit tests for Twin Properties serializer
 */
public class TwinPropertiesTest {

    /* Tests_SRS_TWIN_PROPERTIES_21_001: [The Desired shall store an instance of the TwinProperty for the Twin `Desired` properties.] */
    /* Tests_SRS_TWIN_PROPERTIES_21_002: [The Reported shall store an instance of the TwinProperty for the Twin `Reported` properties.]*/
    @Test
    public void constructor_succeed()
    {
        // Arrange
        // Act
        TwinProperties twinProperties = new TwinProperties();

        // Assert
        assertThat(twinProperties.Desired.GetVersion(), is(0));
        assertThat(twinProperties.Reported.GetVersion(), is(0));
    }
}
