/**
 * A NestJS request type.
 * Will either be express or fastify depending on the underlying platform used
 */
export type Request = any;

/**
 * A NestJS response type
 * Will either be express or fastify depending on the underlying platform used
 */
export type Response = any;

/**
 * A common generic type for all service methods to accept as input.
 * Contains all of the information pulled and formatted from the request,
 * as well as the raw request and response objects for advanced cases.
 */
export type ServiceContext<PayloadType = {}, HeadersType = {}, QueryParamsType = {}, PathParamsType = {}> = {
    payload: PayloadType;
    headers: HeadersType;
    queryParams: QueryParamsType;
    pathParams: PathParamsType;
    request: Request;
    response: Response;
}
