# BigchainDB CRAB ORM

## Abstract
Object Relational Mapping (ORM) provides a methodology and mechanism for object-oriented systems to hold their long-term
data safely in a database, with transactional control over it, yet have it expressed when needed in program object. [1]

This makes it very easy to work with relational databases in storing and manging application data.

A recent invention, BigchainDB, a scalable blockchain database. Which make use of blockchain and decentralized ecosystem
to provide an immutable database storage. [2]

But because of features of BigchainDB the conventional ORM model cannot be used on a BigchainDB database.

A conventional ORM model supports CRUD (Create, Read, Update & Delete) operations, which cannot be implemented on a 
database which is on a blockchain.

Data cannot be deleted nor updated in a blockchain database. Blockchain data are made imutabble in several ways. No APIs
for changing or deleting data, replication to all nodes, internal watch dogs, etc. [3]

So it's practically impossible to have the CRUD model on a blockchain database like BigchainDB, another model, 
CRAB (Create, Retrieve, Append & Burn) have to implemented.

So in this project, a CRAB ORM model is implemented on a BigchainDB in Java.

## Create Operation
Records will be created as a transactions on a BigchainDB, where as each mapped object will have a unique transaction
structure and transaction meta values. Because BigchainDB is build on a MongoDB database which stores records as Json
objects, so records of a mapped object cannot be stored together in a table structure.

Every transaction have a unique ID which is a SHA3-256 hash of the transaction, loosely speaking. [4]

This will act as an ID to the records stored in the transaction.

```json
{
    "asset": {
        "columns_1": "1 Data....",
        "columns_2": "2 Data....",
        "columns_3": "3 Data...."
    },
    "metadata": {
        "trans_id": 1,
        "static_id": 1,
        "table_name": "db_table_name",
        "prev_id": null,
        "burned": null,
        "timestamp": 243242344
    }
}
```
* `asset` will store records in database column format.
* `metadata->transaction_id` will store the unique ID of every transaction.
* `metadata->static_id` will store the unique ID of an object in the database.
* `metadata->table_name` will store the name of the ORM model the current record belongs to.
* `metadata->previous_id` will store the id of the previous transaction of the same record. In the case of update operation,
the `_id` filed will store the id of the previous state of the record. When a find query is made on a record,
the program will make sure there is no other transaction on the BigchainDB database with `_id` linked to the queried record.
* `metadata->burned` will be set true if a record is to be burned.
* `metadata->timestamp` will be the timestamp the transaction is made. This will give info on when a record is created and
all the updates made on the transactions.

## Read Operation
Every object stored in a database should be retrieved when needed. In this CRAB model, there can be more than one transactions
records for a single object record. Because when appends (updates) are made, there will be different version of the object 
record stored in the BlockchainDB database. It is also possible for a system to reference an outdated object record, 
when using the transaction ID to reference to an object record.

In read operations, when a query into an object is made, a secondary background operation is will be executed to check if
the retrieved record is the latest version of the object record. If not the latest version of the record will be returned 
and a list of the previous version of the record will also be attached to the request.

## Append Operation
The update operation of a CRUD model is replaced by append in a CRAB model. Because records in a blockchain database like
BigchainDB cannot be changed, appends can be adapted to show change in record state. 
An append operation on a record will register a transaction with the changes on the record and the prev_id key in metadata
block will have the id of the previous transaction of the record. But the static_id will persist.

## Burn Operation
Burn operation will mark a signature on an object record to indicate the deletion of the record. But the records won't be
really deleted from the database. The metadata->burned key will be set true and asset be set to null. 

But still the metadata->prev_id will be pointed to the previous state of the record. In case a read on that state of the
record is made, it could be detected that this record is burned.

[1]: https://dl.acm.org/citation.cfm?id=1376773     "Object/relational mapping 2008: hibernate and the entity data model (edm)"
[2]: https://docs.bigchaindb.com/en/latest      "BigchainDB Documentation"
[3]: https://docs.bigchaindb.com/en/latest/immutable.html       "How BigchainDB is Immutable"
[4]: https://github.com/bigchaindb/BEPs/tree/master/13#transaction-components       "BigchainDB Transaction Componenets"