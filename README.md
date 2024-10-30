This is an example showing how to do distributed locking using PESSIMISTIC_WRITE in db.

This example requires a PostgreSQL database with a table named `MY_LOCK` which has only 1 column `ID`.

```
docker run -d --name postgres-container -p 5432:5432 -e POSTGRES_PASSWORD=postgres postgres:alpine
```