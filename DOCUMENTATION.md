# Abstract
**BigchainDB** provide a great and reliable to store data in a blockchain database. [BigchainDB Documentatioin Website][1]. But because of the processes that a
record have to go through before adding a record to the database, the system can be difficult to work with in a 
conventional application. So this Java API will provide an easier way to work with a **BigchainDB** database system 
in an **Object Relational Mapping** manner.

Records will be abstractly `created`, `read`, `updated` and `deleted` from a **BigchainDB**, as in conventional
relational databases like MySQL. Tho the records will not be `deleted` or `updated` in the actual database. But this API should act as
if they are.

# Create Operation
Creating a record will go through the following steps;
1. **Validation:** Object will first be validated to match the requirement of the mapped model.
2. **Sending Create Request to BigchainDB:** After the record meet the object model requirement. Then record will be send
to a BigchainDB through it's API as a blockchain transactions.

## Record Structure on The BigchainDB
In BigchainDB every transaction have a unique ID which this API will also use as the ID of a record.
```json
{
    "asset": {
        "columns_1": "1 Data....",
        "columns_2": "2 Data....",
        "columns_3": "3 Data...."
    },
    "metadata": {
        "table_name": "db_table_name",
        "_id": null,
        "burned": null,
        "timestamp": 243242344
    }
}
```
* `asset` will store records in database column format.
* `metadata->table_name` will store the name of the ORM model of the record.
* `metadata->_id` will store the id of the previous transaction of the same record. In the case of update operation,
the `_id` filed will store the id of the previous state of the record. When a find query is made on a record,
the program will make sure there is no other transaction on the BigchainDB database with `_id` linked to the queried record.
<table>
  <tr>
    <th>id</th>
    <th>_id</th>
    <th>records</th>
  </tr>
  <tr>
    <td>1</td>
    <td>null</td>
    <td>initial record state</td>
  </tr>
  <tr>
    <td>2</td>
    <td>1</td>
    <td>first record update</td>
  </tr>
  <tr>
    <td>3</td>
    <td>2</td>
    <td>second record update</td>
  </tr>
  <tr>
    <td>.....</td>
    <td>.....</td>
    <td>.....</td>
  </tr>
</table>

* `metadata->burned` will always be null. Unless a record is to be deleted. Then an appending will be done of the record. With **burned**
set to true. Then the API will perceive the record deleted if there is a transaction of the record with burned set to **true**.
* `metadata->timestamp` will store the timestamp of every transaction. Which will also act as timestamp of when records are created, 
update and deleted.
# Project Structure

## Annotations
Using annotation to map Java object to database records.

Using the following Annotation objects to map objects to database records.

* **Entity**: To indicate the object to be mapped.
* **Column**: To indicate declared fields of the object to be used as database column.
* **Id**: To indicate the float field to be used as database id.
* **Nullable**: If an column can be nullable.
* **Unique**: If an column should have unique values.

Basic relational database properties are implemented. Ignoring other validations
like value length check and others.

## Repository
The repository will do the annotation validation and the help in connecting the mapped object to 
the database table. Do the validations; Id, Nullable, Unique.  Because `BigchainDB` data is sored
NoSQL MongoDB database, so such validation are not supported.

# Terminology
* **Transaction:**
In BigchainDB, transactions are used to register, issue, create or transfer things (e.g. assets).
Transactions are the most basic kind of record stored by BigchainDB. [GitHub - BigchainDB Enhancement Proposals][2]

[1]: https://docs.bigchaindb.com/en/latest/     "BigchainDB Documentatioin Website"
[2]: https://github.com/bigchaindb/BEPs/tree/master/13  "GitHub - BigchainDB Enhancement Proposals"