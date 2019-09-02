package graphql.util;

import graphql.Internal;

import java.util.Objects;
import java.util.function.Function;

/**
 * Represents a function that accepts three parameters and produces a result.
 * This is a three-arity representation of {@link Function}, as well as
 * {@link java.util.function.BiFunction}.
 *
 * The functional method for this interface is {@link #apply(Object, Object, Object)}.
 *
 * @param <T> The type of the first parameter
 * @param <U> The type of the second parameter
 * @param <V> The type of the third parameter
 * @param <R> The type of the result of the function.
 */
@Internal
@FunctionalInterface
public interface TriFunction<T, U, V, R> {

    /**
     * Applies this function for the given parameters: t, u, and v.
     *
     * @param t the first parameter.
     * @param u the second parameter.
     * @param v the third parameter.
     *
     * @return the function result.
     */
    R apply(T t, U u, V v);

    /**
     * Returns a composed version of this function that when called applies this function to its
     * inputs and then applies the {@code after} to its result.
     *
     * @param <W>   the output type of the {@code after} function as well as the return type of the composed
     *              function.
     * @param after - the function to apply after this function is applied.
     *
     * @return - a composed function that first applies this function and then {@code after} to its result.
     * @throws NullPointerException if {@code after} is null.
     */
    default <W> TriFunction<T, U, V, W> andThen(Function<? super R, ? extends W> after) {
        Objects.requireNonNull(after, "The 'after' parameter cannot be null.");
        return (T t, U u, V v) -> after.apply(apply(t, u, v));
    }
}
