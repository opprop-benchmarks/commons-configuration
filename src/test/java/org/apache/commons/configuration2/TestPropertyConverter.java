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

package org.apache.commons.configuration2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

/**
 * Test class for PropertyConverter.
 *
 * @author Emmanuel Bourg
 * @version $Revision$, $Date$
 */
public class TestPropertyConverter extends TestCase
{
    /** An array with test values for the flatten test.*/
    private static final Integer[] FLATTEN_VALUES = { 1, 2, 3, 4, 5, 6, 28 };

    public void testSplit()
    {
        String s = "abc, xyz , 123";
        List<String> list = PropertyConverter.split(s, ',');

        assertEquals("size", 3, list.size());
        assertEquals("1st token for '" + s + "'", "abc", list.get(0));
        assertEquals("2nd token for '" + s + "'", "xyz", list.get(1));
        assertEquals("3rd token for '" + s + "'", "123", list.get(2));
    }

    public void testSplitNoTrim()
    {
        String s = "abc, xyz , 123";
        List<String> list = PropertyConverter.split(s, ',', false);

        assertEquals("size", 3, list.size());
        assertEquals("1st token for '" + s + "'", "abc", list.get(0));
        assertEquals("2nd token for '" + s + "'", " xyz ", list.get(1));
        assertEquals("3rd token for '" + s + "'", " 123", list.get(2));
    }

    public void testSplitWithEscapedSeparator()
    {
        String s = "abc\\,xyz, 123";
        List<String> list = PropertyConverter.split(s, ',');

        assertEquals("size", 2, list.size());
        assertEquals("1st token for '" + s + "'", "abc,xyz", list.get(0));
        assertEquals("2nd token for '" + s + "'", "123", list.get(1));
    }

    public void testSplitEmptyValues()
    {
        String s = ",,";
        List<String> list = PropertyConverter.split(s, ',');

        assertEquals("size", 3, list.size());
        assertEquals("1st token for '" + s + "'", "", list.get(0));
        assertEquals("2nd token for '" + s + "'", "", list.get(1));
        assertEquals("3rd token for '" + s + "'", "", list.get(2));
    }

    public void testSplitWithEndingSlash()
    {
        String s = "abc, xyz\\";
        List<String> list = PropertyConverter.split(s, ',');

        assertEquals("size", 2, list.size());
        assertEquals("1st token for '" + s + "'", "abc", list.get(0));
        assertEquals("2nd token for '" + s + "'", "xyz\\", list.get(1));
    }

    public void testSplitNull()
    {
        List<String> list = PropertyConverter.split(null, ',');
        assertNotNull(list);
        assertTrue(list.isEmpty());
    }

    /**
     * Tests whether an escape character can be itself escaped.
     */
    public void testSplitEscapeEscapeChar()
    {
        List<String> list = PropertyConverter.split("C:\\Temp\\\\,xyz", ',');
        assertEquals("Wrong list size", 2, list.size());
        assertEquals("Wrong element 1", "C:\\Temp\\", list.get(0));
        assertEquals("Wrong element 2", "xyz", list.get(1));
    }

    /**
     * Tests whether delimiters are correctly escaped.
     */
    public void testEscapeDelimiters()
    {
        assertEquals("Wrong escaped delimiters",
                "C:\\\\Temp\\\\\\,D:\\\\Data\\\\", PropertyConverter
                        .escapeDelimiters("C:\\Temp\\,D:\\Data\\", ','));
    }

    public void testToIterator()
    {
        int[] array = new int[]{1, 2, 3};

        Iterator<?> it = PropertyConverter.toIterator(array, ',');

        assertEquals("1st element", 1, it.next());
        assertEquals("2nd element", 2, it.next());
        assertEquals("3rd element", 3, it.next());
    }

    /**
     * Tests flattening an array of values.
     */
    public void testFlattenArray()
    {
        checkFlattenResult(PropertyConverter.flatten(FLATTEN_VALUES, ','));
    }

    /**
     * Tests flattening a collection.
     */
    public void testFlattenCollection()
    {
        checkFlattenResult(PropertyConverter.flatten(Arrays
                .asList(FLATTEN_VALUES), ','));
    }

    /**
     * Tests flattening an iterator.
     */
    public void testFlattenIterator()
    {
        checkFlattenResult(PropertyConverter.flatten(Arrays.asList(
                FLATTEN_VALUES).iterator(), ','));
    }

    /**
     * Tests flattening a comma delimited string.
     */
    public void testFlattenString()
    {
        StringBuilder buf = new StringBuilder();
        for (Integer val : FLATTEN_VALUES)
        {
            if (buf.length() > 0)
            {
                buf.append(',');
            }
            buf.append(val);
        }
        checkFlattenResult(PropertyConverter.flatten(buf.toString(), ','));
    }

    /**
     * Tests flattening a null value.
     */
    public void testFlattenNull()
    {
        assertTrue("Result collection not empty", PropertyConverter.flatten(
                null, ',').isEmpty());
    }

    /**
     * Tests the flatten() method with a complex mixed input.
     */
    public void testFlattenMixed()
    {
        Collection<Object> data = new ArrayList<Object>();
        data.add(1);
        data.add("2,3");
        Object[] ar = new Object[2];
        ar[0] = 4;
        Collection<Object> data2 = new ArrayList<Object>();
        data2.add("5");
        data2.add(new Object[] {
                6, "28"
        });
        ar[1] = data2;
        data.add(ar);
        checkFlattenResult(PropertyConverter.flatten(data, ','));
    }

    /**
     * Tests the result of a flatten operation.
     *
     * @param col the resulting collection
     */
    private void checkFlattenResult(Collection<?> col)
    {
        assertEquals("Wrong number of elements", FLATTEN_VALUES.length, col
                .size());
        Iterator<?> it = col.iterator();
        for (Integer val : FLATTEN_VALUES)
        {
            assertEquals("Wrong value in result", val.toString(), String
                    .valueOf(it.next()));
        }
    }

    /**
     * Tests the interpolation features.
     */
    public void testInterpolateString()
    {
        PropertiesConfiguration config = new PropertiesConfiguration();
        config.addProperty("animal", "quick brown fox");
        config.addProperty("target", "lazy dog");
        assertEquals("Wrong interpolation",
                "The quick brown fox jumps over the lazy dog.",
                PropertyConverter.interpolate("The ${animal} jumps over the ${target}.", config));
    }

    /**
     * Tests interpolation of an object. Here nothing should be substituted.
     */
    public void testInterpolateObject()
    {
        assertEquals("Object was not correctly interpolated", 42,
                PropertyConverter.interpolate(42, new PropertiesConfiguration()));
    }

    /**
     * Tests complex interpolation where the variables' values contain in turn
     * other variables.
     */
    public void testInterpolateRecursive()
    {
        PropertiesConfiguration config = new PropertiesConfiguration();
        config.addProperty("animal", "${animal_attr} fox");
        config.addProperty("target", "${target_attr} dog");
        config.addProperty("animal_attr", "quick brown");
        config.addProperty("target_attr", "lazy");
        assertEquals("Wrong complex interpolation",
                "The quick brown fox jumps over the lazy dog.",
                PropertyConverter.interpolate("The ${animal} jumps over the ${target}.", config));
    }

    /**
     * Tests an interpolation that leads to a cycle. This should throw an
     * exception.
     */
    public void testCyclicInterpolation()
    {
        PropertiesConfiguration config = new PropertiesConfiguration();
        config.addProperty("animal", "${animal_attr} ${species}");
        config.addProperty("animal_attr", "quick brown");
        config.addProperty("species", "${animal}");
        try
        {
            PropertyConverter.interpolate("This is a ${animal}", config);
            fail("Cyclic interpolation was not detected!");
        }
        catch (IllegalStateException iex)
        {
            // ok
        }
    }

    /**
     * Tests interpolation if a variable is unknown. Then the variable won't be
     * substituted.
     */
    public void testInterpolationUnknownVariable()
    {
        PropertiesConfiguration config = new PropertiesConfiguration();
        config.addProperty("animal", "quick brown fox");
        assertEquals("Wrong interpolation",
                "The quick brown fox jumps over ${target}.",
                PropertyConverter.interpolate("The ${animal} jumps over ${target}.", config));
    }

}
