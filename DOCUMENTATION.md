# Abstract
**BigchainDB** provide a great and reliable to store data in a blockchain database. But because of the processes that a
record have to go through before adding a record to the database, the system can be difficult to work with in a 
conventional application. So this Java API will provide an easier way to work with a **BigchainDB** database system 
in an **Object Relational Mapping** manner.

Records will be abstractly `created`, `read`, `updated` and `deleted` in a **BigchainDB**, as in conventional
relational databases like MySQL. Tho the records will not be actually be `deleted` or `updated`. But this API should act as
if they are.

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


## Transaction Structure
Data will be stored in a transactions in the following format;


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

