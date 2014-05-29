About
=====

This repository contains a simple client library that can be used to query the Yahoo Query Language (YQL) web service.

For more information on YQL, please refer to: https://developer.yahoo.com/yql/

Usage Example
-------------

```
YqlClient client = new DefaultYqlClient();
YqlQuery query = new YqlQuery("select * from geo.oceans");
YqlResult result = client.query(query);
String result = result.getContent();
```

Features
--------
- *YqlQuery* takes care of building the request URL for you
- Parameters *diagnostics*, *env* etc. are supported
- OAuth support (untested though)

What's missing
--------------
- Streaming support

