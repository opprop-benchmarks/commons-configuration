/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.commons.configuration;

import java.util.Iterator;

import junit.framework.TestCase;

/**
 * Test class for EnvironmentConfiguration.
 *
 * @author Oliver Heger
 * @version $Id$
 */
public class TestEnvironmentConfiguration extends TestCase
{
    /** Stores the configuration to be tested. */
    private EnvironmentConfiguration config;

    protected void setUp() throws Exception
    {
        super.setUp();
        config = new EnvironmentConfiguration();
    }

    /**
     * Tests whether a newly created configuration contains some properties. (We
     * expect that at least some properties are set in each environment.)
     */
    public void testInit()
    {
        boolean found = false;
        assertFalse("No properties found", config.isEmpty());
        for (Iterator it = config.getKeys(); it.hasNext();)
        {
            String key = (String) it.next();
            assertTrue("Key not found: " + key, config.containsKey(key));
            assertNotNull("No value for property " + key, config.getString(key));
            found = true;
        }
        assertTrue("No property keys returned", found);
    }

    /**
     * Tests removing properties. This should not be possible.
     */
    public void testClearProperty()
    {
        String key = (String) config.getKeys().next();
        try
        {
            config.clearProperty(key);
            fail("Could remove a property!");
        }
        catch (UnsupportedOperationException uoex)
        {
            // ok
        }
    }

    /**
     * Tests removing all properties. This should not be possible.
     */
    public void testClear()
    {
        try
        {
            config.clear();
            fail("Could remove properties!");
        }
        catch (UnsupportedOperationException uoex)
        {
            // ok
        }
    }

    /**
     * Tries to add another property. This should cause an exception.
     */
    public void testAddProperty()
    {
        try
        {
            config.addProperty("JAVA_HOME", "C:\\java");
            fail("Could add a property!");
        }
        catch (UnsupportedOperationException uoex)
        {
            // ok
        }
    }

    /**
     * Tries to set the value of a property. This should cause an exception.
     */
    public void testSetProperty()
    {
        try
        {
            config.setProperty("JAVA_HOME", "C:\\java");
            fail("Could set a property!");
        }
        catch (UnsupportedOperationException uoex)
        {
            // ok
        }
    }
}
