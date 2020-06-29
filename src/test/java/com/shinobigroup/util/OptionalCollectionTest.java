package com.shinobigroup.util;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Test;

public class OptionalCollectionTest {

    private static final List<String> EMPTY = Collections.emptyList();
    private static final List<String> NON_EMPTY = Arrays.asList("one", "two", "three");

    @Test
    public void shouldAllowConstructionFromNull() {
        OptionalCollection<String> op = new OptionalCollection<String>(null);

        check(op, false, true);
    }

    @Test
    public void shouldAllowConstructionFromExistingEmptyCollection() {
        OptionalCollection<String> op = new OptionalCollection<String>(EMPTY);

        check(op, true, true);
    }

    @Test
    public void shouldAllowConstructionFromExistingNonEmptyCollection() {
        OptionalCollection<String> op = new OptionalCollection<String>(NON_EMPTY);

        check(op, true, false);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowExceptionFromStaticOfMethodWithNull() {
        OptionalCollection.of(null);
    }

    @Test
    public void shouldCreateFromStaticOfMethodWithExistingEmptyCollection() {
        OptionalCollection<String> op = OptionalCollection.of(EMPTY);

        check(op, true, true);
    }

    @Test
    public void shouldCreateFromStaticOfMethodWithExistingNonEmptyCollection() {
        OptionalCollection<String> op = OptionalCollection.of(NON_EMPTY);

        check(op, true, false);
    }

    @Test
    public void shouldCreateFromStaticOfNullableMethodWithNull() {
        OptionalCollection<String> op = OptionalCollection.ofNullable(null);

        check(op, false, true);
    }

    @Test
    public void shouldCreateFromStaticOfNullableMethodWithExistingEmptyCollection() {
        OptionalCollection<String> op = OptionalCollection.ofNullable(EMPTY);

        check(op, true, true);
    }

    @Test
    public void shouldCreateFromStaticOfNullableMethodWithExistingNonEmptyCollection() {
        OptionalCollection<String> op = OptionalCollection.ofNullable(NON_EMPTY);

        check(op, true, false);
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowExceptionWhenGetUsedOnNullTarget() {
        OptionalCollection<String> op = new OptionalCollection<String>(null);
        op.get();
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowExceptionOnGetWhenBuiltFromNullOfNullable() {
        OptionalCollection<String> op = OptionalCollection.ofNullable(null);
        op.get();
    }

    @Test
    public void shouldReturnOtherWhenNotPresent() {
        OptionalCollection<String> op = new OptionalCollection<String>(null);

        assertThat(op.orElse(NON_EMPTY),is(NON_EMPTY));
    }

    @Test
    public void shouldReturnCollectionWhenPresentAndEmpty() {
        OptionalCollection<String> op = new OptionalCollection<String>(EMPTY);

        assertThat(op.orElse(NON_EMPTY),is(EMPTY));
    }

    @Test
    public void shouldReturnCollectionWhenPresentAndNonEmpty() {
        OptionalCollection<String> op = new OptionalCollection<String>(NON_EMPTY);

        assertThat(op.orElse(EMPTY),is(NON_EMPTY));
    }

    private void check(final OptionalCollection<String> op, final boolean shouldBePresent, final boolean shouldBeEmpty) {
        assertThat(op.isPresent(),is(shouldBePresent));
        assertThat(op.isEmpty(),is(shouldBeEmpty));

        op.ifPresent(col -> assertThat(shouldBePresent,is(true)));
        op.ifNotPresent(() -> assertThat(shouldBePresent,is(false)));
        op.ifEmpty(() -> assertThat(shouldBeEmpty,is(true)));
        op.ifNotEmpty(col -> assertThat(shouldBeEmpty,is(false)));
    }
}
