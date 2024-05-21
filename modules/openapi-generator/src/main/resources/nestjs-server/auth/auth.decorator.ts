import { SetMetadata } from '@nestjs/common';

export const API_KEY_AUTH = 'API_KEY_AUTH';
export const BEARER_AUTH_KEY = 'BEARER_AUTH';
export const OAUTH_KEY = 'OAUTH';
export const OIDC_AUTH_KEY = 'OIDC_AUTH';

export enum ApiKeyLocation {
    HEADER = 'HEADER',
    QUERY = 'QUERY',
    COOKIE = 'COOKIE',
}

/**
 * Mark a route as requiring an api key for authentication
 * @param location -- the location of the api key
 * @param keys -- the key(s) to compare (usually one, but could be multiple)
 */
export const ApiKeyAuth = (location: ApiKeyLocation, ...keys: string[]) => {
    return SetMetadata(API_KEY_AUTH, {
        location,
        keys,
    });
};

/**
 * Mark a route as requiring JWT authentication
 */
export const BearerAuth = () => SetMetadata(BEARER_AUTH_KEY, true);

/**
 * Mark a route as requiring JWT authentication with OAuth
 * @param scopes -- the list of scopes required for authorization (if ANY match, auth is approved)
 */
export const OAuth = (...scopes: string[]) => SetMetadata(OAUTH_KEY, scopes);

/**
 * Mark a route as requiring OIDC auth
 *
 * NOT CURRENTLY SUPPORTED (TODO)
 */
export const OidcAuth = () => SetMetadata(OIDC_AUTH_KEY, true);
