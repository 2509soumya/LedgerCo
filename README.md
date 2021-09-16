About the Project
-----------------
This is a Spring boot project for a hypothetical startup LedgerCo,it helps issuing loans to users and enables them to check their balances given a particular time of the loan period.

Design Pattern Used
-------------------
App takes inspiration from the **Command Design Pattern** , the different commandhandlers are implemented as separate classes that implement a generic CommandExecutor interface.

Model , Data and Business Layers are separated from each other.

Time/Space Complexity of the App
--------------------------
LOAN , PAYMENT operation is O(1)
BALANCE operation is O(k) , k being the number of lumpsum payments made for the loan.

Further optimization using segment trees is possible for very large volume of data and a frequent update scenario.

Data Layer
----------
An inmemory h2 db is used as the data store,as the project uses spring jpa it is flexible enough and can be plugged into any desired database.


Usage
-----
java -jar (Pass the name of jar) (location of the file containing the command)
