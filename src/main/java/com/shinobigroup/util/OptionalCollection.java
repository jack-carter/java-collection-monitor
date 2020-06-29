package com.shinobigroup.util;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Consumer;

public class OptionalCollection<T> {

    public static <T> OptionalCollection<T> ofNullable(final Collection<T> collection) {
        return new OptionalCollection<T>(collection, false);
    }

    public static <T> OptionalCollection<T> of(final Collection<T> collection) {
        return new OptionalCollection<T>(collection, true);
    }

    private Optional<Collection<T>> collection;

    public OptionalCollection(final Collection<T> collection, final boolean shouldBeStrict) {
        this.collection = shouldBeStrict ? Optional.of(collection) : Optional.ofNullable(collection);
    }

    public OptionalCollection(final Collection<T> collection) {
        this(collection, false);
    }

    public Collection<T> get() {
        return collection.get();
    }

    public boolean isPresent() {
        return collection.isPresent();
    }

    public OptionalCollection<T> ifPresent(final Consumer<Collection<T>> consumer) {
        if (isPresent()) {
            consumer.accept(collection.get());
        }
        return this;
    }

    public OptionalCollection<T> ifNotPresent(final Runnable runnable) {
        if (!isPresent()) {
            runnable.run();
        }
        return this;
    }

    public boolean isEmpty() {
        return !isPresent() || collection.get().isEmpty();
    }

    public OptionalCollection<T> ifEmpty(final Runnable runnable) {
        if (isEmpty()) {
            runnable.run();
        }
        return this;
    }

    public OptionalCollection<T> ifNotEmpty(final Consumer<Collection<T>> consumer) {
        if (!isEmpty()) {
            consumer.accept(collection.get());
        }
        return this;
    }

    /**
     * Return the value if present, otherwise return 'other'
     * 
     * @param other
     * @return other when not present, underlying collection at all other times
     */
    public Collection<T> orElse(final Collection<T> other) {
        return isPresent() ? collection.get() : other;
    }

}
