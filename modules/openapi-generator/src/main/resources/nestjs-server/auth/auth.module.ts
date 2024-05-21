import { Module } from '@nestjs/common';
import { APP_GUARD } from '@nestjs/core';
import { JwtModule } from '@nestjs/jwt';
import { AggregateAuthGuard, ApiKeyAuthGuard, BearerAuthGuard, OAuthGuard, OidcAuthGuard } from './auth.guard';

import { JwtStrategy } from './jwt.strategy';

@Module({
    imports: [JwtModule.register({})],
    providers: [
        JwtStrategy,
        ApiKeyAuthGuard,
        BearerAuthGuard,
        OAuthGuard,
        OidcAuthGuard,
        {
            provide: APP_GUARD,
            useClass: AggregateAuthGuard,
        },
    ],
})
export class AuthModule {}
