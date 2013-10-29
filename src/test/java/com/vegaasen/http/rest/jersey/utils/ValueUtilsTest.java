package com.vegaasen.http.rest.jersey.utils;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author <a href="vegard.aasen@gmail.com">vegardaasen</a>
 */
public class ValueUtilsTest {

    @Test
    public void shouldAssembleValues() {
        String one = "one";
        String two = "two";
        String assembled = ValueUtils.assembleValue(one, two);
        assertNotNull(assembled);
        assertFalse(assembled.isEmpty());
        assertTrue(assembled.startsWith(ValueUtils.CRLY_RIGHT));
        assertTrue(assembled.contains(one));
        assertTrue(assembled.contains(two));
        assertFalse(assembled.contains("three"));
    }

    @Test
    public void shouldNotDoAnything() {
        String assembled = ValueUtils.assembleValue("");
        assertNotNull(assembled);
        assertTrue(assembled.isEmpty());
    }

    @Test
    public void shouldNotDoAnythingWithNilledValue() {
        String assembled = ValueUtils.assembleValue((String) null);
        assertNotNull(assembled);
        assertTrue(assembled.isEmpty());
    }

    @Test
    public void shouldDisassemlbeString() {
        final String assembledString = "{one,two}";
        final List<String> disassembledStrings = ValueUtils.disassembleValue(assembledString);
        assertNotNull(disassembledStrings);
        assertFalse(disassembledStrings.isEmpty());
        assertEquals(2, disassembledStrings.size());
        assertTrue(disassembledStrings.contains("one"));
        assertTrue(disassembledStrings.contains("two"));
    }

    @Test
    public void shouldDisassemlbeStringWithNoSeparator() {
        final String assembledString = "{one}";
        final List<String> disassembledStrings = ValueUtils.disassembleValue(assembledString);
        assertNotNull(disassembledStrings);
        assertFalse(disassembledStrings.isEmpty());
        assertEquals(1, disassembledStrings.size());
        assertTrue(disassembledStrings.contains("one"));
        assertFalse(disassembledStrings.contains("two"));
    }

    @Test
    public void shouldDisassemlbeStrangeStringString() {
        final String assembledString = "{one{}{}{}{}}{}{}{}{}{},{},{}{,two}";
        final List<String> disassembledStrings = ValueUtils.disassembleValue(assembledString);
        assertNotNull(disassembledStrings);
        assertFalse(disassembledStrings.isEmpty());
        assertEquals(4, disassembledStrings.size());
        assertFalse(disassembledStrings.contains("one"));
        assertTrue(disassembledStrings.contains("two"));
        assertTrue(disassembledStrings.contains("{}"));
        assertTrue(disassembledStrings.contains("{}{"));
        assertTrue(disassembledStrings.contains("one{}{}{}{}}{}{}{}{}{}"));
    }

    @Test
    public void shouldNotDisassembleStringEmptyValue() {
        List<String> assembled = ValueUtils.disassembleValue("");
        assertNotNull(assembled);
        assertTrue(assembled.isEmpty());
    }

    @Test
    public void shouldNotDisassembleStringNilledValue() {
        List<String> assembled = ValueUtils.disassembleValue(null);
        assertNotNull(assembled);
        assertTrue(assembled.isEmpty());
    }

}
