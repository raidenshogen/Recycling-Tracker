package org.example.ecopoints_recycling_tracker.Config;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;

/**
 * NEW FILE. Without this, an @PreAuthorize failure surfaces as a bare
 * 500/AccessDeniedException in the GraphQL response, which is
 * confusing on the frontend. This maps it to a proper FORBIDDEN /
 * UNAUTHORIZED GraphQL error the Angular error handler can branch on.
 */
@Configuration
public class GraphQlSecurityConfig {

    @Bean
    public DataFetcherExceptionResolverAdapter securityExceptionResolver() {
        return new DataFetcherExceptionResolverAdapter() {
            @Override
            protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
                if (ex instanceof AccessDeniedException) {
                    return GraphqlErrorBuilder.newError(env)
                            .message("You don't have permission to do that.")
                            .errorType(ErrorType.FORBIDDEN)
                            .build();
                }
                if (ex instanceof AuthenticationCredentialsNotFoundException) {
                    return GraphqlErrorBuilder.newError(env)
                            .message("Please log in first.")
                            .errorType(ErrorType.UNAUTHORIZED)
                            .build();
                }
                return null; // let other resolvers / default handling take it
            }
        };
    }
}
