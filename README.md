# BigchainDB CRAB ORM

## Abstract
[BigchainDB](https://bigchaindb.com/) a blockchain database with some;
* Database characteristics: 
    * storing data in a MongoDB database
    * using the full power of MongoDB’s query engine to search and query all stored data
* Blockchain characteristics:
    * decentralization
    * immutability
    * native support for assets

Make it possible for the storage of data in a fully functional database with blockchain protection.

This thesis focus in implementing a ORM (Object Relational Mapping) on the BigchainDB database. 
Unlike other non blockchain database, BigchainDB doesn't support CRUD (Create, Read, Update and Delete) functionality.

Because of the immutability made possible by the blockchain technology, instead of `update` an `append ` can be made.
Also instead of `delete` a `burn` can be made.

This project focus on providing this `CRAB` functionality in Java.

## Aim
Aim of this project is to be able to use the BigchainDB database through a object relational model. Providing the CRAB functionality.