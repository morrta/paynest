## Context

Our domain currently needs to represent multiple types of identifiers such as:

- WalletId
- CustomerId

Although these identifiers may use the same underlying representation such as UUID or String they have different **domain 
meanings** 
Using raw Strings or UUIDs in method signatures makes it possible to accidentally pass one identifier type where another
is expected and the compiler won't be able to distinguish a WalletId from a CustomerId for example. 
# Trade-Offs: 
Although Strongly typed IDs introduce additional code and complexity, there is a lot to gain from using them:
 
- Compile-time type safety: 
- Better domain expressiveness
- Centralized validation

# Decision
Throughout this project we will enforce the usage of strongly typed Id value objects rather than raw String or UUID values

