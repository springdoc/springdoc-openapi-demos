---
layout: default
---
<h1> Configuration of springdoc-openapi </h1> 

* TOC
{:toc}

<h2> How to configure springdoc-openapi? </h2>

springdoc-openapi relies on standard [spring configuration properties](https://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-features.html#boot-features-external-config)  (yml or properties) using the standard files locations.

### springdoc-openapi-core properties

Parameter name | Default Value | Description
--- | --- | :-------
<a name="springdoc.api-docs.path"></a>`springdoc.api-docs.path` | `/v3/api-docs` | `String`, For custom path of the OpenAPI documentation in Json format.
<a name="springdoc.api-docs.enabled"></a>`springdoc.api-docs.enabled` | `true` | `Boolean`. To disable the springdoc-openapi endpoint (/v3/api-docs by default).
<a name="springdoc.packages-to-scan"></a>`springdoc.packages-to-scan` | `*`| `List of Strings`.The list of packages to scan (comma separated)
<a name="springdoc.paths-to-match"></a>`springdoc.paths-to-match` | `/*`| `List of Strings`.The list of paths to match (comma separated)
<a name="springdoc.paths-to-exclude"></a>`springdoc.paths-to-exclude` | | `List of Strings`.The list of paths to exclude (comma separated)
<a name="springdoc.packages-to-exclude"></a>`springdoc.packages-to-exclude` | | `List of Strings`.The list of packages to exclude (comma separated)
<a name="springdoc.default-consumes-media-type"></a>`springdoc.default-consumes-media-type` | `application/json` | `String`. The default consumes media type.
<a name="springdoc.default-produces-media-type"></a>`springdoc.default-produces-media-type` | `*/*` | `String`.The default produces media type.
<a name="springdoc.cache.disabled"></a>`springdoc.cache.disabled` | `false` | `Boolean`. To disable the springdoc-openapi cache of the the calculated OpenAPI. 
<a name="springdoc.show-actuator"></a>`springdoc.show-actuator` | `false` |  `Boolean`. To display the actuator endpoints.
<a name="springdoc.auto-tag-classes"></a>`springdoc.auto-tag-classes` | `true` | `Boolean`. To disable the springdoc-openapi automatic tags.
<a name="springdoc.model-and-view-allowed"></a>`springdoc.model-and-view-allowed` | `false` | `Boolean`. To allow RestControllers with ModelAndView return to appear in the OpenAPI descritpion.
<a name="springdoc.override-with-generic-response"></a>`springdoc.override-with-generic-response` | `true` | `Boolean`. When true, automatically adds @ControllerAdvice responses to all the generated responses.
<a name="springdoc.api-docs.groups.enabled"></a>`springdoc.api-docs.groups.enabled` | `true` | `Boolean`. To disable the springdoc-openapi groups.
<a name="springdoc.group-configs[0].group"></a>`springdoc.group-configs[0].group` | | `String`.The group name
<a name="springdoc.group-configs[0].packages-to-scan"></a>`springdoc.group-configs[0].packages-to-scan` | `*`| `List of Strings`.The list of packages to scan for a group (comma separated)
<a name="springdoc.group-configs[0].paths-to-match"></a>`springdoc.group-configs[0].paths-to-match` | `/*`| `List of Strings`.The list of paths to match for a group(comma separated)
<a name="springdoc.group-configs[0].paths-to-exclude"></a>`springdoc.group-configs[0].paths-to-exclude` | ``| `List of Strings`.The list of paths to exclude for a group(comma separated)
<a name="springdoc.group-configs[0].packages-to-exclude"></a>`springdoc.group-configs[0].packages-to-exclude` | | `List of Strings`.The list of packages to exclude for a group(comma separated)
<a name="springdoc.webjars.prefix"></a>`springdoc.webjars.prefix` | `/webjars` |`String`, To change the webjars prefix that is visible the url of swagger-ui for spring-webflux.
<a name="springdoc.api-docs.resolve-schema-properties"></a>`springdoc.api-docs.resolve-schema-properties` | `false` | `Boolean`. To enable  property resolver on @Schema (name, title and description).
<a name="springdoc.remove-broken-reference-definitions"></a>`springdoc.remove-broken-reference-definitions` | `true` | `Boolean`. To disable removal of broken reference defintions.

### swagger-ui properties
- The support of the swagger-ui properties is available on `springdoc-openapi`.  See [Official documentation](https://swagger.io/docs/open-source-tools/swagger-ui/usage/configuration/).

- You can use the same swagger-ui properties in the documentation as Spring Boot properties.
**NOTE**: All these properties should be declared with the following prefix: `springdoc.swagger-ui`

Parameter name | Default Value | Description
--------- | --- | :------------------------------------
<a name="springdoc.swagger-ui.path"></a>`springdoc.swagger-ui.path` | `/swagger-ui.html` |`String`, For custom path of the swagger-ui HTML documentation.
<a name="springdoc.swagger-ui.configUrl"></a>`springdoc.swagger-ui.configUrl` | `/v3/api-docs/swagger-config` |  `String`. URL to fetch external configuration document from.
<a name="springdoc.swagger-ui.layout"></a>`springdoc.swagger-ui.layout` | `BaseLayout`  | `String`. The name of a component available via the plugin system to use as the top-level layout for Swagger UI.
<a name="springdoc.swagger-ui.validatorUrl"></a>`springdoc.swagger-ui.validatorUrl` | `null` | By default, Swagger UI attempts to validate specs against swagger.io's online validator. You can use this parameter to set a different validator URL, for example for locally deployed validators ([Validator Badge](https://github.com/swagger-api/validator-badge)). Setting it to `null` will disable validation.
<a name="springdoc.swagger-ui.filter"></a>`springdoc.swagger-ui.filter` | `false` | `Boolean OR String`. If set, enables filtering. The top bar will show an edit box that you can use to filter the tagged operations that are shown. Can be Boolean to enable or disable, or a string, in which case filtering will be enabled using that string as the filter expression. Filtering is case sensitive matching the filter expression anywhere inside the tag.
<a name="springdoc.swagger-ui.operationsSorter"></a>`springdoc.swagger-ui.operationsSorter` | | `Function=(a => a)`. Apply a sort to the operation list of each API. It can be 'alpha' (sort by paths alphanumerically), 'method' (sort by HTTP method) or a function (see Array.prototype.sort() to know how sort function works). Default is the order returned by the server unchanged.
<a name="springdoc.swagger-ui.tagSorter"></a>`springdoc.swagger-ui.tagsSorter` |  | `Function=(a => a)`. Apply a sort to the tag list of each API. It can be 'alpha' (sort by paths alphanumerically) or a function (see [Array.prototype.sort()](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Array/sort) to learn how to write a sort function). Two tag name strings are passed to the sorter for each pass. Default is the order determined by Swagger UI.
<a name="springdoc.swagger-ui.oauth2RedirectUrl"></a>`springdoc.swagger-ui.oauth2RedirectUrl` | `/swagger-ui/oauth2-redirect.html` | `String`. OAuth redirect URL.
<a name="springdoc.swagger-ui.displayOperationId"></a>`springdoc.swagger-ui.displayOperationId` | `false` | `Boolean`. Controls the display of operationId in operations list. The default is `false`.
<a name="springdoc.swagger-ui.displayRequestDuration"></a>`springdoc.swagger-ui.displayRequestDuration` | `false` | `Boolean`. Controls the display of the request duration (in milliseconds) for "Try it out" requests.
<a name="springdoc.swagger-ui.deepLinking"></a>`springdoc.swagger-ui.deepLinking` | `false` | `Boolean`. If set to `true`, enables deep linking for tags and operations. See the [Deep Linking documentation](/docs/usage/deep-linking.md) for more information.
<a name="springdoc.swagger-ui.defaultModelsExpandDepth"></a>`springdoc.swagger-ui.defaultModelsExpandDepth` | `1` | `Number`. The default expansion depth for models (set to -1 completely hide the models).
<a name="springdoc.swagger-ui.defaultModelExpandDepth"></a>`springdoc.swagger-ui.defaultModelExpandDepth` | `1` | `Number`. The default expansion depth for the model on the model-example section.
<a name="springdoc.swagger-ui.defaultModelRendering"></a>`springdoc.swagger-ui.defaultModelRendering` |  | `String=["example"*, "model"]`. Controls how the model is shown when the API is first rendered. (The user can always switch the rendering for a given model by clicking the 'Model' and 'Example Value' links.)
<a name="springdoc.swagger-ui.docExpansion"></a>`springdoc.swagger-ui.docExpansion` |  | `String=["list"*, "full", "none"]`. Controls the default expansion setting for the operations and tags. It can be 'list' (expands only the tags), 'full' (expands the tags and operations) or 'none' (expands nothing).
<a name="springdoc.swagger-ui.maxDisplayedTags"></a>`springdoc.swagger-ui.maxDisplayedTags` |  | `Number`. If set, limits the number of tagged operations displayed to at most this many. The default is to show all operations.
<a name="springdoc.swagger-ui.showExtensions"></a>`springdoc.swagger-ui.showExtensions` | `false` | `Boolean`. Controls the display of vendor extension (`x-`) fields and values for Operations, Parameters, and Schema.
<a name="springdoc.swagger-ui.url"></a>`springdoc.swagger-ui.url` |  | `String`.To configure, the path of a custom OpenAPI file . Will be ignored if `urls` is used.
<a name="springdoc.swagger-ui.showCommonExtensions"></a>`springdoc.swagger-ui.showCommonExtensions` | `false` | `Boolean`. Controls the display of extensions (`pattern`, `maxLength`, `minLength`, `maximum`, `minimum`) fields and values for Parameters.
<a name="springdoc.swagger-ui.supportedSubmitMethods"></a>`springdoc.swagger-ui.supportedSubmitMethods` |  | `Array=["get", "put", "post", "delete", "options", "head", "patch", "trace"]`. List of HTTP methods that have the "Try it out" feature enabled. An empty array disables "Try it out" for all operations. This does not filter the operations from the display.
<a name="springdoc.swagger-ui.urls[0].url"></a>`springdoc.swagger-ui.urls[0].url` |  | `URL`. The url of the swagger group, used by Topbar plugin.  URLs must be unique among all items in this array, since they're used as identifiers.
<a name="springdoc.swagger-ui.urls[0].name"></a>`springdoc.swagger-ui.urls[0].name` |  | `String`. The name of the swagger group, used by Topbar plugin.  Names must be unique among all items in this array, since they're used as identifiers.
<a name="springdoc.swagger-ui.oauth.clientId"></a>`springdoc.swagger-ui.oauth.clientId` |  | `String`. Default clientId. MUST be a string.
<a name="springdoc.swagger-ui.oauth.clientSecret"></a>`springdoc.swagger-ui.oauth.clientSecret` |  | `String`.  Default clientSecret. Never use this parameter in your production environment. It exposes cruicial security information. This feature is intended for dev/test environments only.
<a name="springdoc.swagger-ui.oauth.realm"></a>`springdoc.swagger-ui.oauth.realm` |  | `String`. realm query parameter (for oauth1) added to authorizationUrl and tokenUrl.
<a name="springdoc.swagger-ui.oauth.appName"></a>`springdoc.swagger-ui.oauth.appName` |  | `String`. OAuth application name, displayed in authorization popup.
<a name="springdoc.swagger-ui.oauth.scopeSeparator"></a>`springdoc.swagger-ui.oauth.scopeSeparator` |  | `String`. OAuth scope separator for passing scopes, encoded before calling, default value is a space (encoded value %20).
<a name="springdoc.swagger-ui.oauth.additionalQueryStringParams"></a>`springdoc.swagger-ui.oauth.additionalQueryStringParams` |  | `String`. Additional query parameters added to authorizationUrl and tokenUrl.
<a name="springdoc.swagger-ui.oauth.useBasicAuthenticationWithAccessCodeGrant"></a>`springdoc.swagger-ui.oauth.useBasicAuthenticationWithAccessCodeGrant` | `false` | `Boolean`. Only activated for the accessCode flow. During the authorization_code request to the tokenUrl, pass the Client Password using the HTTP Basic Authentication scheme (Authorization header with Basic base64encode(client_id + client_secret)).
<a name="springdoc.swagger-ui.oauth.usePkceWithAuthorizationCodeGrant"></a>`springdoc.swagger-ui.oauth.usePkceWithAuthorizationCodeGrant` | `false` | `Boolean`.Only applies to authorizatonCode flows. Proof Key for Code Exchange brings enhanced security for OAuth public clients.


[back](./)