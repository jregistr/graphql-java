package graphql.schema.idl;

import graphql.GraphQLError;
import graphql.Internal;
import graphql.language.Argument;
import graphql.language.Directive;
import graphql.language.StringValue;
import graphql.schema.idl.errors.InvalidDeprecationDirectiveError;
import graphql.schema.idl.errors.SchemaProblem;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

@Internal
public interface SchemaValidityChecker {

    @FunctionalInterface
    interface SchemaTypeCheckerRule {
        void apply(TypeDefinitionRegistry typeRegistry, List<GraphQLError> errors, RuntimeWiring wiring);
    }

    void check(TypeDefinitionRegistry typeRegistry, List<GraphQLError> errors, RuntimeWiring wiring) throws SchemaProblem;

    default SchemaValidityChecker andThen(SchemaValidityChecker after) {
        SchemaValidityChecker self = this;

        return (typeRegistry, wiring, enforceSchemaDirectives) -> {
            self.check(typeRegistry, wiring, enforceSchemaDirectives);
            after.check(typeRegistry, wiring, enforceSchemaDirectives);
        };
    }

    /**
     * A special check for the magic @deprecated directive
     *
     * @param errors        the list of errors
     * @param directive     the directive to check
     * @param errorSupplier the error supplier function
     */
    static void checkDeprecatedDirective(List<GraphQLError> errors, Directive directive, Supplier<InvalidDeprecationDirectiveError> errorSupplier) {
        if ("deprecated".equals(directive.getName())) {
            // it can have zero args
            List<Argument> arguments = directive.getArguments();
            if (arguments.size() == 0) {
                return;
            }
            // but if has more than it must have 1 called "reason" of type StringValue
            if (arguments.size() == 1) {
                Argument arg = arguments.get(0);
                if ("reason".equals(arg.getName()) && arg.getValue() instanceof StringValue) {
                    return;
                }
            }
            // not valid
            errors.add(errorSupplier.get());
        }
    }

    /**
     * A simple function that takes a list of things, asks for their names and checks that the
     * names are unique within that list.  If not it calls the error handler function
     *
     * @param errors            the error list
     * @param listOfNamedThings the list of named things
     * @param namer             the function naming a thing
     * @param errorFunction     the function producing an error
     */
    static <T, E extends GraphQLError> void checkNamedUniqueness(List<GraphQLError> errors, List<T> listOfNamedThings, Function<T, String> namer, BiFunction<String, T, E> errorFunction) {
        Set<String> names = new LinkedHashSet<>();
        listOfNamedThings.forEach(thing -> {
            String name = namer.apply(thing);
            if (names.contains(name)) {
                errors.add(errorFunction.apply(name, thing));
            } else {
                names.add(name);
            }
        });
    }
}
