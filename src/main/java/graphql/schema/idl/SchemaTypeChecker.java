package graphql.schema.idl;

import graphql.GraphQLError;
import graphql.Internal;
import graphql.schema.idl.errors.SchemaProblem;

import java.util.ArrayList;
import java.util.List;

/**
 * This helps pre check the state of the type system to ensure it can be made into an executable schema.
 * <p>
 * It looks for missing types and ensure certain invariants are true before a schema can be made.
 */
@Internal
public final class SchemaTypeChecker {

    private final SchemaValidityChecker validityChecker;

    @Deprecated
    public SchemaTypeChecker() {
        validityChecker = null;
    }

    public SchemaTypeChecker(TypeDefinitionRegistry typeRegistry, RuntimeWiring wiring, boolean enforceSchemaDirectives) {
        validityChecker = null;
    }

    /**
     *
     * @param typeRegistry
     * @param wiring
     * @param enforceSchemaDirectives set to true if this checker should
     * @return a list of zero or more errors found while checking the registry.
     * @throws SchemaProblem if there are problems in assembling a schema such as missing type resolvers or no operations defined
     * @deprecated Please instead construct this class by calling any constructor other than the default and using {@link SchemaTypeChecker#runChecks()}
     */
    @Deprecated
    public List<GraphQLError> checkTypeRegistry(TypeDefinitionRegistry typeRegistry, RuntimeWiring wiring, boolean enforceSchemaDirectives) throws SchemaProblem {
//        List<GraphQLError> errors = new ArrayList<>();
//        checkForMissingTypes(errors, typeRegistry);

//        SchemaTypeExtensionsChecker typeExtensionsChecker = new SchemaTypeExtensionsChecker();

//        typeExtensionsChecker.checkTypeExtensions(errors, typeRegistry);

//        checkInterfacesAreImplemented(errors, typeRegistry);

//        checkSchemaInvariants(errors, typeRegistry);

//        checkScalarImplementationsArePresent(errors, typeRegistry, wiring);
//        checkTypeResolversArePresent(errors, typeRegistry, wiring);

//        checkFieldsAreSensible(errors, typeRegistry);

        //check directive definitions before checking directive usages
//        checkDirectiveDefinitions(typeRegistry, errors);

//        if (enforceSchemaDirectives) {
//            SchemaTypeDirectivesChecker directivesChecker = new SchemaTypeDirectivesChecker(typeRegistry, wiring);
//            directivesChecker.checkTypeDirectives(errors);
//        }

//        return errors;
        return new SchemaTypeChecker(typeRegistry, wiring, enforceSchemaDirectives).runChecks();
    }

    public List<GraphQLError> runChecks() throws SchemaProblem {
        throw new UnsupportedOperationException("Not yet implemented");
    }

}
