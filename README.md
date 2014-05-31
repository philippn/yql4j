About
=====

This repository contains a simple client library that can be used to query the Yahoo Query Language (YQL) web service.

For more information on YQL, please refer to: https://developer.yahoo.com/yql/

Usage Example
-------------

```
YqlClient client = new DefaultYqlClient();
YqlQuery query = new YqlQuery("select * from geo.oceans where name=@name");
query.addVariable("name", "Arctic Ocean");
YqlResult result = client.query(query);

// Now you can do whatever you like with the raw result
String rawResult = result.getContentAsString();

// But if you are lazy, you may also get the content mapped as object graph
// Please note though: You will have to provide your own mapping classes, 
// i.e. PlaceCollectionType and PlaceType!
QueryResultType<PlaceCollectionType> mappedResult = 
		result.getContentAsMappedObject(
				new TypeReference<QueryResultType<PlaceCollectionType>>() {});
for (PlaceType item : mappedResult.getResults().getContent()) {
	// Do something with the item
}
```

Features
--------
- *YqlQuery* takes care of building the request URL for you
    - Built-in parameters *diagnostics*, *env* etc. are supported
    - Support for YQL variable substitution
    - Support for YQl query aliases
- *YqlResult* supports mapping the content to an object graph
- Support OAuth signed requests (may or may not be useful)

What's missing
--------------
- Streaming support
- OAuth authentication (two-legged and three-legged)

