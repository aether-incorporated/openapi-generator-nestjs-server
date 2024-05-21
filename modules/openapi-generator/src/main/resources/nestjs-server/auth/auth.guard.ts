/*eslint-disable*/
import { CanActivate, ExecutionContext, Injectable } from '@nestjs/common';
import { Reflector } from '@nestjs/core';
import { AuthGuard } from '@nestjs/passport';

import { ApiKeyLocation, API_KEY_AUTH, BEARER_AUTH_KEY, OAUTH_KEY, OIDC_AUTH_KEY } from './auth.decorator';

@Injectable()
export class ApiKeyAuthGuard implements CanActivate {
    constructor(private reflector: Reflector) {}

    canActivate(context: ExecutionContext): any {
        const apiKeyAuth = this.reflector.get<{ location: ApiKeyLocation; keys: string[] }>(API_KEY_AUTH, context.getHandler());

        if (!apiKeyAuth) {
            return undefined;
        }

        // TODO -- check api key
        return false;
    }
}

@Injectable()
export class BearerAuthGuard extends AuthGuard('jwt') {
    constructor(private reflector: Reflector) {
        super();
    }

    canActivate(context: ExecutionContext): any {
        const isBearerAuth = this.reflector.get<boolean>(BEARER_AUTH_KEY, context.getHandler());

        if (!isBearerAuth) {
            return undefined;
        }

        return super.canActivate(context);
    }
}

@Injectable()
export class OAuthGuard extends AuthGuard('jwt') {
    constructor(private reflector: Reflector) {
        super();
    }

    async canActivate(context: ExecutionContext): Promise<any> {
        const oAuthScopes = this.reflector.get<string[]>(OAUTH_KEY, context.getHandler());

        if (!oAuthScopes) {
            return undefined;
        }

        // First validate the jwt is valid
        const isValidJwt = await super.canActivate(context);
        if (!isValidJwt) {
            return false;
        }

        // Then check if the user has the correct scopes
        if (oAuthScopes.length === 0) {
            return true;
        }
        const { user } = context.switchToHttp().getRequest();
        return oAuthScopes.some((scope) => user?.scopes?.includes(scope));
    }
}

// TODO -- implement this properly, right now it just acts as a JWT check
@Injectable()
export class OidcAuthGuard extends AuthGuard('jwt') {
    constructor(private reflector: Reflector) {
        super();
    }

    canActivate(context: ExecutionContext): any {
        const isOidcAuth = this.reflector.get<boolean>(OIDC_AUTH_KEY, context.getHandler());

        if (!isOidcAuth) {
            return undefined;
        }

        return super.canActivate(context);
    }
}

/**
 * Wrapper for ALL the auth guards used in the
 */
@Injectable()
export class AggregateAuthGuard implements CanActivate {
    constructor(
        private apiKeyAuthGuard: ApiKeyAuthGuard,
        private bearerAuthGuard: BearerAuthGuard,
        private oAuthGuard: OAuthGuard,
        private oidcAuthGuard: OidcAuthGuard
    ) {}

    async canActivate(context: ExecutionContext): Promise<any> {
        let canActivate: boolean | undefined = undefined;
        const authGuards = [this.apiKeyAuthGuard, this.bearerAuthGuard, this.oAuthGuard, this.oidcAuthGuard];

        for (const authGuard of authGuards) {
            const canActivateAuthGuard = await authGuard.canActivate(context);
            if (canActivateAuthGuard === true) {
                return true;
            }
            if (canActivateAuthGuard === false) {
                canActivate = false;
            }
        }

        // If it is undefined, none of the guards ran / were set, so allow access
        if (canActivate === undefined) {
            return true;
        }
        return false;
    }
}
