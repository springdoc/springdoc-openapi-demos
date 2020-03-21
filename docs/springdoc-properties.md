---
layout: default
---
<h1> Configuration of springdoc-openapi </h1> 

* TOC
{:toc}

<h2> How to configure springdoc-openapi? </h2>

springdoc-openapi relies on standard [spring configuration properties](https://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-features.html#boot-features-external-config)  (yml or properties) using the standard files locations.

### springdoc-openapi-core properties

| Parameter name |  Default Value    | Description |
|:---------------|:------------------|:------------|
springdoc.api-docs.path | `/v3/api-docs` | `String`, For custom path of the OpenAPI documentation in Json format.
springdoc.api-docs.enabled | `true` | `Boolean`. To disable the springdoc-openapi endpoint (/v3/api-docs by default).
springdoc.packages-to-scan | `*`| `List of Strings`.The list of packages to scan (comma separated)
springdoc.paths-to-match | `/*`| `List of Strings`.The list of paths to match (comma separated)
springdoc.paths-to-exclude | | `List of Strings`.The list of paths to exclude (comma separated)
springdoc.packages-to-exclude | | `List of Strings`.The list of packages to exclude (comma separated)
springdoc.default-consumes-media-type | `application/json` | `String`. The default consumes media type.
springdoc.default-produces-media-type | `*/*` | `String`.The default produces media type.
springdoc.cache.disabled | `false` | `Boolean`. To disable the springdoc-openapi cache of the the calculated OpenAPI. 
springdoc.show-actuator | `false` |  `Boolean`. To display the actuator endpoints.
springdoc.auto-tag-classes | `true` | `Boolean`. To disable the springdoc-openapi automatic tags.
springdoc.model-and-view-allowed | `false` | `Boolean`. To allow RestControllers with ModelAndView return to appear in the OpenAPI descritpion.
springdoc.override-with-generic-response | `true` | `Boolean`. When true, automatically adds @ControllerAdvice responses to all the generated responses.
springdoc.api-docs.groups.enabled | `true` | `Boolean`. To disable the springdoc-openapi groups.
springdoc.group-configs[0].group | | `String`.The group name
springdoc.group-configs[0].packages-to-scan | `*`| `List of Strings`.The list of packages to scan for a group (comma separated)
springdoc.group-configs[0].paths-to-match | `/*`| `List of Strings`.The list of paths to match for a group(comma separated)
springdoc.group-configs[0].paths-to-exclude | ``| `List of Strings`.The list of paths to exclude for a group(comma separated)
springdoc.group-configs[0].packages-to-exclude | | `List of Strings`.The list of packages to exclude for a group(comma separated)
springdoc.webjars.prefix | `/webjars` |`String`, To change the webjars prefix that is visible the url of swagger-ui for spring-webflux.
springdoc.api-docs.resolve-schema-properties | `false` | `Boolean`. To enable  property resolver on @Schema (name, title and description).
springdoc.remove-broken-reference-definitions | `true` | `Boolean`. To disable removal of broken reference defintions.

### swagger-ui properties
- The support of the swagger-ui properties is available on `springdoc-openapi`.  See [Official documentation](https://swagger.io/docs/open-source-tools/swagger-ui/usage/configuration/).

- You can use the same swagger-ui properties in the documentation as Spring Boot properties.
**NOTE**: All these properties should be declared with the following prefix: `springdoc.swagger-ui`

| Parameter name |  Default Value    | Description |
|:---------------|:------------------|:------------|
springdoc.swagger-ui.path | `/swagger-ui.html` |`String`, For custom path of the swagger-ui HTML documentation.
springdoc.swagger-ui.configUrl | `/v3/api-docs/swagger-config` |  `String`. URL to fetch external configuration document from.
springdoc.swagger-ui.layout | `BaseLayout`  | `String`. The name of a component available via the plugin system to use as the top-level layout for Swagger UI.
springdoc.swagger-ui.validatorUrl | `null` | By default, Swagger UI attempts to validate specs against swagger.io's online validator. You can use this parameter to set a different validator URL, for example for locally deployed validators ([Validator Badge](https://github.com/swagger-api/validator-badge)). Setting it to `null` will disable validation.
springdoc.swagger-ui.filter | `false` | `Boolean OR String`. If set, enables filtering. The top bar will show an edit box that you can use to filter the tagged operations that are shown. Can be Boolean to enable or disable, or a string, in which case filtering will be enabled using that string as the filter expression. Filtering is case sensitive matching the filter expression anywhere inside the tag.
springdoc.swagger-ui.operationsSorter | | `Function=(a => a)`. Apply a sort to the operation list of each API. It can be 'alpha' (sort by paths alphanumerically), 'method' (sort by HTTP method) or a function (see Array.prototype.sort() to know how sort function works). Default is the order returned by the server unchanged.
springdoc.swagger-ui.tagsSorter |  | `Function=(a => a)`. Apply a sort to the tag list of each API. It can be 'alpha' (sort by paths alphanumerically) or a function (see [Array.prototype.sort()](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Array/sort) to learn how to write a sort function). Two tag name strings are passed to the sorter for each pass. Default is the order determined by Swagger UI.
springdoc.swagger-ui.oauth2RedirectUrl | `/swagger-ui/oauth2-redirect.html` | `String`. OAuth redirect URL.
springdoc.swagger-ui.displayOperationId | `false` | `Boolean`. Controls the display of operationId in operations list. The default is `false`.
springdoc.swagger-ui.displayRequestDuration | `false` | `Boolean`. Controls the display of the request duration (in milliseconds) for "Try it out" requests.
springdoc.swagger-ui.deepLinking | `false` | `Boolean`. If set to `true`, enables deep linking for tags and operations. See the [Deep Linking documentation](/docs/usage/deep-linking.md) for more information.
springdoc.swagger-ui.defaultModelsExpandDepth | `1` | `Number`. The default expansion depth for models (set to -1 completely hide the models).
springdoc.swagger-ui.defaultModelExpandDepth | `1` | `Number`. The default expansion depth for the model on the model-example section.
springdoc.swagger-ui.defaultModelRendering |  | `String=["example"*, "model"]`. Controls how the model is shown when the API is first rendered. (The user can always switch the rendering for a given model by clicking the 'Model' and 'Example Value' links.)
springdoc.swagger-ui.docExpansion |  | `String=["list"*, "full", "none"]`. Controls the default expansion setting for the operations and tags. It can be 'list' (expands only the tags), 'full' (expands the tags and operations) or 'none' (expands nothing).
springdoc.swagger-ui.maxDisplayedTags |  | `Number`. If set, limits the number of tagged operations displayed to at most this many. The default is to show all operations.
springdoc.swagger-ui.showExtensions | `false` | `Boolean`. Controls the display of vendor extension (`x-`) fields and values for Operations, Parameters, and Schema.
springdoc.swagger-ui.url |  | `String`.To configure, the path of a custom OpenAPI file . Will be ignored if `urls` is used.
springdoc.swagger-ui.showCommonExtensions | `false` | `Boolean`. Controls the display of extensions (`pattern`, `maxLength`, `minLength`, `maximum`, `minimum`) fields and values for Parameters.
springdoc.swagger-ui.supportedSubmitMethods |  | `Array=["get", "put", "post", "delete", "options", "head", "patch", "trace"]`. List of HTTP methods that have the "Try it out" feature enabled. An empty array disables "Try it out" for all operations. This does not filter the operations from the display.
springdoc.swagger-ui.urls[0].url |  | `URL`. The url of the swagger group, used by Topbar plugin.  URLs must be unique among all items in this array, since they're used as identifiers.
springdoc.swagger-ui.urls[0].name |  | `String`. The name of the swagger group, used by Topbar plugin.  Names must be unique among all items in this array, since they're used as identifiers.
springdoc.swagger-ui.oauth.clientId |  | `String`. Default clientId. MUST be a string.
springdoc.swagger-ui.oauth.clientSecret |  | `String`.  Default clientSecret. Never use this parameter in your production environment. It exposes cruicial security information. This feature is intended for dev/test environments only.
springdoc.swagger-ui.oauth.realm |  | `String`. realm query parameter (for oauth1) added to authorizationUrl and tokenUrl.
springdoc.swagger-ui.oauth.appName |  | `String`. OAuth application name, displayed in authorization popup.
springdoc.swagger-ui.oauth.scopeSeparator |  | `String`. OAuth scope separator for passing scopes, encoded before calling, default value is a space (encoded value %20).
springdoc.swagger-ui.oauth.additionalQueryStringParams |  | `String`. Additional query parameters added to authorizationUrl and tokenUrl.
springdoc.swagger-ui.oauth.<br/>useBasicAuthenticationWithAccessCodeGrant | `false` | `Boolean`. Only activated for the accessCode flow. During the authorization_code request to the tokenUrl, pass the Client Password using the HTTP Basic Authentication scheme (Authorization header with Basic base64encode(client_id + client_secret)).
springdoc.swagger-ui.oauth.usePkceWithAuthorizationCodeGrant | `false` | `Boolean`.Only applies to authorizatonCode flows. Proof Key for Code Exchange brings enhanced security for OAuth public clients.


[back](./)