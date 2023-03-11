# MongoDB Client, Java
## Def
This is a simple client for MongoDB.
I use it in my projects.
## Example
    try (Mongo<RapnetDocument> mongo = new Mongo<>(RapnetDocument.class)) {
        //find source
        Bson projectionFields = Projections.fields(
                Projections.include("createDate"));
        Bson sort = Sorts.descending("createDate");
        MongoSelectQuery queryLastDoc = MongoSelectQuery.builder()
                .projection(projectionFields)
                .sort(sort)
                .limit(1)
                .build();
        List<RapnetDocument> resultList = mongo.executeSelect(queryLastDoc);
        RapnetDocument expected = resultList.get(0);
    
        //update source
        LocalDateTime date = LocalDateTime.now();
        expected.setCreateDate(date);
        UpdateResult updateResult = mongo.executeUpdateDoc(expected);
        System.out.println("Update Result: " + updateResult);
    
        //assert changes
        MongoSelectQuery queryExpectedDoc = MongoSelectQuery.builder()
                .filter(eq("_id", expected.getId()))
                .build();
        RapnetDocument actual = mongo.executeSelect(queryExpectedDoc).get(0);
        assertThat("expected is actual", expected, is(actual));
    }
